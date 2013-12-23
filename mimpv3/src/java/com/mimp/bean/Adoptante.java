package com.mimp.bean;
// Generated Dec 23, 2013 3:56:21 PM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Adoptante generated by hbm2java
 */
public class Adoptante  implements java.io.Serializable {


     private long idadoptante;
     private InfoFamilia infoFamilia;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private Character sexo;
     private Date fechaNac;
     private String lugarNac;
     private String depaNac;
     private String paisNac;
     private Character tipoDoc;
     private String NDoc;
     private String celular;
     private String correo;
     private String nivelInstruccion;
     private Short culminoNivel;
     private String profesion;
     private Short trabajadorDepend;
     private String ocupActualDep;
     private String centroTrabajo;
     private String direccionCentro;
     private String telefonoCentro;
     private Long ingresoDep;
     private Short trabajadorIndepend;
     private String ocupActualInd;
     private Long ingresoIndep;
     private Short seguroSalud;
     private String tipoSeguro;
     private Short seguroVida;
     private Short sistPensiones;
     private String saludActual;

    public Adoptante() {
    }

	
    public Adoptante(long idadoptante, InfoFamilia infoFamilia) {
        this.idadoptante = idadoptante;
        this.infoFamilia = infoFamilia;
    }
    public Adoptante(long idadoptante, InfoFamilia infoFamilia, String nombre, String apellidoP, String apellidoM, Character sexo, Date fechaNac, String lugarNac, String depaNac, String paisNac, Character tipoDoc, String NDoc, String celular, String correo, String nivelInstruccion, Short culminoNivel, String profesion, Short trabajadorDepend, String ocupActualDep, String centroTrabajo, String direccionCentro, String telefonoCentro, Long ingresoDep, Short trabajadorIndepend, String ocupActualInd, Long ingresoIndep, Short seguroSalud, String tipoSeguro, Short seguroVida, Short sistPensiones, String saludActual) {
       this.idadoptante = idadoptante;
       this.infoFamilia = infoFamilia;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.sexo = sexo;
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
   
    public long getIdadoptante() {
        return this.idadoptante;
    }
    
    public void setIdadoptante(long idadoptante) {
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
    public Character getSexo() {
        return this.sexo;
    }
    
    public void setSexo(Character sexo) {
        this.sexo = sexo;
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
    public Short getCulminoNivel() {
        return this.culminoNivel;
    }
    
    public void setCulminoNivel(Short culminoNivel) {
        this.culminoNivel = culminoNivel;
    }
    public String getProfesion() {
        return this.profesion;
    }
    
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    public Short getTrabajadorDepend() {
        return this.trabajadorDepend;
    }
    
    public void setTrabajadorDepend(Short trabajadorDepend) {
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
    public Long getIngresoDep() {
        return this.ingresoDep;
    }
    
    public void setIngresoDep(Long ingresoDep) {
        this.ingresoDep = ingresoDep;
    }
    public Short getTrabajadorIndepend() {
        return this.trabajadorIndepend;
    }
    
    public void setTrabajadorIndepend(Short trabajadorIndepend) {
        this.trabajadorIndepend = trabajadorIndepend;
    }
    public String getOcupActualInd() {
        return this.ocupActualInd;
    }
    
    public void setOcupActualInd(String ocupActualInd) {
        this.ocupActualInd = ocupActualInd;
    }
    public Long getIngresoIndep() {
        return this.ingresoIndep;
    }
    
    public void setIngresoIndep(Long ingresoIndep) {
        this.ingresoIndep = ingresoIndep;
    }
    public Short getSeguroSalud() {
        return this.seguroSalud;
    }
    
    public void setSeguroSalud(Short seguroSalud) {
        this.seguroSalud = seguroSalud;
    }
    public String getTipoSeguro() {
        return this.tipoSeguro;
    }
    
    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }
    public Short getSeguroVida() {
        return this.seguroVida;
    }
    
    public void setSeguroVida(Short seguroVida) {
        this.seguroVida = seguroVida;
    }
    public Short getSistPensiones() {
        return this.sistPensiones;
    }
    
    public void setSistPensiones(Short sistPensiones) {
        this.sistPensiones = sistPensiones;
    }
    public String getSaludActual() {
        return this.saludActual;
    }
    
    public void setSaludActual(String saludActual) {
        this.saludActual = saludActual;
    }




}


