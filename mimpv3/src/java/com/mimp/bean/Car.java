package com.mimp.bean;
// Generated Dec 13, 2013 9:53:43 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Car generated by hbm2java
 */
public class Car  implements java.io.Serializable {


     private long idcar;
     private String nombre;
     private String direccion;
     private String departamento;
     private String provincia;
     private String distrito;
     private String director;
     private String contacto;
     private String correo;
     private String fax;
     private String celular;
     private String telefono;
     private String observaciones;
     private Set<Nna> nnas = new HashSet<Nna>(0);

    public Car() {
    }

	
    public Car(long idcar) {
        this.idcar = idcar;
    }
    public Car(long idcar, String nombre, String direccion, String departamento, String provincia, String distrito, String director, String contacto, String correo, String fax, String celular, String telefono, String observaciones, Set<Nna> nnas) {
       this.idcar = idcar;
       this.nombre = nombre;
       this.direccion = direccion;
       this.departamento = departamento;
       this.provincia = provincia;
       this.distrito = distrito;
       this.director = director;
       this.contacto = contacto;
       this.correo = correo;
       this.fax = fax;
       this.celular = celular;
       this.telefono = telefono;
       this.observaciones = observaciones;
       this.nnas = nnas;
    }
   
    public long getIdcar() {
        return this.idcar;
    }
    
    public void setIdcar(long idcar) {
        this.idcar = idcar;
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
    public String getDirector() {
        return this.director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    public String getContacto() {
        return this.contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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


