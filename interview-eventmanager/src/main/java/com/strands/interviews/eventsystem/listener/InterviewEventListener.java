package com.strands.interviews.eventsystem.listener;

import com.strands.interviews.eventsystem.InterviewEvent;

import java.util.List;

/**
 * A simple event listener.
 */
public interface InterviewEventListener<T extends InterviewEvent>
{
    /**
     * Perform some action as a response to an Interview event. The EventManager will
     * ensure that this is only called if the class of the event type is one we
     * have declared that we handle in getHandledEventClasses.
     *
     * @param event some event triggered within an Interview
     */
    void handleEvent(T event);

    /**
     * Determine which event classes this listener is interested in.
     * 
     * <p>The EventManager performs rudimentary filtering of events by their class. If
     * you want to receive only a subset of events passing through the system, return
     * an array of the Classes you wish to listen for from this method.
     *
     * <p>For the sake of efficiency, only exact class matches are performed. Sub/superclassing
     * is not taken into account.
     *
     * @return An array of the event classes that this event listener is interested in,
     *         or an empty array if the listener should receive all events. <b>Must not</b>
     *         return null.
     */
    List<T> getHandledEventClasses();

    /**
     * Determine if an Interview event is supported by the listener. If it is, it will handle this events whenever they
     * are published. Otherwise, it will ignore them.
     *
     * @param event some InterviewEvent event
     * @return True if an Interview event is supported by the listener. False, otherwise.
     */
    boolean supportsEvent(InterviewEvent event);
}
