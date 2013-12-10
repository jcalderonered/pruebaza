package com.mimp.bean;
// Generated 10/12/2013 02:25:47 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Familia generated by hbm2java
 */
public class Familia  implements java.io.Serializable {


     private Integer idfamilia;
     private Entidad entidad;
     private String user;
     private String pass;
     private String correo;
     private Boolean habilitado;
     private String constancia;
     private Set<ExpedienteFamilia> expedienteFamilias = new HashSet<ExpedienteFamilia>(0);
     private Set<FichaSolicitudAdopcion> fichaSolicitudAdopcions = new HashSet<FichaSolicitudAdopcion>(0);
     private Set<PostAdopcion> postAdopcions = new HashSet<PostAdopcion>(0);
     private Set<FormularioSesion> formularioSesions = new HashSet<FormularioSesion>(0);
     private Set<Atencion> atencions = new HashSet<Atencion>(0);
     private Set<InfoFamilia> infoFamilias = new HashSet<InfoFamilia>(0);
     private Set<AsistenciaFR> asistenciaFRs = new HashSet<AsistenciaFR>(0);

    public Familia() {
    }

    public Familia(Entidad entidad, String user, String pass, String correo, Boolean habilitado, String constancia, Set<ExpedienteFamilia> expedienteFamilias, Set<FichaSolicitudAdopcion> fichaSolicitudAdopcions, Set<PostAdopcion> postAdopcions, Set<FormularioSesion> formularioSesions, Set<Atencion> atencions, Set<InfoFamilia> infoFamilias, Set<AsistenciaFR> asistenciaFRs) {
       this.entidad = entidad;
       this.user = user;
       this.pass = pass;
       this.correo = correo;
       this.habilitado = habilitado;
       this.constancia = constancia;
       this.expedienteFamilias = expedienteFamilias;
       this.fichaSolicitudAdopcions = fichaSolicitudAdopcions;
       this.postAdopcions = postAdopcions;
       this.formularioSesions = formularioSesions;
       this.atencions = atencions;
       this.infoFamilias = infoFamilias;
       this.asistenciaFRs = asistenciaFRs;
    }
   
    public Integer getIdfamilia() {
        return this.idfamilia;
    }
    
    public void setIdfamilia(Integer idfamilia) {
        this.idfamilia = idfamilia;
    }
    public Entidad getEntidad() {
        return this.entidad;
    }
    
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    public String getUser() {
        return this.user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Boolean getHabilitado() {
        return this.habilitado;
    }
    
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    public String getConstancia() {
        return this.constancia;
    }
    
    public void setConstancia(String constancia) {
        this.constancia = constancia;
    }
    public Set<ExpedienteFamilia> getExpedienteFamilias() {
        return this.expedienteFamilias;
    }
    
    public void setExpedienteFamilias(Set<ExpedienteFamilia> expedienteFamilias) {
        this.expedienteFamilias = expedienteFamilias;
    }
    public Set<FichaSolicitudAdopcion> getFichaSolicitudAdopcions() {
        return this.fichaSolicitudAdopcions;
    }
    
    public void setFichaSolicitudAdopcions(Set<FichaSolicitudAdopcion> fichaSolicitudAdopcions) {
        this.fichaSolicitudAdopcions = fichaSolicitudAdopcions;
    }
    public Set<PostAdopcion> getPostAdopcions() {
        return this.postAdopcions;
    }
    
    public void setPostAdopcions(Set<PostAdopcion> postAdopcions) {
        this.postAdopcions = postAdopcions;
    }
    public Set<FormularioSesion> getFormularioSesions() {
        return this.formularioSesions;
    }
    
    public void setFormularioSesions(Set<FormularioSesion> formularioSesions) {
        this.formularioSesions = formularioSesions;
    }
    public Set<Atencion> getAtencions() {
        return this.atencions;
    }
    
    public void setAtencions(Set<Atencion> atencions) {
        this.atencions = atencions;
    }
    public Set<InfoFamilia> getInfoFamilias() {
        return this.infoFamilias;
    }
    
    public void setInfoFamilias(Set<InfoFamilia> infoFamilias) {
        this.infoFamilias = infoFamilias;
    }
    public Set<AsistenciaFR> getAsistenciaFRs() {
        return this.asistenciaFRs;
    }
    
    public void setAsistenciaFRs(Set<AsistenciaFR> asistenciaFRs) {
        this.asistenciaFRs = asistenciaFRs;
    }




}


