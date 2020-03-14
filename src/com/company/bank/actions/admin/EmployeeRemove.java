package com.company.bank.actions.admin;

import com.company.bank.actions.Action;
import com.company.bank.Utilities.UserUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.util.List;
import java.util.Scanner;

public class EmployeeRemove implements Action {
    private List<User> usersList;
    private Scanner sc;

    public EmployeeRemove(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private void employeeRemove() {
        String login;
        System.out.println("Which employee do you want to delete?");
        displayEmployee();
        login = sc.nextLine();
        for (User user : usersList) {
            if (login.equals(user.getLogin())) {
                usersList.remove(user);
                System.out.println("Success!");
                break;
            }
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
        return "Remove Employee";
    }

    @Override
    public void execute() {
        employeeRemove();
    }

    @Override
    public String toString() {
        return "Remove Employee";
    }
}
