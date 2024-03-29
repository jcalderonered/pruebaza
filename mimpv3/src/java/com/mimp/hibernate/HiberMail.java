/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimp.hibernate;

import com.mimp.bean.Entidad;
import com.mimp.bean.Familia;
import com.mimp.bean.FichaSolicitudAdopcion;
import com.mimp.bean.InfoFamilia;
import com.mimp.bean.Organismo;
import com.mimp.bean.Personal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.annotation.Resource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hernán
 */
@Service("HiberMail")
@Transactional
public class HiberMail {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    ArrayList<Object> temp = new ArrayList<Object>();

    public static void generateAndSendEmail(String correo, String pass_plano, String user) {

        final String username = "formacionadopcion@gmail.com";
        final String password = "cairani.";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("formacionadopcion@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correo));
            message.setSubject("Sistema de adopciones");
            message.setText("Estimado solicitante,"
                    + "\n\n Su solicitud de recuperación de contraseña ha sido procesada. Su usuario y contraseña para acceder a la plataforma de adopciones son los siguientes:"
                    + "\n\n Usuario: " + user
                    + "\n\n Contraseña: " + pass_plano
                    + "\n\n Saludos cordiales, ");
                    

            Transport.send(message);

        } catch (Exception ex) {

        }

        /*catch (MessagingException e) {
         throw new RuntimeException(e);
         }*/
    }

    public static void generateAndSendEmail2(String correo, String pass_plano, String user) {

        final String username = "formacionadopcion@gmail.com";
        final String password = "cairani.";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("formacionadopcion@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correo));
            message.setSubject("Sistema de adopciones");
            message.setText("Estimado solicitante,"
                    + "\n\n Bienvenido al SISTEMA INFORMÁTICO DEL REGISTRO NACIONAL DE ADOPCIONES, "
                    + "sus credenciales para inscribirse al taller son las siguientes:"
                    + "\n\n Usuario: " + user
                    + "\n\n Contraseña: " + pass_plano
//                    + "\n\n Para ingresar al sistema realizar lo siguiente, "
//                    + "\n\n"
//                    + "\n\n"
//                    + "\n\n A. Click directamente en el siguiente link: "
//                    + "\n\n"
//                    + "\n\n       1. Click: http://app.mimp.gob.pe:8080/sirna "
//                    + "\n\n       2. Ingresar con el usuario y contraseña mencionadas líneas arriba. "
//                    + "\n\n"
//                    + "\n\n B. En caso no funcione el link, ingresar al sistema desde la página web: "
//                    + "\n\n"
//                    + "\n\n       1. Ingresar a la página web: www.mimp.gob.pe "
//                    + "\n\n       2. En la barra de menú “Direcciones Generales” submenú “Vicemin. Pob. Vulnerables” seleccionar “Adopciones” "
//                    + "\n\n       3. Click en SIRNA “Sistema Informático del Registro Nacional de Adopciones” "
//                    + "\n\n       4. Ingresar con el usuario y contraseña mencionadas líneas arriba. "
//                    + "\n\n"
//                    + "\n\n A través del SIRNA usted podrá realizar las siguientes acciones: "
//                    + "\n\n"
//                    + "\n\n       - Inscribirse a uno de los talleres programados. "
//                    + "\n\n       - Descargar las lecturas de su taller. "
//                    + "\n\n       - Revisar el estado del proceso de adopción. "
//                    + "\n\n       - Cambiar su contraseña. "
//                    + "\n\n"
//                    + "\n\n Para continuar con el proceso, por favor ingresar al sistema e inscribirse a uno de los talleres programados, hasta un día antes de inicio del taller y/o las bacantes se  "
//                    + "\n\n encuentres disponibles. "
                    + "\n\n"
                    + "\n\n De tener alguna complicación y no fue posible su ingreso al sistema, comunicarse inmediatamente con la unidad de adopción correspondiente.  "
                    + "\n\n"
                    + "\n\n Atentamente, "
                    + "\n\n"
                    + "\n\n Dirección General de Adopciones "
                    + "\n\n"
                    + "\n\n Ministerio de la Mujer y Poblaciones Vulnerables "
                    + "\n\n ");

            Transport.send(message);

            /*  } catch (Exception ex) {
             */
        } catch (Exception ex) {

        }

        /*catch (MessagingException e) {
         throw new RuntimeException(e);
         }*/
    }

 /*   public ArrayList<Object> usuario(String user) {

        org.hibernate.Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Personal personal;
        Entidad entidad;
        Familia familia;

        ArrayList<Object> temp = new ArrayList<Object>();

        String hqlP = "FROM Personal P WHERE P.user = :usuario ";//:=login
        String hqlF = "FROM Familia F WHERE F.user = :usuario  ";
        String hqlE = "FROM Entidad R WHERE R.user = :usuario ";

        Query queryP = session.createQuery(hqlP);
        queryP.setString("usuario", user);

        Object queryResultP = queryP.uniqueResult();

        Query queryF = session.createQuery(hqlF);
        queryF.setString("usuario", user);

        Object queryResultF = queryF.uniqueResult();

        Query queryE = session.createQuery(hqlE);

        Object queryResultE = queryE.uniqueResult();

        if (queryResultP != null) {
            personal = (Personal) queryResultP;

            temp.add("personal");
            temp.add(personal);

            return temp;
        } else if (queryResultF != null) {
            familia = (Familia) queryResultF;

            for (Iterator iter = familia.getInfoFamilias().iterator(); iter.hasNext();) {
                InfoFamilia ifa = (InfoFamilia) iter.next();

            }
            for (Iterator iter2 = familia.getFichaSolicitudAdopcions().iterator(); iter2.hasNext();) {
                FichaSolicitudAdopcion ficha = (FichaSolicitudAdopcion) iter2.next();

            }
            temp.add("familia");
            temp.add(familia);
            return temp;
        } else if (queryResultE != null) {
            entidad = (Entidad) queryResultE;

            for (Iterator iter = entidad.getOrganismos().iterator(); iter.hasNext();) {
                Organismo org = (Organismo) iter.next();

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
*/
    public ArrayList<Object> usuario2(String user, String pass) {
        
        org.hibernate.Session session = sessionFactory.getCurrentSession();

        final String usuario = user;
        final String password = pass;

        Work work = new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String query = "{call CONTRASENA(?, ?, ?, ?)}";
                CallableStatement statement = connection.prepareCall(query);
                statement.setString(1, usuario);
                statement.setString(2, password);
                statement.registerOutParameter(3, java.sql.Types.VARCHAR);
                statement.registerOutParameter(4, java.sql.Types.VARCHAR);
                statement.execute();

                String correo = statement.getString(3);
                String mensaje = statement.getString(4);
                temp.add(0, correo);
                temp.add(1, mensaje);
                statement.close();
            }
        };
        
        session.doWork(work);

        return temp;
    }

}
