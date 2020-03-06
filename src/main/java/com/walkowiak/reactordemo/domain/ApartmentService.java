package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.domain.model.Apartment;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
class ApartmentService {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentService.class);

    private final BigCityApartsClient bigCityApartsClient;
    private final CozyHousesApartsClient cozyHousesClient;

    ApartmentService(BigCityApartsClient bigCityApartsClient,
                     CozyHousesApartsClient cozyHousesClient) {
        this.bigCityApartsClient = bigCityApartsClient;
        this.cozyHousesClient = cozyHousesClient;
    }

    Mono<Collection<Apartment>> getApartments() {
        Mono<Collection<Apartment>> bigCityAparts =
                bigCityApartsClient
                        .getApartments();

        Mono<Collection<Apartment>> cozyHousesAparts =
                cozyHousesClient
                        .getApartments();

        Mono<Collection<Apartment>> allAparts =
                bigCityAparts
                        .zipWith(cozyHousesAparts, CollectionUtils::union);

        logger.info("Starting execution");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        return allAparts
                .doFinally(x -> {
                    stopWatch.stop();
                    logger.info("Total execution time was [{}]", stopWatch.getTotalTimeMillis());
                });
    }

}
