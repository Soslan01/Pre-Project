package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.support.CommonDaoException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void createUsersTable() throws CommonDaoException;

    void dropUsersTable() throws CommonDaoException;

    void saveUser(String name, String lastName, byte age) throws CommonDaoException;

    void removeUserById(long id) throws CommonDaoException;

    List<User> getAllUsers() throws CommonDaoException;

    void cleanUsersTable() throws CommonDaoException;
}
