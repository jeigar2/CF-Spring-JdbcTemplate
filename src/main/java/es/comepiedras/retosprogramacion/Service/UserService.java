package es.comepiedras.retosprogramacion.Service;

import es.comepiedras.retosprogramacion.model.User;
import es.comepiedras.retosprogramacion.repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void createUser(User user){
        userRepository.createUser(user);
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void updateUserByEmail(User user) {
        userRepository.updateUserByEmail(user);
    }

    public void deleteUser(int id){
        userRepository.deleteUser(id);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void initDB() {
        userRepository.initDB();
    }

}
