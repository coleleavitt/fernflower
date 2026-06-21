public class ag {
   private long a = 0L;
   private long[] b = new long[100];
   private int c = 0;
   private int d = 0;

   public void a(long var1) {
      label34:
      label34: {
         var5 = an.k;
         synchronized(this.b){}
         this.b[this.c++] = var1;
         var10000 = this.c;
         var10001 = this.b.length - 1;
         if (!var5)
         var7 = this;
         if (!var5)
         var10000 = this.c;
         var10001 = this.d;
         if (var10000 > var10001)
         var7 = this;
         var7.d = this.c;
         ++var8.a;
         return;
         label37:
         try {
            label36:
            if (var10000 >= var10001) {
               this.c = 0;
               break label36;
            }
            break label37;
         } catch (a_ var6) {
            throw var6;
         }
         label38:
         label38: {
            var8 = this;
            label40:
            label40: {
               var8 = this;
               if (var5) {
                  break label38;
               }
               this.a = 0L;
               if (this.a < 9223372036854775806L) {
                  break label40;
               }
            }         }      }   }

   public double a() {
      try {
         if (this.d == 0) {
            return 0.0;
         }
      } catch (a_ var4) {
         throw var4;
      }

      double var1 = 0.0;

      for(int var3 = 0; var3 <= this.d; ++var3) {
         var1 += (double)this.b[var3];
      }

      return var1 / (double)this.d;
   }

   public long b() {
      return this.a;
   }
}
