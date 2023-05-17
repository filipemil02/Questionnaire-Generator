package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Chestionar {

    public static int idChestionar = 1;
    public String nume;
    List<Intrebare> intrebari= new ArrayList<Intrebare>();

    public Chestionar(String nume, List<Intrebare> intrebari) {
        this.nume = nume;
        this.intrebari = intrebari;

        try (FileWriter ches = new FileWriter("./intrebari.csv", true);
             BufferedWriter bw = new BufferedWriter(ches);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(this.idChestionar + "," + this.nume);
            for(int i = 0 ; i < intrebari.size(); i++){
                out.print("," + this.intrebari.get(i).identificatorIntrebare);
            }
            out.println("");
            out.close();
            bw.close();
            ches.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int verificaChestionar(String nume) {
        int exista = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(("chestionare.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {;
                String[] splitt = line.split(",");
                if (splitt[3].compareTo(nume) == 0) {
                    return 1; //gasita
                }
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        return 0; //nu exista
    }

    public static void  adaugaChestionar(Chestionar chestionar,String username,String parola){
        int dim = chestionar.intrebari.size() ;

        try (FileWriter intr = new FileWriter("./chestionare.csv", true);
             BufferedWriter bw = new BufferedWriter(intr);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(username + "," + parola + "," +chestionar.idChestionar+ "," + chestionar.nume );
            for(int i = 0 ; i < dim; i++){
                out.print("," + chestionar.intrebari.get(i).identificatorIntrebare);
            }
            out.println("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Chestionar.idChestionar++;
    }

    public static int getIdChestionar(String text){
        int exista = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(("chestionare.csv")))) {
            String line;
            int lines = Intrebare.nrRanduri();
            int cont = 0;
            while ((line = br.readLine()) != null) {
                String[] splitt = line.split(",");
                if(splitt[3].compareTo(text)==0){
                    return Integer.parseInt(splitt[2]); //gasita
                }
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        return 0;
    }

    public static void getAllChestionare(){
        int exista = 0;
        System.out.print("{ 'status' : 'ok', 'message' :'[");

        try (BufferedReader br = new BufferedReader(new FileReader(("chestionare.csv")))) {
            String line;
            int lines = Intrebare.nrRanduri();
            int cont = 0;
            while ((line = br.readLine()) != null) {
                String[] splitt = line.split(",");
                String id = splitt[2];
                String text = splitt[3];
                //String chestionarFacut = splitt[10];
                if(cont + 1 == lines)
                    System.out.print("{\"quizz_id\" : \"" + id + "\", \"quizz_name\" : \"" + text + "\"}]'");
                else
                    System.out.print("{\"quizz_id\" : \"" + id + "\", \"quizz_name\" : \"" + text + "\"}, ");
                cont++;
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        System.out.print("}");
    }
}
