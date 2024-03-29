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
import java.math.BigDecimal;
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
@Service("HiberEtapa")
@Transactional
public class HiberEtapa {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    ResultSet temp;
    ResultSet temp2;
    ResultSet temp3;
    ResultSet temp_entidad;
    ResultSet temp_familia;
    ResultSet temp_unidad;
    ResultSet temp_personal;
    ResultSet temp_resolucion;
    ResultSet temp_evaluacion;
    ResultSet temp_expediente;
    ResultSet temp_designacion;
    ResultSet temp_nna;
    ResultSet temp_estudio;
    ResultSet temp_postadopcion;
    ResultSet temp_infopost;
    ResultSet temp_juzgado;
    ResultSet temp_car;

//    public ArrayList<Familia> getListaFamilias() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "FROM Familia F WHERE F.habilitado = 0";
//        Query query = session.createQuery(hql);
//        List familias = query.list();
//        ArrayList<Familia> allFamilias = new ArrayList();
//        if (!query.list().isEmpty()) {
//            for (Iterator iter = familias.iterator(); iter.hasNext();) {
//                Familia temp = (Familia) iter.next();
//                Hibernate.initialize(temp.getExpedienteFamilias());
//                Hibernate.initialize(temp.getAsistenciaFRs());
//
//                String hql2 = "FROM FormularioSesion F WHERE F.familia = :idFamilia ORDER BY F.fechaSol DESC ";
//                Query query2 = session.createQuery(hql2);
//                query2.setLong("idFamilia", temp.getIdfamilia());
//                query2.setMaxResults(1);
//                Object queryResult = query2.uniqueResult();
//                if (queryResult != null) {
//                    FormularioSesion tempFs = (FormularioSesion) queryResult;
//                    Hibernate.initialize(tempFs.getAsistentes());
//                    Set<FormularioSesion> listFs = new HashSet<FormularioSesion>();
//                    listFs.add(tempFs);
//                    temp.setFormularioSesions(listFs);
//                } else {
//                    temp.setFormularioSesions(null);
//                }
//                allFamilias.add(temp);
//
//            }
//        }
//        return allFamilias;
//
//    }
    public ArrayList<Familia> getListaFamilias() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        final ArrayList<Familia> allFamilias = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_FAM_HAB(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<AsistenciaFR> listAFR = new HashSet<AsistenciaFR>();
                    Set<FormularioSesion> listFS = new HashSet<FormularioSesion>();

                    Familia tempFamp = new Familia();
                    tempFamp.setIdfamilia(rs.getLong("IDFAMILIA"));
                    tempFamp.setCorreo(rs.getString("CORREO"));
                    tempFamp.setHabilitado(rs.getShort("HABILITADO"));
                    tempFamp.setConstancia(rs.getString("CONSTANCIA"));

                    String hql2 = "{call HE_GET_EXPFAM_BY_IDFAM(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempFamp.getIdfamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    if (rs2.next()) {
                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                        tempExpFam.setIdexpedienteFamilia(rs2.getLong("IDEXPEDIENTE_FAMILIA"));
                        tempExpFam.setExpediente(rs2.getString("EXPEDIENTE"));
                        tempExpFam.setHt(rs2.getString("HT"));

                        listExp.add(tempExpFam);
                    }
                    rs2.close();
                    statement2.close();

                    String hql3 = "{call HE_AFR_BY_IDFAM(?,?)}";
                    CallableStatement statement3 = connection.prepareCall(hql3);
                    statement3.setLong(1, tempFamp.getIdfamilia());
                    statement3.registerOutParameter(2, OracleTypes.CURSOR);
                    statement3.execute();

                    ResultSet rs3 = (ResultSet) statement3.getObject(2);

                    while (rs3.next()) {
                        AsistenciaFR tempAFR = new AsistenciaFR();
                        tempAFR.setIdasistenciaFR(rs3.getLong("IDASISTENCIA_F_R"));
                        String asist = "";
                        asist = rs3.getString("ASISTENCIA");
                        if (!rs3.wasNull()) {
                            tempAFR.setAsistencia(asist.charAt(0));
                        }
                        tempAFR.setInasJus(rs3.getShort("INAS_JUS"));
                        tempAFR.setFamilia(tempFamp);

                        listAFR.add(tempAFR);

                    }
                    rs3.close();
                    statement3.close();

                    String hql4 = "{call HE_GET_FS_BY_IDFAM(?,?)}";
                    CallableStatement statement4 = connection.prepareCall(hql4);
                    statement4.setLong(1, tempFamp.getIdfamilia());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();

                    ResultSet rs4 = (ResultSet) statement4.getObject(2);

                    if (rs4.next()) {
                        FormularioSesion tempFS = new FormularioSesion();
                        Sesion tempSesion = new Sesion();
                        tempFS.setIdformularioSesion(rs4.getLong("IDFORMULARIO_SESION"));
                        tempSesion.setIdsesion(rs4.getLong("IDSESION"));
                        tempSesion.setUnidad(rs4.getString("UNIDAD"));
                        Set<Asistente> listA = new HashSet<Asistente>();

                        String hql5 = "{call HE_GET_ASIS_BY_IDFS(?,?)}";
                        CallableStatement statement5 = connection.prepareCall(hql5);
                        statement5.setLong(1, tempFS.getIdformularioSesion());
                        statement5.registerOutParameter(2, OracleTypes.CURSOR);
                        statement5.execute();
                        ResultSet rs5 = (ResultSet) statement5.getObject(2);

                        while (rs5.next()) {
                            Asistente tempAsis = new Asistente();
                            tempAsis.setIdasistente(rs5.getLong("IDASISTENTE"));
                            tempAsis.setNombre(rs5.getString("NOMBRE"));
                            tempAsis.setApellidoP(rs5.getString("APELLIDO_P"));
                            tempAsis.setApellidoM(rs5.getString("APELLIDO_M"));
                            String tempsexo = "";
                            tempsexo = rs5.getString("SEXO");
                            if (!rs5.wasNull()) {
                                tempAsis.setSexo(tempsexo.charAt(0));
                            }
                            tempAsis.setEdad(rs5.getShort("EDAD"));
                            tempAsis.setCorreo(rs5.getString("CORREO"));
                            tempAsis.setFormularioSesion(tempFS);

                            listA.add(tempAsis);
                        }
                        rs5.close();
                        statement5.close();

                        tempFS.setSesion(tempSesion);
                        tempFS.setAsistentes(listA);
                        listFS.add(tempFS);
                    }
                    rs4.close();
                    statement4.close();

                    tempFamp.setExpedienteFamilias(listExp);
                    tempFamp.setAsistenciaFRs(listAFR);
                    tempFamp.setFormularioSesions(listFS);

                    allFamilias.add(tempFamp);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allFamilias;

    }

    /*  public Familia getFamilia(long id) {
     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     Familia fam = new Familia();

     session.beginTransaction();
     String hqlA = "FROM Familia F WHERE F.id = :id";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("id", id);
     Object queryResultA = queryA.uniqueResult();

     fam = (Familia) queryResultA;
        
     return fam;
     }*/
    public Familia getFamilia(final long id) {
        Session session = sessionFactory.getCurrentSession();

        final Familia fam = new Familia();
        final Entidad ent = new Entidad();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GETFAMILIA(?, ?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, id);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);
                while (temp.next()) {
                    fam.setIdfamilia(temp.getShort(1));
                    if (temp.getShort(2) != 0) {

                        String hql2 = "{call HE_GETENTIDAD(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, temp.getShort(2));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        temp2 = (ResultSet) statement2.getObject(2);
                        while (temp2.next()) {
                            ent.setIdentidad(temp2.getShort(1));
                            ent.setNombre(temp2.getString(2));
                            ent.setUser(temp2.getString(3));
                            ent.setPass(temp2.getString(4));
                            ent.setDireccion(temp2.getString(5));
                            ent.setTelefono(temp2.getString(6));
                            ent.setPais(temp2.getString(7));
                            ent.setResolAuto(temp2.getString(8));
                            ent.setFechaResol(temp2.getDate(9));
                            ent.setResolRenov(temp2.getString(10));
                            ent.setFechaRenov(temp2.getDate(11));
                            ent.setFechaVenc(temp2.getDate(12));
                            ent.setObs(temp2.getString(13));
                            ent.setCorreo(temp2.getString(14));
                            fam.setEntidad(ent);
                        }
                        temp2.close();
                        statement2.close();
                    }
                    fam.setUser(temp.getString(3));
                    fam.setPass(temp.getString(4));
                    fam.setCorreo(temp.getString(5));
                    fam.setHabilitado(temp.getShort(6));
                    fam.setConstancia(temp.getString(7));

                }
                temp.close();
                statement.close();
            }
        };
        session.doWork(work);

        return fam;
    }

//    public void UpdateFamilia(Familia temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    public void UpdateFamilia(Familia temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idFam = temp.getIdfamilia();
        final String user = temp.getUser();
        final String pas = temp.getPass();
        final short hab = temp.getHabilitado();
        final String mail = temp.getCorreo();
        final String cons = temp.getConstancia();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_FAMILIA(?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idFam);
                statement.setString(2, user);
                statement.setString(3, pas);
                statement.setString(4, mail);
                statement.setShort(5, hab);
                statement.setString(6, cons);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    /*public ExpedienteFamilia getExpedienteFamilia(long id) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ExpedienteFamilia expFamilia = new ExpedienteFamilia();
     String hqlA = "FROM ExpedienteFamilia EF WHERE EF.id = :id";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("id", id);
     Object queryResultA = queryA.uniqueResult();

     expFamilia = (ExpedienteFamilia) queryResultA;

     return expFamilia;

     }*/
    public ExpedienteFamilia getExpedienteFamilia(final long id) {

        Session session = sessionFactory.getCurrentSession();

        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Familia fam = new Familia();
        final Unidad unidad = new Unidad();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, id);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);
                while (temp.next()) {
                    expFamilia.setIdexpedienteFamilia(temp.getShort(1));
                    if (temp.getShort(2) != 0) {

                        String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, temp.getShort(2));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        temp2 = (ResultSet) statement2.getObject(2);
                        while (temp2.next()) {
                            fam.setIdfamilia(temp2.getShort(1));
                            expFamilia.setFamilia(fam);
                        }
                        temp2.close();
                        statement2.close();
                    }
                    if (temp.getShort(3) != 0) {

                        String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, temp.getShort(3));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        temp3 = (ResultSet) statement3.getObject(2);
                        while (temp3.next()) {
                            unidad.setIdunidad(temp3.getShort(1));
                            expFamilia.setUnidad(unidad);
                        }
                        temp3.close();
                        statement3.close();
                    }
                    expFamilia.setNumero(temp.getLong(4));
                    expFamilia.setExpediente(temp.getString(5));
                    expFamilia.setHt(temp.getString(6));
                    expFamilia.setNumeroExpediente(temp.getString(7));
                    expFamilia.setFechaIngresoDga(temp.getDate(8));
                    expFamilia.setEstado(temp.getString(9));
                    expFamilia.setTupa(temp.getDate(10));
                    expFamilia.setNacionalidad(temp.getString(11));
                    expFamilia.setRnsa(temp.getShort(12));
                    expFamilia.setRnaa(temp.getShort(13));
                    expFamilia.setTipoFamilia(temp.getString(14));
                    expFamilia.setTipoListaEspera(temp.getString(15));
                    expFamilia.setHtFicha(temp.getString(16));
                    expFamilia.setnFicha(temp.getString(17));
                    expFamilia.setFechaIngresoFicha(temp.getDate(18));

                }
                temp.close();
                statement.close();
            }
        };
        session.doWork(work);

        return expFamilia;

    }

    /* public Evaluacion getEvaluacion(long id) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     Evaluacion tempEval = new Evaluacion();

     String hqlA = "FROM Evaluacion E WHERE E.id = :id";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("id", id);
     Object queryResultA = queryA.uniqueResult();

     tempEval = (Evaluacion) queryResultA;

     return tempEval;

     }*/
    public Evaluacion getEvaluacion(final long id) {

        Session session = sessionFactory.getCurrentSession();

        final Evaluacion tempEval = new Evaluacion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Personal personal = new Personal();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_EVALUACION(?, ?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, id);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);
                while (temp.next()) {
                    tempEval.setIdevaluacion(temp.getShort(1));
                    if (temp.getShort(2) != 0) {

                        String hql2 = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, temp.getShort(2));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        temp2 = (ResultSet) statement2.getObject(2);
                        while (temp2.next()) {
                            expFamilia.setIdexpedienteFamilia(temp2.getShort(1));
                            tempEval.setExpedienteFamilia(expFamilia);
                        }
                        temp2.close();
                        statement2.close();
                    }
                    if (temp.getShort(3) != 0) {

                        String hql3 = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, temp.getShort(3));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        temp3 = (ResultSet) statement3.getObject(2);
                        while (temp3.next()) {
                            personal.setIdpersonal(temp3.getShort(1));
                            tempEval.setPersonal(personal);
                        }
                        temp3.close();
                        statement3.close();
                    }
                    tempEval.setTipo(temp.getString(4));
                    tempEval.setFechaAsignacion(temp.getDate(5));
                    tempEval.setResultado(temp.getString(6));
                    tempEval.setFechaResultado(temp.getDate(7));
                    tempEval.setObservacion(temp.getString(8));
                    tempEval.setSustento(temp.getString(9));
                    tempEval.setNDesignacion(temp.getString(10));
                    tempEval.setNumEval(temp.getString(11));
                    tempEval.setPersInt(temp.getString("PERS_INT"));
                    tempEval.setNumPersInt(temp.getLong("NUM_PERS_INT"));
                }
                temp.close();
                statement.close();
            }
        };
        session.doWork(work);

        return tempEval;

    }

    /*  public Evaluacion getEvaluacion2(long id) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     Evaluacion tempEval = new Evaluacion();

     String hqlA = "FROM Evaluacion E WHERE E.id = :id";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("id", id);
     Object queryResultA = queryA.uniqueResult();

     tempEval = (Evaluacion) queryResultA;
     Hibernate.initialize(tempEval.getExpedienteFamilia().getFamilia());

     return tempEval;

     } */
    public Evaluacion getEvaluacion2(final long id) {

        Session session = sessionFactory.getCurrentSession();
        final Evaluacion tempEval = new Evaluacion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Personal personal = new Personal();
        final Familia fam = new Familia();
        final Entidad ent = new Entidad();
        final Unidad unidad = new Unidad();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql_evaluacion = "{call HE_GET_EVALUACION(?, ?)}";
                CallableStatement statement = connection.prepareCall(hql_evaluacion);
                statement.setLong(1, id);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(2);
                while (temp.next()) {
                    tempEval.setIdevaluacion(temp.getShort(1));
                    if (temp.getShort(2) != 0) {

                        String hql_expediente = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_expediente = connection.prepareCall(hql_expediente);
                        statement_expediente.setLong(1, temp.getShort(2));
                        statement_expediente.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_expediente.execute();
                        temp2 = (ResultSet) statement_expediente.getObject(2);
                        while (temp2.next()) {
                            expFamilia.setIdexpedienteFamilia(temp2.getShort(1));

                            if (temp2.getShort(2) != 0) {

                                String hql_expediente_familia = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement_expediente_familia = connection.prepareCall(hql_expediente_familia);
                                statement_expediente_familia.setLong(1, temp2.getShort(2));
                                statement_expediente_familia.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_expediente_familia.execute();
                                temp_familia = (ResultSet) statement_expediente_familia.getObject(2);
                                while (temp_familia.next()) {
                                    fam.setIdfamilia(temp_familia.getShort(1));

                                    if (temp_familia.getShort(2) != 0) {

                                        String hql_expediente_familia_entidad = "{call HE_GETENTIDAD(?, ?)}";
                                        CallableStatement statement_expediente_familia_entidad = connection.prepareCall(hql_expediente_familia_entidad);
                                        statement_expediente_familia_entidad.setLong(1, temp.getShort(2));
                                        statement_expediente_familia_entidad.registerOutParameter(2, OracleTypes.CURSOR);
                                        statement_expediente_familia_entidad.execute();
                                        temp_entidad = (ResultSet) statement_expediente_familia_entidad.getObject(2);
                                        while (temp_entidad.next()) {
                                            ent.setIdentidad(temp_entidad.getShort(1));
                                            fam.setEntidad(ent);
                                        }
                                        temp_entidad.close();
                                        statement_expediente_familia_entidad.close();
                                    }
                                    fam.setUser(temp_familia.getString(3));
                                    fam.setPass(temp_familia.getString(4));
                                    fam.setCorreo(temp_familia.getString(5));
                                    fam.setHabilitado(temp_familia.getShort(6));
                                    fam.setConstancia(temp_familia.getString(7));
                                    expFamilia.setFamilia(fam);
                                }
                                temp_familia.close();
                                statement_expediente_familia.close();
                            }

                            if (temp2.getShort(3) != 0) {

                                String hql_expediente_unidad = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement_expediente_unidad = connection.prepareCall(hql_expediente_unidad);
                                statement_expediente_unidad.setLong(1, temp2.getShort(3));
                                statement_expediente_unidad.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_expediente_unidad.execute();
                                temp_unidad = (ResultSet) statement_expediente_unidad.getObject(2);
                                while (temp_unidad.next()) {
                                    unidad.setIdunidad(temp_unidad.getShort(1));
                                    expFamilia.setUnidad(unidad);
                                }
                                temp_unidad.close();
                                statement_expediente_unidad.close();
                            }

                            expFamilia.setNumero(temp2.getLong(4));
                            expFamilia.setExpediente(temp2.getString(5));
                            expFamilia.setHt(temp2.getString(6));
                            expFamilia.setNumeroExpediente(temp2.getString(7));
                            expFamilia.setFechaIngresoDga(temp2.getDate(8));
                            expFamilia.setEstado(temp2.getString(9));
                            expFamilia.setTupa(temp2.getDate(10));
                            expFamilia.setNacionalidad(temp2.getString(11));
                            expFamilia.setRnsa(temp2.getShort(12));
                            expFamilia.setRnaa(temp2.getShort(13));
                            expFamilia.setTipoFamilia(temp2.getString(14));
                            expFamilia.setTipoListaEspera(temp2.getString(15));
                            expFamilia.setHtFicha(temp2.getString(16));
                            expFamilia.setnFicha(temp2.getString(17));
                            expFamilia.setFechaIngresoFicha(temp2.getDate(18));

                            tempEval.setExpedienteFamilia(expFamilia);
                        }
                        temp2.close();
                        statement_expediente.close();
                    }

                    if (temp.getShort(3) != 0) {

                        String hql_evaluacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_evaluacion_personal = connection.prepareCall(hql_evaluacion_personal);
                        statement_evaluacion_personal.setLong(1, temp.getShort(3));
                        statement_evaluacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_evaluacion_personal.execute();
                        temp_personal = (ResultSet) statement_evaluacion_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));
                            tempEval.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_evaluacion_personal.close();
                    }
                    tempEval.setTipo(temp.getString(4));
                    tempEval.setFechaAsignacion(temp.getDate(5));
                    tempEval.setResultado(temp.getString(6));
                    tempEval.setFechaResultado(temp.getDate(7));
                    tempEval.setObservacion(temp.getString(8));
                    tempEval.setSustento(temp.getString(9));
                    tempEval.setNDesignacion(temp.getString(10));
                    tempEval.setNumEval(temp.getString(11));

                }
                temp.close();
                statement.close();
            }
        };
        session.doWork(work);

        return tempEval;

    }

    /*  public Resolucion getResolucion(long idResolucion) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     Resolucion tempResol = new Resolucion();

     String hqlA = "FROM Resolucion R WHERE R.id = :id";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("id", idResolucion);
     Object queryResultA = queryA.uniqueResult();

     tempResol = (Resolucion) queryResultA;
     Hibernate.initialize(tempResol.getEvaluacion().getExpedienteFamilia());
     return tempResol;

     }*/
    public Resolucion getResolucion(final long idResolucion) {

        Session session = sessionFactory.getCurrentSession();
        final Resolucion resolucion = new Resolucion();
        final Evaluacion evaluacion = new Evaluacion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Familia fam = new Familia();
        final Unidad unidad = new Unidad();
        final Personal personal = new Personal();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql_resolucion = "{call HE_GET_RESOLUCION(?, ?)}";
                CallableStatement statement_resolucion = connection.prepareCall(hql_resolucion);
                statement_resolucion.setLong(1, idResolucion);
                statement_resolucion.registerOutParameter(2, OracleTypes.CURSOR);
                statement_resolucion.execute();

                temp_resolucion = (ResultSet) statement_resolucion.getObject(2);
                while (temp_resolucion.next()) {
                    resolucion.setIdresolucion(temp_resolucion.getShort(1));
                    if (temp_resolucion.getShort(2) != 0) {

                        String hql_resolucion_evaluacion = "{call HE_GET_EVALUACION(?, ?)}";
                        CallableStatement statement_resolucion_evaluacion = connection.prepareCall(hql_resolucion_evaluacion);
                        statement_resolucion_evaluacion.setLong(1, temp_resolucion.getShort(2));
                        statement_resolucion_evaluacion.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_resolucion_evaluacion.execute();
                        temp_evaluacion = (ResultSet) statement_resolucion_evaluacion.getObject(2);
                        while (temp_evaluacion.next()) {
                            evaluacion.setIdevaluacion(temp_evaluacion.getShort(1));

                            if (temp_evaluacion.getShort(2) != 0) {

                                String hql_resolucion_evaluacion_expediente = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                                CallableStatement statement_resolucion_evaluacion_expediente = connection.prepareCall(hql_resolucion_evaluacion_expediente);
                                statement_resolucion_evaluacion_expediente.setLong(1, temp_evaluacion.getShort(2));
                                statement_resolucion_evaluacion_expediente.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_resolucion_evaluacion_expediente.execute();
                                temp_expediente = (ResultSet) statement_resolucion_evaluacion_expediente.getObject(2);
                                while (temp_expediente.next()) {
                                    expFamilia.setIdexpedienteFamilia(temp_expediente.getShort(1));

                                    if (temp_expediente.getShort(2) != 0) {

                                        String hql_resolucion_evaluacion_expediente_familia = "{call HE_GETFAMILIA(?, ?)}";
                                        CallableStatement statement_resolucion_evaluacion_expediente_familia = connection.prepareCall(hql_resolucion_evaluacion_expediente_familia);
                                        statement_resolucion_evaluacion_expediente_familia.setLong(1, temp_expediente.getShort(2));
                                        statement_resolucion_evaluacion_expediente_familia.registerOutParameter(2, OracleTypes.CURSOR);
                                        statement_resolucion_evaluacion_expediente_familia.execute();
                                        temp_familia = (ResultSet) statement_resolucion_evaluacion_expediente_familia.getObject(2);
                                        while (temp_familia.next()) {
                                            fam.setIdfamilia(temp_familia.getShort(1));
                                            expFamilia.setFamilia(fam);
                                        }
                                        temp_familia.close();
                                        statement_resolucion_evaluacion_expediente_familia.close();
                                    }

                                    if (temp_expediente.getShort(3) != 0) {

                                        String hql_resolucion_evaluacion_expediente_unidad = "{call HE_GET_UNIDAD(?, ?)}";
                                        CallableStatement statement_resolucion_evaluacion_expediente_unidad = connection.prepareCall(hql_resolucion_evaluacion_expediente_unidad);
                                        statement_resolucion_evaluacion_expediente_unidad.setLong(1, temp_expediente.getShort(3));
                                        statement_resolucion_evaluacion_expediente_unidad.registerOutParameter(2, OracleTypes.CURSOR);
                                        statement_resolucion_evaluacion_expediente_unidad.execute();
                                        temp_unidad = (ResultSet) statement_resolucion_evaluacion_expediente_unidad.getObject(2);
                                        while (temp_unidad.next()) {
                                            unidad.setIdunidad(temp_unidad.getShort(1));
                                            expFamilia.setUnidad(unidad);
                                        }
                                        temp_unidad.close();
                                        statement_resolucion_evaluacion_expediente_unidad.close();
                                    }

                                    expFamilia.setNumero(temp_expediente.getLong(4));
                                    expFamilia.setExpediente(temp_expediente.getString(5));
                                    expFamilia.setHt(temp_expediente.getString(6));
                                    expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                                    expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                                    expFamilia.setEstado(temp_expediente.getString(9));
                                    expFamilia.setTupa(temp_expediente.getDate(10));
                                    expFamilia.setNacionalidad(temp_expediente.getString(11));
                                    expFamilia.setRnsa(temp_expediente.getShort(12));
                                    expFamilia.setRnaa(temp_expediente.getShort(13));
                                    expFamilia.setTipoFamilia(temp_expediente.getString(14));
                                    expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                                    expFamilia.setHtFicha(temp_expediente.getString(16));
                                    expFamilia.setnFicha(temp_expediente.getString(17));
                                    expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                                    evaluacion.setExpedienteFamilia(expFamilia);
                                }
                                temp_expediente.close();
                                statement_resolucion_evaluacion_expediente.close();
                            }

                            if (temp_evaluacion.getShort(3) != 0) {

                                String hql_evaluacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                                CallableStatement statement_evaluacion_personal = connection.prepareCall(hql_evaluacion_personal);
                                statement_evaluacion_personal.setLong(1, temp_evaluacion.getShort(3));
                                statement_evaluacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_evaluacion_personal.execute();
                                temp_personal = (ResultSet) statement_evaluacion_personal.getObject(2);
                                while (temp_personal.next()) {
                                    personal.setIdpersonal(temp_personal.getShort(1));
                                    evaluacion.setPersonal(personal);
                                }
                                temp_personal.close();
                                statement_evaluacion_personal.close();
                            }
                            evaluacion.setTipo(temp_evaluacion.getString(4));
                            evaluacion.setFechaAsignacion(temp_evaluacion.getDate(5));
                            evaluacion.setResultado(temp_evaluacion.getString(6));
                            evaluacion.setFechaResultado(temp_evaluacion.getDate(7));
                            evaluacion.setObservacion(temp_evaluacion.getString(8));
                            evaluacion.setSustento(temp_evaluacion.getString(9));
                            evaluacion.setNDesignacion(temp_evaluacion.getString(10));
                            evaluacion.setNumEval(temp_evaluacion.getString(11));

                            resolucion.setEvaluacion(evaluacion);
                        }
                        temp_evaluacion.close();
                        statement_resolucion_evaluacion.close();
                    }

                    resolucion.setTipo(temp_resolucion.getString(3));
                    resolucion.setNumero(temp_resolucion.getString(4));
                    resolucion.setFechaResol(temp_resolucion.getDate(5));
                    resolucion.setFechaNotificacion(temp_resolucion.getDate(6));

                }
                temp_resolucion.close();
                statement_resolucion.close();
            }
        };
        session.doWork(work);

        return resolucion;

    }

    /*  public Resolucion getResolucion2(long idEval) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     Resolucion tempResol = new Resolucion();

     String hqlA = "FROM Resolucion R WHERE R.evaluacion = :id";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("id", idEval);
     Object queryResultA = queryA.uniqueResult();

     tempResol = (Resolucion) queryResultA;
     return tempResol;

     }*/
    public Resolucion getResolucion2(final long idEval) {

        Session session = sessionFactory.getCurrentSession();
        final Resolucion resolucion = new Resolucion();
        final Evaluacion tempEval = new Evaluacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql_resolucion = "{call HE_GET_RESOLUCION_2(?, ?)}";
                CallableStatement statement_resolucion = connection.prepareCall(hql_resolucion);
                statement_resolucion.setLong(1, idEval);
                statement_resolucion.registerOutParameter(2, OracleTypes.CURSOR);
                statement_resolucion.execute();

                temp_resolucion = (ResultSet) statement_resolucion.getObject(2);
                while (temp_resolucion.next()) {
                    resolucion.setIdresolucion(temp_resolucion.getLong(1));
                    if (temp_resolucion.getLong(2) != 0) {

                        String hql_resolucion_evaluacion = "{call HE_GET_EVALUACION(?, ?)}";
                        CallableStatement statement_resolucion_evaluacion = connection.prepareCall(hql_resolucion_evaluacion);
                        statement_resolucion_evaluacion.setLong(1, temp_resolucion.getLong(2));
                        statement_resolucion_evaluacion.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_resolucion_evaluacion.execute();
                        temp2 = (ResultSet) statement_resolucion_evaluacion.getObject(2);
                        while (temp2.next()) {
                            tempEval.setIdevaluacion(temp2.getLong(1));
                            resolucion.setEvaluacion(tempEval);
                        }
                        temp2.close();
                        statement_resolucion_evaluacion.close();
                    }

                    resolucion.setTipo(temp_resolucion.getString(3));
                    resolucion.setNumero(temp_resolucion.getString(4));
                    resolucion.setFechaResol(temp_resolucion.getDate(5));
                    resolucion.setFechaNotificacion(temp_resolucion.getDate(6));

                }
                temp_resolucion.close();
                statement_resolucion.close();
            }
        };
        session.doWork(work);
        return resolucion;

    }

//    public Evaluacion getLegal(long id) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        Evaluacion tempEval = new Evaluacion();
//
//        String hqlA = "FROM Evaluacion E WHERE E.id = :id";
//        Query queryA = session.createQuery(hqlA);
//        queryA.setLong("id", id);
//        Object queryResultA = queryA.uniqueResult();
//
//        tempEval = (Evaluacion) queryResultA;
//        Hibernate.initialize(tempEval.getExpedienteFamilia());
//        String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol DESC";
//        Query query2 = session.createQuery(hql2);
//        query2.setLong("idEvaluacion", tempEval.getIdevaluacion());
//        List resoluciones = query2.list();
//        Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
//        for (Iterator iter2 = resoluciones.iterator(); iter2.hasNext();) {
//            Resolucion resolTemp = (Resolucion) iter2.next();
//            tempResoluciones.add(resolTemp);
//        }
//        tempEval.setResolucions(tempResoluciones);
//
//        return tempEval;
//
//    }
    public Evaluacion getLegal(long id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final Long idE = id;
        final Evaluacion tempEval = new Evaluacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_LEGAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idE);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Personal temPersonal = new Personal();
                    Set<Resolucion> listaRe = new HashSet<Resolucion>();

                    tempEval.setIdevaluacion(rs.getLong("IDEVALUACION"));
                    tempEval.setTipo(rs.getString("TIPO"));
                    tempEval.setFechaAsignacion(rs.getDate("FECHA_ASIGNACION"));
                    tempEval.setResultado(rs.getString("RESULTADO"));
                    tempEval.setFechaResultado(rs.getDate("FECHA_RESULTADO"));
                    tempEval.setObservacion(rs.getString("OBSERVACION"));
                    tempEval.setSustento(rs.getString("SUSTENTO"));
                    tempEval.setNDesignacion(rs.getString("N_DESIGNACION"));
                    tempEval.setNumEval(rs.getString("NUM_EVAL"));

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    temPersonal.setIdpersonal(rs.getLong("IDPERSONAL"));

                    tempEval.setPersonal(temPersonal);
                    tempEval.setExpedienteFamilia(tempEF);

                    String hql2 = "{call HE_LISTA_RESOL_LEGAL(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempEval.getIdevaluacion());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Resolucion tempRe = new Resolucion();
                        tempRe.setIdresolucion(rs2.getLong("IDRESOLUCION"));
                        tempRe.setTipo(rs2.getString("TIPO"));
                        tempRe.setNumero(rs2.getString("NUMERO"));
                        tempRe.setFechaResol(rs2.getDate("FECHA_RESOL"));
                        tempRe.setFechaNotificacion(rs2.getDate("FECHA_NOTIFICACION"));
                        tempRe.setEvaluacion(tempEval);
                        listaRe.add(tempRe);

                    }
                    rs2.close();
                    statement2.close();

                    tempEval.setResolucions(listaRe);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return tempEval;

    }

//    public ArrayList<ExpedienteFamilia> ListaExpedientes(String nacionalidad, String estado) {
//
//        Session session = sessionFactory.getCurrentSession();
//
//        session.beginTransaction();
//
//        String hql = "from ExpedienteFamilia EF where EF.nacionalidad = :nacionalidad and EF.estado = :estado";
//        Query query = session.createQuery(hql);
//        query.setString("nacionalidad", nacionalidad);
//        query.setString("estado", estado);
//        List expedientes = query.list();
//        ArrayList<ExpedienteFamilia> allExpedienteFamilia = new ArrayList();
//        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
//            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
//            Hibernate.initialize(temp.getEvaluacions());
//            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
//            for (Evaluacion eval : temp.getEvaluacions()) {
//                if (eval.getTipo().equals("legal")) {
//                    String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol DESC";
//                    Query query2 = session.createQuery(hql2);
//                    query2.setLong("idEvaluacion", eval.getIdevaluacion());
//                    query2.setMaxResults(1);
//                    List resoluciones = query2.list();
//                    Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
//                    for (Iterator iter2 = resoluciones.iterator(); iter2.hasNext();) {
//                        Resolucion resolTemp = (Resolucion) iter2.next();
//                        tempResoluciones.add(resolTemp);
//                        System.out.print(resolTemp.getFechaResol());
//                    }
//                    eval.setResolucions(tempResoluciones);
//                    tempEvaluaciones.add(eval);
//                } else {
//                    tempEvaluaciones.add(eval);
//                }
//
//            }
//            temp.setEvaluacions(tempEvaluaciones);
//            allExpedienteFamilia.add(temp);
//        }
//        return allExpedienteFamilia;
//    }
    public ArrayList<ExpedienteFamilia> ListaExpedientes(String nacionalidad, String estado) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        final String nac = nacionalidad;
        final String est = estado;
        final ArrayList<ExpedienteFamilia> allExpedienteFamilia = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_LISTA_EXP_FAM_BY_NAC_EST(?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, nac);
                statement.setString(2, est);
                statement.registerOutParameter(3, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(3);

                while (rs.next()) {
                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();

                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Unidad tempUA = new Unidad();

                    tempUA.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUA.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    String hql2 = "{call HE_LISTAEVAL_BY_IDEXPFAM(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempEF.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(tempEF);
                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("legal")) {

                            String hql3 = "{call HE_ULTRESOL_LEGAL(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    tempEF.setUnidad(tempUA);
                    tempEF.setEvaluacions(listaEv);
                    allExpedienteFamilia.add(tempEF);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allExpedienteFamilia;
    }

//    public void updateExpedienteFamilia(ExpedienteFamilia temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    public void updateExpedienteFamilia(ExpedienteFamilia temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String expediente = temp.getExpediente();
        final String ht = temp.getHt();
        final String numExp = temp.getNumeroExpediente();
        final String estado = temp.getEstado();
        final String nac = temp.getNacionalidad();
        final short rnsa = temp.getRnsa();
        final short rnaa = temp.getRnaa();
        final String tipofam = temp.getTipoFamilia();
        final String tipolista = temp.getTipoListaEspera();
        final String htficha = temp.getHtFicha();
        final String nficha = temp.getnFicha();
        final Long idExp = temp.getIdexpedienteFamilia();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATEEXP(?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.setString(2, expediente);
                statement.setString(3, ht);
                statement.setString(4, numExp);
                statement.setString(5, estado);
                statement.setString(6, nac);
                statement.setShort(7, rnsa);
                statement.setShort(8, rnaa);
                statement.setString(9, tipofam);
                statement.setString(10, tipolista);
                statement.setString(11, htficha);
                statement.setString(12, nficha);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void crearEvaluacion(Evaluacion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    public void crearEvaluacion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idExp = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final Long idPer = temp.getPersonal().getIdpersonal();
        final String tipoEval = temp.getTipo();
        final Date fechaAsig = temp.getFechaAsignacion();
        final String result = temp.getResultado();
        final Date fechaResul = temp.getFechaResultado();
        final String obs = temp.getObservacion();
        final String sust = temp.getSustento();
        final String ndesig = temp.getNDesignacion();
        final String numEval = temp.getNumEval();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_CREAR_EVAL(?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.setLong(2, idPer);
                statement.setString(3, tipoEval);
                statement.setDate(4, (java.sql.Date) fechaAsig);
                statement.setString(5, result);
                statement.setDate(6, (java.sql.Date) fechaResul);
                statement.setString(7, obs);
                statement.setString(8, sust);
                statement.setString(9, ndesig);
                statement.setString(10, numEval);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void updateEvaluacion(Evaluacion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    public void updateEvaluacion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idEval = temp.getIdevaluacion();
        final Long idExp = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final Long idPer = temp.getPersonal().getIdpersonal();
        final String tipoEval = temp.getTipo();
        final Date fechaAsig = temp.getFechaAsignacion();
        final String result = temp.getResultado();
        final Date fechaResul = temp.getFechaResultado();
        final String obs = temp.getObservacion();
        final String sust = temp.getSustento();
        final String ndesig = temp.getNDesignacion();
        final String numEval = temp.getNumEval();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_EVAL(?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idEval);
                statement.setLong(2, idExp);
                statement.setLong(3, idPer);
                statement.setString(4, tipoEval);
                statement.setDate(5, (java.sql.Date) fechaAsig);
                statement.setString(6, result);
                statement.setDate(7, (java.sql.Date) fechaResul);
                statement.setString(8, obs);
                statement.setString(9, sust);
                statement.setString(10, ndesig);
                statement.setString(11, numEval);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void crearResolEvaluacion(Resolucion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    public void crearResolEvaluacion(Resolucion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idEv = temp.getEvaluacion().getIdevaluacion();
        final String tipoResol = temp.getTipo();
        final String numResol = temp.getNumero();
        final Date fechaRes = temp.getFechaResol();
        final Date fechaNot = temp.getFechaNotificacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_CREAR_RESOL(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idEv);
                statement.setString(2, tipoResol);
                statement.setString(3, numResol);
                statement.setDate(4, (java.sql.Date) fechaRes);
                statement.setDate(5, (java.sql.Date) fechaNot);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void updateResolEvaluacion(Resolucion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    public void updateResolEvaluacion(Resolucion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idRes = temp.getIdresolucion();
        final Long idEv = temp.getEvaluacion().getIdevaluacion();
        final String tipoResol = temp.getTipo();
        final String numResol = temp.getNumero();
        final Date fechaRes = temp.getFechaResol();
        final Date fechaNot = temp.getFechaNotificacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_RESOL(?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idRes);
                statement.setLong(2, idEv);
                statement.setString(3, tipoResol);
                statement.setString(4, numResol);
                statement.setDate(5, (java.sql.Date) fechaRes);
                statement.setDate(6, (java.sql.Date) fechaNot);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public ArrayList<ExpedienteFamilia> listaInfoFamilias() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "from ExpedienteFamilia EF where (EF.estado = :estado) or (EF.estado = :estado2) or (EF.estado = :estado3)";
//        Query query = session.createQuery(hql);
//        query.setString("estado", "espera");
//        query.setString("estado2", "adopcion");
//        query.setString("estado3", "post");
//        List expedientes = query.list();
//        ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();
//        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
//            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
//            Hibernate.initialize(temp.getFamilia());
//            Hibernate.initialize(temp.getEvaluacions());
//            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
//            for (Evaluacion eval : temp.getEvaluacions()) {
//                if (eval.getTipo().equals("legal")) {
//                    String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
//                    Query query2 = session.createQuery(hql2);
//                    query2.setLong("idEvaluacion", eval.getIdevaluacion());
//                    query2.setString("tipo", "apto");
//                    query2.setMaxResults(1);
//                    Object queryResult = query2.uniqueResult();
//                    Resolucion tempResol = (Resolucion) queryResult;
//                    Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
//                    tempResoluciones.add(tempResol);
//                    eval.setResolucions(tempResoluciones);
//                    tempEvaluaciones.add(eval);
//
//                }
//
//            }
//            temp.setEvaluacions(tempEvaluaciones);
//            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
//            Query query2 = session.createQuery(hql2);
//            query2.setLong("id", temp.getFamilia().getIdfamilia());
//            query2.setMaxResults(1);
//            Object queryResult = query2.uniqueResult();
//            InfoFamilia tempIf = (InfoFamilia) queryResult;
//            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
//            tempInfFam.add(tempIf);
//            temp.getFamilia().setInfoFamilias(tempInfFam);
//            allInfoFam.add(temp);
//        }
//
//        return allInfoFam;
//
//    }
    public ArrayList<ExpedienteFamilia> listaExpInfoFam() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_LISTA_EXP_POR_ESTADOS(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Unidad TempUn = new Unidad();
                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();
                    Set<InfoFamilia> listaInf = new HashSet<InfoFamilia>();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    TempUn.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempEF.setNumero(rs.getLong("NUMERO"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    String hql2 = "{call HE_LISTAEVAL_BY_IDEXPFAM(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempEF.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(tempEF);
                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("legal")) {

                            String hql3 = "{call HE_ULTRESOL_LEGAL(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    String hql4 = "{call HE_GETINFOFAM_POR_IDFAM(?,?)}";
                    CallableStatement statement4 = connection.prepareCall(hql4);
                    statement4.setLong(1, tempFam.getIdfamilia());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();

                    ResultSet rs4 = (ResultSet) statement4.getObject(2);

                    if (rs4.next()) {
                        InfoFamilia tempInfo = new InfoFamilia();
                        tempInfo.setIdinfoFamilia(rs4.getLong("IDINFO_FAMILIA"));
                        tempInfo.setFamilia(tempFam);
                        tempInfo.setDepRes(rs4.getString("DEP_RES"));
                        tempInfo.setPaisRes(rs4.getString("PAIS_RES"));
                        tempInfo.setDomicilio(rs4.getString("DOMICILIO"));
                        tempInfo.setPropiedadVivienda(rs4.getString("PROPIEDAD_VIVIENDA"));
                        tempInfo.setTipoVivienda(rs4.getString("TIPO_VIVIENDA"));
                        tempInfo.setAreaVivTotal(rs4.getLong("AREA_VIV_TOTAL"));
                        tempInfo.setAreaVivConst(rs4.getLong("AREA_VIV_CONST"));
                        tempInfo.setDistVivienda(rs4.getString("DIST_VIVIENDA"));
                        tempInfo.setLuz(rs4.getShort("LUZ"));
                        tempInfo.setAgua(rs4.getShort("AGUA"));
                        tempInfo.setDesague(rs4.getShort("DESAGUE"));
                        tempInfo.setOtrosServ(rs4.getString("OTROS_SERV"));
                        tempInfo.setMaterConst(rs4.getString("MATER_CONST"));
                        tempInfo.setPared(rs4.getString("PARED"));
                        tempInfo.setTecho(rs4.getString("TECHO"));
                        tempInfo.setPiso(rs4.getString("PISO"));
                        String charValueStr = "";
                        if (rs4.getString("NIVEL_SOCIOECONOMICO") != null) {
                            charValueStr = rs4.getString("NIVEL_SOCIOECONOMICO");
                        }
                        if (!charValueStr.equals("") && charValueStr != null) {
                            tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                        }
                        tempInfo.setExpectativaEdadMin(rs4.getShort("EXPECTATIVA_EDAD_MIN"));
                        tempInfo.setExpectativaGenero(rs4.getString("EXPECTATIVA_GENERO"));
                        tempInfo.setOrigenHijos(rs4.getString("ORIGEN_HIJOS"));
                        tempInfo.setPuedeViajar(rs4.getShort("PUEDE_VIAJAR"));
                        tempInfo.setPredisposicionAp(rs4.getString("PREDISPOSICION_AP"));
                        tempInfo.setCondicion(rs4.getString("CONDICION"));
                        tempInfo.setAntecedenteFamilia(rs4.getString("ANTECEDENTE_FAMILIA"));
                        tempInfo.setFechaAntecedenteFamilia(rs4.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                        tempInfo.setObservaciones(rs4.getString("OBSERVACIONES"));
                        tempInfo.setNnaIncesto(rs4.getShort("NNA_INCESTO"));
                        tempInfo.setNnaMental(rs4.getShort("NNA_MENTAL"));
                        tempInfo.setNnaEpilepsia(rs4.getShort("NNA_EPILEPSIA"));
                        tempInfo.setNnaAbuso(rs4.getShort("NNA_ABUSO"));
                        tempInfo.setNnaSifilis(rs4.getShort("NNA_SIFILIS"));
                        tempInfo.setNnaSeguiMedico(rs4.getShort("NNA_SEGUI_MEDICO"));
                        tempInfo.setNnaOperacion(rs4.getShort("NNA_OPERACION"));
                        tempInfo.setNnaHiperactivo(rs4.getShort("NNA_HIPERACTIVO"));
                        tempInfo.setNnaEspecial(rs4.getShort("NNA_ESPECIAL"));
                        tempInfo.setNnaEnfermo(rs4.getShort("NNA_ENFERMO"));
                        tempInfo.setNnaMayor(rs4.getShort("NNA_MAYOR"));
                        tempInfo.setNnaAdolescente(rs4.getShort("NNA_ADOLESCENTE"));
                        tempInfo.setNnaHermano(rs4.getShort("NNA_HERMANO"));
                        tempInfo.setEstadoCivil(rs4.getString("ESTADO_CIVIL"));
                        tempInfo.setFechaMatrimonio(rs4.getDate("FECHA_MATRIMONIO"));
                        tempInfo.setTelefono(rs4.getString("TELEFONO"));
                        tempInfo.setExpectativaEdadMax(rs4.getShort("EXPECTATIVA_EDAD_MAX"));
                        tempInfo.setnHijos(rs4.getShort("NHIJOS"));
                        listaInf.add(tempInfo);
                    }
                    rs4.close();
                    statement4.close();

                    Set<Designacion> listaDesig = new HashSet<Designacion>();
                    String hql5 = "{call REPORTE_ULTDESIG(?,?)}";
                    CallableStatement statement5 = connection.prepareCall(hql5);
                    statement5.setLong(1, tempEF.getIdexpedienteFamilia());
                    statement5.registerOutParameter(2, OracleTypes.CURSOR);
                    statement5.execute();

                    ResultSet rs5 = (ResultSet) statement5.getObject(2);
                    
                    if (rs5.next()) {
                        Designacion tempDesig = new Designacion();
                        tempDesig.setIddesignacion(rs5.getLong("IDDESIGNACION"));
                        tempDesig.setNDesignacion(rs5.getString("N_DESIGNACION"));
                        tempDesig.setPrioridad(rs5.getLong("PRIORIDAD"));
                        tempDesig.setFechaPropuesta(rs5.getDate("FECHA_PROPUESTA"));
                        tempDesig.setFechaConsejo(rs5.getDate("FECHA_CONSEJO"));
                        tempDesig.setAceptacionConsejo(rs5.getShort("ACEPTACION_CONSEJO"));
                        tempDesig.setTipoPropuesta(rs5.getString("TIPO_PROPUESTA"));
                        listaDesig.add(tempDesig);
                        
                    }
                    rs5.close();
                    statement5.close();

                    tempFam.setInfoFamilias(listaInf);

                    tempEF.setDesignacions(listaDesig);
                    tempEF.setFamilia(tempFam);
                    tempEF.setUnidad(TempUn);
                    tempEF.setEvaluacions(listaEv);

                    allInfoFam.add(tempEF);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allInfoFam;

    }

//    public ArrayList<ExpedienteFamilia> listaInfoFamilias(String exp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "from ExpedienteFamilia EF where (EF.estado = :estado) or (EF.estado = :estado2) or (EF.estado = :estado3) and EF.expediente like :exp";
//        Query query = session.createQuery(hql);
//        query.setString("estado", "espera");
//        query.setString("estado2", "adopcion");
//        query.setString("estado3", "estudio");
//        query.setString("exp", '%' + exp + '%');
//        List expedientes = query.list();
//        ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();
//        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
//            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
//            Hibernate.initialize(temp.getFamilia());
//            Hibernate.initialize(temp.getEvaluacions());
//            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
//            for (Evaluacion eval : temp.getEvaluacions()) {
//                if (eval.getTipo().equals("legal")) {
//                    String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
//                    Query query2 = session.createQuery(hql2);
//                    query2.setLong("idEvaluacion", eval.getIdevaluacion());
//                    query2.setString("tipo", "apto");
//                    query2.setMaxResults(1);
//                    Object queryResult = query2.uniqueResult();
//                    Resolucion tempResol = (Resolucion) queryResult;
//                    Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
//                    tempResoluciones.add(tempResol);
//                    eval.setResolucions(tempResoluciones);
//                    tempEvaluaciones.add(eval);
//
//                }
//
//            }
//            temp.setEvaluacions(tempEvaluaciones);
//            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
//            Query query2 = session.createQuery(hql2);
//            query2.setLong("id", temp.getFamilia().getIdfamilia());
//            query2.setMaxResults(1);
//            Object queryResult = query2.uniqueResult();
//            InfoFamilia tempIf = (InfoFamilia) queryResult;
//            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
//            tempInfFam.add(tempIf);
//            temp.getFamilia().setInfoFamilias(tempInfFam);
//            allInfoFam.add(temp);
//        }
//
//        return allInfoFam;
//
//    }
    public ArrayList<ExpedienteFamilia> listaInfoFamilias(String exp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String BuscarExp = exp;
        final ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_BUSCAR_EXP(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, BuscarExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Unidad TempUn = new Unidad();
                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();
                    Set<InfoFamilia> listaInf = new HashSet<InfoFamilia>();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    TempUn.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempEF.setNumero(rs.getLong("NUMERO"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    String hql2 = "{call HE_LISTAEVAL_BY_IDEXPFAM(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempEF.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(tempEF);
                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("legal")) {

                            String hql3 = "{call HE_ULTRESOL_LEGAL(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    String hql4 = "{call HE_GETINFOFAM_POR_IDFAM(?,?)}";
                    CallableStatement statement4 = connection.prepareCall(hql4);
                    statement4.setLong(1, tempFam.getIdfamilia());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();

                    ResultSet rs4 = (ResultSet) statement4.getObject(2);

                    if (rs4.next()) {
                        InfoFamilia tempInfo = new InfoFamilia();
                        tempInfo.setIdinfoFamilia(rs4.getLong("IDINFO_FAMILIA"));
                        tempInfo.setFamilia(tempFam);
                        tempInfo.setDepRes(rs4.getString("DEP_RES"));
                        tempInfo.setPaisRes(rs4.getString("PAIS_RES"));
                        tempInfo.setDomicilio(rs4.getString("DOMICILIO"));
                        tempInfo.setPropiedadVivienda(rs4.getString("PROPIEDAD_VIVIENDA"));
                        tempInfo.setTipoVivienda(rs4.getString("TIPO_VIVIENDA"));
                        tempInfo.setAreaVivTotal(rs4.getLong("AREA_VIV_TOTAL"));
                        tempInfo.setAreaVivConst(rs4.getLong("AREA_VIV_CONST"));
                        tempInfo.setDistVivienda(rs4.getString("DIST_VIVIENDA"));
                        tempInfo.setLuz(rs4.getShort("LUZ"));
                        tempInfo.setAgua(rs4.getShort("AGUA"));
                        tempInfo.setDesague(rs4.getShort("DESAGUE"));
                        tempInfo.setOtrosServ(rs4.getString("OTROS_SERV"));
                        tempInfo.setMaterConst(rs4.getString("MATER_CONST"));
                        tempInfo.setPared(rs4.getString("PARED"));
                        tempInfo.setTecho(rs4.getString("TECHO"));
                        tempInfo.setPiso(rs4.getString("PISO"));
                        String charValueStr = "";
                        if (rs4.getString("NIVEL_SOCIOECONOMICO") != null) {
                            charValueStr = rs4.getString("NIVEL_SOCIOECONOMICO");
                        }
                        if (!charValueStr.equals("") && charValueStr != null) {
                            tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                        }
                        tempInfo.setExpectativaEdadMin(rs4.getShort("EXPECTATIVA_EDAD_MIN"));
                        tempInfo.setExpectativaGenero(rs4.getString("EXPECTATIVA_GENERO"));
                        tempInfo.setOrigenHijos(rs4.getString("ORIGEN_HIJOS"));
                        tempInfo.setPuedeViajar(rs4.getShort("PUEDE_VIAJAR"));
                        tempInfo.setPredisposicionAp(rs4.getString("PREDISPOSICION_AP"));
                        tempInfo.setCondicion(rs4.getString("CONDICION"));
                        tempInfo.setAntecedenteFamilia(rs4.getString("ANTECEDENTE_FAMILIA"));
                        tempInfo.setFechaAntecedenteFamilia(rs4.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                        tempInfo.setObservaciones(rs4.getString("OBSERVACIONES"));
                        tempInfo.setNnaIncesto(rs4.getShort("NNA_INCESTO"));
                        tempInfo.setNnaMental(rs4.getShort("NNA_MENTAL"));
                        tempInfo.setNnaEpilepsia(rs4.getShort("NNA_EPILEPSIA"));
                        tempInfo.setNnaAbuso(rs4.getShort("NNA_ABUSO"));
                        tempInfo.setNnaSifilis(rs4.getShort("NNA_SIFILIS"));
                        tempInfo.setNnaSeguiMedico(rs4.getShort("NNA_SEGUI_MEDICO"));
                        tempInfo.setNnaOperacion(rs4.getShort("NNA_OPERACION"));
                        tempInfo.setNnaHiperactivo(rs4.getShort("NNA_HIPERACTIVO"));
                        tempInfo.setNnaEspecial(rs4.getShort("NNA_ESPECIAL"));
                        tempInfo.setNnaEnfermo(rs4.getShort("NNA_ENFERMO"));
                        tempInfo.setNnaMayor(rs4.getShort("NNA_MAYOR"));
                        tempInfo.setNnaAdolescente(rs4.getShort("NNA_ADOLESCENTE"));
                        tempInfo.setNnaHermano(rs4.getShort("NNA_HERMANO"));
                        tempInfo.setEstadoCivil(rs4.getString("ESTADO_CIVIL"));
                        tempInfo.setFechaMatrimonio(rs4.getDate("FECHA_MATRIMONIO"));
                        tempInfo.setTelefono(rs4.getString("TELEFONO"));
                        tempInfo.setExpectativaEdadMax(rs4.getShort("EXPECTATIVA_EDAD_MAX"));
                        tempInfo.setnHijos(rs4.getShort("NHIJOS"));
                        listaInf.add(tempInfo);
                    }
                    rs4.close();
                    statement4.close();
                    Entidad tempEnt = new Entidad();
                    String hql5 = "{call HE_GETENTIDAD_FAMILIA(?,?)}";
                    CallableStatement statement5 = connection.prepareCall(hql5);
                    statement5.setLong(1, tempFam.getIdfamilia());
                    statement5.registerOutParameter(2, OracleTypes.CURSOR);
                    statement5.execute();

                    ResultSet rs5 = (ResultSet) statement5.getObject(2);

                    if (rs5.next()) {
                        tempEnt.setIdentidad(rs5.getLong("IDENTIDAD"));
                        tempEnt.setNombre(rs5.getString("NOMBRE"));
                    }
                    rs5.close();
                    statement5.close();

                    Set<Designacion> listaDesig = new HashSet<Designacion>();
                    String hql6 = "{call REPORTE_ULTDESIG(?,?)}";
                    CallableStatement statement6 = connection.prepareCall(hql6);
                    statement6.setLong(1, tempEF.getIdexpedienteFamilia());
                    statement6.registerOutParameter(2, OracleTypes.CURSOR);
                    statement6.execute();

                    ResultSet rs6 = (ResultSet) statement6.getObject(2);

                    if (rs6.next()) {
                        Designacion tempDesig = new Designacion();
                        tempDesig.setIddesignacion(rs6.getLong("IDDESIGNACION"));
                        tempDesig.setNDesignacion(rs6.getString("N_DESIGNACION"));
                        tempDesig.setPrioridad(rs6.getLong("PRIORIDAD"));
                        tempDesig.setFechaPropuesta(rs6.getDate("FECHA_PROPUESTA"));
                        tempDesig.setFechaConsejo(rs6.getDate("FECHA_CONSEJO"));
                        tempDesig.setAceptacionConsejo(rs6.getShort("ACEPTACION_CONSEJO"));
                        tempDesig.setTipoPropuesta(rs6.getString("TIPO_PROPUESTA"));
                        listaDesig.add(tempDesig);

                    }
                    rs6.close();
                    statement6.close();

                    tempFam.setEntidad(tempEnt);
                    tempFam.setInfoFamilias(listaInf);

                    tempEF.setDesignacions(listaDesig);
                    tempEF.setFamilia(tempFam);
                    tempEF.setUnidad(TempUn);
                    tempEF.setEvaluacions(listaEv);

                    allInfoFam.add(tempEF);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allInfoFam;

    }

//    public ExpedienteFamilia getInfoFamilia(long id) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        ExpedienteFamilia expFamilia = new ExpedienteFamilia();
//        String hqlA = "FROM ExpedienteFamilia EF WHERE EF.id = :id";
//
//        Query queryA = session.createQuery(hqlA);
//        queryA.setLong("id", id);
//        Object queryResultA = queryA.uniqueResult();
//
//        expFamilia = (ExpedienteFamilia) queryResultA;
//        Hibernate.initialize(expFamilia.getFamilia());
//        Hibernate.initialize(expFamilia.getEvaluacions());
//        Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
//        for (Evaluacion eval : expFamilia.getEvaluacions()) {
//            if (eval.getTipo().equals("legal")) {
//                String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
//                Query query2 = session.createQuery(hql2);
//                query2.setLong("idEvaluacion", eval.getIdevaluacion());
//                query2.setString("tipo", "apto");
//                query2.setMaxResults(1);
//                Object queryResult = query2.uniqueResult();
//                Resolucion tempResol = (Resolucion) queryResult;
//                Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
//                tempResoluciones.add(tempResol);
//                eval.setResolucions(tempResoluciones);
//                tempEvaluaciones.add(eval);
//
//            }
//
//        }
//        expFamilia.setEvaluacions(tempEvaluaciones);
//        String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
//        Query query2 = session.createQuery(hql2);
//        query2.setLong("id", expFamilia.getFamilia().getIdfamilia());
//        query2.setMaxResults(1);
//        Object queryResult = query2.uniqueResult();
//        InfoFamilia tempIf = (InfoFamilia) queryResult;
//        Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
//        tempInfFam.add(tempIf);
//        expFamilia.getFamilia().setInfoFamilias(tempInfFam);
//
//        return expFamilia;
//
//    }
    public ExpedienteFamilia getInfoFamilia(long id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idExp = id;
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_GET_EXPEDIENTE_FAMILIA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    //ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Unidad TempUn = new Unidad();
                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();
                    Set<InfoFamilia> listaInf = new HashSet<InfoFamilia>();

                    expFamilia.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    TempUn.setIdunidad(rs.getLong("IDUNIDAD"));
                    expFamilia.setNumero(rs.getLong("NUMERO"));
                    expFamilia.setExpediente(rs.getString("EXPEDIENTE"));
                    expFamilia.setHt(rs.getString("HT"));
                    expFamilia.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    expFamilia.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    expFamilia.setEstado(rs.getString("ESTADO"));
                    expFamilia.setTupa(rs.getDate("TUPA"));
                    expFamilia.setNacionalidad(rs.getString("NACIONALIDAD"));
                    expFamilia.setRnsa(rs.getShort("RNSA"));
                    expFamilia.setRnaa(rs.getShort("RNAA"));
                    expFamilia.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    expFamilia.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    expFamilia.setHtFicha(rs.getString("HTFICHA"));
                    expFamilia.setnFicha(rs.getString("NFICHA"));
                    expFamilia.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    String hql2 = "{call HE_LISTAEVAL_BY_IDEXPFAM(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, expFamilia.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(expFamilia);
                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("legal")) {

                            String hql3 = "{call HE_ULTRESOL_LEGAL(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    String hql4 = "{call HE_GETINFOFAM_POR_IDFAM(?,?)}";
                    CallableStatement statement4 = connection.prepareCall(hql4);
                    statement4.setLong(1, tempFam.getIdfamilia());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();

                    ResultSet rs4 = (ResultSet) statement4.getObject(2);

                    if (rs4.next()) {
                        InfoFamilia tempInfo = new InfoFamilia();
                        tempInfo.setIdinfoFamilia(rs4.getLong("IDINFO_FAMILIA"));
                        tempInfo.setFamilia(tempFam);
                        tempInfo.setDepRes(rs4.getString("DEP_RES"));
                        tempInfo.setPaisRes(rs4.getString("PAIS_RES"));
                        tempInfo.setDomicilio(rs4.getString("DOMICILIO"));
                        tempInfo.setPropiedadVivienda(rs4.getString("PROPIEDAD_VIVIENDA"));
                        tempInfo.setTipoVivienda(rs4.getString("TIPO_VIVIENDA"));
                        tempInfo.setAreaVivTotal(rs4.getLong("AREA_VIV_TOTAL"));
                        tempInfo.setAreaVivConst(rs4.getLong("AREA_VIV_CONST"));
                        tempInfo.setDistVivienda(rs4.getString("DIST_VIVIENDA"));
                        tempInfo.setLuz(rs4.getShort("LUZ"));
                        tempInfo.setAgua(rs4.getShort("AGUA"));
                        tempInfo.setDesague(rs4.getShort("DESAGUE"));
                        tempInfo.setOtrosServ(rs4.getString("OTROS_SERV"));
                        tempInfo.setMaterConst(rs4.getString("MATER_CONST"));
                        tempInfo.setPared(rs4.getString("PARED"));
                        tempInfo.setTecho(rs4.getString("TECHO"));
                        tempInfo.setPiso(rs4.getString("PISO"));
                        String charValueStr = "";
                        if (rs4.getString("NIVEL_SOCIOECONOMICO") != null) {
                            charValueStr = rs4.getString("NIVEL_SOCIOECONOMICO");
                        }
                        if (!charValueStr.equals("") && charValueStr != null) {
                            tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                        }
                        tempInfo.setExpectativaEdadMin(rs4.getShort("EXPECTATIVA_EDAD_MIN"));
                        tempInfo.setExpectativaGenero(rs4.getString("EXPECTATIVA_GENERO"));
                        tempInfo.setOrigenHijos(rs4.getString("ORIGEN_HIJOS"));
                        tempInfo.setPuedeViajar(rs4.getShort("PUEDE_VIAJAR"));
                        tempInfo.setPredisposicionAp(rs4.getString("PREDISPOSICION_AP"));
                        tempInfo.setCondicion(rs4.getString("CONDICION"));
                        tempInfo.setAntecedenteFamilia(rs4.getString("ANTECEDENTE_FAMILIA"));
                        tempInfo.setFechaAntecedenteFamilia(rs4.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                        tempInfo.setObservaciones(rs4.getString("OBSERVACIONES"));
                        tempInfo.setNnaIncesto(rs4.getShort("NNA_INCESTO"));
                        tempInfo.setNnaMental(rs4.getShort("NNA_MENTAL"));
                        tempInfo.setNnaEpilepsia(rs4.getShort("NNA_EPILEPSIA"));
                        tempInfo.setNnaAbuso(rs4.getShort("NNA_ABUSO"));
                        tempInfo.setNnaSifilis(rs4.getShort("NNA_SIFILIS"));
                        tempInfo.setNnaSeguiMedico(rs4.getShort("NNA_SEGUI_MEDICO"));
                        tempInfo.setNnaOperacion(rs4.getShort("NNA_OPERACION"));
                        tempInfo.setNnaHiperactivo(rs4.getShort("NNA_HIPERACTIVO"));
                        tempInfo.setNnaEspecial(rs4.getShort("NNA_ESPECIAL"));
                        tempInfo.setNnaEnfermo(rs4.getShort("NNA_ENFERMO"));
                        tempInfo.setNnaMayor(rs4.getShort("NNA_MAYOR"));
                        tempInfo.setNnaAdolescente(rs4.getShort("NNA_ADOLESCENTE"));
                        tempInfo.setNnaHermano(rs4.getShort("NNA_HERMANO"));
                        tempInfo.setEstadoCivil(rs4.getString("ESTADO_CIVIL"));
                        tempInfo.setFechaMatrimonio(rs4.getDate("FECHA_MATRIMONIO"));
                        tempInfo.setTelefono(rs4.getString("TELEFONO"));
                        tempInfo.setExpectativaEdadMax(rs4.getShort("EXPECTATIVA_EDAD_MAX"));
                        tempInfo.setnHijos(rs4.getShort("NHIJOS"));
                        listaInf.add(tempInfo);
                    }
                    rs4.close();
                    statement4.close();

                    Entidad tempEnt = new Entidad();
                    String hql5 = "{call HE_GETENTIDAD_FAMILIA(?,?)}";
                    CallableStatement statement5 = connection.prepareCall(hql5);
                    statement5.setLong(1, tempFam.getIdfamilia());
                    statement5.registerOutParameter(2, OracleTypes.CURSOR);
                    statement5.execute();

                    ResultSet rs5 = (ResultSet) statement5.getObject(2);

                    if (rs5.next()) {
                        tempEnt.setIdentidad(rs5.getLong("IDENTIDAD"));
                        tempEnt.setNombre(rs5.getString("NOMBRE"));
                    }
                    rs5.close();
                    statement5.close();

                    Set<Designacion> listaDesig = new HashSet<Designacion>();
                    String hql6 = "{call REPORTE_ULTDESIG(?,?)}";
                    CallableStatement statement6 = connection.prepareCall(hql6);
                    statement6.setLong(1, expFamilia.getIdexpedienteFamilia());
                    statement6.registerOutParameter(2, OracleTypes.CURSOR);
                    statement6.execute();

                    ResultSet rs6 = (ResultSet) statement6.getObject(2);

                    if (rs6.next()) {
                        Designacion tempDesig = new Designacion();
                        tempDesig.setIddesignacion(rs6.getLong("IDDESIGNACION"));
                        tempDesig.setNDesignacion(rs6.getString("N_DESIGNACION"));
                        tempDesig.setPrioridad(rs6.getLong("PRIORIDAD"));
                        tempDesig.setFechaPropuesta(rs6.getDate("FECHA_PROPUESTA"));
                        tempDesig.setFechaConsejo(rs6.getDate("FECHA_CONSEJO"));
                        tempDesig.setAceptacionConsejo(rs6.getShort("ACEPTACION_CONSEJO"));
                        tempDesig.setTipoPropuesta(rs6.getString("TIPO_PROPUESTA"));
                        listaDesig.add(tempDesig);

                    }
                    rs6.close();
                    statement6.close();

                    tempFam.setEntidad(tempEnt);

                    tempFam.setInfoFamilias(listaInf);
                    expFamilia.setDesignacions(listaDesig);
                    expFamilia.setFamilia(tempFam);
                    expFamilia.setUnidad(TempUn);
                    expFamilia.setEvaluacions(listaEv);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return expFamilia;

    }

    /*  public ArrayList<Designacion> getListaDesignaciones() {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<Designacion> allDesig = new ArrayList();
     String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo ORDER BY D.fechaPropuesta DESC";
     Query query = session.createQuery(hql);
     query.setShort("aceptacionConsejo", Short.parseShort("1"));
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     Designacion temp = (Designacion) iter.next();
     Hibernate.initialize(temp.getExpedienteFamilia());
     Hibernate.initialize(temp.getNna());
     allDesig.add(temp);
     }
     return allDesig;
     } */
    public ArrayList<Designacion> getListaDesignaciones() {

        Session session = sessionFactory.getCurrentSession();
        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_DESIGNACIONES_SIN(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp_designacion = (ResultSet) statement.getObject(1);
                while (temp_designacion.next()) {
                    Designacion designacion = new Designacion();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Personal personal = new Personal();
                    Nna nna = new Nna();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();
                    Juzgado juzgado = new Juzgado();
                    Car car = new Car();

                    designacion.setIddesignacion(temp_designacion.getShort(1));
                    if (temp_designacion.getShort(2) != 0) {

                        String hql_designacion_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_designacion_expFamilia = connection.prepareCall(hql_designacion_expFamilia);
                        statement_designacion_expFamilia.setLong(1, temp_designacion.getShort(2));
                        statement_designacion_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_designacion_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getShort(1));

                            if (temp_expediente.getShort(2) != 0) {

                                String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement2 = connection.prepareCall(hql2);
                                statement2.setLong(1, temp_expediente.getShort(2));
                                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                                statement2.execute();
                                temp2 = (ResultSet) statement2.getObject(2);
                                while (temp2.next()) {
                                    fam.setIdfamilia(temp2.getShort(1));
                                    expFamilia.setFamilia(fam);
                                }
                                temp2.close();
                                statement2.close();
                            }
                            if (temp_expediente.getShort(3) != 0) {

                                String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement3 = connection.prepareCall(hql3);
                                statement3.setLong(1, temp_expediente.getShort(3));
                                statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                statement3.execute();
                                temp2 = (ResultSet) statement3.getObject(2);
                                while (temp2.next()) {
                                    unidad.setIdunidad(temp2.getShort(1));
                                    unidad.setDepartamento(temp2.getString("DEPARTAMENTO"));
                                    expFamilia.setUnidad(unidad);
                                }
                                temp2.close();
                                statement3.close();
                            }
                            expFamilia.setNumero(temp_expediente.getLong(4));
                            expFamilia.setExpediente(temp_expediente.getString(5));
                            expFamilia.setHt(temp_expediente.getString(6));
                            expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                            expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                            expFamilia.setEstado(temp_expediente.getString(9));
                            expFamilia.setTupa(temp_expediente.getDate(10));
                            expFamilia.setNacionalidad(temp_expediente.getString(11));
                            expFamilia.setRnsa(temp_expediente.getShort(12));
                            expFamilia.setRnaa(temp_expediente.getShort(13));
                            expFamilia.setTipoFamilia(temp_expediente.getString(14));
                            expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                            expFamilia.setHtFicha(temp_expediente.getString(16));
                            expFamilia.setnFicha(temp_expediente.getString(17));
                            expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                            designacion.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_designacion_expFamilia.close();
                    }
                    if (temp_designacion.getShort(3) != 0) {

                        String hql_designacion_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_designacion_NNA = connection.prepareCall(hql_designacion_NNA);
                        statement_designacion_NNA.setLong(1, temp_designacion.getShort(3));
                        statement_designacion_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_NNA.execute();
                        temp_nna = (ResultSet) statement_designacion_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));

                            if (temp_nna.getShort(2) != 0) {

                                String hql_designacion_NNA_Juzgado = "{call HE_GET_JUZGADO(?, ?)}";
                                CallableStatement statement_designacion_NNA_Juzgado = connection.prepareCall(hql_designacion_NNA_Juzgado);
                                statement_designacion_NNA_Juzgado.setLong(1, temp_nna.getShort(2));
                                statement_designacion_NNA_Juzgado.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_NNA_Juzgado.execute();
                                temp_juzgado = (ResultSet) statement_designacion_NNA_Juzgado.getObject(2);
                                while (temp_juzgado.next()) {
                                    juzgado.setIdjuzgado(temp_juzgado.getShort(1));

                                    nna.setJuzgado(juzgado);
                                }
                                temp_juzgado.close();
                                statement_designacion_NNA_Juzgado.close();
                            }

                            if (temp_nna.getShort(3) != 0) {

                                String hql_designacion_NNA_CAR = "{call HE_GET_CAR(?, ?)}";
                                CallableStatement statement_designacion_NNA_CAR = connection.prepareCall(hql_designacion_NNA_CAR);
                                statement_designacion_NNA_CAR.setLong(1, temp_nna.getShort(3));
                                statement_designacion_NNA_CAR.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_NNA_CAR.execute();
                                temp_car = (ResultSet) statement_designacion_NNA_CAR.getObject(2);
                                while (temp_car.next()) {
                                    car.setIdcar(temp_car.getShort(1));

                                    nna.setCar(car);
                                }
                                temp_car.close();
                                statement_designacion_NNA_CAR.close();
                            }

                            nna.setNombre(temp_nna.getString(4));
                            nna.setApellidoP(temp_nna.getString(5));
                            nna.setApellidoM(temp_nna.getString(6));
                            nna.setSexo(temp_nna.getString(7));
                            nna.setFechaNacimiento(temp_nna.getDate(8));
                            nna.setEdadAnhos(temp_nna.getShort(9));
                            nna.setEdadMeses(temp_nna.getShort(10));
                            nna.setActaNacimiento(temp_nna.getShort(11));
                            nna.setCondicionSalud(temp_nna.getString(12));
                            nna.setDepartamentoNacimiento(temp_nna.getString(13));
                            nna.setProvinciaNacimiento(temp_nna.getString(14));
                            nna.setDistritoNacimiento(temp_nna.getString(15));
                            nna.setPaisNacimiento(temp_nna.getString(16));
                            nna.setLugarNac(temp_nna.getString(17));
                            nna.setFechaResolAbandono(temp_nna.getDate(18));
                            nna.setFechaResolConsentida(temp_nna.getDate(19));
                            nna.setClasificacion(temp_nna.getString(20));
                            nna.setIncesto(temp_nna.getShort(21));
                            nna.setMental(temp_nna.getShort(22));
                            nna.setEpilepsia(temp_nna.getShort(23));
                            nna.setAbuso(temp_nna.getShort(24));
                            nna.setSifilis(temp_nna.getShort(25));
                            nna.setSeguiMedico(temp_nna.getShort(26));
                            nna.setOperacion(temp_nna.getShort(27));
                            nna.setHiperactivo(temp_nna.getShort(28));
                            nna.setEspecial(temp_nna.getShort(29));
                            nna.setEnfermo(temp_nna.getShort(30));
                            nna.setMayor(temp_nna.getShort(31));
                            nna.setAdolescente(temp_nna.getShort(32));
                            nna.setHermano(temp_nna.getShort(33));
                            nna.setNn(temp_nna.getShort(34));
                            nna.setObservaciones(temp_nna.getString(35));
                            nna.setNResolAband(temp_nna.getString(36));
                            nna.setNResolCons(temp_nna.getString(37));

                            designacion.setNna(nna);
                        }
                        temp_nna.close();
                        statement_designacion_NNA.close();
                    }
                    if (temp_designacion.getShort(4) != 0) {

                        String hql_designacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_designacion_personal = connection.prepareCall(hql_designacion_personal);
                        statement_designacion_personal.setLong(1, temp_designacion.getShort(4));
                        statement_designacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_personal.execute();
                        temp_personal = (ResultSet) statement_designacion_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));
                            designacion.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_designacion_personal.close();
                    }
                    designacion.setNDesignacion(temp_designacion.getString(5));
                    designacion.setPrioridad(temp_designacion.getLong(6));
                    designacion.setFechaPropuesta(temp_designacion.getDate(7));
                    designacion.setFechaConsejo(temp_designacion.getDate(8));
                    designacion.setAceptacionConsejo(temp_designacion.getShort(9));
                    designacion.setTipoPropuesta(temp_designacion.getString(10));
                    designacion.setObs(temp_designacion.getString(11));

                    allDesig.add(designacion);

                }
                temp_designacion.close();
                statement.close();
            }
        };

        session.doWork(work);
        return allDesig;
    }

    /* public ArrayList<Designacion> getListaDesignacionesDeFamilia(long idExpFam) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<Designacion> allDesig = new ArrayList();
     String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.expedienteFamilia = :id";
     Query query = session.createQuery(hql);
     query.setShort("aceptacionConsejo", Short.parseShort("0"));
     query.setLong("id", idExpFam);
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     Designacion temp = (Designacion) iter.next();
     allDesig.add(temp);
     }
     return allDesig;
     }*/
    public ArrayList<Designacion> getListaDesignacionesDeFamilia(final long idExpFam) {

        Session session = sessionFactory.getCurrentSession();

        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_DESIGNACIONES_FAM(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExpFam);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp_designacion = (ResultSet) statement.getObject(2);
                while (temp_designacion.next()) {
                    Designacion designacion = new Designacion();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Personal personal = new Personal();
                    Nna nna = new Nna();

                    designacion.setIddesignacion(temp_designacion.getShort(1));
                    if (temp_designacion.getShort(2) != 0) {

                        String hql_designacion_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_designacion_expFamilia = connection.prepareCall(hql_designacion_expFamilia);
                        statement_designacion_expFamilia.setLong(1, temp_designacion.getShort(2));
                        statement_designacion_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_designacion_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getShort(1));

                            designacion.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_designacion_expFamilia.close();
                    }
                    if (temp_designacion.getShort(3) != 0) {

                        String hql_designacion_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_designacion_NNA = connection.prepareCall(hql_designacion_NNA);
                        statement_designacion_NNA.setLong(1, temp_designacion.getShort(3));
                        statement_designacion_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_NNA.execute();
                        temp_nna = (ResultSet) statement_designacion_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));
                            designacion.setNna(nna);
                        }
                        temp_nna.close();
                        statement_designacion_NNA.close();
                    }
                    if (temp_designacion.getShort(4) != 0) {

                        String hql_designacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_designacion_personal = connection.prepareCall(hql_designacion_personal);
                        statement_designacion_personal.setLong(1, temp_designacion.getShort(4));
                        statement_designacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_personal.execute();
                        temp_personal = (ResultSet) statement_designacion_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));
                            designacion.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_designacion_personal.close();
                    }
                    designacion.setNDesignacion(temp_designacion.getString(5));
                    designacion.setPrioridad(temp_designacion.getLong(6));
                    designacion.setFechaPropuesta(temp_designacion.getDate(7));
                    designacion.setFechaConsejo(temp_designacion.getDate(8));
                    designacion.setAceptacionConsejo(temp_designacion.getShort(9));
                    designacion.setTipoPropuesta(temp_designacion.getString(10));
                    designacion.setObs(temp_designacion.getString(11));

                    allDesig.add(designacion);

                }
                temp_designacion.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allDesig;
    }


    /*  public Designacion getDesignacion(long idExpFamilia, long idNna) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();

     Designacion tempDesig = new Designacion();

     String hqlA = "FROM Designacion D WHERE D.expedienteFamilia = :idExpFamilia and D.nna = :idNna";
     Query queryA = session.createQuery(hqlA);
     queryA.setLong("idExpFamilia", idExpFamilia);
     queryA.setLong("idNna", idNna);
     Object queryResultA = queryA.uniqueResult();

     tempDesig = (Designacion) queryResultA;
     return tempDesig;

     }*/
    public Designacion getDesignacion(final long idExpFamilia, final long idNna) {

        Session session = sessionFactory.getCurrentSession();

        final Designacion designacion = new Designacion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Personal personal = new Personal();
        final Nna nna = new Nna();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_DESIGNACION(?, ?, ?)}";
                CallableStatement statement_designacion = connection.prepareCall(hql);
                statement_designacion.setLong(1, idExpFamilia);
                statement_designacion.setLong(2, idNna);
                statement_designacion.registerOutParameter(3, OracleTypes.CURSOR);
                statement_designacion.execute();

                temp_designacion = (ResultSet) statement_designacion.getObject(3);
                while (temp_designacion.next()) {
                    designacion.setIddesignacion(temp_designacion.getShort(1));
                    if (temp_designacion.getShort(2) != 0) {

                        String hql_designacion_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_designacion_expFamilia = connection.prepareCall(hql_designacion_expFamilia);
                        statement_designacion_expFamilia.setLong(1, temp_designacion.getShort(2));
                        statement_designacion_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_designacion_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getShort(1));

                            designacion.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_designacion_expFamilia.close();
                    }
                    if (temp_designacion.getShort(3) != 0) {

                        String hql_designacion_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_designacion_NNA = connection.prepareCall(hql_designacion_NNA);
                        statement_designacion_NNA.setLong(1, temp_designacion.getShort(3));
                        statement_designacion_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_NNA.execute();
                        temp_nna = (ResultSet) statement_designacion_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));
                            designacion.setNna(nna);
                        }
                        temp_nna.close();
                        statement_designacion_NNA.close();
                    }
                    if (temp_designacion.getShort(4) != 0) {

                        String hql_designacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_designacion_personal = connection.prepareCall(hql_designacion_personal);
                        statement_designacion_personal.setLong(1, temp_designacion.getShort(4));
                        statement_designacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_personal.execute();
                        temp_personal = (ResultSet) statement_designacion_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));
                            designacion.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_designacion_personal.close();
                    }
                    designacion.setNDesignacion(temp_designacion.getString(5));
                    designacion.setPrioridad(temp_designacion.getLong(6));
                    designacion.setFechaPropuesta(temp_designacion.getDate(7));
                    designacion.setFechaConsejo(temp_designacion.getDate(8));
                    designacion.setAceptacionConsejo(temp_designacion.getShort(9));
                    designacion.setTipoPropuesta(temp_designacion.getString(10));
                    designacion.setObs(temp_designacion.getString(11));
                }
                temp_designacion.close();
                statement_designacion.close();
            }
        };
        session.doWork(work);
        return designacion;

    }

    /*    public ArrayList<EstudioCaso> getListaEstudioCaso(long idNna) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
     String hql = "FROM EstudioCaso EC WHERE EC.nna = :id";
     Query query = session.createQuery(hql);
     query.setLong("id", idNna);
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     EstudioCaso temp = (EstudioCaso) iter.next();
     allEstudioCaso.add(temp);
     }
     return allEstudioCaso;
     }*/
    public ArrayList<EstudioCaso> getListaEstudioCaso(long idNna) {

        Session session = sessionFactory.getCurrentSession();

        final Long idN = idNna;
        final ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_ESTUDIOCASO(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp_estudio = (ResultSet) statement.getObject(2);
                while (temp_estudio.next()) {
                    EstudioCaso estudio = new EstudioCaso();
                    Nna nna = new Nna();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();

                    estudio.setIdestudioCaso(temp_estudio.getShort(1));
                    if (temp_estudio.getShort(2) != 0) {

                        String hql_estudio_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_estudio_NNA = connection.prepareCall(hql_estudio_NNA);
                        statement_estudio_NNA.setLong(1, temp_estudio.getShort(2));
                        statement_estudio_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_NNA.execute();
                        temp_nna = (ResultSet) statement_estudio_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));
                            estudio.setNna(nna);
                        }
                        temp_nna.close();
                        statement_estudio_NNA.close();
                    }
                    if (temp_estudio.getShort(3) != 0) {

                        String hql_estudio_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_estudio_expFamilia = connection.prepareCall(hql_estudio_expFamilia);
                        statement_estudio_expFamilia.setLong(1, temp_estudio.getShort(3));
                        statement_estudio_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_estudio_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getShort(1));

                            estudio.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_estudio_expFamilia.close();
                    }

                    estudio.setOrden(temp_estudio.getString(4));
                    estudio.setFechaEstudio(temp_estudio.getDate(5));
                    estudio.setFechaSolAdop(temp_estudio.getDate(6));
                    estudio.setResultado(temp_estudio.getString(7));
                    estudio.setPrioridad(temp_estudio.getLong(8));
                    estudio.setNSolicitud(temp_estudio.getLong(9));

                    allEstudioCaso.add(estudio);

                }
                temp_estudio.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allEstudioCaso;
    }

    /*   public ArrayList<EstudioCaso> getListaEstudioCasoOrden(String orden) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();
     String hql = "FROM EstudioCaso EC WHERE EC.orden = :orden ORDER BY EC.prioridad ASC";
     Query query = session.createQuery(hql);
     query.setString("orden", orden);
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     EstudioCaso temp = (EstudioCaso) iter.next();
     Hibernate.initialize(temp.getExpedienteFamilia());
     allEstudioCaso.add(temp);
     }
     return allEstudioCaso;
     }*/
    public ArrayList<EstudioCaso> getListaEstudioCasoOrden(final String orden) {

        Session session = sessionFactory.getCurrentSession();

        final ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_ESTUDIOCASOORDEN(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, orden);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp_estudio = (ResultSet) statement.getObject(2);
                while (temp_estudio.next()) {
                    EstudioCaso estudio = new EstudioCaso();
                    Nna nna = new Nna();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();

                    estudio.setIdestudioCaso(temp_estudio.getLong(1));
                    if (temp_estudio.getLong(2) != 0) {

                        String hql_estudio_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_estudio_NNA = connection.prepareCall(hql_estudio_NNA);
                        statement_estudio_NNA.setLong(1, temp_estudio.getLong(2));
                        statement_estudio_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_NNA.execute();
                        temp_nna = (ResultSet) statement_estudio_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getLong(1));
                            estudio.setNna(nna);
                        }
                        temp_nna.close();
                        statement_estudio_NNA.close();
                    }
                    if (temp_estudio.getLong(3) != 0) {

                        String hql_estudio_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_estudio_expFamilia = connection.prepareCall(hql_estudio_expFamilia);
                        statement_estudio_expFamilia.setLong(1, temp_estudio.getLong(3));
                        statement_estudio_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_estudio_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getLong(1));

                            if (temp_expediente.getLong(2) != 0) {

                                String hql_designacion_expediente_familia = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement_designacion_expediente_familia = connection.prepareCall(hql_designacion_expediente_familia);
                                statement_designacion_expediente_familia.setLong(1, temp_expediente.getLong(2));
                                statement_designacion_expediente_familia.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_familia.execute();
                                temp_familia = (ResultSet) statement_designacion_expediente_familia.getObject(2);
                                while (temp_familia.next()) {
                                    fam.setIdfamilia(temp_familia.getLong(1));
                                    expFamilia.setFamilia(fam);
                                }
                                temp_familia.close();
                                statement_designacion_expediente_familia.close();
                            }

                            if (temp_expediente.getLong(3) != 0) {

                                String hql_designacion_expediente_unidad = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement_designacion_expediente_unidad = connection.prepareCall(hql_designacion_expediente_unidad);
                                statement_designacion_expediente_unidad.setLong(1, temp_expediente.getLong(3));
                                statement_designacion_expediente_unidad.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_unidad.execute();
                                temp_unidad = (ResultSet) statement_designacion_expediente_unidad.getObject(2);
                                while (temp_unidad.next()) {
                                    unidad.setIdunidad(temp_unidad.getLong(1));
                                    expFamilia.setUnidad(unidad);
                                }
                                temp_unidad.close();
                                statement_designacion_expediente_unidad.close();
                            }

                            expFamilia.setNumero(temp_expediente.getLong(4));
                            expFamilia.setExpediente(temp_expediente.getString(5));
                            expFamilia.setHt(temp_expediente.getString(6));
                            expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                            expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                            expFamilia.setEstado(temp_expediente.getString(9));
                            expFamilia.setTupa(temp_expediente.getDate(10));
                            expFamilia.setNacionalidad(temp_expediente.getString(11));
                            expFamilia.setRnsa(temp_expediente.getShort(12));
                            expFamilia.setRnaa(temp_expediente.getShort(13));
                            expFamilia.setTipoFamilia(temp_expediente.getString(14));
                            expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                            expFamilia.setHtFicha(temp_expediente.getString(16));
                            expFamilia.setnFicha(temp_expediente.getString(17));
                            expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                            estudio.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_estudio_expFamilia.close();
                    }

                    estudio.setOrden(temp_estudio.getString(4));
                    estudio.setFechaEstudio(temp_estudio.getDate(5));
                    estudio.setFechaSolAdop(temp_estudio.getDate(6));
                    estudio.setResultado(temp_estudio.getString(7));
                    estudio.setPrioridad(temp_estudio.getLong(8));
                    estudio.setNSolicitud(temp_estudio.getLong(9));

                    allEstudioCaso.add(estudio);

                }
                temp_estudio.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allEstudioCaso;
    }
    /*  public EstudioCaso getEstudioCaso(long idEstudio) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();

     EstudioCaso tempEstudio = new EstudioCaso();

     String hql = "FROM EstudioCaso EC WHERE EC.id = :idEstudio";
     Query query = session.createQuery(hql);
     query.setLong("idEstudio", idEstudio);
     Object queryResultA = query.uniqueResult();

     tempEstudio = (EstudioCaso) queryResultA;
     Hibernate.initialize(tempEstudio.getExpedienteFamilia());
     return tempEstudio;

     }*/

    public EstudioCaso getEstudioCaso(final long idEstudio) {

        Session session = sessionFactory.getCurrentSession();

        final EstudioCaso estudio = new EstudioCaso();

        final Designacion designacion = new Designacion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Personal personal = new Personal();
        final Nna nna = new Nna();
        final Familia fam = new Familia();
        final Unidad unidad = new Unidad();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_ESTUDIO_CASO(?, ?)}";
                CallableStatement statement_estudio = connection.prepareCall(hql);
                statement_estudio.setLong(1, idEstudio);
                statement_estudio.registerOutParameter(2, OracleTypes.CURSOR);
                statement_estudio.execute();

                temp_estudio = (ResultSet) statement_estudio.getObject(2);
                while (temp_estudio.next()) {
                    estudio.setIdestudioCaso(temp_estudio.getLong(1));
                    if (temp_estudio.getLong(2) != 0) {

                        String hql_estudio_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_estudio_NNA = connection.prepareCall(hql_estudio_NNA);
                        statement_estudio_NNA.setLong(1, temp_estudio.getLong(2));
                        statement_estudio_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_NNA.execute();
                        temp_nna = (ResultSet) statement_estudio_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getLong(1));
                            estudio.setNna(nna);
                        }
                        temp_nna.close();
                        statement_estudio_NNA.close();
                    }
                    if (temp_estudio.getLong(3) != 0) {

                        String hql_estudio_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_estudio_expFamilia = connection.prepareCall(hql_estudio_expFamilia);
                        statement_estudio_expFamilia.setLong(1, temp_estudio.getLong(3));
                        statement_estudio_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_estudio_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getLong(1));

                            if (temp_expediente.getLong(2) != 0) {

                                String hql_designacion_expediente_familia = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement_designacion_expediente_familia = connection.prepareCall(hql_designacion_expediente_familia);
                                statement_designacion_expediente_familia.setLong(1, temp_expediente.getLong(2));
                                statement_designacion_expediente_familia.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_familia.execute();
                                temp_familia = (ResultSet) statement_designacion_expediente_familia.getObject(2);
                                while (temp_familia.next()) {
                                    fam.setIdfamilia(temp_familia.getLong(1));
                                    expFamilia.setFamilia(fam);
                                }
                                temp_familia.close();
                                statement_designacion_expediente_familia.close();
                            }

                            if (temp_expediente.getLong(3) != 0) {

                                String hql_designacion_expediente_unidad = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement_designacion_expediente_unidad = connection.prepareCall(hql_designacion_expediente_unidad);
                                statement_designacion_expediente_unidad.setLong(1, temp_expediente.getLong(3));
                                statement_designacion_expediente_unidad.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_unidad.execute();
                                temp_unidad = (ResultSet) statement_designacion_expediente_unidad.getObject(2);
                                while (temp_unidad.next()) {
                                    unidad.setIdunidad(temp_unidad.getLong(1));
                                    expFamilia.setUnidad(unidad);
                                }
                                temp_unidad.close();
                                statement_designacion_expediente_unidad.close();
                            }

                            expFamilia.setNumero(temp_expediente.getLong(4));
                            expFamilia.setExpediente(temp_expediente.getString(5));
                            expFamilia.setHt(temp_expediente.getString(6));
                            expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                            expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                            expFamilia.setEstado(temp_expediente.getString(9));
                            expFamilia.setTupa(temp_expediente.getDate(10));
                            expFamilia.setNacionalidad(temp_expediente.getString(11));
                            expFamilia.setRnsa(temp_expediente.getShort(12));
                            expFamilia.setRnaa(temp_expediente.getShort(13));
                            expFamilia.setTipoFamilia(temp_expediente.getString(14));
                            expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                            expFamilia.setHtFicha(temp_expediente.getString(16));
                            expFamilia.setnFicha(temp_expediente.getString(17));
                            expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                            estudio.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_estudio_expFamilia.close();
                    }

                    estudio.setOrden(temp_estudio.getString(4));
                    estudio.setFechaEstudio(temp_estudio.getDate(5));
                    estudio.setFechaSolAdop(temp_estudio.getDate(6));
                    estudio.setResultado(temp_estudio.getString(7));
                    estudio.setPrioridad(temp_estudio.getLong(8));
                    estudio.setNSolicitud(temp_estudio.getLong(9));
                }
                temp_estudio.close();
                statement_estudio.close();
            }
        };
        session.doWork(work);
        return estudio;

    }

    /*  public PostAdopcion getPostAdopcion(long idPost) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();

     PostAdopcion tempPost = new PostAdopcion();

     String hql = "FROM PostAdopcion PA WHERE PA.id = :idPost";
     Query query = session.createQuery(hql);
     query.setLong("idPost", idPost);
     Object queryResultA = query.uniqueResult();

     tempPost = (PostAdopcion) queryResultA;

     return tempPost;

     } */
    public PostAdopcion getPostAdopcion(final long idPost) {

        Session session = sessionFactory.getCurrentSession();

        final PostAdopcion postadopcion = new PostAdopcion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Familia fam = new Familia();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql_postadopcion = "{call HE_GET_POST_ADOPCION(?, ?)}";
                CallableStatement statement_postadopcion = connection.prepareCall(hql_postadopcion);
                statement_postadopcion.setLong(1, idPost);
                statement_postadopcion.registerOutParameter(2, OracleTypes.CURSOR);
                statement_postadopcion.execute();

                temp_postadopcion = (ResultSet) statement_postadopcion.getObject(2);
                while (temp_postadopcion.next()) {
                    postadopcion.setIdpostAdopcion(temp_postadopcion.getLong(1));
                    if (temp_postadopcion.getLong(2) != 0) {

                        String hql_postadopcion_familia = "{call HE_GETFAMILIA(?, ?)}";
                        CallableStatement statement_postadopcion_familia = connection.prepareCall(hql_postadopcion_familia);
                        statement_postadopcion_familia.setLong(1, temp_postadopcion.getLong(2));
                        statement_postadopcion_familia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_postadopcion_familia.execute();
                        temp_familia = (ResultSet) statement_postadopcion_familia.getObject(2);
                        while (temp_familia.next()) {
                            fam.setIdfamilia(temp_familia.getLong(1));
                            expFamilia.setFamilia(fam);
                        }
                        temp_familia.close();
                        statement_postadopcion_familia.close();
                    }
                    postadopcion.setFamilia(fam);
                    postadopcion.setNumeroInformes(temp_postadopcion.getLong(3));
                    postadopcion.setFechaResolucion(temp_postadopcion.getDate(4));
                    postadopcion.setidNna(temp_postadopcion.getLong(5));

                }
                temp_postadopcion.close();
                statement_postadopcion.close();
            }
        };
        session.doWork(work);
        return postadopcion;

    }

    /*   public ArrayList<InformePostAdoptivo> getListaInformesPost(long idPost) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList();

     String hql = "FROM InformePostAdoptivo IP WHERE IP.postAdopcion = :idPost ORDER BY IP.numeroInforme ASC";
     Query query = session.createQuery(hql);
     query.setLong("idPost", idPost);
     List informes = query.list();

     for (Iterator iter = informes.iterator(); iter.hasNext();) {
     InformePostAdoptivo temp = (InformePostAdoptivo) iter.next();
     Hibernate.initialize(temp.getPersonal());
     allInformePostAdoptivo.add(temp);
     }
     return allInformePostAdoptivo;
     }*/
    public ArrayList<InformePostAdoptivo> getListaInformesPost(final long idPost) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ArrayList<InformePostAdoptivo> allInformePostAdoptivo = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql_informepost = "{call HE_GET_LISTA_INFORMES_POST(?, ?)}";
                CallableStatement statement_informespost = connection.prepareCall(hql_informepost);
                statement_informespost.setLong(1, idPost);
                statement_informespost.registerOutParameter(2, OracleTypes.CURSOR);
                statement_informespost.execute();

                temp_infopost = (ResultSet) statement_informespost.getObject(2);
                while (temp_infopost.next()) {

                    InformePostAdoptivo infopost = new InformePostAdoptivo();
                    PostAdopcion postadopcion = new PostAdopcion();
                    Personal personal = new Personal();
                    Unidad unidad = new Unidad();

                    infopost.setIdinformePostAdoptivo(temp_infopost.getShort(1));
                    if (temp_infopost.getShort(2) != 0) {

                        String hql_informepost_postadopcion = "{call HE_GET_POST_ADOPCION(?, ?)}";
                        CallableStatement statement_informepost_postadopcion = connection.prepareCall(hql_informepost_postadopcion);
                        statement_informepost_postadopcion.setLong(1, temp_infopost.getShort(2));
                        statement_informepost_postadopcion.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_informepost_postadopcion.execute();
                        temp_postadopcion = (ResultSet) statement_informepost_postadopcion.getObject(2);
                        while (temp_postadopcion.next()) {
                            postadopcion.setIdpostAdopcion(temp_postadopcion.getShort(1));
                            infopost.setPostAdopcion(postadopcion);
                        }
                        temp_postadopcion.close();
                        statement_informepost_postadopcion.close();
                    }
                    if (temp_infopost.getShort(3) != 0) {

                        String hql_informepost_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_informepost_personal = connection.prepareCall(hql_informepost_personal);
                        statement_informepost_personal.setLong(1, temp_infopost.getShort(3));
                        statement_informepost_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_informepost_personal.execute();
                        temp_personal = (ResultSet) statement_informepost_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));

                            if (temp_personal.getShort(2) != 0) {

                                String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement3 = connection.prepareCall(hql3);
                                statement3.setLong(1, temp_personal.getLong(2));
                                statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                statement3.execute();
                                temp2 = (ResultSet) statement3.getObject(2);
                                while (temp2.next()) {
                                    unidad.setIdunidad(temp2.getShort(1));
                                    personal.setUnidad(unidad);
                                }
                                temp2.close();
                                statement3.close();
                            }

                            personal.setNombre(temp_personal.getString(3));
                            personal.setApellidoP(temp_personal.getString(4));
                            personal.setApellidoM(temp_personal.getString(5));
                            personal.setUser(temp_personal.getString(6));
                            personal.setPass(temp_personal.getString(7));
                            personal.setCorreoTrabajo(temp_personal.getString(8));
                            personal.setCorreoPersonal(temp_personal.getString(9));
                            personal.setProfesion(temp_personal.getString(10));
                            personal.setGradoInstruccion(temp_personal.getString(11));
                            personal.setCargo(temp_personal.getString(12));
                            personal.setDni(temp_personal.getString(13));
                            personal.setFechaNacimiento(temp_personal.getDate(14));
                            personal.setRegimen(temp_personal.getString(15));
                            personal.setFechaIngreso(temp_personal.getDate(16));
                            personal.setDomicilio(temp_personal.getString(17));
                            personal.setRol(temp_personal.getString(18));

                            infopost.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_informepost_personal.close();
                    }
                    infopost.setEstado(temp_infopost.getString(4));
                    infopost.setNumeroInforme(temp_infopost.getString(5));
                    infopost.setFechaRecepcionProyectado(temp_infopost.getDate(6));
                    infopost.setFechaRecepcion(temp_infopost.getDate(7));
                    infopost.setFechaInforme(temp_infopost.getDate(8));
                    infopost.setFechaActa(temp_infopost.getDate(9));
                    infopost.setObs(temp_infopost.getString(10));

                    allInformePostAdoptivo.add(infopost);

                }
                temp_infopost.close();
                statement_informespost.close();
            }
        };

        session.doWork(work);

        return allInformePostAdoptivo;
    }

    /* public InformePostAdoptivo getInformePost(long idInforme) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();

     InformePostAdoptivo tempPost = new InformePostAdoptivo();

     String hql = "FROM InformePostAdoptivo IP WHERE IP.id = :idPost";
     Query query = session.createQuery(hql);
     query.setLong("idPost", idInforme);
     Object queryResultA = query.uniqueResult();

     tempPost = (InformePostAdoptivo) queryResultA;

     return tempPost;

     }*/
    public InformePostAdoptivo getInformePost(final long idInforme) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final InformePostAdoptivo infopost = new InformePostAdoptivo();
        final PostAdopcion postadopcion = new PostAdopcion();
        final ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        final Familia fam = new Familia();
        final Personal personal = new Personal();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql_infopost = "{call HE_GET_INFORME_POST(?, ?)}";
                CallableStatement statement_infopost = connection.prepareCall(hql_infopost);
                statement_infopost.setLong(1, idInforme);
                statement_infopost.registerOutParameter(2, OracleTypes.CURSOR);
                statement_infopost.execute();

                temp_infopost = (ResultSet) statement_infopost.getObject(2);
                while (temp_infopost.next()) {
                    infopost.setIdinformePostAdoptivo(temp_infopost.getShort(1));
                    if (temp_infopost.getShort(2) != 0) {

                        String hql_informepost_postadopcion = "{call HE_GET_POST_ADOPCION(?, ?)}";
                        CallableStatement statement_informepost_postadopcion = connection.prepareCall(hql_informepost_postadopcion);
                        statement_informepost_postadopcion.setLong(1, temp_infopost.getShort(2));
                        statement_informepost_postadopcion.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_informepost_postadopcion.execute();
                        temp_postadopcion = (ResultSet) statement_informepost_postadopcion.getObject(2);
                        while (temp_postadopcion.next()) {
                            postadopcion.setIdpostAdopcion(temp_postadopcion.getShort(1));
                            infopost.setPostAdopcion(postadopcion);
                        }
                        temp_postadopcion.close();
                        statement_informepost_postadopcion.close();
                    }
                    if (temp_infopost.getShort(3) != 0) {

                        String hql_informepost_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_informepost_personal = connection.prepareCall(hql_informepost_personal);
                        statement_informepost_personal.setLong(1, temp_infopost.getShort(3));
                        statement_informepost_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_informepost_personal.execute();
                        temp_personal = (ResultSet) statement_informepost_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));
                            infopost.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_informepost_personal.close();
                    }
                    infopost.setEstado(temp_infopost.getString(4));
                    infopost.setNumeroInforme(temp_infopost.getString(5));
                    infopost.setFechaRecepcionProyectado(temp_infopost.getDate(6));
                    infopost.setFechaRecepcion(temp_infopost.getDate(7));
                    infopost.setFechaInforme(temp_infopost.getDate(8));
                    infopost.setFechaActa(temp_infopost.getDate(9));
                    infopost.setObs(temp_infopost.getString(10));

                }
                temp_infopost.close();
                statement_infopost.close();
            }
        };
        session.doWork(work);

        return infopost;

    }


    /*  public ArrayList<ExpedienteFamilia> getListaReevaluación() {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<ExpedienteFamilia> allReevaluacion = new ArrayList();
     String hql = "from ExpedienteFamilia EF where EF.estado = :estado";
     Query query = session.createQuery(hql);
     query.setString("estado", "reevaluacion");
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
     allReevaluacion.add(temp);
     }
     return allReevaluacion;
     }*/
    public ArrayList<ExpedienteFamilia> getListaReevaluación() {

        Session session = sessionFactory.getCurrentSession();

        final ArrayList<ExpedienteFamilia> allReevaluacion = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTAREEVALUACION(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp = (ResultSet) statement.getObject(1);
                while (temp.next()) {

                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();

                    expFamilia.setIdexpedienteFamilia(temp.getShort(1));
                    if (temp.getShort(2) != 0) {

                        String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, temp.getShort(2));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        temp2 = (ResultSet) statement2.getObject(2);
                        while (temp2.next()) {
                            fam.setIdfamilia(temp2.getShort(1));
                            expFamilia.setFamilia(fam);
                        }
                        temp2.close();
                        statement2.close();
                    }
                    if (temp.getShort(3) != 0) {

                        String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, temp.getShort(3));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        temp2 = (ResultSet) statement3.getObject(2);
                        while (temp2.next()) {
                            unidad.setIdunidad(temp2.getShort(1));
                            unidad.setDepartamento(temp2.getString("DEPARTAMENTO"));
                            expFamilia.setUnidad(unidad);
                        }
                        temp2.close();
                        statement3.close();
                    }
                    expFamilia.setNumero(temp.getLong(4));
                    expFamilia.setExpediente(temp.getString(5));
                    expFamilia.setHt(temp.getString(6));
                    expFamilia.setNumeroExpediente(temp.getString(7));
                    expFamilia.setFechaIngresoDga(temp.getDate(8));
                    expFamilia.setEstado(temp.getString(9));
                    expFamilia.setTupa(temp.getDate(10));
                    expFamilia.setNacionalidad(temp.getString(11));
                    expFamilia.setRnsa(temp.getShort(12));
                    expFamilia.setRnaa(temp.getShort(13));
                    expFamilia.setTipoFamilia(temp.getString(14));
                    expFamilia.setTipoListaEspera(temp.getString(15));
                    expFamilia.setHtFicha(temp.getString(16));
                    expFamilia.setnFicha(temp.getString(17));
                    expFamilia.setFechaIngresoFicha(temp.getDate(18));

                    allReevaluacion.add(expFamilia);

                }
                temp.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allReevaluacion;
    }

//    public void crearDesignacion(Designacion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    public void crearDesignacion(Designacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idEx = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final Long idNna = temp.getNna().getIdnna();
        final Long idPer = temp.getPersonal().getIdpersonal();
        final String nDesig = temp.getNDesignacion();
        final Date fechaPro = temp.getFechaPropuesta();
        final Date fechaCon = temp.getFechaConsejo();
        final short acepCon = temp.getAceptacionConsejo();
        final String tipo = temp.getTipoPropuesta();
        final String obs = temp.getObs();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_CREAR_DESIG(?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idEx);
                statement.setLong(2, idNna);
                statement.setLong(3, idPer);
                statement.setString(4, nDesig);
                statement.setDate(5, (java.sql.Date) fechaPro);
                statement.setDate(6, (java.sql.Date) fechaCon);
                statement.setShort(7, acepCon);
                statement.setString(8, tipo);
                statement.setString(9, obs);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void updateDesignacion(Designacion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    public void updateDesignacion(Designacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idDe = temp.getIddesignacion();
        final Long idEx = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final Long idNna = temp.getNna().getIdnna();
        final Long idPer = temp.getPersonal().getIdpersonal();
        final String nDesig = temp.getNDesignacion();
        final Long prio = temp.getPrioridad();
        final Date fechaPro = temp.getFechaPropuesta();
        final Date fechaCon = temp.getFechaConsejo();
        final short acepCon = temp.getAceptacionConsejo();
        final String tipo = temp.getTipoPropuesta();
        final String obs = temp.getObs();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_DESIG(?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idDe);
                statement.setString(2, nDesig);
                statement.setLong(3, prio);
                statement.setDate(4, (java.sql.Date) fechaPro);
                statement.setDate(5, (java.sql.Date) fechaCon);
                statement.setShort(6, acepCon);
                statement.setString(7, tipo);
                statement.setString(8, obs);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public ArrayList<Designacion> getListaDesignaciones(long idNna) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        ArrayList<Designacion> allDesig = new ArrayList();
//        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.nna = :id ORDER BY D.fechaPropuesta DESC";
//        Query query = session.createQuery(hql);
//        query.setShort("aceptacionConsejo", Short.parseShort("1"));
//        query.setLong("id", idNna);
//        List expedientes = query.list();
//        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
//            Designacion temp = (Designacion) iter.next();
//            Hibernate.initialize(temp.getExpedienteFamilia());
//            Hibernate.initialize(temp.getNna().getExpedienteNnas());
//            allDesig.add(temp);
//        }
//        return allDesig;
//    }
    public ArrayList<Designacion> getListaDesignaciones(String numDesig) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String nDesig = numDesig;
        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_LISTADESIG_BY_NDESIG(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, nDesig);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Designacion tempDesig = new Designacion();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Nna tempNna = new Nna();
                    Personal tempPerso = new Personal();

                    tempDesig.setIddesignacion(rs.getLong(1));
                    tempDesig.setNDesignacion(rs.getString(5));
                    tempDesig.setPrioridad(rs.getLong(6));
                    tempDesig.setFechaPropuesta(rs.getDate(7));
                    tempDesig.setFechaConsejo(rs.getDate(8));
                    tempDesig.setAceptacionConsejo(rs.getShort(9));
                    tempDesig.setTipoPropuesta(rs.getString(10));
                    tempDesig.setObs(rs.getString(11));

                    tempPerso.setIdpersonal(rs.getLong(4));

                    tempEF.setIdexpedienteFamilia(rs.getLong(2));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    tempNna.setIdnna(rs.getLong("IDNNA"));
                    tempNna.setNombre(rs.getString("NOMBRE"));
                    tempNna.setApellidoP(rs.getString("APELLIDO_P"));
                    tempNna.setApellidoM(rs.getString("APELLIDO_M"));
                    tempNna.setSexo(rs.getString("SEXO"));
                    tempNna.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));
                    tempNna.setEdadAnhos(rs.getShort("EDAD_ANHOS"));
                    tempNna.setEdadMeses(rs.getShort("EDAD_MESES"));
                    tempNna.setActaNacimiento(rs.getShort("ACTA_NACIMIENTO"));
                    tempNna.setCondicionSalud(rs.getString("CONDICION_SALUD"));
                    tempNna.setDepartamentoNacimiento(rs.getString("DEPARTAMENTO_NACIMIENTO"));
                    tempNna.setProvinciaNacimiento(rs.getString("PROVINCIA_NACIMIENTO"));
                    tempNna.setDistritoNacimiento(rs.getString("DISTRITO_NACIMIENTO"));
                    tempNna.setPaisNacimiento(rs.getString("PAIS_NACIMIENTO"));
                    tempNna.setLugarNac(rs.getString("LUGAR_NAC"));
                    tempNna.setFechaResolAbandono(rs.getDate("FECHA_RESOL_ABANDONO"));
                    tempNna.setFechaResolConsentida(rs.getDate("FECHA_RESOL_CONSENTIDA"));
                    tempNna.setClasificacion(rs.getString("CLASIFICACION"));
                    tempNna.setIncesto(rs.getShort("INCESTO"));
                    tempNna.setMental(rs.getShort("MENTAL"));
                    tempNna.setEpilepsia(rs.getShort("EPILEPSIA"));
                    tempNna.setAbuso(rs.getShort("ABUSO"));
                    tempNna.setSifilis(rs.getShort("SIFILIS"));
                    tempNna.setSeguiMedico(rs.getShort("SEGUI_MEDICO"));
                    tempNna.setOperacion(rs.getShort("OPERACION"));
                    tempNna.setHiperactivo(rs.getShort("HIPERACTIVO"));
                    tempNna.setEspecial(rs.getShort("ESPECIAL"));
                    tempNna.setEnfermo(rs.getShort("ENFERMO"));
                    tempNna.setMayor(rs.getShort("MAYOR"));
                    tempNna.setAdolescente(rs.getShort("ADOLESCENTE"));
                    tempNna.setHermano(rs.getShort("HERMANO"));
                    tempNna.setNn(rs.getShort("NN"));
                    tempNna.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempDesig.setExpedienteFamilia(tempEF);
                    tempDesig.setNna(tempNna);
                    tempDesig.setPersonal(tempPerso);

                    allDesig.add(tempDesig);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allDesig;
    }

    public ArrayList<Designacion> getListaDesignacionesEstadoAdopcion(String numDesig) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String nDesig = numDesig;
        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_DESIG_EST_ADOP(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, nDesig);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Designacion tempDesig = new Designacion();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Nna tempNna = new Nna();
                    Personal tempPerso = new Personal();

                    tempDesig.setIddesignacion(rs.getLong(1));
                    tempDesig.setNDesignacion(rs.getString(5));
                    tempDesig.setPrioridad(rs.getLong(6));
                    tempDesig.setFechaPropuesta(rs.getDate(7));
                    tempDesig.setFechaConsejo(rs.getDate(8));
                    tempDesig.setAceptacionConsejo(rs.getShort(9));
                    tempDesig.setTipoPropuesta(rs.getString(10));
                    tempDesig.setObs(rs.getString(11));

                    tempPerso.setIdpersonal(rs.getLong(4));

                    tempEF.setIdexpedienteFamilia(rs.getLong(2));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    tempNna.setIdnna(rs.getLong("IDNNA"));
                    tempNna.setNombre(rs.getString("NOMBRE"));
                    tempNna.setApellidoP(rs.getString("APELLIDO_P"));
                    tempNna.setApellidoM(rs.getString("APELLIDO_M"));
                    tempNna.setSexo(rs.getString("SEXO"));
                    tempNna.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));
                    tempNna.setEdadAnhos(rs.getShort("EDAD_ANHOS"));
                    tempNna.setEdadMeses(rs.getShort("EDAD_MESES"));
                    tempNna.setActaNacimiento(rs.getShort("ACTA_NACIMIENTO"));
                    tempNna.setCondicionSalud(rs.getString("CONDICION_SALUD"));
                    tempNna.setDepartamentoNacimiento(rs.getString("DEPARTAMENTO_NACIMIENTO"));
                    tempNna.setProvinciaNacimiento(rs.getString("PROVINCIA_NACIMIENTO"));
                    tempNna.setDistritoNacimiento(rs.getString("DISTRITO_NACIMIENTO"));
                    tempNna.setPaisNacimiento(rs.getString("PAIS_NACIMIENTO"));
                    tempNna.setLugarNac(rs.getString("LUGAR_NAC"));
                    tempNna.setFechaResolAbandono(rs.getDate("FECHA_RESOL_ABANDONO"));
                    tempNna.setFechaResolConsentida(rs.getDate("FECHA_RESOL_CONSENTIDA"));
                    tempNna.setClasificacion(rs.getString("CLASIFICACION"));
                    tempNna.setIncesto(rs.getShort("INCESTO"));
                    tempNna.setMental(rs.getShort("MENTAL"));
                    tempNna.setEpilepsia(rs.getShort("EPILEPSIA"));
                    tempNna.setAbuso(rs.getShort("ABUSO"));
                    tempNna.setSifilis(rs.getShort("SIFILIS"));
                    tempNna.setSeguiMedico(rs.getShort("SEGUI_MEDICO"));
                    tempNna.setOperacion(rs.getShort("OPERACION"));
                    tempNna.setHiperactivo(rs.getShort("HIPERACTIVO"));
                    tempNna.setEspecial(rs.getShort("ESPECIAL"));
                    tempNna.setEnfermo(rs.getShort("ENFERMO"));
                    tempNna.setMayor(rs.getShort("MAYOR"));
                    tempNna.setAdolescente(rs.getShort("ADOLESCENTE"));
                    tempNna.setHermano(rs.getShort("HERMANO"));
                    tempNna.setNn(rs.getShort("NN"));
                    tempNna.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempDesig.setExpedienteFamilia(tempEF);
                    tempDesig.setNna(tempNna);
                    tempDesig.setPersonal(tempPerso);

                    allDesig.add(tempDesig);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allDesig;
    }
//    public ArrayList<Designacion> getListaAdopciones() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        ArrayList<Designacion> allDesig = new ArrayList();
//        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo ORDER BY D.fechaConsejo ASC";
//        Query query = session.createQuery(hql);
//        query.setShort("aceptacionConsejo", Short.parseShort("0"));
//        List expedientes = query.list();
//        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
//            Designacion temp = (Designacion) iter.next();
//            Hibernate.initialize(temp.getExpedienteFamilia().getEvaluacions());
//            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
//            for (Evaluacion eval : temp.getExpedienteFamilia().getEvaluacions()) {
//                if (eval.getTipo().equals("empatia") || eval.getTipo().equals("informe")) {
//                    String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol ASC";
//                    Query query2 = session.createQuery(hql2);
//                    query2.setLong("idEvaluacion", eval.getIdevaluacion());
//                    query2.setMaxResults(1);
//                    Object queryResult = query2.uniqueResult();
//                    Resolucion tempResol = (Resolucion) queryResult;
//                    Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
//                    tempResoluciones.add(tempResol);
//                    eval.setResolucions(tempResoluciones);
//                    tempEvaluaciones.add(eval);
//
//                }
//
//            }
//            temp.getExpedienteFamilia().setEvaluacions(tempEvaluaciones);
//            Hibernate.initialize(temp.getNna());
//            allDesig.add(temp);
//        }
//        return allDesig;
//    }

    public ArrayList<Designacion> getListaAdopciones() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_LISTADESIG_ADOPCION(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    Designacion tempDesig = new Designacion();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Nna tempNna = new Nna();
                    Personal tempPerso = new Personal();
                    Unidad tempUA = new Unidad();

                    tempDesig.setIddesignacion(rs.getLong(1));
                    tempDesig.setNDesignacion(rs.getString(5));
                    tempDesig.setPrioridad(rs.getLong(6));
                    tempDesig.setFechaPropuesta(rs.getDate(7));
                    tempDesig.setFechaConsejo(rs.getDate(8));
                    tempDesig.setAceptacionConsejo(rs.getShort(9));
                    tempDesig.setTipoPropuesta(rs.getString(10));
                    tempDesig.setObs(rs.getString(11));

                    tempPerso.setIdpersonal(rs.getLong(4));

                    tempUA.setIdunidad(rs.getLong("IDUNIDAD"));
                    String hql4 = "{call HE_GET_UNIDAD(?, ?)}";
                    CallableStatement statement4 = connection.prepareCall(hql4);
                    statement4.setLong(1, tempUA.getIdunidad());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();
                    ResultSet rs4 = (ResultSet) statement4.getObject(2);
                    if (rs4.next()) {
                        tempUA.setIdunidad(rs4.getLong(1));
                        tempUA.setDepartamento(rs4.getString("DEPARTAMENTO"));
                        tempEF.setUnidad(tempUA);
                    }
                    statement4.close();

                    tempEF.setIdexpedienteFamilia(rs.getLong(2));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setHt(rs.getString("HT"));
                    tempEF.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempEF.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempEF.setEstado(rs.getString("ESTADO"));
                    tempEF.setTupa(rs.getDate("TUPA"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempEF.setRnsa(rs.getShort("RNSA"));
                    tempEF.setRnaa(rs.getShort("RNAA"));
                    tempEF.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempEF.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempEF.setHtFicha(rs.getString("HTFICHA"));
                    tempEF.setnFicha(rs.getString("NFICHA"));
                    tempEF.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();

                    String hql2 = "{call HE_LISTAEVAL_BY_IDEXPFAM(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempEF.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(tempEF);
                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("empatia") || tempEval.getTipo().equals("informe")) {

                            String hql3 = "{call HE_ULTRESOL_LEGAL(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    tempEF.setEvaluacions(listaEv);

                    tempNna.setIdnna(rs.getLong("IDNNA"));
                    tempNna.setNombre(rs.getString("NOMBRE"));
                    tempNna.setApellidoP(rs.getString("APELLIDO_P"));
                    tempNna.setApellidoM(rs.getString("APELLIDO_M"));
                    tempNna.setSexo(rs.getString("SEXO"));
                    tempNna.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));
                    tempNna.setEdadAnhos(rs.getShort("EDAD_ANHOS"));
                    tempNna.setEdadMeses(rs.getShort("EDAD_MESES"));
                    tempNna.setActaNacimiento(rs.getShort("ACTA_NACIMIENTO"));
                    tempNna.setCondicionSalud(rs.getString("CONDICION_SALUD"));
                    tempNna.setDepartamentoNacimiento(rs.getString("DEPARTAMENTO_NACIMIENTO"));
                    tempNna.setProvinciaNacimiento(rs.getString("PROVINCIA_NACIMIENTO"));
                    tempNna.setDistritoNacimiento(rs.getString("DISTRITO_NACIMIENTO"));
                    tempNna.setPaisNacimiento(rs.getString("PAIS_NACIMIENTO"));
                    tempNna.setLugarNac(rs.getString("LUGAR_NAC"));
                    tempNna.setFechaResolAbandono(rs.getDate("FECHA_RESOL_ABANDONO"));
                    tempNna.setFechaResolConsentida(rs.getDate("FECHA_RESOL_CONSENTIDA"));
                    tempNna.setClasificacion(rs.getString("CLASIFICACION"));
                    tempNna.setIncesto(rs.getShort("INCESTO"));
                    tempNna.setMental(rs.getShort("MENTAL"));
                    tempNna.setEpilepsia(rs.getShort("EPILEPSIA"));
                    tempNna.setAbuso(rs.getShort("ABUSO"));
                    tempNna.setSifilis(rs.getShort("SIFILIS"));
                    tempNna.setSeguiMedico(rs.getShort("SEGUI_MEDICO"));
                    tempNna.setOperacion(rs.getShort("OPERACION"));
                    tempNna.setHiperactivo(rs.getShort("HIPERACTIVO"));
                    tempNna.setEspecial(rs.getShort("ESPECIAL"));
                    tempNna.setEnfermo(rs.getShort("ENFERMO"));
                    tempNna.setMayor(rs.getShort("MAYOR"));
                    tempNna.setAdolescente(rs.getShort("ADOLESCENTE"));
                    tempNna.setHermano(rs.getShort("HERMANO"));
                    tempNna.setNn(rs.getShort("NN"));
                    tempNna.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempDesig.setExpedienteFamilia(tempEF);
                    tempDesig.setNna(tempNna);
                    tempDesig.setPersonal(tempPerso);

                    allDesig.add(tempDesig);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allDesig;
    }

//    public void deleteResolucion(Resolucion temp) {
//        
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        
//        session.delete(temp);
//    
//    }
    public void deleteResolucion(Resolucion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idR = temp.getIdresolucion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_DELETE_RESOL(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idR);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    //    public void deleteEvaluacion(Evaluacion temp) {
//        
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        
//        session.delete(temp);
//    
//    }
    public void deleteEvaluacion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idE = temp.getIdevaluacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_DELETE_EVAL(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idE);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void crearEstudioCaso(EstudioCaso temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    public void crearEstudioCaso(EstudioCaso temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = temp.getNna().getIdnna();
        final Long idExp = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final String ord = temp.getOrden();
        final Long prior = temp.getPrioridad();
        final Long nsol = temp.getNSolicitud();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_CREAR_ESTUDIO(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.setLong(2, idExp);
                statement.setString(3, ord);
                statement.setLong(4, prior);
                statement.setLong(5, nsol);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void updateEstudioCaso(EstudioCaso temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.update(temp);
//
//    }
    public void updateEstudioCaso(EstudioCaso temp) {

        Session session = sessionFactory.getCurrentSession();

        final Long idEs = temp.getIdestudioCaso();
        final Date fechaEst = temp.getFechaEstudio();
        final Date fechaSol = temp.getFechaSolAdop();
        final String res = temp.getResultado();
        final Long nsol = temp.getNSolicitud();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_ESTUDIO(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idEs);
                statement.setDate(2, (java.sql.Date) fechaEst);
                statement.setDate(3, (java.sql.Date) fechaSol);
                statement.setString(4, res);
                statement.setLong(5, nsol);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void crearPostAdopcion(PostAdopcion temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    public void crearPostAdopcion(PostAdopcion temp) {

        Session session = sessionFactory.getCurrentSession();

        final Long idFam = temp.getFamilia().getIdfamilia();
        final Date fechaResol = temp.getFechaResolucion();
        final Long idNna = temp.getidNna();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_SAVE_POST(?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idFam);
                statement.setDate(2, (java.sql.Date) fechaResol);
                statement.setLong(3, idNna);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    /*public void updatePostAdopcion(PostAdopcion temp) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();

     session.update(temp);

     }*/
    public void updatePostAdopcion(PostAdopcion temp) {

        Session session = sessionFactory.getCurrentSession();

        final Long idpost = temp.getIdpostAdopcion();
        final Long idFam = temp.getFamilia().getIdfamilia();
        final Long numInfor = temp.getNumeroInformes();
        final Date fechaResol = temp.getFechaResolucion();
        final Long idNna = temp.getidNna();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_POST_ADOPCION(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idpost);
                statement.setLong(2, idFam);
                statement.setLong(3, numInfor);
                statement.setDate(4, (java.sql.Date) fechaResol);
                statement.setLong(5, idNna);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

//    public void crearInformePost(InformePostAdoptivo temp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        session.save(temp);
//
//    }
    public void crearInformePost(InformePostAdoptivo temp) {

        Session session = sessionFactory.getCurrentSession();

        final Long idpost = temp.getPostAdopcion().getIdpostAdopcion();
        final Long idpersonal = temp.getPersonal().getIdpersonal();
        final String estado = temp.getEstado();
        final String numero_informe = temp.getNumeroInforme();
        final Date fecha_rec_proy = temp.getFechaRecepcionProyectado();
        final Date fecha_rec = temp.getFechaRecepcion();
        final Date fecha_info = temp.getFechaInforme();
        final Date fecha_act = temp.getFechaActa();
        final String obs = temp.getObs();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_SAVE_INFORME_POST(?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idpost);
                statement.setLong(2, idpersonal);
                statement.setString(3, estado);
                statement.setString(4, numero_informe);
                statement.setDate(5, (java.sql.Date) fecha_rec_proy);
                statement.setDate(6, (java.sql.Date) fecha_rec);
                statement.setDate(7, (java.sql.Date) fecha_info);
                statement.setDate(8, (java.sql.Date) fecha_act);
                statement.setString(9, obs);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    /* public void updateInformePost(InformePostAdoptivo temp) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();

     session.update(temp);

     }*/
    public void updateInformePost(InformePostAdoptivo temp) {

        Session session = sessionFactory.getCurrentSession();

        final Long idinfo_post = temp.getIdinformePostAdoptivo();
        final Long idpost = temp.getPostAdopcion().getIdpostAdopcion();
        final Long idpersonal = temp.getPersonal().getIdpersonal();
        final String estado = temp.getEstado();
        final String numero_informe = temp.getNumeroInforme();
        final Date fecha_rec_proy = temp.getFechaRecepcionProyectado();
        final Date fecha_rec = temp.getFechaRecepcion();
        final Date fecha_info = temp.getFechaInforme();
        final Date fecha_act = temp.getFechaActa();
        final String obs = temp.getObs();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_INFO_POST(?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idinfo_post);
                statement.setLong(2, idpost);
                statement.setLong(3, idpersonal);
                statement.setString(4, estado);
                statement.setString(5, numero_informe);
                statement.setDate(6, (java.sql.Date) fecha_rec_proy);
                statement.setDate(7, (java.sql.Date) fecha_rec);
                statement.setDate(8, (java.sql.Date) fecha_info);
                statement.setDate(9, (java.sql.Date) fecha_act);
                statement.setString(10, obs);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    /* public ArrayList<ExpedienteFamilia> getListaEspera() {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<ExpedienteFamilia> allEspera = new ArrayList();
     String hql = "from ExpedienteFamilia EF where EF.estado = :estado";
     Query query = session.createQuery(hql);
     query.setString("estado", "espera");
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
     Hibernate.initialize(temp.getEvaluacions());
     Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
     for (Evaluacion eval : temp.getEvaluacions()) {
     if (eval.getTipo().equals("legal")) {
     String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion and R.tipo = :tipo ORDER BY R.fechaResol DESC";
     Query query2 = session.createQuery(hql2);
     query2.setLong("idEvaluacion", eval.getIdevaluacion());
     query2.setString("tipo", "apto");
     query2.setMaxResults(1);
     Object queryResult = query2.uniqueResult();
     Resolucion tempResol = (Resolucion) queryResult;
     Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
     tempResoluciones.add(tempResol);
     eval.setResolucions(tempResoluciones);
     tempEvaluaciones.add(eval);

     }

     }
     temp.setEvaluacions(tempEvaluaciones);
     allEspera.add(temp);
     }
     return allEspera;
     }*/
    public ArrayList<ExpedienteFamilia> getListaEspera() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<ExpedienteFamilia> allEspera = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_LISTA_ESPERA(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp_expediente = (ResultSet) statement.getObject(1);

                while (temp_expediente.next()) {
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();
                    Personal personal = new Personal();

                    expFamilia.setIdexpedienteFamilia(temp_expediente.getLong(1));
                    if (temp_expediente.getLong(2) != 0) {

                        String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, temp_expediente.getLong(2));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        temp2 = (ResultSet) statement2.getObject(2);
                        while (temp2.next()) {
                            fam.setIdfamilia(temp2.getLong(1));
                            expFamilia.setFamilia(fam);
                        }
                        temp2.close();
                        statement2.close();
                    }
                    if (temp_expediente.getLong(3) != 0) {

                        String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, temp_expediente.getLong(3));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        temp2 = (ResultSet) statement3.getObject(2);
                        while (temp2.next()) {
                            unidad.setIdunidad(temp2.getLong(1));
                            unidad.setDepartamento(temp2.getString("DEPARTAMENTO"));
                            expFamilia.setUnidad(unidad);
                        }
                        temp2.close();
                        statement3.close();
                    }
                    expFamilia.setNumero(temp_expediente.getLong(4));
                    expFamilia.setExpediente(temp_expediente.getString(5));
                    expFamilia.setHt(temp_expediente.getString(6));
                    expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                    expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                    expFamilia.setEstado(temp_expediente.getString(9));
                    expFamilia.setTupa(temp_expediente.getDate(10));
                    expFamilia.setNacionalidad(temp_expediente.getString(11));
                    expFamilia.setRnsa(temp_expediente.getShort(12));
                    expFamilia.setRnaa(temp_expediente.getShort(13));
                    expFamilia.setTipoFamilia(temp_expediente.getString(14));
                    expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                    expFamilia.setHtFicha(temp_expediente.getString(16));
                    expFamilia.setnFicha(temp_expediente.getString(17));
                    expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();

                    String hql2 = "{call HE_GET_LISTA_EVALUACIONES(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, expFamilia.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(expFamilia);

                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("legal")) {

                            String hql3 = "{call HE_GET_RESOLUCION_APTA(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    expFamilia.setEvaluacions(listaEv);
                    allEspera.add(expFamilia);
                }
                temp_expediente.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allEspera;
    }

    /* public ArrayList<Designacion> getListaDesignaciones2(long idNna) {

     Session session = sessionFactory.getCurrentSession();
     session.beginTransaction();
     ArrayList<Designacion> allDesig = new ArrayList();
     String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.nna = :id ORDER BY D.fechaPropuesta DESC";
     Query query = session.createQuery(hql);
     query.setShort("aceptacionConsejo", Short.parseShort("0"));
     query.setLong("id", idNna);
     List expedientes = query.list();
     for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
     Designacion temp = (Designacion) iter.next();
     Hibernate.initialize(temp.getExpedienteFamilia());
     Hibernate.initialize(temp.getNna().getExpedienteNnas());
     allDesig.add(temp);
     }
     return allDesig;
     }*/
    public ArrayList<Designacion> getListaDesignaciones2(final long idNna) {

        Session session = sessionFactory.getCurrentSession();

        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_DESIGNACIONES_2(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idNna);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                temp_designacion = (ResultSet) statement.getObject(2);
                while (temp_designacion.next()) {
                    Designacion designacion = new Designacion();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    ExpedienteNna expNna = new ExpedienteNna();
                    Personal personal = new Personal();
                    Nna nna = new Nna();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();
                    Juzgado juzgado = new Juzgado();
                    Car car = new Car();

                    designacion.setIddesignacion(temp_designacion.getShort(1));
                    if (temp_designacion.getShort(2) != 0) {

                        String hql_designacion_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_designacion_expFamilia = connection.prepareCall(hql_designacion_expFamilia);
                        statement_designacion_expFamilia.setLong(1, temp_designacion.getShort(2));
                        statement_designacion_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_expFamilia.execute();
                        temp_expediente = (ResultSet) statement_designacion_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getShort(1));

                            if (temp_expediente.getShort(2) != 0) {

                                String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement2 = connection.prepareCall(hql2);
                                statement2.setLong(1, temp_expediente.getShort(2));
                                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                                statement2.execute();
                                temp2 = (ResultSet) statement2.getObject(2);
                                while (temp2.next()) {
                                    fam.setIdfamilia(temp2.getShort(1));
                                    expFamilia.setFamilia(fam);
                                }
                                temp2.close();
                                statement2.close();
                            }
                            if (temp_expediente.getShort(3) != 0) {

                                String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement3 = connection.prepareCall(hql3);
                                statement3.setLong(1, temp_expediente.getShort(3));
                                statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                statement3.execute();
                                temp2 = (ResultSet) statement3.getObject(2);
                                while (temp2.next()) {
                                    unidad.setIdunidad(temp2.getShort(1));
                                    expFamilia.setUnidad(unidad);
                                }
                                temp2.close();
                                statement3.close();
                            }
                            expFamilia.setNumero(temp_expediente.getLong(4));
                            expFamilia.setExpediente(temp_expediente.getString(5));
                            expFamilia.setHt(temp_expediente.getString(6));
                            expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                            expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                            expFamilia.setEstado(temp_expediente.getString(9));
                            expFamilia.setTupa(temp_expediente.getDate(10));
                            expFamilia.setNacionalidad(temp_expediente.getString(11));
                            expFamilia.setRnsa(temp_expediente.getShort(12));
                            expFamilia.setRnaa(temp_expediente.getShort(13));
                            expFamilia.setTipoFamilia(temp_expediente.getString(14));
                            expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                            expFamilia.setHtFicha(temp_expediente.getString(16));
                            expFamilia.setnFicha(temp_expediente.getString(17));
                            expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                            designacion.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_designacion_expFamilia.close();
                    }
                    if (temp_designacion.getShort(3) != 0) {

                        String hql_designacion_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_designacion_NNA = connection.prepareCall(hql_designacion_NNA);
                        statement_designacion_NNA.setLong(1, temp_designacion.getShort(3));
                        statement_designacion_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_NNA.execute();
                        temp_nna = (ResultSet) statement_designacion_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));

                            if (temp_nna.getShort(2) != 0) {

                                String hql_designacion_NNA_Juzgado = "{call HE_GET_JUZGADO(?, ?)}";
                                CallableStatement statement_designacion_NNA_Juzgado = connection.prepareCall(hql_designacion_NNA_Juzgado);
                                statement_designacion_NNA_Juzgado.setLong(1, temp_nna.getShort(2));
                                statement_designacion_NNA_Juzgado.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_NNA_Juzgado.execute();
                                temp_juzgado = (ResultSet) statement_designacion_NNA_Juzgado.getObject(2);
                                while (temp_juzgado.next()) {
                                    juzgado.setIdjuzgado(temp_juzgado.getShort(1));

                                    nna.setJuzgado(juzgado);
                                }
                                temp_juzgado.close();
                                statement_designacion_NNA_Juzgado.close();
                            }

                            if (temp_nna.getShort(3) != 0) {

                                String hql_designacion_NNA_CAR = "{call HE_GET_CAR(?, ?)}";
                                CallableStatement statement_designacion_NNA_CAR = connection.prepareCall(hql_designacion_NNA_CAR);
                                statement_designacion_NNA_CAR.setLong(1, temp_nna.getShort(3));
                                statement_designacion_NNA_CAR.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_NNA_CAR.execute();
                                temp_car = (ResultSet) statement_designacion_NNA_CAR.getObject(2);
                                while (temp_car.next()) {
                                    car.setIdcar(temp_car.getShort(1));

                                    nna.setCar(car);
                                }
                                temp_car.close();
                                statement_designacion_NNA_CAR.close();
                            }

                            nna.setNombre(temp_nna.getString(4));
                            nna.setApellidoP(temp_nna.getString(5));
                            nna.setApellidoM(temp_nna.getString(6));
                            nna.setSexo(temp_nna.getString(7));
                            nna.setFechaNacimiento(temp_nna.getDate(8));
                            nna.setEdadAnhos(temp_nna.getShort(9));
                            nna.setEdadMeses(temp_nna.getShort(10));
                            nna.setActaNacimiento(temp_nna.getShort(11));
                            nna.setCondicionSalud(temp_nna.getString(12));
                            nna.setDepartamentoNacimiento(temp_nna.getString(13));
                            nna.setProvinciaNacimiento(temp_nna.getString(14));
                            nna.setDistritoNacimiento(temp_nna.getString(15));
                            nna.setPaisNacimiento(temp_nna.getString(16));
                            nna.setLugarNac(temp_nna.getString(17));
                            nna.setFechaResolAbandono(temp_nna.getDate(18));
                            nna.setFechaResolConsentida(temp_nna.getDate(19));
                            nna.setClasificacion(temp_nna.getString(20));
                            nna.setIncesto(temp_nna.getShort(21));
                            nna.setMental(temp_nna.getShort(22));
                            nna.setEpilepsia(temp_nna.getShort(23));
                            nna.setAbuso(temp_nna.getShort(24));
                            nna.setSifilis(temp_nna.getShort(25));
                            nna.setSeguiMedico(temp_nna.getShort(26));
                            nna.setOperacion(temp_nna.getShort(27));
                            nna.setHiperactivo(temp_nna.getShort(28));
                            nna.setEspecial(temp_nna.getShort(29));
                            nna.setEnfermo(temp_nna.getShort(30));
                            nna.setMayor(temp_nna.getShort(31));
                            nna.setAdolescente(temp_nna.getShort(32));
                            nna.setHermano(temp_nna.getShort(33));
                            nna.setNn(temp_nna.getShort(34));
                            nna.setObservaciones(temp_nna.getString(35));
                            nna.setNResolAband(temp_nna.getString(36));
                            nna.setNResolCons(temp_nna.getString(37));

                            String hql_listaexp = "{call HE_LISTAEXP_BY_IDNNA(?,?)}";
                            CallableStatement statement_listaexp = connection.prepareCall(hql_listaexp);
                            statement_listaexp.setLong(1, idNna);
                            statement_listaexp.registerOutParameter(2, OracleTypes.CURSOR);
                            statement_listaexp.execute();
                            Set<ExpedienteNna> listaExp = new HashSet<ExpedienteNna>();

                            temp_expediente = (ResultSet) statement_listaexp.getObject(2);

                            while (temp_expediente.next()) {

                                expNna.setIdexpedienteNna(temp_expediente.getLong(1));

                                if (temp_expediente.getLong(2) != 0) {

                                    String hql_NNA = "{call HE_GET_NNA(?, ?)}";
                                    CallableStatement statement_NNA = connection.prepareCall(hql_NNA);
                                    statement_NNA.setLong(1, temp_expediente.getLong(2));
                                    statement_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                                    statement_NNA.execute();
                                    ResultSet temp_nna_2 = (ResultSet) statement_NNA.getObject(2);
                                    while (temp_nna_2.next()) {
                                        nna.setIdnna(temp_nna_2.getLong(1));

                                        nna.setCar(car);
                                    }
                                    temp_nna_2.close();
                                    statement_NNA.close();
                                }

                                if (temp_expediente.getLong(3) != 0) {

                                    String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                                    CallableStatement statement3 = connection.prepareCall(hql3);
                                    statement3.setLong(1, temp_expediente.getLong(3));
                                    statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                    statement3.execute();
                                    temp2 = (ResultSet) statement3.getObject(2);
                                    while (temp2.next()) {
                                        unidad.setIdunidad(temp2.getShort(1));
                                        expFamilia.setUnidad(unidad);
                                    }
                                    temp2.close();
                                    statement3.close();
                                }

                                expNna.setNumero(temp_expediente.getString(4));
                                expNna.setFechaIngreso(temp_expediente.getDate(5));
                                expNna.setHt(temp_expediente.getString(6));
                                expNna.setNExpTutelar(temp_expediente.getString(7));
                                expNna.setProcTutelar(temp_expediente.getString(8));
                                expNna.setFichaIntegral(temp_expediente.getShort(9));
                                expNna.setComentarios(temp_expediente.getString(10));
                                expNna.setRespLegalNombre(temp_expediente.getString(11));
                                expNna.setRespLegalP(temp_expediente.getString(12));
                                expNna.setRespLegalM(temp_expediente.getString(13));
                                expNna.setRespPsicosocialNombre(temp_expediente.getString(14));
                                expNna.setRespPiscosocialP(temp_expediente.getString(15));
                                expNna.setRespPsicosocialM(temp_expediente.getString(16));
                                expNna.setEstado(temp_expediente.getString(17));
                                expNna.setFechaEstado(temp_expediente.getDate(18));
                                expNna.setAdoptable(temp_expediente.getShort(19));
                                expNna.setFechaResolCons(temp_expediente.getDate(20));
                                expNna.setNacional(temp_expediente.getShort(21));
                                expNna.setDiagnostico(temp_expediente.getString(22));
                                expNna.setCodigoReferencia(temp_expediente.getString(23));
                                expNna.setNActual(temp_expediente.getString(24));
                                expNna.setApellidopActual(temp_expediente.getString(25));
                                expNna.setApellidomActual(temp_expediente.getString(26));
                                expNna.setObservaciones(temp_expediente.getString(27));
                                expNna.setFechaInvTutelar(temp_expediente.getDate(28));

                                listaExp.add(expNna);

                            }
                            temp_expediente.close();
                            statement_listaexp.close();

                            nna.setExpedienteNnas(listaExp);

                            designacion.setNna(nna);
                        }
                        temp_nna.close();
                        statement_designacion_NNA.close();
                    }
                    if (temp_designacion.getShort(4) != 0) {

                        String hql_designacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_designacion_personal = connection.prepareCall(hql_designacion_personal);
                        statement_designacion_personal.setLong(1, temp_designacion.getShort(4));
                        statement_designacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_personal.execute();
                        temp_personal = (ResultSet) statement_designacion_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getShort(1));
                            designacion.setPersonal(personal);
                        }
                        temp_personal.close();
                        statement_designacion_personal.close();
                    }
                    designacion.setNDesignacion(temp_designacion.getString(5));
                    designacion.setPrioridad(temp_designacion.getLong(6));
                    designacion.setFechaPropuesta(temp_designacion.getDate(7));
                    designacion.setFechaConsejo(temp_designacion.getDate(8));
                    designacion.setAceptacionConsejo(temp_designacion.getShort(9));
                    designacion.setTipoPropuesta(temp_designacion.getString(10));
                    designacion.setObs(temp_designacion.getString(11));

                    allDesig.add(designacion);

                }
                temp_designacion.close();
                statement.close();
            }
        };

        session.doWork(work);
        return allDesig;
    }

//    public ArrayList<PostAdopcion> getListaPostAdopcion() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        ArrayList<PostAdopcion> allPostAdopcion = new ArrayList();
//        ArrayList<Resolucion> allResoluciones = new ArrayList();
//
//        String hql1 = "FROM Resolucion R WHERE R.tipo = :tipo";
//        Query query1 = session.createQuery(hql1);
//        query1.setString("tipo", "adopcion");
//        List tempResoluciones = query1.list();
//
//        for (Iterator iter = tempResoluciones.iterator(); iter.hasNext();) {
//            Resolucion temp = (Resolucion) iter.next();
//            Hibernate.initialize(temp.getEvaluacion().getExpedienteFamilia());
//            allResoluciones.add(temp);
//        }
//
//        String hql2 = "FROM PostAdopcion PA ORDER BY PA.idpostAdopcion ASC";
//        Query query2 = session.createQuery(hql2);
//        List tempPost = query2.list();
//
//        if (!allResoluciones.isEmpty() && !tempPost.isEmpty()) {
//            for (Iterator iter = tempPost.iterator(); iter.hasNext();) {
//                PostAdopcion temp2 = (PostAdopcion) iter.next();
//                for (Resolucion resol : allResoluciones) {
//                    if (resol.getFechaResol().equals(temp2.getFechaResolucion())) {
//                        Set<ExpedienteFamilia> tempExpediente = new HashSet<ExpedienteFamilia>(0);
//                        tempExpediente.add(resol.getEvaluacion().getExpedienteFamilia());
//                        Hibernate.initialize(temp2.getFamilia());
//                        Hibernate.initialize(temp2.getInformePostAdoptivos());
//                        temp2.getFamilia().setExpedienteFamilias(tempExpediente);
//                    }
//                }
//                allPostAdopcion.add(temp2);
//            }
//        }
//
//        return allPostAdopcion;
//    }
    public ArrayList<PostAdopcion> getListaPostAdopcion() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ArrayList<PostAdopcion> allPostAdopcion = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_POST(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<InfoFamilia> listInfo = new HashSet<InfoFamilia>();
                    Set<Evaluacion> listEval = new HashSet<Evaluacion>();
                    Set<Resolucion> listResol = new HashSet<Resolucion>();
                    Set<InformePostAdoptivo> listInformes = new HashSet<InformePostAdoptivo>();

                    Resolucion tempResol = new Resolucion();
                    Evaluacion tempEval = new Evaluacion();
                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    PostAdopcion tempPost = new PostAdopcion();
                    Unidad tempUa = new Unidad();
                    InfoFamilia tempInfo = new InfoFamilia();

                    tempEval.setIdevaluacion(rs.getLong("IDEVALUACION"));

                    tempResol.setIdresolucion(rs.getLong("IDRESOLUCION"));
                    tempResol.setTipo(rs.getString("TIPO"));
                    tempResol.setNumero(rs.getString("NUMERO"));
                    tempResol.setFechaResol(rs.getDate("FECHA_RESOL"));
                    tempResol.setFechaNotificacion(rs.getDate("FECHA_NOTIFICACION"));

                    tempPost.setIdpostAdopcion(rs.getLong("IDPOST_ADOPCION"));
                    tempPost.setNumeroInformes(rs.getLong("NUMERO_INFORMES"));
                    tempPost.setidNna(rs.getLong("IDNNA"));
                    tempPost.setFechaResolucion(rs.getDate("FECHA_RESOLUCION"));

                    String hql4 = "{call REPORTE_POST_INFORME(?,?)}";
                    CallableStatement statement4 = connection.prepareCall(hql4);
                    statement4.setLong(1, tempPost.getIdpostAdopcion());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();

                    ResultSet rs4 = (ResultSet) statement4.getObject(2);

                    while (rs4.next()) {
                        InformePostAdoptivo tempInforme = new InformePostAdoptivo();
                        Personal tempPersonal = new Personal();
                        tempInforme.setFechaRecepcionProyectado(rs4.getDate("FECHA_RECEPCION_PROYECTADO"));
                        tempInforme.setFechaRecepcion(rs4.getDate("FECHA_RECEPCION"));
                        tempPersonal.setNombre(rs4.getString("NOMBRE"));
                        tempPersonal.setApellidoP(rs4.getString("APELLIDO_P"));
                        tempInforme.setFechaInforme(rs4.getDate("FECHA_INFORME"));
                        tempInforme.setEstado(rs4.getString("ESTADO"));
                        tempInforme.setFechaActa(rs4.getDate("FECHA_ACTA"));

                        tempInforme.setPersonal(tempPersonal);
                        listInformes.add(tempInforme);

                    }
                    rs4.close();
                    statement4.close();
                    tempPost.setInformePostAdoptivos(listInformes);

                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
                        Entidad tempEnt = new Entidad();
                        String query2 = "{call RENAD_ENTIDAD(?,?)}";
                        CallableStatement statement2 = connection.prepareCall(query2);
                        statement2.setLong(1, idEntidad);
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        ResultSet rs2 = (ResultSet) statement2.getObject(2);
                        while (rs2.next()) {
                            tempEnt.setIdentidad(rs2.getLong("IDENTIDAD"));
                            tempEnt.setNombre(rs2.getString("NOMBRE"));
                            tempEnt.setUser(rs2.getString("USER_"));
                            tempEnt.setPass(rs2.getString("PASS"));
                            tempEnt.setDireccion(rs2.getString("DIRECCION"));
                            tempEnt.setTelefono(rs2.getString("TELEFONO"));
                            tempEnt.setPais(rs2.getString("PAIS"));
                            tempEnt.setResolAuto(rs2.getString("RESOL_AUTO"));
                            tempEnt.setFechaResol(rs2.getDate("FECHA_RESOL"));
                            tempEnt.setResolRenov(rs2.getString("RESOL_RENOV"));
                            tempEnt.setFechaRenov(rs2.getDate("FECHA_RENOV"));
                            tempEnt.setFechaVenc(rs2.getDate("FECHA_VENC"));
                            tempEnt.setObs(rs2.getString("OBS"));
                        }
                        rs2.close();
                        statement2.close();
                        tempFam.setEntidad(tempEnt);
                    }

                    tempExpFam.setExpediente(rs.getString("EXPEDIENTE"));
                    tempExpFam.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempUa.setNombre(rs.getString("NOMBRE"));
                    tempUa.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUa.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempInfo.setIdinfoFamilia(rs.getLong("IDINFO_FAMILIA"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDepRes(rs.getString("DEP_RES"));

                    Set<Adoptante> listadop = new HashSet<Adoptante>();
                    String query3 = "{call RENAD_ADOPTANTE(?,?)}";
                    CallableStatement statement3 = connection.prepareCall(query3);
                    statement3.setLong(1, tempInfo.getIdinfoFamilia());
                    statement3.registerOutParameter(2, OracleTypes.CURSOR);
                    statement3.execute();
                    ResultSet rs3 = (ResultSet) statement3.getObject(2);
                    while (rs3.next()) {
                        Adoptante tempAdoptante = new Adoptante();
                        tempAdoptante.setIdadoptante(rs3.getLong("IDADOPTANTE"));
                        tempAdoptante.setInfoFamilia(tempInfo);
                        tempAdoptante.setNombre(rs3.getString("NOMBRE"));
                        tempAdoptante.setApellidoP(rs3.getString("APELLIDO_P"));
                        tempAdoptante.setApellidoM(rs3.getString("APELLIDO_M"));

                        String tempsexo = "";
                        tempsexo = rs3.getString("SEXO");
                        if (!rs3.wasNull()) {
                            tempAdoptante.setSexo(tempsexo.charAt(0));
                        }

                        tempAdoptante.setFechaNac(rs3.getDate("FECHA_NAC"));
                        tempAdoptante.setLugarNac(rs3.getString("LUGAR_NAC"));
                        tempAdoptante.setDepaNac(rs3.getString("DEPA_NAC"));
                        tempAdoptante.setPaisNac(rs3.getString("PAIS_NAC"));

                        String tempTipoDoc = "";
                        tempTipoDoc = rs3.getString("TIPO_DOC");
                        if (!rs3.wasNull()) {
                            tempAdoptante.setTipoDoc(tempTipoDoc.charAt(0));
                        }

                        tempAdoptante.setNDoc(rs3.getString("N_DOC"));
                        tempAdoptante.setCelular(rs3.getString("CELULAR"));
                        tempAdoptante.setCorreo(rs3.getString("CORREO"));
                        tempAdoptante.setNivelInstruccion(rs3.getString("NIVEL_INSTRUCCION"));
                        tempAdoptante.setCulminoNivel(rs3.getShort("CULMINO_NIVEL"));
                        tempAdoptante.setProfesion(rs3.getString("PROFESION"));
                        tempAdoptante.setTrabajadorDepend(rs3.getShort("TRABAJADOR_DEPEND"));
                        tempAdoptante.setOcupActualDep(rs3.getString("OCUP_ACTUAL_DEP"));
                        tempAdoptante.setCentroTrabajo(rs3.getString("CENTRO_TRABAJO"));
                        tempAdoptante.setDireccionCentro(rs3.getString("DIRECCION_CENTRO"));
                        tempAdoptante.setTelefonoCentro(rs3.getString("TELEFONO_CENTRO"));
                        tempAdoptante.setIngresoDep(rs3.getLong("INGRESO_DEP"));
                        tempAdoptante.setTrabajadorIndepend(rs3.getShort("TRABAJADOR_INDEPEND"));
                        tempAdoptante.setOcupActualInd(rs3.getString("OCUP_ACTUAL_IND"));
                        tempAdoptante.setIngresoIndep(rs3.getLong("INGRESO_INDEP"));
                        tempAdoptante.setSeguroSalud(rs3.getShort("SEGURO_SALUD"));
                        tempAdoptante.setTipoSeguro(rs3.getString("TIPO_SEGURO"));
                        tempAdoptante.setSeguroVida(rs3.getShort("SEGURO_VIDA"));
                        tempAdoptante.setSistPensiones(rs3.getShort("SIST_PENSIONES"));
                        tempAdoptante.setSaludActual(rs3.getString("SALUD_ACTUAL"));

                        listadop.add(tempAdoptante);

                    }
                    rs3.close();
                    statement3.close();
                    tempInfo.setAdoptantes(listadop);

                    tempExpFam.setUnidad(tempUa);
                    tempExpFam.setFamilia(tempFam);
                    tempEval.setExpedienteFamilia(tempExpFam);
                    tempResol.setEvaluacion(tempEval);
                    listResol.add(tempResol);

                    tempEval.setResolucions(listResol);

                    listEval.add(tempEval);

                    tempExpFam.setEvaluacions(listEval);

                    listExp.add(tempExpFam);

                    tempFam.setExpedienteFamilias(listExp);

                    tempInfo.setFamilia(tempFam);
                    listInfo.add(tempInfo);

                    tempFam.setInfoFamilias(listInfo);

                    tempPost.setFamilia(tempFam);

                    allPostAdopcion.add(tempPost);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allPostAdopcion;
    }

    public void crearRevision(Revision temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = temp.getNna().getIdnna();
        final Long idExp = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final String num = temp.getNumero();
        final Date fechaRev = temp.getFechaRevision();
        final String coment = temp.getComentarios();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_CREAR_REVISION(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.setLong(2, idExp);
                statement.setString(3, num);
                statement.setDate(4, (java.sql.Date) fechaRev);
                statement.setString(5, coment);

                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    public ArrayList<Revision> getListaRevision(long idNna) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<Revision> allRevision = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call HE_GET_LISTA_REVISION(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Revision tempRev = new Revision();
                    tempRev.setIdrevision(rs.getLong("IDREVISION"));
                    tempRev.setNumero(rs.getString("NUMERO"));
                    allRevision.add(tempRev);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allRevision;
    }

    public void updateRevision(String Num, String comments) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final String id = Num;
        final String frase = comments;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_UPDATE_REVISION(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, id);
                statement.setString(2, frase);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    public ArrayList<Revision> getListaRevisionNumero(final String numero) {

        Session session = sessionFactory.getCurrentSession();

        final ArrayList<Revision> allRevision = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_REVISION_NUM(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, numero);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);
                while (rs.next()) {
                    Revision tempRev = new Revision();
                    Nna nna = new Nna();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();

                    tempRev.setIdrevision(rs.getLong("IDREVISION"));
                    if (rs.getLong("IDNNA") != 0) {

                        String hql2 = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, rs.getLong("IDNNA"));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        ResultSet rs2 = (ResultSet) statement2.getObject(2);
                        if (rs2.next()) {
                            nna.setIdnna(rs2.getLong(1));
                            tempRev.setNna(nna);
                        }
                        statement2.close();
                    }
                    if (rs.getLong("IDEXPEDIENTE_FAMILIA") != 0) {

                        String hql3 = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, rs.getLong("IDEXPEDIENTE_FAMILIA"));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        ResultSet rs3 = (ResultSet) statement3.getObject(2);
                        if (rs3.next()) {
                            expFamilia.setIdexpedienteFamilia(rs3.getLong(1));

                            if (rs3.getLong(2) != 0) {

                                String hql4 = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement4 = connection.prepareCall(hql4);
                                statement4.setLong(1, rs3.getLong(2));
                                statement4.registerOutParameter(2, OracleTypes.CURSOR);
                                statement4.execute();
                                ResultSet rs4 = (ResultSet) statement4.getObject(2);
                                if (rs4.next()) {
                                    fam.setIdfamilia(rs4.getLong(1));
                                    expFamilia.setFamilia(fam);
                                }
                                rs4.close();
                                statement4.close();
                            }

                            if (rs3.getLong(3) != 0) {

                                String hql5 = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement5 = connection.prepareCall(hql5);
                                statement5.setLong(1, rs3.getLong(3));
                                statement5.registerOutParameter(2, OracleTypes.CURSOR);
                                statement5.execute();
                                ResultSet rs5 = (ResultSet) statement5.getObject(2);
                                if (rs5.next()) {
                                    unidad.setIdunidad(rs5.getLong(1));
                                    expFamilia.setUnidad(unidad);
                                }
                                rs5.close();
                                statement5.close();
                            }

                            expFamilia.setNumero(rs3.getLong(4));
                            expFamilia.setExpediente(rs3.getString(5));
                            expFamilia.setHt(rs3.getString(6));
                            expFamilia.setNumeroExpediente(rs3.getString(7));
                            expFamilia.setFechaIngresoDga(rs3.getDate(8));
                            expFamilia.setEstado(rs3.getString(9));
                            expFamilia.setTupa(rs3.getDate(10));
                            expFamilia.setNacionalidad(rs3.getString(11));
                            expFamilia.setRnsa(rs3.getShort(12));
                            expFamilia.setRnaa(rs3.getShort(13));
                            expFamilia.setTipoFamilia(rs3.getString(14));
                            expFamilia.setTipoListaEspera(rs3.getString(15));
                            expFamilia.setHtFicha(rs3.getString(16));
                            expFamilia.setnFicha(rs3.getString(17));
                            expFamilia.setFechaIngresoFicha(rs3.getDate(18));

                            tempRev.setExpedienteFamilia(expFamilia);
                        }
                        rs3.close();
                        statement3.close();
                    }

                    tempRev.setNumero(rs.getString("NUMERO"));
                    tempRev.setFechaRevision(rs.getDate("FECHA_REVISION"));
                    tempRev.setComentarios(rs.getString("COMENTARIOS"));

                    allRevision.add(tempRev);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allRevision;
    }

    public void crearEvaluacionAdopcion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idExp = temp.getExpedienteFamilia().getIdexpedienteFamilia();
        final Long idPer = temp.getPersonal().getIdpersonal();
        final String tipoEval = temp.getTipo();
        final Date fechaAsig = temp.getFechaAsignacion();
        final String result = temp.getResultado();
        final Date fechaResul = temp.getFechaResultado();
        final String obs = temp.getObservacion();
        final String sust = temp.getSustento();
        final String ndesig = temp.getNDesignacion();
        final String numEval = temp.getNumEval();
        final String persInt = temp.getPersInt();
        final Long numPersInte = temp.getNumPersInt();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_CREAR_EVAL_ADOP(?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.setLong(2, idPer);
                statement.setString(3, tipoEval);
                statement.setDate(4, (java.sql.Date) fechaAsig);
                statement.setString(5, result);
                statement.setDate(6, (java.sql.Date) fechaResul);
                statement.setString(7, obs);
                statement.setString(8, sust);
                statement.setString(9, ndesig);
                statement.setString(10, numEval);
                statement.setString(11, persInt);
                statement.setLong(12, numPersInte);
                statement.execute();
                statement.close();
            }
        };

        session.doWork(work);

    }

    public ArrayList<ExpedienteFamilia> getListaEsperaAdopcionInter() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<ExpedienteFamilia> allEspera = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_GET_LISTA_ESPERA_INTER(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                temp_expediente = (ResultSet) statement.getObject(1);

                while (temp_expediente.next()) {
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();
                    Personal personal = new Personal();

                    expFamilia.setIdexpedienteFamilia(temp_expediente.getLong(1));
                    if (temp_expediente.getLong(2) != 0) {

                        String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                        CallableStatement statement2 = connection.prepareCall(hql2);
                        statement2.setLong(1, temp_expediente.getLong(2));
                        statement2.registerOutParameter(2, OracleTypes.CURSOR);
                        statement2.execute();
                        temp2 = (ResultSet) statement2.getObject(2);
                        while (temp2.next()) {
                            fam.setIdfamilia(temp2.getLong(1));
                            expFamilia.setFamilia(fam);
                        }
                        temp2.close();
                        statement2.close();
                    }
                    if (temp_expediente.getLong(3) != 0) {

                        String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, temp_expediente.getLong(3));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        temp2 = (ResultSet) statement3.getObject(2);
                        while (temp2.next()) {
                            unidad.setIdunidad(temp2.getLong(1));
                            unidad.setDepartamento(temp2.getString("DEPARTAMENTO"));
                            expFamilia.setUnidad(unidad);
                        }
                        temp2.close();
                        statement3.close();
                    }
                    expFamilia.setNumero(temp_expediente.getLong(4));
                    expFamilia.setExpediente(temp_expediente.getString(5));
                    expFamilia.setHt(temp_expediente.getString(6));
                    expFamilia.setNumeroExpediente(temp_expediente.getString(7));
                    expFamilia.setFechaIngresoDga(temp_expediente.getDate(8));
                    expFamilia.setEstado(temp_expediente.getString(9));
                    expFamilia.setTupa(temp_expediente.getDate(10));
                    expFamilia.setNacionalidad(temp_expediente.getString(11));
                    expFamilia.setRnsa(temp_expediente.getShort(12));
                    expFamilia.setRnaa(temp_expediente.getShort(13));
                    expFamilia.setTipoFamilia(temp_expediente.getString(14));
                    expFamilia.setTipoListaEspera(temp_expediente.getString(15));
                    expFamilia.setHtFicha(temp_expediente.getString(16));
                    expFamilia.setnFicha(temp_expediente.getString(17));
                    expFamilia.setFechaIngresoFicha(temp_expediente.getDate(18));

                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();

                    String hql2 = "{call HE_GET_LISTA_EVALUACIONES(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, expFamilia.getIdexpedienteFamilia());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        Evaluacion tempEval = new Evaluacion();
                        tempEval.setIdevaluacion(rs2.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(expFamilia);

                        tempEval.setTipo(rs2.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs2.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs2.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs2.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs2.getString("OBSERVACION"));
                        tempEval.setSustento(rs2.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs2.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs2.getString("NUM_EVAL"));
                        Set<Resolucion> listaRe = new HashSet<Resolucion>();

                        if (tempEval.getTipo().equals("legal")) {

                            String hql3 = "{call HE_GET_RESOLUCION_APTA(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, tempEval.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                Resolucion tempResol = new Resolucion();
                                tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempResol.setTipo(rs3.getString("TIPO"));
                                tempResol.setNumero(rs3.getString("NUMERO"));
                                tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                tempResol.setEvaluacion(tempEval);

                                listaRe.add(tempResol);

                            }
                            rs3.close();
                            statement3.close();

                        }

                        tempEval.setResolucions(listaRe);
                        listaEv.add(tempEval);

                    }
                    rs2.close();
                    statement2.close();

                    expFamilia.setEvaluacions(listaEv);
                    allEspera.add(expFamilia);
                }
                temp_expediente.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allEspera;
    }

    public ArrayList<Entidad> listaEntidadesRevision(String ent) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String BuscarEnt = ent;
        final ArrayList<Entidad> allEnt = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_BUSCAR_ENTIDAD(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, BuscarEnt);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Entidad tempEnt = new Entidad();
                    tempEnt.setIdentidad(rs.getLong("IDENTIDAD"));
                    tempEnt.setNombre(rs.getString("NOMBRE"));
                    tempEnt.setPais(rs.getString("PAIS"));
                    allEnt.add(tempEnt);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allEnt;
    }

    public ArrayList<Long> listaNnaDeRevision(String numRev) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String identificador = numRev;
        final ArrayList<Long> allID = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_REVISION_NNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, identificador);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    allID.add(rs.getLong("IDNNA"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allID;
    }

    public ArrayList<Long> listaExpedientesDeRevision(String numRev) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String identificador = numRev;
        final ArrayList<Long> allID = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_REVISION_EXPFAM(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, identificador);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    allID.add(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allID;
    }

    public ArrayList<Long> listaOrganismosDeRevision(String numRev) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String identificador = numRev;
        final ArrayList<Long> allID = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_REVISION_ENTIDAD(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, identificador);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    allID.add(rs.getLong("IDENTIDAD"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allID;
    }

    public ArrayList<EstudioCaso> listaExpedientesDeEstudio(String orden) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String identificador = orden;
        final ArrayList<EstudioCaso> listaEstudios = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_ESTUDIO_EXPFAM(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, identificador);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    EstudioCaso estTemp = new EstudioCaso();
                    ExpedienteFamilia tempExp = new ExpedienteFamilia();
                    estTemp.setIdestudioCaso(rs.getLong("IDESTUDIO_CASO"));
                    tempExp.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    estTemp.setOrden(rs.getString("ORDEN"));
                    estTemp.setFechaEstudio(rs.getDate("FECHA_ESTUDIO"));
                    estTemp.setFechaSolAdop(rs.getDate("FECHA_SOL_ADOP"));
                    estTemp.setResultado(rs.getString("RESULTADO"));
                    estTemp.setPrioridad(rs.getLong("PRIORIDAD"));
                    estTemp.setNSolicitud(rs.getLong("N_SOLICITUD"));
                    String hql2 = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();
                    ResultSet rs2 = (ResultSet) statement2.getObject(2);
                    if (rs2.next()) {
                        tempExp.setExpediente(rs2.getString(5));
                    }
                    rs2.close();
                    statement2.close();
                    estTemp.setExpedienteFamilia(tempExp);
                    listaEstudios.add(estTemp);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return listaEstudios;
    }

    public ArrayList<Long> listaNnaDeEstudio(String ORDEN) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final String identificador = ORDEN;
        final ArrayList<Long> allID = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call HE_ESTUDIO_NNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, identificador);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    allID.add(rs.getLong("IDNNA"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allID;
    }

}
