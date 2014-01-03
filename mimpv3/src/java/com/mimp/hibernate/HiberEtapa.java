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
    
    public ExpedienteFamilia getExpedienteFamilia(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        String hqlA = "FROM ExpedienteFamilia EF WHERE EF.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        expFamilia = (ExpedienteFamilia) queryResultA;
        
        return expFamilia;
        
    }
    
    public Evaluacion getEvaluacion(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Evaluacion tempEval = new Evaluacion();
        
        String hqlA = "FROM Evaluacion E WHERE E.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempEval = (Evaluacion) queryResultA;
        
        return tempEval;
        
    }
    
    public Resolucion getResolucion(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Resolucion tempResol = new Resolucion();
        
        String hqlA = "FROM Resolucion R WHERE R.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempResol = (Resolucion) queryResultA;
        Hibernate.initialize(tempResol.getEvaluacion().getExpedienteFamilia());
        return tempResol;
        
    }
    
    public Evaluacion getLegal(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Evaluacion tempEval = new Evaluacion();
        
        String hqlA = "FROM Evaluacion E WHERE E.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempEval = (Evaluacion) queryResultA;
        
        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol DESC";
                        Query query2 = session.createQuery(hql2);
                        query2.setLong("idEvaluacion", tempEval.getIdevaluacion());
                        List resoluciones = query2.list();
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                            for (Iterator iter2 = resoluciones.iterator(); iter2.hasNext();) {
                                    Resolucion resolTemp = (Resolucion) iter2.next();
                                    tempResoluciones.add(resolTemp);
                            }
                        tempEval.setResolucions(tempResoluciones);
        
        
        return tempEval;
        
    }
    
     public ArrayList<ExpedienteFamilia> ListaExpedientes(String nacionalidad,String estado) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "from ExpedienteFamilia EF where EF.nacionalidad = :nacionalidad and EF.estado = :estado";
        Query query = session.createQuery(hql);
        query.setString("nacionalidad", nacionalidad);
        query.setString("estado", estado);
        List expedientes = query.list();
        ArrayList<ExpedienteFamilia> allExpedienteFamilia = new ArrayList();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getEvaluacions());
            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
            for (Evaluacion eval : temp.getEvaluacions() ){
                    if(eval.getTipo().equals("legal")){
                        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol DESC";
                        Query query2 = session.createQuery(hql2);
                        query2.setLong("idEvaluacion", eval.getIdevaluacion());
                        List resoluciones = query2.list();
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                            for (Iterator iter2 = resoluciones.iterator(); iter2.hasNext();) {
                                    Resolucion resolTemp = (Resolucion) iter2.next();
                                    tempResoluciones.add(resolTemp);
                                    System.out.print(resolTemp);
                            }
                        eval.setResolucions(tempResoluciones);
                        tempEvaluaciones.add(eval);
                    }else{
                        tempEvaluaciones.add(eval);
                    }
                    
            }
            temp.setEvaluacions(tempEvaluaciones);
            allExpedienteFamilia.add(temp);
        }
        return allExpedienteFamilia;
    }
     
    public void updateExpedienteFamilia(ExpedienteFamilia temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public void crearEvaluacion(Evaluacion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    } 
    
    public void updateEvaluacion(Evaluacion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public void crearResolEvaluacion(Resolucion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    } 
    
    public void updateResolEvaluacion(Resolucion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public ArrayList<ExpedienteFamilia> listaInfoFamilias(){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        String hql = "from ExpedienteFamilia EF where EF.estado = :estado";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        List expedientes = query.list();
        ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getFamilia());
            Hibernate.initialize(temp.getEvaluacions());
            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
            for (Evaluacion eval : temp.getEvaluacions()){
                if(eval.getTipo().equals("legal")){
                        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
                        Query query2 = session.createQuery(hql2);
                        query2.setLong("idEvaluacion", eval.getIdevaluacion());
                        query2.setString("tipo", "apto");
                        query2.setMaxResults(1);
                        Object queryResult = query2.uniqueResult();
                        Resolucion tempResol = (Resolucion) queryResult;
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                        tempResoluciones.add(tempResol);
                        eval.setResolucions(tempResoluciones);
                        tempEvaluaciones.add(eval);
                        
                    }
                
            }
            temp.setEvaluacions(tempEvaluaciones);
            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("id", temp.getFamilia().getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult = query2.uniqueResult();
            InfoFamilia tempIf = (InfoFamilia) queryResult;
            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
            tempInfFam.add(tempIf);
            temp.getFamilia().setInfoFamilias(tempInfFam);
            allInfoFam.add(temp);
        }
        
        return  allInfoFam;
    
    }

    public ArrayList<ExpedienteFamilia> listaInfoFamilias(String exp){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        String hql = "from ExpedienteFamilia EF where EF.estado = :estado and EF.expediente like :exp";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        query.setString("exp",'%' + exp + '%');
        List expedientes = query.list();
        ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getFamilia());
            Hibernate.initialize(temp.getEvaluacions());
            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
            for (Evaluacion eval : temp.getEvaluacions()){
                if(eval.getTipo().equals("legal")){
                        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
                        Query query2 = session.createQuery(hql2);
                        query2.setLong("idEvaluacion", eval.getIdevaluacion());
                        query2.setString("tipo", "apto");
                        query2.setMaxResults(1);
                        Object queryResult = query2.uniqueResult();
                        Resolucion tempResol = (Resolucion) queryResult;
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                        tempResoluciones.add(tempResol);
                        eval.setResolucions(tempResoluciones);
                        tempEvaluaciones.add(eval);
                        
                    }
                
            }
            temp.setEvaluacions(tempEvaluaciones);
            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("id", temp.getFamilia().getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult = query2.uniqueResult();
            InfoFamilia tempIf = (InfoFamilia) queryResult;
            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
            tempInfFam.add(tempIf);
            temp.getFamilia().setInfoFamilias(tempInfFam);
            allInfoFam.add(temp);
        }
        
        return  allInfoFam;
    
    }
    
    public ExpedienteFamilia getInfoFamilia(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        String hqlA = "FROM ExpedienteFamilia EF WHERE EF.id = :id";
        
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        expFamilia = (ExpedienteFamilia) queryResultA;
        Hibernate.initialize(expFamilia.getFamilia());
        Hibernate.initialize(expFamilia.getEvaluacions());
        Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
            for (Evaluacion eval : expFamilia.getEvaluacions()){
                if(eval.getTipo().equals("legal")){
                        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
                        Query query2 = session.createQuery(hql2);
                        query2.setLong("idEvaluacion", eval.getIdevaluacion());
                        query2.setString("tipo", "apto");
                        query2.setMaxResults(1);
                        Object queryResult = query2.uniqueResult();
                        Resolucion tempResol = (Resolucion) queryResult;
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                        tempResoluciones.add(tempResol);
                        eval.setResolucions(tempResoluciones);
                        tempEvaluaciones.add(eval);
                        
                    }
                
            }
            expFamilia.setEvaluacions(tempEvaluaciones);
            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("id", expFamilia.getFamilia().getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult = query2.uniqueResult();
            InfoFamilia tempIf = (InfoFamilia) queryResult;
            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
            tempInfFam.add(tempIf);
            expFamilia.getFamilia().setInfoFamilias(tempInfFam);
        
        
        return expFamilia;
        
    }
    
}
