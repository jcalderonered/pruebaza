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
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();

    
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
    
    @RequestMapping(value = "/ConstanciaInformativa", method = RequestMethod.GET)
    public ModelAndView ConstanciaInformativa(ModelMap map, 
                                              @RequestParam("idFamilia") long id,
                                              @RequestParam("constancia") String nombre,
                                              HttpSession session) {
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

    

}