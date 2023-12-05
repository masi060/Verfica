package main.java.com;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadGioco extends Thread {
    Socket client;
    BufferedReader inDalClient;
    String parola;
    DataOutputStream outVersoClient;

    public ThreadGioco (Socket client, String parola) {
        this.client = client;
        this.parola = parola;
    }

    public void run() {
        
        String parola = this.parola;
        String lettera;
        int cont = 0;
        try {
            System.out.println("sono collegato al client: " + client.getInetAddress() + " la sua parola e' " + parola);
            // creao i tubi
            this.inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.outVersoClient = new DataOutputStream(client.getOutputStream());
            // invio al client
            int numParola = parola.length();
            String parolasegreta = "";
            String daRitornare = "";
            for(int i = 0; i< numParola+1; i++){
                        parolasegreta = "*";
                    }
            do {
                // leggo la lettera inviata dal client
                lettera = inDalClient.readLine();
                // se e' 1 vuol dire che sta provando a indovinare una lettera se invece e' + di 1 allora sta provando a indovinare la parola
                if(lettera.length() == 1){
                    if (parola.contains(lettera)) {
                        daRitornare = parolasegreta;
                    }
                    else{
                        daRitornare = "la parola non contiene: "+ lettera ;
                    }
                }
                else{
                    if(lettera.equals(parola)){
                        daRitornare = "Hai indovinato la parola";
                    }
                    if(!lettera.equals(parola)){
                        daRitornare = "non hai indovinato la parola";
                    }
                }

                outVersoClient.writeBytes(daRitornare + "\n");
                daRitornare = client.getInetAddress() + " da indovinare : " + parola + " utente ha messo: " + lettera + " ritorno: " + daRitornare;
                System.out.println(daRitornare);
                cont++;
            } while (!daRitornare.equals("Hai indovinato la parola"));
            // invio il numero di tentativi
            outVersoClient.writeBytes(cont + "\n");
            System.out.println("\nclient " + client.getInetAddress() + " ha finito con " + cont + " tentaivi");
        } catch (Exception e) {
            System.out.println("\t\terrore nella comunicazione: " + e.getMessage());
        }
    }
}