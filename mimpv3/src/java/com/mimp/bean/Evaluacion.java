package com.mimp.bean;
// Generated Nov 22, 2013 4:14:42 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Evaluacion generated by hbm2java
 */
public class Evaluacion  implements java.io.Serializable {


     private Integer idevaluacion;
     private ExpedienteFamilia expedienteFamilia;
     private String tipo;
     private Date fechaAsignacion;
     private String responsableNombre;
     private String responsableApellidoP;
     private String responsableApellidoM;
     private String resultado;
     private Date fechaResultado;
     private String observacion;
     private String sustento;
     private Set<EvalLegal> evalLegals = new HashSet<EvalLegal>(0);

    public Evaluacion() {
    }

	
    public Evaluacion(ExpedienteFamilia expedienteFamilia) {
        this.expedienteFamilia = expedienteFamilia;
    }
    public Evaluacion(ExpedienteFamilia expedienteFamilia, String tipo, Date fechaAsignacion, String responsableNombre, String responsableApellidoP, String responsableApellidoM, String resultado, Date fechaResultado, String observacion, String sustento, Set<EvalLegal> evalLegals) {
       this.expedienteFamilia = expedienteFamilia;
       this.tipo = tipo;
       this.fechaAsignacion = fechaAsignacion;
       this.responsableNombre = responsableNombre;
       this.responsableApellidoP = responsableApellidoP;
       this.responsableApellidoM = responsableApellidoM;
       this.resultado = resultado;
       this.fechaResultado = fechaResultado;
       this.observacion = observacion;
       this.sustento = sustento;
       this.evalLegals = evalLegals;
    }
   
    public Integer getIdevaluacion() {
        return this.idevaluacion;
    }
    
    public void setIdevaluacion(Integer idevaluacion) {
        this.idevaluacion = idevaluacion;
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
    public String getResponsableNombre() {
        return this.responsableNombre;
    }
    
    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }
    public String getResponsableApellidoP() {
        return this.responsableApellidoP;
    }
    
    public void setResponsableApellidoP(String responsableApellidoP) {
        this.responsableApellidoP = responsableApellidoP;
    }
    public String getResponsableApellidoM() {
        return this.responsableApellidoM;
    }
    
    public void setResponsableApellidoM(String responsableApellidoM) {
        this.responsableApellidoM = responsableApellidoM;
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
    public Set<EvalLegal> getEvalLegals() {
        return this.evalLegals;
    }
    
    public void setEvalLegals(Set<EvalLegal> evalLegals) {
        this.evalLegals = evalLegals;
    }




}


