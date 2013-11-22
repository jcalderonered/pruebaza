package com.mimp.bean;
// Generated Nov 22, 2013 4:14:42 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * InformePostAdoptivo generated by hbm2java
 */
public class InformePostAdoptivo  implements java.io.Serializable {


     private Integer idinformePostAdoptivo;
     private PostAdopcion postAdopcion;
     private String estado;
     private String numeroInforme;
     private Date fechaRecepcionProyectado;
     private Date fechaRecepcion;
     private String profesAsigNom;
     private String profesAsigP;
     private String profesAsigM;
     private Date fechaInforme;
     private Date fechaActa;

    public InformePostAdoptivo() {
    }

	
    public InformePostAdoptivo(PostAdopcion postAdopcion) {
        this.postAdopcion = postAdopcion;
    }
    public InformePostAdoptivo(PostAdopcion postAdopcion, String estado, String numeroInforme, Date fechaRecepcionProyectado, Date fechaRecepcion, String profesAsigNom, String profesAsigP, String profesAsigM, Date fechaInforme, Date fechaActa) {
       this.postAdopcion = postAdopcion;
       this.estado = estado;
       this.numeroInforme = numeroInforme;
       this.fechaRecepcionProyectado = fechaRecepcionProyectado;
       this.fechaRecepcion = fechaRecepcion;
       this.profesAsigNom = profesAsigNom;
       this.profesAsigP = profesAsigP;
       this.profesAsigM = profesAsigM;
       this.fechaInforme = fechaInforme;
       this.fechaActa = fechaActa;
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
    public String getProfesAsigNom() {
        return this.profesAsigNom;
    }
    
    public void setProfesAsigNom(String profesAsigNom) {
        this.profesAsigNom = profesAsigNom;
    }
    public String getProfesAsigP() {
        return this.profesAsigP;
    }
    
    public void setProfesAsigP(String profesAsigP) {
        this.profesAsigP = profesAsigP;
    }
    public String getProfesAsigM() {
        return this.profesAsigM;
    }
    
    public void setProfesAsigM(String profesAsigM) {
        this.profesAsigM = profesAsigM;
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




}


