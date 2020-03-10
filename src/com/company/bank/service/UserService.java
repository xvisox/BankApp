package com.company.bank.service;

import com.company.bank.database.Database;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public static void saveUsers(List<User> usersList) {
        List<String> lines = new ArrayList<>();
        for (User user : usersList) {
            StringBuilder sb = new StringBuilder();
            sb.append(user.getLogin());
            sb.append(",");
            sb.append(user.getPassword());
            sb.append(",");
            sb.append(user.getRole());
            sb.append(",");
            sb.append(user.isAccepted());
            lines.add(sb.toString());
        }
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new FileOutputStream(Database.usersFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            pr.println(line);
            pr.flush();
        }
        pr.close();
    }

    public static List<User> loadUsers() {
        List<User> usersList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Database.usersFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] split = line.split(",");
                User user = new User();
                user.setLogin(split[0]);
                user.setPassword(split[1]);
                user.setRole(Role.valueOf(split[2]));//???????? help me
                user.setAccepted(Boolean.parseBoolean(split[3]));
                usersList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }


}