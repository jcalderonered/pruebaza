package com.mimp.bean;
// Generated Dec 23, 2013 10:39:27 AM by Hibernate Tools 3.6.0



/**
 * Autoridad generated by hbm2java
 */
public class Autoridad  implements java.io.Serializable {


     private long idautoridad;
     private Entidad entidad;
     private String tipo;

    public Autoridad() {
    }

	
    public Autoridad(long idautoridad, Entidad entidad) {
        this.idautoridad = idautoridad;
        this.entidad = entidad;
    }
    public Autoridad(long idautoridad, Entidad entidad, String tipo) {
       this.idautoridad = idautoridad;
       this.entidad = entidad;
       this.tipo = tipo;
    }
   
    public long getIdautoridad() {
        return this.idautoridad;
    }
    
    public void setIdautoridad(long idautoridad) {
        this.idautoridad = idautoridad;
    }
    public Entidad getEntidad() {
        return this.entidad;
    }
    
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




}


