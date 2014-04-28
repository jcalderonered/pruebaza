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
    public ModelAndView Orgcambiarcontra_POST(ModelMap map, HttpSession session,
            @RequestParam("oldpass") String oldpass,
            @RequestParam("newpass") String newpass,
            @RequestParam("newpassconf") String newpassconf) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        String mensaje = "";
        if (usuario == null) {
            mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        session.setAttribute("oldpass", oldpass);
        session.setAttribute("newpass", newpass);
        session.setAttribute("newpassconf", newpassconf);

        return new ModelAndView("redirect:/Orgcambiarcontra", map);
    }

    @RequestMapping(value = "/Orgcambiarcontra", method = RequestMethod.GET)
    public ModelAndView Orgcambiarcontra_GET(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        String mensaje = "";
        if (usuario == null) {
            mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("oldpass") != null && session.getAttribute("newpass") != null
                && session.getAttribute("newpassconf") != null) {
            String oldpass = (String) session.getAttribute("oldpass");
            String newpass = (String) session.getAttribute("newpass");
            String newpassconf = (String) session.getAttribute("newpassconf");

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

            String pagina = "/Entidad/contra_ent";
            map.addAttribute("mensaje", mensaje);

            session.removeAttribute("oldpass");
            session.removeAttribute("newpass");
            session.removeAttribute("newpassconf");

            return new ModelAndView(pagina, map);
        } else {
            return new ModelAndView("/Entidad/inicio_ent", map);

        }

    }

    @RequestMapping(value = "/listaFam", method = RequestMethod.GET)
    public ModelAndView ListaFam(ModelMap map, HttpSession session) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaFam", ServicioOrganismo.ListaFamDeEnt(usuario.getIdentidad()));
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

        Adoptante LaAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "f");
        Adoptante ElAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "m");
        String doc = "";
        if (LaAdop.getTipoDoc() != null) {
            doc = String.valueOf(LaAdop.getTipoDoc());
        }
        map.put("LaAdop", LaAdop);
        map.put("ElAdop", ElAdop);
        map.put("idInfo", idInfo);
        map.put("doc", doc);
        if (LaAdop.getIdadoptante() != 0) {
            map.addAttribute("estCivil", LaAdop.getInfoFamilia().getEstadoCivil().charAt(0));
        }
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

        Adoptante LaAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "f");
        Adoptante ElAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "m");
        String doc = "";
        if (ElAdop.getTipoDoc() != null) {
            doc = String.valueOf(ElAdop.getTipoDoc());
        }

        map.put("LaAdop", LaAdop);
        map.put("ElAdop", ElAdop);
        map.put("idInfo", idInfo);
        map.put("doc", doc);
        if (ElAdop.getIdadoptante() != 0) {
            map.addAttribute("estCivil", ElAdop.getInfoFamilia().getEstadoCivil().charAt(0));
        }
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

        Adoptante LaAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "f");
        Adoptante ElAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "m");
        String doc = "";
        if (LaAdop.getTipoDoc() != null) {
            doc = String.valueOf(LaAdop.getTipoDoc());
        }
        map.put("LaAdop", LaAdop);
        map.put("ElAdop", ElAdop);
        map.put("idInfo", idInfo);
        map.put("doc", doc);
        if (LaAdop.getIdadoptante() != 0) {
            map.addAttribute("estCivil", LaAdop.getInfoFamilia().getEstadoCivil().charAt(0));
        }
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

        Adoptante LaAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "f");
        Adoptante ElAdop = ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "m");
        String doc = "";
        if (LaAdop.getTipoDoc() != null) {
            doc = String.valueOf(LaAdop.getTipoDoc());
        }
        InfoFamilia infoNNA = ServicioOrganismo.InfoFamilia(idInfo);

        map.put("LaAdop", LaAdop);
        map.put("ElAdop", ElAdop);
        map.put("InfoNNA", infoNNA);
        map.put("idInfo", idInfo);
        if (infoNNA.getExpectativaGenero() != null) {
            map.put("Genero", infoNNA.getExpectativaGenero());
        }                
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

        map.put("LaAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "f"));
        map.put("ElAdop", ServicioOrganismo.AdopPorIdFamPorSex(idInfo, "m"));
        map.put("InfoNNA", ServicioOrganismo.InfoFamilia(idInfo));
        map.put("InfoExp", ServicioOrganismo.ExpPorIDFamilia(ServicioOrganismo.InfoFamilia(idInfo).getFamilia().getIdfamilia()));
        map.put("idInfo", idInfo);
        return new ModelAndView("/Entidad/info_fam/info_registro", map);
    }

    @RequestMapping(value = "/Entestado", method = RequestMethod.POST)
    public ModelAndView Entestado(ModelMap map, HttpSession session, @RequestParam("idfam") long idFam) {
        Entidad usuario = (Entidad) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            String si = "SI";
            String no = "NO";

            //Inicialmente seteamos todos los valores en no
            map.addAttribute("eval", no);
            map.addAttribute("espera", no);
            map.addAttribute("adop", no);
            map.addAttribute("postadop", no);

            Familia fam = ServicioOrganismo.FamiliaPorID(idFam);

            if (!fam.getExpedienteFamilias().isEmpty()){
                ExpedienteFamilia exp = fam.getExpedienteFamilias().iterator().next();
                if(exp.getEstado().equals("evaluacion")){
                    map.addAttribute("eval", si);
                }else if(exp.getEstado().equals("espera")){
                    map.addAttribute("eval", si);
                    map.addAttribute("espera", si);
                }else if(exp.getEstado().equals("estudio")){
                    map.addAttribute("eval", si);
                    map.addAttribute("espera", si);
                }else if(exp.getEstado().equals("designacion")){
                    map.addAttribute("eval", si);
                    map.addAttribute("espera", si);
                }else if(exp.getEstado().equals("adopcion")){
                    map.addAttribute("eval", si);
                    map.addAttribute("espera", si);
                    map.addAttribute("adop", si);
                }else if(exp.getEstado().equals("post")){
                    map.addAttribute("eval", si);
                    map.addAttribute("espera", si);
                    map.addAttribute("adop", si);
                    map.addAttribute("postadop", si);
                }
            }
//            for (Iterator iter3 = fam.getExpedienteFamilias().iterator(); iter3.hasNext();) {
//                ExpedienteFamilia exp = (ExpedienteFamilia) iter3.next();
//
//                ExpedienteFamilia expTemp = ServicioOrganismo.ExpPorIDFamilia(idFam);
//                Boolean flag = false;
//                for (Iterator iter4 = expTemp.getEvaluacions().iterator(); iter4.hasNext();) {
//                    Evaluacion eval = (Evaluacion) iter4.next();
//                    Evaluacion eval2 = ServicioOrganismo.EvalPorIDEval(eval.getIdevaluacion());
//                    if (!eval2.getEvalLegals().isEmpty()) {
//                        flag = true;
//                    }
//                }
//                if (!expTemp.getEstado().isEmpty() || expTemp.getEstado().equals("") || expTemp.getEstado().equals("Null")) {
//                    if (expTemp.getEstado().equals("Apto") || flag) {
//                        map.addAttribute("eval", si);
//                        map.addAttribute("espera", si);
//                        if (!expTemp.getDesignacions().isEmpty()) {
//                            for (Iterator iter5 = expTemp.getDesignacions().iterator(); iter5.hasNext();) {
//                                Designacion deg = (Designacion) iter5.next();
//                                if (deg.getAceptacionConsejo() == 1) {
//                                    map.addAttribute("adop", no);
//                                    map.addAttribute("postadop", no);
//                                } else {
//                                    map.addAttribute("adop", si);
//                                    Boolean flag2 = false;
//                                    for (Iterator iter6 = expTemp.getEvaluacions().iterator(); iter6.hasNext();) {
//                                        Evaluacion eval = (Evaluacion) iter6.next();
//                                        Evaluacion eval3 = ServicioOrganismo.EvalPorIDEval(eval.getIdevaluacion());
//                                        for (Iterator iter7 = eval3.getResolucions().iterator(); iter7.hasNext();) {
//                                            Resolucion resol = (Resolucion) iter7.next();
//                                            if (resol.getTipo().equals("Adopción")) {
//                                                flag2 = true;
//                                            }
//                                        }
//                                    }
//                                    if (flag2) {
//                                        map.addAttribute("postadop", si);
//                                    } else {
//                                        map.addAttribute("postadop", no);
//                                    }
//                                }
//                            }
//                        } else {
//                            map.addAttribute("adop", no);
//                            map.addAttribute("postadop", no);
//                        }
//                    } else {
//                        map.addAttribute("eval", no);
//                        map.addAttribute("espera", no);
//                        map.addAttribute("adop", no);
//                        map.addAttribute("postadop", no);
//                    }
//                } else {
//                    map.addAttribute("eval", no);
//                    map.addAttribute("espera", no);
//                    map.addAttribute("adop", no);
//                    map.addAttribute("postadop", no);
//                }
//            }

        }
        String pagina = "/Entidad/estado_proc";
        return new ModelAndView(pagina, map);
    }
}
