package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pre-project?useSSL=false",
                    "root",
                    "root"
            );
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Session getSession() {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/manualtest");
            properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            properties.setProperty(Environment.USER, "root");
            properties.setProperty(Environment.PASS, "root");

            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .addProperties(properties)
                    .buildSessionFactory();
        }
        return sessionFactory.openSession();
    }
}
