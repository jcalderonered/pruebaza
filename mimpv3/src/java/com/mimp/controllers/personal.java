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

    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();
    @Resource(name = "HiberMain")
    private HiberMain ServicioMain = new HiberMain();

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
        map.put("listaTalleres",ServicioPersonal.listaTalleres());
        map.put("formato",format);
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
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
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
        //List<Personal> lista = Servicio.listaPersonal();
        //map.addAttribute("id", temp);
        return new ModelAndView("/Personal/inicio_personal", map);
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
        //FALTA
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
        String fechaEmision = format.dateToString(temp.getEntidad().getFechaResol());
        String fechaRenov = format.dateToString(temp.getEntidad().getFechaRenov());
        String fechaVenc = format.dateToString(temp.getEntidad().getFechaVenc());
        map.addAttribute("fechaEmision", fechaEmision);
        map.addAttribute("fechaRenov", fechaRenov);
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
        ent.setFechaResol(format.stringToDate(fecha_emis_resol));
        ent.setResolRenov(resol_renov);
        ent.setFechaRenov(format.stringToDate(fecha_renov));
        ent.setFechaVenc(format.stringToDate(fecha_venc_aut));
        ent.setObs(obs);
        ent.setUser(user);
        ent.setPass(pass);
        aut.setTipo(tipo);

        ServicioPersonal.InsertAut(ent, aut);

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
        temp.getEntidad().setPass(pass);
        temp.getEntidad().setDireccion(direccion);
        temp.getEntidad().setTelefono(telefono);
        temp.getEntidad().setResolAuto(resol_aut);
        temp.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
        temp.getEntidad().setResolRenov(resol_renov);
        temp.getEntidad().setFechaRenov(format.stringToDate(fecha_renov));
        temp.getEntidad().setFechaVenc(format.stringToDate(fecha_venc_aut));
        temp.getEntidad().setObs(obs);
        ServicioPersonal.UpdateAut(temp.getEntidad(), temp);
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
        String fechaEmision = format.dateToString(temp.getEntidad().getFechaResol());
        String fechaRenov = format.dateToString(temp.getEntidad().getFechaRenov());
        String fechaVenc = format.dateToString(temp.getEntidad().getFechaVenc());

        String fechaAutR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaAuto());
        String fechaRenovR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaRenov());
        String fechaVencR = format.dateToString(temp.getRepresentantes().iterator().next().getFechaVencAuto());

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
        rep.setFechaAuto(format.stringToDate(fechaAutR));
        rep.setFechaRenov(format.stringToDate(fechaRenovR));
        rep.setFechaVencAuto(format.stringToDate(fechaVencR));
        rep.setCorreo(correo);
        rep.setDireccion(direccionR);
        rep.setCelular(celular);
        rep.setObs(obsR);

        ent.setNombre(nombre);
        ent.setDireccion(direccion);
        ent.setTelefono(telefono);
        ent.setPais(pais);
        ent.setUser(user);
        ent.setPass(pass);
        ent.setResolAuto(resol_aut);
        ent.setFechaResol(format.stringToDate(fecha_emis_resol));
        ent.setResolRenov(resol_renov);
        ent.setFechaRenov(format.stringToDate(fecha_renov));
        ent.setFechaVenc(format.stringToDate(fecha_venc_aut));
        ent.setObs(obs);

        ServicioPersonal.InsertOrg(ent, rep, org);

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

        org.getRepresentantes().iterator().next().setFechaAuto(format.stringToDate(fechaAutR));
        org.getRepresentantes().iterator().next().setFechaRenov(format.stringToDate(fechaRenovR));
        org.getRepresentantes().iterator().next().setFechaVencAuto(format.stringToDate(fechaVencR));
        org.getRepresentantes().iterator().next().setCorreo(correo);
        org.getRepresentantes().iterator().next().setDireccion(direccionR);
        org.getRepresentantes().iterator().next().setCelular(celular);
        org.getRepresentantes().iterator().next().setObs(obsR);

        org.getEntidad().setNombre(nombre);
        org.getEntidad().setUser(user);
        org.getEntidad().setPass(pass);
        org.getEntidad().setDireccion(direccion);
        org.getEntidad().setTelefono(telefono);
        org.getEntidad().setPais(pais);
        org.getEntidad().setResolAuto(resol_aut);
        org.getEntidad().setFechaResol(format.stringToDate(fecha_emis_resol));
        org.getEntidad().setResolRenov(resol_renov);
        org.getEntidad().setFechaRenov(format.stringToDate(fecha_renov));
        org.getEntidad().setFechaVenc(format.stringToDate(fecha_venc_aut));
        org.getEntidad().setObs(obs);

        ServicioPersonal.UpdateOrg(org.getEntidad(), org.getRepresentantes().iterator().next(), org);

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
        String fechaNac = format.dateToString(temp.getFechaNacimiento());
        String fechaIng = format.dateToString(temp.getFechaIngreso());
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
        temp.setPass(pass);
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        temp.setFechaNacimiento(format.stringToDate(fechaNac));
        temp.setRegimen(regimen);
        temp.setFechaIngreso(format.stringToDate(fechaIng));
        temp.setDomicilio(domicilio);
        temp.setRol(rol);

        //Unidad temp2 = new Unidad();
        //temp2 = ServicioPersonal.getUa(ua);
        //temp.setUnidad(temp2);
        ServicioPersonal.UpdatePersonal(temp);
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
        pass = DigestUtils.sha512Hex(pass);
        temp.setPass(pass);
        temp.setCorreoTrabajo(correoT);
        temp.setCorreoPersonal(correoP);
        temp.setProfesion(profesion);
        temp.setGradoInstruccion(grado);
        temp.setCargo(cargo);
        temp.setDni(dni);
        temp.setFechaNacimiento(format.stringToDate(fechaNac));
        temp.setRegimen(regimen);
        temp.setFechaIngreso(format.stringToDate(fechaIng));
        temp.setDomicilio(domicilio);
        temp.setRol(rol);

        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);

        temp.setUnidad(temp2);

        ServicioPersonal.InsertPersonal(temp);

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
        temp.setFechaNacimiento(format.stringToDate(fechaNac));
        temp.setRegimen(regimen);
        temp.setFechaIngreso(format.stringToDate(fechaIng));
        temp.setDomicilio(domicilio);
        temp.setRol(rol);

        Unidad temp2 = new Unidad();
        temp2 = ServicioPersonal.getUa(ua);

        temp.setUnidad(temp2);

        ServicioPersonal.UpdatePersonal(temp);

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
        tempSesion.setFecha(format.stringToDate(fecha));
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
        map.put("listaTalleres",ServicioPersonal.listaTalleres());
        map.put("formato",format);
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
        if(fecha.contains("ene") || fecha.contains("feb") || fecha.contains("mar") || fecha.contains("abr") ||
                fecha.contains("may") || fecha.contains("jun") || fecha.contains("jul") || fecha.contains("ago") 
                || fecha.contains("set") || fecha.contains("oct") || fecha.contains("nov") || fecha.contains("dic")){
            tempSesion.setFecha(tempfecha);
        } else {
            tempSesion.setFecha(format.stringToDate(fecha));
        }
        tempSesion.setHora(hora);
        tempSesion.setDuracion(duracion);
        tempSesion.setDireccion(direccion);
        tempSesion.setFacilitador(capacitador);

        ServicioPersonal.PersonalUpdateSesion(tempSesion);

        map.put("listaSesiones", ServicioPersonal.listaSesiones());
        map.put("listaTalleres",ServicioPersonal.listaTalleres());
        map.put("formato",format);
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
        
        map.put("listaSesiones", ServicioPersonal.listaSesiones());
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
        String fecha = format.dateToString(temp.getFecha());
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

        temp.setInicioInscripcion(ts.stringToTimestamp(comienzo));
        temp.setFinInscripcion(ts.stringToTimestamp(fin));
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

        temp.setInicioInscripcion(ts.stringToTimestamp(comienzo));
        temp.setFinInscripcion(ts.stringToTimestamp(fin));
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
        map.put("formato",format);
        map.put("listaAsistentes", allAsistentes);
        map.put("formulario",fs);
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
        map.put("listaTalleres",ServicioPersonal.listaTalleres());
        map.put("formato",format);
        
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
        map.put("listaTalleres",ServicioPersonal.listaTalleres());
        map.put("formato",format);
        
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
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
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
        map.put("formato",format);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
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
        
        map.put("turno2",tempT2);
        map.addAttribute("formato", format);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
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
        String fecha = format.dateToString(tempReun.getFecha());
        
        map.addAttribute("fecha", fecha);
        map.put("reunion",tempReun);
        map.put("turno2",tempReun.getTurno2());
        map.addAttribute("formato", format);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
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
                                             @RequestParam("facilitador") String facilitador,
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
        tempReun.setFecha(format.stringToDate(fecha));
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
        map.put("formato",format);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
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
                                             @RequestParam("facilitador") String facilitador,
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
        tempReun.setFecha(format.stringToDate(fecha));
        tempReun.setHora(hora);
        tempReun.setDuracion(duracion);
        tempReun.setDireccion(direccion);
        tempReun.setFacilitador(facilitador);
        tempReun.setCapacidad(capac);
        
        ServicioPersonal.PersonalUpdateReunion(tempReun);
        
        Turno2 tempT2 = new Turno2();
        tempT2 = ServicioPersonal.getTurno2(tempReun.getTurno2().getIdturno2());
        map.put("turno2", tempT2);
        map.put("formato",format);
        map.put("listaPersonal",ServicioPersonal.ListaPersonal());
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
        if (tempSesion.getFecha() != null){
             fecha = format.dateToString(tempSesion.getFecha());
        }
        allFormularios = ServicioPersonal.InscritosSesion(idSesion);
        
        map.addAttribute("fecha", fecha);
        map.put("sesion",tempSesion );
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
        
        AsistenciaFT aft = new AsistenciaFT();
        FormularioSesion fs = new FormularioSesion();
        fs = ServicioPersonal.getFormulario(idFormulario);
        Sesion tempSesion = new Sesion();
        Turno tempTurno = new Turno();
        
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        String fecha = "";
        tempSesion = ServicioPersonal.getSesion(idSesion);
        if (tempSesion.getFecha() != null){
             fecha = format.dateToString(tempSesion.getFecha());
        }
        
        tempTurno = ServicioPersonal.getTurno(tempSesion.getTurnos().iterator().next().getIdturno());
       
        aft = ServicioPersonal.getAFT(idFormulario);
        String asistencia = "A";
        char c = asistencia.charAt(0);
        aft.setAsistencia(c);
        ServicioPersonal.marcarAsistenciaSesion(aft);
        
        allFormularios = ServicioPersonal.InscritosSesion(idSesion);
        
        map.addAttribute("fecha", fecha);
        map.put("sesion",tempSesion );
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
        if (tempSesion.getFecha() != null){
             fecha = format.dateToString(tempSesion.getFecha());
        }
        
        fam.setUser(user);
        fam.setPass("12345");
        fam.setCorreo(user);
        Short habilitado = Short.parseShort("0");
        fam.setHabilitado(habilitado);
        ServicioPersonal.crearCuentaFamilia(fam, fs);
        
        allFormularios = ServicioPersonal.InscritosSesion(idSesion);
        
        map.addAttribute("fecha", fecha);
        map.put("sesion",tempSesion );
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
        AsistenciaFR afr = new AsistenciaFR();
        Reunion tempReun = new Reunion();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        
        tempReun = ServicioPersonal.getReunion(idReunion);
        
        afr = ServicioPersonal.getAsistFR(idFamilia, idReunion);
        char c = asistencia.charAt(0);
        afr.setAsistencia(c);
        
        ServicioPersonal.updateAsistenciaFR(afr);
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
        AsistenciaFR afr = new AsistenciaFR();
        Reunion tempReun = new Reunion();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        
        tempReun = ServicioPersonal.getReunion(idReunion);
        
        afr = ServicioPersonal.getAsistFR(idFamilia, idReunion);
        Short sh = Short.valueOf(justificado);
        afr.setInasJus(sh);
        
        ServicioPersonal.updateAsistenciaFR(afr);
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
}
