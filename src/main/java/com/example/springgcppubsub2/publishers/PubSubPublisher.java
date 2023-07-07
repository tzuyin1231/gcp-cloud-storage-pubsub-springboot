package com.example.springgcppubsub2.publishers;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PubSubPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PubSubPublisher.class);

    private final PubSubTemplate pubSubTemplate;

    protected PubSubPublisher(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    protected abstract String topic();

    public void publish(String message) {
        LOGGER.info("publishing to topic [{}], message: [{}]", topic(), message);
        pubSubTemplate.publish(topic(), message);
    }

}
