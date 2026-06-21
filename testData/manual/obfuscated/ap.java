public abstract class ap implements ak {
   private final String a;
   private final String b;
   public static int c;
   private static final String d;

   public ap(String var1, String var2) {
      this.b = var1;
      this.a = var2;
   }

   public String b() {
      return this.a;
   }

   public String a() {
      return this.b;
   }

   public int hashCode() {
      return (this.b + this.a).hashCode();
   }

   public boolean equals(Object var1) {
      label39:
      label39: {
         var2 = an.k;
         var10000 = var1;
         if (!var2)
         if (var1 != null)
         var10000 = var1;
         var4 = var10000 instanceof ak;
         if (!var2)
         if (!var4)
         return false;
         var4 = al.a(this.a, ((ak)var1).b());
         return var4;
         label41: {
            var4 = true;
            return var4;
            label43:
            label43: {
               var4 = false;
               return var4;
               label47:
               label47: {
                  try {
                     label42:
                     if (var2) {
                        return var4;
                     } else {
                        if (var4) {
                           break label43;
                        }
                        break label42;
                     }
                  } catch (a_ var3) {
                     throw var3;
                  }
                  label49:
                  label49: {
                     if (!var4) {
                        break label47;
                     }
                     var4 = al.a(this.b, ((ak)var1).a());
                     if (var2) {
                        break label49;
                     }
                  }               }            }         }      }   }

   public String toString() {
      return this.b + d + this.a;
   }

   static {
      char[] var10000 = "K\u000eX".toCharArray();
      int var10002 = var10000.length;
      int var1 = 0;
      char[] var9 = var10000;
      int var4 = var10002;
      char[] var15;
      int var10003;
      if (var10002 <= 1) {
         var15 = var10000;
         var10003 = var1;
      } else {
         var9 = var10000;
         var4 = var10002;
         if (var10002 <= var1) {
            d = (new String(var10000)).intern();
            return;
         }

         var15 = var10000;
         var10003 = var1;
      }

      while(true) {
         char var10004 = var15[var10003];
         byte var10005;
         switch (var1 % 5) {
            case 0:
               var10005 = 107;
               break;
            case 1:
               var10005 = 35;
               break;
            case 2:
               var10005 = 120;
               break;
            case 3:
               var10005 = 65;
               break;
            default:
               var10005 = 23;
         }

         var15[var10003] = (char)(var10004 ^ var10005);
         ++var1;
         if (var4 == 0) {
            var10003 = var4;
            var15 = var9;
         } else {
            if (var4 <= var1) {
               d = (new String(var9)).intern();
               return;
            }

            var15 = var9;
            var10003 = var1;
         }
      }
   }
}
