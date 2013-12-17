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
@Service("HiberMain")
@Transactional
public class HiberMain {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public ArrayList<Object> usuario(String user, String pass) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Personal personal;
        Entidad entidad;
        Familia familia;
        
        pass = DigestUtils.md5Hex(pass);

        ArrayList<Object> temp = new ArrayList<Object>();

        String hqlP = "FROM Personal P WHERE P.user = :usuario and P.pass = :password ";//:=login
        String hqlF = "FROM Familia F WHERE F.user = :usuario and F.pass = :password ";
        String hqlE = "FROM Entidad R WHERE R.user = :usuario and R.pass = :password ";

        Query queryP = session.createQuery(hqlP);
        queryP.setString("usuario", user);
        queryP.setString("password", pass);
        Object queryResultP = queryP.uniqueResult();

        Query queryF = session.createQuery(hqlF);
        queryF.setString("usuario", user);
        queryF.setString("password", pass);
        Object queryResultF = queryF.uniqueResult();

        Query queryE = session.createQuery(hqlE);
        queryE.setString("usuario", user);
        queryE.setString("password", pass);
        Object queryResultE = queryE.uniqueResult();

        if (queryResultP != null) {
            personal = (Personal) queryResultP;
            temp.add("personal");
            temp.add(personal);
            return temp;
        } else if (queryResultF != null) {
            familia = (Familia) queryResultF;
            Hibernate.initialize(familia.getInfoFamilias());
            Hibernate.initialize(familia.getFormularioSesions());
            //Mejorar
            for (Iterator iter = familia.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                Hibernate.initialize(ifa.getAdoptantes());
                Hibernate.initialize(ifa.getHijoActs());
                Hibernate.initialize(ifa.getResidenteActs());
            }
            temp.add("familia");
            temp.add(familia);
            return temp;
        } else if (queryResultE != null) {
            entidad = (Entidad) queryResultE;
            Hibernate.initialize(entidad.getAutoridads());
            Hibernate.initialize(entidad.getOrganismos());
            //Mejorar este punto (no deberia ser un FOR para solo un parametro)
            for (Iterator iter = entidad.getOrganismos().iterator(); iter.hasNext();) {
                Organismo org = (Organismo) iter.next();
                Hibernate.initialize(org.getRepresentantes());
            }
            if (entidad.getAutoridads().isEmpty()) {
                temp.add("representante");
            } else {
                temp.add("autoridad");
            }
            temp.add(entidad);
            return temp;
        } else {
            temp.add("none");
            return temp;
        }

    }

    public ArrayList<Turno> ListaTurnos() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        //Date now = new Date();
        String queryTurnos = "FROM Turno T WHERE trunc(T.inicioInscripcion) = TO_DATE(current_date) "
                + "and trunc(T.finInscripcion) = TO_DATE(current_date) order by T.inicioInscripcion";
        Query query = session.createQuery(queryTurnos);
        List lista = query.list();
        ArrayList<Turno> Turnos = new ArrayList();
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            Turno temp = (Turno) iter.next();
            Hibernate.initialize(temp.getSesion());
            Hibernate.initialize(temp.getAsistenciaFTs());
            Turnos.add(temp);
        }

        return Turnos;

    }

    public Turno getTurno(long id) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String queryTurno = "FROM Turno T WHERE T.id = :id ";
        Query query = session.createQuery(queryTurno);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();
        Turno temp = (Turno) queryResult;
        Hibernate.initialize(temp.getSesion());
        Hibernate.initialize(temp.getAsistenciaFTs());
        return temp;
    }

    public void InsertFormInd(Asistente asis, FormularioSesion fs, AsistenciaFT aft) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        session.save(fs);
        aft.setFormularioSesion(fs);

        asis.setFormularioSesion(fs);
        fs.getAsistentes().add(asis);

        session.save(asis);
        session.save(aft);
    }

    public void InsertFormGrp(Asistente asisEl, Asistente asisElla, FormularioSesion fs, AsistenciaFT aft) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        asisEl.setFormularioSesion(fs);
        fs.getAsistentes().add(asisEl);
        
        asisElla.setFormularioSesion(fs);
        fs.getAsistentes().add(asisElla);
        
        aft.setFormularioSesion(fs);
        fs.getAsistenciaFTs().add(aft);
        
        session.save(fs);
        session.save(asisEl);
        session.save(asisElla);
        session.save(aft);
        
        
    }

    public ArrayList<Asistente> listaAsistentes(long id) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Asistente A where A.formularioSesion.sesion.idsesion = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        List asistentes = query.list();
        ArrayList<Asistente> allAsistentes = new ArrayList();
        for (Iterator iter = asistentes.iterator(); iter.hasNext();) {
            Asistente temp = (Asistente) iter.next();
            Hibernate.initialize(temp.getFormularioSesion());
            allAsistentes.add(temp);
        }
        return allAsistentes;

    }

    public ArrayList<Sesion> listaSesionesSiguientes(Date fecha) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Sesion S where S.fecha > :fecha  order by S.fecha";
        Query query = session.createQuery(hql);
        query.setDate("fecha", fecha);
        List asistentes = query.list();
        ArrayList<Sesion> allSesiones = new ArrayList();
        for (Iterator iter = asistentes.iterator(); iter.hasNext();) {
            Sesion temp = (Sesion) iter.next();
            allSesiones.add(temp);
        }
        return allSesiones;

    }

    public ArrayList<Turno> turnosSesion(long idSesion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Turno T where T.sesion = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idSesion);
        List asistentes = query.list();
        ArrayList<Turno> allTurnos = new ArrayList();
        for (Iterator iter = asistentes.iterator(); iter.hasNext();) {
            Turno temp = (Turno) iter.next();

            allTurnos.add(temp);
        }
        return allTurnos;

    }
}
