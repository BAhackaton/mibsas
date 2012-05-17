package com.adfi.miciudad;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Mapa extends MapActivity {
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	protected LocationManager locationManager;

	MyLocationOverlay myLocationOverlay;
	
	List<Registro> listaRegistros;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_mapa);
		
		
		listaRegistros=MyProperties.getInstance().listaRegistros;
		
		final MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(15);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(
        		LocationManager.NETWORK_PROVIDER, 
        		MINIMUM_TIME_BETWEEN_UPDATES, 
        		MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
        		new MyLocationListener()
        );
       
       	
        final MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this, mapView);
    	mapOverlays.add(myLocationOverlay);
    	myLocationOverlay.enableMyLocation();
    
    	myLocationOverlay.runOnFirstFix(new Runnable() {
    		public void run() {
    			mapView.getController().animateTo(
    			  myLocationOverlay.getMyLocation());
    			
    		 	}
    	});
    	
    	
    	  Drawable icono1= this.getResources().getDrawable(R.drawable.icono1);
    	  Drawable icono2= this.getResources().getDrawable(R.drawable.icono2);
    	  Drawable icono3= this.getResources().getDrawable(R.drawable.icono3);
    	  Drawable icono4= this.getResources().getDrawable(R.drawable.icono4);
    	  
    	  agregarOverlayPersonalizado(1,new OverlayPersonalizado(icono1,this),mapOverlays,false);
    	  agregarOverlayPersonalizado(2,new OverlayPersonalizado(icono2,this),mapOverlays,false);
          agregarOverlayPersonalizado(3,new OverlayPersonalizado(icono3,this),mapOverlays,false);
         agregarOverlayPersonalizado(4,new OverlayPersonalizado(icono4,this),mapOverlays,false);
         agregarOverlayPersonalizado(5,new OverlayPersonalizado(icono1,this),mapOverlays,false);
         agregarOverlayPersonalizado(6,new OverlayPersonalizado(icono1,this),mapOverlays,false);
         agregarOverlayPersonalizado(7,new OverlayPersonalizado(icono4,this),mapOverlays,false);
         agregarOverlayPersonalizado(8,new OverlayPersonalizado(icono4,this),mapOverlays,false);
         agregarOverlayPersonalizado(9,new OverlayPersonalizado(icono3,this),mapOverlays,false);
         agregarOverlayPersonalizado(10,new OverlayPersonalizado(icono4,this),mapOverlays,false);
         agregarOverlayPersonalizado(11,new OverlayPersonalizado(icono1,this),mapOverlays,false);
         agregarOverlayPersonalizado(12,new OverlayPersonalizado(icono1,this),mapOverlays,false);
      
         
         Drawable icono1r= this.getResources().getDrawable(R.drawable.icono1solved);
   	  Drawable icono2r= this.getResources().getDrawable(R.drawable.icono2solved);
   	  Drawable icono3r= this.getResources().getDrawable(R.drawable.icono3solved);
   	  Drawable icono4r= this.getResources().getDrawable(R.drawable.icono4solved);
   	 agregarOverlayPersonalizado(1,new OverlayPersonalizado(icono1r,this),mapOverlays,true);
	  agregarOverlayPersonalizado(2,new OverlayPersonalizado(icono2r,this),mapOverlays,true);
     agregarOverlayPersonalizado(3,new OverlayPersonalizado(icono3r,this),mapOverlays,true);
    agregarOverlayPersonalizado(4,new OverlayPersonalizado(icono4r,this),mapOverlays,true);
    agregarOverlayPersonalizado(5,new OverlayPersonalizado(icono1r,this),mapOverlays,true);
    agregarOverlayPersonalizado(6,new OverlayPersonalizado(icono1r,this),mapOverlays,true);
    agregarOverlayPersonalizado(7,new OverlayPersonalizado(icono4r,this),mapOverlays,true);
    agregarOverlayPersonalizado(8,new OverlayPersonalizado(icono4r,this),mapOverlays,true);
    agregarOverlayPersonalizado(9,new OverlayPersonalizado(icono3r,this),mapOverlays,true);
    agregarOverlayPersonalizado(10,new OverlayPersonalizado(icono4r,this),mapOverlays,true);
    agregarOverlayPersonalizado(11,new OverlayPersonalizado(icono1r,this),mapOverlays,true);
    agregarOverlayPersonalizado(12,new OverlayPersonalizado(icono1r,this),mapOverlays,true);
 
          
  
    
		}

	
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    final MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.invalidate();
	    
	    LinearLayout l=(LinearLayout) findViewById(R.id.fondo);
	    l.invalidate();
	     }

	
	
	private void agregarOverlayPersonalizado(int categoria, OverlayPersonalizado op, List<Overlay> mapOverlays, boolean buscaresueltos)
	{
		
		
         for (int i=0; i<listaRegistros.size(); i++) {    
		    	
        	 
        	 	if (listaRegistros.get(i).categoria==categoria && listaRegistros.get(i).solved==buscaresueltos){
		    	Double la1 =  listaRegistros.get(i).latitud*1E6;
		    	Double lo1 =  listaRegistros.get(i).longitud*1E6;
	    	    GeoPoint point3  = new GeoPoint(la1.intValue(),lo1.intValue());
		        
		        OverlayItem overlayItem = new OverlayItem(point3, String.valueOf(listaRegistros.get(i).id) ,listaRegistros.get(i).descripcion);
		        op.addOverlay(overlayItem,1);
		        }
        	 	
        	 
        	 
		    }
         mapOverlays.add(op);
         op.populateNow();
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
		
		//	Toast.makeText(Mapa.this, "Posición encontrada", Toast.LENGTH_LONG).show();
		}

		public void onStatusChanged(String s, int i, Bundle b) {
		//	Toast.makeText(Mapa.this, "Provider status changed",
			//		Toast.LENGTH_LONG).show();
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
