package main.java;

public class Book {
    private String id;
    private String name;
    private int stockNum;
    private float cost;
    private String topic;

    // Constructor:
    public Book(String id, String name, int stockNum, float cost, String topic) {
        this.id = id;
        this.name  = name;
        this.stockNum = stockNum;
        this.cost = cost;
        this.topic = topic;
    }

    // Getters:
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getStockNum() {
        return this.stockNum;
    }

    public float getCost() {
        return this.cost;
    }

    public String getTopic() {
        return this.topic;
    }

    // Setters:
    public void setStockNum(int StockNum) {
        this.stockNum = StockNum;
    }

    public void decrementStockNum() {
        if (this.stockNum <= 0) {
            throw new IllegalArgumentException("No such item left!");
        }
        this.stockNum--;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String toString() {
        return id + "," + name + "," + stockNum + "," + cost + "," + topic;
    }
}
