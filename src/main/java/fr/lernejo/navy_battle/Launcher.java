package main.java.fr.lernejo.navy_battle;

import java.io.IOException;


class Launcher{


        public static void main(String[] args) throws IOException{

            Serveur cam = new Serveur(Integer.parseInt(args[0]));
            if(args.length == 2){
                cam.getUrl(args[1]);
            }

            cam.createServeur();



        }


}
