package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.WithMockedServer;
import com.walkowiak.reactordemo.domain.model.Apartment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static com.walkowiak.reactordemo.Stubs.bigCityAparts;
import static com.walkowiak.reactordemo.Stubs.cozyHousesAparts;

@SpringBootTest
@ActiveProfiles("test")
class ApartmentServiceTest extends WithMockedServer {

    @Autowired
    ApartmentService apartmentService;

    @Test
    void shouldGetApartments() {
        bigCityAparts().willRespondInMillis(200).withOneApartment();
        cozyHousesAparts().willRespondInMillis(400).withOneApartment();

        Collection<Apartment> apartments = apartmentService.getApartments();

        assert apartments.size() == 2;
    }
}
