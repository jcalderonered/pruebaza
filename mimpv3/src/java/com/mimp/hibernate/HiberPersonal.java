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
         String hql = "From Sesion S ";
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
    
    
}
