/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.HiberFamilia;
import com.mimp.hibernate.HiberMain;
import com.mimp.hibernate.HiberOrganismo;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
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

    @Resource(name = "HiberOrganismo")
    private HiberOrganismo ServicioOrganismo = new HiberOrganismo();

    @RequestMapping("/inicioEnt")
    public ModelAndView InicioEnt(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        String pagina = "/Entidad/inicio_ent";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping(value = "/contraEnt", method = RequestMethod.GET)
    public ModelAndView ContraEnt(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Entidad/contra_ent", map);
    }

    //////////////////////// NAVEGACION ///////////////
    @RequestMapping(value = "/inicioEnt", method = RequestMethod.GET)
    public ModelAndView inicioEnt(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Entidad/inicio_ent", map);
    }

    @RequestMapping("/Orgcambiarcontra")
    public ModelAndView Orgcambiarcontra(ModelMap map, HttpSession session, @RequestParam("oldpass") String oldpass, @RequestParam("newpass") String newpass, @RequestParam("newpassconf") String newpassconf) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        String mensaje = "";
        if (usuario == null) {
            mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            oldpass = DigestUtils.sha512Hex(oldpass);
            if (usuario.getPass().equals(oldpass)) {
                if (newpass.equals(newpassconf)) {
                    newpass = DigestUtils.sha512Hex(newpass);
                    usuario.setPass(newpass);
                    ServicioOrganismo.CambiaPass(usuario);
                    mensaje = "La contraseña se ha cambiado con exito.";
                } else {
                    mensaje = "Las contraseñas no coinciden. Favor de reescribir la nueva contraseña.";
                }
            } else {
                mensaje = "Contraseña de usuario incorrecta. Ingrese nuevamente.";
            }
        }
        String pagina = "/Entidad/contra_ent";
        map.addAttribute("mensaje", mensaje);
        return new ModelAndView(pagina, map);
    }

    @RequestMapping(value = "/listaFam", method = RequestMethod.GET)
    public ModelAndView ListaFam(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaFam", ServicioOrganismo.ListaFam(usuario.getIdentidad()));
        map.put("listaAdop", ServicioOrganismo.ListaAdopPorEnt(usuario.getIdentidad()));
        map.put("listaExp", ServicioOrganismo.ListaExpFamPorEnt(usuario.getIdentidad()));
        return new ModelAndView("/Entidad/lista_fam", map);
    }

    @RequestMapping(value = "/Einfo", method = RequestMethod.POST)
    public ModelAndView Einfo(ModelMap map, HttpSession session, @RequestParam("idInfo") int idInfo) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("LaAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "F"));
        map.put("ElAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "M"));
        map.put("idInfo", idInfo);
        return new ModelAndView("/Entidad/info_fam/info_ella", map);
    }

    @RequestMapping(value = "/ElAdop", method = RequestMethod.GET)
    public ModelAndView ElAdop(ModelMap map, HttpSession session, @RequestParam("idInfo") int idInfo) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("LaAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "F"));
        map.put("ElAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "M"));
        map.put("idInfo", idInfo);
        return new ModelAndView("/Entidad/info_fam/info_el", map);
    }

    @RequestMapping(value = "/ElaAdop", method = RequestMethod.GET)
    public ModelAndView ElaAdop(ModelMap map, HttpSession session, @RequestParam("idInfo") int idInfo) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("LaAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "F"));
        map.put("ElAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "M"));
        map.put("idInfo", idInfo);
        return new ModelAndView("/Entidad/info_fam/info_ella", map);
    }

    @RequestMapping(value = "/infoNNA", method = RequestMethod.GET)
    public ModelAndView infoNNA(ModelMap map, HttpSession session, @RequestParam("idInfo") int idInfo) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("LaAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "F"));
        map.put("ElAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "M"));
        map.put("InfoNNA", ServicioOrganismo.InfoFamilia(idInfo));
        map.put("idInfo", idInfo);
        return new ModelAndView("/Entidad/info_fam/info_ant_nna", map);
    }

    @RequestMapping(value = "/infoExp", method = RequestMethod.GET)
    public ModelAndView infoExp(ModelMap map, HttpSession session, @RequestParam("idInfo") int idInfo) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("LaAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "F"));
        map.put("ElAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "M"));
        map.put("InfoNNA", ServicioOrganismo.InfoFamilia(idInfo));
        map.put("InfoExp", ServicioOrganismo.ExpPorIDFamilia(ServicioOrganismo.InfoFamilia(idInfo).getFamilia().getIdfamilia()));
        map.put("idInfo", idInfo);
        return new ModelAndView("/Entidad/info_fam/info_registro", map);
    }

    @RequestMapping(value = "/estadoProc", method = RequestMethod.POST)
    public ModelAndView estadoProc(ModelMap map, HttpSession session, @RequestParam("idfam") int idFam) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        
        map.put("idInfo", idFam);
        return new ModelAndView("/Entidad/estado_proc", map);
    }
}
