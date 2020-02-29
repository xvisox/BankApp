package com.company.bank.actions.registration;

import com.company.bank.actions.Action;
import com.company.bank.database.Database;
import com.company.bank.users.Role;
import com.company.bank.users.User;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SignUp implements Action {
    private List<User> usersList;
    private Scanner sc;

    public SignUp(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private void signUp() {
        String login, password;
        login = settingLogin();
        password = settingPassword();
        User user = new User(login, password, Role.CUSTOMER, false);
        usersList.add(user);
        saveUsers();
    }

    private String settingPassword() {
        String password, securityInfo;
        securityInfo = "Your password isnt secure, try following above instructions";

        System.out.println("Enter password: ");
        password = sc.nextLine();
        while (!passCheck(password)) {
            System.out.println(securityInfo);
            password = sc.nextLine();
        }
        System.out.println("Correct password");
        return password;
    }

    private String settingLogin() {
        String login;
        System.out.println("Enter login:");
        login = sc.nextLine();
        while (isAlreadyTaken(login)) {
            System.out.println("Username is already taken, try again");
            login = sc.nextLine();
        }
        return login;
    }

    private static boolean passCheck(String password) {
        boolean valid = true;
        if (password.length() < 8) {
            System.out.println("Password is not eight characters long.");
            valid = false;
        }
        String upperCase = "(.*[A-Z].*)";
        if (!password.matches(upperCase)) {
            System.out.println("Password must contain at least one capital letter.");
            valid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            System.out.println("Password must contain at least one number.");
            valid = false;
        }
        String specialChars = "(.*[ ! # @ $ % ^ & * ( ) - _ = + [ ] ; : ' \" , < . > / ?].*)";
        if (!password.matches(specialChars)) {
            System.out.println("Password must contain at least one special character.");
            valid = false;
        }
        String space = "(.*[   ].*)";
        if (password.matches(space)) {
            System.out.println("Password cannot contain a space.");
            valid = false;
        }
        if (password.startsWith("?")) {
            System.out.println("Password cannot start with '?'.");
            valid = false;

        }
        if (password.startsWith("!")) {
            System.out.println("Password cannot start with '!'.");
            valid = false;
        }
        return valid;
    }

    private boolean isAlreadyTaken(String login) {
        for (User user : usersList) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private void saveUsers() {
        List<String> lines = new ArrayList<>();
        for (User user : usersList) {
            StringBuilder sb = new StringBuilder();
            sb.append(user.getLogin());
            sb.append(",");
            sb.append(user.getPassword());
            sb.append(",");
            sb.append(user.getRole());
            sb.append(",");
            sb.append(user.isAccepted());
            lines.add(sb.toString());
        }
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new FileOutputStream(Database.usersFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            pr.println(line);
            pr.flush();
        }
        pr.close();
    }

    public static List<User> loadUsers() {
        List<User> usersList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Database.usersFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] split = line.split(",");
                User user = new User();
                user.setLogin(split[0]);
                user.setPassword(split[1]);
                user.setRole(Role.valueOf(split[2]));//???????? help me
                user.setAccepted(Boolean.parseBoolean(split[3]));
                usersList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public String getActionName() {
        return "Registration";
    }

    @Override
    public void execute() {
        signUp();
    }
}
