/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.HiberMain;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
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
public class familia {

    @RequestMapping("/inicioFam")
    public ModelAndView InicioFam(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        String pagina = "/Familia/inicio_familia";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FactDatos")
    public ModelAndView Act_datos(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        String pagina = "/Familia/Act_datos/datos_ella";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Finscripcion")
    public ModelAndView Finscripcion(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Date fechaactual = new Date();
        Date ultfecha = new Date(10, 0, 01);
        for (Iterator iter = usuario.getFormularioSesions().iterator(); iter.hasNext();) {
            FormularioSesion form = (FormularioSesion) iter.next();
            if (ultfecha.before(form.getFechaSol())) {
                ultfecha = form.getFechaSol();
            }
        }
        String pagina;
        if (ultfecha.getYear() < fechaactual.getYear()){
            pagina = "/Familia/Inscripcion/inscripcion_sesionInfo";
        } else {
            pagina = "/Familia/Inscripcion/inscripcion_Talleres";
        }
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Festado")
    public ModelAndView Festado(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String pagina = "/Familia/estado_proc";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fcontra")
    public ModelAndView Fcontra(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        String pagina = "/Familia/contra_familia";
        return new ModelAndView(pagina, map);
    }
}
