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
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author john
 */
@Service("HiberNna")
@Transactional
public class HiberNna {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    ResultSet temp;

//    public Nna getNna(long id) {
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        Nna tempNna = new Nna();
//
//        session.beginTransaction();
//        String hqlA = "FROM Nna N WHERE N.id = :id";
//        Query queryA = session.createQuery(hqlA);
//        queryA.setLong("id", id);
//        Object queryResultA = queryA.uniqueResult();
//
//        tempNna = (Nna) queryResultA;
//        Hibernate.initialize(tempNna.getExpedienteNnas());
//        Hibernate.initialize(tempNna.getCar());
//        return tempNna;
//    }
    public Nna getNna(long id) {
        Session session = sessionFactory.getCurrentSession();
        final Nna tempnna = new Nna();
        final Long idnna = id;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;
                Car car;

                String hql = "{call HN_GET_NNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idnna);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);

                while (temp.next()) {
                    tempnna.setIdnna(idnna);
                    tempnna.setNombre(temp.getString(1));
                    tempnna.setApellidoP(temp.getString(2));
                    tempnna.setApellidoM(temp.getString(3));
                    tempnna.setSexo(temp.getString(4));
                    tempnna.setFechaNacimiento(temp.getDate(5));
                    tempnna.setEdadAnhos(temp.getShort(6));
                    tempnna.setEdadMeses(temp.getShort(7));
                    tempnna.setActaNacimiento(temp.getShort(8));
                    tempnna.setCondicionSalud(temp.getString(9));
                    tempnna.setDepartamentoNacimiento(temp.getString(10));
                    tempnna.setProvinciaNacimiento(temp.getString(11));
                    tempnna.setDistritoNacimiento(temp.getString(12));
                    tempnna.setPaisNacimiento(temp.getString(13));
                    tempnna.setLugarNac(temp.getString(14));
                    tempnna.setFechaResolAbandono(temp.getDate(15));
                    tempnna.setFechaResolConsentida(temp.getDate(16));
                    tempnna.setClasificacion(temp.getString(17));
                    tempnna.setIncesto(temp.getShort(18));
                    tempnna.setMental(temp.getShort(19));
                    tempnna.setEpilepsia(temp.getShort(20));
                    tempnna.setAbuso(temp.getShort(21));
                    tempnna.setSifilis(temp.getShort(22));
                    tempnna.setSeguiMedico(temp.getShort(23));
                    tempnna.setOperacion(temp.getShort(24));
                    tempnna.setHiperactivo(temp.getShort(25));
                    tempnna.setEspecial(temp.getShort(26));
                    tempnna.setEnfermo(temp.getShort(27));
                    tempnna.setMayor(temp.getShort(28));
                    tempnna.setAdolescente(temp.getShort(29));
                    tempnna.setHermano(temp.getShort(30));
                    tempnna.setNn(temp.getShort(31));
                    tempnna.setObservaciones(temp.getString(32));
                    tempnna.setNResolAband(temp.getString(33));
                    tempnna.setNResolCons(temp.getString(34));
                    expnna = new ExpedienteNna();
                    expnna.setIdexpedienteNna(temp.getShort(35));
                    expnna.setNumero(temp.getString(36));
                    expnna.setFechaIngreso(temp.getDate(37));
                    expnna.setHt(temp.getString(38));
                    expnna.setNExpTutelar(temp.getString(39));
                    expnna.setProcTutelar(temp.getString(40));
                    expnna.setFichaIntegral(temp.getShort(41));
                    expnna.setComentarios(temp.getString(42));
                    expnna.setRespLegalNombre(temp.getString(43));
                    expnna.setRespLegalP(temp.getString(44));
                    expnna.setRespLegalM(temp.getString(45));
                    expnna.setRespPsicosocialNombre(temp.getString(46));
                    expnna.setRespPiscosocialP(temp.getString(47));
                    expnna.setRespPsicosocialM(temp.getString(48));
                    expnna.setEstado(temp.getString(49));
                    expnna.setFechaEstado(temp.getDate(50));
                    expnna.setAdoptable(temp.getShort(51));
                    expnna.setFechaResolCons(temp.getDate(52));
                    expnna.setNacional(temp.getShort(53));
                    expnna.setDiagnostico(temp.getString(54));
                    expnna.setCodigoReferencia(temp.getString(55));
                    expnna.setNActual(temp.getString(56));
                    expnna.setApellidopActual(temp.getString(57));
                    expnna.setApellidomActual(temp.getString(58));
                    expnna.setObservaciones(temp.getString(59));
                    expnna.setFechaInvTutelar(temp.getDate(60));
                    Set<ExpedienteNna> listexp = new HashSet<ExpedienteNna>();
                    listexp.add(expnna);
                    car = new Car();
                    car.setIdcar(temp.getShort(61));
                    car.setNombre(temp.getString(62));
                    car.setDireccion(temp.getString(63));
                    car.setDepartamento(temp.getString(64));
                    car.setProvincia(temp.getString(65));
                    car.setDistrito(temp.getString(66));
                    car.setDirector(temp.getString(67));
                    car.setContacto(temp.getString(68));
                    car.setCorreo(temp.getString(69));
                    car.setFax(temp.getString(70));
                    car.setCelular(temp.getString(71));
                    car.setTelefono(temp.getString(72));
                    car.setObservaciones(temp.getString(73));
                    tempnna.setCar(car);
                }
            }
        };
        session.doWork(work);
        
        return tempnna;
    }

    public Nna getNnaPostAdopcion(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Nna tempNna = new Nna();

        session.beginTransaction();
        String hqlA = "FROM Nna N WHERE N.id = :id";
        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        tempNna = (Nna) queryResultA;
        Hibernate.initialize(tempNna.getExpedienteNnas());
        Hibernate.initialize(tempNna.getCar());
        Hibernate.initialize(tempNna.getJuzgado());
        return tempNna;
    }

    public void crearNna(Nna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateNna(Nna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public ArrayList<Nna> ListaNna(String clasificacion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "from Nna N where N.clasificacion = :clasificacion ORDER BY N.idnna DESC";
        Query query = session.createQuery(hql);
        query.setString("clasificacion", clasificacion);
        List listaNna = query.list();
        ArrayList<Nna> allNna = new ArrayList();
        for (Iterator iter = listaNna.iterator(); iter.hasNext();) {
            Nna temp = (Nna) iter.next();
            Hibernate.initialize(temp.getDesignacions());
            Hibernate.initialize(temp.getExpedienteNnas());
            if (clasificacion.equals("prioritario")) {
                Hibernate.initialize(temp.getEstudioCasos());
            }
            allNna.add(temp);
        }
        return allNna;
    }

    public ExpedienteNna getExpNna(long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ExpedienteNna tempExpNna = new ExpedienteNna();

        session.beginTransaction();
        String hql = "FROM ExpedienteNna E WHERE E.nna = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", idNna);
        Object queryResult = query.uniqueResult();

        tempExpNna = (ExpedienteNna) queryResult;
        Hibernate.initialize(tempExpNna.getNna());
        return tempExpNna;
    }

//    public ArrayList<ExpedienteNna> listaExpNna() {
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "FROM ExpedienteNna";
//        Query query = session.createQuery(hql);
//        List listaExpNna = query.list();
//        ArrayList<ExpedienteNna> allExpNna = new ArrayList();
//        for (Iterator iter = listaExpNna.iterator(); iter.hasNext();) {
//            ExpedienteNna temp = (ExpedienteNna) iter.next();
//
//            allExpNna.add(temp);
//        }
//        return allExpNna;
//    }
    public ArrayList<ExpedienteNna> listaExpNna() {
        Session session = sessionFactory.getCurrentSession();
        final ArrayList<ExpedienteNna> allExpNna = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HN_GET_EXPEDIENTE_NNA(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(1);

                while (temp.next()) {
                    expnna = new ExpedienteNna();
                    expnna.setIdexpedienteNna(temp.getShort(1));
                    expnna.setNumero(temp.getString(4));
                    expnna.setFechaIngreso(temp.getDate(5));
                    expnna.setHt(temp.getString(6));
                    expnna.setNExpTutelar(temp.getString(7));
                    expnna.setProcTutelar(temp.getString(8));
                    expnna.setFichaIntegral(temp.getShort(9));
                    expnna.setComentarios(temp.getString(10));
                    expnna.setRespLegalNombre(temp.getString(11));
                    expnna.setRespLegalP(temp.getString(12));
                    expnna.setRespLegalM(temp.getString(13));
                    expnna.setRespPsicosocialNombre(temp.getString(14));
                    expnna.setRespPiscosocialP(temp.getString(15));
                    expnna.setRespPsicosocialM(temp.getString(16));
                    expnna.setEstado(temp.getString(17));
                    expnna.setFechaEstado(temp.getDate(18));
                    expnna.setAdoptable(temp.getShort(19));
                    expnna.setFechaResolCons(temp.getDate(20));
                    expnna.setNacional(temp.getShort(21));
                    expnna.setDiagnostico(temp.getString(22));
                    expnna.setCodigoReferencia(temp.getString(23));
                    expnna.setNActual(temp.getString(24));
                    expnna.setApellidopActual(temp.getString(25));
                    expnna.setApellidomActual(temp.getString(26));
                    expnna.setObservaciones(temp.getString(27));
                    expnna.setFechaInvTutelar(temp.getDate(28));
                    allExpNna.add(expnna);
                }
            }
        };
        session.doWork(work);
        return allExpNna;
    }

    public void crearExpNna(ExpedienteNna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateExpNna(ExpedienteNna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }
}
