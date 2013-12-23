/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.HiberEtapa;
import com.mimp.util.*;
import com.mimp.hibernate.HiberMain;
import com.mimp.hibernate.HiberPersonal;
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
public class nna {
    
    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();
    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();
    
    @RequestMapping(value = "/nna", method = RequestMethod.GET)
    public ModelAndView Nna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        return new ModelAndView("/Personal/nna/lista_nna", map);
    }
    
    @RequestMapping(value = "/nnaPrioritarios", method = RequestMethod.GET)
    public ModelAndView nnaPrioritarios(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        return new ModelAndView("/Personal/nna/lista_nna_prior", map);
    }
    
    @RequestMapping(value = "/nnaSeguimiento", method = RequestMethod.GET)
    public ModelAndView nnaSeguimiento(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        return new ModelAndView("/Personal/nna/lista_nna_seg", map);
    }
    
    @RequestMapping(value = "/agregarNna", method = RequestMethod.GET)
    public ModelAndView agregarNna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }
    
    
}
