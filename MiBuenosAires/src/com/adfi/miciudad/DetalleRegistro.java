package com.adfi.miciudad;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleRegistro extends MapActivity {
	private static final String IO_BUFFER_SIZE = null;

	private ProgressDialog progDailog;

	private WebView myWebView;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalleregistro);
		
		leerDatos();
		
	}
	
	
	private void leerDatos(){
	
		
		
		
		Registro r=MyProperties.getInstance().registroActual;
		
	
 	    	
 	    	
 	        myWebView = (WebView) findViewById(R.id.webview);
 	        myWebView.setWebViewClient(new MyWebViewClient());
 	       
 	        WebSettings webSettings = myWebView.getSettings();
 	        webSettings.setJavaScriptEnabled(true);
 	    
 	        myWebView.loadUrl("http://miciudad.raise.fm/mapa/mapa.php?id="+r.id);
 	    	//GeoPoint point  = new GeoPoint(la1.intValue(),lo1.intValue());
 	 			
	}
	
	

	   private class MyWebViewClient extends WebViewClient {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	         return false;
	        }
	    }


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
