package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.WithMockedServer;
import com.walkowiak.reactordemo.domain.model.Apartment;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;

import static com.walkowiak.reactordemo.Stubs.bigCityAparts;
import static com.walkowiak.reactordemo.Stubs.cozyHousesAparts;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@ActiveProfiles("test")
class ApartmentServiceTest extends WithMockedServer {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentServiceTest.class);

    @Autowired
    ApartmentService apartmentService;

    @Test
    void shouldGetApartments() {
        bigCityAparts().willRespondInMillis(1500).withOneApartment();
        cozyHousesAparts().willRespondInMillis(4000).withOneApartment();

        Collection<Apartment> apartments = new ArrayList<>();

        apartmentService.getApartments().subscribe(apartments::addAll);

        await().atMost(Duration.FIVE_SECONDS).until(() -> apartments.size() == 2);
    }
}
