package com.mimp.bean;
// Generated 5/12/2013 05:37:51 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Adoptante generated by hbm2java
 */
public class Adoptante  implements java.io.Serializable {


     private Integer idadoptante;
     private InfoFamilia infoFamilia;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private Date fechaNac;
     private String lugarNac;
     private String depaNac;
     private String paisNac;
     private Character tipoDoc;
     private String NDoc;
     private String celular;
     private String correo;
     private String nivelInstruccion;
     private Boolean culminoNivel;
     private String profesion;
     private Boolean trabajadorDepend;
     private String ocupActualDep;
     private String centroTrabajo;
     private String direccionCentro;
     private String telefonoCentro;
     private Integer ingresoDep;
     private Boolean trabajadorIndepend;
     private String ocupActualInd;
     private Integer ingresoIndep;
     private Boolean seguroSalud;
     private String tipoSeguro;
     private Boolean seguroVida;
     private Boolean sistPensiones;
     private String saludActual;

    public Adoptante() {
    }

	
    public Adoptante(InfoFamilia infoFamilia) {
        this.infoFamilia = infoFamilia;
    }
    public Adoptante(InfoFamilia infoFamilia, String nombre, String apellidoP, String apellidoM, Date fechaNac, String lugarNac, String depaNac, String paisNac, Character tipoDoc, String NDoc, String celular, String correo, String nivelInstruccion, Boolean culminoNivel, String profesion, Boolean trabajadorDepend, String ocupActualDep, String centroTrabajo, String direccionCentro, String telefonoCentro, Integer ingresoDep, Boolean trabajadorIndepend, String ocupActualInd, Integer ingresoIndep, Boolean seguroSalud, String tipoSeguro, Boolean seguroVida, Boolean sistPensiones, String saludActual) {
       this.infoFamilia = infoFamilia;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.fechaNac = fechaNac;
       this.lugarNac = lugarNac;
       this.depaNac = depaNac;
       this.paisNac = paisNac;
       this.tipoDoc = tipoDoc;
       this.NDoc = NDoc;
       this.celular = celular;
       this.correo = correo;
       this.nivelInstruccion = nivelInstruccion;
       this.culminoNivel = culminoNivel;
       this.profesion = profesion;
       this.trabajadorDepend = trabajadorDepend;
       this.ocupActualDep = ocupActualDep;
       this.centroTrabajo = centroTrabajo;
       this.direccionCentro = direccionCentro;
       this.telefonoCentro = telefonoCentro;
       this.ingresoDep = ingresoDep;
       this.trabajadorIndepend = trabajadorIndepend;
       this.ocupActualInd = ocupActualInd;
       this.ingresoIndep = ingresoIndep;
       this.seguroSalud = seguroSalud;
       this.tipoSeguro = tipoSeguro;
       this.seguroVida = seguroVida;
       this.sistPensiones = sistPensiones;
       this.saludActual = saludActual;
    }
   
    public Integer getIdadoptante() {
        return this.idadoptante;
    }
    
    public void setIdadoptante(Integer idadoptante) {
        this.idadoptante = idadoptante;
    }
    public InfoFamilia getInfoFamilia() {
        return this.infoFamilia;
    }
    
    public void setInfoFamilia(InfoFamilia infoFamilia) {
        this.infoFamilia = infoFamilia;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoP() {
        return this.apellidoP;
    }
    
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }
    public String getApellidoM() {
        return this.apellidoM;
    }
    
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }
    public Date getFechaNac() {
        return this.fechaNac;
    }
    
    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
    public String getLugarNac() {
        return this.lugarNac;
    }
    
    public void setLugarNac(String lugarNac) {
        this.lugarNac = lugarNac;
    }
    public String getDepaNac() {
        return this.depaNac;
    }
    
    public void setDepaNac(String depaNac) {
        this.depaNac = depaNac;
    }
    public String getPaisNac() {
        return this.paisNac;
    }
    
    public void setPaisNac(String paisNac) {
        this.paisNac = paisNac;
    }
    public Character getTipoDoc() {
        return this.tipoDoc;
    }
    
    public void setTipoDoc(Character tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    public String getNDoc() {
        return this.NDoc;
    }
    
    public void setNDoc(String NDoc) {
        this.NDoc = NDoc;
    }
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getNivelInstruccion() {
        return this.nivelInstruccion;
    }
    
    public void setNivelInstruccion(String nivelInstruccion) {
        this.nivelInstruccion = nivelInstruccion;
    }
    public Boolean getCulminoNivel() {
        return this.culminoNivel;
    }
    
    public void setCulminoNivel(Boolean culminoNivel) {
        this.culminoNivel = culminoNivel;
    }
    public String getProfesion() {
        return this.profesion;
    }
    
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    public Boolean getTrabajadorDepend() {
        return this.trabajadorDepend;
    }
    
    public void setTrabajadorDepend(Boolean trabajadorDepend) {
        this.trabajadorDepend = trabajadorDepend;
    }
    public String getOcupActualDep() {
        return this.ocupActualDep;
    }
    
    public void setOcupActualDep(String ocupActualDep) {
        this.ocupActualDep = ocupActualDep;
    }
    public String getCentroTrabajo() {
        return this.centroTrabajo;
    }
    
    public void setCentroTrabajo(String centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }
    public String getDireccionCentro() {
        return this.direccionCentro;
    }
    
    public void setDireccionCentro(String direccionCentro) {
        this.direccionCentro = direccionCentro;
    }
    public String getTelefonoCentro() {
        return this.telefonoCentro;
    }
    
    public void setTelefonoCentro(String telefonoCentro) {
        this.telefonoCentro = telefonoCentro;
    }
    public Integer getIngresoDep() {
        return this.ingresoDep;
    }
    
    public void setIngresoDep(Integer ingresoDep) {
        this.ingresoDep = ingresoDep;
    }
    public Boolean getTrabajadorIndepend() {
        return this.trabajadorIndepend;
    }
    
    public void setTrabajadorIndepend(Boolean trabajadorIndepend) {
        this.trabajadorIndepend = trabajadorIndepend;
    }
    public String getOcupActualInd() {
        return this.ocupActualInd;
    }
    
    public void setOcupActualInd(String ocupActualInd) {
        this.ocupActualInd = ocupActualInd;
    }
    public Integer getIngresoIndep() {
        return this.ingresoIndep;
    }
    
    public void setIngresoIndep(Integer ingresoIndep) {
        this.ingresoIndep = ingresoIndep;
    }
    public Boolean getSeguroSalud() {
        return this.seguroSalud;
    }
    
    public void setSeguroSalud(Boolean seguroSalud) {
        this.seguroSalud = seguroSalud;
    }
    public String getTipoSeguro() {
        return this.tipoSeguro;
    }
    
    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }
    public Boolean getSeguroVida() {
        return this.seguroVida;
    }
    
    public void setSeguroVida(Boolean seguroVida) {
        this.seguroVida = seguroVida;
    }
    public Boolean getSistPensiones() {
        return this.sistPensiones;
    }
    
    public void setSistPensiones(Boolean sistPensiones) {
        this.sistPensiones = sistPensiones;
    }
    public String getSaludActual() {
        return this.saludActual;
    }
    
    public void setSaludActual(String saludActual) {
        this.saludActual = saludActual;
    }




}


