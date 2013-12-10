package com.mimp.bean;
// Generated Dec 10, 2013 11:15:19 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Taller generated by hbm2java
 */
public class Taller  implements java.io.Serializable {


     private Integer idtaller;
     private String NSesion;
     private String tipoTaller;
     private String nombre;
     private Boolean habilitado;
     private Byte NReunion;
     private Set<Grupo> grupos = new HashSet<Grupo>(0);

    public Taller() {
    }

    public Taller(String NSesion, String tipoTaller, String nombre, Boolean habilitado, Byte NReunion, Set<Grupo> grupos) {
       this.NSesion = NSesion;
       this.tipoTaller = tipoTaller;
       this.nombre = nombre;
       this.habilitado = habilitado;
       this.NReunion = NReunion;
       this.grupos = grupos;
    }
   
    public Integer getIdtaller() {
        return this.idtaller;
    }
    
    public void setIdtaller(Integer idtaller) {
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
    public Boolean getHabilitado() {
        return this.habilitado;
    }
    
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    public Byte getNReunion() {
        return this.NReunion;
    }
    
    public void setNReunion(Byte NReunion) {
        this.NReunion = NReunion;
    }
    public Set<Grupo> getGrupos() {
        return this.grupos;
    }
    
    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }




}


