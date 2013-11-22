/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mimp.hibernate;

import org.hibernate.Session;

/**
 *
 * @author john
 */
public class Helper {
    Session session = null;

  public Helper() {
  this.session = HibernateUtil.getSessionFactory().getCurrentSession();
  }
}
