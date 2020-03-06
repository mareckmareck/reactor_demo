package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.WithMockedServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static com.walkowiak.reactordemo.Stubs.bigCityAparts;
import static com.walkowiak.reactordemo.Stubs.cozyHousesAparts;

@SpringBootTest
@ActiveProfiles("test")
class ApartmentServiceTest extends WithMockedServer {

    @Autowired
    ApartmentService apartmentService;

    @Test
    void shouldGetApartments() {
        bigCityAparts().willRespondInMillis(1500).withOneApartment();
        cozyHousesAparts().willRespondInMillis(4000).withOneApartment();

        StepVerifier
                .create(apartmentService.getApartments())
                .expectNextMatches(apartments -> apartments.size() == 2)
                .verifyComplete();
    }
}
