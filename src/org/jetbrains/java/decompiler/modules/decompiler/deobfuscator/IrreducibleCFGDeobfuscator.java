// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.java.decompiler.modules.decompiler.deobfuscator;

import org.jetbrains.java.decompiler.main.DecompilerContext;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.modules.decompiler.StatEdge;
import org.jetbrains.java.decompiler.modules.decompiler.StatEdge.EdgeDirection;
import org.jetbrains.java.decompiler.modules.decompiler.StatEdge.EdgeType;
import org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement;
import org.jetbrains.java.decompiler.modules.decompiler.stats.Statement;
import org.jetbrains.java.decompiler.modules.decompiler.stats.Statement.StatementType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class IrreducibleCFGDeobfuscator {


  public static boolean isStatementIrreducible(Statement statement) {

    class Node {
      public final Integer id;
      public final Set<Node> preds = new HashSet<>();
      public final Set<Node> succs = new HashSet<>();

      Node(Integer id) {
        this.id = id;
      }
    }

    HashMap<Integer, Node> mapNodes = new HashMap<>();

    // checking exceptions and creating nodes
    for (Statement stat : statement.getStats()) {
      if (!stat.getSuccessorEdges(EdgeType.EXCEPTION).isEmpty()) {
        return false;
      }

      mapNodes.put(stat.id, new Node(stat.id));
    }

    // connecting nodes
    for (Statement stat : statement.getStats()) {
      Node node = mapNodes.get(stat.id);

      for (Statement succ : stat.getNeighbours(EdgeType.REGULAR, EdgeDirection.FORWARD)) {
        Node nodeSucc = mapNodes.get(succ.id);

        node.succs.add(nodeSucc);
        nodeSucc.preds.add(node);
      }
    }

    // transforming and reducing the graph
    while (true) {
      int ttype = 0;
      Node node = null;

      for (Node nd : mapNodes.values()) {
        if (nd.succs.contains(nd)) { // T1
          ttype = 1;
        }
        else if (nd.preds.size() == 1) { // T2
          ttype = 2;
        }

        if (ttype != 0) {
          node = nd;
          break;
        }
      }

      if (node != null) {
        if (ttype == 1) {
          node.succs.remove(node);
          node.preds.remove(node);
        }
        else {
          Node pred = node.preds.iterator().next();

          pred.succs.addAll(node.succs);
          pred.succs.remove(node);

          for (Node succ : node.succs) {
            succ.preds.remove(node);
            succ.preds.add(pred);
          }

          mapNodes.remove(node.id);
        }
      }
      else { // no transformation applicable
        return mapNodes.size() > 1; // reducible iff one node remains
      }
    }
  }


  private static SplitCandidate getCandidateForSplitting(Statement statement) {

    SplitCandidate candidateForSplitting = null;
    int sizeCandidateForSplitting = Integer.MAX_VALUE;
    int succsCandidateForSplitting = Integer.MAX_VALUE;

    for (Set<Statement> component : getStronglyConnectedComponents(statement)) {
      if (!isCyclicComponent(component)) {
        continue;
      }

      List<SplitCandidate> entries = new ArrayList<>();
      for (Statement stat : component) {
        if (containsCopiedStatement(stat)) {
          continue;
        }

        StatEdge entryEdge = getComponentEntryEdge(stat, component);
        if (entryEdge != null &&
            !stat.getNeighboursSet(EdgeType.REGULAR, EdgeDirection.BACKWARD).isEmpty()) {
          entries.add(new SplitCandidate(stat, entryEdge, component));
        }
      }

      if (entries.size() < 2) {
        continue;
      }

      for (SplitCandidate candidate : entries) {
        Statement stat = candidate.statement;
        int succCount = stat.getNeighboursSet(EdgeType.REGULAR, EdgeDirection.FORWARD).size();
        int size = getStatementSize(stat) * (stat.getPredecessorEdges(EdgeType.REGULAR).size() - 1);

        if (size < sizeCandidateForSplitting ||
            size == sizeCandidateForSplitting && succCount < succsCandidateForSplitting) {
          candidateForSplitting = candidate;
          sizeCandidateForSplitting = size;
          succsCandidateForSplitting = succCount;
        }
      }
    }

    return candidateForSplitting;
  }

  private static SplitCandidate getPredecessorCandidateForSplitting(Statement statement) {

    SplitCandidate candidateForSplitting = null;
    int sizeCandidateForSplitting = Integer.MAX_VALUE;
    int succsCandidateForSplitting = Integer.MAX_VALUE;

    for (Statement stat : statement.getStats()) {
      Set<Statement> setPreds = stat.getNeighboursSet(EdgeType.REGULAR, EdgeDirection.BACKWARD);
      if (setPreds.size() > 1) {
        int succCount = stat.getNeighboursSet(EdgeType.REGULAR, EdgeDirection.FORWARD).size();
        int size = getStatementSize(stat) * (setPreds.size() - 1);

        if (succCount <= succsCandidateForSplitting &&
            (succCount < succsCandidateForSplitting || size < sizeCandidateForSplitting)) {
          StatEdge enteredge = stat.getPredecessorEdges(EdgeType.REGULAR).iterator().next();
          candidateForSplitting = new SplitCandidate(stat, enteredge, null);
          sizeCandidateForSplitting = size;
          succsCandidateForSplitting = succCount;
        }
      }
    }

    return candidateForSplitting;
  }

  public static boolean splitIrreducibleNode(Statement statement) {

    SplitCandidate candidate = getPredecessorCandidateForSplitting(statement);
    if (candidate != null) {
      Statement splitnode = candidate.statement;
      StatEdge enteredge = candidate.entryEdge;
      traceSplit(statement, splitnode, enteredge);

      // copy the smallest statement
      Statement splitcopy = copyStatement(splitnode, null, new HashMap<>());
      initCopiedStatement(splitcopy);

      // insert the copy
      splitcopy.setParent(statement);
      statement.getStats().addWithKey(splitcopy, splitcopy.id);

      // switch input edges
      for (StatEdge prededge : splitnode.getPredecessorEdges(EdgeType.DIRECT_ALL)) {
        if (candidate.isEntryEdge(prededge)) {
          splitnode.removePredecessor(prededge);
          prededge.getSource().changeEdgeNode(EdgeDirection.FORWARD, prededge, splitcopy);
          splitcopy.addPredecessor(prededge);
        }
      }

      // connect successors
      for (StatEdge succ : splitnode.getSuccessorEdges(EdgeType.DIRECT_ALL)) {
        splitcopy.addSuccessor(new StatEdge(succ.getType(), splitcopy, succ.getDestination(), succ.closure));
      }

      return true;
    }

    DecompilerContext.getLogger().writeMessage(
      "No irreducible split candidate found in statement " + statement.id + ':' + statement.type,
      IFernflowerLogger.Severity.TRACE);
    return false;
  }

  private static boolean splitIrreducibleComponent(Statement statement, ComponentSplitCandidate candidate) {

    traceComponentSplit(statement, candidate);

    boolean split = false;
    for (ComponentEntry entry : candidate.entries) {
      if (entry == candidate.keepEntry) {
        continue;
      }

      HashMap<Statement, Statement> mapCopies = new HashMap<>();
      for (Statement original : candidate.component) {
        Statement copy = copyStatement(original, null, mapCopies);
        initCopiedStatement(copy);
        copy.setParent(statement);
        statement.getStats().addWithKey(copy, copy.id);
      }
      traceComponentCopy(entry, mapCopies);

      for (Statement original : candidate.component) {
        Statement copy = mapCopies.get(original);
        for (StatEdge edge : original.getSuccessorEdges(EdgeType.DIRECT_ALL)) {
          copy.addSuccessor(new StatEdge(edge.getType(),
                                         copy,
                                         mapCopies.getOrDefault(edge.getDestination(), edge.getDestination()),
                                         mapCopies.getOrDefault(edge.closure, edge.closure)));
        }
      }

      Statement entryCopy = mapCopies.get(entry.statement);
      for (StatEdge edge : new ArrayList<>(entry.entryEdges)) {
        entry.statement.removePredecessor(edge);
        edge.getSource().changeEdgeNode(EdgeDirection.FORWARD, edge, entryCopy);
        entryCopy.addPredecessor(edge);
      }

      split = true;
    }

    return split;
  }

  private static ComponentSplitCandidate getComponentCandidateForSplitting(Statement statement) {

    ComponentSplitCandidate candidate = null;
    int bestSize = Integer.MAX_VALUE;

    for (Set<Statement> component : getStronglyConnectedComponents(statement)) {
      if (!isCyclicComponent(component)) {
        continue;
      }

      List<ComponentEntry> entries = getComponentEntries(statement, component);
      if (entries.size() < 2) {
        continue;
      }

      ComponentEntry keepEntry = chooseEntryToKeep(statement, entries);
      int size = getComponentSize(component) * (entries.size() - 1);
      if (size < bestSize) {
        candidate = new ComponentSplitCandidate(component, entries, keepEntry);
        bestSize = size;
      }
    }

    return candidate;
  }

  private static List<ComponentEntry> getComponentEntries(Statement statement, Set<Statement> component) {

    List<ComponentEntry> entries = new ArrayList<>();
    Set<Statement> visited = new HashSet<>();

    for (Statement stat : statement.getPostReversePostOrderList()) {
      if (component.contains(stat)) {
        addComponentEntry(entries, visited, stat, component);
      }
    }

    for (Statement stat : statement.getStats()) {
      if (component.contains(stat)) {
        addComponentEntry(entries, visited, stat, component);
      }
    }

    return entries;
  }

  private static void addComponentEntry(List<ComponentEntry> entries,
                                        Set<Statement> visited,
                                        Statement statement,
                                        Set<Statement> component) {

    if (!visited.add(statement)) {
      return;
    }

    List<StatEdge> entryEdges = new ArrayList<>();
    for (StatEdge edge : statement.getPredecessorEdges(EdgeType.REGULAR)) {
      if (!component.contains(edge.getSource())) {
        entryEdges.add(edge);
      }
    }

    if (!entryEdges.isEmpty()) {
      entries.add(new ComponentEntry(statement, entryEdges));
    }
  }

  private static ComponentEntry chooseEntryToKeep(Statement statement, List<ComponentEntry> entries) {
    Statement first = statement.getFirst();
    if (first != null) {
      for (ComponentEntry entry : entries) {
        if (entry.statement == first) {
          return entry;
        }
      }
    }

    for (ComponentEntry entry : entries) {
      if (!containsCopiedStatement(entry.statement)) {
        return entry;
      }
    }

    return entries.get(0);
  }

  private record ComponentSplitCandidate(Set<Statement> component, List<ComponentEntry> entries, ComponentEntry keepEntry) {
  }

  private record ComponentEntry(Statement statement, List<StatEdge> entryEdges) {
  }

  private record SplitCandidate(Statement statement, StatEdge entryEdge, Set<Statement> component) {
    private boolean isEntryEdge(StatEdge edge) {
      if (component == null) {
        return edge.getSource() == entryEdge.getSource() ||
               edge.closure == entryEdge.getSource();
      }
      return !component.contains(edge.getSource()) ||
             edge.getSource() == entryEdge.getSource() ||
             edge.closure == entryEdge.getSource();
    }
  }

  private static StatEdge getComponentEntryEdge(Statement statement, Set<Statement> component) {
    for (StatEdge edge : statement.getPredecessorEdges(EdgeType.REGULAR)) {
      if (!component.contains(edge.getSource()) && !containsCopiedStatement(edge.getSource())) {
        return edge;
      }
    }

    return null;
  }

  private static boolean containsCopiedStatement(Statement statement) {
    if (statement.isCopied()) {
      return true;
    }

    for (Statement child : statement.getStats()) {
      if (containsCopiedStatement(child)) {
        return true;
      }
    }

    return false;
  }

  private static boolean isCyclicComponent(Set<Statement> component) {
    if (component.size() > 1) {
      return true;
    }

    Statement statement = component.iterator().next();
    return statement.getNeighboursSet(EdgeType.REGULAR, EdgeDirection.FORWARD).contains(statement);
  }

  private static int getComponentSize(Set<Statement> component) {
    int size = 0;
    for (Statement statement : component) {
      size += getStatementSize(statement);
    }
    return size;
  }

  private static List<Set<Statement>> getStronglyConnectedComponents(Statement statement) {
    Set<Statement> nodes = new HashSet<>();
    for (Statement stat : statement.getStats()) {
      nodes.add(stat);
    }

    SccState state = new SccState();
    for (Statement stat : statement.getStats()) {
      if (!state.indices.containsKey(stat)) {
        strongConnect(stat, nodes, state);
      }
    }

    return state.components;
  }

  private static void strongConnect(Statement statement, Set<Statement> nodes, SccState state) {
    state.indices.put(statement, state.nextIndex);
    state.lowIndices.put(statement, state.nextIndex);
    state.nextIndex++;
    state.stack.add(statement);
    state.onStack.add(statement);

    for (Statement successor : statement.getNeighbours(EdgeType.REGULAR, EdgeDirection.FORWARD)) {
      if (!nodes.contains(successor)) {
        continue;
      }

      if (!state.indices.containsKey(successor)) {
        strongConnect(successor, nodes, state);
        state.lowIndices.put(statement, Math.min(state.lowIndices.get(statement), state.lowIndices.get(successor)));
      }
      else if (state.onStack.contains(successor)) {
        state.lowIndices.put(statement, Math.min(state.lowIndices.get(statement), state.indices.get(successor)));
      }
    }

    if (state.lowIndices.get(statement).intValue() == state.indices.get(statement).intValue()) {
      Set<Statement> component = new HashSet<>();
      Statement member;
      do {
        member = state.stack.remove(state.stack.size() - 1);
        state.onStack.remove(member);
        component.add(member);
      }
      while (member != statement);
      state.components.add(component);
    }
  }

  private static final class SccState {
    private int nextIndex;
    private final HashMap<Statement, Integer> indices = new HashMap<>();
    private final HashMap<Statement, Integer> lowIndices = new HashMap<>();
    private final List<Statement> stack = new ArrayList<>();
    private final Set<Statement> onStack = new HashSet<>();
    private final List<Set<Statement>> components = new ArrayList<>();
  }

  private static void traceSplit(Statement statement, Statement splitnode, StatEdge enteredge) {
    StringBuilder buffer = new StringBuilder("Splitting irreducible statement ")
      .append(statement.id)
      .append(':')
      .append(statement.type)
      .append(" stats=")
      .append(statement.getStats().size())
      .append(" candidate=")
      .append(splitnode.id)
      .append(':')
      .append(splitnode.type)
      .append(" candidateSize=")
      .append(getStatementSize(splitnode))
      .append(" enter=")
      .append(enteredge.getSource().id)
      .append("->")
      .append(enteredge.getDestination().id)
      .append(" preds=");

    for (Statement pred : splitnode.getNeighbours(EdgeType.REGULAR, EdgeDirection.BACKWARD)) {
      buffer.append(pred.id).append(':').append(pred.type).append(',');
    }

    buffer.append(" succs=");
    for (Statement succ : splitnode.getNeighbours(EdgeType.REGULAR, EdgeDirection.FORWARD)) {
      buffer.append(succ.id).append(':').append(succ.type).append(',');
    }

    DecompilerContext.getLogger().writeMessage(buffer.toString(), IFernflowerLogger.Severity.TRACE);
  }

  private static void traceComponentSplit(Statement statement, ComponentSplitCandidate candidate) {
    StringBuilder buffer = new StringBuilder("Splitting irreducible component in statement ")
      .append(statement.id)
      .append(':')
      .append(statement.type)
      .append(" componentSize=")
      .append(candidate.component.size())
      .append(" entries=");

    for (ComponentEntry entry : candidate.entries) {
      buffer.append(entry.statement.id)
        .append(':')
        .append(entry.statement.type)
        .append(entry == candidate.keepEntry ? "(keep)" : "")
        .append("[");
      for (StatEdge edge : entry.entryEdges) {
        buffer.append(edge.getSource().id).append("->").append(edge.getDestination().id).append(',');
      }
      buffer.append("]");
    }

    DecompilerContext.getLogger().writeMessage(buffer.toString(), IFernflowerLogger.Severity.TRACE);
  }

  private static void traceComponentCopy(ComponentEntry entry, HashMap<Statement, Statement> mapCopies) {
    StringBuilder buffer = new StringBuilder("Component clone for entry ")
      .append(entry.statement.id)
      .append(':')
      .append(entry.statement.type)
      .append(" copies=");

    for (java.util.Map.Entry<Statement, Statement> copied : mapCopies.entrySet()) {
      buffer.append(copied.getKey().id)
        .append("->")
        .append(copied.getValue().id)
        .append(',');
    }

    DecompilerContext.getLogger().writeMessage(buffer.toString(), IFernflowerLogger.Severity.TRACE);
  }

  private static int getStatementSize(Statement statement) {

    int res;

    if (statement.type == StatementType.BASIC_BLOCK) {
      res = ((BasicBlockStatement)statement).getBlock().getSeq().length();
    }
    else {
      res = statement.getStats().stream().mapToInt(IrreducibleCFGDeobfuscator::getStatementSize).sum();
    }

    return res;
  }

  private static Statement copyStatement(Statement from, Statement to, HashMap<Statement, Statement> mapAltToCopies) {

    if (to == null) {
      // first outer invocation
      to = from.getSimpleCopy();
      mapAltToCopies.put(from, to);
    }

    // copy statements
    for (Statement st : from.getStats()) {
      Statement stcopy = st.getSimpleCopy();

      to.getStats().addWithKey(stcopy, stcopy.id);
      mapAltToCopies.put(st, stcopy);
    }

    // copy edges
    for (int i = 0; i < from.getStats().size(); i++) {
      Statement stold = from.getStats().get(i);
      Statement stnew = to.getStats().get(i);

      for (StatEdge edgeold : stold.getSuccessorEdges(EdgeType.DIRECT_ALL)) {
        // type cannot be TYPE_EXCEPTION (checked in isIrreducibleTriangle)
        StatEdge edgenew = new StatEdge(edgeold.getType(), stnew,
                                        mapAltToCopies.containsKey(edgeold.getDestination())
                                        ? mapAltToCopies.get(edgeold.getDestination())
                                        : edgeold.getDestination(),
                                        mapAltToCopies.containsKey(edgeold.closure)
                                        ? mapAltToCopies.get(edgeold.closure)
                                        : edgeold.closure);

        stnew.addSuccessor(edgenew);
      }
    }

    // recurse statements
    for (int i = 0; i < from.getStats().size(); i++) {
      Statement stold = from.getStats().get(i);
      Statement stnew = to.getStats().get(i);

      copyStatement(stold, stnew, mapAltToCopies);
    }

    return to;
  }

  private static void initCopiedStatement(Statement statement) {

    statement.initSimpleCopy();
    statement.setCopied(true);

    for (Statement st : statement.getStats()) {
      st.setParent(statement);
      initCopiedStatement(st);
    }
  }
}
