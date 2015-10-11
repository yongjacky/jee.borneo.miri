package com.borneo.framework.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class HttpConnection {

    private static final String ENCODE = "UTF-8";

    public synchronized static JSONObject SendRequest(String content, String url) {
        return SendRequest(content, url, null);
    }

    public synchronized static JSONObject SendRequest(byte[] bt, String url) {
        return SendRequest(bt, url, null);
    }

    public synchronized static JSONObject SendRequest(String content, String url, String sessionId) {
        byte[] btArr = null;
        try {
            btArr = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return SendRequest(btArr, url, sessionId);
    }

    public synchronized static JSONObject SendRequest(byte[] bt, String url, String sessionId) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) httpUrl.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", "" + Integer.toString(bt.length));
            httpConn.setRequestProperty("Content-Language", "UTF-8");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(sessionId)) {
                String myCookies = "JSESSIONID=" + sessionId;
                httpConn.setRequestProperty("Cookie", myCookies);
            }
            httpConn.setUseCaches(false);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);

            OutputStream out = httpConn.getOutputStream();
            out.write(bt);
            out.flush();
            out.close();

            //System.out.println("   ------ URL: " + url);
            //int len = bt.length;
            //if (len > 1024) {
            //len = 1024;	
            //}
            //System.out.println("   >>>>>> Request: " + new String(bt).substring(0, len));

            int code = httpConn.getResponseCode();
            StringBuffer responseMessage = new StringBuffer();
            if (code == 200) {
                InputStream is = httpConn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, ENCODE);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    responseMessage.append(line);
                }
                is.close();
            } else {
                return null;
            }
            if (responseMessage != null) {
                try {
                    JSONObject outer = JSONObject.fromObject(responseMessage.toString());
                    JSONArray parameters = outer.getJSONArray("parameters");
                    if (parameters != null) {
                        JSONObject result = parameters.getJSONObject(0);
                        //System.out.println("   <<<<<< Response: " + result.toString() + "\n");
                        return result;
                    }
                } catch (JSONException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
