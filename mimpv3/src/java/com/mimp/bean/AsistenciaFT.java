package com.mimp.bean;
// Generated 10/12/2013 03:07:34 PM by Hibernate Tools 3.6.0



/**
 * AsistenciaFT generated by hbm2java
 */
public class AsistenciaFT  implements java.io.Serializable {


     private Integer idasistenciaFT;
     private FormularioSesion formularioSesion;
     private Turno turno;
     private Character asistencia;
     private Boolean inasJus;

    public AsistenciaFT() {
    }

	
    public AsistenciaFT(FormularioSesion formularioSesion, Turno turno) {
        this.formularioSesion = formularioSesion;
        this.turno = turno;
    }
    public AsistenciaFT(FormularioSesion formularioSesion, Turno turno, Character asistencia, Boolean inasJus) {
       this.formularioSesion = formularioSesion;
       this.turno = turno;
       this.asistencia = asistencia;
       this.inasJus = inasJus;
    }
   
    public Integer getIdasistenciaFT() {
        return this.idasistenciaFT;
    }
    
    public void setIdasistenciaFT(Integer idasistenciaFT) {
        this.idasistenciaFT = idasistenciaFT;
    }
    public FormularioSesion getFormularioSesion() {
        return this.formularioSesion;
    }
    
    public void setFormularioSesion(FormularioSesion formularioSesion) {
        this.formularioSesion = formularioSesion;
    }
    public Turno getTurno() {
        return this.turno;
    }
    
    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    public Character getAsistencia() {
        return this.asistencia;
    }
    
    public void setAsistencia(Character asistencia) {
        this.asistencia = asistencia;
    }
    public Boolean getInasJus() {
        return this.inasJus;
    }
    
    public void setInasJus(Boolean inasJus) {
        this.inasJus = inasJus;
    }




}


