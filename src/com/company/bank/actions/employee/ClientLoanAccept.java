package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.loans.Loan;
import com.company.bank.utilities.AccountBalanceUtility;
import com.company.bank.utilities.LoansUtility;
import com.company.bank.users.Role;

import java.util.Map;
import java.util.Scanner;

public class ClientLoanAccept implements Action {
    private Map<String, Loan> loanMap;
    private Scanner sc;
    private Map<String, Double> accountBalanceMap;

    public ClientLoanAccept(Map<String, Loan> loanMap, Scanner sc, Map<String, Double> accountBalanceMap) {
        this.loanMap = loanMap;
        this.sc = sc;
        this.accountBalanceMap = accountBalanceMap;
    }

    private void acceptLoan() {
        displayLoans();
        System.out.println("Whose loan do you want to accept?");
        String login = sc.nextLine();
        Loan userLoan = loanMap.get(login);
        if (userLoan != null) {
            userLoan.setAccepted(true);
            System.out.println("Success!");
            LoansUtility.saveLoans(loanMap);
            double dAmount = userLoan.getAmount();
            accountBalanceMap.put(login, accountBalanceMap.get(login) + dAmount);
            AccountBalanceUtility.saveBalance(accountBalanceMap);
        } else {
            System.out.println("User not found");
        }
    }

    private void displayLoans() {
        for (Map.Entry<String, Loan> entry : loanMap.entrySet()) {
            if (!entry.getValue().isAccepted()) {
                String login = "|login: " + entry.getKey();
                String nameAndSurname = "|name and surname: " + entry.getValue().getName() + " " + entry.getValue().getSurname();
                String amount = "|amount: " + entry.getValue().getAmount();
                String pesel = "|pesel: " + entry.getValue().getPesel() + '|';
                System.out.format("%-15s %-35s %-15s %-20s\n", login, nameAndSurname, amount, pesel);
            }
        }

    }

    @Override
    public Role getRole() {
        return Role.EMPLOYEE;
    }

    @Override
    public String getActionName() {
        return "Accept loan request";
    }

    @Override
    public void execute() {
        acceptLoan();
    }
}
