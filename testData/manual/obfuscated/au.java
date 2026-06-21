import java.util.TreeSet;

class au extends ap {
   private long d;
   private long e;
   final an f;

   au(an var1, String var2, String var3) {
      super(var2, var3);
      this.f = var1;
      this.d = 0L;
      this.e = 0L;
   }

   public double d() {
      label62:
      label62: {
         var17 = an.k;
         var1 = 0L;
         var3 = System.nanoTime();
         var5 = new TreeSet(an.b(this.f).keySet());
         var6 = an.c(this.f).dumpAllThreads(false, false);
         var7 = var6.length;
         var8 = 0;
         if (var8 < var7)
         var9 = var6[var8];
         var10 = an.c(this.f).getThreadCpuTime(var9.getThreadId());
         var12 = (ao)an.b(this.f).get(var9.getThreadId());
         if (!var17)
         if (var12 == null)
         var12 = new ao();
         var12.a = var9.getThreadName();
         an.b(this.f).put(var9.getThreadId(), var12);
         if (var17)
         var13 = (double)Math.abs(var10 - var12.e);
         var15 = (double)Math.abs(var3 - var12.d);
         double var26;
         var10000 = (var26 = var15 - 0.0) == 0.0 ? 0 : (var26 < 0.0 ? -1 : 1);
         if (!var17)
         if (var10000 > 0)
         var12.c = (double)Math.round(10000.0 * (var13 / var15)) / 100.0;
         var5.remove(var9.getThreadId());
         var12.e = var10;
         var12.d = var3;
         var12.b = var9.getThreadState();
         var1 += var10;
         ++var8;
         if (var17)
         var18 = var5.iterator();
         if (var18.hasNext())
         Long var20 = (Long)var18.next();
         an.b(this.f).remove(var20);
         if (var17)
         var19 = 0.0;
         var23 = this;
         if (!var17)
         if (this.e > 0L)
         var21 = (double)Math.abs(var1 - this.d);
         var22 = (double)Math.abs(var3 - this.e);
         var24 = var22;
         var10001 = 0.0;
         if (!var17)
         if (var22 == 0.0)
         var19 = 0.0;
         if (var17)
         var24 = (double)Math.round(10000.0 * (var21 / (double)an.d(this.f).getAvailableProcessors() / var22));
         var10001 = 100.0;
         var19 = var24 / var10001;
         this.d = var1;
         var23 = this;
         return var19;
         label64: {
            an.k = var25;
            return var19;
            label66:
            label66: {
               var25 = true;
               label67:
               label67: {
                  var23.e = var3;
                  if (ap.c == 0) {
                     return var19;
                  }
                  var25 = false;
                  if (!var17) {
                     break label67;
                  }
               }            }         }      }   }

   public String c() {
      return "%";
   }

   public Double e() {
      return 100.0;
   }
}
