package com.example.demoGateway;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@EnableBinding(Source.class)
@RequiredArgsConstructor
@Slf4j
@Component
public class MyGatewayFilter implements GatewayFilter {

    private final Source source;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpResponse response=exchange.getResponse();

        final Crea crea = new Crea("operation_crea2", "domaine_crea2");
        log.info("Publishing new crea: {}", crea);
        source.output().send(MessageBuilder.withPayload(crea).build());
        log.info("Published new crea: " + crea.getOperation() + " " + crea.getNomDomaine());

        return response.setComplete();
    }
}
