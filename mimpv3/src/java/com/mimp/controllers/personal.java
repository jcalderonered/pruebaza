/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.controllers;

import com.mimp.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author john
 */
@Controller
public class personal {
    
    dateFormat format = new dateFormat();
    timeStampFormat ts = new timeStampFormat();
    
    @Resource(name="HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name="HiberMain")
    private HiberMain ServicioMain = new HiberMain();
    //////////////////////// NAVEGACION ///////////////
    @RequestMapping (value = "/inicioper", method = RequestMethod.GET)
    public ModelAndView InicioPer(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/inicio_personal",map);
    }
    
    @RequestMapping (value = "/inf", method = RequestMethod.GET)
    public ModelAndView Inf(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        
        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        return new ModelAndView("/Personal/Informativa/lista_charlas",map);
    }
    
    @RequestMapping (value = "/nna", method = RequestMethod.GET)
    public ModelAndView Nna(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
       // map.addAttribute("id", temp);
        return new ModelAndView("/Personal/nna/lista_nna",map);
    }
    
    @RequestMapping (value = "/juzgado", method = RequestMethod.GET)
    public ModelAndView Juzgado(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaJuzgados",ServicioPersonal.ListaJuzgado());
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg",map);
    }
    
    @RequestMapping (value = "/car", method = RequestMethod.GET)
    public ModelAndView Car(ModelMap map){
    
        
        map.put("listaCar",ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/registros/car/lista_car",map);
    }
    
    @RequestMapping (value = "/ua", method = RequestMethod.GET)
    public ModelAndView Ua(ModelMap map){
    
        map.put("listaUa",ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/ua/lista_ua",map);
    }
    
    @RequestMapping (value = "/famint", method = RequestMethod.GET)
    public ModelAndView FamInt(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/fam_inter/lista_fam_int",map);
    }
    
    @RequestMapping (value = "/fametap", method = RequestMethod.GET)
    public ModelAndView FamEtap(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_formativa",map);
    }
    
    @RequestMapping (value = "/reg", method = RequestMethod.GET)
    public ModelAndView Registros(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/inicio_personal",map);
    }
    
    @RequestMapping (value = "/usuarios", method = RequestMethod.GET)
    public ModelAndView Usuarios(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/registros/usuarios/lista_personal",map);
    }
    
    @RequestMapping (value = "/organismo", method = RequestMethod.GET)
    public ModelAndView Organismo(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaOrganismos",ServicioPersonal.ListaOrganismos());
        return new ModelAndView("/Personal/registros/organismo/lista_org",map);
    }
    
    @RequestMapping (value = "/autoridad", method = RequestMethod.GET)
    public ModelAndView Autoridad(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaAutoridades",ServicioPersonal.ListaAutoridades());
        return new ModelAndView("/Personal/registros/autoridad/lista_aut",map);
    }
    
    @RequestMapping (value = "/password", method = RequestMethod.GET)
    public ModelAndView Password(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/actualizar_pass",map);
    }
    
    ////////////////////// REGISTRAR/EDITAR AUTORIDAD ////////////////////////////////////
    
    @RequestMapping (value = "/irEditarAut", method = RequestMethod.GET)
    public ModelAndView IrEditarAut(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/registros/autoridad/editar_aut",map);
    }
    
    @RequestMapping (value = "/irEditarAut2", method = RequestMethod.POST)
    public ModelAndView IrEditarAut2(ModelMap map,@RequestParam("id") int id){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        Autoridad temp = new Autoridad();
        temp = ServicioPersonal.getAutoridad(id);
        String fechaEmision = format.dateToString(temp.getEntidad().getFechaResol());
        String fechaRenov = format.dateToString(temp.getEntidad().getFechaRenov());
        String fechaVenc = format.dateToString(temp.getEntidad().getFechaVenc());
        map.addAttribute("fechaEmision", fechaEmision);
        map.addAttribute("fechaRenov", fechaRenov);
        map.addAttribute("fechaVenc", fechaVenc);
        map.put("autoridad",temp);
        return new ModelAndView("/Personal/registros/autoridad/editar_aut",map);
    }
    
    @RequestMapping (value = "/editAut", method = RequestMethod.POST)
    public ModelAndView EditarAut(ModelMap map,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("tipo") String tipo,
                        @RequestParam("pais") String pais,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("user") String user,
                        @RequestParam("pass") String pass,
                        @RequestParam("resol_aut") String resol_aut,
                        @RequestParam("fecha_emis_resol") String fecha_emis_resol,
                        @RequestParam("resol_renov") String resol_renov,
                        @RequestParam("fecha_renov") String fecha_renov,
                        @RequestParam("fecha_venc_aut") String fecha_venc_aut,
                        @RequestParam("obs") String obs){
    
        Autoridad aut = new Autoridad();
        Entidad ent = new Entidad();
        
        ent.setNombre(nombre);
        ent.setDireccion(direccion);
        ent.setTelefono(telefono);
        ent.setPais(pais);
        ent.setResolAuto(resol_aut);
        ent.setFechaResol(format.stringToDate(fecha_emis_resol));
        ent.setResolRenov(resol_renov);
        ent.setFechaRenov(format.stringToDate(fecha_renov));
        ent.setFechaVenc(format.stringToDate(fecha_venc_aut));
        ent.setObs(obs);
        ent.setUser(user);
        ent.setPass(pass);
        aut.setTipo(tipo);
        
        
        ServicioPersonal.InsertAut(ent, aut);
        
        map.put("listaAutoridades",ServicioPersonal.ListaAutoridades());
        return new ModelAndView("/Personal/registros/autoridad/lista_aut",map);
    }
    
    @RequestMapping (value = "/updateAut", method = RequestMethod.POST)
    public ModelAndView UpdateAut(ModelMap map,
                                @RequestParam("id") int id,
                                @RequestParam("nombre") String nombre,
                                @RequestParam("tipo") String tipo,
                                @RequestParam("pais") String pais,
                                @RequestParam("direccion") String direccion,
                                @RequestParam("telefono") String telefono,
                                @RequestParam("user") String user,
                                @RequestParam("pass") String pass,
                                @RequestParam("resol_aut") String resol_aut,
                                @RequestParam("fecha_emis_resol") String fecha_emis_resol,
                                @RequestParam("resol_renov") String resol_renov,
                                @RequestParam("fecha_renov") String fecha_renov,
                                @RequestParam("fecha_venc_aut") String fecha_venc_aut,
                                @RequestParam("obs") String obs){
    
        Autoridad temp = new Autoridad();
        temp = ServicioPersonal.getAutoridad(id);
        temp.setTipo(tipo);
        
        temp.getEntidad().setNombre(nombre);
        temp.getEntidad().setPais(pais);
        temp.getEntidad().setUser(user);
        temp.getEntidad().setPass(pass);
        temp.getEntidad().setDireccion(direccion);
        temp.getEntidad().setTelefono(telefono);
        temp.getEntidad().setResolAuto(resol_aut);
        temp.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
        temp.getEntidad().setResolRenov(resol_renov);
        temp.getEntidad().setFechaRenov(format.stringToDate(fecha_renov));
        temp.getEntidad().setFechaVenc(format.stringToDate(fecha_venc_aut));
        temp.getEntidad().setObs(obs);
        ServicioPersonal.UpdateAut(temp.getEntidad(), temp);
        map.put("listaAutoridades",ServicioPersonal.ListaAutoridades());
        return new ModelAndView("/Personal/registros/autoridad/lista_aut",map);
    }
    /////////////////////////////////////////////////////////////////////////////////
    
    ////////////////////// REGISTRAR/EDITAR ORGANISMO ////////////////////////////////////
    
    @RequestMapping (value = "/irEditarOrg", method = RequestMethod.GET)
    public ModelAndView IrEditarOrg(ModelMap map){
    
        
        return new ModelAndView("/Personal/registros/organismo/editar_org",map);
    }
    
    @RequestMapping (value = "/irEditarOrg2", method = RequestMethod.POST)
    public ModelAndView IrEditarOrg2(ModelMap map,@RequestParam("id") int id){
    
        
        Organismo temp = new Organismo();
        temp = ServicioPersonal.getOrganismo(id);
        String fechaEmision = format.dateToString(temp.getEntidad().getFechaResol());
        String fechaRenov = format.dateToString(temp.getEntidad().getFechaRenov());
        String fechaVenc = format.dateToString(temp.getEntidad().getFechaVenc());
        
        String fechaAutR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaAuto());
        String fechaRenovR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaRenov());
        String fechaVencR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaVencAuto());
        
        map.addAttribute("fechaEmision", fechaEmision);
        map.addAttribute("fechaRenov", fechaRenov);
        map.addAttribute("fechaVenc", fechaVenc);
        map.addAttribute("fechaAutR", fechaAutR);
        map.addAttribute("fechaRenovR", fechaRenovR);
        map.addAttribute("fechaVencR", fechaVencR);
        
        map.put("organismo",temp);
        
        return new ModelAndView("/Personal/registros/organismo/editar_org",map);
    }
    
    @RequestMapping (value = "/editOrg", method = RequestMethod.POST)
    public ModelAndView EditarOrg(ModelMap map,
                        //datos a ingresar en entidad
                        @RequestParam("nombre") String nombre,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("pais") String pais,
                        @RequestParam("resol_aut") String resol_aut,
                        @RequestParam("fecha_emis_resol") String fecha_emis_resol,
                        @RequestParam("resol_renov") String resol_renov,
                        @RequestParam("fecha_renov") String fecha_renov,
                        @RequestParam("fecha_venc_aut") String fecha_venc_aut,
                        @RequestParam("obs") String obs,
                        //datos a ingresar en organismo
                        @RequestParam("competencia") String competencia,
                         //datos a ingresar en representante
                        @RequestParam("nombreR") String nombreR,
                        @RequestParam("apellidoP") String apellidoP,
                        @RequestParam("apellidoM") String apellidoM,
                        @RequestParam("user") String user,
                        @RequestParam("pass") String pass,
                        @RequestParam("fechaAutR") String fechaAutR,
                        @RequestParam("fechaRenovR") String fechaRenovR,
                        @RequestParam("fechaVencR") String fechaVencR,
                        @RequestParam("correo") String correo,
                        @RequestParam("celular") String celular,
                        @RequestParam("direccionR") String direccionR,
                        @RequestParam("obsR") String obsR
                        ){
        
        Organismo org = new Organismo();
        Representante rep = new Representante();
        Entidad ent = new Entidad();
        
        org.setCompetencia(competencia);
        
        rep.setNombre(nombreR);
        rep.setApellidoP(apellidoP);
        rep.setApelldoM(apellidoM);
        rep.setFechaAuto(format.stringToDate(fechaAutR));
        rep.setFechaRenov(format.stringToDate(fechaRenovR));
        rep.setFechaVencAuto(format.stringToDate(fechaVencR));
        rep.setCorreo(correo);
        rep.setDireccion(direccionR);
        rep.setCelular(celular);
        rep.setObs(obsR);
        
        
        ent.setNombre(nombre);
        ent.setDireccion(direccion);
        ent.setTelefono(telefono);
        ent.setPais(pais);
        ent.setUser(user);
        ent.setPass(pass);
        ent.setResolAuto(resol_aut);
        ent.setFechaResol(format.stringToDate(fecha_emis_resol));
        ent.setResolRenov(resol_renov);
        ent.setFechaRenov(format.stringToDate(fecha_renov));
        ent.setFechaVenc(format.stringToDate(fecha_venc_aut));
        ent.setObs(obs);
        
        
        
        ServicioPersonal.InsertOrg(ent, rep, org);
        
         map.put("listaOrganismos",ServicioPersonal.ListaOrganismos());
        return new ModelAndView("/Personal/registros/organismo/lista_org",map);
    }
    
    @RequestMapping (value = "/updateOrg", method = RequestMethod.POST)
    public ModelAndView UpdateOrg(ModelMap map,
                        @RequestParam("id") int id,
                        //datos a ingresar en entidad
                        @RequestParam("nombre") String nombre,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("pais") String pais,
                        @RequestParam("resol_aut") String resol_aut,
                        @RequestParam("fecha_emis_resol") String fecha_emis_resol,
                        @RequestParam("resol_renov") String resol_renov,
                        @RequestParam("fecha_renov") String fecha_renov,
                        @RequestParam("fecha_venc_aut") String fecha_venc_aut,
                        @RequestParam("obs") String obs,
                        //datos a ingresar en organismo
                        @RequestParam("competencia") String competencia,
                         //datos a ingresar en representante
                        @RequestParam("nombreR") String nombreR,
                        @RequestParam("apellidoP") String apellidoP,
                        @RequestParam("apellidoM") String apellidoM,
                        @RequestParam("user") String user,
                        @RequestParam("pass") String pass,
                        @RequestParam("fechaAutR") String fechaAutR,
                        @RequestParam("fechaRenovR") String fechaRenovR,
                        @RequestParam("fechaVencR") String fechaVencR,
                        @RequestParam("correo") String correo,
                        @RequestParam("celular") String celular,
                        @RequestParam("direccionR") String direccionR,
                        @RequestParam("obsR") String obsR
                        ){
        
        Organismo org = new Organismo();
        
        
        org = ServicioPersonal.getOrganismo(id);
        
        org.setCompetencia(competencia);
        
        org.getRepresentantes().iterator().next().setNombre(nombreR);
        org.getRepresentantes().iterator().next().setApellidoP(apellidoP);
        org.getRepresentantes().iterator().next().setApelldoM(apellidoM);
        
        org.getRepresentantes().iterator().next().setFechaAuto(format.stringToDate(fechaAutR));
        org.getRepresentantes().iterator().next().setFechaRenov(format.stringToDate(fechaRenovR));
        org.getRepresentantes().iterator().next().setFechaVencAuto(format.stringToDate(fechaVencR));
        org.getRepresentantes().iterator().next().setCorreo(correo);
        org.getRepresentantes().iterator().next().setDireccion(direccionR);
        org.getRepresentantes().iterator().next().setCelular(celular);
        org.getRepresentantes().iterator().next().setObs(obsR);
        
        
        org.getEntidad().setNombre(nombre);
        org.getEntidad().setUser(user);
        org.getEntidad().setPass(pass);
        org.getEntidad().setDireccion(direccion);
        org.getEntidad().setTelefono(telefono);
        org.getEntidad().setPais(pais);
        org.getEntidad().setResolAuto(resol_aut);
        org.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
        org.getEntidad().setResolRenov(resol_renov);
        org.getEntidad().setFechaRenov(format.stringToDate(fecha_renov));
        org.getEntidad().setFechaVenc(format.stringToDate(fecha_venc_aut));
        org.getEntidad().setObs(obs);
        
        
        
        ServicioPersonal.UpdateOrg(org.getEntidad(), org.getRepresentantes().iterator().next(), org);
        
        map.put("listaOrganismos",ServicioPersonal.ListaOrganismos());
        return new ModelAndView("/Personal/registros/organismo/lista_org",map);
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR CAR ////////////////////////////////////
    @RequestMapping (value = "/irEditarCar", method = RequestMethod.GET)
    public ModelAndView IrEditarCar(ModelMap map){
    
        
        return new ModelAndView("/Personal/registros/car/editar_car",map);
    }
    
    @RequestMapping (value = "/irEditarCar2", method = RequestMethod.POST)
    public ModelAndView IrEditarCar2(ModelMap map,@RequestParam("id") int id){
    
        
        Car temp = new Car();
        temp = ServicioPersonal.getCar(id);
        map.put("car",temp);
        
        return new ModelAndView("/Personal/registros/car/editar_car",map);
    }
    
    @RequestMapping (value = "/editCar", method = RequestMethod.POST)
    public ModelAndView EditarCar(ModelMap map,
                        //datos a ingresar en CAR
                        @RequestParam("nombre") String nombre,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("departamento") String departamento,
                        @RequestParam("provincia") String provincia,
                        @RequestParam("distrito") String distrito,
                        @RequestParam("director") String director,
                        @RequestParam("correo") String correo,
                        @RequestParam("fax") String fax,
                        @RequestParam("celular") String celular,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("obs") String obs
                        
                        ){
        
        Car car = new Car();
        
        car.setNombre(nombre);
        car.setDireccion(direccion);
        car.setDepartamento(departamento);
        car.setProvincia(provincia);
        car.setDistrito(distrito);
        car.setDirector(director);
        car.setCorreo(correo);
        car.setFax(fax);
        car.setCelular(celular);
        car.setTelefono(telefono);
        car.setObservaciones(obs);
        
        ServicioPersonal.InsertCar(car);
        
         map.put("listaCar",ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/registros/car/lista_car",map);
    }
    
    @RequestMapping (value = "/updateCar", method = RequestMethod.POST)
    public ModelAndView UpdateCar(ModelMap map,
                        //datos a ingresar en CAR
                        @RequestParam("id") int id,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("departamento") String departamento,
                        @RequestParam("provincia") String provincia,
                        @RequestParam("distrito") String distrito,
                        @RequestParam("director") String director,
                        @RequestParam("correo") String correo,
                        @RequestParam("fax") String fax,
                        @RequestParam("celular") String celular,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("obs") String obs
                        
                        ){
        
        Car car = new Car();
        car = ServicioPersonal.getCar(id);
        car.setNombre(nombre);
        car.setDireccion(direccion);
        car.setDepartamento(departamento);
        car.setProvincia(provincia);
        car.setDistrito(distrito);
        car.setDirector(director);
        car.setCorreo(correo);
        car.setFax(fax);
        car.setCelular(celular);
        car.setTelefono(telefono);
        car.setObservaciones(obs);
        
        ServicioPersonal.UpdateCar(car);
        
         map.put("listaCar",ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/registros/car/lista_car",map);
    }
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR JUZGADOS ////////////////////////////////////
    
    @RequestMapping (value = "/irEditarJuzgado", method = RequestMethod.GET)
    public ModelAndView IrEditarJuzgado(ModelMap map){
    
        
        return new ModelAndView("/Personal/registros/juzgado/editar_juzgado",map);
    }
    
    @RequestMapping (value = "/irEditarJuzgado2", method = RequestMethod.POST)
    public ModelAndView IrEditarJuzgado2(ModelMap map,@RequestParam("id") int id){
    
        
        Juzgado temp = new Juzgado();
        temp = ServicioPersonal.getJuzgado(id);
        map.put("juzgado",temp);
        
        return new ModelAndView("/Personal/registros/juzgado/editar_juzgado",map);
    }
    
    @RequestMapping (value = "/editJuzgado", method = RequestMethod.POST)
    public ModelAndView EditarJuzgado(ModelMap map,
                        //datos a ingresar en  Juzgado
                        @RequestParam("nombre") String nombre,
                        @RequestParam("denominacion") String denominacion,
                        @RequestParam("especialidad") String especialidad,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("departamento") String departamento,
                        @RequestParam("corteS") String corteS,
                        @RequestParam("distritoJ") String distritoJ,
                        @RequestParam("nombreJ") String nombreJ,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("correo") String correo,
                        @RequestParam("obs") String obs
                        
                        ){
        
        Juzgado juzg = new Juzgado();
        
        juzg.setNombre(nombre);
        juzg.setDenominacion(denominacion);
        juzg.setEspecialidad(especialidad);
        juzg.setDireccion(direccion);
        juzg.setDepartamento(departamento);
        juzg.setCorteSuperior(corteS);
        juzg.setDistritoJudicial(distritoJ);
        juzg.setNombreJuez(nombreJ);
        juzg.setTelefono(telefono);
        juzg.setCorreo(correo);
        juzg.setObservaciones(obs);
        
        
        ServicioPersonal.InsertJuzgado(juzg);
        
         map.put("listaJuzgados",ServicioPersonal.ListaJuzgado());
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg",map);
    }
    
    @RequestMapping (value = "/updateJuzgado", method = RequestMethod.POST)
    public ModelAndView UpdateJuzgado(ModelMap map,
                        //datos a ingresar en Juzgado
                        @RequestParam("id") int id,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("denominacion") String denominacion,
                        @RequestParam("especialidad") String especialidad,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("departamento") String departamento,
                        @RequestParam("corteS") String corteS,
                        @RequestParam("distritoJ") String distritoJ,
                        @RequestParam("nombreJ") String nombreJ,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("correo") String correo,
                        @RequestParam("obs") String obs
                        
                        ){
        
        Juzgado juzg = new Juzgado();
        juzg = ServicioPersonal.getJuzgado(id);
        juzg.setNombre(nombre);
        juzg.setDenominacion(denominacion);
        juzg.setEspecialidad(especialidad);
        juzg.setDireccion(direccion);
        juzg.setDepartamento(departamento);
        juzg.setCorteSuperior(corteS);
        juzg.setDistritoJudicial(distritoJ);
        juzg.setNombreJuez(nombreJ);
        juzg.setTelefono(telefono);
        juzg.setCorreo(correo);
        juzg.setObservaciones(obs);
        
        ServicioPersonal.UpdateJuzgado(juzg);
        
         map.put("listaJuzgados",ServicioPersonal.ListaJuzgado());
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg",map);
    }
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR UA ////////////////////////////////////
    @RequestMapping (value = "/irEditarUa", method = RequestMethod.GET)
    public ModelAndView IrEditarUa(ModelMap map){
    
        
        return new ModelAndView("/Personal/registros/ua/editar_ua",map);
    }
    @RequestMapping (value = "/irEditarUa2", method = RequestMethod.POST)
    public ModelAndView IrEditarUa2(ModelMap map,@RequestParam("id") int id){
    
        
        Unidad temp = new Unidad();
        temp = ServicioPersonal.getUa(id);
        map.put("ua",temp);
        
        return new ModelAndView("/Personal/registros/ua/editar_ua",map);
    }
    @RequestMapping (value = "/editUa", method = RequestMethod.POST)
    public ModelAndView EditarUa(ModelMap map,
                        //datos a ingresar en UA
                        @RequestParam("nombre") String nombre,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("departamento") String departamento,
                        @RequestParam("provincia") String provincia,
                        @RequestParam("distrito") String distrito,
                        @RequestParam("competenciaR") String competenciaR,
                        @RequestParam("correo") String correo,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("celular") String celular,
                        @RequestParam("obs") String obs
                        ){
        
        Unidad ua = new Unidad();
        
        ua.setNombre(nombre);
        ua.setDireccion(direccion);
        ua.setDepartamento(departamento);
        ua.setProvincia(provincia);
        ua.setDistrito(distrito);
        ua.setCompetenciaRegional(competenciaR);
        ua.setCorreo(correo);
        ua.setTelefono(telefono);
        ua.setCelular(celular);
        ua.setObs(obs);
        
        ServicioPersonal.InsertUa(ua);
        
         map.put("listaUa",ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/ua/lista_ua",map);
    }
    
    @RequestMapping (value = "/updateUa", method = RequestMethod.POST)
    public ModelAndView UpdateUa(ModelMap map,
                        //datos a ingresar en UA
                        @RequestParam("id") int id,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("direccion") String direccion,
                        @RequestParam("departamento") String departamento,
                        @RequestParam("provincia") String provincia,
                        @RequestParam("distrito") String distrito,
                        @RequestParam("competenciaR") String competenciaR,
                        @RequestParam("correo") String correo,
                        @RequestParam("telefono") String telefono,
                        @RequestParam("celular") String celular,
                        @RequestParam("obs") String obs
                        
                        ){
        
        Unidad ua = new Unidad();
        ua = ServicioPersonal.getUa(id);
        ua.setNombre(nombre);
        ua.setDireccion(direccion);
        ua.setDepartamento(departamento);
        ua.setProvincia(provincia);
        ua.setDistrito(distrito);
        ua.setCompetenciaRegional(competenciaR);
        ua.setCorreo(correo);
        ua.setTelefono(telefono);
        ua.setCelular(celular);
        ua.setObs(obs);
        
        ServicioPersonal.UpdateUa(ua);
        
         map.put("listaUa",ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/ua/lista_ua",map);
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    
     ///////////////PERSONAL DE UA ///////////////////////////////////////////
    @RequestMapping (value = "/irListaPersonalUa", method = RequestMethod.POST)
    public ModelAndView ListaPersonalUa(ModelMap map, @RequestParam("ïdUA") int idUa){
    
        map.put("ua",ServicioPersonal.getUa(idUa));
        map.put("listaPersonalUa",ServicioPersonal.ListaPersonalUa(idUa));
        return new ModelAndView("/Personal/registros/ua/lista_ua_personal",map);
    }
     /////////////////////////////////////////////////////////////////////////////////
    ///////////////EDITAR PERSONAL UA ///////////////////////////////////////////
    @RequestMapping (value = "/EditarPersonalUa", method = RequestMethod.POST)
    public ModelAndView EditarPersonalUa(ModelMap map,@RequestParam("id") int id){
    
        
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(id);
        String fechaNac = format.dateToString(temp.getFechaNacimiento());
        String fechaIng = format.dateToString(temp.getFechaIngreso());
        String noUa = "deshabilitar";
        map.addAttribute("fechaNac", fechaNac);
        map.addAttribute("fechaIng", fechaIng);
        map.addAttribute("disabled",noUa );
        map.put("personal",temp);
        map.put("listaUa",ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/usuarios/editar_personal",map);
    }
    
    @RequestMapping (value = "/updatePersonalUa", method = RequestMethod.POST)
    public ModelAndView UpdatePersonalUa(ModelMap map,
                        //datos a ingresar en Personal
                        @RequestParam("idPers") int idPers,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("apellidoP") String apellidoP,
                        @RequestParam("apellidoM") String apellidoM,
                        @RequestParam("user") String user,
                        @RequestParam("pass") String pass,
                        @RequestParam("correoT") String correoT,
                        @RequestParam("correoP") String correoP,
                        @RequestParam("profesion") String profesion,
                        @RequestParam("grado") String grado,
                        @RequestParam("cargo") String cargo,
                        @RequestParam("dni") int dni,
                        @RequestParam("fechaNac") String fechaNac,
                        @RequestParam("regimen") String regimen,
                        @RequestParam("fechaIng") String fechaIng,
                        @RequestParam("domicilio") String domicilio,
                        @RequestParam("rol") String rol
                        //@RequestParam("ua") int ua
                        ){
        
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(idPers);
        
        temp.setNombre(nombre);
        temp.setApellidoP(apellidoP);
        temp.setApellidoM(apellidoM);
        temp.setUser(user);
        temp.setPass(pass);
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        temp.setFechaNacimiento(format.stringToDate(fechaNac));
        temp.setRegimen(regimen);
        temp.setFechaIngreso(format.stringToDate(fechaIng));
        temp.setDomicilio(domicilio);
        temp.setRol(rol);
        
        //Unidad temp2 = new Unidad();
        //temp2 = ServicioPersonal.getUa(ua);
        
        //temp.setUnidad(temp2);
        
        ServicioPersonal.UpdatePersonal(temp);
        map.put("ua",ServicioPersonal.getUa(temp.getUnidad().getIdunidad()));
        map.put("listaPersonalUa",ServicioPersonal.ListaPersonalUa(temp.getUnidad().getIdunidad()));
        return new ModelAndView("/Personal/registros/ua/lista_ua_personal",map);
    }
    /////////////////////////////////////////////////////////////////////////////////
     ///////////////ASIGNAR PERSONAL PASO 1 ///////////////////////////////////////////
    @RequestMapping (value = "/asignarPersonalUa", method = RequestMethod.POST)
    public ModelAndView asignarPersonalUa(ModelMap map, @RequestParam("idUa") int idUa){
    
        map.put("ua",ServicioPersonal.getUa(idUa));
        map.put("listaPersonalNoUa",ServicioPersonal.ListaPersonalNoUa(idUa));
        return new ModelAndView("/Personal/registros/ua/lista_asign_pers",map);
    }
    /////////////////////////////////////////////////////////////////////////////////
    ///////////////ASIGNAR PERSONAL PASO 2 ///////////////////////////////////////////
    @RequestMapping (value = "/asignarPersonalUa2", method = RequestMethod.POST)
    public ModelAndView asignarPersonalUa2(ModelMap map, @RequestParam("idUa") int idUa,@RequestParam("idPers") int idPers){
    
        Personal per = new Personal();
        Unidad ua = new Unidad();
        ua = ServicioPersonal.getUa(idUa);
        per = ServicioPersonal.getPersonal(idPers);
        
        per.setUnidad(ua);
        ServicioPersonal.UpdatePersonal(per);
        map.put("ua",ServicioPersonal.getUa(idUa));
        map.put("listaPersonalUa",ServicioPersonal.ListaPersonalUa(idUa));
        return new ModelAndView("/Personal/registros/ua/lista_ua_personal",map);
    }
    
     /////////////////////////////////////////////////////////////////////////////////
     ////////////////////// REGISTRAR/EDITAR PERSONAL ////////////////////////////////////
    @RequestMapping (value = "/irEditarPersonal", method = RequestMethod.GET)
    public ModelAndView IrEditarPersonal(ModelMap map){
    
        map.put("listaUa",ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/usuarios/editar_personal",map);
    }
    @RequestMapping (value = "/irEditarPersonal2", method = RequestMethod.POST)
    public ModelAndView IrEditarPersonal2(ModelMap map,@RequestParam("id") int id){
    
        
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(id);
        String fechaNac = format.dateToString(temp.getFechaNacimiento());
        String fechaIng = format.dateToString(temp.getFechaIngreso());
        map.addAttribute("fechaNac", fechaNac);
        map.addAttribute("fechaIng", fechaIng);
        map.put("personal",temp);
        map.put("listaUa",ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/usuarios/editar_personal",map);
    }
    
    @RequestMapping (value = "/editPersonal", method = RequestMethod.POST)
    public ModelAndView EditarPersonal(ModelMap map,
                        //datos a ingresar en Personal
                        @RequestParam("nombre") String nombre,
                        @RequestParam("apellidoP") String apellidoP,
                        @RequestParam("apellidoM") String apellidoM,
                        @RequestParam("user") String user,
                        @RequestParam("pass") String pass,
                        @RequestParam("correoT") String correoT,
                        @RequestParam("correoP") String correoP,
                        @RequestParam("profesion") String profesion,
                        @RequestParam("grado") String grado,
                        @RequestParam("cargo") String cargo,
                        @RequestParam("dni") int dni,
                        @RequestParam("fechaNac") String fechaNac,
                        @RequestParam("regimen") String regimen,
                        @RequestParam("fechaIng") String fechaIng,
                        @RequestParam("domicilio") String domicilio,
                        @RequestParam("rol") String rol,
                        @RequestParam("ua") int ua
                        ){
        
        Personal temp = new Personal();
        
        temp.setNombre(nombre);
        temp.setApellidoP(apellidoP);
        temp.setApellidoM(apellidoM);
        temp.setUser(user);
        temp.setPass(pass);
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        temp.setFechaNacimiento(format.stringToDate(fechaNac));
        temp.setRegimen(regimen);
        temp.setFechaIngreso(format.stringToDate(fechaIng));
        temp.setDomicilio(domicilio);
        temp.setRol(rol);
        
        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);
        
        temp.setUnidad(temp2);
        
        ServicioPersonal.InsertPersonal(temp);
        
         map.put("listaPersonal",ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/registros/usuarios/lista_personal",map);
    }
    
    @RequestMapping (value = "/updatePersonal", method = RequestMethod.POST)
    public ModelAndView UpdatePersonal(ModelMap map,
                        //datos a ingresar en Personal
                        @RequestParam("idPers") int idPers,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("apellidoP") String apellidoP,
                        @RequestParam("apellidoM") String apellidoM,
                        @RequestParam("user") String user,
                        @RequestParam("pass") String pass,
                        @RequestParam("correoT") String correoT,
                        @RequestParam("correoP") String correoP,
                        @RequestParam("profesion") String profesion,
                        @RequestParam("grado") String grado,
                        @RequestParam("cargo") String cargo,
                        @RequestParam("dni") int dni,
                        @RequestParam("fechaNac") String fechaNac,
                        @RequestParam("regimen") String regimen,
                        @RequestParam("fechaIng") String fechaIng,
                        @RequestParam("domicilio") String domicilio,
                        @RequestParam("rol") String rol,
                        @RequestParam("ua") int ua
                        ){
        
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(idPers);
        
        temp.setNombre(nombre);
        temp.setApellidoP(apellidoP);
        temp.setApellidoM(apellidoM);
        temp.setUser(user);
        temp.setPass(pass);
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        temp.setFechaNacimiento(format.stringToDate(fechaNac));
        temp.setRegimen(regimen);
        temp.setFechaIngreso(format.stringToDate(fechaIng));
        temp.setDomicilio(domicilio);
        temp.setRol(rol);
        
        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);
        
        temp.setUnidad(temp2);
        
        ServicioPersonal.UpdatePersonal(temp);
        
         map.put("listaPersonal",ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/registros/usuarios/lista_personal",map);
    }
    
    //// EDITAR SESION INFORMATIVA //////////////
    @RequestMapping (value = "/PersonalEditarSesion", method = RequestMethod.POST)
    public ModelAndView PersonalEditarSesion(ModelMap map,@RequestParam("idSesion") int id){
    
        
        Sesion temp = new Sesion();
        ArrayList<Personal> allPersonal = new ArrayList();
        ArrayList<Turno> allTurnos = new ArrayList();
        allTurnos = ServicioMain.turnosSesion(id);
        temp = ServicioPersonal.getSesion(id);
        allPersonal = ServicioPersonal.ListaPersonal();
        String fecha = format.dateToString(temp.getFecha());
        String hora = ts.HourToString(temp.getHora());
        
        
        map.put("listaTurnos", allTurnos);
        map.put("sesion",temp);
        map.put("listaPersonal",allPersonal);
        map.addAttribute("ts", ts);
        map.addAttribute("fecha", fecha);
        map.addAttribute("hora", hora);
        return new ModelAndView("/Personal/Informativa/lista_sesion",map);
    }
    
    
    
    
    
}
