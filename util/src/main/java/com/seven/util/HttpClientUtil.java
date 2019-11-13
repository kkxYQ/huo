package com.seven.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @ Description    HttpClient请求工具类
 * @ Author         dong
 * @ Date           2019-02-13 13:52
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECTION_TIMEOUT = 6000;

    // 请求超时时间
    public static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 10000;

    // http
    public static final String HTTP = "http";

    // https
    public static final String HTTPS = "https";


    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static String get(String url) {
        return get(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, null, params);
    }


    /**
     * 发送get请求；带请求头和请求参数（1.处理http请求;2.处理https请求,信任所有证书）
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param reqMap 请求参数集合
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> reqMap) {
        String result = "";
        if (StringUtils.isEmpty(url)) {
            return result;
        }
        // 处理参数
        List<NameValuePair> params = new ArrayList<>();
        if (reqMap != null && reqMap.keySet().size() > 0) {
            Iterator<Map.Entry<String, String>> iter = reqMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entity = iter.next();
                params.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
            }
        }

        // 创建httpClient对象
        CloseableHttpClient httpClient = null;
        if (url.startsWith(HTTPS)) {
            // 创建一个SSL信任所有证书的httpClient对象
            httpClient = HttpClientUtil.createSSLInsecureClient();
        }else {
            httpClient = HttpClients.createDefault();
        }

        CloseableHttpResponse response = null;
        HttpGet httpGet = null;

        try {
            if (params != null && params.size() > 0) {
                URIBuilder builder = new URIBuilder(url);
                builder.setParameters(params);
                httpGet = new HttpGet(builder.build());
            }else {
                httpGet = new HttpGet(url);
            }

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECTION_TIMEOUT)   //设置连接超时时间
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT) // 设置请求超时时间
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setRedirectsEnabled(true)//默认允许自动重定向
                    .build();
            httpGet.setConfig(requestConfig);

            // 设置请求头
            packageHeader(headers, httpGet);

            // 发送请求，并接收响应
            response = httpClient.execute(httpGet);

            result = handleResponse(url, ENCODING, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(httpClient, response);
        }

        return result;
    }


    /**
     * 发送post请求；不带请求头和请求参数
     * （1.处理http请求;2.处理https请求,信任所有证书）
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static String post(String url) {
        Map<String, String> map = null;
        return post(url, map);
    }

    /**
     * 发送post请求；带请求参数
     * （1.处理http请求;2.处理https请求,信任所有证书）
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, null, params);
    }



    /**
     *  post请求；带请求头和请求参数
     *  (1.处理http请求;2.处理https请求,信任所有证书)
     * @param url   请求地址
     * @param headers   请求头集合
     * @param reqMap   请求参数集合
     * @return
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> reqMap){
        String result = "";
        if (StringUtils.isEmpty(url)) {
            return result;
        }

        // 添加参数
        List<NameValuePair> params = new ArrayList<>();
        if (reqMap != null && reqMap.keySet().size() > 0) {
            Iterator<Map.Entry<String, String>> iter = reqMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entity = iter.next();
                params.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
            }
        }

        // 创建httpClient对象
        CloseableHttpClient httpClient = null;
        if (url.startsWith(HTTPS)) {
            // 创建一个SSL信任所有证书的httpClient对象
            httpClient = HttpClientUtil.createSSLInsecureClient();
        }else {
            httpClient = HttpClients.createDefault();
        }

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECTION_TIMEOUT)   //设置连接超时时间
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT) // 设置请求超时时间
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setRedirectsEnabled(true)//默认允许自动重定向
                    .build();
            httpPost.setConfig(requestConfig);

            //设置请求头
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            packageHeader(headers, httpPost);

            // 设置参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, ENCODING));

            // 发送请求，并接收响应
            response = httpClient.execute(httpPost);
            result = handleResponse(url, ENCODING, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(httpClient, response);
        }
        return result;
    }


    /**
     * 发送post请求；带请求参数
     * （1.处理http请求;2.处理https请求,信任所有证书）
     * @param url 请求地址
     * @param jsonParams json字符串
     * @return
     * @throws Exception
     */
    public static String post(String url, String jsonParams) {
        return post(url, null, jsonParams);
    }


    /**
     * post请求(1.处理http请求;2.处理https请求,信任所有证书)
     * @param url
     * @param jsonParams 入参是个json字符串
     * @param headers
     * @return
     */
    public static String post(String url, Map<String, String> headers, String jsonParams){
        String result = "";
        if (StringUtils.isEmpty(url)) {
            return result;
        }

        CloseableHttpClient httpClient = null;
        if (url.startsWith(HTTPS)) {
            // 创建一个SSL信任所有证书的httpClient对象
            httpClient = HttpClientUtil.createSSLInsecureClient();
        }else {
            httpClient = HttpClients.createDefault();
        }
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECTION_TIMEOUT)   //设置连接超时时间
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT) // 设置请求超时时间
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setRedirectsEnabled(true)//默认允许自动重定向
                    .build();
            httpPost.setConfig(requestConfig);

            //设置请求头
            httpPost.setHeader("Content-Type","application/json");
            packageHeader(headers, httpPost);

            // 设置参数
            httpPost.setEntity(new StringEntity(jsonParams,ContentType.create("application/json", ENCODING)));

            // 发送请求，并接收响应
            response = httpClient.execute(httpPost);
            result = handleResponse(url, ENCODING, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(httpClient, response);
        }
        return result;
    }


    /**
     * 创建一个SSL信任所有证书的httpClient对象
     *
     * @return
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                // 默认信任所有证书
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslcsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return HttpClients.createDefault();
    }

    /**
     * Description: 封装请求头
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 处理响应，获取响应报文
     * @param url
     * @param encoding
     * @param response
     * @return
     * @throws IOException
     */
    private static String handleResponse(String url, String encoding, CloseableHttpResponse response) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // 获取响应实体
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        br = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
                        String s = null;
                        while ((s = br.readLine()) != null) {
                            sb.append(s);
                        }
                    }

                    // 释放entity
                    EntityUtils.consume(entity);
                }else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                    LOGGER.info("-----> get请求404,未找到资源. url:" + url);
                }else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                    LOGGER.info("-----> get请求500,服务器端异常. url:" + url);
                }
            }
        } catch (Exception e) {
            LOGGER.error("----->url:" + url + ",处理响应，获取响应报文异常：" + e.getMessage());
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    /**
     * 释放资源
     * @param httpClient
     * @param response
     */
    private static void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                LOGGER.error("-----> 释放response资源异常:" + e.getMessage());
                e.printStackTrace();
            }
        }

        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                LOGGER.error("-----> 释放httpclient资源异常:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /**
     * 采用绕过验证的方式处理https请求
     * @param url
     * @param reqMap
     * @param encoding
     * @return
     */
    public static String postSSLUrl(String url, Map<String, String> reqMap, String encoding){
        String result = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 添加参数
        List<NameValuePair> params = new ArrayList<>();
        if (reqMap != null && reqMap.keySet().size() > 0) {
            Iterator<Map.Entry<String, String>> iter = reqMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entity = iter.next();
                params.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
            }
        }

        try{
            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();
            //设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);

            //创建自定义的httpclient对象
            httpClient = HttpClients.custom().setConnectionManager(connManager).build();

            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params, encoding));

            //指定报文头Content-type、User-Agent
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            //httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");

            //执行请求操作，并拿到结果（同步阻塞）
            response = httpClient.execute(httpPost);
            result = handleResponse(url, encoding, response);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            closeResource(httpClient, response);
        }

        return result;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }


}
