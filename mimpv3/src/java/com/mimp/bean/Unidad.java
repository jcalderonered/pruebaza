package com.mimp.bean;
// Generated Nov 22, 2013 4:14:42 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Unidad generated by hbm2java
 */
public class Unidad  implements java.io.Serializable {


     private Integer idunidad;
     private String nombre;
     private String direccion;
     private String departamento;
     private String provincia;
     private String distrito;
     private String competenciaRegional;
     private String correo;
     private String telefono;
     private String celular;
     private String obs;
     private Set<ExpedienteNna> expedienteNnas = new HashSet<ExpedienteNna>(0);
     private Set<ExpedienteFamilia> expedienteFamilias = new HashSet<ExpedienteFamilia>(0);
     private Set<Personal> personals = new HashSet<Personal>(0);

    public Unidad() {
    }

    public Unidad(String nombre, String direccion, String departamento, String provincia, String distrito, String competenciaRegional, String correo, String telefono, String celular, String obs, Set<ExpedienteNna> expedienteNnas, Set<ExpedienteFamilia> expedienteFamilias, Set<Personal> personals) {
       this.nombre = nombre;
       this.direccion = direccion;
       this.departamento = departamento;
       this.provincia = provincia;
       this.distrito = distrito;
       this.competenciaRegional = competenciaRegional;
       this.correo = correo;
       this.telefono = telefono;
       this.celular = celular;
       this.obs = obs;
       this.expedienteNnas = expedienteNnas;
       this.expedienteFamilias = expedienteFamilias;
       this.personals = personals;
    }
   
    public Integer getIdunidad() {
        return this.idunidad;
    }
    
    public void setIdunidad(Integer idunidad) {
        this.idunidad = idunidad;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    public String getDistrito() {
        return this.distrito;
    }
    
    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
    public String getCompetenciaRegional() {
        return this.competenciaRegional;
    }
    
    public void setCompetenciaRegional(String competenciaRegional) {
        this.competenciaRegional = competenciaRegional;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getObs() {
        return this.obs;
    }
    
    public void setObs(String obs) {
        this.obs = obs;
    }
    public Set<ExpedienteNna> getExpedienteNnas() {
        return this.expedienteNnas;
    }
    
    public void setExpedienteNnas(Set<ExpedienteNna> expedienteNnas) {
        this.expedienteNnas = expedienteNnas;
    }
    public Set<ExpedienteFamilia> getExpedienteFamilias() {
        return this.expedienteFamilias;
    }
    
    public void setExpedienteFamilias(Set<ExpedienteFamilia> expedienteFamilias) {
        this.expedienteFamilias = expedienteFamilias;
    }
    public Set<Personal> getPersonals() {
        return this.personals;
    }
    
    public void setPersonals(Set<Personal> personals) {
        this.personals = personals;
    }




}


