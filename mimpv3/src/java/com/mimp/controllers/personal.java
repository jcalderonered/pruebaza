/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.controllers;

import com.mimp.bean.Personal;
import com.mimp.hibernate.Helper;
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
    
    @Resource(name="Helper")
    private Helper Servicio = new Helper();
    
    @RequestMapping (value = "/info", method = RequestMethod.POST)
    public ModelAndView Info(ModelMap map,@RequestParam("id") int temp){
    
        //List<Personal> lista = Servicio.listaPersonal();
        map.addAttribute("id", temp);
        return new ModelAndView("info",map);
    }
    
}
