package com.mimp.bean;
// Generated Dec 18, 2013 9:57:16 AM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PostAdopcion generated by hbm2java
 */
public class PostAdopcion  implements java.io.Serializable {


     private long idpostAdopcion;
     private Familia familia;
     private Long numeroInformes;
     private Date fechaResolucion;
     private Set<InformePostAdoptivo> informePostAdoptivos = new HashSet<InformePostAdoptivo>(0);

    public PostAdopcion() {
    }

	
    public PostAdopcion(long idpostAdopcion, Familia familia) {
        this.idpostAdopcion = idpostAdopcion;
        this.familia = familia;
    }
    public PostAdopcion(long idpostAdopcion, Familia familia, Long numeroInformes, Date fechaResolucion, Set<InformePostAdoptivo> informePostAdoptivos) {
       this.idpostAdopcion = idpostAdopcion;
       this.familia = familia;
       this.numeroInformes = numeroInformes;
       this.fechaResolucion = fechaResolucion;
       this.informePostAdoptivos = informePostAdoptivos;
    }
   
    public long getIdpostAdopcion() {
        return this.idpostAdopcion;
    }
    
    public void setIdpostAdopcion(long idpostAdopcion) {
        this.idpostAdopcion = idpostAdopcion;
    }
    public Familia getFamilia() {
        return this.familia;
    }
    
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    public Long getNumeroInformes() {
        return this.numeroInformes;
    }
    
    public void setNumeroInformes(Long numeroInformes) {
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


