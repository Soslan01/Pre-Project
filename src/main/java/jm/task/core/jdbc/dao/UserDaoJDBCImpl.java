package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.DBUtil;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.dao.UserQueries.createTableSql;
import static jm.task.core.jdbc.dao.UserQueries.dropUsersTableSql;
import static jm.task.core.jdbc.dao.UserQueries.getUsersSql;
import static jm.task.core.jdbc.dao.UserQueries.insertTableSql;
import static jm.task.core.jdbc.dao.UserQueries.removeUsersSql;
import static jm.task.core.jdbc.dao.UserQueries.truncateUsersSql;

public class UserDaoJDBCImpl implements UserDao {
    private static Logger LOG = Logger.getLogger(UserDaoJDBCImpl.class);

    //Создание таблицы;
    public void createUsersTable() throws SQLException {
        try (Connection connection = DBUtil.getJdbcConnection(); Statement statement = connection.createStatement()) {
            //выполняется SQL запрос в консоли;
            statement.execute(createTableSql);

            LOG.info("Table \"users\" is created!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Удаление таблицы;
    public void dropUsersTable() throws SQLException {
        try (Connection connection = DBUtil.getJdbcConnection(); Statement statement = connection.createStatement()) {
            statement.execute(dropUsersTableSql);

            LOG.info("Table \"users\" is deleted!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Добавление пользователя в таблицу;
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = DBUtil.getJdbcConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTableSql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            LOG.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Удаление пользователя по id;
    public void removeUserById(long id) throws SQLException {
        try (Connection connection = DBUtil.getJdbcConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUsersSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            LOG.info("User с id - " + id + " удален из базы!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Вывод всех пользователей из таблицы;
    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();

        try (Connection connection = DBUtil.getJdbcConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getUsersSql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("USERNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                allUsers.add(user);
            }

            LOG.info("Все записи из таблицы User успешно получены!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
        return allUsers;
    }

    //Удаление всех пользователей из таблицы;
    public void cleanUsersTable() throws SQLException {
        try (Connection connection = DBUtil.getJdbcConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(truncateUsersSql);

            LOG.info("Таблица \"users\" удалена успешно!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
