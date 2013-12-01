/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.hibernate;
import java.util.*;
import org.hibernate.Session;
import com.mimp.bean.*;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author john
 */
@Service("HiberMain")
@Transactional
public class HiberMain {
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    
  public ArrayList<Object> usuario (String user, String pass){
      
      Session session = sessionFactory.getCurrentSession();
      session.beginTransaction();  
      Personal personal = new Personal();
      Representante representante = new Representante();
      Autoridad autoridad = new Autoridad();
      Familia familia = new Familia();
      
        ArrayList<Object> temp = new ArrayList<Object>();
      
      
      
        String hqlP = "FROM Personal P WHERE P.user = :usuario and P.pass = :password ";//:=login
        String hqlF = "FROM Familia F WHERE F.user = :usuario and F.pass = :password ";
        String hqlR = "FROM Representante R WHERE R.user = :usuario and R.pass = :password ";
        String hqlA = "FROM Autoridad A WHERE A.user = :usuario and A.pass = :password ";
        
        Query queryP = session.createQuery(hqlP);
        queryP.setString("usuario", user);
        queryP.setString("password", pass);
        Object queryResultP = queryP.uniqueResult();  
        
        Query queryF = session.createQuery(hqlF);
        queryF.setString("usuario", user);
        queryF.setString("password", pass);
        Object queryResultF = queryF.uniqueResult();  
        
        Query queryR = session.createQuery(hqlR);
        queryR.setString("usuario", user);
        queryR.setString("password", pass);
        Object queryResultR = queryR.uniqueResult();  
        
        Query queryA = session.createQuery(hqlA);
        queryA.setString("usuario", user);
        queryA.setString("password", pass);
        Object queryResultA = queryA.uniqueResult();  
        
        //session.getTransaction().commit(); 
        
        if (queryResultP != null){
            personal = (Personal) queryResultP;
            temp.add("personal");
            temp.add(queryResultP);
            return temp;
        }else if (queryResultF != null){
            familia = (Familia) queryResultF;
            temp.add("familia");
            temp.add(queryResultF);
            return temp;
        }else if (queryResultR != null){
            representante = (Representante) queryResultR;
            temp.add("representante");
            temp.add(queryResultR);
            return temp;
        }else if (queryResultA != null){
            autoridad = (Autoridad) queryResultA;
            temp.add("autoridad");
            temp.add(queryResultA);
            return temp;
        }else {
            
            temp.add("none");
            return temp;
        }
        //temp.add(results);
      
  }
  
  
}
