package com.vajda.spockExamples.interactions;

import java.util.List;

import org.slf4j.Logger;

public class Publisher {

    private final Logger logger;
    private final Worker worker;
    
    private List<Subscriber> subscribers;
    
    public Publisher(Logger logger, Worker worker) {
        this.logger = logger;
        this.worker = worker;
    }
    
    public void publish(Object event) {
        for(Subscriber s : subscribers) {
            try {
                if (s.receive(event)) {
                    worker.doWorkA();
                } else {
                    worker.doWorkB();
                }
            } catch(Exception e) {
                logger.error("Error happened", e);
            }
        }
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
}
