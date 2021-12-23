package main.java.fr.lernejo.navy_battle;

import java.io.IOException;


class Launcher{

        public static void main(String[] args) throws IOException{
            String url ="";

            if(args.length == 2){
                url = args[1];
            }
            Serveur server = new Serveur(Integer.parseInt(args[0]),url);
            server.createServeur();

        }
}
