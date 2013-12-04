package com.mimp.bean;
// Generated Dec 4, 2013 5:48:18 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PostAdopcion generated by hbm2java
 */
public class PostAdopcion  implements java.io.Serializable {


     private Integer idpostAdopcion;
     private Familia familia;
     private Integer numeroInformes;
     private Date fechaResolucion;
     private Set<InformePostAdoptivo> informePostAdoptivos = new HashSet<InformePostAdoptivo>(0);

    public PostAdopcion() {
    }

	
    public PostAdopcion(Familia familia) {
        this.familia = familia;
    }
    public PostAdopcion(Familia familia, Integer numeroInformes, Date fechaResolucion, Set<InformePostAdoptivo> informePostAdoptivos) {
       this.familia = familia;
       this.numeroInformes = numeroInformes;
       this.fechaResolucion = fechaResolucion;
       this.informePostAdoptivos = informePostAdoptivos;
    }
   
    public Integer getIdpostAdopcion() {
        return this.idpostAdopcion;
    }
    
    public void setIdpostAdopcion(Integer idpostAdopcion) {
        this.idpostAdopcion = idpostAdopcion;
    }
    public Familia getFamilia() {
        return this.familia;
    }
    
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    public Integer getNumeroInformes() {
        return this.numeroInformes;
    }
    
    public void setNumeroInformes(Integer numeroInformes) {
        this.numeroInformes = numeroInformes;
    }
    public Date getFechaResolucion() {
        return this.fechaResolucion;
    }
    
    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
    public Set<InformePostAdoptivo> getInformePostAdoptivos() {
        return this.informePostAdoptivos;
    }
    
    public void setInformePostAdoptivos(Set<InformePostAdoptivo> informePostAdoptivos) {
        this.informePostAdoptivos = informePostAdoptivos;
    }




}


