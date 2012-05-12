package com.adfi.miciudad;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import com.google.android.maps.MyLocationOverlay;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Cargar extends Activity {
	
	boolean salir=false;
	
	private ProgressDialog progDailog;
	
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	protected static final int CAMERA_PIC_REQUEST = 100;
	Double latitud,longitud;
	Bitmap imagenCamara=null;
	
	protected LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		 requestWindowFeature(Window.FEATURE_PROGRESS);
		  
		 MyProperties.getInstance().vuelveDialogoCarga=false;
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        
	        locationManager.requestLocationUpdates(
	        		LocationManager.NETWORK_PROVIDER, 
	        		MINIMUM_TIME_BETWEEN_UPDATES, 
	        		MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
	        		new MyLocationListener()
	        );
		 
	       
		setContentView(R.layout.registro);
		
		latitud=0.0;
		longitud=0.0;
		
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
	    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
	        android.R.layout.simple_spinner_dropdown_item,
	            new String[] { "Bache", "Cámaras de seguridad", "Semáforos descompuestos" ,"Poste o árbol caído", "Piquete","Mejora espacio público","Otra propuesta de mejora"});
	    
	   
	    spinner.setAdapter(spinnerArrayAdapter);
	    
	    Button foto=(Button)findViewById(R.id.btnfoto);
	    foto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		
			}
		});
	    
	    
	    
	    Button enviar=(Button)findViewById(R.id.btnenviar);
	    enviar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				progDailog = ProgressDialog.show(v.getContext(),
			    		"Subiendo datos", "Por favor espere....",
			    		true);
				
				new Thread() {
		    		public void run() {
		    			
		    			String id=postData();
						postImage(id);	
						
						 while (!salir) {
						 
						 
						 }
						

						 handler.sendEmptyMessage(0);
		    		}
		    		}.start();
				
				
				
			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		
			
		}

	};

	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
	    	super.onActivityResult(requestCode, resultCode, data);
	    	imagenCamara=null;
		    if (resultCode == RESULT_OK &&  requestCode==CAMERA_PIC_REQUEST)
		    {
		    		Bitmap userImage = (Bitmap)data.getExtras().get("data"); 
		    		imagenCamara=userImage;
		    		
		    		Button btnEnviar=(Button) findViewById(R.id.btnenviar);
		    		btnEnviar.setVisibility(View.VISIBLE);
		    }
		    	
	   	}
 
	    
	public String postData(){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://miciudad.raise.fm/registro.php");

		Spinner categoria=(Spinner) findViewById(R.id.spinner1);
		
		
		EditText descripcion=(EditText) findViewById(R.id.descripcion);
		EditText direccion=(EditText) findViewById(R.id.direccion);
		EditText nombre=(EditText) findViewById(R.id.nombre);
		
		try {
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("categoria", String.valueOf(categoria.getSelectedItemPosition()+1)));
		    nameValuePairs.add(new BasicNameValuePair("descripcion", descripcion.getText().toString()));
		    nameValuePairs.add(new BasicNameValuePair("direccion", direccion.getText().toString()));
		    nameValuePairs.add(new BasicNameValuePair("nombre", nombre.getText().toString()));
		    nameValuePairs.add(new BasicNameValuePair("lat", latitud.toString()));
		    nameValuePairs.add(new BasicNameValuePair("lon", longitud.toString()));
		    httppost.setEntity((HttpEntity) new UrlEncodedFormEntity(nameValuePairs));

		    ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    String response = httpclient.execute(httppost, responseHandler);

		    Log.v("ciudad",response);
		    if(response.contains("OK")){
		    	String []separado=response.split("#");
		    	
		    	return separado[1];
		   }
		    
		    
		} catch (ClientProtocolException e) {
			//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
		    
			//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		return null;
	}

	void postImage(String nombre_archivo){
		
		
		

			if (imagenCamara!=null){
			
			
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagenCamara.compress(Bitmap.CompressFormat.JPEG, 30, stream); //compress to which format you want
        
        byte [] byte_arr = stream.toByteArray();

        String image_str = Base64.encodeBytes(byte_arr);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();    
        nameValuePairs.add(new BasicNameValuePair("image",image_str));
       nameValuePairs.add(new BasicNameValuePair("image_name",nombre_archivo+".jpg"));
       // nameValuePairs.add(new BasicNameValuePair("image_name","willy.jpg"));

        try{
        		HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://havasmediaargentina.com/miciudad/subirFoto.php");
        		//HttpPost httppost = new HttpPost("http://4flirt.mobi/android/ws/subirFoto.php");
        		
        		
        		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                
                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
    		    //   String response = httpclient.execute(httppost, responseHandler);
    		    
    		    HttpResponse response = httpclient.execute(httppost);
    		    HttpEntity entity = response.getEntity();

        	
    		    
               // Toast.makeText(this, "Registro Subido "+nombre_archivo, Toast.LENGTH_LONG).show();

        }catch(Exception e){

          // Toast.makeText(this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();

          //    System.out.println("Error in http connection "+e.toString());
	}
		
		}
		
			
			progDailog.dismiss();
			salir=true;
			
			MyProperties.getInstance().vuelveDialogoCarga=true;
			MyProperties.getInstance().idregistrado=Integer.parseInt(nombre_archivo);
			
			this.finish();
	}
	
	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
		
			Toast.makeText(Cargar.this, "Posicion Encontrada ", Toast.LENGTH_LONG).show();
			latitud=location.getLatitude();
			longitud=location.getLongitude();
		}

		public void onStatusChanged(String s, int i, Bundle b) {
			

		}

		public void onProviderDisabled(String s) {
		/*	Toast.makeText(PosicionActivity.this,
					"Provider disabled by the user. GPS turned off",
					Toast.LENGTH_LONG).show(); */
		}

		public void onProviderEnabled(String s) {
			/* Toast.makeText(PosicionActivity.this,
					"Provider enabled by the user. GPS turned on",
					Toast.LENGTH_LONG).show(); */
		}
	}
	
}