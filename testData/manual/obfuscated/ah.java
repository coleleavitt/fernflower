import java.util.concurrent.TimeUnit;

public class ah {
   private volatile long a = -1L;
   private volatile long b = 0L;

   public void a() {
      label22:
      label22: {
         var1 = an.k;
         return;
         label24: {
            ++var3.b;
            return;
            label27:
            label27: {
               label25:
               if (var10000 >= 0) {
                  return;
               } else {
                  var3 = this;
                  break label25;
               }
               label31:
               label31: {
                  try {
                     label29: {
                        var3 = this;
                        if (var1) {
                           break label27;
                        }

                        long var4;
                        var10000 = (var4 = this.b - 9223372036854775797L) == 0L ? 0 : (var4 < 0L ? -1 : 1);
                        break label29;
                     }
                  } catch (a_ var2) {
                     throw var2;
                  }
                  label33:
                  label33: {
                     long var5;
                     var10000 = (var5 = this.a - 0L) == 0L ? 0 : (var5 < 0L ? -1 : 1);
                     if (var1) {
                        break label31;
                     }
                     this.c();
                     if (var10000 >= 0) {
                        break label33;
                     }
                  }               }            }         }      }   }

   public double a(TimeUnit var1) {
      return (double)(this.b / this.b(var1));
   }

   public long b() {
      return this.b;
   }

   public long b(TimeUnit var1) {
      long var2 = System.currentTimeMillis() - this.a;
      return TimeUnit.MILLISECONDS.convert(var2, var1);
   }

   public void c() {
      this.a = System.currentTimeMillis();
      this.b = 0L;
   }
}
