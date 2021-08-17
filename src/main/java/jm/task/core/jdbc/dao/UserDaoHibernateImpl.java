package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = Util.getSession();
            session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS user(" +
                            "id INT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(64)," +
                            "lastName VARCHAR(64)," +
                            "age INT" +
                            ");"
            ).executeUpdate();
            session.getTransaction().rollback();
        } catch (RuntimeException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException x) {
                    x.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = Util.getSession();
            session.beginTransaction();
            session.createSQLQuery(
                    "DROP TABLE IF EXISTS user"
            ).executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException x) {
                    x.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User with name " + name + " was added.");
        } catch (RuntimeException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException x) {
                    x.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException x) {
                    x.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()) {
            return session.createQuery("FROM User").list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException x) {
                    x.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
