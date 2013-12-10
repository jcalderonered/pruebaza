package com.mimp.bean;
// Generated 10/12/2013 02:25:47 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Personal generated by hbm2java
 */
public class Personal  implements java.io.Serializable {


     private Integer idpersonal;
     private Unidad unidad;
     private String nombre;
     private String apellidoP;
     private String apellidoM;
     private String user;
     private String pass;
     private String correoTrabajo;
     private String correoPersonal;
     private String profesion;
     private String gradoInstruccion;
     private String cargo;
     private Integer dni;
     private Date fechaNacimiento;
     private String regimen;
     private Date fechaIngreso;
     private String domicilio;
     private String rol;
     private Set<Atencion> atencions = new HashSet<Atencion>(0);
     private Set<Log> logs = new HashSet<Log>(0);
     private Set<InformePostAdoptivo> informePostAdoptivos = new HashSet<InformePostAdoptivo>(0);
     private Set<Designacion> designacions = new HashSet<Designacion>(0);
     private Set<Evaluacion> evaluacions = new HashSet<Evaluacion>(0);

    public Personal() {
    }

	
    public Personal(Unidad unidad) {
        this.unidad = unidad;
    }
    public Personal(Unidad unidad, String nombre, String apellidoP, String apellidoM, String user, String pass, String correoTrabajo, String correoPersonal, String profesion, String gradoInstruccion, String cargo, Integer dni, Date fechaNacimiento, String regimen, Date fechaIngreso, String domicilio, String rol, Set<Atencion> atencions, Set<Log> logs, Set<InformePostAdoptivo> informePostAdoptivos, Set<Designacion> designacions, Set<Evaluacion> evaluacions) {
       this.unidad = unidad;
       this.nombre = nombre;
       this.apellidoP = apellidoP;
       this.apellidoM = apellidoM;
       this.user = user;
       this.pass = pass;
       this.correoTrabajo = correoTrabajo;
       this.correoPersonal = correoPersonal;
       this.profesion = profesion;
       this.gradoInstruccion = gradoInstruccion;
       this.cargo = cargo;
       this.dni = dni;
       this.fechaNacimiento = fechaNacimiento;
       this.regimen = regimen;
       this.fechaIngreso = fechaIngreso;
       this.domicilio = domicilio;
       this.rol = rol;
       this.atencions = atencions;
       this.logs = logs;
       this.informePostAdoptivos = informePostAdoptivos;
       this.designacions = designacions;
       this.evaluacions = evaluacions;
    }
   
    public Integer getIdpersonal() {
        return this.idpersonal;
    }
    
    public void setIdpersonal(Integer idpersonal) {
        this.idpersonal = idpersonal;
    }
    public Unidad getUnidad() {
        return this.unidad;
    }
    
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
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
    public String getUser() {
        return this.user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getCorreoTrabajo() {
        return this.correoTrabajo;
    }
    
    public void setCorreoTrabajo(String correoTrabajo) {
        this.correoTrabajo = correoTrabajo;
    }
    public String getCorreoPersonal() {
        return this.correoPersonal;
    }
    
    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }
    public String getProfesion() {
        return this.profesion;
    }
    
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    public String getGradoInstruccion() {
        return this.gradoInstruccion;
    }
    
    public void setGradoInstruccion(String gradoInstruccion) {
        this.gradoInstruccion = gradoInstruccion;
    }
    public String getCargo() {
        return this.cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public Integer getDni() {
        return this.dni;
    }
    
    public void setDni(Integer dni) {
        this.dni = dni;
    }
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getRegimen() {
        return this.regimen;
    }
    
    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public String getDomicilio() {
        return this.domicilio;
    }
    
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public String getRol() {
        return this.rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Set<Atencion> getAtencions() {
        return this.atencions;
    }
    
    public void setAtencions(Set<Atencion> atencions) {
        this.atencions = atencions;
    }
    public Set<Log> getLogs() {
        return this.logs;
    }
    
    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }
    public Set<InformePostAdoptivo> getInformePostAdoptivos() {
        return this.informePostAdoptivos;
    }
    
    public void setInformePostAdoptivos(Set<InformePostAdoptivo> informePostAdoptivos) {
        this.informePostAdoptivos = informePostAdoptivos;
    }
    public Set<Designacion> getDesignacions() {
        return this.designacions;
    }
    
    public void setDesignacions(Set<Designacion> designacions) {
        this.designacions = designacions;
    }
    public Set<Evaluacion> getEvaluacions() {
        return this.evaluacions;
    }
    
    public void setEvaluacions(Set<Evaluacion> evaluacions) {
        this.evaluacions = evaluacions;
    }




}


