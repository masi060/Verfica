package com.example;
import java.net.ServerSocket;
import java.net.Socket;

import main.java.com.ThreadGioco;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            try (// creo un socket
            ServerSocket serverSocket = new ServerSocket(3000)) {
                // accetto il client che si collega
                while (true) {
                    // apro il socket di ascolto
                    Socket client = serverSocket.accept();
                    String parola = "ciao";
                    // passo tutto alla classe ThreadGioco
                    ThreadGioco th = new ThreadGioco(client, parola);
                    th.start();
                }
            }
        } catch (Exception e) {
            System.out.println("\nErrore");
        }
    }
}