package com.adfi.miciudad;


	import java.io.IOException;
	import java.io.InputStream;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;

	import org.xmlpull.v1.XmlPullParser;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
	import android.util.Log;
import android.util.Xml;

	public class RssParserPull
	{
		
		
		private URL rssUrl;
		private boolean ready=false;
		

			
	    public RssParserPull(String url) 
	    {
			try 
			{
	            this.rssUrl = new URL(url);
	        } 
			catch (MalformedURLException e) 
			{
	            throw new RuntimeException(e);
	        }
	    }
	    
	    public List<Registro> parse() 
	    {
	        List<Registro> listaRegistros = null;
	        XmlPullParser parser = Xml.newPullParser();
	        
	        try 
	        {
	            parser.setInput(this.getInputStream(), null);
	            
	            int evento = parser.getEventType();
	            
	            Registro  registroActual = null;
	            
	            while (evento != XmlPullParser.END_DOCUMENT)
	            {
	                String etiqueta = null;
	                
	                switch (evento)
	                {
	                    case XmlPullParser.START_DOCUMENT:
	                    	
	                    	listaRegistros = new ArrayList<Registro>();
	                        break;
	                        
	                    case XmlPullParser.START_TAG:
	                    	
	                    	etiqueta = parser.getName();
	                        
	                        if (etiqueta.equals("r"))
	                        {
	                        	registroActual = new Registro();
	                        } 
	                        else if (registroActual != null)
	                        {

	                        	
	                        	if (etiqueta.equals("id"))
	                            {
	                        		registroActual.id =Long.parseLong(parser.nextText());
	                            }else if (etiqueta.equals("cat"))
	                            {
	                            	
	                            	registroActual.categoria=Integer.parseInt(parser.nextText());
	                            	Log.v("xml",String.valueOf(registroActual.categoria));
	                            }
	                            else if (etiqueta.equals("des"))
	                            {
	                            	registroActual.descripcion=parser.nextText();
	                            } 
	                            else if (etiqueta.equals("nom"))
	                            {
	                            	registroActual.nombre=parser.nextText();
	                            } 
	                            else if (etiqueta.equals("fec"))
	                            {
	                            	registroActual.fecha=parser.nextText();
	                            }  else if (etiqueta.equals("lat"))
	                            {
	                            	registroActual.latitud=Double.parseDouble(parser.nextText());
	                            } else if (etiqueta.equals("lon"))
	                            {
	                            	registroActual.longitud=Double.parseDouble(parser.nextText());
	                            } else if (etiqueta.equals("direccion"))
	                            {
	                            	registroActual.direccion=parser.nextText();
	                            } 
	                            else if (etiqueta.equals("tic"))
	                            {
	                            	registroActual.ticket=parser.nextText();
	                            } else if (etiqueta.equals("sol"))
	                            {
	                            	if (Integer.parseInt(parser.nextText())==1){
	                            		registroActual.solved=true;
	                            	}
	                            	else{
	                            		registroActual.solved=false;
	                            	}
	                            } 
	                            
	                        }
	                        break;
	                        
	                    case XmlPullParser.END_TAG:
	                    	
	                    	etiqueta = parser.getName();
	                    	
	                        if (etiqueta.equals("r") && registroActual != null)
	                        {
	                        	listaRegistros.add(registroActual);
	                        } 
	                        break;
	                }
	                
	                evento = parser.next();
	            }
	            ready=true;
	        } 
	        catch (Exception ex) 
	        {
	           throw new RuntimeException(ex);
	        	

	        }
	        
	        return listaRegistros;
	    }
	    
		private InputStream getInputStream() 
		{
	        try 
	        {
	            return rssUrl.openConnection().getInputStream();
	        } 
	        catch (IOException e) 
	        {
	            throw new RuntimeException(e);
	        }
	    }

		public boolean isReady() {
			return ready;
		}

		public void setReady(boolean ready) {
			this.ready = ready;
		}
		
		public void parseGoogle() 
		    {
		        
		        XmlPullParser parser = Xml.newPullParser();
		        
		        try 
		        {
		            parser.setInput(this.getInputStream(), null);
		            
		            int evento = parser.getEventType();
		           
		            
		            while (evento != XmlPullParser.END_DOCUMENT)
		            {
		                String etiqueta = null;
		                
		                switch (evento)
		                {
		                    case XmlPullParser.START_DOCUMENT:
		                    	
		                    	
		                        break;
		                        
		                    case XmlPullParser.START_TAG:
		                    	
		                    	etiqueta = parser.getName();
		                       
		                       
		                        break;
		                        
		                    case XmlPullParser.END_TAG:
		                    
		                        break;
		                }
		                
		                evento = parser.next();
		            }
		            ready=true;
		        } 
		        catch (Exception ex) 
		        {
		            throw new RuntimeException(ex);
		        }
		        
		    }
	}
