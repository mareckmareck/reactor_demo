package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.config.BigCityApartsConfig;
import com.walkowiak.reactordemo.domain.model.Apartment;
import com.walkowiak.reactordemo.domain.model.ApartmentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Collection;

@SuppressWarnings("ConstantConditions")
@Component
public class BigCityApartsClient {

    private final RestTemplate restTemplate;
    private final BigCityApartsConfig config;

    private static final Logger logger = LoggerFactory.getLogger(BigCityApartsClient.class);

    public BigCityApartsClient(RestTemplate restTemplate, BigCityApartsConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public Mono<Collection<Apartment>> getApartments() {
        return Mono.fromCallable(this::executeGetApartments);
    }

    private Collection<Apartment> executeGetApartments() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("Big City Aparts - sending request");

        Collection<Apartment> response = restTemplate.getForObject(config.getUrl(), ApartmentCollection.class).getApartments();

        stopWatch.stop();
        logger.info("Big City Aparts replied in [{}]", stopWatch.getLastTaskTimeMillis());

        return response;
    }
}
