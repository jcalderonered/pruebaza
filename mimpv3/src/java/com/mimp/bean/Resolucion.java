package com.mimp.bean;
// Generated Dec 18, 2013 9:57:16 AM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Resolucion generated by hbm2java
 */
public class Resolucion  implements java.io.Serializable {


     private long idresolucion;
     private Evaluacion evaluacion;
     private String tipo;
     private String numero;
     private Date fechaResol;
     private Date fechaNotificacion;

    public Resolucion() {
    }

	
    public Resolucion(long idresolucion, Evaluacion evaluacion) {
        this.idresolucion = idresolucion;
        this.evaluacion = evaluacion;
    }
    public Resolucion(long idresolucion, Evaluacion evaluacion, String tipo, String numero, Date fechaResol, Date fechaNotificacion) {
       this.idresolucion = idresolucion;
       this.evaluacion = evaluacion;
       this.tipo = tipo;
       this.numero = numero;
       this.fechaResol = fechaResol;
       this.fechaNotificacion = fechaNotificacion;
    }
   
    public long getIdresolucion() {
        return this.idresolucion;
    }
    
    public void setIdresolucion(long idresolucion) {
        this.idresolucion = idresolucion;
    }
    public Evaluacion getEvaluacion() {
        return this.evaluacion;
    }
    
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getFechaResol() {
        return this.fechaResol;
    }
    
    public void setFechaResol(Date fechaResol) {
        this.fechaResol = fechaResol;
    }
    public Date getFechaNotificacion() {
        return this.fechaNotificacion;
    }
    
    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }




}


