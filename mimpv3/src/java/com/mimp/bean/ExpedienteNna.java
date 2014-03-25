package com.mimp.bean;
// Generated Dec 23, 2013 3:56:21 PM by Hibernate Tools 3.6.0


import java.util.*;
import java.util.HashSet;
import java.util.Set;

/**
 * ExpedienteNna generated by hbm2java
 */
public class ExpedienteNna  implements java.io.Serializable {


     private long idexpedienteNna;
     private Nna nna;
     private Unidad unidad;
     private String numero;
     private Date fechaIngreso;
     private String ht;
     private String NExpTutelar;
     private String procTutelar;
     private Short fichaIntegral;
     private String comentarios;
     private String respLegalNombre;
     private String respLegalP;
     private String respLegalM;
     private String respPsicosocialNombre;
     private String respPiscosocialP;
     private String respPsicosocialM;
     private String estado;
     private Date fechaEstado;
     private Short adoptable;
     private Date fechaResolCons;
     private Short nacional;
     private String diagnostico;
     private String codigoReferencia;
     private String NActual;
     private String apellidopActual;
     private String apellidomActual;
     private String observaciones;
     private Date fechaInvTutelar;
     private Date fechaIngPrio;
     private Date fechaActualizacion;  
     private Set<InformeNna> informeNnas = new HashSet<InformeNna>(0);
    public ExpedienteNna() {
    }

	
    public ExpedienteNna(long idexpedienteNna, Nna nna, Unidad unidad) {
        this.idexpedienteNna = idexpedienteNna;
        this.nna = nna;
        this.unidad = unidad;
    }
    public ExpedienteNna(long idexpedienteNna, Nna nna, Unidad unidad, String numero, Date fechaIngreso, String ht, String NExpTutelar, String procTutelar, Short fichaIntegral, String comentarios, String respLegalNombre, String respLegalP, String respLegalM, String respPsicosocialNombre, String respPiscosocialP, String respPsicosocialM, String estado, Date fechaEstado, Short adoptable, Date fechaResolCons, Short nacional, String diagnostico, String codigoReferencia, String NActual, String apellidopActual, String apellidomActual, String observaciones, Date fechaInvTutelar, Date fechaIngPrio, Date fechaActualizacion,Set<InformeNna> informeNnas) {
       this.idexpedienteNna = idexpedienteNna;
       this.nna = nna;
       this.unidad = unidad;
       this.numero = numero;
       this.fechaIngreso = fechaIngreso;
       this.ht = ht;
       this.NExpTutelar = NExpTutelar;
       this.procTutelar = procTutelar;
       this.fichaIntegral = fichaIntegral;
       this.comentarios = comentarios;
       this.respLegalNombre = respLegalNombre;
       this.respLegalP = respLegalP;
       this.respLegalM = respLegalM;
       this.respPsicosocialNombre = respPsicosocialNombre;
       this.respPiscosocialP = respPiscosocialP;
       this.respPsicosocialM = respPsicosocialM;
       this.estado = estado;
       this.fechaEstado = fechaEstado;
       this.adoptable = adoptable;
       this.fechaResolCons = fechaResolCons;
       this.nacional = nacional;
       this.diagnostico = diagnostico;
       this.codigoReferencia = codigoReferencia;
       this.NActual = NActual;
       this.apellidopActual = apellidopActual;
       this.apellidomActual = apellidomActual;
       this.observaciones = observaciones;
       this.fechaInvTutelar = fechaInvTutelar;
       this.fechaIngPrio = fechaIngPrio;
       this.fechaActualizacion = fechaActualizacion;
       this.informeNnas = informeNnas;
    }
   
    public long getIdexpedienteNna() {
        return this.idexpedienteNna;
    }
    
    public void setIdexpedienteNna(long idexpedienteNna) {
        this.idexpedienteNna = idexpedienteNna;
    }
    public Nna getNna() {
        return this.nna;
    }
    
    public void setNna(Nna nna) {
        this.nna = nna;
    }
    public Unidad getUnidad() {
        return this.unidad;
    }
    
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public String getHt() {
        return this.ht;
    }
    
    public void setHt(String ht) {
        this.ht = ht;
    }
    public String getNExpTutelar() {
        return this.NExpTutelar;
    }
    
    public void setNExpTutelar(String NExpTutelar) {
        this.NExpTutelar = NExpTutelar;
    }
    public String getProcTutelar() {
        return this.procTutelar;
    }
    
    public void setProcTutelar(String procTutelar) {
        this.procTutelar = procTutelar;
    }
    public Short getFichaIntegral() {
        return this.fichaIntegral;
    }
    
    public void setFichaIntegral(Short fichaIntegral) {
        this.fichaIntegral = fichaIntegral;
    }
    public String getComentarios() {
        return this.comentarios;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    public String getRespLegalNombre() {
        return this.respLegalNombre;
    }
    
    public void setRespLegalNombre(String respLegalNombre) {
        this.respLegalNombre = respLegalNombre;
    }
    public String getRespLegalP() {
        return this.respLegalP;
    }
    
    public void setRespLegalP(String respLegalP) {
        this.respLegalP = respLegalP;
    }
    public String getRespLegalM() {
        return this.respLegalM;
    }
    
    public void setRespLegalM(String respLegalM) {
        this.respLegalM = respLegalM;
    }
    public String getRespPsicosocialNombre() {
        return this.respPsicosocialNombre;
    }
    
    public void setRespPsicosocialNombre(String respPsicosocialNombre) {
        this.respPsicosocialNombre = respPsicosocialNombre;
    }
    public String getRespPiscosocialP() {
        return this.respPiscosocialP;
    }
    
    public void setRespPiscosocialP(String respPiscosocialP) {
        this.respPiscosocialP = respPiscosocialP;
    }
    public String getRespPsicosocialM() {
        return this.respPsicosocialM;
    }
    
    public void setRespPsicosocialM(String respPsicosocialM) {
        this.respPsicosocialM = respPsicosocialM;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaEstado() {
        return this.fechaEstado;
    }
    
    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }
    public Short getAdoptable() {
        return this.adoptable;
    }
    
    public void setAdoptable(Short adoptable) {
        this.adoptable = adoptable;
    }
    public Date getFechaResolCons() {
        return this.fechaResolCons;
    }
    
    public void setFechaResolCons(Date fechaResolCons) {
        this.fechaResolCons = fechaResolCons;
    }
    public Short getNacional() {
        return this.nacional;
    }
    
    public void setNacional(Short nacional) {
        this.nacional = nacional;
    }
    public String getDiagnostico() {
        return this.diagnostico;
    }
    
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    public String getCodigoReferencia() {
        return this.codigoReferencia;
    }
    
    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }
    public String getNActual() {
        return this.NActual;
    }
    
    public void setNActual(String NActual) {
        this.NActual = NActual;
    }
    public String getApellidopActual() {
        return this.apellidopActual;
    }
    
    public void setApellidopActual(String apellidopActual) {
        this.apellidopActual = apellidopActual;
    }
    public String getApellidomActual() {
        return this.apellidomActual;
    }
    
    public void setApellidomActual(String apellidomActual) {
        this.apellidomActual = apellidomActual;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Date getFechaInvTutelar() {
        return this.fechaInvTutelar;
    }
    
    public void setFechaInvTutelar(Date fechaInvTutelar) {
        this.fechaInvTutelar = fechaInvTutelar;
    }

    public Date getFechaIngPrio() {
        return this.fechaIngPrio;
    }
    
    public void setFechaIngPrio(Date fechaIngPrio) {
        this.fechaIngPrio = fechaIngPrio;
    }

    public Date getFechaActualizacion() {
        return this.fechaActualizacion;
    }
    
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Set<InformeNna> getInformeNnas() {
        return this.informeNnas;
    }
    
    public void setInformeNnas(Set<InformeNna> informeNnas) {
        this.informeNnas = informeNnas;
    }
}


