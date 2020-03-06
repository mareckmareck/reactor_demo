package com.walkowiak.reactordemo.adapter.api;

import com.walkowiak.reactordemo.domain.ApartmentService;
import com.walkowiak.reactordemo.domain.model.ApartmentCollection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    private Mono<ApartmentCollection> getApartments() {
        return apartmentService.getApartments();
    }
}
