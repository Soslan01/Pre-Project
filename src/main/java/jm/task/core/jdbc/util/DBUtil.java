package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Logger LOG = Logger.getLogger(DBUtil.class);
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Настройка DriverManager для подключения по простому JDBC
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // Настройка SessionFactory для подключение через Hibernate
            Configuration config = new Configuration();
            config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/myDBTest");
            config.setProperty("hibernate.connection.username", "root");
            config.setProperty("hibernate.connection.password", "sos100398");
            config.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
            config.addAnnotatedClass(User.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable e) {
            LOG.error("Не удалось выполнить настройку подключения к базе данных: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getJdbcConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDBTest", "root", "sos100398");
            if(!connection.isClosed()) {
                LOG.info("Соединение с БД установлено!");
            }
            return connection;
        } catch (SQLException e) {
            LOG.error("Не удалось загрузить класс драйвера!");
            throw e;
        }
    }

    public static Session getHibernateSession() throws SQLException {
        Session session = sessionFactory.openSession();
        if (session.isConnected()){
            LOG.info("Соединение с БД установлено!");
        } else {
            LOG.error("Не удалось загрузить класс драйвера!");
            throw new SQLException("Hibernate cannot connect to database.");
        }
        return session;
    }
}