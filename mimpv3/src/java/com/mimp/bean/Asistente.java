package com.mimp.bean;
// Generated Dec 23, 2013 10:39:27 AM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Asistente generated by hbm2java
 */
public class Asistente  implements java.io.Serializable {


     private long idasistente;
     private FormularioSesion formularioSesion;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private Character sexo;
     private String paisNac;
     private String depNac;
     private String provNac;
     private Short edad;
     private Date fechaNac;
     private Character tipoDoc;
     private String NDoc;
     private String profesion;
     private String celular;
     private String correo;

    public Asistente() {
    }

	
    public Asistente(long idasistente, FormularioSesion formularioSesion) {
        this.idasistente = idasistente;
        this.formularioSesion = formularioSesion;
    }
    public Asistente(long idasistente, FormularioSesion formularioSesion, String nombre, String apellidoP, String apellidoM, Character sexo, String paisNac, String depNac, String provNac, Short edad, Date fechaNac, Character tipoDoc, String NDoc, String profesion, String celular, String correo) {
       this.idasistente = idasistente;
       this.formularioSesion = formularioSesion;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.sexo = sexo;
       this.paisNac = paisNac;
       this.depNac = depNac;
       this.provNac = provNac;
       this.edad = edad;
       this.fechaNac = fechaNac;
       this.tipoDoc = tipoDoc;
       this.NDoc = NDoc;
       this.profesion = profesion;
       this.celular = celular;
       this.correo = correo;
    }
   
    public long getIdasistente() {
        return this.idasistente;
    }
    
    public void setIdasistente(long idasistente) {
        this.idasistente = idasistente;
    }
    public FormularioSesion getFormularioSesion() {
        return this.formularioSesion;
    }
    
    public void setFormularioSesion(FormularioSesion formularioSesion) {
        this.formularioSesion = formularioSesion;
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
    public String getApellidoM() {
        return this.apellidoM;
    }
    
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }
    public Character getSexo() {
        return this.sexo;
    }
    
    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }
    public String getPaisNac() {
        return this.paisNac;
    }
    
    public void setPaisNac(String paisNac) {
        this.paisNac = paisNac;
    }
    public String getDepNac() {
        return this.depNac;
    }
    
    public void setDepNac(String depNac) {
        this.depNac = depNac;
    }
    public String getProvNac() {
        return this.provNac;
    }
    
    public void setProvNac(String provNac) {
        this.provNac = provNac;
    }
    public Short getEdad() {
        return this.edad;
    }
    
    public void setEdad(Short edad) {
        this.edad = edad;
    }
    public Date getFechaNac() {
        return this.fechaNac;
    }
    
    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
    public Character getTipoDoc() {
        return this.tipoDoc;
    }
    
    public void setTipoDoc(Character tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    public String getNDoc() {
        return this.NDoc;
    }
    
    public void setNDoc(String NDoc) {
        this.NDoc = NDoc;
    }
    public String getProfesion() {
        return this.profesion;
    }
    
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }




}


