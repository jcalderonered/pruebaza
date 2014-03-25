/*
 * To change this license header, choose License Headers in Project 
 Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.hibernate;

import com.mimp.bean.*;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service("HiberReporte")
@Transactional
public class HiberReporte {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "HiberEtapa")
    private HiberEtapa servicioEtapa = new HiberEtapa();

//    public ArrayList<Organismo> ReporteOrganismo() {
//
//        Session session = sessionFactory.getCurrentSession();
//
//        session.beginTransaction();
//
//        String hql = "FROM Organismo";
//        Query query = session.createQuery(hql);
//        List organismos = query.list();
//        ArrayList<Organismo> allOrganismos = new ArrayList();
//        for (Iterator iter = organismos.iterator(); iter.hasNext();) {
//            Organismo temp = (Organismo) iter.next();
//            Hibernate.initialize(temp.getEntidad());
//            Hibernate.initialize(temp.getRepresentantes());
//            allOrganismos.add(temp);
//        }
//        return allOrganismos;
//    }

    public ArrayList<Organismo> ReporteOrganismo2() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        final ArrayList<Organismo> allOrganismos = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call REPORTE_ORGANISMO(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {

                    Set<Representante> listaRep = new HashSet<Representante>(0);
                    Organismo tempOrg = new Organismo();
                    Entidad tempEnt = new Entidad();
                    Representante tempRep = new Representante();

                    tempEnt.setIdentidad(rs.getLong("IDENTIDAD"));
                    tempEnt.setNombre(rs.getString(5));
                    tempEnt.setUser(rs.getString("USER_"));
                    tempEnt.setPass(rs.getString("PASS"));
                    tempEnt.setDireccion(rs.getString(8));
                    tempEnt.setTelefono(rs.getString(9));
                    tempEnt.setPais(rs.getString(10));
                    tempEnt.setResolAuto(rs.getString(11));
                    tempEnt.setFechaResol(rs.getDate(12));
                    tempEnt.setResolRenov(rs.getString(13));
                    tempEnt.setFechaRenov(rs.getDate(14));
                    tempEnt.setFechaVenc(rs.getDate(15));
                    tempEnt.setObs(rs.getString(16));
                    tempEnt.setCorreo(rs.getString(17));

                    tempOrg.setIdorganismo(rs.getLong("IDORGANISMO"));
                    tempOrg.setCompetencia(rs.getString("COMPETENCIA"));
                    tempOrg.setEntidad(tempEnt);

                    tempRep.setIdrepresentante(rs.getLong(18));
                    tempRep.setOrganismo(tempOrg);
                    tempRep.setNombre(rs.getString(20));
                    tempRep.setApellidoP(rs.getString(21));
                    tempRep.setApelldoM(rs.getString(22));
                    tempRep.setFechaAuto(rs.getDate(23));
                    tempRep.setFechaRenov(rs.getDate(24));
                    tempRep.setFechaVencAuto(rs.getDate(25));
                    tempRep.setCorreo(rs.getString(26));
                    tempRep.setDireccion(rs.getString(27));
                    tempRep.setCelular(rs.getString(28));
                    tempRep.setTelefono(rs.getString(29));
                    tempRep.setObs(rs.getString(30));

                    listaRep.add(tempRep);
                    tempOrg.setRepresentantes(listaRep);

                    allOrganismos.add(tempOrg);
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allOrganismos;
    }

//    public ArrayList<PostAdopcion> ReportePostAdopcion() {
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
//            Hibernate.initialize(temp.getEvaluacion().getExpedienteFamilia().getUnidad());
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
//                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
//                        tempExpFam = resol.getEvaluacion().getExpedienteFamilia();
//                        Set<Evaluacion> tempEvaluaciones = new HashSet<Evaluacion>(0);
//                        Hibernate.initialize(tempExpFam.getEvaluacions());
//                        for (Evaluacion evaluacion : tempExpFam.getEvaluacions()) {
//                            if (evaluacion.getTipo().equals("informe")) {
//                                Hibernate.initialize(evaluacion.getResolucions());
//                                tempEvaluaciones.add(evaluacion);
//                            }
//                        }
//                        tempExpFam.setEvaluacions(tempEvaluaciones);
//
//                        tempExpediente.add(tempExpFam);
//                        Hibernate.initialize(temp2.getFamilia().getEntidad());
//                        Hibernate.initialize(temp2.getFamilia().getInfoFamilias());
//                        Set<InfoFamilia> tempInfoFam = new HashSet<InfoFamilia>(0);
//                        for (InfoFamilia infofam : temp2.getFamilia().getInfoFamilias()) {
//                            String hql3 = "from Adoptante A where A.infoFamilia = :idinfofam";
//                            Query query3 = session.createQuery(hql3);
//                            query3.setLong("idinfofam", infofam.getIdinfoFamilia());
//                            List adoptantes = query3.list();
//                            Set<Adoptante> tempAdoptantes = new HashSet<Adoptante>(0);
//                            for (Iterator iter9 = adoptantes.iterator(); iter9.hasNext();) {
//                                Adoptante adopTemp = (Adoptante) iter9.next();
//                                tempAdoptantes.add(adopTemp);
//                            }
//                            infofam.setAdoptantes(tempAdoptantes);
//                            //También puede incluirse hijo y residente
//                            tempInfoFam.add(infofam);
//                        }
//                        temp2.getFamilia().setInfoFamilias(tempInfoFam);
//                        Hibernate.initialize(temp2.getInformePostAdoptivos());
//                        Set<InformePostAdoptivo> listaInformes = new HashSet<InformePostAdoptivo>(0);
//                        for (InformePostAdoptivo informePostAdoptivo : temp2.getInformePostAdoptivos()) {
//                            Hibernate.initialize(informePostAdoptivo.getPersonal());
//                            listaInformes.add(informePostAdoptivo);
//                        }
//                        temp2.setInformePostAdoptivos(listaInformes);
//                        temp2.getFamilia().setExpedienteFamilias(tempExpediente);
//
//                    }
//                }
//                allPostAdopcion.add(temp2);
//            }
//        }
//
//        return allPostAdopcion;
//    }

    public ArrayList<PostAdopcion> ReportePostAdopcion2() {

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

//    public Designacion getDesigNnaFam(Long idNna, Long idExp) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Designacion tempDesig = new Designacion();
//        String hql = "from Designacion D where D.nna = :idNna and D.expedienteFamilia = :idExp and D.aceptacionConsejo = :acep";
//        Query query = session.createQuery(hql);
//        query.setLong("idNna", idNna);
//        query.setLong("idExp", idExp);
//        query.setShort("acep", Short.parseShort("2"));
//        Object queryResult = query.uniqueResult();
//        tempDesig = (Designacion) queryResult;
//
//        return tempDesig;
//    }

    public Designacion getDesigNnaFam2(Long idNna, Long idExp) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long tempIdnna = idNna;
        final Long tempIdexp = idExp;
        final Designacion tempDesig = new Designacion();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_DESIGNNAFAM(?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, tempIdexp);
                statement.setLong(2, tempIdnna);
                statement.registerOutParameter(3, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(3);

                if (rs.next()) {
                    tempDesig.setIddesignacion(rs.getLong("IDDESIGNACION"));
                    tempDesig.setNDesignacion(rs.getString("N_DESIGNACION"));
                    tempDesig.setPrioridad(rs.getLong("PRIORIDAD"));
                    tempDesig.setFechaPropuesta(rs.getDate("FECHA_PROPUESTA"));
                    tempDesig.setFechaConsejo(rs.getDate("FECHA_CONSEJO"));
                    tempDesig.setAceptacionConsejo(rs.getShort("ACEPTACION_CONSEJO"));
                    tempDesig.setTipoPropuesta(rs.getString("TIPO_PROPUESTA"));
                    tempDesig.setObs(rs.getString("OBS"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return tempDesig;
    }

//    public ArrayList<Nna> ListaNnaReporte(String clasificacion) {
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
//            Hibernate.initialize(temp.getJuzgado());
//            Hibernate.initialize(temp.getCar());
//            Hibernate.initialize(temp.getDesignacions());
//            Hibernate.initialize(temp.getExpedienteNnas());
//            Set<ExpedienteNna> listaExp = new HashSet<ExpedienteNna>(0);
//            if (!temp.getExpedienteNnas().isEmpty()) {
//                for (ExpedienteNna nnaExp : temp.getExpedienteNnas()) {
//                    Hibernate.initialize(nnaExp.getUnidad());
//                    listaExp.add(nnaExp);
//                }
//                temp.setExpedienteNnas(listaExp);
//            }
//
//            if (clasificacion.equals("prioritario")) {
//                Hibernate.initialize(temp.getEstudioCasos());
//
//                Set<EstudioCaso> listaEst = new HashSet<EstudioCaso>(0);
//                if (!temp.getEstudioCasos().isEmpty()) {
//                    for (EstudioCaso estudioCaso : temp.getEstudioCasos()) {
//                        Hibernate.initialize(estudioCaso.getExpedienteFamilia());
//                        listaEst.add(estudioCaso);
//                    }
//                    temp.setEstudioCasos(listaEst);
//                }
//
//                Set<Designacion> listaDesignacions = new HashSet<Designacion>(0);
//                if (!temp.getDesignacions().isEmpty()) {
//                    for (Designacion desig : temp.getDesignacions()) {
//                        Hibernate.initialize(desig.getExpedienteFamilia().getFamilia().getEntidad());
//                        Hibernate.initialize(desig.getExpedienteFamilia().getFamilia().getInfoFamilias());
//                        listaDesignacions.add(desig);
//                    }
//                    temp.setDesignacions(listaDesignacions);
//                }
//            }
//            allNna.add(temp);
//        }
//        return allNna;
//    }
    public ArrayList<Nna> ListaNnaReporte(String clasificacion) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        final String clas = clasificacion;
        final ArrayList<Nna> allNna = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_LISTANNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setString(1, clas);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Set<ExpedienteNna> listExp = new HashSet<ExpedienteNna>();
                    ExpedienteNna tempExpNna = new ExpedienteNna();
                    Nna tempNna = new Nna();
                    Car tempCar = new Car();
                    Juzgado tempJuz = new Juzgado();
                    Unidad tempUa = new Unidad();

                    tempNna.setIdnna(rs.getLong(1));
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
                    tempNna.setObservaciones(rs.getString(35));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempExpNna.setIdexpedienteNna(rs.getLong("IDEXPEDIENTE_NNA"));
                    tempExpNna.setNumero(rs.getString("NUMERO"));
                    tempExpNna.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                    tempExpNna.setHt(rs.getString("HT"));
                    tempExpNna.setNExpTutelar(rs.getString("N_EXP_TUTELAR"));
                    tempExpNna.setProcTutelar(rs.getString("PROC_TUTELAR"));
                    tempExpNna.setFichaIntegral(rs.getShort("FICHA_INTEGRAL"));
                    tempExpNna.setComentarios(rs.getString("COMENTARIOS"));
                    tempExpNna.setRespLegalNombre(rs.getString("RESP_LEGAL_NOMBRE"));
                    tempExpNna.setRespLegalP(rs.getString("RESP_LEGAL_P"));
                    tempExpNna.setRespLegalM(rs.getString("RESP_LEGAL_M"));
                    tempExpNna.setRespPsicosocialNombre(rs.getString("RESP_PSICOSOCIAL_NOMBRE"));
                    tempExpNna.setRespPiscosocialP(rs.getString("RESP_PISCOSOCIAL_P"));
                    tempExpNna.setRespPsicosocialM(rs.getString("RESP_PSICOSOCIAL_M"));
                    tempExpNna.setEstado(rs.getString("ESTADO"));
                    tempExpNna.setFechaEstado(rs.getDate("FECHA_ESTADO"));
                    tempExpNna.setAdoptable(rs.getShort("ADOPTABLE"));
                    tempExpNna.setFechaResolCons(rs.getDate("FECHA_RESOL_CONS"));
                    tempExpNna.setNacional(rs.getShort("NACIONAL"));
                    tempExpNna.setDiagnostico(rs.getString("DIAGNOSTICO"));
                    tempExpNna.setCodigoReferencia(rs.getString("CODIGO_REFERENCIA"));
                    tempExpNna.setNActual(rs.getString("N_ACTUAL"));
                    tempExpNna.setApellidopActual(rs.getString("APELLIDOP_ACTUAL"));
                    tempExpNna.setApellidomActual(rs.getString("APELLIDOM_ACTUAL"));
                    tempExpNna.setObservaciones(rs.getString(64));
                    tempExpNna.setFechaInvTutelar(rs.getDate("FECHA_INV_TUTELAR"));

                    tempCar.setIdcar(rs.getLong(3));
                    tempCar.setNombre(rs.getString("CARN"));
                    tempCar.setDireccion(rs.getString("CARDIR"));
                    tempCar.setDepartamento(rs.getString("CARDEP"));
                    tempCar.setProvincia(rs.getString("CARPROV"));
                    tempCar.setDistrito(rs.getString("CARDIST"));

                    tempJuz.setIdjuzgado(rs.getLong(2));
                    tempJuz.setNombre(rs.getString("JNOM"));
                    tempJuz.setDistritoJudicial(rs.getString("DISTRITO_JUDICIAL"));

                    tempUa.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUa.setNombre("UANOM");

                    tempExpNna.setUnidad(tempUa);
                    listExp.add(tempExpNna);

                    tempNna.setExpedienteNnas(listExp);
                    tempNna.setCar(tempCar);
                    tempNna.setJuzgado(tempJuz);

                    allNna.add(tempNna);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allNna;
    }

    public ArrayList<Nna> ListaNnaPriorita() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        final ArrayList<Nna> allNna = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_NNAPRIORITARIOS(?)}";
                CallableStatement statement = connection.prepareCall(hql);

                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    Set<ExpedienteNna> listExp = new HashSet<ExpedienteNna>();
                    Set<EstudioCaso> listEst = new HashSet<EstudioCaso>();
                    Set<Designacion> listDes = new HashSet<Designacion>();

                    ExpedienteNna tempExpNna = new ExpedienteNna();
                    Nna tempNna = new Nna();
                    Car tempCar = new Car();
                    Juzgado tempJuz = new Juzgado();
                    Unidad tempUa = new Unidad();

                    tempNna.setIdnna(rs.getLong(1));
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
                    tempNna.setObservaciones(rs.getString(35));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempExpNna.setIdexpedienteNna(rs.getLong("IDEXPEDIENTE_NNA"));
                    tempExpNna.setNumero(rs.getString("NUMERO"));
                    tempExpNna.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                    tempExpNna.setHt(rs.getString("HT"));
                    tempExpNna.setNExpTutelar(rs.getString("N_EXP_TUTELAR"));
                    tempExpNna.setProcTutelar(rs.getString("PROC_TUTELAR"));
                    tempExpNna.setFichaIntegral(rs.getShort("FICHA_INTEGRAL"));
                    tempExpNna.setComentarios(rs.getString("COMENTARIOS"));
                    tempExpNna.setRespLegalNombre(rs.getString("RESP_LEGAL_NOMBRE"));
                    tempExpNna.setRespLegalP(rs.getString("RESP_LEGAL_P"));
                    tempExpNna.setRespLegalM(rs.getString("RESP_LEGAL_M"));
                    tempExpNna.setRespPsicosocialNombre(rs.getString("RESP_PSICOSOCIAL_NOMBRE"));
                    tempExpNna.setRespPiscosocialP(rs.getString("RESP_PISCOSOCIAL_P"));
                    tempExpNna.setRespPsicosocialM(rs.getString("RESP_PSICOSOCIAL_M"));
                    tempExpNna.setEstado(rs.getString("ESTADO"));
                    tempExpNna.setFechaEstado(rs.getDate("FECHA_ESTADO"));
                    tempExpNna.setAdoptable(rs.getShort("ADOPTABLE"));
                    tempExpNna.setFechaResolCons(rs.getDate("FECHA_RESOL_CONS"));
                    tempExpNna.setNacional(rs.getShort("NACIONAL"));
                    tempExpNna.setDiagnostico(rs.getString("DIAGNOSTICO"));
                    tempExpNna.setCodigoReferencia(rs.getString("CODIGO_REFERENCIA"));
                    tempExpNna.setNActual(rs.getString("N_ACTUAL"));
                    tempExpNna.setApellidopActual(rs.getString("APELLIDOP_ACTUAL"));
                    tempExpNna.setApellidomActual(rs.getString("APELLIDOM_ACTUAL"));
                    tempExpNna.setObservaciones(rs.getString(64));
                    tempExpNna.setFechaInvTutelar(rs.getDate("FECHA_INV_TUTELAR"));
                    tempExpNna.setFechaIngPrio(rs.getDate("FECHA_ING_PRIO"));
                    tempExpNna.setFechaActualizacion(rs.getDate("FECHA_ACTUALIZACION"));

                    tempCar.setIdcar(rs.getLong(3));
                    tempCar.setNombre(rs.getString("CARN"));
                    tempCar.setDireccion(rs.getString("CARDIR"));
                    tempCar.setDepartamento(rs.getString("CARDEP"));
                    tempCar.setProvincia(rs.getString("CARPROV"));
                    tempCar.setDistrito(rs.getString("CARDIST"));

                    tempJuz.setIdjuzgado(rs.getLong(2));
                    tempJuz.setNombre(rs.getString("JNOM"));
                    tempJuz.setDistritoJudicial(rs.getString("DISTRITO_JUDICIAL"));

                    tempUa.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUa.setNombre("UANOM");

                    tempExpNna.setUnidad(tempUa);
                    listExp.add(tempExpNna);

                    tempNna.setExpedienteNnas(listExp);
                    tempNna.setCar(tempCar);
                    tempNna.setJuzgado(tempJuz);

                    String hql2 = "{call REPORTE_LISTAEST(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempNna.getIdnna());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                        EstudioCaso tempEst = new EstudioCaso();

                        tempEst.setIdestudioCaso(rs2.getLong("IDESTUDIO_CASO"));
                        tempEst.setOrden(rs2.getString("ORDEN"));
                        tempEst.setFechaEstudio(rs2.getDate("FECHA_ESTUDIO"));
                        tempEst.setFechaSolAdop(rs2.getDate("FECHA_SOL_ADOP"));
                        tempEst.setResultado(rs2.getString("RESULTADO"));
                        tempEst.setPrioridad(rs2.getLong("PRIORIDAD"));
                        tempEst.setNSolicitud(rs2.getLong("N_SOLICITUD"));

                        tempExpFam.setIdexpedienteFamilia(rs2.getLong("IDEXPEDIENTE_FAMILIA"));
                        tempExpFam.setExpediente(rs2.getString("EXPEDIENTE"));

                        tempEst.setExpedienteFamilia(tempExpFam);

                        listEst.add(tempEst);
                    }
                    rs2.close();
                    statement2.close();

                    tempNna.setEstudioCasos(listEst);

                    String hql3 = "{call REPORTE_LISTADESIG(?,?)}";
                    CallableStatement statement3 = connection.prepareCall(hql3);
                    statement3.setLong(1, tempNna.getIdnna());
                    statement3.registerOutParameter(2, OracleTypes.CURSOR);
                    statement3.execute();

                    ResultSet rs3 = (ResultSet) statement3.getObject(2);

                    while (rs3.next()) {
                        Set<InfoFamilia> listInfo = new HashSet<InfoFamilia>();
                        Set<ExpedienteFamilia> listExpFam = new HashSet<ExpedienteFamilia>();
                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                        InfoFamilia tempInfoFam = new InfoFamilia();
                        Familia tempFam = new Familia();
                        Designacion tempDesig = new Designacion();

                        tempDesig.setIddesignacion(rs3.getLong("IDDESIGNACION"));
                        tempDesig.setNDesignacion(rs3.getString("N_DESIGNACION"));
                        tempDesig.setPrioridad(rs3.getLong("PRIORIDAD"));
                        tempDesig.setFechaPropuesta(rs3.getDate("FECHA_PROPUESTA"));
                        tempDesig.setFechaConsejo(rs3.getDate("FECHA_CONSEJO"));
                        tempDesig.setAceptacionConsejo(rs3.getShort("ACEPTACION_CONSEJO"));
                        tempDesig.setTipoPropuesta(rs3.getString("TIPO_PROPUESTA"));
                        tempDesig.setObs(rs3.getString("OBS"));

                        tempExpFam.setIdexpedienteFamilia(rs3.getLong("IDEXPEDIENTE_FAMILIA"));
                        tempExpFam.setExpediente(rs3.getString("EXPEDIENTE"));

                        tempInfoFam.setIdinfoFamilia(rs3.getLong("IDINFO_FAMILIA"));
                        tempInfoFam.setPaisRes(rs3.getString("PAIS_RES"));
                        tempInfoFam.setDepRes(rs3.getString("DEP_RES"));

                        tempFam.setIdfamilia(rs3.getLong("IDFAMILIA"));
                        Long idEn = rs3.getLong("IDENTIDAD");
                        if (!rs.wasNull()) {
                            Entidad tempEnt = new Entidad();
                            String query4 = "{call RENAD_ENTIDAD(?,?)}";
                            CallableStatement statement4 = connection.prepareCall(query4);
                            statement4.setLong(1, idEn);
                            statement4.registerOutParameter(2, OracleTypes.CURSOR);
                            statement4.execute();
                            ResultSet rs4 = (ResultSet) statement4.getObject(2);
                            while (rs4.next()) {
                                tempEnt.setIdentidad(rs4.getLong("IDENTIDAD"));
                                tempEnt.setNombre(rs4.getString("NOMBRE"));
                                tempEnt.setUser(rs4.getString("USER_"));
                                tempEnt.setPass(rs4.getString("PASS"));
                                tempEnt.setDireccion(rs4.getString("DIRECCION"));
                                tempEnt.setTelefono(rs4.getString("TELEFONO"));
                                tempEnt.setPais(rs4.getString("PAIS"));
                                tempEnt.setResolAuto(rs4.getString("RESOL_AUTO"));
                                tempEnt.setFechaResol(rs4.getDate("FECHA_RESOL"));
                                tempEnt.setResolRenov(rs4.getString("RESOL_RENOV"));
                                tempEnt.setFechaRenov(rs4.getDate("FECHA_RENOV"));
                                tempEnt.setFechaVenc(rs4.getDate("FECHA_VENC"));
                                tempEnt.setObs(rs4.getString("OBS"));
                            }
                            rs4.close();
                            statement4.close();
                            tempFam.setEntidad(tempEnt);
                        }

                        tempInfoFam.setFamilia(tempFam);
                        tempExpFam.setFamilia(tempFam);

                        listExpFam.add(tempExpFam);
                        listInfo.add(tempInfoFam);

                        tempFam.setExpedienteFamilias(listExpFam);
                        tempFam.setInfoFamilias(listInfo);

                        tempDesig.setExpedienteFamilia(tempExpFam);

                        listDes.add(tempDesig);
                    }
                    rs3.close();
                    statement3.close();

                    tempNna.setDesignacions(listDes);

                    allNna.add(tempNna);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allNna;
    }

    public ArrayList<Nna> ListaHistorNnaPriorita() {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        final ArrayList<Nna> allNna = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_HIST_PRIO(?)}";
                CallableStatement statement = connection.prepareCall(hql);

                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    Set<ExpedienteNna> listExp = new HashSet<ExpedienteNna>();
                    Set<EstudioCaso> listEst = new HashSet<EstudioCaso>();
                    Set<Designacion> listDes = new HashSet<Designacion>();

                    ExpedienteNna tempExpNna = new ExpedienteNna();
                    Nna tempNna = new Nna();
                    Car tempCar = new Car();
                    Juzgado tempJuz = new Juzgado();
                    Unidad tempUa = new Unidad();

                    tempNna.setIdnna(rs.getLong(1));
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
                    tempNna.setObservaciones(rs.getString(35));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempExpNna.setIdexpedienteNna(rs.getLong("IDEXPEDIENTE_NNA"));
                    tempExpNna.setNumero(rs.getString("NUMERO"));
                    tempExpNna.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                    tempExpNna.setHt(rs.getString("HT"));
                    tempExpNna.setNExpTutelar(rs.getString("N_EXP_TUTELAR"));
                    tempExpNna.setProcTutelar(rs.getString("PROC_TUTELAR"));
                    tempExpNna.setFichaIntegral(rs.getShort("FICHA_INTEGRAL"));
                    tempExpNna.setComentarios(rs.getString("COMENTARIOS"));
                    tempExpNna.setRespLegalNombre(rs.getString("RESP_LEGAL_NOMBRE"));
                    tempExpNna.setRespLegalP(rs.getString("RESP_LEGAL_P"));
                    tempExpNna.setRespLegalM(rs.getString("RESP_LEGAL_M"));
                    tempExpNna.setRespPsicosocialNombre(rs.getString("RESP_PSICOSOCIAL_NOMBRE"));
                    tempExpNna.setRespPiscosocialP(rs.getString("RESP_PISCOSOCIAL_P"));
                    tempExpNna.setRespPsicosocialM(rs.getString("RESP_PSICOSOCIAL_M"));
                    tempExpNna.setEstado(rs.getString("ESTADO"));
                    tempExpNna.setFechaEstado(rs.getDate("FECHA_ESTADO"));
                    tempExpNna.setAdoptable(rs.getShort("ADOPTABLE"));
                    tempExpNna.setFechaResolCons(rs.getDate("FECHA_RESOL_CONS"));
                    tempExpNna.setNacional(rs.getShort("NACIONAL"));
                    tempExpNna.setDiagnostico(rs.getString("DIAGNOSTICO"));
                    tempExpNna.setCodigoReferencia(rs.getString("CODIGO_REFERENCIA"));
                    tempExpNna.setNActual(rs.getString("N_ACTUAL"));
                    tempExpNna.setApellidopActual(rs.getString("APELLIDOP_ACTUAL"));
                    tempExpNna.setApellidomActual(rs.getString("APELLIDOM_ACTUAL"));
                    tempExpNna.setObservaciones(rs.getString(64));
                    tempExpNna.setFechaInvTutelar(rs.getDate("FECHA_INV_TUTELAR"));
                    tempExpNna.setFechaIngPrio(rs.getDate("FECHA_ING_PRIO"));
                    tempExpNna.setFechaActualizacion(rs.getDate("FECHA_ACTUALIZACION"));
                    tempExpNna.setFechaIngPrio(rs.getDate("FECHA_ING_PRIO"));

                    tempCar.setIdcar(rs.getLong(3));
                    tempCar.setNombre(rs.getString("CARN"));
                    tempCar.setDireccion(rs.getString("CARDIR"));
                    tempCar.setDepartamento(rs.getString("CARDEP"));
                    tempCar.setProvincia(rs.getString("CARPROV"));
                    tempCar.setDistrito(rs.getString("CARDIST"));

                    tempJuz.setIdjuzgado(rs.getLong(2));
                    tempJuz.setNombre(rs.getString("JNOM"));
                    tempJuz.setDistritoJudicial(rs.getString("DISTRITO_JUDICIAL"));

                    tempUa.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUa.setNombre("UANOM");

                    tempExpNna.setUnidad(tempUa);
                    listExp.add(tempExpNna);

                    tempNna.setExpedienteNnas(listExp);
                    tempNna.setCar(tempCar);
                    tempNna.setJuzgado(tempJuz);

                    String hql2 = "{call REPORTE_LISTAEST(?,?)}";
                    CallableStatement statement2 = connection.prepareCall(hql2);
                    statement2.setLong(1, tempNna.getIdnna());
                    statement2.registerOutParameter(2, OracleTypes.CURSOR);
                    statement2.execute();

                    ResultSet rs2 = (ResultSet) statement2.getObject(2);

                    while (rs2.next()) {
                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                        EstudioCaso tempEst = new EstudioCaso();

                        tempEst.setIdestudioCaso(rs2.getLong("IDESTUDIO_CASO"));
                        tempEst.setOrden(rs2.getString("ORDEN"));
                        tempEst.setFechaEstudio(rs2.getDate("FECHA_ESTUDIO"));
                        tempEst.setFechaSolAdop(rs2.getDate("FECHA_SOL_ADOP"));
                        tempEst.setResultado(rs2.getString("RESULTADO"));
                        tempEst.setPrioridad(rs2.getLong("PRIORIDAD"));
                        tempEst.setNSolicitud(rs2.getLong("N_SOLICITUD"));

                        tempExpFam.setIdexpedienteFamilia(rs2.getLong("IDEXPEDIENTE_FAMILIA"));
                        tempExpFam.setExpediente(rs2.getString("EXPEDIENTE"));

                        tempEst.setExpedienteFamilia(tempExpFam);

                        listEst.add(tempEst);
                    }
                    rs2.close();
                    statement2.close();

                    tempNna.setEstudioCasos(listEst);

                    String hql3 = "{call REPORTE_LISTADESIG(?,?)}";
                    CallableStatement statement3 = connection.prepareCall(hql3);
                    statement3.setLong(1, tempNna.getIdnna());
                    statement3.registerOutParameter(2, OracleTypes.CURSOR);
                    statement3.execute();

                    ResultSet rs3 = (ResultSet) statement3.getObject(2);

                    while (rs3.next()) {
                        Set<InfoFamilia> listInfo = new HashSet<InfoFamilia>();
                        Set<ExpedienteFamilia> listExpFam = new HashSet<ExpedienteFamilia>();
                        ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                        InfoFamilia tempInfoFam = new InfoFamilia();
                        Familia tempFam = new Familia();
                        Designacion tempDesig = new Designacion();

                        tempDesig.setIddesignacion(rs3.getLong("IDDESIGNACION"));
                        tempDesig.setNDesignacion(rs3.getString("N_DESIGNACION"));
                        tempDesig.setPrioridad(rs3.getLong("PRIORIDAD"));
                        tempDesig.setFechaPropuesta(rs3.getDate("FECHA_PROPUESTA"));
                        tempDesig.setFechaConsejo(rs3.getDate("FECHA_CONSEJO"));
                        tempDesig.setAceptacionConsejo(rs3.getShort("ACEPTACION_CONSEJO"));
                        tempDesig.setTipoPropuesta(rs3.getString("TIPO_PROPUESTA"));
                        tempDesig.setObs(rs3.getString("OBS"));

                        tempExpFam.setIdexpedienteFamilia(rs3.getLong("IDEXPEDIENTE_FAMILIA"));
                        tempExpFam.setExpediente(rs3.getString("EXPEDIENTE"));

                        tempInfoFam.setIdinfoFamilia(rs3.getLong("IDINFO_FAMILIA"));
                        tempInfoFam.setPaisRes(rs3.getString("PAIS_RES"));
                        tempInfoFam.setDepRes(rs3.getString("DEP_RES"));

                        tempFam.setIdfamilia(rs3.getLong("IDFAMILIA"));
                        Long idEn = rs3.getLong("IDENTIDAD");
                        if (!rs.wasNull()) {
                            Entidad tempEnt = new Entidad();
                            String query4 = "{call RENAD_ENTIDAD(?,?)}";
                            CallableStatement statement4 = connection.prepareCall(query4);
                            statement4.setLong(1, idEn);
                            statement4.registerOutParameter(2, OracleTypes.CURSOR);
                            statement4.execute();
                            ResultSet rs4 = (ResultSet) statement4.getObject(2);
                            while (rs4.next()) {
                                tempEnt.setIdentidad(rs4.getLong("IDENTIDAD"));
                                tempEnt.setNombre(rs4.getString("NOMBRE"));
                                tempEnt.setUser(rs4.getString("USER_"));
                                tempEnt.setPass(rs4.getString("PASS"));
                                tempEnt.setDireccion(rs4.getString("DIRECCION"));
                                tempEnt.setTelefono(rs4.getString("TELEFONO"));
                                tempEnt.setPais(rs4.getString("PAIS"));
                                tempEnt.setResolAuto(rs4.getString("RESOL_AUTO"));
                                tempEnt.setFechaResol(rs4.getDate("FECHA_RESOL"));
                                tempEnt.setResolRenov(rs4.getString("RESOL_RENOV"));
                                tempEnt.setFechaRenov(rs4.getDate("FECHA_RENOV"));
                                tempEnt.setFechaVenc(rs4.getDate("FECHA_VENC"));
                                tempEnt.setObs(rs4.getString("OBS"));
                            }
                            rs4.close();
                            statement4.close();
                            tempFam.setEntidad(tempEnt);
                        }

                        tempInfoFam.setFamilia(tempFam);
                        tempExpFam.setFamilia(tempFam);

                        listExpFam.add(tempExpFam);
                        listInfo.add(tempInfoFam);

                        tempFam.setExpedienteFamilias(listExpFam);
                        tempFam.setInfoFamilias(listInfo);

                        tempDesig.setExpedienteFamilia(tempExpFam);

                        listDes.add(tempDesig);
                    }
                    rs3.close();
                    statement3.close();

                    tempNna.setDesignacions(listDes);

                    allNna.add(tempNna);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return allNna;
    }

//    public ArrayList<Familia> getListaFamilias() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "FROM Familia F WHERE F.habilitado = 0";
//        Query query = session.createQuery(hql);
//        List familias = query.list();
//        ArrayList<Familia> allFamilias = new ArrayList();
//        for (Iterator iter = familias.iterator(); iter.hasNext();) {
//            Familia temp = (Familia) iter.next();
//            Hibernate.initialize(temp.getEntidad());
//            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia ORDER BY EF.idexpedienteFamilia DESC";
//            Query query2 = session.createQuery(hql2);
//            query2.setLong("idFamilia", temp.getIdfamilia());
//            query2.setMaxResults(1);
//            Object queryResult2 = query2.uniqueResult();
//            if (queryResult2 != null) {
//                ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
//                Hibernate.initialize(tempExp.getUnidad());
//                Hibernate.initialize(tempExp.getEvaluacions());
//                Set<Evaluacion> listEval = new HashSet<Evaluacion>();
//                for (Evaluacion eval : tempExp.getEvaluacions()) {
//                    Hibernate.initialize(eval.getPersonal());
//                    //if(eval.getTipo().equals("legal")) Hibernate.initialize(eval.getResolucions());
//                    listEval.add(eval);
//                }
//                tempExp.setEvaluacions(listEval);
//                Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
//                listExp.add(tempExp);
//                temp.setExpedienteFamilias(listExp);
//            }
//
//            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
//            Query query3 = session.createQuery(hql3);
//            query3.setLong("idFamilia", temp.getIdfamilia());
//            query3.setMaxResults(1);
//            Object queryResult3 = query3.uniqueResult();
//            if (queryResult3 != null) {
//                InfoFamilia infoFam = (InfoFamilia) queryResult3;
//                Hibernate.initialize(infoFam.getAdoptantes());
//                Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
//                listInf.add(infoFam);
//                temp.setInfoFamilias(listInf);
//            }
//            if (query2.uniqueResult() != null && query3.uniqueResult() != null) {
//                allFamilias.add(temp);
//            }
//        }
//
//        return allFamilias;
//
//    }

    public ArrayList<Familia> getListaFamilias2() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<Familia> allFamilias = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String query = "{call RENAD_PARTE1(?)}";
                CallableStatement statement = connection.prepareCall(query);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
                    Set<Adoptante> listadop = new HashSet<Adoptante>();

                    Familia tempFam = new Familia();
                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                    Unidad tempUnidad = new Unidad();
                    InfoFamilia tempInfo = new InfoFamilia();

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
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

                    tempFam.setUser(rs.getString("USER_"));
                    tempFam.setPass(rs.getString("PASS"));
                    tempFam.setCorreo(rs.getString(5));
                    tempFam.setHabilitado(rs.getShort("HABILITADO"));
                    tempFam.setConstancia(rs.getString("CONSTANCIA"));

                    tempUnidad.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUnidad.setNombre(rs.getString("NOMBRE"));
                    tempUnidad.setDireccion(rs.getString("DIRECCION"));
                    tempUnidad.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempUnidad.setProvincia(rs.getString("PROVINCIA"));
                    tempUnidad.setDistrito(rs.getString("DISTRITO"));
                    tempUnidad.setCompetenciaRegional(rs.getString("COMPETENCIA_REGIONAL"));
                    tempUnidad.setCorreo(rs.getString("CORREO"));
                    tempUnidad.setTelefono(rs.getString("TELEFONO"));
                    tempUnidad.setCelular(rs.getString("CELULAR"));
                    tempUnidad.setObs(rs.getString("OBS"));

                    tempExpFam.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempExpFam.setFamilia(tempFam);
                    tempExpFam.setUnidad(tempUnidad);
                    tempExpFam.setNumero(rs.getLong("NUMERO"));
                    tempExpFam.setExpediente(rs.getString("EXPEDIENTE"));
                    tempExpFam.setHt(rs.getString("HT"));
                    tempExpFam.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempExpFam.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempExpFam.setEstado(rs.getString("ESTADO"));
                    tempExpFam.setTupa(rs.getDate("TUPA"));
                    tempExpFam.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempExpFam.setRnsa(rs.getShort("RNSA"));
                    tempExpFam.setRnaa(rs.getShort("RNAA"));
                    tempExpFam.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempExpFam.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempExpFam.setHtFicha(rs.getString("HTFICHA"));
                    tempExpFam.setnFicha(rs.getString("NFICHA"));
                    tempExpFam.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));

                    Set<Evaluacion> listEval = new HashSet<Evaluacion>();
                    String query4 = "{call REPORTE_EVALUACIONES(?,?)}";
                    CallableStatement statement4 = connection.prepareCall(query4);
                    statement4.setLong(1, tempExpFam.getIdexpedienteFamilia());
                    statement4.registerOutParameter(2, OracleTypes.CURSOR);
                    statement4.execute();
                    ResultSet rs4 = (ResultSet) statement4.getObject(2);
                    while (rs4.next()) {
                        Personal tempPersonal = new Personal();
                        Evaluacion tempEval = new Evaluacion();

                        tempPersonal.setIdpersonal(rs4.getLong("IDPERSONAL"));
                        tempPersonal.setNombre(rs4.getString("NOMBRE"));
                        tempPersonal.setApellidoP(rs4.getString("APELLIDO_P"));
                        tempPersonal.setApellidoM(rs4.getString("APELLIDO_M"));
                        tempPersonal.setUser(rs4.getString("USER_"));
                        tempPersonal.setPass(rs4.getString("PASS"));
                        tempPersonal.setCorreoTrabajo(rs4.getString("CORREO_TRABAJO"));
                        tempPersonal.setCorreoPersonal(rs4.getString("CORREO_PERSONAL"));
                        tempPersonal.setProfesion(rs4.getString("PROFESION"));
                        tempPersonal.setGradoInstruccion(rs4.getString("GRADO_INSTRUCCION"));
                        tempPersonal.setCargo(rs4.getString("CARGO"));
                        tempPersonal.setDni(rs4.getString("DNI"));
                        tempPersonal.setFechaNacimiento(rs4.getDate("FECHA_NACIMIENTO"));
                        tempPersonal.setRegimen(rs4.getString("REGIMEN"));
                        tempPersonal.setFechaIngreso(rs4.getDate("FECHA_INGRESO"));
                        tempPersonal.setDomicilio(rs4.getString("DOMICILIO"));
                        tempPersonal.setRol(rs4.getString("ROL"));

                        tempEval.setIdevaluacion(rs4.getLong("IDEVALUACION"));
                        tempEval.setExpedienteFamilia(tempExpFam);
                        tempEval.setPersonal(tempPersonal);
                        tempEval.setTipo(rs4.getString("TIPO"));
                        tempEval.setFechaAsignacion(rs4.getDate("FECHA_ASIGNACION"));
                        tempEval.setResultado(rs4.getString("RESULTADO"));
                        tempEval.setFechaResultado(rs4.getDate("FECHA_RESULTADO"));
                        tempEval.setObservacion(rs4.getString("OBSERVACION"));
                        tempEval.setSustento(rs4.getString("SUSTENTO"));
                        tempEval.setNDesignacion(rs4.getString("N_DESIGNACION"));
                        tempEval.setNumEval(rs4.getString("NUM_EVAL"));

                        listEval.add(tempEval);

                    }
                    rs4.close();
                    statement4.close();

                    tempExpFam.setEvaluacions(listEval);
//                     
//                    
//                     
                    tempInfo.setIdinfoFamilia(rs.getLong("IDINFO_FAMILIA"));
                    tempInfo.setFamilia(tempFam);
                    tempInfo.setDepRes(rs.getString("DEP_RES"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDomicilio(rs.getString("DOMICILIO"));
                    tempInfo.setPropiedadVivienda(rs.getString("PROPIEDAD_VIVIENDA"));
                    tempInfo.setTipoVivienda(rs.getString("TIPO_VIVIENDA"));
                    tempInfo.setAreaVivTotal(rs.getLong("AREA_VIV_TOTAL"));
                    tempInfo.setAreaVivConst(rs.getLong("AREA_VIV_CONST"));
                    tempInfo.setDistVivienda(rs.getString("DIST_VIVIENDA"));
                    tempInfo.setLuz(rs.getShort("LUZ"));
                    tempInfo.setAgua(rs.getShort("AGUA"));
                    tempInfo.setDesague(rs.getShort("DESAGUE"));
                    tempInfo.setOtrosServ(rs.getString("OTROS_SERV"));
                    tempInfo.setMaterConst(rs.getString("MATER_CONST"));
                    tempInfo.setPared(rs.getString("PARED"));
                    tempInfo.setTecho(rs.getString("TECHO"));
                    tempInfo.setPiso(rs.getString("PISO"));
                    String charValueStr = "";
                    if (rs.getString("NIVEL_SOCIOECONOMICO") != null) {
                        charValueStr = rs.getString("NIVEL_SOCIOECONOMICO");
                    }
                    if (!charValueStr.equals("") && charValueStr != null) {
                        tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                    }
                    tempInfo.setExpectativaEdadMin(rs.getShort("EXPECTATIVA_EDAD_MIN"));
                    tempInfo.setExpectativaGenero(rs.getString("EXPECTATIVA_GENERO"));
                    tempInfo.setOrigenHijos(rs.getString("ORIGEN_HIJOS"));
                    tempInfo.setPuedeViajar(rs.getShort("PUEDE_VIAJAR"));
                    tempInfo.setPredisposicionAp(rs.getString("PREDISPOSICION_AP"));
                    tempInfo.setCondicion(rs.getString("CONDICION"));
                    tempInfo.setAntecedenteFamilia(rs.getString("ANTECEDENTE_FAMILIA"));
                    tempInfo.setFechaAntecedenteFamilia(rs.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                    tempInfo.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempInfo.setNnaIncesto(rs.getShort("NNA_INCESTO"));
                    tempInfo.setNnaMental(rs.getShort("NNA_MENTAL"));
                    tempInfo.setNnaEpilepsia(rs.getShort("NNA_EPILEPSIA"));
                    tempInfo.setNnaAbuso(rs.getShort("NNA_ABUSO"));
                    tempInfo.setNnaSifilis(rs.getShort("NNA_SIFILIS"));
                    tempInfo.setNnaSeguiMedico(rs.getShort("NNA_SEGUI_MEDICO"));
                    tempInfo.setNnaOperacion(rs.getShort("NNA_OPERACION"));
                    tempInfo.setNnaHiperactivo(rs.getShort("NNA_HIPERACTIVO"));
                    tempInfo.setNnaEspecial(rs.getShort("NNA_ESPECIAL"));
                    tempInfo.setNnaEnfermo(rs.getShort("NNA_ENFERMO"));
                    tempInfo.setNnaMayor(rs.getShort("NNA_MAYOR"));
                    tempInfo.setNnaAdolescente(rs.getShort("NNA_ADOLESCENTE"));
                    tempInfo.setNnaHermano(rs.getShort("NNA_HERMANO"));
                    tempInfo.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    tempInfo.setFechaMatrimonio(rs.getDate("FECHA_MATRIMONIO"));
                    tempInfo.setTelefono(rs.getString("TELEFONO"));
                    tempInfo.setExpectativaEdadMax(rs.getShort("EXPECTATIVA_EDAD_MAX"));
                    tempInfo.setnHijos(rs.getShort("NHIJOS"));
//                     
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
//                        
                    listExp.add(tempExpFam);
                    listInf.add(tempInfo);
//                      
                    tempFam.setExpedienteFamilias(listExp);
                    tempFam.setInfoFamilias(listInf);
                    allFamilias.add(tempFam);

                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);
        return allFamilias;

    }

//    public ArrayList<Familia> getListaFamiliasNacionales() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "FROM Familia F WHERE F.habilitado = 0";
//        Query query = session.createQuery(hql);
//        List familias = query.list();
//        ArrayList<Familia> allFamilias = new ArrayList();
//        for (Iterator iter = familias.iterator(); iter.hasNext();) {
//            Familia temp = (Familia) iter.next();
//            Hibernate.initialize(temp.getEntidad());
//            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia and EF.nacionalidad = :nacionalidad ORDER BY EF.idexpedienteFamilia DESC";
//            Query query2 = session.createQuery(hql2);
//            query2.setLong("idFamilia", temp.getIdfamilia());
//            query2.setString("nacionalidad", "nacional");
//            query2.setMaxResults(1);
//            Object queryResult2 = query2.uniqueResult();
//            if (queryResult2 != null) {
//                ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
//                Hibernate.initialize(tempExp.getUnidad());
//                Hibernate.initialize(tempExp.getEvaluacions());
//                Set<Evaluacion> listEval = new HashSet<Evaluacion>();
//                for (Evaluacion eval : tempExp.getEvaluacions()) {
//                    Hibernate.initialize(eval.getPersonal());
//                    //if(eval.getTipo().equals("legal")) Hibernate.initialize(eval.getResolucions());
//                    listEval.add(eval);
//                }
//                tempExp.setEvaluacions(listEval);
//                Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
//                listExp.add(tempExp);
//                temp.setExpedienteFamilias(listExp);
//            }
//
//            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
//            Query query3 = session.createQuery(hql3);
//            query3.setLong("idFamilia", temp.getIdfamilia());
//            query3.setMaxResults(1);
//            Object queryResult3 = query3.uniqueResult();
//            if (queryResult3 != null) {
//                InfoFamilia infoFam = (InfoFamilia) queryResult3;
//                Hibernate.initialize(infoFam.getAdoptantes());
//                Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
//                listInf.add(infoFam);
//                temp.setInfoFamilias(listInf);
//            }
//            if (query2.uniqueResult() != null && query3.uniqueResult() != null) {
//                allFamilias.add(temp);
//            }
//        }
//
//        return allFamilias;
//
//    }

    public ArrayList<Familia> getListaFamiliasNacionales2() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<Familia> allFamilias = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String query = "{call REPORTE_ADOPCIONNAC(?)}";
                CallableStatement statement = connection.prepareCall(query);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
                    Set<Adoptante> listadop = new HashSet<Adoptante>();

                    Familia tempFam = new Familia();
                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                    Unidad tempUnidad = new Unidad();
                    InfoFamilia tempInfo = new InfoFamilia();

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
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

                    tempFam.setUser(rs.getString("USER_"));
                    tempFam.setPass(rs.getString("PASS"));
                    tempFam.setCorreo(rs.getString(5));
                    tempFam.setHabilitado(rs.getShort("HABILITADO"));
                    tempFam.setConstancia(rs.getString("CONSTANCIA"));

                    tempUnidad.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUnidad.setNombre(rs.getString("NOMBRE"));
                    tempUnidad.setDireccion(rs.getString("DIRECCION"));
                    tempUnidad.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempUnidad.setProvincia(rs.getString("PROVINCIA"));
                    tempUnidad.setDistrito(rs.getString("DISTRITO"));
                    tempUnidad.setCompetenciaRegional(rs.getString("COMPETENCIA_REGIONAL"));
                    tempUnidad.setCorreo(rs.getString("CORREO"));
                    tempUnidad.setTelefono(rs.getString("TELEFONO"));
                    tempUnidad.setCelular(rs.getString("CELULAR"));
                    tempUnidad.setObs(rs.getString("OBS"));

                    tempExpFam.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempExpFam.setFamilia(tempFam);
                    tempExpFam.setUnidad(tempUnidad);
                    tempExpFam.setNumero(rs.getLong("NUMERO"));
                    tempExpFam.setExpediente(rs.getString("EXPEDIENTE"));
                    tempExpFam.setHt(rs.getString("HT"));
                    tempExpFam.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempExpFam.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempExpFam.setEstado(rs.getString("ESTADO"));
                    tempExpFam.setTupa(rs.getDate("TUPA"));
                    tempExpFam.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempExpFam.setRnsa(rs.getShort("RNSA"));
                    tempExpFam.setRnaa(rs.getShort("RNAA"));
                    tempExpFam.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempExpFam.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempExpFam.setHtFicha(rs.getString("HTFICHA"));
                    tempExpFam.setnFicha(rs.getString("NFICHA"));
                    tempExpFam.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));
//                     
//                    
//                     
                    tempInfo.setIdinfoFamilia(rs.getLong("IDINFO_FAMILIA"));
                    tempInfo.setFamilia(tempFam);
                    tempInfo.setDepRes(rs.getString("DEP_RES"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDomicilio(rs.getString("DOMICILIO"));
                    tempInfo.setPropiedadVivienda(rs.getString("PROPIEDAD_VIVIENDA"));
                    tempInfo.setTipoVivienda(rs.getString("TIPO_VIVIENDA"));
                    tempInfo.setAreaVivTotal(rs.getLong("AREA_VIV_TOTAL"));
                    tempInfo.setAreaVivConst(rs.getLong("AREA_VIV_CONST"));
                    tempInfo.setDistVivienda(rs.getString("DIST_VIVIENDA"));
                    tempInfo.setLuz(rs.getShort("LUZ"));
                    tempInfo.setAgua(rs.getShort("AGUA"));
                    tempInfo.setDesague(rs.getShort("DESAGUE"));
                    tempInfo.setOtrosServ(rs.getString("OTROS_SERV"));
                    tempInfo.setMaterConst(rs.getString("MATER_CONST"));
                    tempInfo.setPared(rs.getString("PARED"));
                    tempInfo.setTecho(rs.getString("TECHO"));
                    tempInfo.setPiso(rs.getString("PISO"));
                    String charValueStr = "";
                    if (rs.getString("NIVEL_SOCIOECONOMICO") != null) {
                        charValueStr = rs.getString("NIVEL_SOCIOECONOMICO");
                    }
                    if (!charValueStr.equals("") && charValueStr != null) {
                        tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                    }
                    tempInfo.setExpectativaEdadMin(rs.getShort("EXPECTATIVA_EDAD_MIN"));
                    tempInfo.setExpectativaGenero(rs.getString("EXPECTATIVA_GENERO"));
                    tempInfo.setOrigenHijos(rs.getString("ORIGEN_HIJOS"));
                    tempInfo.setPuedeViajar(rs.getShort("PUEDE_VIAJAR"));
                    tempInfo.setPredisposicionAp(rs.getString("PREDISPOSICION_AP"));
                    tempInfo.setCondicion(rs.getString("CONDICION"));
                    tempInfo.setAntecedenteFamilia(rs.getString("ANTECEDENTE_FAMILIA"));
                    tempInfo.setFechaAntecedenteFamilia(rs.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                    tempInfo.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempInfo.setNnaIncesto(rs.getShort("NNA_INCESTO"));
                    tempInfo.setNnaMental(rs.getShort("NNA_MENTAL"));
                    tempInfo.setNnaEpilepsia(rs.getShort("NNA_EPILEPSIA"));
                    tempInfo.setNnaAbuso(rs.getShort("NNA_ABUSO"));
                    tempInfo.setNnaSifilis(rs.getShort("NNA_SIFILIS"));
                    tempInfo.setNnaSeguiMedico(rs.getShort("NNA_SEGUI_MEDICO"));
                    tempInfo.setNnaOperacion(rs.getShort("NNA_OPERACION"));
                    tempInfo.setNnaHiperactivo(rs.getShort("NNA_HIPERACTIVO"));
                    tempInfo.setNnaEspecial(rs.getShort("NNA_ESPECIAL"));
                    tempInfo.setNnaEnfermo(rs.getShort("NNA_ENFERMO"));
                    tempInfo.setNnaMayor(rs.getShort("NNA_MAYOR"));
                    tempInfo.setNnaAdolescente(rs.getShort("NNA_ADOLESCENTE"));
                    tempInfo.setNnaHermano(rs.getShort("NNA_HERMANO"));
                    tempInfo.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    tempInfo.setFechaMatrimonio(rs.getDate("FECHA_MATRIMONIO"));
                    tempInfo.setTelefono(rs.getString("TELEFONO"));
                    tempInfo.setExpectativaEdadMax(rs.getShort("EXPECTATIVA_EDAD_MAX"));
                    tempInfo.setnHijos(rs.getShort("NHIJOS"));
//                     
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
//                        
                    listExp.add(tempExpFam);
                    listInf.add(tempInfo);
//                      
                    tempFam.setExpedienteFamilias(listExp);
                    tempFam.setInfoFamilias(listInf);
                    allFamilias.add(tempFam);

                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return allFamilias;

    }

//    public ArrayList<Familia> getListaFamiliasInternacionales() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "FROM Familia F WHERE F.habilitado = 0";
//        Query query = session.createQuery(hql);
//        List familias = query.list();
//        ArrayList<Familia> allFamilias = new ArrayList();
//        for (Iterator iter = familias.iterator(); iter.hasNext();) {
//            Familia temp = (Familia) iter.next();
//            Hibernate.initialize(temp.getEntidad());
//            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia and EF.nacionalidad = :nacionalidad ORDER BY EF.idexpedienteFamilia DESC";
//            Query query2 = session.createQuery(hql2);
//            query2.setLong("idFamilia", temp.getIdfamilia());
//            query2.setString("nacionalidad", "internacional");
//            query2.setMaxResults(1);
//            Object queryResult2 = query2.uniqueResult();
//            if (queryResult2 != null) {
//                ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
//                Hibernate.initialize(tempExp.getUnidad());
//                Hibernate.initialize(tempExp.getEvaluacions());
//                Set<Evaluacion> listEval = new HashSet<Evaluacion>();
//                for (Evaluacion eval : tempExp.getEvaluacions()) {
//                    Hibernate.initialize(eval.getPersonal());
//                    //if(eval.getTipo().equals("legal")) Hibernate.initialize(eval.getResolucions());
//                    listEval.add(eval);
//                }
//                tempExp.setEvaluacions(listEval);
//                Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
//                listExp.add(tempExp);
//                temp.setExpedienteFamilias(listExp);
//            }
//
//            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
//            Query query3 = session.createQuery(hql3);
//            query3.setLong("idFamilia", temp.getIdfamilia());
//            query3.setMaxResults(1);
//            Object queryResult3 = query3.uniqueResult();
//            if (queryResult3 != null) {
//                InfoFamilia infoFam = (InfoFamilia) queryResult3;
//                Hibernate.initialize(infoFam.getAdoptantes());
//                Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
//                listInf.add(infoFam);
//                temp.setInfoFamilias(listInf);
//            }
//            if (query2.uniqueResult() != null && query3.uniqueResult() != null) {
//                allFamilias.add(temp);
//            }
//        }
//
//        return allFamilias;
//
//    }

    public ArrayList<Familia> getListaFamiliasInternacionales2() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<Familia> allFamilias = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String query = "{call REPORTE_ADOPCIONEXT(?)}";
                CallableStatement statement = connection.prepareCall(query);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
                    Set<Adoptante> listadop = new HashSet<Adoptante>();

                    Familia tempFam = new Familia();
                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                    Unidad tempUnidad = new Unidad();
                    InfoFamilia tempInfo = new InfoFamilia();

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
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

                    tempFam.setUser(rs.getString("USER_"));
                    tempFam.setPass(rs.getString("PASS"));
                    tempFam.setCorreo(rs.getString(5));
                    tempFam.setHabilitado(rs.getShort("HABILITADO"));
                    tempFam.setConstancia(rs.getString("CONSTANCIA"));

                    tempUnidad.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUnidad.setNombre(rs.getString("NOMBRE"));
                    tempUnidad.setDireccion(rs.getString("DIRECCION"));
                    tempUnidad.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempUnidad.setProvincia(rs.getString("PROVINCIA"));
                    tempUnidad.setDistrito(rs.getString("DISTRITO"));
                    tempUnidad.setCompetenciaRegional(rs.getString("COMPETENCIA_REGIONAL"));
                    tempUnidad.setCorreo(rs.getString("CORREO"));
                    tempUnidad.setTelefono(rs.getString("TELEFONO"));
                    tempUnidad.setCelular(rs.getString("CELULAR"));
                    tempUnidad.setObs(rs.getString("OBS"));

                    tempExpFam.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempExpFam.setFamilia(tempFam);
                    tempExpFam.setUnidad(tempUnidad);
                    tempExpFam.setNumero(rs.getLong("NUMERO"));
                    tempExpFam.setExpediente(rs.getString("EXPEDIENTE"));
                    tempExpFam.setHt(rs.getString("HT"));
                    tempExpFam.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempExpFam.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempExpFam.setEstado(rs.getString("ESTADO"));
                    tempExpFam.setTupa(rs.getDate("TUPA"));
                    tempExpFam.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempExpFam.setRnsa(rs.getShort("RNSA"));
                    tempExpFam.setRnaa(rs.getShort("RNAA"));
                    tempExpFam.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempExpFam.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempExpFam.setHtFicha(rs.getString("HTFICHA"));
                    tempExpFam.setnFicha(rs.getString("NFICHA"));
                    tempExpFam.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));
//                     
//                    
//                     
                    tempInfo.setIdinfoFamilia(rs.getLong("IDINFO_FAMILIA"));
                    tempInfo.setFamilia(tempFam);
                    tempInfo.setDepRes(rs.getString("DEP_RES"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDomicilio(rs.getString("DOMICILIO"));
                    tempInfo.setPropiedadVivienda(rs.getString("PROPIEDAD_VIVIENDA"));
                    tempInfo.setTipoVivienda(rs.getString("TIPO_VIVIENDA"));
                    tempInfo.setAreaVivTotal(rs.getLong("AREA_VIV_TOTAL"));
                    tempInfo.setAreaVivConst(rs.getLong("AREA_VIV_CONST"));
                    tempInfo.setDistVivienda(rs.getString("DIST_VIVIENDA"));
                    tempInfo.setLuz(rs.getShort("LUZ"));
                    tempInfo.setAgua(rs.getShort("AGUA"));
                    tempInfo.setDesague(rs.getShort("DESAGUE"));
                    tempInfo.setOtrosServ(rs.getString("OTROS_SERV"));
                    tempInfo.setMaterConst(rs.getString("MATER_CONST"));
                    tempInfo.setPared(rs.getString("PARED"));
                    tempInfo.setTecho(rs.getString("TECHO"));
                    tempInfo.setPiso(rs.getString("PISO"));
                    String charValueStr = "";
                    if (rs.getString("NIVEL_SOCIOECONOMICO") != null) {
                        charValueStr = rs.getString("NIVEL_SOCIOECONOMICO");
                    }
                    if (!charValueStr.equals("") && charValueStr != null) {
                        tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                    }
                    tempInfo.setExpectativaEdadMin(rs.getShort("EXPECTATIVA_EDAD_MIN"));
                    tempInfo.setExpectativaGenero(rs.getString("EXPECTATIVA_GENERO"));
                    tempInfo.setOrigenHijos(rs.getString("ORIGEN_HIJOS"));
                    tempInfo.setPuedeViajar(rs.getShort("PUEDE_VIAJAR"));
                    tempInfo.setPredisposicionAp(rs.getString("PREDISPOSICION_AP"));
                    tempInfo.setCondicion(rs.getString("CONDICION"));
                    tempInfo.setAntecedenteFamilia(rs.getString("ANTECEDENTE_FAMILIA"));
                    tempInfo.setFechaAntecedenteFamilia(rs.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                    tempInfo.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempInfo.setNnaIncesto(rs.getShort("NNA_INCESTO"));
                    tempInfo.setNnaMental(rs.getShort("NNA_MENTAL"));
                    tempInfo.setNnaEpilepsia(rs.getShort("NNA_EPILEPSIA"));
                    tempInfo.setNnaAbuso(rs.getShort("NNA_ABUSO"));
                    tempInfo.setNnaSifilis(rs.getShort("NNA_SIFILIS"));
                    tempInfo.setNnaSeguiMedico(rs.getShort("NNA_SEGUI_MEDICO"));
                    tempInfo.setNnaOperacion(rs.getShort("NNA_OPERACION"));
                    tempInfo.setNnaHiperactivo(rs.getShort("NNA_HIPERACTIVO"));
                    tempInfo.setNnaEspecial(rs.getShort("NNA_ESPECIAL"));
                    tempInfo.setNnaEnfermo(rs.getShort("NNA_ENFERMO"));
                    tempInfo.setNnaMayor(rs.getShort("NNA_MAYOR"));
                    tempInfo.setNnaAdolescente(rs.getShort("NNA_ADOLESCENTE"));
                    tempInfo.setNnaHermano(rs.getShort("NNA_HERMANO"));
                    tempInfo.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    tempInfo.setFechaMatrimonio(rs.getDate("FECHA_MATRIMONIO"));
                    tempInfo.setTelefono(rs.getString("TELEFONO"));
                    tempInfo.setExpectativaEdadMax(rs.getShort("EXPECTATIVA_EDAD_MAX"));
                    tempInfo.setnHijos(rs.getShort("NHIJOS"));
//                     
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
//                        
                    listExp.add(tempExpFam);
                    listInf.add(tempInfo);
//                      
                    tempFam.setExpedienteFamilias(listExp);
                    tempFam.setInfoFamilias(listInf);
                    allFamilias.add(tempFam);

                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return allFamilias;

    }

//    public Resolucion getUltimaResolucion(Long expFam) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Resolucion ultimaResol = new Resolucion();
//
//        String hql = "from Evaluacion E where (E.tipo = :tipo1) or (E.tipo = :tipo2) and E.expedienteFamilia = :expFam";
//        Query query = session.createQuery(hql);
//        query.setString("tipo1", "legal");
//        query.setString("tipo2", "informe");
//        query.setLong("expFam", expFam);
//        List evaluaciones = query.list();
//
//        if (!evaluaciones.isEmpty()) {
//            for (Iterator iter = evaluaciones.iterator(); iter.hasNext();) {
//                Evaluacion temp = (Evaluacion) iter.next();
//                if (temp.getTipo().equals("informe")) {
//                    String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval ORDER BY R.idresolucion DESC";
//                    Query query1 = session.createQuery(hql1);
//                    query1.setLong("idEval", temp.getIdevaluacion());
//                    query1.setMaxResults(1);
//                    Object queryResult1 = query1.uniqueResult();
//                    if (queryResult1 != null) {
//                        ultimaResol = (Resolucion) queryResult1;
//                        return ultimaResol;
//                    }
//
//                } else {
//
//                    String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval ORDER BY R.idresolucion DESC";
//                    Query query1 = session.createQuery(hql1);
//                    query1.setLong("idEval", temp.getIdevaluacion());
//                    query1.setMaxResults(1);
//                    Object queryResult1 = query1.uniqueResult();
//                    if (queryResult1 != null) {
//                        ultimaResol = (Resolucion) queryResult1;
//                    }
//                }
//
//            }
//        }
//        return ultimaResol;
//    }

    public Resolucion getUltimaResolucion2(Long expFam) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idExp = expFam;
        final Resolucion ultimaResol = new Resolucion();
        final Resolucion tempInforme = new Resolucion();
        final Resolucion tempEmpatia = new Resolucion();
        final Resolucion tempLegal = new Resolucion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_ULTRESOL1(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);
                Set<Evaluacion> listEval = new HashSet<Evaluacion>();
                while (rs.next()) {
                    Evaluacion tempEval = new Evaluacion();
                    tempEval.setIdevaluacion(rs.getLong("IDEVALUACION"));
                    tempEval.setTipo(rs.getString("TIPO"));
                    listEval.add(tempEval);
                }
                rs.close();
                statement.close();

                if (!listEval.isEmpty()) {

                    for (Evaluacion evaluacion : listEval) {

                        if (evaluacion.getTipo().equals("informe")) {
                            tempInforme.setIdresolucion(evaluacion.getIdevaluacion());
                            String hql2 = "{call RENAD_RESOLEVAL(?,?)}";
                            CallableStatement statement2 = connection.prepareCall(hql2);
                            statement2.setLong(1, evaluacion.getIdevaluacion());
                            statement2.registerOutParameter(2, OracleTypes.CURSOR);
                            statement2.execute();

                            ResultSet rs2 = (ResultSet) statement2.getObject(2);

                            if (rs2.next()) {
                                tempInforme.setIdresolucion(rs2.getLong("IDRESOLUCION"));
                                tempInforme.setTipo(rs2.getString("TIPO"));
                                tempInforme.setNumero(rs2.getString("NUMERO"));
                                tempInforme.setFechaResol(rs2.getDate("FECHA_RESOL"));
                                tempInforme.setFechaNotificacion(rs2.getDate("FECHA_NOTIFICACION"));
                            }
                            rs2.close();
                            statement2.close();
                        } else if (evaluacion.getTipo().equals("empatia")) {
                            tempEmpatia.setIdresolucion(evaluacion.getIdevaluacion());
                            String hql3 = "{call RENAD_RESOLEVAL(?,?)}";
                            CallableStatement statement3 = connection.prepareCall(hql3);
                            statement3.setLong(1, evaluacion.getIdevaluacion());
                            statement3.registerOutParameter(2, OracleTypes.CURSOR);
                            statement3.execute();

                            ResultSet rs3 = (ResultSet) statement3.getObject(2);

                            if (rs3.next()) {
                                tempEmpatia.setIdresolucion(rs3.getLong("IDRESOLUCION"));
                                tempEmpatia.setTipo(rs3.getString("TIPO"));
                                tempEmpatia.setNumero(rs3.getString("NUMERO"));
                                tempEmpatia.setFechaResol(rs3.getDate("FECHA_RESOL"));
                                tempEmpatia.setFechaNotificacion(rs3.getDate("FECHA_NOTIFICACION"));
                            }
                            rs3.close();
                            statement3.close();

                        } else {
                            tempLegal.setIdresolucion(evaluacion.getIdevaluacion());
                            String hql4 = "{call RENAD_RESOLEVAL(?,?)}";
                            CallableStatement statement4 = connection.prepareCall(hql4);
                            statement4.setLong(1, evaluacion.getIdevaluacion());
                            statement4.registerOutParameter(2, OracleTypes.CURSOR);
                            statement4.execute();

                            ResultSet rs4 = (ResultSet) statement4.getObject(2);

                            if (rs4.next()) {
                                tempLegal.setIdresolucion(rs4.getLong("IDRESOLUCION"));
                                tempLegal.setTipo(rs4.getString("TIPO"));
                                tempLegal.setNumero(rs4.getString("NUMERO"));
                                tempLegal.setFechaResol(rs4.getDate("FECHA_RESOL"));
                                tempLegal.setFechaNotificacion(rs4.getDate("FECHA_NOTIFICACION"));
                            }
                            rs4.close();
                            statement4.close();

                        }

                    }

                }

            }
        };

        session.doWork(work);
        if (tempInforme.getIdresolucion() != 0) {
            return tempInforme;
        } else if (tempEmpatia.getIdresolucion() != 0) {
            return tempEmpatia;
        } else {
            return tempLegal;
        }

    }

//    public Resolucion getResolucionAptitud(Long expFam) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Resolucion ultimaResol = new Resolucion();
//
//        String hql = "from Evaluacion E where E.tipo = :tipo1 and E.expedienteFamilia = :expFam";
//        Query query = session.createQuery(hql);
//        query.setString("tipo1", "legal");
//        query.setLong("expFam", expFam);
//        Object queryResultA = query.uniqueResult();
//
//        if (query.uniqueResult() != null) {
//            Evaluacion temp = (Evaluacion) queryResultA;
//            String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval and R.tipo = :tipo";
//            Query query1 = session.createQuery(hql1);
//            query1.setLong("idEval", temp.getIdevaluacion());
//            query1.setString("tipo", "apto");
//            Object queryResult1 = query1.uniqueResult();
//            if (query1.uniqueResult() != null) {
//                ultimaResol = (Resolucion) queryResult1;
//                return ultimaResol;
//            }
//
//        }
//        return ultimaResol;
//    }

    public Resolucion getResolucionAptitud2(Long expFam) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Resolucion ultimaResol = new Resolucion();
        final Long idExp = expFam;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call RENAD_APTITUD(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    ultimaResol.setIdresolucion(rs.getLong("IDRESOLUCION"));
                    ultimaResol.setTipo(rs.getString("TIPO"));
                    ultimaResol.setNumero(rs.getString("NUMERO"));
                    ultimaResol.setFechaResol(rs.getDate("FECHA_RESOL"));
                    ultimaResol.setFechaNotificacion(rs.getDate("FECHA_NOTIFICACION"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return ultimaResol;
    }

//    public Designacion getUltimaDesignacion(Long expFam) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Designacion ultimaDesig = new Designacion();
//
//        String hql = "from Designacion D where D.expedienteFamilia = :expFam ORDER BY D.fechaPropuesta DESC";
//        Query query = session.createQuery(hql);
//        query.setLong("expFam", expFam);
//        query.setMaxResults(1);
//        Object queryResultA = query.uniqueResult();
//
//        if (query.uniqueResult() != null) {
//            ultimaDesig = (Designacion) queryResultA;
//        }
//        return ultimaDesig;
//    }

    public Designacion getUltimaDesignacion2(Long expFam) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idExp = expFam;
        final Designacion ultimaDesig = new Designacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_ULTDESIG(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    ultimaDesig.setIddesignacion(rs.getLong("IDDESIGNACION"));
                    ultimaDesig.setFechaPropuesta(rs.getDate("FECHA_PROPUESTA"));

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return ultimaDesig;
    }

//    public Designacion getUltimaDesignacionNna(Long expFam) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Designacion ultimaDesig = new Designacion();
//
//        String hql = "from Designacion D where D.expedienteFamilia = :expFam ORDER BY D.fechaPropuesta DESC";
//        Query query = session.createQuery(hql);
//        query.setLong("expFam", expFam);
//        query.setMaxResults(1);
//        Object queryResultA = query.uniqueResult();
//
//        if (query.uniqueResult() != null) {
//            ultimaDesig = (Designacion) queryResultA;
//            Hibernate.initialize(ultimaDesig.getNna());
//        }
//        return ultimaDesig;
//    }

    public Designacion getUltimaDesignacionNna2(Long expFam) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idExp = expFam;
        final Designacion ultimaDesig = new Designacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_ULTDESIG(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {

                    Nna tempNna = new Nna();
                    tempNna.setIdnna(rs.getLong(3));
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

                    ultimaDesig.setIddesignacion(rs.getLong("IDDESIGNACION"));
                    ultimaDesig.setNDesignacion(rs.getString("N_DESIGNACION"));
                    ultimaDesig.setPrioridad(rs.getLong("PRIORIDAD"));
                    ultimaDesig.setFechaPropuesta(rs.getDate("FECHA_PROPUESTA"));
                    ultimaDesig.setFechaConsejo(rs.getDate("FECHA_CONSEJO"));
                    ultimaDesig.setAceptacionConsejo(rs.getShort("ACEPTACION_CONSEJO"));
                    ultimaDesig.setTipoPropuesta(rs.getString("TIPO_PROPUESTA"));
                    ultimaDesig.setObs(rs.getString("OBS"));

                    ultimaDesig.setNna(tempNna);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return ultimaDesig;
    }

//    public ArrayList<Familia> getRenad() {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        String hql = "FROM Familia F WHERE F.habilitado = 0";
//        Query query = session.createQuery(hql);
//        List familias = query.list();
//        ArrayList<Familia> allFamilias = new ArrayList();
//        for (Iterator iter = familias.iterator(); iter.hasNext();) {
//            Familia temp = (Familia) iter.next();
//            Hibernate.initialize(temp.getEntidad());
//            String hql2 = "FROM ExpedienteFamilia EF WHERE EF.familia = :idFamilia and EF.estado != :estado ORDER BY EF.idexpedienteFamilia DESC";
//            Query query2 = session.createQuery(hql2);
//            query2.setLong("idFamilia", temp.getIdfamilia());
//            query2.setString("estado", "evaluacion");
//            query2.setMaxResults(1);
//            Object queryResult2 = query2.uniqueResult();
//            if (queryResult2 != null) {
//                ExpedienteFamilia tempExp = (ExpedienteFamilia) queryResult2;
//                Hibernate.initialize(tempExp.getUnidad());
//                Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
//                listExp.add(tempExp);
//                temp.setExpedienteFamilias(listExp);
//            }
//
//            String hql3 = "FROM InfoFamilia IF WHERE IF.familia = :idFamilia ORDER BY IF.idinfoFamilia DESC";
//            Query query3 = session.createQuery(hql3);
//            query3.setLong("idFamilia", temp.getIdfamilia());
//            query3.setMaxResults(1);
//            Object queryResult3 = query3.uniqueResult();
//            if (queryResult3 != null) {
//                InfoFamilia infoFam = (InfoFamilia) queryResult3;
//                Hibernate.initialize(infoFam.getAdoptantes());
//                Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
//                listInf.add(infoFam);
//                temp.setInfoFamilias(listInf);
//            }
//            if (query2.uniqueResult() != null && query3.uniqueResult() != null) {
//                allFamilias.add(temp);
//            }
//        }
//
//        return allFamilias;
//
//    }

    public ArrayList<Familia> getRenad_Parte1() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final ArrayList<Familia> allFamilias = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String query = "{call RENAD_PARTE1(?)}";
                CallableStatement statement = connection.prepareCall(query);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
                    Set<Adoptante> listadop = new HashSet<Adoptante>();

                    Familia tempFam = new Familia();
                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                    Unidad tempUnidad = new Unidad();
                    InfoFamilia tempInfo = new InfoFamilia();

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
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

                    tempFam.setUser(rs.getString("USER_"));
                    tempFam.setPass(rs.getString("PASS"));
                    tempFam.setCorreo(rs.getString(5));
                    tempFam.setHabilitado(rs.getShort("HABILITADO"));
                    tempFam.setConstancia(rs.getString("CONSTANCIA"));

                    tempUnidad.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUnidad.setNombre(rs.getString("NOMBRE"));
                    tempUnidad.setDireccion(rs.getString("DIRECCION"));
                    tempUnidad.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempUnidad.setProvincia(rs.getString("PROVINCIA"));
                    tempUnidad.setDistrito(rs.getString("DISTRITO"));
                    tempUnidad.setCompetenciaRegional(rs.getString("COMPETENCIA_REGIONAL"));
                    tempUnidad.setCorreo(rs.getString("CORREO"));
                    tempUnidad.setTelefono(rs.getString("TELEFONO"));
                    tempUnidad.setCelular(rs.getString("CELULAR"));
                    tempUnidad.setObs(rs.getString("OBS"));

                    tempExpFam.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempExpFam.setFamilia(tempFam);
                    tempExpFam.setUnidad(tempUnidad);
                    tempExpFam.setNumero(rs.getLong("NUMERO"));
                    tempExpFam.setExpediente(rs.getString("EXPEDIENTE"));
                    tempExpFam.setHt(rs.getString("HT"));
                    tempExpFam.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempExpFam.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempExpFam.setEstado(rs.getString("ESTADO"));
                    tempExpFam.setTupa(rs.getDate("TUPA"));
                    tempExpFam.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempExpFam.setRnsa(rs.getShort("RNSA"));
                    tempExpFam.setRnaa(rs.getShort("RNAA"));
                    tempExpFam.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempExpFam.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempExpFam.setHtFicha(rs.getString("HTFICHA"));
                    tempExpFam.setnFicha(rs.getString("NFICHA"));
                    tempExpFam.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));
//                     
//                    
//                     
                    tempInfo.setIdinfoFamilia(rs.getLong("IDINFO_FAMILIA"));
                    tempInfo.setFamilia(tempFam);
                    tempInfo.setDepRes(rs.getString("DEP_RES"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDomicilio(rs.getString("DOMICILIO"));
                    tempInfo.setPropiedadVivienda(rs.getString("PROPIEDAD_VIVIENDA"));
                    tempInfo.setTipoVivienda(rs.getString("TIPO_VIVIENDA"));
                    tempInfo.setAreaVivTotal(rs.getLong("AREA_VIV_TOTAL"));
                    tempInfo.setAreaVivConst(rs.getLong("AREA_VIV_CONST"));
                    tempInfo.setDistVivienda(rs.getString("DIST_VIVIENDA"));
                    tempInfo.setLuz(rs.getShort("LUZ"));
                    tempInfo.setAgua(rs.getShort("AGUA"));
                    tempInfo.setDesague(rs.getShort("DESAGUE"));
                    tempInfo.setOtrosServ(rs.getString("OTROS_SERV"));
                    tempInfo.setMaterConst(rs.getString("MATER_CONST"));
                    tempInfo.setPared(rs.getString("PARED"));
                    tempInfo.setTecho(rs.getString("TECHO"));
                    tempInfo.setPiso(rs.getString("PISO"));
                    String charValueStr = "";
                    if (rs.getString("NIVEL_SOCIOECONOMICO") != null) {
                        charValueStr = rs.getString("NIVEL_SOCIOECONOMICO");
                    }
                    if (!charValueStr.equals("") && charValueStr != null) {
                        tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                    }
                    tempInfo.setExpectativaEdadMin(rs.getShort("EXPECTATIVA_EDAD_MIN"));
                    tempInfo.setExpectativaGenero(rs.getString("EXPECTATIVA_GENERO"));
                    tempInfo.setOrigenHijos(rs.getString("ORIGEN_HIJOS"));
                    tempInfo.setPuedeViajar(rs.getShort("PUEDE_VIAJAR"));
                    tempInfo.setPredisposicionAp(rs.getString("PREDISPOSICION_AP"));
                    tempInfo.setCondicion(rs.getString("CONDICION"));
                    tempInfo.setAntecedenteFamilia(rs.getString("ANTECEDENTE_FAMILIA"));
                    tempInfo.setFechaAntecedenteFamilia(rs.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                    tempInfo.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempInfo.setNnaIncesto(rs.getShort("NNA_INCESTO"));
                    tempInfo.setNnaMental(rs.getShort("NNA_MENTAL"));
                    tempInfo.setNnaEpilepsia(rs.getShort("NNA_EPILEPSIA"));
                    tempInfo.setNnaAbuso(rs.getShort("NNA_ABUSO"));
                    tempInfo.setNnaSifilis(rs.getShort("NNA_SIFILIS"));
                    tempInfo.setNnaSeguiMedico(rs.getShort("NNA_SEGUI_MEDICO"));
                    tempInfo.setNnaOperacion(rs.getShort("NNA_OPERACION"));
                    tempInfo.setNnaHiperactivo(rs.getShort("NNA_HIPERACTIVO"));
                    tempInfo.setNnaEspecial(rs.getShort("NNA_ESPECIAL"));
                    tempInfo.setNnaEnfermo(rs.getShort("NNA_ENFERMO"));
                    tempInfo.setNnaMayor(rs.getShort("NNA_MAYOR"));
                    tempInfo.setNnaAdolescente(rs.getShort("NNA_ADOLESCENTE"));
                    tempInfo.setNnaHermano(rs.getShort("NNA_HERMANO"));
                    tempInfo.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    tempInfo.setFechaMatrimonio(rs.getDate("FECHA_MATRIMONIO"));
                    tempInfo.setTelefono(rs.getString("TELEFONO"));
                    tempInfo.setExpectativaEdadMax(rs.getShort("EXPECTATIVA_EDAD_MAX"));
                    tempInfo.setnHijos(rs.getShort("NHIJOS"));
//                     
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
//                        
                    listExp.add(tempExpFam);
                    listInf.add(tempInfo);
//                      
                    tempFam.setExpedienteFamilias(listExp);
                    tempFam.setInfoFamilias(listInf);
                    allFamilias.add(tempFam);

                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);
        return allFamilias;

    }

//    public Designacion getUltimaDesignacionNnaCarJuzExp(Long expFam) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Designacion ultimaDesig = new Designacion();
//
//        String hql = "from Designacion D where D.expedienteFamilia = :expFam ORDER BY D.fechaPropuesta DESC";
//        Query query = session.createQuery(hql);
//        query.setLong("expFam", expFam);
//        query.setMaxResults(1);
//        Object queryResultA = query.uniqueResult();
//
//        if (query.uniqueResult() != null) {
//            ultimaDesig = (Designacion) queryResultA;
//            Hibernate.initialize(ultimaDesig.getNna().getExpedienteNnas());
//            Hibernate.initialize(ultimaDesig.getNna().getCar());
//            Hibernate.initialize(ultimaDesig.getNna().getJuzgado());
//
//        }
//        return ultimaDesig;
//    }

    public Designacion getUltimaDesignacionNnaCarJuzExp2(Long expFam) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        final Long idExp = expFam;
        final Designacion ultimaDesig = new Designacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call RENAD_DESIGNACION(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()){
                    Nna tempNna = new Nna();
                    ExpedienteNna tempExpNna = new ExpedienteNna();
                    Car tempCar = new Car();
                    Juzgado tempJuzg = new Juzgado();
                    Set<ExpedienteNna> listExp = new HashSet<ExpedienteNna>();

                    ultimaDesig.setIddesignacion(rs.getLong("IDDESIGNACION"));
                    ultimaDesig.setNDesignacion(rs.getString("N_DESIGNACION"));
                    ultimaDesig.setPrioridad(rs.getLong("PRIORIDAD"));
                    ultimaDesig.setFechaPropuesta(rs.getDate("FECHA_PROPUESTA"));
                    ultimaDesig.setFechaConsejo(rs.getDate("FECHA_CONSEJO"));
                    ultimaDesig.setAceptacionConsejo(rs.getShort("ACEPTACION_CONSEJO"));
                    ultimaDesig.setTipoPropuesta(rs.getString("TIPO_PROPUESTA"));
                    ultimaDesig.setObs(rs.getString("OBS"));

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
                    tempNna.setObservaciones(rs.getString(46));
                    tempNna.setNResolAband(rs.getString("N_RESOL_ABAND"));
                    tempNna.setNResolCons(rs.getString("N_RESOL_CONS"));

                    tempExpNna.setIdexpedienteNna(rs.getLong("IDEXPEDIENTE_NNA"));
                    tempExpNna.setNumero(rs.getString("NUMERO"));
                    tempExpNna.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                    tempExpNna.setHt(rs.getString("HT"));
                    tempExpNna.setNExpTutelar(rs.getString("N_EXP_TUTELAR"));
                    tempExpNna.setProcTutelar(rs.getString("PROC_TUTELAR"));
                    tempExpNna.setFichaIntegral(rs.getShort("FICHA_INTEGRAL"));
                    tempExpNna.setComentarios(rs.getString("COMENTARIOS"));
                    tempExpNna.setRespLegalNombre(rs.getString("RESP_LEGAL_NOMBRE"));
                    tempExpNna.setRespLegalP(rs.getString("RESP_LEGAL_P"));
                    tempExpNna.setRespLegalM(rs.getString("RESP_LEGAL_M"));
                    tempExpNna.setRespPsicosocialNombre(rs.getString("RESP_PSICOSOCIAL_NOMBRE"));
                    tempExpNna.setRespPiscosocialP(rs.getString("RESP_PISCOSOCIAL_P"));
                    tempExpNna.setRespPsicosocialM(rs.getString("RESP_PSICOSOCIAL_M"));
                    tempExpNna.setEstado(rs.getString("ESTADO"));
                    tempExpNna.setFechaEstado(rs.getDate("FECHA_ESTADO"));
                    tempExpNna.setAdoptable(rs.getShort("ADOPTABLE"));
                    tempExpNna.setFechaResolCons(rs.getDate("FECHA_RESOL_CONS"));
                    tempExpNna.setNacional(rs.getShort("NACIONAL"));
                    tempExpNna.setDiagnostico(rs.getString("DIAGNOSTICO"));
                    tempExpNna.setCodigoReferencia(rs.getString("CODIGO_REFERENCIA"));
                    tempExpNna.setNActual(rs.getString("N_ACTUAL"));
                    tempExpNna.setApellidopActual(rs.getString("APELLIDOP_ACTUAL"));
                    tempExpNna.setApellidomActual(rs.getString("APELLIDOM_ACTUAL"));
                    tempExpNna.setObservaciones(rs.getString(75));
                    tempExpNna.setFechaInvTutelar(rs.getDate("FECHA_INV_TUTELAR"));

                    tempCar.setIdcar(rs.getLong("IDCAR"));
                    tempCar.setNombre(rs.getString(78));
                    tempCar.setDireccion(rs.getString(79));
                    tempCar.setDepartamento(rs.getString(80));
                    tempCar.setProvincia(rs.getString(81));
                    tempCar.setDistrito(rs.getString(82));
                    tempCar.setDirector(rs.getString(83));
                    tempCar.setContacto(rs.getString(84));
                    tempCar.setCorreo(rs.getString(85));
                    tempCar.setFax(rs.getString(86));
                    tempCar.setCelular(rs.getString(87));
                    tempCar.setTelefono(rs.getString(88));
                    tempCar.setObservaciones(rs.getString(89));

                    tempJuzg.setIdjuzgado(rs.getLong(90));
                    tempJuzg.setNombre(rs.getString(91));
                    tempJuzg.setDenominacion(rs.getString(92));
                    tempJuzg.setEspecialidad(rs.getString(93));
                    tempJuzg.setDireccion(rs.getString(94));
                    tempJuzg.setDepartamento(rs.getString(95));
                    tempJuzg.setCorteSuperior(rs.getString(96));
                    tempJuzg.setDistritoJudicial(rs.getString(97));
                    tempJuzg.setNombreJuez(rs.getString(98));
                    tempJuzg.setTelefono(rs.getString(99));
                    tempJuzg.setCorreo(rs.getString(100));
                    tempJuzg.setObservaciones(rs.getString(101));

                    listExp.add(tempExpNna);
                    tempNna.setExpedienteNnas(listExp);
                    tempNna.setCar(tempCar);
                    tempNna.setJuzgado(tempJuzg);

                    ultimaDesig.setNna(tempNna);

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);
        return ultimaDesig;
    }

//    public Evaluacion getEvaluacion(Long expFam, String tipo) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Evaluacion tempEval = new Evaluacion();
//
//        String hql = "from Evaluacion E where E.tipo = :tipo1 and E.expedienteFamilia = :expFam";
//        Query query = session.createQuery(hql);
//        query.setString("tipo1", tipo);
//        query.setLong("expFam", expFam);
//        Object queryResultA = query.uniqueResult();
//
//        if (query.uniqueResult() != null) {
//            tempEval = (Evaluacion) queryResultA;
//        }
//        return tempEval;
//    }

    public Evaluacion getEvaluacion2(Long expFam, String tipo) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Evaluacion tempEval = new Evaluacion();
        final Long idExp = expFam;
        final String temp = tipo;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call RENAD_EVALUACION(?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idExp);
                statement.setString(2, temp);
                statement.registerOutParameter(3, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(3);

                if (rs.next()) {
                    tempEval.setIdevaluacion(rs.getLong("IDEVALUACION"));
                    tempEval.setTipo(rs.getString("TIPO"));
                    tempEval.setFechaAsignacion(rs.getDate("FECHA_ASIGNACION"));
                    tempEval.setResultado(rs.getString("RESULTADO"));
                    tempEval.setFechaResultado(rs.getDate("FECHA_RESULTADO"));
                    tempEval.setObservacion(rs.getString("OBSERVACION"));
                    tempEval.setSustento(rs.getString("SUSTENTO"));
                    tempEval.setNDesignacion(rs.getString("N_DESIGNACION"));
                    tempEval.setNumEval(rs.getString("NUM_EVAL"));

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);
        return tempEval;
    }

//    public Resolucion getResolucionDeEvaluacion(Long idEval) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Resolucion ultimaResol = new Resolucion();
//
//        String hql1 = "FROM Resolucion R WHERE R.evaluacion = :idEval ORDER BY R.idresolucion DESC";
//        Query query1 = session.createQuery(hql1);
//        query1.setLong("idEval", idEval);
//        query1.setMaxResults(1);
//        Object queryResult1 = query1.uniqueResult();
//        if (query1.uniqueResult() != null) {
//            ultimaResol = (Resolucion) queryResult1;
//            return ultimaResol;
//        }
//        return ultimaResol;
//    }

    public Resolucion getResolucionDeEvaluacion2(Long idEval) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idEv = idEval;
        final Resolucion ultimaResol = new Resolucion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ExpedienteNna expnna;

                String hql = "{call RENAD_RESOLEVAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idEv);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    ultimaResol.setIdresolucion(rs.getLong("IDRESOLUCION"));
                    ultimaResol.setTipo(rs.getString("TIPO"));
                    ultimaResol.setNumero(rs.getString("NUMERO"));
                    ultimaResol.setFechaResol(rs.getDate("FECHA_RESOL"));
                    ultimaResol.setFechaNotificacion(rs.getDate("FECHA_NOTIFICACION"));

                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return ultimaResol;
    }

    public Nna getNna(Long idNna) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idEv = idNna;
        final Nna tempNna = new Nna();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_GETNNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idEv);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    tempNna.setIdnna(rs.getLong("IDNNA"));
                    tempNna.setNombre(rs.getString("NOMBRE"));
                    tempNna.setApellidoP(rs.getString("APELLIDO_P"));
                    tempNna.setSexo(rs.getString("SEXO"));
                    tempNna.setLugarNac(rs.getString("LUGAR_NAC"));
                    tempNna.setFechaNacimiento(rs.getDate("FECHA_NACIMIENTO"));
                    tempNna.setClasificacion(rs.getString("CLASIFICACION"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return tempNna;
    }

    public Designacion getDesignacionNna(Long idNna) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long id = idNna;
        final Designacion ultimaDesig = new Designacion();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_DESIGNNA(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, id);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    ultimaDesig.setIddesignacion(rs.getLong("IDDESIGNACION"));
                    ultimaDesig.setFechaPropuesta(rs.getDate("FECHA_PROPUESTA"));
                    ultimaDesig.setFechaConsejo(rs.getDate("FECHA_CONSEJO"));
                }
                rs.close();
                statement.close();
            }
        };

        session.doWork(work);

        return ultimaDesig;
    }

    public ArrayList<Familia> getListaAdoptantesExtranjero() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final ArrayList<Familia> allFamilias = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String query = "{call REPORTE_ADOP_EXT(?)}";
                CallableStatement statement = connection.prepareCall(query);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    Set<ExpedienteFamilia> listExp = new HashSet<ExpedienteFamilia>();
                    Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
                    Set<Adoptante> listadop = new HashSet<Adoptante>();

                    Familia tempFam = new Familia();
                    ExpedienteFamilia tempExpFam = new ExpedienteFamilia();
                    Unidad tempUnidad = new Unidad();
                    InfoFamilia tempInfo = new InfoFamilia();

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
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

                    tempFam.setUser(rs.getString("USER_"));
                    tempFam.setPass(rs.getString("PASS"));
                    tempFam.setCorreo(rs.getString(5));
                    tempFam.setHabilitado(rs.getShort("HABILITADO"));
                    tempFam.setConstancia(rs.getString("CONSTANCIA"));

                    tempUnidad.setIdunidad(rs.getLong("IDUNIDAD"));
                    tempUnidad.setNombre(rs.getString("NOMBRE"));
                    tempUnidad.setDireccion(rs.getString("DIRECCION"));
                    tempUnidad.setDepartamento(rs.getString("DEPARTAMENTO"));
                    tempUnidad.setProvincia(rs.getString("PROVINCIA"));
                    tempUnidad.setDistrito(rs.getString("DISTRITO"));
                    tempUnidad.setCompetenciaRegional(rs.getString("COMPETENCIA_REGIONAL"));
                    tempUnidad.setCorreo(rs.getString("CORREO"));
                    tempUnidad.setTelefono(rs.getString("TELEFONO"));
                    tempUnidad.setCelular(rs.getString("CELULAR"));
                    tempUnidad.setObs(rs.getString("OBS"));

                    tempExpFam.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempExpFam.setFamilia(tempFam);
                    tempExpFam.setUnidad(tempUnidad);
                    tempExpFam.setNumero(rs.getLong("NUMERO"));
                    tempExpFam.setExpediente(rs.getString("EXPEDIENTE"));
                    tempExpFam.setHt(rs.getString("HT"));
                    tempExpFam.setNumeroExpediente(rs.getString("NUMERO_EXPEDIENTE"));
                    tempExpFam.setFechaIngresoDga(rs.getDate("FECHA_INGRESO_DGA"));
                    tempExpFam.setEstado(rs.getString("ESTADO"));
                    tempExpFam.setTupa(rs.getDate("TUPA"));
                    tempExpFam.setNacionalidad(rs.getString("NACIONALIDAD"));
                    tempExpFam.setRnsa(rs.getShort("RNSA"));
                    tempExpFam.setRnaa(rs.getShort("RNAA"));
                    tempExpFam.setTipoFamilia(rs.getString("TIPO_FAMILIA"));
                    tempExpFam.setTipoListaEspera(rs.getString("TIPO_LISTA_ESPERA"));
                    tempExpFam.setHtFicha(rs.getString("HTFICHA"));
                    tempExpFam.setnFicha(rs.getString("NFICHA"));
                    tempExpFam.setFechaIngresoFicha(rs.getDate("FECHA_INGRESO_FICHA"));
//                     
//                    
//                     
                    tempInfo.setIdinfoFamilia(rs.getLong("IDINFO_FAMILIA"));
                    tempInfo.setFamilia(tempFam);
                    tempInfo.setDepRes(rs.getString("DEP_RES"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDomicilio(rs.getString("DOMICILIO"));
                    tempInfo.setPropiedadVivienda(rs.getString("PROPIEDAD_VIVIENDA"));
                    tempInfo.setTipoVivienda(rs.getString("TIPO_VIVIENDA"));
                    tempInfo.setAreaVivTotal(rs.getLong("AREA_VIV_TOTAL"));
                    tempInfo.setAreaVivConst(rs.getLong("AREA_VIV_CONST"));
                    tempInfo.setDistVivienda(rs.getString("DIST_VIVIENDA"));
                    tempInfo.setLuz(rs.getShort("LUZ"));
                    tempInfo.setAgua(rs.getShort("AGUA"));
                    tempInfo.setDesague(rs.getShort("DESAGUE"));
                    tempInfo.setOtrosServ(rs.getString("OTROS_SERV"));
                    tempInfo.setMaterConst(rs.getString("MATER_CONST"));
                    tempInfo.setPared(rs.getString("PARED"));
                    tempInfo.setTecho(rs.getString("TECHO"));
                    tempInfo.setPiso(rs.getString("PISO"));
                    String charValueStr = "";
                    if (rs.getString("NIVEL_SOCIOECONOMICO") != null) {
                        charValueStr = rs.getString("NIVEL_SOCIOECONOMICO");
                    }
                    if (!charValueStr.equals("") && charValueStr != null) {
                        tempInfo.setNivelSocioeconomico(charValueStr.charAt(0));
                    }
                    tempInfo.setExpectativaEdadMin(rs.getShort("EXPECTATIVA_EDAD_MIN"));
                    tempInfo.setExpectativaGenero(rs.getString("EXPECTATIVA_GENERO"));
                    tempInfo.setOrigenHijos(rs.getString("ORIGEN_HIJOS"));
                    tempInfo.setPuedeViajar(rs.getShort("PUEDE_VIAJAR"));
                    tempInfo.setPredisposicionAp(rs.getString("PREDISPOSICION_AP"));
                    tempInfo.setCondicion(rs.getString("CONDICION"));
                    tempInfo.setAntecedenteFamilia(rs.getString("ANTECEDENTE_FAMILIA"));
                    tempInfo.setFechaAntecedenteFamilia(rs.getDate("FECHA_ANTECEDENTE_FAMILIA"));
                    tempInfo.setObservaciones(rs.getString("OBSERVACIONES"));
                    tempInfo.setNnaIncesto(rs.getShort("NNA_INCESTO"));
                    tempInfo.setNnaMental(rs.getShort("NNA_MENTAL"));
                    tempInfo.setNnaEpilepsia(rs.getShort("NNA_EPILEPSIA"));
                    tempInfo.setNnaAbuso(rs.getShort("NNA_ABUSO"));
                    tempInfo.setNnaSifilis(rs.getShort("NNA_SIFILIS"));
                    tempInfo.setNnaSeguiMedico(rs.getShort("NNA_SEGUI_MEDICO"));
                    tempInfo.setNnaOperacion(rs.getShort("NNA_OPERACION"));
                    tempInfo.setNnaHiperactivo(rs.getShort("NNA_HIPERACTIVO"));
                    tempInfo.setNnaEspecial(rs.getShort("NNA_ESPECIAL"));
                    tempInfo.setNnaEnfermo(rs.getShort("NNA_ENFERMO"));
                    tempInfo.setNnaMayor(rs.getShort("NNA_MAYOR"));
                    tempInfo.setNnaAdolescente(rs.getShort("NNA_ADOLESCENTE"));
                    tempInfo.setNnaHermano(rs.getShort("NNA_HERMANO"));
                    tempInfo.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    tempInfo.setFechaMatrimonio(rs.getDate("FECHA_MATRIMONIO"));
                    tempInfo.setTelefono(rs.getString("TELEFONO"));
                    tempInfo.setExpectativaEdadMax(rs.getShort("EXPECTATIVA_EDAD_MAX"));
                    tempInfo.setnHijos(rs.getShort("NHIJOS"));
//                     
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
//                        
                    listExp.add(tempExpFam);
                    listInf.add(tempInfo);
//                      
                    tempFam.setExpedienteFamilias(listExp);
                    tempFam.setInfoFamilias(listInf);
                    allFamilias.add(tempFam);

                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return allFamilias;
    }

    public ArrayList<FormularioSesion> getInscritosSI(Long idsesion) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idsesion_in = idsesion;
        final ArrayList<FormularioSesion> allFamilias = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_INSCRITOS_SESION(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idsesion_in);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    FormularioSesion form = new FormularioSesion();
                    form.setIdformularioSesion(rs.getLong("IDFORMULARIO_SESION"));
                    form.setFechaSol(rs.getDate("FECHA_SOL"));
                    form.setPaisRes(rs.getString("PAIS_RES"));
                    form.setDepRes(rs.getString("DEP_RES"));
                    form.setProvRes(rs.getString("PROV_RES"));
                    form.setDistritoRes(rs.getString("DISTRITO_RES"));
                    form.setDireccionRes(rs.getString("DIRECCION_RES"));
                    form.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    form.setTelefono(rs.getString("TELEFONO"));

                    Set<Asistente> listAsist = getAsistentes(rs.getLong("IDFORMULARIO_SESION"));
                    form.setAsistentes(listAsist);

                    allFamilias.add(form);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        //METODO BUBBLESORT PARA ORDENAR SEGUN EL APELLIDO DE LA ASISTENTE MUJER
        int n = allFamilias.size();
        FormularioSesion auxform;
        for (int i = 0; i < n - 1; i++) {
            ArrayList<Asistente> asist_temp = new ArrayList(allFamilias.get(i).getAsistentes());
            for (int j = i; j < n - 1; j++) {
                ArrayList<Asistente> asist_temp2 = new ArrayList(allFamilias.get(j + 1).getAsistentes());
                if (asist_temp.get(0).getApellidoP().compareToIgnoreCase(asist_temp2.get(0).getApellidoP()) > 0) {
                    auxform = allFamilias.get(i);
                    allFamilias.set(i, allFamilias.get(j + 1));
                    allFamilias.set(j + 1, auxform);
                }
            }
        }

        return allFamilias;
    }

    public Set<Asistente> getAsistentes(Long idformulario) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idform = idformulario;
        final Set<Asistente> listaAsist = new HashSet<Asistente>();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call GET_ASISTENTES(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idform);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                Asistente asist;
                while (rs2.next()) {
                    asist = new Asistente();
                    asist.setIdasistente(rs2.getLong("IDASISTENTE"));
                    asist.setNombre(rs2.getString("NOMBRE"));
                    asist.setApellidoP(rs2.getString("APELLIDO_P"));
                    asist.setApellidoM(rs2.getString("APELLIDO_M"));
                    if (!rs2.wasNull()) {
                        asist.setSexo(rs2.getString("SEXO").charAt(0));
                    }
                    asist.setPaisNac(rs2.getString("PAIS_NAC"));
                    asist.setDepNac(rs2.getString("DEP_NAC"));
                    asist.setProvNac(rs2.getString("PROV_NAC"));
                    asist.setEdad(rs2.getShort("EDAD"));
                    asist.setFechaNac(rs2.getDate("FECHA_NAC"));
                    if (!rs2.wasNull()) {
                        asist.setTipoDoc(rs2.getString("TIPO_DOC").charAt(0));
                    }
                    asist.setNDoc(rs2.getString("N_DOC"));
                    asist.setProfesion(rs2.getString("PROFESION"));
                    asist.setCelular(rs2.getString("CELULAR"));
                    asist.setCorreo(rs2.getString("CORREO"));

                    listaAsist.add(asist);
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return listaAsist;
    }

    public ArrayList<Revision> getRevisionExpMensual(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<Revision> listaRev = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_REVISION_MENSUAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Revision tempRev = new Revision();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempRev.setExpedienteFamilia(tempEF);

                    tempRev.setFechaRevision(rs.getDate("FECHA_REVISION"));
                    listaRev.add(tempRev);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return listaRev;
    }

    public ArrayList<Revision> getRevisionExpHistorico(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<Revision> listaRev = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_REVISION_HISTORICO(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    Revision tempRev = new Revision();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempRev.setExpedienteFamilia(tempEF);

                    tempRev.setFechaRevision(rs.getDate("FECHA_REVISION"));
                    listaRev.add(tempRev);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return listaRev;
    }

    public ArrayList<EstudioCaso> getEstudioCasoMensual(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<EstudioCaso> listaEstudio = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_ESTUDIO_MENSUAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    EstudioCaso tempEst = new EstudioCaso();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempEst.setExpedienteFamilia(tempEF);
                    tempEst.setResultado(rs.getString("RESULTADO"));
                    tempEst.setPrioridad(rs.getLong("PRIORIDAD"));
                    tempEst.setFechaEstudio(rs.getDate("FECHA_ESTUDIO"));
                    tempEst.setFechaSolAdop(rs.getDate("FECHA_SOL_ADOP"));
                    listaEstudio.add(tempEst);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return listaEstudio;
    }

    public ArrayList<EstudioCaso> getEstudioCasoHistorico(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<EstudioCaso> listaEstudio = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_ESTUDIO_HISTORICO(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    EstudioCaso tempEst = new EstudioCaso();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempEst.setExpedienteFamilia(tempEF);
                    tempEst.setPrioridad(rs.getLong("PRIORIDAD"));
                    tempEst.setFechaEstudio(rs.getDate("FECHA_ESTUDIO"));
                    tempEst.setFechaSolAdop(rs.getDate("FECHA_SOL_ADOP"));
                    listaEstudio.add(tempEst);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return listaEstudio;
    }

    public ArrayList<EstudioCaso> getSolicitudAdopcionMensual(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<EstudioCaso> listaEstudio = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_SOLICITUD_MENSUAL(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    EstudioCaso tempEst = new EstudioCaso();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempEst.setExpedienteFamilia(tempEF);
                    tempEst.setPrioridad(rs.getLong("PRIORIDAD"));
                    tempEst.setFechaEstudio(rs.getDate("FECHA_ESTUDIO"));
                    tempEst.setFechaSolAdop(rs.getDate("FECHA_SOL_ADOP"));
                    listaEstudio.add(tempEst);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return listaEstudio;
    }

    public ArrayList<EstudioCaso> getSolicitudAdopcionHistorico(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final ArrayList<EstudioCaso> listaEstudio = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_SOLICITUD_HISTORICO(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                while (rs.next()) {
                    EstudioCaso tempEst = new EstudioCaso();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempEst.setExpedienteFamilia(tempEF);
                    tempEst.setPrioridad(rs.getLong("PRIORIDAD"));
                    tempEst.setFechaEstudio(rs.getDate("FECHA_ESTUDIO"));
                    tempEst.setFechaSolAdop(rs.getDate("FECHA_SOL_ADOP"));
                    listaEstudio.add(tempEst);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return listaEstudio;
    }

    public ArrayList<FormularioSesion> getAsistenciaSI(Long idsesion) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idsesion_in = idsesion;
        final ArrayList<FormularioSesion> allFamilias = new ArrayList();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_ASISTENCIA_SESION(?,?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idsesion_in);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.registerOutParameter(3, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs2 = (ResultSet) statement.getObject(2);
                ResultSet rs = (ResultSet) statement.getObject(3);

                Sesion sesion = new Sesion();

                while (rs2.next()) {
                    sesion.setIdsesion(rs2.getLong("IDSESION"));
                    sesion.setNSesion(rs2.getString("N_SESION"));
                    sesion.setHabilitado(rs2.getShort("HABILITADO"));
                    sesion.setFecha(rs2.getDate("FECHA"));
                    sesion.setHora(rs2.getString("HORA"));
                    sesion.setDireccion(rs2.getString("DIRECCION"));
                    sesion.setDuracion(rs2.getString("DURACION"));
                    sesion.setFacilitador(rs2.getString("FACILITADOR"));
                    sesion.setAsistencia(rs2.getShort("ASISTENCIA"));
                    sesion.setUnidad(rs2.getString("UNIDAD"));
                }
                rs2.close();

                while (rs.next()) {
                    FormularioSesion form = new FormularioSesion();
                    form.setSesion(sesion);
                    form.setIdformularioSesion(rs.getLong("IDFORMULARIO_SESION"));
                    form.setFechaSol(rs.getDate("FECHA_SOL"));
                    form.setPaisRes(rs.getString("PAIS_RES"));
                    form.setDepRes(rs.getString("DEP_RES"));
                    form.setProvRes(rs.getString("PROV_RES"));
                    form.setDistritoRes(rs.getString("DISTRITO_RES"));
                    form.setDireccionRes(rs.getString("DIRECCION_RES"));
                    form.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
                    form.setTelefono(rs.getString("TELEFONO"));

                    Long idfamilia = rs.getLong("IDFAMILIA");

                    if (idfamilia != 0) {
                        form.setFamilia(servicioEtapa.getFamilia(idfamilia));
                    } else {
                        Familia fami = new Familia();
                        fami.setIdfamilia(0);
                        form.setFamilia(fami);
                    }

                    Set<AsistenciaFT> listAsistencia = getAsistencia(rs.getLong("IDFORMULARIO_SESION"));
                    form.setAsistenciaFTs(listAsistencia);

                    Set<Asistente> listAsist = getAsistentes(rs.getLong("IDFORMULARIO_SESION"));
                    form.setAsistentes(listAsist);

                    allFamilias.add(form);
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        //METODO BUBBLESORT PARA ORDENAR SEGUN EL APELLIDO DE LA ASISTENTE MUJER
        int n = allFamilias.size();
        FormularioSesion auxform;
        for (int i = 0; i < n - 1; i++) {
            ArrayList<Asistente> asist_temp = new ArrayList(allFamilias.get(i).getAsistentes());
            for (int j = i; j < n - 1; j++) {
                ArrayList<Asistente> asist_temp2 = new ArrayList(allFamilias.get(j + 1).getAsistentes());
                if (asist_temp.get(0).getApellidoP().compareToIgnoreCase(asist_temp2.get(0).getApellidoP()) > 0) {
                    auxform = allFamilias.get(i);
                    allFamilias.set(i, allFamilias.get(j + 1));
                    allFamilias.set(j + 1, auxform);
                }
            }
        }

        return allFamilias;
    }

    public Set<AsistenciaFT> getAsistencia(Long idformulario) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idform = idformulario;
        final Set<AsistenciaFT> listaAsist = new HashSet<AsistenciaFT>();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_ASISTENCIA(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idform);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                AsistenciaFT asist;
                while (rs2.next()) {
                    asist = new AsistenciaFT();
                    asist.setIdasistenciaFT(rs2.getLong("IDASISTENCIA_F_T"));
                    if (!rs2.wasNull()) {
                        asist.setAsistencia(rs2.getString("ASISTENCIA").charAt(0));
                    }
                    asist.setInasJus(rs2.getShort("INAS_JUS"));

                    listaAsist.add(asist);
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return listaAsist;
    }

    public Designacion getFamiliaDesignadoNna(Long idNna) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idN = idNna;
        final Designacion tempDesig = new Designacion();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql = "{call REPORTE_NNA_DESIG(?,?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.setLong(1, idN);
                statement.registerOutParameter(2, OracleTypes.CURSOR);
                statement.execute();

                ResultSet rs = (ResultSet) statement.getObject(2);

                if (rs.next()) {
                    Set<InfoFamilia> listInf = new HashSet<InfoFamilia>();
                    InfoFamilia tempInfo = new InfoFamilia();
                    ExpedienteFamilia tempEF = new ExpedienteFamilia();
                    Familia tempFam = new Familia();
                    Entidad tempEnt = new Entidad();

                    tempEF.setIdexpedienteFamilia(rs.getLong("IDEXPEDIENTE_FAMILIA"));
                    tempEF.setExpediente(rs.getString("EXPEDIENTE"));
                    tempEF.setNacionalidad(rs.getString("NACIONALIDAD"));

                    tempFam.setIdfamilia(rs.getLong("IDFAMILIA"));
                    tempInfo.setPaisRes(rs.getString("PAIS_RES"));
                    tempInfo.setDepRes(rs.getString("DEP_RES"));
                    listInf.add(tempInfo);
                    tempFam.setInfoFamilias(listInf);
                    Long idEntidad = rs.getLong("IDENTIDAD");
                    if (!rs.wasNull()) {
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
                    tempEF.setFamilia(tempFam);
                    tempDesig.setExpedienteFamilia(tempEF);
                    tempDesig.setFechaConsejo(rs.getDate("FECHA_CONSEJO"));
                }
                rs.close();
                statement.close();
            }
        };
        session.doWork(work);

        return tempDesig;
    }

    //LISTO
    public Turno2 getAsistenciaTaller(Long idtaller, Long idgrupo, Long idturno2) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idtaller_in = idtaller;
        final Long idgrupo_in = idgrupo;
        final Long idturno2_in = idturno2;

        final Taller taller = getTaller(idtaller_in);
        final Grupo grupo = getGrupo(idgrupo_in);
        grupo.setTaller(taller);
        final Turno2 turno2 = getTurno2(idturno2_in);
        turno2.setGrupo(grupo);
        turno2.setReunions(getReunion(idturno2_in));

        final Set<Reunion> listareunionaux = new HashSet<Reunion>();
        for (Reunion reuaux : turno2.getReunions()) {
            reuaux.setAsistenciaFRs(getAsistenciasFR(reuaux.getIdreunion()));
            listareunionaux.add(reuaux);
        }
        turno2.setReunions(listareunionaux);

        //METODO BUBBLESORT PARA ORDENAR SEGUN EL APELLIDO DE LA ASISTENTE MUJER
//        int n = turno2.getReunions();
//        FormularioSesion auxform;
//        for (int i = 0; i < n - 1; i++) {
//            ArrayList<Asistente> asist_temp = new ArrayList(allFamilias.get(i).getAsistentes());
//            for (int j = i; j < n - 1; j++) {
//                ArrayList<Asistente> asist_temp2 = new ArrayList(allFamilias.get(j + 1).getAsistentes());
//                if (asist_temp.get(0).getApellidoP().compareToIgnoreCase(asist_temp2.get(0).getApellidoP()) > 0) {
//                    auxform = allFamilias.get(i);
//                    allFamilias.set(i, allFamilias.get(j + 1));
//                    allFamilias.set(j + 1, auxform);
//                }
//            }
//        }
        return turno2;
    }

    //LISTO
    public Taller getTaller(Long idtaller) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idtaller_in = idtaller;
        final Taller taller = new Taller();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_TALLER(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idtaller_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                while (rs2.next()) {
                    taller.setIdtaller(idtaller_in);
                    taller.setNSesion(rs2.getString("N_SESION"));
                    taller.setTipoTaller(rs2.getString("TIPO_TALLER"));
                    taller.setNombre(rs2.getString("NOMBRE"));
                    taller.setHabilitado(rs2.getShort("HABILITADO"));
                    taller.setNReunion(rs2.getShort("N_REUNION"));
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return taller;
    }

    //LISTO
    public Grupo getGrupo(Long idgrupo) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idgrupo_in = idgrupo;
        final Grupo grupo = new Grupo();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_GRUPO(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idgrupo_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                while (rs2.next()) {
                    grupo.setIdgrupo(idgrupo_in);
                    grupo.setNombre(rs2.getString("NOMBRE"));
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return grupo;
    }

    //LISTO
    public Turno2 getTurno2(Long idturno2) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idturno2_in = idturno2;
        final Turno2 turno2 = new Turno2();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_TURNO2(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idturno2_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                while (rs2.next()) {
                    turno2.setIdturno2(idturno2_in);
                    turno2.setNombre(rs2.getString("NOMBRE"));
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return turno2;
    }

    //LISTO
    public Set<Reunion> getReunion(Long idturno2) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idturno2_in = idturno2;
        final Set<Reunion> listareunion = new HashSet<Reunion>();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_REUNIONES(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idturno2_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                Reunion reunion;
                while (rs2.next()) {
                    reunion = new Reunion();
                    reunion.setIdreunion(rs2.getLong("IDREUNION"));
                    reunion.setFecha(rs2.getDate("FECHA"));
                    reunion.setHora(rs2.getString("HORA"));
                    reunion.setDuracion(rs2.getString("DURACION"));
                    reunion.setDireccion(rs2.getString("DIRECCION"));
                    reunion.setIdentificador(rs2.getShort("IDENTIFICADOR"));
                    reunion.setFacilitador(rs2.getString("FACILITADOR"));
                    reunion.setCapacidad(rs2.getShort("CAPACIDAD"));
                    reunion.setAsistencia(rs2.getShort("ASISTENCIA"));
                    reunion.setUnidad(rs2.getString("UNIDAD"));

                    listareunion.add(reunion);
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return listareunion;
    }

    //LISTO
    public Set<AsistenciaFR> getAsistenciasFR(Long idreunion) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idreunion_in = idreunion;
        final Set<AsistenciaFR> listaAsist = new HashSet<AsistenciaFR>();
        final Set<AsistenciaFR> listaAsistAux = new HashSet<AsistenciaFR>();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_ASISTENCIA_REUNION(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idreunion_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                AsistenciaFR asist;
                Familia fam;
                while (rs2.next()) {
                    asist = new AsistenciaFR();
                    asist.setIdasistenciaFR(rs2.getLong("IDASISTENCIA_F_R"));
                    asist.setAsistencia(rs2.getString("ASISTENCIA").charAt(0));
                    asist.setInasJus(rs2.getShort("INAS_JUS"));

                    fam = new Familia();
                    fam.setIdfamilia(rs2.getLong("IDFAMILIA"));
                    asist.setFamilia(fam);

                    listaAsist.add(asist);
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        AsistenciaFR asistaux2;
        for (AsistenciaFR asistaux : listaAsist) {
            asistaux2 = asistaux;
            asistaux2.setFamilia(getFamilia(asistaux.getFamilia().getIdfamilia()));
            listaAsistAux.add(asistaux2);
        }

        return listaAsistAux;
    }

    public Familia getFamilia(Long idfamilia) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idfamilia_in = idfamilia;
        final Familia fami = new Familia();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql3 = "{call HE_GETFAMILIA(?,?)}";
                CallableStatement statement3 = connection.prepareCall(hql3);
                statement3.setLong(1, idfamilia_in);
                statement3.registerOutParameter(2, OracleTypes.CURSOR);
                statement3.execute();

                ResultSet rs3 = (ResultSet) statement3.getObject(2);

                while (rs3.next()) {
                    fami.setIdfamilia(idfamilia_in);
                    fami.setUser(rs3.getString(3));
                }
                rs3.close();
                statement3.close();
            }
        };
        session.doWork(work);

        fami.setInfoFamilias(getInfoFam(fami.getIdfamilia()));

        return fami;
    }

    public Set<InfoFamilia> getInfoFam(Long idfamilia) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idfamilia_in = idfamilia;
        final Set<InfoFamilia> InfoFami = new HashSet<InfoFamilia>();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_INFOFAM_POR_IDFAM(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idfamilia_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                InfoFamilia ifa;
                while (rs2.next()) {
                    ifa = new InfoFamilia();
                    ifa.setIdinfoFamilia(rs2.getLong("IDINFO_FAMILIA"));
                    ifa.setEstadoCivil(rs2.getString("ESTADO_CIVIL"));

                    InfoFami.add(ifa);
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        for (InfoFamilia ifam : InfoFami) {
            ifam.setAdoptantes(getAdoptantes(ifam.getIdinfoFamilia()));
        }

        return InfoFami;
    }

    public Set<Adoptante> getAdoptantes(Long idinfo_fam) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        final Long idinfo_fam_in = idinfo_fam;
        final Set<Adoptante> listaAdop = new HashSet<Adoptante>();
        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String hql2 = "{call REPORTE_GET_ADOPTANTE(?,?)}";
                CallableStatement statement2 = connection.prepareCall(hql2);
                statement2.setLong(1, idinfo_fam_in);
                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                statement2.execute();

                ResultSet rs2 = (ResultSet) statement2.getObject(2);

                Adoptante adopaux;
                while (rs2.next()) {
                    adopaux = new Adoptante();
                    adopaux.setNombre(rs2.getString("NOMBRE"));
                    adopaux.setApellidoP(rs2.getString("APELLIDO_P"));
                    adopaux.setApellidoM(rs2.getString("APELLIDO_M"));
                    adopaux.setSexo(rs2.getString("SEXO").charAt(0));
                    adopaux.setFechaNac(rs2.getDate("FECHA_NAC"));

                    listaAdop.add(adopaux);
                }
                rs2.close();
                statement2.close();
            }
        };
        session.doWork(work);

        return listaAdop;
    }

    public ArrayList<Designacion> getListaPropuestaDesignacion() {

        Session session = sessionFactory.getCurrentSession();
        final ArrayList<Designacion> allDesig = new ArrayList();

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String hql = "{call REPORTE_PROP_DESIG(?)}";
                CallableStatement statement = connection.prepareCall(hql);
                statement.registerOutParameter(1, OracleTypes.CURSOR);
                statement.execute();

                ResultSet temp_designacion = (ResultSet) statement.getObject(1);
                while (temp_designacion.next()) {
                    Designacion designacion = new Designacion();
                    ExpedienteFamilia expFamilia = new ExpedienteFamilia();
                    Personal personal = new Personal();
                    Nna nna = new Nna();
                    Familia fam = new Familia();
                    Unidad unidad = new Unidad();
                    Juzgado juzgado = new Juzgado();
                    Car car = new Car();

                    designacion.setIddesignacion(temp_designacion.getLong(1));
                    if (temp_designacion.getLong(2) != 0) {

                        String hql_designacion_expFamilia = "{call HE_GET_EXPEDIENTE_FAMILIA(?, ?)}";
                        CallableStatement statement_designacion_expFamilia = connection.prepareCall(hql_designacion_expFamilia);
                        statement_designacion_expFamilia.setLong(1, temp_designacion.getLong(2));
                        statement_designacion_expFamilia.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_expFamilia.execute();
                        ResultSet temp_expediente = (ResultSet) statement_designacion_expFamilia.getObject(2);
                        while (temp_expediente.next()) {
                            expFamilia.setIdexpedienteFamilia(temp_expediente.getLong(1));

                            if (temp_expediente.getLong(2) != 0) {

                                String hql2 = "{call HE_GETFAMILIA(?, ?)}";
                                CallableStatement statement2 = connection.prepareCall(hql2);
                                statement2.setLong(1, temp_expediente.getLong(2));
                                statement2.registerOutParameter(2, OracleTypes.CURSOR);
                                statement2.execute();
                                ResultSet temp2 = (ResultSet) statement2.getObject(2);
                                while (temp2.next()) {
                                    fam.setIdfamilia(temp2.getLong(1));
                                    expFamilia.setFamilia(fam);
                                }
                                temp2.close();
                                statement2.close();
                            }
                            if (temp_expediente.getShort(3) != 0) {

                                String hql3 = "{call HE_GET_UNIDAD(?, ?)}";
                                CallableStatement statement3 = connection.prepareCall(hql3);
                                statement3.setLong(1, temp_expediente.getLong(3));
                                statement3.registerOutParameter(2, OracleTypes.CURSOR);
                                statement3.execute();
                                ResultSet temp2 = (ResultSet) statement3.getObject(2);
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

                            designacion.setExpedienteFamilia(expFamilia);
                        }
                        temp_expediente.close();
                        statement_designacion_expFamilia.close();
                    }
                    if (temp_designacion.getLong(3) != 0) {

                        String hql_designacion_NNA = "{call HE_GET_NNA(?, ?)}";
                        CallableStatement statement_designacion_NNA = connection.prepareCall(hql_designacion_NNA);
                        statement_designacion_NNA.setLong(1, temp_designacion.getLong(3));
                        statement_designacion_NNA.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_NNA.execute();
                        ResultSet temp_nna = (ResultSet) statement_designacion_NNA.getObject(2);
                        while (temp_nna.next()) {
                            nna.setIdnna(temp_nna.getLong(1));

                            if (temp_nna.getLong(2) != 0) {

                                String hql_designacion_NNA_Juzgado = "{call HE_GET_JUZGADO(?, ?)}";
                                CallableStatement statement_designacion_NNA_Juzgado = connection.prepareCall(hql_designacion_NNA_Juzgado);
                                statement_designacion_NNA_Juzgado.setLong(1, temp_nna.getLong(2));
                                statement_designacion_NNA_Juzgado.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_NNA_Juzgado.execute();
                                ResultSet temp_juzgado = (ResultSet) statement_designacion_NNA_Juzgado.getObject(2);
                                while (temp_juzgado.next()) {
                                    juzgado.setIdjuzgado(temp_juzgado.getLong(1));

                                    nna.setJuzgado(juzgado);
                                }
                                temp_juzgado.close();
                                statement_designacion_NNA_Juzgado.close();
                            }

                            if (temp_nna.getLong(3) != 0) {

                                String hql_designacion_NNA_CAR = "{call HE_GET_CAR(?, ?)}";
                                CallableStatement statement_designacion_NNA_CAR = connection.prepareCall(hql_designacion_NNA_CAR);
                                statement_designacion_NNA_CAR.setLong(1, temp_nna.getLong(3));
                                statement_designacion_NNA_CAR.registerOutParameter(2, OracleTypes.CURSOR);
                                statement_designacion_NNA_CAR.execute();
                                ResultSet temp_car = (ResultSet) statement_designacion_NNA_CAR.getObject(2);
                                while (temp_car.next()) {
                                    car.setIdcar(temp_car.getLong(1));
                                    car.setNombre(temp_car.getString("NOMBRE"));
                                    car.setDepartamento(temp_car.getString("DEPARTAMENTO"));
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

                            Set<ExpedienteNna> listaExpNna = new HashSet<ExpedienteNna>();

                            String hql10 = "{call HN_GET_EXPEDIENTE_NNA_UNO(?,?)}";
                            CallableStatement statement10 = connection.prepareCall(hql10);
                            statement10.setLong(1, nna.getIdnna());
                            statement10.registerOutParameter(2, OracleTypes.CURSOR);
                            statement10.execute();

                            ResultSet rs10 = (ResultSet) statement10.getObject(2);

                            if (rs10.next()) {
                                ExpedienteNna expnna = new ExpedienteNna();
                                expnna.setIdexpedienteNna(rs10.getLong(1));
                                expnna.setEstado(rs10.getString(15));
                                expnna.setCodigoReferencia(rs10.getString(21));
                                listaExpNna.add(expnna);
                            }
                            rs10.close();
                            statement10.close();

                            nna.setExpedienteNnas(listaExpNna);
                            designacion.setNna(nna);
                        }
                        temp_nna.close();
                        statement_designacion_NNA.close();
                    }
                    if (temp_designacion.getLong(4) != 0) {

                        String hql_designacion_personal = "{call HE_GET_PERSONAL(?, ?)}";
                        CallableStatement statement_designacion_personal = connection.prepareCall(hql_designacion_personal);
                        statement_designacion_personal.setLong(1, temp_designacion.getLong(4));
                        statement_designacion_personal.registerOutParameter(2, OracleTypes.CURSOR);
                        statement_designacion_personal.execute();
                        ResultSet temp_personal = (ResultSet) statement_designacion_personal.getObject(2);
                        while (temp_personal.next()) {
                            personal.setIdpersonal(temp_personal.getLong(1));
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

                        Set<Adoptante> listadop = new HashSet<Adoptante>();
                        String query7 = "{call RENAD_ADOPTANTE(?,?)}";
                        CallableStatement statement7 = connection.prepareCall(query7);
                        statement7.setLong(1, tempInfo.getIdinfoFamilia());
                        statement7.registerOutParameter(2, OracleTypes.CURSOR);
                        statement7.execute();
                        ResultSet rs7 = (ResultSet) statement7.getObject(2);
                        while (rs7.next()) {
                            Adoptante tempAdoptante = new Adoptante();
                            tempAdoptante.setIdadoptante(rs7.getLong("IDADOPTANTE"));
                            tempAdoptante.setInfoFamilia(tempInfo);
                            tempAdoptante.setNombre(rs7.getString("NOMBRE"));
                            tempAdoptante.setApellidoP(rs7.getString("APELLIDO_P"));
                            tempAdoptante.setApellidoM(rs7.getString("APELLIDO_M"));

                            String tempsexo = "";
                            tempsexo = rs7.getString("SEXO");
                            if (!rs7.wasNull()) {
                                tempAdoptante.setSexo(tempsexo.charAt(0));
                            }

                            tempAdoptante.setFechaNac(rs7.getDate("FECHA_NAC"));
                            tempAdoptante.setLugarNac(rs7.getString("LUGAR_NAC"));
                            tempAdoptante.setDepaNac(rs7.getString("DEPA_NAC"));
                            tempAdoptante.setPaisNac(rs7.getString("PAIS_NAC"));

                            String tempTipoDoc = "";
                            tempTipoDoc = rs7.getString("TIPO_DOC");
                            if (!rs7.wasNull()) {
                                tempAdoptante.setTipoDoc(tempTipoDoc.charAt(0));
                            }

                            tempAdoptante.setNDoc(rs7.getString("N_DOC"));
                            tempAdoptante.setCelular(rs7.getString("CELULAR"));
                            tempAdoptante.setCorreo(rs7.getString("CORREO"));
                            tempAdoptante.setNivelInstruccion(rs7.getString("NIVEL_INSTRUCCION"));
                            tempAdoptante.setCulminoNivel(rs7.getShort("CULMINO_NIVEL"));
                            tempAdoptante.setProfesion(rs7.getString("PROFESION"));
                            tempAdoptante.setTrabajadorDepend(rs7.getShort("TRABAJADOR_DEPEND"));
                            tempAdoptante.setOcupActualDep(rs7.getString("OCUP_ACTUAL_DEP"));
                            tempAdoptante.setCentroTrabajo(rs7.getString("CENTRO_TRABAJO"));
                            tempAdoptante.setDireccionCentro(rs7.getString("DIRECCION_CENTRO"));
                            tempAdoptante.setTelefonoCentro(rs7.getString("TELEFONO_CENTRO"));
                            tempAdoptante.setIngresoDep(rs7.getLong("INGRESO_DEP"));
                            tempAdoptante.setTrabajadorIndepend(rs7.getShort("TRABAJADOR_INDEPEND"));
                            tempAdoptante.setOcupActualInd(rs7.getString("OCUP_ACTUAL_IND"));
                            tempAdoptante.setIngresoIndep(rs7.getLong("INGRESO_INDEP"));
                            tempAdoptante.setSeguroSalud(rs7.getShort("SEGURO_SALUD"));
                            tempAdoptante.setTipoSeguro(rs7.getString("TIPO_SEGURO"));
                            tempAdoptante.setSeguroVida(rs7.getShort("SEGURO_VIDA"));
                            tempAdoptante.setSistPensiones(rs7.getShort("SIST_PENSIONES"));
                            tempAdoptante.setSaludActual(rs7.getString("SALUD_ACTUAL"));

                            listadop.add(tempAdoptante);

                        }
                        rs7.close();
                        statement7.close();
                        
                        tempInfo.setAdoptantes(listadop);

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

}
