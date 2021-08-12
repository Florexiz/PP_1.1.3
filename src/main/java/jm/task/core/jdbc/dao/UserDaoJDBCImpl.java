package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS user(" +
                    "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(64)," +
                    "last_name VARCHAR(64)," +
                    "age INT" +
                    ");"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT user (name, last_name, age) VALUES  (?, ?, ?);")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User with name " + name + " was added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE user_id=?;")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery("SELECT * FROM user");
            List<User> users = new ArrayList<>();
            while (set.next()) {
                users.add(new User (
                        set.getString("name"),
                        set.getString("last_name"),
                        set.getByte("age")
                ));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE  user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
