package com.company.bank;

import com.company.bank.operations.Action;
import com.company.bank.operations.LoginAction;
import com.company.bank.operations.SignUp;
import com.company.bank.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<User> usersList = SignUp.loadUsers();

        //action
        List<Action> menuActions = new ArrayList<>();
        SignUp signUpAction = new SignUp(usersList, sc);
        LoginAction logInAction = new LoginAction(sc,usersList);

        menuActions.add(signUpAction);
        menuActions.add(logInAction);

        //menu
        int actionNumber = 0;
        System.out.println("Wybierz akcje: ");
        for (int i = 0; i < menuActions.size(); i++) {
            System.out.println(i + ". " + menuActions.get(i).getActionName());
        }
        actionNumber = sc.nextInt();
        sc.nextLine();
        menuActions.get(actionNumber).execute();

    }
}
