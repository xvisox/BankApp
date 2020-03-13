package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.admin.EmployeeAddAction;
import com.company.bank.actions.admin.EmployeeEdit;
import com.company.bank.actions.admin.EmployeeRemove;
import com.company.bank.actions.customer.LoanApplication;
import com.company.bank.actions.customer.TransferAction;
import com.company.bank.actions.employee.ClientAccountAccept;
import com.company.bank.actions.employee.ClientLoanAccept;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.loans.Loan;
import com.company.bank.users.User;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Initializer {
    private final List<Action> actionList;
    private final List<User> usersList;
    private final Scanner sc;
    private final Map<String, Double> balanceMap;
    private final Map<String, Loan> loanMap;
    private final LoginAction loginAction;

    public Initializer(List<Action> actionList, List<User> usersList, Scanner sc, Map<String, Double> balanceMap, Map<String, Loan> loanMap, LoginAction loginAction) {
        this.actionList = actionList;
        this.usersList = usersList;
        this.sc = sc;
        this.balanceMap = balanceMap;
        this.loanMap = loanMap;
        this.loginAction = loginAction;
    }

    public void init() {
        EmployeeAddAction employeeAddAction = new EmployeeAddAction(usersList, sc);
        EmployeeRemove employeeRemove = new EmployeeRemove(usersList, sc);
        EmployeeEdit employeeEdit = new EmployeeEdit(usersList, sc);
        ClientAccountAccept clientAccountAccept = new ClientAccountAccept(usersList, sc, balanceMap);
        LoanApplication loanApplication = new LoanApplication(loanMap, sc, loginAction);
        ClientLoanAccept clientLoanAccept = new ClientLoanAccept(loanMap, sc, balanceMap);
        TransferAction transferAction = new TransferAction(balanceMap, loginAction, sc);

        actionList.add(employeeAddAction);
        actionList.add(employeeRemove);
        actionList.add(employeeEdit);
        actionList.add(clientAccountAccept);
        actionList.add(loanApplication);
        actionList.add(clientLoanAccept);
        actionList.add(transferAction);
    }
}
