/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.util.*;
import com.mimp.hibernate.HiberMain;
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
public class main {

    @Resource(name = "HiberMain")
    private HiberMain ServicioMain = new HiberMain();
    dateFormat df = new dateFormat();
    timeStampFormat ts = new timeStampFormat();

    @RequestMapping("/")
    public String hello() {
        return "login";
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(ModelMap map, @RequestParam("email") String email, @RequestParam("password") String pass, HttpSession session) {

        String pagina;
        String mensaje = "El usuario se encuentra Deshabilitado. Favor contactar a la Dirección General de Adopciones para más información";

        ArrayList aux = ServicioMain.usuario(email, pass);
        if (aux.get(0) == "personal") {
            Personal personal = (Personal) aux.get(1);
            if (!personal.getRol().equals("Inactivo")) {
                session.setAttribute("usuario", personal);
                pagina = "/Personal/inicio_personal";
            } else {
                map.addAttribute("mensaje", mensaje);
                pagina = "login";
            }
        } else if (aux.get(0) == "familia") {
            Familia familia = (Familia) aux.get(1);
            if (familia.getHabilitado() == 1) {
                session.setAttribute("usuario", familia);
                pagina = "/Familia/inicio_familia";
            } else {
                map.addAttribute("mensaje", mensaje);
                pagina = "login";
            }
        } else if (aux.get(0) == "representante" || aux.get(0) == "autoridad") {
            Entidad entidad = (Entidad) aux.get(1);
            session.setAttribute("usuario", entidad);
            pagina = "/Entidad/inicio_ent";
        } else {
            mensaje = "Usuario y/o contraseña incorrectos";
            map.addAttribute("mensaje", mensaje);
            pagina = "login";
        }
        return new ModelAndView(pagina, map);
    }

    @RequestMapping("/SesionInfInicio")
    public ModelAndView SesionInfInicio(ModelMap map) {

        ArrayList<Turno> temp = new ArrayList<>();
        ArrayList<Turno> temp2 = new ArrayList<>();
        Date now = new Date();
        temp = ServicioMain.ListaTurnos();
        String pagina = "";
        //String fecha = ts.TimeToString(temp.get(0).getInicioInscripcion());
        if (temp.isEmpty()) {
            pagina = "/Inscripcion/no_sesion_prog";
            return new ModelAndView(pagina);
        } else {
            for (int i = 0; i < temp.size(); i++) {
                if (now.after((Date) temp.get(i).getInicioInscripcion()) && now.before((Date) temp.get(i).getFinInscripcion())) {
                    if (i == temp.size() - 1 && temp.get(i).getAsistenciaFTs().size() >= temp.get(i).getVacantes()) {
                        ArrayList<Sesion> tempSesiones = new ArrayList<>();
                        tempSesiones = ServicioMain.listaSesionesSiguientes(temp.get(i).getSesion().getFecha());
                        if (tempSesiones.isEmpty()) {
                            pagina = "/Inscripcion/no_sesion_prog";
                            return new ModelAndView(pagina);
                        } else {
                            ArrayList<Turno> tempTurnos = new ArrayList<>();
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

    @RequestMapping(value = "/SesionInfElegirEstado", method = RequestMethod.POST)
    public ModelAndView SesionInfElegirEstado(ModelMap map, @RequestParam("idTurno") int turno) {

        map.addAttribute("idTurno", turno);
        return new ModelAndView("/Inscripcion/inscripcion_sesion2", map);

    }

    @RequestMapping(value = "/SesionInfEstado2", method = RequestMethod.POST)
    public ModelAndView SesionInfElegirEstado2(ModelMap map, @RequestParam("estado") String estado, @RequestParam("idTurno") int turno) {
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

    @RequestMapping(value = "/inscSesInd", method = RequestMethod.POST)
    public ModelAndView inscSesInd(ModelMap map,
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
            @RequestParam("idTurno") int turno
    ) {

        Turno temp = ServicioMain.getTurno(turno);
        FormularioSesion fs = new FormularioSesion();
        Asistente asis = new Asistente();
        AsistenciaFT aft = new AsistenciaFT();
        fs.setSesion(temp.getSesion());
        aft.setTurno(temp);
        Date today = new Date();

        fs.setFechaSol(today);
        fs.setPaisRes(pais);
        fs.setDepRes(dep);
        fs.setProvRes(prov);
        fs.setDistritoRes(dist);
        fs.setDireccionRes(dir);
        fs.setTelefono(telf);
        fs.setEstadoCivil(estado);

        asis.setNombre(nombre);
        asis.setApellidoP(apellidoP);
        asis.setApellidoM(apellidoM);
        asis.setPaisNac(paisNac);
        asis.setDepNac(depNac);
        asis.setProvNac(proNac);
        short b = Byte.valueOf(edad);
        asis.setEdad(b);
        asis.setFechaNac(df.stringToDate(fechaNac));
        char c = doc.charAt(0);
        asis.setTipoDoc(c);
        asis.setNDoc(numDoc);
        asis.setProfesion(profesion);
        asis.setCelular(cel);
        asis.setCorreo(correo);

        ArrayList<Asistente> tempList = new ArrayList<>();
        tempList = ServicioMain.listaAsistentes(temp.getSesion().getIdsesion());
        for (Asistente asistente : tempList) {
            if (asistente.getNDoc().equals(numDoc)) {
                return new ModelAndView("contacto", map);
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
            ServicioMain.InsertFormInd(asis, fs, aft);
            return new ModelAndView("/Inscripcion/inscripcion_sesion1b", map);
        }
        //return new ModelAndView("contacto", map);
    }

    @RequestMapping(value = "/inscSesGrp", method = RequestMethod.POST)
    public ModelAndView inscSesGrp(ModelMap map,
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
            @RequestParam("idTurno") int turno
    ) {

        Turno temp = ServicioMain.getTurno(turno);
        FormularioSesion fs = new FormularioSesion();
        Asistente asisEl = new Asistente();
        Asistente asisElla = new Asistente();
        AsistenciaFT aft = new AsistenciaFT();
        fs.setSesion(temp.getSesion());
        aft.setTurno(temp);
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
        asisEl.setFechaNac(df.stringToDate(fechaNacEl));
        char cEl = docEl.charAt(0);
        asisEl.setTipoDoc(cEl);
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
        asisElla.setFechaNac(df.stringToDate(fechaNacElla));
        char cElla = docElla.charAt(0);
        asisElla.setTipoDoc(cElla);
        asisElla.setNDoc(numDocElla);
        asisElla.setProfesion(profesionElla);
        asisElla.setCelular(celElla);
        asisElla.setCorreo(correoElla);

        ArrayList<Asistente> tempList = new ArrayList<>();
        tempList = ServicioMain.listaAsistentes(temp.getSesion().getIdsesion());
        for (Asistente asistente : tempList) {
            if (asistente.getNDoc().equals(numDocEl) || asistente.getNDoc().equals(numDocElla)) {
                return new ModelAndView("contacto", map);
            }

        }
        if (temp.getVacantes() > temp.getAsistenciaFTs().size()) {
            map.put("ts", ts);
            map.put("turno", temp);
            ServicioMain.InsertFormGrp(asisEl, asisElla, fs, aft);
            return new ModelAndView("/Inscripcion/inscripcion_sesion4", map);
        }
        return new ModelAndView("contacto", map);
    }

}
