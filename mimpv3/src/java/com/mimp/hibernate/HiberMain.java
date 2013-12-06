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
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    
  public ArrayList<Object> usuario (String user, String pass){
      
      Session session = sessionFactory.getCurrentSession();
      session.beginTransaction();  
      Personal personal = new Personal();
      Representante representante = new Representante();
      Autoridad autoridad = new Autoridad();
      Familia familia = new Familia();
      
        ArrayList<Object> temp = new ArrayList<Object>();
      
      
      
        String hqlP = "FROM Personal P WHERE P.user = :usuario and P.pass = :password ";//:=login
        String hqlF = "FROM Familia F WHERE F.user = :usuario and F.pass = :password ";
        String hqlR = "FROM Representante R WHERE R.user = :usuario and R.pass = :password ";
        String hqlA = "FROM Autoridad A WHERE A.user = :usuario and A.pass = :password ";
        
        Query queryP = session.createQuery(hqlP);
        queryP.setString("usuario", user);
        queryP.setString("password", pass);
        Object queryResultP = queryP.uniqueResult();  
        
        Query queryF = session.createQuery(hqlF);
        queryF.setString("usuario", user);
        queryF.setString("password", pass);
        Object queryResultF = queryF.uniqueResult();  
        
        Query queryR = session.createQuery(hqlR);
        queryR.setString("usuario", user);
        queryR.setString("password", pass);
        Object queryResultR = queryR.uniqueResult();  
        
        Query queryA = session.createQuery(hqlA);
        queryA.setString("usuario", user);
        queryA.setString("password", pass);
        Object queryResultA = queryA.uniqueResult();  
        
        //session.getTransaction().commit(); 
        
        if (queryResultP != null){
            personal = (Personal) queryResultP;
            temp.add("personal");
            temp.add(personal);
            return temp;
        }else if (queryResultF != null){
            familia = (Familia) queryResultF;
            temp.add("familia");
            temp.add(queryResultF);
            return temp;
        }else if (queryResultR != null){
            representante = (Representante) queryResultR;
            temp.add("representante");
            temp.add(queryResultR);
            return temp;
        }else if (queryResultA != null){
            autoridad = (Autoridad) queryResultA;
            temp.add("autoridad");
            temp.add(queryResultA);
            return temp;
        }else {
            
            temp.add("none");
            return temp;
        }
        //temp.add(results);
      
  }
  
   public ArrayList<Turno> ListaTurnos (){
       
       Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
    
    //Date now = new Date();
    
    
    String queryTurnos = "FROM Turno as T WHERE cast(T.inicioInscripcion as date) = current_date() "
                        + "and cast(T.finInscripcion as date) = current_date() order by T.inicioInscripcion";
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
   
    public Turno getTurno (int id){
    
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String queryTurno = "FROM Turno T WHERE T.id = :id ";
        Query query = session.createQuery(queryTurno);
        query.setInteger("id", id);
        Object queryResult = query.uniqueResult();  
        Turno temp = (Turno)queryResult;
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
  
  public void InsertFormGrp(Asistente asisEl, Asistente asisElla, FormularioSesion fs, AsistenciaFT aft) {

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        
        session.save(fs);
        aft.setFormularioSesion(fs);
        
        asisEl.setFormularioSesion(fs);
        asisElla.setFormularioSesion(fs);
        fs.getAsistentes().add(asisEl);
        fs.getAsistentes().add(asisElla);

        session.save(asisEl);
        session.save(asisElla);
        session.save(aft);
    }
  
    public ArrayList<Asistente> listaAsistentes(int id){
    
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        String hql = "FROM Asistente A where A.formularioSesion.sesion.idsesion = :id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        List asistentes = query.list();
        ArrayList<Asistente> allAsistentes = new ArrayList();
        for (Iterator iter = asistentes.iterator(); iter.hasNext();) {
            Asistente temp = (Asistente) iter.next();
            Hibernate.initialize(temp.getFormularioSesion());
            allAsistentes.add(temp);
        }
        return allAsistentes;
    
    
    
    }
  
}
