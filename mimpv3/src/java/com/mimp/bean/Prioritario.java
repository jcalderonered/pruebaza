package com.mimp.bean;
// Generated 10/12/2013 02:25:47 PM by Hibernate Tools 3.6.0



/**
 * Prioritario generated by hbm2java
 */
public class Prioritario  implements java.io.Serializable {


     private Integer idprioritario;
     private Nna nna;
     private String codMayor;
     private String codAdolescente;
     private String codHermano;
     private String codSalud;
     private String codSeguimiento;
     private String diagnostico;
     private String grupoReferencia;
     private String profesional;
     private String comentario;

    public Prioritario() {
    }

	
    public Prioritario(Nna nna) {
        this.nna = nna;
    }
    public Prioritario(Nna nna, String codMayor, String codAdolescente, String codHermano, String codSalud, String codSeguimiento, String diagnostico, String grupoReferencia, String profesional, String comentario) {
       this.nna = nna;
       this.codMayor = codMayor;
       this.codAdolescente = codAdolescente;
       this.codHermano = codHermano;
       this.codSalud = codSalud;
       this.codSeguimiento = codSeguimiento;
       this.diagnostico = diagnostico;
       this.grupoReferencia = grupoReferencia;
       this.profesional = profesional;
       this.comentario = comentario;
    }
   
    public Integer getIdprioritario() {
        return this.idprioritario;
    }
    
    public void setIdprioritario(Integer idprioritario) {
        this.idprioritario = idprioritario;
    }
    public Nna getNna() {
        return this.nna;
    }
    
    public void setNna(Nna nna) {
        this.nna = nna;
    }
    public String getCodMayor() {
        return this.codMayor;
    }
    
    public void setCodMayor(String codMayor) {
        this.codMayor = codMayor;
    }
    public String getCodAdolescente() {
        return this.codAdolescente;
    }
    
    public void setCodAdolescente(String codAdolescente) {
        this.codAdolescente = codAdolescente;
    }
    public String getCodHermano() {
        return this.codHermano;
    }
    
    public void setCodHermano(String codHermano) {
        this.codHermano = codHermano;
    }
    public String getCodSalud() {
        return this.codSalud;
    }
    
    public void setCodSalud(String codSalud) {
        this.codSalud = codSalud;
    }
    public String getCodSeguimiento() {
        return this.codSeguimiento;
    }
    
    public void setCodSeguimiento(String codSeguimiento) {
        this.codSeguimiento = codSeguimiento;
    }
    public String getDiagnostico() {
        return this.diagnostico;
    }
    
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    public String getGrupoReferencia() {
        return this.grupoReferencia;
    }
    
    public void setGrupoReferencia(String grupoReferencia) {
        this.grupoReferencia = grupoReferencia;
    }
    public String getProfesional() {
        return this.profesional;
    }
    
    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }
    public String getComentario() {
        return this.comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }




}


