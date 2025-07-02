package com.example.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class StartupLogger {
    private static final Logger LOG = Logger.getLogger(StartupLogger.class);
    private final long start = System.currentTimeMillis();

    void onStart(@Observes StartupEvent ev) {
        long duration = System.currentTimeMillis() - start;
        LOG.infof("Order service started in %d ms", duration);
    }
}
