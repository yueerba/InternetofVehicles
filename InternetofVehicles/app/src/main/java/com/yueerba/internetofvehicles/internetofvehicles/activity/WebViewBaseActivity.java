package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yueerba.internetofvehicles.internetofvehicles.R;

//此处出过Caused by: android.content.res.Resources$NotFoundException:的异常
//原因是AppCompatActivity与activity之间跳转的问题，所以我改成了activity
public class WebViewBaseActivity extends Activity {

    private WebView webView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 注意顺序
        setContentView(R.layout.activity_webview);

        webView = (WebView)findViewById(R.id.webView);

        //通过Intent调用系统浏览器打开指定网页
        /*Uri uri = Uri.parse("https://www.baidu.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);*/

        //1.WebView通过loadUrl加载页面
        /*webView.loadUrl("http://www.cheshouye.com/api.html");*/

        webView.loadDataWithBaseURL(null," <iframe name=\"weizhang\" src=\"http://m.cheshouye.com/api/weizhang/\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\"></iframe>", "text/html", "UTF-8",null);

        //2.WebView通过loadUrl加载HTML脚本(解析，显示)
        //String summary = "<html><body>You scored <b>192</b> points.</body></html>";
        //webView.loadData(summary, "text/html", null);

        //3.WebView通过loadUrl加载本地assets目录下页面
        //webView.loadUrl("file:///android_asset/index.html");

        webView.requestFocus();//使页面获得焦点

        //当用户点击WebView中的页面链接时，通常是由Android系统默认的浏览器打开并加载目标URL
        //这样的行为对用户来说是不友好的，你可以在WebView中覆盖这一行为，那么链接就会在WebView中打开
        //WebViewClient帮助WebView处理一些页面控制和请求通知
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值为true控制网页在WebView中打开，false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings webSettings = webView.getSettings();
        //设置支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);

        //判断页面加载过程
        //由于有些网页加载缓慢，我们需要去判断页面的加载过程，制作进度条给予用户良好的体验效果
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100){//网页加载完成
                    closeProgressDialog();
                }else{//网页加载中
                    openProgressDialog(newProgress);
                }
            }

            // 设置应用程序的标题title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                WebViewBaseActivity.this.setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });

        //WebView缓存的应用，默认情况下不使用缓存
        //优先使用缓存,提高下次打开同一页面的速度(/data/data/包名/cache/webViewChachChromlum)
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    //后退与前进
    //当WebView覆盖了URL加载，会自动生成历史访问记录，可以通过goBack()或goForward()
    //向前或向后访问已访问过的站点
   @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if((keyCode==KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            //Toast.makeText(this,webView.getUrl(),Toast.LENGTH_SHORT).show();
            webView.goBack();//返回上一页面
            return true;
        }else{
            System.exit(0);//某些Android版本不生效
        }
        return super.onKeyDown(keyCode,event);
    }

    private void closeProgressDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private void openProgressDialog(int newProgress){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setTitle("正在加载");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(newProgress);
            dialog.show();
        }else{
            dialog.setProgress(newProgress);
        }
    }
}
