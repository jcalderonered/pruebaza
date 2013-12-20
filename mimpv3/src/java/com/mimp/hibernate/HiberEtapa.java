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
    
    
}
