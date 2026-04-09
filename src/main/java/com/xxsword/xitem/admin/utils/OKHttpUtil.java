package com.xxsword.xitem.admin.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OKHttpUtil {

    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static final OkHttpClient HTTPS_CLIENT;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        try {
            TrustManager[] trustAllCerts = buildTrustManagers();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.warn("HTTPS client init with trust-all failed, fallback to default SSL: {}", e.getMessage());
        }
        HTTPS_CLIENT = builder.build();
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * HTTP GET 请求
     */
    public static String httpGet(String url) {
        return doGet(CLIENT, url);
    }

    /**
     * HTTPS GET 请求（支持自签名证书）
     */
    public static String httpsGet(String url) {
        return doGet(HTTPS_CLIENT, url);
    }

    private static String doGet(OkHttpClient client, String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body == null) {
                log.warn("GET {} response body is null, code={}", url, response.code());
                return null;
            }
            return body.string();
        } catch (Exception e) {
            log.error("GET {} failed: {}", url, e.getMessage(), e);
            return null;
        }
    }

    /**
     * HTTP POST 表单请求
     */
    public static String httpPost(String url, Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                builder.add(entry.getKey(), value != null ? value : "");
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        return doPost(CLIENT, request, url);
    }

    /**
     * HTTP POST 自定义 Request
     */
    public static String httpPost(Request request) {
        return doPost(CLIENT, request, request.url().toString());
    }

    /**
     * HTTPS POST 自定义 Request（支持自签名证书）
     */
    public static String httpsPost(Request request) {
        return doPost(HTTPS_CLIENT, request, request.url().toString());
    }

    private static String doPost(OkHttpClient client, Request request, String urlForLog) {
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body == null) {
                log.warn("POST {} response body is null, code={}", urlForLog, response.code());
                return null;
            }
            return body.string();
        } catch (IOException e) {
            log.error("POST {} failed: {}", urlForLog, e.getMessage(), e);
            return null;
        }
    }

    /**
     * HTTP POST JSON 请求
     */
    public static String httpPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        return doPostJson(CLIENT, request, url);
    }

    /**
     * HTTPS POST JSON 请求（支持自签名证书）
     */
    public static String httpsPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        return doPostJson(HTTPS_CLIENT, request, url);
    }

    private static String doPostJson(OkHttpClient client, Request request, String urlForLog) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + " for " + urlForLog);
            }
            ResponseBody body = response.body();
            return body != null ? body.string() : "";
        }
    }

    /**
     * 构建信任所有证书的 OkHttpClient（仅用于自签名/内网等特殊场景，生产环境慎用）
     */
//    public static OkHttpClient.Builder buildOKHttpClient() {
//        try {
//            TrustManager[] trustAllCerts = buildTrustManagers();
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
//            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
//            builder.hostnameVerifier((hostname, session) -> true);
//            return builder;
//        } catch (NoSuchAlgorithmException | KeyManagementException e) {
//            log.error("buildOKHttpClient failed: {}", e.getMessage(), e);
//            return new OkHttpClient.Builder();
//        }
//    }

    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

}
