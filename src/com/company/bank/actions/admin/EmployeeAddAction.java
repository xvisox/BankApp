package com.company.bank.actions.admin;

import com.company.bank.actions.Action;
import com.company.bank.service.PasswordService;
import com.company.bank.service.UserService;
import com.company.bank.users.Role;
import com.company.bank.users.User;

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
        UserService.saveUsers(usersList);
    }

    private String settingLogin() {
        String login;
        System.out.println("Enter login:");
        login = sc.nextLine();
        while (PasswordService.isAlreadyTaken(login, usersList)) {
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
        while (!PasswordService.passCheck(password)) {
            System.out.println(securityInfo);
            password = sc.nextLine();
        }
        System.out.println("Correct password");
        return password;
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public String getActionName() {
        return "Add Employee";
    }

    @Override
    public void execute() {
        addEmployee();
    }

    @Override
    public String toString() {
        return "Add Employee";
    }
}
