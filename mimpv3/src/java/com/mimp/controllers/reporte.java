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

    @RequestMapping("/Reportes/OrganismosAcreditados")
    public void ReporteOrganismo(ModelMap map, HttpSession session,
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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\8 Registro del número Organismos Acreditados.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Organismo> listaorg
                    = ServicioPersonal.ListaOrganismos();

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

    //PROBAR + COMPLETAR (FALTA VER COMO REFERENCIAR NNA CON POST-ADOPCION)
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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\7 Registros Post-Adopción.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<PostAdopcion> listapost = ServicioReporte.ReportePostAdopcion();

            int i = 3;
            for (PostAdopcion post : listapost) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                //En caso de hermanos, ambos tendrán el mismo nombre de expediente
                for (Iterator iter = post.getFamilia().getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                cell.setCellValue(exp.getExpediente());
                cell = row.createCell(2);
                Adoptante el = new Adoptante();
                Adoptante ella = new Adoptante();
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
                cell.setCellValue(el.getApellidoM());
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
                //¿Cómo diferenciar la designación de un hermano sobre otro?
                Designacion desig = new Designacion();
                for (Iterator iter4 = exp.getDesignacions().iterator(); iter4.hasNext();) {
                    desig = (Designacion) iter4.next();

                }
                cell.setCellValue(""); //NNA nombre
                cell = row.createCell(13);
                cell.setCellValue("");
                cell = row.createCell(14);
                cell.setCellValue("");
                cell = row.createCell(15);
                cell.setCellValue("");
                cell = row.createCell(16);
                cell.setCellValue("");
                cell = row.createCell(17);
                cell.setCellValue("");
                cell = row.createCell(18);
                cell.setCellValue("");
                cell = row.createCell(19);
                cell.setCellValue("");
                cell = row.createCell(20);
                Resolucion resol = new Resolucion();
                for (Iterator iter5 = exp.getEvaluacions().iterator();
                        iter5.hasNext();) {
                    Evaluacion eval = (Evaluacion) iter5.next();
                    for (Iterator iter6 = eval.getResolucions().iterator(); iter6.hasNext();) {
                        resol = (Resolucion) iter6.next();
                        if (resol.getTipo().equals("adopcion")) {
                            break;
                        }
                    }
                }
                cell.setCellValue(resol.getNumero());
                cell = row.createCell(21);
                cell.setCellValue(post.getFamilia().getEntidad().getNombre());
                cell = row.createCell(22);
                cell.setCellValue(desig.getTipoPropuesta());
                cell = row.createCell(23);
                cell.setCellValue("");//sacar de clasificacion NNA
                cell = row.createCell(24);
                cell.setCellValue(exp.getUnidad().getNombre());
                int index = 25;
                int numinf = 0;
                String fecha = "";
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
                    cell.setCellValue(inf.getPersonal().getNombre() + " "
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
                        cell = row.createCell(67); //Ultima celda de la fila (REVISAR)
                        try {
                            fecha = format.dateToString(inf.getFechaActa());
                        } catch (Exception ex) {
                        }
                        cell.setCellValue(fecha);
                        fecha = "";
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

    //PROBAR
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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\1 Registro Nacional de NNAs declarados Judicialmente en Abandono.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Nna> listanna = ServicioNna.ListaNna("prioritario");

            int i = 2;
            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
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
                cell.setCellValue(exp.getUnidad().getNombre());
                cell = row.createCell(10);
                if (nna.getActaNacimiento() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(11);
                if (nna.getIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(12);
                if (nna.getMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(13);
                if (nna.getEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(14);
                if (nna.getAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(15);
                if (nna.getSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(16);
                if (nna.getSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(17);
                if (nna.getOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(18);
                if (nna.getHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(19);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(20);
                cell.setCellValue(exp.getProcTutelar());
                cell = row.createCell(21);
                cell.setCellValue(nna.getJuzgado().getNombre());
                cell = row.createCell(22);
                cell.setCellValue(nna.getJuzgado().getDistritoJudicial());
                cell = row.createCell(23);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(24);
                cell.setCellValue(nna.getCar().getDireccion());
                cell = row.createCell(25);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(26);
                cell.setCellValue(nna.getCar().getProvincia());
                cell = row.createCell(27);
                cell.setCellValue(nna.getCar().getDistrito());
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
                if (exp.getFichaIntegral() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(32);
                cell.setCellValue(exp.getRespLegalNombre() + " "
                        + exp.getRespLegalP());
                cell = row.createCell(33);
                cell.setCellValue(exp.getRespPsicosocialNombre() + " "
                        + exp.getRespPiscosocialP());
                cell = row.createCell(34);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(35);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = nna.getDesignacions().iterator();
                        iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
                i++;
            }

            listanna = ServicioNna.ListaNna("regular");

            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
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
                cell.setCellValue(exp.getUnidad().getNombre());
                cell = row.createCell(10);
                if (nna.getActaNacimiento() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(11);
                if (nna.getIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(12);
                if (nna.getMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(13);
                if (nna.getEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(14);
                if (nna.getAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(15);
                if (nna.getSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(16);
                if (nna.getSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(17);
                if (nna.getOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(18);
                if (nna.getHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(19);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(20);
                cell.setCellValue(exp.getProcTutelar());
                cell = row.createCell(21);
                cell.setCellValue(nna.getJuzgado().getNombre());
                cell = row.createCell(22);
                cell.setCellValue(nna.getJuzgado().getDistritoJudicial());
                cell = row.createCell(23);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(24);
                cell.setCellValue(nna.getCar().getDireccion());
                cell = row.createCell(25);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(26);
                cell.setCellValue(nna.getCar().getProvincia());
                cell = row.createCell(27);
                cell.setCellValue(nna.getCar().getDistrito());
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
                if (exp.getFichaIntegral() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(32);
                cell.setCellValue(exp.getRespLegalNombre() + " "
                        + exp.getRespLegalP());
                cell = row.createCell(33);
                cell.setCellValue(exp.getRespPsicosocialNombre() + " "
                        + exp.getRespPiscosocialP());
                cell = row.createCell(34);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(35);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = nna.getDesignacions().iterator();
                        iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
                i++;
            }

            listanna = ServicioNna.ListaNna("seguimiento");

            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
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
                cell.setCellValue(exp.getUnidad().getNombre());
                cell = row.createCell(10);
                if (nna.getActaNacimiento() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(11);
                if (nna.getIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(12);
                if (nna.getMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(13);
                if (nna.getEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(14);
                if (nna.getAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(15);
                if (nna.getSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(16);
                if (nna.getSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(17);
                if (nna.getOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(18);
                if (nna.getHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(19);
                cell.setCellValue(nna.getLugarNac());
                cell = row.createCell(20);
                cell.setCellValue(exp.getProcTutelar());
                cell = row.createCell(21);
                cell.setCellValue(nna.getJuzgado().getNombre());
                cell = row.createCell(22);
                cell.setCellValue(nna.getJuzgado().getDistritoJudicial());
                cell = row.createCell(23);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(24);
                cell.setCellValue(nna.getCar().getDireccion());
                cell = row.createCell(25);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(26);
                cell.setCellValue(nna.getCar().getProvincia());
                cell = row.createCell(27);
                cell.setCellValue(nna.getCar().getDistrito());
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
                if (exp.getFichaIntegral() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(32);
                cell.setCellValue(exp.getRespLegalNombre() + " "
                        + exp.getRespLegalP());
                cell = row.createCell(33);
                cell.setCellValue(exp.getRespPsicosocialNombre() + " "
                        + exp.getRespPiscosocialP());
                cell = row.createCell(34);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(35);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = nna.getDesignacions().iterator();
                        iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\2 Registro de NNAs prioritarios.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Nna> listanna = ServicioNna.ListaNna("prioritario");

            int i = 2;
            for (Nna nna : listanna) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteNna exp = new ExpedienteNna();
                String fecha = "";
                for (Iterator iter = nna.getExpedienteNnas().iterator();
                        iter.hasNext();) {
                    exp = (ExpedienteNna) iter.next();
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
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac < mesAct) {
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
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(15);
                try {
                    fecha = format.dateToString(nna.getFechaResolConsentida());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(15);
                Prioritario prio = new Prioritario();
                for (Iterator iter2 = nna.getPrioritarios().iterator();
                        iter2.hasNext();) {
                    prio = (Prioritario) iter2.next();
                }
                cell.setCellValue(prio.getDiagnostico());
                cell = row.createCell(16);
                cell.setCellValue(prio.getGrupoReferencia());
                cell = row.createCell(17);
                if (nna.getEspecial() == 0) {
                    cell.setCellValue(prio.getCodSalud());
                } else if (nna.getMayor() == 0) {
                    cell.setCellValue(prio.getCodMayor());
                } else if (nna.getEnfermo() == 0) {
                    cell.setCellValue(prio.getCodSeguimiento());
                } else if (nna.getHermano() == 0) {
                    cell.setCellValue(prio.getCodHermano());
                } else if (nna.getAdolescente() == 0) {
                    cell.setCellValue(prio.getCodAdolescente());
                }
                cell = row.createCell(18);
                cell.setCellValue(""); //FALTA FECHA INGRESO AL GRUPO PRIORITARIO
                cell = row.createCell(19);
                cell.setCellValue(prio.getProfesional());
                cell = row.createCell(20);
                cell.setCellValue(nna.getObservaciones());
                cell = row.createCell(21);
                cell.setCellValue(prio.getComentario());
                int index = 1;
                int indexcelda = 22;
                for (Iterator iter3 = nna.getEstudioCasos().iterator();
                        iter3.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter3.next();
                    if (index > 3) {
                        break;
                    }
                    if (indexcelda == 25) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden());
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
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    cell.setCellValue(est.getNSolicitud().toString());
//Historico (revisar)
                    indexcelda++;

                    index++;
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter4 = nna.getDesignacions().iterator();
                        iter4.hasNext();) {
                    Designacion daux = (Designacion) iter4.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell = row.createCell(32);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(33);
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                cell = row.createCell(34);
                InfoFamilia inf = new InfoFamilia();
                for (Iterator iter5 = desig.getExpedienteFamilia().getFamilia().getInfoFamilias().iterator(); iter5.hasNext();) {
                    inf = (InfoFamilia) iter5.next();

                }
                cell.setCellValue(inf.getPaisRes());
                cell = row.createCell(35);
                cell.setCellValue(inf.getDepRes());
                cell = row.createCell(36);
                cell.setCellValue(desig.getExpedienteFamilia().getFamilia().getEntidad().getNombre());

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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\2.0Registro de NNAs prioritarios Mensual por Grupo de Referencia.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.createRow(11);
            Date fechaAct = new Date();
            Cell cell = row.createCell(0);
            cell.setCellValue("Reporte al " + format.dateToString(fechaAct));
            sheet = wb.getSheetAt(1);
            ArrayList<Nna> listamayor = null;
            ArrayList<Nna> listasalud = null;
            ArrayList<Nna> listahermano = null;
            ArrayList<Nna> listaespecial = null;
            ArrayList<Nna> listaadolescente = null;

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Nna> listanna = ServicioNna.ListaNna("prioritario");

            for (Nna nnaAux : listanna) {
                if (nnaAux.getMayor() == 0) {
                    listamayor.add(nnaAux);
                }
            }

            int i = 2;
            for (Nna nna : listamayor) {
                row = sheet.createRow(i);
                Prioritario prior = new Prioritario();
                for (Iterator iter = nna.getPrioritarios().iterator();
                        iter.hasNext();) {
                    prior = (Prioritario) iter.next();
                }

                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(prior.getCodMayor());
                cell = row.createCell(2);
                cell.setCellValue(nna.getNombre() + " "
                        + nna.getApellidoP());
                cell = row.createCell(3);
                cell.setCellValue(nna.getSexo());
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
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac < mesAct) {
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
                cell.setCellValue(edad);
                cell = row.createCell(6);
                cell.setCellValue(meses);
                cell = row.createCell(7);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(8);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(9);
                cell.setCellValue(nna.getObservaciones());
                int index = 1;
                int indexcelda = 10;
                for (Iterator iter2 = nna.getEstudioCasos().iterator(); iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 13) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden()); //REVISAR
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
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    cell.setCellValue(est.getNSolicitud().toString());//Historico (revisar)
                    indexcelda++;

                    index++;
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell = row.createCell(17);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(18);
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                cell = row.createCell(19);
                cell.setCellValue(desig.getNDesignacion().toString());//Historico (revisar)
                cell = row.createCell(20);
                cell.setCellValue(prior.getProfesional());

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
                Prioritario prior = new Prioritario();
                for (Iterator iter = nna.getPrioritarios().iterator();
                        iter.hasNext();) {
                    prior = (Prioritario) iter.next();
                }

                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(prior.getCodMayor());
                cell = row.createCell(2);
                cell.setCellValue(nna.getNombre());
                cell = row.createCell(3);
                cell.setCellValue(nna.getApellidoP() + " "
                        + nna.getApellidoM());
                cell = row.createCell(4);
                cell.setCellValue(nna.getSexo());
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
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac < mesAct) {
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
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(9);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(10);
                cell.setCellValue(nna.getObservaciones());
                int index = 1;
                int indexcelda = 11;
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 14) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden());
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
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    cell.setCellValue(est.getNSolicitud().toString());//Historico (revisar)
                    indexcelda++;

                    index++;
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell = row.createCell(18);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(19);
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                cell = row.createCell(20);
                cell.setCellValue(desig.getNDesignacion().toString());//Historico (revisar)
                cell = row.createCell(21);
                cell.setCellValue(prior.getProfesional());

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
                Prioritario prior = new Prioritario();
                for (Iterator iter = nna.getPrioritarios().iterator(); iter.hasNext();) {
                    prior = (Prioritario) iter.next();
                }

                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(prior.getCodMayor());
                cell = row.createCell(2);
                cell.setCellValue(nna.getNombre());
                cell = row.createCell(3);
                cell.setCellValue(nna.getApellidoP() + " " + nna.getApellidoM());
                cell = row.createCell(4);
                cell.setCellValue(nna.getSexo());
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
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac < mesAct) {
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
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(9);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(10);
                cell.setCellValue(nna.getObservaciones());
                int index = 1;
                int indexcelda = 11;
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 14) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden());
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
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    cell.setCellValue(est.getNSolicitud().toString());//Historico (revisar)
                    indexcelda++;

                    index++;
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell = row.createCell(18);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(19);
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                cell = row.createCell(20);
                cell.setCellValue(desig.getNDesignacion().toString());//Historico (revisar)
                cell = row.createCell(21);
                cell.setCellValue(prior.getProfesional());

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
                Prioritario prior = new Prioritario();
                for (Iterator iter = nna.getPrioritarios().iterator();
                        iter.hasNext();) {
                    prior = (Prioritario) iter.next();
                }

                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(prior.getCodMayor());
                cell = row.createCell(2);
                cell.setCellValue(nna.getNombre());
                cell = row.createCell(3);
                cell.setCellValue(nna.getApellidoP());
                cell = row.createCell(4);
                cell.setCellValue(nna.getSexo());
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
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac < mesAct) {
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
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(9);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(10);
                cell.setCellValue(prior.getDiagnostico());
                cell = row.createCell(11);
                cell.setCellValue(nna.getObservaciones());
                int index = 1;
                int indexcelda = 12;
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    //VER AQUI
                    if (indexcelda == 15) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden());
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
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    cell.setCellValue(est.getNSolicitud().toString());//Historico (revisar)
                    indexcelda++;

                    index++;
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter3 = nna.getDesignacions().iterator(); iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell = row.createCell(19);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(20);
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                cell = row.createCell(21);
                cell.setCellValue(desig.getNDesignacion().toString());
//Historico (revisar)
                cell = row.createCell(22);
                cell.setCellValue(prior.getProfesional());

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
                Prioritario prior = new Prioritario();
                for (Iterator iter = nna.getPrioritarios().iterator();
                        iter.hasNext();) {
                    prior = (Prioritario) iter.next();
                }
                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(prior.getCodMayor());
                cell = row.createCell(2);
                cell.setCellValue(nna.getNombre());
                cell = row.createCell(3);
                cell.setCellValue(nna.getApellidoP() + " "
                        + nna.getApellidoM());
                cell = row.createCell(4);
                cell.setCellValue(nna.getSexo());
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
                    edad = añoAct - añoNac - 1;
                }
                if (añoAct != añoNac && mesNac == mesAct) {
                    edad = añoAct - mesAct;
                }
                if (añoAct != añoNac && mesNac < mesAct) {
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
                cell.setCellValue(edad);
                cell = row.createCell(7);
                cell.setCellValue(meses);
                cell = row.createCell(8);
                cell.setCellValue(nna.getCar().getNombre());
                cell = row.createCell(9);
                cell.setCellValue(nna.getCar().getDepartamento());
                cell = row.createCell(10);
                cell.setCellValue(nna.getObservaciones());
                int index = 1;
                int indexcelda = 11;
                for (Iterator iter2 = nna.getEstudioCasos().iterator();
                        iter2.hasNext();) {
                    EstudioCaso est = (EstudioCaso) iter2.next();
                    if (index > 2) {
                        break;
                    }
                    if (indexcelda == 14) {
                        cell = row.createCell(indexcelda);
                        cell.setCellValue(est.getOrden());
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
                    cell.setCellValue(est.getExpedienteFamilia().getExpediente());
                    indexcelda++;
                    cell = row.createCell(indexcelda);
                    cell.setCellValue(est.getNSolicitud().toString());//Historico (revisar)
                    indexcelda++;

                    index++;
                }
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter3 = nna.getDesignacions().iterator();
                        iter3.hasNext();) {
                    Designacion daux = (Designacion) iter3.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell = row.createCell(18);
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(19);
                cell.setCellValue(desig.getExpedienteFamilia().getExpediente());
                cell = row.createCell(20);
                cell.setCellValue(desig.getNDesignacion().toString());//Historico (revisar)
                cell = row.createCell(21);
                cell.setCellValue(prior.getProfesional());

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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\4 Registro de Adoptantes Aptos Nacionales.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioEtapa.getListaFamilias();//Cambiar

            int i = 2;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                cell.setCellValue(exp.getExpediente());
                cell = row.createCell(2);
                cell.setCellValue(exp.getNumeroExpediente());
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
                cell.setCellValue(el.getNombre());
                cell = row.createCell(4);
                cell.setCellValue(el.getApellidoP());
                cell = row.createCell(5);
                cell.setCellValue(el.getApellidoM());
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
                cell.setCellValue(ella.getNombre());
                cell = row.createCell(9);
                cell.setCellValue(ella.getApellidoP());
                cell = row.createCell(10);
                cell.setCellValue(ella.getApellidoM());
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
                cell.setCellValue(ifam.getEstadoCivil());
                cell = row.createCell(14);
                cell.setCellValue(ifam.getPaisRes());
                cell = row.createCell(15);
                cell.setCellValue(ifam.getDepRes());
                cell = row.createCell(16);
                cell.setCellValue(ifam.getNivelSocioeconomico());
                cell = row.createCell(17);
                cell.setCellValue(exp.getTipoFamilia());
                cell = row.createCell(18);
                cell.setCellValue(exp.getNacionalidad());
                cell = row.createCell(19);
                cell.setCellValue(exp.getUnidad().getNombre());
                Resolucion resol = new Resolucion();
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    Evaluacion eval = (Evaluacion) iter3.next();
                    for (Iterator iter4 = eval.getResolucions().iterator(); iter4.hasNext();) {
                        resol = (Resolucion) iter4.next();
                        if (resol.getTipo().equals("apto")) {
                            break;
                        }
                    }
                }
                cell = row.createCell(20);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                cell.setCellValue(ifam.getExpectativaEdadMin().toString());
                cell = row.createCell(22);
                cell.setCellValue(ifam.getExpectativaEdadMax().toString());
                cell = row.createCell(23);
                cell.setCellValue(ifam.getExpectativaGenero());
                cell = row.createCell(24);
                if (ifam.getNnaIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(25);
                if (ifam.getNnaMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(26);
                if (ifam.getNnaEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(27);
                if (ifam.getNnaAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(28);
                if (ifam.getNnaSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(29);
                if (ifam.getNnaSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(30);
                if (ifam.getNnaOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                if (ifam.getNnaHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(32);
                if (ifam.getNnaEspecial() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(33);
                if (ifam.getNnaEnfermo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(34);
                if (ifam.getNnaMayor() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(35);
                if (ifam.getNnaAdolescente() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(36);
                if (ifam.getNnaHermano() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(37);
                cell.setCellValue(ifam.getnHijos().toString());
                cell = row.createCell(38);
                cell.setCellValue(ifam.getOrigenHijos());
                cell = row.createCell(39);
                if (ifam.getPuedeViajar() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(40);
                cell.setCellValue(ifam.getPredisposicionAp());
                cell = row.createCell(41);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(42);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = exp.getDesignacions().iterator(); iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(43);
                cell.setCellValue(ifam.getObservaciones());

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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\5 Registro de Adoptantes Aptos Internacional.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioEtapa.getListaFamilias();//Cambiar

            int i = 2;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                cell.setCellValue(exp.getExpediente());
                cell = row.createCell(2);
                cell.setCellValue(exp.getNumeroExpediente());
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
                cell.setCellValue(el.getNombre());
                cell = row.createCell(4);
                cell.setCellValue(el.getApellidoP());
                cell = row.createCell(5);
                cell.setCellValue(el.getApellidoM());
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
                cell.setCellValue(ella.getNombre());
                cell = row.createCell(9);
                cell.setCellValue(ella.getApellidoP());
                cell = row.createCell(10);
                cell.setCellValue(ella.getApellidoM());
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
                cell.setCellValue(ifam.getEstadoCivil());
                cell = row.createCell(14);
                cell.setCellValue(ifam.getPaisRes());
                cell = row.createCell(15);
                cell.setCellValue(ifam.getDepRes());
                cell = row.createCell(16);
                cell.setCellValue(ifam.getNivelSocioeconomico());
                cell = row.createCell(17);
                cell.setCellValue(exp.getTipoFamilia());
                cell = row.createCell(18);
                cell.setCellValue(exp.getNacionalidad());
                cell = row.createCell(19);
                cell.setCellValue(fam.getEntidad().getNombre());
                Resolucion resol = new Resolucion();
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    Evaluacion eval = (Evaluacion) iter3.next();
                    for (Iterator iter4 = eval.getResolucions().iterator(); iter4.hasNext();) {
                        resol = (Resolucion) iter4.next();
                        if (resol.getTipo().equals("apto")) {
                            break;
                        }
                    }
                }
                cell = row.createCell(20);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                cell.setCellValue(ifam.getExpectativaEdadMin().toString());
                cell = row.createCell(22);
                cell.setCellValue(ifam.getExpectativaEdadMax().toString());
                cell = row.createCell(23);
                cell.setCellValue(ifam.getExpectativaGenero());
                cell = row.createCell(24);
                if (ifam.getNnaIncesto() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(25);
                if (ifam.getNnaMental() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(26);
                if (ifam.getNnaEpilepsia() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(27);
                if (ifam.getNnaAbuso() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(28);
                if (ifam.getNnaSifilis() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(29);
                if (ifam.getNnaSeguiMedico() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(30);
                if (ifam.getNnaOperacion() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(31);
                if (ifam.getNnaHiperactivo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(32);
                if (ifam.getNnaEspecial() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(33);
                if (ifam.getNnaEnfermo() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(34);
                if (ifam.getNnaMayor() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(35);
                if (ifam.getNnaAdolescente() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(36);
                if (ifam.getNnaHermano() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(37);
                cell.setCellValue(ifam.getnHijos().toString());
                cell = row.createCell(38);
                cell.setCellValue(ifam.getOrigenHijos());
                cell = row.createCell(39);
                if (ifam.getPuedeViajar() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(40);
                cell.setCellValue(ifam.getPredisposicionAp());
                cell = row.createCell(41);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(42);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = exp.getDesignacions().iterator(); iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(43);
                cell.setCellValue(ifam.getObservaciones());

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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\6 Registro de Adopción en el Extranjero.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioEtapa.getListaFamilias();//Cambiar

            int i = 3;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                cell.setCellValue(exp.getExpediente());
                cell = row.createCell(2);
                cell.setCellValue(exp.getNumeroExpediente());
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
                cell.setCellValue(el.getNombre());
                cell = row.createCell(4);
                cell.setCellValue(el.getApellidoP());
                cell = row.createCell(5);
                cell.setCellValue(el.getApellidoM());
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
                cell.setCellValue(ella.getNombre());
                cell = row.createCell(9);
                cell.setCellValue(ella.getApellidoP());
                cell = row.createCell(10);
                cell.setCellValue(ella.getApellidoM());
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
                cell.setCellValue(ifam.getEstadoCivil());
                cell = row.createCell(14);
                cell.setCellValue(ifam.getPaisRes());
                cell = row.createCell(15);
                cell.setCellValue(ifam.getDepRes());
                cell = row.createCell(16);
                cell.setCellValue(ifam.getTelefono());
                cell = row.createCell(17);
                cell.setCellValue(ifam.getDomicilio());
                cell = row.createCell(18);
                cell.setCellValue(fam.getCorreo());
                cell = row.createCell(19);
                cell.setCellValue(fam.getEntidad().getPais());
                Resolucion resol = new Resolucion();
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    Evaluacion eval = (Evaluacion) iter3.next();
                    for (Iterator iter4 = eval.getResolucions().iterator(); iter4.hasNext();) {
                        resol = (Resolucion) iter4.next();
                        if (resol.getTipo().equals("apto")) {
                            break;
                        }
                    }
                }
                cell = row.createCell(20);
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                cell.setCellValue(ifam.getExpectativaEdadMin().toString());
                cell = row.createCell(22);
                cell.setCellValue(ifam.getExpectativaEdadMax().toString());
                cell = row.createCell(23);
                cell.setCellValue(ifam.getExpectativaGenero());
                cell = row.createCell(24);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(25);
                cell.setCellValue(ifam.getObservaciones()); //AQUI
                cell = row.createCell(38);
                cell.setCellValue(ifam.getOrigenHijos());
                cell = row.createCell(39);
                if (ifam.getPuedeViajar() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(40);
                cell.setCellValue(ifam.getPredisposicionAp());
                cell = row.createCell(41);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(42);
                Date ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = exp.getDesignacions().iterator(); iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(43);
                cell.setCellValue(ifam.getObservaciones());
                //Debemos determinar si una resolucion desfavorable fue dada despues de una designacion
                resol = new Resolucion();
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    Evaluacion eval = (Evaluacion) iter3.next();
                    for (Iterator iter4 = eval.getResolucions().iterator(); iter4.hasNext();) {
                        resol = (Resolucion) iter4.next();
                        if (resol.getTipo().equals("sinefecto") || resol.getTipo().equals("revocacion")) {
                            if (resol.getFechaResol().after(desig.getFechaConsejo())) {
                                desig = new Designacion();
                            }

                        }
                    }
                }
                if (desig.getIddesignacion() != 0) {
                    cell = row.createCell(44);
                    cell.setCellValue(desig.getNna().getNombre() + " " + desig.getNna().getApellidoP());
                    cell = row.createCell(45);
                    try {
                        fecha = format.dateToString(desig.getNna().getFechaNacimiento());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    cell = row.createCell(46);
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
                    cell = row.createCell(47);
                    cell.setCellValue(desig.getNna().getSexo());//sexo
                    cell = row.createCell(48);
                    String caracteristicas = "Presenta lo siguiente: ";
                    if (desig.getNna().getSeguiMedico() == 0) {
                        caracteristicas = caracteristicas + "";
                    }
                    if (desig.getNna().getOperacion() == 0) {
                        caracteristicas = caracteristicas + "";
                    }
                    if (desig.getNna().getHiperactivo() == 0) {
                        caracteristicas = caracteristicas + "";
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
            response.setHeader("Content-Disposition", "attachment; filename=Registro de Adopcion en el Extranjero.xlsx");
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
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\3 Registro Nacional de Exp. Nacionales e Internacionales.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(1);

            //Aquí va el query que consigue los datos de la tabla
            ArrayList<Familia> listafam = ServicioEtapa.getListaFamilias();//Cambiar

            int i = 3;
            for (Familia fam : listafam) {
                Row row = sheet.createRow(i);

                Cell cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                ExpedienteFamilia exp = new ExpedienteFamilia();
                for (Iterator iter = fam.getExpedienteFamilias().iterator(); iter.hasNext();) {
                    exp = (ExpedienteFamilia) iter.next();
                }
                cell.setCellValue(exp.getExpediente());
                cell = row.createCell(2);
                cell.setCellValue(exp.getHtFicha());
                cell = row.createCell(3);
                cell.setCellValue(exp.getnFicha());
                cell = row.createCell(4);
                cell.setCellValue(exp.getFechaIngresoFicha());
                cell = row.createCell(5);
                cell.setCellValue(exp.getHt());
                cell = row.createCell(6);
                cell.setCellValue(exp.getUnidad().getNombre());
                cell = row.createCell(7);
                cell.setCellValue(exp.getNumeroExpediente());
                cell = row.createCell(8);
                String fecha = "";
                int año = 0;
                try {
                    fecha = format.dateToString(exp.getFechaIngresoDga());
                    año = exp.getFechaIngresoDga().getYear();
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(9);
                cell.setCellValue(año);
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
                cell = row.createCell(10);
                cell.setCellValue(el.getNombre());
                cell = row.createCell(11);
                cell.setCellValue(el.getApellidoP());
                cell = row.createCell(12);
                cell.setCellValue(el.getApellidoM());
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
                cell.setCellValue(ella.getNombre());
                cell = row.createCell(15);
                cell.setCellValue(ella.getApellidoP());
                cell = row.createCell(16);
                cell.setCellValue(ella.getApellidoM());
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
                cell.setCellValue(ifam.getEstadoCivil().charAt(0));
                cell = row.createCell(19);
                cell.setCellValue(el.getPaisNac());
                cell = row.createCell(20);
                cell.setCellValue(ella.getPaisNac());
                cell = row.createCell(21);
                cell.setCellValue(ifam.getPaisRes());
                cell = row.createCell(22);
                cell.setCellValue(ifam.getDepRes());
                cell = row.createCell(23);
                cell.setCellValue(exp.getTipoFamilia());
                cell = row.createCell(24);
                cell.setCellValue(exp.getTipoListaEspera());
                cell = row.createCell(25);
                cell.setCellValue(fam.getEntidad().getNombre());
                cell = row.createCell(26);
                cell.setCellValue(exp.getNacionalidad());
                Evaluacion eval = new Evaluacion();
                Date ultfecha = new Date(10, 0, 01);
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    Evaluacion evalaux = (Evaluacion) iter3.next();
                    if (ultfecha.before(evalaux.getFechaAsignacion()) && evalaux.getTipo().equals("legal")) {
                        ultfecha = evalaux.getFechaAsignacion();
                        eval = evalaux;
                    }
                }
                cell = row.createCell(27);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(28);
                cell.setCellValue(eval.getPersonal().getNombre() + " " + eval.getPersonal().getApellidoP());
                cell = row.createCell(29);
                cell.setCellValue(eval.getResultado());
                cell = row.createCell(30);
                cell.setCellValue(eval.getObservacion());
                eval = new Evaluacion();
                ultfecha = new Date(10, 0, 01);
                for (Iterator iter4 = exp.getEvaluacions().iterator(); iter4.hasNext();) {
                    Evaluacion evalaux = (Evaluacion) iter4.next();
                    if (ultfecha.before(evalaux.getFechaAsignacion()) && evalaux.getTipo().equals("psicologica")) {
                        ultfecha = evalaux.getFechaAsignacion();
                        eval = evalaux;
                    }
                }
                cell = row.createCell(31);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(32);
                cell.setCellValue(eval.getPersonal().getNombre() + " " + eval.getPersonal().getApellidoP());
                cell = row.createCell(33);
                cell.setCellValue(eval.getResultado());
                cell = row.createCell(34);
                cell.setCellValue(eval.getObservacion());
                eval = new Evaluacion();
                ultfecha = new Date(10, 0, 01);
                for (Iterator iter5 = exp.getEvaluacions().iterator(); iter5.hasNext();) {
                    Evaluacion evalaux = (Evaluacion) iter5.next();
                    if (ultfecha.before(evalaux.getFechaAsignacion()) && evalaux.getTipo().equals("social")) {
                        ultfecha = evalaux.getFechaAsignacion();
                        eval = evalaux;
                    }
                }
                cell = row.createCell(35);
                try {
                    fecha = format.dateToString(eval.getFechaAsignacion());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(36);
                cell.setCellValue(eval.getPersonal().getNombre() + " " + eval.getPersonal().getApellidoP());
                cell = row.createCell(37);
                cell.setCellValue(eval.getResultado());
                cell = row.createCell(38);
                cell.setCellValue(eval.getObservacion());
                cell = row.createCell(39);
                cell.setCellValue("");//REVISAR FECHA DE VENCIMIENTO DEIA
                cell = row.createCell(40);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(41);
                try {
                    fecha = format.dateToString(exp.getTupa());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";//AQUI
                Resolucion resol = new Resolucion();
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    eval = (Evaluacion) iter3.next();
                    for (Iterator iter4 = eval.getResolucions().iterator(); iter4.hasNext();) {
                        resol = (Resolucion) iter4.next();
                        if (resol.getTipo().equals("apto") || resol.getTipo().equals("improcedente") || resol.getTipo().equals("fin") || resol.getTipo().equals("observado")) {
                            break;
                        }
                    }
                }
                cell = row.createCell(42);
                
                try {
                    fecha = format.dateToString(resol.getFechaResol());
                } catch (Exception ex) {
                }
                cell.setCellValue(fecha);
                fecha = "";
                cell = row.createCell(21);
                cell.setCellValue(ifam.getExpectativaEdadMin().toString());
                cell = row.createCell(22);
                cell.setCellValue(ifam.getExpectativaEdadMax().toString());
                cell = row.createCell(23);
                cell.setCellValue(ifam.getExpectativaGenero());
                cell = row.createCell(24);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(25);
                cell.setCellValue(ifam.getObservaciones());
                cell = row.createCell(38);
                cell.setCellValue(ifam.getOrigenHijos());
                cell = row.createCell(39);
                if (ifam.getPuedeViajar() == 0) {
                    cell.setCellValue("Si");
                } else {
                    cell.setCellValue("No");
                }
                cell = row.createCell(40);
                cell.setCellValue(ifam.getPredisposicionAp());
                cell = row.createCell(41);
                cell.setCellValue(exp.getEstado());
                cell = row.createCell(42);
                ultfecha = new Date(10, 0, 01);
                Designacion desig = new Designacion();
                for (Iterator iter = exp.getDesignacions().iterator(); iter.hasNext();) {
                    Designacion daux = (Designacion) iter.next();
                    if (ultfecha.before(daux.getFechaConsejo())) {
                        ultfecha = daux.getFechaConsejo();
                        desig = daux;
                    }
                }
                cell.setCellValue(desig.getFechaConsejo());
                cell = row.createCell(43);
                cell.setCellValue(ifam.getObservaciones());
                //Debemos determinar si una resolucion desfavorable fue dada despues de una designacion
                resol = new Resolucion();
                for (Iterator iter3 = exp.getEvaluacions().iterator(); iter3.hasNext();) {
                    eval = (Evaluacion) iter3.next();
                    for (Iterator iter4 = eval.getResolucions().iterator(); iter4.hasNext();) {
                        resol = (Resolucion) iter4.next();
                        if (resol.getTipo().equals("sinefecto") || resol.getTipo().equals("revocacion")) {
                            if (resol.getFechaResol().after(desig.getFechaConsejo())) {
                                desig = new Designacion();
                            }

                        }
                    }
                }
                if (desig.getIddesignacion() != 0) {
                    cell = row.createCell(44);
                    cell.setCellValue(desig.getNna().getNombre() + " " + desig.getNna().getApellidoP());
                    cell = row.createCell(45);
                    try {
                        fecha = format.dateToString(desig.getNna().getFechaNacimiento());
                    } catch (Exception ex) {
                    }
                    cell.setCellValue(fecha);
                    fecha = "";
                    cell = row.createCell(46);
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
                    cell = row.createCell(47);
                    cell.setCellValue(desig.getNna().getSexo());//sexo
                    cell = row.createCell(48);
                    String caracteristicas = "Presenta lo siguiente: ";
                    if (desig.getNna().getSeguiMedico() == 0) {
                        caracteristicas = caracteristicas + "";
                    }
                    if (desig.getNna().getOperacion() == 0) {
                        caracteristicas = caracteristicas + "";
                    }
                    if (desig.getNna().getHiperactivo() == 0) {
                        caracteristicas = caracteristicas + "";
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
            response.setHeader("Content-Disposition", "attachment; filename=Registro Nacional de Exp. Nacionales e Internacionales.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }
}
