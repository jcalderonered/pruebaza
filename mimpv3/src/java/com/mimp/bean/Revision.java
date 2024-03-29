package com.mimp.bean;
// Generated Dec 23, 2013 3:56:21 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * EstudioCaso generated by hbm2java
 */
public class Revision  implements java.io.Serializable {


     private long idrevision;
     private Nna nna;
     private ExpedienteFamilia expedienteFamilia;
     private String numero;
     private Date fechaRevision;
     private String comentarios;
     private long identidad;
     private String nombre;
     
    public Revision() {
    }

	
    public Revision(long idrevision, Nna nna, ExpedienteFamilia expedienteFamilia) {
        this.idrevision = idrevision;
        this.nna = nna;
        this.expedienteFamilia = expedienteFamilia;
    }
    public Revision(long idrevision, Nna nna, ExpedienteFamilia expedienteFamilia, String numero, Date fechaRevision, String comentarios, long identidad, String nombre) {
       this.idrevision = idrevision;
       this.nna = nna;
       this.expedienteFamilia = expedienteFamilia;
       this.numero = numero;
       this.fechaRevision = fechaRevision;
       this.comentarios = comentarios;
       this.identidad = identidad;
       this.nombre = nombre;
    }
   
    public long getIdrevision() {
        return this.idrevision;
    }
    
    public void setIdrevision(long idrevision) {
        this.idrevision = idrevision;
    }
    public Nna getNna() {
        return this.nna;
    }
    
    public void setNna(Nna nna) {
        this.nna = nna;
    }
    public ExpedienteFamilia getExpedienteFamilia() {
        return this.expedienteFamilia;
    }
    
    public void setExpedienteFamilia(ExpedienteFamilia expedienteFamilia) {
        this.expedienteFamilia = expedienteFamilia;
    }
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getFechaRevision() {
        return this.fechaRevision;
    }
    
    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
    
    public String getComentarios() {
        return this.comentarios;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public long getIdEntidad() {
        return this.identidad;
    }
    
    public void setIdEntidad(long identidad) {
        this.identidad = identidad;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}


