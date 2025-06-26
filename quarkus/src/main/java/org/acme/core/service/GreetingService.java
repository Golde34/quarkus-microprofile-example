package org.acme.core.service;

import java.time.Duration;
import java.util.List;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {
    
    public Uni<String> greeting(String name) {
        return Uni.createFrom().item(name)
                .onItem().transform(n -> String.format("hello %s", name));
    }

    public Multi<String> greetings(int count, String name) {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n -> String.format("hello %s - %d", name, n))
                .select().first(count);
    }

    public Multi<String> greetings(List<String> names) {
        return Multi.createFrom().items(names)
                .onItem().transform(name -> String.format("hello %s", name));
    }
}
