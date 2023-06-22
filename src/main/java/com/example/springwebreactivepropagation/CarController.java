package com.example.springwebreactivepropagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
public class CarController {

    @GetMapping("/cars")
    public Mono<String> getCars() {
        return Mono.just("Audi A8")
                .doOnNext(s->log.info("return car"))
                .delayElement(Duration.ofMillis(10))
                .doOnNext(s->log.info("after dely"));
    }
}
