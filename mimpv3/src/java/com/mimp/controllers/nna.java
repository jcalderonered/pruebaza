/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.HiberEtapa;
import com.mimp.util.*;
import com.mimp.hibernate.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
public class nna {

    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();
    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name = "HiberNna")
    private HiberNna ServicioNna = new HiberNna();
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();

    @RequestMapping(value = "/nna", method = RequestMethod.GET)
    public ModelAndView Nna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("listaNna", ServicioNna.ListaNna("regular"));
        return new ModelAndView("/Personal/nna/lista_nna", map);
    }

    @RequestMapping(value = "/nnaPrioritarios", method = RequestMethod.GET)
    public ModelAndView nnaPrioritarios(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("listaNna", ServicioNna.ListaNna("prioritario"));
        return new ModelAndView("/Personal/nna/lista_nna_prior", map);
    }

    @RequestMapping(value = "/nnaSeguimiento", method = RequestMethod.GET)
    public ModelAndView nnaSeguimiento(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaNna", ServicioNna.ListaNna("seguimiento"));
        return new ModelAndView("/Personal/nna/lista_nna_seg", map);
    }

    @RequestMapping(value = "/agregarNna", method = RequestMethod.GET)
    public ModelAndView agregarNna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

    @RequestMapping(value = "/editarNna", method = RequestMethod.POST)
    public ModelAndView editarNna(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        tempNna = ServicioNna.getNna(idNna);

        map.addAttribute("fechainv", "");
        map.addAttribute("fechaing", "");
        map.addAttribute("fechaest", "");
        map.put("nna", tempNna);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

    @RequestMapping(value = "/crearNna", method = RequestMethod.POST)
    public ModelAndView crearNna(ModelMap map, HttpSession session,
            @RequestParam(value = "nn", required = false) String nn,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidoP", required = false) String apellidoP,
            @RequestParam(value = "apellidoM", required = false) String apellidoM,
            @RequestParam(value = "sexo", required = false) String sexo,
            @RequestParam(value = "fechaNac", required = false) String fechaNac,
            @RequestParam(value = "edad", required = false) String edad,
            @RequestParam(value = "meses", required = false) String meses,
            @RequestParam(value = "idJuzgado", required = false) long idJuzgado,
            @RequestParam(value = "idCar", required = false) long idCar,
            @RequestParam(value = "actaNac", required = false) String actaNac,
            @RequestParam(value = "dep", required = false) String dep,
            @RequestParam(value = "prov", required = false) String prov,
            @RequestParam(value = "dist", required = false) String dist,
            @RequestParam(value = "direccion", required = false) String direccion,
            @RequestParam(value = "numResolAband", required = false) String numResolAband,
            @RequestParam(value = "fechaResolAband", required = false) String fechaResolAband,
            @RequestParam(value = "numResolConsen", required = false) String numResolConsen,
            @RequestParam(value = "fechaResolConsen", required = false) String fechaResolConsen,
            @RequestParam(value = "incesto", required = false) String incesto,
            @RequestParam(value = "psiquiatrica", required = false) String psiquiatrica,
            @RequestParam(value = "epilepsia", required = false) String epilepsia,
            @RequestParam(value = "abuso", required = false) String abuso,
            @RequestParam(value = "sano", required = false) String sifilis,
            @RequestParam(value = "estable", required = false) String estable,
            @RequestParam(value = "intervencion", required = false) String intervencion,
            @RequestParam(value = "trastorno", required = false) String trastorno,
            @RequestParam(value = "prioritarios", required = false) String prioritarios,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        short tempNn;
        if (nn == null) {
            tempNn = Byte.valueOf("1");
        } else {
            tempNn = Byte.valueOf("0");
        }
        tempNna.setNn(tempNn);

        tempNna.setNombre(nombre);
        tempNna.setApellidoP(apellidoP);
        tempNna.setApellidoM(apellidoM);
        tempNna.setSexo(sexo);
        Date tempfecha = tempNna.getFechaNacimiento();
        if (fechaNac != null) {
            if (fechaNac.contains("ene") || fechaNac.contains("feb") || fechaNac.contains("mar") || fechaNac.contains("abr")
                    || fechaNac.contains("may") || fechaNac.contains("jun") || fechaNac.contains("jul") || fechaNac.contains("ago")
                    || fechaNac.contains("set") || fechaNac.contains("oct") || fechaNac.contains("nov") || fechaNac.contains("dic")) {
                tempNna.setFechaNacimiento(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    tempNna.setFechaNacimiento(df.stringToDate(fechaNac));
                } else {
                    tempNna.setFechaNacimiento(null);
                }
            }
        } else {
            tempNna.setFechaNacimiento(null);
        }
        short edadtemp = Byte.valueOf(edad);
        short mesestemp = Byte.valueOf(meses);
        tempNna.setEdadAnhos(edadtemp);
        tempNna.setEdadMeses(mesestemp);

        tempNna.setJuzgado(ServicioPersonal.getJuzgado(idJuzgado));
        tempNna.setCar(ServicioPersonal.getCar(idCar));

        short acta;
        if (actaNac.equals("s")) {
            acta = Byte.valueOf("0");
        } else {
            acta = Byte.valueOf("1");
        }
        tempNna.setActaNacimiento(acta);

        tempNna.setDepartamentoNacimiento(dep);
        tempNna.setProvinciaNacimiento(prov);
        tempNna.setDistritoNacimiento(dist);
        tempNna.setDistritoNacimiento(direccion);
        tempNna.setNResolAband(numResolAband);
        tempfecha = tempNna.getFechaResolAbandono();
        if (fechaResolAband != null) {
            if (fechaResolAband.contains("ene") || fechaResolAband.contains("feb") || fechaResolAband.contains("mar") || fechaResolAband.contains("abr")
                    || fechaResolAband.contains("may") || fechaResolAband.contains("jun") || fechaResolAband.contains("jul") || fechaResolAband.contains("ago")
                    || fechaResolAband.contains("set") || fechaResolAband.contains("oct") || fechaResolAband.contains("nov") || fechaResolAband.contains("dic")) {
                tempNna.setFechaResolAbandono(tempfecha);
            } else {
                if (!fechaResolAband.equals("")) {
                    tempNna.setFechaResolAbandono(df.stringToDate(fechaResolAband));
                } else {
                    tempNna.setFechaResolAbandono(null);
                }
            }
        } else {
            tempNna.setFechaResolAbandono(null);
        }
        tempNna.setNResolCons(numResolConsen);
        tempfecha = tempNna.getFechaResolConsentida();
        if (fechaResolConsen != null) {
            if (fechaResolConsen.contains("ene") || fechaResolConsen.contains("feb") || fechaResolConsen.contains("mar") || fechaResolConsen.contains("abr")
                    || fechaResolConsen.contains("may") || fechaResolConsen.contains("jun") || fechaResolConsen.contains("jul") || fechaResolConsen.contains("ago")
                    || fechaResolConsen.contains("set") || fechaResolConsen.contains("oct") || fechaResolConsen.contains("nov") || fechaResolConsen.contains("dic")) {
                tempNna.setFechaResolConsentida(tempfecha);
            } else {
                if (!fechaResolConsen.equals("")) {
                    tempNna.setFechaResolConsentida(df.stringToDate(fechaResolConsen));
                } else {
                    tempNna.setFechaResolConsentida(null);
                }
            }
        } else {
            tempNna.setFechaResolAbandono(null);
        }
        if (incesto == null) {
            incesto = "1";
        }
        short shIncesto = Byte.valueOf(incesto);
        if (psiquiatrica == null) {
            psiquiatrica = "1";
        }
        short shPsiquiatrica = Byte.valueOf(psiquiatrica);
        if (epilepsia == null) {
            epilepsia = "1";
        }
        short shEpilepsia = Byte.valueOf(epilepsia);
        if (abuso == null) {
            abuso = "1";
        }
        short shAbuso = Byte.valueOf(abuso);
        if (sifilis == null) {
            sifilis = "1";
        }
        short shSifilis = Byte.valueOf(sifilis);
        if (estable == null) {
            estable = "1";
        }
        short shEstable = Byte.valueOf(estable);
        if (intervencion == null) {
            intervencion = "1";
        }
        short shIntervencion = Byte.valueOf(intervencion);
        if (trastorno == null) {
            trastorno = "1";
        }
        short shTrastorno = Byte.valueOf(trastorno);

        tempNna.setIncesto(shIncesto);
        tempNna.setMental(shPsiquiatrica);
        //tempNna.setEpilepsia(shEpilepsia);
        tempNna.setAbuso(shAbuso);
        tempNna.setSeguiMedico(shEstable);
        tempNna.setOperacion(shIntervencion);
        tempNna.setHiperactivo(shTrastorno);

        short especial = Byte.valueOf("1");
        short enfermo = Byte.valueOf("1");
        short mayores = Byte.valueOf("1");
        short adolescentes = Byte.valueOf("1");
        short hermanos = Byte.valueOf("1");

        if (prioritarios != null && prioritarios.equals("e")) {
            especial = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("s")) {
            enfermo = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("m")) {
            mayores = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("a")) {
            adolescentes = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("h")) {
            hermanos = Byte.valueOf("0");
        }

        tempNna.setEspecial(especial);
        tempNna.setEnfermo(enfermo);
        tempNna.setMayor(mayores);
        tempNna.setAdolescente(adolescentes);
        tempNna.setHermano(hermanos);

        if (prioritarios != null) {
            tempNna.setClasificacion("prioritario");
        } else {
            tempNna.setClasificacion("regular");
        }
        tempNna.setObservaciones(obs);

        ServicioNna.crearNna(tempNna);

        if (prioritarios != null) {
            map.put("listaNna", ServicioNna.ListaNna("prioritario"));
            return new ModelAndView("/Personal/nna/lista_nna_prior", map);
        } else {
            map.put("listaNna", ServicioNna.ListaNna("regular"));
            return new ModelAndView("/Personal/nna/lista_nna", map);
        }

    }

    @RequestMapping(value = "/updateNna", method = RequestMethod.POST)
    public ModelAndView updateNna(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna,
            @RequestParam(value = "nn", required = false) String nn,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidoP", required = false) String apellidoP,
            @RequestParam(value = "apellidoM", required = false) String apellidoM,
            @RequestParam(value = "sexo", required = false) String sexo,
            @RequestParam(value = "fechaNac", required = false) String fechaNac,
            @RequestParam(value = "edad", required = false) String edad,
            @RequestParam(value = "meses", required = false) String meses,
            @RequestParam(value = "idJuzgado", required = false) long idJuzgado,
            @RequestParam(value = "idCar", required = false) long idCar,
            @RequestParam(value = "actaNac", required = false) String actaNac,
            @RequestParam(value = "dep", required = false) String dep,
            @RequestParam(value = "prov", required = false) String prov,
            @RequestParam(value = "dist", required = false) String dist,
            @RequestParam(value = "direccion", required = false) String direccion,
            @RequestParam(value = "numResolAband", required = false) String numResolAband,
            @RequestParam(value = "fechaResolAband", required = false) String fechaResolAband,
            @RequestParam(value = "numResolConsen", required = false) String numResolConsen,
            @RequestParam(value = "fechaResolConsen", required = false) String fechaResolConsen,
            @RequestParam(value = "incesto", required = false) String incesto,
            @RequestParam(value = "psiquiatrica", required = false) String psiquiatrica,
            @RequestParam(value = "epilepsia", required = false) String epilepsia,
            @RequestParam(value = "abuso", required = false) String abuso,
            @RequestParam(value = "sifilis", required = false) String sifilis,
            @RequestParam(value = "estable", required = false) String estable,
            @RequestParam(value = "intervencion", required = false) String intervencion,
            @RequestParam(value = "trastorno", required = false) String trastorno,
            @RequestParam(value = "prioritarios", required = false) String prioritarios,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        tempNna = ServicioNna.getNna(idNna);
        short tempNn;
        if (nn == null) {
            tempNn = Byte.valueOf("1");
        } else {
            tempNn = Byte.valueOf("0");
        }
        tempNna.setNn(tempNn);

        tempNna.setNombre(nombre);
        tempNna.setApellidoP(apellidoP);
        tempNna.setApellidoM(apellidoM);
        tempNna.setSexo(sexo);
        Date tempfecha = tempNna.getFechaNacimiento();
        if (fechaNac != null) {
            if (fechaNac.contains("ene") || fechaNac.contains("feb") || fechaNac.contains("mar") || fechaNac.contains("abr")
                    || fechaNac.contains("may") || fechaNac.contains("jun") || fechaNac.contains("jul") || fechaNac.contains("ago")
                    || fechaNac.contains("set") || fechaNac.contains("oct") || fechaNac.contains("nov") || fechaNac.contains("dic")) {
                tempNna.setFechaNacimiento(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    tempNna.setFechaNacimiento(df.stringToDate(fechaNac));
                } else {
                    tempNna.setFechaNacimiento(null);
                }
            }
        } else {
            tempNna.setFechaNacimiento(null);
        }
        short edadtemp = Byte.valueOf(edad);
        short mesestemp = Byte.valueOf(meses);
        tempNna.setEdadAnhos(edadtemp);
        tempNna.setEdadMeses(mesestemp);

        tempNna.setJuzgado(ServicioPersonal.getJuzgado(idJuzgado));
        tempNna.setCar(ServicioPersonal.getCar(idCar));

        short acta;
        if (actaNac.equals("s")) {
            acta = Byte.valueOf("0");
        } else {
            acta = Byte.valueOf("1");
        }
        tempNna.setActaNacimiento(acta);

        tempNna.setDepartamentoNacimiento(dep);
        tempNna.setProvinciaNacimiento(prov);
        tempNna.setDistritoNacimiento(dist);
        tempNna.setDistritoNacimiento(direccion);
        tempNna.setNResolAband(numResolAband);
        tempfecha = tempNna.getFechaResolAbandono();
        if (fechaResolAband != null) {
            if (fechaResolAband.contains("ene") || fechaResolAband.contains("feb") || fechaResolAband.contains("mar") || fechaResolAband.contains("abr")
                    || fechaResolAband.contains("may") || fechaResolAband.contains("jun") || fechaResolAband.contains("jul") || fechaResolAband.contains("ago")
                    || fechaResolAband.contains("set") || fechaResolAband.contains("oct") || fechaResolAband.contains("nov") || fechaResolAband.contains("dic")) {
                tempNna.setFechaResolAbandono(tempfecha);
            } else {
                if (!fechaResolAband.equals("")) {
                    tempNna.setFechaResolAbandono(df.stringToDate(fechaResolAband));
                } else {
                    tempNna.setFechaResolAbandono(null);
                }
            }
        } else {
            tempNna.setFechaResolAbandono(null);
        }
        tempNna.setNResolCons(numResolConsen);
        tempfecha = tempNna.getFechaResolConsentida();
        if (fechaResolConsen != null) {
            if (fechaResolConsen.contains("ene") || fechaResolConsen.contains("feb") || fechaResolConsen.contains("mar") || fechaResolConsen.contains("abr")
                    || fechaResolConsen.contains("may") || fechaResolConsen.contains("jun") || fechaResolConsen.contains("jul") || fechaResolConsen.contains("ago")
                    || fechaResolConsen.contains("set") || fechaResolConsen.contains("oct") || fechaResolConsen.contains("nov") || fechaResolConsen.contains("dic")) {
                tempNna.setFechaResolConsentida(tempfecha);
            } else {
                if (!fechaResolConsen.equals("")) {
                    tempNna.setFechaResolConsentida(df.stringToDate(fechaResolConsen));
                } else {
                    tempNna.setFechaResolConsentida(null);
                }
            }
        } else {
            tempNna.setFechaResolConsentida(null);
        }

        short shIncesto = Byte.valueOf(incesto);
        short shPsiquiatrica = Byte.valueOf(psiquiatrica);
        short shEpilepsia = Byte.valueOf(epilepsia);
        short shAbuso = Byte.valueOf(abuso);
        short shSifilis = Byte.valueOf(sifilis);
        short shEstable = Byte.valueOf(estable);
        short shIntervencion = Byte.valueOf(intervencion);
        short shTrastorno = Byte.valueOf(trastorno);

        tempNna.setIncesto(shIncesto);
        tempNna.setMental(shPsiquiatrica);
        tempNna.setEpilepsia(shEpilepsia);
        tempNna.setAbuso(shAbuso);
        tempNna.setSifilis(shSifilis);
        tempNna.setSeguiMedico(shEstable);
        tempNna.setOperacion(shIntervencion);
        tempNna.setHiperactivo(shTrastorno);

        short especial = Byte.valueOf("1");
        short enfermo = Byte.valueOf("1");
        short mayores = Byte.valueOf("1");
        short adolescentes = Byte.valueOf("1");
        short hermanos = Byte.valueOf("1");

        if (prioritarios != null && prioritarios.equals("e")) {
            especial = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("s")) {
            enfermo = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("m")) {
            mayores = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("a")) {
            adolescentes = Byte.valueOf("0");
        } else if (prioritarios != null && prioritarios.equals("h")) {
            hermanos = Byte.valueOf("0");
        }

        tempNna.setEspecial(especial);
        tempNna.setEnfermo(enfermo);
        tempNna.setMayor(mayores);
        tempNna.setAdolescente(adolescentes);
        tempNna.setHermano(hermanos);

        if (prioritarios != null) {
            tempNna.setClasificacion("prioritario");
        }
        tempNna.setObservaciones(obs);

        ServicioNna.updateNna(tempNna);

        if (tempNna.getClasificacion().equals("prioritario")) {
            map.put("listaNna", ServicioNna.ListaNna("prioritario"));
            return new ModelAndView("/Personal/nna/lista_nna_prior", map);
        } else if (tempNna.getClasificacion().equals("regular")) {
            map.put("listaNna", ServicioNna.ListaNna("regular"));
            return new ModelAndView("/Personal/nna/lista_nna", map);
        } else {
            map.put("listaNna", ServicioNna.ListaNna("seguimiento"));
            return new ModelAndView("/Personal/nna/lista_nna_seg", map);
        }

    }

    @RequestMapping(value = "/agregarExpedienteNna", method = RequestMethod.POST)
    public ModelAndView agregarExpedienteNna(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("nna", ServicioNna.getNna(idNna));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        return new ModelAndView("/Personal/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/editarExpedienteNna", method = RequestMethod.POST)
    public ModelAndView editarExpedienteNna(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ExpedienteNna tempExp = new ExpedienteNna();
        tempExp = ServicioNna.getExpNna(idNna);
        map.addAttribute("fechainv", df.dateToString(tempExp.getFechaInvTutelar()));
        map.addAttribute("fechaing", df.dateToString(tempExp.getFechaIngreso()));
        map.addAttribute("fechaest", df.dateToString(tempExp.getFechaEstado()));
        map.put("expediente", tempExp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        return new ModelAndView("/Personal/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/crearExpedienteNna", method = RequestMethod.POST)
    public ModelAndView crearExpedienteNna(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna,
            @RequestParam(value = "numero", required = false) String numero,
            @RequestParam(value = "nombreActual", required = false) String nombreActual,
            @RequestParam(value = "apellidoPActual", required = false) String apellidoPActual,
            @RequestParam(value = "apellidoMActual", required = false) String apellidoMActual,
            @RequestParam(value = "fechaIngreso", required = false) String fechaIngreso,
            @RequestParam(value = "ht", required = false) String ht,
            @RequestParam(value = "nInvTutelar", required = false) String nInvTutelar,
            @RequestParam(value = "fechaInvTutelar", required = false) String fechaInvTutelar,
            @RequestParam(value = "procTutelar", required = false) String procTutelar,
            @RequestParam(value = "fichaInt", required = false) String fichaInt,
            @RequestParam(value = "respLegal", required = false) String respLegal,
            @RequestParam(value = "respPsico", required = false) String respPsico,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "fechaEstado", required = false) String fechaEstado,
            @RequestParam(value = "clasificacion", required = false) String clasificacion,
            @RequestParam(value = "grpRef", required = false) String grpRef,
            @RequestParam(value = "codMayor", required = false) String codMayor,
            @RequestParam(value = "codAdoles", required = false) String codAdoles,
            @RequestParam(value = "codHermano", required = false) String codHermano,
            @RequestParam(value = "codSalud", required = false) String codSalud,
            @RequestParam(value = "codEspeciales", required = false) String codEspeciales,
            @RequestParam(value = "diagnostico", required = false) String diagnostico,
            @RequestParam(value = "comentario", required = false) String comentario) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        ExpedienteNna tempExp = new ExpedienteNna();
        tempNna = ServicioNna.getNna(idNna);
        tempExp.setUnidad(usuario.getUnidad());
        tempExp.setNumero(numero);
        tempExp.setNActual(nombreActual);
        tempExp.setApellidopActual(apellidoPActual);
        tempExp.setApellidomActual(apellidoMActual);
        Date tempfecha = tempExp.getFechaIngreso();
        if (fechaIngreso != null) {
            if (fechaIngreso.contains("ene") || fechaIngreso.contains("feb") || fechaIngreso.contains("mar") || fechaIngreso.contains("abr")
                    || fechaIngreso.contains("may") || fechaIngreso.contains("jun") || fechaIngreso.contains("jul") || fechaIngreso.contains("ago")
                    || fechaIngreso.contains("set") || fechaIngreso.contains("oct") || fechaIngreso.contains("nov") || fechaIngreso.contains("dic")) {
                tempExp.setFechaIngreso(tempfecha);
            } else {
                if (!fechaIngreso.equals("")) {
                    tempExp.setFechaIngreso(df.stringToDate(fechaIngreso));
                } else {
                    tempExp.setFechaIngreso(null);
                }
            }
        } else {
            tempExp.setFechaIngreso(null);
        }
        tempExp.setHt(ht);
        tempExp.setNExpTutelar(nInvTutelar);
        tempfecha = tempExp.getFechaInvTutelar();
        if (fechaInvTutelar != null) {
            if (fechaInvTutelar.contains("ene") || fechaInvTutelar.contains("feb") || fechaInvTutelar.contains("mar") || fechaInvTutelar.contains("abr")
                    || fechaInvTutelar.contains("may") || fechaInvTutelar.contains("jun") || fechaInvTutelar.contains("jul") || fechaInvTutelar.contains("ago")
                    || fechaInvTutelar.contains("set") || fechaInvTutelar.contains("oct") || fechaInvTutelar.contains("nov") || fechaInvTutelar.contains("dic")) {
                tempExp.setFechaInvTutelar(tempfecha);
            } else {
                if (!fechaInvTutelar.equals("")) {
                    tempExp.setFechaInvTutelar(df.stringToDate(fechaInvTutelar));
                } else {
                    tempExp.setFechaInvTutelar(null);
                }
            }
        } else {
            tempExp.setFechaInvTutelar(null);
        }
        tempExp.setProcTutelar(procTutelar);
        if (fichaInt == null) {
            fichaInt = "2";
        }
        tempExp.setFichaIntegral(Short.parseShort(fichaInt));
        tempExp.setRespLegalNombre(respLegal);
        tempExp.setRespPsicosocialNombre(respPsico);
        tempExp.setEstado(estado);
        tempfecha = tempExp.getFechaEstado();
        if (fechaEstado != null) {
            if (fechaEstado.contains("ene") || fechaEstado.contains("feb") || fechaEstado.contains("mar") || fechaEstado.contains("abr")
                    || fechaEstado.contains("may") || fechaEstado.contains("jun") || fechaEstado.contains("jul") || fechaEstado.contains("ago")
                    || fechaEstado.contains("set") || fechaEstado.contains("oct") || fechaEstado.contains("nov") || fechaEstado.contains("dic")) {
                tempExp.setFechaEstado(tempfecha);
            } else {
                if (!fechaEstado.equals("")) {
                    tempExp.setFechaEstado(df.stringToDate(fechaEstado));
                } else {
                    tempExp.setFechaEstado(null);
                }
            }
        } else {
            tempExp.setFechaEstado(null);
        }
        tempNna.setClasificacion(clasificacion);
        tempExp.setDiagnostico(diagnostico);
        tempExp.setComentarios(comentario);

        if (clasificacion != null && clasificacion.equals("prioritario")) {
            if (codMayor != null) {
                tempExp.setCodigoReferencia(codMayor);
            }
            if (codAdoles != null) {
                tempExp.setCodigoReferencia(codAdoles);
            }
            if (codHermano != null) {
                tempExp.setCodigoReferencia(codHermano);
            }
            if (codSalud != null) {
                tempExp.setCodigoReferencia(codSalud);
            }
            if (codEspeciales != null) {
                tempExp.setCodigoReferencia(codEspeciales);
            }
        }

        if (clasificacion != null && (clasificacion.equals("regular") || clasificacion.equals("seguimiento"))) {
            tempNna.setEspecial(Short.parseShort("1"));
            tempNna.setMayor(Short.parseShort("1"));
            tempNna.setHermano(Short.parseShort("1"));
            tempNna.setAdolescente(Short.parseShort("1"));
            tempNna.setEnfermo(Short.parseShort("1"));
        }
        ServicioNna.updateNna(tempNna);
        tempExp.setNna(tempNna);
        ServicioNna.crearExpNna(tempExp);

        map.addAttribute("fechainv", df.dateToString(tempExp.getFechaInvTutelar()));
        map.addAttribute("fechaing", df.dateToString(tempExp.getFechaIngreso()));
        map.addAttribute("fechaest", df.dateToString(tempExp.getFechaEstado()));
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

    @RequestMapping(value = "/updateExpedienteNna", method = RequestMethod.POST)
    public ModelAndView updateExpedienteNna(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna,
            @RequestParam(value = "numero", required = false) String numero,
            @RequestParam(value = "nombreActual", required = false) String nombreActual,
            @RequestParam(value = "apellidoPActual", required = false) String apellidoPActual,
            @RequestParam(value = "apellidoMActual", required = false) String apellidoMActual,
            @RequestParam(value = "fechaIngreso", required = false) String fechaIngreso,
            @RequestParam(value = "ht", required = false) String ht,
            @RequestParam(value = "nInvTutelar", required = false) String nInvTutelar,
            @RequestParam(value = "fechaInvTutelar", required = false) String fechaInvTutelar,
            @RequestParam(value = "procTutelar", required = false) String procTutelar,
            @RequestParam(value = "respLegal", required = false) String respLegal,
            @RequestParam(value = "respPsico", required = false) String respPsico,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "fechaEstado", required = false) String fechaEstado,
            @RequestParam(value = "clasificacion", required = false) String clasificacion,
            @RequestParam(value = "grpRef", required = false) String grpRef,
            @RequestParam(value = "codMayor", required = false) String codMayor,
            @RequestParam(value = "codAdoles", required = false) String codAdoles,
            @RequestParam(value = "codHermano", required = false) String codHermano,
            @RequestParam(value = "codSalud", required = false) String codSalud,
            @RequestParam(value = "codEspeciales", required = false) String codEspeciales,
            @RequestParam(value = "diagnostico", required = false) String diagnostico,
            @RequestParam(value = "comentario", required = false) String comentario) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        ExpedienteNna tempExp = new ExpedienteNna();
        tempExp = ServicioNna.getExpNna(idNna);
        tempNna = ServicioNna.getNna(idNna);

        tempExp.setNumero(numero);
        tempExp.setNActual(estado);
        tempExp.setApellidopActual(apellidoPActual);
        tempExp.setApellidomActual(apellidoMActual);
        Date tempfecha = tempExp.getFechaIngreso();
        if (fechaIngreso != null) {
            if (fechaIngreso.contains("ene") || fechaIngreso.contains("feb") || fechaIngreso.contains("mar") || fechaIngreso.contains("abr")
                    || fechaIngreso.contains("may") || fechaIngreso.contains("jun") || fechaIngreso.contains("jul") || fechaIngreso.contains("ago")
                    || fechaIngreso.contains("set") || fechaIngreso.contains("oct") || fechaIngreso.contains("nov") || fechaIngreso.contains("dic")) {
                tempExp.setFechaIngreso(tempfecha);
            } else {
                if (!fechaIngreso.equals("")) {
                    tempExp.setFechaIngreso(df.stringToDate(fechaIngreso));
                } else {
                    tempExp.setFechaIngreso(null);
                }
            }
        } else {
            tempExp.setFechaIngreso(null);
        }
        tempExp.setHt(ht);
        tempExp.setNExpTutelar(nInvTutelar);
        tempfecha = tempExp.getFechaInvTutelar();
        if (fechaInvTutelar != null) {
            if (fechaInvTutelar.contains("ene") || fechaInvTutelar.contains("feb") || fechaInvTutelar.contains("mar") || fechaInvTutelar.contains("abr")
                    || fechaInvTutelar.contains("may") || fechaInvTutelar.contains("jun") || fechaInvTutelar.contains("jul") || fechaInvTutelar.contains("ago")
                    || fechaInvTutelar.contains("set") || fechaInvTutelar.contains("oct") || fechaInvTutelar.contains("nov") || fechaInvTutelar.contains("dic")) {
                tempExp.setFechaInvTutelar(tempfecha);
            } else {
                if (!fechaInvTutelar.equals("")) {
                    tempExp.setFechaInvTutelar(df.stringToDate(fechaInvTutelar));
                } else {
                    tempExp.setFechaInvTutelar(null);
                }
            }
        } else {
            tempExp.setFechaInvTutelar(null);
        }
        tempExp.setProcTutelar(procTutelar);
        tempExp.setRespLegalNombre(respLegal);
        tempExp.setRespPsicosocialNombre(respPsico);
        tempExp.setEstado(estado);
        tempfecha = tempExp.getFechaEstado();
        if (fechaEstado != null) {
            if (fechaEstado.contains("ene") || fechaEstado.contains("feb") || fechaEstado.contains("mar") || fechaEstado.contains("abr")
                    || fechaEstado.contains("may") || fechaEstado.contains("jun") || fechaEstado.contains("jul") || fechaEstado.contains("ago")
                    || fechaEstado.contains("set") || fechaEstado.contains("oct") || fechaEstado.contains("nov") || fechaEstado.contains("dic")) {
                tempExp.setFechaEstado(tempfecha);
            } else {
                if (!fechaInvTutelar.equals("")) {
                    tempExp.setFechaEstado(df.stringToDate(fechaEstado));
                } else {
                    tempExp.setFechaEstado(null);
                }
            }
        } else {
            tempExp.setFechaEstado(null);
        }
        tempNna.setClasificacion(clasificacion);
        tempExp.setDiagnostico(diagnostico);
        tempExp.setComentarios(comentario);

        if (clasificacion != null && clasificacion.equals("prioritario")) {
            if (codMayor != null) {
                tempExp.setCodigoReferencia(codMayor);
            }
            if (codAdoles != null) {
                tempExp.setCodigoReferencia(codAdoles);
            }
            if (codHermano != null) {
                tempExp.setCodigoReferencia(codHermano);
            }
            if (codSalud != null) {
                tempExp.setCodigoReferencia(codSalud);
            }
            if (codEspeciales != null) {
                tempExp.setCodigoReferencia(codEspeciales);
            }
        }

        if (clasificacion != null && (clasificacion.equals("regular") || clasificacion.equals("seguimiento"))) {
            tempNna.setEspecial(Short.parseShort("1"));
            tempNna.setMayor(Short.parseShort("1"));
            tempNna.setHermano(Short.parseShort("1"));
            tempNna.setAdolescente(Short.parseShort("1"));
            tempNna.setEnfermo(Short.parseShort("1"));
        }
        ServicioNna.updateNna(tempNna);
        ServicioNna.updateExpNna(tempExp);

        map.addAttribute("fechainv", df.dateToString(tempExp.getFechaInvTutelar()));
        map.addAttribute("fechaing", df.dateToString(tempExp.getFechaIngreso()));
        map.addAttribute("fechaest", df.dateToString(tempExp.getFechaEstado()));
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

}
