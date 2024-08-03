package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("Ivan", "Ivanovich", (byte) 50);
        service.saveUser("Виктор", "Иванович", (byte) 65);
        service.saveUser("Ivan", "Ivanovich", (byte) 50);
        service.saveUser("Виктор", "Иванович", (byte) 65);
        service.removeUserById(2);
        service.removeUserById(20);
        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
