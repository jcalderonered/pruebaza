package com.mimp.bean;
// Generated Dec 4, 2013 5:48:18 PM by Hibernate Tools 3.6.0


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
     private Boolean resultado;

    public EstudioCaso() {
    }

	
    public EstudioCaso(Nna nna, ExpedienteFamilia expedienteFamilia) {
        this.nna = nna;
        this.expedienteFamilia = expedienteFamilia;
    }
    public EstudioCaso(Nna nna, ExpedienteFamilia expedienteFamilia, String orden, Date fechaEstudio, Date fechaSolAdop, Boolean resultado) {
       this.nna = nna;
       this.expedienteFamilia = expedienteFamilia;
       this.orden = orden;
       this.fechaEstudio = fechaEstudio;
       this.fechaSolAdop = fechaSolAdop;
       this.resultado = resultado;
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
    public Boolean getResultado() {
        return this.resultado;
    }
    
    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }




}


