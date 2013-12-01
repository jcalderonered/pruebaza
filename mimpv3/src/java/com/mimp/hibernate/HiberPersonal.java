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
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("HiberPersonal")
@Transactional
public class HiberPersonal {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void InsertAut(Entidad ent, Autoridad aut) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(ent);

        aut.setEntidad(ent);
        ent.getAutoridads().add(aut);

        session.save(aut);

        // session.getTransaction().commit(); 
    }
    
    public void UpdateAut(Entidad ent, Autoridad aut){
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        
        session.update(ent);
        
        session.update(aut);
    
    
    }
    public Autoridad getAutoridad(int id) {
        Session session = sessionFactory.getCurrentSession();
        Autoridad aut = new Autoridad();
        
        session.beginTransaction();
        String hqlA = "FROM Autoridad A WHERE A.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setInteger("id", id);
        Object queryResultA = queryA.uniqueResult();
        
        aut = (Autoridad) queryResultA;
        Hibernate.initialize(aut.getEntidad());
        return aut;
    }

    public ArrayList<Autoridad> ListaAutoridades() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Autoridad";
        Query query = session.createQuery(hql);
        List autoridades = query.list();
        ArrayList<Autoridad> allAutoridades = new ArrayList();
        for (Iterator iter = autoridades.iterator(); iter.hasNext();) {
            Autoridad temp = (Autoridad) iter.next();
            Hibernate.initialize(temp.getEntidad());
            allAutoridades.add(temp);
        }
        return allAutoridades;
    }

}
