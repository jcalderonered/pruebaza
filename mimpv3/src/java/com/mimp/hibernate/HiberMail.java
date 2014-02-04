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

    public static void generateAndSendEmail(String correo, String pass_plano, String user) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.tls.enable", "true");
        
        props.put("mail.smtp.host", "172.16.100.13");
        
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("formacionadopcion@mimp.gob.pe"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correo));
            message.setSubject("Prueba - Sistema de adopciones");
            message.setText("Estimado adoptante,"
                    + "\n\n Su solicitud de recuperación de contraseña ha sido procesada. Su usuario y contraseña para acceder a la plataforma de adopciones son los siguientes:"
                    + "\n\n Usuario:" + user
                    + "\n\n Contraseña:" + pass_plano
                    + "\n\n Saludos cordiales, ");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Object> usuario(String user) {

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
        queryE.setString("usuario", user);

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

}
