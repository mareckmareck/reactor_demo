package com.walkowiak.reactordemo.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class ApartmentCollection {

    private final Collection<Apartment> apartments;

    @JsonCreator
    private ApartmentCollection(@JsonProperty("apartments") Collection<Apartment> apartments) {
        this.apartments = apartments;
    }

    public Collection<Apartment> getApartments() {
        return apartments;
    }
}
