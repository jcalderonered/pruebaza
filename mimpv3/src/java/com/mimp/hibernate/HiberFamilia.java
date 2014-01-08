/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.hibernate;

import java.util.*;
import org.hibernate.Session;
import com.mimp.bean.*;
import com.mimp.util.dateFormat;
import javax.annotation.Resource;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("HiberFamilia")
@Transactional
public class HiberFamilia {
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    dateFormat format = new dateFormat();
    
    public ArrayList<Taller> listaTalleres (){
        
    Session session = sessionFactory.getCurrentSession();
        
        Short numReun ;
        int cont = 0;
        String size;
        
        session.beginTransaction();
        String hql = "From Taller T order by T.id";
        Query query = session.createQuery(hql);
        List talleres = query.list();
        ArrayList<Taller> allTalleres = new ArrayList();
        
        
        
        
         
        for (Iterator iter = talleres.iterator(); iter.hasNext();) {
                Taller temp = (Taller) iter.next();
                    Hibernate.initialize(temp.getGrupos());
                  Set<Grupo> allGrp = new HashSet<Grupo>(0);  
            for (Grupo grp : temp.getGrupos()) {
                         Hibernate.initialize(grp.getTurno2s());
                  Set<Turno2> allT2 = new HashSet<Turno2>(0);       
                for (Turno2 t2 : grp.getTurno2s()) {
                            Hibernate.initialize(t2.getReunions());
                                 cont = cont + t2.getReunions().size();
                           allT2.add(t2);
                }
                grp.setTurno2s(allT2);
                allGrp.add(grp);
            }
            temp.setGrupos(allGrp);
            size = String.valueOf(cont);
            numReun = Short.parseShort(size);
            temp.setNReunion(numReun);
            allTalleres.add(temp);
            cont = 0;
        }
        return allTalleres;
    
    }
    
    public Sesion sesionMasProx (Date fechaActual){
        
    Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        String hql = "From Sesion S order by S.fecha";
        Query query = session.createQuery(hql);
        List sesiones = query.list();
        ArrayList<Sesion> allSesiones = new ArrayList();
        
        Sesion sesionProx = new Sesion();
        int i = 0;
        for (Iterator iter = sesiones.iterator(); iter.hasNext();) {
            Sesion temp = (Sesion) iter.next();
            
            allSesiones.add(temp);                        
            
            if( (fechaActual.getYear() <=  temp.getFecha().getYear()) && (fechaActual.getMonth() <= temp.getFecha().getMonth())){
            sesionProx = temp;
            break;
            }                        
            
        }
        return sesionProx;
    
    }
    
    
    //<----------USUARIO---------->
    public void CambiaPass(Familia familia) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(familia);
    }
}
