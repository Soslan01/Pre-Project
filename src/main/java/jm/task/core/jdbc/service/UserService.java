package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.support.DaoException;

import java.util.List;

public interface UserService {
    void createUsersTable() throws DaoException;

    void dropUsersTable() throws DaoException;

    void saveUser(String name, String lastName, byte age) throws DaoException;

    void removeUserById(long id) throws DaoException;

    List<User> getAllUsers() throws DaoException;

    void cleanUsersTable() throws DaoException;
}
