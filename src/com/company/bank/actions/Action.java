package com.company.bank.actions;

import com.company.bank.users.Role;

import java.util.List;

public interface Action {
    List<Role> getRole();
    String getActionName();
    void execute();
}
