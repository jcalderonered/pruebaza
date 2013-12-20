/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.util;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class timeStampFormat {
    
    public Date stringToTime (String temp){
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MMM/yyyy hh:mm", new Locale("ES"));
        
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
    
    public Timestamp stringToTimestamp (String temp){
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        Date utiDate = new Date();
        try {
            utiDate = dateFormater.parse(temp);
        } catch (ParseException ex) {
            Logger.getLogger(dateFormat.class.getName()).log(Level.SEVERE, null, ex);
        }  
        long time = utiDate.getTime();
        
        Timestamp ts = new Timestamp(time);
        return ts;
        
    }
    
    public String TimeToString (Date temp){
        SimpleDateFormat fromsql = new SimpleDateFormat("dd/MMM/yyyy HH:mm",new Locale("ES"));
        //SimpleDateFormat mystring = new SimpleDateFormat("dd/MM/yyyy");
        
        String fecha = fromsql.format(temp).toUpperCase();
    
        return fecha;
    }
    
    public String FechaTurno (Date temp){
        SimpleDateFormat fromsql = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat mystring = new SimpleDateFormat("dd/MM/yyyy");
        
        String fecha = fromsql.format(temp);
    
        return fecha;
    }
    
    public String DateToString (Date temp){
        SimpleDateFormat fromsql = new SimpleDateFormat("dd-MMM-yyyy",new Locale("ES"));
        //SimpleDateFormat mystring = new SimpleDateFormat("dd/MM/yyyy");
        
        String fecha = fromsql.format(temp).toUpperCase();
    
        return fecha;
    }
    
    public String HourToString (Timestamp temp){
        SimpleDateFormat fromsql = new SimpleDateFormat("HH:mm",new Locale("ES"));
        //SimpleDateFormat mystring = new SimpleDateFormat("dd/MM/yyyy");
        
        String fecha = fromsql.format(temp);
    
        return fecha;
    }
    
    
    
    
}
