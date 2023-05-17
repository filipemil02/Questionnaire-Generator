package com.example.project;

import java.io.*;
import java.util.Scanner;

public class Users {
    public String username;
    public String parola;
    public Users(String username, String parola) {
        this.username = username;
        this.parola = parola;
    }

    public static int adaugaUser(String username,String parola){
        File user = new File("./users.csv");
        Scanner sc = null;
        try {
            sc = new Scanner(user);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int exista = 0;
        while(sc.hasNextLine()){
            String line = sc.next();
            if(line.compareTo(username)==0){
                return 0;
            }
            sc.nextLine();
        }
        try (FileWriter users = new FileWriter("./users.csv", true);
             BufferedWriter bw = new BufferedWriter(users);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(username + " ");
            out.println(parola);
            return 1; // a fost adaugat cu succes
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int verificaUser(String username,String parola){
        File user = new File("./users.csv");
        Scanner sc = null;
        try {
            sc = new Scanner(user);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int exista = 0;
        while(sc.hasNextLine()){
            String usern = sc.next();
            String pass = sc.next();
            //System.out.println(usern + " " + pass);
            if(usern.compareTo(username)==0 && pass.compareTo(parola)==0){
                return 1; //gasit
            }
            sc.nextLine();
        }
        return 0; //nu exista
    }
}
