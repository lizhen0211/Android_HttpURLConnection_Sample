package com.example.lz.android_httpurlconnection_sample.net;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2016/9/20.
 */
public class HttpResponse {

    public final int statusCode;
    public final String responseContents;
    public final Map<String, List<String>> responseHeaders;

    public HttpResponse(int statusCode, String responseContents, Map<String, List<String>> responseHeaders) {
        this.statusCode = statusCode;
        this.responseContents = responseContents;
        this.responseHeaders = responseHeaders;
    }

    public interface ErrorListener {
        void onErrorResponse(int errorCode, String errorResult);
    }

    public interface Listener {
        void onResponse(HttpResponse result);
    }
}
