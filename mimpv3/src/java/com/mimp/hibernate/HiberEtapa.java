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
               
                while(rs.next()){
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
               
                                if(rs2.next()){
                                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                                    tempExpFam.setIdexpedienteFamilia(rs2.getLong("IDEXPEDIENTE_FAMILIA"));
                                    tempExpFam.setExpediente(rs2.getString("EXPEDIENTE"));
                                    tempExpFam.setHt(rs2.getString("HT"));
                
                                    listExp.add(tempExpFam);
                                }
                                
                        statement2.close();
                                
                        String hql3 = "{call HE_AFR_BY_IDFAM(?,?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, tempFamp.getIdfamilia());
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                
                        ResultSet rs3 = (ResultSet) statement3.getObject(2);
               
                                while(rs3.next()){
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
                        statement3.close();
                                
                        String hql4 = "{call HE_GET_FS_BY_IDFAM(?,?)}";
                        CallableStatement statement4 = connection.prepareCall(hql4);
                        statement4.setLong(1, tempFamp.getIdfamilia());
                        statement4.registerOutParameter(2, OracleTypes.CURSOR);
                        statement4.execute();
                
                        ResultSet rs4 = (ResultSet) statement4.getObject(2);
               
                                if(rs4.next()){
                                   FormularioSesion tempFS = new FormularioSesion();
                                   tempFS.setIdformularioSesion(rs4.getLong("IDFORMULARIO_SESION"));
                                   Set<Asistente> listA = new HashSet<Asistente>();   
                                   
                                                String hql5 = "{call HE_GET_ASIS_BY_IDFS(?,?)}";
                                                CallableStatement statement5 = connection.prepareCall(hql5);
                                                statement5.setLong(1, tempFS.getIdformularioSesion());
                                                statement5.registerOutParameter(2, OracleTypes.CURSOR);
                                                statement5.execute();
                                                ResultSet rs5 = (ResultSet) statement5.getObject(2);
                                                
                                                while(rs5.next()){
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
                                                statement5.close();
                                              
                                      tempFS.setAsistentes(listA);
                                      listFS.add(tempFS);
                                }           
                                statement4.close();
                                
                          tempFamp.setExpedienteFamilias(listExp);
                          tempFamp.setAsistenciaFRs(listAFR);
                          tempFamp.setFormularioSesions(listFS);
                            
                          allFamilias.add(tempFamp);
                }
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
                        statement2.close();
                    }
                    fam.setUser(temp.getString(3));
                    fam.setPass(temp.getString(4));
                    fam.setCorreo(temp.getString(5));
                    fam.setHabilitado(temp.getShort(6));
                    fam.setConstancia(temp.getString(7));

                }
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
                            expFamilia.setUnidad(unidad);
                        }
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
                        statement2.close();
                    }
                    if (temp.getShort(3) != 0) {

                        String hql3 = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement3 = connection.prepareCall(hql3);
                        statement3.setLong(1, temp.getShort(3));
                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                        statement3.execute();
                        temp2 = (ResultSet) statement3.getObject(2);
                        while (temp2.next()) {
                            personal.setIdpersonal(temp2.getShort(1));
                            tempEval.setPersonal(personal);
                        }
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
                }
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
                                        statement_expediente_familia_entidad.close();
                                    }
                                    fam.setUser(temp_familia.getString(3));
                                    fam.setPass(temp_familia.getString(4));
                                    fam.setCorreo(temp_familia.getString(5));
                                    fam.setHabilitado(temp_familia.getShort(6));
                                    fam.setConstancia(temp_familia.getString(7));
                                    expFamilia.setFamilia(fam);
                                }
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
                                statement_evaluacion_personal.close();
                            }
                            evaluacion.setTipo(temp.getString(4));
                            evaluacion.setFechaAsignacion(temp.getDate(5));
                            evaluacion.setResultado(temp.getString(6));
                            evaluacion.setFechaResultado(temp.getDate(7));
                            evaluacion.setObservacion(temp.getString(8));
                            evaluacion.setSustento(temp.getString(9));
                            evaluacion.setNDesignacion(temp.getString(10));
                            evaluacion.setNumEval(temp.getString(11));

                            resolucion.setEvaluacion(evaluacion);
                        }
                        statement_resolucion_evaluacion.close();
                    }

                    resolucion.setTipo(temp_resolucion.getString(3));
                    resolucion.setNumero(temp_resolucion.getString(4));
                    resolucion.setFechaResol(temp_resolucion.getDate(5));
                    resolucion.setFechaNotificacion(temp_resolucion.getDate(6));

                }
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
                    resolucion.setIdresolucion(temp_resolucion.getShort(1));
                    if (temp_resolucion.getShort(2) != 0) {

                        String hql_resolucion_evaluacion = "{call HE_GET_EVALUACION(?, ?)}";
                        CallableStatement statement_resolucion_evaluacion = connection.prepareCall(hql_resolucion_evaluacion);
                        statement_resolucion_evaluacion.setLong(1, temp_resolucion.getShort(2));
                        statement_resolucion_evaluacion.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_resolucion_evaluacion.execute();
                        temp2 = (ResultSet) statement_resolucion_evaluacion.getObject(2);
                        while (temp2.next()) {
                            tempEval.setIdevaluacion(temp2.getShort(1));
                            resolucion.setEvaluacion(tempEval);
                        }
                        statement_resolucion_evaluacion.close();
                    }

                    resolucion.setTipo(temp_resolucion.getString(3));
                    resolucion.setNumero(temp_resolucion.getString(4));
                    resolucion.setFechaResol(temp_resolucion.getDate(5));
                    resolucion.setFechaNotificacion(temp_resolucion.getDate(6));

                }
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
               
                if(rs.next()){
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
               
                                while(rs2.next()){
                                    Resolucion tempRe = new Resolucion();
                                    tempRe.setIdresolucion(rs2.getLong("IDRESOLUCION"));
                                    tempRe.setTipo(rs2.getString("TIPO"));
                                    tempRe.setNumero(rs2.getString("NUMERO"));
                                    tempRe.setFechaResol(rs2.getDate("FECHA_RESOL"));
                                    tempRe.setFechaNotificacion(rs2.getDate("FECHA_NOTIFICACION"));
                                    tempRe.setEvaluacion(tempEval);
                                    listaRe.add(tempRe);
                                    
                                }
                                
                        statement2.close();
                        
                        tempEval.setResolucions(listaRe);
                        
                }
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
               
                while(rs.next()){
                    Set<Evaluacion> listaEv = new HashSet<Evaluacion>();
                    
                    
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    
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
               
                                while(rs2.next()){
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
                                    
                                    if(tempEval.getTipo().equals("legal"))
                                    {
                                        
                                        String hql3 = "{call HE_ULTRESOL_LEGAL(?,?)}";
                                        CallableStatement statement3 = connection.prepareCall(hql3);
                                        statement3.setLong(1, tempEval.getIdevaluacion());
                                        statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                        statement3.execute();
                                        
                                        ResultSet rs3 = (ResultSet) statement3.getObject(2);
                                        
                                        if(rs3.next()){
                                            Resolucion tempResol = new Resolucion();
                                            tempResol.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                            tempResol.setTipo(rs3.getString("TIPO"));
                                            tempResol.setNumero(rs3.getString("NUMERO"));
                                            tempResol.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                            tempResol.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                                            tempResol.setEvaluacion(tempEval);
                                            
                                            listaRe.add(tempResol);
                                            
                                            
                                        }
                                        statement3.close();
                                        
                                    }
                                    
                                    tempEval.setResolucions(listaRe);
                                    listaEv.add(tempEval);
                                    
                                }
                                
                        statement2.close();
                    
                    
                    tempEF.setEvaluacions(listaEv);
                    allExpedienteFamilia.add(tempEF);
                }
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
                statement.setLong(1,idExp );
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

    public void crearEvaluacion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateEvaluacion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public void crearResolEvaluacion(Resolucion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateResolEvaluacion(Resolucion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public ArrayList<ExpedienteFamilia> listaInfoFamilias() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "from ExpedienteFamilia EF where (EF.estado = :estado) or (EF.estado = :estado2) or (EF.estado = :estado3)";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        query.setString("estado2", "adopcion");
        query.setString("estado3", "post");
        List expedientes = query.list();
        ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getFamilia());
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
            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("id", temp.getFamilia().getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult = query2.uniqueResult();
            InfoFamilia tempIf = (InfoFamilia) queryResult;
            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
            tempInfFam.add(tempIf);
            temp.getFamilia().setInfoFamilias(tempInfFam);
            allInfoFam.add(temp);
        }

        return allInfoFam;

    }

    public ArrayList<ExpedienteFamilia> listaInfoFamilias(String exp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql = "from ExpedienteFamilia EF where (EF.estado = :estado) or (EF.estado = :estado2) or (EF.estado = :estado3) and EF.expediente like :exp";
        Query query = session.createQuery(hql);
        query.setString("estado", "espera");
        query.setString("estado2", "adopcion");
        query.setString("estado3", "estudio");
        query.setString("exp", '%' + exp + '%');
        List expedientes = query.list();
        ArrayList<ExpedienteFamilia> allInfoFam = new ArrayList();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
            ExpedienteFamilia temp = (ExpedienteFamilia) iter.next();
            Hibernate.initialize(temp.getFamilia());
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
            String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
            Query query2 = session.createQuery(hql2);
            query2.setLong("id", temp.getFamilia().getIdfamilia());
            query2.setMaxResults(1);
            Object queryResult = query2.uniqueResult();
            InfoFamilia tempIf = (InfoFamilia) queryResult;
            Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
            tempInfFam.add(tempIf);
            temp.getFamilia().setInfoFamilias(tempInfFam);
            allInfoFam.add(temp);
        }

        return allInfoFam;

    }

    public ExpedienteFamilia getInfoFamilia(long id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ExpedienteFamilia expFamilia = new ExpedienteFamilia();
        String hqlA = "FROM ExpedienteFamilia EF WHERE EF.id = :id";

        Query queryA = session.createQuery(hqlA);
        queryA.setLong("id", id);
        Object queryResultA = queryA.uniqueResult();

        expFamilia = (ExpedienteFamilia) queryResultA;
        Hibernate.initialize(expFamilia.getFamilia());
        Hibernate.initialize(expFamilia.getEvaluacions());
        Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
        for (Evaluacion eval : expFamilia.getEvaluacions()) {
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
        expFamilia.setEvaluacions(tempEvaluaciones);
        String hql2 = "from InfoFamilia IF where IF.familia = :id ORDER BY IF.idinfoFamilia DESC";
        Query query2 = session.createQuery(hql2);
        query2.setLong("id", expFamilia.getFamilia().getIdfamilia());
        query2.setMaxResults(1);
        Object queryResult = query2.uniqueResult();
        InfoFamilia tempIf = (InfoFamilia) queryResult;
        Set<InfoFamilia> tempInfFam = new HashSet<InfoFamilia>(0);
        tempInfFam.add(tempIf);
        expFamilia.getFamilia().setInfoFamilias(tempInfFam);

        return expFamilia;

    }

    public void crearDesignacion(Designacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateDesignacion(Designacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public ArrayList<Designacion> getListaDesignaciones() {

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
    }

    public ArrayList<Designacion> getListaDesignaciones(long idNna) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo and D.nna = :id ORDER BY D.fechaPropuesta DESC";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("1"));
        query.setLong("id", idNna);
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
            Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia());
            Hibernate.initialize(temp.getNna().getExpedienteNnas());
            allDesig.add(temp);
        }
        return allDesig;
    }

    public ArrayList<Designacion> getListaDesignaciones2(long idNna) {

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
    }

    public ArrayList<Designacion> getListaDesignacionesDeFamilia(long idExpFam) {

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
    }

    public ArrayList<Designacion> getListaAdopciones() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<Designacion> allDesig = new ArrayList();
        String hql = "FROM Designacion D WHERE D.aceptacionConsejo = :aceptacionConsejo ORDER BY D.fechaConsejo ASC";
        Query query = session.createQuery(hql);
        query.setShort("aceptacionConsejo", Short.parseShort("0"));
        List expedientes = query.list();
        for (Iterator iter = expedientes.iterator(); iter.hasNext();) {
            Designacion temp = (Designacion) iter.next();
            Hibernate.initialize(temp.getExpedienteFamilia().getEvaluacions());
            Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
            for (Evaluacion eval : temp.getExpedienteFamilia().getEvaluacions()) {
                if (eval.getTipo().equals("empatia") || eval.getTipo().equals("informe")) {
                    String hql2 = "from Resolucion R where R.evaluacion = :idEvaluacion ORDER BY R.fechaResol ASC";
                    Query query2 = session.createQuery(hql2);
                    query2.setLong("idEvaluacion", eval.getIdevaluacion());
                    query2.setMaxResults(1);
                    Object queryResult = query2.uniqueResult();
                    Resolucion tempResol = (Resolucion) queryResult;
                    Set<Resolucion> tempResoluciones = new HashSet<Resolucion>(0);
                    tempResoluciones.add(tempResol);
                    eval.setResolucions(tempResoluciones);
                    tempEvaluaciones.add(eval);

                }

            }
            temp.getExpedienteFamilia().setEvaluacions(tempEvaluaciones);
            Hibernate.initialize(temp.getNna());
            allDesig.add(temp);
        }
        return allDesig;
    }

    public void deleteResolucion(Resolucion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(temp);

    }

    public void deleteEvaluacion(Evaluacion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(temp);

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
                        statement_designacion_personal.close();
                    }
                    designacion.setNDesignacion(temp_designacion.getLong(5));
                    designacion.setPrioridad(temp_designacion.getLong(6));
                    designacion.setFechaPropuesta(temp_designacion.getDate(7));
                    designacion.setFechaConsejo(temp_designacion.getDate(8));
                    designacion.setAceptacionConsejo(temp_designacion.getShort(9));
                    designacion.setTipoPropuesta(temp_designacion.getString(10));
                    designacion.setObs(temp_designacion.getString(11));
                }
                statement_designacion.close();
            }
        };
        session.doWork(work);
        return designacion;

    }

    public void crearEstudioCaso(EstudioCaso temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateEstudioCaso(EstudioCaso temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public ArrayList<EstudioCaso> getListaEstudioCaso(long idNna) {

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
        session.beginTransaction();
        final ArrayList<EstudioCaso> allEstudioCaso = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call HE_GET_LISTA_ESTUDIO_CASO_ORDEN(?,?)}";
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

                    estudio.setIdestudioCaso(temp_estudio.getShort(1));
                    if (temp_estudio.getShort(2) != 0) {

                        String hql_estudio_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_estudio_NNA = connection.prepareCall(hql_estudio_NNA);
                        statement_estudio_NNA.setLong(1, temp_designacion.getShort(2));
                        statement_estudio_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_NNA.execute();
                        temp_nna = (ResultSet) statement_estudio_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));
                            estudio.setNna(nna);
                        }
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

                            if (temp_expediente.getShort(2) != 0) {

                                String hql_designacion_expediente_familia = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement_designacion_expediente_familia = connection.prepareCall(hql_designacion_expediente_familia);
                                statement_designacion_expediente_familia.setLong(1, temp_expediente.getShort(2));
                                statement_designacion_expediente_familia.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_familia.execute();
                                temp_familia = (ResultSet) statement_designacion_expediente_familia.getObject(2);
                                while (temp_familia.next()) {
                                    fam.setIdfamilia(temp_familia.getShort(1));
                                    expFamilia.setFamilia(fam);
                                }
                                statement_designacion_expediente_familia.close();
                            }

                            if (temp_expediente.getShort(3) != 0) {

                                String hql_designacion_expediente_unidad = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement_designacion_expediente_unidad = connection.prepareCall(hql_designacion_expediente_unidad);
                                statement_designacion_expediente_unidad.setLong(1, temp_expediente.getShort(3));
                                statement_designacion_expediente_unidad.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_unidad.execute();
                                temp_unidad = (ResultSet) statement_designacion_expediente_unidad.getObject(2);
                                while (temp_unidad.next()) {
                                    unidad.setIdunidad(temp_unidad.getShort(1));
                                    expFamilia.setUnidad(unidad);
                                }
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
                        statement_estudio_expFamilia.close();
                    }

                    estudio.setOrden(temp_estudio.getString(4));
                    estudio.setFechaEstudio(temp_estudio.getDate(5));
                    estudio.setFechaSolAdop(temp_estudio.getDate(6));
                    estudio.setResultado(temp_estudio.getString(7));
                    estudio.setPrioridad(temp_estudio.getLong(8));
                    estudio.setNSolicitud(temp_estudio.getBigDecimal(9));

                    allEstudioCaso.add(estudio);

                }

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
                    estudio.setIdestudioCaso(temp_estudio.getShort(1));
                    if (temp_estudio.getShort(2) != 0) {

                        String hql_estudio_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_estudio_NNA = connection.prepareCall(hql_estudio_NNA);
                        statement_estudio_NNA.setLong(1, temp_designacion.getShort(2));
                        statement_estudio_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_estudio_NNA.execute();
                        temp_nna = (ResultSet) statement_estudio_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getShort(1));
                            estudio.setNna(nna);
                        }
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

                            if (temp_expediente.getShort(2) != 0) {

                                String hql_designacion_expediente_familia = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement_designacion_expediente_familia = connection.prepareCall(hql_designacion_expediente_familia);
                                statement_designacion_expediente_familia.setLong(1, temp_expediente.getShort(2));
                                statement_designacion_expediente_familia.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_familia.execute();
                                temp_familia = (ResultSet) statement_designacion_expediente_familia.getObject(2);
                                while (temp_familia.next()) {
                                    fam.setIdfamilia(temp_familia.getShort(1));
                                    expFamilia.setFamilia(fam);
                                }
                                statement_designacion_expediente_familia.close();
                            }

                            if (temp_expediente.getShort(3) != 0) {

                                String hql_designacion_expediente_unidad = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement_designacion_expediente_unidad = connection.prepareCall(hql_designacion_expediente_unidad);
                                statement_designacion_expediente_unidad.setLong(1, temp_expediente.getShort(3));
                                statement_designacion_expediente_unidad.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_expediente_unidad.execute();
                                temp_unidad = (ResultSet) statement_designacion_expediente_unidad.getObject(2);
                                while (temp_unidad.next()) {
                                    unidad.setIdunidad(temp_unidad.getShort(1));
                                    expFamilia.setUnidad(unidad);
                                }
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
                        statement_estudio_expFamilia.close();
                    }

                    estudio.setOrden(temp_estudio.getString(4));
                    estudio.setFechaEstudio(temp_estudio.getDate(5));
                    estudio.setFechaSolAdop(temp_estudio.getDate(6));
                    estudio.setResultado(temp_estudio.getString(7));
                    estudio.setPrioridad(temp_estudio.getLong(8));
                    estudio.setNSolicitud(temp_estudio.getBigDecimal(9));
                }
                statement_estudio.close();
            }
        };
        session.doWork(work);
        return estudio;

    }

    public void crearPostAdopcion(PostAdopcion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updatePostAdopcion(PostAdopcion temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public void crearInformePost(InformePostAdoptivo temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(temp);

    }

    public void updateInformePost(InformePostAdoptivo temp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.update(temp);

    }

    public ArrayList<PostAdopcion> getListaPostAdopcion() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<PostAdopcion> allPostAdopcion = new ArrayList();
        ArrayList<Resolucion> allResoluciones = new ArrayList();

        String hql1 = "FROM Resolucion R WHERE R.tipo = :tipo";
        Query query1 = session.createQuery(hql1);
        query1.setString("tipo", "adopcion");
        List tempResoluciones = query1.list();

        for (Iterator iter = tempResoluciones.iterator(); iter.hasNext();) {
            Resolucion temp = (Resolucion) iter.next();
            Hibernate.initialize(temp.getEvaluacion().getExpedienteFamilia());
            allResoluciones.add(temp);
        }

        String hql2 = "FROM PostAdopcion PA ORDER BY PA.idpostAdopcion ASC";
        Query query2 = session.createQuery(hql2);
        List tempPost = query2.list();

        if (!allResoluciones.isEmpty() && !tempPost.isEmpty()) {
            for (Iterator iter = tempPost.iterator(); iter.hasNext();) {
                PostAdopcion temp2 = (PostAdopcion) iter.next();
                for (Resolucion resol : allResoluciones) {
                    if (resol.getFechaResol().equals(temp2.getFechaResolucion())) {
                        Set<ExpedienteFamilia> tempExpediente = new HashSet<ExpedienteFamilia>(0);
                        tempExpediente.add(resol.getEvaluacion().getExpedienteFamilia());
                        Hibernate.initialize(temp2.getFamilia());
                        Hibernate.initialize(temp2.getInformePostAdoptivos());
                        temp2.getFamilia().setExpedienteFamilias(tempExpediente);
                    }
                }
                allPostAdopcion.add(temp2);
            }
        }

        return allPostAdopcion;
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

                temp_postadopcion = (ResultSet) statement_postadopcion.getObject(3);
                while (temp_postadopcion.next()) {
                    postadopcion.setIdpostAdopcion(temp_postadopcion.getShort(1));
                    if (temp_postadopcion.getShort(2) != 0) {

                        String hql_postadopcion_familia = "{call HE_GETFAMILIA(?, ?)}";
                        CallableStatement statement_postadopcion_familia = connection.prepareCall(hql_postadopcion_familia);
                        statement_postadopcion_familia.setLong(1, temp_postadopcion.getShort(2));
                        statement_postadopcion_familia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_postadopcion_familia.execute();
                        temp_familia = (ResultSet) statement_postadopcion_familia.getObject(2);
                        while (temp_familia.next()) {
                            fam.setIdfamilia(temp_familia.getShort(1));
                            expFamilia.setFamilia(fam);
                        }
                        statement_postadopcion_familia.close();
                    }

                    postadopcion.setNumeroInformes(temp_postadopcion.getLong(3));
                    postadopcion.setFechaResolucion(temp_postadopcion.getDate(4));
                    postadopcion.setidNna(temp_postadopcion.getLong(5));

                }
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
                                statement3.setLong(1, temp_personal.getShort(3));
                                statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                statement3.execute();
                                temp2 = (ResultSet) statement3.getObject(2);
                                while (temp2.next()) {
                                    unidad.setIdunidad(temp2.getShort(1));
                                    personal.setUnidad(unidad);
                                }
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

                temp_infopost = (ResultSet) statement_infopost.getObject(3);
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
                statement_infopost.close();
            }
        };
        session.doWork(work);

        return infopost;

    }

    public ArrayList<ExpedienteFamilia> getListaEspera() {

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
                            expFamilia.setUnidad(unidad);
                        }
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

                statement.close();
            }
        };

        session.doWork(work);

        return allReevaluacion;
    }

}
