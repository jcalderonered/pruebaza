package com.mimp.bean;
// Generated 10/12/2013 02:25:47 PM by Hibernate Tools 3.6.0



/**
 * Residente generated by hbm2java
 */
public class Residente  implements java.io.Serializable {


     private Integer idresidente;
     private FichaSolicitudAdopcion fichaSolicitudAdopcion;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private String parentesco;
     private Byte edad;
     private String ocupacion;
     private String estadoSalud;

    public Residente() {
    }

	
    public Residente(FichaSolicitudAdopcion fichaSolicitudAdopcion) {
        this.fichaSolicitudAdopcion = fichaSolicitudAdopcion;
    }
    public Residente(FichaSolicitudAdopcion fichaSolicitudAdopcion, String nombre, String apellidoP, String apellidoM, String parentesco, Byte edad, String ocupacion, String estadoSalud) {
       this.fichaSolicitudAdopcion = fichaSolicitudAdopcion;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.parentesco = parentesco;
       this.edad = edad;
       this.ocupacion = ocupacion;
       this.estadoSalud = estadoSalud;
    }
   
    public Integer getIdresidente() {
        return this.idresidente;
    }
    
    public void setIdresidente(Integer idresidente) {
        this.idresidente = idresidente;
    }
    public FichaSolicitudAdopcion getFichaSolicitudAdopcion() {
        return this.fichaSolicitudAdopcion;
    }
    
    public void setFichaSolicitudAdopcion(FichaSolicitudAdopcion fichaSolicitudAdopcion) {
        this.fichaSolicitudAdopcion = fichaSolicitudAdopcion;
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
    public String getParentesco() {
        return this.parentesco;
    }
    
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    public Byte getEdad() {
        return this.edad;
    }
    
    public void setEdad(Byte edad) {
        this.edad = edad;
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




}


