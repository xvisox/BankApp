package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.actions.registration.SignUp;
import com.company.bank.loans.Loan;
import com.company.bank.service.AccountBalanceService;
import com.company.bank.service.LoansService;
import com.company.bank.service.UserService;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<User> usersList = UserService.loadUsers();
        Map<String, Double> balanceMap = AccountBalanceService.loadBalance();
        Map<String, Loan> loanMap = LoansService.loadLoans();
        List<Action> actions = new ArrayList<>();
        LoginAction loginAction = new LoginAction(sc, usersList);
        SignUp signUp = new SignUp(usersList, sc);
        Initializer initializer = new Initializer(actions, usersList, sc, balanceMap, loanMap, loginAction);
        initializer.init();

        //menu
        User sessionUser = mainMenuUser(sc, loginAction, signUp);
        //actions
        if (sessionUser != null && sessionUser.isAccepted()) {
            Role sessionAccess = sessionUser.getRole();
            List<Action> sessionActions = Action.getActionForRoles(actions, sessionAccess);
            switch (sessionAccess) {
                case ADMIN:
                    System.out.println("Logged as admin");
                    displayAndExecute(sessionActions, sc);
                    break;
                case EMPLOYEE:
                    System.out.println("Logged as employee");
                    displayAndExecute(sessionActions, sc);
                    break;
                case CUSTOMER:
                    System.out.println("Logged as customer");
                    displayAndExecute(sessionActions, sc);
                    break;
            }
        } else {
            System.out.println("Try again later...");
        }
    }

    private static User mainMenuUser(Scanner sc, LoginAction loginAction, SignUp signUp) {
        System.out.println("Choose action:\n0 Sign Up\n1 Log In");
        String commandStr = sc.nextLine();
        int command = Integer.parseInt(commandStr);
        switch (command) {
            case 0:
                signUp.execute();
                return null;
            case 1:
                loginAction.execute();
                return loginAction.getSessionUser();
            default:
                return null;
        }
    }

    private static void displayAndExecute(List<Action> actions, Scanner sc) {
        String actionNumberStr;
        int actionNumber;
        System.out.println("Choose action: ");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println(i + ". " + actions.get(i).getActionName());
        }
        actionNumberStr = sc.nextLine();
        actionNumber = Integer.parseInt(actionNumberStr);
        while (actionNumber >= actions.size()) {
            System.out.println("Try Again");
            actionNumberStr = sc.nextLine();
            actionNumber = Integer.parseInt(actionNumberStr);
        }
        actions.get(actionNumber).execute();
    }
}
