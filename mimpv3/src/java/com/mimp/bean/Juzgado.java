package com.mimp.bean;
// Generated Dec 23, 2013 10:39:27 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Juzgado generated by hbm2java
 */
public class Juzgado  implements java.io.Serializable {


     private long idjuzgado;
     private String nombre;
     private String denominacion;
     private String especialidad;
     private String direccion;
     private String departamento;
     private String corteSuperior;
     private String distritoJudicial;
     private String nombreJuez;
     private String telefono;
     private String correo;
     private String observaciones;
     private Set<Nna> nnas = new HashSet<Nna>(0);

    public Juzgado() {
    }

	
    public Juzgado(long idjuzgado) {
        this.idjuzgado = idjuzgado;
    }
    public Juzgado(long idjuzgado, String nombre, String denominacion, String especialidad, String direccion, String departamento, String corteSuperior, String distritoJudicial, String nombreJuez, String telefono, String correo, String observaciones, Set<Nna> nnas) {
       this.idjuzgado = idjuzgado;
       this.nombre = nombre;
       this.denominacion = denominacion;
       this.especialidad = especialidad;
       this.direccion = direccion;
       this.departamento = departamento;
       this.corteSuperior = corteSuperior;
       this.distritoJudicial = distritoJudicial;
       this.nombreJuez = nombreJuez;
       this.telefono = telefono;
       this.correo = correo;
       this.observaciones = observaciones;
       this.nnas = nnas;
    }
   
    public long getIdjuzgado() {
        return this.idjuzgado;
    }
    
    public void setIdjuzgado(long idjuzgado) {
        this.idjuzgado = idjuzgado;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDenominacion() {
        return this.denominacion;
    }
    
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    public String getEspecialidad() {
        return this.especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getDepartamento() {
        return this.departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public String getCorteSuperior() {
        return this.corteSuperior;
    }
    
    public void setCorteSuperior(String corteSuperior) {
        this.corteSuperior = corteSuperior;
    }
    public String getDistritoJudicial() {
        return this.distritoJudicial;
    }
    
    public void setDistritoJudicial(String distritoJudicial) {
        this.distritoJudicial = distritoJudicial;
    }
    public String getNombreJuez() {
        return this.nombreJuez;
    }
    
    public void setNombreJuez(String nombreJuez) {
        this.nombreJuez = nombreJuez;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Set<Nna> getNnas() {
        return this.nnas;
    }
    
    public void setNnas(Set<Nna> nnas) {
        this.nnas = nnas;
    }




}


