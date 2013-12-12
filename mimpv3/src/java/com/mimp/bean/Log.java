package com.mimp.bean;
// Generated Dec 12, 2013 4:23:07 PM by Hibernate Tools 3.6.0


import java.io.Serializable;

/**
 * Log generated by hbm2java
 */
public class Log  implements java.io.Serializable {


     private long idlog;
     private Personal personal;
     private Serializable fecha;
     private String tipoReg;
     private String NReg;
     private String incidencia;

    public Log() {
    }

	
    public Log(long idlog, Personal personal) {
        this.idlog = idlog;
        this.personal = personal;
    }
    public Log(long idlog, Personal personal, Serializable fecha, String tipoReg, String NReg, String incidencia) {
       this.idlog = idlog;
       this.personal = personal;
       this.fecha = fecha;
       this.tipoReg = tipoReg;
       this.NReg = NReg;
       this.incidencia = incidencia;
    }
   
    public long getIdlog() {
        return this.idlog;
    }
    
    public void setIdlog(long idlog) {
        this.idlog = idlog;
    }
    public Personal getPersonal() {
        return this.personal;
    }
    
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    public Serializable getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Serializable fecha) {
        this.fecha = fecha;
    }
    public String getTipoReg() {
        return this.tipoReg;
    }
    
    public void setTipoReg(String tipoReg) {
        this.tipoReg = tipoReg;
    }
    public String getNReg() {
        return this.NReg;
    }
    
    public void setNReg(String NReg) {
        this.NReg = NReg;
    }
    public String getIncidencia() {
        return this.incidencia;
    }
    
    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }




}


