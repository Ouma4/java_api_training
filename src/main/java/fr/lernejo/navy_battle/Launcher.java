package main.java.fr.lernejo.navy_battle;

import java.io.IOException;


class Launcher{


        public static void main(String[] args) throws IOException{

            Serveur cam = new Serveur(Integer.parseInt(args[0]));
            cam.createServeur();



        }


}
