package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class App {
    public static void main(String[] args) {
        try {
            // creao un socket / collegamento al server
            Socket socket = new Socket("localhost", 3000);
            // creo i buffer e stream per comunicare
            DataOutputStream outVersoServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String rigaRitornata;
            String numero = "1";
            do {
                // leggo una lettera dalla tastiera
                System.out.println("metti un numero");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                numero = in.readLine();
                
                switch (numero) {
                    case "1":
                            System.out.println("inserisci una lettera");
                            String lettera = in.readLine();
                            // invio la lettera al server
                            outVersoServer.writeBytes(lettera);
                            // aspetto la risposta dal server
                            rigaRitornata = inDalServer.readLine();
                        break;
                    case "2":
                            System.out.println("Quale e' la parola secondo te?");
                            String Indparola = in.readLine();
                            outVersoServer.writeBytes(Indparola);
                            // aspetto la risposta dal server
                            rigaRitornata = inDalServer.readLine();
                        break;
                    default:
                        break;
                }
            } while (numero.equals("3"));
            // aspetto il numero di tentativi dal server
            rigaRitornata = inDalServer.readLine();
            // stampo
            System.out.println("\n\n Hai indovinato in:" + rigaRitornata);
            // chiudo il socket (chiuso dal client!)
            socket.close();

        } catch (Exception e) {
            System.out.println("Errore");
        }
    }
}