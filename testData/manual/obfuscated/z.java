import java.util.List;

public class z<P> {
   private List<P> a;
   private Class<P> b;

   private z(Class<P> var1) {
      this.b = var1;
   }

   public static <P> z<P> a(Class<P> var0) {
      return new z<P>(var0);
   }

   public List<P> a() {
      label14:
      label14: {
         return var10000;
         label16: {
            var10000 = this.a;
            return var10000;
            label18:
            label18: {
               var10000 = this.a;
               if (y.d != 0) {
                  return var10000;
               }
               this.a = this.b();
               if (var10000 != null) {
                  break label18;
               }
            }         }      }   }

   public List<P> b() {
      return t.b(this.b);
   }
}
