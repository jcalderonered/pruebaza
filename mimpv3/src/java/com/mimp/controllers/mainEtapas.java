/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.*;
import com.mimp.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author john
 */
@Controller
public class mainEtapas {

    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();
    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name = "HiberNna")
    private HiberNna ServicioNna = new HiberNna();
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();
    long tempIdNnaRegular;
    long nnaEditarEstudioCaso;
    ArrayList<ExpedienteFamilia> listaMatching = new ArrayList();
    ArrayList<ExpedienteFamilia> listaEstudioCaso = new ArrayList();
    ArrayList<Designacion> allDesig = new ArrayList();
    Nna nnaPrioritario = new Nna();
    @RequestMapping(value = "/fametap", method = RequestMethod.GET)
    public ModelAndView FamEtap(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
        ArrayList<Familia> allFamilias = new ArrayList();
        allFamilias = servicioEtapa.getListaFamilias();
        map.put("listaFamilias",allFamilias);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_formativa", map);
    }
    
    @RequestMapping(value = "/ConstanciaInformativa", method = RequestMethod.POST)
    public ModelAndView ConstanciaInformativa(ModelMap map, 
                                              @RequestParam("idFamilia") long idFamilia,
                                              @RequestParam("constancia") String constancia,
                                              HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Familia tempFam = new Familia();
        tempFam = servicioEtapa.getFamilia(idFamilia);
        tempFam.setConstancia(constancia);
        
        servicioEtapa.UpdateFamilia(tempFam);
        
        ArrayList<Familia> allFamilias = new ArrayList();
        allFamilias = servicioEtapa.getListaFamilias();
        map.put("listaFamilias",allFamilias);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_formativa", map);
    }
    
    @RequestMapping(value = "/EtapaEvalNac", method = RequestMethod.GET)
    public ModelAndView EtapaEvalNac(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        
    }

    @RequestMapping(value = "/EtapaEvalInter", method = RequestMethod.GET)
    public ModelAndView EtapaEvalInter(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        
    }
    
    @RequestMapping(value = "/PersonalAgregarEvalPsicologica", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarEvalPsicologica(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "idPsicologica", required = false) String idPsicologica
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idPsicologica.equals("")){
            long temp = Long.parseLong(idPsicologica);
            tempEval = servicioEtapa.getEvaluacion(temp);
            map.put("psicologica",tempEval);
        }
        
        map.put("idExpediente",idExpediente);
        map.put("familia",familia);
        map.put("origen",origen);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());   
        
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_psic", map);
        
    }
    
    @RequestMapping(value = "/PersonalCrearEvalPsicologicaNac", method = RequestMethod.POST)
    public ModelAndView PersonalCrearEvalPsicologicaNac(ModelMap map, HttpSession session,
                                                       @ModelAttribute("idExpediente") long idExpediente,      
                                                       @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
                                                       @RequestParam(value = "personal", required = false) long personal,
                                                       @RequestParam(value = "resultado", required = false) String resultado,
                                                       @RequestParam(value = "fechaResul", required = false) String fechaResul,
                                                       @RequestParam(value = "obs", required = false) String obs,
                                                       @RequestParam("origen") String origen
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(servicioEtapa.getExpedienteFamilia(idExpediente));
        tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
        tempEval.setPersonal(ServicioPersonal.getPersonal(personal));
        tempEval.setResultado(resultado);
        tempEval.setFechaResultado(df.stringToDate(fechaResul));
        tempEval.setObservacion(obs);
        tempEval.setTipo("psicologica");
        
        servicioEtapa.crearEvaluacion(tempEval);
        
        
        
        
        if(origen.equals("nacional")){
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }else{
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        }
        
    }
    
    @RequestMapping(value = "/PersonalUpdateEvalPsicologicaNac", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateEvalPsicologicaNac(ModelMap map, HttpSession session,
                                                       @RequestParam("idEvalPsicologica") long idEvalPsicologica,      
                                                       @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
                                                       @RequestParam(value = "personal", required = false) long personal,
                                                       @RequestParam(value = "resultado", required = false) String resultado,
                                                       @RequestParam(value = "fechaResul", required = false) String fechaResul,
                                                       @RequestParam(value = "obs", required = false) String obs,
                                                       @RequestParam("origen") String origen
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion evalPsicologica = new Evaluacion();
        evalPsicologica = servicioEtapa.getEvaluacion(idEvalPsicologica);
        
        evalPsicologica.setFechaAsignacion(df.stringToDate(fechaAsig));
        evalPsicologica.setPersonal(ServicioPersonal.getPersonal(personal));
        evalPsicologica.setResultado(resultado);
        evalPsicologica.setFechaResultado(df.stringToDate(fechaResul));
        evalPsicologica.setObservacion(obs);
        
        servicioEtapa.updateEvaluacion(evalPsicologica);
        
        
        if(origen.equals("nacional")){
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }else{
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        }
        
    }
    
    @RequestMapping(value = "/PersonalAgregarEvalSocial", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarEvalSocial(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "idSocial", required = false) String idSocial
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idSocial.equals("")){
            long temp = Long.parseLong(idSocial);
            tempEval = servicioEtapa.getEvaluacion(temp);
            map.put("social",tempEval);
        }
        
        map.put("idExpediente",idExpediente);
        map.put("familia",familia);
        map.put("origen",origen);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());   
        
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_soc", map);
        
    }
    
    @RequestMapping(value = "/PersonalCrearEvalSocialNac", method = RequestMethod.POST)
    public ModelAndView PersonalCrearEvalSocialNac(ModelMap map, HttpSession session,
                                                       @ModelAttribute("idExpediente") long idExpediente, 
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
                                                       @RequestParam(value = "personal", required = false) long personal,
                                                       @RequestParam(value = "resultado", required = false) String resultado,
                                                       @RequestParam(value = "fechaResul", required = false) String fechaResul,
                                                       @RequestParam(value = "obs", required = false) String obs
                                                       
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(servicioEtapa.getExpedienteFamilia(idExpediente));
        tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
        tempEval.setPersonal(ServicioPersonal.getPersonal(personal));
        tempEval.setResultado(resultado);
        tempEval.setFechaResultado(df.stringToDate(fechaResul));
        tempEval.setObservacion(obs);
        tempEval.setTipo("social");
        
        servicioEtapa.crearEvaluacion(tempEval);
        
        
        if(origen.equals("nacional")){
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }else{
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        }
    }
    
    @RequestMapping(value = "/PersonalUpdateEvalSocialNac", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateEvalSocialNac(ModelMap map, HttpSession session,
                                                       @RequestParam("idEvalSocial") long idEvalSocial, 
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
                                                       @RequestParam(value = "personal", required = false) long personal,
                                                       @RequestParam(value = "resultado", required = false) String resultado,
                                                       @RequestParam(value = "fechaResul", required = false) String fechaResul,
                                                       @RequestParam(value = "obs", required = false) String obs
                                                       
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion evalSocial = new Evaluacion();
        evalSocial = servicioEtapa.getEvaluacion(idEvalSocial);
        
        evalSocial.setFechaAsignacion(df.stringToDate(fechaAsig));
        evalSocial.setPersonal(ServicioPersonal.getPersonal(personal));
        evalSocial.setResultado(resultado);
        evalSocial.setFechaResultado(df.stringToDate(fechaResul));
        evalSocial.setObservacion(obs);
        
        servicioEtapa.updateEvaluacion(evalSocial);
        
        
        if(origen.equals("nacional")){
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }else{
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        }
        
    }
    
    @RequestMapping(value = "/PersonalAgregarEvalLegal", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarEvalLegal(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "idLegal", required = false) String idLegal
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idLegal.equals("")){
            long temp = Long.parseLong(idLegal);
            tempEval = servicioEtapa.getLegal(temp);
             map.put("legal",tempEval);
        }
        
        map.put("idExpediente",idExpediente);
        map.put("familia",familia);
       
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());   
        
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_legal", map);
        
    }
    
    @RequestMapping(value = "/PersonalCrearEvalLegalNac", method = RequestMethod.POST)
    public ModelAndView PersonalCrearEvalLegalNac(ModelMap map, HttpSession session,
                                                       @ModelAttribute("idExpediente") long idExpediente, 
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
                                                       @RequestParam(value = "personal", required = false) long personal,
                                                       @RequestParam(value = "resultado", required = false) String resultado,
                                                       @RequestParam(value = "fechaResul", required = false) String fechaResul,
                                                       @RequestParam(value = "obs", required = false) String obs
                                                       
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(servicioEtapa.getExpedienteFamilia(idExpediente));
        tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
        tempEval.setPersonal(ServicioPersonal.getPersonal(personal));
        tempEval.setResultado(resultado);
        tempEval.setFechaResultado(df.stringToDate(fechaResul));
        tempEval.setObservacion(obs);
        tempEval.setTipo("legal");
        
        servicioEtapa.crearEvaluacion(tempEval);
        
        
        if(origen.equals("nacional")){
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }else{
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        }
        
    }
    
     @RequestMapping(value = "/PersonalUpdateEvalLegalNac", method = RequestMethod.POST)
     public ModelAndView PersonalUpdateEvalLegalNac(ModelMap map, HttpSession session,
                                                       @RequestParam("idEvalLegal") long idEvalLegal, 
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
                                                       @RequestParam(value = "personal", required = false) long personal,
                                                       @RequestParam(value = "resultado", required = false) String resultado,
                                                       @RequestParam(value = "fechaResul", required = false) String fechaResul,
                                                       @RequestParam(value = "obs", required = false) String obs
                                                       
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion evalLegal = new Evaluacion();
        evalLegal = servicioEtapa.getEvaluacion(idEvalLegal);
        
        evalLegal.setFechaAsignacion(df.stringToDate(fechaAsig));
        evalLegal.setPersonal(ServicioPersonal.getPersonal(personal));
        evalLegal.setResultado(resultado);
        evalLegal.setFechaResultado(df.stringToDate(fechaResul));
        evalLegal.setObservacion(obs);
        
        servicioEtapa.updateEvaluacion(evalLegal);
        
        
        if(origen.equals("nacional")){
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }else{
            
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional","evaluacion");
        map.put("ListaExpedientes",allListaExpedientes);
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
        }
        
    }
     
    @RequestMapping(value = "/PersonalRegistrarResolucionEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalRegistrarResolucionEvaluacion(ModelMap map, HttpSession session,
                                                       @RequestParam("idLegal") long idLegal,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam("familia") String familia
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        map.put("idLegal",idLegal); 
        map.put("familia",familia); 
        map.put("origen",origen); 
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/reg_resol", map);
        
    } 
    
    @RequestMapping(value = "/PersonalEditarResolucionEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalEditarResolucionEvaluacion(ModelMap map, HttpSession session,
                                                       @RequestParam("idResol") long idResol,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam("familia") String familia
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        map.put("resolucion",servicioEtapa.getResolucion(idResol)); 
        map.put("familia",familia); 
        map.put("origen",origen); 
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/reg_resol", map);
        
    } 
    
    @RequestMapping(value = "/PersonalCrearResolEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalCrearResolEvaluacion(ModelMap map, HttpSession session,
                                                       @RequestParam("idLegal") long idLegal,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("numResol") String numResol,
                                                       @RequestParam("tipo") String tipo,
                                                       @RequestParam("fechaResol") String fechaResol,
                                                       @RequestParam("fechaNot") String fechaNot
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        tempEval = servicioEtapa.getLegal(idLegal);
        
        Resolucion tempResol = new Resolucion();
        tempResol.setEvaluacion(tempEval);
        tempResol.setNumero(numResol);
        tempResol.setTipo(tipo);
        tempResol.setFechaResol(df.stringToDate(fechaResol));
        tempResol.setFechaNotificacion(df.stringToDate(fechaNot));
        
        servicioEtapa.crearResolEvaluacion(tempResol);
        tempEval = servicioEtapa.getLegal(idLegal);
        if(tipo != null && tipo.equals("apto")){
        ExpedienteFamilia tempFam = new ExpedienteFamilia();
        tempFam = tempEval.getExpedienteFamilia();
        tempFam.setEstado("espera");
        servicioEtapa.updateExpedienteFamilia(tempFam);
        }
        map.put("familia",familia);
        map.put("legal",tempEval);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal()); 
        map.put("origen",origen); 
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_legal", map);
        
    }
    
    
    
    @RequestMapping(value = "/PersonalUpdateResolEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateResolEvaluacion(ModelMap map, HttpSession session,
                                                       @RequestParam("idResolucion") long idResolucion,
                                                       @RequestParam("origen") String origen,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("numResol") String numResol,
                                                       @RequestParam("tipo") String tipo,
                                                       @RequestParam("fechaResol") String fechaResol,
                                                       @RequestParam("fechaNot") String fechaNot
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
       
        Resolucion tempResol = new Resolucion();
        tempResol = servicioEtapa.getResolucion(idResolucion);
        tempResol.setNumero(numResol);
        tempResol.setTipo(tipo);
        tempResol.setFechaResol(df.stringToDate(fechaResol));
        tempResol.setFechaNotificacion(df.stringToDate(fechaNot));
        
        servicioEtapa.updateResolEvaluacion(tempResol);
        tempResol = servicioEtapa.getResolucion(idResolucion);
        if(tipo != null && tipo.equals("apto")){
        ExpedienteFamilia tempFam = new ExpedienteFamilia();
        tempFam = tempResol.getEvaluacion().getExpedienteFamilia();
        tempFam.setEstado("espera");
        servicioEtapa.updateExpedienteFamilia(tempFam);
        }
        map.put("familia",familia);
        map.put("legal",servicioEtapa.getLegal(tempResol.getEvaluacion().getIdevaluacion()));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());   
        map.put("origen",origen); 
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_legal", map);
        
    }
    
    /*------- REGISTRAR DESIGNACIÓN REGULARES---------*/
    
    @RequestMapping(value = "/registrarDesignacion", method = RequestMethod.POST)
    public ModelAndView registrarDesignacion(ModelMap map, HttpSession session,
                                                       @RequestParam("idNna") long idNna
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        listaMatching.clear();
        Nna tempNna = new Nna();
        ArrayList<ExpedienteFamilia> allExp = new ArrayList();
        
        tempNna = ServicioNna.getNna(idNna);
        allExp = servicioEtapa.listaInfoFamilias();
        
        for (ExpedienteFamilia expedienteFamilia : allExp) {
            for (InfoFamilia infoFamilia : expedienteFamilia.getFamilia().getInfoFamilias()) {
                 if(tempNna.getEdadAnhos() >= infoFamilia.getExpectativaEdadMin() && 
                         tempNna.getEdadAnhos() >= infoFamilia.getExpectativaEdadMin() &&
                         tempNna.getSexo().equals(infoFamilia.getExpectativaGenero())) {
                     
                     if( tempNna.getIncesto() == infoFamilia.getNnaIncesto() &&
                         tempNna.getMental() == infoFamilia.getNnaMental() &&
                         tempNna.getEpilepsia() == infoFamilia.getNnaEpilepsia() &&
                         tempNna.getAbuso() == infoFamilia.getNnaAbuso() &&
                         tempNna.getSifilis() == infoFamilia.getNnaSifilis() &&
                         tempNna.getSeguiMedico() == infoFamilia.getNnaSeguiMedico() &&
                         tempNna.getOperacion() == infoFamilia.getNnaHiperactivo() &&
                         tempNna.getHiperactivo() == infoFamilia.getNnaHiperactivo() ){
                         
                         listaMatching.add(expedienteFamilia);
                     }
                 }
            }
        }
        tempIdNnaRegular = idNna;
        map.put("listaMatching",listaMatching);
        map.put("df",df);
        return new ModelAndView("/Personal/nna/reg_desig", map);
        
    } 
    
    @RequestMapping(value = "/agregarExp", method = RequestMethod.GET)
    public ModelAndView agregarExp(ModelMap map, HttpSession session
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("df",df);
        return new ModelAndView("/Personal/nna/agregar_exp", map);
        
    } 
    
    @RequestMapping(value = "/buscarExpediente", method = RequestMethod.POST)
    public ModelAndView buscarExpediente(ModelMap map, HttpSession session,
                                                       @RequestParam("exp") String exp
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
        listaBusqueda = servicioEtapa.listaInfoFamilias(exp);
        
        map.put("df",df);
        map.put("listaMatching",listaMatching);
        map.put("listaBusqueda",listaBusqueda);
        return new ModelAndView("/Personal/nna/agregar_exp", map);
        
    } 
    
    @RequestMapping(value = "/agregarExpediente", method = RequestMethod.POST)
    public ModelAndView agregarExpediente(ModelMap map, HttpSession session, long[] idExpediente
            
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if(idExpediente != null){
        for (long l : idExpediente) {
            ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
            listaMatching.add(tempExp);
        }
        }
        map.put("listaMatching",listaMatching);
        map.put("df",df);
        return new ModelAndView("/Personal/nna/reg_desig", map);
        
    } 
    
    @RequestMapping(value = "/insertarDesignación", method = RequestMethod.POST)
    public ModelAndView insertarDesignación(ModelMap map, HttpSession session, long[] idExpediente,
                                            @RequestParam(value = "fecha", required = false) String fecha,
                                            @RequestParam(value = "numDesig", required = false) String numDesig
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if(idExpediente != null && idExpediente.length < 4 && fecha != null && numDesig != null){
        for (long l : idExpediente) {
            ExpedienteFamilia tempExp = servicioEtapa.getExpedienteFamilia(l);
            Nna tempNna = ServicioNna.getNna(tempIdNnaRegular);
            Designacion tempDesign = new Designacion();
            tempDesign.setExpedienteFamilia(tempExp);
            tempDesign.setNna(tempNna);
            tempDesign.setPersonal(usuario);
            tempDesign.setFechaPropuesta(df.stringToDate(fecha));
            tempDesign.setNDesignacion(Long.parseLong(numDesig));
            if(idExpediente.length == 1) tempDesign.setTipoPropuesta("directa");
            if(idExpediente.length == 2) tempDesign.setTipoPropuesta("dupla");
            if(idExpediente.length == 3) tempDesign.setTipoPropuesta("terna");
            tempDesign.setAceptacionConsejo(Short.parseShort("1"));
            servicioEtapa.crearDesignacion(tempDesign);
            tempExp.setEstado("designado");
            servicioEtapa.updateExpedienteFamilia(tempExp);
            ExpedienteNna tempExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
            tempExpNna.setEstado("desig");
            Date ahora = new Date();
            tempExpNna.setFechaEstado(ahora);
            ServicioNna.updateExpNna(tempExpNna);
            
        }
        map.put("listaDesignaciones",servicioEtapa.getListaDesignaciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
        
        }else if(fecha == null || numDesig == null){
            map.put("mensaje","Debe colocar una fecha y un número");
            map.put("listaMatching",listaMatching);
            map.put("df",df);
            return new ModelAndView("/Personal/nna/reg_desig", map);
        }else if(idExpediente != null && idExpediente.length >= 4){
            map.put("mensaje","Debe seleccionar como máximo Tres expedientes");
            map.put("listaMatching",listaMatching);
            map.put("df",df);
            return new ModelAndView("/Personal/nna/reg_desig", map);
        }else if(idExpediente == null){
            map.put("mensaje","Debe seleccionar al menos un expediente");
            map.put("listaMatching",listaMatching);
            map.put("df",df);
            return new ModelAndView("/Personal/nna/reg_desig", map);
        }
        
        map.put("listaMatching",listaMatching);
        map.put("df",df);
        return new ModelAndView("/Personal/nna/reg_desig", map);
        
    } 
    
    /*------- REGISTRAR DESIGNACIÓN PRIORITARIOS---------*/
    
    @RequestMapping(value = "/agregarEstudio", method = RequestMethod.POST)
    public ModelAndView agregarEstudio(ModelMap map, HttpSession session,
                                                       @RequestParam("idNna") long idNna
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        nnaPrioritario = ServicioNna.getNna(idNna);
        listaEstudioCaso.clear();
        return new ModelAndView("/Personal/nna/reg_estudio", map);
    }
    
    @RequestMapping(value = "/insertarEstudio", method = RequestMethod.POST)
    public ModelAndView insertarEstudio(ModelMap map, HttpSession session,
                                                       @RequestParam( value = "orden", required = false) String orden,
                                                       @RequestParam( value = "agregar", required = false) String agregar,
                                                       @RequestParam( value = "eliminar", required = false) String eliminar,
                                                       @RequestParam( value = "registrar", required = false) String registrar,
                                                       int[] delete,
                                                       Long[] prioridad,
                                                       String[] fecha
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (agregar != null) {
            map.put("df",df);
            return new ModelAndView("/Personal/nna/agregar_exp_prioritario", map);
        }
        if (eliminar != null && delete != null) {
            for (int i : delete) {
                listaEstudioCaso.remove(i);
            }
               
                map.put("df",df);
                map.put("listaEstudioCaso",listaEstudioCaso);
                return new ModelAndView("/Personal/nna/reg_estudio", map);
        }
        if (registrar != null) {
            for (int i = 0;i<listaEstudioCaso.size() ; i++) {
                EstudioCaso tempEst = new EstudioCaso();
                tempEst.setNna(nnaPrioritario);
                tempEst.setExpedienteFamilia(listaEstudioCaso.get(i));
                ExpedienteFamilia expFam1 = listaEstudioCaso.get(i);
                expFam1.setEstado("estudio");
                servicioEtapa.updateExpedienteFamilia(expFam1);
                tempEst.setOrden(orden);
                tempEst.setFechaEstudio(df.stringToDate(fecha[i]));
                tempEst.setPrioridad(prioridad[i]);
                
                servicioEtapa.crearEstudioCaso(tempEst);
                
                
            }
            map.put("listaEstudios",servicioEtapa.getListaEstudioCaso(nnaPrioritario.getIdnna()));
            return new ModelAndView("/Personal/nna/lista_estudios", map);
        }
        
        return new ModelAndView("/Personal/nna/reg_estudio", map);
    }
    
    @RequestMapping(value = "/buscarExpedientePrioritario", method = RequestMethod.POST)
    public ModelAndView buscarExpedientePrioritario(ModelMap map, HttpSession session,
                                                       @RequestParam("exp") String exp
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
        listaBusqueda = servicioEtapa.listaInfoFamilias(exp);
        
        map.put("df",df);
        map.put("listaEstudioCaso",listaEstudioCaso);
        map.put("listaBusqueda",listaBusqueda);
        return new ModelAndView("/Personal/nna/agregar_exp_prioritario", map);
        
    } 
    
    @RequestMapping(value = "/agregarExpedientePrioritario", method = RequestMethod.POST)
    public ModelAndView agregarExpedientePrioritario(ModelMap map, HttpSession session, long[] idExpediente
            
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if(idExpediente != null){
        for (long l : idExpediente) {
            ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
            listaEstudioCaso.add(tempExp);
        }
        }
        map.put("listaEstudioCaso",listaEstudioCaso);
        map.put("df",df);
        return new ModelAndView("/Personal/nna/reg_estudio", map);
        
    } 
    
    @RequestMapping(value = "/verEstudios", method = RequestMethod.POST)
    public ModelAndView verEstudios(ModelMap map, HttpSession session,
                                                       @RequestParam("idNna") long idNna
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        nnaEditarEstudioCaso = idNna;
        map.put("listaEstudios",servicioEtapa.getListaEstudioCaso(idNna));
        return new ModelAndView("/Personal/nna/lista_estudios", map);
        
    } 
    
    @RequestMapping(value = "/EditarEstudio", method = RequestMethod.POST)
    public ModelAndView EditarEstudio(ModelMap map, HttpSession session,
                                                       @RequestParam("orden") String orden
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("df",df);
        map.put("nna",nnaEditarEstudioCaso);
        map.put("listaEstudios",servicioEtapa.getListaEstudioCasoOrden(orden));
        return new ModelAndView("/Personal/nna/edit_estudio", map);
        
    } 
    
    @RequestMapping(value = "/ActualizarEstudio", method = RequestMethod.POST)
    public ModelAndView ActualizarEstudio(ModelMap map, HttpSession session,
                                                       @RequestParam("orden") String orden,
                                                       @RequestParam("idEstudio") long idEstudio,
                                                       @RequestParam("resultado") String resultado
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if(resultado.equals("acep")){
            ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
            tempEst.setResultado(resultado);
            servicioEtapa.updateEstudioCaso(tempEst);
            allEstudioCaso = servicioEtapa.getListaEstudioCasoOrden(orden);
            for (EstudioCaso estudioCaso : allEstudioCaso) {
                if(estudioCaso.getResultado() == null){
                estudioCaso.setResultado("noobs");
                servicioEtapa.updateEstudioCaso(estudioCaso);
                ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
                tempExp = estudioCaso.getExpedienteFamilia();
                tempExp.setEstado("espera");
                servicioEtapa.updateExpedienteFamilia(tempExp);
                }
            }
            map.put("df",df);
            map.put("nna",nnaEditarEstudioCaso);
            map.put("listaEstudios",servicioEtapa.getListaEstudioCasoOrden(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        }else if(resultado.equals("noacep")){
            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
            tempEst.setResultado(resultado);
            servicioEtapa.updateEstudioCaso(tempEst);
            ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
            tempExp = tempEst.getExpedienteFamilia();
            tempExp.setEstado("espera");
            servicioEtapa.updateExpedienteFamilia(tempExp);
            map.put("df",df);
            map.put("nna",nnaEditarEstudioCaso);
            map.put("listaEstudios",servicioEtapa.getListaEstudioCasoOrden(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        }else if(resultado.equals("noobs")){
            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
            tempEst.setResultado(resultado);
            servicioEtapa.updateEstudioCaso(tempEst);
            ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
            tempExp = tempEst.getExpedienteFamilia();
            tempExp.setEstado("espera");
            servicioEtapa.updateExpedienteFamilia(tempExp);
            map.put("df",df);
            map.put("nna",nnaEditarEstudioCaso);
            map.put("listaEstudios",servicioEtapa.getListaEstudioCasoOrden(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        }else{
        
        EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
        tempEst.setResultado(resultado);
        servicioEtapa.updateEstudioCaso(tempEst);
        
        map.put("df",df);
        map.put("nna",nnaEditarEstudioCaso);
        map.put("listaEstudios",servicioEtapa.getListaEstudioCasoOrden(orden));
        return new ModelAndView("/Personal/nna/edit_estudio", map);
        }
    } 
    
    @RequestMapping(value = "/generarDesignacionPrioritario", method = RequestMethod.POST)
    public ModelAndView generarDesignacionPrioritario(ModelMap map, HttpSession session,
                                                       @RequestParam("orden") String orden,
                                                       @RequestParam("fechaSolicitud") String fechaSolicitud,
                                                       @RequestParam("numDesig") String numDesig,
                                                       @RequestParam("idEstudio") long idEstudio,
                                                       @RequestParam("fechaPropuesta") String fechaPropuesta
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if( numDesig == null || fechaSolicitud == null || fechaPropuesta == null){
            
            map.put("mensaje","Debe llenar los datos correctamente");
            map.put("df",df);
            map.put("listaEstudios",servicioEtapa.getListaEstudioCasoOrden(orden));
        
        }
        
        EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
        tempEst.setFechaSolAdop(df.stringToDate(fechaSolicitud));
        servicioEtapa.updateEstudioCaso(tempEst);
        
            ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
            Nna tempNna = ServicioNna.getNna(nnaEditarEstudioCaso);
            Designacion tempDesign = new Designacion();
            tempDesign.setExpedienteFamilia(tempExp);
            tempDesign.setNna(tempNna);
            tempDesign.setPersonal(usuario);
            tempDesign.setFechaPropuesta(df.stringToDate(fechaPropuesta));
            tempDesign.setNDesignacion(Long.parseLong(numDesig));
            tempDesign.setTipoPropuesta("directa");
            tempDesign.setAceptacionConsejo(Short.parseShort("1"));
            servicioEtapa.crearDesignacion(tempDesign);
            tempExp.setEstado("designado");
            servicioEtapa.updateExpedienteFamilia(tempExp);
            ExpedienteNna tempExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
            tempExpNna.setEstado("desig");
            Date ahora = new Date();
            tempExpNna.setFechaEstado(ahora);
            ServicioNna.updateExpNna(tempExpNna);
            
        map.put("listaDesignaciones",servicioEtapa.getListaDesignaciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);    
        
        
    } 
    
    
    
    /*------- ETAPA DESIGNACION---------*/
    
    @RequestMapping(value = "/EtapaDesig", method = RequestMethod.GET)
    public ModelAndView EtapaDesig(ModelMap map, HttpSession session
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        map.put("listaDesignaciones",servicioEtapa.getListaDesignaciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
        
    } 
    
    @RequestMapping(value = "/designacionConsejo", method = RequestMethod.POST)
    public ModelAndView designacionConsejo(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        allDesig.clear();
        allDesig = servicioEtapa.getListaDesignaciones(idNna);
        Nna newNna = allDesig.get(0).getNna();
        String propuesta = allDesig.get(0).getTipoPropuesta();
              
        map.put("nna",newNna);
        map.put("listaDesignaciones",allDesig);
        map.put("propuesta",propuesta);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/desig_consejo", map);
        
    } 
    
    @RequestMapping(value = "/aceptacionConsejo", method = RequestMethod.POST)
    public ModelAndView aceptacionConsejo(ModelMap map, HttpSession session, 
                                          @RequestParam("idNna") long idNna,
                                          @RequestParam("fechaConsejo") String fechaConsejo,
                                          @RequestParam("prioridad") long[] prioridad,
                                          @RequestParam("obs") String obs
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        if(prioridad != null){
            ExpedienteNna tempExpNna = ServicioNna.getExpNna(idNna);
            tempExpNna.setEstado("adop");
            Date now = new Date();
            tempExpNna.setFechaEstado(now);
            ServicioNna.updateExpNna(tempExpNna);
            for (int i = 0; i < prioridad.length; i++) {
                Designacion tempDesg = allDesig.get(i);
                tempDesg.setFechaConsejo(df.stringToDate(fechaConsejo));
                tempDesg.setAceptacionConsejo(Short.parseShort("0"));
                tempDesg.setObs(obs);
                tempDesg.setPrioridad(prioridad[i]);
                ExpedienteFamilia tempExpFam = tempDesg.getExpedienteFamilia();
                tempExpFam.setEstado("adopcion");
                servicioEtapa.updateExpedienteFamilia(tempExpFam);
                servicioEtapa.updateDesignacion(tempDesg);
            }
        
        map.put("listaAdopciones",servicioEtapa.getListaAdopciones());    
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        }
        
        map.put("listaDesignaciones",servicioEtapa.getListaDesignaciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
        
    } 
    
    /*  ETAPA  DE ADOPCION */
    @RequestMapping(value = "/EtapaAdopcion", method = RequestMethod.GET)
    public ModelAndView EtapaAdopcion(ModelMap map, HttpSession session
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        map.put("listaAdopciones",servicioEtapa.getListaAdopciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        
    } 
    
    @RequestMapping(value = "/evalEmpatia", method = RequestMethod.POST)
    public ModelAndView evalEmpatia(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam(value = "idEmpatia", required = false) String idEmpatia
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idEmpatia.equals("")){
            long temp = Long.parseLong(idEmpatia);
            tempEval = servicioEtapa.getEvaluacion(temp);
            map.put("empatia",tempEval);
        }
        
        map.put("idExpediente",idExpediente);
        map.put("familia",familia);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());   
        
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/eval_emp", map);
        
    }
    
    @RequestMapping(value = "/crearEvalEmpatia", method = RequestMethod.POST)
    public ModelAndView crearEvalEmpatia(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("personal") long personal,
                                                       @RequestParam("resultado") String resultado,
                                                       @RequestParam("fechaEval") String fechaEval,
                                                       @RequestParam("obs") String obs
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        ExpedienteFamilia EF = new ExpedienteFamilia();
        Personal tempP = new Personal();
        EF = servicioEtapa.getExpedienteFamilia(idExpediente);
        tempP = ServicioPersonal.getPersonal(personal);
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(EF);
        tempEval.setPersonal(tempP);
        tempEval.setTipo("empatia");
        tempEval.setResultado(resultado);
        tempEval.setFechaResultado(df.stringToDate(fechaEval));
        tempEval.setObservacion(obs);
        
        servicioEtapa.crearEvaluacion(tempEval);
        
        map.put("listaAdopciones",servicioEtapa.getListaAdopciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        
    }
    
    @RequestMapping(value = "/resolEvalEmpatia", method = RequestMethod.POST)
    public ModelAndView resolEvalEmpatia(ModelMap map, HttpSession session,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("idNna") long idNna,
                                                       @RequestParam("idEmpatia") String idEmpatia
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idEmpatia.equals("")){
            long temp = Long.parseLong(idEmpatia);
            if (servicioEtapa.getResolucion2(temp) != null ) {
            Resolucion tempResol = servicioEtapa.getResolucion(temp);
            map.put("resolucion",tempResol);
            }
        }
        map.put("idEmpatia",idEmpatia);
        map.put("idNna",idNna);
        map.put("familia",familia); 
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/reg_resol_emp", map);
        
    }
    
    @RequestMapping(value = "/crearResolEmpatia", method = RequestMethod.POST)
    public ModelAndView crearResolEmpatia(ModelMap map, HttpSession session,
                                                       @RequestParam("idEmpatia") long idEmpatia,
                                                       @RequestParam("idNna") long idNna,
                                                       @RequestParam("numResol") String numResol,
                                                       @RequestParam("tipo") String tipo,
                                                       @RequestParam("fechaResol") String fechaResol,
                                                       @RequestParam( value = "eliminar", required = false ) String eliminar
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        tempEval = servicioEtapa.getEvaluacion2(idEmpatia);
        Designacion tempDesig = new Designacion();
        tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), idNna);
        
        
        if(eliminar != null){
            Familia tempFamilia = tempEval.getExpedienteFamilia().getFamilia();
            tempFamilia.setHabilitado(Short.parseShort("1"));
            ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
            tempExpFam.setEstado("eliminado");
            tempDesig.setAceptacionConsejo(Short.parseShort("3"));
            servicioEtapa.UpdateFamilia(tempFamilia);
            servicioEtapa.updateExpedienteFamilia(tempExpFam);
            servicioEtapa.updateDesignacion(tempDesig);
            if (tempDesig.getTipoPropuesta().equals("directa")){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
            
        }
           
        if(eliminar == null){
            if(tipo.equals("sinefecto")){
                servicioEtapa.deleteEvaluacion(tempEval);
                ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                tempExpFam.setEstado("espera");
                servicioEtapa.updateExpedienteFamilia(tempExpFam);
                tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                servicioEtapa.updateDesignacion(tempDesig);
                if (tempDesig.getTipoPropuesta().equals("directa")){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
            }else {
                Resolucion tempResol = new Resolucion();
                tempResol.setEvaluacion(tempEval);
                tempResol.setNumero(numResol);
                tempResol.setTipo(tipo);
                tempResol.setFechaResol(df.stringToDate(fechaResol));
                servicioEtapa.crearResolEvaluacion(tempResol);
            
            }
        
        }
        
        map.put("listaAdopciones",servicioEtapa.getListaAdopciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        
    }
    
    @RequestMapping(value = "/evalInforme", method = RequestMethod.POST)
    public ModelAndView evalInforme(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam(value = "idInforme", required = false) String idInforme
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idInforme.equals("")){
            long temp = Long.parseLong(idInforme);
            tempEval = servicioEtapa.getEvaluacion(temp);
            map.put("informe",tempEval);
        }
        
        map.put("idExpediente",idExpediente);
        map.put("familia",familia);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());   
        
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/inf_col_fam", map);
        
    }
    
    @RequestMapping(value = "/crearEvalInforme", method = RequestMethod.POST)
    public ModelAndView crearEvalInforme(ModelMap map, HttpSession session,
                                                       @RequestParam("idExpediente") long idExpediente,
                                                       @RequestParam("personal") long personal,
                                                       @RequestParam("resultado") String resultado,
                                                       @RequestParam("fechaEval") String fechaEval,
                                                       @RequestParam("obs") String obs
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        ExpedienteFamilia EF = new ExpedienteFamilia();
        Personal tempP = new Personal();
        EF = servicioEtapa.getExpedienteFamilia(idExpediente);
        tempP = ServicioPersonal.getPersonal(personal);
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(EF);
        tempEval.setPersonal(tempP);
        tempEval.setTipo("informe");
        tempEval.setResultado(resultado);
        tempEval.setFechaResultado(df.stringToDate(fechaEval));
        tempEval.setObservacion(obs);
        
        servicioEtapa.crearEvaluacion(tempEval);
        
        map.put("listaAdopciones",servicioEtapa.getListaAdopciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        
    }
    
    @RequestMapping(value = "/resolEvalInforme", method = RequestMethod.POST)
    public ModelAndView resolEvalInforme(ModelMap map, HttpSession session,
                                                       @RequestParam("familia") String familia,
                                                       @RequestParam("idInforme") String idInforme,
                                                       @RequestParam("idNna") String idNna,
                                                       @RequestParam("idEmpatia") String idEmpatia
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        if (!idInforme.equals("")){
            long temp = Long.parseLong(idInforme);
            if (servicioEtapa.getResolucion2(temp) != null){
            Resolucion tempResol = servicioEtapa.getResolucion(temp);
            map.put("resolucion",tempResol);
            }
        }
        map.put("idInforme",idInforme);
        map.put("idEmpatia",idEmpatia);
        map.put("idNna",idNna);
        map.put("familia",familia); 
        map.put("df",df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/reg_resol_inf", map);
        
    }
    
    @RequestMapping(value = "/crearResolInforme", method = RequestMethod.POST)
    public ModelAndView crearResolInforme(ModelMap map, HttpSession session,
                                                       @RequestParam("idInforme") long idInforme,
                                                       @RequestParam("idNna") long idNna,
                                                       @RequestParam("idEmpatia") long idEmpatia,
                                                       @RequestParam("numResol") String numResol,
                                                       @RequestParam("tipo") String tipo,
                                                       @RequestParam("fechaResol") String fechaResol,
                                                       @RequestParam( value = "eliminar", required = false ) String eliminar
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = new Evaluacion();
        tempEval = servicioEtapa.getEvaluacion2(idInforme);
        Designacion tempDesig = new Designacion();
        tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), idNna);
        
        if(eliminar != null){
            Familia tempFamilia = tempEval.getExpedienteFamilia().getFamilia();
            tempFamilia.setHabilitado(Short.parseShort("1"));
            ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
            tempExpFam.setEstado("eliminado");
            tempDesig.setAceptacionConsejo(Short.parseShort("3"));
            servicioEtapa.UpdateFamilia(tempFamilia);
            servicioEtapa.updateExpedienteFamilia(tempExpFam);
            servicioEtapa.updateDesignacion(tempDesig);
            
            if (tempDesig.getTipoPropuesta().equals("directa")){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
            }
            if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
            }
            if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
            }
            
        }
           
        if(eliminar == null){
            if(tipo.equals("revocacion")){
                Resolucion tempResol = servicioEtapa.getResolucion2(idEmpatia);
                Evaluacion tempEmpatia = servicioEtapa.getEvaluacion(idEmpatia);
                servicioEtapa.deleteResolucion(tempResol);
                servicioEtapa.deleteEvaluacion(tempEmpatia);
                ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                servicioEtapa.deleteEvaluacion(tempEval);
                tempExpFam.setEstado("espera");
                servicioEtapa.updateExpedienteFamilia(tempExpFam);
                tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                servicioEtapa.updateDesignacion(tempDesig);
                
                if (tempDesig.getTipoPropuesta().equals("directa")){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3){
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("eval");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                }
                }else {
                Resolucion tempResol = new Resolucion();
                tempResol.setEvaluacion(tempEval);
                tempResol.setNumero(numResol);
                tempResol.setTipo(tipo);
                tempResol.setFechaResol(df.stringToDate(fechaResol));
                servicioEtapa.crearResolEvaluacion(tempResol);
                ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                tempExpFam.setEstado("post");
                tempDesig.setAceptacionConsejo(Short.parseShort("2"));
                servicioEtapa.updateExpedienteFamilia(tempExpFam);
                servicioEtapa.updateDesignacion(tempDesig);
                PostAdopcion tempPost = new PostAdopcion();
                tempPost.setFamilia(tempEval.getExpedienteFamilia().getFamilia());
                tempPost.setFechaResolucion(df.stringToDate(fechaResol));
                servicioEtapa.crearPostAdopcion(tempPost);
                ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                ExpNna.setEstado("arch");
                Date ahora = new Date();
                ExpNna.setFechaEstado(ahora);
                ServicioNna.updateExpNna(ExpNna);
                ArrayList<Designacion> tempDesigs = new ArrayList();
                tempDesigs = servicioEtapa.getListaDesignaciones2(idNna);
                for (Designacion designacion : tempDesigs) {
                         designacion.setAceptacionConsejo(Short.parseShort("3"));
                         ExpedienteFamilia tempExpFam2 = designacion.getExpedienteFamilia();
                         tempExpFam2.setEstado("espera");
                         servicioEtapa.updateExpedienteFamilia(tempExpFam2);
                         servicioEtapa.updateDesignacion(designacion);
                }
                
                map.put("listaPost",servicioEtapa.getListaPostAdopcion());
                return new ModelAndView("/Personal/Buscador_etapa/etapa_post/etapa_post", map);
            }
        
        }
        
        map.put("listaAdopciones",servicioEtapa.getListaAdopciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        
    }
    
    /*  ETAPA  POST ADOPCION */
    @RequestMapping(value = "/EtapaPostAdopcion", method = RequestMethod.GET)
    public ModelAndView EtapaPostAdopcion(ModelMap map, HttpSession session
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        map.put("listaPost",servicioEtapa.getListaPostAdopcion());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/etapa_post", map);
        
    } 
    
    @RequestMapping(value = "/UpdatePostAdopcion", method = RequestMethod.POST)
    public ModelAndView UpdatePostAdopcion(ModelMap map, HttpSession session,
                                           @RequestParam("idPost") long idPost,
                                           @RequestParam("familia") String familia
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        PostAdopcion newPost = servicioEtapa.getPostAdopcion(idPost);
        map.put("df",df);
        map.put("post",newPost);
        map.put("familia",familia);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/expediente_post", map);
        
    } 
    
    @RequestMapping(value = "/EditarExpedientePost", method = RequestMethod.POST)
    public ModelAndView EditarExpedientePost(ModelMap map, HttpSession session,
                                           @RequestParam("idPost") long idPost,
                                           @RequestParam("numInformes") Long numInformes
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        PostAdopcion newPost = servicioEtapa.getPostAdopcion(idPost);
        newPost.setNumeroInformes(numInformes);
        servicioEtapa.updatePostAdopcion(newPost);
        
        map.put("listaPost",servicioEtapa.getListaPostAdopcion());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/etapa_post", map);
        
    } 
    
    @RequestMapping(value = "/verInformesPost", method = RequestMethod.POST)
    public ModelAndView verInformesPost(ModelMap map, HttpSession session,
                                           @RequestParam("idPost") long idPost,
                                           @RequestParam("familia") String familia,
                                           @RequestParam("numInformes") int numInformes
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList<InformePostAdoptivo>(numInformes);
        ArrayList<InformePostAdoptivo> informesTabla = new ArrayList();
        informesTabla = servicioEtapa.getListaInformesPost(idPost);
        
        if(informesTabla.isEmpty()){
            for (int i = 0 ; i < numInformes ; i++){
                    InformePostAdoptivo temp = new InformePostAdoptivo();
                    allInformePostAdoptivo.add(temp);
            }
            map.put("listaInformes",allInformePostAdoptivo);
        }else{
            for (InformePostAdoptivo informePostAdoptivo : informesTabla) {
                int temp = Integer.parseInt(informePostAdoptivo.getNumeroInforme()) - 1;
                if(temp > allInformePostAdoptivo.size()){
                    for (int i = allInformePostAdoptivo.size(); i <= temp ; i++){
                        InformePostAdoptivo tempIP = new InformePostAdoptivo();
                        if (i == temp){
                            allInformePostAdoptivo.add(informePostAdoptivo);
                        }
                        else{
                        allInformePostAdoptivo.add(i,tempIP);
                        }
                  }
                }else{
                allInformePostAdoptivo.add(temp,informePostAdoptivo);
                }
            }
            /*
            if (informesTabla.size() < numInformes){
                for (int i = 0 ; i < numInformes ; i++){
                    if(allInformePostAdoptivo.get(i) == null){
                    InformePostAdoptivo temp = new InformePostAdoptivo();
                    allInformePostAdoptivo.add(i,temp);
                    }
            }
            }
            */
            map.put("listaInformes",allInformePostAdoptivo);
        }
        map.put("familia",familia);
        map.put("idPost",idPost);
        map.put("numInformes",numInformes);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/informes_general", map);
        
    } 
    
    @RequestMapping(value = "/EditarInforme", method = RequestMethod.POST)
    public ModelAndView EditarInforme(ModelMap map, HttpSession session,
                                           @RequestParam("idInforme") String idInforme,
                                           @RequestParam(value = "ultimo" , required = false) String ultimo,
                                           @RequestParam("familia") String familia,
                                           @RequestParam("num") String num,
                                           @RequestParam("idPost") long idPost,
                                           @RequestParam("numInformes") int numInformes
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        if (!idInforme.equals("")){
             long id = Long.parseLong(idInforme);
             InformePostAdoptivo temp = servicioEtapa.getInformePost(id);
             map.put("informe", temp);
             map.put("ultimo",ultimo);
             map.put("familia",familia);
             map.put("num",num);
             map.put("df",df);
             map.put("listaPersonal", ServicioPersonal.ListaPersonal());
             map.put("idPost",idPost);
             map.put("numInformes",numInformes);
        }else{
             map.put("familia",familia);
             map.put("ultimo",ultimo);
             map.put("num",num);
             map.put("df",df);
             map.put("listaPersonal", ServicioPersonal.ListaPersonal());
             map.put("idPost",idPost);
             map.put("numInformes",numInformes);
        }
        
        map.put("listaPost",servicioEtapa.getListaPostAdopcion());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/informe_post", map);
        
    } 
    
    @RequestMapping(value = "/InsertarInforme", method = RequestMethod.POST)
    public ModelAndView InsertarInforme(ModelMap map, HttpSession session,
                                           @RequestParam("idInforme") String idInforme,
                                           @RequestParam("num") String num,
                                           @RequestParam(value = "estado", required = false) String estado,
                                           @RequestParam(value = "fechaProyectado", required = false) String fechaProyectado, 
                                           @RequestParam(value = "fechaRecepcion", required = false) String fechaRecepcion,
                                           @RequestParam(value = "personal", required = false) long personal,
                                           @RequestParam(value = "fechaInforme", required = false) String fechaInforme,
                                           @RequestParam(value = "fechaActa", required = false) String fechaActa,
                                           @RequestParam(value = "obs", required = false) String obs,
                                           @RequestParam("idPost") long idPost,
                                           @RequestParam("familia") String familia,
                                           @RequestParam("numInformes") int numInformes
                                                       ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
       
        if (!idInforme.equals("")){
             long id = Long.parseLong(idInforme);
             InformePostAdoptivo temp = servicioEtapa.getInformePost(id);
             temp.setNumeroInforme(num);
            temp.setEstado(estado);
            if (fechaProyectado != null) temp.setFechaRecepcionProyectado(df.stringToDate(fechaProyectado));
            if (fechaProyectado == null) temp.setFechaRecepcionProyectado(null);
            if (fechaRecepcion != null) temp.setFechaRecepcion(df.stringToDate(fechaRecepcion));
            if (fechaRecepcion == null) temp.setFechaRecepcionProyectado(null);
            if (fechaInforme != null) temp.setFechaInforme(df.stringToDate(fechaInforme));
            if (fechaInforme == null) temp.setFechaInforme(null);
            if (fechaActa != null) temp.setFechaActa(df.stringToDate(fechaActa));
            if (fechaActa == null) temp.setFechaActa(null);
            Personal pers = ServicioPersonal.getPersonal(personal);
            temp.setPersonal(pers);
            temp.setObs(obs);
            servicioEtapa.updateInformePost(temp);
             
        }else{
            InformePostAdoptivo tempPost = new InformePostAdoptivo();
            tempPost.setNumeroInforme(num);
            tempPost.setEstado(estado);
            if (fechaProyectado != null) tempPost.setFechaRecepcionProyectado(df.stringToDate(fechaProyectado));
            if (fechaProyectado == null) tempPost.setFechaRecepcionProyectado(null);
            if (fechaRecepcion != null) tempPost.setFechaRecepcion(df.stringToDate(fechaRecepcion));
            if (fechaRecepcion == null) tempPost.setFechaRecepcionProyectado(null);
            if (fechaInforme != null) tempPost.setFechaInforme(df.stringToDate(fechaInforme));
            if (fechaInforme == null) tempPost.setFechaInforme(null);
            if (fechaActa != null) tempPost.setFechaActa(df.stringToDate(fechaActa));
            if (fechaActa == null) tempPost.setFechaActa(null);
            Personal pers = ServicioPersonal.getPersonal(personal);
            tempPost.setPersonal(pers);
            tempPost.setObs(obs);
            PostAdopcion tempPostAdopcion = new PostAdopcion();
            tempPostAdopcion = servicioEtapa.getPostAdopcion(idPost);
            tempPost.setPostAdopcion(tempPostAdopcion);
            servicioEtapa.crearInformePost(tempPost);
        }
        
        ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList<InformePostAdoptivo>(numInformes);
        ArrayList<InformePostAdoptivo> informesTabla = new ArrayList();
        informesTabla = servicioEtapa.getListaInformesPost(idPost);
        
        if(informesTabla.isEmpty()){
            for (int i = 0 ; i < numInformes ; i++){
                    InformePostAdoptivo temp = new InformePostAdoptivo();
                    allInformePostAdoptivo.add(temp);
            }
            map.put("listaInformes",allInformePostAdoptivo);
        }else{
            for (InformePostAdoptivo informePostAdoptivo : informesTabla) {
                int temp = Integer.parseInt(informePostAdoptivo.getNumeroInforme()) - 1;
                if(temp > allInformePostAdoptivo.size()){
                    for (int i = allInformePostAdoptivo.size(); i <= temp ; i++){
                        InformePostAdoptivo tempIP = new InformePostAdoptivo();
                        if (i == temp){
                            allInformePostAdoptivo.add(informePostAdoptivo);
                        }
                        else{
                        allInformePostAdoptivo.add(i,tempIP);
                        }
                  }
                }else{
                allInformePostAdoptivo.add(temp,informePostAdoptivo);
                }
            }
            /*
            if (informesTabla.size() < numInformes){
                for (int i = 0 ; i < numInformes ; i++){
                    if(allInformePostAdoptivo.get(i) == null){
                    InformePostAdoptivo temp = new InformePostAdoptivo();
                    allInformePostAdoptivo.add(i,temp);
                    }
            }
            }
            */
            map.put("listaInformes",allInformePostAdoptivo);
        }
        map.put("familia",familia);
        map.put("idPost",idPost);
        map.put("numInformes",numInformes);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/informes_general", map);
        
    } 
    
}