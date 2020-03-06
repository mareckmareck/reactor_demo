package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.domain.model.ApartmentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Mono;

@Service
public class ApartmentService {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentService.class);

    private final BigCityApartsClient bigCityApartsClient;
    private final CozyHousesApartsClient cozyHousesClient;

    ApartmentService(BigCityApartsClient bigCityApartsClient,
                     CozyHousesApartsClient cozyHousesClient) {
        this.bigCityApartsClient = bigCityApartsClient;
        this.cozyHousesClient = cozyHousesClient;
    }

    public Mono<ApartmentCollection> getApartments() {
        Mono<ApartmentCollection> bigCityAparts =
                bigCityApartsClient
                        .getApartments();

        Mono<ApartmentCollection> cozyHousesAparts =
                cozyHousesClient
                        .getApartments();

        Mono<ApartmentCollection> allAparts =
                bigCityAparts
                        .zipWith(cozyHousesAparts, ApartmentCollection::union);

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
