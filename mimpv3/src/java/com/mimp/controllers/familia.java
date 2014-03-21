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
import com.mimp.util.dateFormat;
import com.mimp.util.timeStampFormat;
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
    timeStampFormat ts = new timeStampFormat();

    @Resource(name = "HiberFamilia")
    private HiberFamilia ServicioFamilia = new HiberFamilia();
    @Resource(name = "HiberMain")
    private HiberMain ServicioMain = new HiberMain();

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
        usuario = ServicioFamilia.obtenerFormulariosFamilia(usuario.getIdfamilia());
        
        String departamento = "";
        Date fechaactual = new Date();
        Date ultfecha = new Date(10, 0, 01);
        for (Iterator iter = usuario.getFormularioSesions().iterator(); iter.hasNext();) {
            FormularioSesion form = (FormularioSesion) iter.next();
            if (ultfecha.before(form.getFechaSol())) {
                ultfecha = form.getFechaSol();
                Sesion ses = ServicioFamilia.getSesionDeFormulario(form.getIdformularioSesion());
                departamento = ses.getUnidad();
            }
        }
        String pagina;
        boolean inscritoTaller = false;
        ArrayList<AsistenciaFR> allReuniones = new ArrayList();
        allReuniones = ServicioFamilia.listaReunionesInscritasFamilia(usuario.getIdfamilia());
        if (!allReuniones.isEmpty()) {
            for (AsistenciaFR asistenciaFR : allReuniones) {
                if (fechaactual.before(asistenciaFR.getReunion().getFecha())) {
                    inscritoTaller = true;
                }
            }
        }
        if (ultfecha.getYear() < fechaactual.getYear()) {
            System.out.print(ultfecha);
            System.out.print(fechaactual);
            Sesion sesionMasProx = new Sesion();
            //ArrayList<Personal> allPersonal = new ArrayList();
            ArrayList<Turno> allTurnos = new ArrayList();
            sesionMasProx = ServicioFamilia.sesionMasProx(fechaactual);

            allTurnos = ServicioMain.turnosSesion(sesionMasProx.getIdsesion());

            //allPersonal = ServicioPersonal.ListaPersonal();
            String fecha = "";
            if (sesionMasProx.getFecha() != null) {
                fecha = format.dateToString(sesionMasProx.getFecha());
            }
            String hora = sesionMasProx.getHora();

            map.put("listaTurnos", allTurnos);
            map.put("sesion", sesionMasProx);
            //map.put("listaPersonal", allPersonal);
            map.addAttribute("ts", ts);
            map.addAttribute("fecha", fecha);
            map.addAttribute("hora", hora);

            map.put("formato", format);

            pagina = "/Familia/Inscripcion/inscripcion_sesionInfo";
        } else {

            //map.put("listaTalleres", ServicioFamilia.listaTalleresHabilitados());
            map.put("listaTalleres", ServicioFamilia.listaTalleresHabilitadosPorDep(departamento));
            map.put("formato", format);
            if (inscritoTaller) {
                map.put("inscrito", "true");
                map.put("listaReuniones", allReuniones);
            } else {
                map.put("inscrito", "false");
            }
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
                                map.addAttribute("eval", si);
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
                String fechaMatri = "";
                if (ifa.getFechaMatrimonio() != null) {
                    fechaMatri = format.dateToString(ifa.getFechaMatrimonio());
                }
                map.addAttribute("fechaMatri", fechaMatri);
                map.addAttribute("estCivil", ifa.getEstadoCivil());
                for (Iterator iter2 = ifa.getAdoptantes().iterator(); iter2.hasNext();) {
                    Adoptante adop = (Adoptante) iter2.next();
                    if (adop.getSexo() == 'f') {
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
                String fechaMatri = "";
                if (ifa.getFechaMatrimonio() != null) {
                    fechaMatri = format.dateToString(ifa.getFechaMatrimonio());
                }
                map.addAttribute("fechaMatri", fechaMatri);
                map.addAttribute("estCivil", ifa.getEstadoCivil());
                for (Iterator iter2 = ifa.getAdoptantes().iterator(); iter2.hasNext();) {
                    Adoptante adop = (Adoptante) iter2.next();
                    if (adop.getSexo() == 'm') {
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
        Date fechaAct = new Date();
        int añoAct = fechaAct.getYear();
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<HijoAct> listaHijos = new ArrayList();
        ArrayList<HijoAct> listaHijosAdop = new ArrayList();
        ArrayList<ResidenteAct> listaRes = new ArrayList();
        for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
            InfoFamilia ifa = (InfoFamilia) iter.next();
            for (Iterator iter2 = ifa.getHijoActs().iterator(); iter2.hasNext();) {
                HijoAct h = (HijoAct) iter2.next();
                int edad = añoAct - h.getFechaNac().getYear();
                if ((h.getFechaNac().getMonth() - fechaAct.getMonth()) > 0) {
                    edad--;
                } else if ((h.getFechaNac().getMonth() - fechaAct.getMonth()) == 0) {
                    if ((h.getFechaNac().getDate() - fechaAct.getDate()) > 0) {
                        edad--;
                    }
                }
                h.setEdad(edad);
                h.setFechaNacString(format.dateToString(h.getFechaNac()));
                if (h.getBiologico() == 0) {
                    listaHijos.add(h);
                } else {
                    h.setFechaAdopString(format.dateToString(h.getFechaAdop()));
                    listaHijosAdop.add(h);
                }
            }
            for (Iterator iter3 = ifa.getResidenteActs().iterator(); iter3.hasNext();) {
                ResidenteAct r = (ResidenteAct) iter3.next();
                listaRes.add(r);
            }
            map.put("listaHijos", listaHijos);
            map.put("listaHijosAdop", listaHijosAdop);
            map.put("listaRes", listaRes);
        }
        String pagina = "/Familia/Act_datos/datos_fam";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FactDatos/opc4")
    public ModelAndView FactDatos4(ModelMap map, HttpSession session
    ) {
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
    public ModelAndView FactDatos5(ModelMap map, HttpSession session
    ) {
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
    @RequestMapping("/Fficha/opc0")
    public ModelAndView Fficha0(ModelMap map, HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            dateFormat format = new dateFormat();
            Date factual = new Date();
            String fechaactual = format.dateToString(factual);
            map.addAttribute("factual", fechaactual);
            FichaSolicitudAdopcion ficha = new FichaSolicitudAdopcion();
            Solicitante sol;
            Hijo hijo;
            Residente res;
            //En caso haya ficha, se crea una nueva ficha ingresando toda la información
            for (Iterator iter = usuario.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                ficha.setEstadoCivil(ifa.getEstadoCivil());
                ficha.setFijo(ifa.getTelefono());
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
                    sol.setSexo(adp.getSexo());
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
            String fechaMatri = "";
            map.addAttribute("fechaMatri", fechaMatri);
            map.addAttribute("estCivil", ficha.getEstadoCivil().charAt(0));
            map.addAttribute("domicilio", ficha.getDomicilio());
            map.addAttribute("fijo", ficha.getFijo());
            Solicitante sol1;
            for (Iterator iter5 = ficha.getSolicitantes().iterator(); iter5.hasNext();) {
                sol1 = (Solicitante) iter5.next();
                if (sol1.getSexo() == 'F') {
                    map.put("sol", sol1);
                    String fechanac = format.dateToString(sol1.getFechaNac());
                    map.addAttribute("fechanac", fechanac);
                }
            }
        }
        String pagina = "/Familia/Ficha/ficha_inscripcion_ella";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc1")
    public ModelAndView Fficha1(ModelMap map, HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            dateFormat format = new dateFormat();
            Date factual = new Date();
            String fechaactual = format.dateToString(factual);
            map.addAttribute("factual", fechaactual);
            FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
            String fechaMatri = "";
            try {
                fechaMatri = format.dateToString(ficha.getFechaMatrimonio());
            } catch (Exception ex) {
            }
            map.addAttribute("fechaMatri", fechaMatri);
            char estCiv = ficha.getEstadoCivil().charAt(0);
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
    public ModelAndView Fficha2(ModelMap map, HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            dateFormat format = new dateFormat();
            Date factual = new Date();
            String fechaactual = format.dateToString(factual);
            map.addAttribute("factual", fechaactual);
            FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
            String fechaMatri = "";
            try {
                fechaMatri = format.dateToString(ficha.getFechaMatrimonio());
            } catch (Exception ex) {
            }
            map.addAttribute("fechaMatri", fechaMatri);
            char estCiv = ficha.getEstadoCivil().charAt(0);
            map.addAttribute("estCivil", estCiv);
            map.addAttribute("domicilio", ficha.getDomicilio());
            map.addAttribute("fijo", ficha.getFijo());
            Solicitante sol;
            for (Iterator iter5 = ficha.getSolicitantes().iterator(); iter5.hasNext();) {
                sol = (Solicitante) iter5.next();
                if (sol.getSexo() == 'M') {
                    map.put("sol", sol);
                    String fechanac = "";
                    try {
                        fechanac = format.dateToString(sol.getFechaNac());
                    } catch (Exception ex) {

                    }
                    map.addAttribute("fechanac", fechanac);
                }
            }
        }
        String pagina = "/Familia/Ficha/ficha_inscripcion_el";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc3")
    public ModelAndView Fficha3(ModelMap map, HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<Hijo> listaHijos = new ArrayList();
        ArrayList<Hijo> listaHijosAdop = new ArrayList();
        ArrayList<Residente> listaRes = new ArrayList();
        FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) session.getAttribute("ficha");
        for (Iterator iter = ficha.getHijos().iterator(); iter.hasNext();) {
            Hijo h = (Hijo) iter.next();
            h.setFechaNacString(format.dateToString(h.getFechaNac()));
            if (h.getBiologico() == 0) {
                listaHijos.add(h);
            } else {
                h.setFechaAdopString(format.dateToString(h.getFechaAdop()));
                listaHijosAdop.add(h);
            }
        }
        for (Iterator iter3 = ficha.getResidentes().iterator(); iter3.hasNext();) {
            Residente r = (Residente) iter3.next();
            listaRes.add(r);
        }
        map.put("listaHijos", listaHijos);
        map.put("listaHijosAdop", listaHijosAdop);
        map.put("listaRes", listaRes);
        String pagina = "/Familia/Ficha/ficha_inscripcion_fam";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/Fficha/opc4")
    public ModelAndView Fficha4(ModelMap map, HttpSession session
    ) {
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
    public ModelAndView Fficha5(ModelMap map, HttpSession session
    ) {
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
    public ModelAndView Fficha6(ModelMap map, HttpSession session
    ) {
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
            @RequestParam(value = "ocup_act_dep_ella", required = false) String ocup_actual,
            @RequestParam(value = "centro_trabajo_ella", required = false) String centro_trabajo,
            @RequestParam(value = "dir_centro_ella", required = false) String dir_centro,
            @RequestParam(value = "tel_centro_ella", required = false) String tel_centro,
            @RequestParam(value = "ingreso_dep_ella", required = false) String ingreso_dep,
            @RequestParam("Trabajador_Indep_ella") String trab_indep,
            @RequestParam(value = "ocup_act_indep_ella", required = false) String ocup_act_indep,
            @RequestParam(value = "ingreso_ind_ella", required = false) String ingreso_ind,
            @RequestParam("seguro_salud_ella") String seguro_salud,
            @RequestParam("tipo_seguro") String tipo_seguro,
            @RequestParam("seguro_vida_ella") String seguro_vida,
            @RequestParam("sist_pen_ella") String sist_pen,
            @RequestParam("est_salud_ella") String est_salud,
            HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        dateFormat format = new dateFormat();
        Date factual = new Date();
        String fechaactual = format.dateToString(factual);
        map.addAttribute("factual", fechaactual);

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
        try {
            sol.setTipoDoc(tipo_doc.charAt(0));
        } catch (Exception ex) {

        }
        sol.setNDoc(n_doc);
        ficha.setDomicilio(domicilio);
        ficha.setFijo(telefono);
        sol.setCelular(celular);
        sol.setCorreo(correo);
        try {
            ficha.setEstadoCivil(est_civil);
        } catch (Exception ex) {

        }
        Date tempfecha = ficha.getFechaMatrimonio();
        if (fecha_matri.contains("ene") || fecha_matri.contains("feb") || fecha_matri.contains("mar") || fecha_matri.contains("abr")
                || fecha_matri.contains("may") || fecha_matri.contains("jun") || fecha_matri.contains("jul") || fecha_matri.contains("ago")
                || fecha_matri.contains("set") || fecha_matri.contains("oct") || fecha_matri.contains("nov") || fecha_matri.contains("dic")) {
            ficha.setFechaMatrimonio(tempfecha);
        } else {
            ficha.setFechaMatrimonio(format.stringToDate(fecha_matri));
        }
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
            if (ingreso_dep != null) {
                String mensaje_ingreso_dep = "ERROR: La información contenida en este campo contiene parámetros inválidos";
                map.addAttribute("mensaje_ing_dep", mensaje_ingreso_dep);
            }
        }
        sol.setTrabajadorIndepend(Short.valueOf(trab_indep));
        sol.setOcupActualInd(ocup_act_indep);
        try {
            sol.setIngresoIndep(Long.valueOf(ingreso_ind));
        } catch (Exception ex) {
            if (ingreso_ind != null) {
                String mensaje_ingreso_indep = "ERROR: La información contenida en este campo contiene parámetros inválidos";
                map.addAttribute("mensaje_ing_indep", mensaje_ingreso_indep);
            }
        }
        sol.setSeguroSalud(Short.valueOf(seguro_salud));
        sol.setTipoSeguro(tipo_seguro);
        sol.setSeguroVida(Short.valueOf(seguro_vida));
        sol.setSistPensiones(Short.valueOf(sist_pen));
        sol.setSaludActual(est_salud);
        ficha.getSolicitantes().add(sol);
        session.removeAttribute("ficha");
        session.setAttribute("ficha", ficha);

        map.put("sol", sol);
        String fechanac = format.dateToString(sol.getFechaNac());
        map.addAttribute("fechanac", fechanac);
        map.addAttribute("estCivil", est_civil.charAt(0));
        map.addAttribute("fechaMatri", format.dateToString(ficha.getFechaMatrimonio()));
        map.addAttribute("estCivil", ficha.getEstadoCivil().charAt(0));
        map.addAttribute("domicilio", ficha.getDomicilio());
        map.addAttribute("fijo", ficha.getFijo());

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
            @RequestParam(value = "ocup_act_dep_el", required = false) String ocup_actual,
            @RequestParam(value = "centro_trabajo_el", required = false) String centro_trabajo,
            @RequestParam(value = "dir_centro_el", required = false) String dir_centro,
            @RequestParam(value = "tel_centro_el", required = false) String tel_centro,
            @RequestParam(value = "ingreso_dep_el", required = false) String ingreso_dep,
            @RequestParam("Trabajador_Indep_el") String trab_indep,
            @RequestParam(value = "ocup_act_indep_el", required = false) String ocup_act_indep,
            @RequestParam(value = "ingreso_ind_el", required = false) String ingreso_ind,
            @RequestParam("seguro_salud_el") String seguro_salud,
            @RequestParam("tipo_seguro") String tipo_seguro,
            @RequestParam("seguro_vida_el") String seguro_vida,
            @RequestParam("sist_pen_el") String sist_pen,
            @RequestParam("est_salud_el") String est_salud,
            HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        dateFormat format = new dateFormat();
        Date factual = new Date();
        String fechaactual = format.dateToString(factual);
        map.addAttribute("factual", fechaactual);

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
        try {
            sol.setTipoDoc(tipo_doc.charAt(0));
        } catch (Exception ex) {

        }
        sol.setNDoc(n_doc);
        ficha.setDomicilio(domicilio);
        ficha.setFijo(telefono);
        sol.setCelular(celular);
        sol.setCorreo(correo);
        try {
            ficha.setEstadoCivil(est_civil);
        } catch (Exception ex) {

        }
        Date tempfecha = ficha.getFechaMatrimonio();
        if (fecha_matri.contains("ene") || fecha_matri.contains("feb") || fecha_matri.contains("mar") || fecha_matri.contains("abr")
                || fecha_matri.contains("may") || fecha_matri.contains("jun") || fecha_matri.contains("jul") || fecha_matri.contains("ago")
                || fecha_matri.contains("set") || fecha_matri.contains("oct") || fecha_matri.contains("nov") || fecha_matri.contains("dic")) {
            ficha.setFechaMatrimonio(tempfecha);
        } else {
            ficha.setFechaMatrimonio(format.stringToDate(fecha_matri));
        }
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
        session.removeAttribute("ficha");
        session.setAttribute("ficha", ficha);

        map.put("sol", sol);
        String fechanac = format.dateToString(sol.getFechaNac());
        map.addAttribute("fechanac", fechanac);
        map.addAttribute("estCivil", est_civil.charAt(0));
        map.addAttribute("fechaMatri", format.dateToString(ficha.getFechaMatrimonio()));
        map.addAttribute("estCivil", ficha.getEstadoCivil().charAt(0));
        map.addAttribute("domicilio", ficha.getDomicilio());
        map.addAttribute("fijo", ficha.getFijo());

        String pagina = "/Familia/Ficha/ficha_inscripcion_el";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FFicha/EditarHijo")
    public ModelAndView FfichaHijo(ModelMap map,
            @RequestParam("apellido_p") String apellidoP,
            @RequestParam("apellido_m") String apellidoM,
            @RequestParam("nombre") String nombre,
            @RequestParam("edad") String edad,
            @RequestParam("fecha_nac") String fecha_nac,
            @RequestParam("fecha_adop") String fecha_adop,
            @RequestParam("ocupacion") String ocupacion,
            @RequestParam("estado_salud") String estado_salud,
            @RequestParam("reside") String reside,
            @RequestParam("biologico") String biologico,
            @RequestParam("idhijo") String idhijo,
            HttpSession session
    ) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //FALTA
        String mensajebio = "";
        String mensajeadop = "";
        Long idhijolong = Long.parseLong(idhijo);
        if (idhijolong == 0) {
            Hijo hijo = new Hijo();
            hijo.setApellidoP(apellidoP);
            hijo.setApellidoM(apellidoM);
            hijo.setNombre(nombre);
            try {
                hijo.setEdad(Short.parseShort(edad));
            } catch (Exception e) {
                mensajebio = "<p style=\"color: red\">El campo edad no contiene parámetros válidos. Ingresarlos nuevamente<p> ";
            }
//            if (fecha_nac.contains("ene") || fecha_nac.contains("feb") || fecha_nac.contains("mar") || fecha_nac.contains("abr")
//                    || fecha_nac.contains("may") || fecha_nac.contains("jun") || fecha_nac.contains("jul") || fecha_nac.contains("ago")
//                    || fecha_nac.contains("set") || fecha_nac.contains("oct") || fecha_nac.contains("nov") || fecha_nac.contains("dic")) {
//                hijo.setfecha();
//                tempSesion.setFecha(tempfecha);
//            } else {
//                tempSesion.setFecha(format.stringToDate(fecha));
//            }
        } else {

        }
        String pagina = "/Familia/Ficha/ficha_inscripcion_adopcion";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FFicha/EditarRes")
    public ModelAndView FfichaRes(ModelMap map,
            @RequestParam("apellido_p") String apellidoP,
            @RequestParam("apellido_m") String apellidoM,
            @RequestParam("nombre") String nombre,
            @RequestParam("parentesco") String parentesco,
            @RequestParam("edad") String edad,
            @RequestParam("ocupacion") String ocupacion,
            @RequestParam("estado_salud") String estado_salud,
            @RequestParam("idresidente") String reside,
            HttpSession session
    ) {
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

    @RequestMapping("/FamiliaInscripcion")
    public ModelAndView FamiliaInscripcion(ModelMap map, HttpSession session,
            @RequestParam("idTurno") long idTurno) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno temp = ServicioMain.getTurno(idTurno);
        int numAsist = ServicioFamilia.AsistentesPorFormulario(usuario.getIdfamilia());
        int cont = 0;
        if (!temp.getAsistenciaFTs().isEmpty()) {
            cont = numAsist + temp.getAsistenciaFTs().size();
        }
        if (temp.getAsistenciaFTs().isEmpty()) {
            cont = numAsist;
        }
        if (cont <= temp.getVacantes()) {
            map.put("mensaje", "inscrito");
            map.put("sesion", temp.getSesion());
            FormularioSesion antiguo = ServicioFamilia.ultimoFormulario(usuario.getIdfamilia());
            FormularioSesion nuevo = new FormularioSesion();
            AsistenciaFT aft = new AsistenciaFT();
            AsistenciaFT aft2 = new AsistenciaFT();
            aft.setTurno(temp);
            aft2.setTurno(temp);
            String asistencia = "F";
            char asist = asistencia.charAt(0);
            aft.setAsistencia(asist);
            aft2.setAsistencia(asist);
            String inajust = "1";
            Short i = Short.valueOf(inajust);
            aft.setInasJus(i);
            aft2.setInasJus(i);
            Asistente asis1 = new Asistente();
            Asistente asis2 = new Asistente();
            Date now = new Date();
            if (antiguo.getAsistentes().size() == 1) {
                Asistente tempAsis = antiguo.getAsistentes().iterator().next();

                asis1.setNombre(tempAsis.getNombre());
                asis1.setApellidoP(tempAsis.getApellidoP());
                asis1.setApellidoM(tempAsis.getApellidoM());
                asis1.setSexo(tempAsis.getSexo());
                asis1.setPaisNac(tempAsis.getPaisNac());
                asis1.setDepNac(tempAsis.getDepNac());
                asis1.setProvNac(tempAsis.getProvNac());
                asis1.setEdad(tempAsis.getEdad());
                asis1.setFechaNac(tempAsis.getFechaNac());
                asis1.setTipoDoc(tempAsis.getTipoDoc());
                asis1.setNDoc(tempAsis.getNDoc());
                asis1.setProfesion(tempAsis.getProfesion());
                asis1.setCelular(tempAsis.getCelular());
                asis1.setCorreo(tempAsis.getCorreo());

                nuevo.setFamilia(usuario);
                nuevo.setFechaSol(now);
                nuevo.setSesion(temp.getSesion());
                nuevo.setPaisRes(antiguo.getPaisRes());
                nuevo.setDepRes(antiguo.getDepRes());
                nuevo.setProvRes(antiguo.getProvRes());
                nuevo.setDistritoRes(antiguo.getDistritoRes());
                nuevo.setDireccionRes(antiguo.getDireccionRes());
                nuevo.setEstadoCivil(antiguo.getEstadoCivil());
                nuevo.setTelefono(antiguo.getTelefono());

                ServicioMain.InsertFormInd(asis1, nuevo, aft);

            } else {
                Asistente elTemp = new Asistente();
                Asistente ellaTemp = new Asistente();
                for (Asistente tempAsis : antiguo.getAsistentes()) {
                    if (tempAsis.getSexo() == 109) {
                        elTemp = tempAsis;
                    }
                    if (tempAsis.getSexo() == 102) {
                        ellaTemp = tempAsis;
                    }
                }

                asis1.setNombre(elTemp.getNombre());
                asis1.setApellidoP(elTemp.getApellidoP());
                asis1.setApellidoM(elTemp.getApellidoM());
                asis1.setSexo(elTemp.getSexo());
                asis1.setPaisNac(elTemp.getPaisNac());
                asis1.setDepNac(elTemp.getDepNac());
                asis1.setProvNac(elTemp.getProvNac());
                asis1.setEdad(elTemp.getEdad());
                asis1.setFechaNac(elTemp.getFechaNac());
                asis1.setTipoDoc(elTemp.getTipoDoc());
                asis1.setNDoc(elTemp.getNDoc());
                asis1.setProfesion(elTemp.getProfesion());
                asis1.setCelular(elTemp.getCelular());
                asis1.setCorreo(elTemp.getCorreo());

                asis2.setNombre(ellaTemp.getNombre());
                asis2.setApellidoP(ellaTemp.getApellidoP());
                asis2.setApellidoM(ellaTemp.getApellidoM());
                asis2.setSexo(ellaTemp.getSexo());
                asis2.setPaisNac(ellaTemp.getPaisNac());
                asis2.setDepNac(ellaTemp.getDepNac());
                asis2.setProvNac(ellaTemp.getProvNac());
                asis2.setEdad(ellaTemp.getEdad());
                asis2.setFechaNac(ellaTemp.getFechaNac());
                asis2.setTipoDoc(ellaTemp.getTipoDoc());
                asis2.setNDoc(ellaTemp.getNDoc());
                asis2.setProfesion(ellaTemp.getProfesion());
                asis2.setCelular(ellaTemp.getCelular());
                asis2.setCorreo(ellaTemp.getCorreo());

                nuevo.setFamilia(usuario);
                nuevo.setFechaSol(now);
                nuevo.setSesion(temp.getSesion());
                nuevo.setPaisRes(antiguo.getPaisRes());
                nuevo.setDepRes(antiguo.getDepRes());
                nuevo.setProvRes(antiguo.getProvRes());
                nuevo.setDistritoRes(antiguo.getDistritoRes());
                nuevo.setDireccionRes(antiguo.getDireccionRes());
                nuevo.setEstadoCivil(antiguo.getEstadoCivil());
                nuevo.setTelefono(antiguo.getTelefono());

                ServicioMain.InsertFormGrp(asis1, asis2, nuevo, aft, aft2);

            }

        } else {
            map.put("mensaje", "fallo");
        }

        String pagina = "/Familia/Inscripcion/inscripcion_sesionInfo_afirm";

        map.put("df", format);
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FamiliaDetalleTaller")
    public ModelAndView FamiliaDetalleTaller(ModelMap map, HttpSession session,
            @RequestParam("idTaller") long idTaller,
            @RequestParam("nombreTaller") String nombreTaller) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        String pagina = "/Familia/Inscripcion/inscripcion_Grupos";
        ArrayList<Grupo> allGrupos = new ArrayList();
        allGrupos = ServicioFamilia.listaGruposDeTaller(idTaller);
        map.put("listaGrupos", allGrupos);
        map.put("nombreTaller", nombreTaller);
        map.put("df", format);
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/FamiliaInscribirTaller")
    public ModelAndView FamiliaInscribirTaller(ModelMap map, HttpSession session,
            @RequestParam("idTurno2") long idTurno2,
            @RequestParam("nombreTaller") String nombreTaller,
            @RequestParam("nombreGrupo") String nombreGrupo,
            @RequestParam("nombreTurno") String nombreTurno) {
        Familia usuario = (Familia) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<Reunion> allReuniones = new ArrayList();
        allReuniones = ServicioFamilia.listaReunionesTurno2(idTurno2);
        short numAsistentes = ServicioFamilia.numAsistentesFormulario(usuario.getIdfamilia());
        boolean inscripcion = false;
        for (Reunion reunion : allReuniones) {
            if ((reunion.getAsistenciaFRs().size() + numAsistentes) > reunion.getCapacidad()) {
                inscripcion = true;
            }
        }
        if (!inscripcion) {
            for (Reunion reunion : allReuniones) {
                AsistenciaFR tempAFR = new AsistenciaFR();
                tempAFR.setFamilia(usuario);
                tempAFR.setReunion(reunion);
                String asistencia = "F";
                char c = asistencia.charAt(0);
                tempAFR.setAsistencia(c);
                tempAFR.setInasJus(Short.parseShort("1"));
                ServicioFamilia.crearAFR(tempAFR);
                short x = 2;
                if (numAsistentes == x) {
                    AsistenciaFR tempAFR2 = new AsistenciaFR();
                    tempAFR2.setFamilia(usuario);
                    tempAFR2.setReunion(reunion);
                    String asistencia2 = "F";
                    char c2 = asistencia2.charAt(0);
                    tempAFR2.setAsistencia(c);
                    tempAFR2.setInasJus(Short.parseShort("1"));
                    ServicioFamilia.crearAFR(tempAFR2);
                }
            }
        }
        if (!inscripcion) {
            map.put("listaReuniones", allReuniones);
            map.put("nombreTaller", nombreTaller);
            map.put("nombreGrupo", nombreGrupo);
            map.put("nombreTurno", nombreTurno);
            map.put("df", format);
        } else {
            map.put("mensaje", "negativo");
            map.put("nombreGrupo", nombreGrupo);
            map.put("nombreTurno", nombreTurno);
        }
        String pagina = "/Familia/Inscripcion/inscripcion_Grupos_afirm";
        return new ModelAndView(pagina, map);
    }
}
