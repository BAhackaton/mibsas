package com.adfi.miciudad;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventoCercano extends Activity {
	
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_PROGRESS);
		 
		 progressDialog = new ProgressDialog(EventoCercano.this);
	      progressDialog.setMessage("Leyendo datos ...");
	      progressDialog.setCancelable(false);
	      progressDialog.show();
		 
		MyProperties.getInstance().vuelveDialogoCarga=false;
		setContentView(R.layout.eventocercano);
		  WebView myWebView = (WebView) findViewById(R.id.webkitWebView1);
		  myWebView.setVisibility(View.VISIBLE);
		  
		   
		  myWebView.setWebViewClient(new WebViewClient() {
			  
			  @Override

		        public boolean shouldOverrideUrlLoading(WebView view, String url) {

		        return false;

		        }
	 	      @Override
	 	      public void onPageFinished(WebView view, String url) {
	 	    	  super.onPageFinished(view, url);
	 	    	  progressDialog.hide();
	 	      }
	 	      });

	         

	          WebSettings webSettings = myWebView.getSettings();

	          webSettings.setJavaScriptEnabled(true);
		    String url="http://miciudad.raise.fm/estaresuelto.php?id="+MyProperties.getInstance().idregistrado;
		      
		    
		    myWebView.loadUrl(url);
		   
	}
	
	@Override
	public void onPause() {
		super.onPause();
	
		progressDialog.dismiss();
	}
	
}
