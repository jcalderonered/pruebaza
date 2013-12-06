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
public class familia {
    
    @RequestMapping("/inicioFam")
    public String InicioFam() {
        return "/Familia/inicio_familia";
    }
    
    @RequestMapping("/FactDatos")
    public String Act_datos() {
        return "/Familia/Act_datos/datos_ella";
    }
    
    @RequestMapping("/Finscripcion")
    public String Finscripcion() {
        //FALTA
        return "/Familia/Act_datos/datos_ella";
    }
    
    @RequestMapping("/Festado")
    public String Festado() {
        return "/Familia/estado_proc";
    }
    
    @RequestMapping("/Fcontra")
    public String Fcontra() {
        return "/Familia/contra_familia";
    }
}
