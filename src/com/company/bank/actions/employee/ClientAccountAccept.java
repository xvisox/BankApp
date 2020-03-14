package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.Utilities.AccountBalanceUtility;
import com.company.bank.Utilities.UserUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientAccountAccept implements Action {
    private List<User> usersList;
    private Scanner sc;
    private Map<String, Double> balanceMap;

    public ClientAccountAccept(List<User> usersList, Scanner sc, Map<String, Double> balanceMap) {
        this.usersList = usersList;
        this.sc = sc;
        this.balanceMap = balanceMap;
    }

    private void accountAccept() {
        String login;
        displayCustomers();
        System.out.println("Which customer account do you want to accept?");
        login = sc.nextLine();
        User foundUser = UserUtility.findUser(usersList, login);
        if (foundUser != null) {
            System.out.println("Success, user found");
            foundUser.setAccepted(true);
            balanceMap.put(foundUser.getLogin(), 0.0);
            UserUtility.saveUsers(usersList);
            AccountBalanceUtility.saveBalance(balanceMap);
        } else {
            System.out.println("User not found");
        }
    }

    private void displayCustomers() {
        usersList.stream()
                .filter(user -> Role.CUSTOMER.equals(user.getRole()) && !user.isAccepted())
                .forEach(System.out::println);
    }

    @Override
    public Role getRole() {
        return Role.EMPLOYEE;
    }

    @Override
    public String getActionName() {
        return "Accept account request";
    }

    @Override
    public void execute() {
        accountAccept();
    }
}
