import java.lang.management.GarbageCollectorMXBean;

class av extends ap {
   private long d;
   private long e;
   final an f;

   av(an var1, String var2, String var3) {
      super(var2, var3);
      this.f = var1;
      this.d = 0L;
      this.e = 0L;
   }

   public double d() {
      label26:
      label26: {
         var11 = an.k;
         var1 = 0L;
         var3 = System.nanoTime();
         var5 = an.e(this.f).iterator();
         double var13 = 0.0;

         av var14;
         label35: {
            label34: {
               try {
                  label32: {
                     var14 = this;
                     if (var11) {
                        break label35;
                     }

                     if (this.e <= 0L) {
                        break label34;
                     }
                     break label32;
                  }
               } catch (a_ var12) {
                  throw var12;
               }

               double var7 = Math.abs((double)var1 - (double)this.d);
               double var9 = (double)Math.abs(var3 - this.e);
               var13 = (double)Math.round(10000.0 * (var7 / var9)) / 100.0;
               break label34;
            }

            this.d = var1;
            var14 = this;
            break label35;
         }

         var14.e = var3;
         return var13;
         label38:
         label38: {
            if (var11) {
               break label38;
            }
            label41: {
               if (!var5.hasNext()) {
                  break label38;
               }

               var6 = (GarbageCollectorMXBean)var5.next();
               break label41;
            }
            label42:
            label42: {
               var1 = var10000 + var10001;
               label44:
               label44: {
                  if (var10000 <= 0L) {
                     break label42;
                  }
                  var10000 = var1;
                  var10001 = var6.getCollectionTime();
                  var10000 = var6.getCollectionTime();
                  var10001 = 0L;
                  if (var11) {
                     break label44;
                  }
               }            }         }      }   }

   public String c() {
      return "%";
   }

   public Double e() {
      return 100.0;
   }
}
