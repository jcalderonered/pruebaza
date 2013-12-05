package com.mimp.bean;
// Generated 5/12/2013 05:37:51 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Atencion generated by hbm2java
 */
public class Atencion  implements java.io.Serializable {


     private Integer idatencion;
     private Personal personal;
     private Familia familia;
     private Date fecha;
     private Date hora;
     private String tipoAtencion;
     private String detalle;
     private String observacion;

    public Atencion() {
    }

	
    public Atencion(Personal personal, Familia familia) {
        this.personal = personal;
        this.familia = familia;
    }
    public Atencion(Personal personal, Familia familia, Date fecha, Date hora, String tipoAtencion, String detalle, String observacion) {
       this.personal = personal;
       this.familia = familia;
       this.fecha = fecha;
       this.hora = hora;
       this.tipoAtencion = tipoAtencion;
       this.detalle = detalle;
       this.observacion = observacion;
    }
   
    public Integer getIdatencion() {
        return this.idatencion;
    }
    
    public void setIdatencion(Integer idatencion) {
        this.idatencion = idatencion;
    }
    public Personal getPersonal() {
        return this.personal;
    }
    
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    public Familia getFamilia() {
        return this.familia;
    }
    
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getHora() {
        return this.hora;
    }
    
    public void setHora(Date hora) {
        this.hora = hora;
    }
    public String getTipoAtencion() {
        return this.tipoAtencion;
    }
    
    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }
    public String getDetalle() {
        return this.detalle;
    }
    
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }




}


