package main.java.fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class Serveur {

    int port = 4598;

    Serveur(int secondPort){
        this.port = secondPort;

    }

    public void createServeur () throws IOException{

        HttpServer ser = HttpServer.create(new InetSocketAddress(this.port), 1);


        ser.createContext("/ping",new Call_Handler());

        ser.createContext("/api/game/start", new Start());

        ser.setExecutor(Executors.newFixedThreadPool(1));

        ser.start();

    }

}
