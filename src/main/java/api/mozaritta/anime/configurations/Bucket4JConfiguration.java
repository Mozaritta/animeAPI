package api.mozaritta.anime.configurations;

import io.github.bucket4j.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Bucket4JConfiguration {

    private Bucket bucket;

    @Bean
    public Bucket bucketConfig(){
        Bandwidth limit = Bandwidth.classic(6, Refill.greedy(6, Duration.ofMinutes(1)));
        bucket = Bucket4j.builder().addLimit(limit).build();
        return bucket;
    }}
