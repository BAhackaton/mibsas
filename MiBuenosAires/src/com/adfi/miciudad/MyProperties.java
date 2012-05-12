package com.adfi.miciudad;

import java.util.List;

public class MyProperties {

    private static MyProperties mInstance= null;


    public Registro registroActual;
    
    public int idtipo;

    List<Registro> listaRegistros;

    public String latIngresada="";

    public String lngIngresada="";

    public boolean vuelveDialogoCarga=false;
    public int idregistrado=0;

    protected MyProperties(){}



    public static synchronized MyProperties getInstance(){

        if(null == mInstance){

                mInstance = new MyProperties();

        }

        return mInstance;

    }

}