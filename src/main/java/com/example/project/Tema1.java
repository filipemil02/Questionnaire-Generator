package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tema1 {

	public static void main(final String[] args){

		List<Users> users = new ArrayList<Users>();
		List<Intrebare> intrebari = new ArrayList<Intrebare>();
		if (args == null)
			System.out.print("Hello world!");

		if(args[0].compareTo("-create-user") == 0) {
			if (args.length == 1) {
				System.out.print("{'status':'error','message':'Please provide username'}");
				return;
			}
			if(args.length == 2) {
				System.out.print("{'status':'error','message':'Please provide password'}");
				return;
			}
			String s1 = args[1];
			String s2 = args[2];
			String[] split = s1.split("'");
			String username = split[1];
			split = s2.split("'");
			String parola = split[1];
			if (Users.adaugaUser(username, parola) == 1) {
				System.out.print("{'status':'ok','message':'User created successfully'}");
				Users user = new Users(username, parola);
				users.add(user);
			} else {
				System.out.print("{'status':'error','message':'User already exists'}");
			}
		}

		if(args[0].compareTo("-cleanup-all") == 0){
			clean();
			System.out.println("{'status':'ok','message':'Cleanup finished successfully'}");
		}

		if(args[0].compareTo("-create-question") == 0) {
			int i = 1;
			if(args.length >= 16){
				System.out.println("{'status':'error','message':'More than 5 answers were submitted'}");
				return;
			}
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				if(split[0].compareTo("-u ") != 0){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				if(split[0].compareTo("-p ") != 0){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				String parola = split[1];
				if(Users.verificaUser(username,parola) == 1){
					i++;
					s1 = args[3];
					split = s1.split("'");
					if(split[0].compareTo("-text ") != 0){
						System.out.println("{'status':'error','message':'No question text provided'}");
						return;
					}
					String text = split[1];
					if(Intrebare.verificaIntrebare(text) == 0){
						i++;
						s1 = args[4];
						split = s1.split("'");
						String tip = split[1];
						int contInput = 5;
                        i++;
						int max = args.length;
						int nrRaspCorecte = 0;
						int nrRasp = 1;
						while(contInput < max){
							s1 = args[contInput++];
                            i++;
							split = s1.split("'");
							if(split[0].compareTo("-answer-" + nrRasp +" " )!= 0){
								System.out.println("{'status':'error','message':'Answer " + nrRasp + " has no answer description'}");
								return;
							}

							String raspuns = split[1];
							s1 = args[contInput++];
                            i++;
							split = s1.split("'");
							if(split[0].compareTo("-answer-" + nrRasp +"-is-correct " )!= 0){
								System.out.println("{'status':'error','message':'Answer " + nrRasp + " has no answer correct flag'}");
								return;
							}
							String valAdevar = split[1];
							nrRasp++;
							if(valAdevar.compareTo("1") == 0)
								nrRaspCorecte++;
						}
						if(nrRasp == 1){
							System.out.println("{'status':'error','message':'No answer provided'}");
							return;
						}
						if(nrRasp == 2){
							System.out.println("{'status':'error','message':'Only one answer provided'}");
							return;
						}
						if(tip.compareTo("single") == 0 && nrRaspCorecte != 1){
							System.out.println("{'status':'error','message':'Single correct answer question has more than one correct answer'}");
							return;
						}
						int inceput = 5;
						int cntR = 1;
						int cntArrray = 0;
						String[] raspunsuri = new String[2*(nrRasp-1)];
						for(int j = inceput; j < max; j++){
							s1 = args[j];
							split = s1.split("'");
							String rasp = split[1];
							for(int k = 0; k < cntArrray ; k = k + 2)
								if(rasp.compareTo(raspunsuri[k]) == 0){
									System.out.println("{'status':'error','message':'Same answer provided more than once'}");
									return;
								}
							j++;
							s1 = args[j];
							split = s1.split(" ");
							String valAdev = split[1];
							Raspuns ras = new Raspuns(cntR,rasp,valAdev);
							raspunsuri[cntArrray++] = new String(rasp);
							raspunsuri[cntArrray++] = new String(valAdev);
						}
						Intrebare intrebare = new Intrebare(text,Intrebare.identificatorIntrebare,raspunsuri,nrRaspCorecte,tip);
						Intrebare.adaugaIntrebare(intrebare);
						intrebari.add(intrebare);
						System.out.println("{'status':'ok','message':'Question added successfully'}");
					}else{
						System.out.println("{'status':'error','message':'Question already exists'}");
						return;
					}
				}else{
					System.out.println("{'status':'error','message':'Login failed'}");
					return;
				}
			}catch(Exception e){
				if(i == 1)
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
				if(i == 2)
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
				if(i == 3)
					System.out.print("{'status':'error','message':'No question text provided'}");
				if(i == 4)
					System.out.print("{'status':'error','message':'No type'}");
				if(i == 5)
					System.out.print("{'status':'error','message':'No answer provided}");
				if(i == 6)
					System.out.print("{'status':'error','message':'Answer 1 has no answer description'}");
				if(i == 8)
					System.out.print("{'status':'error','message':'Answer 2 has no answer description'}");
				if(i == 10)
					System.out.print("{'status':'error','message':'Answer 3 has no answer description'}");
				if(i == 12)
					System.out.print("{'status':'error','message':'Answer 4 has no answer description'}");
				if(i == 14)
					System.out.print("{'status':'error','message':'Answer 5 has no answer description'}");

			}
		}
		if(args[0].compareTo("-get-question-id-by-text") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1) {
					s1 = args[3];
					split = s1.split("'");
					String intrebare = split[1];
					int id = Intrebare.getIdIntrebare(intrebare);
					if(id == 0){
						System.out.println("{'status':'error','message':'Question does not exist'}");
						return;
					}else
						System.out.println("{'status':'ok','message':'"+id+"'}");
				}else{
					System.out.println("{'status':'error','message':'Login failed'}");
					return;
				}

			}catch(Exception e){
				if(i == 1){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-get-all-questions") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				i++;
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1) {
					Intrebare.getAllQuestions();
				}else{
					System.out.println("{'status':'error','message':'Login failed'}");
					return;
				}
			}catch(Exception e){
				if(i == 1){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-create-quizz") == 0){
			int i = 1;
			if(args.length >=15){
				System.out.print("{'status':'error','message':'Quizz has more than 10 questions'}");
			}
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1) {
					i++;
					s1 = args[3];
					split = s1.split("'");
					String nume = split[1];
					if(Chestionar.verificaChestionar(nume) == 0){
						int contorIntrebari = 0;
						i++;
						int nrIntrebari = args.length - 4;
						int curent = 4;
						List<Intrebare> intrebariQuizz = new ArrayList<Intrebare>();
						while(contorIntrebari != nrIntrebari){
							s1 = args[curent];
							split = s1.split("'");
							String idIntrebare = split[1];
							contorIntrebari++;
							if(Intrebare.verificaIdIntrebare(idIntrebare) == 1){
								intrebariQuizz.add(Intrebare.getIntrebare(idIntrebare));
							}else{
								System.out.println("{'status':'error','message':'Question ID for question "  + contorIntrebari + " does not exist'}");
								return;
							}
							curent++;
						}
						Chestionar c = new Chestionar(nume,intrebariQuizz);
						Chestionar.adaugaChestionar(c,username,password);
						System.out.print("{'status':'ok','message':'Quizz added succesfully'}");
					}else{
						System.out.println("{'status':'error','message':'Quizz name already exists'}");
						return;
					}
				}else{
					System.out.println("{'status':'error','message':'Login failed'}");
					return;
				}
			}catch(Exception e){
				if(i == 1){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.println("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-get-quizz-by-name") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1){
					i++;
					s1 = args[3];
					split = s1.split("'");
					String nume = split[1];
					if(Chestionar.verificaChestionar(nume) == 1){
						System.out.print("{'status':'ok','message':'" + Chestionar.getIdChestionar(nume) + "'}");
					}else{
						System.out.print("{'status':'error','message':'Quizz does not exist'}");
					}
				}else{
					System.out.print("{'status':'error','message':'Login failed'}");
					return;
				}
			}catch(Exception e){
				if(i == 1){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-get-all-quizzes") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1){
					Chestionar.getAllChestionare();
				}else{
					System.out.print("{'status':'error','message':'Login failed'}");
					return;
				}

			}catch(Exception e){
				if(i == 1){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-get-quizz-details-by-id") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1){

				}else{
					System.out.print("{'status':'error','message':'Login failed'}");
					return;
				}

			}catch (Exception e){
				if(i == 1){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-submit-quizz") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1){

				}else{
					System.out.print("{'status':'error','message':'Login failed'}");
					return;
				}

			}catch (Exception e){
				if(i == 1){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-delete-quizz-by-id") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1){

				}else{
					System.out.print("{'status':'error','message':'Login failed'}");
					return;
				}

			}catch (Exception e){
				if(i == 1){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}

		if(args[0].compareTo("-get-my-solutions") == 0){
			int i = 1;
			try{
				String s1 = args[1];
				String[] split = s1.split("'");
				String username = split[1];
				i++;
				s1 = args[2];
				split = s1.split("'");
				String password = split[1];
				if(Users.verificaUser(username,password) == 1){

				}else{
					System.out.print("{'status':'error','message':'Login failed'}");
					return;
				}

			}catch (Exception e){
				if(i == 1){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
				if(i == 2){
					System.out.print("{'status':'error','message':'You need to be authenticated'}");
					return;
				}
			}
		}
	}

	public static void clean(){
		try (FileWriter users = new FileWriter("./users.csv", false);
			 BufferedWriter bw = new BufferedWriter(users);
			 PrintWriter out = new PrintWriter(bw)) {
			out.print("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try (FileWriter users = new FileWriter("./intrebari.csv", false);
			 BufferedWriter bw = new BufferedWriter(users);
			 PrintWriter out = new PrintWriter(bw)) {
			out.print("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Intrebare.identificatorIntrebare = 1;

		try (FileWriter users = new FileWriter("./chestionare.csv", false);
			 BufferedWriter bw = new BufferedWriter(users);
			 PrintWriter out = new PrintWriter(bw)) {
			out.print("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Chestionar.idChestionar = 1;
	}

}
