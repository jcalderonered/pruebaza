package com.mimp.bean;
// Generated 6/12/2013 11:57:53 AM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * InformePostAdoptivo generated by hbm2java
 */
public class InformePostAdoptivo  implements java.io.Serializable {


     private Integer idinformePostAdoptivo;
     private PostAdopcion postAdopcion;
     private Personal personal;
     private String estado;
     private String numeroInforme;
     private Date fechaRecepcionProyectado;
     private Date fechaRecepcion;
     private Date fechaInforme;
     private Date fechaActa;
     private String obs;

    public InformePostAdoptivo() {
    }

	
    public InformePostAdoptivo(PostAdopcion postAdopcion, Personal personal) {
        this.postAdopcion = postAdopcion;
        this.personal = personal;
    }
    public InformePostAdoptivo(PostAdopcion postAdopcion, Personal personal, String estado, String numeroInforme, Date fechaRecepcionProyectado, Date fechaRecepcion, Date fechaInforme, Date fechaActa, String obs) {
       this.postAdopcion = postAdopcion;
       this.personal = personal;
       this.estado = estado;
       this.numeroInforme = numeroInforme;
       this.fechaRecepcionProyectado = fechaRecepcionProyectado;
       this.fechaRecepcion = fechaRecepcion;
       this.fechaInforme = fechaInforme;
       this.fechaActa = fechaActa;
       this.obs = obs;
    }
   
    public Integer getIdinformePostAdoptivo() {
        return this.idinformePostAdoptivo;
    }
    
    public void setIdinformePostAdoptivo(Integer idinformePostAdoptivo) {
        this.idinformePostAdoptivo = idinformePostAdoptivo;
    }
    public PostAdopcion getPostAdopcion() {
        return this.postAdopcion;
    }
    
    public void setPostAdopcion(PostAdopcion postAdopcion) {
        this.postAdopcion = postAdopcion;
    }
    public Personal getPersonal() {
        return this.personal;
    }
    
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNumeroInforme() {
        return this.numeroInforme;
    }
    
    public void setNumeroInforme(String numeroInforme) {
        this.numeroInforme = numeroInforme;
    }
    public Date getFechaRecepcionProyectado() {
        return this.fechaRecepcionProyectado;
    }
    
    public void setFechaRecepcionProyectado(Date fechaRecepcionProyectado) {
        this.fechaRecepcionProyectado = fechaRecepcionProyectado;
    }
    public Date getFechaRecepcion() {
        return this.fechaRecepcion;
    }
    
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
    public Date getFechaInforme() {
        return this.fechaInforme;
    }
    
    public void setFechaInforme(Date fechaInforme) {
        this.fechaInforme = fechaInforme;
    }
    public Date getFechaActa() {
        return this.fechaActa;
    }
    
    public void setFechaActa(Date fechaActa) {
        this.fechaActa = fechaActa;
    }
    public String getObs() {
        return this.obs;
    }
    
    public void setObs(String obs) {
        this.obs = obs;
    }




}


