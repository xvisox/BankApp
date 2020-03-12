package com.company.bank.service;

import com.company.bank.database.Database;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountBalanceService {

    public static void saveBalance(Map<String, Double> balanceMap) {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, Double> entry : balanceMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getKey());
            sb.append(",");
            sb.append(entry.getValue());
            lines.add(sb.toString());
        }
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new FileOutputStream(Database.accountBalanceFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            pr.println(line);
            pr.flush();
        }
        pr.close();
    }

    public static Map<String, Double> loadBalance() {
        Map<String, Double> balanceMap = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Database.accountBalanceFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] split = line.split(",");
                balanceMap.put(split[0], Double.parseDouble(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balanceMap;
    }

}
