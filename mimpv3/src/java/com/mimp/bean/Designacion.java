package com.mimp.bean;
// Generated 6/12/2013 11:57:53 AM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Designacion generated by hbm2java
 */
public class Designacion  implements java.io.Serializable {


     private Integer iddesignacion;
     private Nna nna;
     private Personal personal;
     private ExpedienteFamilia expedienteFamilia;
     private Integer NDesignacion;
     private Integer prioridad;
     private Date fechaPropuesta;
     private Date fechaConsejo;
     private Boolean aceptacionConsejo;
     private String tipoPropuesta;
     private String obs;

    public Designacion() {
    }

	
    public Designacion(Nna nna, Personal personal, ExpedienteFamilia expedienteFamilia) {
        this.nna = nna;
        this.personal = personal;
        this.expedienteFamilia = expedienteFamilia;
    }
    public Designacion(Nna nna, Personal personal, ExpedienteFamilia expedienteFamilia, Integer NDesignacion, Integer prioridad, Date fechaPropuesta, Date fechaConsejo, Boolean aceptacionConsejo, String tipoPropuesta, String obs) {
       this.nna = nna;
       this.personal = personal;
       this.expedienteFamilia = expedienteFamilia;
       this.NDesignacion = NDesignacion;
       this.prioridad = prioridad;
       this.fechaPropuesta = fechaPropuesta;
       this.fechaConsejo = fechaConsejo;
       this.aceptacionConsejo = aceptacionConsejo;
       this.tipoPropuesta = tipoPropuesta;
       this.obs = obs;
    }
   
    public Integer getIddesignacion() {
        return this.iddesignacion;
    }
    
    public void setIddesignacion(Integer iddesignacion) {
        this.iddesignacion = iddesignacion;
    }
    public Nna getNna() {
        return this.nna;
    }
    
    public void setNna(Nna nna) {
        this.nna = nna;
    }
    public Personal getPersonal() {
        return this.personal;
    }
    
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    public ExpedienteFamilia getExpedienteFamilia() {
        return this.expedienteFamilia;
    }
    
    public void setExpedienteFamilia(ExpedienteFamilia expedienteFamilia) {
        this.expedienteFamilia = expedienteFamilia;
    }
    public Integer getNDesignacion() {
        return this.NDesignacion;
    }
    
    public void setNDesignacion(Integer NDesignacion) {
        this.NDesignacion = NDesignacion;
    }
    public Integer getPrioridad() {
        return this.prioridad;
    }
    
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    public Date getFechaPropuesta() {
        return this.fechaPropuesta;
    }
    
    public void setFechaPropuesta(Date fechaPropuesta) {
        this.fechaPropuesta = fechaPropuesta;
    }
    public Date getFechaConsejo() {
        return this.fechaConsejo;
    }
    
    public void setFechaConsejo(Date fechaConsejo) {
        this.fechaConsejo = fechaConsejo;
    }
    public Boolean getAceptacionConsejo() {
        return this.aceptacionConsejo;
    }
    
    public void setAceptacionConsejo(Boolean aceptacionConsejo) {
        this.aceptacionConsejo = aceptacionConsejo;
    }
    public String getTipoPropuesta() {
        return this.tipoPropuesta;
    }
    
    public void setTipoPropuesta(String tipoPropuesta) {
        this.tipoPropuesta = tipoPropuesta;
    }
    public String getObs() {
        return this.obs;
    }
    
    public void setObs(String obs) {
        this.obs = obs;
    }




}


