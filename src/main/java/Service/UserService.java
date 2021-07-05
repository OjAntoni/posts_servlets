package Service;

import Entity.Role;
import Entity.User;
import Storage.storage_interfaces.UserStorage;

import java.util.Optional;

public class UserService {
    UserStorage storage;

    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public boolean save(User user) {
        if (user == null) return false;
        if (!storage.existsById(user.getId())) {
            storage.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(User user) {
        if (user == null) return false;
        if (storage.existsById(user.getId())) {
            storage.delete(user);
            return true;
        }
        return false;
    }

    public boolean delete(int userId) {
        return delete(new User(userId,null,null,null, Role.USER));
    }

    public boolean updateUsername(User user, String newUsername) {
        if (user == null || newUsername == null) return false;
        if (storage.existsById(user.getId())) {
            storage.upgradeUsername(user, newUsername);
            return true;
        }
        return false;
    }

    public boolean updateUsername(int userId, String newUsername) {
        return updateUsername(new User(userId,null,null,null, Role.USER),newUsername);
    }

    public boolean updateUserPassword(User user, String newPassword) {
        if (user == null || newPassword == null) return false;
        if (storage.existsById(user.getId())) {
            storage.upgradeUserPassword(user, newPassword);
            return true;
        }
        return false;
    }

    public boolean updateUserPassword(int userId, String newPassword) {
        return updateUserPassword(new User(userId,null,null,null, Role.USER), newPassword);
    }

    public Optional<User> getUserById(int id) {
        if (id <= 0) return Optional.empty();
        if (storage.existsById(id)) {
            return Optional.of(storage.getUserById(id));
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByUsername(String username){
        return Optional.ofNullable(storage.getUserByUsername(username));
    }

    public boolean existsByUsername(String username){
        return storage.existsByUsername(username);
    }
}
