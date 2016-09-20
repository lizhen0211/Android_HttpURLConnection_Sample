package com.example.lz.android_httpurlconnection_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lz.android_httpurlconnection_sample.net.HttpRequest;
import com.example.lz.android_httpurlconnection_sample.net.HttpResponse;

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

    public void onPostPairClick(View view) {

    }

    public void onPostJsonClick(View view) {

    }

    public void onPostXmlClick(View view) {

    }
}
