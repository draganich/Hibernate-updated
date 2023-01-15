package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory;
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.URL, URL);
            settings.put(Environment.DIALECT, DIALECT);
            settings.put(Environment.USER, USERNAME);
            settings.put(Environment.PASS, PASSWORD);
            settings.put(Environment.DRIVER, DRIVER);

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (HibernateException e) {
            throw new RuntimeException("Исключение при подключении к Hibernate.");
        }
        return sessionFactory;
}
    public static void closeSessionFactory (SessionFactory sessionFactory){
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
