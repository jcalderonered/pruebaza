package com.mimp.bean;
// Generated 29/11/2013 03:12:05 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Evaluacion generated by hbm2java
 */
public class Evaluacion  implements java.io.Serializable {


     private Integer idevaluacion;
     private Personal personal;
     private ExpedienteFamilia expedienteFamilia;
     private String tipo;
     private Date fechaAsignacion;
     private String resultado;
     private Date fechaResultado;
     private String observacion;
     private String sustento;
     private String NDesignacion;
     private Set<Resolucion> resolucions = new HashSet<Resolucion>(0);
     private Set<EvalLegal> evalLegals = new HashSet<EvalLegal>(0);

    public Evaluacion() {
    }

	
    public Evaluacion(Personal personal, ExpedienteFamilia expedienteFamilia) {
        this.personal = personal;
        this.expedienteFamilia = expedienteFamilia;
    }
    public Evaluacion(Personal personal, ExpedienteFamilia expedienteFamilia, String tipo, Date fechaAsignacion, String resultado, Date fechaResultado, String observacion, String sustento, String NDesignacion, Set<Resolucion> resolucions, Set<EvalLegal> evalLegals) {
       this.personal = personal;
       this.expedienteFamilia = expedienteFamilia;
       this.tipo = tipo;
       this.fechaAsignacion = fechaAsignacion;
       this.resultado = resultado;
       this.fechaResultado = fechaResultado;
       this.observacion = observacion;
       this.sustento = sustento;
       this.NDesignacion = NDesignacion;
       this.resolucions = resolucions;
       this.evalLegals = evalLegals;
    }
   
    public Integer getIdevaluacion() {
        return this.idevaluacion;
    }
    
    public void setIdevaluacion(Integer idevaluacion) {
        this.idevaluacion = idevaluacion;
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
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Date getFechaAsignacion() {
        return this.fechaAsignacion;
    }
    
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    public String getResultado() {
        return this.resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public Date getFechaResultado() {
        return this.fechaResultado;
    }
    
    public void setFechaResultado(Date fechaResultado) {
        this.fechaResultado = fechaResultado;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public String getSustento() {
        return this.sustento;
    }
    
    public void setSustento(String sustento) {
        this.sustento = sustento;
    }
    public String getNDesignacion() {
        return this.NDesignacion;
    }
    
    public void setNDesignacion(String NDesignacion) {
        this.NDesignacion = NDesignacion;
    }
    public Set<Resolucion> getResolucions() {
        return this.resolucions;
    }
    
    public void setResolucions(Set<Resolucion> resolucions) {
        this.resolucions = resolucions;
    }
    public Set<EvalLegal> getEvalLegals() {
        return this.evalLegals;
    }
    
    public void setEvalLegals(Set<EvalLegal> evalLegals) {
        this.evalLegals = evalLegals;
    }




}


