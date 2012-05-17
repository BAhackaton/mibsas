package com.adfi.miciudad;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	
	private ProgressDialog progDailog;
	List<Registro> listaRegistros;
	
	private ProgressBar progress;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        
        
       Button b=(Button)findViewById(R.id.button1);
       b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent(getApplicationContext(), Tabs.class);
			startActivity(intent);
		}
	});
      
        progress=(ProgressBar) findViewById(R.id.progressBar1);
        
        
        inicializar();
        
    }



public void inicializar(){
	if (isNetworkReachable()){
		
		 
        listaRegistros = new ArrayList<Registro>();
        leerServicio();
          	
    } else{
    	Toast.makeText(this, "No hay conectividad con Internet, verifique la conexión e intente nuevamente", Toast.LENGTH_SHORT).show();
    }
}
public boolean isNetworkReachable() { 
	ConnectivityManager mManager =
	(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 
	NetworkInfo current = mManager.getActiveNetworkInfo();
	if(current == null) {
	return false; }
	return (current.getState() == NetworkInfo.State.CONNECTED); 
}
private void leerServicio(){
	
	 progress.setVisibility(View.VISIBLE);
    		new Thread() {
    		public void run() {
    			boolean salir=false;
    		try{
    		
    			RssParserPull saxparser = new RssParserPull("http://miciudad.raise.fm/get.php");
    			
             while (!salir) {
            	  listaRegistros = saxparser.parse();
            	  if (saxparser.isReady()) {
                	 salir=true;
    
                	 progress.setVisibility(View.INVISIBLE);
                 }
             }
    			
    			
    			
    		} catch (Exception e) { }
    		handler.sendEmptyMessage(0);
    		 progress.setVisibility(View.INVISIBLE);
    		}
    		}.start();
	
}
private Handler handler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
	
	MyProperties.getInstance().listaRegistros=listaRegistros;
		  
	Intent intent = new Intent(getApplicationContext(), Tabs.class);
	startActivity(intent);
	}

};


}

