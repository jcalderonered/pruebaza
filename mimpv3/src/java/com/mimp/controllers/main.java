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
import com.mimp.hibernate.HiberMain;
import com.mimp.hibernate.HiberNna;
import com.mimp.hibernate.HiberPersonal;
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
    
    InfoFamilia infoFam = new InfoFamilia();
    ExpedienteFamilia expediente = new ExpedienteFamilia();
    Nna nnaAdoptado = new Nna();
    ExpedienteNna expNna = new ExpedienteNna();
    /*      */
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
            if (familia.getHabilitado() == 0) {
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
        } else if (email.equals("") || pass.equals("")){
            mensaje = "Por favor llenar ambos campos";
            map.addAttribute("mensaje", mensaje);
            pagina = "login";
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
        if (estado.equals("soltero") || estado.equals("viudo") || estado.equals("divorciado")) sexo = "m";
        if (estado.equals("soltera") || estado.equals("viuda") || estado.equals("divorciada")) sexo = "f";
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
            //ServicioMain.InsertFormInd(asis, fs, aft);
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

        String m = "m";
        String f = "f";
        Turno temp = ServicioMain.getTurno(turno);
        FormularioSesion fs = new FormularioSesion();
        Asistente asisEl = new Asistente();
        Asistente asisElla = new Asistente();
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
        asisElla.setFechaNac(df.stringToDate(fechaNacElla));
        char cElla = docElla.charAt(0);
        asisElla.setTipoDoc(cElla);
        char sexoElla = f.charAt(0);
        asisElla.setSexo(sexoElla);
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

/** ESTA SECCION ES USADA PARA ACTUALIZAR LOS DATOS DE LA FAMILIA POR PARTE DEL PERSONAL**/   

@RequestMapping(value = "/IrPersonalFamilia", method = RequestMethod.POST)
    public ModelAndView IrPersonalFamilia(ModelMap map, HttpSession session, 
                                          @RequestParam(value="estado", required = false) String estado,
                                          @RequestParam(value="idFamilia", required = false) String idFamilia,
                                          @RequestParam(value="idExpediente", required = false) String idExpediente
                                          ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if(estado.equals("formativa")){
            Long idFam = Long.parseLong(idFamilia);
            infoFam = ServicioMain.getInfoFamPorIdFamilia(idFam);
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(idFam);
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(idFam);
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(idFam);
        }else if(estado.equals("evaluacion")){
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            
        }else if(estado.equals("espera")){
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        
        }else if(estado.equals("designacion")){
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        }else if(estado.equals("adopcion")){
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        }else if(estado.equals("reevaluacion")){
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
        
        
        }else if(estado.equals("post")){
            Long idExp = Long.parseLong(idExpediente);
            ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExp);
            expediente = tempExp;
            infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
            for (Adoptante adop : infoFam.getAdoptantes()) {
                if(adop.getSexo() == 'f') Ella = adop;
                if(adop.getSexo() == 'm') El = adop;
            }
            listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaSesiones = ServicioMain.getListaSesionesPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaAsistenciaReuniones = ServicioMain.getListaAsistenciaFRPorFamilia(tempExp.getFamilia().getIdfamilia());
            listaEvaluaciones = ServicioMain.getListaEvaluacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaDesignaciones = ServicioMain.getListaDesignacionesPorExpediente(tempExp.getIdexpedienteFamilia());
            listaEstudios = ServicioMain.getListaEstudiosPorExpediente(tempExp.getIdexpedienteFamilia());
            
            for (Designacion desig : listaDesignaciones) {
                    if(desig.getAceptacionConsejo() == 2){
                        nnaAdoptado = desig.getNna();
                        expNna = desig.getNna().getExpedienteNnas().iterator().next();
                    }
            }
            
        }
        
      
        etapaOrigen = estado;
        map.put("df",df);
        map.put("infoFam",infoFam);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("Ella",Ella);
        return new ModelAndView("/Personal/familia/info_ella", map);
}     
    
@RequestMapping(value = "/elSolicitante", method = RequestMethod.GET)
    public ModelAndView elSolicitante(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df",df);
        map.put("infoFam",infoFam);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("El",El);
        return new ModelAndView("/Personal/familia/info_el", map);
} 

@RequestMapping(value = "/laSolicitante", method = RequestMethod.GET)
    public ModelAndView laSolicitante(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
        //ArrayList<Familia> allFamilias = new ArrayList();
        //allFamilias = servicioEtapa.getListaFamilias();
        map.put("df",df);
        map.put("infoFam",infoFam);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("Ella",Ella);
        return new ModelAndView("/Personal/familia/info_ella", map);
}   

@RequestMapping(value = "/compFamiliar", method = RequestMethod.GET)
    public ModelAndView compFamiliar(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_fam", map);
}       
 
@RequestMapping(value = "/vivienda", method = RequestMethod.GET)
    public ModelAndView vivienda(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
    
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("infoFam",infoFam);
        return new ModelAndView("/Personal/familia/info_vivienda", map);
}      

@RequestMapping(value = "/infoExpediente", method = RequestMethod.GET)
    public ModelAndView infoExpediente(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("listaUA",ServicioPersonal.ListaUa());
        map.put("listaEntidad",ServicioPersonal.ListaEntidades());
        return new ModelAndView("/Personal/familia/info_registro", map);
}      

@RequestMapping(value = "/procesoAdopcion", method = RequestMethod.GET)
    public ModelAndView procesoAdopcion(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
     
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("listaSesiones",listaSesiones);
        map.put("listaAsistenciaReuniones",listaAsistenciaReuniones);
        map.put("listaEvaluaciones",listaEvaluaciones);
        map.put("listaDesignaciones",listaDesignaciones);
        map.put("listaEstudios",listaEstudios);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_adop/info_adop", map);
}     

@RequestMapping(value = "/antNna", method = RequestMethod.GET)
    public ModelAndView antNna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
       
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("infoFam",infoFam);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_ant_nna", map);
}
  
@RequestMapping(value = "/nnaAsociado", method = RequestMethod.GET)
    public ModelAndView nnaAsociado(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("nna",nnaAdoptado);
        map.put("exp",expNna);
        return new ModelAndView("/Personal/familia/info_nna", map);
}
 
@RequestMapping(value = "/atenciones", method = RequestMethod.GET)
    public ModelAndView atenciones(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
       
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("listaAtenciones",listaAtenciones);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_atencion", map);
}

@RequestMapping(value = "/agregarAtencion", method = RequestMethod.GET)
    public ModelAndView agregarAtencion(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
         map.put("df",df);
         map.put("estado",etapaOrigen);
         map.put("expediente",expediente);
         map.put("listaPersonal",ServicioPersonal.ListaPersonal());
         return new ModelAndView("/Personal/familia/info_atencion_edit", map);
}  
    
@RequestMapping(value = "/DetalleAtencion", method = RequestMethod.POST)
    public ModelAndView DetalleAtencion(ModelMap map, HttpSession session,@RequestParam(value="idAtencion") long idAtencion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Atencion temp = ServicioMain.getAtencion(idAtencion);
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("atencion",temp);
        map.put("expediente",expediente);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/familia/info_atencion_edit", map);
    }    
    
    @RequestMapping(value = "/crearAtencion", method = RequestMethod.POST)
    public ModelAndView crearAtencion(ModelMap map, HttpSession session,
                                      @RequestParam(value="personal") long personal,
                                      @RequestParam(value="fecha") String fecha,
                                      @RequestParam(value="hora") String hora,
                                      @RequestParam(value="tipo") String tipo,
                                      @RequestParam(value="detalle",required = false) String detalle,
                                      @RequestParam(value="obs",required = false) String obs      
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
        if(fecha != null && !fecha.equals("")) tempAtn.setFecha(df.stringToDate(fecha));
        if(fecha == null || fecha.equals("")) tempAtn.setFecha(null);
        tempAtn.setHora(hora);
        tempAtn.setTipoAtencion(tipo);
        tempAtn.setDetalle(detalle);
        tempAtn.setObservacion(obs);
        
        ServicioMain.crearAtencion(tempAtn);
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(expediente.getFamilia().getIdfamilia());
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("listaAtenciones",listaAtenciones);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_atencion", map);
}
    
@RequestMapping(value = "/updateAtencion", method = RequestMethod.POST)
    public ModelAndView updateAtencion(ModelMap map, HttpSession session,
                                      @RequestParam(value="idAtencion") long idAtencion,
                                      @RequestParam(value="personal") long personal,
                                      @RequestParam(value="fecha") String fecha,
                                      @RequestParam(value="hora") String hora,
                                      @RequestParam(value="tipo") String tipo,
                                      @RequestParam(value="detalle",required = false) String detalle,
                                      @RequestParam(value="obs",required = false) String obs      
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
        if(fecha != null && !fecha.equals("")) tempAtn.setFecha(df.stringToDate(fecha));
        if(fecha == null || fecha.equals("")) tempAtn.setFecha(null);
        tempAtn.setHora(hora);
        tempAtn.setTipoAtencion(tipo);
        tempAtn.setDetalle(detalle);
        tempAtn.setObservacion(obs);
        
        ServicioMain.updateAtencion(tempAtn);
        listaAtenciones.clear();
        listaAtenciones = ServicioMain.getListaAtencionesPorFamilia(expediente.getFamilia().getIdfamilia());
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("listaAtenciones",listaAtenciones);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_atencion", map);
}    
    
@RequestMapping(value = "/DetalleSesion", method = RequestMethod.POST)
    public ModelAndView DetalleSesion(ModelMap map, HttpSession session,@RequestParam(value="idSesion") long idSesion) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Sesion tempSes = ServicioMain.getInfoSesion(idSesion);
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("sesion",tempSes);
        return new ModelAndView("/Personal/familia/info_adop/detalle_sesion", map);
}   
 
@RequestMapping(value = "/DetalleTaller", method = RequestMethod.POST)
    public ModelAndView DetalleTaller(ModelMap map, HttpSession session,
                                      @RequestParam(value="idTurno") long idTurno,
                                      @RequestParam(value="tipoTaller") String tipoTaller,
                                      @RequestParam(value="nombreTaller") String nombreTaller,
                                      @RequestParam(value="nombreGrupo") String nombreGrupo,
                                      @RequestParam(value="nombreTurno") String nombreTurno
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
        
       
        
        map.put("listaAsistencia",listaAsistenciaReuniones);
        map.put("listaReuniones",allReuniones);
        map.put("tipoTaller",tipoTaller);
        map.put("nombreTaller",nombreTaller);
        map.put("nombreGrupo",nombreGrupo);
        map.put("nombreTurno",nombreTurno);
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_adop/detalle_taller", map);
}       
 
@RequestMapping(value = "/DetalleEvaluacion", method = RequestMethod.POST)
    public ModelAndView DetalleEvaluacion(ModelMap map, HttpSession session,@RequestParam(value="idEval") long idEval) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Evaluacion tempEval = ServicioMain.getEvaluacionJuntoPersonal(idEval);
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("evaluacion",tempEval);
        return new ModelAndView("/Personal/familia/info_adop/detalle_evaluacion", map);
}
 
    
    
    @RequestMapping(value = "/ActualizarInfoFamilia", method = RequestMethod.POST)
    public ModelAndView ActualizarInfoFamilia(ModelMap map, HttpSession session,
                                              @RequestParam(value="incesto") String incesto,  
                                              @RequestParam(value="mental") String mental,  
                                              @RequestParam(value="epilepsia") String epilepsia,  
                                              @RequestParam(value="abuso") String abuso,  
                                              @RequestParam(value="sifilis") String sifilis,  
                                              @RequestParam(value="seguimiento") String seguimiento,
                                              @RequestParam(value="operacion") String operacion,  
                                              @RequestParam(value="hiperactivo") String hiperactivo,  
                                              @RequestParam(value="especial") String especial,  
                                              @RequestParam(value="salud") String salud,  
                                              @RequestParam(value="mayor") String mayor,  
                                              @RequestParam(value="adolescente") String adolescente,
                                              @RequestParam(value="hermanos") String hermanos,
                                              
                                              @RequestParam(value="viajar") String viajar,
                                              @RequestParam(value="edadMin") String edadMin,
                                              @RequestParam(value="edadMax") String edadMax,
                                              @RequestParam(value="genero") String genero
                                    
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
        
        if (viajar != null && !viajar.equals("")) infoFam.setPuedeViajar(Short.parseShort(viajar));
        if (edadMin != null && !edadMin.equals("")) infoFam.setExpectativaEdadMin(Short.parseShort(edadMin));
        if (edadMax != null && !edadMax.equals("")) infoFam.setExpectativaEdadMax(Short.parseShort(edadMax));
        if (genero != null && !genero.equals("")) infoFam.setExpectativaGenero(genero);
        
        ServicioMain.updateInfoFam(infoFam);
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("infoFam",infoFam);
        map.put("expediente",expediente);
        return new ModelAndView("/Personal/familia/info_ant_nna", map);
}    
        
        @RequestMapping(value = "/ActualizarRegistro", method = RequestMethod.POST)
        public ModelAndView ActualizarRegistro(ModelMap map, HttpSession session,
                                              @RequestParam(value="numero") long numero,  
                                              @RequestParam(value="ht") String ht,  
                                              @RequestParam(value="numeroExp") String numeroExp,  
                                              @RequestParam(value="fechaIngreso") String fechaIngreso,  
                                              @RequestParam(value="tupa") String tupa,  
                                              @RequestParam(value="nacionalidad") String nacionalidad,
                                              @RequestParam(value="rnsa") String rnsa,  
                                              @RequestParam(value="rnaa") String rnaa,  
                                              @RequestParam(value="tipoFamilia") String tipoFamilia,  
                                              @RequestParam(value="tipoEspera") String tipoEspera,  
                                              @RequestParam(value="unidad") long unidad,  
                                              @RequestParam(value="entAsoc") long entAsoc
                                    
                                             ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        Unidad tempUa = ServicioPersonal.getUa(unidad);
        Entidad tempEnt = ServicioPersonal.getEntidad(entAsoc);
        
        expediente.setNumero(numero);
        expediente.setHt(ht);
        expediente.setNumeroExpediente(numeroExp);
        if(fechaIngreso != null && !fechaIngreso.equals("")) expediente.setFechaIngresoDga(df.stringToDate(fechaIngreso));
        if(fechaIngreso == null && fechaIngreso.equals("")) expediente.setFechaIngresoDga(null);
        if(tupa != null && !tupa.equals("")) expediente.setTupa(df.stringToDate(tupa));
        if(tupa == null && tupa.equals("")) expediente.setTupa(null);
        
        expediente.setNacionalidad(nacionalidad);
        expediente.setRnsa(Short.parseShort(rnsa));
        expediente.setRnaa(Short.parseShort(rnaa));
        
        expediente.setTipoFamilia(tipoFamilia);
        expediente.setTipoListaEspera(tipoEspera);
        
        expediente.setUnidad(tempUa);
        expediente.getFamilia().setEntidad(tempEnt);
        
        servicioEtapa.updateExpedienteFamilia(expediente);
        servicioEtapa.UpdateFamilia(expediente.getFamilia());
        
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("listaUA",ServicioPersonal.ListaUa());
        map.put("listaEntidad",ServicioPersonal.ListaEntidades());
        return new ModelAndView("/Personal/familia/info_registro", map);
}    
        
        @RequestMapping(value = "/ActualizarAdoptante", method = RequestMethod.POST)
        public ModelAndView ActualizarAdoptante(ModelMap map, HttpSession session,
                                              @RequestParam(value="adoptante") String adoptante,  
                                              @RequestParam(value="nombre", required = false) String nombre,  
                                              @RequestParam(value="apellidoP", required = false) String apellidoP,  
                                              @RequestParam(value="apellidoM", required = false) String apellidoM,  
                                              @RequestParam(value="fechaNac", required = false) String fechaNac,  
                                              @RequestParam(value="lugarNac", required = false) String lugarNac,
                                              @RequestParam(value="depNac", required = false) String depNac,  
                                              @RequestParam(value="paisNac", required = false) String paisNac,  
                                              @RequestParam(value="doc", required = false) String doc, 
                                              @RequestParam(value="numDoc", required = false) String numDoc, 
                                              @RequestParam(value="numCel", required = false) String numCel, 
                                              @RequestParam(value="correo", required = false) String correo, 
                                              @RequestParam(value="fechaMat", required = false) String fechaMat, 
                                              @RequestParam(value="nivelInstruccion") String nivelInstruccion,  
                                              @RequestParam(value="culminoNivel") String culminoNivel,
                                              @RequestParam(value="profesion") String profesion,
                                              @RequestParam(value="trabDep", required = false) String trabDep,
                                              @RequestParam(value="ocupacionDep", required = false) String ocupacionDep,
                                              @RequestParam(value="centroTrabajo", required = false) String centroTrabajo,
                                              @RequestParam(value="direccionTrabajo", required = false) String direccionTrabajo,
                                              @RequestParam(value="telefonoTrabajo", required = false) String telefonoTrabajo,
                                              @RequestParam(value="ingresoDep", required = false) String ingresoDep,
                                              @RequestParam(value="trabIndep", required = false) String trabIndep,
                                              @RequestParam(value="ocupacionInd", required = false) String ocupacionInd,
                                              @RequestParam(value="ingresoInd", required = false) String ingresoInd,
                                              @RequestParam(value="seguroSalud", required = false) String seguroSalud,
                                              @RequestParam(value="tipoSeguro", required = false) String tipoSeguro,
                                              @RequestParam(value="seguroVida", required = false) String seguroVida,
                                              @RequestParam(value="sisPensiones", required = false) String sisPensiones,
                                              @RequestParam(value="estadoActual", required = false) String estadoActual
                                    
                                             ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if(adoptante.equals("el")){
            El.setNombre(nombre);
            El.setApellidoP(apellidoP);
            El.setApellidoM(apellidoM);
            if(fechaNac != null && !fechaNac.equals("")) El.setFechaNac(df.stringToDate(fechaNac));
            if(fechaNac == null || fechaNac.equals("")) El.setFechaNac(null);
            El.setLugarNac(lugarNac);
            El.setDepaNac(depNac);
            El.setPaisNac(paisNac);
            if (doc != null && !doc.equals("")){
            char d = doc.charAt(0);
            El.setTipoDoc(d);
            }
            El.setNDoc(numDoc);
            El.setCelular(numCel);
            El.setCorreo(correo);
            if (infoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")){
                infoFam.setFechaMatrimonio(df.stringToDate(fechaMat));
            }else if(fechaMat == null || fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(null);
            }
            El.setNivelInstruccion(nivelInstruccion);
            if (culminoNivel != null && !culminoNivel.equals("")) El.setCulminoNivel(Short.parseShort(culminoNivel));
            El.setProfesion(profesion);
            /*Trabajo*/
            if (trabDep != null && !trabDep.equals("")) 
            {  
                El.setTrabajadorDepend(Short.parseShort(trabDep));
            }else{
                El.setTrabajadorDepend(null);
            }
            if (ocupacionDep != null && !ocupacionDep.equals("")) {
            El.setOcupActualDep(ocupacionDep);
            }else{
            El.setOcupActualDep(null);
            }
            if (centroTrabajo != null && !centroTrabajo.equals("")) {
            El.setCentroTrabajo(centroTrabajo);
            }else{
            El.setCentroTrabajo(null);
            }
            if (direccionTrabajo != null && !direccionTrabajo.equals("")) {
            El.setDireccionCentro(direccionTrabajo);
            }else {
            El.setDireccionCentro(null);
            }
            if (telefonoTrabajo != null && !telefonoTrabajo.equals("")) {
            El.setTelefonoCentro(telefonoTrabajo);
            }else{
            El.setTelefonoCentro(null);
            }
            if (ingresoDep != null && !ingresoDep.equals(""))
            {
                El.setIngresoDep(Long.parseLong(ingresoDep));
            }else{
                El.setIngresoDep(null);
            }
            if (trabIndep != null && !trabIndep.equals("")) {
                El.setTrabajadorIndepend(Short.parseShort(trabIndep));
            }else{
                El.setTrabajadorIndepend(null);
            }
            if (ocupacionInd != null && !ocupacionInd.equals("")) {
            El.setOcupActualInd(ocupacionInd);
            }
            else{
            El.setOcupActualInd(null);
                }
            
            if (ingresoInd != null && !ocupacionInd.equals("")) {
                El.setIngresoIndep(Long.parseLong(ingresoInd));
            }else{
                El.setIngresoIndep(null);
            }
            /*Fin Trabajo*/
            if (seguroSalud != null && !seguroSalud.equals("")) El.setSeguroSalud(Short.parseShort(seguroSalud));
            El.setTipoSeguro(tipoSeguro);
            if (seguroVida != null && !seguroVida.equals("")) El.setSeguroVida(Short.parseShort(seguroVida));
            if (sisPensiones != null && !sisPensiones.equals("")) El.setSistPensiones(Short.parseShort(sisPensiones));
            El.setSaludActual(estadoActual);
            
            ServicioMain.updateAdoptante(El);
            if (El.getApellidoP() != null && Ella.getApellidoP()!= null )
            {
                expediente.setExpediente(El.getApellidoP() + "-" + Ella.getApellidoP());
            }else if(El.getApellidoP() != null){
                expediente.setExpediente(El.getApellidoP());
            }else if(Ella.getApellidoP() != null){
                expediente.setExpediente(Ella.getApellidoP());
            }
            servicioEtapa.updateExpedienteFamilia(expediente);
            map.put("df",df);
            map.put("estado",etapaOrigen);
            map.put("expediente",expediente);
            map.put("El",El);
            return new ModelAndView("/Personal/familia/info_el", map);
        }else{
            
            Ella.setNombre(nombre);
            Ella.setApellidoP(apellidoP);
            Ella.setApellidoM(apellidoM);
            if(fechaNac != null && !fechaNac.equals("")) Ella.setFechaNac(df.stringToDate(fechaNac));
            if(fechaNac == null || fechaNac.equals("")) Ella.setFechaNac(null);
            Ella.setLugarNac(lugarNac);
            Ella.setDepaNac(depNac);
            Ella.setPaisNac(paisNac);
            if (doc != null && !doc.equals("")){
            char d = doc.charAt(0);
            Ella.setTipoDoc(d);
            }
            Ella.setNDoc(numDoc);
            Ella.setCelular(numCel);
            Ella.setCorreo(correo);
            if (infoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")){
                infoFam.setFechaMatrimonio(df.stringToDate(fechaMat));
            }else if(fechaMat == null || fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(null);
            }
            Ella.setNivelInstruccion(nivelInstruccion);
            if (culminoNivel != null && !culminoNivel.equals("")) Ella.setCulminoNivel(Short.parseShort(culminoNivel));
            Ella.setProfesion(profesion);
            /*Trabajo*/
            if (trabDep != null && !trabDep.equals("")) 
            {  
                Ella.setTrabajadorDepend(Short.parseShort(trabDep));
            }else{
                Ella.setTrabajadorDepend(null);
            }
            if (ocupacionDep != null && !ocupacionDep.equals("")) {
            Ella.setOcupActualDep(ocupacionDep);
            }else{
            Ella.setOcupActualDep(null);
            }
            if (centroTrabajo != null && !centroTrabajo.equals("")) {
            Ella.setCentroTrabajo(centroTrabajo);
            }else{
            Ella.setCentroTrabajo(null);
            }
            if (direccionTrabajo != null && !direccionTrabajo.equals("")) {
            Ella.setDireccionCentro(direccionTrabajo);
            }else {
            Ella.setDireccionCentro(null);
            }
            if (telefonoTrabajo != null && !telefonoTrabajo.equals("")) {
            Ella.setTelefonoCentro(telefonoTrabajo);
            }else{
            Ella.setTelefonoCentro(null);
            }
            if (ingresoDep != null && !ingresoDep.equals(""))
            {
                Ella.setIngresoDep(Long.parseLong(ingresoDep));
            }else{
                Ella.setIngresoDep(null);
            }
            if (trabIndep != null && !trabIndep.equals("")) {
                Ella.setTrabajadorIndepend(Short.parseShort(trabIndep));
            }else{
                Ella.setTrabajadorIndepend(null);
            }
            if (ocupacionInd != null && !ocupacionInd.equals("")) {
            Ella.setOcupActualInd(ocupacionInd);
            }
            else{
            Ella.setOcupActualInd(null);
                }
            
            if (ingresoInd != null && !ocupacionInd.equals("")) {
                Ella.setIngresoIndep(Long.parseLong(ingresoInd));
            }else{
                Ella.setIngresoIndep(null);
            }
            /*Fin Trabajo*/
            if (seguroSalud != null && !seguroSalud.equals("")) Ella.setSeguroSalud(Short.parseShort(seguroSalud));
            Ella.setTipoSeguro(tipoSeguro);
            if (seguroVida != null && !seguroVida.equals("")) Ella.setSeguroVida(Short.parseShort(seguroVida));
            if (sisPensiones != null && !sisPensiones.equals("")) Ella.setSistPensiones(Short.parseShort(sisPensiones));
            Ella.setSaludActual(estadoActual);
            
            ServicioMain.updateAdoptante(Ella);
            if (El.getApellidoP() != null && Ella.getApellidoP()!= null )
            {
                expediente.setExpediente(El.getApellidoP() + "-" + Ella.getApellidoP());
            }else if(El.getApellidoP() != null){
                expediente.setExpediente(El.getApellidoP());
            }else if(Ella.getApellidoP() != null){
                expediente.setExpediente(Ella.getApellidoP());
            }
            servicioEtapa.updateExpedienteFamilia(expediente);
            map.put("df",df);
            map.put("estado",etapaOrigen);
            map.put("expediente",expediente);
            map.put("Ella",Ella);
            return new ModelAndView("/Personal/familia/info_ella", map);
            
        }
        
        
        
}    
        
@RequestMapping(value = "/ActualizarVivienda", method = RequestMethod.POST)
public ModelAndView ActualizarVivienda(ModelMap map, HttpSession session,
                                              @RequestParam(value="propiedadVivienda", required = false) String propiedadVivienda,  
                                              @RequestParam(value="propiedadDescrip", required = false) String propiedadDescrip,  
                                              @RequestParam(value="tipoVivienda", required = false) String tipoVivienda,  
                                              @RequestParam(value="tipoDescrip", required = false) String tipoDescrip,  
                                              @RequestParam(value="domicilio", required = false) String domicilio,  
                                              @RequestParam(value="DepRes", required = false) String DepRes,
                                              @RequestParam(value="PaisRes", required = false) String PaisRes,  
                                              @RequestParam(value="telefono", required = false) String telefono,  
                                              @RequestParam(value="areaVivTotal", required = false) String areaVivTotal,  
                                              @RequestParam(value="areaVivConst", required = false) String areaVivConst,  
                                              @RequestParam(value="distViv", required = false) String distViv,  
                                              @RequestParam(value="luz", required = false) String luz,
                                              @RequestParam(value="agua", required = false) String agua,
                                              
                                              @RequestParam(value="desague", required = false) String desague,
                                              @RequestParam(value="otrosServ", required = false) String otrosServ,
                                              @RequestParam(value="materConst", required = false) String materConst,
                                              @RequestParam(value="materConstDesc", required = false) String materConstDesc,
                                              @RequestParam(value="pared", required = false) String pared,
                                              @RequestParam(value="paredDesc", required = false) String paredDesc,
                                              @RequestParam(value="techo", required = false) String techo,
                                              @RequestParam(value="techoDesc", required = false) String techoDesc,
                                              @RequestParam(value="piso", required = false) String piso,
                                              @RequestParam(value="pisoDesc", required = false) String pisoDesc
                                    
                                             ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        
        if(propiedadVivienda != null && (propiedadVivienda.equals("Propia") || propiedadVivienda.equals("Alquilada"))){
            infoFam.setPropiedadVivienda(propiedadVivienda);
        }else if (propiedadDescrip != null){
            infoFam.setPropiedadVivienda(propiedadDescrip);
        }
        
        if(tipoVivienda != null && (tipoVivienda.equals("Casa") || tipoVivienda.equals("Departamento"))){
            infoFam.setTipoVivienda(tipoVivienda);
        }else if (tipoDescrip != null){
            infoFam.setTipoVivienda(tipoDescrip);
        }
        
        if(areaVivTotal != null && !areaVivTotal.equals("")) {
            Long temp = Long.parseLong(areaVivTotal);
            infoFam.setAreaVivTotal(temp);
        }
        
        if(areaVivConst != null && !areaVivConst.equals("")) {
            Long temp = Long.parseLong(areaVivConst);
            infoFam.setAreaVivConst(temp);
        }
        
        infoFam.setDistVivienda(distViv);
        
        infoFam.setLuz(Short.parseShort(luz));
        infoFam.setAgua(Short.parseShort(agua));
        infoFam.setDesague(Short.parseShort(desague));
        
        if(otrosServ != null && !otrosServ.equals("")) infoFam.setOtrosServ(otrosServ);
            
        
        infoFam.setDomicilio(domicilio);
        infoFam.setDepRes(DepRes);
        infoFam.setPaisRes(PaisRes);
        infoFam.setTelefono(telefono);
        
        if (materConst != null && (materConst.equals("Noble"))) { infoFam.setMaterConst(materConst);
        }else {
            infoFam.setMaterConst(materConstDesc);
        }
        if (pared != null && (pared.equals("Ladrillo"))) { infoFam.setPared(pared);
        }else {
            infoFam.setPared(paredDesc);
        }
        if (techo != null && (techo.equals("Concreto"))) { infoFam.setTecho(techo);
        }else {
            infoFam.setTecho(techoDesc);
        }
        if (piso != null && (piso.equals("Cemento"))) { infoFam.setPiso(piso);
        }else {
            infoFam.setPiso(pisoDesc);
        }
        
        ServicioMain.updateInfoFam(infoFam);
        
        map.put("df",df);
        map.put("estado",etapaOrigen);
        map.put("expediente",expediente);
        map.put("infoFam",infoFam);
        return new ModelAndView("/Personal/familia/info_vivienda", map);
        
}    
    
}
