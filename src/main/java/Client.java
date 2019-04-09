package main.java;
import java.io.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("../docs/output.txt"));
        Random random = new Random();
        long start = System.currentTimeMillis();
        String[] topics = {"distributed_systems", "graduate_school"};
        for (int i = 0; i < 100; i++) {
            String url0 = "http://" + args[0] + ":8001/search/" + topics[random.nextInt(2)];
            String url1 = "http://" + args[0] + ":8001/search/" + topics[random.nextInt(2)];
            String url2 = "http://" + args[0] + ":8001/lookup/" + (random.nextInt(4) + 1);
            String url3 = "http://" + args[0] + ":8001/lookup/" + (random.nextInt(4) + 1);
            String url4 = "http://" + args[0] + ":8001/lookup/" + (random.nextInt(4) + 1);
            String url5 = "http://" + args[0] + ":8001/lookup/" + (random.nextInt(4) + 1);
            String url6 = "http://" + args[0] + ":8001/buy/" + (random.nextInt(4) + 1);
            String url7 = "http://" + args[0] + ":8001/buy/" + (random.nextInt(4) + 1);
            String url8 = "http://" + args[0] + ":8001/buy/" + (random.nextInt(4) + 1);
            String url9 = "http://" + args[0] + ":8001/buy/" + (random.nextInt(4) + 1);

            String[] url = {url0, url1, url2, url3, url4, url5, url6, url7, url8, url9};

            for (int j = 0; j < url.length; j++) {
                String[] cmd1 = {"curl", url[j]};

                String s;
                try {
                    Process process = Runtime.getRuntime().exec(cmd1);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((s = reader.readLine()) != null) {
                        ps.println(s);
                    }
                } catch (Exception e) {
                    // do nothing
                    e.printStackTrace();
                }
            }
            System.out.println(i + " iterations have finished");
        }
        long end = System.currentTimeMillis();
        ps.close();
        System.out.println("Total runtime = " + (end - start) + " ms");
    }
}
