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
@Service("HiberEtapa")
@Transactional
public class HiberEtapa {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    
public ArrayList<Familia> getListaFamilias () {
        
         Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
         
        String hql = "FROM Familia F WHERE F.habilitado = 0";
        Query query = session.createQuery(hql);
        List familias = query.list();
        ArrayList<Familia> allFamilias = new ArrayList();
        for (Iterator iter = familias.iterator(); iter.hasNext();) {
            Familia temp = (Familia) iter.next();
            Hibernate.initialize(temp.getAsistenciaFRs());
            
            String hql2 = "FROM FormularioSesion F WHERE F.familia = :idFamilia ORDER BY F.fechaSol DESC ";
            Query query2 = session.createQuery(hql2);
            query2.setLong("idFamilia", temp.getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult = query2.uniqueResult();
            FormularioSesion tempFs = (FormularioSesion) queryResult;
            Hibernate.initialize(tempFs.getAsistentes());
            Set<FormularioSesion> listFs = new HashSet<FormularioSesion>();
            listFs.add(tempFs);
            temp.setFormularioSesions(listFs);
            allFamilias.add(temp);
        } 
    
        return allFamilias;
    
    }
    
    public Familia getFamilia(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Familia fam = new Familia();

        session.beginTransaction();
        String hqlA = "FROM Familia F WHERE F.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        fam = (Familia) queryResultA;
        
        return fam;
    }
    
    public void UpdateFamilia (Familia temp){
            
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    }
    
     public ArrayList<ExpedienteFamilia> ListaExpedientes(String nacionalidad,String estado) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "from ExpedienteFamilia EF where EF.nacionalidad = :nacionalidad and EF.estado = :estado";
        Query query = session.createQuery(hql);
        query.setString("nacionalidad", nacionalidad);
        query.setString("estado", estado);
        List autoridades = query.list();
        ArrayList<ExpedienteFamilia> allExpedienteFamilia = new ArrayList();
        for (Iterator iter = autoridades.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getEvaluacions());
            allExpedienteFamilia.add(temp);
        }
        return allExpedienteFamilia;
    }

}
