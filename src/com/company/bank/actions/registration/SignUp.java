package com.company.bank.actions.registration;

import com.company.bank.service.PasswordService;
import com.company.bank.service.UserService;
import com.company.bank.users.Role;
import com.company.bank.users.User;


import java.util.List;
import java.util.Scanner;

public class SignUp {
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
        UserService.saveUsers(usersList);
    }

    private String settingPassword() {
        String password, securityInfo;
        securityInfo = "Your password isnt secure, try following above instructions";

        System.out.println("Enter password: ");
        password = sc.nextLine();
        while (!PasswordService.passCheck(password)) {
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
        while (PasswordService.isAlreadyTaken(login, usersList)) {
            System.out.println("Username is already taken, try again");
            login = sc.nextLine();
        }
        return login;
    }

    public void execute() {
        signUp();
    }
}
