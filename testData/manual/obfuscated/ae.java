import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.locks.ReentrantLock;

@aa(
   a = {ad.class}
)
public class ae implements ad {
   @x(
      a = ac.class
   )
   private List<ac> a;
   private long b = 0L;
   private Timer c;
   private ReentrantLock d = new ReentrantLock();
   public static boolean e;

   public ae() {
      this.a();
   }

   public void a() {
      label27:
      label27: {
         var2 = e;
         this.d.unlock();
         return;
         label29:
         label29: {
            this.d.lock();
            this.c.schedule(new ba(this, (af)null), 60000L, 60000L);
            label30:
            label30: {
               var10000.cancel();
               this.c = new Timer(true);
               label32:
               label32: {
                  var10000 = this.c;
                  if (var2) {
                     break label32;
                  }
                  label34:
                  label34: {
                     label35:
                     label35: {
                        if (var10000 == null)
                        var10000 = this.c;
                        this.c = new Timer(true);
                        if (!var2) {
                           break label30;
                        }
                     }                  }               }            }         }      }   }

   public void b() {
      label30:
      label30: {
         var2 = e;
         this.d.lock();
         var10000 = this;
         if (!var2)
         if (this.c != null)
         this.c.cancel();
         var10000 = this;
         var10000.d.unlock();
         return;
         label32: {
            e = var3;
            return;
            label34:
            label34: {
               var3 = true;
               label35:
               label35: {
                  if (ap.c == 0) {
                     return;
                  }
                  var3 = false;
                  if (!var2) {
                     break label35;
                  }
               }            }         }      }   }

   public String a() {
      try {
         if (this.b == 0L) {
            return "-";
         }
      } catch (a_ var1) {
         throw var1;
      }

      return DateFormat.getDateTimeInstance().format(new Date(this.b));
   }

   static List a(ae var0) {
      return var0.a;
   }

   static long a(ae var0, long var1) {
      return var0.b = var1;
   }
}
