/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.HiberFamilia;
import com.mimp.util.dateFormat;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
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

    @Resource(name = "HiberFamilia")
    private HiberFamilia ServicioFamilia = new HiberFamilia();

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
        if (ultfecha.getYear() < fechaactual.getYear()) {
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
        } else {
            String si = "SI";
            String no = "NO";

            Date fechaactual = new Date();
            Date ultfecha = new Date(10, 0, 01);
            for (Iterator iter = usuario.getFormularioSesions().iterator(); iter.hasNext();) {
                FormularioSesion form = (FormularioSesion) iter.next();
                if (ultfecha.before(form.getFechaSol())) {
                    ultfecha = form.getFechaSol();
                }
            }
            if (ultfecha.getYear() < fechaactual.getYear()) {
                map.addAttribute("sesion", no);
                map.addAttribute("taller", no);
                map.addAttribute("ficha", no);
                map.addAttribute("boton", 0);
                map.addAttribute("eval", no);
                map.addAttribute("espera", no);
                map.addAttribute("adop", no);
                map.addAttribute("postadop", no);
            } else {
                map.addAttribute("sesion", si);
                if (usuario.getConstancia().equals("")) {
                    map.addAttribute("taller", no);
                    map.addAttribute("ficha", no);
                    map.addAttribute("boton", 0);
                    map.addAttribute("eval", no);
                    map.addAttribute("espera", no);
                    map.addAttribute("adop", no);
                    map.addAttribute("postadop", no);
                } else {
                    map.addAttribute("taller", si);
                    for (Iterator iter2 = usuario.getFichaSolicitudAdopcions().iterator(); iter2.hasNext();) {
                        FichaSolicitudAdopcion form = (FichaSolicitudAdopcion) iter2.next();
                        if (form == null) {
                            map.addAttribute("ficha", no);
                            map.addAttribute("boton", 1);
                            map.addAttribute("eval", no);
                            map.addAttribute("espera", no);
                            map.addAttribute("adop", no);
                            map.addAttribute("postadop", no);
                        } else {
                            map.addAttribute("ficha", si);
                            map.addAttribute("boton", 0);
                            for (Iterator iter3 = usuario.getExpedienteFamilias().iterator(); iter3.hasNext();) {
                                ExpedienteFamilia exp = (ExpedienteFamilia) iter3.next();
                                Boolean flag = false;
                                for (Iterator iter4 = exp.getEvaluacions().iterator(); iter4.hasNext();) {
                                    Evaluacion eval = (Evaluacion) iter4.next();
                                    if (!eval.getEvalLegals().isEmpty()) {
                                        flag = true;
                                    }
                                }
                                if (exp.getEstado().equals("Apto") || flag) {
                                    map.addAttribute("eval", si);
                                    map.addAttribute("espera", si);
                                    if (!exp.getDesignacions().isEmpty()) {
                                        for (Iterator iter5 = exp.getDesignacions().iterator(); iter5.hasNext();) {
                                            Designacion deg = (Designacion) iter5.next();
                                            if (deg.getAceptacionConsejo() == 0) {
                                                map.addAttribute("adop", no);
                                                map.addAttribute("postadop", no);
                                            } else {
                                                map.addAttribute("adop", si);
                                                Boolean flag2 = false;
                                                for (Iterator iter6 = exp.getEvaluacions().iterator(); iter6.hasNext();) {
                                                    Evaluacion eval = (Evaluacion) iter6.next();
                                                    for(Iterator iter7 = eval.getResolucions().iterator(); iter7.hasNext();){
                                                        Resolucion resol = (Resolucion) iter7.next();
                                                        if(resol.getTipo().equals("Adopción")){
                                                            flag2 = true;
                                                        }
                                                    }
                                                }
                                                if(flag2){
                                                    map.addAttribute("postadop", si);
                                                } else {
                                                    map.addAttribute("postadop", no);
                                                }
                                            }
                                        }
                                    } else {
                                        map.addAttribute("adop", no);
                                        map.addAttribute("postadop", no);
                                    }
                                } else {
                                    map.addAttribute("eval", no);
                                    map.addAttribute("espera", no);
                                    map.addAttribute("adop", no);
                                    map.addAttribute("postadop", no);
                                }
                            }
                        }
                    }
                }
            }
        }
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

    @RequestMapping("/Fcambiarcontra")
    public ModelAndView Fcambiarcontra(ModelMap map, HttpSession session, @RequestParam("oldpass") String oldpass, @RequestParam("newpass") String newpass, @RequestParam("newpassconf") String newpassconf) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        String mensaje = "";
        if (usuario == null) {
            mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            oldpass = DigestUtils.md5Hex(oldpass);
            if (usuario.getPass().equals(oldpass)) {
                if (newpass.equals(newpassconf)) {
                    newpass = DigestUtils.md5Hex(newpass);
                    usuario.setPass(newpass);
                    ServicioFamilia.CambiaPass(usuario);
                    mensaje = "La contraseña se ha cambiado con exito.";
                } else {
                    mensaje = "Las contraseñas no coinciden. Favor de reescribir la nueva contraseña.";
                }
            } else {
                mensaje = "Contraseña de usuario incorrecta. Ingrese nuevamente.";
            }
        }

        String pagina = "/Familia/contra_familia";
        map.addAttribute("mensaje", mensaje);
        return new ModelAndView(pagina, map);
    }

    //Ver informacion actual de la familia
    @RequestMapping("/FactDatos/opc1")
    public ModelAndView FactDatos1(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        dateFormat format = new dateFormat();
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                String fechaMatri = format.dateToString(ifa.getFechaMatrimonio());
                map.addAttribute("fechaMatri", fechaMatri);
                map.addAttribute("estCivil", ifa.getEstadoCivil().charAt(0));
                for (Iterator iter2 = ifa.getAdoptantes().iterator(); iter2.hasNext();) {
                    Adoptante adop = (Adoptante) iter2.next();
                    if (adop.getSexo() == 'F') {
                        map.put("adop", adop);
                        String fechanac = format.dateToString(adop.getFechaNac());
                        map.addAttribute("fechanac", fechanac);
                    }
                }
            }
        }
        String pagina = "/Familia/Act_datos/datos_ella";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FactDatos/opc2")
    public ModelAndView FactDatos2(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        dateFormat format = new dateFormat();
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                String fechaMatri = format.dateToString(ifa.getFechaMatrimonio());
                map.addAttribute("fechaMatri", fechaMatri);
                map.addAttribute("estCivil", ifa.getEstadoCivil().charAt(0));
                for (Iterator iter2 = ifa.getAdoptantes().iterator(); iter2.hasNext();) {
                    Adoptante adop = (Adoptante) iter2.next();
                    if (adop.getSexo() == 'M') {
                        map.put("adop", adop);
                        String fechanac = format.dateToString(adop.getFechaNac());
                        map.addAttribute("fechanac", fechanac);
                    }
                }
            }
        }
        String pagina = "/Familia/Act_datos/datos_el";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FactDatos/opc3")
    public ModelAndView FactDatos3(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        String pagina = "/Familia/Act_datos/datos_fam";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FactDatos/opc4")
    public ModelAndView FactDatos4(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                map.put("ifa", ifa);
            }
        }
        String pagina = "/Familia/Act_datos/datos_vivienda";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FactDatos/opc5")
    public ModelAndView FactDatos5(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                map.put("ifa", ifa);
            }
        }
        String pagina = "/Familia/Act_datos/datos_nna";
        return new ModelAndView(pagina, map);
    }
}
