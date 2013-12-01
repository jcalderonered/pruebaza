/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.controllers;

import com.mimp.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.*;
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
    
    @Resource(name="HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
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
        return new ModelAndView("/Personal/Informativa/lista_sesion",map);
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
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg",map);
    }
    
    @RequestMapping (value = "/car", method = RequestMethod.GET)
    public ModelAndView Car(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/registros/car/lista_car",map);
    }
    
    @RequestMapping (value = "/ua", method = RequestMethod.GET)
    public ModelAndView Ua(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
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
        return new ModelAndView("/Personal/registros/usuarios/lista_personal",map);
    }
    
    @RequestMapping (value = "/organismo", method = RequestMethod.GET)
    public ModelAndView Organismo(ModelMap map){
    
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
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
        
        aut.setTipo(tipo);
        aut.setUser(user);
        aut.setPass(pass);
        
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
        temp.setUser(user);
        temp.setPass(pass);
        temp.getEntidad().setNombre(nombre);
        temp.getEntidad().setPais(pais);
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
    
}