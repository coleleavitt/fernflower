public class y<P> {
   private P a;
   private Class<P> b;
   private boolean c;
   public static int d;

   private y(Class<P> var1) {
      this.b = var1;
   }

   public static <P> y<P> a(Class<P> var0) {
      return new y<P>(var0);
   }

   public P a() {
      label14:
      label14: {
         return (P)var10000;
         label16: {
            var10000 = this.a;
            return (P)var10000;
            label18:
            label18: {
               var10000 = this;
               if (d != 0) {
                  return (P)var10000;
               }
               this.a = (P)t.a(this.b);
               this.c = true;
               if (this.c) {
                  break label18;
               }
            }         }      }   }
}
