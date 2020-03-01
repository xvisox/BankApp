package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.database.Database;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeAddAction implements Action {
    private List<User> usersList;

    public EmployeeAddAction(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private Scanner sc;

    private void addEmployee() {
        String login, password;
        login = settingLogin();
        password = settingPassword();
        User user = new User(login, password, Role.EMPLOYEE, true);
        usersList.add(user);
        saveUsers();
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

    private String settingPassword() {
        String password, securityInfo;
        securityInfo = "Password isnt secure, try following above instructions";

        System.out.println("Enter password: ");
        password = sc.nextLine();
        while (!passCheck(password)) {
            System.out.println(securityInfo);
            password = sc.nextLine();
        }
        System.out.println("Correct password");
        return password;
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


    @Override
    public String getActionName() {
        return "Add Employee";
    }

    @Override
    public void execute() {
        addEmployee();
    }
}
