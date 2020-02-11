package info.revenberg.song.service;

import org.springframework.context.ApplicationEvent;

/**
 * This is an optional class used in publishing application events.
 * This can be used to inject events into the Spring Boot audit management endpoint.
 */
public class VersServiceEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public VersServiceEvent(Object source) {
        super(source);
    }

}