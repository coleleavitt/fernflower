import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.LRUMap;

class e<K, V> implements a<K, V> {
   private static final int a = 25;
   protected int b;
   protected f<K, V> c;
   protected Map<K, b<K, V>> d;
   protected ah e = new ah();
   protected ah f = new ah();
   protected List<Long> g = new ArrayList(25);
   protected List<Long> h = new ArrayList(25);
   protected Date i = null;
   protected final String j;
   protected final long k;
   protected final g<V> l;
   private final long m;

   public e(String var1, int var2, long var3, f<K, V> var5, g<V> var6, long var7) {
      this.j = var1;
      this.b = var2;
      this.m = var7;
      if (var2 > 0) {
         this.d = Collections.synchronizedMap(new LRUMap(var2));
      } else {
         this.d = Collections.synchronizedMap(new HashMap(var2));
      }

      this.k = var3;
      this.c = var5;
      this.l = var6;
   }

   public String a() {
      return this.j;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.d.size();
   }

   public long d() {
      return this.e.b() + this.f.b();
   }

   public Long f() {
      long var1 = this.e.b();
      long var3 = this.f.b();

      long var10000;
      try {
         if (var1 + var3 == 0L) {
            var10000 = 0L;
            return var10000;
         }
      } catch (a_ var5) {
         throw var5;
      }

      var10000 = Math.round(100.0 * (double)var1 / (double)(var1 + var3));
      return var10000;
   }

   public Date h() {
      return this.i;
   }

   public void i() {
      label48:
      label48: {
         var5 = d.b;
         this.g.add(this.d());
         var10000 = this.g.size();
         var10001 = 25;
         if (!var5)
         if (var10000 > 25)
         this.g.remove(0);
         this.h.add(this.f());
         var10000 = this.h.size();
         if (!var5)
         var10001 = 25;
         this.e.c();
         this.f.c();
         this.i = new Date();
         var9 = this.k;
         if (!var5)
         long var11;
         var10000 = (var11 = var9 - 0L) == 0L ? 0 : (var11 < 0L ? -1 : 1);
         var1 = var9;
         var3 = this.d.entrySet().iterator();
         label50:
         if (var10000 <= 0) {
            return;
         } else {
            var9 = System.currentTimeMillis();
            break label50;
         }
         label53:
         try {
            label52:
            if (var10000 > var10001) {
               this.h.remove(0);
               break label52;
            }
            break label53;
         } catch (a_ var7) {
            throw var7;
         }
         label54:
         label54: {
            return;
            label57: {
               return;
               label61: {
                  label62:
                  if (!var3.hasNext()) {
                     return;
                  } else {
                     var4 = (Map.Entry)var3.next();
                     break label62;
                  }
                  label64: {
                     if (var10 > 0) {
                        return;
                     }

                     var3.remove();
                     if (!var5) {
                        continue label54;
                     }

                     int var6 = ap.c;
                     ++var6;
                     ap.c = var6;
                     return;
                     label65:
                     label65: {
                        if (var10 == 0) {
                           return;
                        }
                        long var12;
                        var10 = (var12 = ((b)var4.getValue()).g() - var1) == 0L ? 0 : (var12 < 0L ? -1 : 1);
                        long var13;
                        var10 = (var13 = ((b)var4.getValue()).g() - 0L) == 0L ? 0 : (var13 < 0L ? -1 : 1);
                        if (var5) {
                           break label65;
                        }
                     }                  }               }            }         }      }   }

   public void j() {
      this.d.clear();
      this.f.c();
      this.e.c();
      this.i = new Date();
   }

   public V a(K var1) {
      return (V)this.a(var1, this.c);
   }

   public boolean c(K var1) {
      return this.d.containsKey(var1);
   }

   public V a(K var1, f<K, V> var2) {
      label40:
      label40: {
         var3 = System.currentTimeMillis();
         var5 = (b)this.d.get(var1);
         if (var5 != null)
         if (var5.g() > 0L)
         if (var5.g() < var3)
         this.d.remove(var1);
         var5 = null;
         label48:
         try {
            if (var5 != null) {
               this.e.a();
               var5.a().a();
               return (V)var5.f();
            }
            break label48;
         } catch (a_ var7) {
            throw var7;
         }

         this.f.a();
         if (var2 != null) {
            Object var6 = var2.a(var1);
            this.a(var1, var6);
            return (V)var6;
         } else {
            return null;
         }
         label50:
         label50: {
            var5 = null;
            label55:
            label55: {
               var5.d(this.m + var3);
               label57:
               label57: {
                  if (this.l == null) {
                     break label50;
                  }
                  if (var5 == null) {
                     break label50;
                  }
                  if (this.m <= 0L) {
                     break label50;
                  }
                  if (var5.h() >= var3) {
                     break label50;
                  }
                  if (!this.l.a(var5.f())) {
                     break label55;
                  }
               }            }         }      }   }

   public void a(K var1, V var2) {
      b var10000;
      b var10001;
      Object var10002;
      Object var10003;
      long var10004;
      label16: {
         try {
            var10000 = new b;
            var10001 = var10000;
            var10002 = var1;
            var10003 = var2;
            if (this.k > 0L) {
               var10004 = this.k + System.currentTimeMillis();
               break label16;
            }
         } catch (a_ var4) {
            throw var4;
         }

         var10004 = 0L;
      }

      var10001.<init>(var10002, var10003, var10004, this.m + System.currentTimeMillis());
      b var3 = var10000;
      this.d.put(var1, var3);
   }

   public void b(K var1) {
      this.d.remove(var1);
   }

   public Iterator<K> k() {
      return this.d.keySet().iterator();
   }

   public List<b<K, V>> l() {
      return new ArrayList(this.d.values());
   }

   public List<Long> e() {
      return this.g;
   }

   public List<Long> g() {
      return this.h;
   }
}
