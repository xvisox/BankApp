package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.database.Database;
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
        saveUsers();
    }

    private void displayEmployee() {
        usersList.stream()
                .filter(user -> Role.EMPLOYEE.equals(user.getRole()))
                .forEach(System.out::println);
    }

    private void saveUsers() {
        List<String> lines = new ArrayList<>();
        for (User user : usersList) {
            StringBuilder sb = new StringBuilder();
            sb.append(user.getLogin());
            sb.append(",");
            sb.append(user.getPassword());
            sb.append(",");
            sb.append(user.getRole());
            sb.append(",");
            sb.append(user.isAccepted());
            lines.add(sb.toString());
        }
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new FileOutputStream(Database.usersFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            pr.println(line);
            pr.flush();
        }
        pr.close();
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
