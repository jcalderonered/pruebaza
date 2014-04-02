/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.util.*;
import com.mimp.hibernate.*;
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
public class main {

    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name = "HiberMain")
    private HiberMain ServicioMain = new HiberMain();
    @Resource(name = "HiberNna")
    private HiberNna ServicioNna = new HiberNna();
    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();
    @Resource(name = "HiberMail")
    private HiberMail hibermail = new HiberMail();

    /* PARAMETROS A PASAR A FAMILIA DENTRO DE PERSONAL */
    String etapaOrigen;
    Adoptante El = new Adoptante();
    Adoptante Ella = new Adoptante();
    ArrayList<Sesion> listaSesiones = new ArrayList();
    ArrayList<AsistenciaFR> listaAsistenciaReuniones = new ArrayList();
    ArrayList<Atencion> listaAtenciones = new ArrayList();
    ArrayList<Evaluacion> listaEvaluaciones = new ArrayList();
    ArrayList<Designacion> listaDesignaciones = new ArrayList();
    ArrayList<EstudioCaso> listaEstudios = new ArrayList();
    ArrayList<Nna> listaNnaRevision = new ArrayList();
    ArrayList<Object> listaRevision = new ArrayList();
    ArrayList<Nna> listaNnaEstudio = new ArrayList();
    ArrayList<Nna> listaNnaAdoptados = new ArrayList();
    ArrayList<ExpedienteFamilia> listaFamiliasEstudio = new ArrayList();
    InfoFamilia infoFam = new InfoFamilia();
    ExpedienteFamilia expediente = new ExpedienteFamilia();
    Nna nnaAdoptado = new Nna();
    ExpedienteNna expNna = new ExpedienteNna();
    Familia famtemp = new Familia();
    String volver;
    /*      */

    @RequestMapping("/")
    public String hello() {
        return "login";
    }

    @RequestMapping("/Contacto")
    public ModelAndView Contacto(ModelMap map, HttpSession session) {
        session.invalidate();
        String pagina = "contacto";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/salir")
    public ModelAndView Salir(ModelMap map, HttpSession session) {
        session.invalidate();
        String pagina = "login";
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/inicio")
    public String Inicio() {
        return "login";
    }

    //LISTO
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login_POST(ModelMap map, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = false) String pass, HttpSession session) {

        session.setAttribute("email", email);
        session.setAttribute("password", pass);

        return new ModelAndView("redirect:/login", map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login_GET(ModelMap map, HttpSession session) {

        String email = "";
        String pass = "";

        String pagina = "login";

        if (session.getAttribute("email") != null) {

            email = session.getAttribute("email").toString();
            pass = session.getAttribute("password").toString();

            String mensaje = "El usuario se encuentra Deshabilitado. Favor contactar a la Dirección General de Adopciones para más información";

            ArrayList aux = ServicioMain.usuario(email, pass);
            if (aux.get(0) == "personal") {
                Personal personal = (Personal) aux.get(1);
                if (!personal.getRol().equals("Inactivo")) {
                    session.setAttribute("usuario", personal);
                    session.setMaxInactiveInterval(900);
                    pagina = "/Personal/inicio_personal";

                    String mensaje_log = "El usuario, " + personal.getUser() + " con ID: " + personal.getIdpersonal() + ". Ingresó al sistema.";
                    String Tipo_registro = "Login";

                    try {
                        String Numero_registro = String.valueOf(personal.getIdpersonal());

                        ServicioPersonal.InsertLog(personal, Tipo_registro, Numero_registro, mensaje_log);
                    } catch (Exception ex) {
                    }

                } else {
                    map.addAttribute("mensaje", mensaje);
                    pagina = "login";
                }
            } else if (aux.get(0) == "familia") {
                Familia familia = (Familia) aux.get(1);
                if (familia.getHabilitado() == 0) {
                    session.setAttribute("usuario", familia);
                    session.setMaxInactiveInterval(900);
                    pagina = "/Familia/inicio_familia";
                } else {
                    map.addAttribute("mensaje", mensaje);
                    pagina = "login";
                }
            } else if (aux.get(0) == "representante" || aux.get(0) == "autoridad") {
                Entidad entidad = (Entidad) aux.get(1);
                session.setAttribute("usuario", entidad);
                session.setMaxInactiveInterval(900);
                pagina = "/Entidad/inicio_ent";
            } else if (email.equals("") || pass.equals("")) {
                mensaje = "Por favor llenar ambos campos";
                map.addAttribute("mensaje", mensaje);
                pagina = "login";
            } else {
                mensaje = "Usuario y/o contraseña incorrectos";
                map.addAttribute("mensaje", mensaje);
                pagina = "login";
            }

        } else {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            pagina = "login";

        }

        session.removeAttribute("email");
        session.removeAttribute("password");

        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/SesionInfInicioPrev")
    public String SesionInfInicioPrev(ModelMap map) {
        map.put("listaUa", ServicioPersonal.ListaUa());
        return "/Inscripcion/inscripcion_prev";
    }

    //LISTO
    @RequestMapping(value = "/SesionInfInicio", method = RequestMethod.POST)
    public ModelAndView SesionInfInicio_POST(ModelMap map, @RequestParam("ua") String ua, HttpSession session) {

        session.setAttribute("ua", ua);

        return new ModelAndView("redirect:/SesionInfInicio", map);
    }

    @RequestMapping(value = "/SesionInfInicio", method = RequestMethod.GET)
    public ModelAndView SesionInfInicio_GET(ModelMap map, HttpSession session) {

        String ua = "";
        try {
            ua = session.getAttribute("ua").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute("ua");

        ArrayList<Turno> temp = new ArrayList();
        ArrayList<Turno> temp2 = new ArrayList();
        Date now = new Date();
        temp = ServicioMain.ListaTurnos(ua);
        String pagina = "";
        //String fecha = ts.TimeToString(temp.get(0).getInicioInscripcion());
        if (temp.isEmpty()) {
            pagina = "/Inscripcion/no_sesion_prog";
            return new ModelAndView(pagina);
        } else {
            for (int i = 0; i < temp.size(); i++) {
                if (now.after((Date) temp.get(i).getInicioInscripcion()) && now.before((Date) temp.get(i).getFinInscripcion())) {
                    if (i == temp.size() - 1 && temp.get(i).getAsistenciaFTs().size() >= temp.get(i).getVacantes()) {
                        ArrayList<Sesion> tempSesiones = new ArrayList();
                        tempSesiones = ServicioMain.listaSesionesSiguientes(temp.get(i).getSesion().getFecha());
                        if (tempSesiones.isEmpty()) {
                            pagina = "/Inscripcion/no_sesion_prog";
                            return new ModelAndView(pagina);
                        } else {
                            ArrayList<Turno> tempTurnos = new ArrayList();
                            tempTurnos = ServicioMain.turnosSesion(tempSesiones.get(0).getIdsesion());
                            if (tempTurnos.isEmpty()) {
                                pagina = "/Inscripcion/no_sesion_prog";
                                return new ModelAndView(pagina);
                            } else {
                                map.put("ts", ts);
                                map.put("listaSesiones", tempSesiones);
                                map.put("listaTurnos", tempTurnos);
                                pagina = "/Inscripcion/inscripcion_sesion1a";
                                return new ModelAndView(pagina, map);
                            }
                        }
                    } else if (temp.get(i).getAsistenciaFTs().size() >= temp.get(i).getVacantes()) {
                        for (int j = i + 1; j < temp.size(); j++) {
                            temp2.add(temp.get(j));
                        }
                        pagina = "/Inscripcion/inscripcion_sesion1b";
                        map.put("ts", ts);
                        map.put("listaTurnos", temp2);
                        return new ModelAndView(pagina, map);
                    }
                }
            }
        }
        if (temp.get(0).getSesion().getHabilitado() == 0) {
            pagina = "/Inscripcion/inscripcion_inicio";
            map.put("ts", ts);
            map.put("listaTurnos", temp);
            return new ModelAndView(pagina, map);
        }
        pagina = "/Inscripcion/no_sesion_prog";
        return new ModelAndView(pagina);
    }

    //LISTO
    @RequestMapping(value = "/SesionInfElegirEstado", method = RequestMethod.POST)
    public ModelAndView SesionInfElegirEstado_POST(ModelMap map, @RequestParam("idTurno") int turno, HttpSession session) {

        session.setAttribute("idTurno", turno);
        return new ModelAndView("redirect:/SesionInfElegirEstado", map);

    }

    @RequestMapping(value = "/SesionInfElegirEstado", method = RequestMethod.GET)
    public ModelAndView SesionInfElegirEstado_GET(ModelMap map, HttpSession session) {

        int turno = 0;
        try {
            turno = Integer.parseInt(session.getAttribute("idTurno").toString());
        } catch (Exception ex) {
            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute("idTurno");

        map.addAttribute("idTurno", turno);
        return new ModelAndView("/Inscripcion/inscripcion_sesion2", map);

    }

    //LISTO
    @RequestMapping(value = "/SesionInfEstado2", method = RequestMethod.POST)
    public ModelAndView SesionInfElegirEstado2_POST(ModelMap map, @RequestParam("estado") String estado, @RequestParam("idTurno") int turno, HttpSession session) {

        session.setAttribute("estado", estado);
        session.setAttribute("turno", turno);

        return new ModelAndView("redirect:/SesionInfEstado2", map);
    }

    @RequestMapping(value = "/SesionInfEstado2", method = RequestMethod.GET)
    public ModelAndView SesionInfElegirEstado2_GET(ModelMap map, HttpSession session) {
        int turno = 0;
        String estado = "";
        try {
            turno = Integer.parseInt(session.getAttribute("turno").toString());
            estado = session.getAttribute("estado").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute("estado");
        session.removeAttribute("turno");

        Turno temp = ServicioMain.getTurno(turno);

        if (estado.equals("casados")) {
            map.addAttribute("idTurno", turno);
            map.put("estado", estado);
            map.put("turno", temp);
            return new ModelAndView("/Inscripcion/inscripcion_sesion3_gru", map);

        } else {

            map.addAttribute("idTurno", turno);
            map.put("estado", estado);
            map.put("turno", temp);
            return new ModelAndView("/Inscripcion/inscripcion_sesion3_ind", map);

        }

    }

    //LISTO
    @RequestMapping(value = "/inscSesInd", method = RequestMethod.POST)
    public ModelAndView inscSesInd_POST(ModelMap map,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidoP") String apellidoP,
            @RequestParam("apellidoM") String apellidoM,
            @RequestParam("paisNac") String paisNac,
            @RequestParam("depNac") String depNac,
            @RequestParam("proNac") String proNac,
            @RequestParam("fechaNac") String fechaNac,
            @RequestParam("edad") String edad,
            @RequestParam("doc") String doc,
            @RequestParam("numDoc") String numDoc,
            @RequestParam("profesion") String profesion,
            @RequestParam("cel") String cel,
            @RequestParam("correo") String correo,
            @RequestParam("pais") String pais,
            @RequestParam("dep") String dep,
            @RequestParam("prov") String prov,
            @RequestParam("dist") String dist,
            @RequestParam("dir") String dir,
            @RequestParam("telf") String telf,
            @RequestParam("estado") String estado,
            @RequestParam("idTurno") long turno,
            HttpSession session
    ) {
        session.setAttribute("nombre", nombre);
        session.setAttribute("apellidoP", apellidoP);
        session.setAttribute("apellidoM", apellidoM);
        session.setAttribute("paisNac", paisNac);
        session.setAttribute("depNac", depNac);
        session.setAttribute("proNac", proNac);
        session.setAttribute("fechaNac", fechaNac);
        session.setAttribute("edad", edad);
        session.setAttribute("doc", doc);
        session.setAttribute("numDoc", numDoc);
        session.setAttribute("profesion", profesion);
        session.setAttribute("cel", cel);
        session.setAttribute("correo", correo);
        session.setAttribute("pais", pais);
        session.setAttribute("dep", dep);
        session.setAttribute("prov", prov);
        session.setAttribute("dist", dist);
        session.setAttribute("dir", dir);
        session.setAttribute("telf", telf);
        session.setAttribute("estado", estado);
        session.setAttribute("turno", turno);

        return new ModelAndView("redirect:/inscSesInd", map);
    }

    @RequestMapping(value = "/inscSesInd", method = RequestMethod.GET)
    public ModelAndView inscSesInd_GET(ModelMap map,
            HttpSession session
    ) {
        long turno = 0;
        String estado = "";
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String paisNac = "";
        String depNac = "";
        String proNac = "";
        String fechaNac = "";
        String edad = "";
        String doc = "";
        String numDoc = "";
        String profesion = "";
        String cel = "";
        String correo = "";
        String pais = "";
        String dep = "";
        String prov = "";
        String dist = "";
        String dir = "";
        String telf = "";
        try {
            turno = Long.parseLong(session.getAttribute("turno").toString());
            estado = session.getAttribute("estado").toString();
            nombre = session.getAttribute("nombre").toString();
            apellidoP = session.getAttribute("apellidoP").toString();
            apellidoM = session.getAttribute("apellidoM").toString();
            paisNac = session.getAttribute("paisNac").toString();
            depNac = session.getAttribute("depNac").toString();
            proNac = session.getAttribute("proNac").toString();
            fechaNac = session.getAttribute("fechaNac").toString();
            edad = session.getAttribute("edad").toString();
            doc = session.getAttribute("doc").toString();
            numDoc = session.getAttribute("numDoc").toString();
            profesion = session.getAttribute("profesion").toString();
            cel = session.getAttribute("cel").toString();
            correo = session.getAttribute("correo").toString();
            pais = session.getAttribute("pais").toString();
            dep = session.getAttribute("dep").toString();
            prov = session.getAttribute("prov").toString();
            dist = session.getAttribute("dist").toString();
            dir = session.getAttribute("dir").toString();
            telf = session.getAttribute("telf").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute("estado");
        session.removeAttribute("turno");
        session.removeAttribute("nombre");
        session.removeAttribute("apellidoP");
        session.removeAttribute("apellidoM");
        session.removeAttribute("paisNac");
        session.removeAttribute("depNac");
        session.removeAttribute("proNac");
        session.removeAttribute("fechaNac");
        session.removeAttribute("edad");
        session.removeAttribute("doc");
        session.removeAttribute("numDoc");
        session.removeAttribute("profesion");
        session.removeAttribute("cel");
        session.removeAttribute("correo");
        session.removeAttribute("pais");
        session.removeAttribute("dep");
        session.removeAttribute("prov");
        session.removeAttribute("dist");
        session.removeAttribute("dir");
        session.removeAttribute("telf");

        Turno temp = ServicioMain.getTurno(turno);
        FormularioSesion fs = new FormularioSesion();
        Asistente asis = new Asistente();
        AsistenciaFT aft = new AsistenciaFT();
        fs.setSesion(temp.getSesion());
        aft.setTurno(temp);
        String asistencia = "F";
        char asist = asistencia.charAt(0);
        aft.setAsistencia(asist);
        String inajust = "1";
        Short i = Short.valueOf(inajust);
        aft.setInasJus(i);

        Date today = new Date();

        fs.setFechaSol(today);
        fs.setPaisRes(pais);
        fs.setDepRes(dep);
        fs.setProvRes(prov);
        fs.setDistritoRes(dist);
        fs.setDireccionRes(dir);
        fs.setTelefono(telf);
        fs.setEstadoCivil(estado);
        String sexo = "";
        if (estado.equals("soltero") || estado.equals("viudo") || estado.equals("divorciado")) {
            sexo = "m";
        }
        if (estado.equals("soltera") || estado.equals("viuda") || estado.equals("divorciada")) {
            sexo = "f";
        }
        char s = sexo.charAt(0);
        asis.setSexo(s);
        asis.setNombre(nombre);
        asis.setApellidoP(apellidoP);
        asis.setApellidoM(apellidoM);
        asis.setPaisNac(paisNac);
        asis.setDepNac(depNac);
        asis.setProvNac(proNac);
        short b = Byte.valueOf(edad);
        asis.setEdad(b);
        if (fechaNac != null && !fechaNac.equals("")) {
            asis.setFechaNac(df.stringToDate(fechaNac));
        }
        char c = doc.charAt(0);
        asis.setTipoDoc(c);
        asis.setNDoc(numDoc);
        asis.setProfesion(profesion);
        asis.setCelular(cel);
        asis.setCorreo(correo);

        ArrayList<Asistente> tempList = new ArrayList();
        tempList = ServicioMain.listaAsistentes(temp.getSesion().getIdsesion());
        if (!tempList.isEmpty()) {
            for (Asistente asistente : tempList) {
                if (asistente.getNDoc().equals(numDoc)) {
                    map.put("mensaje", "inscrito");
                    return new ModelAndView("/Inscripcion/inscripcion_sesion1b", map);
                }

            }
        }
        if (temp.getVacantes() > temp.getAsistenciaFTs().size()) {
            map.put("ts", ts);
            map.put("turno", temp);
            ServicioMain.InsertFormInd(asis, fs, aft);
            return new ModelAndView("/Inscripcion/inscripcion_sesion4", map);
        } else {
            map.put("ts", ts);
            map.put("turno", temp);
            //ServicioMain.InsertFormInd(asis, fs, aft);
            return new ModelAndView("/Inscripcion/inscripcion_sesion1b", map);
        }
        //return new ModelAndView("contacto", map);
    }

    @RequestMapping(value = "/inscSesGrp", method = RequestMethod.POST)
    public ModelAndView inscSesGrp_POST(ModelMap map,
            @RequestParam("nombreEl") String nombreEl,
            @RequestParam("apellidoPEl") String apellidoPEl,
            @RequestParam("apellidoMEl") String apellidoMEl,
            @RequestParam("paisNacEl") String paisNacEl,
            @RequestParam("depNacEl") String depNacEl,
            @RequestParam("proNacEl") String proNacEl,
            @RequestParam("fechaNacEl") String fechaNacEl,
            @RequestParam("edadEl") String edadEl,
            @RequestParam("docEl") String docEl,
            @RequestParam("numDocEl") String numDocEl,
            @RequestParam("profesionEl") String profesionEl,
            @RequestParam("celEl") String celEl,
            @RequestParam("correoEl") String correoEl,
            @RequestParam("nombreElla") String nombreElla,
            @RequestParam("apellidoPElla") String apellidoPElla,
            @RequestParam("apellidoMElla") String apellidoMElla,
            @RequestParam("paisNacElla") String paisNacElla,
            @RequestParam("depNacElla") String depNacElla,
            @RequestParam("proNacElla") String proNacElla,
            @RequestParam("fechaNacElla") String fechaNacElla,
            @RequestParam("edadElla") String edadElla,
            @RequestParam("docElla") String docElla,
            @RequestParam("numDocElla") String numDocElla,
            @RequestParam("profesionElla") String profesionElla,
            @RequestParam("celELla") String celElla,
            @RequestParam("correoElla") String correoElla,
            @RequestParam("pais") String pais,
            @RequestParam("dep") String dep,
            @RequestParam("prov") String prov,
            @RequestParam("dist") String dist,
            @RequestParam("dir") String dir,
            @RequestParam("telf") String telf,
            @RequestParam("estado") String estado,
            @RequestParam("idTurno") int turno,
            HttpSession session
    ) {
        session.setAttribute("nombreEl", nombreEl);
        session.setAttribute("apellidoPEl", apellidoPEl);
        session.setAttribute("apellidoMEl", apellidoMEl);
        session.setAttribute("paisNacEl", paisNacEl);
        session.setAttribute("depNacEl", depNacEl);
        session.setAttribute("proNacEl", proNacEl);
        session.setAttribute("fechaNacEl", fechaNacEl);
        session.setAttribute("edadEl", edadEl);
        session.setAttribute("docEl", docEl);
        session.setAttribute("numDocEl", numDocEl);
        session.setAttribute("profesionEl", profesionEl);
        session.setAttribute("celEl", celEl);
        session.setAttribute("correoEl", correoEl);
        session.setAttribute("nombreElla", nombreElla);
        session.setAttribute("apellidoPElla", apellidoPElla);
        session.setAttribute("apellidoMElla", apellidoMElla);
        session.setAttribute("paisNacElla", paisNacElla);
        session.setAttribute("depNacElla", depNacElla);
        session.setAttribute("proNacElla", proNacElla);
        session.setAttribute("fechaNacElla", fechaNacElla);
        session.setAttribute("edadElla", edadElla);
        session.setAttribute("docElla", docElla);
        session.setAttribute("numDocElla", numDocElla);
        session.setAttribute("profesionElla", profesionElla);
        session.setAttribute("celElla", celElla);
        session.setAttribute("correoElla", correoElla);
        session.setAttribute("pais", pais);
        session.setAttribute("dep", dep);
        session.setAttribute("prov", prov);
        session.setAttribute("dist", dist);
        session.setAttribute("dir", dir);
        session.setAttribute("telf", telf);
        session.setAttribute("estado", estado);
        session.setAttribute("turno", turno);

        return new ModelAndView("redirect:/inscSesGrp", map);
    }

    @RequestMapping(value = "/inscSesGrp", method = RequestMethod.GET)
    public ModelAndView inscSesGrp_GET(ModelMap map,
            HttpSession session
    ) {
        int turno = 0;
        String estado = "";
        String nombreEl = "";
        String apellidoPEl = "";
        String apellidoMEl = "";
        String paisNacEl = "";
        String depNacEl = "";
        String proNacEl = "";
        String fechaNacEl = "";
        String edadEl = "";
        String docEl = "";
        String numDocEl = "";
        String profesionEl = "";
        String celEl = "";
        String correoEl = "";
        String nombreElla = "";
        String apellidoPElla = "";
        String apellidoMElla = "";
        String paisNacElla = "";
        String depNacElla = "";
        String proNacElla = "";
        String fechaNacElla = "";
        String edadElla = "";
        String docElla = "";
        String numDocElla = "";
        String profesionElla = "";
        String celElla = "";
        String correoElla = "";
        String pais = "";
        String dep = "";
        String prov = "";
        String dist = "";
        String dir = "";
        String telf = "";
        try {
            turno = Integer.parseInt(session.getAttribute("turno").toString());
            estado = session.getAttribute("estado").toString();
            nombreEl = session.getAttribute("nombreEl").toString();
            apellidoPEl = session.getAttribute("apellidoPEl").toString();
            apellidoMEl = session.getAttribute("apellidoMEl").toString();
            paisNacEl = session.getAttribute("paisNacEl").toString();
            depNacEl = session.getAttribute("depNacEl").toString();
            proNacEl = session.getAttribute("proNacEl").toString();
            fechaNacEl = session.getAttribute("fechaNacEl").toString();
            edadEl = session.getAttribute("edadEl").toString();
            docEl = session.getAttribute("docEl").toString();
            numDocEl = session.getAttribute("numDocEl").toString();
            profesionEl = session.getAttribute("profesionEl").toString();
            celEl = session.getAttribute("celEl").toString();
            correoEl = session.getAttribute("correoEl").toString();
            nombreElla = session.getAttribute("nombreElla").toString();
            apellidoPElla = session.getAttribute("apellidoPElla").toString();
            apellidoMElla = session.getAttribute("apellidoMElla").toString();
            paisNacElla = session.getAttribute("paisNacElla").toString();
            depNacElla = session.getAttribute("depNacElla").toString();
            proNacElla = session.getAttribute("proNacElla").toString();
            fechaNacElla = session.getAttribute("fechaNacElla").toString();
            edadElla = session.getAttribute("edadElla").toString();
            docElla = session.getAttribute("docElla").toString();
            numDocElla = session.getAttribute("numDocElla").toString();
            profesionElla = session.getAttribute("profesionElla").toString();
            celElla = session.getAttribute("celElla").toString();
            correoElla = session.getAttribute("correoElla").toString();
            pais = session.getAttribute("pais").toString();
            dep = session.getAttribute("dep").toString();
            prov = session.getAttribute("prov").toString();
            dist = session.getAttribute("dist").toString();
            dir = session.getAttribute("dir").toString();
            telf = session.getAttribute("telf").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute("estado");
        session.removeAttribute("turno");
        session.removeAttribute("nombreEl");
        session.removeAttribute("apellidoPEl");
        session.removeAttribute("apellidoMEl");
        session.removeAttribute("paisNacEl");
        session.removeAttribute("depNacEl");
        session.removeAttribute("proNacEl");
        session.removeAttribute("fechaNacEl");
        session.removeAttribute("edadEl");
        session.removeAttribute("docEl");
        session.removeAttribute("numDocEl");
        session.removeAttribute("profesionEl");
        session.removeAttribute("celEl");
        session.removeAttribute("correoEl");
        session.removeAttribute("nombreElla");
        session.removeAttribute("apellidoPElla");
        session.removeAttribute("apellidoMElla");
        session.removeAttribute("paisNacElla");
        session.removeAttribute("depNacElla");
        session.removeAttribute("proNacElla");
        session.removeAttribute("fechaNacElla");
        session.removeAttribute("edadElla");
        session.removeAttribute("docElla");
        session.removeAttribute("numDocElla");
        session.removeAttribute("profesionElla");
        session.removeAttribute("celElla");
        session.removeAttribute("correoElla");
        session.removeAttribute("pais");
        session.removeAttribute("dep");
        session.removeAttribute("prov");
        session.removeAttribute("dist");
        session.removeAttribute("dir");
        session.removeAttribute("telf");

        String m = "m";
        String f = "f";
        Turno temp = ServicioMain.getTurno(turno);
        FormularioSesion fs = new FormularioSesion();
        Asistente asisEl = new Asistente();
        Asistente asisElla = new Asistente();
        AsistenciaFT aft = new AsistenciaFT();
        AsistenciaFT aft2 = new AsistenciaFT();
        fs.setSesion(temp.getSesion());
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
        Date today = new Date();

        fs.setFechaSol(today);
        fs.setPaisRes(pais);
        fs.setDepRes(dep);
        fs.setProvRes(prov);
        fs.setDistritoRes(dist);
        fs.setDireccionRes(dir);
        fs.setTelefono(telf);
        fs.setEstadoCivil(estado);

        asisEl.setNombre(nombreEl);
        asisEl.setApellidoP(apellidoPEl);
        asisEl.setApellidoM(apellidoMEl);
        asisEl.setPaisNac(paisNacEl);
        asisEl.setDepNac(depNacEl);
        asisEl.setProvNac(proNacEl);
        short bEl = Byte.valueOf(edadEl);
        asisEl.setEdad(bEl);
        if (fechaNacEl != null && !fechaNacEl.equals("")) {
            asisEl.setFechaNac(df.stringToDate(fechaNacEl));
        }
        char cEl = docEl.charAt(0);
        asisEl.setTipoDoc(cEl);
        char sexoEl = m.charAt(0);
        asisEl.setSexo(sexoEl);
        asisEl.setNDoc(numDocEl);
        asisEl.setProfesion(profesionEl);
        asisEl.setCelular(celEl);
        asisEl.setCorreo(correoEl);

        asisElla.setNombre(nombreElla);
        asisElla.setApellidoP(apellidoPElla);
        asisElla.setApellidoM(apellidoMElla);
        asisElla.setPaisNac(paisNacElla);
        asisElla.setDepNac(depNacElla);
        asisElla.setProvNac(proNacElla);
        short bElla = Byte.valueOf(edadElla);
        asisElla.setEdad(bElla);
        if (fechaNacElla != null && !fechaNacElla.equals("")) {
            asisElla.setFechaNac(df.stringToDate(fechaNacElla));
        }
        char cElla = docElla.charAt(0);
        asisElla.setTipoDoc(cElla);
        char sexoElla = f.charAt(0);
        asisElla.setSexo(sexoElla);
        asisElla.setNDoc(numDocElla);
        asisElla.setProfesion(profesionElla);
        asisElla.setCelular(celElla);
        asisElla.setCorreo(correoElla);

        ArrayList<Asistente> tempList = new ArrayList();
        tempList = ServicioMain.listaAsistentes(temp.getSesion().getIdsesion());
        if (!tempList.isEmpty()) {
            for (Asistente asistente : tempList) {
                if (asistente.getNDoc().equals(numDocEl) || asistente.getNDoc().equals(numDocElla)) {
                    map.put("mensaje", "inscrito");
                    return new ModelAndView("/Inscripcion/inscripcion_sesion1b", map);
                }

            }
        }
        if (temp.getVacantes() > temp.getAsistenciaFTs().size() + 1) {
            map.put("ts", ts);
            map.put("turno", temp);
            ServicioMain.InsertFormGrp(asisEl, asisElla, fs, aft, aft2);
            return new ModelAndView("/Inscripcion/inscripcion_sesion4", map);
        }
        return new ModelAndView("/Inscripcion/inscripcion_sesion1b", map);
    }

    /**
     * ESTA SECCION ES USADA PARA ACTUALIZAR LOS DATOS DE LA FAMILIA POR PARTE
     * DEL PERSONAL*
     */
    //PROBAR
    @RequestMapping(value = "/IrPersonalFamilia", method = RequestMethod.POST)
    public ModelAndView IrPersonalFamilia_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "idFamilia", required = false) String idFamilia,
            @RequestParam(value = "idExpediente", required = false) String idExpediente,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        session.setAttribute("estado", estado);
        session.setAttribute("idFamilia", idFamilia);
        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/IrPersonalFamilia", map);
    }

    @RequestMapping(value = "/IrPersonalFamilia", method = RequestMethod.GET)
    public ModelAndView IrPersonalFamilia_GET(ModelMap map, HttpSession session) {
        String idFamilia = "";
        String idExpediente = "";
        String volver = "";
        String estado = "";
       // try {
            idFamilia = (String) session.getAttribute("idFamilia");
            idExpediente = (String)session.getAttribute("idExpediente");
            volver =(String) session.getAttribute("volver");
            estado = (String)session.getAttribute("estado");
       // } catch (Exception ex) {
         //   return new ModelAndView("redirect:/inicioper", map);
       // }
       

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (estado.equals("formativa")) {
            Long idFam = Long.parseLong(idFamilia);
            infoFam = ServicioMain.getInfoFamPorIdFamilia(idFam);
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(idFam);
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(idFam);
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(idFam);
        } else if (estado.equals("evaluacion")) {
            Long idExp = Long.parseLong(idExpediente);
            System.out.print(estado);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("espera")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("esperainter")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaNnaAdoptantesAdopcion(tempExp.getIdexpedienteFamilia());
            //listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("designacion")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        } else if (estado.equals("adopcion")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        } else if (estado.equals("reevaluacion")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("post")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
            //nnaAdoptado = ServicioNna.getNnaPostAdopcion(idNna);
            //expNna = nnaAdoptado.getExpedienteNnas().iterator().next();
            listaNnaAdoptados.clear();
            for (Designacion tempDesig : listaDesignaciones) {
                if (tempDesig.getAceptacionConsejo() == 2) {
                    listaNnaAdoptados.add(tempDesig.getNna());
                }
            }

            /*
             for (Designacion desig : listaDesignaciones) {
             if(desig.getAceptacionConsejo() == 2){
             nnaAdoptado = desig.getNna();
             expNna = desig.getNna().getExpedienteNnas().iterator().next();
             }
             }
             */
        }

        etapaOrigen = estado;

        map.put("df", df);
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("Ella", Ella);
        map.addAttribute("volver", volver);

        return new ModelAndView("/Personal/familia/info_ella", map);
    }

    //PROBAR
    @RequestMapping(value = "/IrPersonalFamilia2", method = RequestMethod.POST)
    public ModelAndView IrPersonalFamilia2_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "idFamilia", required = false) String idFamilia,
            @RequestParam(value = "idExpediente", required = false) String idExpediente,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "idNna", required = false) Long idNna,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "estado2", required = false) String estado2
    ) {
        session.setAttribute("estado", estado);
        session.setAttribute("idFamilia", idFamilia);
        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("volver", volver);
        session.setAttribute("idNna", idNna);
        session.setAttribute("expediente2", expediente2);
        session.setAttribute("HT", HT);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("tipofamilia", tipofamilia);
        session.setAttribute("estado2", estado2);

        return new ModelAndView("redirect:/IrPersonalFamilia2", map);
    }

    @RequestMapping(value = "/IrPersonalFamilia2", method = RequestMethod.GET)
    public ModelAndView IrPersonalFamilia2_GET(ModelMap map, HttpSession session) {
        long idNna = 0;
        String idFamilia = "";
        String idExpediente = "";
        String volver = "";
        String estado = "";
        String expediente2 = "";
        String HT = "";
        String nacionalidad = "";
        String tipofamilia = "";
        String estado2 = "";
        
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
            idFamilia = (String) session.getAttribute("idFamilia");
            idExpediente = (String) session.getAttribute("idExpediente");
            volver = (String) session.getAttribute("volver");
            estado = (String) session.getAttribute("estado");
            expediente2 = (String) session.getAttribute("expediente2");
            HT = (String) session.getAttribute("HT");
            nacionalidad = (String) session.getAttribute("nacionalidad");
            tipofamilia = (String) session.getAttribute("tipofamilia");
            estado2 = (String) session.getAttribute("estado2");
        
        session.removeAttribute("estado");
        session.removeAttribute("idFamilia");
        session.removeAttribute("idExpediente");
        session.removeAttribute("volver");
        session.removeAttribute("idNna");
        session.removeAttribute("expediente2");
        session.removeAttribute("HT");
        session.removeAttribute("nacionalidad");
        session.removeAttribute("tipofamilia");
        session.removeAttribute("estado2");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (estado.equals("formativa")) {
            Long idFam = Long.parseLong(idFamilia);
            infoFam = ServicioMain.getInfoFamPorIdFamilia(idFam);
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(idFam);
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(idFam);
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(idFam);
        } else if (estado.equals("evaluacion")) {
            Long idExp = Long.parseLong(idExpediente);
            System.out.print(estado);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("espera")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("esperainter")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaNnaAdoptantesAdopcion(tempExp.getIdexpedienteFamilia());
            //listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("designacion")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        } else if (estado.equals("adopcion")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        } else if (estado.equals("reevaluacion")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());

        } else if (estado.equals("post")) {
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if (adop.getSexo() == 'f') {
                    Ella = adop;
                }
                if (adop.getSexo() == 'm') {
                    El = adop;
                }
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
            nnaAdoptado = ServicioNna.getNnaPostAdopcion(idNna);
            expNna = nnaAdoptado.getExpedienteNnas().iterator().next();

            /*
             for (Designacion desig : listaDesignaciones) {
             if(desig.getAceptacionConsejo() == 2){
             nnaAdoptado = desig.getNna();
             expNna = desig.getNna().getExpedienteNnas().iterator().next();
             }
             }
             */
        }

        etapaOrigen = estado;

        map.put("df", df);
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("Ella", Ella);
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);

        return new ModelAndView("/Personal/Buscador/familia/info_ella", map);
    }

    //NO SE HA HECHO CAMBIOS
    @RequestMapping(value = "/Habilitacion", method = RequestMethod.GET)
    public ModelAndView Habilitacion(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (infoFam.getFamilia().getHabilitado() == 0) {
            infoFam.getFamilia().setHabilitado(Short.parseShort("1"));
            servicioEtapa.UpdateFamilia(infoFam.getFamilia());
        } else {
            infoFam.getFamilia().setHabilitado(Short.parseShort("0"));
            servicioEtapa.UpdateFamilia(infoFam.getFamilia());
        }
        String pagina = "/Personal/familia/info_user_pass";
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);

        return new ModelAndView(pagina, map);
    }

    //PROBAR
    @RequestMapping(value = "/elSolicitante", method = RequestMethod.POST)
    public ModelAndView elSolicitante_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/elSolicitante", map);
    }

    @RequestMapping(value = "/elSolicitante", method = RequestMethod.GET)
    public ModelAndView elSolicitante_GET(ModelMap map,
            HttpSession session) {
        String volver = "";
        try {
            volver = session.getAttribute("volver").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("volver");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("El", El);
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_el", map);
    }

    //PROBAR
    @RequestMapping(value = "/elSolicitante2", method = RequestMethod.POST)
    public ModelAndView elSolicitante2_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        session.setAttribute("volver", volver);
        session.setAttribute("expediente2", expediente2);
        session.setAttribute("estado2", estado2);
        session.setAttribute("HT", HT);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("tipofamilia", tipofamilia);

        return new ModelAndView("redirect:/elSolicitante2", map);
    }

    @RequestMapping(value = "/elSolicitante2", method = RequestMethod.GET)
    public ModelAndView elSolicitante2_GET(ModelMap map,
            HttpSession session) {
        String volver = "";
        String expediente2 = "";
        String estado2 = "";
        String HT = "";
        String nacionalidad = "";
        String tipofamilia = "";
        try {
            volver = session.getAttribute("volver").toString();
            expediente2 = session.getAttribute("expediente2").toString();
            estado2 = session.getAttribute("estado2").toString();
            HT = session.getAttribute("HT").toString();
            nacionalidad = session.getAttribute("nacionalidad").toString();
            tipofamilia = session.getAttribute("tipofamilia").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("volver");
        session.removeAttribute("expediente2");
        session.removeAttribute("estado2");
        session.removeAttribute("HT");
        session.removeAttribute("nacionalidad");
        session.removeAttribute("tipofamilia");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("El", El);
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_el", map);
    }

    //PROBAR
    @RequestMapping(value = "/laSolicitante", method = RequestMethod.POST)
    public ModelAndView laSolicitante_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/laSolicitante", map);
    }

    @RequestMapping(value = "/laSolicitante", method = RequestMethod.GET)
    public ModelAndView laSolicitante_GET(ModelMap map,
            HttpSession session) {
        String volver = "";
        try {
            volver = session.getAttribute("volver").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("volver");
        
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("Ella", Ella);
        map.put("volver", volver);
        return new ModelAndView("/Personal/familia/info_ella", map);
    }

    //PROBAR
    @RequestMapping(value = "/laSolicitante2", method = RequestMethod.POST)
    public ModelAndView laSolicitante2_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "estado", required = false) String estado2,
            HttpSession session) {
        session.setAttribute("volver", volver);
        session.setAttribute("expediente2",expediente2);
        session.setAttribute("HT",HT);
        session.setAttribute("nacionalidad",nacionalidad);
        session.setAttribute("tipofamilia",tipofamilia);
        session.setAttribute("estado2",estado2);

        return new ModelAndView("redirect:/laSolicitante2", map);
    }
    
    @RequestMapping(value = "/laSolicitante2", method = RequestMethod.GET)
    public ModelAndView laSolicitante2_GET(ModelMap map,
            HttpSession session) {
        String volver = "";
        String expediente2 = "";
        String HT = "";
        String nacionalidad = "";
        String tipofamilia = "";
        String estado2 = "";
        try{
            volver = session.getAttribute("volver").toString();
            expediente2 = session.getAttribute("expediente2").toString();
            HT = session.getAttribute("HT").toString();
            nacionalidad = session.getAttribute("nacionalidad").toString();
            tipofamilia = session.getAttribute("tipofamilia").toString();
            estado2 = session.getAttribute("estado2").toString();
        } catch (Exception ex){
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("volver");
        session.removeAttribute("expediente2");
        session.removeAttribute("HT");
        session.removeAttribute("nacionalidad");
        session.removeAttribute("tipofamilia");
        session.removeAttribute("estado2");
        
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("Ella", Ella);
        map.put("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_ella", map);
    }

    //NO SE HAN HECHO CAMBIOS
    @RequestMapping(value = "/compFamiliar", method = RequestMethod.GET)
    public ModelAndView compFamiliar(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        return new ModelAndView("/Personal/familia/info_fam", map);
    }

    //NO SE HAN HECHO CAMBIOS
    @RequestMapping(value = "/vivienda", method = RequestMethod.GET)
    public ModelAndView vivienda(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("infoFam", infoFam);
        return new ModelAndView("/Personal/familia/info_vivienda", map);
    }

    //PROBAR
    @RequestMapping(value = "/infoExpediente", method = RequestMethod.POST)
    public ModelAndView infoExpediente_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        session.setAttribute("volver",volver);
        
        return new ModelAndView("redirect:/infoExpediente", map);
    }
    
    @RequestMapping(value = "/infoExpediente", method = RequestMethod.GET)
    public ModelAndView infoExpediente_GET(ModelMap map,
            HttpSession session) {
        String volver = "";
        try {
            volver = session.getAttribute("volver").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("volver");
        
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaUA", ServicioPersonal.ListaUa());
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_registro", map);
    }

    //
    @RequestMapping(value = "/infoExpediente2", method = RequestMethod.GET)
    public ModelAndView infoExpediente2(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaUA", ServicioPersonal.ListaUa());
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_registro", map);
    }

    @RequestMapping(value = "/procesoAdopcion", method = RequestMethod.GET)
    public ModelAndView procesoAdopcion(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaSesiones", listaSesiones);
        map.put("listaAsistenciaReuniones", listaAsistenciaReuniones);
        map.put("listaEvaluaciones", listaEvaluaciones);
        map.put("listaDesignaciones", listaDesignaciones);
        map.put("listaEstudios", listaEstudios);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_adop/info_adop", map);
    }

    @RequestMapping(value = "/procesoAdopcion2", method = RequestMethod.GET)
    public ModelAndView procesoAdopcion2(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaSesiones", listaSesiones);
        map.put("listaAsistenciaReuniones", listaAsistenciaReuniones);
        map.put("listaEvaluaciones", listaEvaluaciones);
        map.put("listaDesignaciones", listaDesignaciones);
        map.put("listaEstudios", listaEstudios);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_adop/info_adop", map);
    }

    @RequestMapping(value = "/antNna", method = RequestMethod.GET)
    public ModelAndView antNna(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("infoFam", infoFam);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_ant_nna", map);
    }

    @RequestMapping(value = "/antNna2", method = RequestMethod.GET)
    public ModelAndView antNna2(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("infoFam", infoFam);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_ant_nna", map);
    }

    @RequestMapping(value = "/nnaAsociado", method = RequestMethod.GET)
    public ModelAndView nnaAsociado(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaNna", listaNnaAdoptados);
        //map.put("exp", expNna);
        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(expediente.getIdexpedienteFamilia()));
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_nna", map);
    }

    @RequestMapping(value = "/nnaAsociado2", method = RequestMethod.GET)
    public ModelAndView nnaAsociado2(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("nna", nnaAdoptado);
        map.put("exp", expNna);
        map.addAttribute("volver", volver);
        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(expediente.getIdexpedienteFamilia()));
        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_nna", map);
    }

    @RequestMapping(value = "/atenciones", method = RequestMethod.GET)
    public ModelAndView atenciones(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaAtenciones", listaAtenciones);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion", map);
    }

    @RequestMapping(value = "/atenciones2", method = RequestMethod.GET)
    public ModelAndView atenciones2(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaAtenciones", listaAtenciones);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion", map);
    }

    @RequestMapping(value = "/agregarAtencion", method = RequestMethod.GET)
    public ModelAndView agregarAtencion(ModelMap map, HttpSession session,
            @RequestParam(value = "volver", required = false) String volver) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion_edit", map);
    }

    @RequestMapping(value = "/agregarAtencion3", method = RequestMethod.POST)
    public ModelAndView agregarAtencion3(ModelMap map, HttpSession session,
            @RequestParam(value = "volver", required = false) String volver) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion_edit", map);
    }

    @RequestMapping(value = "/agregarAtencion2", method = RequestMethod.GET)
    public ModelAndView agregarAtencion2(ModelMap map,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion_edit", map);
    }

    @RequestMapping(value = "/DetalleAtencion", method = RequestMethod.POST)
    public ModelAndView DetalleAtencion(ModelMap map,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session,
            @RequestParam(value = "idAtencion") long idAtencion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Atencion temp = ServicioMain.getAtencion(idAtencion);
        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("atencion", temp);
        map.put("expediente", expediente);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/familia/info_atencion_edit", map);
    }

    @RequestMapping(value = "/DetalleAtencion2", method = RequestMethod.POST)
    public ModelAndView DetalleAtencion2(ModelMap map,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session,
            @RequestParam(value = "idAtencion") long idAtencion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Atencion temp = ServicioMain.getAtencion(idAtencion);
        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("atencion", temp);
        map.put("expediente", expediente);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion_edit", map);
    }

    @RequestMapping(value = "/crearAtencion", method = RequestMethod.POST)
    public ModelAndView crearAtencion(ModelMap map, HttpSession session,
            @RequestParam(value = "personal") long personal,
            @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "hora") String hora,
            @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "detalle", required = false) String detalle,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = new Atencion();
        Familia tempFam = expediente.getFamilia();
        Personal tempPer = ServicioPersonal.getPersonal(personal);

        tempAtn.setFamilia(tempFam);
        tempAtn.setPersonal(tempPer);
        if (fecha != null && !fecha.equals("")) {
            tempAtn.setFecha(df.stringToDate(fecha));
        }
        if (fecha == null || fecha.equals("")) {
            tempAtn.setFecha(null);
        }
        tempAtn.setHora(hora);
        tempAtn.setTipoAtencion(tipo);
        tempAtn.setDetalle(detalle);
        tempAtn.setObservacion(obs);

        ServicioMain.crearAtencion(tempAtn);
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(expediente.getFamilia().getIdfamilia());
        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaAtenciones", listaAtenciones);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion", map);
    }

    @RequestMapping(value = "/crearAtencion2", method = RequestMethod.POST)
    public ModelAndView crearAtencion2(ModelMap map, HttpSession session,
            @RequestParam(value = "personal") long personal,
            @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "hora") String hora,
            @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "detalle", required = false) String detalle,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = new Atencion();
        Familia tempFam = expediente.getFamilia();
        Personal tempPer = ServicioPersonal.getPersonal(personal);

        tempAtn.setFamilia(tempFam);
        tempAtn.setPersonal(tempPer);
        if (fecha != null && !fecha.equals("")) {
            tempAtn.setFecha(df.stringToDate(fecha));
        }
        if (fecha == null || fecha.equals("")) {
            tempAtn.setFecha(null);
        }
        tempAtn.setHora(hora);
        tempAtn.setTipoAtencion(tipo);
        tempAtn.setDetalle(detalle);
        tempAtn.setObservacion(obs);

        ServicioMain.crearAtencion(tempAtn);
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(expediente.getFamilia().getIdfamilia());
        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaAtenciones", listaAtenciones);
        map.put("expediente", expediente);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        map.addAttribute("volver", volver);

        return new ModelAndView("/Personal/Buscador/familia/info_atencion", map);
    }

    @RequestMapping(value = "/updateAtencion", method = RequestMethod.POST)
    public ModelAndView updateAtencion(ModelMap map, HttpSession session,
            @RequestParam(value = "idAtencion") long idAtencion,
            @RequestParam(value = "personal") long personal,
            @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "hora") String hora,
            @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "detalle", required = false) String detalle,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = ServicioMain.getAtencion(idAtencion);
        Personal tempPer = ServicioPersonal.getPersonal(personal);
        tempAtn.setPersonal(tempPer);
        if (fecha != null && !fecha.equals("")) {
            tempAtn.setFecha(df.stringToDate(fecha));
        }
        if (fecha == null || fecha.equals("")) {
            tempAtn.setFecha(null);
        }
        tempAtn.setHora(hora);
        tempAtn.setTipoAtencion(tipo);
        tempAtn.setDetalle(detalle);
        tempAtn.setObservacion(obs);

        ServicioMain.updateAtencion(tempAtn);
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(expediente.getFamilia().getIdfamilia());
        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaAtenciones", listaAtenciones);
        map.put("expediente", expediente);
        map.put("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion", map);
    }

    @RequestMapping(value = "/updateAtencion2", method = RequestMethod.POST)
    public ModelAndView updateAtencion2(ModelMap map, HttpSession session,
            @RequestParam(value = "idAtencion") long idAtencion,
            @RequestParam(value = "personal") long personal,
            @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "hora") String hora,
            @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "detalle", required = false) String detalle,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = ServicioMain.getAtencion(idAtencion);
        Personal tempPer = ServicioPersonal.getPersonal(personal);
        tempAtn.setPersonal(tempPer);
        if (fecha != null && !fecha.equals("")) {
            tempAtn.setFecha(df.stringToDate(fecha));
        }
        if (fecha == null || fecha.equals("")) {
            tempAtn.setFecha(null);
        }
        tempAtn.setHora(hora);
        tempAtn.setTipoAtencion(tipo);
        tempAtn.setDetalle(detalle);
        tempAtn.setObservacion(obs);

        ServicioMain.updateAtencion(tempAtn);
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(expediente.getFamilia().getIdfamilia());
        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("listaAtenciones", listaAtenciones);
        map.put("expediente", expediente);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        map.put("volver", volver);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion", map);
    }

    @RequestMapping(value = "/DetalleSesion", method = RequestMethod.POST)
    public ModelAndView DetalleSesion(ModelMap map, HttpSession session,
            @RequestParam(value = "idSesion") long idSesion, 
            @RequestParam(value = "volver", required = false) String volver) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Sesion tempSes = ServicioMain.getInfoSesion(idSesion);

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("sesion", tempSes);
        map.put("volver", volver);
        return new ModelAndView("/Personal/familia/info_adop/detalle_sesion", map);
    }

    @RequestMapping(value = "/DetalleTaller", method = RequestMethod.POST)
    public ModelAndView DetalleTaller(ModelMap map, HttpSession session,
            @RequestParam(value = "idTurno") long idTurno,
            @RequestParam(value = "tipoTaller") String tipoTaller,
            @RequestParam(value = "nombreTaller") String nombreTaller,
            @RequestParam(value = "nombreGrupo") String nombreGrupo,
            @RequestParam(value = "nombreTurno") String nombreTurno,
            @RequestParam(value = "volver", required = false) String volver            
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<Reunion> allReuniones = new ArrayList();
        ArrayList<String> allAsistencia = new ArrayList();
        allReuniones = ServicioMain.getListaReunionesPorTurno(idTurno);

        map.put("listaAsistencia", listaAsistenciaReuniones);
        map.put("listaReuniones", allReuniones);
        map.put("tipoTaller", tipoTaller);
        map.put("nombreTaller", nombreTaller);
        map.put("nombreGrupo", nombreGrupo);
        map.put("nombreTurno", nombreTurno);

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("volver", volver);
        
        return new ModelAndView("/Personal/familia/info_adop/detalle_taller", map);
    }

    @RequestMapping(value = "/DetalleEvaluacion", method = RequestMethod.POST)
    public ModelAndView DetalleEvaluacion(ModelMap map, HttpSession session, @RequestParam(value = "idEval") long idEval) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Evaluacion tempEval = ServicioMain.getEvaluacionJuntoPersonal(idEval);

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("evaluacion", tempEval);
        return new ModelAndView("/Personal/familia/info_adop/detalle_evaluacion", map);
    }

    @RequestMapping(value = "/ActualizarInfoFamilia", method = RequestMethod.POST)
    public ModelAndView ActualizarInfoFamilia(ModelMap map, HttpSession session,
            @RequestParam(value = "incesto") String incesto,
            @RequestParam(value = "mental") String mental,
            @RequestParam(value = "epilepsia") String epilepsia,
            @RequestParam(value = "abuso") String abuso,
            @RequestParam(value = "sifilis") String sifilis,
            @RequestParam(value = "seguimiento") String seguimiento,
            @RequestParam(value = "operacion") String operacion,
            @RequestParam(value = "hiperactivo") String hiperactivo,
            @RequestParam(value = "especial") String especial,
            @RequestParam(value = "salud") String salud,
            @RequestParam(value = "mayor") String mayor,
            @RequestParam(value = "adolescente") String adolescente,
            @RequestParam(value = "hermanos") String hermanos,
            @RequestParam(value = "viajar") String viajar,
            @RequestParam(value = "edadMin") String edadMin,
            @RequestParam(value = "edadMax") String edadMax,
            @RequestParam(value = "genero") String genero,
            @RequestParam(value = "numHijos") String numHijos,
            @RequestParam(value = "nivel") String nivel,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        infoFam.setNnaIncesto(Short.parseShort(incesto));
        infoFam.setNnaMental(Short.parseShort(mental));
        infoFam.setNnaEpilepsia(Short.parseShort(epilepsia));
        infoFam.setNnaAbuso(Short.parseShort(abuso));
        infoFam.setNnaSifilis(Short.parseShort(sifilis));
        infoFam.setNnaSeguiMedico(Short.parseShort(seguimiento));
        infoFam.setNnaOperacion(Short.parseShort(operacion));
        infoFam.setNnaHiperactivo(Short.parseShort(hiperactivo));
        infoFam.setNnaEspecial(Short.parseShort(especial));
        infoFam.setNnaEnfermo(Short.parseShort(salud));
        infoFam.setNnaMayor(Short.parseShort(mayor));
        infoFam.setNnaAdolescente(Short.parseShort(adolescente));
        infoFam.setNnaHermano(Short.parseShort(hermanos));

        if (viajar != null && !viajar.equals("")) {
            infoFam.setPuedeViajar(Short.parseShort(viajar));
        }
        if (edadMin != null && !edadMin.equals("")) {
            infoFam.setExpectativaEdadMin(Short.parseShort(edadMin));
        }
        if (edadMax != null && !edadMax.equals("")) {
            infoFam.setExpectativaEdadMax(Short.parseShort(edadMax));
        }
        if (genero != null && !genero.equals("")) {
            infoFam.setExpectativaGenero(genero);
        }
        if (numHijos != null && !numHijos.equals("")) {
            infoFam.setnHijos(Short.parseShort(numHijos));
        }
        if (nivel != null && !nivel.equals("")) {
            char n = nivel.charAt(0);
            infoFam.setNivelSocioeconomico(n);
        }

        ServicioMain.updateInfoFam(infoFam);

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("infoFam", infoFam);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_ant_nna", map);
    }

    @RequestMapping(value = "/ActualizarRegistro", method = RequestMethod.POST)
    public ModelAndView ActualizarRegistro(ModelMap map, HttpSession session,
            //@RequestParam(value="numero") long numero,  
            @RequestParam(value = "htFicha") String htFicha,
            @RequestParam(value = "nFicha") String nFicha,
            @RequestParam(value = "fechaIngresoFicha") String fechaIngresoFicha,
            @RequestParam(value = "ht") String ht,
            @RequestParam(value = "numeroExp") String numeroExp,
            @RequestParam(value = "fechaIngreso") String fechaIngreso,
            @RequestParam(value = "tupa") String tupa,
            @RequestParam(value = "nacionalidad") String nacionalidad,
            //@RequestParam(value="rnsa") String rnsa,  
            //@RequestParam(value="rnaa") String rnaa,  
            @RequestParam(value = "tipoFamilia") String tipoFamilia,
            @RequestParam(value = "tipoEspera") String tipoEspera,
            @RequestParam(value = "unidad") long unidad,
            @RequestParam(value = "entAsoc") long entAsoc,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Unidad tempUa = ServicioPersonal.getUa(unidad);
        Entidad tempEnt = ServicioPersonal.getEntidad(entAsoc);

        //expediente.setNumero(numero);
        expediente.setHtFicha(htFicha);
        expediente.setnFicha(nFicha);
        if (fechaIngresoFicha != null && !fechaIngresoFicha.equals("")) {
            expediente.setFechaIngresoFicha(df.stringToDate(fechaIngresoFicha));
        }
        if (fechaIngresoFicha == null || fechaIngresoFicha.equals("")) {
            expediente.setFechaIngresoDga(null);
        }
        expediente.setHt(ht);
        expediente.setNumeroExpediente(numeroExp);
        if (fechaIngreso != null && !fechaIngreso.equals("")) {
            expediente.setFechaIngresoDga(df.stringToDate(fechaIngreso));
        } else if (fechaIngreso == null || fechaIngreso.equals("")) {
            expediente.setFechaIngresoDga(null);
        }
        if (tupa != null && !tupa.equals("")) {
            expediente.setTupa(df.stringToDate(tupa));
        } else if (tupa == null || tupa.equals("")) {
            expediente.setTupa(null);
        }

        expediente.setNacionalidad(nacionalidad);
        //expediente.setRnsa(Short.parseShort(rnsa));
        //expediente.setRnaa(Short.parseShort(rnaa));

        expediente.setTipoFamilia(tipoFamilia);
        expediente.setTipoListaEspera(tipoEspera);

        expediente.setUnidad(tempUa);
        expediente.getFamilia().setEntidad(tempEnt);

        ServicioMain.updateExpFam(expediente);
        ServicioMain.updateFam(expediente.getFamilia());

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("listaUA", ServicioPersonal.ListaUa());
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_registro", map);
    }

    @RequestMapping(value = "/ActualizarAdoptante", method = RequestMethod.POST)
    public ModelAndView ActualizarAdoptante(ModelMap map, HttpSession session,
            @RequestParam(value = "adoptante") String adoptante,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidoP", required = false) String apellidoP,
            @RequestParam(value = "apellidoM", required = false) String apellidoM,
            @RequestParam(value = "fechaNac", required = false) String fechaNac,
            @RequestParam(value = "lugarNac", required = false) String lugarNac,
            @RequestParam(value = "depNac", required = false) String depNac,
            @RequestParam(value = "paisNac", required = false) String paisNac,
            @RequestParam(value = "doc", required = false) String doc,
            @RequestParam(value = "numDoc", required = false) String numDoc,
            @RequestParam(value = "pasaporte", required = false) String pasaporte,
            @RequestParam(value = "numCel", required = false) String numCel,
            @RequestParam(value = "correo", required = false) String correo,
            @RequestParam(value = "estadoCivil", required = false) String estadoCivil,
            @RequestParam(value = "fechaMat", required = false) String fechaMat,
            @RequestParam(value = "volver", required = false) String volver
    //            @RequestParam(value = "nivelInstruccion", required = false) String nivelInstruccion,
    //            @RequestParam(value = "culminoNivel", required = false) String culminoNivel,
    //            @RequestParam(value = "profesion", required = false) String profesion,
    //            @RequestParam(value = "trabDep", required = false) String trabDep,
    //            @RequestParam(value = "ocupacionDep", required = false) String ocupacionDep,
    //            @RequestParam(value = "centroTrabajo", required = false) String centroTrabajo,
    //            @RequestParam(value = "direccionTrabajo", required = false) String direccionTrabajo,
    //            @RequestParam(value = "telefonoTrabajo", required = false) String telefonoTrabajo,
    //            @RequestParam(value = "ingresoDep", required = false) String ingresoDep,
    //            @RequestParam(value = "trabIndep", required = false) String trabIndep,
    //            @RequestParam(value = "ocupacionInd", required = false) String ocupacionInd,
    //            @RequestParam(value = "ingresoInd", required = false) String ingresoInd,
    //            @RequestParam(value = "seguroSalud", required = false) String seguroSalud,
    //            @RequestParam(value = "tipoSeguro", required = false) String tipoSeguro,
    //            @RequestParam(value = "seguroVida", required = false) String seguroVida,
    //            @RequestParam(value = "sisPensiones", required = false) String sisPensiones,
    //            @RequestParam(value = "estadoActual", required = false) String estadoActual
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (adoptante.equals("el")) {
            El.setNombre(nombre);
            El.setApellidoP(apellidoP);
            El.setApellidoM(apellidoM);
            if (fechaNac != null && !fechaNac.equals("")) {
                El.setFechaNac(df.stringToDate(fechaNac));
            }
            if (fechaNac == null || fechaNac.equals("")) {
                El.setFechaNac(null);
            }
            El.setLugarNac(lugarNac);
            El.setDepaNac(depNac);
            El.setPaisNac(paisNac);
            if (doc != null && !doc.equals("")) {
                char d = doc.charAt(0);
                El.setTipoDoc(d);
            }
            El.setNDoc(numDoc);
            El.setPasaporte(pasaporte);
            El.setCelular(numCel);
            El.setCorreo(correo);
            infoFam.setEstadoCivil(estadoCivil);
            if (infoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(df.stringToDate(fechaMat));
            } else if (fechaMat == null || fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(null);
            }
            ServicioMain.updateInfoFam(infoFam);
//            El.setNivelInstruccion(nivelInstruccion);
//            if (culminoNivel != null && !culminoNivel.equals("")) {
//                El.setCulminoNivel(Short.parseShort(culminoNivel));
//            }
//            El.setProfesion(profesion);
//            /*Trabajo*/
//            if (trabDep != null && !trabDep.equals("")) {
//                El.setTrabajadorDepend(Short.parseShort(trabDep));
//            } else {
//                El.setTrabajadorDepend(null);
//            }
//            if (ocupacionDep != null && !ocupacionDep.equals("")) {
//                El.setOcupActualDep(ocupacionDep);
//            } else {
//                El.setOcupActualDep(null);
//            }
//            if (centroTrabajo != null && !centroTrabajo.equals("")) {
//                El.setCentroTrabajo(centroTrabajo);
//            } else {
//                El.setCentroTrabajo(null);
//            }
//            if (direccionTrabajo != null && !direccionTrabajo.equals("")) {
//                El.setDireccionCentro(direccionTrabajo);
//            } else {
//                El.setDireccionCentro(null);
//            }
//            if (telefonoTrabajo != null && !telefonoTrabajo.equals("")) {
//                El.setTelefonoCentro(telefonoTrabajo);
//            } else {
//                El.setTelefonoCentro(null);
//            }
//            if (ingresoDep != null && !ingresoDep.equals("")) {
//                El.setIngresoDep(Long.parseLong(ingresoDep));
//            } else {
//                El.setIngresoDep(null);
//            }
//            if (trabIndep != null && !trabIndep.equals("")) {
//                El.setTrabajadorIndepend(Short.parseShort(trabIndep));
//            } else {
//                El.setTrabajadorIndepend(null);
//            }
//            if (ocupacionInd != null && !ocupacionInd.equals("")) {
//                El.setOcupActualInd(ocupacionInd);
//            } else {
//                El.setOcupActualInd(null);
//            }
//
//            if (ingresoInd != null && !ocupacionInd.equals("")) {
//                El.setIngresoIndep(Long.parseLong(ingresoInd));
//            } else {
//                El.setIngresoIndep(null);
//            }
//            /*Fin Trabajo*/
//            if (seguroSalud != null && !seguroSalud.equals("")) {
//                El.setSeguroSalud(Short.parseShort(seguroSalud));
//            }
//            El.setTipoSeguro(tipoSeguro);
//            if (seguroVida != null && !seguroVida.equals("")) {
//                El.setSeguroVida(Short.parseShort(seguroVida));
//            }
//            if (sisPensiones != null && !sisPensiones.equals("")) {
//                El.setSistPensiones(Short.parseShort(sisPensiones));
//            }
//            El.setSaludActual(estadoActual);

            ServicioMain.updateAdoptante(El);
            if ((El.getApellidoP() != null && !El.getApellidoP().equals("")) && (Ella.getApellidoP() != null && !Ella.getApellidoP().equals(""))) {
                expediente.setExpediente(El.getApellidoP() + " - " + Ella.getApellidoP());
            } else if (El.getApellidoP() != null && !El.getApellidoP().equals("")) {
                expediente.setExpediente(El.getApellidoP());
            } else if (Ella.getApellidoP() != null && !Ella.getApellidoP().equals("")) {
                expediente.setExpediente(Ella.getApellidoP());
            }
            servicioEtapa.updateExpedienteFamilia(expediente);
            map.put("df", df);
            map.put("estado", etapaOrigen);
            map.put("infoFam", infoFam);
            map.put("expediente", expediente);
            map.addAttribute("volver", volver);
            map.put("El", El);
            return new ModelAndView("/Personal/familia/info_el", map);
        } else {

            Ella.setNombre(nombre);
            Ella.setApellidoP(apellidoP);
            Ella.setApellidoM(apellidoM);
            if (fechaNac != null && !fechaNac.equals("")) {
                Ella.setFechaNac(df.stringToDate(fechaNac));
            }
            if (fechaNac == null || fechaNac.equals("")) {
                Ella.setFechaNac(null);
            }
            Ella.setLugarNac(lugarNac);
            Ella.setDepaNac(depNac);
            Ella.setPaisNac(paisNac);
            if (doc != null && !doc.equals("")) {
                char d = doc.charAt(0);
                Ella.setTipoDoc(d);
            }
            Ella.setNDoc(numDoc);
            Ella.setPasaporte(pasaporte);
            Ella.setCelular(numCel);
            Ella.setCorreo(correo);
            infoFam.setEstadoCivil(estadoCivil);
            if (infoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(df.stringToDate(fechaMat));
            } else if (fechaMat == null || fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(null);
            }
            ServicioMain.updateInfoFam(infoFam);
//            Ella.setNivelInstruccion(nivelInstruccion);
//            if (culminoNivel != null && !culminoNivel.equals("")) {
//                Ella.setCulminoNivel(Short.parseShort(culminoNivel));
//            }
//            Ella.setProfesion(profesion);
//            /*Trabajo*/
//            if (trabDep != null && !trabDep.equals("")) {
//                Ella.setTrabajadorDepend(Short.parseShort(trabDep));
//            } else {
//                Ella.setTrabajadorDepend(null);
//            }
//            if (ocupacionDep != null && !ocupacionDep.equals("")) {
//                Ella.setOcupActualDep(ocupacionDep);
//            } else {
//                Ella.setOcupActualDep(null);
//            }
//            if (centroTrabajo != null && !centroTrabajo.equals("")) {
//                Ella.setCentroTrabajo(centroTrabajo);
//            } else {
//                Ella.setCentroTrabajo(null);
//            }
//            if (direccionTrabajo != null && !direccionTrabajo.equals("")) {
//                Ella.setDireccionCentro(direccionTrabajo);
//            } else {
//                Ella.setDireccionCentro(null);
//            }
//            if (telefonoTrabajo != null && !telefonoTrabajo.equals("")) {
//                Ella.setTelefonoCentro(telefonoTrabajo);
//            } else {
//                Ella.setTelefonoCentro(null);
//            }
//            if (ingresoDep != null && !ingresoDep.equals("")) {
//                Ella.setIngresoDep(Long.parseLong(ingresoDep));
//            } else {
//                Ella.setIngresoDep(null);
//            }
//            if (trabIndep != null && !trabIndep.equals("")) {
//                Ella.setTrabajadorIndepend(Short.parseShort(trabIndep));
//            } else {
//                Ella.setTrabajadorIndepend(null);
//            }
//            if (ocupacionInd != null && !ocupacionInd.equals("")) {
//                Ella.setOcupActualInd(ocupacionInd);
//            } else {
//                Ella.setOcupActualInd(null);
//            }
//
//            if (ingresoInd != null && !ocupacionInd.equals("")) {
//                Ella.setIngresoIndep(Long.parseLong(ingresoInd));
//            } else {
//                Ella.setIngresoIndep(null);
//            }
            /*Fin Trabajo*/
//            if (seguroSalud != null && !seguroSalud.equals("")) {
//                Ella.setSeguroSalud(Short.parseShort(seguroSalud));
//            }
//            Ella.setTipoSeguro(tipoSeguro);
//            if (seguroVida != null && !seguroVida.equals("")) {
//                Ella.setSeguroVida(Short.parseShort(seguroVida));
//            }
//            if (sisPensiones != null && !sisPensiones.equals("")) {
//                Ella.setSistPensiones(Short.parseShort(sisPensiones));
//            }
//            Ella.setSaludActual(estadoActual);

            ServicioMain.updateAdoptante(Ella);
            if ((El.getApellidoP() != null && !El.getApellidoP().equals("")) && (Ella.getApellidoP() != null && !Ella.getApellidoP().equals(""))) {
                expediente.setExpediente(El.getApellidoP() + " - " + Ella.getApellidoP());
            } else if (El.getApellidoP() != null && !El.getApellidoP().equals("")) {
                expediente.setExpediente(El.getApellidoP());
            } else if (Ella.getApellidoP() != null && !Ella.getApellidoP().equals("")) {
                expediente.setExpediente(Ella.getApellidoP());
            }
            servicioEtapa.updateExpedienteFamilia(expediente);
            map.put("df", df);
            map.put("estado", etapaOrigen);
            map.put("infoFam", infoFam);
            map.put("expediente", expediente);
            map.put("Ella", Ella);
            map.addAttribute("volver", volver);
            return new ModelAndView("/Personal/familia/info_ella", map);

        }

    }

    @RequestMapping(value = "/ActualizarVivienda", method = RequestMethod.POST)
    public ModelAndView ActualizarVivienda(ModelMap map, HttpSession session,
            @RequestParam(value = "propiedadVivienda", required = false) String propiedadVivienda,
            @RequestParam(value = "propiedadDescrip", required = false) String propiedadDescrip,
            @RequestParam(value = "tipoVivienda", required = false) String tipoVivienda,
            @RequestParam(value = "tipoDescrip", required = false) String tipoDescrip,
            @RequestParam(value = "domicilio", required = false) String domicilio,
            @RequestParam(value = "DepRes", required = false) String DepRes,
            @RequestParam(value = "PaisRes", required = false) String PaisRes,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "areaVivTotal", required = false) String areaVivTotal,
            @RequestParam(value = "areaVivConst", required = false) String areaVivConst,
            @RequestParam(value = "distViv", required = false) String distViv,
            @RequestParam(value = "luz", required = false) String luz,
            @RequestParam(value = "agua", required = false) String agua,
            @RequestParam(value = "desague", required = false) String desague,
            @RequestParam(value = "otrosServ", required = false) String otrosServ,
            @RequestParam(value = "materConst", required = false) String materConst,
            @RequestParam(value = "materConstDesc", required = false) String materConstDesc,
            @RequestParam(value = "pared", required = false) String pared,
            @RequestParam(value = "paredDesc", required = false) String paredDesc,
            @RequestParam(value = "techo", required = false) String techo,
            @RequestParam(value = "techoDesc", required = false) String techoDesc,
            @RequestParam(value = "piso", required = false) String piso,
            @RequestParam(value = "pisoDesc", required = false) String pisoDesc
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (propiedadVivienda != null && (propiedadVivienda.equals("Propia") || propiedadVivienda.equals("Alquilada"))) {
            infoFam.setPropiedadVivienda(propiedadVivienda);
        } else if (propiedadDescrip != null) {
            infoFam.setPropiedadVivienda(propiedadDescrip);
        }

        if (tipoVivienda != null && (tipoVivienda.equals("Casa") || tipoVivienda.equals("Departamento"))) {
            infoFam.setTipoVivienda(tipoVivienda);
        } else if (tipoDescrip != null) {
            infoFam.setTipoVivienda(tipoDescrip);
        }

        if (areaVivTotal != null && !areaVivTotal.equals("")) {
            Long temp = Long.parseLong(areaVivTotal);
            infoFam.setAreaVivTotal(temp);
        }

        if (areaVivConst != null && !areaVivConst.equals("")) {
            Long temp = Long.parseLong(areaVivConst);
            infoFam.setAreaVivConst(temp);
        }

        infoFam.setDistVivienda(distViv);

        infoFam.setLuz(Short.parseShort(luz));
        infoFam.setAgua(Short.parseShort(agua));
        infoFam.setDesague(Short.parseShort(desague));

        if (otrosServ != null && !otrosServ.equals("")) {
            infoFam.setOtrosServ(otrosServ);
        }

        infoFam.setDomicilio(domicilio);
        infoFam.setDepRes(DepRes);
        infoFam.setPaisRes(PaisRes);
        infoFam.setTelefono(telefono);

        if (materConst != null && (materConst.equals("Noble"))) {
            infoFam.setMaterConst(materConst);
        } else {
            infoFam.setMaterConst(materConstDesc);
        }
        if (pared != null && (pared.equals("Ladrillo"))) {
            infoFam.setPared(pared);
        } else {
            infoFam.setPared(paredDesc);
        }
        if (techo != null && (techo.equals("Concreto"))) {
            infoFam.setTecho(techo);
        } else {
            infoFam.setTecho(techoDesc);
        }
        if (piso != null && (piso.equals("Cemento"))) {
            infoFam.setPiso(piso);
        } else {
            infoFam.setPiso(pisoDesc);
        }

        ServicioMain.updateInfoFam(infoFam);

        map.put("df", df);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.put("infoFam", infoFam);
        return new ModelAndView("/Personal/familia/info_vivienda", map);

    }

    /*  CRONOGRAMA */
    @RequestMapping("/CronogramaAnualPrev")
    public String CronogramaAnualPrev(ModelMap map) {
        map.put("listaUa", ServicioPersonal.ListaUa());
        return "cronograma_prev";
    }

    //LISTO
    @RequestMapping(value = "/CronogramaAnual", method = RequestMethod.POST)
    public ModelAndView CronogramaAnual_GET(ModelMap map, HttpSession session, @RequestParam(value = "ua") String ua) {

        session.setAttribute("ua", ua);
        return new ModelAndView("redirect:/CronogramaAnual", map);
    }

    @RequestMapping(value = "/CronogramaAnual", method = RequestMethod.GET)
    public ModelAndView CronogramaAnual_POST(ModelMap map, HttpSession session) {

        String ua = "";
        try {
            ua = session.getAttribute("ua").toString();
        } catch (Exception ex) {
            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute(ua);

        ArrayList<Taller> allTalleres = new ArrayList();
        allTalleres = ServicioMain.listaTalleresProgramados(ua);
        ArrayList<Sesion> allSesiones = new ArrayList();
        allSesiones = ServicioMain.getListaSesionesProgramadas(ua);

        map.put("df", df);
        map.put("listaTalleres", allTalleres);
        map.put("listaSesiones", allSesiones);
        return new ModelAndView("cronograma", map);
    }

    @RequestMapping(value = "/GenerarExpNac", method = RequestMethod.POST)
    public ModelAndView GenerarExpNac(ModelMap map, @RequestParam(value = "idFamilia") long idFamilia,
            @RequestParam(value = "el", required = false) String el,
            @RequestParam(value = "ella", required = false) String ella) {

        String exp = el + " - " + ella;
        if (el == null || el.equals("")) {
            exp = ella;
        }
        if (ella == null || ella.equals("")) {
            exp = el;
        }
        map.put("df", df);
        map.put("idFamilia", idFamilia);
        map.put("exp", exp);
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        return new ModelAndView("/Personal/Buscador_etapa/gen_exp", map);

    }

    @RequestMapping(value = "/CrearExpNac", method = RequestMethod.POST)
    public ModelAndView CrearExpNac(ModelMap map, HttpSession session,
            @RequestParam(value = "idFamilia") long idFamilia,
            @RequestParam(value = "numeroFicha") String numeroFicha,
            @RequestParam(value = "ht") String ht,
            @RequestParam(value = "exp") String exp,
            @RequestParam(value = "fechaIngresoFicha") String fechaIngresoFicha,
            @RequestParam(value = "entAsoc", required = false) Long entAsoc
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Familia tempFam = new Familia();
        tempFam = servicioEtapa.getFamilia(idFamilia);
        ExpedienteFamilia expediente = new ExpedienteFamilia();
        if (entAsoc != null && !entAsoc.equals("")) {
            Entidad tempEnt = ServicioPersonal.getEntidad(entAsoc);
            tempFam.setEntidad(tempEnt);
        }
//
        expediente.setHtFicha(ht);
        expediente.setExpediente(exp);
//        expediente.setHt(ht);
        expediente.setnFicha(numeroFicha);
        if (fechaIngresoFicha != null && !fechaIngresoFicha.equals("")) {
            expediente.setFechaIngresoFicha(df.stringToDate(fechaIngresoFicha));
        }
        if (fechaIngresoFicha == null || fechaIngresoFicha.equals("")) {
            expediente.setFechaIngresoFicha(null);
        }
//        if (tupa != null && !tupa.equals("")) {
//            expediente.setTupa(df.stringToDate(tupa));
//        }
//        if (tupa == null && tupa.equals("")) {
//            expediente.setTupa(null);
//        }
//        expediente.setTipoFamilia(tipoFamilia);
        expediente.setUnidad(usuario.getUnidad());
        expediente.setEstado("evaluacion");
        expediente.setNacionalidad("nacional");
        expediente.setRnsa(Short.parseShort("0"));
        expediente.setRnaa(Short.parseShort("1"));
        expediente.setFamilia(tempFam);
        ServicioMain.crearUpdateExpFam(expediente);

        map.put("listaFamilias", servicioEtapa.getListaFamilias());
        return new ModelAndView("Personal/Buscador_etapa/etapa_formativa", map);

    }

    @RequestMapping(value = "/EditUserPass", method = RequestMethod.GET)
    public ModelAndView EditUserPass(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);

        return new ModelAndView("/Personal/familia/info_user_pass", map);
    }

    @RequestMapping(value = "/EditUserPass2", method = RequestMethod.GET)
    public ModelAndView EditUserPass2(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);

        return new ModelAndView("/Personal/Buscador/familia/info_user_pass", map);
    }

    @RequestMapping(value = "/generarContrasenaFam", method = RequestMethod.POST)
    public ModelAndView generarContrasenaFam(ModelMap map, HttpSession session,
            @RequestParam("password2") String newpass,
            @RequestParam(value = "volver", required = false) String volver) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        String mensaje = "";
        if (usuario == null) {
            mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            if (newpass != null && !newpass.equals("")) {
                newpass = DigestUtils.sha512Hex(newpass);
                infoFam.getFamilia().setPass(newpass);
                servicioEtapa.UpdateFamilia(infoFam.getFamilia());
                mensaje = "La contraseña se ha cambiado con exito.";
            } else {

                mensaje = "Contraseña no válida. Por favor generar una nueva contraseña.";
            }
        }
        String pagina = "/Personal/familia/info_user_pass";
        map.put("infoFam", infoFam);
        map.put("estado", etapaOrigen);
        map.put("expediente", expediente);
        map.addAttribute("mensaje", mensaje);
        map.addAttribute("volver", volver);
        return new ModelAndView(pagina, map);
    }

    @RequestMapping(value = "/recordarContra")
    public ModelAndView recordarContra(ModelMap map) {

        String pagina = "/record_contra";
        return new ModelAndView(pagina);
    }

    @RequestMapping(value = "/recordarContraEnvio", method = RequestMethod.POST)
    public ModelAndView recordarContraEnvio(ModelMap map, HttpSession session, @RequestParam("usuario") String user) {

        String pass_plano = ServicioPersonal.generateUniqueToken(8);
        String pass = DigestUtils.sha512Hex(pass_plano);

        //ArrayList aux = hibermail.usuario(user);
        ArrayList aux = hibermail.usuario2(user, pass);

        String pagina;
        String mensaje = null;

        if (!user.equals("") && !aux.isEmpty()) {

            hibermail.generateAndSendEmail(aux.get(0).toString(), pass_plano, user);
            mensaje = aux.get(1).toString();

            map.addAttribute("mensaje", mensaje);
            pagina = "login";

        } else if (aux.isEmpty()) {
            mensaje = "Lo sentimos el correo no se encuentra registrado en la base de datos";
            map.addAttribute("mensaje", mensaje);
            pagina = "record_contra";

        } else {

            mensaje = "Por favor llenar el campo solicitado";
            map.addAttribute("mensaje", mensaje);
            pagina = "record_contra";

        };

        /*
         if (aux.get(0) == "personal") {
         Personal personal = (Personal) aux.get(1);
         if (!personal.getRol().equals("Inactivo")) {
         session.setAttribute("usuario", personal);
         personal.setPass(pass);
         ServicioPersonal.UpdatePersonal(personal);
         hibermail.generateAndSendEmail(personal.getCorreoPersonal(), pass_plano, personal.getUser());
         mensaje = "Una nueva contraseña se ha enviado a su correo de contacto asociado a su cuenta"
         + ". Por favor, revisar su bandeja.";
         map.addAttribute("mensaje", mensaje);
         pagina = "login";

         } else {
         mensaje = "El usuario se encuentra Deshabilitado. Favor contactar a la Dirección General de Adopciones para más información";
         map.addAttribute("mensaje", mensaje);
         pagina = "login";
         }
         } else if (aux.get(0) == "familia") {
         Familia familia = (Familia) aux.get(1);
         if (familia.getHabilitado() == 0) {
         session.setAttribute("usuario", familia);
         familia.setPass(pass);
         servicioEtapa.UpdateFamilia(familia);
         hibermail.generateAndSendEmail(familia.getCorreo(), pass_plano, familia.getUser());
         mensaje = "Una nueva contraseña se ha enviado a su correo de contacto asociado a su cuenta"
         + ". Por favor, revisar su bandeja.";
         map.addAttribute("mensaje", mensaje);
         pagina = "login";
         } else {
         map.addAttribute("mensaje", mensaje);
         pagina = "login";
         }
         } else if (aux.get(0) == "representante") {
         Entidad entidad = (Entidad) aux.get(1);
         session.setAttribute("usuario", entidad);
         entidad.setPass(pass);
         ServicioPersonal.UpdateAut(entidad, null);
         Organismo org = ServicioPersonal.getOrganismobyentidad(entidad.getIdentidad());

         Representante rep = ServicioPersonal.getRepresentantebyOrganismo(org.getIdorganismo());
         hibermail.generateAndSendEmail(rep.getCorreo(), pass_plano, entidad.getUser());
         mensaje = "Una nueva contraseña se ha enviado a su correo de contacto asociado a su cuenta"
         + ". Por favor, revisar su bandeja.";
         map.addAttribute("mensaje", mensaje);
         pagina = "login";                        

         } else if (aux.get(0) == "autoridad") {
         Entidad entidad = (Entidad) aux.get(1);
         session.setAttribute("usuario", entidad);
         entidad.setPass(pass);
         ServicioPersonal.UpdateAut(entidad, null);
            
            
         hibermail.generateAndSendEmail(entidad.getCorreo(), pass_plano, entidad.getUser());
         mensaje = "Una nueva contraseña se ha enviado a su correo de contacto asociado a su cuenta"
         + ". Por favor, revisar su bandeja.";
         map.addAttribute("mensaje", mensaje);
         pagina = "login";

         }else if (user.equals("")) {
         mensaje = "Por favor llenar el campo solicitado";
         map.addAttribute("mensaje", mensaje);
         pagina = "record_contra";
         } else {
         mensaje = "El Usuario no está registrado en nuestra base de datos. Por favor, comunicarse con el coordinador de adopciones";
         map.addAttribute("mensaje", mensaje);
         pagina = "login";
         }
         */
        return new ModelAndView(pagina, map);
    }

    @RequestMapping(value = "/MainListaNnaAdoptadosExtranjero", method = RequestMethod.POST)
    public ModelAndView MainListaNnaAdoptadosExtranjero(ModelMap map,
            @RequestParam("idExpediente") Long idExpediente,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.addAttribute("df", df);
        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(idExpediente));
        return new ModelAndView("/Personal/nna_adop_ext/lista_nna", map);
    }

    @RequestMapping(value = "/MainRegistrarAdopcionExtranjero", method = RequestMethod.POST)
    public ModelAndView MainRegistrarAdopcionExtranjero(ModelMap map,
            @RequestParam("idExpediente") Long idExpediente,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.addAttribute("df", df);
        map.addAttribute("idExpediente", idExpediente);
        return new ModelAndView("/Personal/nna_adop_ext/editar_nna", map);
    }

    @RequestMapping(value = "/MainCrearNna", method = RequestMethod.POST)
    public ModelAndView MainCrearNna(ModelMap map, HttpSession session,
            @RequestParam("idExpediente") Long idExpediente,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidoP", required = false) String apellidoP,
            @RequestParam(value = "apellidoM", required = false) String apellidoM,
            @RequestParam(value = "sexo", required = false) String sexo,
            @RequestParam(value = "fechaNac", required = false) String fechaNac,
            @RequestParam(value = "edad", required = false) String edad,
            @RequestParam(value = "meses", required = false) String meses,
            @RequestParam(value = "paisNac", required = false) String paisNac,
            @RequestParam(value = "dep", required = false) String dep,
            @RequestParam(value = "prov", required = false) String prov,
            @RequestParam(value = "dist", required = false) String dist,
            @RequestParam(value = "direccion", required = false) String direccion,
            @RequestParam(value = "incesto") String incesto,
            @RequestParam(value = "mental") String mental,
            @RequestParam(value = "epilepsia") String epilepsia,
            @RequestParam(value = "abuso") String abuso,
            @RequestParam(value = "sifilis") String sifilis,
            @RequestParam(value = "seguimiento") String seguimiento,
            @RequestParam(value = "operacion") String operacion,
            @RequestParam(value = "hiperactivo") String hiperactivo,
            @RequestParam(value = "especial") String especial,
            @RequestParam(value = "salud") String salud,
            @RequestParam(value = "mayor") String mayor,
            @RequestParam(value = "adolescente") String adolescente,
            @RequestParam(value = "hermanos") String hermanos,
            @RequestParam(value = "fechaAdopcion", required = false) String fechaAdopcion,
            @RequestParam(value = "numAdopcion", required = false) String numAdopcion,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        Designacion tempDesig = new Designacion();
        ExpedienteFamilia tempExp = new ExpedienteFamilia();

        tempExp = servicioEtapa.getExpedienteFamilia(idExpediente);
        tempNna.setNombre(nombre);
        tempNna.setApellidoP(apellidoP);
        tempNna.setApellidoM(apellidoM);
        tempNna.setSexo(sexo);
        if (fechaNac != null && !fechaNac.equals("")) {
            tempNna.setFechaNacimiento(df.stringToDate(fechaNac));
        }
        short edadtemp = 0;
        short mesestemp = 0;
        if (edad != null && !edad.equals("")) {
            edadtemp = Byte.valueOf(edad);
        }
        if (meses != null && !meses.equals("")) {
            mesestemp = Byte.valueOf(meses);
        }
        tempNna.setEdadAnhos(edadtemp);
        tempNna.setEdadMeses(mesestemp);

        tempNna.setJuzgado(ServicioPersonal.ListaJuzgado().iterator().next());
        tempNna.setCar(ServicioPersonal.ListaCar().iterator().next());
        tempNna.setPaisNacimiento(paisNac);
        tempNna.setDepartamentoNacimiento(dep);
        tempNna.setProvinciaNacimiento(prov);
        tempNna.setDistritoNacimiento(dist);
        tempNna.setLugarNac(direccion);

        tempNna.setIncesto(Short.parseShort(incesto));
        tempNna.setMental(Short.parseShort(mental));
        tempNna.setEpilepsia(Short.parseShort(epilepsia));
        tempNna.setAbuso(Short.parseShort(abuso));
        tempNna.setSifilis(Short.parseShort(sifilis));
        tempNna.setSeguiMedico(Short.parseShort(seguimiento));
        tempNna.setOperacion(Short.parseShort(operacion));
        tempNna.setHiperactivo(Short.parseShort(hiperactivo));

        tempNna.setEspecial(Short.parseShort(especial));
        tempNna.setEnfermo(Short.parseShort(salud));
        tempNna.setMayor(Short.parseShort(mayor));
        tempNna.setAdolescente(Short.parseShort(adolescente));
        tempNna.setHermano(Short.parseShort(hermanos));

        tempNna.setClasificacion("internacional");
        tempNna.setObservaciones(obs);

        tempDesig.setAceptacionConsejo(Short.parseShort("4"));
        if (fechaAdopcion != null && !fechaAdopcion.equals("")) {
            tempDesig.setFechaConsejo(df.stringToDate(fechaAdopcion));
            tempDesig.setFechaPropuesta(df.stringToDate(fechaAdopcion));
        } else {
            tempDesig.setFechaConsejo(null);
            tempDesig.setFechaPropuesta(null);
        }

        tempDesig.setNDesignacion(numAdopcion);
        long prioridad = 1;
        tempDesig.setPrioridad(prioridad);
        tempDesig.setTipoPropuesta("extranjero");

        tempDesig.setExpedienteFamilia(tempExp);
        tempDesig.setNna(tempNna);
        tempDesig.setPersonal(usuario);

        ServicioMain.crearAdopcionAdoptantesExtranjero(tempNna, tempDesig);

        String mensaje_log = "Se registró una nueva Adopción en el extranjero a la base de datos con Nombre del NNA, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());
        String Tipo_registro = "NNA";

        //try{
        String Numero_registro = String.valueOf(tempNna.getIdnna());

        ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);

        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(idExpediente));
        return new ModelAndView("/Personal/nna_adop_ext/lista_nna", map);

    }

    @RequestMapping(value = "/MainEditarNna", method = RequestMethod.POST)
    public ModelAndView MainEditarNna(ModelMap map,
            @RequestParam("idDesig") Long idDesig,
            @RequestParam("idNna") Long idNna,
            @RequestParam("fechaAdopcion") String fechaAdopcion,
            @RequestParam("numAdopcion") String numAdopcion,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.addAttribute("df", df);
        map.addAttribute("idDesig", idDesig);
        map.addAttribute("fechaAdopcion", fechaAdopcion);
        map.addAttribute("numAdopcion", numAdopcion);
        map.addAttribute("nna", ServicioMain.getNnaAdoptantesExtranjero(idNna));
        return new ModelAndView("/Personal/nna_adop_ext/editar_nna", map);
    }

    @RequestMapping(value = "/MainUpdateNna", method = RequestMethod.POST)
    public ModelAndView MainUpdateNna(ModelMap map, HttpSession session,
            @RequestParam("idDesig") Long idDesig,
            @RequestParam("idNna") Long idNna,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidoP", required = false) String apellidoP,
            @RequestParam(value = "apellidoM", required = false) String apellidoM,
            @RequestParam(value = "sexo", required = false) String sexo,
            @RequestParam(value = "fechaNac", required = false) String fechaNac,
            @RequestParam(value = "edad", required = false) String edad,
            @RequestParam(value = "meses", required = false) String meses,
            @RequestParam(value = "paisNac", required = false) String paisNac,
            @RequestParam(value = "dep", required = false) String dep,
            @RequestParam(value = "prov", required = false) String prov,
            @RequestParam(value = "dist", required = false) String dist,
            @RequestParam(value = "direccion", required = false) String direccion,
            @RequestParam(value = "incesto") String incesto,
            @RequestParam(value = "mental") String mental,
            @RequestParam(value = "epilepsia") String epilepsia,
            @RequestParam(value = "abuso") String abuso,
            @RequestParam(value = "sifilis") String sifilis,
            @RequestParam(value = "seguimiento") String seguimiento,
            @RequestParam(value = "operacion") String operacion,
            @RequestParam(value = "hiperactivo") String hiperactivo,
            @RequestParam(value = "especial") String especial,
            @RequestParam(value = "salud") String salud,
            @RequestParam(value = "mayor") String mayor,
            @RequestParam(value = "adolescente") String adolescente,
            @RequestParam(value = "hermanos") String hermanos,
            @RequestParam(value = "fechaAdopcion", required = false) String fechaAdopcion,
            @RequestParam(value = "numAdopcion", required = false) String numAdopcion,
            @RequestParam(value = "obs", required = false) String obs
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Nna tempNna = new Nna();
        Designacion tempDesig = new Designacion();

        tempNna = ServicioMain.getNnaAdoptantesExtranjero(idNna);
        tempDesig = ServicioMain.getDesignacionAdoptantesExtranjero(idDesig);

        tempNna.setNombre(nombre);
        tempNna.setApellidoP(apellidoP);
        tempNna.setApellidoM(apellidoM);
        tempNna.setSexo(sexo);
        if (fechaNac != null && !fechaNac.equals("")) {
            tempNna.setFechaNacimiento(df.stringToDate(fechaNac));
        }
        short edadtemp = 0;
        short mesestemp = 0;
        if (edad != null && !edad.equals("")) {
            edadtemp = Byte.valueOf(edad);
        }
        if (meses != null && !meses.equals("")) {
            mesestemp = Byte.valueOf(meses);
        }
        tempNna.setEdadAnhos(edadtemp);
        tempNna.setEdadMeses(mesestemp);
        tempNna.setPaisNacimiento(paisNac);
        tempNna.setDepartamentoNacimiento(dep);
        tempNna.setProvinciaNacimiento(prov);
        tempNna.setDistritoNacimiento(dist);
        tempNna.setLugarNac(direccion);

        tempNna.setIncesto(Short.parseShort(incesto));
        tempNna.setMental(Short.parseShort(mental));
        tempNna.setEpilepsia(Short.parseShort(epilepsia));
        tempNna.setAbuso(Short.parseShort(abuso));
        tempNna.setSifilis(Short.parseShort(sifilis));
        tempNna.setSeguiMedico(Short.parseShort(seguimiento));
        tempNna.setOperacion(Short.parseShort(operacion));
        tempNna.setHiperactivo(Short.parseShort(hiperactivo));

        tempNna.setEspecial(Short.parseShort(especial));
        tempNna.setEnfermo(Short.parseShort(salud));
        tempNna.setMayor(Short.parseShort(mayor));
        tempNna.setAdolescente(Short.parseShort(adolescente));
        tempNna.setHermano(Short.parseShort(hermanos));

        tempNna.setObservaciones(obs);

        if (fechaAdopcion != null && !fechaAdopcion.equals("")) {
            tempDesig.setFechaConsejo(df.stringToDate(fechaAdopcion));
            tempDesig.setFechaPropuesta(df.stringToDate(fechaAdopcion));
        } else {
            tempDesig.setFechaConsejo(null);
            tempDesig.setFechaPropuesta(null);
        }

        tempDesig.setNDesignacion(numAdopcion);
        ServicioMain.crearAdopcionAdoptantesExtranjero(tempNna, tempDesig);

        String mensaje_log = "Se actualizó una Adopción en el extranjero a la base de datos con Nombre del NNA, " + tempNna.getNombre() + " y ID: " + String.valueOf(tempNna.getIdnna());
        String Tipo_registro = "NNA";

        String Numero_registro = String.valueOf(tempNna.getIdnna());

        ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);

        map.addAttribute("df", df);
        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(tempDesig.getExpedienteFamilia().getIdexpedienteFamilia()));
        return new ModelAndView("/Personal/nna_adop_ext/lista_nna", map);

    }

    /*  SECCIÓN DEDICADA AL PROCESO DE ADOPCION PRIORITARIOS*/
    @RequestMapping(value = "/MainPrioritariosInicio", method = RequestMethod.POST)
    public ModelAndView MainPrioritariosInicio(ModelMap map,
            @RequestParam("idNna") Long idNna,
            @RequestParam(value = "editarExpedienteNna", required = false) String editarExpedienteNna,
            @RequestParam(value = "editarNna", required = false) String editarNna,
            @RequestParam(value = "registrarRev", required = false) String registrarRev,
            Long[] listaNna,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (editarNna != null) {
            map.addAttribute("idNna", idNna);
            return new ModelAndView("forward:/editarNna", map);
        } else if (editarExpedienteNna != null) {
            map.addAttribute("idNna", idNna);
            return new ModelAndView("forward:/editarExpedienteNna", map);
        } else if (registrarRev != null) {
            if (listaNna == null) {
                String mensaje = "Debe elegir al menos un NNA para iniciar el proceso de revisión de expediente";
                map.put("mensaje", mensaje);
                map.put("listaNna", ServicioNna.ListaNna("prioritario"));
                return new ModelAndView("/Personal/nna/lista_nna_prior", map);
            } else {
                ArrayList<Nna> tempListaN = new ArrayList();
                for (Long long1 : listaNna) {
                    Nna tempN = ServicioNna.getNna(long1);
                    tempListaN.add(tempN);
                }
                listaNnaRevision = tempListaN;
                listaRevision.clear();
                map.put("listaNna", listaNnaRevision);
                return new ModelAndView("/Personal/nna/reg_revision", map);
            }

        } else {
            if (listaNna == null) {
                String mensaje = "Debe elegir al menos un NNA para iniciar el proceso de estudio de caso";
                map.put("mensaje", mensaje);
                map.put("listaNna", ServicioNna.ListaNna("prioritario"));
                return new ModelAndView("/Personal/nna/lista_nna_prior", map);
            } else {
                ArrayList<Nna> tempListaN = new ArrayList();
                for (Long long1 : listaNna) {
                    Nna tempN = ServicioNna.getNna(long1);
                    tempListaN.add(tempN);
                }
                listaNnaEstudio = tempListaN;
                listaFamiliasEstudio.clear();
                map.put("listaNna", listaNnaEstudio);
                return new ModelAndView("/Personal/nna/reg_estudio", map);
            }
        }
    }

    @RequestMapping(value = "/MainInsertarRevision", method = RequestMethod.POST)
    public ModelAndView MainInsertarRevision(ModelMap map, HttpSession session,
            @RequestParam(value = "numero", required = false) String numero,
            @RequestParam(value = "comentarios", required = false) String comentarios,
            @RequestParam(value = "agregarFamilia", required = false) String agregarFamilia,
            @RequestParam(value = "agregarEntidad", required = false) String agregarEntidad,
            @RequestParam(value = "eliminar", required = false) String eliminar,
            @RequestParam(value = "registrar", required = false) String registrar,
            @RequestParam(value = "idNna", required = false) String idNna,
            String[] delete,
            String[] fecha
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (agregarFamilia != null) {
            map.put("df", df);
            map.addAttribute("idNna", idNna);
            return new ModelAndView("/Personal/nna/agregar_exp_revision", map);
        }
        if (agregarEntidad != null) {
            map.put("df", df);
            map.addAttribute("idNna", idNna);
            return new ModelAndView("/Personal/nna/agregar_entidad_revision", map);
        }
        if (eliminar != null && delete != null) {
//            for (int i : delete) {
//                listaRevision.remove(i);
//            }
            for (Iterator<Object> iterator = listaRevision.iterator(); iterator.hasNext();) {
                Object temp = iterator.next();
                for (String ident : delete) {
                    if (temp instanceof Entidad) {
                        Entidad temporalEnt = (Entidad) temp;
                        if (temporalEnt.getNombre().equals(ident)) {
                            iterator.remove();
                        }
                    } else if (temp instanceof ExpedienteFamilia) {
                        ExpedienteFamilia temporalEF = (ExpedienteFamilia) temp;
                        if (temporalEF.getExpediente().equals(ident)) {
                            iterator.remove();
                        }
                    }
                }

            }
            map.put("df", df);
            map.put("listaNna", listaNnaRevision);
            map.put("listaRevision", listaRevision);
            map.addAttribute("idNna", idNna);
            return new ModelAndView("/Personal/nna/reg_revision", map);
        }
        if (registrar != null && numero != null && !numero.equals("")) {
            String existeRev = ServicioMain.verificarExistenciaIDEstudio(numero);
            if (existeRev.equals("si")) {
                String mensaje = "el identificador ingresado ya existe";
                map.put("mensaje", mensaje);
                map.put("listaNna", listaNnaRevision);
                map.put("listaRevision", listaRevision);
                map.put("df", df);
                return new ModelAndView("/Personal/nna/reg_revision", map);
            }
            if (!listaRevision.isEmpty()) {
                for (int i = 0; i < listaRevision.size(); i++) {
                    Object temp = listaRevision.get(i);
                    if (temp instanceof Entidad) {
                        Entidad temporalEnt = (Entidad) temp;
                        for (Nna tempN : listaNnaRevision) {
                            Revision tempRev = new Revision();
                            tempRev.setNna(tempN);
                            tempRev.setIdEntidad(temporalEnt.getIdentidad());
                            tempRev.setNombre(temporalEnt.getNombre());
                            tempRev.setNumero(numero);
                            tempRev.setComentarios(comentarios);
                            if (fecha[i] != null && !fecha[i].equals("")) {
                                Date fechaRev = df.stringToDate(fecha[i]);
                                tempRev.setFechaRevision(fechaRev);
                            } else {
                                tempRev.setFechaRevision(null);
                            }
                            ServicioMain.crearRevision(tempRev);
                            String mensaje_log = "Se registró nueva revisión de expediente de NNA con ID: " + String.valueOf(tempN.getNombre() + tempN.getApellidoP() + tempN.getApellidoM());
                            String Tipo_registro = "Registro";

                            try {
                                String Numero_registro = tempRev.getNumero();

                                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                            } catch (Exception ex) {
                            }
                        }

                    } else if (temp instanceof ExpedienteFamilia) {
                        ExpedienteFamilia temporalEF = (ExpedienteFamilia) temp;
                        for (Nna tempN : listaNnaRevision) {
                            Revision tempRev = new Revision();
                            tempRev.setNna(tempN);
                            tempRev.setExpedienteFamilia(temporalEF);
                            tempRev.setNumero(numero);
                            tempRev.setComentarios(comentarios);
                            if (fecha[i] != null && !fecha[i].equals("")) {
                                Date fechaRev = df.stringToDate(fecha[i]);
                                tempRev.setFechaRevision(fechaRev);
                            } else {
                                tempRev.setFechaRevision(null);
                            }
                            ServicioMain.crearRevision(tempRev);
                            String mensaje_log = "Se registró nueva revisión de expediente de NNA con ID: " + String.valueOf(tempN.getNombre() + tempN.getApellidoP() + tempN.getApellidoM());
                            String Tipo_registro = "Registro";

                            try {
                                String Numero_registro = tempRev.getNumero();

                                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
                map.put("listaRevision", ServicioMain.getListaRevisiones());
                return new ModelAndView("/Personal/nna/lista_revision", map);
            }
            if (listaRevision.isEmpty()) {
                String mensaje = "Debe tener al menos una familia u Organismo en la lista";
                map.put("mensaje", mensaje);
            }
        } else if (registrar != null && (numero == null || numero.equals(""))) {
            String mensaje = "Debe ingresar el identificador de la Revisión de expediente";
            map.put("mensaje", mensaje);
        }
        map.put("listaNna", listaNnaRevision);
        map.put("listaRevision", listaRevision);
        map.put("df", df);
        return new ModelAndView("/Personal/nna/reg_revision", map);
    }

    @RequestMapping(value = "/MainBuscarExpedienteRevision", method = RequestMethod.POST)
    public ModelAndView MainBuscarExpedienteRevision(ModelMap map, HttpSession session,
            @RequestParam("exp") String exp
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
        listaBusqueda = servicioEtapa.listaInfoFamilias(exp);

        map.put("df", df);
        map.put("listaRevision", listaRevision);
        map.put("listaBusqueda", listaBusqueda);
        return new ModelAndView("/Personal/nna/agregar_exp_revision", map);

    }

    @RequestMapping(value = "/MainBuscarEntidadRevision", method = RequestMethod.POST)
    public ModelAndView MainBuscarEntidadRevision(ModelMap map, HttpSession session,
            @RequestParam("ent") String ent
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<Entidad> listaEntidades = new ArrayList();
        listaEntidades = servicioEtapa.listaEntidadesRevision(ent);

        map.put("df", df);
        map.put("listaRevision", listaRevision);
        map.put("listaEntidades", listaEntidades); //aqui me kede
        return new ModelAndView("/Personal/nna/agregar_entidad_revision", map);

    }

    @RequestMapping(value = "/MainAgregarExpedienteRevision", method = RequestMethod.POST)
    public ModelAndView MainAgregarExpedienteRevision(ModelMap map, HttpSession session, long[] idExpediente
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (idExpediente != null) {
            for (long l : idExpediente) {
                ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
                listaRevision.add(tempExp);
            }
        }
        map.put("listaNna", listaNnaRevision);
        map.put("listaRevision", listaRevision);
        map.put("df", df);
        return new ModelAndView("/Personal/nna/reg_revision", map);

    }

    @RequestMapping(value = "/MainAgregarEntidadRevision", method = RequestMethod.POST)
    public ModelAndView MainAgregarEntidadRevision(ModelMap map, HttpSession session, long[] idEntidad
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (idEntidad != null) {
            for (long l : idEntidad) {
                Entidad tempEnt = ServicioPersonal.getEntidad(l);
                listaRevision.add(tempEnt);
            }
        }
        map.put("listaNna", listaNnaRevision);
        map.put("listaRevision", listaRevision);
        map.put("df", df);
        return new ModelAndView("/Personal/nna/reg_revision", map);

    }

    @RequestMapping(value = "/MainEditarRevision", method = RequestMethod.POST)
    public ModelAndView EditarRevision(ModelMap map, HttpSession session,
            @RequestParam("numero") String numero
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        listaFamiliasEstudio.clear();
        ArrayList<Long> allID = new ArrayList();
        ArrayList<Nna> listaDeNna = new ArrayList();
        ArrayList<ExpedienteFamilia> listaDeExpedientes = new ArrayList();
        ArrayList<Entidad> listaDeEntidades = new ArrayList();
        allID = servicioEtapa.listaNnaDeRevision(numero);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                listaDeNna.add(nnaInfo);
            }
        }
        allID.clear();
        allID = servicioEtapa.listaExpedientesDeRevision(numero);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                ExpedienteFamilia expInfo = ServicioMain.getInformacionRegistro(id);
                listaDeExpedientes.add(expInfo);
            }
        }
        listaFamiliasEstudio = listaDeExpedientes;
        allID.clear();

        allID = servicioEtapa.listaOrganismosDeRevision(numero);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Entidad entidadInfo = ServicioPersonal.getEntidad(id);
                listaDeEntidades.add(entidadInfo);
            }
        }
        allID.clear();

        map.put("listaNna", listaDeNna);
        map.put("listaExpedientes", listaFamiliasEstudio);
        map.put("listaEntidades", listaDeEntidades);
        ArrayList<Revision> allRevisiones = new ArrayList();
        allRevisiones = ServicioMain.getListaRevisionesPorNumero(numero);
        map.put("listaRevisiones", allRevisiones);
        map.put("df", df);
        map.addAttribute("numero", numero);
        return new ModelAndView("/Personal/nna/edit_revision", map);

    }

    @RequestMapping(value = "/MainGuardarRevision", method = RequestMethod.POST)
    public ModelAndView MainGuardarRevision(ModelMap map, HttpSession session,
            @RequestParam("numero") String numero,
            @RequestParam("coments") String coments
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        listaFamiliasEstudio.clear();
        ArrayList<Revision> allRevisiones = new ArrayList();
        allRevisiones = ServicioMain.getListaRevisionesPorNumero(numero);

        for (Revision revision : allRevisiones) {
            revision.setComentarios(coments);
            ServicioMain.crearRevision(revision);
        }

        ArrayList<Long> allID = new ArrayList();
        ArrayList<Nna> listaDeNna = new ArrayList();
        ArrayList<ExpedienteFamilia> listaDeExpedientes = new ArrayList();
        ArrayList<Entidad> listaDeEntidades = new ArrayList();
        allID = servicioEtapa.listaNnaDeRevision(numero);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                listaDeNna.add(nnaInfo);
            }
        }
        allID.clear();
        allID = servicioEtapa.listaExpedientesDeRevision(numero);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                ExpedienteFamilia expInfo = ServicioMain.getInformacionRegistro(id);
                listaDeExpedientes.add(expInfo);
            }
        }
        listaFamiliasEstudio = listaDeExpedientes;
        allID.clear();

        allID = servicioEtapa.listaOrganismosDeRevision(numero);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Entidad entidadInfo = ServicioPersonal.getEntidad(id);
                listaDeEntidades.add(entidadInfo);
            }
        }
        allID.clear();

        map.put("listaNna", listaDeNna);
        map.put("listaExpedientes", listaFamiliasEstudio);
        map.put("listaEntidades", listaDeEntidades);
        allRevisiones = ServicioMain.getListaRevisionesPorNumero(numero);
        map.put("listaRevisiones", allRevisiones);
        map.put("df", df);
        map.addAttribute("numero", numero);
        return new ModelAndView("/Personal/nna/edit_revision", map);

    }

    @RequestMapping(value = "/listaRevision", method = RequestMethod.GET)
    public ModelAndView listaRevision(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaRevision", ServicioMain.getListaRevisiones());
        return new ModelAndView("/Personal/nna/lista_revision", map);

    }

    @RequestMapping(value = "/listaEstudio", method = RequestMethod.GET)
    public ModelAndView listaEstudio(ModelMap map, HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("listaEstudios", ServicioMain.getListaEstudios());
        return new ModelAndView("/Personal/nna/lista_estudios", map);

    }

    @RequestMapping(value = "/MainInsertarEstudio", method = RequestMethod.POST)
    public ModelAndView MainInsertarEstudio(ModelMap map, HttpSession session,
            @RequestParam(value = "orden", required = false) String orden,
            @RequestParam(value = "agregar", required = false) String agregar,
            @RequestParam(value = "eliminar", required = false) String eliminar,
            @RequestParam(value = "registrar", required = false) String registrar,
            Long[] delete,
            Long[] prioridad,
            Long[] idNna,
            @RequestParam(value = "numero", required = false) String numero
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        if (agregar != null) {
            map.put("df", df);
            map.addAttribute("numero", numero);
            return new ModelAndView("/Personal/nna/agregar_exp_prioritario", map);
        }
        if (eliminar != null && delete != null) {
            for (Iterator<ExpedienteFamilia> iterator = listaFamiliasEstudio.iterator(); iterator.hasNext();) {
                ExpedienteFamilia temp = iterator.next();
                for (Long ident : delete) {
                    if (temp.getIdexpedienteFamilia() == ident) {
                        iterator.remove();
                    }
                }
            }
            map.put("df", df);
            map.put("listaNna", listaNnaEstudio);
            map.put("listaEstudioCaso", listaFamiliasEstudio);
            return new ModelAndView("/Personal/nna/reg_estudio", map);
        }
        if (registrar != null) {
            String existencia = ServicioMain.verificarExistenciaIDEstudio(orden);
            if (existencia.equals("si")) {
                String mensaje = "El identificador ingresado ya se encuentra en el sistema";
                map.put("df", df);
                map.put("mensaje", mensaje);
                map.put("listaNna", listaNnaEstudio);
                map.put("listaEstudioCaso", listaFamiliasEstudio);
                return new ModelAndView("/Personal/nna/reg_estudio", map);
            }
            if (orden == null || orden.equals("")) {
                String mensaje = "Debe ingresar el identificador de este estudio de caso";
                map.put("df", df);
                map.put("mensaje", mensaje);
                map.put("listaNna", listaNnaEstudio);
                map.put("listaEstudioCaso", listaFamiliasEstudio);
                return new ModelAndView("/Personal/nna/reg_estudio", map);
            } else if (listaFamiliasEstudio.isEmpty()) {
                String mensaje = "Debe tener al menos una familia en la lista";
                map.put("df", df);
                map.put("mensaje", mensaje);
                map.put("listaNna", listaNnaEstudio);
                map.put("listaEstudioCaso", listaFamiliasEstudio);
                return new ModelAndView("/Personal/nna/reg_estudio", map);
            } else {
                for (int i = 0; i < listaFamiliasEstudio.size(); i++) {
                    for (int j = 0; j < listaNnaEstudio.size(); j++) {
                        EstudioCaso tempEst = new EstudioCaso();
                        tempEst.setNna(listaNnaEstudio.get(j));
                        tempEst.setExpedienteFamilia(listaFamiliasEstudio.get(i));
                        ExpedienteFamilia expFam1 = listaFamiliasEstudio.get(i);
                        expFam1.setEstado("estudio");
                        servicioEtapa.updateExpedienteFamilia(expFam1);
                        tempEst.setOrden(orden);
//                
                        tempEst.setPrioridad(prioridad[i]);
                        long nsolicitud = 1;
                        tempEst.setNSolicitud(nsolicitud);
                        servicioEtapa.crearEstudioCaso(tempEst);
//
                        String mensaje_log = "Se registró nueva familia al estudio de caso del NNA con Nombres: "
                                + listaNnaEstudio.get(j).getNombre() + listaNnaEstudio.get(j).getApellidoP() + listaNnaEstudio.get(j).getApellidoM();
                        String Tipo_registro = "Estudio";
//
                        try {
                            String Numero_registro = tempEst.getOrden();

                            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
                        } catch (Exception ex) {
                        }
                    }
                }
                map.addAttribute("idNna", idNna);
                map.addAttribute("numero", numero);
                map.put("listaEstudios", ServicioMain.getListaEstudios());
                return new ModelAndView("/Personal/nna/lista_estudios", map);
            }
        }
        return new ModelAndView("/Personal/nna/reg_estudio", map);
    }

    @RequestMapping(value = "/MainBuscarExpedientePrioritario", method = RequestMethod.POST)
    public ModelAndView MainBuscarExpedientePrioritario(ModelMap map, HttpSession session,
            @RequestParam("exp") String exp
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
        listaBusqueda = servicioEtapa.listaInfoFamilias(exp);

        map.put("df", df);
        map.put("listaEstudioCaso", listaFamiliasEstudio);
        map.put("listaBusqueda", listaBusqueda);
        return new ModelAndView("/Personal/nna/agregar_exp_prioritario", map);

    }

    @RequestMapping(value = "/MainAgregarExpedientePrioritario", method = RequestMethod.POST)
    public ModelAndView MainAgregarExpedientePrioritario(ModelMap map, HttpSession session, long[] idExpediente
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (idExpediente != null) {
            for (long l : idExpediente) {
                ExpedienteFamilia tempExp = servicioEtapa.getInfoFamilia(l);
                listaFamiliasEstudio.add(tempExp);
            }
        }
        map.put("listaNna", listaNnaEstudio);
        map.put("listaEstudioCaso", listaFamiliasEstudio);
        map.put("df", df);
        return new ModelAndView("/Personal/nna/reg_estudio", map);

    }

    @RequestMapping(value = "/MainEditarEstudio", method = RequestMethod.POST)
    public ModelAndView EditarEstudio(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<EstudioCaso> listaEstudiosCaso = new ArrayList();
        listaEstudiosCaso = servicioEtapa.listaExpedientesDeEstudio(orden);

        map.put("df", df);

        ArrayList<Long> allID = new ArrayList();
        ArrayList<Nna> listaDeNna = new ArrayList();
        allID = servicioEtapa.listaNnaDeEstudio(orden);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                listaDeNna.add(nnaInfo);
            }
        }
        allID.clear();
        map.put("listaNna", listaDeNna);
        map.put("listaEstudios", listaEstudiosCaso);
        return new ModelAndView("/Personal/nna/edit_estudio", map);

    }

    @RequestMapping(value = "/MainActualizarEstudio", method = RequestMethod.POST)
    public ModelAndView MainActualizarEstudio(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden,
            Long[] idExpFam,
            String[] resultado,
            String[] fechaEst,
            @RequestParam("elegido") int elegido
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<Long> allID = new ArrayList();
        ArrayList<Nna> listaDeNna = new ArrayList();
        allID = servicioEtapa.listaNnaDeEstudio(orden);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                listaDeNna.add(nnaInfo);
            }
        }
        allID.clear();

        if (resultado[elegido].equals("acep")) {

            for (Nna nna : listaDeNna) {
                EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam[elegido], orden);
                if (fechaEst[elegido] != null && !fechaEst[elegido].equals("")) {
                    tempEst.setFechaEstudio(df.stringToDate(fechaEst[elegido]));
                } else if (fechaEst[elegido] == null && fechaEst[elegido].equals("")) {
                    tempEst.setFechaEstudio(null);
                }
                tempEst.setResultado(resultado[elegido]);
                servicioEtapa.updateEstudioCaso(tempEst);

                String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
                String Tipo_registro = "Estu_Caso";

                //try{
                String Numero_registro = tempEst.getOrden();

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            }

            ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
            allEstudioCaso = servicioEtapa.getListaEstudioCasoOrden(orden);
            for (EstudioCaso estudioCaso : allEstudioCaso) {
                if (estudioCaso.getResultado() == null) {
                    estudioCaso.setResultado("noobs");
                    servicioEtapa.updateEstudioCaso(estudioCaso);
                    ExpedienteFamilia tempExp = estudioCaso.getExpedienteFamilia();
                    tempExp = estudioCaso.getExpedienteFamilia();
                    tempExp.setEstado("espera");
                    servicioEtapa.updateExpedienteFamilia(tempExp);
                }
            }

            map.put("df", df);
            map.put("listaNna", listaDeNna);
            map.put("listaEstudios", servicioEtapa.listaExpedientesDeEstudio(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        } else if (resultado[elegido].equals("noacep")) {
            for (Nna nna : listaDeNna) {
                EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam[elegido], orden);
                if (fechaEst[elegido] != null && !fechaEst[elegido].equals("")) {
                    tempEst.setFechaEstudio(df.stringToDate(fechaEst[elegido]));
                } else if (fechaEst[elegido] == null && fechaEst[elegido].equals("")) {
                    tempEst.setFechaEstudio(null);
                }

                tempEst.setResultado(resultado[elegido]);
                servicioEtapa.updateEstudioCaso(tempEst);
                ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
                tempExp = tempEst.getExpedienteFamilia();
                tempExp.setEstado("espera");
                servicioEtapa.updateExpedienteFamilia(tempExp);

                String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
                String Tipo_registro = "Estu_Caso";

                //try{
                String Numero_registro = tempEst.getOrden();

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            }
            map.put("df", df);
            map.put("listaNna", listaDeNna);
            map.put("listaEstudios", servicioEtapa.listaExpedientesDeEstudio(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        } else if (resultado[elegido].equals("noobs")) {
            for (Nna nna : listaDeNna) {
                EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam[elegido], orden);
                tempEst.setResultado(resultado[elegido]);
                servicioEtapa.updateEstudioCaso(tempEst);
                ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
                tempExp = tempEst.getExpedienteFamilia();
                tempExp.setEstado("espera");
                servicioEtapa.updateExpedienteFamilia(tempExp);

                String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
                String Tipo_registro = "Estu_Caso";

                //try{
                String Numero_registro = tempEst.getOrden();

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            }
            map.put("df", df);
            map.put("listaNna", listaDeNna);
            map.put("listaEstudios", servicioEtapa.listaExpedientesDeEstudio(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        } else {
            for (Nna nna : listaDeNna) {
                EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam[elegido], orden);
                if (fechaEst[elegido] != null && !fechaEst[elegido].equals("")) {
                    tempEst.setFechaEstudio(df.stringToDate(fechaEst[elegido]));
                } else if (fechaEst[elegido] == null && fechaEst[elegido].equals("")) {
                    tempEst.setFechaEstudio(null);
                }
                tempEst.setResultado(resultado[elegido]);
                servicioEtapa.updateEstudioCaso(tempEst);

                String mensaje_log = "Se editó el estudio de caso con Orden: " + tempEst.getOrden() + " y ID: " + String.valueOf(tempEst.getIdestudioCaso());
                String Tipo_registro = "Estu_Caso";

                //try{
                String Numero_registro = tempEst.getOrden();

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            }
            map.put("df", df);
            map.put("listaNna", listaDeNna);
            map.put("listaEstudios", servicioEtapa.listaExpedientesDeEstudio(orden));
            return new ModelAndView("/Personal/nna/edit_estudio", map);
        }
    }

    @RequestMapping(value = "/MainGuardarFechaSolicitud", method = RequestMethod.POST)
    public ModelAndView MainGuardarFechaSolicitud(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden,
            @RequestParam("fechaSolicitud") String fechaSolicitud,
            @RequestParam("idExpFam") long idExpFam
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<Long> allID = new ArrayList();
        ArrayList<Nna> listaDeNna = new ArrayList();
        allID = servicioEtapa.listaNnaDeEstudio(orden);
        if (!allID.isEmpty()) {
            for (Long id : allID) {
                Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                listaDeNna.add(nnaInfo);
            }
        }

        allID.clear();
        for (Nna nna : listaDeNna) {
            EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam, orden);
            if (fechaSolicitud != null && !fechaSolicitud.equals("")) {
                tempEst.setFechaSolAdop(df.stringToDate(fechaSolicitud));
            } else if (fechaSolicitud == null && fechaSolicitud.equals("")) {
                tempEst.setFechaSolAdop(null);
            }
            ServicioMain.updateEstudio(tempEst);
        }
        map.put("df", df);
        map.put("listaNna", listaDeNna);
        map.put("listaEstudios", servicioEtapa.listaExpedientesDeEstudio(orden));
        return new ModelAndView("/Personal/nna/edit_estudio", map);

    }

    @RequestMapping(value = "/MainGenerarDesignacionPrioritario", method = RequestMethod.POST)
    public ModelAndView MainGenerarDesignacionPrioritario(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden,
            @RequestParam("numDesig") String numDesig,
            @RequestParam("idExpFam") long idExpFam,
            @RequestParam("fechaPropuesta") String fechaPropuesta
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (numDesig == null || numDesig.equals("") || fechaPropuesta == null || fechaPropuesta.equals("")) {
            ArrayList<EstudioCaso> listaEstudiosCaso = new ArrayList();
            listaEstudiosCaso = servicioEtapa.listaExpedientesDeEstudio(orden);

            ArrayList<Long> allID = new ArrayList();
            ArrayList<Nna> listaDeNna = new ArrayList();
            allID = servicioEtapa.listaNnaDeEstudio(orden);
            if (!allID.isEmpty()) {
                for (Long id : allID) {
                    Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                    listaDeNna.add(nnaInfo);
                }
            }

            allID.clear();
            map.put("mensaje", "Debe llenar los datos correctamente");
            map.put("df", df);
            map.put("listaNna", listaDeNna);
            map.put("listaEstudios", listaEstudiosCaso);
            return new ModelAndView("/Personal/nna/edit_estudio", map);

        } else {
            ArrayList<Long> allID = new ArrayList();
            ArrayList<Nna> listaDeNna = new ArrayList();
            allID = servicioEtapa.listaNnaDeEstudio(orden);
            if (!allID.isEmpty()) {
                for (Long id : allID) {
                    Nna nnaInfo = ServicioMain.getTodosDatosNna(id);
                    listaDeNna.add(nnaInfo);
                }
            }

            allID.clear();

            for (Nna nna : listaDeNna) {
                EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam, orden);
                long nsol = 0;
                tempEst.setNSolicitud(nsol);
                ServicioMain.updateEstudio(tempEst);
            }

            for (Nna nna : listaDeNna) {
                EstudioCaso tempEst = ServicioMain.getEstudioCasoEspecifico(nna.getIdnna(), idExpFam, orden);
                ExpedienteFamilia tempExp = tempEst.getExpedienteFamilia();
                Nna tempNna = nna;
                Designacion tempDesign = new Designacion();
                tempDesign.setExpedienteFamilia(tempExp);
                tempDesign.setNna(tempNna);
                tempDesign.setPersonal(usuario);
                tempDesign.setNDesignacion(numDesig);
                tempDesign.setTipoPropuesta("directa");
                tempDesign.setAceptacionConsejo(Short.parseShort("1"));
                Date fechaPropuestaDesig = df.stringToDate(fechaPropuesta);
                tempDesign.setFechaPropuesta(fechaPropuestaDesig);
                servicioEtapa.crearDesignacion(tempDesign);
                tempExp.setEstado("designado");
                servicioEtapa.updateExpedienteFamilia(tempExp);
                ExpedienteNna tempExpNna = ServicioNna.getExpNna(tempNna.getIdnna());
                tempExpNna.setEstado("desig");
                Date ahora = new Date();
                java.sql.Date sql = new java.sql.Date(ahora.getTime());
                tempExpNna.setFechaEstado(sql);
                ServicioNna.updateExpNna(tempExpNna);
            }
            map.put("listaDesignaciones", servicioEtapa.getListaDesignaciones());
            return new ModelAndView("/Personal/Buscador_etapa/etapa_designacion/etapa_designacion", map);
        }
    }

}
