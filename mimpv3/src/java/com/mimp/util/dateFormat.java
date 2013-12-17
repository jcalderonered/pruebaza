/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.util;

//import com.test.entidad.user;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author john
 */
public class dateFormat {
        
        
        
    public Date stringToDate (String temp){
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
        
        Date utiDate = new Date();
        try {
            utiDate = dateFormater.parse(temp);
        } catch (ParseException ex) {
            Logger.getLogger(dateFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sql = new java.sql.Date(utiDate.getTime());
        System.out.print(sql);
        return sql;
    }
    
    public String dateToString (Date temp){
        SimpleDateFormat fromsql = new SimpleDateFormat("dd/MMM/yyyy",new Locale("ES"));
        //SimpleDateFormat mystring = new SimpleDateFormat("dd/MM/yyyy");
        
        String fecha = fromsql.format(temp);
    
        return fecha;
    }
    
}
