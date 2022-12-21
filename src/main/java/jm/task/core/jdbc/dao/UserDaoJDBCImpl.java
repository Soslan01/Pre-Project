package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    //Создание таблицы;
    public void createUsersTable() throws SQLException {

        String createTableSQL = "CREATE TABLE IF NOT EXISTS USERS("
                + "ID INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE, "
                + "USERNAME VARCHAR(26) NOT NULL, "
                + "LASTNAME VARCHAR(26) NOT NULL, "
                + "AGE INT(3) NOT NULL)";

        try (Connection connection = Util.getDBConnection(); Statement statement = connection.createStatement()) {
            //выполняется SQL запрос в консоли;
            statement.execute(createTableSQL);
            System.out.println("Table \"users\" is created!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Удаление таблицы;
    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getDBConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS myDBTest.USERS");
            System.out.println("Table \"users\" is deleted!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Добавление пользователя в таблицу;
    public void saveUser(String name, String lastName, byte age) {
        String insertTableSQL = "INSERT INTO myDBTest.USERS(USERNAME, LASTNAME, AGE) VALUES(?,?,?)";

        try (Connection connection = Util.getDBConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertTableSQL)) {

            prStatement.setString(1, name);
            prStatement.setString(2, lastName);
            prStatement.setByte(3, age);
            prStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Удаление пользователя по id;
    public void removeUserById(long id) throws SQLException {
        String removeUsersSQL = "DELETE FROM myDBTest.USERS WHERE id = ?";

        try (Connection connection = Util.getDBConnection();
             PreparedStatement prstatement = connection.prepareStatement(removeUsersSQL)) {
            prstatement.setLong(1, id);
            prstatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Вывод всех пользователей из таблицы;
    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        String getUsersSQl = "SELECT ID, USERNAME, LASTNAME, AGE FROM myDBTest.USERS";

        try (Connection connection = Util.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getUsersSQl)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("USERNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return allUsers;
    }

    //Удаление всех пользователей из таблицы;
    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getDBConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE USERS");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
