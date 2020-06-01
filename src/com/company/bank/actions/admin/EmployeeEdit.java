package com.company.bank.actions.admin;

import com.company.bank.actions.Action;
import com.company.bank.utilities.UserUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.util.List;
import java.util.Scanner;

public class EmployeeEdit implements Action {
    private List<User> usersList;
    private Scanner sc;

    public EmployeeEdit(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private void editEmployee() {
        String login, password;
        boolean found = false;
        System.out.println("Which employee do you want to edit?");
        displayEmployee();
        login = sc.nextLine();
        for (User user : usersList) {
            if (login.equals(user.getLogin())) {
                System.out.println("User found, change password: ");
                password = sc.nextLine();
                user.setPassword(password);
                System.out.println("Success!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("User not found");
        }
        UserUtility.saveUsers(usersList);
    }

    private void displayEmployee() {
        usersList.stream()
                .filter(user -> Role.EMPLOYEE.equals(user.getRole()))
                .forEach(System.out::println);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public String getActionName() {
        return "Edit Employee";
    }

    @Override
    public void execute() {
        editEmployee();
    }

    @Override
    public String toString() {
        return "Edit Employee";
    }
}
