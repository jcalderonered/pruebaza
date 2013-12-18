package com.mimp.bean;
// Generated Dec 18, 2013 9:57:16 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Taller generated by hbm2java
 */
public class Taller  implements java.io.Serializable {


     private long idtaller;
     private String NSesion;
     private String tipoTaller;
     private String nombre;
     private Short habilitado;
     private Short NReunion;
     private String unidad;
     private Set<Grupo> grupos = new HashSet<Grupo>(0);

    public Taller() {
    }

	
    public Taller(long idtaller) {
        this.idtaller = idtaller;
    }
    public Taller(long idtaller, String NSesion, String tipoTaller, String nombre, Short habilitado, Short NReunion, String unidad, Set<Grupo> grupos) {
       this.idtaller = idtaller;
       this.NSesion = NSesion;
       this.tipoTaller = tipoTaller;
       this.nombre = nombre;
       this.habilitado = habilitado;
       this.NReunion = NReunion;
       this.unidad = unidad;
       this.grupos = grupos;
    }
   
    public long getIdtaller() {
        return this.idtaller;
    }
    
    public void setIdtaller(long idtaller) {
        this.idtaller = idtaller;
    }
    public String getNSesion() {
        return this.NSesion;
    }
    
    public void setNSesion(String NSesion) {
        this.NSesion = NSesion;
    }
    public String getTipoTaller() {
        return this.tipoTaller;
    }
    
    public void setTipoTaller(String tipoTaller) {
        this.tipoTaller = tipoTaller;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Short getHabilitado() {
        return this.habilitado;
    }
    
    public void setHabilitado(Short habilitado) {
        this.habilitado = habilitado;
    }
    public Short getNReunion() {
        return this.NReunion;
    }
    
    public void setNReunion(Short NReunion) {
        this.NReunion = NReunion;
    }
    public String getUnidad() {
        return this.unidad;
    }
    
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    public Set<Grupo> getGrupos() {
        return this.grupos;
    }
    
    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }




}


