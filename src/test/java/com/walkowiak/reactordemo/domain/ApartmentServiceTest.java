package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.WithMockedServer;
import com.walkowiak.reactordemo.domain.model.ApartmentCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.walkowiak.reactordemo.Stubs.bigCityAparts;
import static com.walkowiak.reactordemo.Stubs.cozyHousesAparts;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ApartmentServiceTest extends WithMockedServer {

    @LocalServerPort
    int serverPort;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldGetApartments() {
        bigCityAparts().willRespondInMillis(1500).withOneApartment();
        cozyHousesAparts().willRespondInMillis(4000).withOneApartment();

        webTestClient
                .get()
                .uri(format("http://localhost:%s/apartments", serverPort))
                .exchange()
                .expectBody(ApartmentCollection.class)
                .value(apartmentCollection -> apartmentCollection.getApartments().size(), equalTo(2));
    }
}
