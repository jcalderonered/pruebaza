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

@Service("HiberOrganismo")
@Transactional
public class HiberOrganismo {
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    
    public void CambiaPass(Entidad entidad) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(entidad);
    }
    
    public ArrayList<Familia> ListaFam(long idEnt) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Familia F WHERE F.entidad = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idEnt);
        List fam = query.list();
        ArrayList<Familia> allFam = new ArrayList();

        for (Iterator iter = fam.iterator(); iter.hasNext();) {
            Familia temp = (Familia) iter.next();
            Hibernate.initialize(temp.getEntidad());            
            allFam.add(temp);
        }
        

        return allFam;
    }
    
    public ArrayList<Adoptante> ListaAdopPorEnt(long idEnt) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Adoptante";
        Query query = session.createQuery(hql);       
        List Adop = query.list();
        ArrayList<Adoptante> allAdop = new ArrayList();

        for (Iterator iter = Adop.iterator(); iter.hasNext();) {
            Adoptante temp = (Adoptante) iter.next();                     
            Hibernate.initialize(temp.getInfoFamilia());            
            if (temp.getInfoFamilia().getFamilia().getEntidad().getIdentidad() == idEnt){
            Hibernate.initialize(temp.getInfoFamilia().getFamilia().getEntidad());
            allAdop.add(temp);
            }
        }        
        return allAdop;
    }
    
    public ArrayList<ExpedienteFamilia> ListaExpFamPorEnt(long idEnt) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM ExpedienteFamilia";
        Query query = session.createQuery(hql);       
        List Exp = query.list();
        ArrayList<ExpedienteFamilia> allExp = new ArrayList();

        for (Iterator iter = Exp.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();                     
            Hibernate.initialize(temp.getFamilia());            
            if (temp.getFamilia().getEntidad().getIdentidad() == idEnt){
            Hibernate.initialize(temp.getFamilia().getEntidad());
            allExp.add(temp);
            
            }
        }        
        return allExp;
    }
    
    public Adoptante LaAdopPorIdFam(long idInfo) {

        Session session = sessionFactory.getCurrentSession();
        Adoptante LaAdop = new Adoptante();

        session.beginTransaction();
        String hqlO = "FROM Adoptante A WHERE A.infoFamilia = :id and A.sexo = 'F'";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", idInfo);
        Object queryResultA = queryO.uniqueResult();

        LaAdop = (Adoptante) queryResultA;
        //Hibernate.initialize(LaAdop.getInfoFamilia());        
        return LaAdop;
    }
    
}
