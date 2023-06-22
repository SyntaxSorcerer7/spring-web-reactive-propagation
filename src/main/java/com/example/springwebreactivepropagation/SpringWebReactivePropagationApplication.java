package com.example.springwebreactivepropagation;

import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

import static com.example.springwebreactivepropagation.LoggingFilter.TRACE_ID;

@SpringBootApplication
public class SpringWebReactivePropagationApplication {

    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        ContextRegistry.getInstance().registerThreadLocalAccessor(TRACE_ID,
                ()-> MDC.get(TRACE_ID),
                tid->MDC.put(TRACE_ID, tid),
                ()->MDC.remove(TRACE_ID));
        SpringApplication.run(SpringWebReactivePropagationApplication.class, args);
    }

}
