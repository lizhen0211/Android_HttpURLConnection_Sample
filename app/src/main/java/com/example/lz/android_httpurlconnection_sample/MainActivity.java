package com.example.lz.android_httpurlconnection_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lz.android_httpurlconnection_sample.net.HttpRequest;
import com.example.lz.android_httpurlconnection_sample.net.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.result);
    }

    public void onGetClick(View view) {
        String url = "http://www.baidu.com";
        HttpRequest request = new HttpRequest(url, HttpRequest.MethodType.GET, new HttpResponse.Listener() {
            @Override
            public void onResponse(final HttpResponse result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(result.responseContents);
                    }
                });
            }
        }, new HttpResponse.ErrorListener() {
            @Override
            public void onErrorResponse(int errorCode, String errorResult) {
                System.out.print(errorCode);
            }
        });
        request.start();
    }

    public void onPostURLEncodedClick(View view) {
        // FIXME: 2016/9/21 lz 替换自己的服务器域名
        String url = "http://www.baidu.com";
        HttpRequest request = new HttpRequest(url, HttpRequest.MethodType.POST, new HttpResponse.Listener() {
            @Override
            public void onResponse(final HttpResponse result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(result.responseContents);
                    }
                });
            }
        }, new HttpResponse.ErrorListener() {
            @Override
            public void onErrorResponse(final int errorCode, final String errorResult) {
                System.out.print(errorCode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(errorCode + ":" + errorResult);
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // FIXME: 2016/9/21 增加你的请求参数
                params.put("testKey", "testValue");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Connection", "keep-alive");
                // FIXME: 2016/9/21 添加你的头文件信息
                return headers;
            }
        };
        request.start();
    }

    public void onPostJsonClick(View view) {
        // FIXME: 2016/11/8 替换自己的服务器域名
        String url = "http://www.baidu.com";
        HttpRequest request = new HttpRequest(url, HttpRequest.MethodType.POST, new HttpResponse.Listener() {
            @Override
            public void onResponse(final HttpResponse result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(result.responseContents);
                    }
                });
            }
        }, new HttpResponse.ErrorListener() {
            @Override
            public void onErrorResponse(final int errorCode, final String errorResult) {
                System.out.print(errorCode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(errorCode + ":" + errorResult);
                    }
                });
            }
        }) {
            @Override
            public byte[] getBody() {
                String data = "{'title':'test', 'sub' : [1,2,3]}";
                // FIXME: 2016/11/8 替换自己的请求参数
                return data.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                // FIXME: 2016/11/8 添加自己的header
                return headers;
            }
        };
        request.start();
    }

    public void onPostXmlClick(View view) {

    }
}
