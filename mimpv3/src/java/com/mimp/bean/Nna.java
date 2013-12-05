package com.mimp.bean;
// Generated 5/12/2013 05:37:51 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Nna generated by hbm2java
 */
public class Nna  implements java.io.Serializable {


     private Integer idnna;
     private Car car;
     private Juzgado juzgado;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private String sexo;
     private Date fechaNacimiento;
     private Byte edadAnhos;
     private Byte edadMeses;
     private Boolean actaNacimiento;
     private String condicionSalud;
     private String departamentoNacimiento;
     private String provinciaNacimiento;
     private String distritoNacimiento;
     private String paisNacimiento;
     private String lugarNac;
     private Date fechaResolAbandono;
     private Date fechaResolConsentida;
     private String clasificacion;
     private Boolean incesto;
     private Boolean mental;
     private Boolean epilepsia;
     private Boolean abuso;
     private Boolean sifilis;
     private Boolean seguiMedico;
     private Boolean operacion;
     private Boolean hiperactivo;
     private Boolean especial;
     private Boolean enfermo;
     private Boolean mayor;
     private Boolean adolescente;
     private Boolean hermano;
     private Boolean nn;
     private Set<EstudioCaso> estudioCasos = new HashSet<EstudioCaso>(0);
     private Set<ExpedienteNna> expedienteNnas = new HashSet<ExpedienteNna>(0);
     private Set<Prioritario> prioritarios = new HashSet<Prioritario>(0);
     private Set<Designacion> designacions = new HashSet<Designacion>(0);

    public Nna() {
    }

	
    public Nna(Car car, Juzgado juzgado) {
        this.car = car;
        this.juzgado = juzgado;
    }
    public Nna(Car car, Juzgado juzgado, String nombre, String apellidoP, String apellidoM, String sexo, Date fechaNacimiento, Byte edadAnhos, Byte edadMeses, Boolean actaNacimiento, String condicionSalud, String departamentoNacimiento, String provinciaNacimiento, String distritoNacimiento, String paisNacimiento, String lugarNac, Date fechaResolAbandono, Date fechaResolConsentida, String clasificacion, Boolean incesto, Boolean mental, Boolean epilepsia, Boolean abuso, Boolean sifilis, Boolean seguiMedico, Boolean operacion, Boolean hiperactivo, Boolean especial, Boolean enfermo, Boolean mayor, Boolean adolescente, Boolean hermano, Boolean nn, Set<EstudioCaso> estudioCasos, Set<ExpedienteNna> expedienteNnas, Set<Prioritario> prioritarios, Set<Designacion> designacions) {
       this.car = car;
       this.juzgado = juzgado;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.sexo = sexo;
       this.fechaNacimiento = fechaNacimiento;
       this.edadAnhos = edadAnhos;
       this.edadMeses = edadMeses;
       this.actaNacimiento = actaNacimiento;
       this.condicionSalud = condicionSalud;
       this.departamentoNacimiento = departamentoNacimiento;
       this.provinciaNacimiento = provinciaNacimiento;
       this.distritoNacimiento = distritoNacimiento;
       this.paisNacimiento = paisNacimiento;
       this.lugarNac = lugarNac;
       this.fechaResolAbandono = fechaResolAbandono;
       this.fechaResolConsentida = fechaResolConsentida;
       this.clasificacion = clasificacion;
       this.incesto = incesto;
       this.mental = mental;
       this.epilepsia = epilepsia;
       this.abuso = abuso;
       this.sifilis = sifilis;
       this.seguiMedico = seguiMedico;
       this.operacion = operacion;
       this.hiperactivo = hiperactivo;
       this.especial = especial;
       this.enfermo = enfermo;
       this.mayor = mayor;
       this.adolescente = adolescente;
       this.hermano = hermano;
       this.nn = nn;
       this.estudioCasos = estudioCasos;
       this.expedienteNnas = expedienteNnas;
       this.prioritarios = prioritarios;
       this.designacions = designacions;
    }
   
    public Integer getIdnna() {
        return this.idnna;
    }
    
    public void setIdnna(Integer idnna) {
        this.idnna = idnna;
    }
    public Car getCar() {
        return this.car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }
    public Juzgado getJuzgado() {
        return this.juzgado;
    }
    
    public void setJuzgado(Juzgado juzgado) {
        this.juzgado = juzgado;
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
    public String getSexo() {
        return this.sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Byte getEdadAnhos() {
        return this.edadAnhos;
    }
    
    public void setEdadAnhos(Byte edadAnhos) {
        this.edadAnhos = edadAnhos;
    }
    public Byte getEdadMeses() {
        return this.edadMeses;
    }
    
    public void setEdadMeses(Byte edadMeses) {
        this.edadMeses = edadMeses;
    }
    public Boolean getActaNacimiento() {
        return this.actaNacimiento;
    }
    
    public void setActaNacimiento(Boolean actaNacimiento) {
        this.actaNacimiento = actaNacimiento;
    }
    public String getCondicionSalud() {
        return this.condicionSalud;
    }
    
    public void setCondicionSalud(String condicionSalud) {
        this.condicionSalud = condicionSalud;
    }
    public String getDepartamentoNacimiento() {
        return this.departamentoNacimiento;
    }
    
    public void setDepartamentoNacimiento(String departamentoNacimiento) {
        this.departamentoNacimiento = departamentoNacimiento;
    }
    public String getProvinciaNacimiento() {
        return this.provinciaNacimiento;
    }
    
    public void setProvinciaNacimiento(String provinciaNacimiento) {
        this.provinciaNacimiento = provinciaNacimiento;
    }
    public String getDistritoNacimiento() {
        return this.distritoNacimiento;
    }
    
    public void setDistritoNacimiento(String distritoNacimiento) {
        this.distritoNacimiento = distritoNacimiento;
    }
    public String getPaisNacimiento() {
        return this.paisNacimiento;
    }
    
    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }
    public String getLugarNac() {
        return this.lugarNac;
    }
    
    public void setLugarNac(String lugarNac) {
        this.lugarNac = lugarNac;
    }
    public Date getFechaResolAbandono() {
        return this.fechaResolAbandono;
    }
    
    public void setFechaResolAbandono(Date fechaResolAbandono) {
        this.fechaResolAbandono = fechaResolAbandono;
    }
    public Date getFechaResolConsentida() {
        return this.fechaResolConsentida;
    }
    
    public void setFechaResolConsentida(Date fechaResolConsentida) {
        this.fechaResolConsentida = fechaResolConsentida;
    }
    public String getClasificacion() {
        return this.clasificacion;
    }
    
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    public Boolean getIncesto() {
        return this.incesto;
    }
    
    public void setIncesto(Boolean incesto) {
        this.incesto = incesto;
    }
    public Boolean getMental() {
        return this.mental;
    }
    
    public void setMental(Boolean mental) {
        this.mental = mental;
    }
    public Boolean getEpilepsia() {
        return this.epilepsia;
    }
    
    public void setEpilepsia(Boolean epilepsia) {
        this.epilepsia = epilepsia;
    }
    public Boolean getAbuso() {
        return this.abuso;
    }
    
    public void setAbuso(Boolean abuso) {
        this.abuso = abuso;
    }
    public Boolean getSifilis() {
        return this.sifilis;
    }
    
    public void setSifilis(Boolean sifilis) {
        this.sifilis = sifilis;
    }
    public Boolean getSeguiMedico() {
        return this.seguiMedico;
    }
    
    public void setSeguiMedico(Boolean seguiMedico) {
        this.seguiMedico = seguiMedico;
    }
    public Boolean getOperacion() {
        return this.operacion;
    }
    
    public void setOperacion(Boolean operacion) {
        this.operacion = operacion;
    }
    public Boolean getHiperactivo() {
        return this.hiperactivo;
    }
    
    public void setHiperactivo(Boolean hiperactivo) {
        this.hiperactivo = hiperactivo;
    }
    public Boolean getEspecial() {
        return this.especial;
    }
    
    public void setEspecial(Boolean especial) {
        this.especial = especial;
    }
    public Boolean getEnfermo() {
        return this.enfermo;
    }
    
    public void setEnfermo(Boolean enfermo) {
        this.enfermo = enfermo;
    }
    public Boolean getMayor() {
        return this.mayor;
    }
    
    public void setMayor(Boolean mayor) {
        this.mayor = mayor;
    }
    public Boolean getAdolescente() {
        return this.adolescente;
    }
    
    public void setAdolescente(Boolean adolescente) {
        this.adolescente = adolescente;
    }
    public Boolean getHermano() {
        return this.hermano;
    }
    
    public void setHermano(Boolean hermano) {
        this.hermano = hermano;
    }
    public Boolean getNn() {
        return this.nn;
    }
    
    public void setNn(Boolean nn) {
        this.nn = nn;
    }
    public Set<EstudioCaso> getEstudioCasos() {
        return this.estudioCasos;
    }
    
    public void setEstudioCasos(Set<EstudioCaso> estudioCasos) {
        this.estudioCasos = estudioCasos;
    }
    public Set<ExpedienteNna> getExpedienteNnas() {
        return this.expedienteNnas;
    }
    
    public void setExpedienteNnas(Set<ExpedienteNna> expedienteNnas) {
        this.expedienteNnas = expedienteNnas;
    }
    public Set<Prioritario> getPrioritarios() {
        return this.prioritarios;
    }
    
    public void setPrioritarios(Set<Prioritario> prioritarios) {
        this.prioritarios = prioritarios;
    }
    public Set<Designacion> getDesignacions() {
        return this.designacions;
    }
    
    public void setDesignacions(Set<Designacion> designacions) {
        this.designacions = designacions;
    }




}


