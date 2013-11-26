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
@Service("Helper")
@Transactional
public class Helper {
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    
  public Object usuario (String login, String pass){
      
      Session session = sessionFactory.getCurrentSession();
      Personal personal = new Personal();
      Representante representante = new Representante();
      Autoridad autoridad = new Autoridad();
      Familia familia = new Familia();
      
      //ArrayList temp = new ArrayList();
      
      
      
        String hql = "FROM Personal P WHERE P.user = '" + login + "' and P.pass = '" + pass + "'";
        Query query = session.createQuery(hql);
        Object queryResult = query.uniqueResult();  
        personal = (Personal) queryResult;
        //temp.add(results);
      return personal;
  }
  
  public List<Personal> listaPersonal(){
            Session session = sessionFactory.getCurrentSession();
  
            String hql = "FROM Personal";
            Query query = session.createQuery(hql);
            List<Personal> temp = (List<Personal>) query.list();
            return temp;
  }
  
}
