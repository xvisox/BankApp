package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.employee.EmployeeEdit;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.actions.registration.SignUp;
import com.company.bank.actions.employee.EmployeeAddAction;
import com.company.bank.actions.employee.EmployeeRemove;
import com.company.bank.service.UserService;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<User> usersList = UserService.loadUsers();
        List<Action> actions = new ArrayList<>();
        Initializer initializer = new Initializer(actions, usersList, sc);
        initializer.init();

        //menu


    }

    private static void actionDisplayAndExecute(List<Action> Actions, Scanner sc) {
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
