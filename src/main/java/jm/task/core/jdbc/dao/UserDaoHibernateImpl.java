package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.support.DaoException;
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
    public void createUsersTable() throws DaoException {
        try (Session session = DBUtil.getHibernateSession()) {
            Query query = session.createSQLQuery(createTableSql);
            query.executeUpdate();

            LOG.info("Table \"users\" is created!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void dropUsersTable() throws DaoException {
        try (Session session = DBUtil.getHibernateSession()) {
            Query query = session.createSQLQuery(dropUsersTableSql);
            query.executeUpdate();

            LOG.info("Table \"users\" is deleted!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws DaoException {
        try (Session session = DBUtil.getHibernateSession()) {
            User newUser = new User(name, lastName, age);
            session.save(newUser);

            LOG.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void removeUserById(long id) throws DaoException {
        try (Session session = DBUtil.getHibernateSession()) {
            User deletedUser = new User();
            deletedUser.setId(id);
            session.delete(deletedUser);

            LOG.info("User с id - " + id + " удален из базы!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        try (Session session = DBUtil.getHibernateSession()) {
            List<User> users = session.createQuery("select u from User u", User.class).getResultList();

            LOG.info("Все записи из таблицы User успешно получены!");
            return users;
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public void cleanUsersTable() throws DaoException {
        try (Session session = DBUtil.getHibernateSession();) {
            Query query = session.createSQLQuery(truncateUsersSql);
            query.executeUpdate();

            LOG.info("Таблица \"users\" удалена успешно!");
        } catch (SQLException | HibernateException e) {
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }

    }
}
