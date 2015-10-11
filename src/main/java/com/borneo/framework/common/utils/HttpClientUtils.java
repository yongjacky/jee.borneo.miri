package com.borneo.framework.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {
    private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    public static PostMethod getPostMethod(String url) {
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        postMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        return postMethod;
    }

    public static GetMethod getGetMethod(String url) {
        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        getMethod.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        return getMethod;
    }

    public static HttpClient getHttpClient(int timeOut) {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
        return httpClient;
    }

    public static String getURL(String urlStr) throws IOException {

        /*
         * HostnameVerifier hv = new HostnameVerifier() {
         * @Override public boolean verify(String hostname, SSLSession session) { System.out.println("Warning: URL Host: " + hostname + " vs. " + session.getPeerHost()); return true; } }; HttpsURLConnection.setDefaultHostnameVerifier(hv);
         */

        URL url = new URL(urlStr);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setRequestMethod("GET");
        uc.setReadTimeout(50000);
        uc.setDoOutput(true);
        uc.connect();
        InputStream inputStream = null;
        try {
            inputStream = uc.getInputStream();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        if (inputStream != null) {
            return getURL(inputStream);
        } else {
            return null;
        }
    }

    public static String getURL(InputStream inputStream) throws IOException {

        String page = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = in.readLine()) != null) {
            page = page + line;
        }
        in.close();
        //log.debug(page);
        return page;
    }

    public static void main(String[] args) {
        HttpClient httpClient = HttpClientUtils.getHttpClient(60000);
        //http://www.tumblr.com/api/reblog
        PostMethod postMethod = HttpClientUtils.getPostMethod("http://www.tumblr.com/api/reblog");
        postMethod.addParameter("post-id", "9596339846");
        try {
            httpClient.executeMethod(postMethod);
            postMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
