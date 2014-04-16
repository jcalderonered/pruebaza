/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.*;
import com.mimp.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author john
 */
@Controller
public class mainEtapas {

    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();
    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name = "HiberNna")
    private HiberNna ServicioNna = new HiberNna();
    @Resource(name = "HiberReporte")
    private HiberReporte ServicioReporte = new HiberReporte();
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();
    long tempIdNnaRegular;
    long nnaEditarEstudioCaso;
    long nnaEditarRevision;
    ArrayList<ExpedienteFamilia> listaMatching = new ArrayList();
    ArrayList<ExpedienteFamilia> listaEstudioCaso = new ArrayList();
    ArrayList<ExpedienteFamilia> listaRevision = new ArrayList();
    ArrayList<Designacion> allDesig = new ArrayList();
    Nna nnaPrioritario = new Nna();
    String volver;

    @RequestMapping(value = "/fametap", method = RequestMethod.GET)
    public ModelAndView FamEtap(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        volver = "/fametap";
        map.addAttribute("volver", volver);
        map.put("listaFamilias", servicioEtapa.getListaFamilias());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_formativa", map);
    }

    @RequestMapping(value = "/ConstanciaInformativa", method = RequestMethod.POST)
    public ModelAndView ConstanciaInformativa_POST(ModelMap map,
            @RequestParam("idFamilia") long idFamilia,
            @RequestParam("constancia") String constancia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        session.setAttribute("idFamilia", idFamilia);
        session.setAttribute("constancia", constancia);

        return new ModelAndView("redirect:/ConstanciaInformativa", map);
    }

    @RequestMapping(value = "/ConstanciaInformativa", method = RequestMethod.GET)
    public ModelAndView ConstanciaInformativa_GET(ModelMap map,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Familia tempFam = new Familia();
        if (session.getAttribute("idFamilia") != null) {

            tempFam = servicioEtapa.getFamilia((long) session.getAttribute("idFamilia"));
            tempFam.setConstancia((String) session.getAttribute("constancia"));

            servicioEtapa.UpdateFamilia(tempFam);

            ArrayList<Familia> allFamilias = new ArrayList();
            allFamilias = servicioEtapa.getListaFamilias();
            map.put("listaFamilias", allFamilias);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Agregó una constancia informativa a la familia"
                    + " con ID: " + session.getAttribute("idFamilia");

            String Tipo_registro = "Constancia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            session.removeAttribute("idFamilia");
            session.removeAttribute("constancia");
            return new ModelAndView("/Personal/Buscador_etapa/etapa_formativa", map);
        } else {
            ArrayList<Familia> allFamilias = new ArrayList();
            allFamilias = servicioEtapa.getListaFamilias();
            map.put("listaFamilias", allFamilias);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_formativa", map);
        }
    }

    @RequestMapping(value = "/EtapaEvalNac", method = RequestMethod.GET)
    public ModelAndView EtapaEvalNac(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
        volver = "/EtapaEvalNac";
        map.addAttribute("volver", volver);
        map.put("ListaExpedientes", allListaExpedientes);
        map.put("df", df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);

    }

    @RequestMapping(value = "/EtapaEvalInter", method = RequestMethod.GET)
    public ModelAndView EtapaEvalInter(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
        volver = "/EtapaEvalInter";
        map.addAttribute("volver", volver);
        allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
        map.put("ListaExpedientes", allListaExpedientes);
        map.put("df", df);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);

    }

    @RequestMapping(value = "/PersonalAgregarEvalPsicologica", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarEvalPsicologica_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("familia") String familia,
            @RequestParam("origen") String origen,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "idPsicologica", required = false) String idPsicologica
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("familia", familia);
        session.setAttribute("origen", origen);
        session.setAttribute("volver", volver);
        session.setAttribute("idPsicologica", idPsicologica);

        return new ModelAndView("redirect:/PersonalAgregarEvalPsicologica", map);

    }

    @RequestMapping(value = "/PersonalAgregarEvalPsicologica", method = RequestMethod.GET)
    public ModelAndView PersonalAgregarEvalPsicologica_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idPsicologica") != null && session.getAttribute("idExpediente") != null) {
            Evaluacion tempEval = new Evaluacion();
            if (!session.getAttribute("idPsicologica").equals("")) {
                long temp = Long.parseLong((String) session.getAttribute("idPsicologica"));
                tempEval = servicioEtapa.getEvaluacion(temp);
                map.put("psicologica", tempEval);
            }

            map.put("idExpediente", session.getAttribute("idExpediente"));
            map.put("familia", session.getAttribute("familia"));
            map.put("origen", session.getAttribute("origen"));
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.addAttribute("volver", volver);
            map.put("df", df);

            session.removeAttribute("idExpediente");
            session.removeAttribute("familia");
            session.removeAttribute("origen");
            session.removeAttribute("volver");
            session.removeAttribute("idPsicologica");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_psic", map);
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            volver = "/EtapaEvalNac";
            map.addAttribute("volver", volver);
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);

        }

    }

    @RequestMapping(value = "/PersonalCrearEvalPsicologicaNac", method = RequestMethod.POST)
    public ModelAndView PersonalCrearEvalPsicologicaNac_POST(ModelMap map, HttpSession session,
            @ModelAttribute("idExpediente") long idExpediente,
            @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "resultado", required = false) String resultado,
            @RequestParam(value = "numEval", required = false) String numEval,
            @RequestParam(value = "fechaResul", required = false) String fechaResul,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam("origen") String origen
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("fechaAsig", fechaAsig);
        session.setAttribute("personal", personal);
        session.setAttribute("resultado", resultado);
        session.setAttribute("numEval", numEval);
        session.setAttribute("fechaResul", fechaResul);
        session.setAttribute("obs", obs);
        session.setAttribute("origen", origen);

        return new ModelAndView("redirect:/PersonalCrearEvalPsicologicaNac", map);
    }

    @RequestMapping(value = "/PersonalCrearEvalPsicologicaNac", method = RequestMethod.GET)
    public ModelAndView PersonalCrearEvalPsicologicaNac_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null && session.getAttribute("personal") != null) {
            Evaluacion tempEval = new Evaluacion();
            tempEval.setExpedienteFamilia(servicioEtapa.getExpedienteFamilia((long) session.getAttribute("idExpediente")));
            String fechaAsig = (String) session.getAttribute("fechaAsig");
            Date tempfecha = tempEval.getFechaAsignacion();
            if (fechaAsig != null) {
                if (fechaAsig.contains("ene") || fechaAsig.contains("feb") || fechaAsig.contains("mar") || fechaAsig.contains("abr")
                        || fechaAsig.contains("may") || fechaAsig.contains("jun") || fechaAsig.contains("jul") || fechaAsig.contains("ago")
                        || fechaAsig.contains("set") || fechaAsig.contains("oct") || fechaAsig.contains("nov") || fechaAsig.contains("dic")) {
                    tempEval.setFechaAsignacion(tempfecha);
                } else {
                    if (!fechaAsig.equals("")) {
                        tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
                    } else {
                        tempEval.setFechaAsignacion(null);
                    }
                }
            } else {
                tempEval.setFechaAsignacion(null);
            }
            tempEval.setPersonal(ServicioPersonal.getPersonal((long) session.getAttribute("personal")));
            tempEval.setResultado((String) session.getAttribute("resultado"));
            String fechaResul = (String) session.getAttribute("fechaResul");
            tempfecha = tempEval.getFechaResultado();
            if (fechaResul != null) {
                if (fechaResul.contains("ene") || fechaResul.contains("feb") || fechaResul.contains("mar") || fechaResul.contains("abr")
                        || fechaResul.contains("may") || fechaResul.contains("jun") || fechaResul.contains("jul") || fechaResul.contains("ago")
                        || fechaResul.contains("set") || fechaResul.contains("oct") || fechaResul.contains("nov") || fechaResul.contains("dic")) {
                    tempEval.setFechaResultado(tempfecha);
                } else {
                    if (!fechaResul.equals("")) {
                        tempEval.setFechaResultado(df.stringToDate(fechaResul));
                    } else {
                        tempEval.setFechaResultado(null);
                    }
                }
            } else {
                tempEval.setFechaResultado(null);
            }
            tempEval.setNumEval((String) session.getAttribute("numEval"));
            tempEval.setObservacion((String) session.getAttribute("obs"));
            tempEval.setTipo("psicologica");

            servicioEtapa.crearEvaluacion(tempEval);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Agregó una Evaluación Psicologica a la familia con ID Expediente"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            String origen = (String) session.getAttribute("origen");
            if (origen.equals("nacional")) {
                session.removeAttribute("idExpediente");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("personal");
                session.removeAttribute("resultado");
                session.removeAttribute("numEval");
                session.removeAttribute("fechaResul");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
            } else {
                session.removeAttribute("idExpediente");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("personal");
                session.removeAttribute("resultado");
                session.removeAttribute("numEval");
                session.removeAttribute("fechaResul");
                session.removeAttribute("obs");
                session.removeAttribute("origen");
                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
            }
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }

    }

    @RequestMapping(value = "/PersonalUpdateEvalPsicologicaNac", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateEvalPsicologicaNac_POST(ModelMap map, HttpSession session,
            @RequestParam("idEvalPsicologica") long idEvalPsicologica,
            @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "resultado", required = false) String resultado,
            @RequestParam(value = "numEval", required = false) String numEval,
            @RequestParam(value = "fechaResul", required = false) String fechaResul,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam("origen") String origen
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idEvalPsicologica", idEvalPsicologica);
        session.setAttribute("fechaAsig", fechaAsig);
        session.setAttribute("personal", personal);
        session.setAttribute("resultado", resultado);
        session.setAttribute("numEval", numEval);
        session.setAttribute("fechaResul", fechaResul);
        session.setAttribute("obs", obs);
        session.setAttribute("origen", origen);

        return new ModelAndView("redirect:/PersonalUpdateEvalPsicologicaNac", map);

    }

    @RequestMapping(value = "/PersonalUpdateEvalPsicologicaNac", method = RequestMethod.GET)
    public ModelAndView PersonalUpdateEvalPsicologicaNac_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idEvalPsicologica") != null && session.getAttribute("personal") != null) {
            Evaluacion evalPsicologica = new Evaluacion();
            evalPsicologica = servicioEtapa.getEvaluacion((long) session.getAttribute("idEvalPsicologica"));

            Date tempfecha = evalPsicologica.getFechaAsignacion();
            String fechaAsig = (String) session.getAttribute("fechaAsig");
            if (fechaAsig != null) {
                if (fechaAsig.contains("ene") || fechaAsig.contains("feb") || fechaAsig.contains("mar") || fechaAsig.contains("abr")
                        || fechaAsig.contains("may") || fechaAsig.contains("jun") || fechaAsig.contains("jul") || fechaAsig.contains("ago")
                        || fechaAsig.contains("set") || fechaAsig.contains("oct") || fechaAsig.contains("nov") || fechaAsig.contains("dic")) {
                    evalPsicologica.setFechaAsignacion(tempfecha);
                } else {
                    if (!fechaAsig.equals("")) {
                        evalPsicologica.setFechaAsignacion(df.stringToDate(fechaAsig));
                    } else {
                        evalPsicologica.setFechaAsignacion(null);
                    }
                }
            } else {
                evalPsicologica.setFechaAsignacion(null);
            }
            /*if (fechaAsig != null && !fechaAsig.equals("")) {
             evalPsicologica.setFechaAsignacion(df.stringToDate(fechaAsig));
             }
             if (fechaAsig == null || fechaAsig.equals("")) {
             evalPsicologica.setFechaAsignacion(null);
             }*/
            evalPsicologica.setPersonal(ServicioPersonal.getPersonal((long) session.getAttribute("personal")));
            evalPsicologica.setResultado((String) session.getAttribute("resultado"));
            tempfecha = evalPsicologica.getFechaResultado();
            String fechaResul = (String) session.getAttribute("fechaResul");
            if (fechaResul != null) {
                if (fechaResul.contains("ene") || fechaResul.contains("feb") || fechaResul.contains("mar") || fechaResul.contains("abr")
                        || fechaResul.contains("may") || fechaResul.contains("jun") || fechaResul.contains("jul") || fechaResul.contains("ago")
                        || fechaResul.contains("set") || fechaResul.contains("oct") || fechaResul.contains("nov") || fechaResul.contains("dic")) {
                    evalPsicologica.setFechaResultado(tempfecha);
                } else {
                    if (!fechaResul.equals("")) {
                        evalPsicologica.setFechaResultado(df.stringToDate(fechaResul));
                    } else {
                        evalPsicologica.setFechaResultado(null);
                    }
                }
            } else {
                evalPsicologica.setFechaResultado(null);
            }
            /*if (fechaResul != null && !fechaResul.equals("")) {
             evalPsicologica.setFechaResultado(df.stringToDate(fechaResul));
             }
             if (fechaResul == null || fechaResul.equals("")) {
             evalPsicologica.setFechaResultado(null);
             }*/
            evalPsicologica.setNumEval((String) session.getAttribute("numEval"));
            evalPsicologica.setObservacion((String) session.getAttribute("obs"));

            servicioEtapa.updateEvaluacion(evalPsicologica);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Editó la Evaluación Psicologica a la familia con ID Expediente"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            String origen = (String) session.getAttribute("origen");
            if (origen.equals("nacional")) {

                session.removeAttribute("idEvalPsicologica");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
            } else {

                session.removeAttribute("idEvalPsicologica");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
            }
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalAgregarEvalSocial", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarEvalSocial_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("familia") String familia,
            @RequestParam("origen") String origen,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "idSocial", required = false) String idSocial
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("familia", familia);
        session.setAttribute("origen", origen);
        session.setAttribute("volver", volver);
        session.setAttribute("idSocial", idSocial);

        return new ModelAndView("redirect:/PersonalAgregarEvalSocial", map);

    }

    @RequestMapping(value = "/PersonalAgregarEvalSocial", method = RequestMethod.GET)
    public ModelAndView PersonalAgregarEvalSocial_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idSocial") != null && session.getAttribute("idExpediente") != null) {
            Evaluacion tempEval = new Evaluacion();
            if (!session.getAttribute("idSocial").equals("")) {
                long temp = Long.parseLong((String) session.getAttribute("idSocial"));
                tempEval = servicioEtapa.getEvaluacion(temp);
                map.put("social", tempEval);
            }

            map.put("idExpediente", session.getAttribute("idExpediente"));
            map.put("familia", session.getAttribute("familia"));
            map.put("origen", session.getAttribute("origen"));
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.addAttribute("volver", volver);
            map.put("df", df);

            session.removeAttribute("idExpediente");
            session.removeAttribute("familia");
            session.removeAttribute("origen");
            session.removeAttribute("volver");
            session.removeAttribute("idSocial");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_soc", map);
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            volver = "/EtapaEvalNac";
            map.addAttribute("volver", volver);
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);

        }

    }

    @RequestMapping(value = "/PersonalCrearEvalSocialNac", method = RequestMethod.POST)
    public ModelAndView PersonalCrearEvalSocialNac_POST(ModelMap map, HttpSession session,
            @ModelAttribute("idExpediente") long idExpediente,
            @RequestParam("origen") String origen,
            @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "resultado", required = false) String resultado,
            @RequestParam(value = "fechaResul", required = false) String fechaResul,
            @RequestParam(value = "numEval", required = false) String numEval,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("origen", origen);
        session.setAttribute("fechaAsig", fechaAsig);
        session.setAttribute("personal", personal);
        session.setAttribute("resultado", resultado);
        session.setAttribute("fechaResul", fechaResul);
        session.setAttribute("numEval", numEval);
        session.setAttribute("obs", obs);
        return new ModelAndView("redirect:/PersonalCrearEvalSocialNac", map);

    }

    @RequestMapping(value = "/PersonalCrearEvalSocialNac", method = RequestMethod.GET)
    public ModelAndView PersonalCrearEvalSocialNac_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null && session.getAttribute("personal") != null) {
            Evaluacion tempEval = new Evaluacion();
            tempEval.setExpedienteFamilia(servicioEtapa.getExpedienteFamilia((long) session.getAttribute("idExpediente")));
            Date tempfecha = tempEval.getFechaAsignacion();
            String fechaAsig = (String) session.getAttribute("fechaAsig");
            if (fechaAsig != null) {
                if (fechaAsig.contains("ene") || fechaAsig.contains("feb") || fechaAsig.contains("mar") || fechaAsig.contains("abr")
                        || fechaAsig.contains("may") || fechaAsig.contains("jun") || fechaAsig.contains("jul") || fechaAsig.contains("ago")
                        || fechaAsig.contains("set") || fechaAsig.contains("oct") || fechaAsig.contains("nov") || fechaAsig.contains("dic")) {
                    tempEval.setFechaAsignacion(tempfecha);
                } else {
                    if (!fechaAsig.equals("")) {
                        tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
                    } else {
                        tempEval.setFechaAsignacion(null);
                    }
                }
            } else {
                tempEval.setFechaAsignacion(null);
            }
            /*if (fechaAsig != null && !fechaAsig.equals("")) {
             tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
             }
             if (fechaAsig == null || fechaAsig.equals("")) {
             tempEval.setFechaAsignacion(null);
             }*/
            tempEval.setPersonal(ServicioPersonal.getPersonal((long) session.getAttribute("personal")));
            tempEval.setResultado((String) session.getAttribute("resultado"));
            tempfecha = tempEval.getFechaResultado();
            String fechaResul = (String) session.getAttribute("fechaResul");
            if (fechaResul != null) {
                if (fechaResul.contains("ene") || fechaResul.contains("feb") || fechaResul.contains("mar") || fechaResul.contains("abr")
                        || fechaResul.contains("may") || fechaResul.contains("jun") || fechaResul.contains("jul") || fechaResul.contains("ago")
                        || fechaResul.contains("set") || fechaResul.contains("oct") || fechaResul.contains("nov") || fechaResul.contains("dic")) {
                    tempEval.setFechaResultado(tempfecha);
                } else {
                    if (!fechaResul.equals("")) {
                        tempEval.setFechaResultado(df.stringToDate(fechaResul));
                    } else {
                        tempEval.setFechaResultado(null);
                    }
                }
            } else {
                tempEval.setFechaResultado(null);
            }
            /*if (fechaResul != null && !fechaResul.equals("")) {
             tempEval.setFechaResultado(df.stringToDate(fechaResul));
             }
             if (fechaResul == null || fechaResul.equals("")) {
             tempEval.setFechaResultado(null);
             }*/
            tempEval.setNumEval((String) session.getAttribute("numEval"));
            tempEval.setObservacion((String) session.getAttribute("obs"));
            tempEval.setTipo("social");

            servicioEtapa.crearEvaluacion(tempEval);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Creó la Evaluación Social a la familia con ID Expediente"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            String origen = (String) session.getAttribute("origen");
            if (origen.equals("nacional")) {

                session.removeAttribute("idExpediente");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
            } else {

                session.removeAttribute("idExpediente");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
            }
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalUpdateEvalSocialNac", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateEvalSocialNac_POST(ModelMap map, HttpSession session,
            @RequestParam("idEvalSocial") long idEvalSocial,
            @RequestParam("origen") String origen,
            @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "resultado", required = false) String resultado,
            @RequestParam(value = "fechaResul", required = false) String fechaResul,
            @RequestParam(value = "numEval", required = false) String numEval,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idEvalSocial", idEvalSocial);
        session.setAttribute("origen", origen);
        session.setAttribute("fechaAsig", fechaAsig);
        session.setAttribute("personal", personal);
        session.setAttribute("resultado", resultado);
        session.setAttribute("fechaResul", fechaResul);
        session.setAttribute("numEval", numEval);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/PersonalUpdateEvalSocialNac", map);

    }

    @RequestMapping(value = "/PersonalUpdateEvalSocialNac", method = RequestMethod.GET)
    public ModelAndView PersonalUpdateEvalSocialNac_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idEvalSocial") != null && session.getAttribute("personal") != null) {
            Evaluacion evalSocial = new Evaluacion();
            evalSocial = servicioEtapa.getEvaluacion((long) session.getAttribute("idEvalSocial"));
            Date tempfecha = evalSocial.getFechaAsignacion();
            String fechaAsig = (String) session.getAttribute("fechaAsig");
            if (fechaAsig != null) {
                if (fechaAsig.contains("ene") || fechaAsig.contains("feb") || fechaAsig.contains("mar") || fechaAsig.contains("abr")
                        || fechaAsig.contains("may") || fechaAsig.contains("jun") || fechaAsig.contains("jul") || fechaAsig.contains("ago")
                        || fechaAsig.contains("set") || fechaAsig.contains("oct") || fechaAsig.contains("nov") || fechaAsig.contains("dic")) {
                    evalSocial.setFechaAsignacion(tempfecha);
                } else {
                    if (!fechaAsig.equals("")) {
                        evalSocial.setFechaAsignacion(df.stringToDate(fechaAsig));
                    } else {
                        evalSocial.setFechaAsignacion(null);
                    }
                }
            } else {
                evalSocial.setFechaAsignacion(null);
            }
            /*if (fechaAsig != null && !fechaAsig.equals("")) {
             evalSocial.setFechaAsignacion(df.stringToDate(fechaAsig));
             }
             if (fechaAsig == null || fechaAsig.equals("")) {
             evalSocial.setFechaAsignacion(null);
             }*/
            evalSocial.setPersonal(ServicioPersonal.getPersonal((long) session.getAttribute("personal")));
            evalSocial.setResultado((String) session.getAttribute("resultado"));
            tempfecha = evalSocial.getFechaResultado();
            String fechaResul = (String) session.getAttribute("fechaResul");
            if (fechaResul != null) {
                if (fechaResul.contains("ene") || fechaResul.contains("feb") || fechaResul.contains("mar") || fechaResul.contains("abr")
                        || fechaResul.contains("may") || fechaResul.contains("jun") || fechaResul.contains("jul") || fechaResul.contains("ago")
                        || fechaResul.contains("set") || fechaResul.contains("oct") || fechaResul.contains("nov") || fechaResul.contains("dic")) {
                    evalSocial.setFechaResultado(tempfecha);
                } else {
                    if (!fechaResul.equals("")) {
                        evalSocial.setFechaResultado(df.stringToDate(fechaResul));
                    } else {
                        evalSocial.setFechaResultado(null);
                    }
                }
            } else {
                evalSocial.setFechaResultado(null);
            }
            /*if (fechaResul != null && !fechaResul.equals("")) {
             evalSocial.setFechaResultado(df.stringToDate(fechaResul));
             }
             if (fechaResul == null || fechaResul.equals("")) {
             evalSocial.setFechaResultado(null);
             }*/
            evalSocial.setNumEval((String) session.getAttribute("numEval"));
            evalSocial.setObservacion((String) session.getAttribute("obs"));

            servicioEtapa.updateEvaluacion(evalSocial);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Editó la Evaluación Social a la familia con ID Expediente"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            String origen = (String) session.getAttribute("origen");
            if (origen.equals("nacional")) {

                session.removeAttribute("idEvalSocial");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
            } else {

                session.removeAttribute("idEvalSocial");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
            }
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalAgregarEvalLegal", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarEvalLegal_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("familia") String familia,
            @RequestParam("origen") String origen,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "idLegal", required = false) String idLegal
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("familia", familia);
        session.setAttribute("origen", origen);
        session.setAttribute("volver", volver);
        session.setAttribute("idLegal", idLegal);

        return new ModelAndView("redirect:/PersonalAgregarEvalLegal", map);
    }

    @RequestMapping(value = "/PersonalAgregarEvalLegal", method = RequestMethod.GET)
    public ModelAndView PersonalAgregarEvalLegal_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("idLegal") != null && session.getAttribute("idExpediente") != null) {
            Evaluacion tempEval = new Evaluacion();
            if (!session.getAttribute("idLegal").equals("")) {
                long temp = Long.parseLong((String) session.getAttribute("idLegal"));
                tempEval = servicioEtapa.getLegal(temp);
                map.put("legal", tempEval);
            }

            map.put("idExpediente", session.getAttribute("idExpediente"));
            map.put("familia", session.getAttribute("familia"));
            map.put("origen", session.getAttribute("origen"));
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.addAttribute("volver", volver);
            map.put("df", df);

            session.removeAttribute("idExpediente");
            session.removeAttribute("familia");
            session.removeAttribute("origen");
            session.removeAttribute("volver");
            session.removeAttribute("idLegal");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_legal", map);
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            volver = "/EtapaEvalNac";
            map.addAttribute("volver", volver);
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalCrearEvalLegalNac", method = RequestMethod.POST)
    public ModelAndView PersonalCrearEvalLegalNac_POST(ModelMap map, HttpSession session,
            @ModelAttribute("idExpediente") long idExpediente,
            @RequestParam("origen") String origen,
            @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "resultado", required = false) String resultado,
            @RequestParam(value = "numEval", required = false) String numEval,
            @RequestParam(value = "fechaResul", required = false) String fechaResul,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("origen", origen);
        session.setAttribute("fechaAsig", fechaAsig);
        session.setAttribute("personal", personal);
        session.setAttribute("resultado", resultado);
        session.setAttribute("numEval", numEval);
        session.setAttribute("fechaResul", fechaResul);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/PersonalCrearEvalLegalNac", map);

    }

    @RequestMapping(value = "/PersonalCrearEvalLegalNac", method = RequestMethod.GET)
    public ModelAndView PersonalCrearEvalLegalNac_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null && session.getAttribute("personal") != null) {
            Evaluacion tempEval = new Evaluacion();
            tempEval.setExpedienteFamilia(servicioEtapa.getExpedienteFamilia((long) session.getAttribute("idExpediente")));
            Date tempfecha = tempEval.getFechaAsignacion();
            String fechaAsig = (String) session.getAttribute("fechaAsig");
            if (fechaAsig != null) {
                if (fechaAsig.contains("ene") || fechaAsig.contains("feb") || fechaAsig.contains("mar") || fechaAsig.contains("abr")
                        || fechaAsig.contains("may") || fechaAsig.contains("jun") || fechaAsig.contains("jul") || fechaAsig.contains("ago")
                        || fechaAsig.contains("set") || fechaAsig.contains("oct") || fechaAsig.contains("nov") || fechaAsig.contains("dic")) {
                    tempEval.setFechaAsignacion(tempfecha);
                } else {
                    if (!fechaAsig.equals("")) {
                        tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
                    } else {
                        tempEval.setFechaAsignacion(null);
                    }
                }
            } else {
                tempEval.setFechaAsignacion(null);
            }
            /*if (fechaAsig != null && !fechaAsig.equals("")) {
             tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
             }
             if (fechaAsig == null || fechaAsig.equals("")) {
             tempEval.setFechaAsignacion(null);
             }*/
            tempEval.setFechaAsignacion(df.stringToDate(fechaAsig));
            tempEval.setPersonal(ServicioPersonal.getPersonal((long) session.getAttribute("personal")));
            tempEval.setResultado((String) session.getAttribute("resultado"));
            tempfecha = tempEval.getFechaResultado();
            String fechaResul = (String) session.getAttribute("fechaResul");
            if (fechaResul != null) {
                if (fechaResul.contains("ene") || fechaResul.contains("feb") || fechaResul.contains("mar") || fechaResul.contains("abr")
                        || fechaResul.contains("may") || fechaResul.contains("jun") || fechaResul.contains("jul") || fechaResul.contains("ago")
                        || fechaResul.contains("set") || fechaResul.contains("oct") || fechaResul.contains("nov") || fechaResul.contains("dic")) {
                    tempEval.setFechaResultado(tempfecha);
                } else {
                    if (!fechaResul.equals("")) {
                        tempEval.setFechaResultado(df.stringToDate(fechaResul));
                    } else {
                        tempEval.setFechaResultado(null);
                    }
                }
            } else {
                tempEval.setFechaResultado(null);
            }
            /*if (fechaResul != null && !fechaResul.equals("")) {
             tempEval.setFechaResultado(df.stringToDate(fechaResul));
             }
             if (fechaResul == null || fechaResul.equals("")) {
             tempEval.setFechaResultado(null);
             }*/
            tempEval.setNumEval((String) session.getAttribute("numEval"));
            tempEval.setObservacion((String) session.getAttribute("obs"));
            tempEval.setTipo("legal");

            servicioEtapa.crearEvaluacion(tempEval);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Creó la Evaluación Legal a la familia con ID Expediente"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            String origen = (String) session.getAttribute("origen");
            if (origen.equals("nacional")) {

                session.removeAttribute("idExpediente");
                session.removeAttribute("personal");
                session.removeAttribute("resultado");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("fechaResul");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
            } else {

                session.removeAttribute("idExpediente");
                session.removeAttribute("personal");
                session.removeAttribute("resultado");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("fechaResul");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
            }
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalUpdateEvalLegalNac", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateEvalLegalNac_POST(ModelMap map, HttpSession session,
            @RequestParam("idEvalLegal") long idEvalLegal,
            @RequestParam("origen") String origen,
            @RequestParam(value = "fechaAsig", required = false) String fechaAsig,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "resultado", required = false) String resultado,
            @RequestParam(value = "numEval", required = false) String numEval,
            @RequestParam(value = "fechaResul", required = false) String fechaResul,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idEvalLegal", idEvalLegal);
        session.setAttribute("origen", origen);
        session.setAttribute("fechaAsig", fechaAsig);
        session.setAttribute("personal", personal);
        session.setAttribute("resultado", resultado);
        session.setAttribute("numEval", numEval);
        session.setAttribute("fechaResul", fechaResul);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/PersonalUpdateEvalLegalNac", map);

    }

    @RequestMapping(value = "/PersonalUpdateEvalLegalNac", method = RequestMethod.GET)
    public ModelAndView PersonalUpdateEvalLegalNac_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("idEvalLegal") != null && session.getAttribute("personal") != null) {
            Evaluacion evalLegal = new Evaluacion();
            evalLegal = servicioEtapa.getEvaluacion((long) session.getAttribute("idEvalLegal"));
            Date tempfecha = evalLegal.getFechaAsignacion();
            String fechaAsig = (String) session.getAttribute("fechaAsig");
            if (fechaAsig != null) {
                if (fechaAsig.contains("ene") || fechaAsig.contains("feb") || fechaAsig.contains("mar") || fechaAsig.contains("abr")
                        || fechaAsig.contains("may") || fechaAsig.contains("jun") || fechaAsig.contains("jul") || fechaAsig.contains("ago")
                        || fechaAsig.contains("set") || fechaAsig.contains("oct") || fechaAsig.contains("nov") || fechaAsig.contains("dic")) {
                    evalLegal.setFechaAsignacion(tempfecha);
                } else {
                    if (!fechaAsig.equals("")) {
                        evalLegal.setFechaAsignacion(df.stringToDate(fechaAsig));
                    } else {
                        evalLegal.setFechaAsignacion(null);
                    }
                }
            } else {
                evalLegal.setFechaAsignacion(null);
            }
            /*if (fechaAsig != null && !fechaAsig.equals("")) {
             evalLegal.setFechaAsignacion(df.stringToDate(fechaAsig));
             }
             if (fechaAsig == null || fechaAsig.equals("")) {
             evalLegal.setFechaAsignacion(null);
             }*/
            evalLegal.setFechaAsignacion(df.stringToDate(fechaAsig));
            evalLegal.setPersonal(ServicioPersonal.getPersonal((long) session.getAttribute("personal")));
            evalLegal.setResultado((String) session.getAttribute("resultado"));
            tempfecha = evalLegal.getFechaResultado();
            String fechaResul = (String) session.getAttribute("fechaResul");
            if (fechaResul != null) {
                if (fechaResul.contains("ene") || fechaResul.contains("feb") || fechaResul.contains("mar") || fechaResul.contains("abr")
                        || fechaResul.contains("may") || fechaResul.contains("jun") || fechaResul.contains("jul") || fechaResul.contains("ago")
                        || fechaResul.contains("set") || fechaResul.contains("oct") || fechaResul.contains("nov") || fechaResul.contains("dic")) {
                    evalLegal.setFechaResultado(tempfecha);
                } else {
                    if (!fechaResul.equals("")) {
                        evalLegal.setFechaResultado(df.stringToDate(fechaResul));
                    } else {
                        evalLegal.setFechaResultado(null);
                    }
                }
            } else {
                evalLegal.setFechaResultado(null);
            }
            /*if (fechaResul != null && !fechaResul.equals("")) {
             evalLegal.setFechaResultado(df.stringToDate(fechaResul));
             }
             if (fechaResul == null || fechaResul.equals("")) {
             evalLegal.setFechaResultado(null);
             }*/
            evalLegal.setNumEval((String) session.getAttribute("numEval"));
            evalLegal.setObservacion((String) session.getAttribute("obs"));

            servicioEtapa.updateEvaluacion(evalLegal);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Editó la Evaluación Legal a la familia con ID Expediente"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }
            String origen = (String) session.getAttribute("origen");
            if (origen.equals("nacional")) {

                session.removeAttribute("idEvalLegal");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
            } else {

                session.removeAttribute("idEvalLegal");
                session.removeAttribute("personal");
                session.removeAttribute("fechaAsig");
                session.removeAttribute("resultado");
                session.removeAttribute("fechaResul");
                session.removeAttribute("numEval");
                session.removeAttribute("obs");
                session.removeAttribute("origen");

                ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
                allListaExpedientes = servicioEtapa.ListaExpedientes("internacional", "evaluacion");
                map.put("ListaExpedientes", allListaExpedientes);
                map.put("df", df);
                return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_int", map);
            }
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalRegistrarResolucionEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalRegistrarResolucionEvaluacion_POST(ModelMap map, HttpSession session,
            @RequestParam("idLegal") long idLegal,
            @RequestParam("origen") String origen,
            @RequestParam("familia") String familia
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idLegal", idLegal);
        session.setAttribute("origen", origen);
        session.setAttribute("familia", familia);

        return new ModelAndView("redirect:/PersonalRegistrarResolucionEvaluacion", map);

    }

    @RequestMapping(value = "/PersonalRegistrarResolucionEvaluacion", method = RequestMethod.GET)
    public ModelAndView PersonalRegistrarResolucionEvaluacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idLegal") != null) {
            map.put("idLegal", session.getAttribute("idLegal"));
            map.put("familia", session.getAttribute("familia"));
            map.put("origen", session.getAttribute("origen"));
            map.put("df", df);

            session.removeAttribute("idLegal");
            session.removeAttribute("familia");
            session.removeAttribute("origen");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/reg_resol", map);
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }

    }

    @RequestMapping(value = "/PersonalEditarResolucionEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalEditarResolucionEvaluacion_POST(ModelMap map, HttpSession session,
            @RequestParam("idResol") long idResol,
            @RequestParam("origen") String origen,
            @RequestParam("familia") String familia
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idResol", idResol);
        session.setAttribute("familia", familia);
        session.setAttribute("origen", origen);

        return new ModelAndView("redirect:/PersonalEditarResolucionEvaluacion", map);

    }

    @RequestMapping(value = "/PersonalEditarResolucionEvaluacion", method = RequestMethod.GET)
    public ModelAndView PersonalEditarResolucionEvaluacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idResol") != null) {
            map.put("resolucion", servicioEtapa.getResolucion((long) session.getAttribute("idResol")));
            map.put("familia", session.getAttribute("familia"));
            map.put("origen", session.getAttribute("origen"));
            map.put("df", df);

            session.removeAttribute("idResol");
            session.removeAttribute("familia");
            session.removeAttribute("origen");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/reg_resol", map);

        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalCrearResolEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalCrearResolEvaluacion_POST(ModelMap map, HttpSession session,
            @RequestParam("idLegal") long idLegal,
            @RequestParam("origen") String origen,
            @RequestParam("familia") String familia,
            @RequestParam("numResol") String numResol,
            @RequestParam("tipo") String tipo,
            @RequestParam("fechaResol") String fechaResol,
            @RequestParam("fechaNot") String fechaNot,
            @RequestParam(value = "tipoEspera", required = false) String tipoEspera
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idLegal", idLegal);
        session.setAttribute("origen", origen);
        session.setAttribute("familia", familia);
        session.setAttribute("numResol", numResol);
        session.setAttribute("tipo", tipo);
        session.setAttribute("fechaResol", fechaResol);
        session.setAttribute("fechaNot", fechaNot);
        session.setAttribute("tipoEspera", tipoEspera);

        return new ModelAndView("redirect:/PersonalCrearResolEvaluacion", map);

    }

    @RequestMapping(value = "/PersonalCrearResolEvaluacion", method = RequestMethod.GET)
    public ModelAndView PersonalCrearResolEvaluacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idLegal") != null) {
            Evaluacion tempEval = new Evaluacion();
            tempEval = servicioEtapa.getLegal((long) session.getAttribute("idLegal"));

            Resolucion tempResol = new Resolucion();
            tempResol.setEvaluacion(tempEval);
            tempResol.setNumero((String) session.getAttribute("numResol"));
            tempResol.setTipo((String) session.getAttribute("tipo"));
            Date tempfecha = tempResol.getFechaResol();
            String fechaResol = (String) session.getAttribute("fechaResol");
            if (fechaResol != null) {
                if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
                        || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
                        || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
                    tempResol.setFechaResol(tempfecha);
                } else {
                    if (!fechaResol.equals("")) {
                        tempResol.setFechaResol(df.stringToDate(fechaResol));
                    } else {
                        tempResol.setFechaResol(null);
                    }
                }
            } else {
                tempResol.setFechaResol(null);
            }
            /*if (fechaResol != null && !fechaResol.equals("")) {
             tempResol.setFechaResol(df.stringToDate(fechaResol));
             }
             if (fechaResol == null || fechaResol.equals("")) {
             tempResol.setFechaResol(null);
             }*/
            tempfecha = tempResol.getFechaNotificacion();
            String fechaNot = (String) session.getAttribute("fechaNot");
            if (fechaNot != null) {
                if (fechaNot.contains("ene") || fechaNot.contains("feb") || fechaNot.contains("mar") || fechaNot.contains("abr")
                        || fechaNot.contains("may") || fechaNot.contains("jun") || fechaNot.contains("jul") || fechaNot.contains("ago")
                        || fechaNot.contains("set") || fechaNot.contains("oct") || fechaNot.contains("nov") || fechaNot.contains("dic")) {
                    tempResol.setFechaNotificacion(tempfecha);
                } else {
                    if (!fechaNot.equals("")) {
                        tempResol.setFechaNotificacion(df.stringToDate(fechaNot));
                    } else {
                        tempResol.setFechaNotificacion(null);
                    }
                }
            } else {
                tempResol.setFechaNotificacion(null);
            }
            /*if (fechaNot != null && !fechaNot.equals("")) {
             tempResol.setFechaNotificacion(df.stringToDate(fechaNot));
             }
             if (fechaNot == null || fechaNot.equals("")) {
             tempResol.setFechaNotificacion(null);
             }*/

            servicioEtapa.crearResolEvaluacion(tempResol);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Creó la Resolucion de Evaluación con ID"
                    + ": " + tempResol.getIdresolucion();

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            tempEval = servicioEtapa.getLegal((long) session.getAttribute("idLegal"));
            String tipo = (String) session.getAttribute("tipo");
            String tipoEspera = (String) session.getAttribute("tipoEspera");
            if (tipo != null && tipo.equals("apto")) {
                ExpedienteFamilia tempFam = new ExpedienteFamilia();
                tempFam = tempEval.getExpedienteFamilia();
                if (tipoEspera.equals("espera") || tipoEspera.equals("")) {
                    tempFam.setEstado("espera");
                } else {
                    tempFam.setEstado("esperainter");
                }
                tempFam.setRnaa(Short.parseShort("0"));
                servicioEtapa.updateExpedienteFamilia(tempFam);
                if (tipoEspera.equals("espera") || tipoEspera.equals("")) {
                    map.put("df", df);
                    map.put("listaEspera", servicioEtapa.getListaEspera());
                    return new ModelAndView("/Personal/Buscador_etapa/lista_espera", map);
                } else {
                    map.put("df", df);
                    map.put("listaEspera", servicioEtapa.getListaEsperaAdopcionInter());
                    return new ModelAndView("/Personal/Buscador_etapa/lista_espera_inter", map);
                }
            }
            map.put("familia", session.getAttribute("familia"));
            map.put("legal", tempEval);
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.put("origen", session.getAttribute("origen"));
            map.put("df", df);

            session.removeAttribute("idLegal");
            session.removeAttribute("origen");
            session.removeAttribute("familia");
            session.removeAttribute("numResol");
            session.removeAttribute("tipo");
            session.removeAttribute("fechaResol");
            session.removeAttribute("fechaNot");
            session.removeAttribute("tipoEsper");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_legal", map);
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    @RequestMapping(value = "/PersonalUpdateResolEvaluacion", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateResolEvaluacion_POST(ModelMap map, HttpSession session,
            @RequestParam("idResolucion") long idResolucion,
            @RequestParam("origen") String origen,
            @RequestParam("familia") String familia,
            @RequestParam("numResol") String numResol,
            @RequestParam("tipo") String tipo,
            @RequestParam("fechaResol") String fechaResol,
            @RequestParam("fechaNot") String fechaNot
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idResolucion", idResolucion);
        session.setAttribute("origen", origen);
        session.setAttribute("familia", familia);
        session.setAttribute("numResol", numResol);
        session.setAttribute("tipo", tipo);
        session.setAttribute("fechaResol", fechaResol);
        session.setAttribute("fechaNot", fechaNot);
        return new ModelAndView("redirect:/PersonalUpdateResolEvaluacion", map);

    }

    @RequestMapping(value = "/PersonalUpdateResolEvaluacion", method = RequestMethod.GET)
    public ModelAndView PersonalUpdateResolEvaluacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idResolucion") != null) {
            Resolucion tempResol = new Resolucion();
            tempResol = servicioEtapa.getResolucion((long) session.getAttribute("idResolucion"));
            tempResol.setNumero((String) session.getAttribute("numResol"));
            tempResol.setTipo((String) session.getAttribute("tipo"));
            Date tempfecha = tempResol.getFechaResol();
            String fechaResol = (String) session.getAttribute("fechaResol");
            if (fechaResol != null) {
                if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
                        || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
                        || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
                    tempResol.setFechaResol(tempfecha);
                } else {
                    if (!fechaResol.equals("")) {
                        tempResol.setFechaResol(df.stringToDate(fechaResol));
                    } else {
                        tempResol.setFechaResol(null);
                    }
                }
            } else {
                tempResol.setFechaResol(null);
            }
            /*if (fechaResol != null && !fechaResol.equals("")) {
             tempResol.setFechaResol(df.stringToDate(fechaResol));
             }
             if (fechaResol == null || fechaResol.equals("")) {
             tempResol.setFechaResol(null);
             }*/
            tempfecha = tempResol.getFechaNotificacion();
            String fechaNot = (String) session.getAttribute("fechaNot");
            if (fechaNot != null) {
                if (fechaNot.contains("ene") || fechaNot.contains("feb") || fechaNot.contains("mar") || fechaNot.contains("abr")
                        || fechaNot.contains("may") || fechaNot.contains("jun") || fechaNot.contains("jul") || fechaNot.contains("ago")
                        || fechaNot.contains("set") || fechaNot.contains("oct") || fechaNot.contains("nov") || fechaNot.contains("dic")) {
                    tempResol.setFechaNotificacion(tempfecha);
                } else {
                    if (!fechaNot.equals("")) {
                        tempResol.setFechaNotificacion(df.stringToDate(fechaNot));
                    } else {
                        tempResol.setFechaNotificacion(null);
                    }
                }
            } else {
                tempResol.setFechaNotificacion(null);
            }
            /*if (fechaNot != null && !fechaNot.equals("")) {
             tempResol.setFechaNotificacion(df.stringToDate(fechaNot));
             }
             if (fechaNot == null || fechaNot.equals("")) {
             tempResol.setFechaNotificacion(null);
             }*/

            servicioEtapa.updateResolEvaluacion(tempResol);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Editó la Resolución con ID"
                    + ": " + session.getAttribute("idResolucion");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            tempResol = servicioEtapa.getResolucion((long) session.getAttribute("idResolucion"));
            String tipo = (String) session.getAttribute("tipo");
            if (tipo != null && tipo.equals("apto")) {
                ExpedienteFamilia tempFam = new ExpedienteFamilia();
                tempFam = tempResol.getEvaluacion().getExpedienteFamilia();
                tempFam.setEstado("espera");
                servicioEtapa.updateExpedienteFamilia(tempFam);
            }
            session.removeAttribute("idResolucion");
            session.removeAttribute("origen");
            session.removeAttribute("familia");
            session.removeAttribute("numResol");
            session.removeAttribute("tipo");
            session.removeAttribute("fechaResol");
            session.removeAttribute("fechaNot");

            map.put("familia", (String) session.getAttribute("familia"));
            map.put("legal", servicioEtapa.getLegal(tempResol.getEvaluacion().getIdevaluacion()));
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.put("origen", (String) session.getAttribute("origen"));
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/detalle_eval_legal", map);
        } else {
            ArrayList<ExpedienteFamilia> allListaExpedientes = new ArrayList();
            allListaExpedientes = servicioEtapa.ListaExpedientes("nacional", "evaluacion");
            map.put("ListaExpedientes", allListaExpedientes);
            map.put("df", df);
            return new ModelAndView("/Personal/Buscador_etapa/etapa_evaluacion/etapa_evaluativa_nac", map);
        }
    }

    /*------- REGISTRAR DESIGNACIÓN REGULARES---------*/
    @RequestMapping(value = "/registrarDesignacion", method = RequestMethod.POST)
    public ModelAndView registrarDesignacion(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        listaMatching.clear();
        Nna tempNna = new Nna();
        ArrayList<ExpedienteFamilia> allExp = new ArrayList();

        tempNna = ServicioNna.getNna(idNna);
        allExp = servicioEtapa.listaExpInfoFam();

        for (ExpedienteFamilia expedienteFamilia : allExp) {
            for (InfoFamilia infoFamilia : expedienteFamilia.getFamilia().getInfoFamilias()) {
                if (infoFamilia.getExpectativaEdadMin() != null
                        && infoFamilia.getExpectativaEdadMax() != null
                        && infoFamilia.getExpectativaGenero() != null
                        && infoFamilia.getNnaIncesto() != null
                        && infoFamilia.getNnaMental() != null
                        && infoFamilia.getNnaEpilepsia() != null
                        && infoFamilia.getNnaAbuso() != null
                        && infoFamilia.getNnaSifilis() != null
                        && infoFamilia.getNnaSeguiMedico() != null
                        && infoFamilia.getNnaHiperactivo() != null
                        && infoFamilia.getNnaOperacion() != null) {
                    if (tempNna.getEdadAnhos() >= infoFamilia.getExpectativaEdadMin()
                            && tempNna.getEdadAnhos() <= infoFamilia.getExpectativaEdadMax()
                            && (tempNna.getSexo().equals(infoFamilia.getExpectativaGenero()) || infoFamilia.getExpectativaGenero().equals("indistinto"))) {

                        if (tempNna.getIncesto() == infoFamilia.getNnaIncesto()
                                && tempNna.getMental() == infoFamilia.getNnaMental()
                                && tempNna.getEpilepsia() == infoFamilia.getNnaEpilepsia()
                                && tempNna.getAbuso() == infoFamilia.getNnaAbuso()
                                && tempNna.getSifilis() == infoFamilia.getNnaSifilis()
                                && tempNna.getSeguiMedico() == infoFamilia.getNnaSeguiMedico()
                                && tempNna.getOperacion() == infoFamilia.getNnaOperacion()
                                && tempNna.getHiperactivo() == infoFamilia.getNnaHiperactivo()) {

                            listaMatching.add(expedienteFamilia);
                        }
                    }
                }
            }
        }
        tempIdNnaRegular = idNna;
        map.put("listaMatching", listaMatching);
        map.put("df", df);
        return new ModelAndView("/Personal/nna/reg_desig", map);

    }

    @RequestMapping(value = "/agregarExp", method = RequestMethod.GET)
    public ModelAndView agregarExp(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("df", df);
        return new ModelAndView("/Personal/nna/agregar_exp", map);

    }

    @RequestMapping(value = "/buscarExpediente", method = RequestMethod.POST)
    public ModelAndView buscarExpediente_POST(ModelMap map, HttpSession session,
            @RequestParam("exp") String exp
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        session.setAttribute("exp", exp);

        return new ModelAndView("redirect:/buscarExpediente");

    }

    @RequestMapping(value = "/buscarExpediente", method = RequestMethod.GET)
    public ModelAndView buscarExpediente_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("exp") != null) {
            ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
            listaBusqueda = servicioEtapa.listaInfoFamilias((String) session.getAttribute("exp"));

            map.put("df", df);
            map.put("listaMatching", listaMatching);
            map.put("listaBusqueda", listaBusqueda);
            session.removeAttribute("exp");
            return new ModelAndView("/Personal/nna/agregar_exp", map);
        } else {
            map.put("listaNna", ServicioNna.ListaNna("regular"));
            return new ModelAndView("/Personal/nna/lista_nna", map);
        }
    }

    @RequestMapping(value = "/agregarExpediente", method = RequestMethod.POST)
    public ModelAndView agregarExpediente_POST(ModelMap map, HttpSession session, long[] idExpediente
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        return new ModelAndView("redirect:/agregarExpediente");
    }

    @RequestMapping(value = "/agregarExpediente", method = RequestMethod.GET)
    public ModelAndView agregarExpediente_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        long[] idExpediente = (long[]) session.getAttribute("idExpediente");
        if (idExpediente != null) {
            for (long l : idExpediente) {
                ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
                listaMatching.add(tempExp);
            }
        }
        map.put("listaMatching", listaMatching);
        map.put("df", df);
        session.removeAttribute("idExpediente");
        return new ModelAndView("/Personal/nna/reg_desig", map);

    }

    @RequestMapping(value = "/insertarDesignacion", method = RequestMethod.POST)
    public ModelAndView insertarDesignación_POST(ModelMap map, HttpSession session, long[] idExpediente,
            @RequestParam(value = "fecha", required = false) String fecha,
            @RequestParam(value = "agregar", required = false) String agregar,
            @RequestParam(value = "numDesig", required = false) String numDesig
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

//        ArrayList<Long> listaExpedientes = new ArrayList();
//        if(idExpediente != null){
//            for (long l : idExpediente) {
//                listaExpedientes.add(l);
//            }
//        }
        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("fecha", fecha);
        session.setAttribute("agregar", agregar);
        session.setAttribute("numDesig", numDesig);

        return new ModelAndView("redirect:/insertarDesignacion", map);
    }

    @RequestMapping(value = "/insertarDesignacion", method = RequestMethod.GET)
    public ModelAndView insertarDesignacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("agregar") != null) {
            map.put("df", df);
            return new ModelAndView("/Personal/nna/agregar_exp", map);
        }

        long[] idExpediente = (long[]) session.getAttribute("idExpediente");
        String fecha = (String) session.getAttribute("fecha");
        String numDesig = (String) session.getAttribute("numDesig");

        if (idExpediente != null && idExpediente.length < 4 && fecha != null && numDesig != null) {
            for (long l : idExpediente) {
                ExpedienteFamilia tempExp = servicioEtapa.getExpedienteFamilia(l);
                Nna tempNna = ServicioNna.getNna(tempIdNnaRegular);
                Designacion tempDesign = new Designacion();
                tempDesign.setExpedienteFamilia(tempExp);
                tempDesign.setNna(tempNna);
                tempDesign.setPersonal(usuario);
                Date tempfecha = tempDesign.getFechaPropuesta();
                if (fecha != null) {
                    if (fecha.contains("ene") || fecha.contains("feb") || fecha.contains("mar") || fecha.contains("abr")
                            || fecha.contains("may") || fecha.contains("jun") || fecha.contains("jul") || fecha.contains("ago")
                            || fecha.contains("set") || fecha.contains("oct") || fecha.contains("nov") || fecha.contains("dic")) {
                        tempDesign.setFechaPropuesta(tempfecha);
                    } else {
                        if (!fecha.equals("")) {
                            tempDesign.setFechaPropuesta(df.stringToDate(fecha));
                        } else {
                            tempDesign.setFechaPropuesta(null);
                        }
                    }
                } else {
                    tempDesign.setFechaPropuesta(null);
                }
                /*if (fecha != null && !fecha.equals("")) {
                 tempDesign.setFechaPropuesta(df.stringToDate(fecha));
                 }
                 if (fecha == null || fecha.equals("")) {
                 tempDesign.setFechaPropuesta(null);
                 }*/
                tempDesign.setNDesignacion(numDesig);
                if (idExpediente.length == 1) {
                    tempDesign.setTipoPropuesta("directa");
                }
                if (idExpediente.length == 2) {
                    tempDesign.setTipoPropuesta("dupla");
                }
                if (idExpediente.length == 3) {
                    tempDesign.setTipoPropuesta("terna");
                }
                tempDesign.setAceptacionConsejo(Short.parseShort("1"));
                servicioEtapa.crearDesignacion(tempDesign);
                tempExp.setEstado("designado");
                servicioEtapa.updateExpedienteFamilia(tempExp);
                ExpedienteNna tempExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                tempExpNna.setEstado("desig");
                Date ahora = new Date();
                java.sql.Date sql = new java.sql.Date(ahora.getTime());
                tempExpNna.setFechaEstado(sql);
                ServicioNna.updateExpNna(tempExpNna);

                String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                        + " con ID: " + usuario.getIdpersonal() + ". Insertó una nueva designación en el sistema.";

                String Tipo_registro = "Personal";

                try {
                    String Numero_registro = String.valueOf(usuario.getIdpersonal());

                    ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                } catch (Exception ex) {
                }

            }
            session.removeAttribute("idExpediente");
            session.removeAttribute("fecha");
            session.removeAttribute("numDesig");
            session.removeAttribute("agregar");
//            map.put("listaDesignaciones", servicioEtapa.getListaDesignaciones());
//            return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
            return new ModelAndView("redirect:/EtapaDesig");

        } else if (idExpediente == null && fecha == null && numDesig == null && session.getAttribute("agregar") == null) {
            return new ModelAndView("redirect:/nna");
        } else if (fecha == null || fecha.equals("") || numDesig == null) {
            map.put("mensaje", "Debe colocar una fecha y un número");
            map.put("listaMatching", listaMatching);
            map.put("df", df);
            return new ModelAndView("/Personal/nna/reg_desig", map);
        } else if (idExpediente != null && idExpediente.length >= 4) {
            map.put("mensaje", "Debe seleccionar como máximo Tres expedientes");
            map.put("listaMatching", listaMatching);
            map.put("df", df);
            return new ModelAndView("/Personal/nna/reg_desig", map);
        } else if (idExpediente == null) {
            map.put("mensaje", "Debe seleccionar al menos un expediente");
            map.put("listaMatching", listaMatching);
            map.put("df", df);
            return new ModelAndView("/Personal/nna/reg_desig", map);
        } else {

//        map.put("listaMatching", listaMatching);
//        map.put("df", df);
//        return new ModelAndView("/Personal/nna/reg_desig", map);
            return new ModelAndView("redirect:/nna");
        }
    }

    /*------- REGISTRAR DESIGNACIÓN PRIORITARIOS---------*/
    @RequestMapping(value = "/agregarEstudio", method = RequestMethod.POST)
    public ModelAndView agregarEstudio_POST(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        session.setAttribute("idNna", idNna);

        return new ModelAndView("redirect:/agregarEstudio", map);
    }

    @RequestMapping(value = "/agregarEstudio", method = RequestMethod.GET)
    public ModelAndView agregarEstudio_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("idNna") != null) {
            nnaEditarEstudioCaso = (long) session.getAttribute("idNna");
            nnaPrioritario = ServicioNna.getNna((long) session.getAttribute("idNna"));
            listaEstudioCaso.clear();
            map.addAttribute("idNna", session.getAttribute("idNna"));
            return new ModelAndView("/Personal/nna/reg_estudio", map);
        } else {
            map.put("listaNna", ServicioNna.ListaNna("regular"));
            return new ModelAndView("/Personal/nna/lista_nna", map);
        }
    }

//    @RequestMapping(value = "/insertarEstudio", method = RequestMethod.POST)
//    public ModelAndView insertarEstudio(ModelMap map, HttpSession session,
//            @RequestParam(value = "orden", required = false) String orden,
//            @RequestParam(value = "agregar", required = false) String agregar,
//            @RequestParam(value = "eliminar", required = false) String eliminar,
//            @RequestParam(value = "registrar", required = false) String registrar,
//            int[] delete,
//            Long[] prioridad,
//            @RequestParam(value = "idNna", required = false) String idNna,
//            @RequestParam(value = "numero", required = false) String numero
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        if (agregar != null) {
//            map.put("df", df);
//            map.addAttribute("idNna", idNna);
//            map.addAttribute("numero", numero);
//            return new ModelAndView("/Personal/nna/agregar_exp_prioritario", map);
//        }
//        if (eliminar != null && delete != null) {
//            for (int i : delete) {
//                listaEstudioCaso.remove(i);
//            }
//
//            map.put("df", df);
//            map.put("listaEstudioCaso", listaEstudioCaso);
//            return new ModelAndView("/Personal/nna/reg_estudio", map);
//        }
//        if (registrar != null) {
//            for (int i = 0; i < listaEstudioCaso.size(); i++) {
//                EstudioCaso tempEst = new EstudioCaso();
//                tempEst.setNna(nnaPrioritario);
//                tempEst.setExpedienteFamilia(listaEstudioCaso.get(i));
//                ExpedienteFamilia expFam1 = listaEstudioCaso.get(i);
//                expFam1.setEstado("estudio");
//                servicioEtapa.updateExpedienteFamilia(expFam1);
//                tempEst.setOrden(orden);
//
//                tempEst.setPrioridad(prioridad[i]);
//
//                servicioEtapa.crearEstudioCaso(tempEst);
//
//                String mensaje_log = "Se registró nueva familia al estudio de caso del NNA con ID: " + String.valueOf(nnaPrioritario.getIdnna());
//                String Tipo_registro = "Login";
//
//                try {
//                    String Numero_registro = tempEst.getOrden();
//
//                    ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
//                } catch (Exception ex) {
//                }
//
//            }
//            map.addAttribute("idNna", idNna);
//            map.addAttribute("numero", numero);
//            map.put("listaEstudios", servicioEtapa.getListaEstudioCaso(nnaPrioritario.getIdnna()));
//            return new ModelAndView("/Personal/nna/lista_estudios", map);
//        }
//
//        return new ModelAndView("/Personal/nna/reg_estudio", map);
//    }
//    @RequestMapping(value = "/buscarExpedientePrioritario", method = RequestMethod.POST)
//    public ModelAndView buscarExpedientePrioritario(ModelMap map, HttpSession session,
//            @RequestParam("exp") String exp
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
//        listaBusqueda = servicioEtapa.listaInfoFamilias(exp);
//
//        map.put("df", df);
//        map.put("listaEstudioCaso", listaEstudioCaso);
//        map.put("listaBusqueda", listaBusqueda);
//        return new ModelAndView("/Personal/nna/agregar_exp_prioritario", map);
//
//    }
//
//    @RequestMapping(value = "/agregarExpedientePrioritario", method = RequestMethod.POST)
//    public ModelAndView agregarExpedientePrioritario(ModelMap map, HttpSession session, long[] idExpediente
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//
//        if (idExpediente != null) {
//            for (long l : idExpediente) {
//                ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
//                listaEstudioCaso.add(tempExp);
//            }
//        }
//        map.put("listaEstudioCaso", listaEstudioCaso);
//        map.put("df", df);
//        return new ModelAndView("/Personal/nna/reg_estudio", map);
//
//    }
//    @RequestMapping(value = "/verEstudios", method = RequestMethod.POST)
//    public ModelAndView verEstudios(ModelMap map, HttpSession session,
//            @RequestParam("idNna") long idNna
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        nnaEditarEstudioCaso = idNna;
//        map.put("listaEstudios", servicioEtapa.getListaEstudioCaso(idNna));
//        return new ModelAndView("/Personal/nna/lista_estudios", map);
//
//    }
//    @RequestMapping(value = "/EditarEstudio", method = RequestMethod.POST)
//    public ModelAndView EditarEstudio(ModelMap map, HttpSession session,
//            @RequestParam("orden") String orden
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        map.put("df", df);
//        map.put("nna", nnaEditarEstudioCaso);
//        map.put("listaEstudios", servicioEtapa.getListaEstudioCasoOrden(orden));
//        return new ModelAndView("/Personal/nna/edit_estudio", map);
//
//    }
//    @RequestMapping(value = "/ActualizarEstudio", method = RequestMethod.POST)
//    public ModelAndView ActualizarEstudio(ModelMap map, HttpSession session,
//            @RequestParam("orden") String orden,
//            @RequestParam("idEstudio") long idEstudio,
//            @RequestParam("resultado") String resultado,
//            @RequestParam("fechaEst") String fechaEst
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        if (resultado.equals("acep")) {
//            ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
//            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
//            if (fechaEst != null && !fechaEst.equals("")) {
//                tempEst.setFechaEstudio(df.stringToDate(fechaEst));
//            } else if (fechaEst == null && fechaEst.equals("")) {
//                tempEst.setFechaEstudio(null);
//            }
//            tempEst.setResultado(resultado);
//            servicioEtapa.updateEstudioCaso(tempEst);
//            allEstudioCaso = servicioEtapa.getListaEstudioCasoOrden(orden);
//            for (EstudioCaso estudioCaso : allEstudioCaso) {
//                if (estudioCaso.getResultado() == null) {
//                    estudioCaso.setResultado("noobs");
//                    servicioEtapa.updateEstudioCaso(estudioCaso);
//                    ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
//                    tempExp = estudioCaso.getExpedienteFamilia();
//                    tempExp.setEstado("espera");
//                    servicioEtapa.updateExpedienteFamilia(tempExp);
//                }
//            }
//
//            String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
//            String Tipo_registro = "Estu_Caso";
//
//            //try{
//            String Numero_registro = tempEst.getOrden();
//
//            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
//            map.put("df", df);
//            map.put("nna", nnaEditarEstudioCaso);
//            map.put("listaEstudios", servicioEtapa.getListaEstudioCasoOrden(orden));
//            return new ModelAndView("/Personal/nna/edit_estudio", map);
//        } else if (resultado.equals("noacep")) {
//            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
//            if (fechaEst != null && !fechaEst.equals("")) {
//                tempEst.setFechaEstudio(df.stringToDate(fechaEst));
//            } else if (fechaEst == null && fechaEst.equals("")) {
//                tempEst.setFechaEstudio(null);
//            }
//
//            tempEst.setResultado(resultado);
//            servicioEtapa.updateEstudioCaso(tempEst);
//            ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
//            tempExp = tempEst.getExpedienteFamilia();
//            tempExp.setEstado("espera");
//            servicioEtapa.updateExpedienteFamilia(tempExp);
//
//            String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
//            String Tipo_registro = "Estu_Caso";
//
//            //try{
//            String Numero_registro = tempEst.getOrden();
//
//            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
//            map.put("df", df);
//            map.put("nna", nnaEditarEstudioCaso);
//            map.put("listaEstudios", servicioEtapa.getListaEstudioCasoOrden(orden));
//            return new ModelAndView("/Personal/nna/edit_estudio", map);
//        } else if (resultado.equals("noobs")) {
//            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
//            tempEst.setResultado(resultado);
//            servicioEtapa.updateEstudioCaso(tempEst);
//            ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
//            tempExp = tempEst.getExpedienteFamilia();
//            tempExp.setEstado("espera");
//            servicioEtapa.updateExpedienteFamilia(tempExp);
//
//            String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
//            String Tipo_registro = "Estu_Caso";
//
//            //try{
//            String Numero_registro = tempEst.getOrden();
//
//            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
//            map.put("df", df);
//            map.put("nna", nnaEditarEstudioCaso);
//            map.put("listaEstudios", servicioEtapa.getListaEstudioCasoOrden(orden));
//            return new ModelAndView("/Personal/nna/edit_estudio", map);
//        } else {
//
//            EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
//            if (fechaEst != null && !fechaEst.equals("")) {
//                tempEst.setFechaEstudio(df.stringToDate(fechaEst));
//            } else if (fechaEst == null && fechaEst.equals("")) {
//                tempEst.setFechaEstudio(null);
//            }
//            tempEst.setResultado(resultado);
//            servicioEtapa.updateEstudioCaso(tempEst);
//
//            String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
//            String Tipo_registro = "Estu_Caso";
//
//            //try{
//            String Numero_registro = tempEst.getOrden();
//
//            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
//
//            map.put("df", df);
//            map.put("nna", nnaEditarEstudioCaso);
//            map.put("listaEstudios", servicioEtapa.getListaEstudioCasoOrden(orden));
//            return new ModelAndView("/Personal/nna/edit_estudio", map);
//        }
//    }
//    @RequestMapping(value = "/generarDesignacionPrioritario", method = RequestMethod.POST)
//    public ModelAndView generarDesignacionPrioritario(ModelMap map, HttpSession session,
//            @RequestParam("orden") String orden,
//            @RequestParam("fechaSolicitud") String fechaSolicitud,
//            @RequestParam("numDesig") String numDesig,
//            @RequestParam("idEstudio") long idEstudio,
//            @RequestParam("fechaPropuesta") String fechaPropuesta
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//
//        if (numDesig == null || fechaSolicitud == null || fechaPropuesta == null) {
//
//            map.put("mensaje", "Debe llenar los datos correctamente");
//            map.put("df", df);
//            map.put("listaEstudios", servicioEtapa.getListaEstudioCasoOrden(orden));
//
//        }
//
//        EstudioCaso tempEst = servicioEtapa.getEstudioCaso(idEstudio);
//        Date tempfecha = tempEst.getFechaSolAdop();
//        if (fechaSolicitud != null) {
//            if (fechaSolicitud.contains("ene") || fechaSolicitud.contains("feb") || fechaSolicitud.contains("mar") || fechaSolicitud.contains("abr")
//                    || fechaSolicitud.contains("may") || fechaSolicitud.contains("jun") || fechaSolicitud.contains("jul") || fechaSolicitud.contains("ago")
//                    || fechaSolicitud.contains("set") || fechaSolicitud.contains("oct") || fechaSolicitud.contains("nov") || fechaSolicitud.contains("dic")) {
//                tempEst.setFechaSolAdop(tempfecha);
//            } else {
//                if (!fechaSolicitud.equals("")) {
//                    tempEst.setFechaSolAdop(df.stringToDate(fechaSolicitud));
//                } else {
//                    tempEst.setFechaSolAdop(null);
//                }
//            }
//        } else {
//            tempEst.setFechaSolAdop(null);
//        }
//        /*if (fechaSolicitud != null && !fechaSolicitud.equals("")) {
//         tempEst.setFechaSolAdop(df.stringToDate(fechaSolicitud));
//         }
//         if (fechaSolicitud == null || fechaSolicitud.equals("")) {
//         tempEst.setFechaSolAdop(null);
//         }*/
//
//        servicioEtapa.updateEstudioCaso(tempEst);
//
//        ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
//        Nna tempNna = ServicioNna.getNna(nnaEditarEstudioCaso);
//        Designacion tempDesign = new Designacion();
//        tempDesign.setExpedienteFamilia(tempExp);
//        tempDesign.setNna(tempNna);
//        tempDesign.setPersonal(usuario);
//        tempfecha = tempDesign.getFechaPropuesta();
//        if (fechaPropuesta != null) {
//            if (fechaPropuesta.contains("ene") || fechaPropuesta.contains("feb") || fechaPropuesta.contains("mar") || fechaPropuesta.contains("abr")
//                    || fechaPropuesta.contains("may") || fechaPropuesta.contains("jun") || fechaPropuesta.contains("jul") || fechaPropuesta.contains("ago")
//                    || fechaPropuesta.contains("set") || fechaPropuesta.contains("oct") || fechaPropuesta.contains("nov") || fechaPropuesta.contains("dic")) {
//                tempDesign.setFechaPropuesta(tempfecha);
//            } else {
//                if (!fechaPropuesta.equals("")) {
//                    tempDesign.setFechaPropuesta(df.stringToDate(fechaPropuesta));
//                } else {
//                    tempDesign.setFechaPropuesta(null);
//                }
//            }
//        } else {
//            tempDesign.setFechaPropuesta(null);
//        }
//        /*if (fechaPropuesta != null && !fechaPropuesta.equals("")) {
//         tempDesign.setFechaPropuesta(df.stringToDate(fechaPropuesta));
//         }
//         if (fechaPropuesta == null || fechaPropuesta.equals("")) {
//         tempDesign.setFechaPropuesta(null);
//         }*/
//        tempDesign.setNDesignacion(numDesig);
//        tempDesign.setTipoPropuesta("directa");
//        tempDesign.setAceptacionConsejo(Short.parseShort("1"));
//        servicioEtapa.crearDesignacion(tempDesign);
//        tempExp.setEstado("designado");
//        servicioEtapa.updateExpedienteFamilia(tempExp);
//        ExpedienteNna tempExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
//        tempExpNna.setEstado("desig");
//        Date ahora = new Date();
//        java.sql.Date sql = new java.sql.Date(ahora.getTime());
//        tempExpNna.setFechaEstado(sql);
//        ServicioNna.updateExpNna(tempExpNna);
//
//        map.put("listaDesignaciones", servicioEtapa.getListaDesignaciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
//
//    }

    /*------- ETAPA DESIGNACION---------*/
    @RequestMapping(value = "/EtapaDesig", method = RequestMethod.GET)
    public ModelAndView EtapaDesig(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        volver = "/EtapaDesig";
        map.addAttribute("volver", volver);
        map.put("listaDesignaciones", servicioEtapa.getListaDesignaciones());
        //map.put("listaDesignaciones", ServicioReporte.getListaPropuestaDesignacion());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);

    }

    @RequestMapping(value = "/designacionConsejo", method = RequestMethod.POST)
    public ModelAndView designacionConsejo_POST(ModelMap map, HttpSession session,
            @RequestParam("numDesig") String numDesig,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("numDesig", numDesig);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/designacionConsejo", map);

    }

    @RequestMapping(value = "/designacionConsejo", method = RequestMethod.GET)
    public ModelAndView designacionConsejo_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("numDesig") != null) {

            String numDesig = (String) session.getAttribute("numDesig");
            ArrayList<Nna> allNna = new ArrayList();
            ArrayList<ExpedienteFamilia> allExpFam = new ArrayList();
            long idNna = 0;
            long idExpFam = 0;
            allDesig.clear();
            allDesig = servicioEtapa.getListaDesignaciones(numDesig);
            for (Designacion desig : allDesig) {
                if (desig.getNna().getIdnna() != idNna) {
                    allNna.add(desig.getNna());
                    idNna = desig.getNna().getIdnna();
                }
                if (desig.getExpedienteFamilia().getIdexpedienteFamilia() != idExpFam) {
                    allExpFam.add(desig.getExpedienteFamilia());
                    idExpFam = desig.getExpedienteFamilia().getIdexpedienteFamilia();
                }
            }

            //Nna newNna = allDesig.get(0).getNna();
            String propuesta = allDesig.get(0).getTipoPropuesta();

            map.put("listaNna", allNna);
            map.put("listaFamilias", allExpFam);
            map.put("propuesta", propuesta);
            map.put("numDesig", numDesig);
            map.addAttribute("volver", volver);

            session.removeAttribute("numDesig");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/desig_consejo", map);
        } else {
            return new ModelAndView("redirect:/EtapaDesig");
        }

    }

    @RequestMapping(value = "/aceptacionConsejo", method = RequestMethod.POST)
    public ModelAndView aceptacionConsejo_POST(ModelMap map, HttpSession session,
            @RequestParam("numDesig") String numDesig,
            @RequestParam("fechaConsejo") String fechaConsejo,
            @RequestParam("prioridad") long[] prioridad,
            @RequestParam("idExp") long[] idExp,
            @RequestParam("obs") String obs,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("numDesig", numDesig);
        session.setAttribute("fechaConsejo", fechaConsejo);
        session.setAttribute("prioridad", prioridad);
        session.setAttribute("idExp", idExp);
        session.setAttribute("obs", obs);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/aceptacionConsejo", map);
    }

    @RequestMapping(value = "/aceptacionConsejo", method = RequestMethod.GET)
    public ModelAndView aceptacionConsejo_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        String numDesig = (String) session.getAttribute("numDesig");
        String fechaConsejo = (String) session.getAttribute("fechaConsejo");
        long[] prioridad = (long[]) session.getAttribute("prioridad");
        long[] idExp = (long[]) session.getAttribute("idExp");
        String obs = (String) session.getAttribute("obs");
        String volver = (String) session.getAttribute("volver");

        if (numDesig != null) {
            if (fechaConsejo == null || fechaConsejo.equals("")) {
                ArrayList<Nna> allNna = new ArrayList();
                ArrayList<ExpedienteFamilia> allExpFam = new ArrayList();
                long idNna = 0;
                long idExpFam = 0;
                allDesig.clear();
                allDesig = servicioEtapa.getListaDesignaciones(numDesig);
                for (Designacion desig : allDesig) {
                    if (desig.getNna().getIdnna() != idNna) {
                        allNna.add(desig.getNna());
                        idNna = desig.getNna().getIdnna();
                    }
                    if (desig.getExpedienteFamilia().getIdexpedienteFamilia() != idExpFam) {
                        allExpFam.add(desig.getExpedienteFamilia());
                        idExpFam = desig.getExpedienteFamilia().getIdexpedienteFamilia();
                    }
                }

                //Nna newNna = allDesig.get(0).getNna();
                String propuesta = allDesig.get(0).getTipoPropuesta();
                String mensaje = "Debe ingresar la fecha de decisión del consejo";
                map.put("listaNna", allNna);
                map.put("listaFamilias", allExpFam);
                map.put("mensaje", mensaje);
                map.put("propuesta", propuesta);
                map.put("numDesig", numDesig);
                map.addAttribute("volver", volver);

                session.removeAttribute("numDesig");
                session.removeAttribute("fechaConsejo");
                session.removeAttribute("prioridad");
                session.removeAttribute("idExp");
                session.removeAttribute("obs");
                session.removeAttribute("volver");

                return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/desig_consejo", map);
            }
        }
        if (idExp != null) {
            for (int i = 0; i < idExp.length; i++) {
                ArrayList<Designacion> tempDesig = new ArrayList();
                tempDesig = servicioEtapa.getListaDesignacionesDeFamilia(idExp[i]);
                if (!tempDesig.isEmpty()) {
                    for (Designacion designacion : tempDesig) {
                        designacion.setAceptacionConsejo(Short.parseShort("3"));
                        servicioEtapa.updateDesignacion(designacion);
                    }
                }
            }
        }
        if (prioridad != null) {
            ArrayList<Nna> allNna = new ArrayList();
            ArrayList<ExpedienteFamilia> allExpFam = new ArrayList();
            long idNna = 0;
            long idExpFam = 0;
            for (Designacion desig : allDesig) {
                if (desig.getNna().getIdnna() != idNna) {
                    ExpedienteNna tempExpNna = ServicioNna.getExpNna(desig.getNna().getIdnna());
                    tempExpNna.setEstado("desig");
                    Date now = new Date();
                    java.sql.Date sql = new java.sql.Date(now.getTime());
                    tempExpNna.setFechaEstado(sql);
                    ServicioNna.updateExpNna(tempExpNna);
                    idNna = desig.getNna().getIdnna();
                }
                if (desig.getExpedienteFamilia().getIdexpedienteFamilia() != idExpFam) {
                    ExpedienteFamilia tempExpFam = desig.getExpedienteFamilia();
                    tempExpFam.setEstado("adopcion");
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    idExpFam = desig.getExpedienteFamilia().getIdexpedienteFamilia();
                }
            }

            for (int i = 0; i < allDesig.size(); i++) {
                if (allDesig.get(i).getTipoPropuesta().equals("directa")) {
                    Date tempfecha = df.stringToDate(fechaConsejo);
                    allDesig.get(i).setFechaConsejo(tempfecha);
                    allDesig.get(i).setAceptacionConsejo(Short.parseShort("0"));
                    allDesig.get(i).setObs(obs);
                    long priority = 1;
                    allDesig.get(i).setPrioridad(priority);
                    servicioEtapa.updateDesignacion(allDesig.get(i));
                } else {
                    Date tempfecha = df.stringToDate(fechaConsejo);
                    allDesig.get(i).setFechaConsejo(tempfecha);
                    allDesig.get(i).setAceptacionConsejo(Short.parseShort("0"));
                    allDesig.get(i).setObs(obs);
                    allDesig.get(i).setPrioridad(prioridad[i]);
                    servicioEtapa.updateDesignacion(allDesig.get(i));
                }
            }

//            ExpedienteNna tempExpNna = ServicioNna.getExpNna(idNna);
//            tempExpNna.setEstado("adop");
//            Date now = new Date();
//            java.sql.Date sql = new java.sql.Date(now.getTime());
//            tempExpNna.setFechaEstado(sql);
//            
//            ServicioNna.updateExpNna(tempExpNna);
//            for (int i = 0; i < prioridad.length; i++) {
//                Designacion tempDesg = allDesig.get(i);
//                Date tempfecha = tempDesg.getFechaConsejo();
//                if (fechaConsejo != null) {
//                    if (fechaConsejo.contains("ene") || fechaConsejo.contains("feb") || fechaConsejo.contains("mar") || fechaConsejo.contains("abr")
//                            || fechaConsejo.contains("may") || fechaConsejo.contains("jun") || fechaConsejo.contains("jul") || fechaConsejo.contains("ago")
//                            || fechaConsejo.contains("set") || fechaConsejo.contains("oct") || fechaConsejo.contains("nov") || fechaConsejo.contains("dic")) {
//                        tempDesg.setFechaConsejo(tempfecha);
//                    } else {
//                        if (!fechaConsejo.equals("")) {
//                            tempDesg.setFechaConsejo(df.stringToDate(fechaConsejo));
//                        } else {
//                            tempDesg.setFechaConsejo(null);
//                        }
//                    }
//                } else {
//                    tempDesg.setFechaConsejo(null);
//                }
//                /*if (fechaConsejo != null && !fechaConsejo.equals("")) {
//                 tempDesg.setFechaConsejo(df.stringToDate(fechaConsejo));
//                 }
//                 if (fechaConsejo == null || fechaConsejo.equals("")) {
//                 tempDesg.setFechaConsejo(null);
//                 }*/
//
//                tempDesg.setAceptacionConsejo(Short.parseShort("0"));
//                tempDesg.setObs(obs);
//                tempDesg.setPrioridad(prioridad[i]);
//                ExpedienteFamilia tempExpFam = tempDesg.getExpedienteFamilia();
//                tempExpFam.setEstado("adopcion");
//                servicioEtapa.updateExpedienteFamilia(tempExpFam);
//                servicioEtapa.updateDesignacion(tempDesg);
//            }
            //map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
            map.addAttribute("volver", volver);

            session.removeAttribute("numDesig");
            session.removeAttribute("fechaConsejo");
            session.removeAttribute("prioridad");
            session.removeAttribute("idExp");
            session.removeAttribute("obs");
            session.removeAttribute("volver");

            map.put("listaDesignaciones", servicioEtapa.getListaDesignaciones());
            return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
        }

        session.removeAttribute("numDesig");
        session.removeAttribute("fechaConsejo");
        session.removeAttribute("prioridad");
        session.removeAttribute("idExp");
        session.removeAttribute("obs");
        session.removeAttribute("volver");

        map.put("listaDesignaciones", servicioEtapa.getListaDesignaciones());
        return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);

    }

    /*  ETAPA  DE ADOPCION */
    @RequestMapping(value = "/EtapaAdopcion", method = RequestMethod.GET)
    public ModelAndView EtapaAdopcion(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        volver = "/EtapaAdopcion";
        map.addAttribute("volver", volver);
        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());

        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);

    }

    //CAMBIOS AL 12/04/2014
    @RequestMapping(value = "/evalAceptacion", method = RequestMethod.POST)
    public ModelAndView evalAceptacion_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("respuesta") String respuesta
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("respuesta", respuesta);

        return new ModelAndView("redirect:/evalAceptacion", map);

    }

    @RequestMapping(value = "/evalAceptacion", method = RequestMethod.GET)
    public ModelAndView evalAceptacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null && session.getAttribute("respuesta") != null) {
            long idExpediente = (long) session.getAttribute("idExpediente");
            String respuesta = (String) session.getAttribute("respuesta");

            ExpedienteFamilia EF = new ExpedienteFamilia();
            Personal tempP = new Personal();
            EF = servicioEtapa.getExpedienteFamilia(idExpediente);
            tempP = ServicioPersonal.getPersonal(usuario.getIdpersonal());
            Evaluacion tempEval = new Evaluacion();
            tempEval.setExpedienteFamilia(EF);
            tempEval.setPersonal(tempP);
            tempEval.setTipo("aceptacion");
            tempEval.setResultado(respuesta);
            Date tempfecha = new Date();
            java.sql.Date sql = new java.sql.Date(tempfecha.getTime());
            tempEval.setFechaResultado(sql);

            /*if (fechaEval != null && !fechaEval.equals("")) {
             tempEval.setFechaResultado(df.stringToDate(fechaEval));
             }
             if (fechaEval == null || fechaEval.equals("")) {
             tempEval.setFechaResultado(null);
             }*/
            tempEval.setNumEval(null);
            tempEval.setObservacion(null);
            tempEval.setPersInt(null);
            long numPers = 0;
            tempEval.setNumPersInt(numPers);

            servicioEtapa.crearEvaluacionAdopcion(tempEval);

            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Creó una Evaluación de adopción para el expediente"
                    + " con ID: " + session.getAttribute("idExpediente");

            String Tipo_registro = "Familia";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            session.removeAttribute("idExpediente");
            session.removeAttribute("respuesta");
//        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
            return new ModelAndView("redirect:/EtapaAdopcion");
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion");
        }

    }

    @RequestMapping(value = "/resolEvalAceptacion", method = RequestMethod.POST)
    public ModelAndView resolEvalAceptacion_POST(ModelMap map, HttpSession session,
            @RequestParam("familia") String familia,
            @RequestParam("idNna") long idNna,
            @RequestParam("idAceptacion") String idAceptacion,
            @RequestParam("numDesig") String numDesig
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("familia", familia);
        session.setAttribute("idNna", idNna);
        session.setAttribute("idAceptacion", idAceptacion);
        session.setAttribute("numDesig", numDesig);

        return new ModelAndView("redirect:/resolEvalAceptacion", map);

    }

    @RequestMapping(value = "/resolEvalAceptacion", method = RequestMethod.GET)
    public ModelAndView resolEvalAceptacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("familia") != null && session.getAttribute("idNna") != null
                && session.getAttribute("idAceptacion") != null
                && session.getAttribute("numDesig") != null) {
            String familia = (String) session.getAttribute("familia");
            long idNna = (long) session.getAttribute("idNna");
            String idAceptacion = (String) session.getAttribute("idAceptacion");
            String numDesig = (String) session.getAttribute("numDesig");

            Evaluacion tempEval = new Evaluacion();
            if (!idAceptacion.equals("")) {
                long temp = Long.parseLong(idAceptacion);
                if (servicioEtapa.getResolucion2(temp) != null) {
                    Resolucion tempResol = servicioEtapa.getResolucion2(temp);
                    map.put("resolucion", tempResol);
                }
            }
            map.put("numDesig", numDesig);
            map.put("idAceptacion", idAceptacion);
            map.put("idNna", idNna);
            map.put("familia", familia);
            map.put("df", df);

            session.removeAttribute("familia");
            session.removeAttribute("idAceptacion");
            session.removeAttribute("numDesig");
            session.removeAttribute("idNna");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/reg_resol_acep", map);
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion");
        }
    }

    @RequestMapping(value = "/crearResolAceptacion", method = RequestMethod.POST)
    public ModelAndView crearResolAceptacion_POST(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna,
            @RequestParam("idAceptacion") long idAceptacion,
            @RequestParam("numDesig") String numDesig,
            @RequestParam("numResol") String numResol,
            @RequestParam("tipo") String tipo,
            @RequestParam("fechaResol") String fechaResol
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idNna", idNna);
        session.setAttribute("idAceptacion", idAceptacion);
        session.setAttribute("numDesig", numDesig);
        session.setAttribute("numResol", numResol);
        session.setAttribute("tipo", tipo);
        session.setAttribute("fechaResol", fechaResol);

        return new ModelAndView("redirect:/crearResolAceptacion", map);

    }

    @RequestMapping(value = "/crearResolAceptacion", method = RequestMethod.GET)
    public ModelAndView crearResolAceptacion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idAceptacion") != null && session.getAttribute("idNna") != null) {

            long idAceptacion = (long) session.getAttribute("idAceptacion");
            long idNna = (long) session.getAttribute("idNna");
            String numDesig = (String) session.getAttribute("numDesig");
            String numResol = (String) session.getAttribute("numResol");
            String fechaResol = (String) session.getAttribute("fechaResol");
            String tipo = (String) session.getAttribute("tipo");

            Evaluacion tempEval = new Evaluacion();
            tempEval = servicioEtapa.getEvaluacion2(idAceptacion);
            Designacion tempDesig = new Designacion();
            tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), idNna);

            ArrayList<Nna> allNna = new ArrayList();
            ArrayList<Designacion> allDesignaciones = new ArrayList();
            long idN = 0;
            allDesignaciones = servicioEtapa.getListaDesignacionesEstadoAdopcion(numDesig);
            for (Designacion desig : allDesignaciones) {
                if (desig.getNna().getIdnna() != idN) {
                    allNna.add(desig.getNna());
                    idN = desig.getNna().getIdnna();
                }
            }

            if (tipo.equals("sinefecto")) {
                if (allNna.size() > 1) {
                    servicioEtapa.deleteEvaluacion(tempEval);
                    ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                    tempExpFam.setEstado("reevaluacion");
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    for (Nna tempNna : allNna) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                        ExpNna.setEstado("adoptable");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                        tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), tempNna.getIdnna());
                        tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                        servicioEtapa.updateDesignacion(tempDesig);

                    }

                } else {
                    servicioEtapa.deleteEvaluacion(tempEval);
                    ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                    tempExpFam.setEstado("reevaluacion");
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                    servicioEtapa.updateDesignacion(tempDesig);
                    if (tempDesig.getTipoPropuesta().equals("directa")) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("adoptable");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                    if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("adoptable");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                    if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("adoptable");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                }
            } else {
                Resolucion tempResol = new Resolucion();
                tempResol.setEvaluacion(tempEval);
                tempResol.setNumero(numResol);
                tempResol.setTipo(tipo);
                Date tempfecha = tempResol.getFechaResol();
                if (fechaResol != null) {
                    if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
                            || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
                            || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
                        tempResol.setFechaResol(tempfecha);
                    } else {
                        if (!fechaResol.equals("")) {
                            tempResol.setFechaResol(df.stringToDate(fechaResol));
                        } else {
                            tempResol.setFechaResol(null);
                        }
                    }
                } else {
                    tempResol.setFechaResol(null);
                }
                /*if (fechaResol != null && !fechaResol.equals("")) {
                 tempResol.setFechaResol(df.stringToDate(fechaResol));
                 }
                 if (fechaResol == null || fechaResol.equals("")) {
                 tempResol.setFechaResol(null);
                 }*/
                servicioEtapa.crearResolEvaluacion(tempResol);

                String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                        + " con ID: " + usuario.getIdpersonal() + ". Creó la Resolución de evaluación con ID"
                        + ": " + session.getAttribute("idEmpatia");

                String Tipo_registro = "Familia";

                try {
                    String Numero_registro = String.valueOf(usuario.getIdpersonal());

                    ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                } catch (Exception ex) {
                }

            }

            session.removeAttribute("idAceptacion");
            session.removeAttribute("idNna");
            session.removeAttribute("numDesig");
            session.removeAttribute("numResol");
            session.removeAttribute("fechaResol");
            session.removeAttribute("tipo");

//        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
            return new ModelAndView("redirect:/EtapaAdopcion");
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion");
        }

    }

    // FINAL DE CAMBIOS
    @RequestMapping(value = "/evalEmpatia", method = RequestMethod.POST)
    public ModelAndView evalEmpatia_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("familia") String familia,
            @RequestParam(value = "idEmpatia", required = false) String idEmpatia
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("familia", familia);
        session.setAttribute("idEmpatia", idEmpatia);

        return new ModelAndView("redirect:/evalEmpatia", map);

    }

    @RequestMapping(value = "/evalEmpatia", method = RequestMethod.GET)
    public ModelAndView evalEmpatia_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null) {
            long idExpediente = (long) session.getAttribute("idExpediente");
            String familia = (String) session.getAttribute("familia");
            String idEmpatia = (String) session.getAttribute("idEmpatia");

            Evaluacion tempEval = new Evaluacion();
            if (!idEmpatia.equals("")) {
                long temp = Long.parseLong(idEmpatia);
                tempEval = servicioEtapa.getEvaluacion(temp);
                map.put("empatia", tempEval);
            }

            map.put("idExpediente", idExpediente);
            map.put("familia", familia);
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.put("df", df);

            session.removeAttribute("idExpediente");
            session.removeAttribute("familia");
            session.removeAttribute("idEmpatia");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/eval_emp", map);
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion", map);
        }
    }

    @RequestMapping(value = "/crearEvalEmpatia", method = RequestMethod.POST)
    public ModelAndView crearEvalEmpatia(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("personal") long personal,
            @RequestParam("resultado") String resultado,
            @RequestParam("fechaEval") String fechaEval,
            @RequestParam("numEval") String numEval,
            @RequestParam("obs") String obs,
            @RequestParam("persInt") String persInt,
            @RequestParam("numPers") long numPers
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ExpedienteFamilia EF = new ExpedienteFamilia();
        Personal tempP = new Personal();
        EF = servicioEtapa.getExpedienteFamilia(idExpediente);
        tempP = ServicioPersonal.getPersonal(personal);
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(EF);
        tempEval.setPersonal(tempP);
        tempEval.setTipo("empatia");
        tempEval.setResultado(resultado);
        Date tempfecha = tempEval.getFechaResultado();
        if (fechaEval != null) {
            if (fechaEval.contains("ene") || fechaEval.contains("feb") || fechaEval.contains("mar") || fechaEval.contains("abr")
                    || fechaEval.contains("may") || fechaEval.contains("jun") || fechaEval.contains("jul") || fechaEval.contains("ago")
                    || fechaEval.contains("set") || fechaEval.contains("oct") || fechaEval.contains("nov") || fechaEval.contains("dic")) {
                tempEval.setFechaResultado(tempfecha);
            } else {
                if (!fechaEval.equals("")) {
                    tempEval.setFechaResultado(df.stringToDate(fechaEval));
                } else {
                    tempEval.setFechaResultado(null);
                }
            }
        } else {
            tempEval.setFechaResultado(null);
        }
        /*if (fechaEval != null && !fechaEval.equals("")) {
         tempEval.setFechaResultado(df.stringToDate(fechaEval));
         }
         if (fechaEval == null || fechaEval.equals("")) {
         tempEval.setFechaResultado(null);
         }*/
        tempEval.setNumEval(numEval);
        tempEval.setObservacion(obs);
        tempEval.setPersInt(persInt);
        if (numPers != 0) {
            tempEval.setNumPersInt(numPers);
        } else {
            long temp = 0;
            tempEval.setNumPersInt(temp);
        }
        servicioEtapa.crearEvaluacionAdopcion(tempEval);

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Creó una Evaluación de adopción para el expediente"
                + " con ID: " + session.getAttribute("idExpediente");

        String Tipo_registro = "Familia";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

//        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        return new ModelAndView("redirect:/EtapaAdopcion");

    }

    @RequestMapping(value = "/resolEvalEmpatia", method = RequestMethod.POST)
    public ModelAndView resolEvalEmpatia_POST(ModelMap map, HttpSession session,
            @RequestParam("familia") String familia,
            @RequestParam("idNna") long idNna,
            @RequestParam("idEmpatia") String idEmpatia,
            @RequestParam("numDesig") String numDesig
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("familia", familia);
        session.setAttribute("idNna", idNna);
        session.setAttribute("idEmpatia", idEmpatia);
        session.setAttribute("numDesig", numDesig);

        return new ModelAndView("redirect:/resolEvalEmpatia", map);

    }

    @RequestMapping(value = "/resolEvalEmpatia", method = RequestMethod.GET)
    public ModelAndView resolEvalEmpatia_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("familia") != null && session.getAttribute("idNna") != null
                && session.getAttribute("idEmpatia") != null
                && session.getAttribute("numDesig") != null) {
            String familia = (String) session.getAttribute("familia");
            long idNna = (long) session.getAttribute("idNna");
            String idEmpatia = (String) session.getAttribute("idEmpatia");
            String numDesig = (String) session.getAttribute("numDesig");

            Evaluacion tempEval = new Evaluacion();
            if (!idEmpatia.equals("")) {
                long temp = Long.parseLong(idEmpatia);
                if (servicioEtapa.getResolucion2(temp) != null) {
                    Resolucion tempResol = servicioEtapa.getResolucion2(temp);
                    map.put("resolucion", tempResol);
                }
            }
            map.put("numDesig", numDesig);
            map.put("idEmpatia", idEmpatia);
            map.put("idNna", idNna);
            map.put("familia", familia);
            map.put("df", df);

            session.removeAttribute("familia");
            session.removeAttribute("idEmpatia");
            session.removeAttribute("numDesig");
            session.removeAttribute("idNna");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/reg_resol_emp", map);
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion");
        }
    }

    @RequestMapping(value = "/crearResolEmpatia", method = RequestMethod.POST)
    public ModelAndView crearResolEmpatia(ModelMap map, HttpSession session,
            @RequestParam("idEmpatia") long idEmpatia,
            @RequestParam("idNna") long idNna,
            @RequestParam("numDesig") String numDesig,
            @RequestParam("numResol") String numResol,
            @RequestParam("tipo") String tipo,
            @RequestParam("fechaResol") String fechaResol,
            @RequestParam(value = "eliminar", required = false) String eliminar
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Evaluacion tempEval = new Evaluacion();
        tempEval = servicioEtapa.getEvaluacion2(idEmpatia);
        Designacion tempDesig = new Designacion();
        tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), idNna);

        ArrayList<Nna> allNna = new ArrayList();
        ArrayList<Designacion> allDesignaciones = new ArrayList();
        long idN = 0;
        allDesignaciones = servicioEtapa.getListaDesignacionesEstadoAdopcion(numDesig);
        for (Designacion desig : allDesignaciones) {
            if (desig.getNna().getIdnna() != idN) {
                allNna.add(desig.getNna());
                idN = desig.getNna().getIdnna();
            }
        }

        if (eliminar != null) {

            if (allNna.size() > 1) {
                Familia tempFamilia = tempEval.getExpedienteFamilia().getFamilia();
                tempFamilia.setHabilitado(Short.parseShort("1"));
                ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                tempExpFam.setEstado("eliminado");
                servicioEtapa.UpdateFamilia(tempFamilia);
                servicioEtapa.updateExpedienteFamilia(tempExpFam);
                for (Nna tempNna : allNna) {
                    ExpedienteNna ExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                    ExpNna.setEstado("adoptable");
                    Date ahora = new Date();
                    java.sql.Date sql = new java.sql.Date(ahora.getTime());
                    ExpNna.setFechaEstado(sql);
                    ServicioNna.updateExpNna(ExpNna);
                    tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), tempNna.getIdnna());
                    tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                    servicioEtapa.updateDesignacion(tempDesig);
                }

            } else {

                Familia tempFamilia = tempEval.getExpedienteFamilia().getFamilia();
                tempFamilia.setHabilitado(Short.parseShort("1"));
                ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                tempExpFam.setEstado("eliminado");
                tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                servicioEtapa.UpdateFamilia(tempFamilia);
                servicioEtapa.updateExpedienteFamilia(tempExpFam);
                servicioEtapa.updateDesignacion(tempDesig);
                if (tempDesig.getTipoPropuesta().equals("directa")) {
                    ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                    ExpNna.setEstado("adoptable");
                    Date ahora = new Date();
                    java.sql.Date sql = new java.sql.Date(ahora.getTime());
                    ExpNna.setFechaEstado(sql);
                    ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2) {
                    ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                    ExpNna.setEstado("adoptable");
                    Date ahora = new Date();
                    java.sql.Date sql = new java.sql.Date(ahora.getTime());
                    ExpNna.setFechaEstado(sql);
                    ServicioNna.updateExpNna(ExpNna);
                }
                if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3) {
                    ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                    ExpNna.setEstado("adoptable");
                    Date ahora = new Date();
                    java.sql.Date sql = new java.sql.Date(ahora.getTime());
                    ExpNna.setFechaEstado(sql);
                    ServicioNna.updateExpNna(ExpNna);
                }
            }

        }

        if (eliminar == null) {
            if (tipo.equals("sinefecto")) {
                if (allNna.size() > 1) {
                    servicioEtapa.deleteEvaluacion(tempEval);
                    ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                    tempExpFam.setEstado("reevaluacion");
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    for (Nna tempNna : allNna) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                        ExpNna.setEstado("adoptable");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                        tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), tempNna.getIdnna());
                        tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                        servicioEtapa.updateDesignacion(tempDesig);

                    }

                } else {
                    servicioEtapa.deleteEvaluacion(tempEval);
                    ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                    tempExpFam.setEstado("reevaluacion");
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                    servicioEtapa.updateDesignacion(tempDesig);
                    if (tempDesig.getTipoPropuesta().equals("directa")) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("eval");
                        Date ahora = new Date();
                        ExpNna.setFechaEstado(ahora);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                    if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("eval");
                        Date ahora = new Date();
                        ExpNna.setFechaEstado(ahora);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                    if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("eval");
                        Date ahora = new Date();
                        ExpNna.setFechaEstado(ahora);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                }
            } else {
                Resolucion tempResol = new Resolucion();
                tempResol.setEvaluacion(tempEval);
                tempResol.setNumero(numResol);
                tempResol.setTipo(tipo);
                Date tempfecha = tempResol.getFechaResol();
                if (fechaResol != null) {
                    if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
                            || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
                            || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
                        tempResol.setFechaResol(tempfecha);
                    } else {
                        if (!fechaResol.equals("")) {
                            tempResol.setFechaResol(df.stringToDate(fechaResol));
                        } else {
                            tempResol.setFechaResol(null);
                        }
                    }
                } else {
                    tempResol.setFechaResol(null);
                }
                /*if (fechaResol != null && !fechaResol.equals("")) {
                 tempResol.setFechaResol(df.stringToDate(fechaResol));
                 }
                 if (fechaResol == null || fechaResol.equals("")) {
                 tempResol.setFechaResol(null);
                 }*/
                servicioEtapa.crearResolEvaluacion(tempResol);

                String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                        + " con ID: " + usuario.getIdpersonal() + ". Creó la Resolución de evaluación con ID"
                        + ": " + session.getAttribute("idEmpatia");

                String Tipo_registro = "Familia";

                try {
                    String Numero_registro = String.valueOf(usuario.getIdpersonal());

                    ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                } catch (Exception ex) {
                }

            }

        }

//        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        return new ModelAndView("redirect:/EtapaAdopcion");

    }

    @RequestMapping(value = "/evalInforme", method = RequestMethod.POST)
    public ModelAndView evalInforme_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("familia") String familia,
            @RequestParam(value = "idInforme", required = false) String idInforme
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("familia", familia);
        session.setAttribute("idInforme", idInforme);

        return new ModelAndView("redirect:/evalInforme", map);

    }

    @RequestMapping(value = "/evalInforme", method = RequestMethod.GET)
    public ModelAndView evalInforme_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null) {

            long idExpediente = (long) session.getAttribute("idExpediente");
            String familia = (String) session.getAttribute("familia");
            String idInforme = (String) session.getAttribute("idInforme");

            Evaluacion tempEval = new Evaluacion();
            if (!idInforme.equals("")) {
                long temp = Long.parseLong(idInforme);
                tempEval = servicioEtapa.getEvaluacion(temp);
                map.put("informe", tempEval);
            }

            map.put("idExpediente", idExpediente);
            map.put("familia", familia);
            map.put("listaPersonal", ServicioPersonal.ListaPersonal());
            map.put("df", df);

            session.removeAttribute("idExpediente");
            session.removeAttribute("familia");
            session.removeAttribute("idInforme");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/inf_col_fam", map);
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion");
        }
    }

    @RequestMapping(value = "/crearEvalInforme", method = RequestMethod.POST)
    public ModelAndView crearEvalInforme(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente,
            @RequestParam("personal") long personal,
            @RequestParam("resultado") String resultado,
            @RequestParam("fechaEval") String fechaEval,
            @RequestParam("numEval") String numEval,
            @RequestParam("obs") String obs,
            @RequestParam("persInt") String persInt,
            @RequestParam("numPers") long numPers
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ExpedienteFamilia EF = new ExpedienteFamilia();
        Personal tempP = new Personal();
        EF = servicioEtapa.getExpedienteFamilia(idExpediente);
        tempP = ServicioPersonal.getPersonal(personal);
        Evaluacion tempEval = new Evaluacion();
        tempEval.setExpedienteFamilia(EF);
        tempEval.setPersonal(tempP);
        tempEval.setTipo("informe");
        tempEval.setResultado(resultado);
        Date tempfecha = tempEval.getFechaResultado();
        if (fechaEval != null) {
            if (fechaEval.contains("ene") || fechaEval.contains("feb") || fechaEval.contains("mar") || fechaEval.contains("abr")
                    || fechaEval.contains("may") || fechaEval.contains("jun") || fechaEval.contains("jul") || fechaEval.contains("ago")
                    || fechaEval.contains("set") || fechaEval.contains("oct") || fechaEval.contains("nov") || fechaEval.contains("dic")) {
                tempEval.setFechaResultado(tempfecha);
            } else {
                if (!fechaEval.equals("")) {
                    tempEval.setFechaResultado(df.stringToDate(fechaEval));
                } else {
                    tempEval.setFechaResultado(null);
                }
            }
        } else {
            tempEval.setFechaResultado(null);
        }
        /*if (fechaEval != null && !fechaEval.equals("")) {
         tempEval.setFechaResultado(df.stringToDate(fechaEval));
         }
         if (fechaEval == null || fechaEval.equals("")) {
         tempEval.setFechaResultado(null);
         }*/
        tempEval.setNumEval(numEval);
        tempEval.setObservacion(obs);
        tempEval.setPersInt(persInt);
        if (numPers != 0) {
            tempEval.setNumPersInt(numPers);
        } else {
            long temp = 0;
            tempEval.setNumPersInt(temp);
        }
        servicioEtapa.crearEvaluacionAdopcion(tempEval);

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Creó evaluación de adopción en el expediente con ID"
                + ": " + session.getAttribute("idExpediente");

        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

//        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
        return new ModelAndView("redirect:/EtapaAdopcion");

    }

    @RequestMapping(value = "/resolEvalInforme", method = RequestMethod.POST)
    public ModelAndView resolEvalInforme_POST(ModelMap map, HttpSession session,
            @RequestParam("familia") String familia,
            @RequestParam("idInforme") String idInforme,
            @RequestParam("idNna") String idNna,
            @RequestParam("numDesig") String numDesig,
            @RequestParam("idEmpatia") String idEmpatia
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("familia", familia);
        session.setAttribute("idInforme", idInforme);
        session.setAttribute("idNna", idNna);
        session.setAttribute("numDesig", numDesig);
        session.setAttribute("idEmpatia", idEmpatia);

        return new ModelAndView("redirect:/resolEvalInforme", map);

    }

    @RequestMapping(value = "/resolEvalInforme", method = RequestMethod.GET)
    public ModelAndView resolEvalInforme_GET(ModelMap map, HttpSession session
    //            @RequestParam("familia") String familia,
    //            @RequestParam("idInforme") String idInforme,
    //            @RequestParam("idNna") String idNna,
    //            @RequestParam("numDesig") String numDesig,
    //            @RequestParam("idEmpatia") String idEmpatia
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idInforme") != null && session.getAttribute("idNna") != null && session.getAttribute("idEmpatia") != null) {

            String familia = (String) session.getAttribute("familia");
            String idInforme = (String) session.getAttribute("idInforme");
            String idNna = (String) session.getAttribute("idNna");
            String numDesig = (String) session.getAttribute("numDesig");
            String idEmpatia = (String) session.getAttribute("idEmpatia");

            Evaluacion tempEval = new Evaluacion();
            if (!idInforme.equals("")) {
                long temp = Long.parseLong(idInforme);
                if (servicioEtapa.getResolucion2(temp) != null) {
                    Resolucion tempResol = servicioEtapa.getResolucion2(temp);
                    map.put("resolucion", tempResol);
                }
            }
            map.put("numDesig", numDesig);
            map.put("idInforme", idInforme);
            map.put("idEmpatia", idEmpatia);
            map.put("idNna", idNna);
            map.put("familia", familia);
            map.put("df", df);

            session.removeAttribute("idInforme");
            session.removeAttribute("familia");
            session.removeAttribute("idNna");
            session.removeAttribute("numDesig");
            session.removeAttribute("idEmpatia");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/reg_resol_inf", map);
        } else {

            return new ModelAndView("redirect:/EtapaAdopcion", map);
        }
    }

    @RequestMapping(value = "/crearResolInforme", method = RequestMethod.POST)
    public ModelAndView crearResolInforme_POST(ModelMap map, HttpSession session,
            @RequestParam("idInforme") long idInforme,
            @RequestParam("idNna") long idNna,
            @RequestParam("idEmpatia") long idEmpatia,
            @RequestParam("numResol") String numResol,
            @RequestParam("tipo") String tipo,
            @RequestParam("fechaResol") String fechaResol,
            @RequestParam("numDesig") String numDesig,
            @RequestParam(value = "eliminar", required = false) String eliminar
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idInforme", idInforme);
        session.setAttribute("idNna", idNna);
        session.setAttribute("idEmpatia", idEmpatia);
        session.setAttribute("numResol", numResol);
        session.setAttribute("tipo", tipo);
        session.setAttribute("fechaResol", fechaResol);
        session.setAttribute("numDesig", numDesig);
        session.setAttribute("eliminar", eliminar);
        return new ModelAndView("redirect:/crearResolInforme", map);

    }

    @RequestMapping(value = "/crearResolInforme", method = RequestMethod.GET)
    public ModelAndView crearResolInforme_GET(ModelMap map, HttpSession session
    //            @RequestParam("idInforme") long idInforme,
    //            @RequestParam("idNna") long idNna,
    //            @RequestParam("idEmpatia") long idEmpatia,
    //            @RequestParam("numResol") String numResol,
    //            @RequestParam("tipo") String tipo,
    //            @RequestParam("fechaResol") String fechaResol,
    //            @RequestParam("numDesig") String numDesig,
    //            @RequestParam(value = "eliminar", required = false) String eliminar
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (session.getAttribute("idInforme") != null && session.getAttribute("idNna") != null && session.getAttribute("idEmpatia") != null) {

            long idInforme = (long) session.getAttribute("idInforme");
            long idNna = (long) session.getAttribute("idNna");
            long idEmpatia = (long) session.getAttribute("idEmpatia");
            String numResol = (String) session.getAttribute("numResol");
            String tipo = (String) session.getAttribute("tipo");
            String fechaResol = (String) session.getAttribute("fechaResol");
            String numDesig = (String) session.getAttribute("numDesig");
            String eliminar = (String) session.getAttribute("eliminar");

            Evaluacion tempEval = new Evaluacion();
            tempEval = servicioEtapa.getEvaluacion2(idInforme);
            Designacion tempDesig = new Designacion();
            tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), idNna);

            ArrayList<Nna> allNna = new ArrayList();
            ArrayList<Designacion> allDesignaciones = new ArrayList();
            long idN = 0;
            allDesignaciones = servicioEtapa.getListaDesignacionesEstadoAdopcion(numDesig);
            for (Designacion desig : allDesignaciones) {
                if (desig.getNna().getIdnna() != idN) {
                    allNna.add(desig.getNna());
                    idN = desig.getNna().getIdnna();
                }
            }

            if (eliminar != null) {
                if (allNna.size() > 1) {
                    Familia tempFamilia = tempEval.getExpedienteFamilia().getFamilia();
                    tempFamilia.setHabilitado(Short.parseShort("1"));
                    ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                    tempExpFam.setEstado("eliminado");
                    servicioEtapa.UpdateFamilia(tempFamilia);
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    for (Nna tempNna : allNna) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                        ExpNna.setEstado("adoptable");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                        tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), tempNna.getIdnna());
                        tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                        servicioEtapa.updateDesignacion(tempDesig);
                    }

                } else {
                    Familia tempFamilia = tempEval.getExpedienteFamilia().getFamilia();
                    tempFamilia.setHabilitado(Short.parseShort("1"));
                    ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                    tempExpFam.setEstado("eliminado");
                    tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                    servicioEtapa.UpdateFamilia(tempFamilia);
                    servicioEtapa.updateExpedienteFamilia(tempExpFam);
                    servicioEtapa.updateDesignacion(tempDesig);

                    if (tempDesig.getTipoPropuesta().equals("directa")) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("eval");
                        Date ahora = new Date();
                        ExpNna.setFechaEstado(ahora);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                    if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("eval");
                        Date ahora = new Date();
                        ExpNna.setFechaEstado(ahora);
                        ServicioNna.updateExpNna(ExpNna);
                    }
                    if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3) {
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("eval");
                        Date ahora = new Date();
                        ExpNna.setFechaEstado(ahora);
                        ServicioNna.updateExpNna(ExpNna);
                    }

                }
            }

            if (eliminar == null) {
                if (tipo.equals("revocacion")) {
                    if (allNna.size() > 1) {
                        Resolucion tempResol = servicioEtapa.getResolucion2(idEmpatia);
                        Evaluacion tempEmpatia = servicioEtapa.getEvaluacion(idEmpatia);
                        servicioEtapa.deleteResolucion(tempResol);
                        servicioEtapa.deleteEvaluacion(tempEmpatia);
                        ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                        servicioEtapa.deleteEvaluacion(tempEval);
                        tempExpFam.setEstado("reevaluacion");
                        servicioEtapa.updateExpedienteFamilia(tempExpFam);
                        for (Nna tempNna : allNna) {
                            ExpedienteNna ExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                            ExpNna.setEstado("adoptable");
                            Date ahora = new Date();
                            java.sql.Date sql = new java.sql.Date(ahora.getTime());
                            ExpNna.setFechaEstado(sql);
                            ServicioNna.updateExpNna(ExpNna);
                            tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), tempNna.getIdnna());
                            tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                            servicioEtapa.updateDesignacion(tempDesig);
                        }

                    } else {
                        Resolucion tempResol = servicioEtapa.getResolucion2(idEmpatia);
                        Evaluacion tempEmpatia = servicioEtapa.getEvaluacion(idEmpatia);
                        servicioEtapa.deleteResolucion(tempResol);
                        servicioEtapa.deleteEvaluacion(tempEmpatia);
                        ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                        servicioEtapa.deleteEvaluacion(tempEval);
                        tempExpFam.setEstado("reevaluacion");
                        servicioEtapa.updateExpedienteFamilia(tempExpFam);
                        tempDesig.setAceptacionConsejo(Short.parseShort("3"));
                        servicioEtapa.updateDesignacion(tempDesig);

                        if (tempDesig.getTipoPropuesta().equals("directa")) {
                            ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                            ExpNna.setEstado("eval");
                            Date ahora = new Date();
                            ExpNna.setFechaEstado(ahora);
                            ServicioNna.updateExpNna(ExpNna);
                        }
                        if (tempDesig.getTipoPropuesta().equals("dupla") && tempDesig.getPrioridad() == 2) {
                            ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                            ExpNna.setEstado("eval");
                            Date ahora = new Date();
                            ExpNna.setFechaEstado(ahora);
                            ServicioNna.updateExpNna(ExpNna);
                        }
                        if (tempDesig.getTipoPropuesta().equals("terna") && tempDesig.getPrioridad() == 3) {
                            ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                            ExpNna.setEstado("eval");
                            Date ahora = new Date();
                            ExpNna.setFechaEstado(ahora);
                            ServicioNna.updateExpNna(ExpNna);
                        }

                    }
                } else {
                    if (allNna.size() > 1) {
                        Resolucion tempResol = new Resolucion();
                        tempResol.setEvaluacion(tempEval);
                        tempResol.setNumero(numResol);
                        tempResol.setTipo(tipo);
                        Date tempfecha = tempResol.getFechaResol();
                        if (fechaResol != null) {
                            if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
                                    || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
                                    || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
                                tempResol.setFechaResol(tempfecha);
                            } else {
                                if (!fechaResol.equals("")) {
                                    tempResol.setFechaResol(df.stringToDate(fechaResol));
                                } else {
                                    tempResol.setFechaResol(null);
                                }
                            }
                        } else {
                            tempResol.setFechaResol(null);
                        }
                        /*if (fechaResol != null && !fechaResol.equals("")) {
                         tempResol.setFechaResol(df.stringToDate(fechaResol));
                         }
                         if (fechaResol == null || fechaResol.equals("")) {
                         tempResol.setFechaResol(null);
                         }*/
                        servicioEtapa.crearResolEvaluacion(tempResol);

                        ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                        tempExpFam.setEstado("post");
                        servicioEtapa.updateExpedienteFamilia(tempExpFam);

                        for (Nna tempNna : allNna) {
                            ExpedienteNna ExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                            ExpNna.setEstado("adop");
                            Date ahora = new Date();
                            java.sql.Date sql = new java.sql.Date(ahora.getTime());
                            ExpNna.setFechaEstado(sql);
                            ServicioNna.updateExpNna(ExpNna);
                            tempDesig = servicioEtapa.getDesignacion(tempEval.getExpedienteFamilia().getIdexpedienteFamilia(), tempNna.getIdnna());
                            tempDesig.setAceptacionConsejo(Short.parseShort("2"));
                            servicioEtapa.updateDesignacion(tempDesig);

                            PostAdopcion tempPost = new PostAdopcion();
                            tempPost.setFamilia(tempEval.getExpedienteFamilia().getFamilia());
                            tempPost.setidNna(tempNna.getIdnna());
                            if (fechaResol != null && !fechaResol.equals("")) {
                                tempPost.setFechaResolucion(df.stringToDate(fechaResol));
                            }
                            if (fechaResol == null || fechaResol.equals("")) {
                                tempPost.setFechaResolucion(null);
                            }
                            servicioEtapa.crearPostAdopcion(tempPost);

                            ArrayList<Designacion> tempDesigs = new ArrayList();
                            tempDesigs = servicioEtapa.getListaDesignaciones2(tempNna.getIdnna());
                            for (Designacion designacion : tempDesigs) {
                                designacion.setAceptacionConsejo(Short.parseShort("3"));
                                ExpedienteFamilia tempExpFam2 = designacion.getExpedienteFamilia();
                                tempExpFam2.setEstado("espera");
                                servicioEtapa.updateExpedienteFamilia(tempExpFam2);
                                servicioEtapa.updateDesignacion(designacion);
                            }

                        }

                    } else {

                        Resolucion tempResol = new Resolucion();
                        tempResol.setEvaluacion(tempEval);
                        tempResol.setNumero(numResol);
                        tempResol.setTipo(tipo);
                        Date tempfecha = tempResol.getFechaResol();
                        if (fechaResol != null) {
                            if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
                                    || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
                                    || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
                                tempResol.setFechaResol(tempfecha);
                            } else {
                                if (!fechaResol.equals("")) {
                                    tempResol.setFechaResol(df.stringToDate(fechaResol));
                                } else {
                                    tempResol.setFechaResol(null);
                                }
                            }
                        } else {
                            tempResol.setFechaResol(null);
                        }
                        /*if (fechaResol != null && !fechaResol.equals("")) {
                         tempResol.setFechaResol(df.stringToDate(fechaResol));
                         }
                         if (fechaResol == null || fechaResol.equals("")) {
                         tempResol.setFechaResol(null);
                         }*/
                        servicioEtapa.crearResolEvaluacion(tempResol);
                        ExpedienteFamilia tempExpFam = tempEval.getExpedienteFamilia();
                        tempExpFam.setEstado("post");
                        servicioEtapa.updateExpedienteFamilia(tempExpFam);

                        tempDesig.setAceptacionConsejo(Short.parseShort("2"));
                        servicioEtapa.updateDesignacion(tempDesig);

                        PostAdopcion tempPost = new PostAdopcion();
                        tempPost.setFamilia(tempEval.getExpedienteFamilia().getFamilia());
                        tempPost.setidNna(idNna);
                        //tempfecha = tempPost.getFechaResolucion();
//                if (fechaResol != null) {
//                    if (fechaResol.contains("ene") || fechaResol.contains("feb") || fechaResol.contains("mar") || fechaResol.contains("abr")
//                            || fechaResol.contains("may") || fechaResol.contains("jun") || fechaResol.contains("jul") || fechaResol.contains("ago")
//                            || fechaResol.contains("set") || fechaResol.contains("oct") || fechaResol.contains("nov") || fechaResol.contains("dic")) {
//                        tempPost.setFechaResolucion(tempfecha);
//                    } else {
//                        if (!fechaResol.equals("")) {
//                            tempPost.setFechaResolucion(df.stringToDate(fechaResol));
//                        } else {
//                            tempPost.setFechaResolucion(null);
//                        }
//                    }
//                } else {
//                    tempPost.setFechaResolucion(null);
//                }
                        if (fechaResol != null && !fechaResol.equals("")) {
                            tempPost.setFechaResolucion(df.stringToDate(fechaResol));
                        }
                        if (fechaResol == null || fechaResol.equals("")) {
                            tempPost.setFechaResolucion(null);
                        }
                        servicioEtapa.crearPostAdopcion(tempPost);
                        ExpedienteNna ExpNna = ServicioNna.getExpNna(idNna);
                        ExpNna.setEstado("adop");
                        Date ahora = new Date();
                        java.sql.Date sql = new java.sql.Date(ahora.getTime());
                        ExpNna.setFechaEstado(sql);
                        ServicioNna.updateExpNna(ExpNna);
                        ArrayList<Designacion> tempDesigs = new ArrayList();
                        tempDesigs = servicioEtapa.getListaDesignaciones2(idNna);
                        for (Designacion designacion : tempDesigs) {
                            designacion.setAceptacionConsejo(Short.parseShort("3"));
                            ExpedienteFamilia tempExpFam2 = designacion.getExpedienteFamilia();
                            tempExpFam2.setEstado("espera");
                            servicioEtapa.updateExpedienteFamilia(tempExpFam2);
                            servicioEtapa.updateDesignacion(designacion);
                        }

//                    map.put("listaPost", servicioEtapa.getListaPostAdopcion());
//                    return new ModelAndView("/Personal/Buscador_etapa/etapa_post/etapa_post", map);
                        session.removeAttribute("idInforme");
                        session.removeAttribute("idNna");
                        session.removeAttribute("idEmpatia");
                        session.removeAttribute("numResol");
                        session.removeAttribute("tipo");
                        session.removeAttribute("fechaResol");
                        session.removeAttribute("numDesig");
                        session.removeAttribute("eliminar");

                        return new ModelAndView("redirect:/EtapaPostAdopcion");
                    }

                }
            }

//        map.put("listaAdopciones", servicioEtapa.getListaAdopciones());
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_adopcion/etapa_adopcion", map);
            session.removeAttribute("idInforme");
            session.removeAttribute("idNna");
            session.removeAttribute("idEmpatia");
            session.removeAttribute("numResol");
            session.removeAttribute("tipo");
            session.removeAttribute("fechaResol");
            session.removeAttribute("numDesig");
            session.removeAttribute("eliminar");
            return new ModelAndView("redirect:/EtapaAdopcion");
        } else {
            return new ModelAndView("redirect:/EtapaAdopcion");
        }
    }

    /*  ETAPA  POST ADOPCION */
    @RequestMapping(value = "/EtapaPostAdopcion", method = RequestMethod.GET)
    public ModelAndView EtapaPostAdopcion(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaPost", servicioEtapa.getListaPostAdopcion());
        volver = "/EtapaPostAdopcion";
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/etapa_post", map);

    }

    @RequestMapping(value = "/UpdatePostAdopcion", method = RequestMethod.POST)
    public ModelAndView UpdatePostAdopcion_POST(ModelMap map, HttpSession session,
            @RequestParam("idPost") long idPost,
            @RequestParam("familia") String familia,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idPost", idPost);
        session.setAttribute("familia", familia);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/UpdatePostAdopcion", map);

    }

    @RequestMapping(value = "/UpdatePostAdopcion", method = RequestMethod.GET)
    public ModelAndView UpdatePostAdopcion_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idPost") != null) {

            long idPost = (long) session.getAttribute("idPost");
            String familia = (String) session.getAttribute("familia");
            String volver = (String) session.getAttribute("volver");

            PostAdopcion newPost = servicioEtapa.getPostAdopcion(idPost);
            map.put("df", df);
            map.put("post", newPost);
            map.put("familia", familia);
            map.addAttribute("volver", volver);

            return new ModelAndView("/Personal/Buscador_etapa/etapa_post/expediente_post", map);
        } else {
            return new ModelAndView("redirect:/EtapaPostAdopcion");
        }

    }

    @RequestMapping(value = "/EditarExpedientePost", method = RequestMethod.POST)
    public ModelAndView EditarExpedientePost(ModelMap map, HttpSession session,
            @RequestParam("idPost") long idPost,
            @RequestParam("numInformes") Long numInformes,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        PostAdopcion newPost = servicioEtapa.getPostAdopcion(idPost);
        newPost.setNumeroInformes(numInformes);
        servicioEtapa.updatePostAdopcion(newPost);

        map.put("listaPost", servicioEtapa.getListaPostAdopcion());
        map.addAttribute("volver", volver);
//        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/etapa_post", map);
        return new ModelAndView("redirect:/EtapaPostAdopcion");

    }

    @RequestMapping(value = "/verInformesPost", method = RequestMethod.POST)
    public ModelAndView verInformesPost(ModelMap map, HttpSession session,
            @RequestParam("idPost") long idPost,
            @RequestParam("familia") String familia,
            @RequestParam("numInformes") int numInformes,
            @RequestParam("fechaAdopcion") String fechaAdopcion,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList<InformePostAdoptivo>(numInformes);
        ArrayList<InformePostAdoptivo> informesTabla = new ArrayList();
        informesTabla = servicioEtapa.getListaInformesPost(idPost);
        int continuacion = 0;
        if (informesTabla.isEmpty()) {
            for (int i = 0; i < numInformes; i++) {
                InformePostAdoptivo temp = new InformePostAdoptivo();
                allInformePostAdoptivo.add(temp);
            }
            map.put("listaInformes", allInformePostAdoptivo);
        } else {
            for (InformePostAdoptivo informePostAdoptivo : informesTabla) {
                int temp = Integer.parseInt(informePostAdoptivo.getNumeroInforme()) - 1;
                continuacion = temp;
                if (temp > allInformePostAdoptivo.size()) {
                    for (int i = allInformePostAdoptivo.size(); i <= temp; i++) {
                        InformePostAdoptivo tempIP = new InformePostAdoptivo();
                        if (i == temp) {
                            allInformePostAdoptivo.add(informePostAdoptivo);
                        } else {
                            allInformePostAdoptivo.add(i, tempIP);
                        }
                    }
                } else {
                    allInformePostAdoptivo.add(temp, informePostAdoptivo);
                }
            }

            if (informesTabla.size() < numInformes) {
                for (int i = continuacion + 1; i < numInformes; i++) {

                    InformePostAdoptivo temp = new InformePostAdoptivo();
                    allInformePostAdoptivo.add(i, temp);

                }
            }
            map.put("listaInformes", allInformePostAdoptivo);
        }
        String[] parts = fechaAdopcion.split("-");
        int anho = Integer.parseInt(parts[0]);
        int mes = Integer.parseInt(parts[1]);
        int dia = Integer.parseInt(parts[2]);
        String fechaEntrega;
        ArrayList<String> listaFechas = new ArrayList();
        for (int i = 0; i < numInformes; i++) {
            int contadorMes = mes + 6;
            if (contadorMes > 12) {
                mes = contadorMes - 12;
                anho = anho + 1;
                switch (mes) {
                    case 1:
                        fechaEntrega = "Ene" + "/" + anho;
                        break;
                    case 2:
                        fechaEntrega = "Feb" + "/" + anho;
                        break;
                    case 3:
                        fechaEntrega = "Mar" + "/" + anho;
                        break;
                    case 4:
                        fechaEntrega = "Abr" + "/" + anho;
                        break;
                    case 5:
                        fechaEntrega = "May" + "/" + anho;
                        break;
                    case 6:
                        fechaEntrega = "Jun" + "/" + anho;
                        break;
                    case 7:
                        fechaEntrega = "Jul" + "/" + anho;
                        break;
                    case 8:
                        fechaEntrega = "Ago" + "/" + anho;
                        break;
                    case 9:
                        fechaEntrega = "Sep" + "/" + anho;
                        break;
                    case 10:
                        fechaEntrega = "Oct" + "/" + anho;
                        break;
                    case 11:
                        fechaEntrega = "Nov" + "/" + anho;
                        break;
                    default:
                        fechaEntrega = "Dec" + "/" + anho;
                }
                listaFechas.add(i, fechaEntrega);
            } else {
                mes = mes + 6;
                fechaEntrega = mes + "/" + anho;
                switch (mes) {
                    case 1:
                        fechaEntrega = "Ene" + "/" + anho;
                        break;
                    case 2:
                        fechaEntrega = "Feb" + "/" + anho;
                        break;
                    case 3:
                        fechaEntrega = "Mar" + "/" + anho;
                        break;
                    case 4:
                        fechaEntrega = "Abr" + "/" + anho;
                        break;
                    case 5:
                        fechaEntrega = "May" + "/" + anho;
                        break;
                    case 6:
                        fechaEntrega = "Jun" + "/" + anho;
                        break;
                    case 7:
                        fechaEntrega = "Jul" + "/" + anho;
                        break;
                    case 8:
                        fechaEntrega = "Ago" + "/" + anho;
                        break;
                    case 9:
                        fechaEntrega = "Sep" + "/" + anho;
                        break;
                    case 10:
                        fechaEntrega = "Oct" + "/" + anho;
                        break;
                    case 11:
                        fechaEntrega = "Nov" + "/" + anho;
                        break;
                    default:
                        fechaEntrega = "Dec" + "/" + anho;
                }
                listaFechas.add(i, fechaEntrega);
            }
        }

        map.put("listaFechas", listaFechas);
        map.put("fechaAdopcion", fechaAdopcion);
        map.put("familia", familia);
        map.put("idPost", idPost);
        map.put("numInformes", numInformes);

        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/Buscador_etapa/etapa_post/informes_general", map);

    }

    @RequestMapping(value = "/EditarInforme", method = RequestMethod.POST)
    public ModelAndView EditarInforme_POST(ModelMap map, HttpSession session,
            @RequestParam("idInforme") String idInforme,
            @RequestParam(value = "ultimo", required = false) String ultimo,
            @RequestParam("familia") String familia,
            @RequestParam("num") String num,
            @RequestParam("idPost") long idPost,
            @RequestParam("numInformes") int numInformes,
            @RequestParam("fechaAproxRecep") String fechaAproxRecep,
            @RequestParam("fechaAdopcion") String fechaAdopcion
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idInforme", idInforme);
        session.setAttribute("ultimo", ultimo);
        session.setAttribute("familia", familia);
        session.setAttribute("num", num);
        session.setAttribute("idPost", idPost);
        session.setAttribute("numInformes", numInformes);
        session.setAttribute("fechaAproxRecep", fechaAproxRecep);
        session.setAttribute("fechaAdopcion", fechaAdopcion);

        return new ModelAndView("redirect:/EditarInforme", map);

    }

    @RequestMapping(value = "/EditarInforme", method = RequestMethod.GET)
    public ModelAndView EditarInforme_GET(ModelMap map, HttpSession session
    //            @RequestParam("idInforme") String idInforme,
    //            @RequestParam(value = "ultimo", required = false) String ultimo,
    //            @RequestParam("familia") String familia,
    //            @RequestParam("num") String num,
    //            @RequestParam("idPost") long idPost,
    //            @RequestParam("numInformes") int numInformes,
    //            @RequestParam("fechaAdopcion") String fechaAdopcion
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idInforme") != null && session.getAttribute("idPost") != null && session.getAttribute("numInformes") != null) {

            String idInforme = (String) session.getAttribute("idInforme");
            String ultimo = (String) session.getAttribute("ultimo");
            String familia = (String) session.getAttribute("familia");
            String num = (String) session.getAttribute("num");
            long idPost = (long) session.getAttribute("idPost");
            int numInformes = (int) session.getAttribute("numInformes");
            String fechaAproxRecep = (String) session.getAttribute("fechaAproxRecep");
            String fechaAdopcion = (String) session.getAttribute("fechaAdopcion");

            if (idInforme != null && !idInforme.equals("")) {
                long id = Long.parseLong(idInforme);
                InformePostAdoptivo temp = servicioEtapa.getInformePost(id);
                map.put("informe", temp);
                map.put("ultimo", ultimo);
                map.put("familia", familia);
                map.put("num", num);
                map.put("df", df);
                map.put("listaPersonal", ServicioPersonal.ListaPersonal());
                map.put("idPost", idPost);
                map.put("numInformes", numInformes);
                map.put("fechaAdopcion", fechaAdopcion);
                String[] parts = fechaAproxRecep.split("/");
                String mes = parts[0];
                int anho = Integer.parseInt(parts[1]);
                
                String fechaApRecep = "";
                if(mes.equals("Ene")){
                   fechaApRecep = "01" + "/" + "01" + "/" + anho;
                }else if(mes.equals("Feb")){
                   fechaApRecep = "01" + "/" + "02" + "/" + anho;
                }else if(mes.equals("Mar")){
                   fechaApRecep = "01" + "/" + "03" + "/" + anho;
                }else if(mes.equals("Abr")){
                   fechaApRecep = "01" + "/" + "04" + "/" + anho;
                }else if(mes.equals("May")){
                   fechaApRecep = "01" + "/" + "05" + "/" + anho;
                }else if(mes.equals("Jun")){
                   fechaApRecep = "01" + "/" + "06" + "/" + anho;
                }else if(mes.equals("Jul")){
                   fechaApRecep = "01" + "/" + "07" + "/" + anho;
                }else if(mes.equals("Ago")){
                   fechaApRecep = "01" + "/" + "08" + "/" + anho;
                }else if(mes.equals("Sep")){
                   fechaApRecep = "01" + "/" + "09" + "/" + anho;
                }else if(mes.equals("Oct")){
                   fechaApRecep = "01" + "/" + "10" + "/" + anho;
                }else if(mes.equals("Nov")){
                   fechaApRecep = "01" + "/" + "11" + "/" + anho;
                }else {
                   fechaApRecep = "01" + "/" + "12" + "/" + anho;
                }
                map.put("fechaApRecep", fechaApRecep);
                
            } else {
                String[] parts = fechaAproxRecep.split("/");
                String mes = parts[0];
                int anho = Integer.parseInt(parts[1]);
                
                String fechaApRecep = "";
                if(mes.equals("Ene")){
                   fechaApRecep = "01" + "/" + "01" + "/" + anho;
                }else if(mes.equals("Feb")){
                   fechaApRecep = "01" + "/" + "02" + "/" + anho;
                }else if(mes.equals("Mar")){
                   fechaApRecep = "01" + "/" + "03" + "/" + anho;
                }else if(mes.equals("Abr")){
                   fechaApRecep = "01" + "/" + "04" + "/" + anho;
                }else if(mes.equals("May")){
                   fechaApRecep = "01" + "/" + "05" + "/" + anho;
                }else if(mes.equals("Jun")){
                   fechaApRecep = "01" + "/" + "06" + "/" + anho;
                }else if(mes.equals("Jul")){
                   fechaApRecep = "01" + "/" + "07" + "/" + anho;
                }else if(mes.equals("Ago")){
                   fechaApRecep = "01" + "/" + "08" + "/" + anho;
                }else if(mes.equals("Sep")){
                   fechaApRecep = "01" + "/" + "09" + "/" + anho;
                }else if(mes.equals("Oct")){
                   fechaApRecep = "01" + "/" + "10" + "/" + anho;
                }else if(mes.equals("Nov")){
                   fechaApRecep = "01" + "/" + "11" + "/" + anho;
                }else {
                   fechaApRecep = "01" + "/" + "12" + "/" + anho;
                }
                map.put("fechaApRecep", fechaApRecep);
                map.put("familia", familia);
                map.put("ultimo", ultimo);
                map.put("num", num);
                map.put("df", df);
                map.put("listaPersonal", ServicioPersonal.ListaPersonal());
                map.put("idPost", idPost);
                map.put("numInformes", numInformes);
                map.put("fechaAdopcion", fechaAdopcion);
            }
            map.put("listaPost", servicioEtapa.getListaPostAdopcion());

            session.removeAttribute("idInforme");
            session.removeAttribute("ultimo");
            session.removeAttribute("familia");
            session.removeAttribute("num");
            session.removeAttribute("idPost");
            session.removeAttribute("numInformes");
            session.removeAttribute("fechaAdopcion");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_post/informe_post", map);
        } else {
            return new ModelAndView("redirect:/EtapaPostAdopcion");
        }

    }

    @RequestMapping(value = "/InsertarInforme", method = RequestMethod.POST)
    public ModelAndView InsertarInforme_POST(ModelMap map, HttpSession session,
            @RequestParam("idInforme") Long idInforme,
            @RequestParam("num") String num,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "fechaProyectado", required = false) String fechaProyectado,
            @RequestParam(value = "fechaRecepcion", required = false) String fechaRecepcion,
            @RequestParam(value = "personal", required = false) long personal,
            @RequestParam(value = "fechaInforme", required = false) String fechaInforme,
            @RequestParam(value = "fechaActa", required = false) String fechaActa,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam("idPost") long idPost,
            @RequestParam("familia") String familia,
            @RequestParam("numInformes") int numInformes,
            @RequestParam("fechaAdopcion") String fechaAdopcion
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idInforme", idInforme);
        session.setAttribute("num", num);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaProyectado", fechaProyectado);
        session.setAttribute("fechaRecepcion", fechaRecepcion);
        session.setAttribute("personal", personal);
        session.setAttribute("fechaInforme", fechaInforme);
        session.setAttribute("fechaActa", fechaActa);
        session.setAttribute("obs", obs);
        session.setAttribute("idPost", idPost);
        session.setAttribute("familia", familia);
        session.setAttribute("numInformes", numInformes);
        session.setAttribute("fechaAdopcion", fechaAdopcion);

        return new ModelAndView("redirect:/InsertarInforme", map);

    }

    @RequestMapping(value = "/InsertarInforme", method = RequestMethod.GET)
    public ModelAndView InsertarInforme_GET(ModelMap map, HttpSession session
    //            @RequestParam("idInforme") Long idInforme,
    //            @RequestParam("num") String num,
    //            @RequestParam(value = "estado", required = false) String estado,
    //            @RequestParam(value = "fechaProyectado", required = false) String fechaProyectado,
    //            @RequestParam(value = "fechaRecepcion", required = false) String fechaRecepcion,
    //            @RequestParam(value = "personal", required = false) long personal,
    //            @RequestParam(value = "fechaInforme", required = false) String fechaInforme,
    //            @RequestParam(value = "fechaActa", required = false) String fechaActa,
    //            @RequestParam(value = "obs", required = false) String obs,
    //            @RequestParam("idPost") long idPost,
    //            @RequestParam("familia") String familia,
    //            @RequestParam("numInformes") int numInformes,
    //            @RequestParam("fechaAdopcion") String fechaAdopcion
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idInforme") != null && session.getAttribute("idPost") != null && session.getAttribute("numInformes") != null) {

            Long idInforme = (Long) session.getAttribute("idInforme");
            String num = (String) session.getAttribute("num");
            String estado = (String) session.getAttribute("estado");
            String fechaProyectado = (String) session.getAttribute("fechaProyectado");
            String fechaRecepcion = (String) session.getAttribute("fechaRecepcion");
            long personal = (long) session.getAttribute("personal");
            String fechaInforme = (String) session.getAttribute("fechaInforme");
            String fechaActa = (String) session.getAttribute("fechaActa");
            String obs = (String) session.getAttribute("obs");
            long idPost = (long) session.getAttribute("idPost");
            String familia = (String) session.getAttribute("familia");
            int numInformes = (int) session.getAttribute("numInformes");
            String fechaAdopcion = (String) session.getAttribute("fechaAdopcion");

            if (idInforme != 0) {
                long id = idInforme;
                InformePostAdoptivo temp = servicioEtapa.getInformePost(id);
                temp.setNumeroInforme(num);
                temp.setEstado(estado);
                //System.out.print("Actualizar" + fechaProyectado);
                Date tempfecha = temp.getFechaRecepcionProyectado();
                if (fechaProyectado != null) {
                    if (fechaProyectado.contains("ene") || fechaProyectado.contains("feb") || fechaProyectado.contains("mar") || fechaProyectado.contains("abr")
                            || fechaProyectado.contains("may") || fechaProyectado.contains("jun") || fechaProyectado.contains("jul") || fechaProyectado.contains("ago")
                            || fechaProyectado.contains("set") || fechaProyectado.contains("oct") || fechaProyectado.contains("nov") || fechaProyectado.contains("dic")) {
                        temp.setFechaRecepcionProyectado(tempfecha);
                    } else {
                        if (!fechaProyectado.equals("")) {
                            temp.setFechaRecepcionProyectado(df.stringToDate(fechaProyectado));
                        } else {
                            temp.setFechaRecepcionProyectado(null);
                        }
                    }
                } else {
                    temp.setFechaRecepcionProyectado(null);
                }
                /*if (fechaProyectado != null && !fechaProyectado.equals("")) {
                 temp.setFechaRecepcionProyectado(df.stringToDate(fechaProyectado));
                 }
                 if (fechaProyectado == null || fechaProyectado.equals("")) {
                 temp.setFechaRecepcionProyectado(null);
                 }*/
                tempfecha = temp.getFechaRecepcion();
                if (fechaRecepcion != null) {
                    if (fechaRecepcion.contains("ene") || fechaRecepcion.contains("feb") || fechaRecepcion.contains("mar") || fechaRecepcion.contains("abr")
                            || fechaRecepcion.contains("may") || fechaRecepcion.contains("jun") || fechaRecepcion.contains("jul") || fechaRecepcion.contains("ago")
                            || fechaRecepcion.contains("set") || fechaRecepcion.contains("oct") || fechaRecepcion.contains("nov") || fechaRecepcion.contains("dic")) {
                        temp.setFechaRecepcion(tempfecha);
                    } else {
                        if (!fechaRecepcion.equals("")) {
                            temp.setFechaRecepcion(df.stringToDate(fechaRecepcion));
                        } else {
                            temp.setFechaRecepcion(null);
                        }
                    }
                } else {
                    temp.setFechaRecepcion(null);
                }
                /*if (fechaRecepcion != null && !fechaRecepcion.equals("")) {
                 temp.setFechaRecepcion(df.stringToDate(fechaRecepcion));
                 }
                 if (fechaRecepcion == null || fechaRecepcion.equals("")) {
                 temp.setFechaRecepcion(null);
                 }*/
                tempfecha = temp.getFechaInforme();
                if (fechaInforme != null) {
                    if (fechaInforme.contains("ene") || fechaInforme.contains("feb") || fechaInforme.contains("mar") || fechaInforme.contains("abr")
                            || fechaInforme.contains("may") || fechaInforme.contains("jun") || fechaInforme.contains("jul") || fechaInforme.contains("ago")
                            || fechaInforme.contains("set") || fechaInforme.contains("oct") || fechaInforme.contains("nov") || fechaInforme.contains("dic")) {
                        temp.setFechaInforme(tempfecha);
                    } else {
                        if (!fechaInforme.equals("")) {
                            temp.setFechaInforme(df.stringToDate(fechaInforme));
                        } else {
                            temp.setFechaInforme(null);
                        }
                    }
                } else {
                    temp.setFechaInforme(null);
                }
                /*if (fechaInforme != null && !fechaInforme.equals("")) {
                 temp.setFechaInforme(df.stringToDate(fechaInforme));
                 }
                 if (fechaInforme == null || fechaInforme.equals("")) {
                 temp.setFechaInforme(null);
                 }*/
                tempfecha = temp.getFechaActa();
                if (fechaActa != null) {
                    if (fechaActa.contains("ene") || fechaActa.contains("feb") || fechaActa.contains("mar") || fechaActa.contains("abr")
                            || fechaActa.contains("may") || fechaActa.contains("jun") || fechaActa.contains("jul") || fechaActa.contains("ago")
                            || fechaActa.contains("set") || fechaActa.contains("oct") || fechaActa.contains("nov") || fechaActa.contains("dic")) {
                        temp.setFechaActa(tempfecha);
                    } else {
                        if (!fechaActa.equals("")) {
                            temp.setFechaActa(df.stringToDate(fechaActa));
                        } else {
                            temp.setFechaActa(null);
                        }
                    }
                } else {
                    temp.setFechaActa(null);
                }
                /*if (fechaActa != null && !fechaActa.equals("")) {
                 temp.setFechaActa(df.stringToDate(fechaActa));
                 }
                 if (fechaActa == null || fechaActa.equals("")) {
                 temp.setFechaActa(null);
                 }*/
                Personal pers = ServicioPersonal.getPersonal(personal);
                temp.setPersonal(pers);
                temp.setObs(obs);
                servicioEtapa.updateInformePost(temp);

            } else {
                InformePostAdoptivo tempPost = new InformePostAdoptivo();
                tempPost.setNumeroInforme(num);
                tempPost.setEstado(estado);
                //System.out.print("crear" + fechaProyectado);
                Date tempfecha = tempPost.getFechaRecepcionProyectado();
                if (fechaProyectado != null) {
                    if (fechaProyectado.contains("ene") || fechaProyectado.contains("feb") || fechaProyectado.contains("mar") || fechaProyectado.contains("abr")
                            || fechaProyectado.contains("may") || fechaProyectado.contains("jun") || fechaProyectado.contains("jul") || fechaProyectado.contains("ago")
                            || fechaProyectado.contains("set") || fechaProyectado.contains("oct") || fechaProyectado.contains("nov") || fechaProyectado.contains("dic")) {
                        tempPost.setFechaRecepcionProyectado(tempfecha);
                    } else {
                        if (!fechaProyectado.equals("")) {
                            tempPost.setFechaRecepcionProyectado(df.stringToDate(fechaProyectado));
                        } else {
                            tempPost.setFechaRecepcionProyectado(null);
                        }
                    }
                } else {
                    tempPost.setFechaRecepcionProyectado(null);
                }
                /*if (fechaProyectado != null && !fechaProyectado.equals("")) {
                 tempPost.setFechaRecepcionProyectado(df.stringToDate(fechaProyectado));
                 }
                 if (fechaProyectado == null || fechaProyectado.equals("")) {
                 tempPost.setFechaRecepcionProyectado(null);
                 }*/
                tempfecha = tempPost.getFechaRecepcion();
                if (fechaRecepcion != null) {
                    if (fechaRecepcion.contains("ene") || fechaRecepcion.contains("feb") || fechaRecepcion.contains("mar") || fechaRecepcion.contains("abr")
                            || fechaRecepcion.contains("may") || fechaRecepcion.contains("jun") || fechaRecepcion.contains("jul") || fechaRecepcion.contains("ago")
                            || fechaRecepcion.contains("set") || fechaRecepcion.contains("oct") || fechaRecepcion.contains("nov") || fechaRecepcion.contains("dic")) {
                        tempPost.setFechaRecepcion(tempfecha);
                    } else {
                        if (!fechaRecepcion.equals("")) {
                            tempPost.setFechaRecepcion(df.stringToDate(fechaRecepcion));
                        } else {
                            tempPost.setFechaRecepcion(null);
                        }
                    }
                } else {
                    tempPost.setFechaRecepcion(null);
                }
                /*if (fechaRecepcion != null && !fechaRecepcion.equals("")) {
                 tempPost.setFechaRecepcion(df.stringToDate(fechaRecepcion));
                 }
                 if (fechaRecepcion == null || fechaRecepcion.equals("")) {
                 tempPost.setFechaRecepcion(null);
                 }*/
                tempfecha = tempPost.getFechaInforme();
                if (fechaInforme != null) {
                    if (fechaInforme.contains("ene") || fechaInforme.contains("feb") || fechaInforme.contains("mar") || fechaInforme.contains("abr")
                            || fechaInforme.contains("may") || fechaInforme.contains("jun") || fechaInforme.contains("jul") || fechaInforme.contains("ago")
                            || fechaInforme.contains("set") || fechaInforme.contains("oct") || fechaInforme.contains("nov") || fechaInforme.contains("dic")) {
                        tempPost.setFechaInforme(tempfecha);
                    } else {
                        if (!fechaInforme.equals("")) {
                            tempPost.setFechaInforme(df.stringToDate(fechaInforme));
                        } else {
                            tempPost.setFechaInforme(null);
                        }
                    }
                } else {
                    tempPost.setFechaInforme(null);
                }
                /*if (fechaInforme != null && !fechaInforme.equals("")) {
                 tempPost.setFechaInforme(df.stringToDate(fechaInforme));
                 }
                 if (fechaInforme == null || fechaInforme.equals("")) {
                 tempPost.setFechaInforme(null);
                 }*/
                tempfecha = tempPost.getFechaActa();
                if (fechaActa != null) {
                    if (fechaActa.contains("ene") || fechaActa.contains("feb") || fechaActa.contains("mar") || fechaActa.contains("abr")
                            || fechaActa.contains("may") || fechaActa.contains("jun") || fechaActa.contains("jul") || fechaActa.contains("ago")
                            || fechaActa.contains("set") || fechaActa.contains("oct") || fechaActa.contains("nov") || fechaActa.contains("dic")) {
                        tempPost.setFechaActa(tempfecha);
                    } else {
                        if (!fechaActa.equals("")) {
                            tempPost.setFechaActa(df.stringToDate(fechaActa));
                        } else {
                            tempPost.setFechaActa(null);
                        }
                    }
                } else {
                    tempPost.setFechaActa(null);
                }
                /*if (fechaActa != null && !fechaActa.equals("")) {
                 tempPost.setFechaActa(df.stringToDate(fechaActa));
                 }
                 if (fechaActa == null || fechaActa.equals("")) {
                 tempPost.setFechaActa(null);
                 }*/
                Personal pers = ServicioPersonal.getPersonal(personal);
                tempPost.setPersonal(pers);
                tempPost.setObs(obs);
                PostAdopcion tempPostAdopcion = new PostAdopcion();
                tempPostAdopcion = servicioEtapa.getPostAdopcion(idPost);
                tempPost.setPostAdopcion(tempPostAdopcion);
                servicioEtapa.crearInformePost(tempPost);

                String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                        + " con ID: " + usuario.getIdpersonal() + ". Creó informe Post Adopción.";

                String Tipo_registro = "Familia";

                try {
                    String Numero_registro = String.valueOf(usuario.getIdpersonal());

                    ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                } catch (Exception ex) {
                }
            }

            ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList<InformePostAdoptivo>(numInformes);
            ArrayList<InformePostAdoptivo> informesTabla = new ArrayList();
            informesTabla = servicioEtapa.getListaInformesPost(idPost);
            int continuacion = 0;
            if (informesTabla.isEmpty()) {
                for (int i = 0; i < numInformes; i++) {
                    InformePostAdoptivo temp = new InformePostAdoptivo();
                    allInformePostAdoptivo.add(temp);
                }
                map.put("listaInformes", allInformePostAdoptivo);
            } else {
                for (InformePostAdoptivo informePostAdoptivo : informesTabla) {
                    int temp = Integer.parseInt(informePostAdoptivo.getNumeroInforme()) - 1;
                    continuacion = temp;
                    if (temp > allInformePostAdoptivo.size()) {
                        for (int i = allInformePostAdoptivo.size(); i <= temp; i++) {
                            InformePostAdoptivo tempIP = new InformePostAdoptivo();
                            if (i == temp) {
                                allInformePostAdoptivo.add(informePostAdoptivo);
                            } else {
                                allInformePostAdoptivo.add(i, tempIP);
                            }
                        }
                    } else {
                        allInformePostAdoptivo.add(temp, informePostAdoptivo);
                    }
                }
                if (informesTabla.size() < numInformes && allInformePostAdoptivo.size() < numInformes) {
                    for (int i = continuacion + 1; i < numInformes; i++) {

                        InformePostAdoptivo temp = new InformePostAdoptivo();
                        allInformePostAdoptivo.add(i, temp);

                    }
                }
                map.put("listaInformes", allInformePostAdoptivo);
            }

            String[] parts = fechaAdopcion.split("-");
            int anho = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]);
            int dia = Integer.parseInt(parts[2]);
            String fechaEntrega;
            ArrayList<String> listaFechas = new ArrayList();
            for (int i = 0; i < numInformes; i++) {
                int contadorMes = mes + 6;
                if (contadorMes > 12) {
                    mes = contadorMes - 12;
                    anho = anho + 1;
                    switch (mes) {
                        case 1:
                            fechaEntrega = "Ene" + "/" + anho;
                            break;
                        case 2:
                            fechaEntrega = "Feb" + "/" + anho;
                            break;
                        case 3:
                            fechaEntrega = "Mar" + "/" + anho;
                            break;
                        case 4:
                            fechaEntrega = "Abr" + "/" + anho;
                            break;
                        case 5:
                            fechaEntrega = "May" + "/" + anho;
                            break;
                        case 6:
                            fechaEntrega = "Jun" + "/" + anho;
                            break;
                        case 7:
                            fechaEntrega = "Jul" + "/" + anho;
                            break;
                        case 8:
                            fechaEntrega = "Ago" + "/" + anho;
                            break;
                        case 9:
                            fechaEntrega = "Sep" + "/" + anho;
                            break;
                        case 10:
                            fechaEntrega = "Oct" + "/" + anho;
                            break;
                        case 11:
                            fechaEntrega = "Nov" + "/" + anho;
                            break;
                        default:
                            fechaEntrega = "Dec" + "/" + anho;
                    }
                    listaFechas.add(i, fechaEntrega);
                } else {
                    mes = mes + 6;
                    fechaEntrega = mes + "/" + anho;
                    switch (mes) {
                        case 1:
                            fechaEntrega = "Ene" + "/" + anho;
                            break;
                        case 2:
                            fechaEntrega = "Feb" + "/" + anho;
                            break;
                        case 3:
                            fechaEntrega = "Mar" + "/" + anho;
                            break;
                        case 4:
                            fechaEntrega = "Abr" + "/" + anho;
                            break;
                        case 5:
                            fechaEntrega = "May" + "/" + anho;
                            break;
                        case 6:
                            fechaEntrega = "Jun" + "/" + anho;
                            break;
                        case 7:
                            fechaEntrega = "Jul" + "/" + anho;
                            break;
                        case 8:
                            fechaEntrega = "Ago" + "/" + anho;
                            break;
                        case 9:
                            fechaEntrega = "Sep" + "/" + anho;
                            break;
                        case 10:
                            fechaEntrega = "Oct" + "/" + anho;
                            break;
                        case 11:
                            fechaEntrega = "Nov" + "/" + anho;
                            break;
                        default:
                            fechaEntrega = "Dec" + "/" + anho;
                    }
                    listaFechas.add(i, fechaEntrega);
                }
            }
            map.put("fechaAdopcion", fechaAdopcion);
            map.put("listaFechas", listaFechas);
            map.put("familia", familia);
            map.put("idPost", idPost);
            map.put("numInformes", numInformes);

            session.removeAttribute("idInforme");
            session.removeAttribute("num");
            session.removeAttribute("estado");
            session.removeAttribute("fechaProyectado");
            session.removeAttribute("fechaRecepcion");
            session.removeAttribute("personal");
            session.removeAttribute("fechaInforme");
            session.removeAttribute("fechaActa");
            session.removeAttribute("obs");
            session.removeAttribute("idPost");
            session.removeAttribute("familia");
            session.removeAttribute("numInformes");
            session.removeAttribute("fechaAdopcion");

            return new ModelAndView("/Personal/Buscador_etapa/etapa_post/informes_general", map);
        } else {
            return new ModelAndView("redirect:/EtapaPostAdopcion");
        }
    }

    @RequestMapping(value = "/Reevaluacion", method = RequestMethod.GET)
    public ModelAndView Reevaluacion(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("listaReevaluacion", servicioEtapa.getListaReevaluación());
        volver = "/Reevaluacion";
        map.put("volver", volver);
        return new ModelAndView("/Personal/Buscador_etapa/lista_reevaluacion", map);
    }

    @RequestMapping(value = "/ListaEspera", method = RequestMethod.GET)
    public ModelAndView ListaEspera(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        volver = "/ListaEspera";
        map.addAttribute("volver", volver);
        map.put("df", df);
        map.put("listaEspera", servicioEtapa.getListaEspera());
        return new ModelAndView("/Personal/Buscador_etapa/lista_espera", map);
    }

    @RequestMapping(value = "/RegresarListaEspera", method = RequestMethod.POST)
    public ModelAndView RegresarListaEspera_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        return new ModelAndView("redirect:/RegresarListaEspera", map);
    }

    @RequestMapping(value = "/RegresarListaEspera", method = RequestMethod.GET)
    public ModelAndView RegresarListaEspera_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null) {
            ExpedienteFamilia tempExp = new ExpedienteFamilia();
            tempExp = servicioEtapa.getExpedienteFamilia((long) session.getAttribute("idExpediente"));
            tempExp.setEstado("espera");
            servicioEtapa.updateExpedienteFamilia(tempExp);
            map.put("df", df);
            map.put("listaReevaluacion", servicioEtapa.getListaReevaluación());

            session.removeAttribute("idExpediente");

            return new ModelAndView("/Personal/Buscador_etapa/lista_reevaluacion", map);
        } else {

            return new ModelAndView("redirect:/Reevaluacion");
        }
    }

    @RequestMapping(value = "/EliminarRegistro", method = RequestMethod.POST)
    public ModelAndView EliminarRegistro_POST(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") long idExpediente
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        session.setAttribute("idExpediente", idExpediente);
        return new ModelAndView("redirect:/RegresarListaEspera", map);
    }

    @RequestMapping(value = "/EliminarRegistro", method = RequestMethod.GET)
    public ModelAndView EliminarRegistro_GET(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (session.getAttribute("idExpediente") != null) {
            ExpedienteFamilia tempExp = new ExpedienteFamilia();
            tempExp = servicioEtapa.getExpedienteFamilia((long) session.getAttribute("idExpediente"));
            tempExp.setEstado("eliminado");
            servicioEtapa.updateExpedienteFamilia(tempExp);
            String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                    + " con ID: " + usuario.getIdpersonal() + ". Eliminó registro ligado al expediente con ID"
                    + ": " + session.getAttribute("idExpediente");

            String Tipo_registro = "Personal";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            map.put("df", df);
            map.put("listaReevaluacion", servicioEtapa.getListaReevaluación());
            map.put("df", df);
            map.put("listaReevaluacion", servicioEtapa.getListaReevaluación());

            session.removeAttribute("idExpediente");

            return new ModelAndView("/Personal/Buscador_etapa/lista_reevaluacion", map);
        } else {

            return new ModelAndView("redirect:/Reevaluacion");

        }
    }

    // REGISTRAR REVISION DE EXPEDIENTE
//    @RequestMapping(value = "/agregarRevision", method = RequestMethod.POST)
//    public ModelAndView agregarRevision(ModelMap map, HttpSession session,
//            @RequestParam("idNna") long idNna
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        nnaEditarRevision = idNna;
//        nnaPrioritario = ServicioNna.getNna(idNna);
//        listaRevision.clear();
//        map.addAttribute("idNna", idNna);
//        return new ModelAndView("/Personal/nna/reg_revision", map);
//    }
//    @RequestMapping(value = "/insertarRevision", method = RequestMethod.POST)
//    public ModelAndView insertarRevision(ModelMap map, HttpSession session,
//            @RequestParam(value = "numero", required = false) String numero,
//            @RequestParam(value = "comentarios", required = false) String comentarios,
//            @RequestParam(value = "agregar", required = false) String agregar,
//            @RequestParam(value = "eliminar", required = false) String eliminar,
//            @RequestParam(value = "registrar", required = false) String registrar,
//            @RequestParam(value = "idNna", required = false) String idNna,
//            int[] delete,
//            String[] fecha
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        if (agregar != null) {
//            map.put("df", df);
//            map.addAttribute("idNna", idNna);
//            return new ModelAndView("/Personal/nna/agregar_exp_revision", map);
//        }
//        if (eliminar != null && delete != null) {
//            for (int i : delete) {
//                listaRevision.remove(i);
//            }
//
//            map.put("df", df);
//            map.put("listaRevision", listaRevision);
//            map.addAttribute("idNna", idNna);
//            return new ModelAndView("/Personal/nna/reg_revision", map);
//        }
//        if (registrar != null) {
//            for (int i = 0; i < listaRevision.size(); i++) {
//                Revision tempRev = new Revision();
//                tempRev.setNna(nnaPrioritario);
//                tempRev.setExpedienteFamilia(listaRevision.get(i));
//                tempRev.setNumero(numero);
//                tempRev.setComentarios(comentarios);
//                Date tempfecha = tempRev.getFechaRevision();
//                if (fecha[i] != null) {
//                    if (fecha[i].contains("ene") || fecha[i].contains("feb") || fecha[i].contains("mar") || fecha[i].contains("abr")
//                            || fecha[i].contains("may") || fecha[i].contains("jun") || fecha[i].contains("jul") || fecha[i].contains("ago")
//                            || fecha[i].contains("set") || fecha[i].contains("oct") || fecha[i].contains("nov") || fecha[i].contains("dic")) {
//                        tempRev.setFechaRevision(tempfecha);
//                    } else {
//                        if (!fecha[i].equals("")) {
//                            tempRev.setFechaRevision(df.stringToDate(fecha[i]));
//                        } else {
//                            tempRev.setFechaRevision(null);
//                        }
//                    }
//                } else {
//                    tempRev.setFechaRevision(null);
//                }
//                /*if (fecha[i] != null && !fecha[i].equals("")) {
//                 tempEst.setFechaEstudio(df.stringToDate(fecha[i]));
//                 }
//                 if (fecha[i] == null || fecha[i].equals("")) {
//                 tempEst.setFechaEstudio(null);
//                 }*/
//
//                servicioEtapa.crearRevision(tempRev);
//
//                String mensaje_log = "Se registró nueva familia a la revisión de expediente del NNA con ID: " + String.valueOf(nnaPrioritario.getIdnna());
//                String Tipo_registro = "Login";
//
//                try {
//                    String Numero_registro = tempRev.getNumero();
//
//                    ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
//                } catch (Exception ex) {
//                }
//
//            }
//            map.put("listaRevision", servicioEtapa.getListaRevision(nnaPrioritario.getIdnna()));
//            return new ModelAndView("/Personal/nna/lista_revision", map);
//        }
//
//        return new ModelAndView("/Personal/nna/reg_revision", map);
//    }
//    @RequestMapping(value = "/verRevision", method = RequestMethod.POST)
//    public ModelAndView verRevision(ModelMap map, HttpSession session,
//            @RequestParam("idNna") long idNna
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        nnaEditarRevision = idNna;
//        map.put("listaRevision", servicioEtapa.getListaRevision(idNna));
//        map.addAttribute("idNna", idNna);
//        return new ModelAndView("/Personal/nna/lista_revision", map);
//
//    }
//
//    @RequestMapping(value = "/buscarExpedienteRevision", method = RequestMethod.POST)
//    public ModelAndView buscarExpedienteRevision(ModelMap map, HttpSession session,
//            @RequestParam("exp") String exp
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
//        listaBusqueda = servicioEtapa.listaInfoFamilias(exp);
//
//        map.put("df", df);
//        map.put("listaRevision", listaRevision);
//        map.put("listaBusqueda", listaBusqueda);
//        return new ModelAndView("/Personal/nna/agregar_exp_revision", map);
//
//    }
//    @RequestMapping(value = "/agregarExpedienteRevision", method = RequestMethod.POST)
//    public ModelAndView agregarExpedienteRevision(ModelMap map, HttpSession session, long[] idExpediente
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//
//        if (idExpediente != null) {
//            for (long l : idExpediente) {
//                ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
//                listaRevision.add(tempExp);
//            }
//        }
//        map.put("listaRevision", listaRevision);
//        map.put("df", df);
//        return new ModelAndView("/Personal/nna/reg_revision", map);
//
//    }
//    @RequestMapping(value = "/EditarRevision", method = RequestMethod.POST)
//    public ModelAndView EditarRevision(ModelMap map, HttpSession session,
//            @RequestParam("numero") String numero,
//            @RequestParam(value = "idNna", required = false) String idNna
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        map.put("df", df);
//        map.put("nna", nnaEditarEstudioCaso);
//        map.put("listaRevision", servicioEtapa.getListaRevisionNumero(numero));
//        map.addAttribute("idNna", idNna);
//        map.addAttribute("numero", numero);
//        return new ModelAndView("/Personal/nna/edit_revision", map);
//
//    }
//    @RequestMapping(value = "/GuardarRevision", method = RequestMethod.POST)
//    public ModelAndView GuardarRevision(ModelMap map, HttpSession session,
//            @RequestParam("numero") String numero,
//            @RequestParam("coments") String coments,
//            @RequestParam("idNna") long idNna
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//
//        servicioEtapa.updateRevision(numero, coments);
//        nnaEditarRevision = idNna;
//        map.put("listaRevision", servicioEtapa.getListaRevision(idNna));
//        return new ModelAndView("/Personal/nna/lista_revision", map);
//
//    }
//    @RequestMapping(value = "/PersonalRevisionEstudio", method = RequestMethod.POST)
//    public ModelAndView PersonalRevisionEstudio(ModelMap map, HttpSession session,
//            @RequestParam("idNna") long idNna,
//            @RequestParam(value = "numero", required = false) String numero,
//            long[] idExpediente
//    ) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        nnaEditarEstudioCaso = idNna;
//        nnaPrioritario = ServicioNna.getNna(idNna);
//        listaEstudioCaso.clear();
//
//        if (idExpediente != null) {
//            for (long l : idExpediente) {
//                ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
//                listaEstudioCaso.add(tempExp);
//            }
//        }
//        map.put("listaEstudioCaso", listaEstudioCaso);
//        map.put("df", df);
//
//        map.addAttribute("idNna", idNna);
//        map.addAttribute("numero", numero);
//        return new ModelAndView("/Personal/nna/reg_estudio", map);
//    }
    @RequestMapping(value = "/esperaInter", method = RequestMethod.GET)
    public ModelAndView esperaInter(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
//        volver = "/ListaEspera";
//        map.addAttribute("volver", volver);
        map.put("df", df);
        map.put("listaEspera", servicioEtapa.getListaEsperaAdopcionInter());
        return new ModelAndView("/Personal/Buscador_etapa/lista_espera_inter", map);
    }

}
