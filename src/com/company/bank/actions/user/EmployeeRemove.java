package com.company.bank.actions.user;

import com.company.bank.actions.Action;
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
        usersList.stream()
                .filter(user -> Role.EMPLOYEE.equals(user.getRole()))
                .forEach(System.out::println);


    }

    @Override
    public String getActionName() {
        return "Edit Employee";
    }

    @Override
    public void execute() {
        employeeRemove();
    }
}
