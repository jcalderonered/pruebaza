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

    dateFormat format = new dateFormat();

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

            //Inicialmente seteamos todos los valores en no
            map.addAttribute("sesion", no);
            map.addAttribute("taller", no);
            map.addAttribute("ficha", no);
            map.addAttribute("boton", 0);
            map.addAttribute("eval", no);
            map.addAttribute("espera", no);
            map.addAttribute("adop", no);
            map.addAttribute("postadop", no);

            Date fechaactual = new Date();
            Date ultfecha = new Date(10, 0, 01);
            for (Iterator iter = usuario.getFormularioSesions().iterator(); iter.hasNext();) {
                FormularioSesion form = (FormularioSesion) iter.next();
                if (ultfecha.before(form.getFechaSol())) {
                    ultfecha = form.getFechaSol();
                }
            }
            if ((ultfecha.getYear() < fechaactual.getYear()) && (usuario.getConstancia() == null)) {
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
                if (usuario.getConstancia() == null) {
                    map.addAttribute("taller", no);
                    map.addAttribute("ficha", no);
                    map.addAttribute("boton", 0);
                    map.addAttribute("eval", no);
                    map.addAttribute("espera", no);
                    map.addAttribute("adop", no);
                    map.addAttribute("postadop", no);
                } else {
                    map.addAttribute("taller", si);
                    if (!usuario.getFichaSolicitudAdopcions().isEmpty()) {
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
                                                if (deg.getAceptacionConsejo() == 1) {
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
                    } else {
                        map.addAttribute("ficha", no);
                        map.addAttribute("boton", 1);
                        map.addAttribute("eval", no);
                        map.addAttribute("espera", no);
                        map.addAttribute("adop", no);
                        map.addAttribute("postadop", no);
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
            if (ficha == null) {
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
                        ResidenteAct ra = (ResidenteAct) iter4.next();
                        res = new Residente();
                        res.setNombre(ra.getNombre());
                        res.setApellidoP(ra.getApellidoP());
                        res.setApellidoM(ra.getApellidoM());
                        res.setParentesco(ra.getParentesco());
                        res.setEdad(ra.getEdad());
                        res.setOcupacion(ra.getOcupacion());
                        res.setEstadoSalud(ra.getEstadoSalud());
                        ficha.getResidentes().add(res);
                    }
                }
                session.setAttribute("ficha", ficha);
            }
            String fechaMatri = "";
            try {
                fechaMatri = format.dateToString(ficha.getFechaMatrimonio());
            } catch (Exception e) {
            }
            map.addAttribute("fechaMatri", fechaMatri);
            char estCiv = 'N';
            try {
                estCiv = ficha.getEstadoCivil().charAt(0);
            } catch (Exception e) {
            }
            map.addAttribute("estCivil", estCiv);
            map.addAttribute("domicilio", ficha.getDomicilio());
            map.addAttribute("fijo", ficha.getFijo());
            Solicitante sol;
            for (Iterator iter5 = ficha.getSolicitantes().iterator(); iter5.hasNext();) {
                sol = (Solicitante) iter5.next();
                if (sol.getSexo() == 'F') {
                    map.put("sol", sol);
                    String fechanac = format.dateToString(sol.getFechaNac());
                    map.addAttribute("fechanac", fechanac);
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
        FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
        String fechaMatri = "";
        try {
            fechaMatri = format.dateToString(ficha.getFechaMatrimonio());
        } catch (Exception e) {
        }
        map.addAttribute("fechaMatri", fechaMatri);
        char estCiv = 'N';
        try {
            estCiv = ficha.getEstadoCivil().charAt(0);
        } catch (Exception e) {
        }
        map.addAttribute("estCivil", estCiv);
        map.addAttribute("domicilio", ficha.getDomicilio());
        map.addAttribute("fijo", ficha.getFijo());
        Solicitante sol;
        for (Iterator iter5 = ficha.getSolicitantes().iterator(); iter5.hasNext();) {
            sol = (Solicitante) iter5.next();
            if (sol.getSexo() == 'M') {
                map.put("sol", sol);
                String fechanac = format.dateToString(sol.getFechaNac());
                map.addAttribute("fechanac", fechanac);
            }
        }
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

    @RequestMapping("/FfichaGuardar/opc1")
    public ModelAndView FfichaGuardarElla(ModelMap map,
            @RequestParam("nombre_ella") String nombre,
            @RequestParam("apellido_p_ella") String apellido_p,
            @RequestParam("apellido_m_ella") String apellido_m,
            @RequestParam("edad_ella") String edad,
            @RequestParam("lugar_nac_ella") String lugar_nac,
            @RequestParam("depa_nac_ella") String depa_nac,
            @RequestParam("pais_nac_ella") String pais_nac,
            @RequestParam("TipoDoc") String tipo_doc,
            @RequestParam("n_doc_ella") String n_doc,
            @RequestParam("domicilio") String domicilio,
            @RequestParam("telefono") String telefono,
            @RequestParam("celular_ella") String celular,
            @RequestParam("correo_ella") String correo,
            @RequestParam("estCivil") String est_civil,
            @RequestParam("fechaMatri") String fecha_matri,
            @RequestParam("nivel_inst_ella") String nivel_inst,
            @RequestParam("culm_nivel_ella") String culm_nivel,
            @RequestParam("prof_ella") String prof,
            @RequestParam("Trabajador_Depend_ella") String trab_depend,
            @RequestParam("ocup_act_dep_ella") String ocup_actual,
            @RequestParam("centro_trabajo_ella") String centro_trabajo,
            @RequestParam("dir_centro_ella") String dir_centro,
            @RequestParam("tel_centro_ella") String tel_centro,
            @RequestParam("ingreso_dep_ella") String ingreso_dep,
            @RequestParam("Trabajador_Indep_ella") String trab_indep,
            @RequestParam("ocup_act_indep_ella") String ocup_act_indep,
            @RequestParam("ingreso_ind_ella") String ingreso_ind,
            @RequestParam("seguro_salud_ella") String seguro_salud,
            @RequestParam("tipo_seguro") String tipo_seguro,
            @RequestParam("seguro_vida_ella") String seguro_vida,
            @RequestParam("sist_pen_ella") String sist_pen,
            @RequestParam("est_salud_ella") String est_salud,
            HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
        Solicitante sol = new Solicitante();
        for (Iterator iter2 = ficha.getSolicitantes().iterator(); iter2.hasNext();) {
            sol = (Solicitante) iter2.next();
            if (sol.getSexo() == 'F') {
                ficha.getSolicitantes().remove(sol);
                break;
            }
        }

        sol.setNombre(nombre);
        sol.setApellidoP(apellido_p);
        sol.setApellidoM(apellido_m);
        try {
            sol.setEdad(Short.parseShort(edad));
        } catch (Exception ex) {
            String mensaje_edad = "ERROR: El campo Edad contiene parámetros inválidos";
            map.addAttribute("mensaje_edad", mensaje_edad);
        }
        sol.setLugarNac(lugar_nac);
        sol.setDepaNac(depa_nac);
        sol.setPaisNac(pais_nac);
        sol.setTipoDoc(tipo_doc.charAt(0));
        sol.setNDoc(n_doc);
        ficha.setDomicilio(domicilio);
        ficha.setFijo(telefono);
        sol.setCelular(celular);
        sol.setCorreo(correo);
        ficha.setEstadoCivil(est_civil);
        ficha.setFechaMatrimonio(format.stringToDate(fecha_matri));
        sol.setNivelInstruccion(nivel_inst);
        sol.setCulminoNivel(Short.valueOf(culm_nivel));
        sol.setProfesion(prof);
        sol.setTrabajadorDepend(Short.valueOf(trab_depend));
        sol.setOcupActualDep(ocup_actual);
        sol.setCentroTrabajo(centro_trabajo);
        sol.setDireccionCentro(dir_centro);
        sol.setTelefonoCentro(tel_centro);
        try {
            sol.setIngresoDep(Long.valueOf(ingreso_dep));
        } catch (Exception ex) {
            String mensaje_ingreso_dep = "ERROR: La información contenida en este campo contiene parámetros inválidos";
            map.addAttribute("mensaje_ing_dep", mensaje_ingreso_dep);
        }
        sol.setTrabajadorIndepend(Short.valueOf(trab_indep));
        sol.setOcupActualInd(ocup_act_indep);
        try {
            sol.setIngresoIndep(Long.valueOf(ingreso_ind));
        } catch (Exception ex) {
            String mensaje_ingreso_indep = "ERROR: La información contenida en este campo contiene parámetros inválidos";
            map.addAttribute("mensaje_ing_indep", mensaje_ingreso_indep);
        }
        sol.setSeguroSalud(Short.valueOf(seguro_salud));
        sol.setTipoSeguro(tipo_seguro);
        sol.setSeguroVida(Short.valueOf(seguro_vida));
        sol.setSistPensiones(Short.valueOf(sist_pen));
        sol.setSaludActual(est_salud);
        ficha.getSolicitantes().add(sol);

        String pagina = "/Familia/Ficha/ficha_inscripcion_ella";
        return new ModelAndView(pagina, map);
    }
    
    @RequestMapping("/FfichaGuardar/opc2")
    public ModelAndView FfichaGuardarEl(ModelMap map,
            @RequestParam("nombre_el") String nombre,
            @RequestParam("apellido_p_el") String apellido_p,
            @RequestParam("apellido_m_el") String apellido_m,
            @RequestParam("edad_el") String edad,
            @RequestParam("lugar_nac_el") String lugar_nac,
            @RequestParam("depa_nac_el") String depa_nac,
            @RequestParam("pais_nac_el") String pais_nac,
            @RequestParam("TipoDoc") String tipo_doc,
            @RequestParam("n_doc_el") String n_doc,
            @RequestParam("domicilio") String domicilio,
            @RequestParam("telefono") String telefono,
            @RequestParam("celular_el") String celular,
            @RequestParam("correo_el") String correo,
            @RequestParam("estCivil") String est_civil,
            @RequestParam("fechaMatri") String fecha_matri,
            @RequestParam("nivel_inst_el") String nivel_inst,
            @RequestParam("culm_nivel_el") String culm_nivel,
            @RequestParam("prof_el") String prof,
            @RequestParam("Trabajador_Depend_el") String trab_depend,
            @RequestParam("ocup_act_dep_el") String ocup_actual,
            @RequestParam("centro_trabajo_el") String centro_trabajo,
            @RequestParam("dir_centro_el") String dir_centro,
            @RequestParam("tel_centro_el") String tel_centro,
            @RequestParam("ingreso_dep_el") String ingreso_dep,
            @RequestParam("Trabajador_Indep_el") String trab_indep,
            @RequestParam("ocup_act_indep_el") String ocup_act_indep,
            @RequestParam("ingreso_ind_el") String ingreso_ind,
            @RequestParam("seguro_salud_el") String seguro_salud,
            @RequestParam("tipo_seguro") String tipo_seguro,
            @RequestParam("seguro_vida_el") String seguro_vida,
            @RequestParam("sist_pen_el") String sist_pen,
            @RequestParam("est_salud_el") String est_salud,
            HttpSession session) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
        Solicitante sol = new Solicitante();
        for (Iterator iter2 = ficha.getSolicitantes().iterator(); iter2.hasNext();) {
            sol = (Solicitante) iter2.next();
            if (sol.getSexo() == 'M') {
                ficha.getSolicitantes().remove(sol);
                break;
            }
        }

        sol.setNombre(nombre);
        sol.setApellidoP(apellido_p);
        sol.setApellidoM(apellido_m);
        try {
            sol.setEdad(Short.parseShort(edad));
        } catch (Exception ex) {
            String mensaje_edad = "ERROR: El campo Edad contiene parámetros inválidos";
            map.addAttribute("mensaje_edad", mensaje_edad);
        }
        sol.setLugarNac(lugar_nac);
        sol.setDepaNac(depa_nac);
        sol.setPaisNac(pais_nac);
        sol.setTipoDoc(tipo_doc.charAt(0));
        sol.setNDoc(n_doc);
        ficha.setDomicilio(domicilio);
        ficha.setFijo(telefono);
        sol.setCelular(celular);
        sol.setCorreo(correo);
        ficha.setEstadoCivil(est_civil);
        ficha.setFechaMatrimonio(format.stringToDate(fecha_matri));
        sol.setNivelInstruccion(nivel_inst);
        sol.setCulminoNivel(Short.valueOf(culm_nivel));
        sol.setProfesion(prof);
        sol.setTrabajadorDepend(Short.valueOf(trab_depend));
        sol.setOcupActualDep(ocup_actual);
        sol.setCentroTrabajo(centro_trabajo);
        sol.setDireccionCentro(dir_centro);
        sol.setTelefonoCentro(tel_centro);
        try {
            sol.setIngresoDep(Long.valueOf(ingreso_dep));
        } catch (Exception ex) {
            String mensaje_ingreso_dep = "ERROR: La información contenida en este campo contiene parámetros inválidos";
            map.addAttribute("mensaje_ing_dep", mensaje_ingreso_dep);
        }
        sol.setTrabajadorIndepend(Short.valueOf(trab_indep));
        sol.setOcupActualInd(ocup_act_indep);
        try {
            sol.setIngresoIndep(Long.valueOf(ingreso_ind));
        } catch (Exception ex) {
            String mensaje_ingreso_indep = "ERROR: La información contenida en este campo contiene parámetros inválidos";
            map.addAttribute("mensaje_ing_indep", mensaje_ingreso_indep);
        }
        sol.setSeguroSalud(Short.valueOf(seguro_salud));
        sol.setTipoSeguro(tipo_seguro);
        sol.setSeguroVida(Short.valueOf(seguro_vida));
        sol.setSistPensiones(Short.valueOf(sist_pen));
        sol.setSaludActual(est_salud);
        ficha.getSolicitantes().add(sol);

        String pagina = "/Familia/Ficha/ficha_inscripcion_ella";
        return new ModelAndView(pagina, map);
    }
}
