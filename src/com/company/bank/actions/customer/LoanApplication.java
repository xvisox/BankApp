package com.company.bank.actions.customer;

import com.company.bank.actions.Action;
import com.company.bank.loans.Loan;
import com.company.bank.service.LoansService;
import com.company.bank.users.Role;

import java.util.Map;
import java.util.Scanner;

public class LoanApplication implements Action {
    private Map<String, Loan> loanMap;
    private Scanner sc;

    public LoanApplication(Map<String, Loan> loanMap, Scanner sc) {
        this.loanMap = loanMap;
        this.sc = sc;
    }

    private void applyForALoan() {
        String login;
        //wolalbym zeby wczytalo login ale nie wiem jak (jeszcze)

        System.out.println("Write down login:");
        login = sc.nextLine();
        Loan loan = createLoan();
        loanMap.put(login,loan);
        LoansService.saveLoans(loanMap);
    }

    private Loan createLoan() {
        String name, surname, peselStr;
        int amount, pesel;
        System.out.println("Name and Surname:");
        String[] split = sc.nextLine().split(" ");
        name = split[0];
        surname = split[0];
        System.out.println("Write down pesel");
        peselStr = sc.nextLine();
        while (!checkPesel(peselStr)) {
            System.out.println("Try again");
            peselStr = sc.nextLine();
        }
        pesel = Integer.parseInt(peselStr);
        System.out.println("Write down amount:");
        amount = sc.nextInt();
        System.out.println("Your application will be considered soon.");
        return new Loan(name, surname, pesel, amount, false, false);
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
