public class ao implements Comparable<ao> {
   protected String a;
   protected Thread.State b;
   protected double c;
   protected long d;
   protected long e;

   public String a() {
      return this.a;
   }

   public Thread.State b() {
      return this.b;
   }

   public double c() {
      return this.c;
   }

   public int a(ao var1) {
      label31:
      label31: {
         var2 = an.k;
         var10000 = var1;
         if (!var2)
         if (var1 == null)
         return -1;
         var10000 = this;
         return var4;
         label41: {
            label58: {
               try {
                  label37: {
                     if (var2) {
                        return var4;
                     }

                     if (var4 == 0) {
                        break label58;
                     }
                     break label37;
                  }
               } catch (a_ var3) {
                  throw var3;
               }

               var4 = 1;
               return var4;
            }

            var4 = 0;
            return var4;
            label42:
            label42: {
               double var5;
               var4 = (var5 = this.c - var1.c) == 0.0 ? 0 : (var5 < 0.0 ? -1 : 1);
               label44:
               label44: {
                  double var6;
                  var4 = (var6 = var10000.c - var1.c) == 0.0 ? 0 : (var6 < 0.0 ? -1 : 1);
                  if (var2) {
                     break label42;
                  }
                  var4 = -1;
                  return var4;
                  if (var4 <= 0) {
                     break label44;
                  }
               }            }         }      }   }
}
