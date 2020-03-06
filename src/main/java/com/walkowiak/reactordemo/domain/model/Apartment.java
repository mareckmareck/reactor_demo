package com.walkowiak.reactordemo.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Apartment {

    private String street;
    private double size;
    private int price;

    @JsonCreator
    public Apartment(@JsonProperty("street") String street, @JsonProperty("size") double size, @JsonProperty("price") int price) {
        this.street = street;
        this.size = size;
        this.price = price;
    }

    public String getStreet() {
        return street;
    }

    public double getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
