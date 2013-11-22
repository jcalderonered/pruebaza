package com.mimp.bean;
// Generated Nov 22, 2013 4:14:42 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Asistente generated by hbm2java
 */
public class Asistente  implements java.io.Serializable {


     private Integer idasistente;
     private FormularioSesion formularioSesion;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private String paisNac;
     private String depNac;
     private String provNac;
     private Byte edad;
     private Date fechaNac;
     private Character tipoDoc;
     private String NDoc;
     private String profesion;
     private String celular;
     private String correo;

    public Asistente() {
    }

	
    public Asistente(FormularioSesion formularioSesion) {
        this.formularioSesion = formularioSesion;
    }
    public Asistente(FormularioSesion formularioSesion, String nombre, String apellidoP, String apellidoM, String paisNac, String depNac, String provNac, Byte edad, Date fechaNac, Character tipoDoc, String NDoc, String profesion, String celular, String correo) {
       this.formularioSesion = formularioSesion;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
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
   
    public Integer getIdasistente() {
        return this.idasistente;
    }
    
    public void setIdasistente(Integer idasistente) {
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
    public Byte getEdad() {
        return this.edad;
    }
    
    public void setEdad(Byte edad) {
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


