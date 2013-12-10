package com.mimp.bean;
// Generated 10/12/2013 03:07:34 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * EstudioCaso generated by hbm2java
 */
public class EstudioCaso  implements java.io.Serializable {


     private Integer idestudioCaso;
     private Nna nna;
     private ExpedienteFamilia expedienteFamilia;
     private String orden;
     private Date fechaEstudio;
     private Date fechaSolAdop;
     private String resultado;
     private Integer prioridad;
     private Long NSolicitud;

    public EstudioCaso() {
    }

	
    public EstudioCaso(Nna nna, ExpedienteFamilia expedienteFamilia) {
        this.nna = nna;
        this.expedienteFamilia = expedienteFamilia;
    }
    public EstudioCaso(Nna nna, ExpedienteFamilia expedienteFamilia, String orden, Date fechaEstudio, Date fechaSolAdop, String resultado, Integer prioridad, Long NSolicitud) {
       this.nna = nna;
       this.expedienteFamilia = expedienteFamilia;
       this.orden = orden;
       this.fechaEstudio = fechaEstudio;
       this.fechaSolAdop = fechaSolAdop;
       this.resultado = resultado;
       this.prioridad = prioridad;
       this.NSolicitud = NSolicitud;
    }
   
    public Integer getIdestudioCaso() {
        return this.idestudioCaso;
    }
    
    public void setIdestudioCaso(Integer idestudioCaso) {
        this.idestudioCaso = idestudioCaso;
    }
    public Nna getNna() {
        return this.nna;
    }
    
    public void setNna(Nna nna) {
        this.nna = nna;
    }
    public ExpedienteFamilia getExpedienteFamilia() {
        return this.expedienteFamilia;
    }
    
    public void setExpedienteFamilia(ExpedienteFamilia expedienteFamilia) {
        this.expedienteFamilia = expedienteFamilia;
    }
    public String getOrden() {
        return this.orden;
    }
    
    public void setOrden(String orden) {
        this.orden = orden;
    }
    public Date getFechaEstudio() {
        return this.fechaEstudio;
    }
    
    public void setFechaEstudio(Date fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }
    public Date getFechaSolAdop() {
        return this.fechaSolAdop;
    }
    
    public void setFechaSolAdop(Date fechaSolAdop) {
        this.fechaSolAdop = fechaSolAdop;
    }
    public String getResultado() {
        return this.resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public Integer getPrioridad() {
        return this.prioridad;
    }
    
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    public Long getNSolicitud() {
        return this.NSolicitud;
    }
    
    public void setNSolicitud(Long NSolicitud) {
        this.NSolicitud = NSolicitud;
    }




}


