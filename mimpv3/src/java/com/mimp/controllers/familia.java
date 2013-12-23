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
                                                    for (Iterator iter7 = eval.getResolucions().iterator(); iter7.hasNext();) {
                                                        Resolucion resol = (Resolucion) iter7.next();
                                                        if (resol.getTipo().equals("Adopción")) {
                                                            flag2 = true;
                                                        }
                                                    }
                                                }
                                                if (flag2) {
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
            oldpass = DigestUtils.sha512Hex(oldpass);
            if (usuario.getPass().equals(oldpass)) {
                if (newpass.equals(newpassconf)) {
                    newpass = DigestUtils.sha512Hex(newpass);
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
    public ModelAndView FactDatos1(ModelMap map, HttpSession session, FichaSolicitudAdopcion fichasol) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        //En el caso no tengamos una ficha de Solicitud adopción, debemos crear una nueva exportando información previa
        if (fichasol == null) {
            FichaSolicitudAdopcion ficha = new FichaSolicitudAdopcion();
            Solicitante sol;
            Hijo hijo;
            Residente res;
            
            
        }
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
        //FALTA
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

    //Ficha de inscripcion de solicitantes para la adopción
    @RequestMapping("/Fficha/opc1")
    public ModelAndView Fficha1(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        dateFormat format = new dateFormat();
        Date factual = new Date();
        String fechaactual = format.dateToString(factual);
        map.addAttribute("hoy", fechaactual);
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            //Verificamos si es la primera vez que llena la ficha
            FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
            if(ficha == null){
                ficha = new FichaSolicitudAdopcion();
                Solicitante sol;
                Hijo hijo;
                Residente res = new Residente();
                
                //En caso haya ficha, se crea una nueva ficha ingresando toda la información
                for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
                    InfoFamilia ifa = (InfoFamilia) iter.next();
                    
                    ficha.setEstadoCivil(ifa.getEstadoCivil());
                    //FALTA TELEFONO
                    ficha.setDomicilio(ifa.getDomicilio());
                    ficha.setDepRes(ifa.getDepRes());
                    ficha.setPropiedadVivienda(ifa.getPropiedadVivienda());
                    ficha.setTipoVivienda(ifa.getTipoVivienda());
                    ficha.setAreaVivTotal(ifa.getAreaVivTotal());
                    ficha.setAreaVivConst(ifa.getAreaVivConst());
                    ficha.setDistVivenda(ifa.getDistVivienda());
                    ficha.setLuz(ifa.getLuz());
                    ficha.setAgua(ifa.getAgua());
                    ficha.setDesague(ifa.getDesague());
                    ficha.setOtrosServ(ifa.getOtrosServ());
                    ficha.setMaterConst(ifa.getMaterConst());
                    ficha.setPared(ifa.getPared());
                    ficha.setTecho(ifa.getTecho());
                    ficha.setPiso(ifa.getPiso());
                    //no se está agregando: n_proc_adop_prev, anho_proceso, proceso_prep, proceso_eval
                    ficha.setNnaIncesto(ifa.getNnaIncesto());
                    ficha.setNnaMental(ifa.getNnaMental());
                    ficha.setNnaEpilepsia(ifa.getNnaEpilepsia());
                    ficha.setNnaAbuso(ifa.getNnaAbuso());
                    ficha.setNnaSifilis(ifa.getNnaSifilis());
                    ficha.setNnaSeguiMedico(ifa.getNnaSeguiMedico());
                    ficha.setNnaOperacion(ifa.getNnaOperacion());
                    ficha.setNnaHiperactivo(ifa.getNnaHiperactivo());
                    ficha.setNnaEspecial(ifa.getNnaEspecial());
                    ficha.setNnaEnfermo(ifa.getNnaEnfermo());
                    ficha.setNnaMayor(ifa.getNnaMayor());
                    ficha.setNnaAdolescente(ifa.getNnaAdolescente());
                    ficha.setNnaHermano(ifa.getNnaHermano());
                    //no se está agregando: nna_foraneo y rpta_foraneo
                    for (Iterator iter2 = ifa.getAdoptantes().iterator(); iter2.hasNext();) {
                        Adoptante adp = (Adoptante) iter2.next();
                        sol = new Solicitante();
                        sol.setNombre(adp.getNombre());
                        sol.setApellidoP(adp.getApellidoP());
                        sol.setApellidoM(adp.getApellidoM());
                        //no se está agregando: edad
                        sol.setFechaNac(adp.getFechaNac());
                        sol.setLugarNac(adp.getLugarNac());
                        sol.setDepaNac(adp.getDepaNac());
                        sol.setPaisNac(adp.getPaisNac());
                        sol.setTipoDoc(adp.getTipoDoc());
                        sol.setNDoc(adp.getNDoc());
                        sol.setCelular(adp.getCelular());
                        sol.setCorreo(adp.getCorreo());
                        sol.setNivelInstruccion(adp.getNivelInstruccion());
                        sol.setCulminoNivel(adp.getCulminoNivel());
                        sol.setProfesion(adp.getProfesion());
                        sol.setTrabajadorDepend(adp.getTrabajadorDepend());
                        sol.setOcupActualDep(adp.getOcupActualDep());
                        sol.setCentroTrabajo(adp.getCentroTrabajo());
                        sol.setDireccionCentro(adp.getDireccionCentro());
                        sol.setTelefonoCentro(adp.getTelefonoCentro());
                        sol.setIngresoDep(adp.getIngresoDep());
                        sol.setTrabajadorIndepend(adp.getTrabajadorIndepend());
                        sol.setOcupActualInd(adp.getOcupActualInd());
                        sol.setIngresoIndep(adp.getIngresoIndep());
                        sol.setSeguroSalud(adp.getSeguroSalud());
                        sol.setTipoSeguro(adp.getTipoSeguro());
                        sol.setSeguroVida(adp.getSeguroVida());
                        sol.setSistPensiones(adp.getSistPensiones());
                        sol.setSaludActual(adp.getSaludActual());
                        ficha.getSolicitantes().add(sol);
                    }
                    for (Iterator iter3 = ifa.getHijoActs().iterator(); iter3.hasNext();) {
                        HijoAct ha = (HijoAct) iter3.next();
                        hijo = new Hijo();
                        hijo.setFechaNac(ha.getFechaNac());
                        hijo.setBiologico(ha.getBiologico());
                        hijo.setFechaAdop(ha.getFechaAdop());
                        //no se esta agregando: edad
                        hijo.setNombre(ha.getNombre());
                        hijo.setApellidoP(ha.getApellidoP());
                        hijo.setApellidoM(ha.getApellidoM());
                        hijo.setOcupacion(ha.getOcupacion());
                        hijo.setEstadoSalud(ha.getEstadoSalud());
                        hijo.setReside(ha.getReside());
                        ficha.getHijos().add(hijo);
                    }
                    for (Iterator iter4 = ifa.getResidenteActs().iterator(); iter4.hasNext();) {
                        //SEGUIR A PARTIR DE AQUI
                    }
                }                
            }
            
            
            
            
            
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
        String pagina = "/Familia/Ficha/ficha_inscripcion_ella";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc2")
    public ModelAndView Fficha2(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String pagina = "/Familia/Ficha/ficha_inscripcion_el";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc3")
    public ModelAndView Fficha3(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String pagina = "/Familia/Ficha/ficha_inscripcion_fam";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc4")
    public ModelAndView Fficha4(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String pagina = "/Familia/Ficha/ficha_inscripcion_vivienda";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc5")
    public ModelAndView Fficha5(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String pagina = "/Familia/Ficha/ficha_inscripcion_adopcion";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc6")
    public ModelAndView Fficha6(ModelMap map, HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String pagina = "/Familia/Ficha/ficha_inscripcion_nna";
        return new ModelAndView(pagina, map);
    }

}
