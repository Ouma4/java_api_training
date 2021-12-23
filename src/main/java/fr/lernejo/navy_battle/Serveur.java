package main.java.fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class Serveur {

    final int port;
    final String url;
    final String enemy ="http://localhost:2323";

    Serveur(int secondPort,String url){
        this.port = secondPort;
        this.url = url;

    }

    private void post(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
            .uri(URI.create(this.enemy + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"23\", \"url\":\"http://localhost:" + this.port + "\", \"message\":\"I'm going to beat you\"}"))
            .build();
        try {
            client.send(post, HttpResponse.BodyHandlers.ofString());
        }
        catch(InterruptedException | IOException e){
            e.printStackTrace();
        }
    }



    public void createServeur () throws IOException{

        HttpServer ser = HttpServer.create(new InetSocketAddress(this.port), 1);
        ser.createContext("/ping",new Call_Handler());
        ser.createContext("/api/game/start", new Start());
        ser.createContext("/api/game/fire", new Fire());
        ser.setExecutor(Executors.newFixedThreadPool(1));
        ser.start();
        if(!url.equals("")){
            post();
        }
    }
}
