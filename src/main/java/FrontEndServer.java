package main.java;

import static spark.Spark.*;
import static main.java.JsonUtil.*;
import java.net.*;
import java.io.*;


public class FrontEndServer {

    public static void main(String[] args) {
        // configure port
        port(8001);

        String catlogIP = "http://" + args[0] + ":8002/";
        String orderIP = "http://" + args[1] + ":8003/";

        get("/search/:topic", (req, res) -> {

            String topic  = req.params(":topic");
            String urlString = catlogIP + "search/" + topic;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        get("/lookup/:bookId", (req, res) -> {
            String bookId  = req.params(":bookId");
            String urlString = catlogIP + "lookup/" + bookId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        get("/buy/:bookId", (req, res) -> {

            String bookId  = req.params(":bookId");
            String urlString = orderIP + "buy/" + bookId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        get("/testF&C", (req, res) -> {

            String urlString = catlogIP + "testF&C";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        get("/testF&O", (req, res) -> {

            String urlString = orderIP + "testF&O";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        get("/testF&C&O", (req, res) -> {

            String urlString = orderIP + "testF&C&O";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
