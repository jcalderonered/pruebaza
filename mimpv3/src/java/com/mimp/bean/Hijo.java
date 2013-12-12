package com.mimp.bean;
// Generated Dec 12, 2013 4:23:07 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Hijo generated by hbm2java
 */
public class Hijo  implements java.io.Serializable {


     private long idhijo;
     private FichaSolicitudAdopcion fichaSolicitudAdopcion;
     private Date fechaNac;
     private Short biologico;
     private Date fechaAdop;
     private Short edad;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private String ocupacion;
     private String estadoSalud;
     private Short reside;

    public Hijo() {
    }

	
    public Hijo(long idhijo, FichaSolicitudAdopcion fichaSolicitudAdopcion) {
        this.idhijo = idhijo;
        this.fichaSolicitudAdopcion = fichaSolicitudAdopcion;
    }
    public Hijo(long idhijo, FichaSolicitudAdopcion fichaSolicitudAdopcion, Date fechaNac, Short biologico, Date fechaAdop, Short edad, String nombre, String apellidoP, String apellidoM, String ocupacion, String estadoSalud, Short reside) {
       this.idhijo = idhijo;
       this.fichaSolicitudAdopcion = fichaSolicitudAdopcion;
       this.fechaNac = fechaNac;
       this.biologico = biologico;
       this.fechaAdop = fechaAdop;
       this.edad = edad;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.ocupacion = ocupacion;
       this.estadoSalud = estadoSalud;
       this.reside = reside;
    }
   
    public long getIdhijo() {
        return this.idhijo;
    }
    
    public void setIdhijo(long idhijo) {
        this.idhijo = idhijo;
    }
    public FichaSolicitudAdopcion getFichaSolicitudAdopcion() {
        return this.fichaSolicitudAdopcion;
    }
    
    public void setFichaSolicitudAdopcion(FichaSolicitudAdopcion fichaSolicitudAdopcion) {
        this.fichaSolicitudAdopcion = fichaSolicitudAdopcion;
    }
    public Date getFechaNac() {
        return this.fechaNac;
    }
    
    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
    public Short getBiologico() {
        return this.biologico;
    }
    
    public void setBiologico(Short biologico) {
        this.biologico = biologico;
    }
    public Date getFechaAdop() {
        return this.fechaAdop;
    }
    
    public void setFechaAdop(Date fechaAdop) {
        this.fechaAdop = fechaAdop;
    }
    public Short getEdad() {
        return this.edad;
    }
    
    public void setEdad(Short edad) {
        this.edad = edad;
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
    public String getOcupacion() {
        return this.ocupacion;
    }
    
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
    public String getEstadoSalud() {
        return this.estadoSalud;
    }
    
    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }
    public Short getReside() {
        return this.reside;
    }
    
    public void setReside(Short reside) {
        this.reside = reside;
    }




}


