package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.actions.registration.SignUp;
import com.company.bank.service.UserService;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<User> usersList = UserService.loadUsers();
        List<Action> actions = new ArrayList<>();
        Initializer initializer = new Initializer(actions, usersList, sc);
        initializer.init();

        //menu
        User sessionUser = mainMenuUser(usersList, sc);
        //actions
        if (sessionUser != null && sessionUser.isAccepted()) {
            Role sessionAccess = sessionUser.getRole();
            List<Action> sessionActions = Action.getActionForRoles(actions, sessionAccess);
            switch (sessionAccess) {
                case ADMIN:
                    displayAndExecute(sessionActions, sc);
            }
        } else {
            System.out.println("Pending registration request... Try again later");
        }
    }

    private static User mainMenuUser(List<User> usersList, Scanner sc) {
        LoginAction loginAction = new LoginAction(sc, usersList);
        SignUp signUp = new SignUp(usersList, sc);
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
        int actionNumber;
        System.out.println("Choose action: ");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println(i + ". " + actions.get(i).getActionName());
        }
        actionNumber = sc.nextInt();
        sc.nextLine();
        actions.get(actionNumber).execute();
    }
}
