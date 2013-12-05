package com.mimp.bean;
// Generated 5/12/2013 05:37:51 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Turno generated by hbm2java
 */
public class Turno  implements java.io.Serializable {


     private Integer idturno;
     private Sesion sesion;
     private Date inicioInscripcion;
     private Date finInscripcion;
     private Byte vacantes;
     private Set<AsistenciaFT> asistenciaFTs = new HashSet<AsistenciaFT>(0);

    public Turno() {
    }

	
    public Turno(Sesion sesion) {
        this.sesion = sesion;
    }
    public Turno(Sesion sesion, Date inicioInscripcion, Date finInscripcion, Byte vacantes, Set<AsistenciaFT> asistenciaFTs) {
       this.sesion = sesion;
       this.inicioInscripcion = inicioInscripcion;
       this.finInscripcion = finInscripcion;
       this.vacantes = vacantes;
       this.asistenciaFTs = asistenciaFTs;
    }
   
    public Integer getIdturno() {
        return this.idturno;
    }
    
    public void setIdturno(Integer idturno) {
        this.idturno = idturno;
    }
    public Sesion getSesion() {
        return this.sesion;
    }
    
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
    public Date getInicioInscripcion() {
        return this.inicioInscripcion;
    }
    
    public void setInicioInscripcion(Date inicioInscripcion) {
        this.inicioInscripcion = inicioInscripcion;
    }
    public Date getFinInscripcion() {
        return this.finInscripcion;
    }
    
    public void setFinInscripcion(Date finInscripcion) {
        this.finInscripcion = finInscripcion;
    }
    public Byte getVacantes() {
        return this.vacantes;
    }
    
    public void setVacantes(Byte vacantes) {
        this.vacantes = vacantes;
    }
    public Set<AsistenciaFT> getAsistenciaFTs() {
        return this.asistenciaFTs;
    }
    
    public void setAsistenciaFTs(Set<AsistenciaFT> asistenciaFTs) {
        this.asistenciaFTs = asistenciaFTs;
    }




}


