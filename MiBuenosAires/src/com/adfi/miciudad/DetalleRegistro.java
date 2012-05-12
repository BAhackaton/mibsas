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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleRegistro extends MapActivity {
	private static final String IO_BUFFER_SIZE = null;

	private ProgressDialog progDailog;
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
		TextView categoria=(TextView) findViewById(R.id.categoria);
		TextView descripcion=(TextView) findViewById(R.id.descripcion);
		TextView direccion=(TextView) findViewById(R.id.direccion);
		
		TextView nombre=(TextView) findViewById(R.id.nombre);
		TextView fecha=(TextView) findViewById(R.id.fecha);
		TextView solucionado=(TextView) findViewById(R.id.estado);
		
		Registro r=MyProperties.getInstance().registroActual;
		
		switch (r.categoria){
		case 1: 
			categoria.setText("Bache");
			break;
		case 2: 
			categoria.setText("Cámaras de seguridad");
			break;
		case 3: 
			categoria.setText("Semáforos descompuestos");
			break;
		case 4: 
			categoria.setText("Poste o árbol caído");
			break;
			
		}
		descripcion.setText(r.descripcion);
		direccion.setText(r.direccion);
		nombre.setText(r.nombre);
		fecha.setText(r.fecha);
		if(r.solved){
			solucionado.setText("Resuelto");
		}else{
			solucionado.setText("Pendiente");
		}
		
 	
 			
         
 			downloadFile("http://miciudad.raise.fm/img/"+r.id+".jpg");
final MapView mapView = (MapView) findViewById(R.id.mapview);
 			
 			
 			Double la1 =  r.latitud*1E6;
 	    	Double lo1 =  r.longitud*1E6;
 	    	GeoPoint point  = new GeoPoint(la1.intValue(),lo1.intValue());
 	       	mapView.getController().setCenter(point);
 	       	
 	       	
 	       	
 	       	List<Overlay> mapOverlays = mapView.getOverlays();
 	    	   
 	    	OverlayItem overlayItem = new OverlayItem(point, "1","1");
 	    	  Drawable icono1=null;
 	      	
 	    	  switch (r.categoria){
 	    	  case 1:
 	    		  if (r.solved){
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono1solved);
 	    		  }else {
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono1);
 	    		  }
 	    		  break;
 	    	  case 2:
 	    		  if (r.solved){
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono2solved);
 	    		  }else {
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono2);
 	    		  }
 	    		  break;
 	    	  case 3:
 	    		  if (r.solved){
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono3solved);
 	    		  }else {
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono3);
 	    		  }
 	    		  break;
 	    	  case 4:
 	    		  if (r.solved){
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono4solved);
 	    		  }else {
 	    			  icono1= this.getResources().getDrawable(R.drawable.icono4);
 	    		  }
 	    		  break;
 	    	  }
 	    	  
 	    	 
 	    	  
 	    	OverlayPersonalizado op=new OverlayPersonalizado(icono1,this);
 	    	op.addOverlay(overlayItem,1);
 	    	

 	       	mapOverlays.add(op);
 	        op.populateNow();
             	 
             	
              
       
 			
 			
	}
	
	 public  void downloadFile(String fileUrl){
         URL myFileUrl =null;          
         try {
              myFileUrl= new URL(fileUrl);
         } catch (MalformedURLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
         }
         try {
              HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
              conn.setDoInput(true);
              conn.connect();
              int length = conn.getContentLength();
              InputStream is = conn.getInputStream();
              
            Bitmap bmp= BitmapFactory.decodeStream(is);
            ImageView foto=(ImageView) findViewById(R.id.foto);
    		foto.setImageBitmap(bmp);
           
         } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
         }
	
 }




	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
