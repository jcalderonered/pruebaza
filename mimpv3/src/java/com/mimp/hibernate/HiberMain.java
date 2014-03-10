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
        
        pass = DigestUtils.sha512Hex(pass);

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
            Hibernate.initialize(personal.getUnidad());
            temp.add("personal");
            temp.add(personal);
            return temp;
        } else if (queryResultF != null) {
            familia = (Familia) queryResultF;
            Hibernate.initialize(familia.getInfoFamilias());
            Hibernate.initialize(familia.getFormularioSesions());
            Hibernate.initialize(familia.getFichaSolicitudAdopcions());
            for (Iterator iter = familia.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();
                Hibernate.initialize(ifa.getAdoptantes());
                Hibernate.initialize(ifa.getHijoActs());
                Hibernate.initialize(ifa.getResidenteActs());
            }
            for (Iterator iter2 = familia.getFichaSolicitudAdopcions().iterator(); iter2.hasNext();) {
                FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) iter2.next();
                Hibernate.initialize(ficha.getSolicitantes());
                Hibernate.initialize(ficha.getHijos());
                Hibernate.initialize(ficha.getResidentes());
            }
            temp.add("familia");
            temp.add(familia);
            return temp;
        } else if (queryResultE != null) {
            entidad = (Entidad) queryResultE;
            Hibernate.initialize(entidad.getAutoridads());
            Hibernate.initialize(entidad.getOrganismos());
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

    public void InsertFormGrp(Asistente asisEl, Asistente asisElla, FormularioSesion fs, AsistenciaFT aft, AsistenciaFT aft2) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        asisEl.setFormularioSesion(fs);
        fs.getAsistentes().add(asisEl);
        
        asisElla.setFormularioSesion(fs);
        fs.getAsistentes().add(asisElla);
        
        aft.setFormularioSesion(fs);
        aft2.setFormularioSesion(fs);
        fs.getAsistenciaFTs().add(aft);
        fs.getAsistenciaFTs().add(aft2);
        
        session.save(fs);
        session.save(asisEl);
        session.save(asisElla);
        session.save(aft);
        session.save(aft2);
        
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
            Hibernate.initialize(temp.getAsistenciaFTs());
            allTurnos.add(temp);
        }
        return allTurnos;

    }
    
    public InfoFamilia getInfoFamPorIdFamilia(long idFamilia){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        InfoFamilia temp = new InfoFamilia();
        
        String hql = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
        Query query = session.createQuery(hql);
        query.setLong("id", idFamilia);
        query.setMaxResults(1);
        Object queryResult = query.uniqueResult();
        temp = (InfoFamilia) queryResult;
        Hibernate.initialize(temp.getFamilia());
        Hibernate.initialize(temp.getAdoptantes());
        //Hibernate.initialize(temp.getHijoActs());
        //Hibernate.initialize(temp.getResidenteActs());
        
        return temp;
    }
    
    public InfoFamilia getInfoFamPorIdPropio(long idInfoFam){
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        InfoFamilia temp = new InfoFamilia();
        
        String hql = "from InfoFamilia IF where IF.idinfoFamilia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idInfoFam);
        Object queryResult = query.uniqueResult();
        temp = (InfoFamilia) queryResult;
        
        return temp;
    }
    
    public void crearInfoFam(InfoFamilia temp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.save(temp);
    }
    
    public void updateInfoFam(InfoFamilia temp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.update(temp);
    }
    
    public ArrayList<Atencion> getListaAtencionesPorFamilia(long idFam) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Atencion A where A.familia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFam);
        List atenciones = query.list();
        ArrayList<Atencion> allAtenciones = new ArrayList();
        for (Iterator iter = atenciones.iterator(); iter.hasNext();) {
            Atencion temp = (Atencion) iter.next();
            Hibernate.initialize(temp.getPersonal());
            allAtenciones.add(temp);
        }
        return allAtenciones;

    }
    
    public Atencion getAtencion(long idAtencion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        Atencion temp = new Atencion();
        String hql = "From Atencion A where A.idatencion = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idAtencion);
        Object queryResult = query.uniqueResult();
        temp = (Atencion) queryResult;
        Hibernate.initialize(temp.getPersonal());
            
        return temp;

    }
    
    public ArrayList<Sesion> getListaSesionesPorFamilia(long idFam) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From FormularioSesion FS where FS.familia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFam);
        List formularios = query.list();
        ArrayList<Sesion> allSesiones = new ArrayList();
        
        for (Iterator iter = formularios.iterator(); iter.hasNext();) {
            FormularioSesion temp = (FormularioSesion) iter.next();
            Hibernate.initialize(temp.getSesion());
            allSesiones.add(temp.getSesion());
        }
        
        return allSesiones;

    }
    
    public ArrayList<AsistenciaFR> getListaAsistenciaFRPorFamilia(long idFam) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From AsistenciaFR AFR where AFR.familia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idFam);
        List asistencia = query.list();
        ArrayList<AsistenciaFR> allAsistenciaFR = new ArrayList();
        
        for (Iterator iter = asistencia.iterator(); iter.hasNext();) {
            AsistenciaFR temp = (AsistenciaFR) iter.next();
            Hibernate.initialize(temp.getReunion().getTurno2().getGrupo().getTaller());
            allAsistenciaFR.add(temp);
        }
        
        return allAsistenciaFR;

    }
    
    public ExpedienteFamilia getInformacionRegistro(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        String hqlA = "FROM ExpedienteFamilia EF WHERE EF.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        expFamilia = (ExpedienteFamilia) queryResultA;
        Hibernate.initialize(expFamilia.getUnidad());
        Hibernate.initialize(expFamilia.getFamilia().getEntidad());
        return expFamilia;
        
    }
    
    public void updateAdoptante ( Adoptante temp){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);
    
    }
    
    public void crearAtencion ( Atencion temp){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(temp);
    
    }
    
    public void updateAtencion ( Atencion temp){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(temp);
    
    }
    
    public Sesion getInfoSesion (long id){
    
        Session session = sessionFactory.getCurrentSession();
        Sesion sesion = new Sesion();

        session.beginTransaction();
        String hql = "From Sesion S where S.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", id);
        Object queryResultU = query.uniqueResult();

        sesion = (Sesion) queryResultU;
        return sesion;
    
    }
    
    public ArrayList<Reunion> getListaReunionesPorTurno(long idTurno) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Reunion R where R.turno2 = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idTurno);
        List reuniones = query.list();
        ArrayList<Reunion> allReuniones = new ArrayList();
        
        for (Iterator iter = reuniones.iterator(); iter.hasNext();) {
            Reunion temp = (Reunion) iter.next();
            allReuniones.add(temp);
        }
        
        return allReuniones;

    }
    
    public ArrayList<Evaluacion> getListaEvaluacionesPorExpediente(long idExp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Evaluacion E where E.expedienteFamilia = :id ORDER BY E.idevaluacion DESC";
        Query query = session.createQuery(hql);
        query.setLong("id", idExp);
        List evaluaciones = query.list();
        ArrayList<Evaluacion> allEvaluaciones = new ArrayList();
        
        for (Iterator iter = evaluaciones.iterator(); iter.hasNext();) {
            Evaluacion temp = (Evaluacion) iter.next();
            Hibernate.initialize(temp.getResolucions());
            allEvaluaciones.add(temp);
        }
        
        return allEvaluaciones;

    }
    
    public ArrayList<Designacion> getListaDesignacionesPorExpediente(long idExp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Designacion D where D.expedienteFamilia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idExp);
        List designaciones = query.list();
        ArrayList<Designacion> allDesignaciones = new ArrayList();
        
        for (Iterator iter = designaciones.iterator(); iter.hasNext();) {
            Designacion temp = (Designacion) iter.next();
            if (temp.getAceptacionConsejo() == 2){
                Hibernate.initialize(temp.getNna().getExpedienteNnas());
                Hibernate.initialize(temp.getNna().getJuzgado());
                Hibernate.initialize(temp.getNna().getCar());
            }else {
                Hibernate.initialize(temp.getNna());
            }
            allDesignaciones.add(temp);
        }
        
        return allDesignaciones;

    }
    
    public ArrayList<Designacion> getListaNnaAdoptantesAdopcion(long idExp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Designacion D where D.expedienteFamilia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idExp);
        List designaciones = query.list();
        ArrayList<Designacion> allDesignaciones = new ArrayList();
        
        for (Iterator iter = designaciones.iterator(); iter.hasNext();) {
            Designacion temp = (Designacion) iter.next();
            if (temp.getAceptacionConsejo() == 4){
                Hibernate.initialize(temp.getNna());
            }
            allDesignaciones.add(temp);
        }
        
        return allDesignaciones;

    }
    
    public ArrayList<EstudioCaso> getListaEstudiosPorExpediente(long idExp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From EstudioCaso E where E.expedienteFamilia = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idExp);
        List estudios = query.list();
        ArrayList<EstudioCaso> allEstudios = new ArrayList();
        
        for (Iterator iter = estudios.iterator(); iter.hasNext();) {
            EstudioCaso temp = (EstudioCaso) iter.next();
            Hibernate.initialize(temp.getNna().getExpedienteNnas());
            allEstudios.add(temp);
        }
        
        return allEstudios;

    }
    
    public Evaluacion getEvaluacionJuntoPersonal(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Evaluacion tempEval = new Evaluacion();
        
        String hqlA = "FROM Evaluacion E WHERE E.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempEval = (Evaluacion) queryResultA;
        Hibernate.initialize(tempEval.getPersonal());
        return tempEval;
        
    }
    
    public ArrayList<Taller> listaTalleresHabilitados() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Taller T order by T.idtaller ASC";
        Query query = session.createQuery(hql);
        List talleres = query.list();
        ArrayList<Taller> allTalleres = new ArrayList();
        for (Iterator iter = talleres.iterator(); iter.hasNext();) {
            Taller temp = (Taller) iter.next();
            Hibernate.initialize(temp.getGrupos());
                Set<Grupo> grupos = new HashSet<Grupo>(0);
            for (Grupo grp : temp.getGrupos()) {
                    Hibernate.initialize(grp.getTurno2s());
                        Set<Turno2> turno2s = new HashSet<Turno2>(0);
                        for (Turno2 trn : grp.getTurno2s()) {
                                Hibernate.initialize(trn.getReunions());
                                turno2s.add(trn);
                         }
                       grp.setTurno2s(turno2s);
                       grupos.add(grp);
            }
            temp.setGrupos(grupos);
            allTalleres.add(temp);
        }
        return allTalleres;

    }
    
    public ArrayList<Sesion> getListaSesionesHabilitadas() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Sesion S order by S.fecha ASC";
        Query query = session.createQuery(hql);
        List sesiones = query.list();
        ArrayList<Sesion> allSesiones = new ArrayList();
        
        for (Iterator iter = sesiones.iterator(); iter.hasNext();) {
            Sesion temp = (Sesion) iter.next();
            Hibernate.initialize(temp.getTurnos());
            allSesiones.add(temp);
        }
        
        return allSesiones;

    }
    
    public void crearUpdateExpFam (ExpedienteFamilia temp){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(temp);
    
    }
    
    public void crearAdopcionAdoptantesExtranjero(Nna tempNna, Designacion tempDesig){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(tempNna);
        session.saveOrUpdate(tempDesig);
    }
    
    public ArrayList<Designacion> getListaDesignacionesAdoptantesExtranjero(long idExp) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Designacion D where D.expedienteFamilia = :idExp and D.aceptacionConsejo = :aceptacion and D.tipoPropuesta = :tipo order by D.fechaConsejo DESC";
        Query query = session.createQuery(hql);
        query.setLong("idExp", idExp);
        query.setShort("aceptacion", Short.parseShort("4"));
        query.setString("tipo", "extranjero");
        List designaciones = query.list();
        ArrayList<Designacion> allDesignaciones = new ArrayList();
        
        for (Iterator iter = designaciones.iterator(); iter.hasNext();) {
            Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getNna());
            allDesignaciones.add(temp);
        }
        
        return allDesignaciones;

    }
    
    public Nna getNnaAdoptantesExtranjero(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Nna tempNna = new Nna();
        
        String hqlA = "FROM Nna N WHERE N.idnna = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempNna = (Nna) queryResultA;
        return tempNna;
        
    }
    
    public Designacion getDesignacionAdoptantesExtranjero(long id){
    
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Designacion tempDesig = new Designacion();
        
        String hqlA = "FROM Designacion D WHERE D.iddesignacion = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempDesig = (Designacion) queryResultA;
        Hibernate.initialize(tempDesig.getExpedienteFamilia());
        return tempDesig;
        
    }
    
}
