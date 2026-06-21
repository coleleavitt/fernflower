import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class a1 implements a0 {
   private Node a;
   private static final XPathFactory b = XPathFactory.newInstance();

   public a1(Node var1) {
      this.a = var1;
   }

   public a0 a(String var1) throws XPathExpressionException {
      Node var2 = (Node)b.newXPath().compile(var1).evaluate(this.a, XPathConstants.NODE);

      try {
         if (var2 == null) {
            return null;
         }
      } catch (XPathExpressionException var3) {
         throw var3;
      }

      return new a1(var2);
   }

   public List<a0> b(String var1) throws XPathExpressionException {
      int var5 = bc.e;
      NodeList var2 = (NodeList)b.newXPath().compile(var1).evaluate(this.a, XPathConstants.NODESET);
      ArrayList var3 = new ArrayList(var2.getLength());
      int var4 = 0;

      ArrayList var10000;
      while(true) {
         if (var4 < var2.getLength()) {
            try {
               var10000 = var3;
               if (var5 != 0) {
                  break;
               }

               var3.add(new a1(var2.item(var4)));
               ++var4;
               if (var5 == 0) {
                  continue;
               }
            } catch (XPathExpressionException var7) {
               throw var7;
            }

            int var6 = ap.c;
            ++var6;
            ap.c = var6;
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public a0[] c(String var1) throws XPathExpressionException {
      List var2 = this.b(var1);
      return (a0[])var2.toArray(new a0[var2.size()]);
   }

   public String d(String var1) throws XPathExpressionException {
      label36:
      label36: {
         var4 = bc.e;
         var2 = b.newXPath().compile(var1).evaluate(this.a, XPathConstants.NODE);
         var10000 = var2;
         if (var4 == 0)
         if (var2 == null)
         return null;
         var10000 = var2;
         if (var4 == 0)
         if (var10000 instanceof Node)
         var3 = ((Node)var2).getTextContent();
         var10000 = var2;
         try {
            String var8 = var10000.toString().trim();
            if (ap.c != 0) {
               ++var4;
               bc.e = var4;
               return var8;
            } else {
               return var8;
            }
         } catch (XPathExpressionException var5) {
            throw var5;
         }
         label41:
         label41: {
            return var7;
            label42: {
               var7 = var3;
               return var7;
               label44:
               label44: {
                  var7 = var3;
                  if (var4 != 0) {
                     return var7;
                  }
                  return var3.trim();
                  if (var3 == null) {
                     break label44;
                  }
               }            }         }      }   }

   public boolean f(String var1) throws XPathExpressionException {
      label16:
      label16: {
         var2 = this.d(var1);
         return var10000;
         label18: {
            var10000 = false;
            return var10000;
            label19:
            label19: {
               label21:
               label21: {
                  if (var2 != null)
                  var10000 = true;
                  return var10000;
                  if (!"".equals(var2)) {
                     break label19;
                  }
               }            }         }      }   }

   public String a() {
      return this.a.getNodeName();
   }

   public String toString() {
      return this.a();
   }

   public o e(String var1) throws XPathExpressionException {
      return o.b(this.d(var1));
   }
}
