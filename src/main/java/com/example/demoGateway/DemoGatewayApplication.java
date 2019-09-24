package com.example.demoGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableConfigurationProperties(DemoGatewayApplication.UriConfiguration.class)

@RestController
public class DemoGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration,
								 MyGatewayFilter creaFilter) {
		String httpUri = uriConfiguration.getHttpbin();
		String da058Uri = uriConfiguration.getDA058();
		return builder.routes()
				.route(r -> r
						.method(HttpMethod.POST).and()
						.path("/certification/identite")
						.filters(f -> f.filter(creaFilter))
						.uri(da058Uri))
		 		.route(r -> r
						.host(httpUri).and().method(HttpMethod.DELETE)
						.filters(f -> f.hystrix(config -> config.setName("mycmd")
						.setFallbackUri("forward:/fallback")))
						.uri(httpUri))
				.route(r -> r
						.path("/get")
						.filters(f -> f.addResponseHeader("Response-From","HttpBin"))
						.uri(httpUri))
				.route(r -> r
						.method(HttpMethod.POST).and()
						.path("/anything")
						.filters(f -> f.addRequestHeader("Passed-By", "demoGateway"))
						.uri(httpUri)).
				build();

	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}

	@ConfigurationProperties
	class UriConfiguration {

		private String httpbin = "http://httpbin.org:80";
		private String DA058 = "https://da058-certification-service.apps.foundry.sii24.pole-emploi.intra/";

		public String getHttpbin() {
			return httpbin;
		}

		public void setHttpbin(String httpbin) {
			this.httpbin = httpbin;
		}

		public String getDA058(){ return DA058; }
	}
}
