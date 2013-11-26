/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.Helper;
import javax.annotation.Resource;
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
public class main {
    
    @Resource(name="Helper")
    private Helper Servicio = new Helper();
    
    @RequestMapping("/")
    public String hello() {
        return "login";
    }
    
    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public ModelAndView login(ModelMap map, @RequestParam("email") String email,@RequestParam("password") String pass){
       
         
        Personal per1 = new Personal();
       per1 = (Personal) Servicio.usuario(email, pass);
        String mensaje = "Los datos ingresados son: email" + email + " y contraseña" + pass + "!";
        
        map.addAttribute("nombre", per1.getNombre());
        map.addAttribute("personal",per1);
        return new ModelAndView ("contacto",map);
        
    }
    
    @RequestMapping (value = "/lista", method = RequestMethod.GET)
    public ModelAndView lista(ModelMap map){
    
        List<Personal> lista = Servicio.listaPersonal();
        map.put("listap", lista);
        return new ModelAndView("contacto",map);
    }
   
}
