package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.support.DaoException;
import jm.task.core.jdbc.util.DBUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.dao.UserQueries.createTableSql;
import static jm.task.core.jdbc.dao.UserQueries.dropUsersTableSql;
import static jm.task.core.jdbc.dao.UserQueries.truncateUsersSql;

public class UserDaoHibernateImpl implements UserDao {
    private static Logger LOG = Logger.getLogger(UserDaoHibernateImpl.class);

    @Override
    @Transactional
    public void createUsersTable() throws DaoException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBUtil.getHibernateSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(createTableSql);
            query.executeUpdate();
            transaction.commit();

            LOG.info("Table \"users\" is created!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void dropUsersTable() throws DaoException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBUtil.getHibernateSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(dropUsersTableSql);
            query.executeUpdate();
            transaction.commit();

            LOG.info("Table \"users\" is deleted!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            session.close();
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) throws DaoException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBUtil.getHibernateSession();
            transaction = session.beginTransaction();
            User newUser = new User(name, lastName, age);
            session.save(newUser);
            transaction.commit();

            LOG.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            session.close();
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void removeUserById(long id) throws DaoException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBUtil.getHibernateSession();
            transaction = session.beginTransaction();
            User deletedUser = new User();
            deletedUser.setId(id);
            session.delete(deletedUser);
            transaction.commit();

            LOG.info("User с id - " + id + " удален из базы!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            session.close();
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws DaoException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBUtil.getHibernateSession();
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("select u from User u", User.class).getResultList();
            transaction.commit();

            LOG.info("Все записи из таблицы User успешно получены!");
            return users;
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            session.close();
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void cleanUsersTable() throws DaoException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBUtil.getHibernateSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(truncateUsersSql);
            query.executeUpdate();
            transaction.commit();

            LOG.info("Таблица \"users\" удалена успешно!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            session.close();
            LOG.error(e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }
}
