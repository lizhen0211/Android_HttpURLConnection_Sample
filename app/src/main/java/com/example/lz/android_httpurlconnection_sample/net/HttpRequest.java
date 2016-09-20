package com.example.lz.android_httpurlconnection_sample.net;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lz on 2016/9/20.
 */
public class HttpRequest {

    private static final String TAG = "HttpRequest";
    /**
     * 请求url
     */
    private String url;

    /**
     * 请求类型
     *
     * @see MethodType
     */
    private MethodType methodType;

    /**
     * 请求成功监听
     */
    private HttpResponse.Listener listener;

    /**
     * 请求失败监听
     */
    private HttpResponse.ErrorListener errorListener;

    private RequestTask requestTask;


    public HttpRequest(String url, MethodType methodType, HttpResponse.Listener listener, HttpResponse.ErrorListener errorListener) {
        this.url = url;
        this.methodType = methodType;
        this.listener = listener;
        this.errorListener = errorListener;
        requestTask = new RequestTask();
    }

    public void start() {
        requestTask.execute(this);
    }

    public void cancel() {
        requestTask.cancel(true);
    }

    void openConnection() {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "openConnection failed,url is invalid");
        } else {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(this.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(methodType.value);
                conn.setDoInput(true);
                //conn.setDoOutput(true);
                //conn.setRequestProperty("Connection", "Keep-Alive");
                //conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
                conn.setUseCaches(false);
                //在对各种参数配置完成后，通过调用connect方法建立TCP连接，但是并未真正获取数据
                //conn.connect()方法不必显式调用，当调用conn.getInputStream()方法时内部也会自动调用connect方法
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    InputStream inputStream = conn.getInputStream();
                    if (listener != null) {
                        String resultStr = HttpUtil.getStringByInputStream(inputStream);
                        listener.onResponse(new HttpResponse(responseCode, resultStr, conn.getHeaderFields()));
                    }
                } else {
                    InputStream errorStream = conn.getErrorStream();
                    if (errorListener != null) {
                        String errorResult = HttpUtil.getStringByInputStream(errorStream);
                        errorListener.onErrorResponse(conn.getResponseCode(), errorResult);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public enum MethodType {

        GET("GET"), POST("POST");

        private final String value;

        MethodType(String value) {
            this.value = value;
        }
    }
}

class RequestTask extends AsyncTask<HttpRequest, Void, Void> {

    @Override
    protected Void doInBackground(HttpRequest... httpRequests) {
        httpRequests[0].openConnection();
        return null;
    }
}

