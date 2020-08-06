package com.strands.interviews.eventsystem.listener;

import com.strands.interviews.eventsystem.events.SubEvent;

import java.util.List;

/**
 * A sub event listener.
 */
public class SubEventListener extends SimpleEventListener<SubEvent> {

    public SubEventListener(List<SubEvent> classes) {
        super(classes);
    }
}
