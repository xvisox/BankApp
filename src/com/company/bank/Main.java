package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.actions.registration.SignUp;
import com.company.bank.actions.user.EmployeeAddAction;
import com.company.bank.actions.user.EmployeeRemove;
import com.company.bank.users.Role;
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
        List<Action> adminActions = new ArrayList<>();
        SignUp signUpAction = new SignUp(usersList, sc);
        LoginAction logInAction = new LoginAction(sc, usersList);
        EmployeeAddAction employeeAddAction = new EmployeeAddAction(usersList, sc);
        EmployeeRemove employeeRemove = new EmployeeRemove(usersList,sc);

        menuActions.add(signUpAction);
        menuActions.add(logInAction);
        adminActions.add(employeeAddAction);
        adminActions.add(employeeRemove);
        //menu
        actionDisplay(menuActions, sc);
        User sessionUser = logInAction.getSessionUser();

        if (sessionUser.isAccepted()) {
            Role sessionAccess = sessionUser.getRole();
            switch (sessionAccess) {
                case ADMIN:
                    actionDisplay(adminActions, sc);

            }
        } else {
            System.out.println("Pending registration request... Try again later");
        }
    }

    private static void actionDisplay(List<Action> Actions, Scanner sc) {
        int actionNumber = 0;
        System.out.println("Choose action ");
        for (int i = 0; i < Actions.size(); i++) {
            System.out.println(i + ". " + Actions.get(i).getActionName());
        }
        actionNumber = sc.nextInt();
        sc.nextLine();
        Actions.get(actionNumber).execute();
    }
}
