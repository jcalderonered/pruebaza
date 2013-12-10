package com.mimp.bean;
// Generated 10/12/2013 03:07:34 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Representante generated by hbm2java
 */
public class Representante  implements java.io.Serializable {


     private Integer idrepresentante;
     private Organismo organismo;
     private String nombre;
     private String apellidoP;
     private String apelldoM;
     private Date fechaAuto;
     private Date fechaRenov;
     private Date fechaVencAuto;
     private String correo;
     private String direccion;
     private String celular;
     private String telefono;
     private String obs;

    public Representante() {
    }

	
    public Representante(Organismo organismo) {
        this.organismo = organismo;
    }
    public Representante(Organismo organismo, String nombre, String apellidoP, String apelldoM, Date fechaAuto, Date fechaRenov, Date fechaVencAuto, String correo, String direccion, String celular, String telefono, String obs) {
       this.organismo = organismo;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apelldoM = apelldoM;
       this.fechaAuto = fechaAuto;
       this.fechaRenov = fechaRenov;
       this.fechaVencAuto = fechaVencAuto;
       this.correo = correo;
       this.direccion = direccion;
       this.celular = celular;
       this.telefono = telefono;
       this.obs = obs;
    }
   
    public Integer getIdrepresentante() {
        return this.idrepresentante;
    }
    
    public void setIdrepresentante(Integer idrepresentante) {
        this.idrepresentante = idrepresentante;
    }
    public Organismo getOrganismo() {
        return this.organismo;
    }
    
    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
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




}


