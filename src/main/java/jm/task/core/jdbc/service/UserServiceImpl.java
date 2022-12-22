package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.support.DaoException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao dao;

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    public void createUsersTable() throws DaoException {
        dao.createUsersTable();
    }

    public void dropUsersTable() throws DaoException {
        dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws DaoException {
        dao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws DaoException {
        dao.removeUserById(id);
    }

    public List<User> getAllUsers() throws DaoException {
        List<User> users = dao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    public void cleanUsersTable() throws DaoException {
        dao.cleanUsersTable();
    }
}
