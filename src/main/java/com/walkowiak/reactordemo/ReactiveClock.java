package com.walkowiak.reactordemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import static java.time.Duration.ofHours;

public class ReactiveClock {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveClock.class);

    public Flux<Long> tick() {
        return Flux
                .interval(ofHours(1))
                .doOnNext(value -> logger.info(String.format("Tick! Count: %s", value)))
                .take(3)
                .doFinally(sig -> logger.info("Finished after 3 ticks"));
    }
}
