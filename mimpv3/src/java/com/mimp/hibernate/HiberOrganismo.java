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
    
    public ArrayList<Familia> ListaFam(long idFam) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Familia F WHERE F.entidad = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFam);
        List fam = query.list();
        ArrayList<Familia> allFam = new ArrayList();

        for (Iterator iter = fam.iterator(); iter.hasNext();) {
            Familia temp = (Familia) iter.next();
            Hibernate.initialize(temp.getEntidad());
            allFam.add(temp);
            Adoptante temp2 = (Adoptante) iter.next();
            temp2.getInfoFamilia().getFamilia().getEntidad().getIdentidad();
        }
        

        return allFam;
    }
}
