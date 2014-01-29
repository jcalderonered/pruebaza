/*
 * To change this license header, choose License Headers in Project 
 Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.hibernate;

import com.mimp.bean.Adoptante;
import com.mimp.bean.ExpedienteFamilia;
import com.mimp.bean.InfoFamilia;
import com.mimp.bean.Organismo;
import com.mimp.bean.PostAdopcion;
import com.mimp.bean.Resolucion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service("HiberEtapa")
@Transactional
public class HiberReporte {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public ArrayList<Organismo> ReporteListaOrganismos() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Organismo";
        Query query = session.createQuery(hql);
        List organismos = query.list();
        ArrayList<Organismo> allOrganismos = new ArrayList();
        for (Iterator iter = organismos.iterator(); iter.hasNext();) {
            Organismo temp = (Organismo) iter.next();
            Hibernate.initialize(temp.getEntidad());
            Hibernate.initialize(temp.getRepresentantes());
            allOrganismos.add(temp);
        }
        return allOrganismos;
    }

    public ArrayList<PostAdopcion> ReporteListaPostAdopcion() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<PostAdopcion> allPostAdopcion = new ArrayList();
        ArrayList<Resolucion> allResoluciones = new ArrayList();

        String hql1 = "FROM Resolucion R WHERE R.tipo = :tipo";
        Query query1 = session.createQuery(hql1);
        query1.setString("tipo", "adopcion");
        List tempResoluciones = query1.list();

        for (Iterator iter = tempResoluciones.iterator(); iter.hasNext();) {
            Resolucion temp = (Resolucion) iter.next();
            Hibernate.initialize(temp.getEvaluacion().getExpedienteFamilia());
            allResoluciones.add(temp);
        }

        String hql2 = "FROM PostAdopcion PA ORDER BY PA.idpostAdopcion ASC";
        Query query2 = session.createQuery(hql2);
        List tempPost = query2.list();

        if (!allResoluciones.isEmpty() && !tempPost.isEmpty()) {
            for (Iterator iter = tempPost.iterator(); iter.hasNext();) {
                PostAdopcion temp2 = (PostAdopcion) iter.next();
                for (Resolucion resol : allResoluciones) {
                    if (resol.getFechaResol().equals(temp2.getFechaResolucion())) {
                        Set<ExpedienteFamilia> tempExpediente = new HashSet<ExpedienteFamilia>(0);
                        tempExpediente.add(resol.getEvaluacion().getExpedienteFamilia());
                        Hibernate.initialize(temp2.getFamilia());
                        Hibernate.initialize(temp2.getFamilia().getInfoFamilias());
                        Set<InfoFamilia> tempInfoFam = new HashSet<InfoFamilia>(0);
                        for (InfoFamilia infofam : temp2.getFamilia().getInfoFamilias()) {
                            String hql3 = "from Adoptante A where A.idinfo_familia = :idinfofam";
                            Query query3 = session.createQuery(hql3);
                            query3.setLong("idinfofam",
                                    infofam.getIdinfoFamilia());
                            List adoptantes = query3.list();
                            Set<Adoptante> tempAdoptantes = new HashSet<Adoptante>(0);
                            for (Iterator iter9 = adoptantes.iterator();
                                    iter9.hasNext();) {
                                Adoptante adopTemp = (Adoptante) iter9.next();
                                tempAdoptantes.add(adopTemp);
                            }
                            infofam.setAdoptantes(tempAdoptantes);
                            //También puede incluirse hijo y residente
                            tempInfoFam.add(infofam);
                        }
                        temp2.getFamilia().setInfoFamilias(tempInfoFam);
                        Hibernate.initialize(temp2.getInformePostAdoptivos());
                        temp2.getFamilia().setExpedienteFamilias(tempExpediente);

                    }
                }
                allPostAdopcion.add(temp2);
            }
        }

        return allPostAdopcion;
    }
}
