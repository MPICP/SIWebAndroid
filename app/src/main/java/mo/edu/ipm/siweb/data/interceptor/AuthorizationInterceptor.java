package mo.edu.ipm.siweb.data.interceptor;

import org.jsoup.Jsoup;

import java.io.IOException;

import mo.edu.ipm.siweb.util.CredentialUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AuthorizationInterceptor implements Interceptor {
    public static final String TAG = "AuthorizationInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
        String content = responseBodyCopy.string();

        boolean needAuthorize;

        if (request.url().toString().contains("login.asp")) {
            return response;
        }

        if (request.url().toString().contains("stud_info.asp")) {
            needAuthorize = studInfoNeedAuth(content);
        } else {
            needAuthorize = needAuth(content);
        }

        if (needAuthorize) {
            if (CredentialUtil.refreshSavedCredential()) {
                return chain.proceed(request);
            } else {
                CredentialUtil.reLogin();
                throw new IOException("Not authorized");
            }
        }

        return response;
    }

    private boolean needAuth (String html) {
        boolean hasAuthTitle = !Jsoup.parse(html)
                .getElementsContainingText("401 Authorization Required").isEmpty();

        return hasAuthTitle;
    }

    private boolean studInfoNeedAuth (String html) {
        return Jsoup.parse(html).body().childNodeSize() == 1;
    }
}
