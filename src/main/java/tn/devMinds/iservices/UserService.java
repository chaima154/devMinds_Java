package tn.devMinds.iservices;


import tn.devMinds.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    // Dummy database for demonstration
    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();
    }

    // Create a new user
    public void createUser(User user) {
        userList.add(user);
        System.out.println("User created: " + user.getFirstName() + " " + user.getLastName());
    }

    // Update an existing user
    public void updateUser(User updatedUser) {
        for (User user : userList) {
            if (user.getId() == updatedUser.getId()) {
                user.setFirstName(updatedUser.getFirstName());
                user.setLastName(updatedUser.getLastName());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                System.out.println("User updated: " + updatedUser.getFirstName() + " " + updatedUser.getLastName());
                return;
            }
        }
        System.out.println("User not found!");
    }

    // Read all users
    public List<User> getAllUsers() {
        return userList;
    }

    // Delete a user
    public void deleteUser(int userId) {
        for (User user : userList) {
            if (user.getId() == userId) {
                userList.remove(user);
                System.out.println("User deleted: " + user.getFirstName() + " " + user.getLastName());
                return;
            }
        }
        System.out.println("User not found!");
    }
}

