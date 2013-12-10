package com.mimp.bean;
// Generated Dec 10, 2013 11:15:19 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Representante generated by hbm2java
 */
public class Representante  implements java.io.Serializable {


     private Integer idrepresentante;
     private String nombre;
     private String apellidoP;
     private String apelldoM;
     private String user;
     private String pass;
     private Date fechaAuto;
     private Date fechaRenov;
     private Date fechaVencAuto;
     private String correo;
     private String direccion;
     private String celular;
     private String telefono;
     private String obs;
     private Set<Organismo> organismos = new HashSet<Organismo>(0);

    public Representante() {
    }

    public Representante(String nombre, String apellidoP, String apelldoM, String user, String pass, Date fechaAuto, Date fechaRenov, Date fechaVencAuto, String correo, String direccion, String celular, String telefono, String obs, Set<Organismo> organismos) {
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apelldoM = apelldoM;
       this.user = user;
       this.pass = pass;
       this.fechaAuto = fechaAuto;
       this.fechaRenov = fechaRenov;
       this.fechaVencAuto = fechaVencAuto;
       this.correo = correo;
       this.direccion = direccion;
       this.celular = celular;
       this.telefono = telefono;
       this.obs = obs;
       this.organismos = organismos;
    }
   
    public Integer getIdrepresentante() {
        return this.idrepresentante;
    }
    
    public void setIdrepresentante(Integer idrepresentante) {
        this.idrepresentante = idrepresentante;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoP() {
        return this.apellidoP;
    }
    
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }
    public String getApelldoM() {
        return this.apelldoM;
    }
    
    public void setApelldoM(String apelldoM) {
        this.apelldoM = apelldoM;
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
    public Date getFechaAuto() {
        return this.fechaAuto;
    }
    
    public void setFechaAuto(Date fechaAuto) {
        this.fechaAuto = fechaAuto;
    }
    public Date getFechaRenov() {
        return this.fechaRenov;
    }
    
    public void setFechaRenov(Date fechaRenov) {
        this.fechaRenov = fechaRenov;
    }
    public Date getFechaVencAuto() {
        return this.fechaVencAuto;
    }
    
    public void setFechaVencAuto(Date fechaVencAuto) {
        this.fechaVencAuto = fechaVencAuto;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getObs() {
        return this.obs;
    }
    
    public void setObs(String obs) {
        this.obs = obs;
    }
    public Set<Organismo> getOrganismos() {
        return this.organismos;
    }
    
    public void setOrganismos(Set<Organismo> organismos) {
        this.organismos = organismos;
    }




}


