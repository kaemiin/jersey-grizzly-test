package test.jersey;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import test.jersey.model.ResultBean;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static junit.framework.Assert.assertEquals;

/**
 * Created by kaemiin on 2016/3/11.
 */
@RunWith(JUnit4.class)
public class DraftServiceTest {

    private boolean isLocalTest = false;

    private boolean isUseCookie = false;

    private String targetSite = "";

    @Test
    //@Ignore
    public void testSendFromDraftOldFashionedWay() {

        String url;

        boolean isUseSSL = false;

        if (isLocalTest) {

            url = "http://localhost:8080/jersey-grizzy-test/draft/1234/send/oldfashionedway";
        }
        else {

            isUseSSL = true;

            url = "https://" + targetSite + "/JerseyTest/draft/1234/send/oldfashionedway";
        }

        ResultBean bean = send(url, isUseSSL, isUseCookie);

        assertEquals("This is old fashioned way.", bean.getResult());
    }

    @Test
    //@Ignore
    public void testSendFromDraftNewWay() {

        String url;

        boolean isUseSSL = false;

        if (isLocalTest) {

            url = "http://localhost:8080/jersey-grizzy-test/draft/1234/send/new";
        }
        else {

            isUseSSL = true;

            url = "https://" + targetSite + "/JerseyTest/draft/1234/send/new";
        }

        ResultBean bean = send(url, isUseSSL, isUseCookie);

        assertEquals("This is new way.", bean.getResult());
    }

    private ResultBean send(String url, boolean isUseSSL, boolean isUseCookie) {

        Client client = null;

        if (isUseSSL) {

            try {

                client = buildUnsecureRestClient();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            client = ClientBuilder.newClient();
        }

        javax.ws.rs.core.Cookie dummyCookie = null;

        Form form = new Form();
        form.param("Topic", "20160311123045");
        form.param("Message", "This is a msg.");

        if (isUseCookie) {

            dummyCookie =
                    new javax.ws.rs.core.Cookie("SESSIONID", java.util.UUID.randomUUID().toString());

            form.param("IsUseCookie", "true");
        }
        else {

            url = url + "?SESSIONID=" + java.util.UUID.randomUUID();

            form.param("IsUseCookie", "false");
        }

        WebTarget target = client.target(url);

        System.out.println(target.getUri());

        return target.request(MediaType.APPLICATION_JSON_TYPE).cookie(dummyCookie)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        ResultBean.class);
    }

    public Client buildUnsecureRestClient() throws GeneralSecurityException {

        SSLContext context = SSLContext.getInstance("TLSv1");

        TrustManager[] trustManagerArray = {
            new NullX509TrustManager()
        };

        context.init(null, trustManagerArray, null);

        return ClientBuilder.newBuilder()
                .hostnameVerifier(new NullHostnameVerifier())
                .sslContext(context)
                .build();
    }

    private static class NullHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostName,
                              SSLSession session) {
            return true;
        }
    }

    private static class NullX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] certs,
                                       String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs,
                                       String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
