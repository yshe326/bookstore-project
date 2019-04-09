package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import static spark.Spark.*;
import static main.java.JsonUtil.*;
import java.io.*;

public class CatalogServer {
    private HashMap<String, Book> books;
    private HashMap<String, ArrayList<Book>> topics;
    private HashSet<String> soldOuts;

    private static final int SOLD_OUTS = 2;
    private static final int STOCK_NUM = 2;
    private String fileName;

    public CatalogServer(String fileName) {
        // initialize books:
        this.books = new HashMap<>();
        this.fileName = fileName;
        readStock();

        // initialize topics:
        this.topics = new HashMap<>();
        for (Book b: this.books.values()) {
            String t = b.getTopic();
            if (!this.topics.containsKey(t)) {
                this.topics.put(t, new ArrayList<>());
            }
            this.topics.get(t).add(b);
        }

        this.soldOuts = new HashSet<>();
    }

    public ArrayList<Book> getTopicBooks(String topic) {
        return topics.get(topic);
    }

    public Book getBook(String bookId) {
        return books.get(bookId);
    }

//    // read the information of books from the stock file
    public void readStock() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();  // skip the first line (header)
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                Book b = new Book(info[0], info[1], Integer.parseInt(info[2]),
                                    Float.parseFloat(info[3]), info[4]);
                books.put(info[0], b);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // write the updated information of each book into file
    public void writeStock() {
        try {
            PrintStream ps = new PrintStream(new File(fileName));
            ps.println("id,name,stock,cost,topic");  // write header
            for (Book book : books.values()) {
                ps.println(book.toString());  // write each book
            }
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        CatalogServer catalogServer = new CatalogServer(args[0]);

        // configure port
        port(8002);

        // handle search:
        get("/search/:topic", (req, res) -> {
            String topic  = req.params(":topic");
            ArrayList<Book> topicBooks = catalogServer.getTopicBooks(topic);
            if (topicBooks != null && !topicBooks.isEmpty()) {
                return topicBooks;
            }
            res.status(400);
            return new ResponseError("No book with topic '%s' was found", topic);
        }, json());

        // handle lookup:
        get("/lookup/:bookId", (req, res) -> {
            String bookId = req.params(":bookId");
            Book book = catalogServer.getBook(bookId);
            if (book != null) {
                return book;
            }
            res.status(400);
            return new ResponseError("No book with id '%s' found", bookId);

        }, json());

        // handle buy:
        get("/buy/:bookId", (req, res) -> {
            String bookId = req.params(":bookId");
            Book book = catalogServer.getBook(bookId);
            if (book != null) {
                if (book.getStockNum() > 0) {
                    catalogServer.getBook(bookId).decrementStockNum();
                    catalogServer.writeStock();
                    return Integer.toString(book.getStockNum());
                } else {
                    catalogServer.soldOuts.add(bookId);
                    if (catalogServer.soldOuts.size() >= SOLD_OUTS) {
                        for (String id : catalogServer.soldOuts) {
                            catalogServer.getBook(id).setStockNum(STOCK_NUM);
                        }
                        catalogServer.soldOuts.clear();
                        catalogServer.writeStock();
                    }
                    return null;
                }
            }
            res.status(400);
            return new ResponseError("No book with id '%s' found", bookId);
        }, json());

        get("/testF&C", (req, res) -> "testF&C succeeds!!!!!!!!!!!", json());
        get("/testO&C", (req, res) -> "testO&C succeeds!!!!!!!!!!!", json());
        get("/testF&C&O", (req, res) -> "testF&C&O succeeds!!!!!!!!!!!", json());

        after((req, res) -> {
            res.type("application/json");
        });

    }
}
