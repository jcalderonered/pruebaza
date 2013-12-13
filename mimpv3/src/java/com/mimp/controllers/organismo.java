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
public class organismo {
    // REVISAR MAPEO
    @RequestMapping("/inicioEnt")
    public String InicioEnt() {
        return "/Entidad/inicio_ent";
    }
    @RequestMapping("/inicioEnt2")
    public ModelAndView InicioEnt(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if(usuario == null){
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        String pagina = "/Entidad/inicio_ent";
        return new ModelAndView(pagina, map);
    }
}
