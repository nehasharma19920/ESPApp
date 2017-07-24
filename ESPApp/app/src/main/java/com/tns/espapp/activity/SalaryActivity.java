package com.tns.espapp.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.tns.espapp.AppConstraint;
import com.tns.espapp.R;
import com.tns.espapp.Utility.SharedPreferenceUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SalaryActivity extends AppCompatActivity {

    private WebView mWebView;
    private Long mOnGoingDownload;
    private Button mCancel;
    private DownloadManager mDownloadManger;
    private ProgressDialog pd;
    private SharedPreferenceUtils sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait Loading...");
        pd.show();
        pd.setCancelable(false);
        mDownloadManger = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        mCancel = (Button)findViewById(R.id.cancelDownload);
        mCancel.setVisibility(View.GONE);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDownload();
            }
        });

        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if (!pd.isShowing()) {
                    pd.show();
                }

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("on finish");
                if (pd.isShowing()) {
                    pd.dismiss();
                }

            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                startDownload(url, userAgent, mimetype);
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        sharedPreferences = SharedPreferenceUtils.getInstance();
        sharedPreferences.setContext(this);
        String empId = sharedPreferences.getString(AppConstraint.EMPID);
        mWebView.loadUrl("http://tnssofts.com/ESP/Info/SalaryInfoWebView/" + empId);

    }

    protected void cancelDownload() {
        if (mOnGoingDownload != null) {
            mDownloadManger.remove(mOnGoingDownload);
            clearDownloadingState();
            Toast.makeText(SalaryActivity.this, "Download canceled", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
    protected void clearDownloadingState() {
        this.unregisterReceiver(mDownloadCompleteListener);
        mCancel.setVisibility(View.GONE);
        mOnGoingDownload = null;
    }

    BroadcastReceiver mDownloadCompleteListener = new BroadcastReceiver() {
        public void onReceive(Context ctx, Intent intent) {
            clearDownloadingState();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            Uri fileUri = mDownloadManger.getUriForDownloadedFile(id);
            Toast.makeText(ctx, fileUri.getLastPathSegment() + " downloaded", Toast.LENGTH_SHORT).show();
        }
    };

    protected void startDownload(String url, String userAgent, String mimetype) {
        Uri fileUri = Uri.parse(url);
        String fileName = fileUri.getLastPathSegment();
        String cookies = CookieManager.getInstance().getCookie(url);

        DownloadManager.Request request = new DownloadManager.Request(fileUri);
        request.setMimeType(mimetype)
                .addRequestHeader("cookie", cookies)
                .addRequestHeader("User-Agent", userAgent)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        this.registerReceiver(mDownloadCompleteListener, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mCancel.setVisibility(View.VISIBLE);
        mOnGoingDownload = mDownloadManger.enqueue(request);
        Toast.makeText(SalaryActivity.this, "SalaryActivity::startDownload: Download of " + fileName + " started", Toast.LENGTH_LONG).show();
    }
}
