package okhttp3.internal.connection;

import com.xunmeng.core.log.Logger;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.net.Proxy.Type;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

public final class e extends okhttp3.internal.http2.d.c implements okhttp3.j {
   static final boolean h = true;
   private Protocol A;
   private okhttp3.internal.http2.d B;
   private okio.e C;
   private okio.d D;
   private int E;
   private int F = 1;
   public final okhttp3.internal.connection.f b;
   boolean c;
   int d;
   int e;
   final List<Reference<okhttp3.internal.connection.i>> f = new ArrayList();
   long g = 9223372036854775807L;
   private final okhttp3.ai w;
   private Socket x;
   private Socket y;
   private okhttp3.u z;

   public e(okhttp3.internal.connection.f var1, okhttp3.ai var2) {
      this.b = var1;
      this.w = var2;
   }

   private void G(int var1, int var2, int var3, okhttp3.f var4, okhttp3.r var5) throws IOException {
      okhttp3.ae var6 = this.N();
      HttpUrl var7 = var6.i();

      for(int var8 = 0; var8 < 21; ++var8) {
         this.H(var1, var2, var4, var5);
         var6 = this.M(var2, var3, var6, var7);
         if (var6 == null) {
            break;
         }

         okhttp3.internal.c.m(this.x);
         this.x = null;
         this.D = null;
         this.C = null;
         var5.g(var4, this.w.f(), this.w.e(), (Protocol)null);
         OkHttpClient.a.g(var4, this.w.f(), this.w.e(), (Protocol)null);
      }

   }

   private void H(int var1, int var2, okhttp3.f var3, okhttp3.r var4) throws IOException {
      Proxy var5 = this.w.e();
      okhttp3.a var6 = this.w.d();
      Socket var15;
      if (var5.type() != Type.DIRECT && var5.type() != Type.HTTP) {
         var15 = new Socket(var5);
      } else {
         var15 = var6.n().createSocket();
      }

      this.x = var15;
      var4.d(var3, this.w.f(), var5);
      OkHttpClient.a.d(var3, this.w.f(), var5);
      this.x.setSoTimeout(var2);

      try {
         okhttp3.internal.e.e.n().a(this.x, this.w.f(), var1);
      } catch (ConnectException var9) {
         StringBuilder var13 = new StringBuilder();
         var13.append("Failed to connect to ");
         var13.append(this.w.f());
         ConnectException var14 = new ConnectException(var13.toString());
         var14.initCause(var9);
         throw var14;
      } catch (NullPointerException var10) {
         StringBuilder var11 = new StringBuilder();
         var11.append("Failed to connect to ");
         var11.append(this.w.f());
         ConnectException var12 = new ConnectException(var11.toString());
         var12.initCause(var10);
         throw var12;
      }

      try {
         this.C = okio.m.b(okio.m.k(this.x));
         this.D = okio.m.c(okio.m.e(this.x));
      } catch (NullPointerException var7) {
         throw new IOException(var7);
      } catch (IllegalArgumentException var8) {
         throw new IOException(var8);
      }
   }

   private void I(okhttp3.internal.connection.b var1, int var2, okhttp3.f var3, okhttp3.r var4) throws IOException {
      if (this.w.d().t() == null) {
         if (this.w.d().p().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            this.y = this.x;
            this.A = Protocol.H2_PRIOR_KNOWLEDGE;
            this.J(var2);
         } else {
            this.y = this.x;
            this.A = Protocol.HTTP_1_1;
         }
      } else {
         var4.e(var3);
         OkHttpClient.a.e(var3);
         this.K(var1);
         var4.f(var3, this.z);
         OkHttpClient.a.f(var3, this.z);
         if (this.A == Protocol.HTTP_2) {
            this.J(var2);
         }

      }
   }

   private void J(int var1) throws IOException {
      this.y.setSoTimeout(0);
      okhttp3.internal.http2.d var2 = (new okhttp3.internal.http2.d.a(true)).i(this.y, this.w.d().l().j(), this.C, this.D).j(this).k(var1).l();
      this.B = var2;
      var2.K();
   }

   private void K(okhttp3.internal.connection.b var1) throws IOException {
      label2798: {
         var2 = this.w.d();
         var3 = var2.t();
         var4 = null;
         Object var5 = null;
         Object var6 = null;
         Object var7 = null;
         var8 = null;
         label2856:
         label2856: {
            label2801: {
               label2800:
               if (true) {
                  break label2800;
               }

            }
            label2866:
            label2866: {
               label2867:
               label2867: {
                  break label2866;
                  label2868:
                  label2868: {
                     break label2866;
                     label2869:
                     label2869: {
                        if (var5 != null) {
                           okhttp3.internal.e.e.n().i((SSLSocket)var5);
                           return;
                        }

                        return;
                        label2889:
                        label2889: {
                           label2879:
                           try {
                              break label2879;
                           } catch (Throwable var82) {
                              break label2869;
                           }

                           label2876:
                           try {
                              break label2876;
                           } catch (Throwable var81) {
                              break label2869;
                           }

                           try {
                              ;
                           } catch (Throwable var78) {
                              break label2869;
                           }
                           label2890:
                           label2890: {
                              var89 = (SSLSocket)var3.createSocket(this.x, var2.l().j(), var2.l().k(), true);
                              if (OkHttpClient.b)
                              var5 = var89;
                              var91 = new okhttp3.internal.connection.h((SSLSocket)var89);
                              var5 = var89;
                              if (!OkHttpClient.d)
                              var5 = var91;
                              var10 = new StringBuilder;
                              var10.<init>();
                              var10.append("use SSLSocketWithCloseLock:");
                              var10.append(var91);
                              var10.append(", isSslSocketReflectionCallFix:");
                              var10.append(OkHttpClient.d);
                              Logger.i("RealConnection", var10.toString());
                              var89 = var91;
                              label2897: {
                                 label2885:
                                 try {
                                    if (true) {
                                       break label2897;
                                    }
                                    break label2885;
                                 } catch (Throwable var83) {
                                    break label2869;
                                 }

                                 label2873:
                                 try {
                                    break label2873;
                                 } catch (Throwable var80) {
                                    break label2869;
                                 }

                                 label2870:
                                 try {
                                    break label2870;
                                 } catch (Throwable var79) {
                                    break label2869;
                                 }

                                 try {
                                    ;
                                 } catch (Throwable var76) {
                                    break label2869;
                                 }
                              }

                              try {
                                 ;
                              } catch (Throwable var77) {
                                 break label2869;
                              }
                              label2891:
                              label2891: {
                                 label2892: {
                                    var9 = var1.a((SSLSocket)var89);
                                    label2854:
                                    if (var9.k()) {
                                       okhttp3.internal.e.e.n().b((SSLSocket)var5, var2.l().j(), var2.p());
                                       break label2854;
                                    }
                                    ((SSLSocket)var89).startHandshake();
                                    var84 = ((SSLSocket)var89).getSession();
                                    var11 = this.L(var84);
                                    if (var11)
                                    IOException var88 = new IOException("a valid ssl session was not established");
                                    throw var88;
                                    var92 = okhttp3.u.a(var84);
                                    if (!var2.u().verify(var2.l().j(), var84))
                                    var2.v().b(var2.l(), var92.e());
                                    var94 = var92.e();
                                    var86 = (String)var4;
                                    var85 = (X509Certificate)var8;
                                    if (var94 != null)
                                    label2851:
                                    if (var9.k()) {
                                       var86 = okhttp3.internal.e.e.n().c((SSLSocket)var5);
                                       break label2851;
                                    }
                                    StringBuilder var95 = new StringBuilder();
                                    var95.append("Hostname ");
                                    var95.append(var2.l().j());
                                    var95.append(" not verified:\n    certificate: ");
                                    var95.append(okhttp3.h.g(var85));
                                    var95.append("\n    DN: ");
                                    var95.append(var85.getSubjectDN().getName());
                                    var95.append("\n    subjectAltNames: ");
                                    var95.append(okhttp3.internal.g.d.c(var85));
                                    SSLPeerUnverifiedException var93 = new SSLPeerUnverifiedException(var95.toString());
                                    throw var93;
                                    var85 = (X509Certificate)var8;
                                    this.y = (Socket)var89;
                                    this.C = okio.m.b(okio.m.k((Socket)var89));
                                    this.D = okio.m.c(okio.m.e(this.y));
                                    this.z = var92;
                                    label2847:
                                    if (var94.size() > 0) {
                                       var85 = (X509Certificate)var92.e().get(0);
                                       break label2847;
                                    }
                                    if (var86 != null)
                                    var87 = Protocol.HTTP_1_1;
                                    var87 = Protocol.get(var86);
                                    this.A = var87;
                                    label2898: {
                                       break label2898;
                                    }
                                    break label2891;
                                    label2846: {
                                       break label2846;
                                    }
                                 }                              }                           }                        }                     }                  }               }            }         }      }   }

   private boolean L(SSLSession var1) {
      boolean var2;
      if (!"NONE".equals(var1.getProtocol()) && !"SSL_NULL_WITH_NULL_NULL".equals(var1.getCipherSuite())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private okhttp3.ae M(int var1, int var2, okhttp3.ae var3, HttpUrl var4) throws IOException {
      StringBuilder var5 = new StringBuilder();
      var5.append("CONNECT ");
      var5.append(okhttp3.internal.c.u(var4, true));
      var5.append(" HTTP/1.1");
      String var10 = var5.toString();

      do {
         okhttp3.internal.c.a var6 = new okhttp3.internal.c.a((OkHttpClient)null, (okhttp3.internal.connection.e)null, this.C, this.D);
         this.C.k().c((long)var1, TimeUnit.MILLISECONDS);
         this.D.k().c((long)var2, TimeUnit.MILLISECONDS);
         var6.p(var3.k(), var10);
         var6.e();
         var11 = var6.f(false).n(var3).C();
         var6.s(var11);
         int var7 = var11.p();
         if (var7 == 200) {
            if (this.C.f().j() && this.D.e().j()) {
               return null;
            }

            IOException var9 = new IOException("TLS tunnel buffered too many bytes!");
            throw var9;
         }

         if (var7 != 407) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Unexpected response code for CONNECT: ");
            var8.append(var11.p());
            throw new IOException(var8.toString());
         }

         var3 = this.w.d().o().b(this.w, var11);
         if (var3 == null) {
            throw new IOException("Failed to authenticate with proxy");
         }
      } while(!"close".equalsIgnoreCase(var11.t("Connection")));

      return var3;
   }

   private okhttp3.ae N() throws IOException {
      okhttp3.ae var1 = (new okhttp3.ae.a()).i(this.w.d().l()).q("CONNECT", (okhttp3.af)null).k("Host", okhttp3.internal.c.u(this.w.d().l(), true)).k("Proxy-Connection", "Keep-Alive").k("User-Agent", okhttp3.internal.d.a()).v();
      okhttp3.ag var2 = (new okhttp3.ag.a()).n(var1).o(Protocol.HTTP_1_1).p(407).q("Preemptive Authenticate").v(okhttp3.internal.c.d).z(-1L).A(-1L).s("Proxy-Authenticate", "OkHttp-Preemptive").C();
      okhttp3.ae var3 = this.w.d().o().b(this.w, var2);
      if (var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   private boolean O(List<okhttp3.ai> var1) {
      int var2 = var1.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         okhttp3.ai var4 = (okhttp3.ai)var1.get(var3);
         if (var4.e().type() == Type.DIRECT && this.w.e().type() == Type.DIRECT && this.w.f().equals(var4.f())) {
            return true;
         }
      }

      return false;
   }

   public okhttp3.ai a() {
      return this.w;
   }

   public void i() {
      if (!h && Thread.holdsLock(this.b)) {
         throw new AssertionError();
      } else {
         okhttp3.internal.connection.f var1 = this.b;
         synchronized(var1) {
            this.c = true;
         }
      }
   }

   public void j(int var1, int var2, int var3, int var4, boolean var5, okhttp3.f var6, okhttp3.r var7) {
      if (this.A != null) {
         IllegalStateException var22 = new IllegalStateException("already connected");
         throw var22;
      } else {
         List var8 = this.w.d().q();
         okhttp3.internal.connection.b var9 = new okhttp3.internal.connection.b(var8);
         if (this.w.d().t() == null) {
            if (!var8.contains(okhttp3.l.d)) {
               throw new RouteException(new UnknownServiceException("CLEARTEXT communication not enabled for client"));
            }

            String var24 = this.w.d().l().j();
            if (!okhttp3.internal.e.e.n().g(var24)) {
               StringBuilder var21 = new StringBuilder();
               var21.append("CLEARTEXT communication to ");
               var21.append(var24);
               var21.append(" not permitted by network security policy");
               throw new RouteException(new UnknownServiceException(var21.toString()));
            }
         } else if (this.w.d().p().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            throw new RouteException(new UnknownServiceException("H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"));
         }

         RouteException var10 = null;

         while(true) {
            label182: {
               label181: {
                  label180: {
                     try {
                        if (this.w.g()) {
                           this.G(var1, var2, var3, var6, var7);
                           var26 = this.x;
                           break label180;
                        }
                     } catch (IOException var20) {
                        var25 = var20;
                        break label182;
                     }

                     try {
                        this.H(var1, var2, var6, var7);
                        break label181;
                     } catch (IOException var19) {
                        var25 = var19;
                        break label182;
                     }
                  }

                  if (var26 == null) {
                     break;
                  }
               }

               try {
                  this.I(var9, var4, var6, var7);
                  var7.g(var6, this.w.f(), this.w.e(), this.A);
                  OkHttpClient.a.g(var6, this.w.f(), this.w.e(), this.A);
                  break;
               } catch (IOException var18) {
                  var25 = var18;
               }
            }

            okhttp3.internal.c.m(this.y);
            okhttp3.internal.c.m(this.x);
            this.y = null;
            this.x = null;
            this.C = null;
            this.D = null;
            this.z = null;
            this.A = null;
            this.B = null;
            var7.h(var6, this.w.f(), this.w.e(), (Protocol)null, var25);
            OkHttpClient.a.h(var6, this.w.f(), this.w.e(), (Protocol)null, var25);
            if (var10 == null) {
               var10 = new RouteException(var25);
            } else {
               var10.addConnectException(var25);
            }

            if (!var5 || !var9.b(var25)) {
               throw var10;
            }
         }

         if (this.w.g() && this.x == null) {
            throw new RouteException(new ProtocolException("Too many tunnel connections attempted: 21"));
         } else {
            if (this.B != null) {
               okhttp3.internal.connection.f var23 = this.b;
               synchronized(var23) {
                  this.F = this.B.x();
               }
            }

         }
      }
   }

   boolean k(okhttp3.a var1, List<okhttp3.ai> var2) {
      if (this.f.size() < this.F && !this.c) {
         if (!okhttp3.internal.a.i.d(this.w.d(), var1)) {
            return false;
         }

         if (var1.l().j().equals(this.a().d().l().j())) {
            return true;
         }

         if (this.B == null) {
            return false;
         }

         if (var2 != null && this.O(var2)) {
            if (var1.u() != okhttp3.internal.g.d.a) {
               return false;
            }

            if (!this.l(var1.l())) {
               return false;
            }

            try {
               var1.v().d(var1.l().j(), this.s().e());
               return true;
            } catch (SSLPeerUnverifiedException var3) {
            }
         }
      }

      return false;
   }

   public boolean l(HttpUrl var1) {
      int var2 = var1.k();
      int var3 = this.w.d().l().k();
      boolean var4 = false;
      if (var2 != var3) {
         return false;
      } else if (!var1.j().equals(this.w.d().l().j())) {
         boolean var5 = var4;
         if (this.z != null) {
            var5 = var4;
            if (okhttp3.internal.g.d.a.b(var1.j(), (X509Certificate)this.z.e().get(0))) {
               var5 = true;
            }
         }

         return var5;
      } else {
         return true;
      }
   }

   okhttp3.internal.b.c m(OkHttpClient var1, okhttp3.z.a var2) throws SocketException {
      if (this.B != null) {
         return new okhttp3.internal.http2.e(var1, this, var2, this.B);
      } else {
         this.y.setSoTimeout(var2.f());
         this.C.k().c((long)var2.f(), TimeUnit.MILLISECONDS);
         this.D.k().c((long)var2.g(), TimeUnit.MILLISECONDS);
         return new okhttp3.internal.c.a(var1, this, this.C, this.D);
      }
   }

   public void n() {
      okhttp3.internal.c.m(this.x);
   }

   public Socket o() {
      return this.y;
   }

   public boolean p(boolean var1) {
      if (!this.y.isClosed() && !this.y.isInputShutdown() && !this.y.isOutputShutdown()) {
         okhttp3.internal.http2.d var2 = this.B;
         if (var2 != null) {
            return var2.M(System.nanoTime());
         } else if (var1) {
            int var3;
            try {
               var3 = this.y.getSoTimeout();
            } catch (SocketTimeoutException var21) {
               boolean var26 = false;
               return true;
            } catch (IOException var22) {
               boolean var10001 = false;
               return false;
            }

            boolean var13 = false;

            try {
               var13 = true;
               this.y.setSoTimeout(1);
               var1 = this.C.j();
               var13 = false;
            } finally {
               if (var13) {
                  try {
                     this.y.setSoTimeout(var3);
                  } catch (SocketTimeoutException var14) {
                     boolean var28 = false;
                     return true;
                  } catch (IOException var15) {
                     boolean var27 = false;
                     return false;
                  }
               }
            }

            if (var1) {
               try {
                  this.y.setSoTimeout(var3);
                  return false;
               } catch (SocketTimeoutException var16) {
                  boolean var30 = false;
                  return true;
               } catch (IOException var17) {
                  boolean var29 = false;
               }
            } else {
               try {
                  this.y.setSoTimeout(var3);
                  return true;
               } catch (SocketTimeoutException var18) {
                  boolean var32 = false;
                  return true;
               } catch (IOException var19) {
                  boolean var31 = false;
               }
            }

            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void q(okhttp3.internal.http2.g var1) throws IOException {
      var1.r(ErrorCode.REFUSED_STREAM, (IOException)null);
   }

   public void r(okhttp3.internal.http2.d var1) {
      okhttp3.internal.connection.f var2 = this.b;
      synchronized(var2) {
         this.F = var1.x();
      }
   }

   public okhttp3.u s() {
      return this.z;
   }

   public boolean t() {
      boolean var1;
      if (this.B != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Connection{");
      var1.append(this.w.d().l().j());
      var1.append(":");
      var1.append(this.w.d().l().k());
      var1.append(", proxy=");
      var1.append(this.w.e());
      var1.append(" hostAddress=");
      var1.append(this.w.f());
      var1.append(" cipherSuite=");
      okhttp3.u var2 = this.z;
      Object var3;
      if (var2 != null) {
         var3 = var2.d();
      } else {
         var3 = "none";
      }

      var1.append(var3);
      var1.append(" protocol=");
      var1.append(this.A);
      var1.append('}');
      return var1.toString();
   }

   void u(IOException var1) {
      if (!h && Thread.holdsLock(this.b)) {
         throw new AssertionError();
      } else {
         okhttp3.internal.connection.f var2 = this.b;
         synchronized(var2){}

         Throwable var10000;
         label874: {
            label873: {
               int var3;
               label880: {
                  label881: {
                     try {
                        if (var1 instanceof StreamResetException) {
                           var94 = ((StreamResetException)var1).errorCode;
                           if (var94 != ErrorCode.REFUSED_STREAM) {
                              break label881;
                           }

                           var3 = this.E + 1;
                           this.E = var3;
                           break label880;
                        }
                     } catch (Throwable var93) {
                        var10000 = var93;
                        boolean var10001 = false;
                        break label874;
                     }

                     try {
                        if (this.t() && !(var1 instanceof ConnectionShutdownException)) {
                           break label873;
                        }
                     } catch (Throwable var92) {
                        var10000 = var92;
                        boolean var96 = false;
                        break label874;
                     }

                     try {
                        this.c = true;
                        if (this.e != 0) {
                           break label873;
                        }
                     } catch (Throwable var91) {
                        var10000 = var91;
                        boolean var97 = false;
                        break label874;
                     }

                     if (var1 != null) {
                        try {
                           this.b.k(this.w, var1);
                        } catch (Throwable var88) {
                           var10000 = var88;
                           boolean var98 = false;
                           break label874;
                        }
                     }

                     try {
                        ++this.d;
                        break label873;
                     } catch (Throwable var87) {
                        var10000 = var87;
                        boolean var99 = false;
                        break label874;
                     }
                  }

                  try {
                     if (var94 != ErrorCode.CANCEL) {
                        this.c = true;
                        ++this.d;
                     }
                     break label873;
                  } catch (Throwable var90) {
                     var10000 = var90;
                     boolean var101 = false;
                     break label874;
                  }
               }

               if (var3 > 1) {
                  try {
                     this.c = true;
                     ++this.d;
                  } catch (Throwable var89) {
                     var10000 = var89;
                     boolean var100 = false;
                     break label874;
                  }
               }
            }

            label839:
            try {
               return;
            } catch (Throwable var86) {
               var10000 = var86;
               boolean var102 = false;
               break label839;
            }
         }

         while(true) {
            Throwable var95 = var10000;

            try {
               throw var95;
            } catch (Throwable var85) {
               var10000 = var85;
               boolean var103 = false;
               continue;
            }
         }
      }
   }
}
