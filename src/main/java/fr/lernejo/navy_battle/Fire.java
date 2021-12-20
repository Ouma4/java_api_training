package main.java.fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Fire implements HttpHandler {
    int restBateaux = 3;
    String resulta = "miss";
    String enemy ="http://localhost:2323";

    //Methode pour savoir si le bateaux a était touche ou pas
    private boolean touch(String Cellule){
        return false;
    }

    //Methode de
    private boolean coulBateaux(String Cellule){
        return false;
    }

    //Methode pour savoire le rest des bateaux
    private boolean resteBataux(){
        if(this.restBateaux == 0){
            return false;
        }
        return true;
    }

    //Les information envoyé à l'ennemie.
    private void renvInfoEnemy(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
            .uri(URI.create(this.enemy + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"consequence\":\""+resulta+"\", \"shipLeft\":\""+resteBataux()+"\"}"))
            .build();
        try {
            client.send(post, HttpResponse.BodyHandlers.ofString());
        }
        catch(InterruptedException | IOException e){
            e.printStackTrace();
        }
    }

    //Le cellule
    private String Cellule(HttpExchange exchange) throws IOException {
        String cell;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        JSONObject json_string = new JSONObject(responseStrBuilder.toString());
        cell = json_string.getString("cell");
        return cell;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            String C = Cellule(exchange);
            if(touch(C)){
                this.resulta ="hit";
                if(coulBateaux(C)){
                    this.resulta="sunk";
                    restBateaux --;
                }
            }
            renvInfoEnemy();
        }
    }
}
