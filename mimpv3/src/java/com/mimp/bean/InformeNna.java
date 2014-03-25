/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.bean;
import java.util.Date;
/**
 *
 * @author john
 */
public class InformeNna implements java.io.Serializable {
    
     private long idinformeNna;
     private ExpedienteNna expedienteNna;
     private String numero;
     private Date fecha;
     private String resultado;
     private String observaciones;
     
     public InformeNna(){
     
    }
     
     public InformeNna(long idinformeNna, ExpedienteNna expedienteNna){
         this.idinformeNna = idinformeNna;
         this.expedienteNna = expedienteNna;
    }
     
     public InformeNna(long idinformeNna,ExpedienteNna expedienteNna, String numero,Date fecha, String resultado,String observaciones){
         this.idinformeNna = idinformeNna;
         this.expedienteNna = expedienteNna;
         this.numero = numero;
         this.fecha = fecha;
         this.resultado = resultado;
         this.observaciones = observaciones;
         
     }

    
    public long getIdinformeNna() {
        return idinformeNna;
    }

    
    public void setIdinformeNna(long idinformeNna) {
        this.idinformeNna = idinformeNna;
    }

    public ExpedienteNna getExpedienteNna() {
        return expedienteNna;
    }

    
    public void setExpedienteNna(ExpedienteNna expedienteNna) {
        this.expedienteNna = expedienteNna;
    }

    
    public String getNumero() {
        return numero;
    }

    
    public void setNumero(String numero) {
        this.numero = numero;
    }

    
    public Date getFecha() {
        return fecha;
    }

    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    public String getResultado() {
        return resultado;
    }

    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
     
     
     
}
