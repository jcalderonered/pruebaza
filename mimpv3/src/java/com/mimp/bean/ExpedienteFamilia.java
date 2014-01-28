package com.mimp.bean;
// Generated Dec 23, 2013 3:56:21 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ExpedienteFamilia generated by hbm2java
 */
public class ExpedienteFamilia  implements java.io.Serializable {


     private long idexpedienteFamilia;
     private Unidad unidad;
     private Familia familia;
     private Long numero;
     private String expediente;
     private String ht;
     private String numeroExpediente;
     private Date fechaIngresoDga;
     private String htFicha;
     private String nFicha;
     private Date fechaIngresoFicha;
     private String estado;
     private Date tupa;
     private String nacionalidad;
     private Short rnsa;
     private Short rnaa;
     private String tipoFamilia;
     private String tipoListaEspera;
     private Set<Designacion> designacions = new HashSet<Designacion>(0);
     private Set<Evaluacion> evaluacions = new HashSet<Evaluacion>(0);
     private Set<EstudioCaso> estudioCasos = new HashSet<EstudioCaso>(0);

    public ExpedienteFamilia() {
    }

	
    public ExpedienteFamilia(long idexpedienteFamilia, Unidad unidad, Familia familia) {
        this.idexpedienteFamilia = idexpedienteFamilia;
        this.unidad = unidad;
        this.familia = familia;
    }
    public ExpedienteFamilia(long idexpedienteFamilia, Unidad unidad, Familia familia, Long numero, String expediente, String ht, String htFicha,String nFicha,String numeroExpediente, Date fechaIngresoDga, Date fechaIngresoFicha, String estado, Date tupa, String nacionalidad, Short rnsa, Short rnaa, String tipoFamilia, String tipoListaEspera, Set<Designacion> designacions, Set<Evaluacion> evaluacions, Set<EstudioCaso> estudioCasos) {
       this.idexpedienteFamilia = idexpedienteFamilia;
       this.unidad = unidad;
       this.familia = familia;
       this.numero = numero;
       this.expediente = expediente;
       this.ht = ht;
       this.htFicha = htFicha;
       this.nFicha = nFicha;
       this.numeroExpediente = numeroExpediente;
       this.fechaIngresoDga = fechaIngresoDga;
       this.fechaIngresoFicha = fechaIngresoFicha;
       this.estado = estado;
       this.tupa = tupa;
       this.nacionalidad = nacionalidad;
       this.rnsa = rnsa;
       this.rnaa = rnaa;
       this.tipoFamilia = tipoFamilia;
       this.tipoListaEspera = tipoListaEspera;
       this.designacions = designacions;
       this.evaluacions = evaluacions;
       this.estudioCasos = estudioCasos;
    }
   
    public long getIdexpedienteFamilia() {
        return this.idexpedienteFamilia;
    }
    
    public void setIdexpedienteFamilia(long idexpedienteFamilia) {
        this.idexpedienteFamilia = idexpedienteFamilia;
    }
    public Unidad getUnidad() {
        return this.unidad;
    }
    
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    public Familia getFamilia() {
        return this.familia;
    }
    
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    public Long getNumero() {
        return this.numero;
    }
    
    public void setNumero(Long numero) {
        this.numero = numero;
    }
    public String getExpediente() {
        return this.expediente;
    }
    
    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }
    public String getHt() {
        return this.ht;
    }
    
    public void setHt(String ht) {
        this.ht = ht;
    }
    public String getHtFicha() {
        return this.htFicha;
    }
    
    public void setHtFicha(String htFicha) {
        this.htFicha = htFicha;
    }
    public String getnFicha() {
        return this.nFicha;
    }
    
    public void setnFicha(String nFicha) {
        this.nFicha = nFicha;
    }
    public String getNumeroExpediente() {
        return this.numeroExpediente;
    }
    
    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }
    public Date getFechaIngresoDga() {
        return this.fechaIngresoDga;
    }
    
    public void setFechaIngresoDga(Date fechaIngresoDga) {
        this.fechaIngresoDga = fechaIngresoDga;
    }
     public Date getFechaIngresoFicha() {
        return this.fechaIngresoFicha;
    }
    
    public void setFechaIngresoFicha(Date fechaIngresoFicha) {
        this.fechaIngresoFicha = fechaIngresoFicha;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getTupa() {
        return this.tupa;
    }
    
    public void setTupa(Date tupa) {
        this.tupa = tupa;
    }
    public String getNacionalidad() {
        return this.nacionalidad;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public Short getRnsa() {
        return this.rnsa;
    }
    
    public void setRnsa(Short rnsa) {
        this.rnsa = rnsa;
    }
    public Short getRnaa() {
        return this.rnaa;
    }
    
    public void setRnaa(Short rnaa) {
        this.rnaa = rnaa;
    }
    public String getTipoFamilia() {
        return this.tipoFamilia;
    }
    
    public void setTipoFamilia(String tipoFamilia) {
        this.tipoFamilia = tipoFamilia;
    }
    public String getTipoListaEspera() {
        return this.tipoListaEspera;
    }
    
    public void setTipoListaEspera(String tipoListaEspera) {
        this.tipoListaEspera = tipoListaEspera;
    }
    public Set<Designacion> getDesignacions() {
        return this.designacions;
    }
    
    public void setDesignacions(Set<Designacion> designacions) {
        this.designacions = designacions;
    }
    public Set<Evaluacion> getEvaluacions() {
        return this.evaluacions;
    }
    
    public void setEvaluacions(Set<Evaluacion> evaluacions) {
        this.evaluacions = evaluacions;
    }
    public Set<EstudioCaso> getEstudioCasos() {
        return this.estudioCasos;
    }
    
    public void setEstudioCasos(Set<EstudioCaso> estudioCasos) {
        this.estudioCasos = estudioCasos;
    }




}


