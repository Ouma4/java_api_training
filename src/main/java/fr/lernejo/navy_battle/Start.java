package main.java.fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Start implements HttpHandler{

    //L'erreure
    private void erreur(String err, HttpExchange inf) throws IOException {

        if(err.equals("Not FOUND"))  {
            inf.sendResponseHeaders(404, err.length());
            try (OutputStream os = inf.getResponseBody()) {
                os.write(err.getBytes());
            }
        }else if(err.equals("Bad Request")){
            inf.sendResponseHeaders(400, err.length());
            try (OutputStream os = inf.getResponseBody()) {
                os.write(err.getBytes());
            }
        }
    }


    //Le test
    private boolean testJson(InputStream tjson) throws IOException, ParseException {
        JSONParser js = new JSONParser();
        JSONObject msg = (JSONObject)js.parse(
            new InputStreamReader(tjson, "UTF-8"));

        String idJson = msg.getString("id");
        String urlJson = msg.getString("url");
        String messageJson = msg.getString("message");

        if(idJson != null && urlJson != null && messageJson != null){
            return true;
        }
        return false;
    }

    //L'envoie de message
    private void envoiMessage(HttpExchange info) throws IOException {
        int port = info.getHttpContext().getServer().getAddress().getPort();
        JSONObject renvoi = new JSONObject();
        renvoi.put("id","22");
        renvoi.put("url","http//localhost:"+port);
        renvoi.put("message","Message recived");
        info.sendResponseHeaders(202, renvoi.toString().length());
        try (OutputStream os = info.getResponseBody()) {
            os.write(renvoi.toString().getBytes());
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")){
            try {
                if(testJson(exchange.getRequestBody())){
                   envoiMessage(exchange);
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            erreur("Bad Request", exchange);
        }else{
            erreur("Not FOUND", exchange);
        }}
}
