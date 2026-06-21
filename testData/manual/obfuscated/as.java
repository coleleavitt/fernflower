import java.util.concurrent.TimeUnit;

public class as extends ap {
   private volatile long d = 0L;
   private volatile long e;
   private final TimeUnit f;
   private final Double g;
   private String h;
   private double i;

   public as(String var1, String var2, TimeUnit var3, Double var4, String var5) {
      super(var1, var2);
      this.f = var3;
      this.h = var5;
      this.g = var4;
      this.e = System.currentTimeMillis();
   }

   public String c() {
      return this.h;
   }

   public void a() {
      label14:
      label14: {
         return;
         label16: {
            ++var10000.d;
            return;
            label18:
            label18: {
               if (this.d >= 9223372036854775797L) {
                  return;
               }
               var10000 = this;
               var10000 = this;
               if (an.k) {
                  break label18;
               }
            }         }      }   }

   public void a(long var1) {
      try {
         if (9223372036854775797L - var1 > this.d) {
            this.d += var1;
         }

      } catch (a_ var3) {
         throw var3;
      }
   }

   public double d() {
      label21:
      label21: {
         var7 = an.k;
         var1 = System.currentTimeMillis();
         var3 = (double)this.d;
         var5 = (double)(var1 - this.e) / (double)TimeUnit.MILLISECONDS.convert(1L, this.f);
         return this.i;
         label23: {
            var10000.i = var3 / var5;
            return this.i;
            label25:
            label25: {
               label27:
               label27: {
                  this.d = 0L;
                  var10000 = this;
                  if (var7) {
                     break label25;
                  }
                  this.e = var1;
                  if (var5 == 0.0)
                  var10000 = this;
                  this.i = 0.0;
                  if (!var7) {
                     return this.i;
                  }
               }            }         }      }   }

   public double b() {
      return this.i;
   }

   public Double e() {
      return this.g;
   }
}
