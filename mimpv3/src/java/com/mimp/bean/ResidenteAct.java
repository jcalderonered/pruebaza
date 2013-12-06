package com.mimp.bean;
// Generated 6/12/2013 11:57:53 AM by Hibernate Tools 3.6.0



/**
 * ResidenteAct generated by hbm2java
 */
public class ResidenteAct  implements java.io.Serializable {


     private Integer idresidenteAct;
     private InfoFamilia infoFamilia;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private String parentesco;
     private Byte edad;
     private String ocupacion;
     private String estadoSalud;

    public ResidenteAct() {
    }

	
    public ResidenteAct(InfoFamilia infoFamilia) {
        this.infoFamilia = infoFamilia;
    }
    public ResidenteAct(InfoFamilia infoFamilia, String nombre, String apellidoP, String apellidoM, String parentesco, Byte edad, String ocupacion, String estadoSalud) {
       this.infoFamilia = infoFamilia;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.parentesco = parentesco;
       this.edad = edad;
       this.ocupacion = ocupacion;
       this.estadoSalud = estadoSalud;
    }
   
    public Integer getIdresidenteAct() {
        return this.idresidenteAct;
    }
    
    public void setIdresidenteAct(Integer idresidenteAct) {
        this.idresidenteAct = idresidenteAct;
    }
    public InfoFamilia getInfoFamilia() {
        return this.infoFamilia;
    }
    
    public void setInfoFamilia(InfoFamilia infoFamilia) {
        this.infoFamilia = infoFamilia;
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


