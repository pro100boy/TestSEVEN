package com.seven.test.service;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Company;
import com.seven.test.model.Role;
import com.seven.test.model.User;
import com.seven.test.util.PasswordUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;

import static com.seven.test.util.UserUtil.*;

@Component
public class SomeService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private SessionFactory hibernateFactory;

    @Autowired
    public SomeService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    // save 1000 users
    public void save() {
        SessionFactory factory = hibernateFactory.unwrap(SessionFactory.class);
        Session session = factory.openSession();
        Transaction tx = null;
        Company company = AuthorizedUser.company();
        try {
            tx = session.beginTransaction();
            for (int i = 0; i < 1_000; i++) {
                session.save(new User(
                        // Integer id, String name, String lastname, String email, String password, Set<Role> roles, String phone, Company company
                        null, RSG_LETTERS.generate(5), RSG_LETTERS.generate(10), RSG_LETTERS.generate(5)+"@"+RSG_LETTERS.generate(5)+".com",
                        PasswordUtil.encode(RSG_PASSWD.generate(15)), Collections.singleton(Role.COMPANY_EMPLOYER), "+" + RSG_DIGITS.generate(10), company
                ));

                if (i % 50 == 0) {
                    session.flush();
                    session.clear();
                    log.info(">>> saved: " + i + " users");
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
