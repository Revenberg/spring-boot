package info.revenberg.song.service;

import org.springframework.context.ApplicationEvent;

/**
 * This is an optional class used in publishing application events.
 * This can be used to inject events into the Spring Boot audit management endpoint.
 */
public class SongServiceEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public SongServiceEvent(Object source) {
        super(source);
    }

}