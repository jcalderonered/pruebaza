package com.mimp.bean;
// Generated 6/12/2013 11:57:53 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Sesion generated by hbm2java
 */
public class Sesion  implements java.io.Serializable {


     private Integer idsesion;
     private String NSesion;
     private Boolean habilitado;
     private Date fecha;
     private Date hora;
     private String direccion;
     private Byte duracion;
     private String capacitador;
     private Byte asistencia;
     private Set<Turno> turnos = new HashSet<Turno>(0);
     private Set<FormularioSesion> formularioSesions = new HashSet<FormularioSesion>(0);

    public Sesion() {
    }

    public Sesion(String NSesion, Boolean habilitado, Date fecha, Date hora, String direccion, Byte duracion, String capacitador, Byte asistencia, Set<Turno> turnos, Set<FormularioSesion> formularioSesions) {
       this.NSesion = NSesion;
       this.habilitado = habilitado;
       this.fecha = fecha;
       this.hora = hora;
       this.direccion = direccion;
       this.duracion = duracion;
       this.capacitador = capacitador;
       this.asistencia = asistencia;
       this.turnos = turnos;
       this.formularioSesions = formularioSesions;
    }
   
    public Integer getIdsesion() {
        return this.idsesion;
    }
    
    public void setIdsesion(Integer idsesion) {
        this.idsesion = idsesion;
    }
    public String getNSesion() {
        return this.NSesion;
    }
    
    public void setNSesion(String NSesion) {
        this.NSesion = NSesion;
    }
    public Boolean getHabilitado() {
        return this.habilitado;
    }
    
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getHora() {
        return this.hora;
    }
    
    public void setHora(Date hora) {
        this.hora = hora;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Byte getDuracion() {
        return this.duracion;
    }
    
    public void setDuracion(Byte duracion) {
        this.duracion = duracion;
    }
    public String getCapacitador() {
        return this.capacitador;
    }
    
    public void setCapacitador(String capacitador) {
        this.capacitador = capacitador;
    }
    public Byte getAsistencia() {
        return this.asistencia;
    }
    
    public void setAsistencia(Byte asistencia) {
        this.asistencia = asistencia;
    }
    public Set<Turno> getTurnos() {
        return this.turnos;
    }
    
    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }
    public Set<FormularioSesion> getFormularioSesions() {
        return this.formularioSesions;
    }
    
    public void setFormularioSesions(Set<FormularioSesion> formularioSesions) {
        this.formularioSesions = formularioSesions;
    }




}


