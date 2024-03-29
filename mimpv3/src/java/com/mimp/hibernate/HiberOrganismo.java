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

    @Resource(name = "sessionFactory")
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

    public ArrayList<Familia> ListaFamDeEnt(long idEnt) {

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
            Hibernate.initialize(temp.getExpedienteFamilias());

            String hql2 = "FROM InfoFamilia IF WHERE IF.familia = :idFam order by IF.idinfoFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("idFam", temp.getIdfamilia());
            query2.setMaxResults(1);
            List infoFam = query2.list();

            Set<InfoFamilia> infoFamilias = new HashSet<InfoFamilia>(0);
            for (Iterator iter2 = infoFam.iterator(); iter2.hasNext();) {
                InfoFamilia tempInf = (InfoFamilia) iter2.next();
                Hibernate.initialize(tempInf.getAdoptantes());
                infoFamilias.add(tempInf);
            }
            temp.setInfoFamilias(infoFamilias);
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
            try {
                if (temp.getInfoFamilia().getFamilia().getEntidad().getIdentidad() == idEnt) {
                    Hibernate.initialize(temp.getInfoFamilia().getFamilia().getEntidad());
                    allAdop.add(temp);
                }
            } catch (Exception ex) {
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
            try {
                if (temp.getFamilia().getEntidad().getIdentidad() == idEnt) {
                    Hibernate.initialize(temp.getFamilia().getEntidad());
                    allExp.add(temp);

                }
            } catch (Exception ex) {
            }
        }
        return allExp;
    }

    public Adoptante AdopPorIdFamPorSex(long idInfo, String sexo) {

        Session session = sessionFactory.getCurrentSession();
        Adoptante AdopPorIdFamPorSex = new Adoptante();

        session.beginTransaction();
        String hqlO = "FROM Adoptante A WHERE A.infoFamilia = :id and A.sexo = :sexo";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", idInfo);
        queryO.setString("sexo", sexo);
        Object queryResultA = queryO.uniqueResult();

        if (queryResultA != null) {
            AdopPorIdFamPorSex = (Adoptante) queryResultA;
            Hibernate.initialize(AdopPorIdFamPorSex.getInfoFamilia());
        }
        return AdopPorIdFamPorSex;
    }

    public InfoFamilia InfoFamilia(long idInfo) {

        Session session = sessionFactory.getCurrentSession();
        InfoFamilia InfoFamilia = new InfoFamilia();

        session.beginTransaction();
        String hqlO = "FROM InfoFamilia I WHERE I.id = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", idInfo);
        Object queryResultA = queryO.uniqueResult();

        InfoFamilia = (InfoFamilia) queryResultA;
        //Hibernate.initialize(LaAdop.getInfoFamilia());        
        return InfoFamilia;
    }

    public ExpedienteFamilia ExpPorIDFamilia(long idFam) {

        Session session = sessionFactory.getCurrentSession();
        ExpedienteFamilia expedienteFamilia = new ExpedienteFamilia();

        session.beginTransaction();
        String hqlO = "FROM ExpedienteFamilia E WHERE E.familia = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", idFam);
        Object queryResultA = queryO.uniqueResult();

        expedienteFamilia = (ExpedienteFamilia) queryResultA;
        Hibernate.initialize(expedienteFamilia.getFamilia());
        Hibernate.initialize(expedienteFamilia.getUnidad().getNombre());
        Hibernate.initialize(expedienteFamilia.getFamilia().getEntidad().getNombre());
        Hibernate.initialize(expedienteFamilia.getEvaluacions());
        Hibernate.initialize(expedienteFamilia.getDesignacions());
        return expedienteFamilia;
    }

    public Familia FamiliaPorID(long idFam) {

        Session session = sessionFactory.getCurrentSession();
        Familia familia = new Familia();

        session.beginTransaction();
        String hqlO = "FROM Familia F WHERE F.id = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", idFam);
        Object queryResultA = queryO.uniqueResult();

        familia = (Familia) queryResultA;
        Hibernate.initialize(familia.getEntidad());
        Hibernate.initialize(familia.getExpedienteFamilias());

        return familia;
    }

    public Evaluacion EvalPorIDEval(long idEval) {

        Session session = sessionFactory.getCurrentSession();
        Evaluacion eval = new Evaluacion();

        session.beginTransaction();
        String hqlO = "FROM Evaluacion E WHERE E.id = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", idEval);
        Object queryResultA = queryO.uniqueResult();

        eval = (Evaluacion) queryResultA;
        Hibernate.initialize(eval.getEvalLegals());
        Hibernate.initialize(eval.getResolucions());

        return eval;
    }

}
