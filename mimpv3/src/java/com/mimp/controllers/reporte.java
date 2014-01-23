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
import java.io.File;
import java.io.FileInputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileOutputStream;
import java.io.IOException;
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
    
    @Resource(name = "HiberPersonal")
    private HiberPersonal ServicioPersonal = new HiberPersonal();

    @RequestMapping("/Reportes/UAs/Ejemplo")
    public ModelAndView ReporteUAs(ModelMap map, HttpSession session, HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        //Create sheet as 'newsheet'
        Sheet sh1 = wb.createSheet("newsheet");
        //Create sheet as '2ndsheet'
        Sheet sh2 = wb.createSheet("2ndsheet");

        //Creamos información de prueba (
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        data.put("1", new Object[]{"Emp No.", "Name", "Salary"});
        data.put("2", new Object[]{1d, "John", 1500000d});
        data.put("3", new Object[]{2d, "Sam", 800000d});
        data.put("4", new Object[]{3d, "Dean", 700000d});

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sh1.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=UnidadesAdopcion.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        try {
            FileInputStream file = new FileInputStream(new File("C:\\test.xls"));
        } catch (Exception ex) {

        }
        return new ModelAndView("/login", map);
    }

    @RequestMapping("/Reportes/UAs")
    public ModelAndView Ejemplo(ModelMap map, HttpSession session, HttpServletResponse response) {
//        Personal usuario = (Personal) session.getAttribute("usuario");
//        if (usuario == null) {
//            String mensaje = "La sesión ha finalizado. Favor identificarse nuevamente";
//            map.addAttribute("mensaje", mensaje);
//            return new ModelAndView("login", map);
//        }
        Workbook wb = new XSSFWorkbook();
        try {
            InputStream inp = new FileInputStream("C:\\Users\\User\\Desktop\\Plantillas\\UnidadesAdopción.xlsx");
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            ArrayList<Unidad> listaUA = ServicioPersonal.ListaUa();
            int i=1;
            for (Unidad ua : listaUA) {
                Row row = sheet.createRow(i);
                
                Cell cell = row.createCell(0);
                cell.setCellValue(ua.getIdunidad());
                cell = row.createCell(1);
                cell.setCellValue(ua.getNombre());
                cell = row.createCell(2);
                cell.setCellValue(ua.getDireccion());
                cell = row.createCell(3);
                cell.setCellValue(ua.getDepartamento());
                cell = row.createCell(4);
                cell.setCellValue(ua.getProvincia());
                cell = row.createCell(5);
                cell.setCellValue(ua.getDistrito());
                cell = row.createCell(6);
                cell.setCellValue(ua.getCompetenciaRegional());
                cell = row.createCell(7);
                cell.setCellValue(ua.getCorreo());
                cell = row.createCell(8);
                
                i++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=UnidadesAdopcion.xlsx");
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return new ModelAndView("/login", map);
    }
}
