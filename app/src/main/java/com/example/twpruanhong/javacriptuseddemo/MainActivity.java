package com.example.twpruanhong.javacriptuseddemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private WebView m_webView;
    private Button m_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_webView = (WebView) findViewById(R.id.wv_test);
        //WebSetting
        WebSettings webSettings = m_webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        m_webView.loadUrl("http://172.16.0.245:8038/index.html");
//        String htmlCode = "<!DOCTYPE html><html><head><meta charset='UTF-8'><script src='http://172.16.0.245:8038/test.js'>" +
//                "</script></head><body><div>sjjifjdijfidjfisjifji</div></body></html>";
        String htmlCode = "<!DOCTYPE html><html><head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>andorid and js Demo</title>\n" +
                "\t\n" +
                "\t<script src=\"http://172.16.0.245:8038/test.js\">\n" +
                "\t\n" +
                "\t</script>\n" +
                "\t\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div style=\"height:100px;background:green\"> \n" +
                "\t这是一个demo\n" +
                "\t</div>\n" +
                "\t<video width=\"50%\" height=\"50%\" controls=\"controls\">\n" +
                "\t\t<source src=\"http://www.w3school.com.cn/i/movie.ogg\" type=\"video/ogg\"></source>\n" +
                "\t\t<source src=\"http://www.w3school.com.cn/i/movie.ogg\" type=\"video/mp4\"></source>\n" +
                "\t</video>\n" +
                "</body>\n" +
                "</html>";
//        m_webView.loadUrl("http://172.16.0.245:8038/androidToJs.html");
        m_webView.loadData(htmlCode,"text/html","utf-8");
//        m_webView.loadUrl("http://172.16.0.245:8038/test.js");
//        String jsStr = "";
//        try {
//            URL jsUrl = new URL("http://172.16.0.245:8038/test.js");
//            try {
//                InputStream is = jsUrl.openStream();
//                byte[] buff = new byte[1024];
//                ByteArrayOutputStream formFile = new ByteArrayOutputStream();
//                FileOutputStream out = null;
//                do {
//                    int numRead = is.read(buff);
//                    if(numRead <=0 ) {
//                        break;
//                    }
//                    formFile.write(buff,0,numRead);
//                } while (true);
//                jsStr = formFile.toString();
////                m_webView.loadUrl("javascript:" + jsStr);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


        m_btn = (Button) findViewById(R.id.btn_test_call);
//        final String finalJsStr = jsStr;
        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_webView.post(new Runnable() {
                    @Override
                    public void run() {
//                        m_webView.loadUrl("javascript:callJs()");//使用loadUrl()
                        m_webView.evaluateJavascript("javascript:callJs()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        m_webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert");
                builder.setMessage(message);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });
    }

}
