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
        map.put("listaNna", ServicioNna.ListaNnaPrioritarios("prioritario"));
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

        map.put("listaNna", ServicioNna.ListaNnaSeguimiento("seguimiento"));
        return new ModelAndView("/Personal/nna/lista_nna_seg", map);
    }

    @RequestMapping(value = "/nnaCol", method = RequestMethod.GET)
    public ModelAndView nnaColocacion(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaNna", ServicioNna.ListaNna("colocacion"));
        return new ModelAndView("/Personal/nna/lista_nna_col", map);
    }

    @RequestMapping(value = "/agregarNna", method = RequestMethod.GET)
    public ModelAndView agregarNna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.addAttribute("fechaNac", "");
        map.addAttribute("fechaResolAband", "");
        map.addAttribute("fechaResolConsen", "");

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

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";

        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("nna", tempNna);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

    @RequestMapping(value = "/editarNna2", method = RequestMethod.POST)
    public ModelAndView editarNna2(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        tempNna = ServicioNna.getNna(idNna);

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";

        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("nna", tempNna);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/Buscador/nna/editar_nna", map);
    }

    @RequestMapping(value = "/editarNna3", method = RequestMethod.POST)
    public ModelAndView editarNna3(ModelMap map, HttpSession session,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        tempNna = ServicioNna.getNna(idNna);

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";

        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("nna", tempNna);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.addAttribute("volver", volver);
        String volver2 = "/editarNna3";
        map.addAttribute("volver", volver);
        map.addAttribute("volver2", volver2);
        map.addAttribute("idNna", idNna);
        return new ModelAndView("/Personal/Buscador_etapa/nna/editar_nna", map);
    }

    @RequestMapping(value = "/editarNna4", method = RequestMethod.POST)
    public ModelAndView editarNna4(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        tempNna = ServicioNna.getNna(idNna);

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";

        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("nna", tempNna);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.addAttribute("idNna", idNna);
        return new ModelAndView("/Personal/nna/editar_nna_1", map);
    }

    //PROBAR
    @RequestMapping(value = "/crearNna", method = RequestMethod.POST)
    public ModelAndView crearNna_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("nn", nn);
        session.setAttribute("nombre", nombre);
        session.setAttribute("apellidoP", apellidoP);
        session.setAttribute("apellidoM", apellidoM);
        session.setAttribute("sexo", sexo);
        session.setAttribute("fechaNac", fechaNac);
        session.setAttribute("edad", edad);
        session.setAttribute("meses", meses);
        session.setAttribute("idJuzgado", idJuzgado);
        session.setAttribute("idCar", idCar);
        session.setAttribute("actaNac", actaNac);
        session.setAttribute("dep", dep);
        session.setAttribute("prov", prov);
        session.setAttribute("dist", dist);
        session.setAttribute("direccion", direccion);
        session.setAttribute("numResolAband", numResolAband);
        session.setAttribute("fechaResolAband", fechaResolAband);
        session.setAttribute("numResolConsen", numResolConsen);
        session.setAttribute("fechaResolConsen", fechaResolConsen);
        session.setAttribute("incesto", incesto);
        session.setAttribute("psiquiatrica", psiquiatrica);
        session.setAttribute("epilepsia", epilepsia);
        session.setAttribute("abuso", abuso);
        session.setAttribute("sifilis", sifilis);
        session.setAttribute("estable", estable);
        session.setAttribute("intervencion", intervencion);
        session.setAttribute("trastorno", trastorno);
        session.setAttribute("prioritarios", prioritarios);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/crearNna", map);
    }

    @RequestMapping(value = "/crearNna", method = RequestMethod.GET)
    public ModelAndView crearNna_GET(ModelMap map, HttpSession session) {
        String nn = "";
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String sexo = "";
        String fechaNac = "";
        String edad = "";
        String meses = "";
        long idJuzgado = 0;
        long idCar = 0;
        String actaNac = "";
        String dep = "";
        String prov = "";
        String dist = "";
        String direccion = "";
        String numResolAband = "";
        String fechaResolAband = "";
        String numResolConsen = "";
        String fechaResolConsen = "";
        String incesto = "";
        String psiquiatrica = "";
        String epilepsia = "";
        String abuso = "";
        String sifilis = "";
        String estable = "";
        String intervencion = "";
        String trastorno = "";
        String prioritarios = "";
        String obs = "";
        if (session.getAttribute("nn") != null) {
            nn = session.getAttribute("nn").toString();
        }
        if (session.getAttribute("nombre") != null) {
            nombre = session.getAttribute("nombre").toString();
        }
        if (session.getAttribute("apellidoP") != null) {
            apellidoP = session.getAttribute("apellidoP").toString();
        }
        if (session.getAttribute("apellidoM") != null) {
            apellidoM = session.getAttribute("apellidoM").toString();
        }
        if (session.getAttribute("sexo") != null) {
            sexo = session.getAttribute("sexo").toString();
        }
        if (session.getAttribute("fechaNac") != null) {
            fechaNac = session.getAttribute("fechaNac").toString();
        }
        if (session.getAttribute("edad") != null) {
            edad = session.getAttribute("edad").toString();
        }
        if (session.getAttribute("meses") != null) {
            meses = session.getAttribute("meses").toString();
        }
        if (session.getAttribute("idJuzgado") != null) {
            idJuzgado = Long.parseLong(session.getAttribute("idJuzgado").toString());
        }
        if (session.getAttribute("idCar") != null) {
            idCar = Long.parseLong(session.getAttribute("idCar").toString());
        }
        if (session.getAttribute("actaNac") != null) {
            actaNac = session.getAttribute("actaNac").toString();
        }
        if (session.getAttribute("dep") != null) {
            dep = session.getAttribute("dep").toString();
        }
        if (session.getAttribute("prov") != null) {
            prov = session.getAttribute("prov").toString();
        }
        if (session.getAttribute("dist") != null) {
            dist = session.getAttribute("dist").toString();
        }
        if (session.getAttribute("direccion") != null) {
            direccion = session.getAttribute("direccion").toString();
        }
        if (session.getAttribute("numResolAband") != null) {
            numResolAband = session.getAttribute("numResolAband").toString();
        }
        if (session.getAttribute("fechaResolAband") != null) {
            fechaResolAband = session.getAttribute("fechaResolAband").toString();
        }
        if (session.getAttribute("numResolConsen") != null) {
            numResolConsen = session.getAttribute("numResolConsen").toString();
        }
        if (session.getAttribute("fechaResolConsen") != null) {
            fechaResolConsen = session.getAttribute("fechaResolConsen").toString();
        }
        if (session.getAttribute("incesto") != null) {
            incesto = session.getAttribute("incesto").toString();
        }
        if (session.getAttribute("psiquiatrica") != null) {
            psiquiatrica = session.getAttribute("psiquiatrica").toString();
        }
        if (session.getAttribute("epilepsia") != null) {
            epilepsia = session.getAttribute("epilepsia").toString();
        }
        if (session.getAttribute("abuso") != null) {
            abuso = session.getAttribute("abuso").toString();
        }
        if (session.getAttribute("sifilis") != null) {
            sifilis = session.getAttribute("sifilis").toString();
        }
        if (session.getAttribute("estable") != null) {
            estable = session.getAttribute("estable").toString();
        }
        if (session.getAttribute("intervencion") != null) {
            intervencion = session.getAttribute("intervencion").toString();
        }
        if (session.getAttribute("trastorno") != null) {
            trastorno = session.getAttribute("trastorno").toString();
        }
        if (session.getAttribute("prioritarios") != null) {
            prioritarios = session.getAttribute("prioritarios").toString();
        }
        if (session.getAttribute("obs") != null) {
            obs = session.getAttribute("obs").toString();
        }
        session.removeAttribute("nn");
        session.removeAttribute("nombre");
        session.removeAttribute("apellidoP");
        session.removeAttribute("apellidoM");
        session.removeAttribute("sexo");
        session.removeAttribute("fechaNac");
        session.removeAttribute("edad");
        session.removeAttribute("meses");
        session.removeAttribute("idJuzgado");
        session.removeAttribute("idCar");
        session.removeAttribute("actaNac");
        session.removeAttribute("dep");
        session.removeAttribute("prov");
        session.removeAttribute("dist");
        session.removeAttribute("direccion");
        session.removeAttribute("numResolAband");
        session.removeAttribute("fechaResolAband");
        session.removeAttribute("numResolConsen");
        session.removeAttribute("fechaResolConsen");
        session.removeAttribute("incesto");
        session.removeAttribute("psiquiatrica");
        session.removeAttribute("epilepsia");
        session.removeAttribute("abuso");
        session.removeAttribute("sifilis");
        session.removeAttribute("estable");
        session.removeAttribute("intervencion");
        session.removeAttribute("trastorno");
        session.removeAttribute("prioritarios");
        session.removeAttribute("obs");

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
        short edadtemp = 0;
        if (edad != null && !edad.equals("")) {
            edadtemp = Byte.valueOf(edad);
        }

        short mesestemp = 0;
        if (meses != null && !meses.equals("")) {
            mesestemp = Byte.valueOf(meses);
        }

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
        } else {
            tempNna.setClasificacion("regular");
        }
        tempNna.setObservaciones(obs);

        ServicioNna.crearNna(tempNna);

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Registró un nuevo NNA a la base de datos con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        if (prioritarios != null) {
            map.put("listaNna", ServicioNna.ListaNna("prioritario"));
            return new ModelAndView("/Personal/nna/lista_nna_prior", map);
        } else {
            map.put("listaNna", ServicioNna.ListaNna("regular"));
            return new ModelAndView("/Personal/nna/lista_nna", map);
        }

    }

    //PROBAR
    @RequestMapping(value = "/updateNna", method = RequestMethod.POST)
    public ModelAndView updateNna_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("nn", nn);
        session.setAttribute("nombre", nombre);
        session.setAttribute("apellidoP", apellidoP);
        session.setAttribute("apellidoM", apellidoM);
        session.setAttribute("sexo", sexo);
        session.setAttribute("fechaNac", fechaNac);
        session.setAttribute("edad", edad);
        session.setAttribute("meses", meses);
        session.setAttribute("idJuzgado", idJuzgado);
        session.setAttribute("idCar", idCar);
        session.setAttribute("actaNac", actaNac);
        session.setAttribute("dep", dep);
        session.setAttribute("prov", prov);
        session.setAttribute("dist", dist);
        session.setAttribute("direccion", direccion);
        session.setAttribute("numResolAband", numResolAband);
        session.setAttribute("fechaResolAband", fechaResolAband);
        session.setAttribute("numResolConsen", numResolConsen);
        session.setAttribute("fechaResolConsen", fechaResolConsen);
        session.setAttribute("incesto", incesto);
        session.setAttribute("psiquiatrica", psiquiatrica);
        session.setAttribute("epilepsia", epilepsia);
        session.setAttribute("abuso", abuso);
        session.setAttribute("sifilis", sifilis);
        session.setAttribute("estable", estable);
        session.setAttribute("intervencion", intervencion);
        session.setAttribute("trastorno", trastorno);
        session.setAttribute("prioritarios", prioritarios);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/updateNna", map);
    }

    @RequestMapping(value = "/updateNna", method = RequestMethod.GET)
    public ModelAndView updateNna_GET(ModelMap map, HttpSession session) {
        long idNna = 0;
        String nn = "";
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String sexo = "";
        String fechaNac = "";
        String edad = "";
        String meses = "";
        long idJuzgado = 0;
        long idCar = 0;
        String actaNac = "";
        String dep = "";
        String prov = "";
        String dist = "";
        String direccion = "";
        String numResolAband = "";
        String fechaResolAband = "";
        String numResolConsen = "";
        String fechaResolConsen = "";
        String incesto = "";
        String psiquiatrica = "";
        String epilepsia = "";
        String abuso = "";
        String sifilis = "";
        String estable = "";
        String intervencion = "";
        String trastorno = "";
        String prioritarios = "";
        String obs = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("nn");
            session.removeAttribute("nombre");
            session.removeAttribute("apellidoP");
            session.removeAttribute("apellidoM");
            session.removeAttribute("sexo");
            session.removeAttribute("fechaNac");
            session.removeAttribute("edad");
            session.removeAttribute("meses");
            session.removeAttribute("idJuzgado");
            session.removeAttribute("idCar");
            session.removeAttribute("actaNac");
            session.removeAttribute("dep");
            session.removeAttribute("prov");
            session.removeAttribute("dist");
            session.removeAttribute("direccion");
            session.removeAttribute("numResolAband");
            session.removeAttribute("fechaResolAband");
            session.removeAttribute("numResolConsen");
            session.removeAttribute("fechaResolConsen");
            session.removeAttribute("incesto");
            session.removeAttribute("psiquiatrica");
            session.removeAttribute("epilepsia");
            session.removeAttribute("abuso");
            session.removeAttribute("sifilis");
            session.removeAttribute("estable");
            session.removeAttribute("intervencion");
            session.removeAttribute("trastorno");
            session.removeAttribute("prioritarios");
            session.removeAttribute("obs");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("idJuzgado") != null) {
            idJuzgado = Long.parseLong(session.getAttribute("idJuzgado").toString());
        }
        if (session.getAttribute("nn") != null) {
            nn = session.getAttribute("nn").toString();
        }
        if (session.getAttribute("nombre") != null) {
            nombre = session.getAttribute("nombre").toString();
        }
        if (session.getAttribute("apellidoP") != null) {
            apellidoP = session.getAttribute("apellidoP").toString();
        }
        if (session.getAttribute("apellidoM") != null) {
            apellidoM = session.getAttribute("apellidoM").toString();
        }
        if (session.getAttribute("sexo") != null) {
            sexo = session.getAttribute("sexo").toString();
        }
        if (session.getAttribute("fechaNac") != null) {
            fechaNac = session.getAttribute("fechaNac").toString();
        }
        if (session.getAttribute("edad") != null) {
            edad = session.getAttribute("edad").toString();
        }
        if (session.getAttribute("meses") != null) {
            meses = session.getAttribute("meses").toString();
        }
        if (session.getAttribute("idJuzgado") != null) {
            idJuzgado = Long.parseLong(session.getAttribute("idJuzgado").toString());
        }
        if (session.getAttribute("idCar") != null) {
            idCar = Long.parseLong(session.getAttribute("idCar").toString());
        }
        if (session.getAttribute("actaNac") != null) {
            actaNac = session.getAttribute("actaNac").toString();
        }
        if (session.getAttribute("dep") != null) {
            dep = session.getAttribute("dep").toString();
        }
        if (session.getAttribute("prov") != null) {
            prov = session.getAttribute("prov").toString();
        }
        if (session.getAttribute("dist") != null) {
            dist = session.getAttribute("dist").toString();
        }
        if (session.getAttribute("direccion") != null) {
            direccion = session.getAttribute("direccion").toString();
        }
        if (session.getAttribute("numResolAband") != null) {
            numResolAband = session.getAttribute("numResolAband").toString();
        }
        if (session.getAttribute("fechaResolAband") != null) {
            fechaResolAband = session.getAttribute("fechaResolAband").toString();
        }
        if (session.getAttribute("numResolConsen") != null) {
            numResolConsen = session.getAttribute("numResolConsen").toString();
        }
        if (session.getAttribute("fechaResolConsen") != null) {
            fechaResolConsen = session.getAttribute("fechaResolConsen").toString();
        }
        if (session.getAttribute("incesto") != null) {
            incesto = session.getAttribute("incesto").toString();
        }
        if (session.getAttribute("psiquiatrica") != null) {
            psiquiatrica = session.getAttribute("psiquiatrica").toString();
        }
        if (session.getAttribute("epilepsia") != null) {
            epilepsia = session.getAttribute("epilepsia").toString();
        }
        if (session.getAttribute("abuso") != null) {
            abuso = session.getAttribute("abuso").toString();
        }
        if (session.getAttribute("sifilis") != null) {
            sifilis = session.getAttribute("sifilis").toString();
        }
        if (session.getAttribute("estable") != null) {
            estable = session.getAttribute("estable").toString();
        }
        if (session.getAttribute("intervencion") != null) {
            intervencion = session.getAttribute("intervencion").toString();
        }
        if (session.getAttribute("trastorno") != null) {
            trastorno = session.getAttribute("trastorno").toString();
        }
        if (session.getAttribute("prioritarios") != null) {
            prioritarios = session.getAttribute("prioritarios").toString();
        }
        if (session.getAttribute("obs") != null) {
            obs = session.getAttribute("obs").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("nn");
        session.removeAttribute("nombre");
        session.removeAttribute("apellidoP");
        session.removeAttribute("apellidoM");
        session.removeAttribute("sexo");
        session.removeAttribute("fechaNac");
        session.removeAttribute("edad");
        session.removeAttribute("meses");
        session.removeAttribute("idJuzgado");
        session.removeAttribute("idCar");
        session.removeAttribute("actaNac");
        session.removeAttribute("dep");
        session.removeAttribute("prov");
        session.removeAttribute("dist");
        session.removeAttribute("direccion");
        session.removeAttribute("numResolAband");
        session.removeAttribute("fechaResolAband");
        session.removeAttribute("numResolConsen");
        session.removeAttribute("fechaResolConsen");
        session.removeAttribute("incesto");
        session.removeAttribute("psiquiatrica");
        session.removeAttribute("epilepsia");
        session.removeAttribute("abuso");
        session.removeAttribute("sifilis");
        session.removeAttribute("estable");
        session.removeAttribute("intervencion");
        session.removeAttribute("trastorno");
        session.removeAttribute("prioritarios");
        session.removeAttribute("obs");

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

        short edadtemp = 0;
        short mesestemp = 0;

        try {
            edadtemp = Byte.valueOf(edad);
            mesestemp = Byte.valueOf(meses);
        } catch (Exception ex) {
        }
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

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Se editó el perfil del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

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

        String tempfechaString = "";
        map.addAttribute("fechainv", tempfechaString);
        map.addAttribute("fechaing", tempfechaString);
        map.addAttribute("fechaest", tempfechaString);
        map.put("nna", ServicioNna.getNna(idNna));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/agregarExpedienteNna2", method = RequestMethod.POST)
    public ModelAndView agregarExpedienteNna2(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        String tempfechaString = "";
        map.addAttribute("fechainv", tempfechaString);
        map.addAttribute("fechaing", tempfechaString);
        map.addAttribute("fechaest", tempfechaString);
        map.put("nna", ServicioNna.getNna(idNna));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/Buscador/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/agregarExpedienteNna3", method = RequestMethod.POST)
    public ModelAndView agregarExpedienteNna3(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "volver2", required = false) String volver2,
            HttpSession session,
            @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        String tempfechaString = "";
        map.addAttribute("fechainv", tempfechaString);
        map.addAttribute("fechaing", tempfechaString);
        map.addAttribute("fechaest", tempfechaString);
        map.put("nna", ServicioNna.getNna(idNna));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.addAttribute("volver", volver);
        map.addAttribute("volver2", volver2);
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/Buscador_etapa/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/agregarExpedienteNna4", method = RequestMethod.POST)
    public ModelAndView agregarExpedienteNna4(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        String tempfechaString = "";
        map.addAttribute("fechainv", tempfechaString);
        map.addAttribute("fechaing", tempfechaString);
        map.addAttribute("fechaest", tempfechaString);
        map.put("nna", ServicioNna.getNna(idNna));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        return new ModelAndView("/Personal/nna/editar_expediente_1", map);
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
        Unidad tempUA = new Unidad();
        tempUA = ServicioPersonal.getUa(tempExp.getUnidad().getIdunidad());
        String tempfechaInv = "";
        String tempfechaIng = "";
        String tempfechaEst = "";
        try {
            tempfechaInv = df.dateToString(tempExp.getFechaInvTutelar());
        } catch (Exception ex) {
        }
        map.addAttribute("fechainv", tempfechaInv);
        try {
            tempfechaIng = df.dateToString(tempExp.getFechaIngreso());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaing", tempfechaIng);
        try {
            tempfechaEst = df.dateToString(tempExp.getFechaEstado());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaest", tempfechaEst);
        map.put("df", df);
        map.put("unidad", tempUA);
        map.put("expediente", tempExp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/editarExpedienteNna2", method = RequestMethod.POST)
    public ModelAndView editarExpedienteNna2(ModelMap map, HttpSession session, @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ExpedienteNna tempExp = new ExpedienteNna();
        tempExp = ServicioNna.getExpNna(idNna);
        String tempfechaInv = "";
        String tempfechaIng = "";
        String tempfechaEst = "";
        try {
            tempfechaInv = df.dateToString(tempExp.getFechaInvTutelar());
        } catch (Exception ex) {
        }
        map.addAttribute("fechainv", tempfechaInv);
        try {
            tempfechaIng = df.dateToString(tempExp.getFechaIngreso());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaing", tempfechaIng);
        try {
            tempfechaEst = df.dateToString(tempExp.getFechaEstado());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaest", tempfechaEst);
        map.put("expediente", tempExp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/Buscador/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/editarExpedienteNna3", method = RequestMethod.POST)
    public ModelAndView editarExpedienteNna3(ModelMap map,
            @RequestParam(value = "volver2", required = false) String volver2,
            HttpSession session,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ExpedienteNna tempExp = new ExpedienteNna();
        tempExp = ServicioNna.getExpNna(idNna);
        String tempfechaInv = "";
        String tempfechaIng = "";
        String tempfechaEst = "";
        try {
            tempfechaInv = df.dateToString(tempExp.getFechaInvTutelar());
        } catch (Exception ex) {
        }
        map.addAttribute("fechainv", tempfechaInv);
        try {
            tempfechaIng = df.dateToString(tempExp.getFechaIngreso());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaing", tempfechaIng);
        try {
            tempfechaEst = df.dateToString(tempExp.getFechaEstado());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaest", tempfechaEst);
        map.put("expediente", tempExp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.put("listaUa", ServicioPersonal.ListaUa());
        map.addAttribute("volver", volver);
        map.addAttribute("volver2", volver2);
        map.addAttribute("idNna", idNna);
        return new ModelAndView("/Personal/Buscador_etapa/nna/editar_expediente", map);
    }

    @RequestMapping(value = "/editarExpedienteNna4", method = RequestMethod.POST)
    public ModelAndView editarExpedienteNna4(ModelMap map,
            @RequestParam(value = "volver2", required = false) String volver2,
            HttpSession session,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam("idNna") long idNna) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ExpedienteNna tempExp = new ExpedienteNna();
        tempExp = ServicioNna.getExpNna(idNna);
        String tempfechaInv = "";
        String tempfechaIng = "";
        String tempfechaEst = "";
        try {
            tempfechaInv = df.dateToString(tempExp.getFechaInvTutelar());
        } catch (Exception ex) {
        }
        map.addAttribute("fechainv", tempfechaInv);
        try {
            tempfechaIng = df.dateToString(tempExp.getFechaIngreso());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaing", tempfechaIng);
        try {
            tempfechaEst = df.dateToString(tempExp.getFechaEstado());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaest", tempfechaEst);
        map.put("expediente", tempExp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.put("listaExpedientes", ServicioNna.listaExpNna());
        map.addAttribute("volver", volver);
        map.addAttribute("volver2", volver2);
        map.addAttribute("idNna", idNna);
        return new ModelAndView("/Personal/nna/editar_expediente_1", map);
    }

    //PROBAR
    @RequestMapping(value = "/crearExpedienteNna", method = RequestMethod.POST)
    public ModelAndView crearExpedienteNna(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna,
            @RequestParam("ua") long ua,
            @RequestParam(value = "numero", required = false) String numero,
            @RequestParam(value = "nombreActual", required = false) String nombreActual,
            @RequestParam(value = "apellidoPActual", required = false) String apellidoPActual,
            @RequestParam(value = "apellidoMActual", required = false) String apellidoMActual,
            @RequestParam(value = "fechaIngreso", required = false) String fechaIngreso,
            @RequestParam(value = "fechaIngresoPrio", required = false) String fechaIngresoPrio,
            @RequestParam(value = "fechaAct", required = false) String fechaAct,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("ua", ua);
        session.setAttribute("numero", numero);
        session.setAttribute("nombreActual", nombreActual);
        session.setAttribute("apellidoPActual", apellidoPActual);
        session.setAttribute("apellidoMActual", apellidoMActual);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("fechaIngresoPrio", fechaIngresoPrio);
        session.setAttribute("fechaAct", fechaAct);
        session.setAttribute("ht", ht);
        session.setAttribute("nInvTutelar", nInvTutelar);
        session.setAttribute("fechaInvTutelar", fechaInvTutelar);
        session.setAttribute("procTutelar", procTutelar);
        session.setAttribute("fichaInt", fichaInt);
        session.setAttribute("respLegal", respLegal);
        session.setAttribute("respPsico", respPsico);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaEstado", fechaEstado);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("grpRef", grpRef);
        session.setAttribute("codMayor", codMayor);
        session.setAttribute("codAdoles", codAdoles);
        session.setAttribute("codHermano", codHermano);
        session.setAttribute("codSalud", codSalud);
        session.setAttribute("codEspeciales", codEspeciales);
        session.setAttribute("diagnostico", diagnostico);
        session.setAttribute("comentario", comentario);

        return new ModelAndView("redirect:/crearExpedienteNna", map);
    }

    @RequestMapping(value = "/crearExpedienteNna", method = RequestMethod.GET)
    public ModelAndView crearExpedienteNna(ModelMap map, HttpSession session) {
        long idNna = 0;
        long ua = 0;
        String numero = "";
        String nombreActual = "";
        String apellidoPActual = "";
        String apellidoMActual = "";
        String fechaIngreso = "";
        String fechaIngresoPrio = "";
        String fechaAct = "";
        String ht = "";
        String nInvTutelar = "";
        String fechaInvTutelar = "";
        String procTutelar = "";
        String fichaInt = "";
        String respLegal = "";
        String respPsico = "";
        String estado = "";
        String fechaEstado = "";
        String clasificacion = "";
        String grpRef = "";
        String codMayor = "";
        String codAdoles = "";
        String codHermano = "";
        String codSalud = "";
        String codEspeciales = "";
        String diagnostico = "";
        String comentario = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
            ua = Long.parseLong(session.getAttribute("ua").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("ua");
            session.removeAttribute("numero");
            session.removeAttribute("nombreActual");
            session.removeAttribute("apellidoPActual");
            session.removeAttribute("apellidoMActual");
            session.removeAttribute("fechaIngreso");
            session.removeAttribute("fechaIngresoPrio");
            session.removeAttribute("fechaAct");
            session.removeAttribute("ht");
            session.removeAttribute("nInvTutelar");
            session.removeAttribute("fechaInvTutelar");
            session.removeAttribute("procTutelar");
            session.removeAttribute("fichaInt");
            session.removeAttribute("respLegal");
            session.removeAttribute("respPsico");
            session.removeAttribute("estado");
            session.removeAttribute("fechaEstado");
            session.removeAttribute("clasificacion");
            session.removeAttribute("grpRef");
            session.removeAttribute("codMayor");
            session.removeAttribute("codAdoles");
            session.removeAttribute("codHermano");
            session.removeAttribute("codSalud");
            session.removeAttribute("codEspeciales");
            session.removeAttribute("diagnostico");
            session.removeAttribute("comentario");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("numero") != null) {
            numero = session.getAttribute("numero").toString();
        }
        if (session.getAttribute("nombreActual") != null) {
            nombreActual = session.getAttribute("nombreActual").toString();
        }
        if (session.getAttribute("apellidoPActual") != null) {
            apellidoPActual = session.getAttribute("apellidoPActual").toString();
        }
        if (session.getAttribute("apellidoMActual") != null) {
            apellidoMActual = session.getAttribute("apellidoMActual").toString();
        }
        if (session.getAttribute("fechaIngreso") != null) {
            fechaIngreso = session.getAttribute("fechaIngreso").toString();
        }
        if (session.getAttribute("fechaIngresoPrio") != null) {
            fechaIngresoPrio = session.getAttribute("fechaIngresoPrio").toString();
        }
        if (session.getAttribute("fechaAct") != null) {
            fechaAct = session.getAttribute("fechaAct").toString();
        }
        if (session.getAttribute("ht") != null) {
            ht = session.getAttribute("ht").toString();
        }
        if (session.getAttribute("nInvTutelar") != null) {
            nInvTutelar = session.getAttribute("nInvTutelar").toString();
        }
        if (session.getAttribute("fechaInvTutelar") != null) {
            fechaInvTutelar = session.getAttribute("fechaInvTutelar").toString();
        }
        if (session.getAttribute("procTutelar") != null) {
            procTutelar = session.getAttribute("procTutelar").toString();
        }
        if (session.getAttribute("fichaInt") != null) {
            fichaInt = session.getAttribute("fichaInt").toString();
        }
        if (session.getAttribute("respLegal") != null) {
            respLegal = session.getAttribute("respLegal").toString();
        }
        if (session.getAttribute("respPsico") != null) {
            respPsico = session.getAttribute("respPsico").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("fechaEstado") != null) {
            fechaEstado = session.getAttribute("fechaEstado").toString();
        }
        if (session.getAttribute("clasificacion") != null) {
            clasificacion = session.getAttribute("clasificacion").toString();
        }
        if (session.getAttribute("grpRef") != null) {
            grpRef = session.getAttribute("grpRef").toString();
        }
        if (session.getAttribute("codMayor") != null) {
            codMayor = session.getAttribute("codMayor").toString();
        }
        if (session.getAttribute("codAdoles") != null) {
            codAdoles = session.getAttribute("codAdoles").toString();
        }
        if (session.getAttribute("codHermano") != null) {
            codHermano = session.getAttribute("codHermano").toString();
        }
        if (session.getAttribute("codSalud") != null) {
            codSalud = session.getAttribute("codSalud").toString();
        }
        if (session.getAttribute("codEspeciales") != null) {
            codEspeciales = session.getAttribute("codEspeciales").toString();
        }
        if (session.getAttribute("diagnostico") != null) {
            diagnostico = session.getAttribute("diagnostico").toString();
        }
        if (session.getAttribute("comentario") != null) {
            comentario = session.getAttribute("comentario").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("ua");
        session.removeAttribute("numero");
        session.removeAttribute("nombreActual");
        session.removeAttribute("apellidoPActual");
        session.removeAttribute("apellidoMActual");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("fechaIngresoPrio");
        session.removeAttribute("fechaAct");
        session.removeAttribute("ht");
        session.removeAttribute("nInvTutelar");
        session.removeAttribute("fechaInvTutelar");
        session.removeAttribute("procTutelar");
        session.removeAttribute("fichaInt");
        session.removeAttribute("respLegal");
        session.removeAttribute("respPsico");
        session.removeAttribute("estado");
        session.removeAttribute("fechaEstado");
        session.removeAttribute("clasificacion");
        session.removeAttribute("grpRef");
        session.removeAttribute("codMayor");
        session.removeAttribute("codAdoles");
        session.removeAttribute("codHermano");
        session.removeAttribute("codSalud");
        session.removeAttribute("codEspeciales");
        session.removeAttribute("diagnostico");
        session.removeAttribute("comentario");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        ExpedienteNna tempExp = new ExpedienteNna();
        tempNna = ServicioNna.getNna(idNna);
        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);
        tempExp.setUnidad(temp2);
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
        if (fechaIngresoPrio != null && !fechaIngresoPrio.equals("")) {
            tempExp.setFechaIngPrio(df.stringToDate(fechaIngresoPrio));
        }
        if (fechaIngresoPrio == null || fechaIngresoPrio.equals("")) {
            tempExp.setFechaIngPrio(null);
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
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respLegal)) {
                    tempExp.setRespLegalP(pers.getNombre());
                    tempExp.setRespLegalM(pers.getApellidoP());
                }
            }
        }
        tempExp.setRespPsicosocialNombre(respPsico);
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respPsico)) {
                    tempExp.setRespPiscosocialP(pers.getNombre());
                    tempExp.setRespPsicosocialM(pers.getApellidoP());
                }
            }
        }
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
        tempExp.setObservaciones(comentario);

        if (clasificacion != null && clasificacion.equals("prioritario")) {
            if (codMayor != null) {
                tempExp.setCodigoReferencia(codMayor);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("0"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
            if (codAdoles != null) {
                tempExp.setCodigoReferencia(codAdoles);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("0"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
            if (codHermano != null) {
                tempExp.setCodigoReferencia(codHermano);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("0"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
            if (codSalud != null) {
                tempExp.setCodigoReferencia(codSalud);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("0"));
            }
            if (codEspeciales != null) {
                tempExp.setCodigoReferencia(codEspeciales);
                tempNna.setEspecial(Short.parseShort("0"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
        }
        tempExp.setNacional(Short.parseShort("0"));
        tempExp.setAdoptable(Short.parseShort("0"));
        if (clasificacion != null && (clasificacion.equals("regular") || clasificacion.equals("seguimiento"))) {
            tempNna.setEspecial(Short.parseShort("1"));
            tempNna.setMayor(Short.parseShort("1"));
            tempNna.setHermano(Short.parseShort("1"));
            tempNna.setAdolescente(Short.parseShort("1"));
            tempNna.setEnfermo(Short.parseShort("1"));
        }
        ServicioNna.updateNna(tempNna);
        if (clasificacion.equals("seguimiento")) {
            tempExp.setAdoptable(Short.parseShort("1"));
            // tempExp.setEstado("eval");
        }
//        Date utiDate = new Date();
//        java.sql.Date fechaAct = new java.sql.Date(utiDate.getTime());
//        tempExp.setFechaActualizacion(fechaAct);
        if (fechaAct != null && !fechaAct.equals("")) {
            tempExp.setFechaActualizacion(df.stringToDate(fechaAct));
        }
        if (fechaAct == null || fechaAct.equals("")) {
            tempExp.setFechaActualizacion(null);
        }
        tempExp.setNna(tempNna);
        ServicioNna.crearExpNna(tempExp);

        String tempfechaNac = "";
        String tempfechaAban = "";
        String tempfechaCon = "";
        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaAban);
        try {
            tempfechaCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaCon);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Creó un nuevo expediente del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

    //PROBAR
    @RequestMapping(value = "/crearExpedienteNna2", method = RequestMethod.POST)
    public ModelAndView crearExpedienteNna2(ModelMap map, HttpSession session,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("numero", numero);
        session.setAttribute("nombreActual", nombreActual);
        session.setAttribute("apellidoPActual", apellidoPActual);
        session.setAttribute("apellidoMActual", apellidoMActual);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("ht", ht);
        session.setAttribute("nInvTutelar", nInvTutelar);
        session.setAttribute("fechaInvTutelar", fechaInvTutelar);
        session.setAttribute("procTutelar", procTutelar);
        session.setAttribute("fichaInt", fichaInt);
        session.setAttribute("respLegal", respLegal);
        session.setAttribute("respPsico", respPsico);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaEstado", fechaEstado);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("grpRef", grpRef);
        session.setAttribute("codMayor", codMayor);
        session.setAttribute("codAdoles", codAdoles);
        session.setAttribute("codHermano", codHermano);
        session.setAttribute("codSalud", codSalud);
        session.setAttribute("codEspeciales", codEspeciales);
        session.setAttribute("diagnostico", diagnostico);
        session.setAttribute("comentario", comentario);

        return new ModelAndView("redirect:/crearExpedienteNna2", map);
    }

    @RequestMapping(value = "/crearExpedienteNna2", method = RequestMethod.GET)
    public ModelAndView crearExpedienteNna2(ModelMap map, HttpSession session) {
        long idNna = 0;
        String numero = "";
        String nombreActual = "";
        String apellidoPActual = "";
        String apellidoMActual = "";
        String fechaIngreso = "";
        String ht = "";
        String nInvTutelar = "";
        String fechaInvTutelar = "";
        String procTutelar = "";
        String fichaInt = "";
        String respLegal = "";
        String respPsico = "";
        String estado = "";
        String fechaEstado = "";
        String clasificacion = "";
        String grpRef = "";
        String codMayor = "";
        String codAdoles = "";
        String codHermano = "";
        String codSalud = "";
        String codEspeciales = "";
        String diagnostico = "";
        String comentario = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("numero");
            session.removeAttribute("nombreActual");
            session.removeAttribute("apellidoPActual");
            session.removeAttribute("apellidoMActual");
            session.removeAttribute("fechaIngreso");
            session.removeAttribute("ht");
            session.removeAttribute("nInvTutelar");
            session.removeAttribute("fechaInvTutelar");
            session.removeAttribute("procTutelar");
            session.removeAttribute("fichaInt");
            session.removeAttribute("respLegal");
            session.removeAttribute("respPsico");
            session.removeAttribute("estado");
            session.removeAttribute("fechaEstado");
            session.removeAttribute("clasificacion");
            session.removeAttribute("grpRef");
            session.removeAttribute("codMayor");
            session.removeAttribute("codAdoles");
            session.removeAttribute("codHermano");
            session.removeAttribute("codSalud");
            session.removeAttribute("codEspeciales");
            session.removeAttribute("diagnostico");
            session.removeAttribute("comentario");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("numero") != null) {
            numero = session.getAttribute("numero").toString();
        }
        if (session.getAttribute("nombreActual") != null) {
            nombreActual = session.getAttribute("nombreActual").toString();
        }
        if (session.getAttribute("apellidoPActual") != null) {
            apellidoPActual = session.getAttribute("apellidoPActual").toString();
        }
        if (session.getAttribute("apellidoMActual") != null) {
            apellidoMActual = session.getAttribute("apellidoMActual").toString();
        }
        if (session.getAttribute("fechaIngreso") != null) {
            fechaIngreso = session.getAttribute("fechaIngreso").toString();
        }
        if (session.getAttribute("ht") != null) {
            ht = session.getAttribute("ht").toString();
        }
        if (session.getAttribute("nInvTutelar") != null) {
            nInvTutelar = session.getAttribute("nInvTutelar").toString();
        }
        if (session.getAttribute("fechaInvTutelar") != null) {
            fechaInvTutelar = session.getAttribute("fechaInvTutelar").toString();
        }
        if (session.getAttribute("procTutelar") != null) {
            procTutelar = session.getAttribute("procTutelar").toString();
        }
        if (session.getAttribute("fichaInt") != null) {
            fichaInt = session.getAttribute("fichaInt").toString();
        }
        if (session.getAttribute("respLegal") != null) {
            respLegal = session.getAttribute("respLegal").toString();
        }
        if (session.getAttribute("respPsico") != null) {
            respPsico = session.getAttribute("respPsico").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("fechaEstado") != null) {
            fechaEstado = session.getAttribute("fechaEstado").toString();
        }
        if (session.getAttribute("clasificacion") != null) {
            clasificacion = session.getAttribute("clasificacion").toString();
        }
        if (session.getAttribute("grpRef") != null) {
            grpRef = session.getAttribute("grpRef").toString();
        }
        if (session.getAttribute("codMayor") != null) {
            codMayor = session.getAttribute("codMayor").toString();
        }
        if (session.getAttribute("codAdoles") != null) {
            codAdoles = session.getAttribute("codAdoles").toString();
        }
        if (session.getAttribute("codHermano") != null) {
            codHermano = session.getAttribute("codHermano").toString();
        }
        if (session.getAttribute("codSalud") != null) {
            codSalud = session.getAttribute("codSalud").toString();
        }
        if (session.getAttribute("codEspeciales") != null) {
            codEspeciales = session.getAttribute("codEspeciales").toString();
        }
        if (session.getAttribute("diagnostico") != null) {
            diagnostico = session.getAttribute("diagnostico").toString();
        }
        if (session.getAttribute("comentario") != null) {
            comentario = session.getAttribute("comentario").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("numero");
        session.removeAttribute("nombreActual");
        session.removeAttribute("apellidoPActual");
        session.removeAttribute("apellidoMActual");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("ht");
        session.removeAttribute("nInvTutelar");
        session.removeAttribute("fechaInvTutelar");
        session.removeAttribute("procTutelar");
        session.removeAttribute("fichaInt");
        session.removeAttribute("respLegal");
        session.removeAttribute("respPsico");
        session.removeAttribute("estado");
        session.removeAttribute("fechaEstado");
        session.removeAttribute("clasificacion");
        session.removeAttribute("grpRef");
        session.removeAttribute("codMayor");
        session.removeAttribute("codAdoles");
        session.removeAttribute("codHermano");
        session.removeAttribute("codSalud");
        session.removeAttribute("codEspeciales");
        session.removeAttribute("diagnostico");
        session.removeAttribute("comentario");

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
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respLegal)) {
                    tempExp.setRespLegalP(pers.getNombre());
                    tempExp.setRespLegalM(pers.getApellidoP());
                }
            }
        }
        tempExp.setRespPsicosocialNombre(respPsico);
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respPsico)) {
                    tempExp.setRespPiscosocialP(pers.getNombre());
                    tempExp.setRespPsicosocialM(pers.getApellidoP());
                }
            }
        }
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
        tempExp.setObservaciones(comentario);

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
        tempExp.setNacional(Short.parseShort("0"));
        tempExp.setAdoptable(Short.parseShort("0"));
        if (clasificacion != null && (clasificacion.equals("regular") || clasificacion.equals("seguimiento"))) {
            tempNna.setEspecial(Short.parseShort("1"));
            tempNna.setMayor(Short.parseShort("1"));
            tempNna.setHermano(Short.parseShort("1"));
            tempNna.setAdolescente(Short.parseShort("1"));
            tempNna.setEnfermo(Short.parseShort("1"));
        }
        ServicioNna.updateNna(tempNna);
        if (clasificacion.equals("seguimiento")) {
            tempExp.setAdoptable(Short.parseShort("1"));
//            tempExp.setEstado("eval");
        }
        tempExp.setNna(tempNna);
        ServicioNna.crearExpNna(tempExp);

        String tempfechaNac = "";
        String tempfechaAban = "";
        String tempfechaCon = "";
        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaAban);
        try {
            tempfechaCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaCon);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Creó un nuevo expediente del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        return new ModelAndView("/Personal/Buscador/nna/editar_nna", map);
    }

    //PROBAR
    @RequestMapping(value = "/crearExpedienteNna4", method = RequestMethod.POST)
    public ModelAndView crearExpedienteNna4_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("numero", numero);
        session.setAttribute("nombreActual", nombreActual);
        session.setAttribute("apellidoPActual", apellidoPActual);
        session.setAttribute("apellidoMActual", apellidoMActual);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("ht", ht);
        session.setAttribute("nInvTutelar", nInvTutelar);
        session.setAttribute("fechaInvTutelar", fechaInvTutelar);
        session.setAttribute("procTutelar", procTutelar);
        session.setAttribute("fichaInt", fichaInt);
        session.setAttribute("respLegal", respLegal);
        session.setAttribute("respPsico", respPsico);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaEstado", fechaEstado);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("grpRef", grpRef);
        session.setAttribute("codMayor", codMayor);
        session.setAttribute("codAdoles", codAdoles);
        session.setAttribute("codHermano", codHermano);
        session.setAttribute("codSalud", codSalud);
        session.setAttribute("codEspeciales", codEspeciales);
        session.setAttribute("diagnostico", diagnostico);
        session.setAttribute("comentario", comentario);

        return new ModelAndView("redirect:/crearExpedienteNna4", map);
    }

    @RequestMapping(value = "/crearExpedienteNna4", method = RequestMethod.GET)
    public ModelAndView crearExpedienteNna4_GET(ModelMap map, HttpSession session) {
        long idNna = 0;
        String numero = "";
        String nombreActual = "";
        String apellidoPActual = "";
        String apellidoMActual = "";
        String fechaIngreso = "";
        String ht = "";
        String nInvTutelar = "";
        String fechaInvTutelar = "";
        String procTutelar = "";
        String fichaInt = "";
        String respLegal = "";
        String respPsico = "";
        String estado = "";
        String fechaEstado = "";
        String clasificacion = "";
        String grpRef = "";
        String codMayor = "";
        String codAdoles = "";
        String codHermano = "";
        String codSalud = "";
        String codEspeciales = "";
        String diagnostico = "";
        String comentario = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("numero");
            session.removeAttribute("nombreActual");
            session.removeAttribute("apellidoPActual");
            session.removeAttribute("apellidoMActual");
            session.removeAttribute("fechaIngreso");
            session.removeAttribute("ht");
            session.removeAttribute("nInvTutelar");
            session.removeAttribute("fechaInvTutelar");
            session.removeAttribute("procTutelar");
            session.removeAttribute("fichaInt");
            session.removeAttribute("respLegal");
            session.removeAttribute("respPsico");
            session.removeAttribute("estado");
            session.removeAttribute("fechaEstado");
            session.removeAttribute("clasificacion");
            session.removeAttribute("grpRef");
            session.removeAttribute("codMayor");
            session.removeAttribute("codAdoles");
            session.removeAttribute("codHermano");
            session.removeAttribute("codSalud");
            session.removeAttribute("codEspeciales");
            session.removeAttribute("diagnostico");
            session.removeAttribute("comentario");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("numero") != null) {
            numero = session.getAttribute("numero").toString();
        }
        if (session.getAttribute("nombreActual") != null) {
            nombreActual = session.getAttribute("nombreActual").toString();
        }
        if (session.getAttribute("apellidoPActual") != null) {
            apellidoPActual = session.getAttribute("apellidoPActual").toString();
        }
        if (session.getAttribute("apellidoMActual") != null) {
            apellidoMActual = session.getAttribute("apellidoMActual").toString();
        }
        if (session.getAttribute("fechaIngreso") != null) {
            fechaIngreso = session.getAttribute("fechaIngreso").toString();
        }
        if (session.getAttribute("ht") != null) {
            ht = session.getAttribute("ht").toString();
        }
        if (session.getAttribute("nInvTutelar") != null) {
            nInvTutelar = session.getAttribute("nInvTutelar").toString();
        }
        if (session.getAttribute("fechaInvTutelar") != null) {
            fechaInvTutelar = session.getAttribute("fechaInvTutelar").toString();
        }
        if (session.getAttribute("procTutelar") != null) {
            procTutelar = session.getAttribute("procTutelar").toString();
        }
        if (session.getAttribute("fichaInt") != null) {
            fichaInt = session.getAttribute("fichaInt").toString();
        }
        if (session.getAttribute("respLegal") != null) {
            respLegal = session.getAttribute("respLegal").toString();
        }
        if (session.getAttribute("respPsico") != null) {
            respPsico = session.getAttribute("respPsico").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("fechaEstado") != null) {
            fechaEstado = session.getAttribute("fechaEstado").toString();
        }
        if (session.getAttribute("clasificacion") != null) {
            clasificacion = session.getAttribute("clasificacion").toString();
        }
        if (session.getAttribute("grpRef") != null) {
            grpRef = session.getAttribute("grpRef").toString();
        }
        if (session.getAttribute("codMayor") != null) {
            codMayor = session.getAttribute("codMayor").toString();
        }
        if (session.getAttribute("codAdoles") != null) {
            codAdoles = session.getAttribute("codAdoles").toString();
        }
        if (session.getAttribute("codHermano") != null) {
            codHermano = session.getAttribute("codHermano").toString();
        }
        if (session.getAttribute("codSalud") != null) {
            codSalud = session.getAttribute("codSalud").toString();
        }
        if (session.getAttribute("codEspeciales") != null) {
            codEspeciales = session.getAttribute("codEspeciales").toString();
        }
        if (session.getAttribute("diagnostico") != null) {
            diagnostico = session.getAttribute("diagnostico").toString();
        }
        if (session.getAttribute("comentario") != null) {
            comentario = session.getAttribute("comentario").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("numero");
        session.removeAttribute("nombreActual");
        session.removeAttribute("apellidoPActual");
        session.removeAttribute("apellidoMActual");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("ht");
        session.removeAttribute("nInvTutelar");
        session.removeAttribute("fechaInvTutelar");
        session.removeAttribute("procTutelar");
        session.removeAttribute("fichaInt");
        session.removeAttribute("respLegal");
        session.removeAttribute("respPsico");
        session.removeAttribute("estado");
        session.removeAttribute("fechaEstado");
        session.removeAttribute("clasificacion");
        session.removeAttribute("grpRef");
        session.removeAttribute("codMayor");
        session.removeAttribute("codAdoles");
        session.removeAttribute("codHermano");
        session.removeAttribute("codSalud");
        session.removeAttribute("codEspeciales");
        session.removeAttribute("diagnostico");
        session.removeAttribute("comentario");

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
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respLegal)) {
                    tempExp.setRespLegalP(pers.getNombre());
                    tempExp.setRespLegalM(pers.getApellidoP());
                }
            }
        }
        tempExp.setRespPsicosocialNombre(respPsico);
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respPsico)) {
                    tempExp.setRespPiscosocialP(pers.getNombre());
                    tempExp.setRespPsicosocialM(pers.getApellidoP());
                }
            }
        }
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
        tempExp.setObservaciones(comentario);

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
        tempExp.setNacional(Short.parseShort("0"));
        tempExp.setAdoptable(Short.parseShort("0"));
        if (clasificacion != null && (clasificacion.equals("regular") || clasificacion.equals("seguimiento"))) {
            tempNna.setEspecial(Short.parseShort("1"));
            tempNna.setMayor(Short.parseShort("1"));
            tempNna.setHermano(Short.parseShort("1"));
            tempNna.setAdolescente(Short.parseShort("1"));
            tempNna.setEnfermo(Short.parseShort("1"));
        }
        ServicioNna.updateNna(tempNna);
        if (clasificacion.equals("seguimiento")) {
            tempExp.setAdoptable(Short.parseShort("1"));
//            tempExp.setEstado("eval");
        }
        tempExp.setNna(tempNna);
        ServicioNna.crearExpNna(tempExp);

        String tempfechaNac = "";
        String tempfechaAban = "";
        String tempfechaCon = "";
        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaAban);
        try {
            tempfechaCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaCon);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Creó un nuevo expediente del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        return new ModelAndView("/Personal/nna/editar_nna_1", map);
    }

    //CONSTRUCCION
    @RequestMapping(value = "/updateExpedienteNna", method = RequestMethod.POST)
    public ModelAndView updateExpedienteNna_POST(ModelMap map, HttpSession session,
            @RequestParam("idNna") long idNna,
            @RequestParam("ua") long ua,
            @RequestParam(value = "numero", required = false) String numero,
            @RequestParam(value = "idExpNna", required = false) Long idExpNna,
            @RequestParam(value = "listaEval", required = false) String listaEval,
            @RequestParam(value = "nombreActual", required = false) String nombreActual,
            @RequestParam(value = "apellidoPActual", required = false) String apellidoPActual,
            @RequestParam(value = "apellidoMActual", required = false) String apellidoMActual,
            @RequestParam(value = "fechaIngreso", required = false) String fechaIngreso,
            @RequestParam(value = "fechaIngresoPrio", required = false) String fechaIngresoPrio,
            @RequestParam(value = "fechaAct", required = false) String fechaAct,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("ua", ua);
        session.setAttribute("numero", numero);
        session.setAttribute("idExpNna", idExpNna);
        session.setAttribute("listaEval", listaEval);
        session.setAttribute("nombreActual", nombreActual);
        session.setAttribute("apellidoPActual", apellidoPActual);
        session.setAttribute("apellidoMActual", apellidoMActual);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("fechaIngresoPrio", fechaIngresoPrio);
        session.setAttribute("fechaAct", fechaAct);
        session.setAttribute("ht", ht);
        session.setAttribute("nInvTutelar", nInvTutelar);
        session.setAttribute("fechaInvTutelar", fechaInvTutelar);
        session.setAttribute("procTutelar", procTutelar);
        session.setAttribute("respLegal", respLegal);
        session.setAttribute("respPsico", respPsico);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaEstado", fechaEstado);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("grpRef", grpRef);
        session.setAttribute("codMayor", codMayor);
        session.setAttribute("codAdoles", codAdoles);
        session.setAttribute("codHermano", codHermano);
        session.setAttribute("codSalud", codSalud);
        session.setAttribute("codEspeciales", codEspeciales);
        session.setAttribute("diagnostico", diagnostico);
        session.setAttribute("comentario", comentario);

        return new ModelAndView("redirect:/updateExpedienteNna", map);
    }

    @RequestMapping(value = "/updateExpedienteNna", method = RequestMethod.GET)
    public ModelAndView updateExpedienteNna_GET(ModelMap map, HttpSession session) {
        long idNna = 0;
        long ua = 0;
        String numero = "";
        String idExpNna = "";
        String listaEval = "";
        String nombreActual = "";
        String apellidoPActual = "";
        String apellidoMActual = "";
        String fechaIngreso = "";
        String fechaIngresoPrio = "";
        String fechaAct = "";
        String ht = "";
        String nInvTutelar = "";
        String fechaInvTutelar = "";
        String procTutelar = "";
        String respLegal = "";
        String respPsico = "";
        String estado = "";
        String fechaEstado = "";
        String clasificacion = "";
        String grpRef = "";
        String codMayor = "";
        String codAdoles = "";
        String codHermano = "";
        String codSalud = "";
        String codEspeciales = "";
        String diagnostico = "";
        String comentario = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
            ua = Long.parseLong(session.getAttribute("ua").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("ua");
            session.removeAttribute("numero");
            session.removeAttribute("idExpNna");
            session.removeAttribute("listaEval");
            session.removeAttribute("nombreActual");
            session.removeAttribute("apellidoPActual");
            session.removeAttribute("apellidoMActual");
            session.removeAttribute("fechaIngreso");
            session.removeAttribute("fechaIngresoPrio");
            session.removeAttribute("fechaAct");
            session.removeAttribute("ht");
            session.removeAttribute("nInvTutelar");
            session.removeAttribute("fechaInvTutelar");
            session.removeAttribute("procTutelar");
            session.removeAttribute("respLegal");
            session.removeAttribute("respPsico");
            session.removeAttribute("estado");
            session.removeAttribute("fechaEstado");
            session.removeAttribute("clasificacion");
            session.removeAttribute("grpRef");
            session.removeAttribute("codMayor");
            session.removeAttribute("codAdoles");
            session.removeAttribute("codHermano");
            session.removeAttribute("codSalud");
            session.removeAttribute("codEspeciales");
            session.removeAttribute("diagnostico");
            session.removeAttribute("comentario");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("numero") != null) {
            numero = session.getAttribute("numero").toString();
        }
        if (session.getAttribute("idExpNna") != null) {
            idExpNna = session.getAttribute("idExpNna").toString();
        }
        if (session.getAttribute("listaEval") != null) {
            listaEval = session.getAttribute("listaEval").toString();
        }
        if (session.getAttribute("nombreActual") != null) {
            nombreActual = session.getAttribute("nombreActual").toString();
        }
        if (session.getAttribute("apellidoPActual") != null) {
            apellidoPActual = session.getAttribute("apellidoPActual").toString();
        }
        if (session.getAttribute("apellidoMActual") != null) {
            apellidoMActual = session.getAttribute("apellidoMActual").toString();
        }
        if (session.getAttribute("fechaIngreso") != null) {
            fechaIngreso = session.getAttribute("fechaIngreso").toString();
        }
        if (session.getAttribute("fechaIngresoPrio") != null) {
            fechaIngresoPrio = session.getAttribute("fechaIngresoPrio").toString();
        }
        if (session.getAttribute("fechaAct") != null) {
            fechaAct = session.getAttribute("fechaAct").toString();
        }
        if (session.getAttribute("ht") != null) {
            ht = session.getAttribute("ht").toString();
        }
        if (session.getAttribute("nInvTutelar") != null) {
            nInvTutelar = session.getAttribute("nInvTutelar").toString();
        }
        if (session.getAttribute("fechaInvTutelar") != null) {
            fechaInvTutelar = session.getAttribute("fechaInvTutelar").toString();
        }
        if (session.getAttribute("procTutelar") != null) {
            procTutelar = session.getAttribute("procTutelar").toString();
        }
        if (session.getAttribute("respLegal") != null) {
            respLegal = session.getAttribute("respLegal").toString();
        }
        if (session.getAttribute("respPsico") != null) {
            respPsico = session.getAttribute("respPsico").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("fechaEstado") != null) {
            fechaEstado = session.getAttribute("fechaEstado").toString();
        }
        if (session.getAttribute("clasificacion") != null) {
            clasificacion = session.getAttribute("clasificacion").toString();
        }
        if (session.getAttribute("grpRef") != null) {
            grpRef = session.getAttribute("grpRef").toString();
        }
        if (session.getAttribute("codMayor") != null) {
            codMayor = session.getAttribute("codMayor").toString();
        }
        if (session.getAttribute("codAdoles") != null) {
            codAdoles = session.getAttribute("codAdoles").toString();
        }
        if (session.getAttribute("codHermano") != null) {
            codHermano = session.getAttribute("codHermano").toString();
        }
        if (session.getAttribute("codSalud") != null) {
            codSalud = session.getAttribute("codSalud").toString();
        }
        if (session.getAttribute("codEspeciales") != null) {
            codEspeciales = session.getAttribute("codEspeciales").toString();
        }
        if (session.getAttribute("diagnostico") != null) {
            diagnostico = session.getAttribute("diagnostico").toString();
        }
        if (session.getAttribute("comentario") != null) {
            comentario = session.getAttribute("comentario").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("ua");
        session.removeAttribute("numero");
        session.removeAttribute("idExpNna");
        session.removeAttribute("listaEval");
        session.removeAttribute("nombreActual");
        session.removeAttribute("apellidoPActual");
        session.removeAttribute("apellidoMActual");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("fechaIngresoPrio");
        session.removeAttribute("fechaAct");
        session.removeAttribute("ht");
        session.removeAttribute("nInvTutelar");
        session.removeAttribute("fechaInvTutelar");
        session.removeAttribute("procTutelar");
        session.removeAttribute("respLegal");
        session.removeAttribute("respPsico");
        session.removeAttribute("estado");
        session.removeAttribute("fechaEstado");
        session.removeAttribute("clasificacion");
        session.removeAttribute("grpRef");
        session.removeAttribute("codMayor");
        session.removeAttribute("codAdoles");
        session.removeAttribute("codHermano");
        session.removeAttribute("codSalud");
        session.removeAttribute("codEspeciales");
        session.removeAttribute("diagnostico");
        session.removeAttribute("comentario");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (listaEval != null) {
            map.addAttribute("idExpNna", idExpNna);
            map.addAttribute("clasificacion", clasificacion);
            return new ModelAndView("forward:/listaInformes", map);
        }
        Nna tempNna = new Nna();
        ExpedienteNna tempExp = new ExpedienteNna();
        tempExp = ServicioNna.getExpNna(idNna);
        tempNna = ServicioNna.getNna(idNna);
        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);
        tempExp.setUnidad(temp2);
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
        if (fechaIngresoPrio != null && !fechaIngresoPrio.equals("")) {
            tempExp.setFechaIngPrio(df.stringToDate(fechaIngresoPrio));
        } else if (fechaIngresoPrio == null || fechaIngresoPrio.equals("")) {
            tempExp.setFechaIngPrio(null);
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
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respLegal)) {
                    tempExp.setRespLegalP(pers.getNombre());
                    tempExp.setRespLegalM(pers.getApellidoP());
                }
            }
        }
        tempExp.setRespPsicosocialNombre(respPsico);
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respPsico)) {
                    tempExp.setRespPiscosocialP(pers.getNombre());
                    tempExp.setRespPsicosocialM(pers.getApellidoP());
                }
            }
        }
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
        tempExp.setObservaciones(comentario);

        if (clasificacion != null && clasificacion.equals("prioritario")) {
            if (codMayor != null) {
                tempExp.setCodigoReferencia(codMayor);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("0"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
            if (codAdoles != null) {
                tempExp.setCodigoReferencia(codAdoles);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("0"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
            if (codHermano != null) {
                tempExp.setCodigoReferencia(codHermano);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("0"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("1"));
            }
            if (codSalud != null) {
                tempExp.setCodigoReferencia(codSalud);
                tempNna.setEspecial(Short.parseShort("1"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("0"));
            }
            if (codEspeciales != null) {
                tempExp.setCodigoReferencia(codEspeciales);
                tempNna.setEspecial(Short.parseShort("0"));
                tempNna.setMayor(Short.parseShort("1"));
                tempNna.setHermano(Short.parseShort("1"));
                tempNna.setAdolescente(Short.parseShort("1"));
                tempNna.setEnfermo(Short.parseShort("1"));
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
        tempExp.setNacional(Short.parseShort("0"));
        if (clasificacion.equals("seguimiento")) {
            tempExp.setAdoptable(Short.parseShort("1"));
//            tempExp.setEstado("eval");
        }
//        Date utiDate = new Date();
//        java.sql.Date fechaAct = new java.sql.Date(utiDate.getTime());
//        tempExp.setFechaActualizacion(fechaAct);
        if (fechaAct != null && !fechaAct.equals("")) {
            tempExp.setFechaActualizacion(df.stringToDate(fechaAct));
        } else if (fechaAct == null || fechaAct.equals("")) {
            tempExp.setFechaActualizacion(null);
        }

        ServicioNna.updateExpNna(tempExp);

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";
        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Editó el expediente (" + tempExp.getIdexpedienteNna() + ") del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        return new ModelAndView("/Personal/nna/editar_nna", map);
    }

    //PROBAR
    @RequestMapping(value = "/updateExpedienteNna2", method = RequestMethod.POST)
    public ModelAndView updateExpedienteNna2_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("numero", numero);
        session.setAttribute("nombreActual", nombreActual);
        session.setAttribute("apellidoPActual", apellidoPActual);
        session.setAttribute("apellidoMActual", apellidoMActual);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("ht", ht);
        session.setAttribute("nInvTutelar", nInvTutelar);
        session.setAttribute("fechaInvTutelar", fechaInvTutelar);
        session.setAttribute("procTutelar", procTutelar);
        session.setAttribute("respLegal", respLegal);
        session.setAttribute("respPsico", respPsico);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaEstado", fechaEstado);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("grpRef", grpRef);
        session.setAttribute("codMayor", codMayor);
        session.setAttribute("codAdoles", codAdoles);
        session.setAttribute("codHermano", codHermano);
        session.setAttribute("codSalud", codSalud);
        session.setAttribute("codEspeciales", codEspeciales);
        session.setAttribute("diagnostico", diagnostico);
        session.setAttribute("comentario", comentario);

        return new ModelAndView("redirect:/updateExpedienteNna2", map);
    }

    @RequestMapping(value = "/updateExpedienteNna2", method = RequestMethod.GET)
    public ModelAndView updateExpedienteNna2_GET(ModelMap map, HttpSession session) {
        long idNna = 0;
        String numero = "";
        String nombreActual = "";
        String apellidoPActual = "";
        String apellidoMActual = "";
        String fechaIngreso = "";
        String ht = "";
        String nInvTutelar = "";
        String fechaInvTutelar = "";
        String procTutelar = "";
        String respLegal = "";
        String respPsico = "";
        String estado = "";
        String fechaEstado = "";
        String clasificacion = "";
        String grpRef = "";
        String codMayor = "";
        String codAdoles = "";
        String codHermano = "";
        String codSalud = "";
        String codEspeciales = "";
        String diagnostico = "";
        String comentario = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("numero");
            session.removeAttribute("nombreActual");
            session.removeAttribute("apellidoPActual");
            session.removeAttribute("apellidoMActual");
            session.removeAttribute("fechaIngreso");
            session.removeAttribute("ht");
            session.removeAttribute("nInvTutelar");
            session.removeAttribute("fechaInvTutelar");
            session.removeAttribute("procTutelar");
            session.removeAttribute("respLegal");
            session.removeAttribute("respPsico");
            session.removeAttribute("estado");
            session.removeAttribute("fechaEstado");
            session.removeAttribute("clasificacion");
            session.removeAttribute("grpRef");
            session.removeAttribute("codMayor");
            session.removeAttribute("codAdoles");
            session.removeAttribute("codHermano");
            session.removeAttribute("codSalud");
            session.removeAttribute("codEspeciales");
            session.removeAttribute("diagnostico");
            session.removeAttribute("comentario");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("numero") != null) {
            numero = session.getAttribute("numero").toString();
        }
        if (session.getAttribute("nombreActual") != null) {
            nombreActual = session.getAttribute("nombreActual").toString();
        }
        if (session.getAttribute("apellidoPActual") != null) {
            apellidoPActual = session.getAttribute("apellidoPActual").toString();
        }
        if (session.getAttribute("apellidoMActual") != null) {
            apellidoMActual = session.getAttribute("apellidoMActual").toString();
        }
        if (session.getAttribute("fechaIngreso") != null) {
            fechaIngreso = session.getAttribute("fechaIngreso").toString();
        }
        if (session.getAttribute("ht") != null) {
            ht = session.getAttribute("ht").toString();
        }
        if (session.getAttribute("nInvTutelar") != null) {
            nInvTutelar = session.getAttribute("nInvTutelar").toString();
        }
        if (session.getAttribute("fechaInvTutelar") != null) {
            fechaInvTutelar = session.getAttribute("fechaInvTutelar").toString();
        }
        if (session.getAttribute("procTutelar") != null) {
            procTutelar = session.getAttribute("procTutelar").toString();
        }
        if (session.getAttribute("respLegal") != null) {
            respLegal = session.getAttribute("respLegal").toString();
        }
        if (session.getAttribute("respPsico") != null) {
            respPsico = session.getAttribute("respPsico").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("fechaEstado") != null) {
            fechaEstado = session.getAttribute("fechaEstado").toString();
        }
        if (session.getAttribute("clasificacion") != null) {
            clasificacion = session.getAttribute("clasificacion").toString();
        }
        if (session.getAttribute("grpRef") != null) {
            grpRef = session.getAttribute("grpRef").toString();
        }
        if (session.getAttribute("codMayor") != null) {
            codMayor = session.getAttribute("codMayor").toString();
        }
        if (session.getAttribute("codAdoles") != null) {
            codAdoles = session.getAttribute("codAdoles").toString();
        }
        if (session.getAttribute("codHermano") != null) {
            codHermano = session.getAttribute("codHermano").toString();
        }
        if (session.getAttribute("codSalud") != null) {
            codSalud = session.getAttribute("codSalud").toString();
        }
        if (session.getAttribute("codEspeciales") != null) {
            codEspeciales = session.getAttribute("codEspeciales").toString();
        }
        if (session.getAttribute("diagnostico") != null) {
            diagnostico = session.getAttribute("diagnostico").toString();
        }
        if (session.getAttribute("comentario") != null) {
            comentario = session.getAttribute("comentario").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("numero");
        session.removeAttribute("nombreActual");
        session.removeAttribute("apellidoPActual");
        session.removeAttribute("apellidoMActual");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("ht");
        session.removeAttribute("nInvTutelar");
        session.removeAttribute("fechaInvTutelar");
        session.removeAttribute("procTutelar");
        session.removeAttribute("respLegal");
        session.removeAttribute("respPsico");
        session.removeAttribute("estado");
        session.removeAttribute("fechaEstado");
        session.removeAttribute("clasificacion");
        session.removeAttribute("grpRef");
        session.removeAttribute("codMayor");
        session.removeAttribute("codAdoles");
        session.removeAttribute("codHermano");
        session.removeAttribute("codSalud");
        session.removeAttribute("codEspeciales");
        session.removeAttribute("diagnostico");
        session.removeAttribute("comentario");

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
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respLegal)) {
                    tempExp.setRespLegalP(pers.getNombre());
                    tempExp.setRespLegalM(pers.getApellidoP());
                }
            }
        }
        tempExp.setRespPsicosocialNombre(respPsico);
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respPsico)) {
                    tempExp.setRespPiscosocialP(pers.getNombre());
                    tempExp.setRespPsicosocialM(pers.getApellidoP());
                }
            }
        }
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
        tempExp.setObservaciones(comentario);

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
        tempExp.setNacional(Short.parseShort("0"));
        if (clasificacion.equals("seguimiento")) {
            tempExp.setAdoptable(Short.parseShort("1"));
//            tempExp.setEstado("eval");
        }
        ServicioNna.updateExpNna(tempExp);

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";
        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Editó el expediente (" + tempExp.getIdexpedienteNna() + ") del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        return new ModelAndView("/Personal/Buscador/nna/editar_nna", map);
    }

    //PROBAR
    @RequestMapping(value = "/updateExpedienteNna4", method = RequestMethod.POST)
    public ModelAndView updateExpedienteNna4_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idNna", idNna);
        session.setAttribute("numero", numero);
        session.setAttribute("nombreActual", nombreActual);
        session.setAttribute("apellidoPActual", apellidoPActual);
        session.setAttribute("apellidoMActual", apellidoMActual);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("ht", ht);
        session.setAttribute("nInvTutelar", nInvTutelar);
        session.setAttribute("fechaInvTutelar", fechaInvTutelar);
        session.setAttribute("procTutelar", procTutelar);
        session.setAttribute("respLegal", respLegal);
        session.setAttribute("respPsico", respPsico);
        session.setAttribute("estado", estado);
        session.setAttribute("fechaEstado", fechaEstado);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("grpRef", grpRef);
        session.setAttribute("codMayor", codMayor);
        session.setAttribute("codAdoles", codAdoles);
        session.setAttribute("codHermano", codHermano);
        session.setAttribute("codSalud", codSalud);
        session.setAttribute("codEspeciales", codEspeciales);
        session.setAttribute("diagnostico", diagnostico);
        session.setAttribute("comentario", comentario);

        return new ModelAndView("redirect:/updateExpedienteNna4", map);
    }

    @RequestMapping(value = "/updateExpedienteNna4", method = RequestMethod.GET)
    public ModelAndView updateExpedienteNna4_GET(ModelMap map, HttpSession session) {
        long idNna = 0;
        String numero = "";
        String nombreActual = "";
        String apellidoPActual = "";
        String apellidoMActual = "";
        String fechaIngreso = "";
        String ht = "";
        String nInvTutelar = "";
        String fechaInvTutelar = "";
        String procTutelar = "";
        String respLegal = "";
        String respPsico = "";
        String estado = "";
        String fechaEstado = "";
        String clasificacion = "";
        String grpRef = "";
        String codMayor = "";
        String codAdoles = "";
        String codHermano = "";
        String codSalud = "";
        String codEspeciales = "";
        String diagnostico = "";
        String comentario = "";
        try {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
        } catch (Exception ex) {
            session.removeAttribute("idNna");
            session.removeAttribute("numero");
            session.removeAttribute("nombreActual");
            session.removeAttribute("apellidoPActual");
            session.removeAttribute("apellidoMActual");
            session.removeAttribute("fechaIngreso");
            session.removeAttribute("ht");
            session.removeAttribute("nInvTutelar");
            session.removeAttribute("fechaInvTutelar");
            session.removeAttribute("procTutelar");
            session.removeAttribute("respLegal");
            session.removeAttribute("respPsico");
            session.removeAttribute("estado");
            session.removeAttribute("fechaEstado");
            session.removeAttribute("clasificacion");
            session.removeAttribute("grpRef");
            session.removeAttribute("codMayor");
            session.removeAttribute("codAdoles");
            session.removeAttribute("codHermano");
            session.removeAttribute("codSalud");
            session.removeAttribute("codEspeciales");
            session.removeAttribute("diagnostico");
            session.removeAttribute("comentario");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("numero") != null) {
            numero = session.getAttribute("numero").toString();
        }
        if (session.getAttribute("nombreActual") != null) {
            nombreActual = session.getAttribute("nombreActual").toString();
        }
        if (session.getAttribute("apellidoPActual") != null) {
            apellidoPActual = session.getAttribute("apellidoPActual").toString();
        }
        if (session.getAttribute("apellidoMActual") != null) {
            apellidoMActual = session.getAttribute("apellidoMActual").toString();
        }
        if (session.getAttribute("fechaIngreso") != null) {
            fechaIngreso = session.getAttribute("fechaIngreso").toString();
        }
        if (session.getAttribute("ht") != null) {
            ht = session.getAttribute("ht").toString();
        }
        if (session.getAttribute("nInvTutelar") != null) {
            nInvTutelar = session.getAttribute("nInvTutelar").toString();
        }
        if (session.getAttribute("fechaInvTutelar") != null) {
            fechaInvTutelar = session.getAttribute("fechaInvTutelar").toString();
        }
        if (session.getAttribute("procTutelar") != null) {
            procTutelar = session.getAttribute("procTutelar").toString();
        }
        if (session.getAttribute("respLegal") != null) {
            respLegal = session.getAttribute("respLegal").toString();
        }
        if (session.getAttribute("respPsico") != null) {
            respPsico = session.getAttribute("respPsico").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("fechaEstado") != null) {
            fechaEstado = session.getAttribute("fechaEstado").toString();
        }
        if (session.getAttribute("clasificacion") != null) {
            clasificacion = session.getAttribute("clasificacion").toString();
        }
        if (session.getAttribute("grpRef") != null) {
            grpRef = session.getAttribute("grpRef").toString();
        }
        if (session.getAttribute("codMayor") != null) {
            codMayor = session.getAttribute("codMayor").toString();
        }
        if (session.getAttribute("codAdoles") != null) {
            codAdoles = session.getAttribute("codAdoles").toString();
        }
        if (session.getAttribute("codHermano") != null) {
            codHermano = session.getAttribute("codHermano").toString();
        }
        if (session.getAttribute("codSalud") != null) {
            codSalud = session.getAttribute("codSalud").toString();
        }
        if (session.getAttribute("codEspeciales") != null) {
            codEspeciales = session.getAttribute("codEspeciales").toString();
        }
        if (session.getAttribute("diagnostico") != null) {
            diagnostico = session.getAttribute("diagnostico").toString();
        }
        if (session.getAttribute("comentario") != null) {
            comentario = session.getAttribute("comentario").toString();
        }
        session.removeAttribute("idNna");
        session.removeAttribute("numero");
        session.removeAttribute("nombreActual");
        session.removeAttribute("apellidoPActual");
        session.removeAttribute("apellidoMActual");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("ht");
        session.removeAttribute("nInvTutelar");
        session.removeAttribute("fechaInvTutelar");
        session.removeAttribute("procTutelar");
        session.removeAttribute("respLegal");
        session.removeAttribute("respPsico");
        session.removeAttribute("estado");
        session.removeAttribute("fechaEstado");
        session.removeAttribute("clasificacion");
        session.removeAttribute("grpRef");
        session.removeAttribute("codMayor");
        session.removeAttribute("codAdoles");
        session.removeAttribute("codHermano");
        session.removeAttribute("codSalud");
        session.removeAttribute("codEspeciales");
        session.removeAttribute("diagnostico");
        session.removeAttribute("comentario");

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
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respLegal)) {
                    tempExp.setRespLegalP(pers.getNombre());
                    tempExp.setRespLegalM(pers.getApellidoP());
                }
            }
        }
        tempExp.setRespPsicosocialNombre(respPsico);
        for (Personal pers : ServicioPersonal.ListaPersonal()) {
            if (pers.getDni() != null) {
                if (pers.getDni().equals(respPsico)) {
                    tempExp.setRespPiscosocialP(pers.getNombre());
                    tempExp.setRespPsicosocialM(pers.getApellidoP());
                }
            }
        }
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
        tempExp.setObservaciones(comentario);

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
        tempExp.setNacional(Short.parseShort("0"));
        if (clasificacion.equals("seguimiento")) {
            tempExp.setAdoptable(Short.parseShort("1"));
//            tempExp.setEstado("eval");
        }
        ServicioNna.updateExpNna(tempExp);

        String tempfechaNac = "";
        String tempfechaResolAban = "";
        String tempfechaResolCon = "";
        try {
            tempfechaNac = df.dateToString(tempNna.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaNac", tempfechaNac);
        try {
            tempfechaResolAban = df.dateToString(tempNna.getFechaResolAbandono());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolAband", tempfechaResolAban);
        try {
            tempfechaResolCon = df.dateToString(tempNna.getFechaResolConsentida());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaResolConsen", tempfechaResolCon);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        map.put("listaCar", ServicioPersonal.ListaCar());
        map.put("nna", ServicioNna.getNna(idNna));

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Editó el expediente (" + tempExp.getIdexpedienteNna() + ") del NNA con Nombre, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(tempNna.getIdnna());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
        return new ModelAndView("/Personal/nna/editar_nna_1", map);
    }

    @RequestMapping(value = "/listaInformes", method = RequestMethod.POST)
    public ModelAndView listaInformes(ModelMap map, HttpSession session,
            @RequestParam(value = "idExpNna") Long idExpNna,
            @RequestParam(value = "clasificacion") String clasificacion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("clasificacion", clasificacion);
        map.put("listaInformes", ServicioNna.listaInformesExpNna(idExpNna));
        map.put("idExpNna", idExpNna);
        return new ModelAndView("/Personal/nna/lista_informes", map);
    }

    @RequestMapping(value = "/NnaAgregarInforme", method = RequestMethod.POST)
    public ModelAndView NnaAgregarInforme(ModelMap map, HttpSession session, @RequestParam(value = "idExpNna") Long idExpNna, @RequestParam(value = "clasificacion") String clasificacion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("clasificacion", clasificacion);
        map.put("idExpNna", idExpNna);
        return new ModelAndView("/Personal/nna/editar_informe", map);
    }

    //PROBAR
    @RequestMapping(value = "/NnaCrearInforme", method = RequestMethod.POST)
    public ModelAndView NnaCrearInforme_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "idExpNna") Long idExpNna,
            @RequestParam(value = "clasificacion") String clasificacion,
            @RequestParam(value = "numInf") String numInf,
            @RequestParam(value = "fechaInf", required = false) String fechaInf,
            @RequestParam(value = "result", required = false) String result,
            @RequestParam(value = "obs", required = false) String obs) {
        session.setAttribute("idExpNna", idExpNna);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("numInf", numInf);
        session.setAttribute("fechaInf", fechaInf);
        session.setAttribute("result", result);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/NnaCrearInforme", map);
    }

    @RequestMapping(value = "/NnaCrearInforme", method = RequestMethod.GET)
    public ModelAndView NnaCrearInforme_GET(ModelMap map, HttpSession session) {
        long idExpNna = 0;
        String clasificacion = "";
        String numInf = "";
        String fechaInf = "";
        String result = "";
        String obs = "";
        try {
            idExpNna = Long.parseLong(session.getAttribute("idExpNna").toString());
            clasificacion = session.getAttribute("clasificacion").toString();
            numInf = session.getAttribute("numInf").toString();
        } catch (Exception ex) {
            session.removeAttribute("idExpNna");
            session.removeAttribute("clasificacion");
            session.removeAttribute("numInf");
            session.removeAttribute("fechaInf");
            session.removeAttribute("result");
            session.removeAttribute("obs");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("fechaInf") != null) {
            fechaInf = session.getAttribute("fechaInf").toString();
        }
        if (session.getAttribute("result") != null) {
            result = session.getAttribute("result").toString();
        }
        if (session.getAttribute("obs") != null) {
            obs = session.getAttribute("obs").toString();
        }
        session.removeAttribute("idExpNna");
        session.removeAttribute("clasificacion");
        session.removeAttribute("numInf");
        session.removeAttribute("fechaInf");
        session.removeAttribute("result");
        session.removeAttribute("obs");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (numInf == null || numInf.equals("")) {
            String mensaje = "Debe ingresar un número de informe";
            map.put("mensaje", mensaje);
            map.put("clasificacion", clasificacion);
            map.put("idExpNna", idExpNna);
            return new ModelAndView("/Personal/nna/editar_informe", map);
        }
        InformeNna informe = new InformeNna();
        informe.setNumero(numInf);
        if (fechaInf != null && !fechaInf.equals("")) {
            informe.setFecha(df.stringToDate(fechaInf));
        } else {
            informe.setFecha(null);
        }

        informe.setResultado(result);
        informe.setObservaciones(obs);

        ServicioNna.crearInforme(informe, idExpNna);

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Agregó un informe al expediente del NNA con  ID de expediente: " + idExpNna;

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(idExpNna);

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("df", df);
        map.put("clasificacion", clasificacion);
        map.put("idExpNna", idExpNna);
        map.put("listaInformes", ServicioNna.listaInformesExpNna(idExpNna));
        return new ModelAndView("/Personal/nna/lista_informes", map);
    }

    @RequestMapping(value = "/NnaEditarInforme", method = RequestMethod.POST)
    public ModelAndView NnaEditarInforme(ModelMap map, HttpSession session,
            @RequestParam(value = "idInf") Long idInf,
            @RequestParam(value = "idExpNna") Long idExpNna,
            @RequestParam(value = "clasificacion") String clasificacion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        InformeNna tempInf = new InformeNna();
        tempInf = ServicioNna.InformeExpNna(idInf);
        map.put("df", df);
        map.put("informe", tempInf);
        map.put("clasificacion", clasificacion);
        map.put("idExpNna", idExpNna);
        return new ModelAndView("/Personal/nna/editar_informe", map);
    }

    //PROBAR
    @RequestMapping(value = "/NnaUpdateInforme", method = RequestMethod.POST)
    public ModelAndView NnaUpdateInforme_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "idExpNna") Long idExpNna,
            @RequestParam(value = "clasificacion") String clasificacion,
            @RequestParam(value = "idInf") Long idInf,
            @RequestParam(value = "numInf") String numInf,
            @RequestParam(value = "fechaInf", required = false) String fechaInf,
            @RequestParam(value = "result", required = false) String result,
            @RequestParam(value = "obs", required = false) String obs) {
        session.setAttribute("idExpNna", idExpNna);
        session.setAttribute("clasificacion", clasificacion);
        session.setAttribute("idInf",idInf);
        session.setAttribute("numInf", numInf);
        session.setAttribute("fechaInf", fechaInf);
        session.setAttribute("result", result);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/NnaUpdateInforme", map);
    }
    
    @RequestMapping(value = "/NnaUpdateInforme", method = RequestMethod.GET)
    public ModelAndView NnaUpdateInforme_GET(ModelMap map, HttpSession session) {
        long idExpNna = 0;
        String clasificacion = "";
        long idInf = 0;
        String numInf = "";
        String fechaInf = "";
        String result = "";
        String obs = "";
        try {
            idExpNna = Long.parseLong(session.getAttribute("idExpNna").toString());
            clasificacion = session.getAttribute("clasificacion").toString();
            idInf =Long.parseLong(session.getAttribute("idEInf").toString());
            numInf = session.getAttribute("numInf").toString();
        } catch (Exception ex) {
            session.removeAttribute("idExpNna");
            session.removeAttribute("clasificacion");
            session.removeAttribute("idInf");
            session.removeAttribute("numInf");
            session.removeAttribute("fechaInf");
            session.removeAttribute("result");
            session.removeAttribute("obs");

            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("fechaInf") != null) {
            fechaInf = session.getAttribute("fechaInf").toString();
        }
        if (session.getAttribute("result") != null) {
            result = session.getAttribute("result").toString();
        }
        if (session.getAttribute("obs") != null) {
            obs = session.getAttribute("obs").toString();
        }
        session.removeAttribute("idExpNna");
        session.removeAttribute("clasificacion");
        session.removeAttribute("idInf");
        session.removeAttribute("numInf");
        session.removeAttribute("fechaInf");
        session.removeAttribute("result");
        session.removeAttribute("obs");
        
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (numInf == null || numInf.equals("")) {
            String mensaje = "Debe ingresar un número de informe";
            map.put("mensaje", mensaje);
            map.put("clasificacion", clasificacion);
            map.put("idExpNna", idExpNna);
            return new ModelAndView("/Personal/nna/editar_informe", map);
        }
        InformeNna informe = new InformeNna();
        informe = ServicioNna.InformeExpNna(idInf);
        informe.setNumero(numInf);
        if (fechaInf != null && !fechaInf.equals("")) {
            informe.setFecha(df.stringToDate(fechaInf));
        } else {
            informe.setFecha(null);
        }

        informe.setResultado(result);
        informe.setObservaciones(obs);

        ServicioNna.updateInforme(informe);

        String mensaje_log = "El usuario: " + usuario.getNombre() + " " + usuario.getApellidoP()
                + " con ID: " + usuario.getIdpersonal() + ". Editó un informe al expediente del NNA con  ID de expediente: " + idExpNna;

        String Tipo_registro = "NNA";

        try {
            String Numero_registro = String.valueOf(idExpNna);

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("df", df);
        map.put("clasificacion", clasificacion);
        map.put("idExpNna", idExpNna);
        map.put("listaInformes", ServicioNna.listaInformesExpNna(idExpNna));
        return new ModelAndView("/Personal/nna/lista_informes", map);
    }
}
