package com.adfi.miciudad;

import java.util.ArrayList;

import com.google.android.maps.MapView;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Lista extends ListActivity {
	
	 private ArrayList<Registro> m_locals = null;
	 private IconListViewAdapter m_adapter;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView(R.layout.tab_lista);
		
	      
       m_locals = new ArrayList<Registro>();
       this.m_adapter = new IconListViewAdapter(this, R.layout.iconrow, m_locals);
       setListAdapter(this.m_adapter);  
       
       inicializarLocales();

       
       Button btn = (Button) findViewById(R.id.button1);
       btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			 Intent intent = new Intent(getApplicationContext(), Cargar.class);
			
			 startActivityForResult(intent, 111);
				
				
		}
	});
       
       
       
   }
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	   
	    if(MyProperties.getInstance().vuelveDialogoCarga==true){
	    
	    	// vuelve a leer por si se agregaron datos
	    	if(MyProperties.getInstance().registroNuevo!=null){
	    		m_adapter.add(MyProperties.getInstance().registroNuevo);
	    		m_adapter.notifyDataSetChanged();
	    		MyProperties.getInstance().registroNuevo=null; // lo vuelvo a poner en null
	    	}
	    	// llama a la nueva ventana
	    	Intent intent = new Intent(this.getApplicationContext(), EventoCercano.class);
	    	startActivity(intent);
	    }
	    
	    
	     }
	
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
   		Registro local = (Registro) l.getItemAtPosition(position);        
      
   		MyProperties.getInstance().registroActual=local;
   		
   	  Intent intent = new Intent(this.getApplicationContext(), DetalleRegistro.class);
	  startActivity(intent);
   	
   }

   
   private void inicializarLocales(){
   	
   	try {
           m_locals = new ArrayList<Registro>();
           
           for (int i=0; i<MyProperties.getInstance().listaRegistros.size(); i++) {
        	   Registro o= MyProperties.getInstance().listaRegistros.get(i);
        	   Registro o1 = new Registro();
           	   o1.id = o.id;
           	   o1.solved = o.solved;
           	   o1.descripcion = o.descripcion;
           	   o1.nombre = o.nombre;
           	   //o1.fecha = o.fecha;
               o1.categoria = o.categoria;
               o1.mail = o.mail;
               o1.latitud = o.latitud;
               o1.longitud = o.longitud;
              // o1.direccion = o.direccion;
               o1.ticket = o.ticket;
              
               m_locals.add(o1);
           }
           
                
          // Log.i("Locales añadidos ", ""+ m_locals.size());
           
         } catch (Exception e) {
           //Log.e("BACKGROUND_PROC", e.getMessage());
         }
         
         if(m_locals != null && m_locals.size() > 0){
             for(int i=0;i<m_locals.size();i++)
             m_adapter.add(m_locals.get(i));
         }

         m_adapter.notifyDataSetChanged();       	
   	
   }  


public class IconListViewAdapter extends ArrayAdapter<Registro> {

	        private ArrayList<Registro> items;

	        public IconListViewAdapter(Context context, int textViewResourceId, ArrayList<Registro> items) {
	                super(context, textViewResourceId, items);
	                this.items = items;
	        }
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	                View v = convertView;
	                if (v == null) {
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    v = vi.inflate(R.layout.iconrow, null);
	                }
	                Registro o = items.get(position);
	                if (o != null) {
	                	
	                        TextView textnombre = (TextView) v.findViewById(R.id.textnombre);
	                        TextView textdireccion = (TextView) v.findViewById(R.id.textdireccion);
	                        ImageView im = (ImageView) v.findViewById(R.id.icono);
	                        
	                        textdireccion.setText(o.descripcion);
	                        Log.v("cat",String.valueOf(o.categoria));
	                        switch(o.categoria) {
		                        case 1:
		                        	 textnombre.setText("Bache");
		                        	 im.setImageResource(R.drawable.icono_lista_1);
		                        	break;
		                        case 2:
		                        	 textnombre.setText("Camara de seguridad");
		                        	 im.setImageResource(R.drawable.icono_lista_2);
		                        	break;
		                        case 3:
		                        	 textnombre.setText("Semaforos descompuestos");
		                        	 im.setImageResource(R.drawable.icono_lista_3);
		                        	break;
		                        case 4:
		                        	 textnombre.setText("Poste o árbol caído");
		                        	 im.setImageResource(R.drawable.icono_lista_4);
		                        	 break;
		                        case 5:
		                        	 textnombre.setText("Aceras Rotas");
		                        	 im.setImageResource(R.drawable.icono_lista_1);
		                        	 break;
		                        case 6:
		                        	 textnombre.setText("Barrido Deficiente");
		                        	 im.setImageResource(R.drawable.icono_lista_1);
		                        	 break;
		                        case 7:
		                        	 textnombre.setText("Corte de raíces");
		                        	 im.setImageResource(R.drawable.icono_lista_4);
		                        	 break;
		                        case 8:
		                        	 textnombre.setText("Extracción de árbol");
		                        	 im.setImageResource(R.drawable.icono_lista_4);
		                        	 break;
		                        case 9:
		                        	 textnombre.setText("Luminarias apagadas");
		                        	 im.setImageResource(R.drawable.icono_lista_3);
		                        	 break;
		                        case 10:
		                        	 textnombre.setText("Poda de ramas");
		                        	 im.setImageResource(R.drawable.icono_lista_4);
		                        	 break;
		                        case 11:
		                        	 textnombre.setText("Residuos voluminosos");
		                        	 im.setImageResource(R.drawable.icono_lista_1);
		                        	 break;
		                        case 12:
		                        	 textnombre.setText("Vehículos abandonados");
		                        	 im.setImageResource(R.drawable.icono_lista_1);
		                        	break;
		                        	
	                        }
	      	                                                      
	                          	                    	                        
	                }
	                return v;
	        }
	}
}