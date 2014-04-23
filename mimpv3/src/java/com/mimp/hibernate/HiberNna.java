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
    String numero_last = "";

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
                Juzgado juz;
                Car car;

                String hql = "{call HN_GET_NNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idnna);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);

                while (temp.next()) {
                    tempnna.setIdnna(idnna);
                    juz = new Juzgado();
                    juz.setIdjuzgado(temp.getLong("IDJUZGADO"));
                    tempnna.setJuzgado(juz);
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
                    tempnna.setFechaResolConsentida(temp.getDate("FECHA_RESOL_CONSENTIDA"));
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
                    tempnna.setNResolCons(temp.getString("N_RESOL_CONS"));
                    try {
                        expnna = getExpNna(tempnna.getIdnna());
                        if (expnna.getIdexpedienteNna() != 0) {
                            Set<ExpedienteNna> listexp = new HashSet<ExpedienteNna>();
                            listexp.add(expnna);
                            tempnna.setExpedienteNnas(listexp);
                        }
                    } catch (Exception e) {

                    }
                    car = new Car();
                    car.setIdcar(temp.getLong("IDCAR"));
                    car.setNombre(temp.getString(37));
                    car.setDireccion(temp.getString(38));
                    car.setDepartamento(temp.getString(39));
                    car.setProvincia(temp.getString(40));
                    car.setDistrito(temp.getString(41));
                    car.setDirector(temp.getString(42));
                    car.setContacto(temp.getString(43));
                    car.setCorreo(temp.getString(44));
                    car.setFax(temp.getString(45));
                    car.setCelular(temp.getString(46));
                    car.setTelefono(temp.getString(47));
                    car.setObservaciones(temp.getString(48));
                    tempnna.setCar(car);
                }
                temp.close();
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
    //PARA PROBARSE
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
                    try {
                        expnna = getExpNna(tempnna.getIdnna());
                        if (expnna.getIdexpedienteNna() != 0) {
                            Set<ExpedienteNna> listexp = new HashSet<ExpedienteNna>();
                            listexp.add(expnna);
                            tempnna.setExpedienteNnas(listexp);
                        }
                    } catch (Exception e) {

                    }
                    car = new Car();
                    car.setIdcar(temp.getLong(35));
                    car.setNombre(temp.getString(36));
                    car.setDireccion(temp.getString(37));
                    car.setDepartamento(temp.getString(38));
                    car.setProvincia(temp.getString(39));
                    car.setDistrito(temp.getString(40));
                    car.setDirector(temp.getString(41));
                    car.setContacto(temp.getString(42));
                    car.setCorreo(temp.getString(43));
                    car.setFax(temp.getString(44));
                    car.setCelular(temp.getString(45));
                    car.setTelefono(temp.getString(46));
                    car.setObservaciones(temp.getString(47));
                    tempnna.setCar(car);
                    juz = new Juzgado();
                    juz.setIdjuzgado(temp.getLong("IDJUZGADO"));
                    juz.setNombre(temp.getString(49));
                    juz.setDenominacion(temp.getString(50));
                    juz.setEspecialidad(temp.getString(51));
                    juz.setDireccion(temp.getString(52));
                    juz.setDepartamento(temp.getString(53));
                    juz.setCorteSuperior(temp.getString(54));
                    juz.setDistritoJudicial(temp.getString(55));
                    juz.setNombreJuez(temp.getString(56));
                    juz.setTelefono(temp.getString(57));
                    juz.setCorreo(temp.getString(58));
                    juz.setObservaciones(temp.getString(59));
                    tempnna.setJuzgado(juz);
                }
                temp.close();
                statement.close();
            }
        };
        session.doWork(work);
        return tempnna;
    }

//    public void crearNna(Nna temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    //PROBADO
    public void crearNna(Nna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final Nna nna = temp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_SAVE_NNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, nna.getJuzgado().getIdjuzgado());
                statement.setLong(2, nna.getCar().getIdcar());
                statement.setString(3, nna.getNombre());
                statement.setString(4, nna.getApellidoP());
                statement.setString(5, nna.getApellidoM());
                statement.setString(6, nna.getSexo());
                statement.setDate(7, (java.sql.Date) nna.getFechaNacimiento());
                statement.setShort(8, nna.getEdadAnhos());
                statement.setShort(9, nna.getEdadMeses());
                statement.setShort(10, nna.getActaNacimiento());
                statement.setString(11, nna.getCondicionSalud());
                statement.setString(12, nna.getDepartamentoNacimiento());
                statement.setString(13, nna.getProvinciaNacimiento());
                statement.setString(14, nna.getDistritoNacimiento());
                statement.setString(15, nna.getPaisNacimiento());
                statement.setString(16, nna.getLugarNac());
                statement.setDate(17, (java.sql.Date) nna.getFechaResolAbandono());
                statement.setDate(18, (java.sql.Date) nna.getFechaResolConsentida());
                statement.setString(19, nna.getClasificacion());
                statement.setShort(20, nna.getIncesto());
                statement.setShort(21, nna.getMental());
                statement.setShort(22, nna.getEpilepsia());
                statement.setShort(23, nna.getAbuso());
                statement.setShort(24, nna.getSifilis());
                statement.setShort(25, nna.getSeguiMedico());
                statement.setShort(26, nna.getOperacion());
                statement.setShort(27, nna.getHiperactivo());
                statement.setShort(28, nna.getEspecial());
                statement.setShort(29, nna.getEnfermo());
                statement.setShort(30, nna.getMayor());
                statement.setShort(31, nna.getAdolescente());
                statement.setShort(32, nna.getHermano());
                statement.setShort(33, nna.getNn());
                statement.setString(34, nna.getObservaciones());
                statement.setString(35, nna.getNResolAband());
                statement.setString(36, nna.getNResolCons());

                statement.execute();
                statement.close();
            }
        };
        session.doWork(work);
    }

//    public void updateNna(Nna temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    //PROBADO Y TERMINASDO
    public void updateNna(Nna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final Nna nna = temp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_UPDATE_NNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, nna.getJuzgado().getIdjuzgado());
                statement.setLong(2, nna.getCar().getIdcar());
                statement.setString(3, nna.getNombre());
                statement.setString(4, nna.getApellidoP());
                statement.setString(5, nna.getApellidoM());
                statement.setString(6, nna.getSexo());
                statement.setDate(7, (java.sql.Date) nna.getFechaNacimiento());
                statement.setShort(8, nna.getEdadAnhos());
                statement.setShort(9, nna.getEdadMeses());
                statement.setShort(10, nna.getActaNacimiento());
                statement.setString(11, nna.getCondicionSalud());
                statement.setString(12, nna.getDepartamentoNacimiento());
                statement.setString(13, nna.getProvinciaNacimiento());
                statement.setString(14, nna.getDistritoNacimiento());
                statement.setString(15, nna.getPaisNacimiento());
                statement.setString(16, nna.getLugarNac());
                statement.setDate(17, (java.sql.Date) nna.getFechaResolAbandono());
                statement.setDate(18, (java.sql.Date) nna.getFechaResolConsentida());
                statement.setString(19, nna.getClasificacion());
                statement.setShort(20, nna.getIncesto());
                statement.setShort(21, nna.getMental());
                statement.setShort(22, nna.getEpilepsia());
                statement.setShort(23, nna.getAbuso());
                statement.setShort(24, nna.getSifilis());
                statement.setShort(25, nna.getSeguiMedico());
                statement.setShort(26, nna.getOperacion());
                statement.setShort(27, nna.getHiperactivo());
                statement.setShort(28, nna.getEspecial());
                statement.setShort(29, nna.getEnfermo());
                statement.setShort(30, nna.getMayor());
                statement.setShort(31, nna.getAdolescente());
                statement.setShort(32, nna.getHermano());
                statement.setShort(33, nna.getNn());
                statement.setString(34, nna.getObservaciones());
                statement.setString(35, nna.getNResolAband());
                statement.setString(36, nna.getNResolCons());
                statement.setLong(37, nna.getIdnna());

                statement.execute();
                statement.close();
            }
        };
        session.doWork(work);
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

                EstudioCaso est;

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
                    Set<Designacion> listDesig = new HashSet<Designacion>();
                    String hql2 = "{call HN_GET_DESIGNACIONES(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, auxnna.getIdnna());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);
                    while (rs2.next()) {
                        desig = new Designacion();
                        desig.setIddesignacion(rs2.getLong(1));
                        desig.setNDesignacion(rs2.getString(5));
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
                        Set<EstudioCaso> listEst = new HashSet<EstudioCaso>();
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
                            est.setNSolicitud(rs3.getLong(9));
                            listEst.add(est);
                        }
                        auxnna.setEstudioCasos(listEst);
                        statement3.close();
                        rs3.close();
                    }
                    allNnaFinal.add(auxnna);
                }

                //METODO BUBBLESORT PARA ORDENAR POR CODIGO
                if (clasif.equals("prioritario")) {
                    Nna auxnna2;
                    int n = allNnaFinal.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = i; j < n - 1; j++) {
                            if (allNnaFinal.get(i).getExpedienteNnas().isEmpty()) {
                                if (!allNnaFinal.get(j + 1).getExpedienteNnas().isEmpty()) {
                                    auxnna2 = allNnaFinal.get(i);
                                    allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                    allNnaFinal.set(j + 1, auxnna2);
                                } else {
                                    String apellidoPrev = "";
                                    String apellidoNext = "";
                                    try{
                                        apellidoPrev = allNnaFinal.get(i).getApellidoP();
                                        apellidoNext = allNnaFinal.get(j + 1).getApellidoP();
                                    } catch (Exception ex){
                                        
                                    }
                                    if (apellidoPrev.compareToIgnoreCase(apellidoNext) > 0) {
                                        auxnna2 = allNnaFinal.get(i);
                                        allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                        allNnaFinal.set(j + 1, auxnna2);
                                    }
                                }
                            } else {
                                Set<ExpedienteNna> listExp1 = allNnaFinal.get(i).getExpedienteNnas();
                                Set<ExpedienteNna> listExp2 = allNnaFinal.get(j + 1).getExpedienteNnas();
                                if (!listExp2.isEmpty()) {
                                    for (ExpedienteNna exp1 : listExp1) {
                                        for (ExpedienteNna exp2 : listExp2) {
                                            String codant = exp1.getCodigoReferencia();
                                            String codpost = exp2.getCodigoReferencia();
                                            if (codant == null){
                                                codant = "";
                                            }
                                            if (codpost == null){
                                                codpost = "";
                                            }
                                            if (codant.compareToIgnoreCase(codpost) > 0) {
                                                auxnna2 = allNnaFinal.get(i);
                                                allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                                allNnaFinal.set(j + 1, auxnna2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        session.doWork(work);

        return allNnaFinal;
    }

    public ArrayList<Nna> ListaNnaPrioritarios(String clasificacion) {
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

                EstudioCaso est;

                String hql = "{call HN_GET_NNA_PRIO(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(1);

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
                    Set<Designacion> listDesig = new HashSet<Designacion>();
                    String hql2 = "{call HN_GET_DESIGNACIONES(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, auxnna.getIdnna());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);
                    while (rs2.next()) {
                        desig = new Designacion();
                        desig.setIddesignacion(rs2.getLong(1));
                        desig.setNDesignacion(rs2.getString(5));
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
                        Set<EstudioCaso> listEst = new HashSet<EstudioCaso>();
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
                            est.setNSolicitud(rs3.getLong(9));
                            listEst.add(est);
                        }
                        auxnna.setEstudioCasos(listEst);
                        statement3.close();
                        rs3.close();
                    }
                    allNnaFinal.add(auxnna);
                }

                //METODO BUBBLESORT PARA ORDENAR POR CODIGO
                if (clasif.equals("prioritario")) {
                    Nna auxnna2;
                    int n = allNnaFinal.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = i; j < n - 1; j++) {
                            if (allNnaFinal.get(i).getExpedienteNnas().isEmpty()) {
                                if (!allNnaFinal.get(j + 1).getExpedienteNnas().isEmpty()) {
                                    auxnna2 = allNnaFinal.get(i);
                                    allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                    allNnaFinal.set(j + 1, auxnna2);
                                } else {
                                    String apellidoPrev = "";
                                    String apellidoNext = "";
                                    try{
                                        apellidoPrev = allNnaFinal.get(i).getApellidoP();
                                        apellidoNext = allNnaFinal.get(j + 1).getApellidoP();
                                    } catch (Exception ex){
                                        
                                    }
                                    if (apellidoPrev.compareToIgnoreCase(apellidoNext) > 0) {
                                        auxnna2 = allNnaFinal.get(i);
                                        allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                        allNnaFinal.set(j + 1, auxnna2);
                                    }
                                }
                            } else {
                                Set<ExpedienteNna> listExp1 = allNnaFinal.get(i).getExpedienteNnas();
                                Set<ExpedienteNna> listExp2 = allNnaFinal.get(j + 1).getExpedienteNnas();
                                if (!listExp2.isEmpty()) {
                                    for (ExpedienteNna exp1 : listExp1) {
                                        for (ExpedienteNna exp2 : listExp2) {
                                            String codant = exp1.getCodigoReferencia();
                                            String codpost = exp2.getCodigoReferencia();
                                            if (codant == null){
                                                codant = "";
                                            }
                                            if (codpost == null){
                                                codpost = "";
                                            }
                                            if (codant.compareToIgnoreCase(codpost) > 0) {
                                                auxnna2 = allNnaFinal.get(i);
                                                allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                                allNnaFinal.set(j + 1, auxnna2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        session.doWork(work);

        return allNnaFinal;
    }
    
    public ArrayList<Nna> ListaNnaSeguimiento(String clasificacion) {
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

                EstudioCaso est;

                String hql = "{call HN_GET_NNA_SEG(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(1);

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
                    Set<Designacion> listDesig = new HashSet<Designacion>();
                    String hql2 = "{call HN_GET_DESIGNACIONES(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, auxnna.getIdnna());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);
                    while (rs2.next()) {
                        desig = new Designacion();
                        desig.setIddesignacion(rs2.getLong(1));
                        desig.setNDesignacion(rs2.getString(5));
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
                        Set<EstudioCaso> listEst = new HashSet<EstudioCaso>();
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
                            est.setNSolicitud(rs3.getLong(9));
                            listEst.add(est);
                        }
                        auxnna.setEstudioCasos(listEst);
                        statement3.close();
                        rs3.close();
                    }
                    allNnaFinal.add(auxnna);
                }

                //METODO BUBBLESORT PARA ORDENAR POR CODIGO
                if (clasif.equals("prioritario")) {
                    Nna auxnna2;
                    int n = allNnaFinal.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = i; j < n - 1; j++) {
                            if (allNnaFinal.get(i).getExpedienteNnas().isEmpty()) {
                                if (!allNnaFinal.get(j + 1).getExpedienteNnas().isEmpty()) {
                                    auxnna2 = allNnaFinal.get(i);
                                    allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                    allNnaFinal.set(j + 1, auxnna2);
                                } else {
                                    String apellidoPrev = "";
                                    String apellidoNext = "";
                                    try{
                                        apellidoPrev = allNnaFinal.get(i).getApellidoP();
                                        apellidoNext = allNnaFinal.get(j + 1).getApellidoP();
                                    } catch (Exception ex){
                                        
                                    }
                                    if (apellidoPrev.compareToIgnoreCase(apellidoNext) > 0) {
                                        auxnna2 = allNnaFinal.get(i);
                                        allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                        allNnaFinal.set(j + 1, auxnna2);
                                    }
                                }
                            } else {
                                Set<ExpedienteNna> listExp1 = allNnaFinal.get(i).getExpedienteNnas();
                                Set<ExpedienteNna> listExp2 = allNnaFinal.get(j + 1).getExpedienteNnas();
                                if (!listExp2.isEmpty()) {
                                    for (ExpedienteNna exp1 : listExp1) {
                                        for (ExpedienteNna exp2 : listExp2) {
                                            String codant = exp1.getCodigoReferencia();
                                            String codpost = exp2.getCodigoReferencia();
                                            if (codant == null){
                                                codant = "";
                                            }
                                            if (codpost == null){
                                                codpost = "";
                                            }
                                            if (codant.compareToIgnoreCase(codpost) > 0) {
                                                auxnna2 = allNnaFinal.get(i);
                                                allNnaFinal.set(i, allNnaFinal.get(j + 1));
                                                allNnaFinal.set(j + 1, auxnna2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        session.doWork(work);

        return allNnaFinal;
    }
//    public ExpedienteNna getExpNna(long idNna) {
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
                    expnna.setIdexpedienteNna(tempexp.getLong(1));
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
                    expnna.setFechaIngPrio(tempexp.getDate("FECHA_ING_PRIO"));
                    expnna.setFechaActualizacion(tempexp.getDate("FECHA_ACTUALIZACION"));
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
                    Unidad unid = new Unidad();
                    unid.setIdunidad(tempexp.getLong(62));
                    expnna.setUnidad(unid);
                }
                tempexp.close();
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
                temp.close();
                statement.close();
            }
        };
        session.doWork(work);
        return allExpNna;
    }

//    public void crearExpNna(ExpedienteNna temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//    }
    //POR PROBAR
    public void crearExpNna(ExpedienteNna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ExpedienteNna expnna = temp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_SAVE_EXP_NNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, expnna.getNna().getIdnna());
                statement.setLong(2, expnna.getUnidad().getIdunidad());
                statement.setString(3, expnna.getNumero());
                statement.setDate(4, (java.sql.Date) expnna.getFechaIngreso());
                statement.setString(5, expnna.getHt());
                statement.setString(6, expnna.getNExpTutelar());
                statement.setString(7, expnna.getProcTutelar());
                statement.setShort(8, expnna.getFichaIntegral());
                statement.setString(9, expnna.getComentarios());
                statement.setString(10, expnna.getRespLegalNombre());
                statement.setString(11, expnna.getRespLegalP());
                statement.setString(12, expnna.getRespLegalM());
                statement.setString(13, expnna.getRespPsicosocialNombre());
                statement.setString(14, expnna.getRespPiscosocialP());
                statement.setString(15, expnna.getRespPsicosocialM());
                statement.setString(16, expnna.getEstado());
                statement.setDate(17, (java.sql.Date) expnna.getFechaEstado());
                statement.setShort(18, expnna.getAdoptable());
                statement.setDate(19, (java.sql.Date) expnna.getFechaResolCons());
                statement.setShort(20, expnna.getNacional());
                statement.setString(21, expnna.getDiagnostico());
                statement.setString(22, expnna.getCodigoReferencia());
                statement.setString(23, expnna.getNActual());
                statement.setString(24, expnna.getApellidopActual());
                statement.setString(25, expnna.getApellidomActual());
                statement.setString(26, expnna.getObservaciones());
                statement.setDate(27, (java.sql.Date) expnna.getFechaInvTutelar());
                statement.setDate(28, (java.sql.Date) expnna.getFechaIngPrio());
                statement.setDate(29, (java.sql.Date) expnna.getFechaActualizacion());

                statement.execute();
                statement.close();
            }
        };
        session.doWork(work);
    }

//    public void updateExpNna(ExpedienteNna temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//    }
    //PARA PROBAR
    public void updateExpNna(ExpedienteNna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ExpedienteNna expnna = temp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_UPDATE_EXP_NNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, expnna.getNna().getIdnna());
                statement.setLong(2, expnna.getUnidad().getIdunidad());
                statement.setString(3, expnna.getNumero());
                statement.setDate(4, (java.sql.Date) expnna.getFechaIngreso());
                statement.setString(5, expnna.getHt());
                statement.setString(6, expnna.getNExpTutelar());
                statement.setString(7, expnna.getProcTutelar());
                statement.setShort(8, expnna.getFichaIntegral());
                statement.setString(9, expnna.getComentarios());
                statement.setString(10, expnna.getRespLegalNombre());
                statement.setString(11, expnna.getRespLegalP());
                statement.setString(12, expnna.getRespLegalM());
                statement.setString(13, expnna.getRespPsicosocialNombre());
                statement.setString(14, expnna.getRespPiscosocialP());
                statement.setString(15, expnna.getRespPsicosocialM());
                statement.setString(16, expnna.getEstado());
                statement.setDate(17, (java.sql.Date) expnna.getFechaEstado());
                statement.setShort(18, expnna.getAdoptable());
                statement.setDate(19, (java.sql.Date) expnna.getFechaResolCons());
                statement.setShort(20, expnna.getNacional());
                statement.setString(21, expnna.getDiagnostico());
                statement.setString(22, expnna.getCodigoReferencia());
                statement.setString(23, expnna.getNActual());
                statement.setString(24, expnna.getApellidopActual());
                statement.setString(25, expnna.getApellidomActual());
                statement.setString(26, expnna.getObservaciones());
                statement.setDate(27, (java.sql.Date) expnna.getFechaInvTutelar());
                statement.setLong(28, expnna.getIdexpedienteNna());
                statement.setDate(29, (java.sql.Date) expnna.getFechaIngPrio());
                statement.setDate(30, (java.sql.Date) expnna.getFechaActualizacion());
                

                statement.execute();
                statement.close();
            }
        };
        session.doWork(work);
    }
    
    public ArrayList<InformeNna> listaInformesExpNna(Long idExpNna) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final Long expNna = idExpNna;
        final ArrayList<InformeNna> lista = new ArrayList();
        
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_LIST_INF_EVAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, expNna);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();
                
                ResultSet rs = (ResultSet) statement.getObject(2);
               
                while(rs.next()){
                    InformeNna tempInf = new InformeNna();
                    tempInf.setIdinformeNna(rs.getLong("IDINFORME_NNA"));
                    tempInf.setNumero(rs.getString("NUMERO"));
                    tempInf.setFecha(rs.getDate("FECHA"));
                    lista.add(tempInf);
                    
                }
                
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);
        return lista;
    }
    
    public void crearInforme(InformeNna tempInf, Long idExp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        final InformeNna inf = tempInf;
        final Long id = idExp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_INSERT_INF(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, id);
                statement.setString(2, inf.getNumero());
                statement.setDate(3, (java.sql.Date) inf.getFecha());
                statement.setString(4, inf.getResultado());
                statement.setString(5, inf.getObservaciones());
                
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }
    
    public InformeNna InformeExpNna(Long idInforme) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final Long id = idInforme;
        final InformeNna temp = new InformeNna();
        
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_GET_INF_EVAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, id);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();
                
                ResultSet rs = (ResultSet) statement.getObject(2);
               
                if(rs.next()){
                    temp.setIdinformeNna(rs.getLong("IDINFORME_NNA"));
                    temp.setNumero(rs.getString("NUMERO"));
                    temp.setFecha(rs.getDate("FECHA"));
                    temp.setResultado(rs.getString("RESULTADO"));
                    temp.setObservaciones(rs.getString("OBSERVACIONES"));
                }
                
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);
        return temp;
    }
    
    public void updateInforme(InformeNna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final InformeNna inf = temp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_UPDATE_INF(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, inf.getIdinformeNna());
                statement.setString(2, inf.getNumero());
                statement.setDate(3, (java.sql.Date) inf.getFecha());
                statement.setString(4, inf.getResultado());
                statement.setString(5, inf.getObservaciones());

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }
    
    
    ///////////// Creación de ID's Automáticos /////////////////
    
    public String get_Last_numero_expediente() {
        Session session = sessionFactory.getCurrentSession();
        Work work;
        work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                numero_last = null;
                ResultSet temp_numero;
                String hql = "{call HN_GET_LAST_EXPEDIENTE_NNA(?)}";
                CallableStatement statement = connection.prepareCall(hql);                
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();
                
                temp_numero = (ResultSet) statement.getObject(1); 
                while(temp_numero.next()){
                numero_last = temp_numero.getString(1);
                }
                temp_numero.close();
                statement.close();
            }
        };
        session.doWork(work);

        return numero_last;
    }
    
    
    public void crearExpNna2(ExpedienteNna temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ExpedienteNna expnna = temp;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HN_SAVE_EXP_NNA2(?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, expnna.getNna().getIdnna());
                statement.setLong(2, expnna.getUnidad().getIdunidad());
                statement.setString(3, expnna.getNumero());                

                statement.execute();
                statement.close();
            }
        };
        session.doWork(work);
    }
    
}
