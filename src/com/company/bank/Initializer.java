package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.employee.EmployeeAddAction;
import com.company.bank.actions.employee.EmployeeEdit;
import com.company.bank.actions.employee.EmployeeRemove;
import com.company.bank.users.User;

import java.util.List;
import java.util.Scanner;

public class Initializer {
    private final List<Action> actionList;
    private final List<User> usersList;
    private final Scanner sc;

    public Initializer(List<Action> actionList, List<User> usersList, Scanner sc) {
        this.actionList = actionList;
        this.usersList = usersList;
        this.sc = sc;
    }

    public void init() {
        EmployeeAddAction employeeAddAction = new EmployeeAddAction(usersList, sc);
        EmployeeRemove employeeRemove = new EmployeeRemove(usersList, sc);
        EmployeeEdit employeeEdit = new EmployeeEdit(usersList, sc);

        actionList.add(employeeAddAction);
        actionList.add(employeeRemove);
        actionList.add(employeeEdit);
    }
}
