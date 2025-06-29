package com.example.finance;

import android.content.Context;

import com.example.finance.data.AppDatabase;
import com.example.finance.data.User;
import com.example.finance.data.UserDao;
import com.example.finance.security.SecurityUtils;

import java.util.List;

public class UserManager {
    private final UserDao userDao;

    public UserManager(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
    }

    public void registerUser(String username, String nome, String email, String password, byte[] foto, RegistrationCallback callback) {
        new Thread(() -> {
            try {
                if (userDao.getUserByUsername(username) != null) {
                    callback.onRegistrationResult(false);
                    return;
                }

                String hashed = SecurityUtils.hashPassword(password);

                User user = new User();
                user.username = username;
                user.nome = nome;
                user.email = email;
                user.hashedPassword = hashed;
                user.foto = foto;

                userDao.insert(user);
                callback.onRegistrationResult(true);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onRegistrationResult(false);
            }
        }).start();
    }

    public interface RegistrationCallback {
        void onRegistrationResult(boolean success);
    }

    public void updateUser(String username, String newPassword, String newInfo, String aesKey) {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            if (user != null) {
                if (!newPassword.isEmpty()) {
                    user.hashedPassword = SecurityUtils.hashPassword(newPassword);
                }
                userDao.update(user);
            }
        }).start();
    }

    public void deleteUser(String username) {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            if (user != null) userDao.delete(user);
        }).start();
    }

    public void getUser(String username, UserCallback callback) {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            callback.onUserLoaded(user);
        }).start();
    }

    public interface UserCallback {
        void onUserLoaded(User user);
    }
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void loginUser(String username, String password, LoginCallback callback) {
        new Thread(() -> {
            User user = userDao.getUserByUsername(username);
            if (user != null && SecurityUtils.checkPassword(password, user.hashedPassword)) {
                callback.onLoginResult(true, user);
            } else {
                callback.onLoginResult(false, null);
            }
        }).start();
    }

    public interface LoginCallback {
        void onLoginResult(boolean success, User user);
    }

}

