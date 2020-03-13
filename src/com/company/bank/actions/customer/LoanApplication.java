package com.company.bank.actions.customer;

import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.loans.Loan;
import com.company.bank.service.LoansService;
import com.company.bank.users.Role;

import java.util.Map;
import java.util.Scanner;

public class LoanApplication implements Action {
    private Map<String, Loan> loanMap;
    private Scanner sc;
    private LoginAction loginAction;

    public LoanApplication(Map<String, Loan> loanMap, Scanner sc, LoginAction loginAction) {
        this.loanMap = loanMap;
        this.sc = sc;
        this.loginAction = loginAction;
    }

    private void applyForALoan() {
        String login = loginAction.getSessionUser().getLogin();
        if (checkUserLoans(loanMap, login)) {
            System.out.println("You can have one loan at the time");
        } else {
            Loan loan = createLoan();
            loanMap.put(login, loan);
            LoansService.saveLoans(loanMap);
        }
    }

    private Loan createLoan() {
        String name, surname, peselStr;
        int amount;
        long pesel;
        System.out.println("Name and Surname:");
        String[] split = sc.nextLine().split(" ");
        name = split[0];
        surname = split[1];
        System.out.println("Write down pesel:");
        peselStr = sc.nextLine();
        while (!checkPesel(peselStr)) {
            System.out.println("Try again.");
            peselStr = sc.nextLine();
        }
        pesel = Long.parseLong(peselStr);
        System.out.println("Write down amount:");
        amount = sc.nextInt();
        System.out.println("Your application will be considered soon.");
        return new Loan(name, surname, pesel, amount, false, false);
    }

    private boolean checkUserLoans(Map<String, Loan> loanMap, String login) {
        for (Map.Entry<String, Loan> entry : loanMap.entrySet()) {
            if (entry.getKey().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPesel(String pesel) {
        return 11 == pesel.length();
    }

    @Override
    public Role getRole() {
        return Role.CUSTOMER;
    }

    @Override
    public String getActionName() {
        return "Loan Application";
    }

    @Override
    public void execute() {
        applyForALoan();
    }
}
