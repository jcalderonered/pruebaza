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

@Service("HiberPersonal")
@Transactional
public class HiberPersonal {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    //<----------AUTORIDAD---------->
    public void InsertAut(Entidad ent, Autoridad aut) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(ent);

        aut.setEntidad(ent);
        ent.getAutoridads().add(aut);

        session.save(aut);

    }

    public void UpdateAut(Entidad ent, Autoridad aut) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.update(ent);

        session.update(aut);

    }

    public Autoridad getAutoridad(long id) {
        Session session = sessionFactory.getCurrentSession();
        Autoridad aut = new Autoridad();

        session.beginTransaction();
        String hqlA = "FROM Autoridad A WHERE A.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        aut = (Autoridad) queryResultA;
        Hibernate.initialize(aut.getEntidad());
        return aut;
    }

    public ArrayList<Autoridad> ListaAutoridades() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Autoridad";
        Query query = session.createQuery(hql);
        List autoridades = query.list();
        ArrayList<Autoridad> allAutoridades = new ArrayList();
        for (Iterator iter = autoridades.iterator(); iter.hasNext();) {
            Autoridad temp = (Autoridad) iter.next();
            Hibernate.initialize(temp.getEntidad());
            allAutoridades.add(temp);
        }
        return allAutoridades;
    }

    //<----------ORGANISMO---------->
    public void InsertOrg(Entidad ent, Representante rep, Organismo org) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        org.setEntidad(ent);
        org.getRepresentantes().add(rep);

        ent.getOrganismos().add(org);
        rep.setOrganismo(org);

        
        session.save(ent);
        session.save(org);
        session.save(rep);
    }

    public void UpdateOrg(Entidad ent, Representante rep, Organismo org) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.update(ent);
        session.update(rep);
        session.update(org);
    }

    public ArrayList<Organismo> ListaOrganismos() {

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

    public Organismo getOrganismo(long id) {
        Session session = sessionFactory.getCurrentSession();
        Organismo org = new Organismo();

        session.beginTransaction();
        String hqlO = "FROM Organismo O WHERE O.id = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", id);
        Object queryResultA = queryO.uniqueResult();

        org = (Organismo) queryResultA;
        Hibernate.initialize(org.getEntidad());
        Hibernate.initialize(org.getRepresentantes());
        return org;
    }

    //<----------CAR---------->
    
    public void InsertCar(Car car) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(car);
        

    }
    
    public void UpdateCar(Car car) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.update(car);
    }

    public ArrayList<Car> ListaCar() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Car";
        Query query = session.createQuery(hql);
        List car = query.list();
        ArrayList<Car> allCar = new ArrayList();

        for (Iterator iter = car.iterator(); iter.hasNext();) {
            Car temp = (Car) iter.next();

            allCar.add(temp);
        }

        return allCar;
    }

    public Car getCar(long id) {
        Session session = sessionFactory.getCurrentSession();
        Car car = new Car();

        session.beginTransaction();
        String hqlC = "FROM Car C WHERE C.id = :id";
        Query queryC = session.createQuery(hqlC);
        queryC.setLong("id", id);
        Object queryResultC = queryC.uniqueResult();

        car = (Car) queryResultC;

        return car;
    }
    //<----------Juzgado---------->
    public void InsertJuzgado(Juzgado juzg) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(juzg);
       
    }
    
    public void UpdateJuzgado(Juzgado juzg) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.update(juzg);
    }
    
    public ArrayList<Juzgado> ListaJuzgado() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Juzgado";
        Query query = session.createQuery(hql);
        List juzgados = query.list();
        ArrayList<Juzgado> allJuzgado = new ArrayList();

        for (Iterator iter = juzgados.iterator(); iter.hasNext();) {
            Juzgado temp = (Juzgado) iter.next();

            allJuzgado.add(temp);
        }

        return allJuzgado;
    }
    public Juzgado getJuzgado(long id) {
        Session session = sessionFactory.getCurrentSession();
        Juzgado juzg = new Juzgado();

        session.beginTransaction();
        String hqlJ = "FROM Juzgado J WHERE J.id = :id";
        Query queryJ = session.createQuery(hqlJ);
        queryJ.setLong("id", id);
        Object queryResultJ = queryJ.uniqueResult();

        juzg = (Juzgado) queryResultJ;

        return juzg;
    }
    
    
    
    
    //<----------UA---------->
    
    public void InsertUa(Unidad ua) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(ua);
    }
    
    public void UpdateUa(Unidad ua) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.update(ua);
    }    
     
    public ArrayList<Unidad> ListaUa() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Unidad";
        Query query = session.createQuery(hql);
        List ua = query.list();
        ArrayList<Unidad> allUa = new ArrayList();

        for (Iterator iter = ua.iterator(); iter.hasNext();) {
            Unidad temp = (Unidad) iter.next();

            allUa.add(temp);
        }

        return allUa;
    }
    
    public Unidad getUa(long id) {
        Session session = sessionFactory.getCurrentSession();
        Unidad ua = new Unidad();

        session.beginTransaction();
        String hqlU = "FROM Unidad U WHERE U.id = :id";
        Query queryU = session.createQuery(hqlU);
        queryU.setLong("id", id);
        Object queryResultU = queryU.uniqueResult();

        ua = (Unidad) queryResultU;

        return ua;
    }
    
    public ArrayList<Personal> ListaPersonalUa(long idUa) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Personal P where P.unidad = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idUa);
        List pers = query.list();
        ArrayList<Personal> allPersonal = new ArrayList();

        for (Iterator iter = pers.iterator(); iter.hasNext();) {
            Personal temp = (Personal) iter.next();
            Hibernate.initialize(temp.getUnidad());
            allPersonal.add(temp);
        }

        return allPersonal;
    }
    
    public ArrayList<Personal> ListaPersonalNoUa(long idUa) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Personal P where not P.unidad = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idUa);
        List pers = query.list();
        ArrayList<Personal> allPersonal = new ArrayList();

        for (Iterator iter = pers.iterator(); iter.hasNext();) {
            Personal temp = (Personal) iter.next();
            Hibernate.initialize(temp.getUnidad());
            allPersonal.add(temp);
        }

        return allPersonal;
    }
    
    //<----------Personal---------->
    public void InsertPersonal(Personal pers) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(pers);
    }
    
    public void UpdatePersonal(Personal pers) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.update(pers);
    }  
    
    public ArrayList<Personal> ListaPersonal() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Personal";
        Query query = session.createQuery(hql);
        List ua = query.list();
        ArrayList<Personal> allPersonal = new ArrayList();

        for (Iterator iter = ua.iterator(); iter.hasNext();) {
            Personal temp = (Personal) iter.next();
            Hibernate.initialize(temp.getUnidad());
            allPersonal.add(temp);
        }

        return allPersonal;
    }
    
    public Personal getPersonal(long idPers) {
        Session session = sessionFactory.getCurrentSession();
        Personal pers = new Personal();

        session.beginTransaction();
        String hqlU = "FROM Personal P WHERE P.id = :id";
        Query queryU = session.createQuery(hqlU);
        queryU.setLong("id", idPers);
        Object queryResultU = queryU.uniqueResult();

        pers = (Personal) queryResultU;
        Hibernate.initialize(pers.getUnidad());
        return pers;
    }
    
    public ArrayList<Sesion> listaSesiones (){
        
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Sesion S order by S.fecha";
        Query query = session.createQuery(hql);
        List sesiones = query.list();
        ArrayList<Sesion> allSesiones = new ArrayList();
        for (Iterator iter = sesiones.iterator(); iter.hasNext();) {
            Sesion temp = (Sesion) iter.next();
            
            allSesiones.add(temp);
            
        }
        return allSesiones;
    
    }
    
    public Sesion getSesion (long id){
    
        Session session = sessionFactory.getCurrentSession();
        Sesion sesion = new Sesion();

        session.beginTransaction();
        String hql = "From Sesion S where S.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResultU = query.uniqueResult();

        sesion = (Sesion) queryResultU;
        Hibernate.initialize(sesion.getTurnos());
        return sesion;
    
    }
    
    /* PERSONAL CREACION/EDICION DE SESIONES Y TALLERES 
    */
    
    
    public void PersonalCrearSesion (Sesion temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.save(temp);
    
    }
    public void PersonalUpdateSesion (Sesion temp){
        
       Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.update(temp);
    }
        
    public void PersonalUpdateTurno (Turno temp){
        
       Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.update(temp);
    }
    
    public void PersonalCrearTurno (Turno temp){
        
       Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.save(temp);
    }
    
    public ArrayList<FormularioSesion> InscritosSesion (long idSesion) {
        
         Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
         
        String hql = "FROM FormularioSesion F where F.sesion = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idSesion);
        List formularios = query.list();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        for (Iterator iter = formularios.iterator(); iter.hasNext();) {
            FormularioSesion temp = (FormularioSesion) iter.next();
            Hibernate.initialize(temp.getAsistentes());
            allFormularios.add(temp);
            
        } 
    
        return allFormularios;
    
    }
    
    public ArrayList<Asistente> asistentePorFormulario (long idFormulario) {
        
         Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
         
        String hql = "FROM Asistente A where A.formularioSesion = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFormulario);
        List asistentes = query.list();
        ArrayList<Asistente> allAsistentes = new ArrayList();
        for (Iterator iter = asistentes.iterator(); iter.hasNext();) {
            Asistente temp = (Asistente) iter.next();
            Hibernate.initialize(temp.getFormularioSesion());
            allAsistentes.add(temp);
            
        } 
    
        return allAsistentes;
    
    }
    
    public void PersonalCrearTaller (Taller temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.save(temp);
    
    }
    
    public void PersonalUpdateTaller (Taller temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.update(temp);
    
    }
    
    public void PersonalCrearGrupo (Grupo temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.save(temp);
    
    }
    
    public void PersonalUpdateGrupo (Grupo temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.update(temp);
    
    }
    
    public void PersonalCrearTurno2 (Turno2 temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.save(temp);
    
    }
    
    public void PersonalUpdateTurno2 (Turno2 temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.update(temp);
    
    }
    
    public void PersonalCrearReunion (Reunion temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.save(temp);
    
    }
    
    public void PersonalUpdateReunion (Reunion temp){
    Session session = sessionFactory.getCurrentSession();
         session.beginTransaction();
        session.update(temp);
    
    }
    
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
            for (Grupo grp : temp.getGrupos()) {
                    Hibernate.initialize(grp.getTurno2s());
                for (Turno2 t2 : grp.getTurno2s()) {
                        Hibernate.initialize(t2.getReunions());
                        cont = cont + t2.getReunions().size();
                }
                    
            }
            size = String.valueOf(cont);
            numReun = Short.parseShort(size);
            temp.setNReunion(numReun);
            allTalleres.add(temp);
            cont = 0;
        }
        return allTalleres;
    
    }
    
    public Taller getTaller (long id){
    
        Session session = sessionFactory.getCurrentSession();
        Taller taller = new Taller();

        session.beginTransaction();
        String hql = "From Taller T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();
        
        taller = (Taller) queryResult;
        Hibernate.initialize(taller.getGrupos());
        
        Set<Grupo> allGrupos = new HashSet<Grupo>(0);
        for (Grupo grup : taller.getGrupos()) {
                Hibernate.initialize(grup.getTurno2s());
                allGrupos.add(grup);
        }
        taller.setGrupos(allGrupos);
        return taller;
    
    }
    
    public Grupo getGrupo (long id){
    
        Session session = sessionFactory.getCurrentSession();
        Grupo grp = new Grupo();

        session.beginTransaction();
        String hql = "From Grupo G where G.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();
        
        grp = (Grupo) queryResult;
        Hibernate.initialize(grp.getTurno2s());
        Hibernate.initialize(grp.getTaller());
        Set<Turno2> allTurno2 = new HashSet<Turno2>(0);
        for (Turno2 t2 : grp.getTurno2s()) {
                Hibernate.initialize(t2.getReunions());
                allTurno2.add(t2);
        }
        grp.setTurno2s(allTurno2);
        return grp;
    
    }
    
    public Turno2 getTurno2 (long id){
    
        Session session = sessionFactory.getCurrentSession();
        Turno2 t2 = new Turno2();

        session.beginTransaction();
        String hql = "From Turno2 T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();
        
        t2 = (Turno2) queryResult;
        Hibernate.initialize(t2.getReunions());
        Hibernate.initialize(t2.getGrupo());
        
        return t2;
    
    }
    
    public Reunion getReunion (long id){
    
        Session session = sessionFactory.getCurrentSession();
        Reunion reun = new Reunion();

        session.beginTransaction();
        String hql = "From Reunion R where R.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();
        
        reun = (Reunion) queryResult;
        Hibernate.initialize(reun.getAsistenciaFRs());
        Hibernate.initialize(reun.getTurno2());
        
        Turno2 temp = new Turno2();
        temp = reun.getTurno2();
        Hibernate.initialize(temp.getGrupo());
        
        reun.setTurno2(temp);
        return reun;
    
    }
    /*  */
}
