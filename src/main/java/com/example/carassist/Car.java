package com.example.carassist;

public class Car {
    private String name;
    private int price;
    private String agency;
    private String comments;

    public Car(String name, int price, String agency, String comments)
    {
        this.name = name;
        this.price = price;
        this.agency = agency;
        this.comments = comments;
    }

    public String getName()
    {
        return name;
    }

    public int getPrice()
    {
        return price;
    }

    public String getAgency()
    {
        return agency;
    }

    public String getComments()
    {
        return comments;
    }
}
