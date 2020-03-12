package com.company.bank.service;

import com.company.bank.database.Database;
import com.company.bank.loans.Loan;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoansService {

    public static void saveLoans(Map<String, Loan> loanMap) {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, Loan> entry : loanMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getKey());
            sb.append(";");
            sb.append(entry.getValue());
            lines.add(sb.toString());
        }
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new FileOutputStream(Database.userLoansFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            pr.println(line);
            pr.flush();
        }
        pr.close();
    }

    public static Map<String, Loan> loadLoans() {
        Map<String, Loan> loanMap = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Database.userLoansFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] split = line.split(";");
                String[] splitLoan = split[1].split(",");
                Loan loan = new Loan();
                loan.setName(splitLoan[0]);
                loan.setSurname(splitLoan[1]);
                loan.setPesel(Long.parseLong(splitLoan[2]));
                loan.setAmount(Integer.parseInt(splitLoan[3]));
                loan.setAccepted(Boolean.parseBoolean(splitLoan[4]));
                loan.setPaid(Boolean.parseBoolean(splitLoan[5]));
                loanMap.put(split[0], loan);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loanMap;
    }


}
