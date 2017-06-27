package com.tns.espapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tns.espapp.R;

/**
 * Created by GARIMA on 6/23/2017.
 */

public class TicketFragment extends Fragment {
    private View view;
    private WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.ticket_fragment, container, false);
        getLayoutsId();

        return view;
    }

    private void getLayoutsId() {
        webView = (WebView) view.findViewById(R.id.webview);
        webView.setWebViewClient(new TicketFragment.MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("http://tnssofts.com/ESP/Info/PersonalInfoWebView");
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
