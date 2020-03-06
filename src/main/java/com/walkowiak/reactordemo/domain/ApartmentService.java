package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.domain.model.Apartment;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Collection;

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

    Collection<Apartment> getApartments() {
        logger.info("Starting execution");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Collection<Apartment> bigCityAparts = bigCityApartsClient.getApartments();
        Collection<Apartment> cozyHousesAparts = cozyHousesClient.getApartments();
        Collection<Apartment> allAparts = CollectionUtils.union(bigCityAparts, cozyHousesAparts);

        stopWatch.stop();
        logger.info("Total execution time was [{}]", stopWatch.getLastTaskTimeMillis());

        return allAparts;
    }
}
