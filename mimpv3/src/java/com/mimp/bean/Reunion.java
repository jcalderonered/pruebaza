package com.mimp.bean;
// Generated Dec 4, 2013 5:48:18 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Reunion generated by hbm2java
 */
public class Reunion  implements java.io.Serializable {


     private Integer idreunion;
     private Turno2 turno2;
     private Date fecha;
     private Date hora;
     private Byte duracion;
     private String direccion;
     private Byte identificador;
     private String facilitador;
     private Byte capacidad;
     private Byte asistencia;
     private Set<AsistenciaFR> asistenciaFRs = new HashSet<AsistenciaFR>(0);

    public Reunion() {
    }

	
    public Reunion(Turno2 turno2) {
        this.turno2 = turno2;
    }
    public Reunion(Turno2 turno2, Date fecha, Date hora, Byte duracion, String direccion, Byte identificador, String facilitador, Byte capacidad, Byte asistencia, Set<AsistenciaFR> asistenciaFRs) {
       this.turno2 = turno2;
       this.fecha = fecha;
       this.hora = hora;
       this.duracion = duracion;
       this.direccion = direccion;
       this.identificador = identificador;
       this.facilitador = facilitador;
       this.capacidad = capacidad;
       this.asistencia = asistencia;
       this.asistenciaFRs = asistenciaFRs;
    }
   
    public Integer getIdreunion() {
        return this.idreunion;
    }
    
    public void setIdreunion(Integer idreunion) {
        this.idreunion = idreunion;
    }
    public Turno2 getTurno2() {
        return this.turno2;
    }
    
    public void setTurno2(Turno2 turno2) {
        this.turno2 = turno2;
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
    public Byte getDuracion() {
        return this.duracion;
    }
    
    public void setDuracion(Byte duracion) {
        this.duracion = duracion;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Byte getIdentificador() {
        return this.identificador;
    }
    
    public void setIdentificador(Byte identificador) {
        this.identificador = identificador;
    }
    public String getFacilitador() {
        return this.facilitador;
    }
    
    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }
    public Byte getCapacidad() {
        return this.capacidad;
    }
    
    public void setCapacidad(Byte capacidad) {
        this.capacidad = capacidad;
    }
    public Byte getAsistencia() {
        return this.asistencia;
    }
    
    public void setAsistencia(Byte asistencia) {
        this.asistencia = asistencia;
    }
    public Set<AsistenciaFR> getAsistenciaFRs() {
        return this.asistenciaFRs;
    }
    
    public void setAsistenciaFRs(Set<AsistenciaFR> asistenciaFRs) {
        this.asistenciaFRs = asistenciaFRs;
    }




}


