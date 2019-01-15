package mo.edu.ipm.siweb.data;

import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import mo.edu.ipm.siweb.data.interceptor.AuthorizationInterceptor;
import mo.edu.ipm.siweb.util.FileUtil;
import mo.edu.ipm.siweb.util.TlsSocketFactoryCompat;
import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.droidsonroids.retrofit2.JspoonConverterFactory;
import retrofit2.Retrofit;

public class HttpService {

    private static SIWebService mSIWebService;
    private static Cache sCache;

    public static SIWebService SIWEB() {
        if (mSIWebService == null) {
            try {
                if (sCache == null) {
//                    initCache();
                }
                CookieManager cookieManager = new CookieManager();
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(new TlsSocketFactoryCompat()).cookieJar(new JavaNetCookieJar(cookieManager))
                        .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC))
                        .addInterceptor(new AuthorizationInterceptor())
//                        .addInterceptor(new CacheControlInterceptor())
//                        .cache(sCache)
                        .followSslRedirects(false)
                        .followRedirects(false)
                        .build();

                mSIWebService = new Retrofit.Builder()
                        .baseUrl(SIWebService.BASE_URL)
                        .addConverterFactory(JspoonConverterFactory.create())
                        .client(client)
                        .build().create(SIWebService.class);
            } catch (NoSuchAlgorithmException nae) {
                return null;
            } catch (KeyManagementException ke) {
                return null;
            }
        }

        return mSIWebService;
    }

    private static void initCache() {
        File file = FileUtil.getCacheDirectory();
        sCache = new Cache(file, 1024 * 1024 * 10);
    }
}
