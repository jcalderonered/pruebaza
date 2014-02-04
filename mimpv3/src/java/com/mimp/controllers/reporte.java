/*
 * To change this license header, choose License Headers in Project 
 Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.controllers;

import java.util.*;
import com.mimp.bean.*;
import com.mimp.hibernate.HiberEtapa;
import com.mimp.util.*;
import com.mimp.hibernate.HiberNna;
import com.mimp.hibernate.HiberPersonal;
import com.mimp.hibernate.HiberReporte;
import java.io.FileInputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
public class reporte {

    dateFormat format = new dateFormat();

    @Resource(name = "HiberReporte")
    private HiberReporte ServicioReporte = new HiberReporte();

    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();

    @Resource(name = "HiberEtapa")
    private HiberEtapa ServicioEtapa = new HiberEtapa();

    @Resource(name = "HiberNna")
    private HiberNna ServicioNna = new HiberNna();

    @RequestMapping(value = "/reporte", method = RequestMethod.GET)
    public ModelAndView PagReporte(ModelMap map, HttpSession session) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        if (usuario == null) {
            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
            map.addAttribute("mensaje", mensaje);
            return new ModelAndView("login", map);
        }
        return new ModelAndView("/Personal/reporte", map);
    }

    @RequestMapping("/Reportes/OrganismosAcreditados")
    public void ReporteOrganismo(ModelMap map, HttpSession session,
            HttpServletResponse response) {
        Personal usuario = (Personal) session.getAttribute("usuario");
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\8 Registro del número Organismos Acreditados.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Organismo> listaorg = ServicioPersonal.ListaOrganismos();

            int i = 1;
            for (Organismo org : listaorg) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(org.getEntidad().getNombre());
                cell = row.createCell(2);
                cell.setCellValue(org.getCompetencia());
                cell = row.createCell(3);
                cell.setCellValue(org.getEntidad().getResolAuto());
                cell = row.createCell(4);
                String fechaVenc = "";
                try {
                    fechaVenc = format.dateToString(org.getEntidad().getFechaVenc());
                } catch (Exception ex) {
                }
                cell.setCellValue(fechaVenc);
                cell = row.createCell(5);
                for (Iterator iter = org.getRepresentantes().iterator();
                        iter.hasNext();) {
                    Representante rep = (Representante) iter.next();
                    cell.setCellValue(rep.getNombre() + " "
                            + rep.getApellidoP());
                }
                cell = row.createCell(6);
                cell.setCellValue(org.getEntidad().getObs());

                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro del número Organismos Acreditados.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/PostAdopcion")
    public void ReportePostAdopcion(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\7 Registros Post-Adopción.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<PostAdopcion> listapost = ServicioReporte.ReportePostAdopcion();

            int i = 3;
            for (PostAdopcion post : listapost) {
                Row row = sheet.createRow(i);

                Nna nna = ServicioNna.getNna(post.getidNna());

                Cell cell = row.createCell(0);
                cell.setCellValue(i-2);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                //En caso de hermanos, ambos tendrán el mismo nombre de expediente
                if(!post.getFamilia().getExpedienteFamilias().isEmpty()){
                    for (Iterator iter = post.getFamilia().getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                    }
                }
                cell.setCellValue(exp.getExpediente());
                cell = row.createCell(2);
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
                if(!post.getFamilia().getInfoFamilias().isEmpty()){
                for (Iterator iter2 = post.getFamilia().getInfoFamilias().iterator(); iter2.hasNext();) {
                    InfoFamilia ifam = (InfoFamilia) iter2.next();
                    for (Iterator iter3 = ifam.getAdoptantes().iterator();
                            iter3.hasNext();) {
                        Adoptante adop = (Adoptante) iter3.next();
                        if (adop.getSexo() == 'm') {
                            el = adop;
                        } else {
                            ella = adop;
                        }
                    }
                }
                }
                cell.setCellValue(el.getNombre());
                cell = row.createCell(3);
                cell.setCellValue(el.getApellidoP());
                cell = row.createCell(4);
                cell.setCellValue(el.getApellidoM());
                cell = row.createCell(5);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - el.getFechaNac().getYear();
                if ((el.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((el.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((el.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(6);
                cell.setCellValue(el.getPaisNac());
                cell = row.createCell(7);
                cell.setCellValue(ella.getNombre());
                cell = row.createCell(8);
                cell.setCellValue(ella.getApellidoP());
                cell = row.createCell(9);
                cell.setCellValue(ella.getApellidoM());
                cell = row.createCell(10);
                edad = añoAct - ella.getFechaNac().getYear();
                if ((ella.getFechaNac().getMonth() - fechaAct.getMonth()) > 0) {
                    edad--;
                } else if ((ella.getFechaNac().getMonth() - fechaAct.getMonth()) == 0) {
                    if ((ella.getFechaNac().getDate() - fechaAct.getDate()) > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(11);
                cell.setCellValue(ella.getPaisNac());
                
                cell = row.createCell(12);
                cell.setCellValue(nna.getNombre() + " " + nna.getApellidoP()); //NNA nombre
                cell = row.createCell(13);
                cell.setCellValue(nna.getSexo());
                cell = row.createCell(14);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(15);
                String fecha = "";
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                if(!post.getFamilia().getInfoFamilias().isEmpty()){
                    cell = row.createCell(16);
                    cell.setCellValue(post.getFamilia().getInfoFamilias().iterator().next().getPaisRes());
                    cell = row.createCell(17);
                    cell.setCellValue(post.getFamilia().getInfoFamilias().iterator().next().getDepRes());
                }
                
                cell = row.createCell(18);
                cell.setCellValue(nna.getClasificacion());
                cell = row.createCell(19);
                Resolucion resol = new Resolucion();
                if(!exp.getEvaluacions().isEmpty()){
                for (Iterator iter5 = exp.getEvaluacions().iterator();
                        iter5.hasNext();) {
                    Evaluacion eval = (Evaluacion) iter5.next();
                    if(!eval.getResolucions().isEmpty()){
                    for (Iterator iter6 = eval.getResolucions().iterator(); iter6.hasNext();) {
                        resol = (Resolucion) iter6.next();
                        if (resol.getTipo().equals("adopcion")) {
                            break;
                            }
                        }
                    }
                }
                }
                cell.setCellValue(resol.getNumero());
                cell = row.createCell(20);
                if (post.getFamilia() != null && post.getFamilia().getEntidad() != null) cell.setCellValue(post.getFamilia().getEntidad().getNombre());
                
                Designacion desig = new Designacion();
                desig = ServicioReporte.getDesigNnaFam( nna.getIdnna(), exp.getIdexpedienteFamilia());
                
                cell = row.createCell(21);
                cell.setCellValue(desig.getTipoPropuesta());
                cell = row.createCell(22);
                cell.setCellValue(nna.getClasificacion());
                cell = row.createCell(23);
                if (exp.getUnidad() != null) cell.setCellValue(exp.getUnidad().getNombre());
                cell = row.createCell(24);
                if (post.getNumeroInformes() != null) cell.setCellValue(post.getNumeroInformes());
                cell = row.createCell(25);
                try {
                    fecha = format.dateToString(post.getFechaResolucion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                int index = 26;
                int numinf = 0;
                fecha = "";
                if(!post.getInformePostAdoptivos().isEmpty()){
                for (Iterator iter7 = post.getInformePostAdoptivos().iterator(); iter7.hasNext();) {
                    InformePostAdoptivo inf = (InformePostAdoptivo) iter7.next();
                    numinf++;

                    cell = row.createCell(index);
                    try {
                        fecha = format.dateToString(inf.getFechaRecepcionProyectado());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    index++;
                    cell = row.createCell(index);
                    try {
                        fecha = format.dateToString(inf.getFechaRecepcion());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    index++;
                    cell = row.createCell(index);
                    if (inf.getPersonal() != null) cell.setCellValue(inf.getPersonal().getNombre() + " "
                            + inf.getPersonal().getApellidoP());
                    index++;
                    cell = row.createCell(index);
                    try {
                        fecha = format.dateToString(inf.getFechaInforme());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    index++;
                    cell = row.createCell(index);
                    cell.setCellValue(inf.getEstado());
                    index++;
                    if (numinf == post.getNumeroInformes().intValue()) {
                        cell = row.createCell(66); //Ultima celda de la fila
                        try {
                            fecha = format.dateToString(inf.getFechaActa());
                        } catch (Exception ex) {
                        }
                        cell.setCellValue(fecha);
                        fecha = "";
                    }
                }
                }
                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registros Post-Adopción.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR + ARREGLAR
    @RequestMapping("/Reportes/NNAs")
    public void ReporteNNAs(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\1 Registro Nacional de NNAs declarados Judicialmente en Abandono.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Nna> listanna = ServicioReporte.ListaNnaReporte("prioritario");

            int i = 2;
            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                try {
                    fecha = format.dateToString(exp.getFechaIngreso());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(2);
                if(exp.getHt() != null){
                cell.setCellValue(exp.getHt());
                }
                cell = row.createCell(3);
                if(exp.getNExpTutelar() != null){
                cell.setCellValue(exp.getNExpTutelar());
                }
                cell = row.createCell(4);
                cell.setCellValue(nna.getNombre() + " "
                        + nna.getApellidoP());
                cell = row.createCell(5);
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - nna.getFechaNacimiento().getYear();
                int meses = 0;
                //Revisar cálculo de meses
                if ((nna.getFechaNacimiento().getMonth()
                        - fechaAct.getMonth()) > 0) {
                    meses = nna.getFechaNacimiento().getMonth()
                            - fechaAct.getMonth();
                    edad--;
                } else if ((nna.getFechaNacimiento().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((nna.getFechaNacimiento().getDate()
                            - fechaAct.getDate()) > 0) {
                        edad--;
                    } else {
                        meses = 1;
                    }
                } else {
                    meses = fechaAct.getMonth() - nna.getFechaNacimiento().getMonth();
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getSexo());
                cell = row.createCell(9);
                if (exp.getUnidad()!=null){
                cell.setCellValue(exp.getUnidad().getNombre());
                }
                cell = row.createCell(10);
                if (nna.getActaNacimiento() != null && nna.getActaNacimiento() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(11);
                if (nna.getIncesto() != null && nna.getIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(12);
                if (nna.getMental() != null && nna.getMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(13);
                if (nna.getEpilepsia() !=null && nna.getEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(14);
                if (nna.getAbuso() != null && nna.getAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(15);
                if (nna.getSifilis() != null && nna.getSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(16);
                if (nna.getSeguiMedico() != null && nna.getSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(17);
                if (nna.getOperacion() != null && nna.getOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(18);
                if (nna.getHiperactivo() != null && nna.getHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(19);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(20);
                cell.setCellValue(exp.getProcTutelar());
                cell = row.createCell(21);
                if(nna.getJuzgado() != null){
                cell.setCellValue(nna.getJuzgado().getNombre());
                }
                cell = row.createCell(22);
                if(nna.getJuzgado() != null){
                cell.setCellValue(nna.getJuzgado().getDistritoJudicial());
                }
                cell = row.createCell(23);
                if(nna.getCar() != null) {
                cell.setCellValue(nna.getCar().getNombre());
                }
                cell = row.createCell(24);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDireccion());
                }
                cell = row.createCell(25);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDepartamento());
                }
                cell = row.createCell(26);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getProvincia());
                }
                cell = row.createCell(27);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDistrito());
                }
                cell = row.createCell(28);
                try {
                    fecha = format.dateToString(nna.getFechaResolAbandono());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(29);
                try {
                    fecha = format.dateToString(nna.getFechaResolConsentida());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(30);
                if (exp.getFichaIntegral() != null && exp.getFichaIntegral() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(32);
                if(exp.getRespLegalNombre() != null && exp.getRespLegalP() != null){
                cell.setCellValue(exp.getRespLegalNombre() + " "
                        + exp.getRespLegalP());
                }
                cell = row.createCell(33);
                if(exp.getRespPsicosocialNombre() != null && exp.getRespPiscosocialP() != null){
                cell.setCellValue(exp.getRespPsicosocialNombre() + " "
                        + exp.getRespPiscosocialP());
                }
                cell = row.createCell(34);
                if(exp.getEstado() != null){
                cell.setCellValue(exp.getEstado());
                }
                cell = row.createCell(35);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter = nna.getDesignacions().iterator();
                        iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if(daux.getFechaConsejo() != null && !daux.getFechaConsejo().equals("")){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                if(desig.getFechaConsejo() != null && !desig.getFechaConsejo().equals("")){
                cell.setCellValue(format.dateToString(desig.getFechaConsejo()));
                }else{
                cell.setCellValue("");
                }
                i++;
            }

            listanna = ServicioReporte.ListaNnaReporte("regular");

            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                try {
                    fecha = format.dateToString(exp.getFechaIngreso());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(2);
                if(exp.getHt() != null){
                cell.setCellValue(exp.getHt());
                }
                cell = row.createCell(3);
                if(exp.getNExpTutelar() != null){
                cell.setCellValue(exp.getNExpTutelar());
                }
                cell = row.createCell(4);
                cell.setCellValue(nna.getNombre() + " "
                        + nna.getApellidoP());
                cell = row.createCell(5);
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - nna.getFechaNacimiento().getYear();
                int meses = 0;
                //Revisar cálculo de meses
                if ((nna.getFechaNacimiento().getMonth()
                        - fechaAct.getMonth()) > 0) {
                    meses = nna.getFechaNacimiento().getMonth()
                            - fechaAct.getMonth();
                    edad--;
                } else if ((nna.getFechaNacimiento().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((nna.getFechaNacimiento().getDate()
                            - fechaAct.getDate()) > 0) {
                        edad--;
                    } else {
                        meses = 1;
                    }
                } else {
                    meses = fechaAct.getMonth() - nna.getFechaNacimiento().getMonth();
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getSexo());
                cell = row.createCell(9);
                if (exp.getUnidad()!=null){
                cell.setCellValue(exp.getUnidad().getNombre());
                }
                cell = row.createCell(10);
                if (nna.getActaNacimiento() != null && nna.getActaNacimiento() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(11);
                if (nna.getIncesto() != null && nna.getIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(12);
                if (nna.getMental() != null && nna.getMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(13);
                if (nna.getEpilepsia() !=null && nna.getEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(14);
                if (nna.getAbuso() != null && nna.getAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(15);
                if (nna.getSifilis() != null && nna.getSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(16);
                if (nna.getSeguiMedico() != null && nna.getSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(17);
                if (nna.getOperacion() != null && nna.getOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(18);
                if (nna.getHiperactivo() != null && nna.getHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(19);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(20);
                cell.setCellValue(exp.getProcTutelar());
                cell = row.createCell(21);
                if(nna.getJuzgado() != null){
                cell.setCellValue(nna.getJuzgado().getNombre());
                }
                cell = row.createCell(22);
                if(nna.getJuzgado() != null){
                cell.setCellValue(nna.getJuzgado().getDistritoJudicial());
                }
                cell = row.createCell(23);
                if(nna.getCar() != null) {
                cell.setCellValue(nna.getCar().getNombre());
                }
                cell = row.createCell(24);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDireccion());
                }
                cell = row.createCell(25);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDepartamento());
                }
                cell = row.createCell(26);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getProvincia());
                }
                cell = row.createCell(27);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDistrito());
                }
                cell = row.createCell(28);
                try {
                    fecha = format.dateToString(nna.getFechaResolAbandono());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(29);
                try {
                    fecha = format.dateToString(nna.getFechaResolConsentida());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(30);
                if (exp.getFichaIntegral() != null && exp.getFichaIntegral() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(32);
                if(exp.getRespLegalNombre() != null && exp.getRespLegalP() != null){
                cell.setCellValue(exp.getRespLegalNombre() + " "
                        + exp.getRespLegalP());
                }
                cell = row.createCell(33);
                if(exp.getRespPsicosocialNombre() != null && exp.getRespPiscosocialP() != null){
                cell.setCellValue(exp.getRespPsicosocialNombre() + " "
                        + exp.getRespPiscosocialP());
                }
                cell = row.createCell(34);
                if(exp.getEstado() != null){
                cell.setCellValue(exp.getEstado());
                }
                cell = row.createCell(35);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter = nna.getDesignacions().iterator();
                        iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if(daux.getFechaConsejo() != null && !daux.getFechaConsejo().equals("")){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                if(desig.getFechaConsejo() != null && !desig.getFechaConsejo().equals("")){
                cell.setCellValue(format.dateToString(desig.getFechaConsejo()));
                }else{
                cell.setCellValue("---");
                }
                i++;
            }

            listanna = ServicioReporte.ListaNnaReporte("seguimiento");

            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                try {
                    fecha = format.dateToString(exp.getFechaIngreso());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(2);
                if(exp.getHt() != null){
                cell.setCellValue(exp.getHt());
                }
                cell = row.createCell(3);
                if(exp.getNExpTutelar() != null){
                cell.setCellValue(exp.getNExpTutelar());
                }
                cell = row.createCell(4);
                cell.setCellValue(nna.getNombre() + " "
                        + nna.getApellidoP());
                cell = row.createCell(5);
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - nna.getFechaNacimiento().getYear();
                int meses = 0;
                //Revisar cálculo de meses
                if ((nna.getFechaNacimiento().getMonth()
                        - fechaAct.getMonth()) > 0) {
                    meses = nna.getFechaNacimiento().getMonth()
                            - fechaAct.getMonth();
                    edad--;
                } else if ((nna.getFechaNacimiento().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((nna.getFechaNacimiento().getDate()
                            - fechaAct.getDate()) > 0) {
                        edad--;
                    } else {
                        meses = 1;
                    }
                } else {
                    meses = fechaAct.getMonth() - nna.getFechaNacimiento().getMonth();
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getSexo());
                cell = row.createCell(9);
                if (exp.getUnidad()!=null){
                cell.setCellValue(exp.getUnidad().getNombre());
                }
                cell = row.createCell(10);
                if (nna.getActaNacimiento() != null && nna.getActaNacimiento() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(11);
                if (nna.getIncesto() != null && nna.getIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(12);
                if (nna.getMental() != null && nna.getMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(13);
                if (nna.getEpilepsia() !=null && nna.getEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(14);
                if (nna.getAbuso() != null && nna.getAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(15);
                if (nna.getSifilis() != null && nna.getSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(16);
                if (nna.getSeguiMedico() != null && nna.getSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(17);
                if (nna.getOperacion() != null && nna.getOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(18);
                if (nna.getHiperactivo() != null && nna.getHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(19);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(20);
                cell.setCellValue(exp.getProcTutelar());
                cell = row.createCell(21);
                if(nna.getJuzgado() != null){
                cell.setCellValue(nna.getJuzgado().getNombre());
                }
                cell = row.createCell(22);
                if(nna.getJuzgado() != null){
                cell.setCellValue(nna.getJuzgado().getDistritoJudicial());
                }
                cell = row.createCell(23);
                if(nna.getCar() != null) {
                cell.setCellValue(nna.getCar().getNombre());
                }
                cell = row.createCell(24);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDireccion());
                }
                cell = row.createCell(25);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDepartamento());
                }
                cell = row.createCell(26);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getProvincia());
                }
                cell = row.createCell(27);
                if(nna.getCar() != null){
                cell.setCellValue(nna.getCar().getDistrito());
                }
                cell = row.createCell(28);
                try {
                    fecha = format.dateToString(nna.getFechaResolAbandono());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(29);
                try {
                    fecha = format.dateToString(nna.getFechaResolConsentida());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(30);
                if (exp.getFichaIntegral() != null && exp.getFichaIntegral() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(32);
                if(exp.getRespLegalNombre() != null && exp.getRespLegalP() != null){
                cell.setCellValue(exp.getRespLegalNombre() + " "
                        + exp.getRespLegalP());
                }
                cell = row.createCell(33);
                if(exp.getRespPsicosocialNombre() != null && exp.getRespPiscosocialP() != null){
                cell.setCellValue(exp.getRespPsicosocialNombre() + " "
                        + exp.getRespPiscosocialP());
                }
                cell = row.createCell(34);
                if(exp.getEstado() != null){
                cell.setCellValue(exp.getEstado());
                }
                cell = row.createCell(35);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter = nna.getDesignacions().iterator();
                        iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if(daux.getFechaConsejo() != null && !daux.getFechaConsejo().equals("")){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                if(desig.getFechaConsejo() != null && !desig.getFechaConsejo().equals("")){
                cell.setCellValue(format.dateToString(desig.getFechaConsejo()));
                }else{
                cell.setCellValue("---");
                }
                i++;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro Nacional de NNAs declarados Judicialmente en Abandono.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/NNAPrioritarios")
    public void ReporteNNAPrioritario(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\2 Registro de NNAs prioritarios.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Nna> listanna = ServicioReporte.ListaNnaReporte("prioritario");

            int i = 2;
            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                try {
                    fecha = format.dateToString(exp.getFechaIngreso());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(2);
                cell.setCellValue(exp.getHt());
                cell = row.createCell(3);
                cell.setCellValue(exp.getNExpTutelar());
                cell = row.createCell(4);
                cell.setCellValue(nna.getNombre() + " "
                        + nna.getApellidoP());
                cell = row.createCell(5);
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int mesAct = fechaAct.getMonth();
                int añoNac = nna.getFechaNacimiento().getYear();
                int mesNac = nna.getFechaNacimiento().getMonth();
                int edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac -1;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getSexo());
                cell = row.createCell(9);
                
                cell.setCellValue(nna.getCar().getNombre());
                
                cell = row.createCell(10);
                cell.setCellValue(nna.getCar().getDireccion());
                cell = row.createCell(11);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(12);
                cell.setCellValue(nna.getCar().getProvincia());
                cell = row.createCell(13);
                cell.setCellValue(nna.getCar().getDistrito());
                cell = row.createCell(14);
                try {
                    fecha = format.dateToString(nna.getFechaResolAbandono());
                } catch (Exception ex) {
                }
                if (fecha != null) cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(15);
                try {
                    fecha = format.dateToString(nna.getFechaResolConsentida());
                } catch (Exception ex) {
                }
                if (fecha != null) cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(16);
                cell.setCellValue(exp.getDiagnostico());
                cell = row.createCell(17);
                if (exp.getCodigoReferencia() != null) cell.setCellValue(exp.getCodigoReferencia().substring(0, 1));
                cell = row.createCell(18);
                if (nna.getEspecial() != null && nna.getEspecial() == 0) {
                    cell.setCellValue(exp.getCodigoReferencia());
                } else if (nna.getMayor() != null && nna.getMayor() == 0) {
                    cell.setCellValue(exp.getCodigoReferencia());
                } else if (nna.getEnfermo() != null && nna.getEnfermo() == 0) {
                    cell.setCellValue(exp.getCodigoReferencia());
                } else if (nna.getHermano() != null && nna.getHermano() == 0) {
                    cell.setCellValue(exp.getCodigoReferencia());
                } else if (nna.getAdolescente() != null && nna.getAdolescente() == 0) {
                    cell.setCellValue(exp.getCodigoReferencia());
                }
                cell = row.createCell(19);
                fecha = "";
                try {
                    fecha = format.dateToString(exp.getFechaIngreso());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha); //FALTA FECHA INGRESO AL GRUPO PRIORITARIO
                
                cell = row.createCell(20);
                if (exp.getRespLegalP() != null ) cell.setCellValue(exp.getRespLegalP());
                cell = row.createCell(21);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(22);
                cell.setCellValue(exp.getComentarios());
                int index = 1;
                int indexcelda = 23;
                if (!nna.getEstudioCasos().isEmpty()){
                for (Iterator iter3 = nna.getEstudioCasos().iterator();
                        iter3.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter3.next();
                    if (index > 3) {
                        break;
                    }
                    if (indexcelda == 26) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden());
                        indexcelda++;
                    }
                    cell = row.createCell(indexcelda);
                    fecha = "";
                    try {
                        fecha = format.dateToString(est.getFechaEstudio());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    if (est.getExpedienteFamilia() != null ) cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    if (est.getNSolicitud() != null){
                    cell.setCellValue(est.getNSolicitud().toString());
                    }
                    indexcelda++;

                    index++;
                }
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter4 = nna.getDesignacions().iterator();
                        iter4.hasNext();) {
                    Designacion daux = (Designacion) iter4.next();
                    if(daux.getFechaConsejo() != null && !daux.getFechaConsejo().equals("")){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                cell = row.createCell(33);
                if(desig.getFechaConsejo() != null && !desig.getFechaConsejo().equals("")){
                cell.setCellValue(desig.getFechaConsejo());
                }
                cell = row.createCell(34);
                if(desig.getExpedienteFamilia() != null){
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                }
                cell = row.createCell(35);
                InfoFamilia inf = new InfoFamilia();
                if(desig.getExpedienteFamilia() != null){
                    if(desig.getExpedienteFamilia().getFamilia() != null){
                        if(desig.getExpedienteFamilia().getFamilia().getInfoFamilias() != null){
                    for (Iterator iter5 = desig.getExpedienteFamilia().getFamilia().getInfoFamilias().iterator(); iter5.hasNext();) {
                    inf = (InfoFamilia) iter5.next();

                        }
                       }
                    }
                }
                if (inf.getPaisRes() != null) cell.setCellValue(inf.getPaisRes());
                cell = row.createCell(36);
                if (inf.getDepRes() != null )cell.setCellValue(inf.getDepRes());
                cell = row.createCell(37);
                if (desig.getExpedienteFamilia() != null){
                  if(desig.getExpedienteFamilia().getFamilia() != null){  
                    if(desig.getExpedienteFamilia().getFamilia().getEntidad() != null){  
                        if (desig.getExpedienteFamilia().getFamilia().getEntidad().getNombre() != null ) cell.setCellValue(desig.getExpedienteFamilia().getFamilia().getEntidad().getNombre());
                     }
                   }
                }
                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro de NNAs prioritarios.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    @RequestMapping("/Reportes/RegistroNNAsPrioGrupos")
    public void ReporteNnaPrioGrupos(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\2.0Registro de NNAs prioritarios Mensual por Grupo de Referencia.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.createRow(11);
            Date fechaAct = new Date();
            Cell cell = row.createCell(0);
            cell.setCellValue("Reporte al " + format.dateToString(fechaAct));
            sheet = wb.getSheetAt(1);
            ArrayList<Nna> listamayor = new ArrayList();
            ArrayList<Nna> listasalud = new ArrayList();
            ArrayList<Nna> listahermano = new ArrayList();
            ArrayList<Nna> listaespecial = new ArrayList();
            ArrayList<Nna> listaadolescente = new ArrayList();

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Nna> listanna = ServicioReporte.ListaNnaReporte("prioritario");

            for (Nna nnaAux : listanna) {
                if (nnaAux.getMayor() == 0) {
                    listamayor.add(nnaAux);
                }
            }

            int i = 2;
            for (Nna nna : listamayor) {
                row = sheet.createRow(i);
                ExpedienteNna exp = new ExpedienteNna();
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                

                cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                try {
                    cell.setCellValue(exp.getCodigoReferencia());
                } catch (Exception ex) {
                }
                cell = row.createCell(2);
                try {
                    cell.setCellValue(nna.getNombre() + " "
                            + nna.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(3);
                try {
                    cell.setCellValue(nna.getSexo());
                } catch (Exception ex) {
                }
                cell = row.createCell(4);
                String fecha = "";
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(5);
                int añoAct = fechaAct.getYear();
                int mesAct = fechaAct.getMonth();
                int añoNac = nna.getFechaNacimiento().getYear();
                int mesNac = nna.getFechaNacimiento().getMonth();
                int edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell.setCellValue(edad);
                cell = row.createCell(6);
                cell.setCellValue(meses);
                cell = row.createCell(7);
                try {
                    cell.setCellValue(nna.getCar().getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(8);
                try {
                    cell.setCellValue(nna.getCar().getDepartamento());
                } catch (Exception ex) {
                }
                cell = row.createCell(9);
                try {
                    cell.setCellValue(nna.getObservaciones());
                } catch (Exception ex) {
                }
                int index = 1;
                int indexcelda = 10;
                if(!nna.getEstudioCasos().isEmpty()){
                for (Iterator iter2 = nna.getEstudioCasos().iterator(); iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 13) {
                        cell = row.createCell(indexcelda);
                        try {
                            cell.setCellValue(est.getOrden());
                        } catch (Exception ex) {
                        }
                        indexcelda++;
                    }
                    cell = row.createCell(indexcelda);
                    try {
                        fecha = format.dateToString(est.getFechaEstudio());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try {
                        cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    } catch (Exception ex) {
                    }
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try {
                        cell.setCellValue(est.getNSolicitud().toString());
                    } catch (Exception ex) {
                    }
                    indexcelda++;

                    index++;
                }
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if(daux.getFechaConsejo() != null){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                cell = row.createCell(17);
                try {
                    cell.setCellValue(desig.getFechaConsejo());
                } catch (Exception ex) {
                }
                cell = row.createCell(18);
                try {
                    cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                } catch (Exception ex) {
                }
                cell = row.createCell(19);
                try {
                    cell.setCellValue(desig.getNDesignacion().toString());
                } catch (Exception ex) {
                }
                cell = row.createCell(20);
                try {
                    cell.setCellValue(exp.getRespLegalP());
                } catch (Exception ex) {
                }

                i++;
            }

            sheet = wb.getSheetAt(2);

            for (Nna nnaAux : listanna) {
                if (nnaAux.getEnfermo() == 0) {
                    listasalud.add(nnaAux);
                }
            }

            i = 2;
            for (Nna nna : listasalud) {
                row = sheet.createRow(i);
                ExpedienteNna exp = new ExpedienteNna();
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                
                

                cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                try{
                cell.setCellValue(exp.getCodigoReferencia());
                }catch(Exception ex){}
                cell = row.createCell(2);
                try{
                cell.setCellValue(nna.getNombre());
                }catch(Exception ex){}
                cell = row.createCell(3);
                try{
                cell.setCellValue(nna.getApellidoP() + " "
                        + nna.getApellidoM());
                }catch(Exception ex){}
                cell = row.createCell(4);
                try{
                cell.setCellValue(nna.getSexo());
                }catch(Exception ex){}
                cell = row.createCell(5);
                String fecha = "";
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                int añoAct = fechaAct.getYear();
                int mesAct = fechaAct.getMonth();
                int añoNac = nna.getFechaNacimiento().getYear();
                int mesNac = nna.getFechaNacimiento().getMonth();
                int edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                try{
                cell.setCellValue(nna.getCar().getNombre());
                }catch(Exception ex){}
                cell = row.createCell(9);
                try{
                cell.setCellValue(nna.getCar().getDepartamento());
                }catch(Exception ex){}
                cell = row.createCell(10);
                try{
                cell.setCellValue(nna.getObservaciones());
                }catch(Exception ex){}
                int index = 1;
                int indexcelda = 11;
                if(!nna.getEstudioCasos().isEmpty()){
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 14) {
                        cell = row.createCell(indexcelda);
                        try{
                        cell.setCellValue(est.getOrden());
                        }catch(Exception ex){}
                        indexcelda++;
                    }
                    cell = row.createCell(indexcelda);
                    try {
                        fecha = format.dateToString(est.getFechaEstudio());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    }catch(Exception ex){}
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getNSolicitud().toString());
                    }catch(Exception ex){}
                    indexcelda++;

                    index++;
                }
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if(daux.getFechaConsejo() != null){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                cell = row.createCell(18);
                try{
                cell.setCellValue(desig.getFechaConsejo());
                }catch(Exception ex){}
                cell = row.createCell(19);
                try{
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                }catch(Exception ex){}
                cell = row.createCell(20);
                try{
                cell.setCellValue(desig.getNDesignacion().toString());
                }catch(Exception ex){}
                cell = row.createCell(21);
                try{
                cell.setCellValue(exp.getRespLegalP());
                }catch(Exception ex){}

                i++;
            }

            sheet = wb.getSheetAt(3);

            for (Nna nnaAux : listanna) {
                if (nnaAux.getHermano() == 0) {
                    listahermano.add(nnaAux);
                }
            }

            i = 2;
            for (Nna nna : listahermano) {
                row = sheet.createRow(i);
                ExpedienteNna exp = new ExpedienteNna();
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                
                

                cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                try{
                cell.setCellValue(exp.getCodigoReferencia());
                }catch(Exception ex){}
                cell = row.createCell(2);
                try{
                cell.setCellValue(nna.getNombre());
                }catch(Exception ex){}
                cell = row.createCell(3);
                try{
                cell.setCellValue(nna.getApellidoP() + " " + nna.getApellidoM());
                }catch(Exception ex){}
                cell = row.createCell(4);
                try{
                cell.setCellValue(nna.getSexo());
                }catch(Exception ex){}
                cell = row.createCell(5);
                String fecha = "";
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                int añoAct = fechaAct.getYear();
                int mesAct = fechaAct.getMonth();
                int añoNac = nna.getFechaNacimiento().getYear();
                int mesNac = nna.getFechaNacimiento().getMonth();
                int edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                try{
                cell.setCellValue(nna.getCar().getNombre());
                }catch(Exception ex){}
                cell = row.createCell(9);
                try{
                cell.setCellValue(nna.getCar().getDepartamento());
                }catch(Exception ex){}
                cell = row.createCell(10);
                try{
                cell.setCellValue(nna.getObservaciones());
                }catch(Exception ex){}
                int index = 1;
                int indexcelda = 11;
                if(!nna.getEstudioCasos().isEmpty()){
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 14) {
                        cell = row.createCell(indexcelda);
                        try{
                        cell.setCellValue(est.getOrden());
                        }catch(Exception ex){}
                        indexcelda++;
                    }
                    cell = row.createCell(indexcelda);
                    try {
                        fecha = format.dateToString(est.getFechaEstudio());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    }catch(Exception ex){}
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getNSolicitud().toString());//Historico
                    }catch(Exception ex){}
                    indexcelda++;

                    index++;
                }
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                }
                cell = row.createCell(18);
                try{
                cell.setCellValue(desig.getFechaConsejo());
                }catch(Exception ex){}
                cell = row.createCell(19);
                try{
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                }catch(Exception ex){}
                cell = row.createCell(20);
                try{
                cell.setCellValue(desig.getNDesignacion().toString());//Historico (revisar)
                }catch(Exception ex){}
                cell = row.createCell(21);
                try{
                cell.setCellValue(exp.getRespLegalP());
                }catch(Exception ex){}

                i++;
            }

            sheet = wb.getSheetAt(4);

            for (Nna nnaAux : listanna) {
                if (nnaAux.getEspecial() == 0) {
                    listaespecial.add(nnaAux);
                }
            }

            i = 2;
            for (Nna nna : listaespecial) {
                row = sheet.createRow(i);
                
                ExpedienteNna exp = new ExpedienteNna();
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                
                

                cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                try{
                cell.setCellValue(exp.getCodigoReferencia());
                }catch(Exception ex){}
                cell = row.createCell(2);
                try{
                cell.setCellValue(nna.getNombre());
                }catch(Exception ex){}
                cell = row.createCell(3);
                try{
                cell.setCellValue(nna.getApellidoP());
                }catch(Exception ex){}
                cell = row.createCell(4);
                try{
                cell.setCellValue(nna.getSexo());
                }catch(Exception ex){}
                cell = row.createCell(5);
                String fecha = "";
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                int añoAct = fechaAct.getYear();
                int mesAct = fechaAct.getMonth();
                int añoNac = nna.getFechaNacimiento().getYear();
                int mesNac = nna.getFechaNacimiento().getMonth();
                int edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                try{
                cell.setCellValue(nna.getCar().getNombre());
                }catch(Exception ex){}
                cell = row.createCell(9);
                try{
                cell.setCellValue(nna.getCar().getDepartamento());
                }catch(Exception ex){}
                cell = row.createCell(10);
                try{
                cell.setCellValue(exp.getDiagnostico());
                }catch(Exception ex){}
                cell = row.createCell(11);
                try{
                cell.setCellValue(nna.getObservaciones());
                }catch(Exception ex){}
                int index = 1;
                int indexcelda = 12;
                if(!nna.getEstudioCasos().isEmpty()){
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 15) {
                        cell = row.createCell(indexcelda);
                        try{
                        cell.setCellValue(est.getOrden());
                        }catch(Exception ex){}
                        indexcelda++;
                    }
                    cell = row.createCell(indexcelda);
                    try {
                        fecha = format.dateToString(est.getFechaEstudio());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    }catch(Exception ex){}
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getNSolicitud().toString());
                    }catch(Exception ex){}
                    indexcelda++;

                    index++;
                }
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if(daux.getFechaConsejo() != null){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                cell = row.createCell(19);
                try{
                cell.setCellValue(desig.getFechaConsejo());
                }catch(Exception ex){}
                cell = row.createCell(20);
                try{
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                }catch(Exception ex){}
                cell = row.createCell(21);
                try{
                cell.setCellValue(desig.getNDesignacion().toString());
                }catch(Exception ex){}
                cell = row.createCell(22);
                try{
                cell.setCellValue(exp.getRespLegalP());
                }catch(Exception ex){}

                i++;
            }

            sheet = wb.getSheetAt(5);

            for (Nna nnaAux : listanna) {
                if (nnaAux.getAdolescente() == 0) {
                    listaadolescente.add(nnaAux);
                }
            }

            i = 2;
            for (Nna nna : listaadolescente) {
                row = sheet.createRow(i);
                
                ExpedienteNna exp = new ExpedienteNna();
                if(!nna.getExpedienteNnas().isEmpty()){
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
                }
                }
                
               
                cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                try{
                cell.setCellValue(exp.getCodigoReferencia());
                }catch(Exception ex){}
                cell = row.createCell(2);
                try{
                cell.setCellValue(nna.getNombre());
                }catch(Exception ex){}
                cell = row.createCell(3);
                try{
                cell.setCellValue(nna.getApellidoP() + " "
                        + nna.getApellidoM());
                }catch(Exception ex){}
                cell = row.createCell(4);
                try{
                cell.setCellValue(nna.getSexo());
                }catch(Exception ex){}
                cell = row.createCell(5);
                String fecha = "";
                try {
                    fecha = format.dateToString(nna.getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(6);
                int añoAct = fechaAct.getYear();
                int mesAct = fechaAct.getMonth();
                int añoNac = nna.getFechaNacimiento().getYear();
                int mesNac = nna.getFechaNacimiento().getMonth();
                int edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                try{
                cell.setCellValue(nna.getCar().getNombre());
                }catch(Exception ex){}
                cell = row.createCell(9);
                try{
                cell.setCellValue(nna.getCar().getDepartamento());
                }catch(Exception ex){}
                cell = row.createCell(10);
                try{
                cell.setCellValue(nna.getObservaciones());
                }catch(Exception ex){}
                int index = 1;
                int indexcelda = 11;
                if(!nna.getEstudioCasos().isEmpty()){
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 14) {
                        cell = row.createCell(indexcelda);
                        try{
                        cell.setCellValue(est.getOrden());
                        }catch(Exception ex){}
                        indexcelda++;
                    }
                    cell = row.createCell(indexcelda);
                    try {
                        fecha = format.dateToString(est.getFechaEstudio());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    }catch(Exception ex){}
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    try{
                    cell.setCellValue(est.getNSolicitud().toString());
                    }catch(Exception ex){}
                    indexcelda++;

                    index++;
                }
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                if(!nna.getDesignacions().isEmpty()){
                for (Iterator iter3 = nna.getDesignacions().iterator();
                        iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if(daux.getFechaConsejo() != null){
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                    }
                }
                }
                cell = row.createCell(18);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(19);
                try{
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                }catch(Exception ex){}
                cell = row.createCell(20);
                try{
                cell.setCellValue(desig.getNDesignacion().toString());
                }catch(Exception ex){}
                
                cell = row.createCell(21);
                cell.setCellValue(exp.getRespLegalP());

                i++;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro de NNAs prioritarios Mensual por Grupo de Referencia.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/AptosNacionales")
    public void ReporteAptosNacionales(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\4 Registro de Adoptantes Aptos Nacionales.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioReporte.getListaFamiliasNacionales();

            int i = 2;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                try {
                    cell.setCellValue(exp.getExpediente());
                } catch (Exception ex) {
                }
                cell = row.createCell(2);
                try {
                    cell.setCellValue(exp.getNumeroExpediente());
                } catch (Exception ex) {
                }
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
                InfoFamilia ifam = new InfoFamilia();
                for (Iterator iter2 = fam.getInfoFamilias().iterator(); iter2.hasNext();) {
                    ifam = (InfoFamilia) iter2.next();
                    for (Iterator iter3 = ifam.getAdoptantes().iterator();
                            iter3.hasNext();) {
                        Adoptante adop = (Adoptante) iter3.next();
                        if (adop.getSexo() == 'm') {
                            el = adop;
                        } else {
                            ella = adop;
                        }
                    }
                }
                cell = row.createCell(3);
                try {
                    cell.setCellValue(el.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(4);
                try {
                    cell.setCellValue(el.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(5);
                try {
                    cell.setCellValue(el.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - el.getFechaNac().getYear();
                if ((el.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((el.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((el.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                String fecha = "";
                try {
                    fecha = format.dateToString(el.getFechaNac());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                cell = row.createCell(8);
                try {
                    cell.setCellValue(ella.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(9);
                try {
                    cell.setCellValue(ella.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(10);
                try {
                    cell.setCellValue(ella.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(11);
                añoAct = fechaAct.getYear();
                edad = añoAct - ella.getFechaNac().getYear();
                if ((ella.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((ella.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((ella.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(12);
                fecha = "";
                try {
                    fecha = format.dateToString(ella.getFechaNac());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(13);
                try {
                    cell.setCellValue(ifam.getEstadoCivil());
                } catch (Exception ex) {
                }
                cell = row.createCell(14);
                try {
                    cell.setCellValue(ifam.getPaisRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(15);
                try {
                    cell.setCellValue(ifam.getDepRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(16);
                try {
                    cell.setCellValue(ifam.getNivelSocioeconomico().toString());
                } catch (Exception ex) {
                }
                cell = row.createCell(17);
                try {
                    cell.setCellValue(exp.getTipoFamilia());
                } catch (Exception ex) {
                }
                cell = row.createCell(18);
                try {
                    cell.setCellValue(exp.getNacionalidad());
                } catch (Exception ex) {
                }
                cell = row.createCell(19);
                try {
                    cell.setCellValue(exp.getUnidad().getNombre());
                } catch (Exception ex) {
                }
                Resolucion resol = new Resolucion();
                resol = ServicioReporte.getResolucionAptitud(exp.getIdexpedienteFamilia());
                
                cell = row.createCell(20);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMin());
                } catch (Exception ex) {
                }
                cell = row.createCell(22);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMax());
                } catch (Exception ex) {
                }
                cell = row.createCell(23);
                try {
                    cell.setCellValue(ifam.getExpectativaGenero());
                } catch (Exception ex) {
                }
                cell = row.createCell(24);
                try {
                    if (ifam.getNnaIncesto() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(25);
                try {
                    if (ifam.getNnaMental() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(26);
                try {
                    if (ifam.getNnaEpilepsia() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(27);
                try {
                    if (ifam.getNnaAbuso() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(28);
                try {
                    if (ifam.getNnaSifilis() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(29);
                try {
                    if (ifam.getNnaSeguiMedico() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(30);
                try {
                    if (ifam.getNnaOperacion() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(31);
                try {
                    if (ifam.getNnaHiperactivo() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(32);
                try {
                    if (ifam.getNnaEspecial() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(33);
                try {
                    if (ifam.getNnaEnfermo() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(34);
                try {
                    if (ifam.getNnaMayor() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(35);
                try {
                    if (ifam.getNnaAdolescente() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(36);
                try {
                    if (ifam.getNnaHermano() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(37);
                try {
                    cell.setCellValue(ifam.getnHijos());
                } catch (Exception ex) {
                }
                cell = row.createCell(38);
                try {
                    cell.setCellValue(ifam.getOrigenHijos());
                } catch (Exception ex) {
                }
                cell = row.createCell(39);
                try {
                    if (ifam.getPuedeViajar() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(40);
                try {
                    cell.setCellValue(ifam.getPredisposicionAp());
                } catch (Exception ex) {
                }
                cell = row.createCell(41);
                try {
                    cell.setCellValue(exp.getEstado());
                } catch (Exception ex) {
                }
                cell = row.createCell(42);
                
                Designacion desig = new Designacion();
                desig = ServicioReporte.getUltimaDesignacion(exp.getIdexpedienteFamilia());
                try {
                    fecha = format.dateToString(desig.getFechaPropuesta());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(43);
                try {
                    cell.setCellValue(ifam.getObservaciones());
                } catch (Exception ex) {
                }

                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro de Adoptantes Aptos Nacionales.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/AptosInternacionales")
    public void ReporteAptosInternacionales(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\5 Registro de Adoptantes Aptos Internacional.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioReporte.getListaFamiliasInternacionales();

            int i = 2;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-1);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                try {
                    cell.setCellValue(exp.getExpediente());
                } catch (Exception ex) {
                }
                cell = row.createCell(2);
                try {
                    cell.setCellValue(exp.getNumeroExpediente());
                } catch (Exception ex) {
                }
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
                InfoFamilia ifam = new InfoFamilia();
                for (Iterator iter2 = fam.getInfoFamilias().iterator(); iter2.hasNext();) {
                    ifam = (InfoFamilia) iter2.next();
                    for (Iterator iter3 = ifam.getAdoptantes().iterator();
                            iter3.hasNext();) {
                        Adoptante adop = (Adoptante) iter3.next();
                        if (adop.getSexo() == 'm') {
                            el = adop;
                        } else {
                            ella = adop;
                        }
                    }
                }
                cell = row.createCell(3);
                try {
                    cell.setCellValue(el.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(4);
                try {
                    cell.setCellValue(el.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(5);
                try {
                    cell.setCellValue(el.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - el.getFechaNac().getYear();
                if ((el.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((el.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((el.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                String fecha = "";
                try {
                    fecha = format.dateToString(el.getFechaNac());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                cell = row.createCell(8);
                try {
                    cell.setCellValue(ella.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(9);
                try {
                    cell.setCellValue(ella.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(10);
                try {
                    cell.setCellValue(ella.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(11);
                añoAct = fechaAct.getYear();
                edad = añoAct - ella.getFechaNac().getYear();
                if ((ella.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((ella.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((ella.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(12);
                fecha = "";
                try {
                    fecha = format.dateToString(ella.getFechaNac());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(13);
                try {
                    cell.setCellValue(ifam.getEstadoCivil());
                } catch (Exception ex) {
                }
                cell = row.createCell(14);
                try {
                    cell.setCellValue(ifam.getPaisRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(15);
                try {
                    cell.setCellValue(ifam.getDepRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(16);
                try {
                    cell.setCellValue(ifam.getNivelSocioeconomico().toString());
                } catch (Exception ex) {
                }
                cell = row.createCell(17);
                try {
                    cell.setCellValue(exp.getTipoFamilia());
                } catch (Exception ex) {
                }
                cell = row.createCell(18);
                try {
                    cell.setCellValue(exp.getNacionalidad());
                } catch (Exception ex) {
                }
                cell = row.createCell(19);
                try {
                    cell.setCellValue(fam.getEntidad().getNombre());
                } catch (Exception ex) {
                }
                Resolucion resol = new Resolucion();
                resol = ServicioReporte.getResolucionAptitud(exp.getIdexpedienteFamilia());
                cell = row.createCell(20);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMin());
                } catch (Exception ex) {
                }
                cell = row.createCell(22);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMax());
                } catch (Exception ex) {
                }
                cell = row.createCell(23);
                try {
                    cell.setCellValue(ifam.getExpectativaGenero());
                } catch (Exception ex) {
                }
                cell = row.createCell(24);
                try {
                    if (ifam.getNnaIncesto() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(25);
                try {
                    if (ifam.getNnaMental() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(26);
                try {
                    if (ifam.getNnaEpilepsia() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(27);
                try {
                    if (ifam.getNnaAbuso() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(28);
                try {
                    if (ifam.getNnaSifilis() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(29);
                try {
                    if (ifam.getNnaSeguiMedico() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(30);
                try {
                    if (ifam.getNnaOperacion() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(31);
                try {
                    if (ifam.getNnaHiperactivo() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(32);
                try {
                    if (ifam.getNnaEspecial() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(33);
                try {
                    if (ifam.getNnaEnfermo() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(34);
                try {
                    if (ifam.getNnaMayor() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(35);
                try {
                    if (ifam.getNnaAdolescente() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(36);
                try {
                    if (ifam.getNnaHermano() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(37);
                try {
                    cell.setCellValue(ifam.getnHijos());
                } catch (Exception ex) {
                }
                cell = row.createCell(38);
                try {
                    cell.setCellValue(ifam.getOrigenHijos());
                } catch (Exception ex) {
                }
                cell = row.createCell(39);
                try {
                    if (ifam.getPuedeViajar() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(40);
                try {
                    cell.setCellValue(ifam.getPredisposicionAp());
                } catch (Exception ex) {
                }
                cell = row.createCell(41);
                try {
                    cell.setCellValue(exp.getEstado());
                } catch (Exception ex) {
                }
                cell = row.createCell(42);
                
                Designacion desig = new Designacion();
                desig = ServicioReporte.getUltimaDesignacion(exp.getIdexpedienteFamilia());
                try {
                    fecha = format.dateToString(desig.getFechaPropuesta());
                    cell.setCellValue(fecha);
                } catch (Exception ex) {
                }
                cell = row.createCell(43);
                try {
                    cell.setCellValue(ifam.getObservaciones());
                } catch (Exception ex) {
                }

                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro de Adoptantes Aptos Internacionales.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/AdopcionExtranjero")
    public void ReporteAdopcionExtranjero(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\6 Registro de Adopción en el Extranjero.xls");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioReporte.getListaFamiliasInternacionales();

            int i = 3;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-2);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                try {
                    cell.setCellValue(exp.getExpediente());
                } catch (Exception ex) {
                }
                cell = row.createCell(2);
                try {
                    cell.setCellValue(exp.getNumeroExpediente());
                } catch (Exception ex) {
                }
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
                InfoFamilia ifam = new InfoFamilia();
                for (Iterator iter2 = fam.getInfoFamilias().iterator(); iter2.hasNext();) {
                    ifam = (InfoFamilia) iter2.next();
                    for (Iterator iter3 = ifam.getAdoptantes().iterator();
                            iter3.hasNext();) {
                        Adoptante adop = (Adoptante) iter3.next();
                        if (adop.getSexo() == 'm') {
                            el = adop;
                        } else {
                            ella = adop;
                        }
                    }
                }
                cell = row.createCell(3);
                try {
                    cell.setCellValue(el.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(4);
                try {
                    cell.setCellValue(el.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(5);
                try {
                    cell.setCellValue(el.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(6);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - el.getFechaNac().getYear();
                if ((el.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((el.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((el.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(7);
                String fecha = "";
                try {
                    fecha = format.dateToString(el.getFechaNac());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                cell = row.createCell(8);
                try {
                    cell.setCellValue(ella.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(9);
                try {
                    cell.setCellValue(ella.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(10);
                try {
                    cell.setCellValue(ella.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(11);
                añoAct = fechaAct.getYear();
                edad = añoAct - ella.getFechaNac().getYear();
                if ((ella.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((ella.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((ella.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(12);
                fecha = "";
                try {
                    fecha = format.dateToString(ella.getFechaNac());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(13);
                try {
                    cell.setCellValue(ifam.getEstadoCivil());
                } catch (Exception ex) {
                }
                cell = row.createCell(14);
                try {
                    cell.setCellValue(ifam.getPaisRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(15);
                try {
                    cell.setCellValue(ifam.getDepRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(16);
                try {
                    cell.setCellValue(ifam.getTelefono());
                } catch (Exception ex) {
                }
                cell = row.createCell(17);
                try {
                    cell.setCellValue(ifam.getDomicilio());
                } catch (Exception ex) {
                }
                cell = row.createCell(18);
                try {
                    cell.setCellValue(ella.getCorreo());
                } catch (Exception ex) {
                }
                cell = row.createCell(19);
                try {
                    if (fam.getEntidad() != null) cell.setCellValue(fam.getEntidad().getPais());
                } catch (Exception ex) {
                }
                Resolucion resol = new Resolucion();
                resol = ServicioReporte.getResolucionAptitud(exp.getIdexpedienteFamilia());
                cell = row.createCell(20);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMin());
                } catch (Exception ex) {
                }
                cell = row.createCell(22);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMax());
                } catch (Exception ex) {
                }
                cell = row.createCell(23);
                try {
                    cell.setCellValue(ifam.getExpectativaGenero());
                } catch (Exception ex) {
                }
                cell = row.createCell(24);
                try {
                    cell.setCellValue(exp.getEstado());
                } catch (Exception ex) {
                }
                cell = row.createCell(25);
                try {
                    cell.setCellValue(ifam.getObservaciones());
                } catch (Exception ex) {
                }
                
                Designacion desig = new Designacion();
                desig = ServicioReporte.getUltimaDesignacionNna(exp.getIdexpedienteFamilia());
                
                short acep = (short) 2;
                
                if (desig.getAceptacionConsejo() == acep) {
                    cell = row.createCell(26);
                    try {
                        cell.setCellValue(desig.getNna().getNombre() + " " + desig.getNna().getApellidoP());
                    } catch (Exception ex) {
                    }
                    cell = row.createCell(27);
                    try {
                        fecha = format.dateToString(desig.getNna().getFechaNacimiento());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    cell = row.createCell(28);
                    edad = añoAct - desig.getNna().getFechaNacimiento().getYear();
                    if ((desig.getNna().getFechaNacimiento().getMonth() - fechaAct.getMonth())
                            > 0) {
                        edad--;
                    } else if ((desig.getNna().getFechaNacimiento().getMonth()
                            - fechaAct.getMonth()) == 0) {
                        if ((desig.getNna().getFechaNacimiento().getDate() - fechaAct.getDate())
                                > 0) {
                            edad--;
                        }
                    }
                    cell.setCellValue(edad);
                    cell = row.createCell(29);
                    try {
                        cell.setCellValue(desig.getNna().getSexo());
                    } catch (Exception ex) {
                    }
                    cell = row.createCell(30);
                    String caracteristicas = "Presenta lo siguiente: ";
                    try {
                        if (desig.getNna().getSeguiMedico() == 0) {
                            caracteristicas = caracteristicas + "requiere de seguimiento médico, ";
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (desig.getNna().getOperacion() == 0) {
                            caracteristicas = caracteristicas + "requiere de cirugía menor, ";
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (desig.getNna().getHiperactivo() == 0) {
                            caracteristicas = caracteristicas + "sufre de TDAH, ";
                        }
                    } catch (Exception ex) {
                    }
                    caracteristicas = caracteristicas + " y las siguientes observaciones: " + desig.getNna().getObservaciones();
                    cell.setCellValue(caracteristicas);//caracteristicas
                }

                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro de Adopcion en el Extranjero.xls");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/Expedientes")
    public void ReporteExpedientes(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\3 Registro Nacional de Exp. Nacionales e Internacionales.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(1);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioReporte.getListaFamilias();

            int i = 3;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i-2);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                String fecha = "";
                if(!fam.getExpedienteFamilias().isEmpty()){
                exp = fam.getExpedienteFamilias().iterator().next();
                }
                                
                if (exp.getExpediente() != null) cell.setCellValue(exp.getExpediente());
                
                cell = row.createCell(2);
                
                if (exp.getHtFicha() != null) cell.setCellValue(exp.getHtFicha());
                
                cell = row.createCell(3);
                
                if (exp.getnFicha() != null) cell.setCellValue(exp.getnFicha());
               
                cell = row.createCell(4);
                
                
                if (exp.getFechaIngresoFicha() != null ) {
                    fecha = format.dateToString(exp.getFechaIngresoFicha());
                }
                
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(5);
              
                if (exp.getHt() != null) cell.setCellValue(exp.getHt());
                
                cell = row.createCell(6);
                
                if (exp.getUnidad() != null) cell.setCellValue(exp.getUnidad().getNombre());
                
                cell = row.createCell(7);
                
                if (exp.getNumeroExpediente() != null) cell.setCellValue(exp.getNumeroExpediente());
                
                cell = row.createCell(8);
                int year = 0;
                
                if(exp.getFechaIngresoDga() != null){
                    fecha = format.dateToString(exp.getFechaIngresoDga());
                    String[] parts = fecha.split("/");
                    year = Integer.parseInt(parts[2]);
                }
                
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(9);
                
                cell.setCellValue(year);
                
                
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
                InfoFamilia ifam = new InfoFamilia();
                if(!fam.getInfoFamilias().isEmpty()){
                for (Iterator iter2 = fam.getInfoFamilias().iterator(); iter2.hasNext();) {
                    ifam = (InfoFamilia) iter2.next();
                    for (Iterator iter3 = ifam.getAdoptantes().iterator();
                            iter3.hasNext();) {
                        Adoptante adop = (Adoptante) iter3.next();
                        if (adop.getSexo() == 'm') {
                            el = adop;
                        } else {
                            ella = adop;
                        }
                    }
                }
                }
                cell = row.createCell(10);
                try {
                    cell.setCellValue(el.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(11);
                try {
                    cell.setCellValue(el.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(12);
                try {
                    cell.setCellValue(el.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(13);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - el.getFechaNac().getYear();
                if ((el.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((el.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((el.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(14);
                try {
                    cell.setCellValue(ella.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(15);
                try {
                    cell.setCellValue(ella.getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(16);
                try {
                    cell.setCellValue(ella.getApellidoM());
                } catch (Exception ex) {
                }
                cell = row.createCell(17);
                añoAct = fechaAct.getYear();
                edad = añoAct - ella.getFechaNac().getYear();
                if ((ella.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((ella.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((ella.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(18);
                try {
                    cell.setCellValue(ella.getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(19);
                try {
                    cell.setCellValue(ifam.getEstadoCivil().substring(0, 1));
                } catch (Exception ex) {
                }
                cell = row.createCell(20);
                try {
                    cell.setCellValue(el.getPaisNac());
                } catch (Exception ex) {
                }
                cell = row.createCell(21);
                try {
                    cell.setCellValue(ella.getPaisNac());
                } catch (Exception ex) {
                }
                cell = row.createCell(22);
                try {
                    cell.setCellValue(ifam.getPaisRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(23);
                try {
                    cell.setCellValue(ifam.getDepRes());
                } catch (Exception ex) {
                }
                cell = row.createCell(24);
                try {
                    cell.setCellValue(exp.getTipoFamilia());
                } catch (Exception ex) {
                }
                cell = row.createCell(25);
                try {
                    cell.setCellValue(exp.getTipoListaEspera());
                } catch (Exception ex) {
                }
                cell = row.createCell(26);
                try {
                    cell.setCellValue(fam.getEntidad().getNombre());
                } catch (Exception ex) {
                }
                cell = row.createCell(27);
                try {
                    cell.setCellValue(exp.getNacionalidad());
                } catch (Exception ex) {
                }
                Evaluacion eval = new Evaluacion();
                Date ultfecha = new Date(10, 0, 01);
                if(!exp.getEvaluacions().isEmpty()){
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    Evaluacion evalaux = (Evaluacion) iter3.next();
                    if(evalaux.getFechaAsignacion() != null){
                    if (ultfecha.before(evalaux.getFechaAsignacion()) && evalaux.getTipo().equals("legal")) {
                        ultfecha = evalaux.getFechaAsignacion();
                        eval = evalaux;
                    }
                    }
                }
                }
                cell = row.createCell(28);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha); //fecha
                fecha = "";
                cell = row.createCell(29);
                try {
                    cell.setCellValue(eval.getPersonal().getNombre() + " " + eval.getPersonal().getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(30);
                try {
                    cell.setCellValue(eval.getResultado());
                } catch (Exception ex) {
                }
                cell = row.createCell(31);
                try {
                    cell.setCellValue(eval.getObservacion());
                } catch (Exception ex) {
                }
                eval = new Evaluacion();
                ultfecha = new Date(10, 0, 01);
                if(!exp.getEvaluacions().isEmpty()){
                for (Iterator iter4 = exp.getEvaluacions().iterator(); iter4.hasNext();) {
                    Evaluacion evalaux = (Evaluacion) iter4.next();
                    if(evalaux.getFechaAsignacion() != null){
                    if (ultfecha.before(evalaux.getFechaAsignacion()) && evalaux.getTipo().equals("psicologica")) {
                        ultfecha = evalaux.getFechaAsignacion();
                        eval = evalaux;
                    }
                    }
                }
                }
                cell = row.createCell(32);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(33);
                try {
                    cell.setCellValue(eval.getPersonal().getNombre() + " " + eval.getPersonal().getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(34);
                try {
                    cell.setCellValue(eval.getResultado());
                } catch (Exception ex) {
                }
                cell = row.createCell(35);
                try {
                    cell.setCellValue(eval.getObservacion());
                } catch (Exception ex) {
                }
                eval = new Evaluacion();
                ultfecha = new Date(10, 0, 01);
                if(!exp.getEvaluacions().isEmpty()){
                for (Iterator iter5 = exp.getEvaluacions().iterator(); iter5.hasNext();) {
                    Evaluacion evalaux = (Evaluacion) iter5.next();
                    if(evalaux.getFechaAsignacion() != null){
                    if (ultfecha.before(evalaux.getFechaAsignacion()) && evalaux.getTipo().equals("social")) {
                        ultfecha = evalaux.getFechaAsignacion();
                        eval = evalaux;
                    }
                    }
                }
                }
                cell = row.createCell(36);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(37);
                try {
                    cell.setCellValue(eval.getPersonal().getNombre() + " " + eval.getPersonal().getApellidoP());
                } catch (Exception ex) {
                }
                cell = row.createCell(38);
                try {
                    cell.setCellValue(eval.getResultado());
                } catch (Exception ex) {
                }
                cell = row.createCell(39);
                try {
                    cell.setCellValue(eval.getObservacion());
                } catch (Exception ex) {
                }
                cell = row.createCell(40);
                try {
                    fecha = format.dateToString(exp.getTupa());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(41);
                try {
                    cell.setCellValue(exp.getEstado());
                } catch (Exception ex) {
                }
                cell = row.createCell(42);
                try {
                    fecha = format.dateToString(exp.getTupa());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                ultfecha = new Date();
                Resolucion resol = new Resolucion();
                if (exp.getIdexpedienteFamilia() != 0) resol = ServicioReporte.getUltimaResolucion(exp.getIdexpedienteFamilia());
                
                cell = row.createCell(43);
                try {
                    cell.setCellValue(resol.getTipo());
                } catch (Exception ex) {
                }
                cell = row.createCell(44);
                try {
                    cell.setCellValue(resol.getNumero());
                } catch (Exception ex) {
                }
                cell = row.createCell(45);
                int añoresol = 0;
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                    //añoresol = resol.getFechaResol().getYear();
                    if(resol.getFechaResol() != null){
                    String[] parts = fecha.split("/");
                    añoresol = Integer.parseInt(parts[2]);
                    }
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(46);
                try {
                    cell.setCellValue(añoresol);
                } catch (Exception ex) {
                }
                cell = row.createCell(47);
                try {
                    fecha = format.dateToString(resol.getFechaNotificacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(48);
                try {
                    cell.setCellValue(ifam.getNivelSocioeconomico().toString());
                } catch (Exception ex) {
                }
                cell = row.createCell(49);
                try {
                    cell.setCellValue(ifam.getExpectativaEdadMin().toString() + " - " + ifam.getExpectativaEdadMax().toString());
                } catch (Exception ex) {
                }
                cell = row.createCell(50);
                String caracteristicas = "Acepta las siguientes condiciones: ";
                try {
                    if (ifam.getNnaSeguiMedico() == 0) {
                        caracteristicas = caracteristicas + "requiere de seguimiento médico, ";
                    }
                } catch (Exception ex) {
                }
                try {
                    if (ifam.getNnaOperacion() == 0) {
                        caracteristicas = caracteristicas + "requiere de cirugía menor, ";
                    }
                } catch (Exception ex) {
                }
                try {
                    if (ifam.getNnaHiperactivo() == 0) {
                        caracteristicas = caracteristicas + "sufre de TDAH.";
                    }
                } catch (Exception ex) {
                }
                cell.setCellValue(caracteristicas);
                cell = row.createCell(51);
                caracteristicas = "Acepta los siguientes antecedentes: ";
                try {
                    if (ifam.getNnaIncesto() == 0) {
                        caracteristicas = caracteristicas + "producto de incesto, ";
                    }
                } catch (Exception ex) {
                }
                try {
                    if (ifam.getNnaMental() == 0) {
                        caracteristicas = caracteristicas + "padres con enfermedad psiquiatrica, ";
                    }
                } catch (Exception ex) {
                }
                try {
                    if (ifam.getNnaEpilepsia() == 0) {
                        caracteristicas = caracteristicas + "padres con epilepsia, ";
                    }
                } catch (Exception ex) {
                }
                try {
                    if (ifam.getNnaAbuso() == 0) {
                        caracteristicas = caracteristicas + "víctima de abuso sexual, ";
                    }
                } catch (Exception ex) {
                }
                try {
                    if (ifam.getNnaSifilis() == 0) {
                        caracteristicas = caracteristicas + "con sífilis al nacer. ";
                    }
                } catch (Exception ex) {
                }
                cell.setCellValue(caracteristicas);
                cell = row.createCell(52);
                try {
                    cell.setCellValue(ifam.getnHijos());
                } catch (Exception ex) {
                }
                cell = row.createCell(53);
                try {
                    cell.setCellValue(ifam.getOrigenHijos());
                } catch (Exception ex) {
                }
                cell = row.createCell(54);
                try {
                    if (ifam.getPuedeViajar() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                } catch (Exception ex) {
                }
                cell = row.createCell(55);
                try {
                    cell.setCellValue(ifam.getPredisposicionAp());
                } catch (Exception ex) {
                }
                cell = row.createCell(56);
                try {
                    cell.setCellValue(ifam.getObservaciones());
                } catch (Exception ex) {
                }

                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Registro Nacional de Exp Nacionales e Internacionales.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    //PROBAR
    @RequestMapping("/Reportes/RENAD")
    public void ReporteRENAD(ModelMap map, HttpSession session,
            HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            //Se llama a la plantilla localizada en la ruta
            InputStream inp = new FileInputStream("C:\\Plantillas\\Test.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioReporte.getListaFamilias();//Solo de prueba, falta cambiar por otro metodo

            int i = 3;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i - 2);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                if (exp.getExpediente() != null) {
                    cell.setCellValue(exp.getExpediente());
                }
                cell = row.createCell(2);
                if (exp.getHt() != null) {
                    cell.setCellValue(exp.getHt());
                }
                cell = row.createCell(3);
                if (exp.getUnidad() != null) {
                    if (exp.getUnidad().getNombre() != null) {
                        cell.setCellValue(exp.getUnidad().getNombre());
                    }
                }
                cell = row.createCell(4);
                if (exp.getNumeroExpediente() != null) {
                    cell.setCellValue(exp.getNumeroExpediente());
                }
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
                InfoFamilia ifam = new InfoFamilia();
                for (Iterator iter2 = fam.getInfoFamilias().iterator(); iter2.hasNext();) {
                    ifam = (InfoFamilia) iter2.next();
                    for (Iterator iter3 = ifam.getAdoptantes().iterator();
                            iter3.hasNext();) {
                        Adoptante adop = (Adoptante) iter3.next();
                        if (adop.getSexo() == 'm') {
                            el = adop;
                        } else if (adop.getSexo() == 'f') {
                            ella = adop;
                        }
                    }
                }
                cell = row.createCell(5);
                if (el.getNombre() != null) {
                    cell.setCellValue(el.getNombre());
                }
                cell = row.createCell(6);
                if (el.getApellidoP() != null) {
                    cell.setCellValue(el.getApellidoP());
                }
                cell = row.createCell(7);
                if (el.getApellidoM() != null) {
                    cell.setCellValue(el.getApellidoM());
                }
                cell = row.createCell(8);
                Date fechaAct = new Date();
                int añoAct = fechaAct.getYear();
                int edad = añoAct - el.getFechaNac().getYear();
                if ((el.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((el.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((el.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(9);
                if (ella.getNombre() != null) {
                    cell.setCellValue(ella.getNombre());
                }
                cell = row.createCell(10);
                if (ella.getApellidoP() != null) {
                    cell.setCellValue(ella.getApellidoP());
                }
                cell = row.createCell(11);
                if (ella.getApellidoM() != null) {
                    cell.setCellValue(ella.getApellidoM());
                }
                cell = row.createCell(12);
                añoAct = fechaAct.getYear();
                edad = añoAct - ella.getFechaNac().getYear();
                if ((ella.getFechaNac().getMonth() - fechaAct.getMonth())
                        > 0) {
                    edad--;
                } else if ((ella.getFechaNac().getMonth()
                        - fechaAct.getMonth()) == 0) {
                    if ((ella.getFechaNac().getDate() - fechaAct.getDate())
                            > 0) {
                        edad--;
                    }
                }
                cell.setCellValue(edad);
                cell = row.createCell(13);
                if (ifam.getEstadoCivil() != null) {
                    cell.setCellValue(ifam.getEstadoCivil().charAt(0));
                }
                cell = row.createCell(14);
                if (el.getPaisNac() != null) {
                    cell.setCellValue(el.getPaisNac());
                }
                cell = row.createCell(15);
                if (ella.getPaisNac() != null) {
                    cell.setCellValue(ella.getPaisNac());
                }
                cell = row.createCell(16);
                if (ifam.getPaisRes() != null) {
                    cell.setCellValue(ifam.getPaisRes());
                }
                cell = row.createCell(17);
                if (ifam.getDepRes() != null) {
                    cell.setCellValue(ifam.getDepRes());
                }
                cell = row.createCell(18);
                if (exp.getTipoFamilia() != null) {
                    cell.setCellValue(exp.getTipoFamilia());
                }
                cell = row.createCell(19);
                if (fam.getEntidad() != null) {
                    if (fam.getEntidad().getNombre() != null) {
                        cell.setCellValue(fam.getEntidad().getNombre());
                    }
                }
                cell = row.createCell(20);
                if (exp.getNacionalidad() != null) {
                    cell.setCellValue(exp.getNacionalidad());
                }
                cell = row.createCell(21);
                Designacion desig = new Designacion();
                desig = ServicioReporte.getUltimaDesignacionNna(exp.getIdexpedienteFamilia());
                if (desig.getNna() != null) {
                    if (desig.getNna().getNombre() != null) {
                        cell.setCellValue(desig.getNna().getNombre());
                    }
                }
                cell = row.createCell(22);
                if (desig.getNna() != null) {
                    if (desig.getNna().getApellidoP() != null) {
                        cell.setCellValue(desig.getNna().getApellidoP());
                    }
                }
                cell = row.createCell(23);
                if (desig.getNna() != null) {
                    if (desig.getNna().getApellidoM() != null) {
                        cell.setCellValue(desig.getNna().getApellidoM());
                    }
                }
                ExpedienteNna tempExp = ServicioNna.getExpNna(desig.getNna().getIdnna());
                if (desig.getNna() != null) {
                    if (desig.getNna().getClasificacion() != null && desig.getNna().getHermano() != null) {
                        if (desig.getNna().getClasificacion().equals("prioritario") && desig.getNna().getHermano() == 0) {
                            cell = row.createCell(24);
                            if(tempExp.getIdexpedienteNna() != 0){
                            cell.setCellValue(tempExp.getCodigoReferencia());
                            }
                        }
                    }
                }
                cell = row.createCell(25);
                if (desig.getNna() != null) {
                    if (desig.getNna().getSexo() != null) {
                        cell.setCellValue(desig.getNna().getSexo());
                    }
                }
                cell = row.createCell(26);
                String fecha = "";
                try {
                    fecha = format.dateToString(desig.getNna().getFechaNacimiento());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                fechaAct = new Date();
                int mesAct = fechaAct.getMonth();
                int añoNac = desig.getNna().getFechaNacimiento().getYear();
                int mesNac = desig.getNna().getFechaNacimiento().getMonth();
                edad = 0;
                int meses = 0;
                if (añoAct != añoNac && mesAct > mesNac) {
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct != añoNac && mesNac > mesAct) {
                    edad = añoAct - añoNac;
                }
                if (añoAct == añoNac) {
                    edad = 0;
                }
                if (mesAct == mesNac) {
                    meses = 0;
                }
                if (mesAct != mesNac && mesAct > mesNac) {
                    meses = mesAct - mesNac;
                }
                if (mesAct != mesNac && mesAct <= mesNac) {
                    meses = mesAct + 12 - mesNac;
                }
                cell = row.createCell(27);
                cell.setCellValue(edad);
                cell = row.createCell(28);
                cell.setCellValue(meses);
                cell = row.createCell(29);
                if (desig.getNna() != null) {
                    if (desig.getNna().getDepartamentoNacimiento() != null) {
                        cell.setCellValue(desig.getNna().getDepartamentoNacimiento());
                    }
                }
                cell = row.createCell(30);
                if (desig.getNna() != null) {
                    if (desig.getNna().getProvinciaNacimiento() != null) {
                        cell.setCellValue(desig.getNna().getProvinciaNacimiento());
                    }
                }
                cell = row.createCell(31);
                if (desig.getNna() != null) {
                    if (desig.getNna().getDistritoNacimiento() != null) {
                        cell.setCellValue(desig.getNna().getDistritoNacimiento());
                    }
                }
                cell = row.createCell(32);
                if (desig.getNna() != null) {
                    if (desig.getNna().getCar() != null) {
                        if (desig.getNna().getCar().getNombre() != null) {
                            cell.setCellValue(desig.getNna().getCar().getNombre());
                        }
                    }
                }
                cell = row.createCell(33);
                if (desig.getNna() != null) {
                    if (desig.getNna().getCar() != null) {
                        if (desig.getNna().getCar().getDepartamento() != null) {
                            cell.setCellValue(desig.getNna().getCar().getDepartamento());
                        }
                    }
                }
                cell = row.createCell(34);
                cell.setCellValue("");//DIT (Revisar)
                ExpedienteNna expnna = tempExp;
                
                cell = row.createCell(35);
                try {
                    fecha = format.dateToString(expnna.getFechaInvTutelar());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(36);
                if (desig.getNna() != null) {
                    if (desig.getNna().getJuzgado() != null) {
                        if (desig.getNna().getJuzgado().getNombre() != null) {
                            cell.setCellValue(desig.getNna().getJuzgado().getNombre());
                        }
                    }
                }
                cell = row.createCell(37);
                String antecedentes = "";
                if (desig.getNna().getIncesto() != null) {
                    if (desig.getNna().getIncesto() == 0) { //No se está guardando dentro de la BD casos de violación
                        antecedentes = antecedentes + " NIV";
                    }
                }
                if (desig.getNna().getMental() != null) {
                    if (desig.getNna().getMental() == 0) {
                        antecedentes = antecedentes + " PPS";
                    }
                }
                if (desig.getNna().getEpilepsia() != null && desig.getNna().getSifilis() != null) {
                    if (desig.getNna().getEpilepsia() == 0 || desig.getNna().getSifilis() == 0) { //Se considera caso de sifilis al nacer o padres con epilepsia como Antecedentes de menor incidencia familiar
                        antecedentes = antecedentes + " AME";
                    }
                }
                if (antecedentes.equals("")) {
                    antecedentes = "NPA";
                }
                cell.setCellValue(antecedentes); //No hay en la BD forma de detectar abuso de drogas como antecedente o determinar un caso de sin antecedentes conocidos
                cell = row.createCell(38);
                boolean flag = true;
                if (desig.getNna() != null) {
                    if (desig.getNna().getEspecial() != null) {
                        if (desig.getNna().getEspecial() == 0) {
                            cell.setCellValue("NE");
                            flag = false;
                        }
                    }
                    if (desig.getNna().getEnfermo() != null) {
                        if (desig.getNna().getEnfermo() == 0) {
                            cell.setCellValue("S");
                            flag = false;
                        }
                    }
                    if (desig.getNna().getMayor() != null) {
                        if (desig.getNna().getMayor() == 0) {
                            cell.setCellValue("M");
                            flag = false;
                        }
                    }
                    if (desig.getNna().getAdolescente() != null) {
                        if (desig.getNna().getAdolescente() == 0) {
                            cell.setCellValue("A");
                            flag = false;
                        }
                    }
                    if (desig.getNna().getHermano() != null) {
                        if (desig.getNna().getHermano() == 0) {
                            cell.setCellValue("H");
                            flag = false;
                        }
                    }
                    if (flag) {
                        cell.setCellValue("NP");
                    }
                }
                cell = row.createCell(39);
                if (desig.getNna() != null) {
                    if (desig.getNna().getClasificacion() != null) {
                        cell.setCellValue(desig.getNna().getClasificacion());
                    }
                }
                cell = row.createCell(40);
                if (desig.getTipoPropuesta() != null) {
                    cell.setCellValue(desig.getTipoPropuesta());
                }
                Resolucion resol = new Resolucion();
                resol = ServicioReporte.getResolucionAptitud(exp.getIdexpedienteFamilia());
                Evaluacion eval = new Evaluacion();
                
                cell = row.createCell(41);
                try {
                    fecha = format.dateToString(exp.getFechaIngresoDga());//Se entiende que la fecha de ingreso de expedientes es la fecha en la que pasa a la DGA
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(42);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(43);
                try {
                    fecha = format.dateToString(desig.getFechaConsejo());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(44);
                if (desig.getAceptacionConsejo() != null) {
                    if (desig.getAceptacionConsejo() == 0) {
                        cell.setCellValue("Si");
                    } else {
                        cell.setCellValue("No");
                    }
                }
                
                //evaluacion de empatia
                eval = ServicioReporte.getEvaluacion(exp.getIdexpedienteFamilia(), "empatia");
                if (eval.getIdevaluacion() != 0){
                    resol = ServicioReporte.getResolucionDeEvaluacion(eval.getIdevaluacion());
                }else {
                    resol = new Resolucion();
                }
                cell = row.createCell(45);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(46);
                try {
                    fecha = format.dateToString(eval.getFechaResultado());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(47);
                if (eval.getNumEval() != null) {
                    cell.setCellValue(eval.getNumEval());
                }
                cell = row.createCell(48);
                if (eval.getResultado() != null) {
                    cell.setCellValue(eval.getResultado());
                }
                cell = row.createCell(49);
                cell.setCellValue("");//No existe fecha externamiento en la BD
                cell = row.createCell(50);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(51);
                if (resol.getTipo() != null) {
                    cell.setCellValue(resol.getTipo());
                }
                cell = row.createCell(52);
                if (resol.getNumero() != null) {
                    cell.setCellValue(resol.getNumero());
                }
                
                eval = ServicioReporte.getEvaluacion(exp.getIdexpedienteFamilia(), "informe");
                if (eval.getIdevaluacion() != 0){
                    resol = ServicioReporte.getResolucionDeEvaluacion(eval.getIdevaluacion());
                }else {
                    resol = new Resolucion();
                }
                
                cell = row.createCell(53);
                try {
                    fecha = format.dateToString(eval.getFechaResultado());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(54);
                if (eval.getNumEval() != null) {
                    cell.setCellValue(eval.getNumEval());
                }
                cell = row.createCell(55);
                if (eval.getResultado() != null) {
                    cell.setCellValue(eval.getResultado());
                }
                cell = row.createCell(56);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(57);
                if (resol.getTipo() != null) {
                    cell.setCellValue(resol.getTipo());
                }
                cell = row.createCell(58);
                if (resol.getNumero() != null) {
                    cell.setCellValue(resol.getNumero());
                }
                cell = row.createCell(59);
                if (expnna.getNActual() != null) {
                    cell.setCellValue(expnna.getNActual());
                }
                cell = row.createCell(60);
                if (expnna.getApellidopActual() != null) {
                    cell.setCellValue(expnna.getApellidopActual());
                }
                cell = row.createCell(61);
                if (expnna.getApellidomActual() != null) {
                    cell.setCellValue(expnna.getApellidomActual());
                }
                cell = row.createCell(62);
                if (expnna.getRespLegalNombre() != null && expnna.getRespLegalP() != null) {
                    cell.setCellValue(expnna.getRespLegalNombre() + " " + expnna.getRespLegalP());
                }
                cell = row.createCell(63);
                if (expnna.getRespPsicosocialNombre() != null && expnna.getRespPiscosocialP() != null) {
                    cell.setCellValue(expnna.getRespPsicosocialNombre() + " " + expnna.getRespPiscosocialP());
                }

                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Test.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }
}
