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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import oracle.jdbc.OracleTypes;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
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
    public boolean usuario(String user) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hqlP = "FROM Personal P WHERE P.user = :usuario ";//:=login
        String hqlF = "FROM Familia F WHERE F.user = :usuario ";
        String hqlE = "FROM Entidad R WHERE R.user = :usuario ";

        Query queryP = session.createQuery(hqlP);
        queryP.setString("usuario", user);
        Object queryResultP = queryP.uniqueResult();

        Query queryF = session.createQuery(hqlF);
        queryF.setString("usuario", user);
        Object queryResultF = queryF.uniqueResult();

        Query queryE = session.createQuery(hqlE);
        queryE.setString("usuario", user);
        Object queryResultE = queryE.uniqueResult();

        if (queryResultP != null) {

            return true;
        } else if (queryResultF != null) {
            return true;
        } else if (queryResultE != null) {
            return true;
        } else {
            return false;
        }

    }

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

    public Organismo getOrganismobyentidad(long id) {
        Session session = sessionFactory.getCurrentSession();
        Organismo org = new Organismo();

        session.beginTransaction();
        String hqlO = "FROM Organismo O WHERE O.entidad = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", id);
        Object queryResultA = queryO.uniqueResult();

        org = (Organismo) queryResultA;
        return org;
    }

    public Representante getRepresentantebyOrganismo(long id) {
        Session session = sessionFactory.getCurrentSession();
        Representante rep = new Representante();

        session.beginTransaction();
        String hqlO = "FROM Representante R WHERE R.organismo = :id";
        Query queryO = session.createQuery(hqlO);
        queryO.setLong("id", id);
        Object queryResultA = queryO.uniqueResult();

        rep = (Representante) queryResultA;
        return rep;
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

        String hql = "FROM Unidad U order by U.idunidad asc";
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

    public ArrayList<Sesion> listaSesiones() {

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

    public Sesion getSesion(long id) {

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
    public void PersonalCrearSesion(Sesion temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);

    }

    public void PersonalUpdateSesion(Sesion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);
    }

    public void PersonalUpdateTurno(Turno temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);
    }

    public void PersonalCrearTurno(Turno temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);
    }

    public ArrayList<FormularioSesion> InscritosSesion(long idSesion) {

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
            Hibernate.initialize(temp.getAsistenciaFTs());
            Hibernate.initialize(temp.getFamilia());
            allFormularios.add(temp);

        }

        return allFormularios;

    }

    public ArrayList<Asistente> asistentePorFormulario(long idFormulario) {

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

    public void PersonalCrearTaller(Taller temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);

    }

    public void PersonalUpdateTaller(Taller temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);

    }

    public void PersonalCrearGrupo(Grupo temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);

    }

    public void PersonalUpdateGrupo(Grupo temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);

    }

    public void PersonalCrearTurno2(Turno2 temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);

    }

    public void PersonalUpdateTurno2(Turno2 temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);

    }

    public void PersonalCrearReunion(Reunion temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);

    }

    public void PersonalUpdateReunion(Reunion temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);

    }

    public ArrayList<Taller> listaTalleres() {

        Session session = sessionFactory.getCurrentSession();

        Short numReun;
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

    public Taller getTaller(long id) {

        Session session = sessionFactory.getCurrentSession();
        Taller taller = new Taller();

        session.beginTransaction();
        String hql = "From Taller T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();

        taller = (Taller) queryResult;
        //Hibernate.initialize(taller.getGrupos());
        String hql2 = "From Grupo G where G.taller = :idT order by G.idgrupo asc";
        query = session.createQuery(hql2);
        query.setLong("idT", taller.getIdtaller());
        List listaGrupos = query.list();

        Set<Grupo> allGrupos = new LinkedHashSet<>();

        for (Iterator iter = listaGrupos.iterator(); iter.hasNext();) {
            Grupo temp = (Grupo) iter.next();
            Hibernate.initialize(temp.getTurno2s());
            allGrupos.add(temp);
        }

//        for (Grupo grup : taller.getGrupos()) {
//            Hibernate.initialize(grup.getTurno2s());
//            allGrupos.add(grup);
//        }
        taller.setGrupos(allGrupos);
        return taller;

    }

    public Grupo getGrupo(long id) {

        Session session = sessionFactory.getCurrentSession();
        Grupo grp = new Grupo();

        session.beginTransaction();
        String hql = "From Grupo G where G.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();

        grp = (Grupo) queryResult;
        Hibernate.initialize(grp.getTaller());

        String hql2 = "From Turno2 T where T.grupo = :idG order by T.idturno2 asc";
        query = session.createQuery(hql2);
        query.setLong("idG", grp.getIdgrupo());
        List listaT2 = query.list();

        //Hibernate.initialize(grp.getTurno2s());
        Set<Turno2> allTurno2 = new LinkedHashSet<>();
        for (Iterator iter = listaT2.iterator(); iter.hasNext();) {
            Turno2 temp = (Turno2) iter.next();
            Hibernate.initialize(temp.getReunions());
            allTurno2.add(temp);
        }
//        for (Turno2 t2 : grp.getTurno2s()) {
//            Hibernate.initialize(t2.getReunions());
//            allTurno2.add(t2);
//        }
        grp.setTurno2s(allTurno2);
        return grp;

    }

    public Turno2 getTurno2(long id) {

        Session session = sessionFactory.getCurrentSession();
        Turno2 t2 = new Turno2();

        session.beginTransaction();
        String hql = "From Turno2 T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();

        t2 = (Turno2) queryResult;
        //Hibernate.initialize(t2.getReunions());

        String hql2 = "From Reunion R where R.turno2 = :idT order by R.fecha ASC";
        query = session.createQuery(hql2);
        query.setLong("idT", t2.getIdturno2());
        List reuniones = query.list();
        Set<Reunion> reunions = new LinkedHashSet<>();
        for (Iterator iter = reuniones.iterator(); iter.hasNext();) {
            Reunion reu = (Reunion) iter.next();
            reunions.add(reu);
        }
        Hibernate.initialize(t2.getGrupo());
        t2.setReunions(reunions);

        return t2;

    }

    public Reunion getReunion(long id) {

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

    public ArrayList<FormularioSesion> InscritosTaller(long idTaller) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<FormularioSesion> allFormularios = new ArrayList();
        Taller tempTaller = new Taller();
        String hql = "From Taller T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idTaller);
        Object queryResult = query.uniqueResult();

        tempTaller = (Taller) queryResult;
        Hibernate.initialize(tempTaller.getGrupos());

        for (Grupo grp : tempTaller.getGrupos()) {
            Hibernate.initialize(grp.getTurno2s());

            for (Turno2 T2 : grp.getTurno2s()) {
                Hibernate.initialize(T2.getReunions());

                for (Reunion reun : T2.getReunions()) {
                    Hibernate.initialize(reun.getAsistenciaFRs());

                    for (AsistenciaFR afr : reun.getAsistenciaFRs()) {
                        Hibernate.initialize(afr.getFamilia().getFormularioSesions());

                        for (FormularioSesion formularioSesion : afr.getFamilia().getFormularioSesions()) {
                            Hibernate.initialize(formularioSesion.getAsistentes());

                            allFormularios.add(formularioSesion);
                        }

                    }
                }
            }

        }

        return allFormularios;

    }

    public ArrayList<FormularioSesion> formulariosReunion(long idReunion) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Reunion R where R.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idReunion);
        Object queryResult = query.uniqueResult();
        Reunion tempReun = (Reunion) queryResult;
        ArrayList<FormularioSesion> allFormularios = new ArrayList();

        String hql2 = "FROM AsistenciaFR AFR where AFR.reunion = :id order by AFR.familia asc";
        Query query2 = session.createQuery(hql2);
        query2.setLong("id", tempReun.getIdreunion());
        List asistencias = query2.list();
        Set<AsistenciaFR> asistenciaFRs = new LinkedHashSet<>();
        for (Iterator iter2 = asistencias.iterator(); iter2.hasNext();) {
            AsistenciaFR temp2 = (AsistenciaFR) iter2.next();
            asistenciaFRs.add(temp2);
        }
        //Hibernate.initialize(tempReun.getAsistenciaFRs());
        tempReun.setAsistenciaFRs(asistenciaFRs);

        long idtemp = 0;
        for (AsistenciaFR afr : tempReun.getAsistenciaFRs()) {
            Hibernate.initialize(afr.getFamilia());
            if (afr.getFamilia().getIdfamilia() != idtemp) {
                String hql3 = "FROM FormularioSesion F where F.familia = :id order by F.fechaSol DESC";
                Query query3 = session.createQuery(hql3);
                query3.setLong("id", afr.getFamilia().getIdfamilia());
                query3.setMaxResults(1);
                List resultados = query3.list();
                for (Iterator iter3 = resultados.iterator(); iter3.hasNext();) {
                    FormularioSesion temp3 = (FormularioSesion) iter3.next();
                    Hibernate.initialize(temp3.getAsistentes());
                    Hibernate.initialize(temp3.getFamilia().getAsistenciaFRs());
                    allFormularios.add(temp3);

                }
                idtemp = afr.getFamilia().getIdfamilia();
            }
        }

        return allFormularios;

    }

    public FormularioSesion getFormulario(long idFormulario) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM FormularioSesion F where F.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFormulario);
        Object queryResult = query.uniqueResult();
        FormularioSesion tempFs = (FormularioSesion) queryResult;
        Hibernate.initialize(tempFs.getAsistentes());
        return tempFs;
    }

    public Turno getTurno(long idTurno) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Turno T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idTurno);
        Object queryResult = query.uniqueResult();
        Turno tempTurno = (Turno) queryResult;

        return tempTurno;
    }

    public Turno getTurnoAsistencias(long idTurno) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Turno T where T.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idTurno);
        Object queryResult = query.uniqueResult();
        Turno tempTurno = (Turno) queryResult;
        Hibernate.initialize(tempTurno.getAsistenciaFTs());
        return tempTurno;
    }

    public void marcarAsistenciaSesion(AsistenciaFT aft) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(aft);

    }

    public ArrayList<AsistenciaFT> getAFT(long idFormulario) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM AsistenciaFT A where A.formularioSesion = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFormulario);
        List asistencias = query.list();
        ArrayList<AsistenciaFT> allAsistencias = new ArrayList();
        for (Iterator iter = asistencias.iterator(); iter.hasNext();) {
            AsistenciaFT temp = (AsistenciaFT) iter.next();
            allAsistencias.add(temp);
        }

        return allAsistencias;

    }

    public void crearCuentaFamilia(Familia fam, FormularioSesion fs) {

        Familia famrep = getFamilia(fam.getUser());
        String usuariotemp = fam.getUser();
        int idextra = 1;

        //Determinamos si el usuario de familia no se repite
        if (famrep != null) {
            if (famrep.getIdfamilia() != 0) {
                while (true) {
                    if (famrep != null) {
                        if (famrep.getIdfamilia() != 0) {
                            if (fam.getUser().equals(famrep.getUser())) {
                                fam.setUser(usuariotemp + idextra);
                                famrep = getFamilia(fam.getUser());
                            } else {
                                break;
                            }
                        }
                    }
                    idextra++;
                }
            }
        }

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        fam.getFormularioSesions().add(fs);
        fs.setFamilia(fam);

        session.save(fam);
        session.update(fs);
        //A partir de aqui pasamos los datos que ya tenemos a InfoFamilia
        InfoFamilia infofam = new InfoFamilia();
        infofam.setFamilia(fam);
        infofam.setDepRes(fs.getDepRes());
        infofam.setPaisRes(fs.getPaisRes());
        infofam.setDomicilio(fs.getDireccionRes());
        infofam.setEstadoCivil(fs.getEstadoCivil());
        infofam.setTelefono(fs.getTelefono());

        for (Iterator iter = fs.getAsistentes().iterator(); iter.hasNext();) {
            Asistente as = (Asistente) iter.next();
            Adoptante ad = new Adoptante();

            ad.setNombre(as.getNombre());
            ad.setApellidoP(as.getApellidoP());
            ad.setApellidoM(as.getApellidoM());
            ad.setSexo(as.getSexo());
            ad.setPaisNac(as.getPaisNac());
            ad.setDepaNac(as.getDepNac());
            ad.setFechaNac(as.getFechaNac());
            ad.setTipoDoc(as.getTipoDoc());
            ad.setNDoc(as.getNDoc());
            ad.setProfesion(as.getProfesion());
            ad.setCelular(as.getCelular());
            ad.setCorreo(as.getCorreo());

            infofam.getAdoptantes().add(ad);
        }

        session.save(infofam);
        for (Iterator iter2 = infofam.getAdoptantes().iterator(); iter2.hasNext();) {
            Adoptante ad = (Adoptante) iter2.next();
            ad.setInfoFamilia(infofam);
            session.save(ad);
        }

    }

    public void updateAsistenciaFR(AsistenciaFR afr) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(afr);
    }

    public void updateReunion(Reunion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);
    }

    public void updateExpFamInt(ExpedienteFamilia temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);
    }

    public void updateFam(Familia temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);
    }

    public ArrayList<AsistenciaFR> getAsistFR(long idFamilia, long idReunion) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql = "From AsistenciaFR AFR where AFR.familia = :idFamilia and AFR.reunion = :idReunion";
        Query query = session.createQuery(hql);
        query.setLong("idFamilia", idFamilia);
        query.setLong("idReunion", idReunion);
        List asistencias = query.list();
        ArrayList<AsistenciaFR> allAsistencias = new ArrayList();
        for (Iterator iter = asistencias.iterator(); iter.hasNext();) {
            AsistenciaFR temp = (AsistenciaFR) iter.next();
            allAsistencias.add(temp);
        }

        return allAsistencias;
    }

    public ArrayList<Entidad> ListaEntidades() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Entidad order by identidad asc";
        Query query = session.createQuery(hql);
        List entidades = query.list();
        ArrayList<Entidad> allEntidades = new ArrayList();
        for (Iterator iter = entidades.iterator(); iter.hasNext();) {
            Entidad temp = (Entidad) iter.next();
            allEntidades.add(temp);
        }
        return allEntidades;
    }

    public Entidad getEntidad(long id) {
        Session session = sessionFactory.getCurrentSession();
        Entidad Ent = new Entidad();

        session.beginTransaction();
        String hqlC = "FROM Entidad E WHERE E.id = :id";
        Query queryC = session.createQuery(hqlC);
        queryC.setLong("id", id);
        Object queryResultC = queryC.uniqueResult();

        Ent = (Entidad) queryResultC;

        return Ent;
    }

    /* FAMILIAS INTERNACIONALES */
    public ArrayList<ExpedienteFamilia> ListaFamiliasInt() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM ExpedienteFamilia EF WHERE EF.nacionalidad = :nac and EF.estado != :est order by EF.numeroExpediente DESC";
        Query query = session.createQuery(hql);
        query.setString("nac", "internacional");
        query.setString("est", "init");
        List entidades = query.list();
        ArrayList<ExpedienteFamilia> allExpedientesInt = new ArrayList();
        if (!query.list().isEmpty()) {
            for (Iterator iter = entidades.iterator(); iter.hasNext();) {
                ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
                allExpedientesInt.add(temp);
            }
        }
        return allExpedientesInt;
    }

    public void crearFamInt(Familia fam, ExpedienteFamilia expFam, InfoFamilia info) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        fam.getInfoFamilias().add(info);
        fam.getExpedienteFamilias().add(expFam);
        expFam.setFamilia(fam);
        info.setFamilia(fam);
        session.save(fam);
        session.save(expFam);
        session.save(info);

    }

    public void crearActualizarAdoptante(Adoptante temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(temp);
    }

    public void CambiaPass(Personal temp) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);
    }

//    public ArrayList<ExpedienteNna> FiltrarNna(ExpedienteNna expNna, Nna datosNna) {
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "";
//        if (expNna.getEstado().equals("eval") || expNna.getEstado().equals("desig")
//                || expNna.getEstado().equals("adop") || expNna.getEstado().equals("arch")) {
//            hql = "from ExpedienteNna E where E.NActual like :nombreAc "
//                    + "and E.apellidopActual like :apellidoPAct and E.apellidomActual like :apellidoMAct "
//                    + "and E.estado like :estado";
//        } else {
//            hql = "from ExpedienteNna E where E.NActual like :nombreAc "
//                    + "or E.apellidopActual like :apellidoPAct or E.apellidomActual like :apellidoMAct "
//                    + "or E.estado like :estado";
//        }
//        Query query = session.createQuery(hql);
//        query.setString("nombreAc", '%' + expNna.getNActual() + '%');
//        query.setString("apellidoPAct", '%' + expNna.getApellidopActual() + '%');
//        query.setString("apellidoMAct", '%' + expNna.getApellidomActual() + '%');
//        query.setString("estado", '%' + expNna.getEstado() + '%');
//
//        List expedientes = query.list();
//        ArrayList<ExpedienteNna> allExpedientes = new ArrayList();
//        if (!expedientes.isEmpty()) {
//            for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
//                ExpedienteNna temp = (ExpedienteNna) iter.next();
//                Hibernate.initialize(temp.getNna());
//
//                if (datosNna.getEspecial() == 0 || datosNna.getEnfermo() == 0 || datosNna.getAdolescente() == 0
//                        || datosNna.getMayor() == 0 || datosNna.getHermano() == 0) {
//
//                    if (temp.getNna().getNombre().contains(datosNna.getNombre())
//                            && temp.getNna().getApellidoP().contains(datosNna.getApellidoP())
//                            && temp.getNna().getApellidoM().contains(datosNna.getApellidoM())
//                            && temp.getNna().getEspecial() == datosNna.getEspecial()
//                            && temp.getNna().getEnfermo() == datosNna.getEnfermo()
//                            && temp.getNna().getAdolescente() == datosNna.getAdolescente()
//                            && temp.getNna().getMayor() == datosNna.getMayor()
//                            && temp.getNna().getHermano() == datosNna.getHermano()) {
//
//                        allExpedientes.add(temp);
//                    }
//                } else if (temp.getNna().getNombre().contains(datosNna.getNombre())
//                        && temp.getNna().getApellidoP().contains(datosNna.getApellidoP())
//                        && temp.getNna().getApellidoM().contains(datosNna.getApellidoM())) {
//                    allExpedientes.add(temp);
//                }
//
//            }
//
//        }
//
//        return allExpedientes;
//
//    }
    public ArrayList<ExpedienteNna> FiltrarNna(ExpedienteNna expNna, Nna datosNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ArrayList<ExpedienteNna> allExpedientes = new ArrayList();
        final String strEstado = expNna.getEstado();
        final String strClas = datosNna.getClasificacion();
        final String strNombre = datosNna.getNombre();
        final String strApellidoP = datosNna.getApellidoP();
        final String strApellidoM = datosNna.getApellidoM();
        final Short esp = datosNna.getEspecial();
        final Short enf = datosNna.getEnfermo();
        final Short mayor = datosNna.getMayor();
        final Short adoles = datosNna.getAdolescente();
        final Short hermano = datosNna.getHermano();
        final String strNombre_Act = expNna.getNActual();
        final String strApellidoP_Act = expNna.getApellidopActual();
        final String strApellidoM_Act = expNna.getApellidomActual();
        final String sexo = datosNna.getSexo();  //Agregado 20-03
        final Long idcar = datosNna.getCar().getIdcar();  //Agregado 20-03
        final Short edad = datosNna.getEdadAnhos(); //Agregado 20-03

        Short edadinicial = 19;
        Short edadfinal = 19;

        switch (edad) {
            case 0:
                edadinicial = 0;
                edadfinal = 0;
                break;
            case 1:
                edadinicial = 0;
                edadfinal = 8;
                break;
            case 2:
                edadinicial = 9;
                edadfinal = 11;
                break;
            case 3:
                edadinicial = 12;
                edadfinal = 18;
                break;
        }
        final Short edadini = edadinicial;
        final Short edadfin = edadfinal;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql;
                CallableStatement statement;

                ResultSet rs;
                if (idcar != 0) {
                    hql = "{call PERS_FILTRAR_NNA_CAR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                    statement = connection.prepareCall(hql);
                    statement.setString(1, strEstado);
                    statement.setString(2, strClas);
                    statement.setString(3, strNombre);
                    statement.setString(4, strApellidoP);
                    statement.setString(5, strApellidoM);
                    statement.setShort(6, esp);
                    statement.setShort(7, enf);
                    statement.setShort(8, mayor);
                    statement.setShort(9, adoles);
                    statement.setShort(10, hermano);
                    statement.setString(11, strNombre_Act);
                    statement.setString(12, strApellidoP_Act);
                    statement.setString(13, strApellidoM_Act);
                    statement.setString(14, sexo);
                    statement.setLong(15, idcar);
                    statement.registerOutParameter(16, OracleTypes.CURSOR);
                    statement.execute();

                    rs = (ResultSet) statement.getObject(16);
                } else {
                    hql = "{call PERS_FILTRAR_NNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                    statement = connection.prepareCall(hql);
                    statement.setString(1, strEstado);
                    statement.setString(2, strClas);
                    statement.setString(3, strNombre);
                    statement.setString(4, strApellidoP);
                    statement.setString(5, strApellidoM);
                    statement.setShort(6, esp);
                    statement.setShort(7, enf);
                    statement.setShort(8, mayor);
                    statement.setShort(9, adoles);
                    statement.setShort(10, hermano);
                    statement.setString(11, strNombre_Act);
                    statement.setString(12, strApellidoP_Act);
                    statement.setString(13, strApellidoM_Act);
                    statement.setString(14, sexo);
                    statement.registerOutParameter(15, OracleTypes.CURSOR);
                    statement.execute();

                    rs = (ResultSet) statement.getObject(15);
                }

                while (rs.next()) {
                    ExpedienteNna tempExpNna = new ExpedienteNna();
                    Nna tempNna = new Nna();

                    tempExpNna.setIdexpedienteNna(rs.getLong("IDEXPEDIENTE_NNA"));
                    tempExpNna.setEstado(rs.getString("ESTADO"));
                    tempExpNna.setNActual(rs.getString("N_ACTUAL"));
                    tempExpNna.setApellidopActual(rs.getString("APELLIDOP_ACTUAL"));
                    tempExpNna.setApellidomActual(rs.getString("APELLIDOM_ACTUAL"));

                    tempNna.setIdnna(rs.getLong("IDNNA"));
                    tempNna.setClasificacion(rs.getString("CLASIFICACION"));
                    tempNna.setNombre(rs.getString("NOMBRE"));
                    tempNna.setApellidoP(rs.getString("APELLIDO_P"));
                    tempNna.setApellidoM(rs.getString("APELLIDO_M"));
                    tempNna.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));

                    boolean flag = false;
                    for (ExpedienteNna aux : allExpedientes) {
                        if (aux.getNna().getIdnna() == tempNna.getIdnna()) {
                            flag = true;
                        }
                    }

                    if (flag) {
                        continue;
                    }

                    int edad = 0;
                    if (tempNna.getFechaNacimiento() != null) {
                        Date fechaAct = new Date();
                        int añoAct = fechaAct.getYear();
                        edad = añoAct - tempNna.getFechaNacimiento().getYear();
                        if ((tempNna.getFechaNacimiento().getMonth() - fechaAct.getMonth())
                                > 0) {
                            edad--;
                        } else if ((tempNna.getFechaNacimiento().getMonth()
                                - fechaAct.getMonth()) == 0) {
                            if ((tempNna.getFechaNacimiento().getDate() - fechaAct.getDate())
                                    > 0) {
                                edad--;
                            }
                        }
                    }

                    if (edadini != 19 && edadfin != 19) {
                        if (edadini == 0 && edadfin == 0) {
                            tempExpNna.setNna(tempNna);
                            allExpedientes.add(tempExpNna);
                        } else if (edad >= edadini && edad <= edadfin) {
                            tempExpNna.setNna(tempNna);
                            allExpedientes.add(tempExpNna);
                        }
                    } else {
                        tempExpNna.setNna(tempNna);
                        allExpedientes.add(tempExpNna);
                    }
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allExpedientes;
    }

    public ArrayList<ExpedienteFamilia> FiltrarFam(ExpedienteFamilia expFam, InfoFamilia infoFam) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "";
        Query query = session.createQuery(hql);
        /*   if (expFam.getEstado().equals("evaluacion") || expFam.getEstado().equals("espera")
         || expFam.getEstado().equals("estudio") || expFam.getEstado().equals("designado")
         || expFam.getEstado().equals("adopcion") || expFam.getEstado().equals("reevaluacion")
         || expFam.getEstado().equals("post") || expFam.getEstado().equals("eliminado")) {
         hql = "from ExpedienteFamilia E where E.expediente like :exp "
         + "and E.estado = :estado ";
         query = session.createQuery(hql);
         query.setString("exp", '%' + expFam.getExpediente() + '%');
         query.setString("estado", expFam.getEstado());
         }

         if (expFam.getTipoFamilia().equals("PP") || expFam.getTipoFamilia().equals("PE")
         || expFam.getTipoFamilia().equals("MP") || expFam.getTipoFamilia().equals("ME")
         || expFam.getTipoFamilia().equals("EP") || expFam.getTipoFamilia().equals("EE")) {
         hql = "from ExpedienteFamilia E where "
         + " E.tipoFamilia like :tipoFamilia ";
         query = session.createQuery(hql);
         query.setString("tipoFamilia", expFam.getTipoFamilia() + '%');
         } else if (expFam.getTipoFamilia().equals("none")) {
         hql = "from ExpedienteFamilia E where E.expediente like :exp "
         + "or E.estado like :estado ";
         query = session.createQuery(hql);
         query.setString("exp", '%' + expFam.getExpediente() + '%');
         query.setString("estado", expFam.getEstado() + '%');
         }*/

        hql = "from ExpedienteFamilia E where ";

        if (!(expFam.getExpediente() == "")) {
            hql = hql + " E.expediente like :exp ";

            query = session.createQuery(hql);
            query.setString("exp", '%' + expFam.getExpediente() + '%');
        }

        if (!(expFam.getHt() == "")) {
            if (hql.equals("from ExpedienteFamilia E where ")) {
                hql = hql + " E.ht like :ht ";

                query = session.createQuery(hql);
                query.setString("ht", '%' + expFam.getHt() + '%');
            } else {

                hql = hql + " and E.ht like :ht ";

                query = session.createQuery(hql);
                if (!(expFam.getExpediente() == "")) {
                    query.setString("exp", '%' + expFam.getExpediente() + '%');
                }

                query.setString("ht", '%' + expFam.getHt() + '%');
            }
        }

        if (!(expFam.getNacionalidad() == "none")) {
            if (hql.equals("from ExpedienteFamilia E where ")) {
                hql = hql + " E.nacionalidad = :nacionalidad ";

                query = session.createQuery(hql);
                query.setString("nacionalidad", expFam.getNacionalidad());
            } else {

                hql = hql + " and E.nacionalidad = :nacionalidad ";

                query = session.createQuery(hql);
                if (!(expFam.getExpediente() == "")) {
                    query.setString("exp", '%' + expFam.getExpediente() + '%');
                }
                if (!(expFam.getHt() == "")) {
                    query.setString("ht", '%' + expFam.getHt() + '%');
                }

                query.setString("nacionalidad", expFam.getNacionalidad());
            }
        }

        if (!(expFam.getEstado() == "none")) {
            if (hql.equals("from ExpedienteFamilia E where ")) {
                hql = hql + " E.estado = :estado ";

                query = session.createQuery(hql);
                query.setString("estado", expFam.getEstado());
            } else {

                hql = hql + " and E.estado like :estado ";

                query = session.createQuery(hql);
                if (!(expFam.getExpediente() == "")) {
                    query.setString("exp", '%' + expFam.getExpediente() + '%');
                }
                if (!(expFam.getHt() == "")) {
                    query.setString("ht", '%' + expFam.getHt() + '%');
                }

                if (!(expFam.getNacionalidad() == "none")) {
                    query.setString("nacionalidad", expFam.getNacionalidad());
                }

                query.setString("estado", expFam.getEstado());
            }
        }

        if (!(expFam.getTipoFamilia() == "none")) {
            if (hql.equals("from ExpedienteFamilia E where ")) {
                hql = hql + " E.tipoFamilia = :tipoFamilia ";

                query = session.createQuery(hql);
                query.setString("tipoFamilia", expFam.getTipoFamilia());
            } else {

                hql = hql + " and E.tipoFamilia like :tipoFamilia ";

                query = session.createQuery(hql);
                if (!(expFam.getExpediente() == "")) {
                    query.setString("exp", '%' + expFam.getExpediente() + '%');
                }
                if (!(expFam.getHt() == "")) {
                    query.setString("ht", '%' + expFam.getHt() + '%');
                }

                if (!(expFam.getNacionalidad() == "none")) {
                    query.setString("nacionalidad", expFam.getNacionalidad());
                }

                if (!(expFam.getEstado() == "none")) {
                    query.setString("estado", expFam.getEstado());
                }

                query.setString("tipoFamilia", expFam.getTipoFamilia());
            }
        }

        ArrayList<ExpedienteFamilia> allExpedientes = new ArrayList();
        if (!infoFam.getDepRes().equals("none")) {
            //FALTA
            if (!hql.equals("from ExpedienteFamilia E where ")) {
                List expedientes = query.list();
                if (!expedientes.isEmpty()) {
                    for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
                        ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
                        Hibernate.initialize(temp.getFamilia());
                        Hibernate.initialize(temp.getUnidad());
                        allExpedientes.add(temp);
                    }
                }
            } else {
                //FALTA
                hql = "FROM INFO_FAMILIA F WHERE F.DEP_RES = :dep";
                query = session.createQuery(hql);
                query.setString("dep", infoFam.getDepRes());

                List listaFamilias = query.list();
                ArrayList<InfoFamilia> fami = new ArrayList<InfoFamilia>();
                for (Iterator iter = listaFamilias.iterator(); iter.hasNext();) {
                    InfoFamilia ifa = (InfoFamilia) iter.next();
                    Hibernate.initialize(ifa.getFamilia());
                    fami.add(ifa);
                }

            }
        } else {
            List expedientes = query.list();
            if (!expedientes.isEmpty()) {
                for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
                    ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
                    Hibernate.initialize(temp.getFamilia());
                    Hibernate.initialize(temp.getUnidad());
                    allExpedientes.add(temp);
                }
            }
        }
        return allExpedientes;
    }

    public void InsertLog(Personal personal, String Tipo_registro, String Numero_registro, String mensaje) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        Log log = new Log();
        Date date = new Date();
        timeStampFormat st = new timeStampFormat();

        log.setPersonal(personal);
        log.setFecha(st.stringToTimestamp(date.toString()));
        log.setTipoReg(Tipo_registro);
        log.setNReg(Numero_registro);
        log.setIncidencia(mensaje);

        session.save(log);

    }

    public ArrayList<Log> getLogParticular(long idPersonal) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Log L where L.personal = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idPersonal);
        List log = query.list();
        ArrayList<Log> allLogsParticular = new ArrayList();
        for (Iterator iter = log.iterator(); iter.hasNext();) {
            Log temp = (Log) iter.next();
            Hibernate.initialize(temp.getPersonal());
            temp.getPersonal().getNombre();
            temp.getPersonal().getApellidoP();
            allLogsParticular.add(temp);
        }

        return allLogsParticular;

    }

    public ArrayList<Log> getLogParticularPorDia(long idPersonal, String dia) {

        timeStampFormat timestamp = new timeStampFormat();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String diain = dia + " 00:00:00";
        String diafin = dia + " 23:59:59";

        String hql = "FROM Log L where L.personal = :id and L.fecha >= :diain and L.fecha <= :diafin";
        Query query = session.createQuery(hql);
        query.setLong("id", idPersonal);
        query.setTimestamp("diain", timestamp.stringToTimestampLog(diain));
        query.setTimestamp("diafin", timestamp.stringToTimestampLog(diafin));
        List log = query.list();
        ArrayList<Log> allLogsParticularPorDia = new ArrayList();
        for (Iterator iter = log.iterator(); iter.hasNext();) {
            Log temp = (Log) iter.next();
            Hibernate.initialize(temp.getPersonal());
            allLogsParticularPorDia.add(temp);
        }

        return allLogsParticularPorDia;
    }

    public ArrayList<Log> getLogPersonal() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Log L ";
        Query query = session.createQuery(hql);
        List log = query.list();
        ArrayList<Log> allLogsParticular = new ArrayList();
        for (Iterator iter = log.iterator(); iter.hasNext();) {
            Log temp = (Log) iter.next();
            Hibernate.initialize(temp.getPersonal());
            allLogsParticular.add(temp);
        }

        return allLogsParticular;

    }

    public ArrayList<Log> getLogPersonalPorDia(String dia) {

        timeStampFormat timestamp = new timeStampFormat();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String diain = dia + " 00:00:00";
        String diafin = dia + " 23:59:59";

        String hql = "FROM Log L where L.fecha >= :diain and L.fecha <= :diafin";
        Query query = session.createQuery(hql);
        query.setTimestamp("diain", timestamp.stringToTimestampLog(diain));
        query.setTimestamp("diafin", timestamp.stringToTimestampLog(diafin));
        List log = query.list();
        ArrayList<Log> allLogsParticularPorDia = new ArrayList();
        for (Iterator iter = log.iterator(); iter.hasNext();) {
            Log temp = (Log) iter.next();
            Hibernate.initialize(temp.getPersonal());
            allLogsParticularPorDia.add(temp);
        }

        return allLogsParticularPorDia;

    }

    public Familia getFamilia(long idFamilia) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Familia F where F.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        Object queryResult = query.uniqueResult();
        Familia tempFa = (Familia) queryResult;

        return tempFa;
    }

    public Familia getFamilia(String user) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "FROM Familia F where F.user = :user";
        Query query = session.createQuery(hql);
        query.setString("user", user);
        Object queryResult = query.uniqueResult();
        Familia tempFa = (Familia) queryResult;

        return tempFa;
    }

    public static synchronized String generateUniqueToken(Integer length) {

        byte random[] = new byte[length];
        Random randomGenerator = new Random();
        StringBuffer buffer = new StringBuffer();

        randomGenerator.nextBytes(random);

        for (int j = 0; j < random.length; j++) {
            byte b1 = (byte) ((random[j] & 0xf0) >> 4);
            byte b2 = (byte) (random[j] & 0x0f);
            if (b1 < 10) {
                buffer.append((char) ('0' + b1));
            } else {
                buffer.append((char) ('A' + (b1 - 10)));
            }
            if (b2 < 10) {
                buffer.append((char) ('0' + b2));
            } else {
                buffer.append((char) ('A' + (b2 - 10)));
            }
        }

        return (buffer.toString());
    }

    public ArrayList<Grupo> listaGruposTurnos2Reuniones(Long idTaller) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idT = idTaller;
        final ArrayList<Grupo> ListaGrupos = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call PERS_LISTAGRUPOS(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idT);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Grupo tempGrp = new Grupo();
                    tempGrp.setIdgrupo(rs.getLong("IDGRUPO"));
                    tempGrp.setNombre(rs.getString("NOMBRE"));
                    Set<Turno2> listTurno2 = new LinkedHashSet<>();
                    String hql2 = "{call PERS_LISTA_TURNO2(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempGrp.getIdgrupo());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Turno2 tempT2 = new Turno2();
                        tempT2.setIdturno2(rs2.getLong("IDTURNO2"));
                        tempT2.setNombre(rs2.getString("NOMBRE"));
                        tempT2.setGrupo(tempGrp);
                        Set<Reunion> listR = new LinkedHashSet<>();
                        String hql3 = "{call PERS_LISTA_REUNION(?,?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, tempT2.getIdturno2());
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();

                        ResultSet rs3 = (ResultSet) statement3.getObject(2);

                        while (rs3.next()) {
                            Reunion tempR = new Reunion();
                            tempR.setIdreunion(rs3.getLong("IDREUNION"));
                            tempR.setFecha(rs3.getDate("FECHA"));
                            tempR.setHora(rs3.getString("HORA"));
                            tempR.setDuracion(rs3.getString("DURACION"));
                            tempR.setDireccion(rs3.getString("DIRECCION"));
                            tempR.setTurno2(tempT2);
                            listR.add(tempR);

                        }
                        rs3.close();
                        statement3.close();

                        tempT2.setReunions(listR);
                        listTurno2.add(tempT2);
                    }
                    rs2.close();
                    statement2.close();
                    tempGrp.setTurno2s(listTurno2);
                    ListaGrupos.add(tempGrp);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return ListaGrupos;
    }

    public ArrayList<FormularioSesion> InscritosReunion(long idReunion) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idR = idReunion;
        final ArrayList<FormularioSesion> allFormularios = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call PERS_ASIS_REUNION(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idR);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    FormularioSesion tempFs = new FormularioSesion();
                    Familia tempF = new Familia();
                    Sesion tempS = new Sesion();
                    tempF.setIdfamilia(rs.getLong("IDFAMILIA"));
                    tempS.setIdsesion(rs.getLong("IDSESION"));
                    tempFs.setIdformularioSesion(rs.getLong("IDFORMULARIO_SESION"));
                    tempFs.setFamilia(tempF);
                    tempFs.setSesion(tempS);
                    Set<Asistente> listAsis = new HashSet<Asistente>(0);
                    String hql2 = "{call HE_GET_ASIS_BY_IDFS(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempFs.getIdformularioSesion());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Asistente tempA = new Asistente();
                        tempA.setIdasistente(rs2.getLong("IDASISTENTE"));
                        tempA.setNombre(rs2.getString("NOMBRE"));
                        tempA.setApellidoP(rs2.getString("APELLIDO_P"));
                        tempA.setApellidoM(rs2.getString("APELLIDO_M"));
                        String tempsexo = "";
                        tempsexo = rs2.getString("SEXO");
                        if (!rs2.wasNull()) {
                            tempA.setSexo(tempsexo.charAt(0));
                        }
                        tempA.setEdad(rs2.getShort("EDAD"));
                        tempA.setCorreo(rs2.getString("CORREO"));
                        tempA.setFormularioSesion(tempFs);
                        listAsis.add(tempA);

                    }
                    rs2.close();
                    statement2.close();
                    tempFs.setAsistentes(listAsis);
                    allFormularios.add(tempFs);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allFormularios;

    }

    public void EliminarSesion(Long idSesion) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idS = idSesion;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call PERS_LISTA_TURNOS(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idS);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Turno tempT2 = new Turno();
                    tempT2.setIdturno(rs.getLong("IDTURNO"));
                    String hql2 = "{call PERS_DELETE_TURNO(?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempT2.getIdturno());
                    statement2.execute();
                    statement2.close();

                }
                rs.close();
                statement.close();

                String hql3 = "{call PERS_DELETE_SESION(?)}";
                CallableStatement statement3 = connection.prepareCall(hql3);
                statement3.setLong(1, idS);
                statement3.execute();
                statement3.close();
            }
        };

        session.doWork(work);
    }

    public void EliminarTaller(Long idTaller) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idT = idTaller;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call PERS_LISTAGRUPOS(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idT);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Grupo tempGrp = new Grupo();
                    tempGrp.setIdgrupo(rs.getLong("IDGRUPO"));
                    tempGrp.setNombre(rs.getString("NOMBRE"));
                    String hql2 = "{call PERS_LISTA_TURNO2(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempGrp.getIdgrupo());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Turno2 tempT2 = new Turno2();
                        tempT2.setIdturno2(rs2.getLong("IDTURNO2"));
                        tempT2.setNombre(rs2.getString("NOMBRE"));
                        tempT2.setGrupo(tempGrp);
                        String hql3 = "{call PERS_LISTA_REUNION(?,?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, tempT2.getIdturno2());
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();

                        ResultSet rs3 = (ResultSet) statement3.getObject(2);

                        while (rs3.next()) {
                            Reunion tempR = new Reunion();
                            tempR.setIdreunion(rs3.getLong("IDREUNION"));
                            String hql4 = "{call PERS_DELETE_REUNION(?)}";
                            CallableStatement statement4 = connection.prepareCall(hql4);
                            statement4.setLong(1, tempR.getIdreunion());
                            statement4.execute();
                            statement4.close();

                        }
                        rs3.close();
                        statement3.close();
                        String hql5 = "{call PERS_DELETE_TURNO2(?)}";
                        CallableStatement statement5 = connection.prepareCall(hql5);
                        statement5.setLong(1, tempT2.getIdturno2());
                        statement5.execute();
                        statement5.close();
                    }
                    rs2.close();
                    statement2.close();
                    String hql6 = "{call PERS_DELETE_GRUPO(?)}";
                    CallableStatement statement6 = connection.prepareCall(hql6);
                    statement6.setLong(1, tempGrp.getIdgrupo());
                    statement6.execute();
                    statement6.close();
                }
                rs.close();
                statement.close();
                String hql7 = "{call PERS_DELETE_TALLER(?)}";
                CallableStatement statement7 = connection.prepareCall(hql7);
                statement7.setLong(1, idT);
                statement7.execute();
                statement7.close();
            }
        };

        session.doWork(work);

    }

    public void DeleteTurnoSesion(Turno temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(temp);

    }

    public void DeleteGrupoTaller(Grupo temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(temp);

    }

    public void DeleteTurno2Grupo(Turno2 temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(temp);

    }

    public void DeleteReunionTurno2(Reunion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(temp);

    }

    public Taller getTallerByIdReunion(long id) {

        Session session = sessionFactory.getCurrentSession();
        Reunion reun = new Reunion();

        session.beginTransaction();
        String hql = "From Reunion R where R.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResult = query.uniqueResult();

        reun = (Reunion) queryResult;
        Hibernate.initialize(reun.getTurno2().getGrupo().getTaller());
        Taller tempT = reun.getTurno2().getGrupo().getTaller();
        return tempT;

    }

    public ArrayList<ExpedienteFamilia> ListaExpedientesActuales() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM ExpedienteFamilia EF order by EF.numeroExpediente ASC";
        Query query = session.createQuery(hql);
        List exp = query.list();
        ArrayList<ExpedienteFamilia> allExpedientes = new ArrayList();

        for (Iterator iter = exp.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();

            allExpedientes.add(temp);
        }

        return allExpedientes;
    }
    
    public ArrayList<String> listaNumExpActuales() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<String> lista = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call LISTANUMEXPACT(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    lista.add(rs.getString("NUMERO_EXPEDIENTE"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return lista;
    }

    public void EliminarFamiliasInternacionales() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM ExpedienteFamilia EF where EF.nacionalidad = :tipo and EF.estado = :est";
        Query query = session.createQuery(hql);
        query.setString("tipo", "internacional");
        query.setString("est", "init");
        List exp = query.list();
        ArrayList<ExpedienteFamilia> allExpedientes = new ArrayList();

        for (Iterator iter = exp.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getFamilia().getInfoFamilias());
            allExpedientes.add(temp);
        }

        for (ExpedienteFamilia expedienteFamilia : allExpedientes) {
            Familia tempFam = expedienteFamilia.getFamilia();
            Set<InfoFamilia> infoFamilias = new HashSet<InfoFamilia>(0);
            infoFamilias = tempFam.getInfoFamilias();
            for (InfoFamilia infoFamilia : infoFamilias) {
                session.delete(infoFamilia);
            }
            session.delete(expedienteFamilia);
            session.delete(tempFam);
        }

    }

    public void EliminarExpedientesNacionales() {
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM ExpedienteFamilia EF where EF.nacionalidad = :tipo and EF.estado = :est";
        Query query = session.createQuery(hql);
        query.setString("tipo", "nacional");
        query.setString("est", "init");
        List exp = query.list();
        ArrayList<ExpedienteFamilia> allExpedientes = new ArrayList();

        for (Iterator iter = exp.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            allExpedientes.add(temp);
        }
        
        for (ExpedienteFamilia expedienteFamilia : allExpedientes) {
            session.delete(expedienteFamilia);
        }
        
                
    }
    
    
    
    public boolean VerificarNumExp(String num) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hqlO = "FROM ExpedienteFamilia EF where EF.numeroExpediente = :idExp";
        Query queryO = session.createQuery(hqlO);
        queryO.setString("idExp", num);
        Object queryResultA = queryO.uniqueResult();

        if (queryResultA != null) {
            return true;
        } else {
            return false;
        }

    }

}
