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
    ResultSet tempexp;

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
    //PROBADO
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
                    tempnna.setExpedienteNnas(listexp);
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
                statement.close();
            }
        };
        session.doWork(work);

        return tempnna;
    }
    
    

//    public Nna getNnaPostAdopcion(long id) {
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
//        Hibernate.initialize(tempNna.getJuzgado());
//        return tempNna;
//    }
    //PROBADO
    public Nna getNnaPostAdopcion(long id) {
        Session session = sessionFactory.getCurrentSession();

        final Nna tempnna = new Nna();
        final Long idnna = id;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;
                Car car;
                Juzgado juz;

                String hql = "{call HN_GET_NNA_POST(?,?)}";
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
                    tempnna.setExpedienteNnas(listexp);
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
                    juz = new Juzgado();
                    juz.setIdjuzgado(temp.getLong(74));
                    juz.setNombre(temp.getString(75));
                    juz.setDenominacion(temp.getString(76));
                    juz.setEspecialidad(temp.getString(77));
                    juz.setDireccion(temp.getString(78));
                    juz.setDepartamento(temp.getString(79));
                    juz.setCorteSuperior(temp.getString(80));
                    juz.setDistritoJudicial(temp.getString(81));
                    juz.setNombreJuez(temp.getString(82));
                    juz.setTelefono(temp.getString(83));
                    juz.setCorreo(temp.getString(84));
                    juz.setObservaciones(temp.getString(85));
                    tempnna.setJuzgado(juz);
                }
                statement.close();
            }
        };
        session.doWork(work);
        return tempnna;
    }

    public void crearNna(Nna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    //EN PROCESO
//    public void crearNna(Nna temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        final Nna nna = temp;
//        
//        Work work = new Work() {
//            @Override
//            public void execute(Connection connection) throws SQLException {
//                
//                String hql = "{call HN_SAVE_NNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//                CallableStatement statement = connection.prepareCall(hql);
//                statement.setLong(1, nna.getJuzgado().getIdjuzgado());
//                statement.setLong(2, nna.getCar().getIdcar());
//                statement.setString(3, nna.getNombre());
//                statement.setString(4, nna.getApellidoP());
//                statement.setString(5, nna.getApellidoM());
//                statement.setString(6, nna.getSexo());
//                java.sql.Date fechanac = new java.sql.Date(nna.getFechaNacimiento().getTime());
//                statement.setDate(7, fechanac);
//                statement.setShort(8, nna.getEdadAnhos());
//                statement.setShort(9, nna.getEdadMeses());
//                statement.setShort(10, nna.getActaNacimiento());
//                statement.setString(11, nna.getCondicionSalud());
//                statement.setString(12, nna.getDepartamentoNacimiento());
//                statement.setString(13, nna.getProvinciaNacimiento());
//                statement.setString(14, nna.getDistritoNacimiento());
//                statement.setString(15, nna.getPaisNacimiento());
//                statement.setString(16, nna.getLugarNac());
//                java.sql.Date fechaAband = new java.sql.Date(nna.getFechaResolAbandono().getTime());
//                statement.setDate(17, fechaAband);
//                java.sql.Date fechaCons = new java.sql.Date(nna.getFechaResolConsentida().getTime());
//                statement.setDate(18, fechaCons);
//                statement.setString(19, nna.getClasificacion());
//                statement.setShort(20, nna.getIncesto());
//                statement.setShort(21, nna.getMental());
//                statement.setShort(22, nna.getEpilepsia());
//                statement.setShort(23, nna.getAbuso());
//                statement.setShort(24, nna.getSifilis());
//                statement.setShort(25, nna.getSeguiMedico());
//                statement.setShort(26, nna.getOperacion());
//                statement.setShort(27, nna.getHiperactivo());
//                statement.setShort(28, nna.getEspecial());
//                statement.setShort(29, nna.getEnfermo());
//                statement.setShort(30, nna.getMayor());
//                statement.setShort(31, nna.getAdolescente());
//                statement.setShort(32, nna.getHermano());
//                statement.setShort(33, nna.getNn());
//                statement.setString(34, nna.getObservaciones());
//                statement.setString(35, nna.getNResolAband());
//                statement.setString(36, nna.getNResolCons());
//                
//                statement.execute();
//                statement.close();
//            }
//        };
//        
//        session.doWork(work);
//
//    }
    public void updateNna(Nna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

//    public ArrayList<Nna> ListaNna(String clasificacion) {
//
//        Session session = sessionFactory.getCurrentSession();
//
//        session.beginTransaction();
//
//        String hql = "from Nna N where N.clasificacion = :clasificacion ORDER BY N.idnna DESC";
//        Query query = session.createQuery(hql);
//        query.setString("clasificacion", clasificacion);
//        List listaNna = query.list();
//        ArrayList<Nna> allNna = new ArrayList();
//        for (Iterator iter = listaNna.iterator(); iter.hasNext();) {
//            Nna temp = (Nna) iter.next();
//            Hibernate.initialize(temp.getDesignacions());
//            Hibernate.initialize(temp.getExpedienteNnas());
//            if (clasificacion.equals("prioritario")) {
//                Hibernate.initialize(temp.getEstudioCasos());
//            }
//            allNna.add(temp);
//        }
//        return allNna;
//    }
    
    //PROBADO Y TERMINADO
    public ArrayList<Nna> ListaNna(String clasificacion) {
        Session session = sessionFactory.getCurrentSession();
        final String clasif = clasificacion;
        final ArrayList<Nna> allNna = new ArrayList();
        final ArrayList<Nna> allNnaAux = new ArrayList();
        final ArrayList<Nna> allNnaFinal = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;
                Nna tempnna;
                Designacion desig;
                Set<Designacion> listDesig = new HashSet<Designacion>();
                EstudioCaso est;
                Set<EstudioCaso> listEst = new HashSet<EstudioCaso>();

                String hql = "{call HN_GET_NNA_CLAS(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, clasif);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);

                while (temp.next()) {
                    tempnna = new Nna();
                    tempnna.setIdnna(temp.getLong(1));
                    tempnna.setNombre(temp.getString(2));
                    tempnna.setApellidoP(temp.getString(3));
                    tempnna.setApellidoM(temp.getString(4));
                    tempnna.setSexo(temp.getString(5));
                    tempnna.setFechaNacimiento(temp.getDate(6));
                    tempnna.setEdadAnhos(temp.getShort(7));
                    tempnna.setEdadMeses(temp.getShort(8));
                    tempnna.setActaNacimiento(temp.getShort(9));
                    tempnna.setCondicionSalud(temp.getString(10));
                    tempnna.setDepartamentoNacimiento(temp.getString(11));
                    tempnna.setProvinciaNacimiento(temp.getString(12));
                    tempnna.setDistritoNacimiento(temp.getString(13));
                    tempnna.setPaisNacimiento(temp.getString(14));
                    tempnna.setLugarNac(temp.getString(15));
                    tempnna.setFechaResolAbandono(temp.getDate(16));
                    tempnna.setFechaResolConsentida(temp.getDate(17));
                    tempnna.setClasificacion(temp.getString(18));
                    tempnna.setIncesto(temp.getShort(19));
                    tempnna.setMental(temp.getShort(20));
                    tempnna.setEpilepsia(temp.getShort(21));
                    tempnna.setAbuso(temp.getShort(22));
                    tempnna.setSifilis(temp.getShort(23));
                    tempnna.setSeguiMedico(temp.getShort(24));
                    tempnna.setOperacion(temp.getShort(25));
                    tempnna.setHiperactivo(temp.getShort(26));
                    tempnna.setEspecial(temp.getShort(27));
                    tempnna.setEnfermo(temp.getShort(28));
                    tempnna.setMayor(temp.getShort(29));
                    tempnna.setAdolescente(temp.getShort(30));
                    tempnna.setHermano(temp.getShort(31));
                    tempnna.setNn(temp.getShort(32));
                    tempnna.setObservaciones(temp.getString(33));
                    tempnna.setNResolAband(temp.getString(34));
                    tempnna.setNResolCons(temp.getString(35));
                    try {
                        expnna = getExpNna(temp.getLong(1));
                        if (expnna.getIdexpedienteNna() != 0) {
                            Set<ExpedienteNna> listexp = new HashSet<ExpedienteNna>();
                            listexp.add(expnna);
                            tempnna.setExpedienteNnas(listexp);
                        }
                    } catch (Exception e) {

                    }
                    allNnaAux.add(tempnna);
                }
                statement.close();
                temp.close();

                //AQUI DESIGNACIONES Y ESTUDIO DE CASOS EN CASO SEA PRIORITARIO
                for (Nna auxnna : allNnaAux) {
                    String hql2 = "{call HN_GET_DESIGNACIONES(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, auxnna.getIdnna());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);
                    while (rs2.next()) {
                        desig = new Designacion();
                        desig.setIddesignacion(rs2.getLong(1));
                        desig.setNDesignacion(rs2.getLong(5));
                        desig.setPrioridad(rs2.getLong(6));
                        desig.setFechaPropuesta(rs2.getDate(7));
                        desig.setFechaConsejo(rs2.getDate(8));
                        desig.setAceptacionConsejo(rs2.getShort(9));
                        desig.setTipoPropuesta(rs2.getString(10));
                        desig.setObs(rs2.getString(11));
                        listDesig.add(desig);
                    }
                    auxnna.setDesignacions(listDesig);
                    allNna.add(auxnna);
                    statement2.close();
                    rs2.close();
                }

                for (Nna auxnna : allNna) {
                    if (auxnna.getClasificacion().equals("prioritario")) {
                        String hql3 = "{call HN_GET_ESTUDIOS_CASO(?,?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, auxnna.getIdnna());
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();

                        ResultSet rs3 = (ResultSet) statement3.getObject(2);
                        while (rs3.next()) {
                            est = new EstudioCaso();
                            est.setIdestudioCaso(rs3.getLong(1));
                            est.setOrden(rs3.getString(4));
                            est.setFechaEstudio(rs3.getDate(5));
                            est.setFechaSolAdop(rs3.getDate(6));
                            est.setResultado(rs3.getString(7));
                            est.setPrioridad(rs3.getLong(8));
                            est.setNSolicitud(rs3.getBigDecimal(9));
                            listEst.add(est);
                        }
                        auxnna.setEstudioCasos(listEst);
                        statement3.close();
                        rs3.close();
                    }
                    allNnaFinal.add(auxnna);
                }
            }
        };
        session.doWork(work);

        return allNnaFinal;
    } //    public ExpedienteNna getExpNna(long idNna) {
    //        Session session = sessionFactory.getCurrentSession();
    //        session.beginTransaction();
    //        ExpedienteNna tempExpNna = new ExpedienteNna();
    //
    //        session.beginTransaction();
    //        String hql = "FROM ExpedienteNna E WHERE E.nna = :id";
    //        Query query = session.createQuery(hql);
    //        query.setLong("id", idNna);
    //        Object queryResult = query.uniqueResult();
    //
    //        tempExpNna = (ExpedienteNna) queryResult;
    //        Hibernate.initialize(tempExpNna.getNna());
    //        return tempExpNna;
    //    }
    
    
    //PROBADO Y TERMINADO
    public ExpedienteNna getExpNna(long idNna) {
        Session session = sessionFactory.getCurrentSession();

        final ExpedienteNna expnna = new ExpedienteNna();
        final Long idnna = idNna;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Nna tempnna;

                String hql = "{call HN_GET_EXPEDIENTE_NNA_UNO(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idnna);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                tempexp = (ResultSet) statement.getObject(2);

                while (tempexp.next()) {
                    expnna.setIdexpedienteNna(tempexp.getShort(1));
                    expnna.setNumero(tempexp.getString(2));
                    expnna.setFechaIngreso(tempexp.getDate(3));
                    expnna.setHt(tempexp.getString(4));
                    expnna.setNExpTutelar(tempexp.getString(5));
                    expnna.setProcTutelar(tempexp.getString(6));
                    expnna.setFichaIntegral(tempexp.getShort(7));
                    expnna.setComentarios(tempexp.getString(8));
                    expnna.setRespLegalNombre(tempexp.getString(9));
                    expnna.setRespLegalP(tempexp.getString(10));
                    expnna.setRespLegalM(tempexp.getString(11));
                    expnna.setRespPsicosocialNombre(tempexp.getString(12));
                    expnna.setRespPiscosocialP(tempexp.getString(13));
                    expnna.setRespPsicosocialM(tempexp.getString(14));
                    expnna.setEstado(tempexp.getString(15));
                    expnna.setFechaEstado(tempexp.getDate(16));
                    expnna.setAdoptable(tempexp.getShort(17));
                    expnna.setFechaResolCons(tempexp.getDate(18));
                    expnna.setNacional(tempexp.getShort(19));
                    expnna.setDiagnostico(tempexp.getString(20));
                    expnna.setCodigoReferencia(tempexp.getString(21));
                    expnna.setNActual(tempexp.getString(22));
                    expnna.setApellidopActual(tempexp.getString(23));
                    expnna.setApellidomActual(tempexp.getString(24));
                    expnna.setObservaciones(tempexp.getString(25));
                    expnna.setFechaInvTutelar(tempexp.getDate(26));
                    tempnna = new Nna();
                    tempnna.setIdnna(tempexp.getLong(27));
                    tempnna.setNombre(tempexp.getString(28));
                    tempnna.setApellidoP(tempexp.getString(29));
                    tempnna.setApellidoM(tempexp.getString(30));
                    tempnna.setSexo(tempexp.getString(31));
                    tempnna.setFechaNacimiento(tempexp.getDate(32));
                    tempnna.setEdadAnhos(tempexp.getShort(33));
                    tempnna.setEdadMeses(tempexp.getShort(34));
                    tempnna.setActaNacimiento(tempexp.getShort(35));
                    tempnna.setCondicionSalud(tempexp.getString(36));
                    tempnna.setDepartamentoNacimiento(tempexp.getString(37));
                    tempnna.setProvinciaNacimiento(tempexp.getString(38));
                    tempnna.setDistritoNacimiento(tempexp.getString(39));
                    tempnna.setPaisNacimiento(tempexp.getString(40));
                    tempnna.setLugarNac(tempexp.getString(41));
                    tempnna.setFechaResolAbandono(tempexp.getDate(42));
                    tempnna.setFechaResolConsentida(tempexp.getDate(43));
                    tempnna.setClasificacion(tempexp.getString(44));
                    tempnna.setIncesto(tempexp.getShort(45));
                    tempnna.setMental(tempexp.getShort(46));
                    tempnna.setEpilepsia(tempexp.getShort(47));
                    tempnna.setAbuso(tempexp.getShort(48));
                    tempnna.setSifilis(tempexp.getShort(49));
                    tempnna.setSeguiMedico(tempexp.getShort(50));
                    tempnna.setOperacion(tempexp.getShort(51));
                    tempnna.setHiperactivo(tempexp.getShort(52));
                    tempnna.setEspecial(tempexp.getShort(53));
                    tempnna.setEnfermo(tempexp.getShort(54));
                    tempnna.setMayor(tempexp.getShort(55));
                    tempnna.setAdolescente(tempexp.getShort(56));
                    tempnna.setHermano(tempexp.getShort(57));
                    tempnna.setNn(tempexp.getShort(58));
                    tempnna.setObservaciones(tempexp.getString(59));
                    tempnna.setNResolAband(tempexp.getString(60));
                    tempnna.setNResolCons(tempexp.getString(61));
                    expnna.setNna(tempnna);
                }
                statement.close();
            }
        };
        session.doWork(work);
        return expnna;
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
    //PROBADO Y TERMINADO
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
                statement.close();
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
