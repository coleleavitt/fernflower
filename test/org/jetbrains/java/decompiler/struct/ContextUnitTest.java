// Copyright 2000-2026 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.java.decompiler.struct;

import org.jetbrains.java.decompiler.main.CancellationManager;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.junit.jupiter.api.Test;

import java.util.jar.Manifest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContextUnitTest {
  @Test
  void saveClosesArchiveWhenSavingEntryIsCanceled() {
    InterruptingResultSaver saver = new InterruptingResultSaver();
    ContextUnit unit = new ContextUnit(ContextUnit.TYPE_JAR, "", "broken.jar", true, saver, new EmptyDecompiledData());
    unit.addDirEntry("pkg/");

    assertThrows(CancellationManager.CanceledException.class, unit::save);
    assertTrue(saver.closeArchiveCalled);
  }

  private static class EmptyDecompiledData implements IDecompiledData {
    @Override
    public String getClassEntryName(StructClass cl, String entryName) {
      return null;
    }

    @Override
    public String getClassContent(StructClass cl) {
      return null;
    }
  }

  private static class InterruptingResultSaver implements IResultSaver {
    private boolean closeArchiveCalled;

    @Override
    public void saveFolder(String path) {
    }

    @Override
    public void copyFile(String source, String path, String entryName) {
    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
    }

    @Override
    public void createArchive(String path, String archiveName, Manifest manifest) {
    }

    @Override
    public void saveDirEntry(String path, String archiveName, String entryName) {
      throw new CancellationManager.CanceledException();
    }

    @Override
    public void copyEntry(String source, String path, String archiveName, String entry) {
    }

    @Override
    public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
    }

    @Override
    public void closeArchive(String path, String archiveName) {
      closeArchiveCalled = true;
    }
  }
}
