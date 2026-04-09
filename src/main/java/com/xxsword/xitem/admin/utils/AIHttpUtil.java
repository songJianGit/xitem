package com.xxsword.xitem.admin.utils;

import com.alibaba.fastjson2.JSONObject;
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
import java.util.stream.Collectors;

/**
 * 单独给AI一个，因为其http非流式响应的数据，需要比较长的超时时间
 */
@Slf4j
public class AIHttpUtil {

    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 90;// 一般在50秒左右就ok了，这里冗余一些秒数
    private static final int WRITE_TIMEOUT = 30;

    private static final OkHttpClient HTTPS_CLIENT;

    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64); // 设置最大总并发数（默认64）
        dispatcher.setMaxRequestsPerHost(32);// 同一主机的最大并发请求数（默认5）

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
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
     * HTTPS POST JSON 请求（支持自签名证书）
     */
    public static String httpsPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        return doPostJson(HTTPS_CLIENT, request, url);
    }

    /**
     * 创建请求对象
     *
     * @param url
     * @param headerInfo
     * @param bodyInfo
     * @return
     */
    public static Request initRequestPost(String url, JSONObject headerInfo, JSONObject bodyInfo) {
        // 创建请求体
        RequestBody body = RequestBody.create(bodyInfo == null ? "" : bodyInfo.toJSONString(), MediaType.parse("application/json; charset=utf-8"));
        // 创建头部对象
        Map<String, Object> map = headerInfo.toJavaObject(Map.class);
        Headers headers = Headers
                .of(map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toString())));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .headers(headers)
                .build();
        return request;
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
