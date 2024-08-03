package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String tableName = "users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ( " +
                    "id BIGINT primary key auto_increment," +
                    "name varchar(40)," +
                    "lastname varchar(40)," +
                    "age TINYINT DEFAULT 18 CHECK(Age > 0 AND Age < 100))";
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "DROP TABLE IF EXISTS " + tableName;
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "INSERT INTO " + tableName + " (name, lastname, age) SELECT "
                    + "'" + name + "',"
                    + "'" + lastName + "',"
                    + age;
            conn.createStatement().executeUpdate(sql);
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "DELETE FROM " + tableName + " WHERE id = " + id;
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "SELECT * FROM " + tableName;
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "TRUNCATE TABLE " + tableName;
            conn.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
