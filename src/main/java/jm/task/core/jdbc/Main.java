package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Util.getSessionFactory().openSession();

        UserDao userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Alex", "Smith", (byte) 21);
        userDaoHibernate.saveUser("John", "Brown", (byte) 54);
        userDaoHibernate.saveUser("Kara", "Johnson", (byte) 13);
        userDaoHibernate.saveUser("Bill", "Williams", (byte) 29);

        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
        userDaoHibernate.removeUserById(1);

        Util.closeSessionFactory(Util.getSessionFactory());
    }
}
