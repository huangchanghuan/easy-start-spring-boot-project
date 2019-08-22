package com.cmic.sim.maap.core.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * 发送http 请求
 */
public class WebUtil {

    private final static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpsGet(String url) throws IOException {
        CloseableHttpClient httpclients = getSSLCloseableHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclients.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity,"UTF-8");
        response.close();
        return result;
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpsPost(String url) throws IOException{
        CloseableHttpClient httpclients = getSSLCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = httpclients.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity,"UTF-8");
        response.close();
        return result;
    }

    /**
     * post请求
     * @param url
     * @param formparams
     * @return
     * @throws IOException
     */
    public static String httpsPost(String url, List<NameValuePair> formparams) throws IOException {
        CloseableHttpClient httpclients = getSSLCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpclients.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String result = EntityUtils.toString(entity1);
        response.close();
        return result;
    }

    public static String HttpsPostSim(String url, JSONObject object) throws IOException {
        CloseableHttpClient httpclients = getSSLCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity=new StringEntity(object.toString(),Consts.UTF_8);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpclients.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String result = EntityUtils.toString(entity1);
        response.close();
        return result;
    }


    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {
        CloseableHttpClient httpclients = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclients.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity,"UTF-8");
        response.close();
        return result;
    }

    /**
     * post请求
     * @param url
     * @param formparams
     * @return
     * @throws IOException
     */
    public static String httpPost(String url,List<NameValuePair> formparams) throws IOException {
        CloseableHttpClient httpclients = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,Consts.UTF_8);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpclients.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String result = EntityUtils.toString(entity1);
        response.close();
        return result;
    }
    private static CloseableHttpClient getSSLCloseableHttpClient() throws IOException{
        CloseableHttpClient httpclients = null;
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                    throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
        try {
            //获取TLS安全协议上下文
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{xtm}, null);
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
            RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", scsf).build();
            PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager(sfr);
            httpclients = HttpClients.custom().setConnectionManager(pcm).setDefaultRequestConfig(defaultConfig).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        if (httpclients == null) {
            logger.error("获取HTTPS连接失败");
            throw new IOException();
        }
        return httpclients;
    }
}
