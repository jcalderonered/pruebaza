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

    public Autoridad getAutoridad(int id) {
        Session session = sessionFactory.getCurrentSession();
        Autoridad aut = new Autoridad();

        session.beginTransaction();
        String hqlA = "FROM Autoridad A WHERE A.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setInteger("id", id);
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

        session.save(ent);
        session.save(rep);

        org.setEntidad(ent);
        org.setRepresentante(rep);

        ent.getOrganismos().add(org);
        rep.getOrganismos().add(org);

        session.save(org);

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
            Hibernate.initialize(temp.getRepresentante());
            allOrganismos.add(temp);
        }
        return allOrganismos;
    }

    public Organismo getOrganismo(int id) {
        Session session = sessionFactory.getCurrentSession();
        Organismo org = new Organismo();

        session.beginTransaction();
        String hqlO = "FROM Organismo O WHERE O.id = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setInteger("id", id);
        Object queryResultA = queryO.uniqueResult();

        org = (Organismo) queryResultA;
        Hibernate.initialize(org.getEntidad());
        Hibernate.initialize(org.getRepresentante());
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

    public Car getCar(int id) {
        Session session = sessionFactory.getCurrentSession();
        Car car = new Car();

        session.beginTransaction();
        String hqlC = "FROM Car C WHERE C.id = :id";
        Query queryC = session.createQuery(hqlC);
        queryC.setInteger("id", id);
        Object queryResultC = queryC.uniqueResult();

        car = (Car) queryResultC;

        return car;
    }

}
