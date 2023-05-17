package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Intrebare {

    String text;
    static int identificatorIntrebare = 1;
    String[] raspunsuri;
    int rasp_corecte;

    String type;

    public Intrebare(String text, int identificatorIntrebare, String[] raspunsuri, int rasp_corecte, String type) {
        this.text = text;
        this.identificatorIntrebare = identificatorIntrebare;
        this.raspunsuri = raspunsuri;
        this.rasp_corecte = rasp_corecte;
        this.type = type;
        int dim = raspunsuri.length;
    }

    public static void adaugaIntrebare(Intrebare intrebare){
        int dim = intrebare.raspunsuri.length;
        try (FileWriter intr = new FileWriter("./intrebari.csv", true);
             BufferedWriter bw = new BufferedWriter(intr);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(intrebare.identificatorIntrebare + "," + intrebare.text + "," + intrebare.type + "," + intrebare.rasp_corecte);
            for(int i = 0 ; i < dim; i++){
                out.print("," + intrebare.raspunsuri[i]);
            }
            out.println("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Intrebare.identificatorIntrebare++;
    }
    public static int verificaIdIntrebare(String text){
        int exista = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(("intrebari.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitt = line.split(",");
                String textIntrebare = splitt[0];
                if(textIntrebare.compareTo(text)==0){
                    return 1; //gasita
                }
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        return 0; //nu exista
    }
    public static int verificaIntrebare(String text){
        int exista = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(("intrebari.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitt = line.split(",");
                String textIntrebare = splitt[1];
                if(textIntrebare.compareTo(text)==0){
                    return 1; //gasita
                }
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        return 0; //nu exista
    }

    public static int getIdIntrebare(String text){
        File file = new File("./intrebari.csv");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int exista = 0;
        while(sc.hasNextLine()){
            String linie = sc.nextLine();
            String[] splitt = linie.split(",");
            if(splitt[1].compareTo(text)==0){
                sc.close();
                return Integer.parseInt(splitt[0]); //gasita
            }
            sc.nextLine();
        }
        sc.close();
        return 0; //nu exista
    }

    public static void getAllQuestions() throws FileNotFoundException {
        int exista = 0;
        System.out.print("{ 'status' : 'ok', 'message' :'[");

        try (BufferedReader br = new BufferedReader(new FileReader(("intrebari.csv")))) {
            String line;
            int lines = Intrebare.nrRanduri();
            int cont = 0;
            while ((line = br.readLine()) != null) {
                String[] splitt = line.split(",");
                String id = splitt[0];
                String text = splitt[1];
                if(cont + 1 == lines)
                    System.out.print("{\"question_id\" : \"" + id + "\", \"question_name\" : \"" + text + "\"}]'");
                else
                    System.out.print("{\"question_id\" : \"" + id + "\", \"question_name\" : \"" + text + "\"}, ");
                cont++;
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        System.out.print("}");
    }

    public static int nrRanduri(){
        int nr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(("intrebari.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                nr++;
            }
        }catch (IOException e) {
            System.out.print("eroare");
        }
        return nr;
    }

    public static Intrebare getIntrebare(String idIntrebare){
        int exista = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(("intrebari.csv")))) {
            String line;
            int lines = Intrebare.nrRanduri();
            int cont = 0;
            while ((line = br.readLine()) != null) {
                String[] splitt = line.split(",");
                if(splitt[0].compareTo(idIntrebare)==0){
                    String[] raspunsuri = new String[splitt.length-4];
                    for(int i = 4; i < splitt.length; i++){
                        raspunsuri[i-4] = splitt[i];
                    }
                    Intrebare i = new Intrebare(splitt[1], Integer.parseInt(splitt[0]), raspunsuri, Integer.parseInt(splitt[3]), splitt[2]);
                    return i; //gasita
                }
            } }catch (IOException e) {
            System.out.print("eroare");
        }
        return null; //nu exista
    }
}


