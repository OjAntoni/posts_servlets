package Storage.DbStorage;

import Entity.Role;
import Entity.User;
import Storage.storage_interfaces.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUserStorage extends DbStorage implements UserStorage {

    public DbUserStorage(){
        super();
    }

    @Override
    public void save(User user) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values(DEFAULT,?,?,?,?) returning id");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, switch (user.getRole()) {
                                                            case USER -> "USER";
                                                            case ADMIN -> "ADMIN";
                                                        });
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        if (user == null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name = 'Unnamed', username = 'deleted', password=''");
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void upgradeUsername(User user, String newUsername) {
        if (user == null || newUsername == null || newUsername.equals("")) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set username=? where id=?");
            preparedStatement.setString(1, newUsername);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void upgradeUserPassword(User user, String newPassword) {
        if (user == null || newPassword == null || newPassword.equals("")) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users  set  password=? where id =?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int anInt = resultSet.getInt(1);
            String string = resultSet.getString(2);
            String string1 = resultSet.getString(3);
            String string2 = resultSet.getString(4);
            String string3 = resultSet.getString(5);
            return new User(anInt, string, string1, string2, switch (string3){
                case "ADMIN" -> Role.ADMIN;
                default -> Role.USER;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        if(username==null || username.isBlank()) return null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String userName = resultSet.getString(3);
            String password = resultSet.getString(4);
            Role role = switch(resultSet.getString(5)){
                case "ADMIN" -> Role.ADMIN;
                default -> Role.USER;
            };
            return new User(id, name, userName, password, role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
