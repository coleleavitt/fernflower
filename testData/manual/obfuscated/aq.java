public class aq extends ap {
   private volatile long d;
   private volatile long e;
   private final String f;
   private static final long g = 4611686018427387903L;
   private final Double h;

   public aq(String var1, String var2, String var3, Double var4) {
      super(var1, var2);
      this.f = var3;
      this.h = var4;
   }

   public String c() {
      return this.f;
   }

   public void a(long var1) {
      label26:
      label26: {
         var7 = an.k;
         var3 = this.d;
         var5 = this.e;
         try {
            if (var9 > 0) {
               this.d = var3 + var1;
               this.e = var5 + 1L;
               return;
            } else {
               return;
            }
         } catch (a_ var8) {
            throw var8;
         }
         label31:
         label31: {
            long var10;
            var9 = (var10 = 9223372036854775807L - var3 - var1) == 0L ? 0 : (var10 < 0L ? -1 : 1);
            label33:
            label33: {
               var3 = var10000 / var10001;
               label35:
               label35: {
                  var5 /= 2L;
                  var10000 = var3;
                  var10001 = 2L;
                  label37:
                  label37: {
                     var10000 = var5;
                     var10001 = 4611686018427387903L;
                     if (var7) {
                        break label35;
                     }
                     long var11;
                     var9 = (var11 = var3 - 4611686018427387903L) == 0L ? 0 : (var11 < 0L ? -1 : 1);
                     if (var7) {
                        break label31;
                     }
                     if (var9 <= 0) {
                        break label33;
                     }
                     if (var5 > 4611686018427387903L) {
                        break label37;
                     }
                  }               }            }         }      }   }

   public double d() {
      label14: {
         var1 = this.d;
         var3 = this.e;
         label16:
         label16: {
            return (double)var10000 / (double)var3;
            label17: {
               if (var3 == 0L)
               var10000 = var1;
               return (double)var10000 / (double)var3;
               return 0.0;
               this.d = 0L;
               this.e = 0L;
               var10000 = var3;
               if (an.k) {
                  return (double)var10000 / (double)var3;
               }
            }         }      }   }

   public Double e() {
      return this.h;
   }
}
