import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ab implements u {
   private List<Object> a = new ArrayList();

   public void a(Class<?> var1) throws Exception {
      label17:
      label17: {
         var7 = y.d;
         return;
         label25: {
            label24: {
               Object var2 = var10000;
               this.a.add(var2);

               for(Class var6 : ((aa)var1.getAnnotation(aa.class)).a()) {
                  t.a(var6, var2);
                  if (var7 != 0) {
                     break;
                  }
                  continue;
               }
               break label24;
            }
            label27:
            label27: {
               if (!var1.isAnnotationPresent(aa.class)) {
                  return;
               }
               var10000 = var1.newInstance();
               var10000 = var1;
               if (var7 != 0) {
                  break label27;
               }
            }         }      }   }

   public void a() throws Exception {
      int var3 = y.d;
      Iterator var1 = this.a.iterator();

      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();

            try {
               v.a(var2);
               if (var3 != 0) {
                  break;
               }

               if (var3 == 0) {
                  continue;
               }
            } catch (Exception var5) {
               throw var5;
            }

            int var4 = ap.c;
            ++var4;
            ap.c = var4;
         }

         this.a.clear();
         break;
      }

   }
}
