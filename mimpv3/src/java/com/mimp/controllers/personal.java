/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import com.mimp.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author john
 */
@Controller
public class personal {

    dateFormat format = new dateFormat();
    timeStampFormat ts = new timeStampFormat();
    Adoptante El = new Adoptante();
    Adoptante Ella = new Adoptante();
    InfoFamilia infoFam = new InfoFamilia();
    ExpedienteFamilia expedienteInt = new ExpedienteFamilia();
    HiberMail hibermail = new HiberMail();

    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name = "HiberMain")
    private HiberMain ServicioMain = new HiberMain();
    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();

    //////////////////////// NAVEGACION ///////////////
    @RequestMapping(value = "/inicioper", method = RequestMethod.GET)
    public ModelAndView InicioPer(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/inicio_personal", map);
    }

    @RequestMapping(value = "/inf", method = RequestMethod.GET)
    public ModelAndView Inf(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("listaTalleres", ServicioPersonal.listaTalleres());
        map.put("formato", format);
        return new ModelAndView("/Personal/Informativa/lista_charlas", map);
    }

    @RequestMapping(value = "/juzgado", method = RequestMethod.GET)
    public ModelAndView Juzgado(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg", map);
    }

    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public ModelAndView Car(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/registros/car/lista_car", map);
    }

    @RequestMapping(value = "/ua", method = RequestMethod.GET)
    public ModelAndView Ua(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/ua/lista_ua", map);
    }

    @RequestMapping(value = "/famint", method = RequestMethod.GET)
    public ModelAndView FamInt(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("df", format);
        map.addAttribute("listaFamilias", ServicioPersonal.ListaFamiliasInt());
        return new ModelAndView("/Personal/fam_inter/lista_fam_int", map);
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public ModelAndView Registros(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        return new ModelAndView("/Personal/Buscador/buscarFamilia", map);
    }

    @RequestMapping(value = "/buscarNna", method = RequestMethod.GET)
    public ModelAndView buscarNna(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        return new ModelAndView("/Personal/Buscador/buscarNna", map);
    }

    @RequestMapping(value = "/FiltrarNna", method = RequestMethod.POST)
    public ModelAndView FiltrarNna(ModelMap map, HttpSession session,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidoP", required = false) String apellidoP,
            @RequestParam(value = "apellidoM", required = false) String apellidoM,
            @RequestParam(value = "nombreAdop", required = false) String nombreAdop,
            @RequestParam(value = "apellidoPAdop", required = false) String apellidoPAdop,
            @RequestParam(value = "apellidoMAdop", required = false) String apellidoMAdop,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "prioritario", required = false) String prioritario
    /*
     @RequestParam(value="incesto",required=false) String incesto,
     @RequestParam(value="mental",required=false) String mental,
     @RequestParam(value="epilepsia",required=false) String epilepsia,
     @RequestParam(value="abuso",required=false) String abuso,
     @RequestParam(value="sifilis",required=false) String sifilis,
     @RequestParam(value="estable",required=false) String estable,
     @RequestParam(value="operacion",required=false) String operacion,
     @RequestParam(value="hiperactivo",required=false) String hiperactivo
     */
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ExpedienteNna datosExp = new ExpedienteNna();
        Nna datosNna = new Nna();

        datosExp.setNActual(nombreAdop);
        datosExp.setApellidopActual(apellidoMAdop);
        datosExp.setApellidomActual(apellidoMAdop);
        if (estado != null && !estado.equals("none")) {
            datosExp.setEstado(estado);
        }
        if (estado == null || estado.equals("none")) {
            datosExp.setEstado("none");
        }
        datosNna.setNombre(nombre);
        datosNna.setApellidoP(apellidoP);
        datosNna.setApellidoM(apellidoM);
        if (prioritario.equals("none")) {
            datosNna.setEspecial(Short.parseShort("1"));
            datosNna.setEnfermo(Short.parseShort("1"));
            datosNna.setAdolescente(Short.parseShort("1"));
            datosNna.setMayor(Short.parseShort("1"));
            datosNna.setHermano(Short.parseShort("1"));
        } else if (prioritario.equals("ne")) {
            datosNna.setEspecial(Short.parseShort("0"));
            datosNna.setEnfermo(Short.parseShort("1"));
            datosNna.setAdolescente(Short.parseShort("1"));
            datosNna.setMayor(Short.parseShort("1"));
            datosNna.setHermano(Short.parseShort("1"));
        } else if (prioritario.equals("ps")) {
            datosNna.setEspecial(Short.parseShort("1"));
            datosNna.setEnfermo(Short.parseShort("0"));
            datosNna.setAdolescente(Short.parseShort("1"));
            datosNna.setMayor(Short.parseShort("1"));
            datosNna.setHermano(Short.parseShort("1"));
        } else if (prioritario.equals("m")) {
            datosNna.setEspecial(Short.parseShort("1"));
            datosNna.setEnfermo(Short.parseShort("1"));
            datosNna.setAdolescente(Short.parseShort("1"));
            datosNna.setMayor(Short.parseShort("0"));
            datosNna.setHermano(Short.parseShort("1"));
        } else if (prioritario.equals("a")) {
            datosNna.setEspecial(Short.parseShort("1"));
            datosNna.setEnfermo(Short.parseShort("1"));
            datosNna.setAdolescente(Short.parseShort("0"));
            datosNna.setMayor(Short.parseShort("1"));
            datosNna.setHermano(Short.parseShort("1"));
        } else if (prioritario.equals("h")) {
            datosNna.setEspecial(Short.parseShort("1"));
            datosNna.setEnfermo(Short.parseShort("1"));
            datosNna.setAdolescente(Short.parseShort("1"));
            datosNna.setMayor(Short.parseShort("1"));
            datosNna.setHermano(Short.parseShort("0"));
        }
        /*
         datosNna.setIncesto(Short.parseShort(incesto));
         datosNna.setMental(Short.parseShort(mental));
         datosNna.setEpilepsia(Short.parseShort(epilepsia));
         datosNna.setAbuso(Short.parseShort(abuso));
         datosNna.setSifilis(Short.parseShort(sifilis));
         datosNna.setSeguiMedico(Short.parseShort(estable));
         datosNna.setOperacion(Short.parseShort(incesto));
         datosNna.setHiperactivo(Short.parseShort(hiperactivo));
         */
        ArrayList<ExpedienteNna> listaBusqueda = new ArrayList();
        listaBusqueda = ServicioPersonal.FiltrarNna(datosExp, datosNna);

        map.put("listaBusqueda", listaBusqueda);
        return new ModelAndView("/Personal/Buscador/buscarNna", map);
    }

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ModelAndView Usuarios(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/registros/usuarios/lista_personal", map);
    }

    @RequestMapping(value = "/organismo", method = RequestMethod.GET)
    public ModelAndView Organismo(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaOrganismos", ServicioPersonal.ListaOrganismos());
        return new ModelAndView("/Personal/registros/organismo/lista_org", map);
    }

    @RequestMapping(value = "/autoridad", method = RequestMethod.GET)
    public ModelAndView Autoridad(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        map.put("listaAutoridades", ServicioPersonal.ListaAutoridades());
        return new ModelAndView("/Personal/registros/autoridad/lista_aut", map);
    }

    @RequestMapping(value = "/act_info", method = RequestMethod.GET)
    public ModelAndView Act_info(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        dateFormat format = new dateFormat();
        String fechanac = "";
        String fechaing = "";
        try {
            fechanac = format.dateToString(usuario.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechanac", fechanac);
        try {
            fechaing = format.dateToString(usuario.getFechaIngreso());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaing", fechaing);
        return new ModelAndView("/Personal/actualizar_info", map);
    }

    @RequestMapping(value = "/act_info/act", method = RequestMethod.POST)
    public ModelAndView Act_info_act(ModelMap map,
            @RequestParam("correo_trabajo") String correo_trabajo,
            @RequestParam("correo_personal") String correo_personal,
            @RequestParam("profesion") String profesion,
            @RequestParam("grado_instruccion") String grado_instruccion,
            @RequestParam("cargo") String cargo,
            @RequestParam("regimen") String regimen,
            @RequestParam("domicilio") String domicilio,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        usuario.setCorreoTrabajo(correo_trabajo);
        usuario.setCorreoPersonal(correo_personal);
        usuario.setProfesion(profesion);
        usuario.setGradoInstruccion(grado_instruccion);
        usuario.setCargo(cargo);
        usuario.setRegimen(regimen);
        usuario.setDomicilio(domicilio);
        session.removeAttribute("usuario");
        session.setAttribute("usuario", usuario);
        ServicioPersonal.UpdatePersonal(usuario);

        dateFormat format = new dateFormat();
        String fechanac = "";
        String fechaing = "";
        try {
            fechanac = format.dateToString(usuario.getFechaNacimiento());
        } catch (Exception ex) {
        }
        map.addAttribute("fechanac", fechanac);
        try {
            fechaing = format.dateToString(usuario.getFechaIngreso());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaing", fechaing);
        return new ModelAndView("/Personal/actualizar_info", map);
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ModelAndView Password(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/actualizar_pass", map);
    }

    ////////////////////// REGISTRAR/EDITAR AUTORIDAD ////////////////////////////////////
    @RequestMapping(value = "/irEditarAut", method = RequestMethod.GET)
    public ModelAndView IrEditarAut(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/registros/autoridad/editar_aut", map);
    }

    @RequestMapping(value = "/irEditarAut2", method = RequestMethod.POST)
    public ModelAndView IrEditarAut2(ModelMap map, @RequestParam("id") int id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        Autoridad temp = new Autoridad();
        temp = ServicioPersonal.getAutoridad(id);
        String fechaEmision = "";
        String fechaRenov = "";
        String fechaVenc = "";
        try {
            fechaEmision = format.dateToString(temp.getEntidad().getFechaResol());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaEmision", fechaEmision);
        try {
            fechaRenov = format.dateToString(temp.getEntidad().getFechaRenov());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaRenov", fechaRenov);
        try {
            fechaVenc = format.dateToString(temp.getEntidad().getFechaVenc());
        } catch (Exception ex) {
        }
        map.addAttribute("fechaVenc", fechaVenc);
        map.put("autoridad", temp);
        return new ModelAndView("/Personal/registros/autoridad/editar_aut", map);
    }

    @RequestMapping(value = "/editAut", method = RequestMethod.POST)
    public ModelAndView EditarAut(ModelMap map,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") String tipo,
            @RequestParam("pais") String pais,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("correo") String correo,
            @RequestParam("resol_aut") String resol_aut,
            @RequestParam("fecha_emis_resol") String fecha_emis_resol,
            @RequestParam("resol_renov") String resol_renov,
            @RequestParam("fecha_renov") String fecha_renov,
            @RequestParam("fecha_venc_aut") String fecha_venc_aut,
            @RequestParam("obs") String obs,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Autoridad aut = new Autoridad();
        Entidad ent = new Entidad();

        ent.setNombre(nombre);
        ent.setDireccion(direccion);
        ent.setTelefono(telefono);
        ent.setPais(pais);
        ent.setResolAuto(resol_aut);
        Date tempfecha = ent.getFechaResol();
        if (fecha_emis_resol != null) {
            if (fecha_emis_resol.contains("ene") || fecha_emis_resol.contains("feb") || fecha_emis_resol.contains("mar") || fecha_emis_resol.contains("abr")
                    || fecha_emis_resol.contains("may") || fecha_emis_resol.contains("jun") || fecha_emis_resol.contains("jul") || fecha_emis_resol.contains("ago")
                    || fecha_emis_resol.contains("set") || fecha_emis_resol.contains("oct") || fecha_emis_resol.contains("nov") || fecha_emis_resol.contains("dic")) {
                ent.setFechaResol(tempfecha);
            } else {
                if (!fecha_emis_resol.equals("")) {
                    ent.setFechaResol(format.stringToDate(fecha_emis_resol));
                } else {
                    ent.setFechaResol(null);
                }
            }
        } else {
            ent.setFechaResol(null);
        }
        ent.setResolRenov(resol_renov);
        tempfecha = ent.getFechaRenov();
        if (fecha_renov != null) {
            if (fecha_renov.contains("ene") || fecha_renov.contains("feb") || fecha_renov.contains("mar") || fecha_renov.contains("abr")
                    || fecha_renov.contains("may") || fecha_renov.contains("jun") || fecha_renov.contains("jul") || fecha_renov.contains("ago")
                    || fecha_renov.contains("set") || fecha_renov.contains("oct") || fecha_renov.contains("nov") || fecha_renov.contains("dic")) {
                ent.setFechaRenov(tempfecha);
            } else {
                if (!fecha_renov.equals("")) {
                    ent.setFechaRenov(format.stringToDate(fecha_renov));
                } else {
                    ent.setFechaRenov(null);
                }
            }
        } else {
            ent.setFechaRenov(null);
        }
        tempfecha = ent.getFechaVenc();
        if (fecha_venc_aut != null) {
            if (fecha_venc_aut.contains("ene") || fecha_venc_aut.contains("feb") || fecha_venc_aut.contains("mar") || fecha_venc_aut.contains("abr")
                    || fecha_venc_aut.contains("may") || fecha_venc_aut.contains("jun") || fecha_venc_aut.contains("jul") || fecha_venc_aut.contains("ago")
                    || fecha_venc_aut.contains("set") || fecha_venc_aut.contains("oct") || fecha_venc_aut.contains("nov") || fecha_venc_aut.contains("dic")) {
                ent.setFechaVenc(tempfecha);
            } else {
                if (!fecha_venc_aut.equals("")) {
                    ent.setFechaVenc(format.stringToDate(fecha_venc_aut));
                } else {
                    ent.setFechaVenc(null);
                }
            }
        } else {
            ent.setFechaVenc(null);
        }
        ent.setObs(obs);
        ent.setUser(user);
        ent.setCorreo(correo);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            ent.setPass(pass);
        }
        aut.setTipo(tipo);

        if (ServicioPersonal.usuario(ent.getUser())) {

            String mensaje_error = "El nombre de usuario ya existe en la base de datos. Por favor, ingresar nuevamente";

            map.put("listaOrganismos", ServicioPersonal.ListaAutoridades());
            map.put("mensaje", mensaje_error);
            return new ModelAndView("/Personal/registros/autoridad/editar_aut", map);
        }

        ServicioPersonal.InsertAut(ent, aut);

        String mensaje_log = "Se creó la autoridad con nombre: " + ent.getNombre()
                + " con ID: " + aut.getIdautoridad();

        String Tipo_registro = "Autoridad";

        try {
            String Numero_registro = String.valueOf(aut.getIdautoridad());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaAutoridades", ServicioPersonal.ListaAutoridades());
        return new ModelAndView("/Personal/registros/autoridad/lista_aut", map);
    }

    @RequestMapping(value = "/updateAut", method = RequestMethod.POST)
    public ModelAndView UpdateAut(ModelMap map,
            @RequestParam("id") long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") String tipo,
            @RequestParam("pais") String pais,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("resol_aut") String resol_aut,
            @RequestParam("fecha_emis_resol") String fecha_emis_resol,
            @RequestParam("resol_renov") String resol_renov,
            @RequestParam("fecha_renov") String fecha_renov,
            @RequestParam("fecha_venc_aut") String fecha_venc_aut,
            @RequestParam("obs") String obs,
            HttpSession session) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("login", map);
        }
        Autoridad temp = new Autoridad();
        temp = ServicioPersonal.getAutoridad(id);
        temp.setTipo(tipo);

        temp.getEntidad().setNombre(nombre);
        temp.getEntidad().setPais(pais);
        temp.getEntidad().setUser(user);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            temp.getEntidad().setPass(pass);
        }
        temp.getEntidad().setDireccion(direccion);
        temp.getEntidad().setTelefono(telefono);
        temp.getEntidad().setResolAuto(resol_aut);
        Date tempfecha = temp.getEntidad().getFechaResol();
        if (fecha_emis_resol != null) {
            if (fecha_emis_resol.contains("ene") || fecha_emis_resol.contains("feb") || fecha_emis_resol.contains("mar") || fecha_emis_resol.contains("abr")
                    || fecha_emis_resol.contains("may") || fecha_emis_resol.contains("jun") || fecha_emis_resol.contains("jul") || fecha_emis_resol.contains("ago")
                    || fecha_emis_resol.contains("set") || fecha_emis_resol.contains("oct") || fecha_emis_resol.contains("nov") || fecha_emis_resol.contains("dic")) {
                temp.getEntidad().setFechaResol(tempfecha);
            } else {
                if (!fecha_emis_resol.equals("")) {
                    temp.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
                } else {
                    temp.getEntidad().setFechaResol(null);
                }
            }
        } else {
            temp.getEntidad().setFechaResol(null);
        }
        temp.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
        temp.getEntidad().setResolRenov(resol_renov);
        tempfecha = temp.getEntidad().getFechaRenov();
        if (fecha_renov != null) {
            if (fecha_renov.contains("ene") || fecha_renov.contains("feb") || fecha_renov.contains("mar") || fecha_renov.contains("abr")
                    || fecha_renov.contains("may") || fecha_renov.contains("jun") || fecha_renov.contains("jul") || fecha_renov.contains("ago")
                    || fecha_renov.contains("set") || fecha_renov.contains("oct") || fecha_renov.contains("nov") || fecha_renov.contains("dic")) {
                temp.getEntidad().setFechaRenov(tempfecha);
            } else {
                if (!fecha_renov.equals("")) {
                    temp.getEntidad().setFechaRenov(format.stringToDate(fecha_renov));
                } else {
                    temp.getEntidad().setFechaRenov(null);
                }
            }
        } else {
            temp.getEntidad().setFechaRenov(null);
        }
        tempfecha = temp.getEntidad().getFechaVenc();
        if (fecha_venc_aut != null) {
            if (fecha_venc_aut.contains("ene") || fecha_venc_aut.contains("feb") || fecha_venc_aut.contains("mar") || fecha_venc_aut.contains("abr")
                    || fecha_venc_aut.contains("may") || fecha_venc_aut.contains("jun") || fecha_venc_aut.contains("jul") || fecha_venc_aut.contains("ago")
                    || fecha_venc_aut.contains("set") || fecha_venc_aut.contains("oct") || fecha_venc_aut.contains("nov") || fecha_venc_aut.contains("dic")) {
                temp.getEntidad().setFechaVenc(tempfecha);
            } else {
                if (!fecha_venc_aut.equals("")) {
                    temp.getEntidad().setFechaVenc(format.stringToDate(fecha_venc_aut));
                } else {
                    temp.getEntidad().setFechaVenc(null);
                }
            }
        } else {
            temp.getEntidad().setFechaVenc(null);
        }
        temp.getEntidad().setObs(obs);
        ServicioPersonal.UpdateAut(temp.getEntidad(), temp);

        String mensaje_log = "Se editó la Autoridad con nombre: " + temp.getEntidad().getNombre()
                + " con ID: " + ServicioPersonal.getAutoridad(temp.getIdautoridad()).getIdautoridad();

        String Tipo_registro = "Autoridad";

        try {
            String Numero_registro = String.valueOf(ServicioPersonal.getAutoridad(temp.getIdautoridad()).getIdautoridad());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaAutoridades", ServicioPersonal.ListaAutoridades());
        return new ModelAndView("/Personal/registros/autoridad/lista_aut", map);
    }
    /////////////////////////////////////////////////////////////////////////////////

    ////////////////////// REGISTRAR/EDITAR ORGANISMO ////////////////////////////////////
    @RequestMapping(value = "/irEditarOrg", method = RequestMethod.GET)
    public ModelAndView IrEditarOrg(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        return new ModelAndView("/Personal/registros/organismo/editar_org", map);
    }

    @RequestMapping(value = "/irEditarOrg2", method = RequestMethod.POST)
    public ModelAndView IrEditarOrg2(ModelMap map, @RequestParam("id") int id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Organismo temp = new Organismo();
        temp = ServicioPersonal.getOrganismo(id);

        String fechaEmision = "";
        String fechaRenov = "";
        String fechaVenc = "";
        String fechaAutR = "";
        String fechaRenovR = "";
        String fechaVencR = "";
        try {
            fechaEmision = format.dateToString(temp.getEntidad().getFechaResol());
        } catch (Exception ex) {
        }
        try {
            fechaRenov = format.dateToString(temp.getEntidad().getFechaRenov());
        } catch (Exception ex) {
        }
        try {
            fechaVenc = format.dateToString(temp.getEntidad().getFechaVenc());
        } catch (Exception ex) {
        }

        try {
            fechaAutR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaAuto());
        } catch (Exception ex) {
        }
        try {
            fechaRenovR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaRenov());
        } catch (Exception ex) {
        }
        try {
            fechaVencR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaVencAuto());
        } catch (Exception ex) {
        }

        map.addAttribute("fechaEmision", fechaEmision);
        map.addAttribute("fechaRenov", fechaRenov);
        map.addAttribute("fechaVenc", fechaVenc);
        map.addAttribute("fechaAutR", fechaAutR);
        map.addAttribute("fechaRenovR", fechaRenovR);
        map.addAttribute("fechaVencR", fechaVencR);

        map.put("organismo", temp);

        return new ModelAndView("/Personal/registros/organismo/editar_org", map);
    }

    @RequestMapping(value = "/editOrg", method = RequestMethod.POST)
    public ModelAndView EditarOrg(ModelMap map,
            //datos a ingresar en entidad
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("pais") String pais,
            @RequestParam("resol_aut") String resol_aut,
            @RequestParam("fecha_emis_resol") String fecha_emis_resol,
            @RequestParam("resol_renov") String resol_renov,
            @RequestParam("fecha_renov") String fecha_renov,
            @RequestParam("fecha_venc_aut") String fecha_venc_aut,
            @RequestParam("obs") String obs,
            //datos a ingresar en organismo
            @RequestParam("competencia") String competencia,
            //datos a ingresar en representante
            @RequestParam("nombreR") String nombreR,
            @RequestParam("apellidoP") String apellidoP,
            @RequestParam("apellidoM") String apellidoM,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("fechaAutR") String fechaAutR,
            @RequestParam("fechaRenovR") String fechaRenovR,
            @RequestParam("fechaVencR") String fechaVencR,
            @RequestParam("correo") String correo,
            @RequestParam("celular") String celular,
            @RequestParam("direccionR") String direccionR,
            @RequestParam("obsR") String obsR,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Organismo org = new Organismo();
        Representante rep = new Representante();
        Entidad ent = new Entidad();

        org.setCompetencia(competencia);

        rep.setNombre(nombreR);
        rep.setApellidoP(apellidoP);
        rep.setApelldoM(apellidoM);
        Date tempfecha = rep.getFechaAuto();
        if (fechaAutR != null) {
            if (fechaAutR.contains("ene") || fechaAutR.contains("feb") || fechaAutR.contains("mar") || fechaAutR.contains("abr")
                    || fechaAutR.contains("may") || fechaAutR.contains("jun") || fechaAutR.contains("jul") || fechaAutR.contains("ago")
                    || fechaAutR.contains("set") || fechaAutR.contains("oct") || fechaAutR.contains("nov") || fechaAutR.contains("dic")) {
                rep.setFechaAuto(tempfecha);
            } else {
                if (!fechaAutR.equals("")) {
                    rep.setFechaAuto(format.stringToDate(fechaAutR));
                } else {
                    rep.setFechaAuto(null);
                }
            }
        } else {
            rep.setFechaAuto(null);
        }
        tempfecha = rep.getFechaRenov();
        if (fechaRenovR != null) {
            if (fechaRenovR.contains("ene") || fechaRenovR.contains("feb") || fechaRenovR.contains("mar") || fechaRenovR.contains("abr")
                    || fechaRenovR.contains("may") || fechaRenovR.contains("jun") || fechaRenovR.contains("jul") || fechaRenovR.contains("ago")
                    || fechaRenovR.contains("set") || fechaRenovR.contains("oct") || fechaRenovR.contains("nov") || fechaRenovR.contains("dic")) {
                rep.setFechaRenov(tempfecha);
            } else {
                if (!fechaAutR.equals("")) {
                    rep.setFechaRenov(format.stringToDate(fechaRenovR));
                } else {
                    rep.setFechaRenov(null);
                }
            }
        } else {
            rep.setFechaRenov(null);
        }
        tempfecha = rep.getFechaVencAuto();
        if (fechaAutR != null) {
            if (fechaVencR.contains("ene") || fechaVencR.contains("feb") || fechaVencR.contains("mar") || fechaVencR.contains("abr")
                    || fechaVencR.contains("may") || fechaVencR.contains("jun") || fechaVencR.contains("jul") || fechaVencR.contains("ago")
                    || fechaVencR.contains("set") || fechaVencR.contains("oct") || fechaVencR.contains("nov") || fechaVencR.contains("dic")) {
                rep.setFechaVencAuto(tempfecha);
            } else {
                if (!fechaAutR.equals("")) {
                    rep.setFechaVencAuto(format.stringToDate(fechaVencR));
                } else {
                    rep.setFechaVencAuto(null);
                }
            }
        } else {
            rep.setFechaVencAuto(null);
        }
        rep.setCorreo(correo);
        rep.setDireccion(direccionR);
        rep.setCelular(celular);
        rep.setObs(obsR);

        ent.setNombre(nombre);
        ent.setDireccion(direccion);
        ent.setTelefono(telefono);
        ent.setPais(pais);
        ent.setUser(user);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            ent.setPass(pass);
        }
        ent.setResolAuto(resol_aut);
        tempfecha = ent.getFechaResol();
        if (fecha_emis_resol != null) {
            if (fecha_emis_resol.contains("ene") || fecha_emis_resol.contains("feb") || fecha_emis_resol.contains("mar") || fecha_emis_resol.contains("abr")
                    || fecha_emis_resol.contains("may") || fecha_emis_resol.contains("jun") || fecha_emis_resol.contains("jul") || fecha_emis_resol.contains("ago")
                    || fecha_emis_resol.contains("set") || fecha_emis_resol.contains("oct") || fecha_emis_resol.contains("nov") || fecha_emis_resol.contains("dic")) {
                ent.setFechaResol(tempfecha);
            } else {
                if (!fecha_emis_resol.equals("")) {
                    ent.setFechaResol(format.stringToDate(fecha_emis_resol));
                } else {
                    ent.setFechaResol(null);
                }
            }
        } else {
            ent.setFechaResol(null);
        }
        ent.setResolRenov(resol_renov);
        tempfecha = ent.getFechaRenov();
        if (fecha_renov != null) {
            if (fecha_renov.contains("ene") || fecha_renov.contains("feb") || fecha_renov.contains("mar") || fecha_renov.contains("abr")
                    || fecha_renov.contains("may") || fecha_renov.contains("jun") || fecha_renov.contains("jul") || fecha_renov.contains("ago")
                    || fecha_renov.contains("set") || fecha_renov.contains("oct") || fecha_renov.contains("nov") || fecha_renov.contains("dic")) {
                ent.setFechaRenov(tempfecha);
            } else {
                if (!fecha_renov.equals("")) {
                    ent.setFechaRenov(format.stringToDate(fecha_renov));
                } else {
                    ent.setFechaRenov(null);
                }
            }
        } else {
            ent.setFechaRenov(null);
        }
        tempfecha = ent.getFechaVenc();
        if (fecha_venc_aut != null) {
            if (fecha_venc_aut.contains("ene") || fecha_venc_aut.contains("feb") || fecha_venc_aut.contains("mar") || fecha_venc_aut.contains("abr")
                    || fecha_venc_aut.contains("may") || fecha_venc_aut.contains("jun") || fecha_venc_aut.contains("jul") || fecha_venc_aut.contains("ago")
                    || fecha_venc_aut.contains("set") || fecha_venc_aut.contains("oct") || fecha_venc_aut.contains("nov") || fecha_venc_aut.contains("dic")) {
                ent.setFechaVenc(tempfecha);
            } else {
                if (!fecha_venc_aut.equals("")) {
                    ent.setFechaVenc(format.stringToDate(fecha_venc_aut));
                } else {
                    ent.setFechaVenc(null);
                }
            }
        } else {
            ent.setFechaVenc(null);
        }
        ent.setObs(obs);

        if (ServicioPersonal.usuario(ent.getUser())) {

            String mensaje_error = "El nombre de usuario ya existe en la base de datos. Por favor, ingresar nuevamente";

            map.put("listaOrganismos", ServicioPersonal.ListaOrganismos());
            map.put("mensaje", mensaje_error);
            return new ModelAndView("/Personal/registros/organismo/editar_org", map);
        }

        ServicioPersonal.InsertOrg(ent, rep, org);

        String mensaje_log = "Se creó el Organismo con nombre: " + org.getEntidad().getNombre()
                + " con ID: " + org.getIdorganismo();

        String Tipo_registro = "Organismo";

        try {
            String Numero_registro = String.valueOf(org.getIdorganismo());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaOrganismos", ServicioPersonal.ListaOrganismos());
        return new ModelAndView("/Personal/registros/organismo/lista_org", map);
    }

    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ModelAndView UpdateOrg(ModelMap map,
            @RequestParam("id") int id,
            //datos a ingresar en entidad
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("telefono") String telefono,
            @RequestParam("pais") String pais,
            @RequestParam("resol_aut") String resol_aut,
            @RequestParam("fecha_emis_resol") String fecha_emis_resol,
            @RequestParam("resol_renov") String resol_renov,
            @RequestParam("fecha_renov") String fecha_renov,
            @RequestParam("fecha_venc_aut") String fecha_venc_aut,
            @RequestParam("obs") String obs,
            //datos a ingresar en organismo
            @RequestParam("competencia") String competencia,
            //datos a ingresar en representante
            @RequestParam("nombreR") String nombreR,
            @RequestParam("apellidoP") String apellidoP,
            @RequestParam("apellidoM") String apellidoM,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("fechaAutR") String fechaAutR,
            @RequestParam("fechaRenovR") String fechaRenovR,
            @RequestParam("fechaVencR") String fechaVencR,
            @RequestParam("correo") String correo,
            @RequestParam("celular") String celular,
            @RequestParam("direccionR") String direccionR,
            @RequestParam("obsR") String obsR,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Organismo org = new Organismo();

        org = ServicioPersonal.getOrganismo(id);

        org.setCompetencia(competencia);

        org.getRepresentantes().iterator().next().setNombre(nombreR);
        org.getRepresentantes().iterator().next().setApellidoP(apellidoP);
        org.getRepresentantes().iterator().next().setApelldoM(apellidoM);

        Date tempfecha = org.getRepresentantes().iterator().next().getFechaAuto();
        if (fechaAutR != null) {
            if (fechaAutR.contains("ene") || fechaAutR.contains("feb") || fechaAutR.contains("mar") || fechaAutR.contains("abr")
                    || fechaAutR.contains("may") || fechaAutR.contains("jun") || fechaAutR.contains("jul") || fechaAutR.contains("ago")
                    || fechaAutR.contains("set") || fechaAutR.contains("oct") || fechaAutR.contains("nov") || fechaAutR.contains("dic")) {
                org.getRepresentantes().iterator().next().setFechaAuto(tempfecha);
            } else {
                if (!fechaAutR.equals("")) {
                    org.getRepresentantes().iterator().next().setFechaAuto(format.stringToDate(fechaAutR));
                } else {
                    org.getRepresentantes().iterator().next().setFechaAuto(null);
                }
            }
        } else {
            org.getRepresentantes().iterator().next().setFechaAuto(null);
        }
        tempfecha = org.getRepresentantes().iterator().next().getFechaRenov();
        if (fechaRenovR != null) {
            if (fechaRenovR.contains("ene") || fechaRenovR.contains("feb") || fechaRenovR.contains("mar") || fechaRenovR.contains("abr")
                    || fechaRenovR.contains("may") || fechaRenovR.contains("jun") || fechaRenovR.contains("jul") || fechaRenovR.contains("ago")
                    || fechaRenovR.contains("set") || fechaRenovR.contains("oct") || fechaRenovR.contains("nov") || fechaRenovR.contains("dic")) {
                org.getRepresentantes().iterator().next().setFechaRenov(tempfecha);
            } else {
                if (!fechaRenovR.equals("")) {
                    org.getRepresentantes().iterator().next().setFechaRenov(format.stringToDate(fechaRenovR));
                } else {
                    org.getRepresentantes().iterator().next().setFechaRenov(null);
                }
            }
        } else {
            org.getRepresentantes().iterator().next().setFechaRenov(null);
        }
        tempfecha = org.getRepresentantes().iterator().next().getFechaVencAuto();
        if (fechaVencR != null) {
            if (fechaVencR.contains("ene") || fechaVencR.contains("feb") || fechaVencR.contains("mar") || fechaVencR.contains("abr")
                    || fechaVencR.contains("may") || fechaVencR.contains("jun") || fechaVencR.contains("jul") || fechaVencR.contains("ago")
                    || fechaVencR.contains("set") || fechaVencR.contains("oct") || fechaVencR.contains("nov") || fechaVencR.contains("dic")) {
                org.getRepresentantes().iterator().next().setFechaVencAuto(tempfecha);
            } else {
                if (!fechaRenovR.equals("")) {
                    org.getRepresentantes().iterator().next().setFechaVencAuto(format.stringToDate(fechaVencR));
                } else {
                    org.getRepresentantes().iterator().next().setFechaVencAuto(null);
                }
            }
        } else {
            org.getRepresentantes().iterator().next().setFechaVencAuto(null);
        }
        org.getRepresentantes().iterator().next().setCorreo(correo);
        org.getRepresentantes().iterator().next().setDireccion(direccionR);
        org.getRepresentantes().iterator().next().setCelular(celular);
        org.getRepresentantes().iterator().next().setObs(obsR);

        org.getEntidad().setNombre(nombre);
        org.getEntidad().setUser(user);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            org.getEntidad().setPass(pass);
        }
        org.getEntidad().setDireccion(direccion);
        org.getEntidad().setTelefono(telefono);
        org.getEntidad().setPais(pais);
        org.getEntidad().setResolAuto(resol_aut);
        tempfecha = org.getEntidad().getFechaResol();
        if (fecha_emis_resol != null) {
            if (fecha_emis_resol.contains("ene") || fecha_emis_resol.contains("feb") || fecha_emis_resol.contains("mar") || fecha_emis_resol.contains("abr")
                    || fecha_emis_resol.contains("may") || fecha_emis_resol.contains("jun") || fecha_emis_resol.contains("jul") || fecha_emis_resol.contains("ago")
                    || fecha_emis_resol.contains("set") || fecha_emis_resol.contains("oct") || fecha_emis_resol.contains("nov") || fecha_emis_resol.contains("dic")) {
                org.getEntidad().setFechaResol(tempfecha);
            } else {
                if (!fecha_emis_resol.equals("")) {
                    org.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
                } else {
                    org.getEntidad().setFechaResol(null);
                }
            }
        } else {
            org.getEntidad().setFechaResol(null);
        }
        org.getEntidad().setResolRenov(resol_renov);
        tempfecha = org.getEntidad().getFechaRenov();
        if (fecha_renov != null) {
            if (fecha_renov.contains("ene") || fecha_renov.contains("feb") || fecha_renov.contains("mar") || fecha_renov.contains("abr")
                    || fecha_renov.contains("may") || fecha_renov.contains("jun") || fecha_renov.contains("jul") || fecha_renov.contains("ago")
                    || fecha_renov.contains("set") || fecha_renov.contains("oct") || fecha_renov.contains("nov") || fecha_renov.contains("dic")) {
                org.getEntidad().setFechaRenov(tempfecha);
            } else {
                if (!fecha_renov.equals("")) {
                    org.getEntidad().setFechaRenov(format.stringToDate(fecha_renov));
                } else {
                    org.getEntidad().setFechaRenov(null);
                }
            }
        } else {
            org.getEntidad().setFechaRenov(null);
        }
        tempfecha = org.getEntidad().getFechaVenc();
        if (fecha_venc_aut != null) {
            if (fecha_venc_aut.contains("ene") || fecha_venc_aut.contains("feb") || fecha_venc_aut.contains("mar") || fecha_venc_aut.contains("abr")
                    || fecha_venc_aut.contains("may") || fecha_venc_aut.contains("jun") || fecha_venc_aut.contains("jul") || fecha_venc_aut.contains("ago")
                    || fecha_venc_aut.contains("set") || fecha_venc_aut.contains("oct") || fecha_venc_aut.contains("nov") || fecha_venc_aut.contains("dic")) {
                org.getEntidad().setFechaVenc(tempfecha);
            } else {
                if (!fecha_venc_aut.equals("")) {
                    org.getEntidad().setFechaVenc(format.stringToDate(fecha_venc_aut));
                } else {
                    org.getEntidad().setFechaVenc(null);
                }
            }
        } else {
            org.getEntidad().setFechaVenc(null);
        }
        org.getEntidad().setObs(obs);

        ServicioPersonal.UpdateOrg(org.getEntidad(), org.getRepresentantes().iterator().next(), org);

        String mensaje_log = "Se editó el Organismo con nombre: " + org.getEntidad().getNombre()
                + " con ID: " + org.getIdorganismo();

        String Tipo_registro = "Organismo";

        try {
            String Numero_registro = String.valueOf(org.getIdorganismo());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaOrganismos", ServicioPersonal.ListaOrganismos());
        return new ModelAndView("/Personal/registros/organismo/lista_org", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR CAR ////////////////////////////////////
    @RequestMapping(value = "/irEditarCar", method = RequestMethod.GET)
    public ModelAndView IrEditarCar(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        return new ModelAndView("/Personal/registros/car/editar_car", map);
    }

    @RequestMapping(value = "/irEditarCar2", method = RequestMethod.POST)
    public ModelAndView IrEditarCar2(ModelMap map, @RequestParam("id") int id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Car temp = new Car();
        temp = ServicioPersonal.getCar(id);
        map.put("car", temp);

        return new ModelAndView("/Personal/registros/car/editar_car", map);
    }

    @RequestMapping(value = "/editCar", method = RequestMethod.POST)
    public ModelAndView EditarCar(ModelMap map,
            //datos a ingresar en CAR
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("departamento") String departamento,
            @RequestParam("provincia") String provincia,
            @RequestParam("distrito") String distrito,
            @RequestParam("director") String director,
            @RequestParam("correo") String correo,
            @RequestParam("fax") String fax,
            @RequestParam("celular") String celular,
            @RequestParam("telefono") String telefono,
            @RequestParam("obs") String obs,
            HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Car car = new Car();

        car.setNombre(nombre);
        car.setDireccion(direccion);
        car.setDepartamento(departamento);
        car.setProvincia(provincia);
        car.setDistrito(distrito);
        car.setDirector(director);
        car.setCorreo(correo);
        car.setFax(fax);
        car.setCelular(celular);
        car.setTelefono(telefono);
        car.setObservaciones(obs);

        ServicioPersonal.InsertCar(car);

        String mensaje_log = "Se registró nuevo CAR con Nombre, " + car.getNombre() + " y ID:" + String.valueOf(car.getIdcar());
        String Tipo_registro = "CAR";

        try {
            String Numero_registro = String.valueOf(car.getIdcar());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/registros/car/lista_car", map);
    }

    @RequestMapping(value = "/updateCar", method = RequestMethod.POST)
    public ModelAndView UpdateCar(ModelMap map,
            //datos a ingresar en CAR
            @RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("departamento") String departamento,
            @RequestParam("provincia") String provincia,
            @RequestParam("distrito") String distrito,
            @RequestParam("director") String director,
            @RequestParam("correo") String correo,
            @RequestParam("fax") String fax,
            @RequestParam("celular") String celular,
            @RequestParam("telefono") String telefono,
            @RequestParam("obs") String obs,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Car car = new Car();
        car = ServicioPersonal.getCar(id);
        car.setNombre(nombre);
        car.setDireccion(direccion);
        car.setDepartamento(departamento);
        car.setProvincia(provincia);
        car.setDistrito(distrito);
        car.setDirector(director);
        car.setCorreo(correo);
        car.setFax(fax);
        car.setCelular(celular);
        car.setTelefono(telefono);
        car.setObservaciones(obs);

        ServicioPersonal.UpdateCar(car);

        String mensaje_log = "Se editó el CAR con Nombre, " + car.getNombre() + " y ID:" + String.valueOf(car.getIdcar());
        String Tipo_registro = "CAR";

        try {
            String Numero_registro = String.valueOf(car.getIdcar());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaCar", ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/registros/car/lista_car", map);
    }
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR JUZGADOS ////////////////////////////////////

    @RequestMapping(value = "/irEditarJuzgado", method = RequestMethod.GET)
    public ModelAndView IrEditarJuzgado(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        return new ModelAndView("/Personal/registros/juzgado/editar_juzgado", map);
    }

    @RequestMapping(value = "/irEditarJuzgado2", method = RequestMethod.POST)
    public ModelAndView IrEditarJuzgado2(ModelMap map, @RequestParam("id") int id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Juzgado temp = new Juzgado();
        temp = ServicioPersonal.getJuzgado(id);
        map.put("juzgado", temp);

        return new ModelAndView("/Personal/registros/juzgado/editar_juzgado", map);
    }

    @RequestMapping(value = "/editJuzgado", method = RequestMethod.POST)
    public ModelAndView EditarJuzgado(ModelMap map,
            //datos a ingresar en  Juzgado
            @RequestParam("nombre") String nombre,
            @RequestParam("denominacion") String denominacion,
            @RequestParam("especialidad") String especialidad,
            @RequestParam("direccion") String direccion,
            @RequestParam("departamento") String departamento,
            @RequestParam("corteS") String corteS,
            @RequestParam("distritoJ") String distritoJ,
            @RequestParam("nombreJ") String nombreJ,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestParam("obs") String obs,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Juzgado juzg = new Juzgado();

        juzg.setNombre(nombre);
        juzg.setDenominacion(denominacion);
        juzg.setEspecialidad(especialidad);
        juzg.setDireccion(direccion);
        juzg.setDepartamento(departamento);
        juzg.setCorteSuperior(corteS);
        juzg.setDistritoJudicial(distritoJ);
        juzg.setNombreJuez(nombreJ);
        juzg.setTelefono(telefono);
        juzg.setCorreo(correo);
        juzg.setObservaciones(obs);

        ServicioPersonal.InsertJuzgado(juzg);

        String mensaje_log = "Se registró nuevo juzgado con Nombre, " + juzg.getNombre() + " y ID:" + String.valueOf(juzg.getIdjuzgado());
        String Tipo_registro = "Juzgado";

        //try{
        String Numero_registro = String.valueOf(juzg.getIdjuzgado());;

        ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);

        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg", map);
    }

    @RequestMapping(value = "/updateJuzgado", method = RequestMethod.POST)
    public ModelAndView UpdateJuzgado(ModelMap map,
            //datos a ingresar en Juzgado
            @RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("denominacion") String denominacion,
            @RequestParam("especialidad") String especialidad,
            @RequestParam("direccion") String direccion,
            @RequestParam("departamento") String departamento,
            @RequestParam("corteS") String corteS,
            @RequestParam("distritoJ") String distritoJ,
            @RequestParam("nombreJ") String nombreJ,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestParam("obs") String obs,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Juzgado juzg = new Juzgado();
        juzg = ServicioPersonal.getJuzgado(id);
        juzg.setNombre(nombre);
        juzg.setDenominacion(denominacion);
        juzg.setEspecialidad(especialidad);
        juzg.setDireccion(direccion);
        juzg.setDepartamento(departamento);
        juzg.setCorteSuperior(corteS);
        juzg.setDistritoJudicial(distritoJ);
        juzg.setNombreJuez(nombreJ);
        juzg.setTelefono(telefono);
        juzg.setCorreo(correo);
        juzg.setObservaciones(obs);

        ServicioPersonal.UpdateJuzgado(juzg);

        String mensaje_log = "Se registró nuevo juzgado con Nombre, " + juzg.getNombre() + " y ID:" + String.valueOf(juzg.getIdjuzgado());
        String Tipo_registro = "Juzgado";

        //try{
        String Numero_registro = String.valueOf(juzg.getIdjuzgado());;

        ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);

        map.put("listaJuzgados", ServicioPersonal.ListaJuzgado());
        return new ModelAndView("/Personal/registros/juzgado/lista_juzg", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR UA ////////////////////////////////////
    @RequestMapping(value = "/irEditarUa", method = RequestMethod.GET)
    public ModelAndView IrEditarUa(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        return new ModelAndView("/Personal/registros/ua/editar_ua", map);
    }

    @RequestMapping(value = "/irEditarUa2", method = RequestMethod.POST)
    public ModelAndView IrEditarUa2(ModelMap map, @RequestParam("id") int id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Unidad temp = new Unidad();
        temp = ServicioPersonal.getUa(id);
        map.put("ua", temp);

        return new ModelAndView("/Personal/registros/ua/editar_ua", map);
    }

    @RequestMapping(value = "/editUa", method = RequestMethod.POST)
    public ModelAndView EditarUa(ModelMap map,
            //datos a ingresar en UA
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("departamento") String departamento,
            @RequestParam("provincia") String provincia,
            @RequestParam("distrito") String distrito,
            @RequestParam("competenciaR") String competenciaR,
            @RequestParam("correo") String correo,
            @RequestParam("telefono") String telefono,
            @RequestParam("celular") String celular,
            @RequestParam("obs") String obs,
            HttpSession session
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Unidad ua = new Unidad();

        ua.setNombre(nombre);
        ua.setDireccion(direccion);
        ua.setDepartamento(departamento);
        ua.setProvincia(provincia);
        ua.setDistrito(distrito);
        ua.setCompetenciaRegional(competenciaR);
        ua.setCorreo(correo);
        ua.setTelefono(telefono);
        ua.setCelular(celular);
        ua.setObs(obs);

        ServicioPersonal.InsertUa(ua);

        String mensaje_log = "Se agregó nuevo UA con Nombre, " + ua.getNombre() + " y ID:" + String.valueOf(ua.getIdunidad());
        String Tipo_registro = "UA";

        try {
            String Numero_registro = String.valueOf(ua.getIdunidad());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/ua/lista_ua", map);
    }

    @RequestMapping(value = "/updateUa", method = RequestMethod.POST)
    public ModelAndView UpdateUa(ModelMap map,
            //datos a ingresar en UA
            @RequestParam("id") int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("departamento") String departamento,
            @RequestParam("provincia") String provincia,
            @RequestParam("distrito") String distrito,
            @RequestParam("competenciaR") String competenciaR,
            @RequestParam("correo") String correo,
            @RequestParam("telefono") String telefono,
            @RequestParam("celular") String celular,
            @RequestParam("obs") String obs,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Unidad ua = new Unidad();
        ua = ServicioPersonal.getUa(id);
        ua.setNombre(nombre);
        ua.setDireccion(direccion);
        ua.setDepartamento(departamento);
        ua.setProvincia(provincia);
        ua.setDistrito(distrito);
        ua.setCompetenciaRegional(competenciaR);
        ua.setCorreo(correo);
        ua.setTelefono(telefono);
        ua.setCelular(celular);
        ua.setObs(obs);

        ServicioPersonal.UpdateUa(ua);

        String mensaje_log = "Se editó el UA con Nombre, " + ua.getNombre() + " y ID:" + String.valueOf(ua.getIdunidad());
        String Tipo_registro = "UA";

        try {
            String Numero_registro = String.valueOf(ua.getIdunidad());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/ua/lista_ua", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////PERSONAL DE UA ///////////////////////////////////////////
    @RequestMapping(value = "/irListaPersonalUa", method = RequestMethod.POST)
    public ModelAndView ListaPersonalUa(ModelMap map, @RequestParam("ïdUA") int idUa, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("ua", ServicioPersonal.getUa(idUa));
        map.put("listaPersonalUa", ServicioPersonal.ListaPersonalUa(idUa));
        return new ModelAndView("/Personal/registros/ua/lista_ua_personal", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////EDITAR PERSONAL UA ///////////////////////////////////////////
    @RequestMapping(value = "/EditarPersonalUa", method = RequestMethod.POST)
    public ModelAndView EditarPersonalUa(ModelMap map, @RequestParam("id") int id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(id);
        String fechaNac = "";
        String fechaIng = "";
        try {
            fechaNac = format.dateToString(temp.getFechaNacimiento());
        } catch (Exception ex) {
        }
        try {
            fechaIng = format.dateToString(temp.getFechaIngreso());
        } catch (Exception ex) {
        }
        String noUa = "deshabilitar";
        map.addAttribute("fechaNac", fechaNac);
        map.addAttribute("fechaIng", fechaIng);
        map.addAttribute("disabled", noUa);
        map.put("personal", temp);
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/usuarios/editar_personal", map);
    }

    @RequestMapping(value = "/updatePersonalUa", method = RequestMethod.POST)
    public ModelAndView UpdatePersonalUa(ModelMap map,
            //datos a ingresar en Personal
            @RequestParam("idPers") long idPers,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidoP") String apellidoP,
            @RequestParam("apellidoM") String apellidoM,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("correoT") String correoT,
            @RequestParam("correoP") String correoP,
            @RequestParam("profesion") String profesion,
            @RequestParam("grado") String grado,
            @RequestParam("cargo") String cargo,
            @RequestParam("dni") String dni,
            @RequestParam("fechaNac") String fechaNac,
            @RequestParam("regimen") String regimen,
            @RequestParam("fechaIng") String fechaIng,
            @RequestParam("domicilio") String domicilio,
            @RequestParam("rol") String rol,
            HttpSession session
    //@RequestParam("ua") int ua
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(idPers);

        temp.setNombre(nombre);
        temp.setApellidoP(apellidoP);
        temp.setApellidoM(apellidoM);
        temp.setUser(user);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            temp.setPass(pass);
        }
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        Date tempfecha = temp.getFechaNacimiento();
        if (fechaNac != null) {
            if (fechaNac.contains("ene") || fechaNac.contains("feb") || fechaNac.contains("mar") || fechaNac.contains("abr")
                    || fechaNac.contains("may") || fechaNac.contains("jun") || fechaNac.contains("jul") || fechaNac.contains("ago")
                    || fechaNac.contains("set") || fechaNac.contains("oct") || fechaNac.contains("nov") || fechaNac.contains("dic")) {
                temp.setFechaNacimiento(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    temp.setFechaNacimiento(format.stringToDate(fechaNac));
                } else {
                    temp.setFechaNacimiento(null);
                }
            }
        } else {
            temp.setFechaNacimiento(null);
        }
        temp.setRegimen(regimen);
        tempfecha = temp.getFechaIngreso();
        if (fechaIng != null) {
            if (fechaIng.contains("ene") || fechaIng.contains("feb") || fechaIng.contains("mar") || fechaIng.contains("abr")
                    || fechaIng.contains("may") || fechaIng.contains("jun") || fechaIng.contains("jul") || fechaIng.contains("ago")
                    || fechaIng.contains("set") || fechaIng.contains("oct") || fechaIng.contains("nov") || fechaIng.contains("dic")) {
                temp.setFechaIngreso(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    temp.setFechaIngreso(format.stringToDate(fechaIng));
                } else {
                    temp.setFechaIngreso(null);
                }
            }
        } else {
            temp.setFechaIngreso(null);
        }
        temp.setDomicilio(domicilio);
        temp.setRol(rol);

        //Unidad temp2 = new Unidad();
        //temp2 = ServicioPersonal.getUa(ua);
        //temp.setUnidad(temp2);
        ServicioPersonal.UpdatePersonal(temp);

        String mensaje_log = "Se editó el personal con nombre " + temp.getNombre() + " " + temp.getApellidoP()
                + " " + temp.getApellidoM()
                + "con ID, " + temp.getIdpersonal();
        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(expedienteInt.getIdexpedienteFamilia());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("ua", ServicioPersonal.getUa(temp.getUnidad().getIdunidad()));
        map.put("listaPersonalUa", ServicioPersonal.ListaPersonalUa(temp.getUnidad().getIdunidad()));
        return new ModelAndView("/Personal/registros/ua/lista_ua_personal", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////ASIGNAR PERSONAL PASO 1 ///////////////////////////////////////////
    @RequestMapping(value = "/asignarPersonalUa", method = RequestMethod.POST)
    public ModelAndView asignarPersonalUa(ModelMap map, @RequestParam("idUa") long idUa, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("ua", ServicioPersonal.getUa(idUa));
        map.put("listaPersonalNoUa", ServicioPersonal.ListaPersonalNoUa(idUa));
        return new ModelAndView("/Personal/registros/ua/lista_asign_pers", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////ASIGNAR PERSONAL PASO 2 ///////////////////////////////////////////
    @RequestMapping(value = "/asignarPersonalUa2", method = RequestMethod.POST)
    public ModelAndView asignarPersonalUa2(ModelMap map, @RequestParam("idUa") long idUa, @RequestParam("idPers") long idPers, HttpSession session) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Personal per = new Personal();
        Unidad ua = new Unidad();
        ua = ServicioPersonal.getUa(idUa);
        per = ServicioPersonal.getPersonal(idPers);

        per.setUnidad(ua);
        ServicioPersonal.UpdatePersonal(per);

        String mensaje_log = "Se asignó nuevo personal con nombre " + per.getNombre() + " " + per.getApellidoP()
                + " " + per.getApellidoM()
                + "con ID, " + per.getIdpersonal() + ". A la Unidad de adopción: "
                + ua.getNombre() + " y ID: " + ua.getIdunidad();
        String Tipo_registro = "UA";

        try {
            String Numero_registro = String.valueOf(ua.getIdunidad());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("ua", ServicioPersonal.getUa(idUa));
        map.put("listaPersonalUa", ServicioPersonal.ListaPersonalUa(idUa));
        return new ModelAndView("/Personal/registros/ua/lista_ua_personal", map);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////// REGISTRAR/EDITAR PERSONAL ////////////////////////////////////
    @RequestMapping(value = "/irEditarPersonal", method = RequestMethod.GET)
    public ModelAndView IrEditarPersonal(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/usuarios/editar_personal", map);
    }

    @RequestMapping(value = "/irEditarPersonal2", method = RequestMethod.POST)
    public ModelAndView IrEditarPersonal2(ModelMap map, @RequestParam("id") long id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(id);
        String fechaNac = "";
        String fechaIng = "";
        if (temp.getFechaNacimiento() != null) {
            fechaNac = format.dateToString(temp.getFechaNacimiento());
        }
        if (temp.getFechaIngreso() != null) {
            fechaIng = format.dateToString(temp.getFechaIngreso());
        }
        map.addAttribute("fechaNac", fechaNac);
        map.addAttribute("fechaIng", fechaIng);
        map.put("personal", temp);
        map.put("listaUa", ServicioPersonal.ListaUa());
        return new ModelAndView("/Personal/registros/usuarios/editar_personal", map);
    }

    @RequestMapping(value = "/editPersonal", method = RequestMethod.POST)
    public ModelAndView EditarPersonal(ModelMap map,
            //datos a ingresar en Personal
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidoP") String apellidoP,
            @RequestParam("apellidoM") String apellidoM,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("correoT") String correoT,
            @RequestParam("correoP") String correoP,
            @RequestParam("profesion") String profesion,
            @RequestParam("grado") String grado,
            @RequestParam("cargo") String cargo,
            @RequestParam("dni") String dni,
            @RequestParam("fechaNac") String fechaNac,
            @RequestParam("regimen") String regimen,
            @RequestParam("fechaIng") String fechaIng,
            @RequestParam("domicilio") String domicilio,
            @RequestParam("rol") String rol,
            @RequestParam("ua") long ua,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Personal temp = new Personal();

        temp.setNombre(nombre);
        temp.setApellidoP(apellidoP);
        temp.setApellidoM(apellidoM);
        temp.setUser(user);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            temp.setPass(pass);
        }
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        Date tempfecha = temp.getFechaNacimiento();
        if (fechaNac != null) {
            if (fechaNac.contains("ene") || fechaNac.contains("feb") || fechaNac.contains("mar") || fechaNac.contains("abr")
                    || fechaNac.contains("may") || fechaNac.contains("jun") || fechaNac.contains("jul") || fechaNac.contains("ago")
                    || fechaNac.contains("set") || fechaNac.contains("oct") || fechaNac.contains("nov") || fechaNac.contains("dic")) {
                temp.setFechaNacimiento(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    temp.setFechaNacimiento(format.stringToDate(fechaNac));
                } else {
                    temp.setFechaNacimiento(null);
                }
            }
        } else {
            temp.setFechaNacimiento(null);
        }
        temp.setRegimen(regimen);
        tempfecha = temp.getFechaIngreso();
        if (fechaIng != null) {
            if (fechaIng.contains("ene") || fechaIng.contains("feb") || fechaIng.contains("mar") || fechaIng.contains("abr")
                    || fechaIng.contains("may") || fechaIng.contains("jun") || fechaIng.contains("jul") || fechaIng.contains("ago")
                    || fechaIng.contains("set") || fechaIng.contains("oct") || fechaIng.contains("nov") || fechaIng.contains("dic")) {
                temp.setFechaIngreso(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    temp.setFechaIngreso(format.stringToDate(fechaIng));
                } else {
                    temp.setFechaIngreso(null);
                }
            }
        } else {
            temp.setFechaIngreso(null);
        }
        temp.setDomicilio(domicilio);
        temp.setRol(rol);

        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);

        temp.setUnidad(temp2);

        if (ServicioPersonal.usuario(temp.getUser())) {

            String mensaje_error = "El nombre de usuario ya existe en la base de datos. Por favor, ingresar nuevamente los datos";

            map.put("mensaje", mensaje_error);
            map.put("listaUa", ServicioPersonal.ListaUa());
            return new ModelAndView("/Personal/registros/usuarios/editar_personal", map);
        }

        ServicioPersonal.InsertPersonal(temp);

        String mensaje_log = "Se creó nuevo usuario con nombre: " + temp.getNombre() + " " + temp.getApellidoP()
                + " con ID: " + temp.getIdpersonal();

        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(temp.getIdpersonal());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/registros/usuarios/lista_personal", map);
    }

    @RequestMapping(value = "/updatePersonal", method = RequestMethod.POST)
    public ModelAndView UpdatePersonal(ModelMap map,
            //datos a ingresar en Personal
            @RequestParam("idPers") long idPers,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidoP") String apellidoP,
            @RequestParam("apellidoM") String apellidoM,
            @RequestParam("user") String user,
            @RequestParam("pass") String pass,
            @RequestParam("correoT") String correoT,
            @RequestParam("correoP") String correoP,
            @RequestParam("profesion") String profesion,
            @RequestParam("grado") String grado,
            @RequestParam("cargo") String cargo,
            @RequestParam("dni") String dni,
            @RequestParam("fechaNac") String fechaNac,
            @RequestParam("regimen") String regimen,
            @RequestParam("fechaIng") String fechaIng,
            @RequestParam("domicilio") String domicilio,
            @RequestParam("rol") String rol,
            @RequestParam("ua") long ua,
            HttpSession session
    ) {

        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Personal temp = new Personal();
        temp = ServicioPersonal.getPersonal(idPers);

        temp.setNombre(nombre);
        temp.setApellidoP(apellidoP);
        temp.setApellidoM(apellidoM);
        temp.setUser(user);
        if (!pass.equals("")) {
            pass = DigestUtils.sha512Hex(pass);
            temp.setPass(pass);
        }
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        Date tempfecha = temp.getFechaNacimiento();
        if (fechaNac != null) {
            if (fechaNac.contains("ene") || fechaNac.contains("feb") || fechaNac.contains("mar") || fechaNac.contains("abr")
                    || fechaNac.contains("may") || fechaNac.contains("jun") || fechaNac.contains("jul") || fechaNac.contains("ago")
                    || fechaNac.contains("set") || fechaNac.contains("oct") || fechaNac.contains("nov") || fechaNac.contains("dic")) {
                temp.setFechaNacimiento(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    temp.setFechaNacimiento(format.stringToDate(fechaNac));
                } else {
                    temp.setFechaNacimiento(null);
                }
            }
        } else {
            temp.setFechaNacimiento(null);
        }
        temp.setRegimen(regimen);
        tempfecha = temp.getFechaIngreso();
        if (fechaIng != null) {
            if (fechaIng.contains("ene") || fechaIng.contains("feb") || fechaIng.contains("mar") || fechaIng.contains("abr")
                    || fechaIng.contains("may") || fechaIng.contains("jun") || fechaIng.contains("jul") || fechaIng.contains("ago")
                    || fechaIng.contains("set") || fechaIng.contains("oct") || fechaIng.contains("nov") || fechaIng.contains("dic")) {
                temp.setFechaIngreso(tempfecha);
            } else {
                if (!fechaNac.equals("")) {
                    temp.setFechaIngreso(format.stringToDate(fechaIng));
                } else {
                    temp.setFechaIngreso(null);
                }
            }
        } else {
            temp.setFechaIngreso(null);
        }
        temp.setDomicilio(domicilio);
        temp.setRol(rol);

        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);

        temp.setUnidad(temp2);

        ServicioPersonal.UpdatePersonal(temp);

        String mensaje_log = "Se editó el usuario con nombre: " + temp.getNombre() + " " + temp.getApellidoP()
                + " con ID: " + temp.getIdpersonal();

        String Tipo_registro = "Personal";

        try {
            String Numero_registro = String.valueOf(temp.getIdpersonal());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/registros/usuarios/lista_personal", map);
    }

    //// EDITAR SESION INFORMATIVA //////////////
    @RequestMapping(value = "/PersonalAgregarSesion", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarSesion(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //ArrayList<Personal> allPersonal = new ArrayList();
        //allPersonal = ServicioPersonal.ListaPersonal();
        //map.put("listaPersonal", allPersonal);
        return new ModelAndView("/Personal/Informativa/lista_sesion", map);
    }

    @RequestMapping(value = "/PersonalCrearSesion", method = RequestMethod.POST)
    public ModelAndView PersonalCrearSesion(ModelMap map,
            @RequestParam("numSesion") String numSesion,
            @RequestParam("fecha") String fecha,
            @RequestParam("hora") String hora,
            @RequestParam("duracion") String duracion,
            @RequestParam("direccion") String direccion,
            @RequestParam("capacitador") String capacitador,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Sesion tempSesion = new Sesion();

        tempSesion.setNSesion(numSesion);
        Date tempfecha = tempSesion.getFecha();
        if (fecha != null) {
            if (fecha.contains("ene") || fecha.contains("feb") || fecha.contains("mar") || fecha.contains("abr")
                    || fecha.contains("may") || fecha.contains("jun") || fecha.contains("jul") || fecha.contains("ago")
                    || fecha.contains("set") || fecha.contains("oct") || fecha.contains("nov") || fecha.contains("dic")) {
                tempSesion.setFecha(tempfecha);
            } else {
                if (!fecha.equals("")) {
                    tempSesion.setFecha(format.stringToDate(fecha));
                } else {
                    tempSesion.setFecha(null);
                }
            }
        } else {
            tempSesion.setFecha(null);
        }
        tempSesion.setHora(hora);
        short habilitado = Byte.valueOf("1");
        short inscritos = Byte.valueOf("0");
        tempSesion.setDuracion(duracion);
        tempSesion.setHabilitado(habilitado);
        tempSesion.setDireccion(direccion);
        tempSesion.setFacilitador(capacitador);
        tempSesion.setAsistencia(inscritos);

        ServicioPersonal.PersonalCrearSesion(tempSesion);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("listaTalleres", ServicioPersonal.listaTalleres());
        map.put("formato", format);
        return new ModelAndView("/Personal/Informativa/lista_charlas", map);
    }

    @RequestMapping(value = "/PersonalUpdateSesion", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateSesion(ModelMap map, @RequestParam("idSesion") long id,
            @RequestParam("numSesion") String numSesion,
            @RequestParam("fecha") String fecha,
            @RequestParam("hora") String hora,
            @RequestParam("duracion") String duracion,
            @RequestParam("direccion") String direccion,
            @RequestParam("capacitador") String capacitador,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Sesion tempSesion = ServicioPersonal.getSesion(id);
        tempSesion.setNSesion(numSesion);
        Date tempfecha = tempSesion.getFecha();
        if (fecha != null) {
            if (fecha.contains("ene") || fecha.contains("feb") || fecha.contains("mar") || fecha.contains("abr")
                    || fecha.contains("may") || fecha.contains("jun") || fecha.contains("jul") || fecha.contains("ago")
                    || fecha.contains("set") || fecha.contains("oct") || fecha.contains("nov") || fecha.contains("dic")) {
                tempSesion.setFecha(tempfecha);
            } else {
                if (!fecha.equals("")) {
                    tempSesion.setFecha(format.stringToDate(fecha));
                } else {
                    tempSesion.setFecha(null);
                }
            }
        } else {
            tempSesion.setFecha(null);
        }
        tempSesion.setHora(hora);
        tempSesion.setDuracion(duracion);
        tempSesion.setDireccion(direccion);
        tempSesion.setFacilitador(capacitador);

        ServicioPersonal.PersonalUpdateSesion(tempSesion);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("listaTalleres", ServicioPersonal.listaTalleres());
        map.put("formato", format);
        return new ModelAndView("/Personal/Informativa/lista_charlas", map);
    }

    @RequestMapping(value = "/PersonalHabilitarSesion", method = RequestMethod.POST)
    public ModelAndView PersonalHabilitarSesion(ModelMap map, @RequestParam("idSesion") long id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Sesion tempSesion = new Sesion();
        tempSesion = ServicioPersonal.getSesion(id);
        short habilitado = Byte.valueOf("0");
        tempSesion.setHabilitado(habilitado);

        ServicioPersonal.PersonalUpdateSesion(tempSesion);

        map.put("listaTalleres", ServicioPersonal.listaTalleres());
        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("formato", format);
        return new ModelAndView("/Personal/Informativa/lista_charlas", map);
    }

    @RequestMapping(value = "/PersonalEditarSesion", method = RequestMethod.POST)
    public ModelAndView PersonalEditarSesion(ModelMap map, @RequestParam("idSesion") long id, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Sesion temp = new Sesion();
        //ArrayList<Personal> allPersonal = new ArrayList();
        ArrayList<Turno> allTurnos = new ArrayList();
        allTurnos = ServicioMain.turnosSesion(id);
        temp = ServicioPersonal.getSesion(id);
        //allPersonal = ServicioPersonal.ListaPersonal();
        String fecha = "";
        try {
            fecha = format.dateToString(temp.getFecha());
        } catch (Exception ex) {
        }
        String hora = temp.getHora();

        map.put("listaTurnos", allTurnos);
        map.put("sesion", temp);
        //map.put("listaPersonal", allPersonal);
        map.addAttribute("ts", ts);
        map.addAttribute("fecha", fecha);
        map.addAttribute("hora", hora);
        return new ModelAndView("/Personal/Informativa/lista_sesion", map);
    }

    @RequestMapping(value = "/PersonalEditarTurno", method = RequestMethod.POST)
    public ModelAndView PersonalEditarTurno(ModelMap map, @RequestParam("idSesion") long idSesion, @RequestParam("index") int index, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Sesion tempSesion = new Sesion();
        tempSesion = ServicioPersonal.getSesion(idSesion);
        map.addAttribute("index", index);
        map.put("sesion", tempSesion);
        return new ModelAndView("/Personal/Informativa/editar_turno", map);
    }

    @RequestMapping(value = "/PersonalCrearTurno", method = RequestMethod.POST)
    public ModelAndView PersonalCrearTurno(ModelMap map, @RequestParam("idSesion") long idSesion,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("horaInicio") String horaInicio,
            @RequestParam("horaFin") String horaFin,
            @RequestParam("vacantes") String vacantes,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno temp = new Turno();
        temp.setSesion(ServicioPersonal.getSesion(idSesion));
        String comienzo = fechaInicio + " " + horaInicio;
        String fin = fechaFin + " " + horaFin;
        Short vac = Short.parseShort(vacantes);

        try {
            temp.setInicioInscripcion(ts.stringToTimestamp(comienzo));
        } catch (Exception ex) {
            temp.setInicioInscripcion(ts.stringToTimestamp(fechaInicio + " 00:00"));
        }
        try {
            temp.setFinInscripcion(ts.stringToTimestamp(fin));
        } catch (Exception ex) {
            temp.setFinInscripcion(ts.stringToTimestamp(fechaFin + " 00:00"));
        }
        temp.setVacantes(vac);

        ServicioPersonal.PersonalCrearTurno(temp);

        Sesion temp2 = new Sesion();
        //ArrayList<Personal> allPersonal = new ArrayList();
        ArrayList<Turno> allTurnos = new ArrayList();
        allTurnos = ServicioMain.turnosSesion(idSesion);
        temp2 = ServicioPersonal.getSesion(idSesion);
        //allPersonal = ServicioPersonal.ListaPersonal();
        String fecha = format.dateToString(temp2.getFecha());
        String hora = temp2.getHora();

        map.put("listaTurnos", allTurnos);
        map.put("sesion", temp2);
        //map.put("listaPersonal", allPersonal);
        map.addAttribute("ts", ts);
        map.addAttribute("fecha", fecha);
        map.addAttribute("hora", hora);
        return new ModelAndView("/Personal/Informativa/lista_sesion", map);
    }

    @RequestMapping(value = "/PersonalEditarTurno2", method = RequestMethod.POST)
    public ModelAndView PersonalEditarTurno2(ModelMap map, @RequestParam("idTurno") long idTurno, @RequestParam("index") int index, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Turno temp = new Turno();
        temp = ServicioMain.getTurno(idTurno);
        map.addAttribute("index", index);
        map.put("turno", temp);
        map.put("ts", ts);

        return new ModelAndView("/Personal/Informativa/editar_turno", map);
    }

    @RequestMapping(value = "/PersonalUpdateTurno", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateTurno(ModelMap map, @RequestParam("idTurno") long idTurno,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @RequestParam("horaInicio") String horaInicio,
            @RequestParam("horaFin") String horaFin,
            @RequestParam("vacantes") String vacantes,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno temp = new Turno();
        temp = ServicioMain.getTurno(idTurno);
        String comienzo = fechaInicio + " " + horaInicio;
        String fin = fechaFin + " " + horaFin;
        Short vac = Short.parseShort(vacantes);

        try {
            temp.setInicioInscripcion(ts.stringToTimestamp(comienzo));
        } catch (Exception ex) {
            temp.setInicioInscripcion(ts.stringToTimestamp(fechaInicio + " 00:00"));
        }
        try {
            temp.setFinInscripcion(ts.stringToTimestamp(fin));
        } catch (Exception ex) {
            temp.setFinInscripcion(ts.stringToTimestamp(fechaFin + " 00:00"));
        }
        temp.setVacantes(vac);

        ServicioPersonal.PersonalUpdateTurno(temp);

        Sesion temp2 = new Sesion();
        //ArrayList<Personal> allPersonal = new ArrayList();
        ArrayList<Turno> allTurnos = new ArrayList();
        allTurnos = ServicioMain.turnosSesion(temp.getSesion().getIdsesion());
        temp2 = ServicioPersonal.getSesion(temp.getSesion().getIdsesion());
        //allPersonal = ServicioPersonal.ListaPersonal();
        String fecha = format.dateToString(temp2.getFecha());
        String hora = temp2.getHora();

        map.put("listaTurnos", allTurnos);
        map.put("sesion", temp2);
        //map.put("listaPersonal", allPersonal);
        map.addAttribute("ts", ts);
        map.addAttribute("fecha", fecha);
        map.addAttribute("hora", hora);
        return new ModelAndView("/Personal/Informativa/lista_sesion", map);
    }

    @RequestMapping(value = "/PersonalInscritosSesion", method = RequestMethod.POST)
    public ModelAndView PersonalInscritosSesion(ModelMap map, @RequestParam("idSesion") long idSesion, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        allFormularios = ServicioPersonal.InscritosSesion(idSesion);

        map.put("listaFormularios", allFormularios);
        return new ModelAndView("/Personal/Informativa/lista_fam_ins", map);
    }

    @RequestMapping(value = "/PersonalInscritosTaller", method = RequestMethod.POST)
    public ModelAndView PersonalInscritosTaller(ModelMap map, @RequestParam("idTaller") long idTaller, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        allFormularios = ServicioPersonal.InscritosTaller(idTaller);

        map.put("listaFormularios", allFormularios);
        return new ModelAndView("/Personal/Informativa/lista_fam_ins", map);
    }

    @RequestMapping(value = "/PersonalDetalleFamiliaInscritaSesion", method = RequestMethod.POST)
    public ModelAndView PersonalDetalleFamiliaInscritaSesion(ModelMap map, @RequestParam("idFormulario") long idFormulario, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<Asistente> allAsistentes = new ArrayList();
        allAsistentes = ServicioPersonal.asistentePorFormulario(idFormulario);

        FormularioSesion fs = new FormularioSesion();
        fs = allAsistentes.get(0).getFormularioSesion();
        map.put("formato", format);
        map.put("listaAsistentes", allAsistentes);
        map.put("formulario", fs);
        return new ModelAndView("/Personal/Informativa/info_familia", map);
    }

    @RequestMapping(value = "/PersonalAgregarTaller", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarTaller(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ArrayList<Sesion> allSesiones = new ArrayList();
        allSesiones = ServicioPersonal.listaSesiones();
        map.put("listaSesiones", allSesiones);
        return new ModelAndView("/Personal/Informativa/edicion_taller", map);
    }

    @RequestMapping(value = "/PersonalCrearTaller", method = RequestMethod.POST)
    public ModelAndView PersonalCrearTaller(ModelMap map,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") String tipo,
            @RequestParam("numSesion") String numSesion,
            @RequestParam("habilitado") String habilitado,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Taller tempTaller = new Taller();

        tempTaller.setNombre(nombre);
        tempTaller.setTipoTaller(tipo);
        tempTaller.setNSesion(numSesion);
        Short habil = Short.parseShort(habilitado);
        Short numReu = Short.parseShort("0");
        tempTaller.setHabilitado(habil);
        tempTaller.setNReunion(numReu);
        ServicioPersonal.PersonalCrearTaller(tempTaller);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("listaTalleres", ServicioPersonal.listaTalleres());
        map.put("formato", format);

        return new ModelAndView("/Personal/Informativa/lista_charlas", map);
    }

    @RequestMapping(value = "/PersonalEditarTaller", method = RequestMethod.POST)
    public ModelAndView PersonalEditarTaller(ModelMap map, @RequestParam("idTaller") long idTaller, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Taller tempTaller = new Taller();
        tempTaller = ServicioPersonal.getTaller(idTaller);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("taller", tempTaller);
        return new ModelAndView("/Personal/Informativa/edicion_taller", map);
    }

    @RequestMapping(value = "/PersonalUpdateTaller", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateTaller(ModelMap map, @RequestParam("idTaller") long idTaller,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") String tipo,
            @RequestParam("numSesion") String numSesion,
            @RequestParam("habilitado") String habilitado,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Taller tempTaller = new Taller();
        tempTaller = ServicioPersonal.getTaller(idTaller);
        tempTaller.setNombre(nombre);
        tempTaller.setTipoTaller(tipo);
        tempTaller.setNSesion(numSesion);
        Short habil = Short.parseShort(habilitado);
        tempTaller.setHabilitado(habil);

        ServicioPersonal.PersonalUpdateTaller(tempTaller);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("listaTalleres", ServicioPersonal.listaTalleres());
        map.put("formato", format);

        return new ModelAndView("/Personal/Informativa/lista_charlas", map);
    }

    @RequestMapping(value = "/PersonalAgregarGrupo", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarGrupo(ModelMap map,
            @RequestParam("idTaller") long idTaller,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.addAttribute("idTaller", idTaller);
        return new ModelAndView("/Personal/Informativa/edicion_grupo", map);
    }

    @RequestMapping(value = "/PersonalCrearGrupo", method = RequestMethod.POST)
    public ModelAndView PersonalCrearGrupo(ModelMap map,
            @RequestParam("idTaller") long idTaller,
            @RequestParam("nombreGrupo") String nombreGrupo,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Grupo tempGrp = new Grupo();
        tempGrp.setTaller(ServicioPersonal.getTaller(idTaller));
        tempGrp.setNombre(nombreGrupo);
        ServicioPersonal.PersonalCrearGrupo(tempGrp);

        Taller tempTaller = new Taller();
        tempTaller = ServicioPersonal.getTaller(idTaller);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("taller", tempTaller);
        return new ModelAndView("/Personal/Informativa/edicion_taller", map);
    }

    @RequestMapping(value = "/PersonalEditarGrupo", method = RequestMethod.POST)
    public ModelAndView PersonalEditarGrupo(ModelMap map,
            @RequestParam("idGrupo") long idGrupo,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Grupo tempGrp = new Grupo();
        tempGrp = ServicioPersonal.getGrupo(idGrupo);

        map.put("grupo", tempGrp);
        return new ModelAndView("/Personal/Informativa/edicion_grupo", map);
    }

    @RequestMapping(value = "/PersonalUpdateGrupo", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateGrupo(ModelMap map,
            @RequestParam("idGrupo") long idGrupo,
            @RequestParam("nombreGrupo") String nombreGrupo,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Grupo tempGrp = new Grupo();
        tempGrp = ServicioPersonal.getGrupo(idGrupo);
        tempGrp.setNombre(nombreGrupo);
        ServicioPersonal.PersonalUpdateGrupo(tempGrp);

        map.put("taller", ServicioPersonal.getTaller(tempGrp.getTaller().getIdtaller()));
        return new ModelAndView("/Personal/Informativa/edicion_taller", map);
    }

    @RequestMapping(value = "/PersonalAgregarTurnoGrupo", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarTurnoGrupo(ModelMap map,
            @RequestParam("idGrupo") long idGrupo,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.addAttribute("formato", format);
        map.put("idGrupo", idGrupo);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/Informativa/edicion_turno2", map);
    }

    @RequestMapping(value = "/PersonalCrearTurno2", method = RequestMethod.POST)
    public ModelAndView PersonalCrearTurno2(ModelMap map,
            @RequestParam("idGrupo") long idGrupo,
            @RequestParam("nombreTurno2") String nombreTurno2,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno2 tempT2 = new Turno2();
        tempT2.setGrupo(ServicioPersonal.getGrupo(idGrupo));
        tempT2.setNombre(nombreTurno2);

        ServicioPersonal.PersonalCrearTurno2(tempT2);

        Grupo tempGrp = new Grupo();
        tempGrp = ServicioPersonal.getGrupo(idGrupo);

        map.put("grupo", tempGrp);
        return new ModelAndView("/Personal/Informativa/edicion_grupo", map);
    }

    @RequestMapping(value = "/PersonalEditarTurnoGrupo", method = RequestMethod.POST)
    public ModelAndView PersonalEditarTurnoGrupo(ModelMap map,
            @RequestParam("idTurno2") long idTurno2,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        Turno2 tempT2 = new Turno2();
        tempT2 = ServicioPersonal.getTurno2(idTurno2);
        map.put("turno2", tempT2);
        map.put("formato", format);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/Informativa/edicion_turno2", map);
    }

    @RequestMapping(value = "/PersonalUpdateTurno2", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateTurno2(ModelMap map,
            @RequestParam("idTurno2") long idTurno2,
            @RequestParam("nombreTurno2") String nombreTurno2,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno2 tempT2 = new Turno2();
        tempT2 = ServicioPersonal.getTurno2(idTurno2);
        tempT2.setNombre(nombreTurno2);
        ServicioPersonal.PersonalUpdateTurno2(tempT2);

        Grupo tempGrp = new Grupo();
        tempGrp = ServicioPersonal.getGrupo(tempT2.getGrupo().getIdgrupo());

        map.put("grupo", tempGrp);
        return new ModelAndView("/Personal/Informativa/edicion_grupo", map);
    }

    @RequestMapping(value = "/PersonalAgregarReunion", method = RequestMethod.POST)
    public ModelAndView PersonalAgregarReunion(ModelMap map,
            @RequestParam("idTurno2") long idTurno2,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno2 tempT2 = new Turno2();
        tempT2 = ServicioPersonal.getTurno2(idTurno2);

        map.put("turno2", tempT2);
        map.addAttribute("formato", format);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/Informativa/edicion_reunion", map);
    }

    @RequestMapping(value = "/PersonalEditarReunion", method = RequestMethod.POST)
    public ModelAndView PersonalEditarReunion(ModelMap map,
            @RequestParam("idReunion") long idReunion,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Reunion tempReun = new Reunion();
        tempReun = ServicioPersonal.getReunion(idReunion);
        String fecha = "";
        try {
            fecha = format.dateToString(tempReun.getFecha());
        } catch (Exception ex) {
        }

        map.addAttribute("fecha", fecha);
        map.put("reunion", tempReun);
        map.put("turno2", tempReun.getTurno2());
        map.addAttribute("formato", format);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/Informativa/edicion_reunion", map);
    }

    @RequestMapping(value = "/PersonalCrearReunion", method = RequestMethod.POST)
    public ModelAndView PersonalCrearReunion(ModelMap map,
            @RequestParam("idTurno2") long idTurno2,
            @RequestParam("fecha") String fecha,
            @RequestParam("hora") String hora,
            @RequestParam("duracion") String duracion,
            @RequestParam("direccion") String direccion,
            @RequestParam("capacidad") String capacidad,
            @RequestParam(value = "facilitador", required = false) String facilitador,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Turno2 tempTurno = new Turno2();
        tempTurno = ServicioPersonal.getTurno2(idTurno2);
        Short asistencia = Short.parseShort("0");
        Short identificador = Short.parseShort("0");

        Short capac = Short.parseShort(capacidad);

        Reunion tempReun = new Reunion();
        tempReun.setTurno2(tempTurno);
        Date tempfecha = tempReun.getFecha();
        if (fecha != null) {
            if (fecha.contains("ene") || fecha.contains("feb") || fecha.contains("mar") || fecha.contains("abr")
                    || fecha.contains("may") || fecha.contains("jun") || fecha.contains("jul") || fecha.contains("ago")
                    || fecha.contains("set") || fecha.contains("oct") || fecha.contains("nov") || fecha.contains("dic")) {
                tempReun.setFecha(tempfecha);
            } else {
                if (!fecha.equals("")) {
                    tempReun.setFecha(format.stringToDate(fecha));
                } else {
                    tempReun.setFecha(null);
                }
            }
        } else {
            tempReun.setFecha(null);
        }
        tempReun.setHora(hora);
        tempReun.setDuracion(duracion);
        tempReun.setDireccion(direccion);
        tempReun.setIdentificador(identificador);
        tempReun.setFacilitador(facilitador);
        tempReun.setCapacidad(capac);
        tempReun.setAsistencia(asistencia);

        ServicioPersonal.PersonalCrearReunion(tempReun);

        Turno2 tempT2 = new Turno2();
        tempT2 = ServicioPersonal.getTurno2(idTurno2);
        map.put("turno2", tempT2);
        map.put("formato", format);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/Informativa/edicion_turno2", map);
    }

    @RequestMapping(value = "/PersonalUpdateReunion", method = RequestMethod.POST)
    public ModelAndView PersonalUpdateReunion(ModelMap map,
            @RequestParam("idReunion") long idReunion,
            @RequestParam("fecha") String fecha,
            @RequestParam("hora") String hora,
            @RequestParam("duracion") String duracion,
            @RequestParam("direccion") String direccion,
            @RequestParam("capacidad") String capacidad,
            @RequestParam(value = "facilitador", required = false) String facilitador,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Reunion tempReun = new Reunion();
        tempReun = ServicioPersonal.getReunion(idReunion);

        Short capac = Short.parseShort(capacidad);
        Date tempfecha = tempReun.getFecha();
        if (fecha != null) {
            if (fecha.contains("ene") || fecha.contains("feb") || fecha.contains("mar") || fecha.contains("abr")
                    || fecha.contains("may") || fecha.contains("jun") || fecha.contains("jul") || fecha.contains("ago")
                    || fecha.contains("set") || fecha.contains("oct") || fecha.contains("nov") || fecha.contains("dic")) {
                tempReun.setFecha(tempfecha);
            } else {
                if (!fecha.equals("")) {
                    tempReun.setFecha(format.stringToDate(fecha));
                } else {
                    tempReun.setFecha(null);
                }
            }
        } else {
            tempReun.setFecha(null);
        }
        tempReun.setHora(hora);
        tempReun.setDuracion(duracion);
        tempReun.setDireccion(direccion);
        tempReun.setFacilitador(facilitador);
        tempReun.setCapacidad(capac);

        ServicioPersonal.PersonalUpdateReunion(tempReun);

        Turno2 tempT2 = new Turno2();
        tempT2 = ServicioPersonal.getTurno2(tempReun.getTurno2().getIdturno2());
        map.put("turno2", tempT2);
        map.put("formato", format);
        map.put("listaPersonal", ServicioPersonal.ListaPersonal());
        return new ModelAndView("/Personal/Informativa/edicion_turno2", map);
    }

    @RequestMapping(value = "/PersonalTomaAsistencia", method = RequestMethod.POST)
    public ModelAndView PersonalTomaAsistencia(ModelMap map,
            @RequestParam("idReunion") long idReunion,
            @RequestParam("nombre") String nombre,
            @RequestParam("grupo") String grupo,
            @RequestParam("turno") String turno,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Reunion tempReun = new Reunion();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        allFormularios = ServicioPersonal.formulariosReunion(idReunion);
        tempReun = ServicioPersonal.getReunion(idReunion);

        map.addAttribute("nombre", nombre);
        map.addAttribute("grupo", grupo);
        map.addAttribute("turno", turno);
        map.addAttribute("formato", format);
        map.put("listaFormularios", allFormularios);
        map.put("reunion", tempReun);
        return new ModelAndView("/Personal/Informativa/toma_asistencia", map);
    }

    @RequestMapping(value = "/PersonalTomaAsistencia2", method = RequestMethod.POST)
    public ModelAndView PersonalTomaAsistencia2(ModelMap map,
            @RequestParam("idSesion") long idSesion,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Sesion tempSesion = new Sesion();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        String fecha = "";
        tempSesion = ServicioPersonal.getSesion(idSesion);
        if (tempSesion.getFecha() != null) {
            fecha = format.dateToString(tempSesion.getFecha());
        }
        allFormularios = ServicioPersonal.InscritosSesion(idSesion);

        map.addAttribute("fecha", fecha);
        map.put("sesion", tempSesion);
        map.put("listaFormularios", allFormularios);
        return new ModelAndView("/Personal/Informativa/toma_asistencia2", map);
    }

    @RequestMapping(value = "/PersonalAsistioSesion", method = RequestMethod.POST)
    public ModelAndView PersonalAsistioSesion(ModelMap map,
            @RequestParam("idSesion") long idSesion,
            @RequestParam("idFormulario") long idFormulario,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        FormularioSesion fs = new FormularioSesion();
        fs = ServicioPersonal.getFormulario(idFormulario);
        Sesion tempSesion = new Sesion();
        Turno tempTurno = new Turno();

        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        String fecha = "";
        tempSesion = ServicioPersonal.getSesion(idSesion);
        if (tempSesion.getFecha() != null) {
            fecha = format.dateToString(tempSesion.getFecha());
        }

        tempTurno = ServicioPersonal.getTurno(tempSesion.getTurnos().iterator().next().getIdturno());
        ArrayList<AsistenciaFT> allAsistencias = new ArrayList();
        allAsistencias = ServicioPersonal.getAFT(idFormulario);
        if (!allAsistencias.isEmpty()) {
            for (AsistenciaFT asistenciaFT : allAsistencias) {
                String asistencia = "A";
                char c = asistencia.charAt(0);
                asistenciaFT.setAsistencia(c);
                ServicioPersonal.marcarAsistenciaSesion(asistenciaFT);
                int i = 1;
                short cont = tempSesion.getAsistencia();
                cont = (short) (cont + (short) (1));

                tempSesion.setAsistencia(cont);
                ServicioPersonal.PersonalUpdateSesion(tempSesion);
            }
        }

        allFormularios = ServicioPersonal.InscritosSesion(idSesion);

        map.addAttribute("fecha", fecha);
        map.put("sesion", tempSesion);
        map.put("listaFormularios", allFormularios);
        return new ModelAndView("/Personal/Informativa/toma_asistencia2", map);
    }

    @RequestMapping(value = "/PersonalCrearUsuarioFamilia", method = RequestMethod.POST)
    public ModelAndView PersonalCrearUsuarioFamilia(ModelMap map,
            @RequestParam("idSesion") long idSesion,
            @RequestParam("idFormulario") long idFormulario,
            @RequestParam("user") String user,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Familia fam = new Familia();
        FormularioSesion fs = new FormularioSesion();
        fs = ServicioPersonal.getFormulario(idFormulario);
        Sesion tempSesion = new Sesion();

        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        String fecha = "";
        tempSesion = ServicioPersonal.getSesion(idSesion);
        if (tempSesion.getFecha() != null) {
            fecha = format.dateToString(tempSesion.getFecha());
        }

        fam.setUser(user);
        String pass_plano = ServicioPersonal.generateUniqueToken(8);
        String pass = DigestUtils.sha512Hex(pass_plano);

        fam.setPass(pass);
        fam.setCorreo(user);
        Short habilitado = Short.parseShort("0");
        fam.setHabilitado(habilitado);

        ServicioPersonal.crearCuentaFamilia(fam, fs);
        hibermail.generateAndSendEmail2(user, pass_plano, user);

        allFormularios = ServicioPersonal.InscritosSesion(idSesion);

        map.addAttribute("fecha", fecha);
        map.put("sesion", tempSesion);
        map.put("listaFormularios", allFormularios);
        return new ModelAndView("/Personal/Informativa/toma_asistencia2", map);
    }

    @RequestMapping(value = "/PersonalAsistenciaReunion", method = RequestMethod.POST)
    public ModelAndView PersonalAsistenciaReunion(ModelMap map,
            @RequestParam("idReunion") long idReunion,
            @RequestParam("idFamilia") long idFamilia,
            @RequestParam("asistencia") String asistencia,
            @RequestParam("nombre") String nombre,
            @RequestParam("grupo") String grupo,
            @RequestParam("turno") String turno,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<AsistenciaFR> allAsistencias = new ArrayList();
        Reunion tempReun = new Reunion();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();

        tempReun = ServicioPersonal.getReunion(idReunion);
        allAsistencias = ServicioPersonal.getAsistFR(idFamilia, idReunion);
        for (AsistenciaFR asistFR : allAsistencias) {
            int i = 1;
            short cont = tempReun.getAsistencia();
            cont = (short) (cont + (short) (1));
            char c = asistencia.charAt(0);
            asistFR.setAsistencia(c);
            tempReun.setAsistencia(cont);
            ServicioPersonal.updateReunion(tempReun);
            ServicioPersonal.updateAsistenciaFR(asistFR);
        }

        allFormularios = ServicioPersonal.formulariosReunion(idReunion);
        map.addAttribute("nombre", nombre);
        map.addAttribute("grupo", grupo);
        map.addAttribute("turno", turno);
        map.addAttribute("formato", format);
        map.put("listaFormularios", allFormularios);
        map.put("reunion", tempReun);
        return new ModelAndView("/Personal/Informativa/toma_asistencia", map);
    }

    @RequestMapping(value = "/PersonalInasistenciaReunion", method = RequestMethod.POST)
    public ModelAndView PersonalInasistenciaReunion(ModelMap map,
            @RequestParam("idReunion") long idReunion,
            @RequestParam("idFamilia") long idFamilia,
            @RequestParam("justificado") String justificado,
            @RequestParam("nombre") String nombre,
            @RequestParam("grupo") String grupo,
            @RequestParam("turno") String turno,
            HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        ArrayList<AsistenciaFR> allAsistencias = new ArrayList();
        Reunion tempReun = new Reunion();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();

        tempReun = ServicioPersonal.getReunion(idReunion);

        allAsistencias = ServicioPersonal.getAsistFR(idFamilia, idReunion);
        for (AsistenciaFR asistFR : allAsistencias) {
            Short sh = Short.valueOf(justificado);
            asistFR.setInasJus(sh);
            ServicioPersonal.updateAsistenciaFR(asistFR);
        }

        allFormularios = ServicioPersonal.formulariosReunion(idReunion);
        map.addAttribute("nombre", nombre);
        map.addAttribute("grupo", grupo);
        map.addAttribute("turno", turno);
        map.addAttribute("formato", format);
        map.put("listaFormularios", allFormularios);
        map.put("reunion", tempReun);
        return new ModelAndView("/Personal/Informativa/toma_asistencia", map);
    }

    /*    FIN DE SESIONES Y TALLERES                      */
    /*    FAMILIAS INTERNACIONALES                        */
    @RequestMapping(value = "/DetallesFamInt", method = RequestMethod.POST)
    public ModelAndView DetallesFamInt(ModelMap map, HttpSession session, @RequestParam(value = "idExpediente") long idExpediente) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ExpedienteFamilia tempExp = ServicioMain.getInformacionRegistro(idExpediente);
        expedienteInt = tempExp;
        infoFam = ServicioMain.getInfoFamPorIdFamilia(tempExp.getFamilia().getIdfamilia());
        for (Adoptante adop : infoFam.getAdoptantes()) {
            if (adop.getSexo() == 'f') {
                Ella = adop;
            }
            if (adop.getSexo() == 'm') {
                El = adop;
            }
        }

        map.put("df", format);
        map.put("expediente", expedienteInt);
        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        return new ModelAndView("/Personal/fam_inter/datos_reg", map);
    }

    @RequestMapping(value = "/AgregarFamInt", method = RequestMethod.POST)
    public ModelAndView AgregarFamInt(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        map.put("listaEntidad", ServicioPersonal.ListaEntidades());
        return new ModelAndView("/Personal/fam_inter/datos_reg", map);
    }

    @RequestMapping(value = "/CrearRegistroInt", method = RequestMethod.POST)
    public ModelAndView CrearRegistroInt(ModelMap map, HttpSession session,
            @RequestParam(value = "ht") String ht,
            @RequestParam(value = "numeroExp") String numeroExp,
            @RequestParam(value = "fechaIngreso") String fechaIngreso,
            @RequestParam(value = "tupa") String tupa,
            @RequestParam(value = "tipoFamilia") String tipoFamilia,
            @RequestParam(value = "entAsoc") long entAsoc
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Familia tempFam = new Familia();
        ExpedienteFamilia expediente = new ExpedienteFamilia();
        Entidad tempEnt = ServicioPersonal.getEntidad(entAsoc);
        infoFam = new InfoFamilia();

        El = new Adoptante();
        Ella = new Adoptante();
        tempFam.setEntidad(tempEnt);
        tempFam.setHabilitado(Short.parseShort("1"));
        tempFam.setUser(usuario.getApellidoP()); //seteo el usuario por defecto como apellido paterno del usuario que lo registra
        String pass = DigestUtils.sha512Hex(usuario.getApellidoP());
        tempFam.setPass(pass);

        expediente.setHt(ht);
        expediente.setNumeroExpediente(numeroExp);
        if (fechaIngreso != null && !fechaIngreso.equals("")) {
            expediente.setFechaIngresoDga(format.stringToDate(fechaIngreso));
        }
        if (fechaIngreso == null && fechaIngreso.equals("")) {
            expediente.setFechaIngresoDga(null);
        }
        if (tupa != null && !tupa.equals("")) {
            expediente.setTupa(format.stringToDate(tupa));
        }
        if (tupa == null && tupa.equals("")) {
            expediente.setTupa(null);
        }
        expediente.setTipoFamilia(tipoFamilia);
        expediente.setUnidad(usuario.getUnidad());
        expediente.setEstado("evaluacion");
        expediente.setNacionalidad("internacional");
        expediente.setRnsa(Short.parseShort("0"));
        expediente.setRnaa(Short.parseShort("1"));

        ServicioPersonal.crearFamInt(tempFam, expediente, infoFam);
        expedienteInt = expediente;

        String mensaje_log = "Se creó nuevo expediente internacional con nombre: " + expedienteInt.getExpediente()
                + " con ID: " + expedienteInt.getIdexpedienteFamilia();

        String Tipo_registro = "Familia";

        try {
            String Numero_registro = String.valueOf(expedienteInt.getNumeroExpediente());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }
        //map.put("idInfo",infoFam.getIdinfoFamilia());
        map.put("infoFam", infoFam);
        map.put("Ella", Ella);
        return new ModelAndView("/Personal/fam_inter/datos_ella", map);
    }

    @RequestMapping(value = "/UpdateRegistroInt", method = RequestMethod.POST)
    public ModelAndView UpdateRegistroInt(ModelMap map, HttpSession session,
            @RequestParam(value = "ht") String ht,
            @RequestParam(value = "numeroExp") String numeroExp,
            @RequestParam(value = "fechaIngreso") String fechaIngreso,
            @RequestParam(value = "tupa") String tupa,
            @RequestParam(value = "tipoFamilia") String tipoFamilia,
            @RequestParam(value = "entAsoc") long entAsoc
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        Entidad tempEnt = ServicioPersonal.getEntidad(entAsoc);

        expedienteInt.getFamilia().setEntidad(tempEnt);
        expedienteInt.setHt(ht);
        expedienteInt.setNumeroExpediente(numeroExp);
        expedienteInt.setTipoFamilia(tipoFamilia);
        if (fechaIngreso != null && !fechaIngreso.equals("")) {
            expedienteInt.setFechaIngresoDga(format.stringToDate(fechaIngreso));
        }
        if (fechaIngreso == null && fechaIngreso.equals("")) {
            expedienteInt.setFechaIngresoDga(null);
        }
        if (tupa != null && !tupa.equals("")) {
            expedienteInt.setTupa(format.stringToDate(tupa));
        }
        if (tupa == null && tupa.equals("")) {
            expedienteInt.setTupa(null);
        }

        servicioEtapa.UpdateFamilia(expedienteInt.getFamilia());
        servicioEtapa.updateExpedienteFamilia(expedienteInt);

        String mensaje_log = "Se editó los datos del expediente "
                + "con familia con Usuario, " + expedienteInt.getFamilia().getUser() + " y ID de expediente:"
                + String.valueOf(expedienteInt.getIdexpedienteFamilia());
        String Tipo_registro = "Familia";

        try {
            String Numero_registro = String.valueOf(expedienteInt.getIdexpedienteFamilia());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("df", format);
        map.put("infoFam", infoFam);
        map.put("Ella", Ella);
        return new ModelAndView("/Personal/fam_inter/datos_ella", map);
    }

    @RequestMapping(value = "/ActualizarAdoptanteInt", method = RequestMethod.POST)
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
            @RequestParam(value = "numCel", required = false) String numCel,
            @RequestParam(value = "correo", required = false) String correo,
            @RequestParam(value = "estadoCivil", required = false) String estadoCivil,
            @RequestParam(value = "fechaMat", required = false) String fechaMat,
            @RequestParam(value = "nivelInstruccion", required = false) String nivelInstruccion,
            @RequestParam(value = "culminoNivel", required = false) String culminoNivel,
            @RequestParam(value = "profesion", required = false) String profesion,
            @RequestParam(value = "trabDep", required = false) String trabDep,
            @RequestParam(value = "ocupacionDep", required = false) String ocupacionDep,
            @RequestParam(value = "centroTrabajo", required = false) String centroTrabajo,
            @RequestParam(value = "direccionTrabajo", required = false) String direccionTrabajo,
            @RequestParam(value = "telefonoTrabajo", required = false) String telefonoTrabajo,
            @RequestParam(value = "ingresoDep", required = false) String ingresoDep,
            @RequestParam(value = "trabIndep", required = false) String trabIndep,
            @RequestParam(value = "ocupacionInd", required = false) String ocupacionInd,
            @RequestParam(value = "ingresoInd", required = false) String ingresoInd,
            @RequestParam(value = "seguroSalud", required = false) String seguroSalud,
            @RequestParam(value = "tipoSeguro", required = false) String tipoSeguro,
            @RequestParam(value = "seguroVida", required = false) String seguroVida,
            @RequestParam(value = "sisPensiones", required = false) String sisPensiones,
            @RequestParam(value = "estadoActual", required = false) String estadoActual
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        if (adoptante.equals("el")) {
            El.setInfoFamilia(infoFam);
            String genero = "m";
            char sexo = genero.charAt(0);
            El.setSexo(sexo);
            El.setNombre(nombre);
            El.setApellidoP(apellidoP);
            El.setApellidoM(apellidoM);
            if (fechaNac != null && !fechaNac.equals("")) {
                El.setFechaNac(format.stringToDate(fechaNac));
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
            El.setCelular(numCel);
            El.setCorreo(correo);
            infoFam.setEstadoCivil(estadoCivil);
            if (infoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(format.stringToDate(fechaMat));
            } else if (fechaMat == null || fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(null);
            }
            El.setNivelInstruccion(nivelInstruccion);
            if (culminoNivel != null && !culminoNivel.equals("")) {
                El.setCulminoNivel(Short.parseShort(culminoNivel));
            }
            El.setProfesion(profesion);
            /*Trabajo*/
            if (trabDep != null && !trabDep.equals("")) {
                El.setTrabajadorDepend(Short.parseShort(trabDep));
            } else {
                El.setTrabajadorDepend(null);
            }
            if (ocupacionDep != null && !ocupacionDep.equals("")) {
                El.setOcupActualDep(ocupacionDep);
            } else {
                El.setOcupActualDep(null);
            }
            if (centroTrabajo != null && !centroTrabajo.equals("")) {
                El.setCentroTrabajo(centroTrabajo);
            } else {
                El.setCentroTrabajo(null);
            }
            if (direccionTrabajo != null && !direccionTrabajo.equals("")) {
                El.setDireccionCentro(direccionTrabajo);
            } else {
                El.setDireccionCentro(null);
            }
            if (telefonoTrabajo != null && !telefonoTrabajo.equals("")) {
                El.setTelefonoCentro(telefonoTrabajo);
            } else {
                El.setTelefonoCentro(null);
            }
            if (ingresoDep != null && !ingresoDep.equals("")) {
                El.setIngresoDep(Long.parseLong(ingresoDep));
            } else {
                El.setIngresoDep(null);
            }
            if (trabIndep != null && !trabIndep.equals("")) {
                El.setTrabajadorIndepend(Short.parseShort(trabIndep));
            } else {
                El.setTrabajadorIndepend(null);
            }
            if (ocupacionInd != null && !ocupacionInd.equals("")) {
                El.setOcupActualInd(ocupacionInd);
            } else {
                El.setOcupActualInd(null);
            }

            if (ingresoInd != null && !ocupacionInd.equals("")) {
                El.setIngresoIndep(Long.parseLong(ingresoInd));
            } else {
                El.setIngresoIndep(null);
            }
            /*Fin Trabajo*/
            if (seguroSalud != null && !seguroSalud.equals("")) {
                El.setSeguroSalud(Short.parseShort(seguroSalud));
            }
            El.setTipoSeguro(tipoSeguro);
            if (seguroVida != null && !seguroVida.equals("")) {
                El.setSeguroVida(Short.parseShort(seguroVida));
            }
            if (sisPensiones != null && !sisPensiones.equals("")) {
                El.setSistPensiones(Short.parseShort(sisPensiones));
            }
            El.setSaludActual(estadoActual);

            ServicioMain.updateInfoFam(infoFam);
            ServicioPersonal.crearActualizarAdoptante(El);
            if (El.getApellidoP() != null && Ella.getApellidoP() != null) {
                expedienteInt.setExpediente(El.getApellidoP() + "-" + Ella.getApellidoP());
            } else if (El.getApellidoP() != null) {
                expedienteInt.setExpediente(El.getApellidoP());
            } else if (Ella.getApellidoP() != null) {
                expedienteInt.setExpediente(Ella.getApellidoP());
            }
            servicioEtapa.updateExpedienteFamilia(expedienteInt);

            String mensaje_log = "Se editó el Adoptante con nombre, " + El.getNombre() + " " + El.getApellidoP()
                    + " y ID:" + String.valueOf(El.getIdadoptante());
            String Tipo_registro = "Adoptante";

            try {
                String Numero_registro = String.valueOf(El.getIdadoptante());;

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            map.put("df", format);
            map.put("infoFam", infoFam);
            map.put("El", El);
            return new ModelAndView("/Personal/fam_inter/datos_el", map);
        } else {
            Ella.setInfoFamilia(infoFam);
            String genero = "f";
            char sexo = genero.charAt(0);
            Ella.setSexo(sexo);
            Ella.setNombre(nombre);
            Ella.setApellidoP(apellidoP);
            Ella.setApellidoM(apellidoM);
            if (fechaNac != null && !fechaNac.equals("")) {
                Ella.setFechaNac(format.stringToDate(fechaNac));
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
            Ella.setCelular(numCel);
            Ella.setCorreo(correo);
            infoFam.setEstadoCivil(estadoCivil);
            if (infoFam.getEstadoCivil().equals("casados") && fechaMat != null && !fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(format.stringToDate(fechaMat));
            } else if (fechaMat == null || fechaMat.equals("")) {
                infoFam.setFechaMatrimonio(null);
            }
            Ella.setNivelInstruccion(nivelInstruccion);
            if (culminoNivel != null && !culminoNivel.equals("")) {
                Ella.setCulminoNivel(Short.parseShort(culminoNivel));
            }
            Ella.setProfesion(profesion);
            /*Trabajo*/
            if (trabDep != null && !trabDep.equals("")) {
                Ella.setTrabajadorDepend(Short.parseShort(trabDep));
            } else {
                Ella.setTrabajadorDepend(null);
            }
            if (ocupacionDep != null && !ocupacionDep.equals("")) {
                Ella.setOcupActualDep(ocupacionDep);
            } else {
                Ella.setOcupActualDep(null);
            }
            if (centroTrabajo != null && !centroTrabajo.equals("")) {
                Ella.setCentroTrabajo(centroTrabajo);
            } else {
                Ella.setCentroTrabajo(null);
            }
            if (direccionTrabajo != null && !direccionTrabajo.equals("")) {
                Ella.setDireccionCentro(direccionTrabajo);
            } else {
                Ella.setDireccionCentro(null);
            }
            if (telefonoTrabajo != null && !telefonoTrabajo.equals("")) {
                Ella.setTelefonoCentro(telefonoTrabajo);
            } else {
                Ella.setTelefonoCentro(null);
            }
            if (ingresoDep != null && !ingresoDep.equals("")) {
                Ella.setIngresoDep(Long.parseLong(ingresoDep));
            } else {
                Ella.setIngresoDep(null);
            }
            if (trabIndep != null && !trabIndep.equals("")) {
                Ella.setTrabajadorIndepend(Short.parseShort(trabIndep));
            } else {
                Ella.setTrabajadorIndepend(null);
            }
            if (ocupacionInd != null && !ocupacionInd.equals("")) {
                Ella.setOcupActualInd(ocupacionInd);
            } else {
                Ella.setOcupActualInd(null);
            }

            if (ingresoInd != null && !ocupacionInd.equals("")) {
                Ella.setIngresoIndep(Long.parseLong(ingresoInd));
            } else {
                Ella.setIngresoIndep(null);
            }
            /*Fin Trabajo*/
            if (seguroSalud != null && !seguroSalud.equals("")) {
                Ella.setSeguroSalud(Short.parseShort(seguroSalud));
            }
            Ella.setTipoSeguro(tipoSeguro);
            if (seguroVida != null && !seguroVida.equals("")) {
                Ella.setSeguroVida(Short.parseShort(seguroVida));
            }
            if (sisPensiones != null && !sisPensiones.equals("")) {
                Ella.setSistPensiones(Short.parseShort(sisPensiones));
            }
            Ella.setSaludActual(estadoActual);

            ServicioPersonal.crearActualizarAdoptante(Ella);
            ServicioMain.updateInfoFam(infoFam);
            if (El.getApellidoP() != null && Ella.getApellidoP() != null) {
                expedienteInt.setExpediente(El.getApellidoP() + "-" + Ella.getApellidoP());
            } else if (El.getApellidoP() != null) {
                expedienteInt.setExpediente(El.getApellidoP());
            } else if (Ella.getApellidoP() != null) {
                expedienteInt.setExpediente(Ella.getApellidoP());
            }
            servicioEtapa.updateExpedienteFamilia(expedienteInt);

            String mensaje_log = "Se editó la Adoptante con nombre, " + Ella.getNombre() + " " + Ella.getApellidoP()
                    + " y ID:" + String.valueOf(Ella.getIdadoptante());
            String Tipo_registro = "Adoptante";

            try {
                String Numero_registro = String.valueOf(Ella.getIdadoptante());;

                ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
            } catch (Exception ex) {
            }

            map.put("df", format);
            map.put("infoFam", infoFam);
            map.put("Ella", Ella);
            return new ModelAndView("/Personal/fam_inter/datos_ella", map);

        }

    }

    @RequestMapping(value = "/VerInfoRegInt", method = RequestMethod.POST)
    public ModelAndView VerInfoRegInt(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //map.put("listaAutoridades", ServicioPersonal.ListaAutoridades());
        map.put("df", format);
        map.put("infoFam", infoFam);
        map.put("Ella", Ella);
        return new ModelAndView("/Personal/fam_inter/datos_ella", map);
    }

    @RequestMapping(value = "/laSolicitanteInt", method = RequestMethod.GET)
    public ModelAndView laSolicitanteInt(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("df", format);
        map.put("infoFam", infoFam);
        map.put("Ella", Ella);
        return new ModelAndView("/Personal/fam_inter/datos_ella", map);
    }

    @RequestMapping(value = "/elSolicitanteInt", method = RequestMethod.GET)
    public ModelAndView elSolicitanteInt(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        map.put("df", format);
        map.put("infoFam", infoFam);
        map.put("El", El);
        return new ModelAndView("/Personal/fam_inter/datos_el", map);
    }

    @RequestMapping(value = "/antNnaInt", method = RequestMethod.GET)
    public ModelAndView antNnaInt(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        //map.put("listaAutoridades", ServicioPersonal.ListaAutoridades());
        map.put("infoFam", infoFam);
        return new ModelAndView("/Personal/fam_inter/datos_nna", map);
    }

    @RequestMapping(value = "/ActualizarInfoFamiliaInt", method = RequestMethod.POST)
    public ModelAndView ActualizarInfoFamiliaInt(ModelMap map, HttpSession session,
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
            @RequestParam(value = "obs") String obs
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
        if (obs != null && !obs.equals("")) {
            infoFam.setObservaciones(obs);
        }

        ServicioMain.updateInfoFam(infoFam);

        String mensaje_log = "Se editó la información de Familia internacional con usuario, "
                + infoFam.getFamilia().getUser() + " y ID, " + infoFam.getFamilia().getIdfamilia();

        String Tipo_registro = "Familia";

        try {
            String Numero_registro = String.valueOf(infoFam.getFamilia().getIdfamilia());;

            ServicioPersonal.InsertLog(usuario, Tipo_registro, Numero_registro, mensaje_log);
        } catch (Exception ex) {
        }

        map.put("df", format);
        map.put("infoFam", infoFam);
        return new ModelAndView("/Personal/fam_inter/datos_nna", map);
    }

    @RequestMapping("/Pcambiarcontra")
    public ModelAndView Pcambiarcontra(ModelMap map, HttpSession session, @RequestParam("oldpass") String oldpass, @RequestParam("newpass") String newpass, @RequestParam("newpassconf") String newpassconf) {
        Personal usuario = (Personal) session.getAttribute("usuario");
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
                    ServicioPersonal.CambiaPass(usuario);
                    mensaje = "La contraseña se ha cambiado con exito.";
                } else {
                    mensaje = "Las contraseñas no coinciden. Favor de reescribir la nueva contraseña.";
                }
            } else {
                mensaje = "Contraseña de usuario incorrecta. Ingrese nuevamente.";
            }
        }
        String pagina = "/Personal/actualizar_pass";
        map.addAttribute("mensaje", mensaje);
        return new ModelAndView(pagina, map);
    }

    @RequestMapping(value = "/FiltrarFam", method = RequestMethod.POST)
    public ModelAndView FiltrarFam(ModelMap map, HttpSession session,
            @RequestParam(value = "expediente", required = false) String expediente,
            @RequestParam(value = "HT", required = false) String HT,
            @RequestParam(value = "nacionalidad", required = false) String nacionalidad,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "tipofamilia", required = false) String tipofamilia
    ) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }

        ExpedienteFamilia datosExp = new ExpedienteFamilia();
        Familia datosFam = new Familia();

        if (expediente != null) {
            datosExp.setExpediente(expediente);
        }
        if (expediente == null) {
            datosExp.setExpediente("none");
        }
        if (HT != null) {
            datosExp.setHt(HT);
        }
        if (HT == null) {
            datosExp.setHt("none");
        }

        if (nacionalidad != null && !nacionalidad.equals("none")) {
            datosExp.setNacionalidad(nacionalidad);
        }
        if (nacionalidad == null || nacionalidad.equals("none")) {
            datosExp.setNacionalidad("none");
        }
        if (estado != null && !estado.equals("none")) {
            datosExp.setEstado(estado);
        }
        if (estado == null || estado.equals("none")) {
            datosExp.setEstado("none");
        }
        if (tipofamilia != null && !tipofamilia.equals("none")) {
            datosExp.setTipoFamilia(tipofamilia);
        }
        if (tipofamilia == null || tipofamilia.equals("none")) {
            datosExp.setTipoFamilia("none");
        }

        ArrayList<ExpedienteFamilia> listaBusqueda = new ArrayList();
        listaBusqueda = ServicioPersonal.FiltrarFam(datosExp, datosFam);

        map.put("listaBusqueda", listaBusqueda);
        return new ModelAndView("/Personal/Buscador/buscarFamilia", map);

    }

    @RequestMapping(value = "/logParticular", method = RequestMethod.POST)
    public ModelAndView logParticular(ModelMap map, HttpSession session, @RequestParam("id") Long idpersonal, @RequestParam("dia") String dia) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        Personal personal = new Personal();

        personal = ServicioPersonal.getPersonal(idpersonal);

        map.put("listaParticularLog", ServicioPersonal.getLogParticularPorDia(idpersonal, dia));
        map.put("user", personal);
        map.put("dia", dia);

        return new ModelAndView("/Personal/registros/usuarios/log_particular", map);
    }

    @RequestMapping(value = "/logParticularFiltroHoy", method = RequestMethod.POST)
    public ModelAndView logParticularFiltroHoy(ModelMap map, HttpSession session, @RequestParam("id") Long idpersonal) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        Date diatemp = new Date();
        String dia = String.valueOf(diatemp.getDate()) + "/" + String.valueOf(diatemp.getMonth() + 1) + "/" + String.valueOf(diatemp.getYear() + 1900);
        Personal personal = new Personal();

        personal = ServicioPersonal.getPersonal(idpersonal);

        map.put("listaParticularLog", ServicioPersonal.getLogParticularPorDia(idpersonal, dia));
        map.put("user", personal);
        map.put("dia", dia);
        return new ModelAndView("/Personal/registros/usuarios/log_particular", map);
    }

    @RequestMapping(value = "/logPersonalFiltro", method = RequestMethod.POST)
    public ModelAndView logPersonalFiltro(ModelMap map, HttpSession session, @RequestParam("dia") String dia) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();

        map.put("listaPersonalLog", ServicioPersonal.getLogPersonalPorDia(dia));
        map.put("dia", dia);

        return new ModelAndView("/Personal/registros/usuarios/log_personal", map);
    }

    @RequestMapping(value = "/logPersonalFiltroHoy", method = RequestMethod.POST)
    public ModelAndView logPersonalFiltroHoy(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        //List<Personal> lista = Servicio.listaPersonal();
        Date diatemp = new Date();
        String dia = String.valueOf(diatemp.getDate()) + "/" + String.valueOf(diatemp.getMonth() + 1) + "/" + String.valueOf(diatemp.getYear() + 1900);

        map.put("listaPersonalLog", ServicioPersonal.getLogPersonalPorDia(dia));
        map.put("dia", dia);

        return new ModelAndView("/Personal/registros/usuarios/log_personal", map);

    }

}
