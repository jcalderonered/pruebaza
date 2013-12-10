package com.mimp.bean;
// Generated 10/12/2013 03:07:34 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad generated by hbm2java
 */
public class Entidad  implements java.io.Serializable {


     private Integer identidad;
     private String nombre;
     private String user;
     private String pass;
     private String direccion;
     private String telefono;
     private String pais;
     private String resolAuto;
     private Date fechaResol;
     private String resolRenov;
     private Date fechaRenov;
     private Date fechaVenc;
     private String obs;
     private Set<Familia> familias = new HashSet<Familia>(0);
     private Set<Organismo> organismos = new HashSet<Organismo>(0);
     private Set<Autoridad> autoridads = new HashSet<Autoridad>(0);

    public Entidad() {
    }

    public Entidad(String nombre, String user, String pass, String direccion, String telefono, String pais, String resolAuto, Date fechaResol, String resolRenov, Date fechaRenov, Date fechaVenc, String obs, Set<Familia> familias, Set<Organismo> organismos, Set<Autoridad> autoridads) {
       this.nombre = nombre;
       this.user = user;
       this.pass = pass;
       this.direccion = direccion;
       this.telefono = telefono;
       this.pais = pais;
       this.resolAuto = resolAuto;
       this.fechaResol = fechaResol;
       this.resolRenov = resolRenov;
       this.fechaRenov = fechaRenov;
       this.fechaVenc = fechaVenc;
       this.obs = obs;
       this.familias = familias;
       this.organismos = organismos;
       this.autoridads = autoridads;
    }
   
    public Integer getIdentidad() {
        return this.identidad;
    }
    
    public void setIdentidad(Integer identidad) {
        this.identidad = identidad;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getPais() {
        return this.pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getResolAuto() {
        return this.resolAuto;
    }
    
    public void setResolAuto(String resolAuto) {
        this.resolAuto = resolAuto;
    }
    public Date getFechaResol() {
        return this.fechaResol;
    }
    
    public void setFechaResol(Date fechaResol) {
        this.fechaResol = fechaResol;
    }
    public String getResolRenov() {
        return this.resolRenov;
    }
    
    public void setResolRenov(String resolRenov) {
        this.resolRenov = resolRenov;
    }
    public Date getFechaRenov() {
        return this.fechaRenov;
    }
    
    public void setFechaRenov(Date fechaRenov) {
        this.fechaRenov = fechaRenov;
    }
    public Date getFechaVenc() {
        return this.fechaVenc;
    }
    
    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }
    public String getObs() {
        return this.obs;
    }
    
    public void setObs(String obs) {
        this.obs = obs;
    }
    public Set<Familia> getFamilias() {
        return this.familias;
    }
    
    public void setFamilias(Set<Familia> familias) {
        this.familias = familias;
    }
    public Set<Organismo> getOrganismos() {
        return this.organismos;
    }
    
    public void setOrganismos(Set<Organismo> organismos) {
        this.organismos = organismos;
    }
    public Set<Autoridad> getAutoridads() {
        return this.autoridads;
    }
    
    public void setAutoridads(Set<Autoridad> autoridads) {
        this.autoridads = autoridads;
    }




}


