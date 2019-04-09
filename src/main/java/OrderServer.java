package main.java;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static main.java.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.port;

public class OrderServer {
    private static final String ORDER_LOG_FILE_NAME = "../test/order.csv";

    private void appendLog(String orderId, String bookId, String status) {
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(ORDER_LOG_FILE_NAME, true));
            output.append(orderId + "," + bookId + "," + status + "\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OrderServer orderServer = new OrderServer();

        port(8003);

        String catlogIP = "http://"+ args[0] +":8002/";

        get("/buy/:bookId", (req, res) -> {

            // get random 4 digits number as order number
            Random random = new Random();
            String orderId = String.format("%04d", random.nextInt(10000));

            String bookId  = req.params(":bookId");
            String urlString = catlogIP + "buy/" + bookId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            if (!content.toString().equals("null")) {
                String orderInf = "bookId: " + bookId + "  order number: " + orderId + "  Stock remain: " + content.toString();
                orderServer.appendLog(orderId, bookId, "Success");
                return orderInf;
            } else {
                orderServer.appendLog(orderId, bookId, "Fail");
                return "bookId: " + bookId + " out of stock ";
            }
        }, json());

        get("/test", (req, res) -> {

            String urlString = catlogIP + "test";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content;
        }, json());

        get("/testF&O", (req, res) -> "testF&O succeeds!!!!!!!!!!!", json());

        get("/testF&C&O", (req, res) -> {

            String urlString = catlogIP + "testF&C&O";
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
    }
}
