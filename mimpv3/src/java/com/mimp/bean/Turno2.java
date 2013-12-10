package com.mimp.bean;
// Generated 10/12/2013 03:07:34 PM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Turno2 generated by hbm2java
 */
public class Turno2  implements java.io.Serializable {


     private Integer idturno2;
     private Grupo grupo;
     private String nombre;
     private Set<Reunion> reunions = new HashSet<Reunion>(0);

    public Turno2() {
    }

	
    public Turno2(Grupo grupo) {
        this.grupo = grupo;
    }
    public Turno2(Grupo grupo, String nombre, Set<Reunion> reunions) {
       this.grupo = grupo;
       this.nombre = nombre;
       this.reunions = reunions;
    }
   
    public Integer getIdturno2() {
        return this.idturno2;
    }
    
    public void setIdturno2(Integer idturno2) {
        this.idturno2 = idturno2;
    }
    public Grupo getGrupo() {
        return this.grupo;
    }
    
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set<Reunion> getReunions() {
        return this.reunions;
    }
    
    public void setReunions(Set<Reunion> reunions) {
        this.reunions = reunions;
    }




}


