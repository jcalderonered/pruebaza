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
            if (queryResult != null){
            FormularioSesion tempFs = (FormularioSesion) queryResult;
            Hibernate.initialize(tempFs.getAsistentes());
            Set<FormularioSesion> listFs = new HashSet<FormularioSesion>();
            listFs.add(tempFs);
            temp.setFormularioSesions(listFs); 
            }else{
            temp.setFormularioSesions(null);
            }
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
    
    public Evaluacion getEvaluacion2(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Evaluacion tempEval = new Evaluacion();
        
        String hqlA = "FROM Evaluacion E WHERE E.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempEval = (Evaluacion) queryResultA;
        Hibernate.initialize(tempEval.getExpedienteFamilia().getFamilia());
        
        return tempEval;
        
    }
    
    public Resolucion getResolucion(long idResolucion){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Resolucion tempResol = new Resolucion();
        
        String hqlA = "FROM Resolucion R WHERE R.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", idResolucion);
        Object queryResultA = queryA.uniqueResult();

        tempResol = (Resolucion) queryResultA;
        Hibernate.initialize(tempResol.getEvaluacion().getExpedienteFamilia());
        return tempResol;
        
    }
    
    public Resolucion getResolucion2(long idEval){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Resolucion tempResol = new Resolucion();
        
        String hqlA = "FROM Resolucion R WHERE R.evaluacion = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", idEval);
        Object queryResultA = queryA.uniqueResult();

        tempResol = (Resolucion) queryResultA;
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
                        query2.setMaxResults(1);
                        List resoluciones = query2.list();
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                            for (Iterator iter2 = resoluciones.iterator(); iter2.hasNext();) {
                                    Resolucion resolTemp = (Resolucion) iter2.next();
                                    tempResoluciones.add(resolTemp);
                                    System.out.print(resolTemp.getFechaResol());
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
        
        String hql = "from ExpedienteFamilia EF where (EF.estado = :estado) or (EF.estado = :estado2) or (EF.estado = :estado3)";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        query.setString("estado2", "adopcion");
        query.setString("estado3", "post");
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
        
        String hql = "from ExpedienteFamilia EF where (EF.estado = :estado) or (EF.estado = :estado2) or (EF.estado = :estado3) and EF.expediente like :exp";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        query.setString("estado2", "adopcion");
        query.setString("estado3", "post");
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
    
    public void crearDesignacion(Designacion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    } 
    
    public void updateDesignacion(Designacion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public ArrayList<Designacion> getListaDesignaciones(){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo ORDER BY D.fechaPropuesta DESC";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("1"));
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia());
            Hibernate.initialize(temp.getNna());
            allDesig.add(temp);
        }
        return allDesig;
    }
    
    public ArrayList<Designacion> getListaDesignaciones(long idNna){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.nna = :id ORDER BY D.fechaPropuesta DESC";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("1"));
        query.setLong("id", idNna);
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia());
            Hibernate.initialize(temp.getNna().getExpedienteNnas());
            allDesig.add(temp);
        }
        return allDesig;
    }
    public ArrayList<Designacion> getListaDesignaciones2(long idNna){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.nna = :id ORDER BY D.fechaPropuesta DESC";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("0"));
        query.setLong("id", idNna);
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia());
            Hibernate.initialize(temp.getNna().getExpedienteNnas());
            allDesig.add(temp);
        }
        return allDesig;
    }
    
    public ArrayList<Designacion> getListaDesignacionesDeFamilia(long idExpFam){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.expedienteFamilia = :id";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("0"));
        query.setLong("id", idExpFam);
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        Designacion temp = (Designacion) iter.next();
            allDesig.add(temp);
        }
        return allDesig;
    }
    
    public ArrayList<Designacion> getListaAdopciones(){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo ORDER BY D.fechaConsejo ASC";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("0"));
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia().getEvaluacions());
            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
            for (Evaluacion eval : temp.getExpedienteFamilia().getEvaluacions()){
                if(eval.getTipo().equals("empatia") || eval.getTipo().equals("informe")){
                        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol ASC";
                        Query query2 = session.createQuery(hql2);
                        query2.setLong("idEvaluacion", eval.getIdevaluacion());
                        query2.setMaxResults(1);
                        Object queryResult = query2.uniqueResult();
                        Resolucion tempResol = (Resolucion) queryResult;
                        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                        tempResoluciones.add(tempResol);
                        eval.setResolucions(tempResoluciones);
                        tempEvaluaciones.add(eval);
                        
                    }
                
            }
            temp.getExpedienteFamilia().setEvaluacions(tempEvaluaciones);
            Hibernate.initialize(temp.getNna());
            allDesig.add(temp);
        }
        return allDesig;
    }
    
    public void deleteResolucion(Resolucion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.delete(temp);
    
    } 
    
    public void deleteEvaluacion(Evaluacion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.delete(temp);
    
    } 
    
    public Designacion getDesignacion(long idExpFamilia, long idNna){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Designacion tempDesig = new Designacion();
        
        String hqlA = "FROM Designacion D WHERE D.expedienteFamilia = :idExpFamilia and D.nna = :idNna";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("idExpFamilia", idExpFamilia);
        queryA.setLong("idNna", idNna);
        Object queryResultA = queryA.uniqueResult();

        tempDesig = (Designacion) queryResultA;
        return tempDesig;
        
    }
    
    public void crearEstudioCaso(EstudioCaso temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    } 
    
    public void updateEstudioCaso(EstudioCaso temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public ArrayList<EstudioCaso> getListaEstudioCaso(long idNna){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
        String hql = "FROM EstudioCaso EC WHERE EC.nna = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idNna);
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        EstudioCaso temp = (EstudioCaso) iter.next();
            allEstudioCaso.add(temp);
        }
        return allEstudioCaso;
    }
    
    public ArrayList<EstudioCaso> getListaEstudioCasoOrden(String orden){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
        String hql = "FROM EstudioCaso EC WHERE EC.orden = :orden ORDER BY EC.prioridad ASC";
        Query query = session.createQuery(hql);
        query.setString("orden", orden);
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        EstudioCaso temp = (EstudioCaso) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia());
            allEstudioCaso.add(temp);
        }
        return allEstudioCaso;
    }
    
    public EstudioCaso getEstudioCaso(long idEstudio){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        EstudioCaso tempEstudio = new EstudioCaso();
        
        String hql = "FROM EstudioCaso EC WHERE EC.id = :idEstudio";
        Query query = session.createQuery(hql);
        query.setLong("idEstudio", idEstudio);
        Object queryResultA = query.uniqueResult();

        tempEstudio = (EstudioCaso) queryResultA;
        Hibernate.initialize(tempEstudio.getExpedienteFamilia());
        return tempEstudio;
        
    }
    
    public void crearPostAdopcion(PostAdopcion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    } 
    
    public void updatePostAdopcion(PostAdopcion temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public void crearInformePost(InformePostAdoptivo temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.save(temp);
    
    } 
    
    public void updateInformePost(InformePostAdoptivo temp){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        session.update(temp);
    
    } 
    
    public ArrayList<PostAdopcion> getListaPostAdopcion(){
        
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
        
        if(!allResoluciones.isEmpty() && !tempPost.isEmpty()){
        for (Iterator iter = tempPost.iterator(); iter.hasNext();) {
        PostAdopcion temp2 = (PostAdopcion) iter.next();
                for (Resolucion resol : allResoluciones) {
                    if(resol.getFechaResol().equals(temp2.getFechaResolucion())){
                        Set<ExpedienteFamilia> tempExpediente = new HashSet<ExpedienteFamilia>(0);                        
                        tempExpediente.add(resol.getEvaluacion().getExpedienteFamilia());
                        Hibernate.initialize(temp2.getFamilia());
                        Hibernate.initialize(temp2.getInformePostAdoptivos());
                        temp2.getFamilia().setExpedienteFamilias(tempExpediente);
                    }
            }
           allPostAdopcion.add(temp2);
        }
        }
        
        return allPostAdopcion;
    }
    
    public PostAdopcion getPostAdopcion(long idPost){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        PostAdopcion tempPost = new PostAdopcion();
        
        String hql = "FROM PostAdopcion PA WHERE PA.id = :idPost";
        Query query = session.createQuery(hql);
        query.setLong("idPost", idPost);
        Object queryResultA = query.uniqueResult();

        tempPost = (PostAdopcion) queryResultA;
        
        return tempPost;
        
    }
    
    public ArrayList<InformePostAdoptivo> getListaInformesPost(long idPost){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList();
        
        String hql = "FROM InformePostAdoptivo IP WHERE IP.postAdopcion = :idPost ORDER BY IP.numeroInforme ASC";
        Query query = session.createQuery(hql);
        query.setLong("idPost", idPost);
        List informes = query.list();
        
        for (Iterator iter = informes.iterator(); iter.hasNext();) {
            InformePostAdoptivo temp = (InformePostAdoptivo) iter.next();
            Hibernate.initialize(temp.getPersonal());
            allInformePostAdoptivo.add(temp);
        }
        return allInformePostAdoptivo;
    }
    
    public InformePostAdoptivo getInformePost(long idInforme){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        InformePostAdoptivo tempPost = new InformePostAdoptivo();
        
        String hql = "FROM InformePostAdoptivo IP WHERE IP.id = :idPost";
        Query query = session.createQuery(hql);
        query.setLong("idPost", idInforme);
        Object queryResultA = query.uniqueResult();

        tempPost = (InformePostAdoptivo) queryResultA;
        
        return tempPost;
        
    }
    
    public ArrayList<ExpedienteFamilia> getListaEspera(){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<ExpedienteFamilia> allEspera = new ArrayList();
        String hql = "from ExpedienteFamilia EF where EF.estado = :estado";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            allEspera.add(temp);
        }
        return allEspera;
    }
    
    public ArrayList<ExpedienteFamilia> getListaReevaluación(){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<ExpedienteFamilia> allReevaluacion = new ArrayList();
        String hql = "from ExpedienteFamilia EF where EF.estado = :estado";
        Query query = session.createQuery(hql);
        query.setString("estado", "reevaluacion");
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
        ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            allReevaluacion.add(temp);
        }
        return allReevaluacion;
    }
    
}
