package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.DBUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.dao.UserQueries.createTableSql;
import static jm.task.core.jdbc.dao.UserQueries.dropUsersTableSql;
import static jm.task.core.jdbc.dao.UserQueries.truncateUsersSql;

public class UserDaoHibernateImpl implements UserDao {
    private static Logger LOG = Logger.getLogger(UserDaoHibernateImpl.class);

    @Override
    public void createUsersTable() throws SQLException {
        try {
            Session session = DBUtil.getHibernateSession();
            Query query = session.createSQLQuery(createTableSql);
            query.executeUpdate();

            LOG.info("Table \"users\" is created!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        try {
            Session session = DBUtil.getHibernateSession();
            Query query = session.createSQLQuery(dropUsersTableSql);
            query.executeUpdate();

            LOG.info("Table \"users\" is deleted!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try {
            User newUser = new User(name, lastName, age);
            Session session = DBUtil.getHibernateSession();
            session.save(newUser);
            session.close();

            LOG.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        try {
            User deletedUser = new User();
            deletedUser.setId(id);

            Session session = DBUtil.getHibernateSession();
            session.delete(deletedUser);
            session.close();

            LOG.info("User с id - " + id + " удален из базы!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try {
            Session session = DBUtil.getHibernateSession();
            List<User> users = session.createQuery("select u from User u", User.class).getResultList();
            session.close();

            LOG.info("Все записи из таблицы User успешно получены!");
            return users;
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        try {
            Session session = DBUtil.getHibernateSession();
            Query query = session.createSQLQuery(truncateUsersSql);
            query.executeUpdate();
            session.close();

            LOG.info("Таблица \"users\" удалена успешно!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw e;
        }

    }
}
