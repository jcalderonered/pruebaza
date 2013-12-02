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
    
    @Resource(name="HiberMain")
    private HiberMain ServicioMain = new HiberMain();
    
    @RequestMapping("/")
    public String hello() {
        return "login";
    }
    
    @RequestMapping("/salir")
    public String Salir() {
        return "login";
    }
    
    @RequestMapping("/inicio")
    public String Inicio() {
        return "login";
    }
    
    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public ModelAndView login(ModelMap map, @RequestParam("email") String email,@RequestParam("password") String pass){
       
         String pagina = "";
        
        ArrayList aux = ServicioMain.usuario(email, pass);
        String mensaje = "Los datos ingresados son: email" + email + " y contraseña" + pass + "!";
        if (aux.get(0) == "personal"){
            pagina = "/Personal/inicio_personal";
        }else if (aux.get(0)=="familia"){
            pagina = "/Familia/inicio_familia";
        }else if (aux.get(0)=="representante" || aux.get(0)=="autoridad"){
            pagina = "/Entidad/inicio_ent";
        }else{
            pagina = "contacto";
        }
        map.addAttribute("usuario", aux.get(1));
        return new ModelAndView (pagina,map);
        
    }
    
    @RequestMapping("/SesionInfInicio")
    public String SesionInfInicio() {
        
        return "/Inscripcion/inscripcion_inicio";
    }
    
    @RequestMapping("/SesionInfElegirEstado")
    public String SesionInfElegirEstado() {
        
        return "/Inscripcion/inscripcion_sesion2";
    }
    
    @RequestMapping(value = "/SesionInfEstado2", method = RequestMethod.POST)
    public String SesionInfElegirEstado2(@RequestParam("estado") String estado) {
        
        if(estado.equals("casados")){
        
            return "/Inscripcion/inscripcion_sesion3_gru";
            
        }else{
            
        return "/Inscripcion/inscripcion_sesion3_ind";
        
        }
        
    }
    
   
}
