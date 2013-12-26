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
        map.put("listaNna",ServicioNna.ListaNna("regular"));
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
        map.put("listaNna",ServicioNna.ListaNna("prioritario"));
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
        
        map.put("listaNna",ServicioNna.ListaNna("seguimiento"));
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
        
        map.put("listaJuzgados",ServicioPersonal.ListaJuzgado());
        map.put("listaCar",ServicioPersonal.ListaCar());
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
        
        map.put("nna",tempNna);
        map.put("listaJuzgados",ServicioPersonal.ListaJuzgado());
        map.put("listaCar",ServicioPersonal.ListaCar());
        return new ModelAndView("/Personal/nna/editar_nna", map);
    }
    
    @RequestMapping(value = "/crearNna", method = RequestMethod.POST)
    public ModelAndView crearNna(ModelMap map, HttpSession session,
                                 @RequestParam("origen") String origen,
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
        if(nn.isEmpty()){ 
            tempNn = Byte.valueOf("1");
        }else{
            tempNn = Byte.valueOf("0");
        }
        tempNna.setNn(tempNn);
        
        tempNna.setNombre(nombre);
        tempNna.setApellidoP(apellidoP);
        tempNna.setApellidoM(apellidoM);
        tempNna.setSexo(sexo);
        
        tempNna.setFechaNacimiento(df.stringToDate(fechaNac));
        short edadtemp = Byte.valueOf(edad);
        short mesestemp = Byte.valueOf(meses);
        tempNna.setEdadAnhos(edadtemp);
        tempNna.setEdadMeses(mesestemp);
        
        tempNna.setJuzgado(ServicioPersonal.getJuzgado(idJuzgado));
        tempNna.setCar(ServicioPersonal.getCar(idCar));
        
        short acta;
        if(actaNac.equals("s")){ 
            acta = Byte.valueOf("0");
        }else{
            acta = Byte.valueOf("1");
        }
        tempNna.setActaNacimiento(acta);
        
        tempNna.setDepartamentoNacimiento(dep);
        tempNna.setProvinciaNacimiento(prov);
        tempNna.setDistritoNacimiento(dist);
        tempNna.setDistritoNacimiento(direccion);
        tempNna.setNResolAband(numResolAband);
        tempNna.setFechaResolAbandono(df.stringToDate(fechaResolAband));
        tempNna.setNResolCons(numResolConsen);
        tempNna.setFechaResolConsentida(df.stringToDate(fechaResolConsen));
        
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
        tempNna.setSeguiMedico(shEstable);
        tempNna.setOperacion(shIntervencion);
        tempNna.setHiperactivo(shTrastorno);
       
        short especial = Byte.valueOf("1");
        short enfermo = Byte.valueOf("1");
        short mayores = Byte.valueOf("1");
        short adolescentes = Byte.valueOf("1");
        short hermanos = Byte.valueOf("1");
        
        if(prioritarios.equals("e")){
            especial = Byte.valueOf("0");
        }else if(prioritarios.equals("s")){
            enfermo = Byte.valueOf("0");                      
        }else if (prioritarios.equals("m")){
            mayores = Byte.valueOf("0");
        }else if (prioritarios.equals("a")){
            adolescentes = Byte.valueOf("0");
        }else if (prioritarios.equals("h")){
            hermanos = Byte.valueOf("0");
        }
        
        
        tempNna.setEspecial(especial);
        tempNna.setEnfermo(enfermo);
        tempNna.setMayor(mayores);
        tempNna.setAdolescente(adolescentes);
        tempNna.setHermano(hermanos);
        
        if(!prioritarios.isEmpty()){
            tempNna.setClasificacion("prioritario");
        }else{
            tempNna.setClasificacion("regular");
        }
        tempNna.setObservaciones(obs);
        
        ServicioNna.crearNna(tempNna);
        
        if(!prioritarios.isEmpty()){
            map.put("listaNna",ServicioNna.ListaNna("prioritario"));
            return new ModelAndView("/Personal/nna/lista_nna_prior", map);
        }else{
            map.put("listaNna",ServicioNna.ListaNna("regular"));
            return new ModelAndView("/Personal/nna/lista_nna", map);
        }
        
        
    }
    
    
    
}
