package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.database.Database;
import com.company.bank.service.UserService;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        UserService.saveUsers(usersList);
    }

    private void displayEmployee() {
        usersList.stream()
                .filter(user -> Role.EMPLOYEE.equals(user.getRole()))
                .forEach(System.out::println);
    }

    @Override
    public List<Role> getRole() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        return roles;
    }

    @Override
    public String getActionName() {
        return "Remove Employee";
    }

    @Override
    public void execute() {
        employeeRemove();
    }
}
