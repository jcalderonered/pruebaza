/*
 * To change this license header, choose License Headers in Project 
 Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.hibernate;

import com.mimp.bean.Adoptante;
import com.mimp.bean.Designacion;
import com.mimp.bean.EstudioCaso;
import com.mimp.bean.Evaluacion;
import com.mimp.bean.ExpedienteFamilia;
import com.mimp.bean.ExpedienteNna;
import com.mimp.bean.Familia;
import com.mimp.bean.FormularioSesion;
import com.mimp.bean.InfoFamilia;
import com.mimp.bean.InformePostAdoptivo;
import com.mimp.bean.Nna;
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
@Service("HiberReporte")
@Transactional
public class HiberReporte {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public ArrayList<Organismo> ReporteOrganismo() {

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

    public ArrayList<PostAdopcion> ReportePostAdopcion() {

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
            Hibernate.initialize(temp.getEvaluacion().getExpedienteFamilia().getUnidad());
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
                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                        tempExpFam = resol.getEvaluacion().getExpedienteFamilia();
                        Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
                        Hibernate.initialize(tempExpFam.getEvaluacions());
                        for (Evaluacion evaluacion : tempExpFam.getEvaluacions()) {
                            if(evaluacion.getTipo().equals("informe")){
                                    Hibernate.initialize(evaluacion.getResolucions());
                                    tempEvaluaciones.add(evaluacion);
                            }
                        }
                        tempExpFam.setEvaluacions(tempEvaluaciones);
                        
                        tempExpediente.add(tempExpFam);
                        Hibernate.initialize(temp2.getFamilia().getEntidad());
                        Hibernate.initialize(temp2.getFamilia().getInfoFamilias());
                        Set<InfoFamilia> tempInfoFam = new HashSet<InfoFamilia>(0);
                        for (InfoFamilia infofam : temp2.getFamilia().getInfoFamilias()) {
                            String hql3 = "from Adoptante A where A.infoFamilia = :idinfofam";
                            Query query3 = session.createQuery(hql3);
                            query3.setLong("idinfofam", infofam.getIdinfoFamilia());
                            List adoptantes = query3.list();
                            Set<Adoptante> tempAdoptantes = new HashSet<Adoptante>(0);
                            for (Iterator iter9 = adoptantes.iterator(); iter9.hasNext();) {
                                Adoptante adopTemp = (Adoptante) iter9.next();
                                tempAdoptantes.add(adopTemp);
                            }
                            infofam.setAdoptantes(tempAdoptantes);
                            //También puede incluirse hijo y residente
                            tempInfoFam.add(infofam);
                        }
                        temp2.getFamilia().setInfoFamilias(tempInfoFam);
                        Hibernate.initialize(temp2.getInformePostAdoptivos());
                        Set<InformePostAdoptivo> listaInformes = new HashSet<InformePostAdoptivo>(0);
                        for (InformePostAdoptivo informePostAdoptivo : temp2.getInformePostAdoptivos()) {
                            Hibernate.initialize(informePostAdoptivo.getPersonal());
                            listaInformes.add(informePostAdoptivo);
                        }
                        temp2.setInformePostAdoptivos(listaInformes);
                        temp2.getFamilia().setExpedienteFamilias(tempExpediente);

                    }
                }
                allPostAdopcion.add(temp2);
            }
        }

        return allPostAdopcion;
    }
    
    public Designacion getDesigNnaFam(Long idNna, Long idExp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Designacion tempDesig = new Designacion();
        String hql = "from Designacion D where D.nna = :idNna and D.expedienteFamilia = :idExp and D.aceptacionConsejo = :acep";
        Query query = session.createQuery(hql);
        query.setLong("idNna", idNna);
        query.setLong("idExp", idExp);
        query.setShort("acep", Short.parseShort("2"));
        Object queryResult = query.uniqueResult();
        tempDesig = (Designacion) queryResult;
        
        return tempDesig;
    }
    public ArrayList<Nna> ListaNnaReporte(String clasificacion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "from Nna N where N.clasificacion = :clasificacion ORDER BY N.idnna DESC";
        Query query = session.createQuery(hql);
        query.setString("clasificacion", clasificacion);
        List listaNna = query.list();
        ArrayList<Nna> allNna = new ArrayList();
        for (Iterator iter = listaNna.iterator(); iter.hasNext();) {
            Nna temp = (Nna) iter.next();
            Hibernate.initialize(temp.getJuzgado());
            Hibernate.initialize(temp.getCar());
            Hibernate.initialize(temp.getDesignacions());
            Hibernate.initialize(temp.getExpedienteNnas());
            Set<ExpedienteNna> listaExp = new HashSet<ExpedienteNna>(0);
            if(!temp.getExpedienteNnas().isEmpty()){
                for (ExpedienteNna nnaExp : temp.getExpedienteNnas()) {
                        Hibernate.initialize(nnaExp.getUnidad());
                        listaExp.add(nnaExp);
                }
                temp.setExpedienteNnas(listaExp);
            }
            
            if(clasificacion.equals("prioritario")){
                Hibernate.initialize(temp.getEstudioCasos());
                
                
                Set<EstudioCaso> listaEst = new HashSet<EstudioCaso>(0);
                if(!temp.getEstudioCasos().isEmpty()){
                for (EstudioCaso estudioCaso : temp.getEstudioCasos()) {
                    Hibernate.initialize(estudioCaso.getExpedienteFamilia());
                    listaEst.add(estudioCaso);
                }
                temp.setEstudioCasos(listaEst);
                }
                
                Set<Designacion> listaDesignacions = new HashSet<Designacion>(0);
                if(!temp.getDesignacions().isEmpty()){
                for (Designacion desig : temp.getDesignacions()) {
                    Hibernate.initialize(desig.getExpedienteFamilia().getFamilia().getEntidad());
                    Hibernate.initialize(desig.getExpedienteFamilia().getFamilia().getInfoFamilias());
                    listaDesignacions.add(desig);
                }
                temp.setDesignacions(listaDesignacions);
                }
            }
            allNna.add(temp);
        }
        return allNna;
    }
    public ArrayList<Nna> ListaNnaPriorita(String clasificacion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "from Nna N where N.clasificacion = :clasificacion ORDER BY N.idnna DESC";
        Query query = session.createQuery(hql);
        query.setString("clasificacion", clasificacion);
        List listaNna = query.list();
        ArrayList<Nna> allNna = new ArrayList();
        for (Iterator iter = listaNna.iterator(); iter.hasNext();) {
            Nna temp = (Nna) iter.next();
            Hibernate.initialize(temp.getJuzgado());
            Hibernate.initialize(temp.getCar());
            Hibernate.initialize(temp.getDesignacions());
            Hibernate.initialize(temp.getExpedienteNnas());
            Set<ExpedienteNna> listaExp = new HashSet<ExpedienteNna>(0);
            if(!temp.getExpedienteNnas().isEmpty()){
                for (ExpedienteNna nnaExp : temp.getExpedienteNnas()) {
                        Hibernate.initialize(nnaExp.getUnidad());
                        listaExp.add(nnaExp);
                }
                temp.setExpedienteNnas(listaExp);
            }
            
            if(clasificacion.equals("prioritario")){
                Hibernate.initialize(temp.getEstudioCasos());
                
                
                Set<EstudioCaso> listaEst = new HashSet<EstudioCaso>(0);
                if(!temp.getEstudioCasos().isEmpty()){
                for (EstudioCaso estudioCaso : temp.getEstudioCasos()) {
                    Hibernate.initialize(estudioCaso.getExpedienteFamilia());
                    listaEst.add(estudioCaso);
                }
                temp.setEstudioCasos(listaEst);
                }
                
                Set<Designacion> listaDesignacions = new HashSet<Designacion>(0);
                if(!temp.getDesignacions().isEmpty()){
                for (Designacion desig : temp.getDesignacions()) {
                    Hibernate.initialize(desig.getExpedienteFamilia().getFamilia().getEntidad());
                    Hibernate.initialize(desig.getExpedienteFamilia().getFamilia().getInfoFamilias());
                    listaDesignacions.add(desig);
                }
                temp.setDesignacions(listaDesignacions);
                }
            }
            allNna.add(temp);
        }
        return allNna;
    }
    
    public ArrayList<Familia> getListaFamilias () {
        
         Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
         
        String hql = "FROM Familia F WHERE F.habilitado = 0";
        Query query = session.createQuery(hql);
        List familias = query.list();
        ArrayList<Familia> allFamilias = new ArrayList();
        for (Iterator iter = familias.iterator(); iter.hasNext();) {
            Familia temp = (Familia) iter.next();
            Hibernate.initialize(temp.getEntidad());
            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia ORDER BY EF.idexpedienteFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("idFamilia", temp.getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult2 = query2.uniqueResult();
            if (queryResult2 != null){
            ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
            Hibernate.initialize(tempExp.getUnidad());
            Hibernate.initialize(tempExp.getEvaluacions());
            Set<Evaluacion> listEval = new HashSet<Evaluacion>();
                for (Evaluacion eval : tempExp.getEvaluacions()) {
                    Hibernate.initialize(eval.getPersonal());
                    //if(eval.getTipo().equals("legal")) Hibernate.initialize(eval.getResolucions());
                    listEval.add(eval);
                }
            tempExp.setEvaluacions(listEval);
            Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
            listExp.add(tempExp);
            temp.setExpedienteFamilias(listExp);
            }
            
            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
            Query query3 = session.createQuery(hql3);
            query3.setLong("idFamilia", temp.getIdfamilia());
            query3.setMaxResults(1);
            Object queryResult3 = query3.uniqueResult();
            if (queryResult3 != null){
            InfoFamilia infoFam = (InfoFamilia) queryResult3;
            Hibernate.initialize(infoFam.getAdoptantes());
            Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
            listInf.add(infoFam);
            temp.setInfoFamilias(listInf);
            }
            if (query2.uniqueResult() != null && query3.uniqueResult() != null){
            allFamilias.add(temp);
            }
        } 
    
        return allFamilias;
    
    }
    
    public ArrayList<Familia> getListaFamiliasNacionales() {
        
         Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
         
        String hql = "FROM Familia F WHERE F.habilitado = 0";
        Query query = session.createQuery(hql);
        List familias = query.list();
        ArrayList<Familia> allFamilias = new ArrayList();
        for (Iterator iter = familias.iterator(); iter.hasNext();) {
            Familia temp = (Familia) iter.next();
            Hibernate.initialize(temp.getEntidad());
            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia and EF.nacionalidad = :nacionalidad ORDER BY EF.idexpedienteFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("idFamilia", temp.getIdfamilia());
            query2.setString("nacionalidad", "nacional");
            query2.setMaxResults(1);
            Object queryResult2 = query2.uniqueResult();
            if (queryResult2 != null){
            ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
            Hibernate.initialize(tempExp.getUnidad());
            Hibernate.initialize(tempExp.getEvaluacions());
            Set<Evaluacion> listEval = new HashSet<Evaluacion>();
                for (Evaluacion eval : tempExp.getEvaluacions()) {
                    Hibernate.initialize(eval.getPersonal());
                    //if(eval.getTipo().equals("legal")) Hibernate.initialize(eval.getResolucions());
                    listEval.add(eval);
                }
            tempExp.setEvaluacions(listEval);
            Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
            listExp.add(tempExp);
            temp.setExpedienteFamilias(listExp);
            }
            
            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
            Query query3 = session.createQuery(hql3);
            query3.setLong("idFamilia", temp.getIdfamilia());
            query3.setMaxResults(1);
            Object queryResult3 = query3.uniqueResult();
            if (queryResult3 != null){
            InfoFamilia infoFam = (InfoFamilia) queryResult3;
            Hibernate.initialize(infoFam.getAdoptantes());
            Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
            listInf.add(infoFam);
            temp.setInfoFamilias(listInf);
            }
            if (query2.uniqueResult() != null && query3.uniqueResult() != null){
            allFamilias.add(temp);
            }
        } 
    
        return allFamilias;
    
    }
    
    public ArrayList<Familia> getListaFamiliasInternacionales() {
        
         Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
         
        String hql = "FROM Familia F WHERE F.habilitado = 0";
        Query query = session.createQuery(hql);
        List familias = query.list();
        ArrayList<Familia> allFamilias = new ArrayList();
        for (Iterator iter = familias.iterator(); iter.hasNext();) {
            Familia temp = (Familia) iter.next();
            Hibernate.initialize(temp.getEntidad());
            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia and EF.nacionalidad = :nacionalidad ORDER BY EF.idexpedienteFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("idFamilia", temp.getIdfamilia());
            query2.setString("nacionalidad", "internacional");
            query2.setMaxResults(1);
            Object queryResult2 = query2.uniqueResult();
            if (queryResult2 != null){
            ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
            Hibernate.initialize(tempExp.getUnidad());
            Hibernate.initialize(tempExp.getEvaluacions());
            Set<Evaluacion> listEval = new HashSet<Evaluacion>();
                for (Evaluacion eval : tempExp.getEvaluacions()) {
                    Hibernate.initialize(eval.getPersonal());
                    //if(eval.getTipo().equals("legal")) Hibernate.initialize(eval.getResolucions());
                    listEval.add(eval);
                }
            tempExp.setEvaluacions(listEval);
            Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
            listExp.add(tempExp);
            temp.setExpedienteFamilias(listExp);
            }
            
            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
            Query query3 = session.createQuery(hql3);
            query3.setLong("idFamilia", temp.getIdfamilia());
            query3.setMaxResults(1);
            Object queryResult3 = query3.uniqueResult();
            if (queryResult3 != null){
            InfoFamilia infoFam = (InfoFamilia) queryResult3;
            Hibernate.initialize(infoFam.getAdoptantes());
            Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
            listInf.add(infoFam);
            temp.setInfoFamilias(listInf);
            }
            if (query2.uniqueResult() != null && query3.uniqueResult() != null){
            allFamilias.add(temp);
            }
        } 
    
        return allFamilias;
    
    }
    
    public Resolucion getUltimaResolucion(Long expFam){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Resolucion ultimaResol = new Resolucion();
        
        String hql = "from Evaluacion E where (E.tipo = :tipo1) or (E.tipo = :tipo2) and E.expedienteFamilia = :expFam";
        Query query = session.createQuery(hql);
        query.setString("tipo1", "legal");
        query.setString("tipo2", "informe");
        query.setLong("expFam", expFam);
        List evaluaciones = query.list();
       
        if(!evaluaciones.isEmpty()){
        for (Iterator iter = evaluaciones.iterator(); iter.hasNext();) {
        Evaluacion temp = (Evaluacion) iter.next();
            if(temp.getTipo().equals("informe")){
                String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval ORDER BY R.idresolucion DESC";
                Query query1 = session.createQuery(hql1);
                query1.setLong("idEval", temp.getIdevaluacion());
                query1.setMaxResults(1);
                Object queryResult1 = query1.uniqueResult();
                if (queryResult1 != null){
                    ultimaResol = (Resolucion) queryResult1;
                    return ultimaResol;
                }
                
            }else{
            
                String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval ORDER BY R.idresolucion DESC";
                Query query1 = session.createQuery(hql1);
                query1.setLong("idEval", temp.getIdevaluacion());
                query1.setMaxResults(1);
                Object queryResult1 = query1.uniqueResult();
                if (queryResult1 != null){
                    ultimaResol = (Resolucion) queryResult1;
                }
            }
        
         }
       }
            return ultimaResol;
    }
    
    public Resolucion getResolucionAptitud(Long expFam){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Resolucion ultimaResol = new Resolucion();
        
        String hql = "from Evaluacion E where E.tipo = :tipo1 and E.expedienteFamilia = :expFam";
        Query query = session.createQuery(hql);
        query.setString("tipo1", "legal");
        query.setLong("expFam", expFam);
        Object queryResultA = query.uniqueResult();
       
        if(query.uniqueResult() != null){
        Evaluacion temp = (Evaluacion) queryResultA;
                String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval and R.tipo = :tipo";
                Query query1 = session.createQuery(hql1);
                query1.setLong("idEval", temp.getIdevaluacion());
                query1.setString("tipo", "apto");
                Object queryResult1 = query1.uniqueResult();
                if (query1.uniqueResult() != null){
                    ultimaResol = (Resolucion) queryResult1;
                    return ultimaResol;
                }
                
         
       }
            return ultimaResol;
    }
    
    public Designacion getUltimaDesignacion(Long expFam){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Designacion ultimaDesig = new Designacion();
        
        String hql = "from Designacion D where D.expedienteFamilia = :expFam ORDER BY D.fechaPropuesta DESC";
        Query query = session.createQuery(hql);
        query.setLong("expFam", expFam);
        query.setMaxResults(1);
        Object queryResultA = query.uniqueResult();
       
        if(query.uniqueResult() != null){
              ultimaDesig = (Designacion) queryResultA;
        }
            return ultimaDesig;
    }
    
    public Designacion getUltimaDesignacionNna(Long expFam){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Designacion ultimaDesig = new Designacion();
        
        String hql = "from Designacion D where D.expedienteFamilia = :expFam ORDER BY D.fechaPropuesta DESC";
        Query query = session.createQuery(hql);
        query.setLong("expFam", expFam);
        query.setMaxResults(1);
        Object queryResultA = query.uniqueResult();
       
        if(query.uniqueResult() != null){
              ultimaDesig = (Designacion) queryResultA;
              Hibernate.initialize(ultimaDesig.getNna());
        }
            return ultimaDesig;
    }
    
    
}
