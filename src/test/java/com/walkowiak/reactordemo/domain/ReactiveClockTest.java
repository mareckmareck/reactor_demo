package com.walkowiak.reactordemo.domain;

import com.walkowiak.reactordemo.ReactiveClock;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static java.time.Duration.ofHours;

class ReactiveClockTest {

    ReactiveClock reactiveClock = new ReactiveClock();

    @Test
    void shouldGetApartments() {
        StepVerifier
                .withVirtualTime(() -> reactiveClock.tick()) // fake time, lazily initiate Flux
                .expectSubscription()
                .thenAwait(ofHours(1)) // move fake time forward by one hour
                .expectNext(0L)
                .thenAwait(ofHours(1))
                .expectNext(1L)
                .thenAwait(ofHours(1))
                .expectNext(2L)
                .verifyComplete();
    }
}
