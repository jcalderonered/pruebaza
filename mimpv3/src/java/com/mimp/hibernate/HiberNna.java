/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.hibernate;

import java.util.*;
import org.hibernate.Session;
import com.mimp.bean.*;
import com.mimp.util.*;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author john
 */
@Service("HiberNna")
@Transactional
public class HiberNna {
    
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
    
    public Nna getNna(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Nna tempNna = new Nna();

        session.beginTransaction();
        String hqlA = "FROM Nna N WHERE N.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempNna = (Nna) queryResultA;
        Hibernate.initialize(tempNna.getExpedienteNnas());
        return tempNna;
    }
    
    public Nna getNnaPostAdopcion(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Nna tempNna = new Nna();

        session.beginTransaction();
        String hqlA = "FROM Nna N WHERE N.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempNna = (Nna) queryResultA;
        Hibernate.initialize(tempNna.getExpedienteNnas());
        Hibernate.initialize(tempNna.getCar());
        Hibernate.initialize(tempNna.getJuzgado());
        return tempNna;
    }
    
    public void crearNna (Nna temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    }
    
    public void updateNna (Nna temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    }
    
    public ArrayList<Nna> ListaNna(String clasificacion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "from Nna N where N.clasificacion = :clasificacion ORDER BY N.idnna DESC";
        Query query = session.createQuery(hql);
        query.setString("clasificacion", clasificacion);
        List listaNna = query.list();
        ArrayList<Nna> allNna = new ArrayList();
        for (Iterator iter = listaNna.iterator(); iter.hasNext();) {
            Nna temp = (Nna) iter.next();
            Hibernate.initialize(temp.getDesignacions());
            Hibernate.initialize(temp.getExpedienteNnas());
            if(clasificacion.equals("prioritario")){
                Hibernate.initialize(temp.getEstudioCasos());
            }
            allNna.add(temp);
        }
        return allNna;
    }
    
    public ExpedienteNna getExpNna(long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ExpedienteNna tempExpNna = new ExpedienteNna();

        session.beginTransaction();
        String hql = "FROM ExpedienteNna E WHERE E.nna = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idNna);
        Object queryResult = query.uniqueResult();

        tempExpNna = (ExpedienteNna) queryResult;
        Hibernate.initialize(tempExpNna.getNna());
        return tempExpNna;
    }
    
    public ArrayList<ExpedienteNna> listaExpNna() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM ExpedienteNna";
        Query query = session.createQuery(hql);
        List listaExpNna = query.list();
        ArrayList<ExpedienteNna> allExpNna = new ArrayList();
        for (Iterator iter = listaExpNna.iterator(); iter.hasNext();) {
            ExpedienteNna temp = (ExpedienteNna) iter.next();

            allExpNna.add(temp);
        }
        return allExpNna;
    }
    
    public void crearExpNna (ExpedienteNna temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    }
    
    public void updateExpNna (ExpedienteNna temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    }
}
