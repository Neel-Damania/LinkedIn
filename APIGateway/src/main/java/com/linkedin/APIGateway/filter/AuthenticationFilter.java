package com.linkedin.APIGateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.linkedin.APIGateway.JwtService;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Auth Request: " + exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")){  
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            final String token = tokenHeader.split("Bearer ")[1];
            try {
                String userId = jwtService.getUserIdFromToken(token);
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(r ->r.header("X-User-Id", userId))
                        .build();
                        
                return chain.filter(mutatedExchange);
            } catch (JwtException e) {
                log.error("JwtException: " + e.getLocalizedMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
    
    static class Config{
        
    }


}
