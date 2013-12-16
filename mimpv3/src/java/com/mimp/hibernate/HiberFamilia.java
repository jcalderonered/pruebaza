/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.hibernate;

import java.util.*;
import org.hibernate.Session;
import com.mimp.bean.*;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("HiberFamilia")
@Transactional
public class HiberFamilia {
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    //<----------USUARIO---------->
    public void CambiaPass(Familia familia) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(familia);
    }
}
