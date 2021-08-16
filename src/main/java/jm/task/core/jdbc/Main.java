package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("Ivan", "Ivanov", (byte)25);
        service.saveUser("Pjotr", "The Third", (byte)80);
        service.saveUser("Nikita", "Terehov", (byte)32);
        service.saveUser("Mikhail", "Tkachenko", (byte)18);
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
