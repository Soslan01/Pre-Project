package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getDBConnection () {
        Connection connection = null;
        try {

            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDBTest", "root", "sos100398");

            if(!connection.isClosed()) {
                System.out.println("Соединение с БД установлено!");
            }

        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера!");
        }
        return connection;
    }
}