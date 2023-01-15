package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS mydb.users"
                    + "(id INT NOT NULL AUTO_INCREMENT," + "name VARCHAR(45)," + "lastName VARCHAR(45),"
                    + "age INT," + "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();

            System.out.println("Таблица успешно создана.");
        } catch (HibernateException e) {
            throw new RuntimeException("Исключение при создании таблицы.");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS mydb.users").executeUpdate();
            transaction.commit();

            System.out.println("Таблица успешно удалена.");
        } catch (HibernateException e) {
            throw new RuntimeException("Исключение при удалении таблицы.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();

            System.out.println("Пользователь с именем " + name + " добавлен в базу данных.");
        } catch (HibernateException e) {
            throw new RuntimeException("Исключение при добавлении параметров пользователя.");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();

            System.out.println("Пользователь с id = " + id + " успешно удалён.");
        } catch (HibernateException e) {
            throw new RuntimeException("Исключение при удалении данных о пользователе.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers;
        try (Session session = Util.getSessionFactory().openSession()) {
            allUsers = session.createQuery("FROM User", User.class).list();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE mydb.users").executeUpdate();
            transaction.commit();

            System.out.println("Все пользователи удалены из базы данных.");
        } catch (Exception e) {
            throw new RuntimeException("Исключение при удалении данных о всех пользователях.");
        }
    }
}

