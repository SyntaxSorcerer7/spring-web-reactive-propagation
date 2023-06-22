package com.example.springwebreactivepropagation;


import io.micrometer.context.ContextRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {

    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String tarceId = Optional.ofNullable(exchange.getRequest().getHeaders().get(TRACE_ID))
                .orElse(new ArrayList<>())
                .stream()
                .findFirst()
                .orElse(UUID.randomUUID().toString());
        MDC.put(TRACE_ID, tarceId);
        log.info("request incomming");
        return chain.filter(exchange)
                //force other thread
                .delayElement(Duration.ofMillis(2))
                .doFinally(s->log.info("request leave"))
            .contextCapture();
    }
}
