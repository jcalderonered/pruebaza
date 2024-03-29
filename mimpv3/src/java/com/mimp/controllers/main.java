/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;
/*
    CONTROLADOR USADO PARA CUBRIR DIVERSAS FUNCIONES ENTRE LAS CUALES SE ENCUENTRAN LA INSCRIPCIÓN A 
    LA SESIÓN INFORMATIVA, VISUALIZACIÓN DE LA INFORMACIÓN DE FAMILIAS DURANTE SU PASO POR LAS ETAPAS DEL PROCESO
    DE ADOPCIÓN Y EL REGISTRO DE LA REVISIÓN DE EXPEDIENTE Y ESTUDIO DE CASO PARA LOS NNA´s.
*/
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

    //CAMBIO DEL 10/06/2015
    @Resource(name = "HiberFamilia")
    private HiberFamilia ServicioFamilia = new HiberFamilia();
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

    //PROCESO DE LOGEO AL SISTEMA
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

            email = (String) session.getAttribute("email");
            pass = (String) session.getAttribute("password");

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
    // FIN DEL PROCESO DE LOGEO
    
    
    // INICIO DEL PROCESO DE INSCRIPCIÓN A LA SESIÓN INFORMATIVA
    @RequestMapping("/SesionInfInicioPrev")
    public String SesionInfInicioPrev(ModelMap map) {
        map.put("listaUa", ServicioPersonal.ListaUa());
        return "/Inscripcion/inscripcion_prev";
    }

 
    @RequestMapping(value = "/SesionInfInicio", method = RequestMethod.POST)
    public ModelAndView SesionInfInicio_POST(ModelMap map, @RequestParam("ua") String ua) {

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
                if (now.after((Date) temp.get(i).getInicioInscripcion()) && now.before((Date) temp.get(i).getFinInscripcion()) && temp.get(i).getSesion().getUnidad().equals(ua)) {
                    if (i == temp.size() - 1 && temp.get(i).getAsistenciaFTs().size() >= temp.get(i).getVacantes()) {
                        ArrayList<Sesion> tempSesiones = new ArrayList();
                        tempSesiones = ServicioMain.listaSesionesSiguientes(temp.get(i).getSesion().getFecha(), ua);
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
            ArrayList<Turno> turnosSesionesHabilitados = new ArrayList(); //busco turnos de sesiones habilitadas
            for (Turno turno : temp) {
                if(turno.getSesion().getHabilitado() == 0){
                    turnosSesionesHabilitados.add(turno);
                }
            }
            map.put("listaTurnos", turnosSesionesHabilitados);
            return new ModelAndView(pagina, map);
        }
        pagina = "/Inscripcion/no_sesion_prog";
        return new ModelAndView(pagina);
    }

    
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
            session.removeAttribute("idTurno");

            return new ModelAndView("redirect:/", map);
        }
        session.removeAttribute("idTurno");

        
        Turno tempTurno = new Turno();
        tempTurno = ServicioMain.getTurno(turno);
        map.addAttribute("idTurno", turno);
        map.addAttribute("objTurno", tempTurno);
        return new ModelAndView("/Inscripcion/inscripcion_sesion2", map);

    }

   
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

        if (estado.equals("casados") || estado.equals("convivientes")) {
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

    //FIN DEL PROCESO DE INSCRIPCIÓN DE UNA FAMILIA A LA SESIÓN INFORMATIVA
    
    /**
     * ESTA SECCION ES USADA PARA ACTUALIZAR LOS DATOS DE LA FAMILIA POR PARTE
     * DEL PERSONAL*
     */
    
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

        if (session.getAttribute("idFamilia") != null) {
            idFamilia = session.getAttribute("idFamilia").toString();
        }
        if (session.getAttribute("idExpediente") != null) {
            idExpediente = session.getAttribute("idExpediente").toString();
        }
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        /*LIMPIANDO LOS ANTIGUOS VALORES DE LOS ADOPTANTES */
        Ella = new Adoptante();
        El = new Adoptante();

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
            //CAMBIO DEL 10/06/2015 
            //SE INICIALIZA EN NULL EL VALOR DEL EXPEDIENTE PORQUE EN PREPARACION NO SE TIENE UN EXPEDIENTE
            expediente = null;
            map.put("idFamilia", idFam);
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idFamilia_sesion", idFam);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("infoFam_sesion", infoFam);
            //
            
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              //session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
                        
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
        
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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
       
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("listaNnaAdoptados_sesion", listaNnaAdoptados);
              session.setAttribute("infoFam_sesion", infoFam);
            //
        }

        etapaOrigen = estado;
        //
        session.setAttribute("etapaOrigen_sesion", etapaOrigen);
        //
        map.put("df", df);
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("Ella", session.getAttribute("ella_sesion"));
        map.addAttribute("volver", volver);

        return new ModelAndView("/Personal/familia/info_ella", map);
    }

    
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

        if (session.getAttribute("idNna") != null) {
            idNna = Long.parseLong(session.getAttribute("idNna").toString());
        }
        if (session.getAttribute("idFamilia") != null) {
            idFamilia = session.getAttribute("idFamilia").toString();
        }
        if (session.getAttribute("idExpediente") != null) {
            idExpediente = session.getAttribute("idExpediente").toString();
        }
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }
        if (session.getAttribute("estado") != null) {
            estado = session.getAttribute("estado").toString();
        }
        if (session.getAttribute("expediente2") != null) {
            expediente2 = session.getAttribute("expediente2").toString();
        }
        if (session.getAttribute("HT") != null) {
            HT = session.getAttribute("HT").toString();
        }
        if (session.getAttribute("nacionalidad") != null) {
            nacionalidad = session.getAttribute("nacionalidad").toString();
        }
        if (session.getAttribute("tipofamilia") != null) {
            tipofamilia = session.getAttribute("tipofamilia").toString();
        }
        if (session.getAttribute("estado2") != null) {
            estado2 = session.getAttribute("estado2").toString();
        }

        session.removeAttribute("estado");
        session.removeAttribute("idFamilia");
        session.removeAttribute("idExpediente");
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

        /*LIMPIANDO LOS ANTIGUOS VALORES DE LOS ADOPTANTES */
        Ella = new Adoptante();
        El = new Adoptante();

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
            //CAMBIO DEL 10/06/2015 
            //SE INICIALIZA EN NULL EL VALOR DEL EXPEDIENTE PORQUE EN PREPARACION NO SE TIENE UN EXPEDIENTE
            expediente = null;
            map.put("idFamilia", idFam);
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idFamilia_sesion", idFam);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("infoFam_sesion", infoFam);
            //
            
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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

             //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              //session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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
        
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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
       
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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

            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("infoFam_sesion", infoFam);
            //
              
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
            //
              session.setAttribute("el_sesion", El);
              session.setAttribute("ella_sesion", Ella);
              session.setAttribute("idExp_sesion", idExp);
              session.setAttribute("expediente_sesion", expediente);
              session.setAttribute("listaAtenciones_sesion", listaAtenciones);
              session.setAttribute("listaSesiones_sesion", listaSesiones);
              session.setAttribute("listaAsistenciaReuniones_sesion", listaAsistenciaReuniones);
              session.setAttribute("listaEvaluaciones_sesion", listaEvaluaciones);
              session.setAttribute("listaDesignaciones_sesion", listaDesignaciones);
              session.setAttribute("listaEstudios_sesion", listaEstudios);
              session.setAttribute("listaNnaAdoptados_sesion", listaNnaAdoptados);
              session.setAttribute("infoFam_sesion", infoFam);
            //
        }

        etapaOrigen = estado;
        //
        session.setAttribute("etapaOrigen_sesion", etapaOrigen);
        //
        map.put("df", df);
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("Ella", session.getAttribute("ella_sesion"));
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);

        return new ModelAndView("/Personal/Buscador/familia/info_ella", map);
    }

    // SECCIÓN DEDICADA AL PROCESO DE HABILITACIÓN/DESHABILITACIÓN DE UNA FAMILIA PARA INGRESAR
    // AL SISTEMA
    
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

    //ENVIA INFORMACIÓN DE EL SOLICITANTE A LA VISTA CORRESPONDIENTE
    
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
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("El", session.getAttribute("el_sesion"));
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_el", map);
    }

    
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
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }
        if (session.getAttribute("expediente2") != null) {
            expediente2 = session.getAttribute("expediente2").toString();
        }
        if (session.getAttribute("estado2") != null) {
            estado2 = session.getAttribute("estado2").toString();
        }
        if (session.getAttribute("HT") != null) {
            HT = session.getAttribute("HT").toString();
        }
        if (session.getAttribute("nacionalidad") != null) {
            nacionalidad = session.getAttribute("nacionalidad").toString();
        }
        if (session.getAttribute("tipofamilia") != null) {
            tipofamilia = session.getAttribute("tipofamilia").toString();
        }
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
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("El", session.getAttribute("el_sesion"));
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_el", map);
    }

    // ENVIA INFORMACIÓN DE LA SOLICITANTE A LA VISTA CORRESPONDIENTE
    
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
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df", df);
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("Ella", session.getAttribute("ella_sesion"));
        map.put("volver", volver);
        return new ModelAndView("/Personal/familia/info_ella", map);
    }

   
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
        session.setAttribute("expediente2", expediente2);
        session.setAttribute("HT", HT);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("tipofamilia", tipofamilia);
        session.setAttribute("estado2", estado2);

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
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }
        if (session.getAttribute("expediente2") != null) {
            expediente2 = session.getAttribute("expediente2").toString();
        }
        if (session.getAttribute("HT") != null) {
            HT = session.getAttribute("HT").toString();
        }
        if (session.getAttribute("nacionalidad") != null) {
            nacionalidad = session.getAttribute("nacionalidad").toString();
        }
        if (session.getAttribute("tipofamilia") != null) {
            tipofamilia = session.getAttribute("tipofamilia").toString();
        }
        if (session.getAttribute("estado2") != null) {
            estado2 = session.getAttribute("estado2").toString();
        }
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
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("Ella", session.getAttribute("ella_sesion"));
        map.put("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_ella", map);
    }

    //ENVÍA INFORMACIÓN DE LA COMPOSICIÓN FAMILIAR A LA VISTA CORRESPONDIENTE
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

    //ENVIÍA LA INFORMACIÓN DE LA VIVIENDA A LA VISTA CORRESPONDIENTE
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

    //ENVÍA LA INFORMACIÓN DEL EXPEDIENTE A LA VISTA CORRESPONDIENTE
    @RequestMapping(value = "/infoExpediente", method = RequestMethod.POST)
    public ModelAndView infoExpediente_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            HttpSession session) {
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/infoExpediente", map);
    }

    @RequestMapping(value = "/infoExpediente", method = RequestMethod.GET)
    public ModelAndView infoExpediente_GET(ModelMap map,
            HttpSession session) {
        String volver = "";
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("listaUA", ServicioPersonal.ListaUa());
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_registro", map);
    }

   
    @RequestMapping(value = "/infoExpediente2", method = RequestMethod.POST)
    public ModelAndView infoExpediente2_POST(ModelMap map,
            @RequestParam(value = "volver", required = false) String volver,
            @RequestParam(value = "expediente", required = false) String expediente2,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado2,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia,
            HttpSession session) {
        session.setAttribute("volver", volver);
        session.setAttribute("expediente2", expediente2);
        session.setAttribute("HT", HT);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("estado2", estado2);
        session.setAttribute("tipofamilia", tipofamilia);

        return new ModelAndView("redirect:/infoExpediente2", map);
    }

    @RequestMapping(value = "/infoExpediente2", method = RequestMethod.GET)
    public ModelAndView infoExpediente2(ModelMap map,
            HttpSession session) {
        String volver = "";
        String expediente2 = "";
        String HT = "";
        String nacionalidad = "";
        String estado2 = "";
        String tipofamilia = "";
        if (session.getAttribute("volver") != null) {
            volver = session.getAttribute("volver").toString();
        }
        if (session.getAttribute("expediente2") != null) {
            expediente2 = session.getAttribute("expediente2").toString();
        }
        if (session.getAttribute("HT") != null) {
            HT = session.getAttribute("HT").toString();
        }
        if (session.getAttribute("nacionalidad") != null) {
            nacionalidad = session.getAttribute("nacionalidad").toString();
        }
        if (session.getAttribute("tipofamilia") != null) {
            tipofamilia = session.getAttribute("tipofamilia").toString();
        }
        if (session.getAttribute("estado2") != null) {
            estado2 = session.getAttribute("estado2").toString();
        }
        session.removeAttribute("expediente2");
        session.removeAttribute("HT");
        session.removeAttribute("nacionalidad");
        session.removeAttribute("estado2");
        session.removeAttribute("tipofamilia");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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

    //ENVÍA INFORMACIÓN DE PROCESO DE ADOPCIÓN A LA VISTA CORRESPONDIENTE
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaSesiones", session.getAttribute("listaSesiones_sesion"));
        map.put("listaAsistenciaReuniones", session.getAttribute("listaAsistenciaReuniones_sesion"));
        map.put("listaEvaluaciones", session.getAttribute("listaEvaluaciones_sesion"));
        map.put("listaDesignaciones", session.getAttribute("listaDesignaciones_sesion"));
        map.put("listaEstudios", session.getAttribute("listaEstudios_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaSesiones", session.getAttribute("listaSesiones_sesion"));
        map.put("listaAsistenciaReuniones", session.getAttribute("listaAsistenciaReuniones_sesion"));
        map.put("listaEvaluaciones", session.getAttribute("listaEvaluaciones_sesion"));
        map.put("listaDesignaciones", session.getAttribute("listaDesignaciones_sesion"));
        map.put("listaEstudios", session.getAttribute("listaEstudios_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_adop/info_adop", map);
    }

    //ENVÍA INFORMACIÓN SOBRE LOS ANTECEDENTES DEL NNA A LA VISTA CORRESPONDIENTE
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_ant_nna", map);
    }

    //ENVÍA INFORMACIÓN DEL NNA ADOPTADO A LA VISTA CORRESPONDIENTE
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("listaNna", session.getAttribute("listaNnaAdoptados_sesion"));
        //map.put("exp", expNna);
        ExpedienteFamilia tempExp = new ExpedienteFamilia();
        tempExp = (ExpedienteFamilia) session.getAttribute("expediente_sesion");
        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(tempExp.getIdexpedienteFamilia()));
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("listaNna", session.getAttribute("listaNnaAdoptados_sesion"));
        //map.put("exp", expNna);
        ExpedienteFamilia tempExp = new ExpedienteFamilia();
        tempExp = (ExpedienteFamilia) session.getAttribute("expediente_sesion");
        map.put("listaDesig", ServicioMain.getListaDesignacionesAdoptantesExtranjero(tempExp.getIdexpedienteFamilia()));
        map.addAttribute("volver", volver);
        
        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_nna", map);
    }

    //ENVÍA LA INFORMACIÓN DE LAS ATENCIONES DE LA FAMILIA A LA VISTA CORRESPONDIENTE
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaAtenciones", session.getAttribute("listaAtenciones_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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
         map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaAtenciones", session.getAttribute("listaAtenciones_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion", map);
    }

    //PROCESO PARA AGREGAR UNA NUEVA ATENCIÓN A LA FAMILIA
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion_edit", map);
    }

    //SE MUESTRA LA INFORMACIÓN CORRESPONDIENTE A LA ATENCIÓN DE UNA FAMILIA
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("atencion", temp);
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("atencion", temp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        return new ModelAndView("/Personal/Buscador/familia/info_atencion_edit", map);
    }

    //PROCESO PARA GUARDAR  UNA ATENCIÓN EN LA BASE DE DATOS
    @RequestMapping(value = "/crearAtencion", method = RequestMethod.POST)
    public ModelAndView crearAtencion_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "personal") long personal,
            @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "hora") String hora,
            @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "detalle", required = false) String detalle,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        session.setAttribute("personal", personal);
        session.setAttribute("fecha", fecha);
        session.setAttribute("hora", hora);
        session.setAttribute("tipo", tipo);
        session.setAttribute("detalle", detalle);
        session.setAttribute("obs", obs);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/crearAtencion", map);
    }

    @RequestMapping(value = "/crearAtencion", method = RequestMethod.GET)
    public ModelAndView crearAtencion_GET(ModelMap map, HttpSession session) {
        long personal = 0;
        String fecha = "";
        String hora = "";
        String tipo = "";
        String detalle = "";
        String obs = "";
        String volver = "";
        try {
            personal = Long.parseLong(session.getAttribute("personal").toString());
            fecha = session.getAttribute("fecha").toString();
            hora = session.getAttribute("hora").toString();
            tipo = session.getAttribute("tipo").toString();
            if (session.getAttribute("detalle") != null) {
                detalle = session.getAttribute("detalle").toString();
            }
            if (session.getAttribute("obs") != null) {
                obs = session.getAttribute("obs").toString();
            }
            if (session.getAttribute("volver") != null) {
                volver = session.getAttribute("volver").toString();
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("personal");
        session.removeAttribute("fecha");
        session.removeAttribute("hora");
        session.removeAttribute("tipo");
        session.removeAttribute("detalle");
        session.removeAttribute("obs");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = new Atencion();
        InfoFamilia tempInfoFam = new InfoFamilia();
        tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        Familia tempFam = tempInfoFam.getFamilia();
        
        
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

        String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Agregó una nueva "
                + "atención perteneciente a la familia con ID: " + tempFam.getIdfamilia();
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempInfoFam.getFamilia().getIdfamilia());
        //
        session.setAttribute("listaAtenciones_sesion", listaAtenciones);
        //
        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaAtenciones", session.getAttribute("listaAtenciones_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion", map);
    }

   
    @RequestMapping(value = "/crearAtencion2", method = RequestMethod.POST)
    public ModelAndView crearAtencion2_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("personal", personal);
        session.setAttribute("fecha", fecha);
        session.setAttribute("hora", hora);
        session.setAttribute("tipo", tipo);
        session.setAttribute("detalle", detalle);
        session.setAttribute("obs", obs);
        session.setAttribute("expediente2", expediente2);
        session.setAttribute("HT", HT);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("estado2", estado2);
        session.setAttribute("tipofamilia", tipofamilia);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/crearAtencion2", map);
    }

    @RequestMapping(value = "/crearAtencion2", method = RequestMethod.GET)
    public ModelAndView crearAtencion2_GET(ModelMap map, HttpSession session) {
        long personal = 0;
        String fecha = "";
        String hora = "";
        String tipo = "";
        String detalle = "";
        String obs = "";
        String expediente2 = "";
        String HT = "";
        String nacionalidad = "";
        String estado2 = "";
        String tipofamilia = "";
        String volver = "";
        try {
            personal = Long.parseLong(session.getAttribute("personal").toString());
            fecha = session.getAttribute("fecha").toString();
            hora = session.getAttribute("hora").toString();
            tipo = session.getAttribute("tipo").toString();
            if (session.getAttribute("detalle") != null) {
                detalle = session.getAttribute("detalle").toString();
            }
            if (session.getAttribute("obs") != null) {
                obs = session.getAttribute("obs").toString();
            }
            if (session.getAttribute("expediente2") != null) {
                expediente2 = session.getAttribute("expediente2").toString();
            }
            if (session.getAttribute("HT") != null) {
                HT = session.getAttribute("HT").toString();
            }
            if (session.getAttribute("nacionalidad") != null) {
                nacionalidad = session.getAttribute("nacionalidad").toString();
            }
            if (session.getAttribute("estado2") != null) {
                estado2 = session.getAttribute("estado2").toString();
            }
            if (session.getAttribute("tipofamilia") != null) {
                tipofamilia = session.getAttribute("tipofamilia").toString();
            }
            if (session.getAttribute("volver") != null) {
                volver = session.getAttribute("volver").toString();
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("personal");
        session.removeAttribute("fecha");
        session.removeAttribute("hora");
        session.removeAttribute("tipo");
        session.removeAttribute("detalle");
        session.removeAttribute("obs");
        session.removeAttribute("expediente2");
        session.removeAttribute("HT");
        session.removeAttribute("nacionalidad");
        session.removeAttribute("estado2");
        session.removeAttribute("tipofamilia");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = new Atencion();
        //Familia tempFam = infoFam.getFamilia();
        InfoFamilia tempInfoFam = new InfoFamilia();
        tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        Familia tempFam = tempInfoFam.getFamilia();
        
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

        String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Agregó una nueva "
                + "atención perteneciente a la familia con ID: " + tempFam.getIdfamilia();
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempInfoFam.getFamilia().getIdfamilia());
        //
        session.setAttribute("listaAtenciones_sesion", listaAtenciones);
        //
        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaAtenciones", session.getAttribute("listaAtenciones_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);
        map.addAttribute("volver", volver);

        return new ModelAndView("/Personal/Buscador/familia/info_atencion", map);
    }

    //PROCESO PARA ACTUALIZAR LA INFORMACIÓN DE UNA ATENCIÓN
    @RequestMapping(value = "/updateAtencion", method = RequestMethod.POST)
    public ModelAndView updateAtencion_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "idAtencion") long idAtencion,
            @RequestParam(value = "personal") long personal,
            @RequestParam(value = "fecha") String fecha,
            @RequestParam(value = "hora") String hora,
            @RequestParam(value = "tipo") String tipo,
            @RequestParam(value = "detalle", required = false) String detalle,
            @RequestParam(value = "obs", required = false) String obs,
            @RequestParam(value = "volver", required = false) String volver
    ) {
        session.setAttribute("idAtencion", idAtencion);
        session.setAttribute("personal", personal);
        session.setAttribute("fecha", fecha);
        session.setAttribute("hora", hora);
        session.setAttribute("tipo", tipo);
        session.setAttribute("detalle", detalle);
        session.setAttribute("obs", obs);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/updateAtencion", map);
    }

    @RequestMapping(value = "/updateAtencion", method = RequestMethod.GET)
    public ModelAndView updateAtencion_GET(ModelMap map, HttpSession session) {
        long idAtencion = 0;
        long personal = 0;
        String fecha = "";
        String hora = "";
        String tipo = "";
        String detalle = "";
        String obs = "";
        String volver = "";
        try {
            idAtencion = Long.parseLong(session.getAttribute("idAtencion").toString());
            personal = Long.parseLong(session.getAttribute("personal").toString());
            fecha = (String) session.getAttribute("fecha");
            hora = (String) session.getAttribute("hora");
            tipo = (String) session.getAttribute("tipo");
            if (session.getAttribute("detalle") != null) {
                detalle = (String) session.getAttribute("detalle");
            }
            if (session.getAttribute("obs") != null) {
                obs = (String) session.getAttribute("obs");
            }
            if (session.getAttribute("volver") != null) {
                volver = (String) session.getAttribute("volver");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("idAtencion");
        session.removeAttribute("personal");
        session.removeAttribute("fecha");
        session.removeAttribute("hora");
        session.removeAttribute("tipo");
        session.removeAttribute("detalle");
        session.removeAttribute("obs");

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

        String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                + "atención con ID: " + idAtencion;
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
        //
        InfoFamilia tempInfoFam = new InfoFamilia();
        tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        //
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempInfoFam.getFamilia().getIdfamilia());
        //
        session.setAttribute("listaAtenciones_sesion", listaAtenciones);
        //
        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaAtenciones", session.getAttribute("listaAtenciones_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("volver", volver);
        return new ModelAndView("/Personal/familia/info_atencion", map);
    }


    @RequestMapping(value = "/updateAtencion2", method = RequestMethod.POST)
    public ModelAndView updateAtencion2_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idAtencion", idAtencion);
        session.setAttribute("personal", personal);
        session.setAttribute("fecha", fecha);
        session.setAttribute("hora", hora);
        session.setAttribute("tipo", tipo);
        session.setAttribute("detalle", detalle);
        session.setAttribute("obs", obs);
        session.setAttribute("expediente2", expediente2);
        session.setAttribute("HT", HT);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("estado2", estado2);
        session.setAttribute("tipofamilia", tipofamilia);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/updateAtencion2", map);
    }

    @RequestMapping(value = "/updateAtencion2", method = RequestMethod.GET)
    public ModelAndView updateAtencion2_GET(ModelMap map, HttpSession session) {
        long idAtencion = 0;
        long personal = 0;
        String fecha = "";
        String hora = "";
        String tipo = "";
        String detalle = "";
        String obs = "";
        String expediente2 = "";
        String HT = "";
        String nacionalidad = "";
        String estado2 = "";
        String tipofamilia = "";
        String volver = "";
        try {
            idAtencion = Long.parseLong(session.getAttribute("idAtencion").toString());
            personal = Long.parseLong(session.getAttribute("personal").toString());
            fecha = (String) session.getAttribute("fecha");
            hora = (String) session.getAttribute("hora");
            tipo = (String) session.getAttribute("tipo");
            if (session.getAttribute("detalle") != null) {
                detalle = (String) session.getAttribute("detalle");
            }
            if (session.getAttribute("obs") != null) {
                obs = (String) session.getAttribute("obs");
            }
            if (session.getAttribute("expediente2") != null) {
                expediente2 = (String) session.getAttribute("expediente2");
            }
            if (session.getAttribute("HT") != null) {
                HT = (String) session.getAttribute("HT");
            }
            if (session.getAttribute("nacionalidad") != null) {
                nacionalidad = (String) session.getAttribute("nacionalidad");
            }
            if (session.getAttribute("estado2") != null) {
                estado2 = (String) session.getAttribute("estado2");
            }
            if (session.getAttribute("tipofamilia") != null) {
                tipofamilia = (String) session.getAttribute("tipofamilia");
            }
            if (session.getAttribute("volver") != null) {
                volver = (String) session.getAttribute("volver");
            }

        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }

        session.removeAttribute(
                "idAtencion");
        session.removeAttribute(
                "personal");
        session.removeAttribute(
                "fecha");
        session.removeAttribute(
                "hora");
        session.removeAttribute(
                "tipo");
        session.removeAttribute(
                "detalle");
        session.removeAttribute(
                "obs");
        session.removeAttribute(
                "expediente2");
        session.removeAttribute(
                "HT");
        session.removeAttribute(
                "nacionalidad");
        session.removeAttribute(
                "estado2");
        session.removeAttribute(
                "tipofamilia");
        session.removeAttribute(
                "volver");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario
                == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Atencion tempAtn = ServicioMain.getAtencion(idAtencion);
        Personal tempPer = ServicioPersonal.getPersonal(personal);

        tempAtn.setPersonal(tempPer);
        if (fecha
                != null && !fecha.equals(
                        "")) {
            tempAtn.setFecha(df.stringToDate(fecha));
        }
        if (fecha
                == null || fecha.equals(
                        "")) {
            tempAtn.setFecha(null);
        }

        tempAtn.setHora(hora);

        tempAtn.setTipoAtencion(tipo);

        tempAtn.setDetalle(detalle);

        tempAtn.setObservacion(obs);

        ServicioMain.updateAtencion(tempAtn);

        String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                + "atención con ID: " + idAtencion;
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
         //
        InfoFamilia tempInfoFam = new InfoFamilia();
        tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        //
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempInfoFam.getFamilia().getIdfamilia());
        //
        session.setAttribute("listaAtenciones_sesion", listaAtenciones);
        //
        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("listaAtenciones", session.getAttribute("listaAtenciones_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("volver", volver);

        map.put(
                "expediente2", expediente2);
        map.put(
                "HT", HT);
        map.put(
                "nacionalidad", nacionalidad);
        map.put(
                "tipofamilia", tipofamilia);
        map.put(
                "estado2", estado2);
        map.put(
                "volver", volver);
        return new ModelAndView(
                "/Personal/Buscador/familia/info_atencion", map);
    }

    //ENVÍA INFORMACIÓN DE LAS SESIONES A LA QUE HA ASISTIDO UNA FAMILIA
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("sesion", tempSes);
        map.put("volver", volver);
        return new ModelAndView("/Personal/familia/info_adop/detalle_sesion", map);
    }

    //ENVÍA INFORMACIÓN DE LOS TALLERES A LOS QUE HA ASISTIDO UNA FAMILIA
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

        map.put("listaAsistencia", session.getAttribute("listaAsistenciaReuniones_sesion"));
        map.put("listaReuniones", allReuniones);
        map.put("tipoTaller", tipoTaller);
        map.put("nombreTaller", nombreTaller);
        map.put("nombreGrupo", nombreGrupo);
        map.put("nombreTurno", nombreTurno);

        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("volver", volver);

        return new ModelAndView("/Personal/familia/info_adop/detalle_taller", map);
    }

    //ENVÍA INFORMACIÓN DE LAS EVALUACIONES DE UNA FAMILIA
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
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.put("evaluacion", tempEval);
        return new ModelAndView("/Personal/familia/info_adop/detalle_evaluacion", map);
    }

    //PROCESO PARA ACTUALIZAR LA INFORMACIÓN DE UNA FAMILIA
    @RequestMapping(value = "/ActualizarInfoFamilia", method = RequestMethod.POST)
    public ModelAndView ActualizarInfoFamilia_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("incesto", incesto);
        session.setAttribute("mental", mental);
        session.setAttribute("epilepsia", epilepsia);
        session.setAttribute("abuso", abuso);
        session.setAttribute("sifilis", sifilis);
        session.setAttribute("seguimiento", seguimiento);
        session.setAttribute("operacion", operacion);
        session.setAttribute("hiperactivo", hiperactivo);
        session.setAttribute("especial", especial);
        session.setAttribute("salud", salud);
        session.setAttribute("mayor", mayor);
        session.setAttribute("adolescente", adolescente);
        session.setAttribute("hermanos", hermanos);
        session.setAttribute("viajar", viajar);
        session.setAttribute("edadMin", edadMin);
        session.setAttribute("edadMax", edadMax);
        session.setAttribute("genero", genero);
        session.setAttribute("numHijos", numHijos);
        session.setAttribute("nivel", nivel);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/ActualizarInfoFamilia", map);
    }

    @RequestMapping(value = "/ActualizarInfoFamilia", method = RequestMethod.GET)
    public ModelAndView ActualizarInfoFamilia_GET(ModelMap map, HttpSession session) {
        String incesto = "";
        String mental = "";
        String epilepsia = "";
        String abuso = "";
        String sifilis = "";
        String seguimiento = "";
        String operacion = "";
        String hiperactivo = "";
        String especial = "";
        String salud = "";
        String mayor = "";
        String adolescente = "";
        String hermanos = "";
        String viajar = "";
        String edadMin = "";
        String edadMax = "";
        String genero = "";
        String numHijos = "";
        String nivel = "";
        String volver = "";
        try {
            incesto = (String) session.getAttribute("incesto");
            mental = (String) session.getAttribute("mental");
            epilepsia = (String) session.getAttribute("epilepsia");
            abuso = (String) session.getAttribute("abuso");
            sifilis = (String) session.getAttribute("sifilis");
            seguimiento = (String) session.getAttribute("seguimiento");
            operacion = (String) session.getAttribute("operacion");
            hiperactivo = (String) session.getAttribute("hiperactivo");
            especial = (String) session.getAttribute("especial");
            salud = (String) session.getAttribute("salud");
            mayor = (String) session.getAttribute("mayor");
            adolescente = (String) session.getAttribute("adolescente");
            hermanos = (String) session.getAttribute("hermanos");
            viajar = (String) session.getAttribute("viajar");
            edadMin = (String) session.getAttribute("edadMin");
            edadMax = (String) session.getAttribute("edadMax");
            genero = (String) session.getAttribute("genero");
            numHijos = (String) session.getAttribute("numHijos");
            nivel = (String) session.getAttribute("nivel");
            if (session.getAttribute("volver") != null) {
                volver = (String) session.getAttribute("volver");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("incesto");
        session.removeAttribute("mental");
        session.removeAttribute("epilepsia");
        session.removeAttribute("abuso");
        session.removeAttribute("sifilis");
        session.removeAttribute("seguimiento");
        session.removeAttribute("operacion");
        session.removeAttribute("hiperactivo");
        session.removeAttribute("especial");
        session.removeAttribute("salud");
        session.removeAttribute("mayor");
        session.removeAttribute("adolescente");
        session.removeAttribute("hermanos");
        session.removeAttribute("viajar");
        session.removeAttribute("edadMin");
        session.removeAttribute("edadMax");
        session.removeAttribute("genero");
        session.removeAttribute("numHijos");
        session.removeAttribute("nivel");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //
        InfoFamilia tempInfoFam = new InfoFamilia();
        tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        //
        if (incesto != null && !incesto.equals("")) {
            tempInfoFam.setNnaIncesto(Short.parseShort(incesto));
        }
        if (mental != null && !mental.equals("")) {
            tempInfoFam.setNnaMental(Short.parseShort(mental));
        }
        if (epilepsia != null && !epilepsia.equals("")) {
            tempInfoFam.setNnaEpilepsia(Short.parseShort(epilepsia));
        }
        if (abuso != null && !abuso.equals("")) {
            tempInfoFam.setNnaAbuso(Short.parseShort(abuso));
        }
        if (sifilis != null && !sifilis.equals("")) {
            tempInfoFam.setNnaSifilis(Short.parseShort(sifilis));
        }
        if (seguimiento != null && !seguimiento.equals("")) {
            tempInfoFam.setNnaSeguiMedico(Short.parseShort(seguimiento));
        }
        if (operacion != null && !operacion.equals("")) {
            tempInfoFam.setNnaOperacion(Short.parseShort(operacion));
        }
        if (hiperactivo != null && !hiperactivo.equals("")) {
            tempInfoFam.setNnaHiperactivo(Short.parseShort(hiperactivo));
        }
        if (especial != null && !especial.equals("")) {
            tempInfoFam.setNnaEspecial(Short.parseShort(especial));
        }
        if (salud != null && !salud.equals("")) {
            tempInfoFam.setNnaEnfermo(Short.parseShort(salud));
        }
        if (mayor != null && !mayor.equals("")) {
            tempInfoFam.setNnaMayor(Short.parseShort(mayor));
        }
        if (adolescente != null && !adolescente.equals("")) {
            tempInfoFam.setNnaAdolescente(Short.parseShort(adolescente));
        }
        if (hermanos != null && !hermanos.equals("")) {
            tempInfoFam.setNnaHermano(Short.parseShort(hermanos));
        }

        if (viajar != null && !viajar.equals("")) {
            tempInfoFam.setPuedeViajar(Short.parseShort(viajar));
        }
        if (edadMin != null && !edadMin.equals("")) {
            tempInfoFam.setExpectativaEdadMin(Short.parseShort(edadMin));
        }
        if (edadMax != null && !edadMax.equals("")) {
            tempInfoFam.setExpectativaEdadMax(Short.parseShort(edadMax));
        }
        if (genero != null && !genero.equals("")) {
            tempInfoFam.setExpectativaGenero(genero);
        }
        if (numHijos != null && !numHijos.equals("")) {
            tempInfoFam.setnHijos(Short.parseShort(numHijos));
        }
        if (nivel != null && !nivel.equals("")) {
            char n = nivel.charAt(0);
            tempInfoFam.setNivelSocioeconomico(n);
        }

        ServicioMain.updateInfoFam(tempInfoFam);

        String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                + "información de la familia con ID: " + tempInfoFam.getFamilia().getIdfamilia();
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
        
        //
        session.setAttribute("infoFam_sesion", tempInfoFam);
        //
        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("infoFam", tempInfoFam);
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_ant_nna", map);
    }

    //PROCESO PARA ACTUALIZAR LA INFORMACIÓN DE UN EXPEDIENTE
    @RequestMapping(value = "/ActualizarRegistro", method = RequestMethod.POST)
    public ModelAndView ActualizarRegistro_POST(ModelMap map, HttpSession session,
            //@RequestParam(value="numero") long numero,  
            @RequestParam(value = "htFicha") String htFicha,
            @RequestParam(value = "nFicha") String nFicha,
            @RequestParam(value = "fechaIngresoFicha") String fechaIngresoFicha,
            @RequestParam(value = "ht") String ht,
            @RequestParam(value = "genNumExp", required = false) String genNumExp,
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
        session.setAttribute("htFicha", htFicha);
        session.setAttribute("nFicha", nFicha);
        session.setAttribute("fechaIngresoFicha", fechaIngresoFicha);
        session.setAttribute("ht", ht);
        session.setAttribute("genNumExp", genNumExp);
        session.setAttribute("numeroExp", numeroExp);
        session.setAttribute("fechaIngreso", fechaIngreso);
        session.setAttribute("tupa", tupa);
        session.setAttribute("nacionalidad", nacionalidad);
        session.setAttribute("tipoFamilia", tipoFamilia);
        session.setAttribute("tipoEspera", tipoEspera);
        session.setAttribute("unidad", unidad);
        session.setAttribute("entAsoc", entAsoc);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/ActualizarRegistro", map);
    }

    @RequestMapping(value = "/ActualizarRegistro", method = RequestMethod.GET)
    public ModelAndView ActualizarRegistro_GET(ModelMap map, HttpSession session) {
        String htFicha = "";
        String nFicha = "";
        String fechaIngresoFicha = "";
        String ht = "";
        String genNumExp = "";
        String numeroExp = "";
        String fechaIngreso = "";
        String tupa = "";
        String nacionalidad = "";
        String tipoFamilia = "";
        String tipoEspera = "";
        long unidad = 0;
        long entAsoc = 0;
        String volver = "";

        try {
            htFicha = (String) session.getAttribute("htFicha");
            nFicha = (String) session.getAttribute("nFicha");
            fechaIngresoFicha = (String) session.getAttribute("fechaIngresoFicha");
            ht = (String) session.getAttribute("ht");
            genNumExp = (String) session.getAttribute("genNumExp");
            numeroExp = (String) session.getAttribute("numeroExp");
            fechaIngreso = (String) session.getAttribute("fechaIngreso");
            tupa = (String) session.getAttribute("tupa");
            nacionalidad = (String) session.getAttribute("nacionalidad");
            tipoFamilia = (String) session.getAttribute("tipoFamilia");
            tipoEspera = (String) session.getAttribute("tipoEspera");
            unidad = Long.parseLong(session.getAttribute("unidad").toString());
            entAsoc = Long.parseLong(session.getAttribute("entAsoc").toString());
            if (session.getAttribute("volver") != null) {
                volver = (String) session.getAttribute("volver");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("htFicha");
        session.removeAttribute("nFicha");
        session.removeAttribute("fechaIngresoFicha");
        session.removeAttribute("ht");
        session.removeAttribute("genNumExp");
        session.removeAttribute("numeroExp");
        session.removeAttribute("fechaIngreso");
        session.removeAttribute("tupa");
        session.removeAttribute("nacionalidad");
        session.removeAttribute("tipoFamilia");
        session.removeAttribute("tipoEspera");
        session.removeAttribute("unidad");
        session.removeAttribute("entAsoc");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
            //
                ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                tempExpFam = (ExpedienteFamilia) session.getAttribute("expediente_sesion");
            //
        if (genNumExp != null && !genNumExp.equals("")) {

            String ID = "";
            int year = Calendar.getInstance().get(Calendar.YEAR);

            //Luego debo generar un nuevo ID
            ArrayList<String> allExpedientes = new ArrayList();
            allExpedientes = ServicioPersonal.listaNumExpActuales();
            
            if (!allExpedientes.isEmpty()) {
                int numElem = allExpedientes.size();
                int cont = 0;
                int idAct = 0;
                int idSig = 0;

                for (String expedienteFamilia : allExpedientes) {
                    numElem--;
                    String[] parts = expedienteFamilia.split("-");
                    idAct = Integer.parseInt(parts[0]);
                    cont++;
                    idSig = cont;
                    map.put("idAct", idAct);
                    map.put("idSig", idSig);
                    if (idAct == idSig) {
                        if (numElem == 0) {
                            cont++;
                            String idGen = String.format("%04d", cont);
                            ID = idGen + "-" + year + "-MIMP/DGA-S";
                            map.put("idGen", idGen);
                        }
                    } else {
                        String idGen = String.format("%04d", idSig);
                        ID = idGen + "-" + year + "-MIMP/DGA-S";
                        map.put("idGen", idGen);
                        break;
                    }

                }
            } else {

                ID = "0001-" + year + "-MIMP/DGA-S";
            }

            
            tempExpFam.setNumeroExpediente(ID);
            ServicioMain.updateExpFam(tempExpFam);
            String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                    + "información de la familia con ID: " + tempExpFam.getFamilia().getIdfamilia();
            String Tipo_registro = "Personal";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            //
            session.setAttribute("expediente_sesion", tempExpFam);
            //
            map.put("df", df);
            map.put("estado", session.getAttribute("etapaOrigen_sesion"));
            map.put("expediente", tempExpFam);
            map.put("listaUA", ServicioPersonal.ListaUa());
            map.put("listaEntidad", ServicioPersonal.ListaEntidades());
            map.addAttribute("volver", volver);
            return new ModelAndView("/Personal/familia/info_registro", map);
        }

        Unidad tempUa = ServicioPersonal.getUa(unidad);
        Entidad tempEnt = ServicioPersonal.getEntidad(entAsoc);

        //expediente.setNumero(numero);
        tempExpFam.setHtFicha(htFicha);
        tempExpFam.setnFicha(nFicha);
        if (fechaIngresoFicha != null && !fechaIngresoFicha.equals("")) {
            tempExpFam.setFechaIngresoFicha(df.stringToDate(fechaIngresoFicha));
        }
        if (fechaIngresoFicha == null || fechaIngresoFicha.equals("")) {
            tempExpFam.setFechaIngresoDga(null);
        }
        tempExpFam.setHt(ht);
        tempExpFam.setNumeroExpediente(numeroExp);
        if (fechaIngreso != null && !fechaIngreso.equals("")) {
            tempExpFam.setFechaIngresoDga(df.stringToDate(fechaIngreso));
        } else if (fechaIngreso == null || fechaIngreso.equals("")) {
            tempExpFam.setFechaIngresoDga(null);
        }
        if (tupa != null && !tupa.equals("")) {
            tempExpFam.setTupa(df.stringToDate(tupa));
        } else if (tupa == null || tupa.equals("")) {
            tempExpFam.setTupa(null);
        }

        tempExpFam.setNacionalidad(nacionalidad);
        //expediente.setRnsa(Short.parseShort(rnsa));
        //expediente.setRnaa(Short.parseShort(rnaa));

        tempExpFam.setTipoFamilia(tipoFamilia);
        tempExpFam.setTipoListaEspera(tipoEspera);

        tempExpFam.setUnidad(tempUa);
        tempExpFam.getFamilia().setEntidad(tempEnt);

        ServicioMain.updateExpFam(tempExpFam);
        ServicioMain.updateFam(tempExpFam.getFamilia());

        String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                + "información de la familia con ID: " + tempExpFam.getFamilia().getIdfamilia();
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(usuario.getIdpersonal());

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        //
            session.setAttribute("expediente_sesion", tempExpFam);
        //
        map.put("df", df);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", tempExpFam);
        map.put("listaUA", ServicioPersonal.ListaUa());
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        map.addAttribute("volver", volver);
        return new ModelAndView("/Personal/familia/info_registro", map);
    }

    //PROCESO PARA ACTUALIZAR LA INFORMACIÓN DE UN ADOPTANTE
    @RequestMapping(value = "/ActualizarAdoptante", method = RequestMethod.POST)
    public ModelAndView ActualizarAdoptante_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("adoptante", adoptante);
        session.setAttribute("nombre", nombre);
        session.setAttribute("apellidoP", apellidoP);
        session.setAttribute("apellidoM", apellidoM);
        session.setAttribute("fechaNac", fechaNac);
        session.setAttribute("lugarNac", lugarNac);
        session.setAttribute("depNac", depNac);
        session.setAttribute("paisNac", paisNac);
        session.setAttribute("doc", doc);
        session.setAttribute("numDoc", numDoc);
        session.setAttribute("pasaporte", pasaporte);
        session.setAttribute("numCel", numCel);
        session.setAttribute("correo", correo);
        session.setAttribute("estadoCivil", estadoCivil);
        session.setAttribute("fechaMat", fechaMat);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/ActualizarAdoptante", map);

    }

    @RequestMapping(value = "/ActualizarAdoptante", method = RequestMethod.GET)
    public ModelAndView ActualizarAdoptante_GET(ModelMap map, HttpSession session) {
        String adoptante = "";
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String fechaNac = "";
        String lugarNac = "";
        String depNac = "";
        String paisNac = "";
        String doc = "";
        String numDoc = "";
        String pasaporte = "";
        String numCel = "";
        String correo = "";
        String estadoCivil = "";
        String fechaMat = "";
        String volver = "";
        try {
            adoptante = (String) session.getAttribute("adoptante");
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        nombre = (String) session.getAttribute("nombre");
        apellidoP = (String) session.getAttribute("apellidoP");
        apellidoM = (String) session.getAttribute("apellidoM");
        fechaNac = (String) session.getAttribute("fechaNac");
        lugarNac = (String) session.getAttribute("lugarNac");
        depNac = (String) session.getAttribute("depNac");
        paisNac = (String) session.getAttribute("paisNac");
        doc = (String) session.getAttribute("doc");
        numDoc = (String) session.getAttribute("numDoc");
        pasaporte = (String) session.getAttribute("pasaporte");
        numCel = (String) session.getAttribute("numCel");
        correo = (String) session.getAttribute("correo");
        estadoCivil = (String) session.getAttribute("estadoCivil");
        fechaMat = (String) session.getAttribute("fechaMat");
        volver = (String) session.getAttribute("volver");
        session.removeAttribute("adoptante");
        session.removeAttribute("nombre");
        session.removeAttribute("apellidoP");
        session.removeAttribute("apellidoM");
        session.removeAttribute("fechaNac");
        session.removeAttribute("lugarNac");
        session.removeAttribute("depNac");
        session.removeAttribute("paisNac");
        session.removeAttribute("doc");
        session.removeAttribute("numDoc");
        session.removeAttribute("pasaporte");
        session.removeAttribute("numCel");
        session.removeAttribute("correo");
        session.removeAttribute("estadoCivil");
        session.removeAttribute("fechaMat");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //
        Adoptante tempEl = new Adoptante();
        Adoptante tempElla = new Adoptante();
        InfoFamilia tempInfoFam = new InfoFamilia();
        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
        
        tempEl = (Adoptante) session.getAttribute("el_sesion");
        tempElla = (Adoptante) session.getAttribute("ella_sesion");
        tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        tempExpFam = (ExpedienteFamilia) session.getAttribute("expediente_sesion");
        //
        if (adoptante != null && adoptante.equals("el")) {
            tempEl.setNombre(nombre);
            tempEl.setApellidoP(apellidoP);
            tempEl.setApellidoM(apellidoM);
            if (fechaNac != null && !fechaNac.equals("")) {
                tempEl.setFechaNac(df.stringToDate(fechaNac));
            }
            if (fechaNac == null || fechaNac.equals("")) {
                tempEl.setFechaNac(null);
            }
            tempEl.setLugarNac(lugarNac);
            tempEl.setDepaNac(depNac);
            tempEl.setPaisNac(paisNac);
            if (doc != null && !doc.equals("")) {
                char d = doc.charAt(0);
                tempEl.setTipoDoc(d);
            }
            tempEl.setNDoc(numDoc);
            tempEl.setPasaporte(pasaporte);
            tempEl.setCelular(numCel);
            tempEl.setCorreo(correo);
            tempInfoFam.setEstadoCivil(estadoCivil);
            if (tempInfoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")) {
                tempInfoFam.setFechaMatrimonio(df.stringToDate(fechaMat));
            } else if (fechaMat == null || fechaMat.equals("")) {
                tempInfoFam.setFechaMatrimonio(null);
            }
            ServicioMain.updateInfoFam(tempInfoFam);
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

            ServicioMain.updateAdoptante(tempEl);
            if ((tempEl.getApellidoP() != null && !tempEl.getApellidoP().equals("")) 
                 && (tempElla.getApellidoP() != null && !tempElla.getApellidoP().equals(""))
                 && tempExpFam != null    ) {
                tempExpFam.setExpediente(tempEl.getApellidoP() + " - " + tempElla.getApellidoP());
            } else if (tempEl.getApellidoP() != null && !tempEl.getApellidoP().equals("")
                  && tempExpFam != null   ) {
                tempExpFam.setExpediente(tempEl.getApellidoP());
            } else if (tempElla.getApellidoP() != null && !tempElla.getApellidoP().equals("")
                  && tempExpFam != null   ) {
                tempExpFam.setExpediente(tempElla.getApellidoP());
            }
            if(tempExpFam != null && tempExpFam.getIdexpedienteFamilia() != 0) servicioEtapa.updateExpedienteFamilia(tempExpFam);

            String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                    + "información del adoptante con ID: " + tempEl.getIdadoptante();
            String Tipo_registro = "Personal";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            session.setAttribute("el_sesion", tempEl);
            session.setAttribute("ella_sesion", tempElla);
            session.setAttribute("expediente_sesion", tempExpFam);
            session.setAttribute("infoFam_sesion", tempInfoFam);
            
            map.put("df", df);
            map.put("estado", session.getAttribute("etapaOrigen_sesion"));
            map.put("infoFam", tempInfoFam);
            map.put("expediente", tempExpFam);
            map.addAttribute("volver", volver);
            map.put("El", tempEl);
            return new ModelAndView("/Personal/familia/info_el", map);
        } else if (adoptante != null) {

            tempElla.setNombre(nombre);
            tempElla.setApellidoP(apellidoP);
            tempElla.setApellidoM(apellidoM);
            if (fechaNac != null && !fechaNac.equals("")) {
                tempElla.setFechaNac(df.stringToDate(fechaNac));
            }
            if (fechaNac == null || fechaNac.equals("")) {
                tempElla.setFechaNac(null);
            }
            tempElla.setLugarNac(lugarNac);
            tempElla.setDepaNac(depNac);
            tempElla.setPaisNac(paisNac);
            if (doc != null && !doc.equals("")) {
                char d = doc.charAt(0);
                tempElla.setTipoDoc(d);
            }
            tempElla.setNDoc(numDoc);
            tempElla.setPasaporte(pasaporte);
            tempElla.setCelular(numCel);
            tempElla.setCorreo(correo);
            tempInfoFam.setEstadoCivil(estadoCivil);
            if (tempInfoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")) {
                tempInfoFam.setFechaMatrimonio(df.stringToDate(fechaMat));
            } else if (fechaMat == null || fechaMat.equals("")) {
                tempInfoFam.setFechaMatrimonio(null);
            }
            ServicioMain.updateInfoFam(tempInfoFam);
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

            ServicioMain.updateAdoptante(tempElla);
            if ((tempEl.getApellidoP() != null && !tempEl.getApellidoP().equals("")) 
                 && (tempElla.getApellidoP() != null && !tempElla.getApellidoP().equals(""))
                 && tempExpFam != null   ) {
                tempExpFam.setExpediente(tempEl.getApellidoP() + " - " + tempElla.getApellidoP());
            } else if (tempEl.getApellidoP() != null && !tempEl.getApellidoP().equals("")
                 && tempExpFam != null   ) {
                tempExpFam.setExpediente(tempEl.getApellidoP());
            } else if (tempElla.getApellidoP() != null && !tempElla.getApellidoP().equals("")
                 && tempExpFam != null   ) {
                tempExpFam.setExpediente(tempElla.getApellidoP());
            }
            if(tempExpFam != null && tempExpFam.getIdexpedienteFamilia() != 0) servicioEtapa.updateExpedienteFamilia(tempExpFam);

            String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Editó la "
                    + "información del adoptante con ID: " + tempElla.getIdadoptante();
            String Tipo_registro = "Personal";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }
            session.setAttribute("el_sesion", tempEl);
            session.setAttribute("ella_sesion", tempElla);
            session.setAttribute("expediente_sesion", tempExpFam);
            session.setAttribute("infoFam_sesion", tempInfoFam);
            
            map.put("df", df);
            map.put("estado", session.getAttribute("etapaOrigen_sesion"));
            map.put("infoFam", tempInfoFam);
            map.put("expediente", tempExpFam);
            map.put("Ella", tempElla);
            map.addAttribute("volver", volver);
            return new ModelAndView("/Personal/familia/info_ella", map);

        } else {
            map.put("df", df);
            map.put("estado", session.getAttribute("etapaOrigen_sesion"));
            map.put("infoFam", tempInfoFam);
            map.put("expediente", tempExpFam);
            map.put("Ella", tempElla);
            map.addAttribute("volver", volver);
            return new ModelAndView("/Personal/familia/info_ella", map);
        }

    }

    //PROCESO PARA ACTUALIZAR LA INFORMACIÓN DE LA VIVIENDA DE LA FAMILIA
    @RequestMapping(value = "/ActualizarVivienda", method = RequestMethod.POST)
    public ModelAndView ActualizarVivienda_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("propiedadVivienda", propiedadVivienda);
        session.setAttribute("propiedadDescrip", propiedadDescrip);
        session.setAttribute("tipoVivienda", tipoVivienda);
        session.setAttribute("tipoDescrip", tipoDescrip);
        session.setAttribute("domicilio", domicilio);
        session.setAttribute("DepRes", DepRes);
        session.setAttribute("PaisRes", PaisRes);
        session.setAttribute("telefono", telefono);
        session.setAttribute("areaVivTotal", areaVivTotal);
        session.setAttribute("areaVivConst", areaVivConst);
        session.setAttribute("distViv", distViv);
        session.setAttribute("luz", luz);
        session.setAttribute("agua", agua);
        session.setAttribute("desague", desague);
        session.setAttribute("otrosServ", otrosServ);
        session.setAttribute("materConst", materConst);
        session.setAttribute("materConstDesc", materConstDesc);
        session.setAttribute("pared", pared);
        session.setAttribute("paredDesc", paredDesc);
        session.setAttribute("techo", techo);
        session.setAttribute("techoDesc", techoDesc);
        session.setAttribute("piso", piso);
        session.setAttribute("pisoDesc", pisoDesc);

        return new ModelAndView("redirect:/ActualizarVivienda", map);
    }

    @RequestMapping(value = "/ActualizarVivienda", method = RequestMethod.GET)
    public ModelAndView ActualizarVivienda_GET(ModelMap map, HttpSession session) {
        String propiedadVivienda = "";
        String propiedadDescrip = "";
        String tipoVivienda = "";
        String tipoDescrip = "";
        String domicilio = "";
        String DepRes = "";
        String PaisRes = "";
        String telefono = "";
        String areaVivTotal = "";
        String areaVivConst = "";
        String distViv = "";
        String luz = "";
        String agua = "";
        String desague = "";
        String otrosServ = "";
        String materConst = "";
        String materConstDesc = "";
        String pared = "";
        String paredDesc = "";
        String techo = "";
        String techoDesc = "";
        String piso = "";
        String pisoDesc = "";
        propiedadVivienda = (String) session.getAttribute("propiedadVivienda");
        propiedadDescrip = (String) session.getAttribute("propiedadDescrip");
        tipoVivienda = (String) session.getAttribute("tipoVivienda");
        tipoDescrip = (String) session.getAttribute("tipoDescrip");
        domicilio = (String) session.getAttribute("domicilio");
        DepRes = (String) session.getAttribute("DepRes");
        PaisRes = (String) session.getAttribute("PaisRes");
        telefono = (String) session.getAttribute("telefono");
        areaVivTotal = (String) session.getAttribute("areaVivTotal");
        areaVivConst = (String) session.getAttribute("areaVivConst");
        distViv = (String) session.getAttribute("distViv");
        luz = (String) session.getAttribute("luz");
        agua = (String) session.getAttribute("agua");
        desague = (String) session.getAttribute("desague");
        otrosServ = (String) session.getAttribute("otrosServ");
        materConst = (String) session.getAttribute("materConst");
        materConstDesc = (String) session.getAttribute("materConstDesc");
        pared = (String) session.getAttribute("pared");
        paredDesc = (String) session.getAttribute("paredDesc");
        techo = (String) session.getAttribute("techo");
        techoDesc = (String) session.getAttribute("techoDesc");
        piso = (String) session.getAttribute("piso");
        pisoDesc = (String) session.getAttribute("pisoDesc");
        session.removeAttribute("propiedadVivienda");
        session.removeAttribute("propiedadDescrip");
        session.removeAttribute("tipoVivienda");
        session.removeAttribute("tipoDescrip");
        session.removeAttribute("domicilio");
        session.removeAttribute("DepRes");
        session.removeAttribute("PaisRes");
        session.removeAttribute("telefono");
        session.removeAttribute("areaVivTotal");
        session.removeAttribute("areaVivConst");
        session.removeAttribute("distViv");
        session.removeAttribute("luz");
        session.removeAttribute("agua");
        session.removeAttribute("desague");
        session.removeAttribute("otrosServ");
        session.removeAttribute("materConst");
        session.removeAttribute("materConstDesc");
        session.removeAttribute("pared");
        session.removeAttribute("paredDesc");
        session.removeAttribute("techo");
        session.removeAttribute("techoDesc");
        session.removeAttribute("piso");
        session.removeAttribute("pisoDesc");

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

    //ENVÍA INFORMACIÓN DEL CRONOGRAMA ANUAL
    @RequestMapping(value = "/CronogramaAnual", method = RequestMethod.POST)
    public ModelAndView CronogramaAnual_GET(ModelMap map, HttpSession session, @RequestParam(value = "ua") String ua) {

        session.setAttribute("ua", ua);
        return new ModelAndView("redirect:/CronogramaAnual", map);
    }

    @RequestMapping(value = "/CronogramaAnual", method = RequestMethod.GET)
    public ModelAndView CronogramaAnual_POST(ModelMap map, HttpSession session) {

        String ua = "";
        try {
            ua = (String) session.getAttribute("ua");
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

    //GENERA EL EXPEDIENTE DE UNA FAMILIA NACIONAL
    @RequestMapping(value = "/GenerarExpNac", method = RequestMethod.POST)
    public ModelAndView GenerarExpNac(ModelMap map, @RequestParam(value = "idFamilia") long idFamilia,
            @RequestParam(value = "el", required = false) String el,
            @RequestParam(value = "ella", required = false) String ella,
            HttpSession session) {

        Personal usuario = (Personal) session.getAttribute("usuario");

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

    //PROCESO PARA GUARDAR UN EXPEDIENTE NACIONAL EN LA BASE DE DATOS
    @RequestMapping(value = "/CrearExpNac", method = RequestMethod.POST)
    public ModelAndView CrearExpNac_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "idFamilia") long idFamilia,
            @RequestParam(value = "numeroFicha") String numeroFicha,
            @RequestParam(value = "ht") String ht,
            @RequestParam(value = "exp") String exp,
            @RequestParam(value = "fechaIngresoFicha") String fechaIngresoFicha,
            @RequestParam(value = "entAsoc", required = false) String entAsoc
    ) {
        session.setAttribute("idFamilia", idFamilia);
        session.setAttribute("numeroFicha", numeroFicha);
        session.setAttribute("ht", ht);
        session.setAttribute("exp", exp);
        session.setAttribute("fechaIngresoFicha", fechaIngresoFicha);
        session.setAttribute("entAsoc", entAsoc);

        return new ModelAndView("redirect:/CrearExpNac", map);
    }

    @RequestMapping(value = "/CrearExpNac", method = RequestMethod.GET)
    public ModelAndView CrearExpNac_GET(ModelMap map, HttpSession session) {
        long idFamilia = 0;
        String numeroFicha = "";
        String exp = "";
        String ht = "";
        String fechaIngresoFicha = "";
        String entAsoc = "";
        try {
            idFamilia = Long.parseLong(session.getAttribute("idFamilia").toString());
            numeroFicha = (String) session.getAttribute("numeroFicha");
            exp = (String) session.getAttribute("exp");
            ht = (String) session.getAttribute("ht");
            fechaIngresoFicha = (String) session.getAttribute("fechaIngresoFicha");
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        if (session.getAttribute("entAsoc") != null) {
            entAsoc = (String) session.getAttribute("entAsoc");
        }
        session.removeAttribute("idFamilia");
        session.removeAttribute("numeroFicha");
        session.removeAttribute("exp");
        session.removeAttribute("ht");
        session.removeAttribute("fechaIngresoFicha");
        session.removeAttribute("entAsoc");

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Familia tempFam = new Familia();
        Entidad tempEnt = new Entidad();
        tempEnt = ServicioPersonal.getEntidad(Long.parseLong(entAsoc));
        tempFam = servicioEtapa.getFamilia(idFamilia);
        ExpedienteFamilia expediente = new ExpedienteFamilia();

        tempFam.setEntidad(tempEnt);
        expediente.setHtFicha(ht);
        expediente.setnFicha(numeroFicha);
        if(fechaIngresoFicha != null && !fechaIngresoFicha.equals("")){
            expediente.setFechaIngresoFicha(df.stringToDate(fechaIngresoFicha));
        }        
        
        expediente.setExpediente(exp);
        expediente.setUnidad(usuario.getUnidad());
        expediente.setEstado("evaluacion");
        expediente.setNacionalidad("nacional");
        expediente.setRnsa(Short.parseShort("0"));
        expediente.setRnaa(Short.parseShort("1"));
        expediente.setFamilia(tempFam);

        ServicioMain.crearUpdateExpFam(expediente);
        
        String mensaje_log = "Se creó nuevo expediente nacional con número: " + expediente.getNumeroExpediente()
                + " con ID: " + expediente.getIdexpedienteFamilia();

        String Tipo_registro = "Familia";

        try {
            String Numero_registro = String.valueOf(expediente.getNumeroExpediente());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
        
        map.put("listaFamilias", servicioEtapa.getListaFamilias());
        return new ModelAndView("Personal/Buscador_etapa/etapa_formativa", map);

    }

    //PROCESO PARA EDITAR LAS CREDENCIALES DE UN USUARIO
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
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
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
        map.put("infoFam", session.getAttribute("infoFam_sesion"));
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("volver", volver);

        map.put("expediente2", expediente2);
        map.put("HT", HT);
        map.put("nacionalidad", nacionalidad);
        map.put("tipofamilia", tipofamilia);
        map.put("estado2", estado2);

        return new ModelAndView("/Personal/Buscador/familia/info_user_pass", map);
    }

    //PROCESO PARA GENERAR UNA NUEVA CONTRASEÑA
    @RequestMapping(value = "/generarContrasenaFam", method = RequestMethod.POST)
    public ModelAndView generarContrasenaFam_POST(ModelMap map, HttpSession session,
            @RequestParam("password2") String newpass,
            @RequestParam(value = "volver", required = false) String volver) {
        session.setAttribute("newpass", newpass);
        session.setAttribute("volver", volver);

        return new ModelAndView("redirect:/generarContrasenaFam", map);
    }

    @RequestMapping(value = "/generarContrasenaFam", method = RequestMethod.GET)
    public ModelAndView generarContrasenaFam_GET(ModelMap map, HttpSession session) {
        String newpass = "";
        String volver = "";
        try {
            newpass = (String) session.getAttribute("newpass");
            if (session.getAttribute("volver") != null) {
                volver = (String) session.getAttribute("volver");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("newpass");

        Personal usuario = (Personal) session.getAttribute("usuario");
        String mensaje = "";
        //
         InfoFamilia tempInfoFam = new InfoFamilia();
         tempInfoFam = (InfoFamilia) session.getAttribute("infoFam_sesion");
        //
        if (usuario == null) {
            mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        } else {
            if (newpass != null && !newpass.equals("")) {
                newpass = DigestUtils.sha512Hex(newpass);
                tempInfoFam.getFamilia().setPass(newpass);
                servicioEtapa.UpdateFamilia(tempInfoFam.getFamilia());
                mensaje = "La contraseña se ha cambiado con exito.";
            } else {

                mensaje = "Contraseña no válida. Por favor generar una nueva contraseña.";
            }
        }
        String pagina = "/Personal/familia/info_user_pass";
        //
        session.setAttribute("infoFam_sesion", tempInfoFam);
        //
        map.put("infoFam", tempInfoFam);
        map.put("estado", session.getAttribute("etapaOrigen_sesion"));
        map.put("expediente", session.getAttribute("expediente_sesion"));
        map.addAttribute("mensaje", mensaje);
        map.addAttribute("volver", volver);
        return new ModelAndView(pagina, map);
    }

    //PROCESO PARA RECORDAR LA CONTRASEÑA
    @RequestMapping(value = "/recordarContra")
    public ModelAndView recordarContra(ModelMap map) {

        String pagina = "/record_contra";
        return new ModelAndView(pagina);
    }

    //ENVÍO DE CORREO PARA RECORDAR LA CONTRASEÑA 
    @RequestMapping(value = "/recordarContraEnvio", method = RequestMethod.POST)
    public ModelAndView recordarContraEnvio(ModelMap map, HttpSession session, @RequestParam("usuario") String user) {

        String pass_plano = ServicioPersonal.generateUniqueToken(8);
        String pass = DigestUtils.sha512Hex(pass_plano);

        //ArrayList aux = hibermail.usuario(user);
        ArrayList aux = hibermail.usuario2(user, pass);

        String pagina;
        String mensaje = null;

        if (!user.equals("") && !aux.isEmpty()) {

            hibermail.generateAndSendEmail((String) aux.get(0), pass_plano, user);
            mensaje = (String) aux.get(1);

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

    //LISTA DE NNA´s EXTRANJEROS ADOPTADOS POR UNA FAMILIA
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

    //PROCESO PARA REGISTRAR UN NNA EXTRANJERO A UNA FAMILIA 
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
    public ModelAndView MainCrearNna_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idExpediente", idExpediente);
        session.setAttribute("nombre", nombre);
        session.setAttribute("apellidoP", apellidoP);
        session.setAttribute("apellidoM", apellidoM);
        session.setAttribute("sexo", sexo);
        session.setAttribute("fechaNac", fechaNac);
        session.setAttribute("edad", edad);
        session.setAttribute("meses", meses);
        session.setAttribute("paisNac", paisNac);
        session.setAttribute("dep", dep);
        session.setAttribute("prov", prov);
        session.setAttribute("dist", dist);
        session.setAttribute("direccion", direccion);
        session.setAttribute("incesto", incesto);
        session.setAttribute("mental", mental);
        session.setAttribute("epilepsia", epilepsia);
        session.setAttribute("abuso", abuso);
        session.setAttribute("sifilis", sifilis);
        session.setAttribute("seguimiento", seguimiento);
        session.setAttribute("operacion", operacion);
        session.setAttribute("hiperactivo", hiperactivo);
        session.setAttribute("especial", especial);
        session.setAttribute("salud", salud);
        session.setAttribute("mayor", mayor);
        session.setAttribute("adolescente", adolescente);
        session.setAttribute("hermanos", hermanos);
        session.setAttribute("fechaAdopcion", fechaAdopcion);
        session.setAttribute("numAdopcion", numAdopcion);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/MainCrearNna", map);
    }

    @RequestMapping(value = "/MainCrearNna", method = RequestMethod.GET)
    public ModelAndView MainCrearNna_GET(ModelMap map, HttpSession session) {
        long idExpediente = 0;
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String sexo = "";
        String fechaNac = "";
        String edad = "";
        String meses = "";
        String paisNac = "";
        String dep = "";
        String prov = "";
        String dist = "";
        String direccion = "";
        String incesto = "";
        String mental = "";
        String epilepsia = "";
        String abuso = "";
        String sifilis = "";
        String seguimiento = "";
        String operacion = "";
        String hiperactivo = "";
        String especial = "";
        String salud = "";
        String mayor = "";
        String adolescente = "";
        String hermanos = "";
        String fechaAdopcion = "";
        String numAdopcion = "";
        String obs = "";
        try {
            idExpediente = Long.parseLong(session.getAttribute("idExpediente").toString());
            if (session.getAttribute("nombre") != null) {
                nombre = (String) session.getAttribute("nombre");
            }
            if (session.getAttribute("apellidoP") != null) {
                apellidoP = (String) session.getAttribute("apellidoP");
            }
            if (session.getAttribute("apellidoM") != null) {
                apellidoM = (String) session.getAttribute("apellidoM");
            }
            if (session.getAttribute("sexo") != null) {
                sexo = (String) session.getAttribute("sexo");
            }
            if (session.getAttribute("fechaNac") != null) {
                fechaNac = (String) session.getAttribute("fechaNac");
            }
            if (session.getAttribute("edad") != null) {
                edad = (String) session.getAttribute("edad");
            }
            if (session.getAttribute("meses") != null) {
                meses = (String) session.getAttribute("meses");
            }
            if (session.getAttribute("paisNac") != null) {
                paisNac = (String) session.getAttribute("paisNac");
            }
            if (session.getAttribute("dep") != null) {
                dep = (String) session.getAttribute("dep");
            }
            if (session.getAttribute("prov") != null) {
                prov = (String) session.getAttribute("prov");
            }
            if (session.getAttribute("dist") != null) {
                dist = (String) session.getAttribute("dist");
            }
            if (session.getAttribute("direccion") != null) {
                direccion = (String) session.getAttribute("direccion");
            }
            incesto = (String) session.getAttribute("incesto");
            mental = (String) session.getAttribute("mental");
            epilepsia = (String) session.getAttribute("epilepsia");
            abuso = (String) session.getAttribute("abuso");
            sifilis = (String) session.getAttribute("sifilis");
            seguimiento = (String) session.getAttribute("seguimiento");
            operacion = (String) session.getAttribute("operacion");
            hiperactivo = (String) session.getAttribute("hiperactivo");
            especial = (String) session.getAttribute("especial");
            salud = (String) session.getAttribute("salud");
            mayor = (String) session.getAttribute("mayor");
            adolescente = (String) session.getAttribute("adolescente");
            hermanos = (String) session.getAttribute("hermanos");
            if (session.getAttribute("fechaAdopcion") != null) {
                fechaAdopcion = (String) session.getAttribute("fechaAdopcion");
            }
            if (session.getAttribute("numAdopcion") != null) {
                numAdopcion = (String) session.getAttribute("numAdopcion");
            }
            if (session.getAttribute("obs") != null) {
                obs = (String) session.getAttribute("obs");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("idExpediente");
        session.removeAttribute("nombre");
        session.removeAttribute("apellidoP");
        session.removeAttribute("apellidoM");
        session.removeAttribute("sexo");
        session.removeAttribute("fechaNac");
        session.removeAttribute("edad");
        session.removeAttribute("meses");
        session.removeAttribute("paisNac");
        session.removeAttribute("dep");
        session.removeAttribute("prov");
        session.removeAttribute("dist");
        session.removeAttribute("direccion");
        session.removeAttribute("incesto");
        session.removeAttribute("mental");
        session.removeAttribute("epilepsia");
        session.removeAttribute("abuso");
        session.removeAttribute("sifilis");
        session.removeAttribute("seguimiento");
        session.removeAttribute("operacion");
        session.removeAttribute("hiperactivo");
        session.removeAttribute("especial");
        session.removeAttribute("salud");
        session.removeAttribute("mayor");
        session.removeAttribute("adolescente");
        session.removeAttribute("hermanos");
        session.removeAttribute("fechaAdopcion");
        session.removeAttribute("numAdopcion");
        session.removeAttribute("obs");

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

    //PROCESO PARA EDITAR Y ACTUALIZAR LA INFORMACIÓN DE UN NNA EXTRANJERO ADOPTADO
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
    public ModelAndView MainUpdateNna_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("idDesig", idDesig);
        session.setAttribute("idNna", idNna);
        session.setAttribute("nombre", nombre);
        session.setAttribute("apellidoP", apellidoP);
        session.setAttribute("apellidoM", apellidoM);
        session.setAttribute("sexo", sexo);
        session.setAttribute("fechaNac", fechaNac);
        session.setAttribute("edad", edad);
        session.setAttribute("meses", meses);
        session.setAttribute("paisNac", paisNac);
        session.setAttribute("dep", dep);
        session.setAttribute("prov", prov);
        session.setAttribute("dist", dist);
        session.setAttribute("direccion", direccion);
        session.setAttribute("incesto", incesto);
        session.setAttribute("mental", mental);
        session.setAttribute("epilepsia", epilepsia);
        session.setAttribute("abuso", abuso);
        session.setAttribute("sifilis", sifilis);
        session.setAttribute("seguimiento", seguimiento);
        session.setAttribute("operacion", operacion);
        session.setAttribute("hiperactivo", hiperactivo);
        session.setAttribute("especial", especial);
        session.setAttribute("salud", salud);
        session.setAttribute("mayor", mayor);
        session.setAttribute("adolescente", adolescente);
        session.setAttribute("hermanos", hermanos);
        session.setAttribute("fechaAdopcion", fechaAdopcion);
        session.setAttribute("numAdopcion", numAdopcion);
        session.setAttribute("obs", obs);

        return new ModelAndView("redirect:/MainUpdateNna", map);

    }

    @RequestMapping(value = "/MainUpdateNna", method = RequestMethod.GET)
    public ModelAndView MainUpdateNna_GET(ModelMap map, HttpSession session) {
        long idDesig = 0;
        long idNna = 0;
        String nombre = "";
        String apellidoP = "";
        String apellidoM = "";
        String sexo = "";
        String fechaNac = "";
        String edad = "";
        String meses = "";
        String paisNac = "";
        String dep = "";
        String prov = "";
        String dist = "";
        String direccion = "";
        String incesto = "";
        String mental = "";
        String epilepsia = "";
        String abuso = "";
        String sifilis = "";
        String seguimiento = "";
        String operacion = "";
        String hiperactivo = "";
        String especial = "";
        String salud = "";
        String mayor = "";
        String adolescente = "";
        String hermanos = "";
        String fechaAdopcion = "";
        String numAdopcion = "";
        String obs = "";
        try {
            idNna = (long) session.getAttribute("idNna");
            idDesig = (long) session.getAttribute("idDesig");
            if (session.getAttribute("nombre") != null) {
                nombre = (String) session.getAttribute("nombre");
            }
            if (session.getAttribute("apellidoP") != null) {
                apellidoP = (String) session.getAttribute("apellidoP");
            }
            if (session.getAttribute("apellidoM") != null) {
                apellidoM = (String) session.getAttribute("apellidoM");
            }
            if (session.getAttribute("sexo") != null) {
                sexo = (String) session.getAttribute("sexo");
            }
            if (session.getAttribute("fechaNac") != null) {
                fechaNac = (String) session.getAttribute("fechaNac");
            }
            if (session.getAttribute("edad") != null) {
                edad = (String) session.getAttribute("edad");
            }
            if (session.getAttribute("meses") != null) {
                meses = (String) session.getAttribute("meses");
            }
            if (session.getAttribute("paisNac") != null) {
                paisNac = (String) session.getAttribute("paisNac");
            }
            if (session.getAttribute("dep") != null) {
                dep = (String) session.getAttribute("dep");
            }
            if (session.getAttribute("prov") != null) {
                prov = (String) session.getAttribute("prov");
            }
            if (session.getAttribute("dist") != null) {
                dist = (String) session.getAttribute("dist");
            }
            if (session.getAttribute("direccion") != null) {
                direccion = (String) session.getAttribute("direccion");
            }
            incesto = (String) session.getAttribute("incesto");
            mental = (String) session.getAttribute("mental");
            epilepsia = (String) session.getAttribute("epilepsia");
            abuso = (String) session.getAttribute("abuso");
            sifilis = (String) session.getAttribute("sifilis");
            seguimiento = (String) session.getAttribute("seguimiento");
            operacion = (String) session.getAttribute("operacion");
            hiperactivo = (String) session.getAttribute("hiperactivo");
            especial = (String) session.getAttribute("especial");
            salud = (String) session.getAttribute("salud");
            mayor = (String) session.getAttribute("mayor");
            adolescente = (String) session.getAttribute("adolescente");
            hermanos = (String) session.getAttribute("hermanos");
            if (session.getAttribute("fechaAdopcion") != null) {
                fechaAdopcion = (String) session.getAttribute("fechaAdopcion");
            }
            if (session.getAttribute("numAdopcion") != null) {
                numAdopcion = (String) session.getAttribute("numAdopcion");
            }
            if (session.getAttribute("obs") != null) {
                obs = (String) session.getAttribute("obs");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("idDesig");
        session.removeAttribute("idNna");
        session.removeAttribute("nombre");
        session.removeAttribute("apellidoP");
        session.removeAttribute("apellidoM");
        session.removeAttribute("sexo");
        session.removeAttribute("fechaNac");
        session.removeAttribute("edad");
        session.removeAttribute("meses");
        session.removeAttribute("paisNac");
        session.removeAttribute("dep");
        session.removeAttribute("prov");
        session.removeAttribute("dist");
        session.removeAttribute("direccion");
        session.removeAttribute("incesto");
        session.removeAttribute("mental");
        session.removeAttribute("epilepsia");
        session.removeAttribute("abuso");
        session.removeAttribute("sifilis");
        session.removeAttribute("seguimiento");
        session.removeAttribute("operacion");
        session.removeAttribute("hiperactivo");
        session.removeAttribute("especial");
        session.removeAttribute("salud");
        session.removeAttribute("mayor");
        session.removeAttribute("adolescente");
        session.removeAttribute("hermanos");
        session.removeAttribute("fechaAdopcion");
        session.removeAttribute("numAdopcion");
        session.removeAttribute("obs");

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
            @RequestParam(value = "idNna", required = false) String idNna,
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

    //Probar
    @RequestMapping(value = "/MainInsertarRevision", method = RequestMethod.POST)
    public ModelAndView MainInsertarRevision_POST(ModelMap map, HttpSession session,
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
        session.setAttribute("numero", numero);
        session.setAttribute("comentarios", comentarios);
        session.setAttribute("agregarFamilia", agregarFamilia);
        session.setAttribute("agregarEntidad", agregarEntidad);
        session.setAttribute("eliminar", eliminar);
        session.setAttribute("registrar", registrar);
        session.setAttribute("idNna", idNna);
        session.setAttribute("delete", delete);
        session.setAttribute("fecha", fecha);

        return new ModelAndView("redirect:/MainInsertarRevision", map);
    }

    @RequestMapping(value = "/MainInsertarRevision", method = RequestMethod.GET)
    public ModelAndView MainInsertarRevision_GET(ModelMap map, HttpSession session) {
        String numero = null;
        String comentarios = null;
        String agregarFamilia = null;
        String agregarEntidad = null;
        String eliminar = null;
        String registrar = null;
        String idNna = null;
        String[] delete;
        String[] fecha;
        try {
            if (session.getAttribute("numero") != null) {
                numero = (String) session.getAttribute("numero");
            }
            if (session.getAttribute("comentarios") != null) {
                comentarios = (String) session.getAttribute("comentarios");
            }
            if (session.getAttribute("agregarFamilia") != null) {
                agregarFamilia = (String) session.getAttribute("agregarFamilia");
            }
            if (session.getAttribute("agregarEntidad") != null) {
                agregarEntidad = (String) session.getAttribute("agregarEntidad");
            }
            if (session.getAttribute("eliminar") != null) {
                eliminar = (String) session.getAttribute("eliminar");
            }
            if (session.getAttribute("registrar") != null) {
                registrar = (String) session.getAttribute("registrar");
            }
            if (session.getAttribute("idNna") != null) {
                idNna = (String) session.getAttribute("idNna");
            }
            delete = (String[]) session.getAttribute("delete");
            fecha = (String[]) session.getAttribute("fecha");
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("numero");
        session.removeAttribute("comentarios");
        session.removeAttribute("agregarFamilia");
        session.removeAttribute("agregarEntidad");
        session.removeAttribute("eliminar");
        session.removeAttribute("registrar");
        session.removeAttribute("idNna");
        session.removeAttribute("delete");
        session.removeAttribute("fecha");

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

    //
    @RequestMapping(value = "/MainGuardarRevision", method = RequestMethod.POST)
    public ModelAndView MainGuardarRevision(ModelMap map, HttpSession session,
            @RequestParam("numero") String numero,
            @RequestParam("coments") String coments
    ) {
        session.setAttribute("numero", numero);
        session.setAttribute("coments", coments);

        return new ModelAndView("redirect:/MainGuardarRevision", map);
    }

    @RequestMapping(value = "/MainGuardarRevision", method = RequestMethod.GET)
    public ModelAndView MainGuardarRevision(ModelMap map, HttpSession session
    ) {
        String numero = "";
        String coments = "";
        try {
            numero = (String) session.getAttribute("numero");
            coments = (String) session.getAttribute("coments");
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("numero");
        session.removeAttribute("coments");

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

            String mensaje_log = "El usuario, " + usuario.getUser() + " con ID: " + usuario.getIdpersonal() + ". Guardó la "
                    + "revisión con número: " + numero;
            String Tipo_registro = "Personal";

            try {
                String Numero_registro = String.valueOf(usuario.getIdpersonal());

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }
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

    //PROBAR
    @RequestMapping(value = "/MainInsertarEstudio", method = RequestMethod.POST)
    public ModelAndView MainInsertarEstudio_POST(ModelMap map, HttpSession session,
            @RequestParam(value = "orden", required = false) String orden,
            @RequestParam(value = "agregar", required = false) String agregar,
            @RequestParam(value = "eliminar", required = false) String eliminar,
            @RequestParam(value = "registrar", required = false) String registrar,
            Long[] delete,
            Long[] prioridad,
            Long[] idNna,
            @RequestParam(value = "numero", required = false) String numero
    ) {
        session.setAttribute("orden", orden);
        session.setAttribute("agregar", agregar);
        session.setAttribute("eliminar", eliminar);
        session.setAttribute("registrar", registrar);
        session.setAttribute("delete", delete);
        session.setAttribute("prioridad", prioridad);
        session.setAttribute("idNna", idNna);
        session.setAttribute("numero", numero);

        return new ModelAndView("redirect:/MainInsertarEstudio", map);
    }

    @RequestMapping(value = "/MainInsertarEstudio", method = RequestMethod.GET)
    public ModelAndView MainInsertarEstudio_GET(ModelMap map, HttpSession session) {
        String orden = null;
        String agregar = null;
        String eliminar = null;
        String registrar = null;
        Long[] delete = null;
        Long[] prioridad = null;
        Long[] idNna = null;
        String numero = null;
        try {
            if (session.getAttribute("orden") != null) {
                orden = (String) session.getAttribute("orden");
            }
            if (session.getAttribute("agregar") != null) {
                agregar = (String) session.getAttribute("agregar");
            }
            if (session.getAttribute("eliminar") != null) {
                eliminar = (String) session.getAttribute("eliminar");
            }
            if (session.getAttribute("registrar") != null) {
                registrar = (String) session.getAttribute("registrar");
            }
            delete = (Long[]) session.getAttribute("delete");
            prioridad = (Long[]) session.getAttribute("prioridad");
            idNna = (Long[]) session.getAttribute("idNna");
            if (session.getAttribute("numero") != null) {
                numero = (String) session.getAttribute("numero");
            }
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("orden");
        session.removeAttribute("agregar");
        session.removeAttribute("eliminar");
        session.removeAttribute("registrar");
        session.removeAttribute("delete");
        session.removeAttribute("prioridad");
        session.removeAttribute("idNna");
        session.removeAttribute("numero");

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

    //PROBAR
    @RequestMapping(value = "/MainActualizarEstudio", method = RequestMethod.POST)
    public ModelAndView MainActualizarEstudio_POST(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden,
            Long[] idExpFam,
            String[] resultado,
            String[] fechaEst,
            @RequestParam("elegido") int elegido
    ) {
        session.setAttribute("orden", orden);
        session.setAttribute("idExpFam", idExpFam);
        session.setAttribute("resultado", resultado);
        session.setAttribute("fechaEst", fechaEst);
        session.setAttribute("elegido", elegido);

        return new ModelAndView("redirect:/MainActualizarEstudio", map);
    }

    @RequestMapping(value = "/MainActualizarEstudio", method = RequestMethod.GET)
    public ModelAndView MainActualizarEstudio_GET(ModelMap map, HttpSession session) {
        String orden = "";
        Long[] idExpFam = null;
        String[] resultado = null;
        String[] fechaEst = null;
        int elegido = 0;
        try {
            orden = (String) session.getAttribute("orden");
            idExpFam = (Long[]) session.getAttribute("idExpFam");
            fechaEst = (String[]) session.getAttribute("fechaEst");
            resultado = (String[]) session.getAttribute("resultado");
            elegido = Integer.parseInt(session.getAttribute("elegido").toString());
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("orden");
        session.removeAttribute("idExpFam");
        session.removeAttribute("resultado");
        session.removeAttribute("fechaEst");
        session.removeAttribute("elegido");

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

    //PROBAR
    @RequestMapping(value = "/MainGuardarFechaSolicitud", method = RequestMethod.POST)
    public ModelAndView MainGuardarFechaSolicitud_POST(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden,
            @RequestParam("fechaSolicitud") String fechaSolicitud,
            @RequestParam("idExpFam") long idExpFam
    ) {
        session.setAttribute("orden", orden);
        session.setAttribute("fechaSolicitud", fechaSolicitud);
        session.setAttribute("idExpFam", idExpFam);

        return new ModelAndView("redirect:/MainGuardarFechaSolicitud", map);
    }

    @RequestMapping(value = "/MainGuardarFechaSolicitud", method = RequestMethod.GET)
    public ModelAndView MainGuardarFechaSolicitud_GET(ModelMap map, HttpSession session) {
        String orden = "";
        String fechaSolicitud = "";
        long idExpFam = 0;
        try {
            orden = (String) session.getAttribute("orden");
            fechaSolicitud = (String) session.getAttribute("fechaSolicitud");
            idExpFam = Long.parseLong(session.getAttribute("idExpFam").toString());
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("orden");
        session.removeAttribute("fechaSolicitud");
        session.removeAttribute("idExpFam");

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

    //PROBAR
    @RequestMapping(value = "/MainGenerarDesignacionPrioritario", method = RequestMethod.POST)
    public ModelAndView MainGenerarDesignacionPrioritario_POST(ModelMap map, HttpSession session,
            @RequestParam("orden") String orden,
            @RequestParam("numDesig") String numDesig,
            @RequestParam("idExpFam") long idExpFam,
            @RequestParam("fechaPropuesta") String fechaPropuesta
    ) {
        session.setAttribute("orden", orden);
        session.setAttribute("numDesig", numDesig);
        session.setAttribute("idExpFam", idExpFam);
        session.setAttribute("fechaPropuesta", fechaPropuesta);

        return new ModelAndView("redirect:/MainGenerarDesignacionPrioritario", map);
    }

    @RequestMapping(value = "/MainGenerarDesignacionPrioritario", method = RequestMethod.GET)
    public ModelAndView MainGenerarDesignacionPrioritario_GET(ModelMap map, HttpSession session) {
        String orden = "";
        String numDesig = "";
        long idExpFam = 0;
        String fechaPropuesta = "";
        try {
            orden = (String) session.getAttribute("orden");
            numDesig = (String) session.getAttribute("numDesig");
            idExpFam = Long.parseLong(session.getAttribute("idExpFam").toString());
            fechaPropuesta = (String) session.getAttribute("fechaPropuesta");
        } catch (Exception ex) {
            return new ModelAndView("redirect:/inicioper", map);
        }
        session.removeAttribute("orden");
        session.removeAttribute("numDesig");
        session.removeAttribute("idExpFam");
        session.removeAttribute("fechaPropuesta");

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
    
    //CAMBIOS 10/06/2015--------------EN EVALUACION
    
//    @RequestMapping(value = "/IrVerMainFormativa", method = RequestMethod.POST)
//    public ModelAndView IrVerMainFormativa(ModelMap map, HttpSession session,
//            @RequestParam(value = "estado", required = false) String estado,
//            @RequestParam(value = "idFamilia", required = false) String idFamilia,
//            @RequestParam(value = "idExpediente", required = false) String idExpediente,
//            @RequestParam(value = "volver", required = false) String volver
//    ) {
//        session.setAttribute("estado", estado);
//        session.setAttribute("idFamilia", idFamilia);
//        session.setAttribute("idExpediente", idExpediente);
//        session.setAttribute("volver", volver);
//
//        return new ModelAndView("redirect:/IrVerMainFormativa", map);
//    }
//
//    @RequestMapping(value = "/IrVerMainFormativa", method = RequestMethod.GET)
//    public ModelAndView IrVerMainFormativa_GET(ModelMap map, HttpSession session) {
//        String idFamilia = "";
//        String idExpediente = "";
//        String volver = "";
//        String estado = "";
//
//        if (session.getAttribute("idFamilia") != null) {
//            idFamilia = session.getAttribute("idFamilia").toString();
//        }
//        if (session.getAttribute("idExpediente") != null) {
//            idExpediente = session.getAttribute("idExpediente").toString();
//        }
//        if (session.getAttribute("volver") != null) {
//            volver = session.getAttribute("volver").toString();
//        }
//        if (session.getAttribute("estado") != null) {
//            estado = session.getAttribute("estado").toString();
//        }
//
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
//        /*LIMPIANDO LOS ANTIGUOS VALORES DE LOS ADOPTANTES */
//        Adoptante EllaLocal = new Adoptante();
//        Adoptante ElLocal = new Adoptante();
//
//        if (estado.equals("formativa")) {
//            Long idFam = Long.parseLong(idFamilia);
//            InfoFamilia infoFamLocal = new InfoFamilia();
//                  infoFamLocal = ServicioMain.getInfoFamPorIdFamilia(idFam);
//                  map.put("infoFam", infoFamLocal);
//            for (Adoptante adop : infoFamLocal.getAdoptantes()) {
//                if (adop.getSexo() == 'f') {
//                    EllaLocal = adop;
//                }
//                if (adop.getSexo() == 'm') {
//                    ElLocal = adop;
//                }
//            }
//            ArrayList<Atencion> listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(idFam);
//            ArrayList<Sesion> listaSesiones = ServicioMain.getListaSesionesPorFamilia(idFam);
//            ArrayList<AsistenciaFR> listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(idFam);
//            //CAMBIO DEL 10/06/2015 
//            //SE INICIALIZA EN NULL EL VALOR DEL EXPEDIENTE PORQUE EN PREPARACION NO SE TIENE UN EXPEDIENTE            
//            map.put("idFamilia", idFam);
//            
//        } 
//        String etapaOrigen = estado;
//
//        map.put("df", df);
//        map.put("estado", etapaOrigen);
//        map.put("Ella", EllaLocal);
//        map.addAttribute("volver", volver);
//
//        return new ModelAndView("/Personal/familia/info_ella", map);
//    }
    
    
    
    
    
    
    
    
}
