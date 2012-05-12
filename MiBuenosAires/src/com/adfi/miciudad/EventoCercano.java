package com.adfi.miciudad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventoCercano extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_PROGRESS);
		 
		 
		MyProperties.getInstance().vuelveDialogoCarga=false;
		setContentView(R.layout.eventocercano);
		  WebView myWebView = (WebView) findViewById(R.id.webkitWebView1);
		  myWebView.setVisibility(View.VISIBLE);
		  
		   
		   myWebView.setWebViewClient(new MyWebViewClient());

	         

	          WebSettings webSettings = myWebView.getSettings();

	          webSettings.setJavaScriptEnabled(true);
		    String url="http://miciudad.raise.fm/estaresuelto.php?id="+MyProperties.getInstance().idregistrado;
		      
		    
		    myWebView.loadUrl(url);
		   
	}
	
	
	private class MyWebViewClient extends WebViewClient {

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

        return false;

        }

    }
}
