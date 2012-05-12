package com.adfi.miciudad;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class OverlayPersonalizado extends ItemizedOverlay{
	private Context mContext;
	private int tipo; //0 usa el overlay 1 usa tres lineas de texto
	
	OverlayItem overlay2;
	 
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	 public OverlayPersonalizado(Drawable defaultMarker, Context context)
	 {
	 super(boundCenterBottom(defaultMarker));
	 mContext = context;
	 }
	

	public void addOverlay(OverlayItem overlay, int tipo) {
		this.tipo=tipo;
	    mOverlays.add(overlay);
	    overlay2=overlay;
	    
	}
	
	public void populateNow(){
		populate();
		
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  
	  long id=Long.parseLong(item.getTitle());
	  
	  for(int i=0; i<MyProperties.getInstance().listaRegistros.size();i++){
		  
		  if(MyProperties.getInstance().listaRegistros.get(i).id==id){
			  MyProperties.getInstance().registroActual=MyProperties.getInstance().listaRegistros.get(i);
			  Intent intent = new Intent(mContext, DetalleRegistro.class);
			  mContext.startActivity(intent);
			  break;
		  }
		  
		 
	  }
    	 /* AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    	  builder.setTitle(item.getTitle());
		  	builder.setMessage(item.getSnippet().toString())
	       .setCancelable(false)
	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                // put your code here
	           }
	       }); 
			AlertDialog alertDialog = builder.create();
			alertDialog.show();*/
		return true;
	}

	}