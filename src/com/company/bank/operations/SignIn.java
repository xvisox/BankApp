package com.company.bank.operations;

import com.company.bank.users.User;

import java.util.List;
import java.util.Scanner;

public class SignIn implements Action {
    private List<User> users;
    private Scanner sc;

    public void signIn(String login, String password) {
        System.out.println("Podaj login:");
        login = sc.nextLine();


    }

    public boolean isAlreadyTaken(String login) {
        for (User user : users) {
            if (user.getLogin() == login) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getActionName() {
        return "Rejstracja";
    }

    @Override
    public void execute() {

    }
}
