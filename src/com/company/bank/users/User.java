package com.company.bank.users;

public class User {
    private String login;
    private String password;
    private Role role;
    private boolean isAccepted;

    public User() {
    }

    public User(String login, String password, Role role, boolean isAccepted) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.isAccepted = isAccepted;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        String sb = "login: " + login + " password: " + password + " role: " + role;
        return sb;
    }
}
