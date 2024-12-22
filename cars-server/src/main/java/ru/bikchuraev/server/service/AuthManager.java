package ru.bikchuraev.server.service;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AuthManager {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private boolean loggedIn = false;

    public boolean login(String login, String password) {
        loggedIn = LOGIN.equals(login) && PASSWORD.equals(password);
        return loggedIn;
    }

    public void logout() {
        loggedIn = false;
    }
}
