package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.config.CozyHousesApartsConfig;
import com.walkowiak.reactordemo.domain.model.ApartmentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CozyHousesApartsClient {

    private final WebClient webClient;
    private CozyHousesApartsConfig config;

    private static final Logger logger = LoggerFactory.getLogger(CozyHousesApartsClient.class);

    public CozyHousesApartsClient(WebClient webClient, CozyHousesApartsConfig config) {
        this.webClient = webClient;
        this.config = config;
    }

    public Mono<ApartmentCollection> getApartments() {
        return executeGetApartments();
    }

    private Mono<ApartmentCollection> executeGetApartments() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("Cozy Houses Aparts - sending request");

        return webClient
                .get()
                .uri(URI.create(config.getUrl()))
                .retrieve()
                .bodyToMono(ApartmentCollection.class)
                .doOnNext(next -> {
                    stopWatch.stop();
                    logger.info("Cozy Houses Aparts replied in [{}]", stopWatch.getLastTaskTimeMillis());
                });
    }
}
