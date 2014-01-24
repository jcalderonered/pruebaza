/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.hibernate;

import java.util.*;
import org.hibernate.Session;
import com.mimp.bean.*;
import com.mimp.util.dateFormat;
import javax.annotation.Resource;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("HiberFamilia")
@Transactional
public class HiberFamilia {
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    dateFormat format = new dateFormat();
    
    public ArrayList<Taller> listaTalleres (){
        
    Session session = sessionFactory.getCurrentSession();
        
        Short numReun ;
        int cont = 0;
        String size;
        
        session.beginTransaction();
        String hql = "From Taller T order by T.id";
        Query query = session.createQuery(hql);
        List talleres = query.list();
        ArrayList<Taller> allTalleres = new ArrayList();
        
        for (Iterator iter = talleres.iterator(); iter.hasNext();) {
                Taller temp = (Taller) iter.next();
                    Hibernate.initialize(temp.getGrupos());
                  Set<Grupo> allGrp = new HashSet<Grupo>(0);  
            for (Grupo grp : temp.getGrupos()) {
                         Hibernate.initialize(grp.getTurno2s());
                  Set<Turno2> allT2 = new HashSet<Turno2>(0);       
                for (Turno2 t2 : grp.getTurno2s()) {
                            Hibernate.initialize(t2.getReunions());
                                 cont = cont + t2.getReunions().size();
                           allT2.add(t2);
                }
                grp.setTurno2s(allT2);
                allGrp.add(grp);
            }
            temp.setGrupos(allGrp);
            size = String.valueOf(cont);
            numReun = Short.parseShort(size);
            temp.setNReunion(numReun);
            allTalleres.add(temp);
            cont = 0;
        }
        return allTalleres;
    
    }
    
    public Sesion sesionMasProx (Date fechaActual){
        
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Sesion S order by S.fecha";
        Query query = session.createQuery(hql);
        List sesiones = query.list();
        ArrayList<Sesion> allSesiones = new ArrayList();
        
        Sesion sesionProx = new Sesion();
        int i = 0;
        for (Iterator iter = sesiones.iterator(); iter.hasNext();) {
            Sesion temp = (Sesion) iter.next();
            
            allSesiones.add(temp);                        
            
            if( (fechaActual.getYear() <=  temp.getFecha().getYear()) && (fechaActual.getMonth() <= temp.getFecha().getMonth())){
            sesionProx = temp;
            break;
            }                        
            
        }
        return sesionProx;
    
    }
    
    
    //<----------USUARIO---------->
    public void CambiaPass(Familia familia) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(familia);
    }
    
    public ArrayList<Taller> listaTalleresHabilitados (){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "From Taller T WHERE T.habilitado = :hab order by T.id";
        Query query = session.createQuery(hql);
        query.setShort("hab", Short.parseShort("0"));
        List talleres = query.list();
        ArrayList<Taller> allTalleres = new ArrayList();
         for (Iterator iter = talleres.iterator(); iter.hasNext();) {
                Taller temp = (Taller) iter.next();
                allTalleres.add(temp);
         }
         return allTalleres;
    }
    
    public ArrayList<Grupo> listaGruposDeTaller(long idTaller){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "From Grupo G WHERE G.taller = :id order by G.id";
        Query query = session.createQuery(hql);
        query.setLong("id", idTaller);
        List grupos = query.list();
        ArrayList<Grupo> allGrupos = new ArrayList();
         for (Iterator iter = grupos.iterator(); iter.hasNext();) {
                Grupo temp = (Grupo) iter.next();
                Hibernate.initialize(temp.getTurno2s());
                Set<Turno2> allT2 = new HashSet<Turno2>(0);       
                for (Turno2 t2 : temp.getTurno2s()) {
                            Hibernate.initialize(t2.getReunions());
                            allT2.add(t2);
                }
                temp.setTurno2s(allT2);
                allGrupos.add(temp);
         }
         return allGrupos;
    }
    
    public ArrayList<Reunion> listaReunionesTurno2(long idTurno2){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "From Reunion R WHERE R.turno2 = :id order by R.id";
        Query query = session.createQuery(hql);
        query.setLong("id", idTurno2);
        List reuniones = query.list();
        ArrayList<Reunion> allReuniones = new ArrayList();
         for (Iterator iter = reuniones.iterator(); iter.hasNext();) {
                Reunion temp = (Reunion) iter.next();
                Hibernate.initialize(temp.getAsistenciaFRs());
                allReuniones.add(temp);
         }
         return allReuniones;
    }
    
    public void crearAFR(AsistenciaFR temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(temp);
    }
    
    public Short numAsistentesFormulario(long idFamilia){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int temp = 0;
        String hql = "From FormularioSesion FS WHERE FS.familia = :id order by FS.idformularioSesion DESC";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        query.setMaxResults(1);
        FormularioSesion qryResult = (FormularioSesion) query.uniqueResult();
        temp = temp + qryResult.getAsistentes().size();
        
         return (short)temp;
    }
    
    public ArrayList<AsistenciaFR> listaReunionesInscritasFamilia(long idFamilia){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "From AsistenciaFR AFR WHERE AFR.familia = :id order by AFR.id DESC";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        List reuniones = query.list();
        ArrayList<AsistenciaFR> allReuniones = new ArrayList();
         for (Iterator iter = reuniones.iterator(); iter.hasNext();) {
                AsistenciaFR temp = (AsistenciaFR) iter.next();
                Hibernate.initialize(temp.getReunion());
                allReuniones.add(temp);
         }
         return allReuniones;
    }
    
    public int AsistentesPorFormulario(long idFamilia){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int temp = 0;
        String hql = "From FormularioSesion FS WHERE FS.familia = :id order by FS.idformularioSesion DESC";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        query.setMaxResults(1);
        FormularioSesion qryResult = (FormularioSesion) query.uniqueResult();
        temp = temp + qryResult.getAsistentes().size();
        
         return temp;
    }
    
    public FormularioSesion ultimoFormulario(long idFamilia){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        int temp = 0;
        String hql = "From FormularioSesion FS WHERE FS.familia = :id order by FS.idformularioSesion DESC";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        query.setMaxResults(1);
        FormularioSesion qryResult = (FormularioSesion) query.uniqueResult();
        Hibernate.initialize(qryResult.getAsistentes());
        
         return qryResult;
    }
    
    public Familia obtenerFormulariosFamilia(long idFamilia){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "From Familia F WHERE F.idfamilia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        Familia qryResult = (Familia) query.uniqueResult();
        Hibernate.initialize(qryResult.getFormularioSesions());
        
         return qryResult;
    }
}
