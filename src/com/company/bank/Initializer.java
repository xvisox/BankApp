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
    private List<Action> actionList;
    private List<User> usersList;
    private Scanner sc;
    private Map<String, Double> balanceMap;
    private Map<String, Loan> loanMap;
    private LoginAction loginAction;

    private Initializer(Builder builder) {
        this.actionList = builder.actionList;
        this.balanceMap = builder.balanceMap;
        this.loanMap = builder.loanMap;
        this.loginAction = builder.loginAction;
        this.sc = builder.sc;
        this.usersList = builder.usersList;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public Scanner getSc() {
        return sc;
    }

    public Map<String, Double> getBalanceMap() {
        return balanceMap;
    }

    public Map<String, Loan> getLoanMap() {
        return loanMap;
    }

    public LoginAction getLoginAction() {
        return loginAction;
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

    public static class Builder {
        private List<Action> actionList;
        private List<User> usersList;
        private Scanner sc;
        private Map<String, Double> balanceMap;
        private Map<String, Loan> loanMap;
        private LoginAction loginAction;

        public Builder actionList(List<Action> actionList) {
            this.actionList = actionList;
            return this;
        }

        public Builder usersList(List<User> usersList) {
            this.usersList = usersList;
            return this;
        }

        public Builder sc(Scanner sc) {
            this.sc = sc;
            return this;
        }

        public Builder balanceMap(Map<String, Double> balanceMap) {
            this.balanceMap = balanceMap;
            return this;
        }

        public Builder loanMap(Map<String, Loan> loanMap) {
            this.loanMap = loanMap;
            return this;
        }

        public Builder loginAction(LoginAction loginAction) {
            this.loginAction = loginAction;
            return this;
        }

        public Initializer build() {
            return new Initializer(this);
        }
    }
}
