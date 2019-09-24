package com.example.demoGateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class CreaGatewayFilterFactory extends AbstractGatewayFilterFactory<CreaGatewayFilterFactory.Config> {

    public CreaGatewayFilterFactory() {
        super(Config.class);
    }

    public static final String CREA_KEY = "crea";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(CREA_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                //Manipulate the response in some way


            }));
        };
    }

    public static class Config {
        private String crea;

        public String getCrea() {
            return crea;
        }

        public void setCrea(String prefix) {
            this.crea = prefix;
        }
    }
}
