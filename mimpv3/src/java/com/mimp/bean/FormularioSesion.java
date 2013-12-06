package com.mimp.bean;
// Generated 6/12/2013 11:57:53 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FormularioSesion generated by hbm2java
 */
public class FormularioSesion  implements java.io.Serializable {


     private Integer idformularioSesion;
     private Familia familia;
     private Sesion sesion;
     private Date fechaSol;
     private String paisRes;
     private String depRes;
     private String provRes;
     private String distritoRes;
     private String direccionRes;
     private String estadoCivil;
     private String telefono;
     private Set<AsistenciaFT> asistenciaFTs = new HashSet<AsistenciaFT>(0);
     private Set<Asistente> asistentes = new HashSet<Asistente>(0);

    public FormularioSesion() {
    }

	
    public FormularioSesion(Familia familia, Sesion sesion) {
        this.familia = familia;
        this.sesion = sesion;
    }
    public FormularioSesion(Familia familia, Sesion sesion, Date fechaSol, String paisRes, String depRes, String provRes, String distritoRes, String direccionRes, String estadoCivil, String telefono, Set<AsistenciaFT> asistenciaFTs, Set<Asistente> asistentes) {
       this.familia = familia;
       this.sesion = sesion;
       this.fechaSol = fechaSol;
       this.paisRes = paisRes;
       this.depRes = depRes;
       this.provRes = provRes;
       this.distritoRes = distritoRes;
       this.direccionRes = direccionRes;
       this.estadoCivil = estadoCivil;
       this.telefono = telefono;
       this.asistenciaFTs = asistenciaFTs;
       this.asistentes = asistentes;
    }
   
    public Integer getIdformularioSesion() {
        return this.idformularioSesion;
    }
    
    public void setIdformularioSesion(Integer idformularioSesion) {
        this.idformularioSesion = idformularioSesion;
    }
    public Familia getFamilia() {
        return this.familia;
    }
    
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    public Sesion getSesion() {
        return this.sesion;
    }
    
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
    public Date getFechaSol() {
        return this.fechaSol;
    }
    
    public void setFechaSol(Date fechaSol) {
        this.fechaSol = fechaSol;
    }
    public String getPaisRes() {
        return this.paisRes;
    }
    
    public void setPaisRes(String paisRes) {
        this.paisRes = paisRes;
    }
    public String getDepRes() {
        return this.depRes;
    }
    
    public void setDepRes(String depRes) {
        this.depRes = depRes;
    }
    public String getProvRes() {
        return this.provRes;
    }
    
    public void setProvRes(String provRes) {
        this.provRes = provRes;
    }
    public String getDistritoRes() {
        return this.distritoRes;
    }
    
    public void setDistritoRes(String distritoRes) {
        this.distritoRes = distritoRes;
    }
    public String getDireccionRes() {
        return this.direccionRes;
    }
    
    public void setDireccionRes(String direccionRes) {
        this.direccionRes = direccionRes;
    }
    public String getEstadoCivil() {
        return this.estadoCivil;
    }
    
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Set<AsistenciaFT> getAsistenciaFTs() {
        return this.asistenciaFTs;
    }
    
    public void setAsistenciaFTs(Set<AsistenciaFT> asistenciaFTs) {
        this.asistenciaFTs = asistenciaFTs;
    }
    public Set<Asistente> getAsistentes() {
        return this.asistentes;
    }
    
    public void setAsistentes(Set<Asistente> asistentes) {
        this.asistentes = asistentes;
    }




}


