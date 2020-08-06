package com.strands.interviews.eventsystem.listener;

import com.strands.interviews.eventsystem.InterviewEvent;
import com.strands.interviews.eventsystem.events.SimpleEvent;

import java.util.List;

/**
 * A simple event listener.
 */
public class SimpleEventListener<T extends SimpleEvent> implements InterviewEventListener<T> {

	private List<T> classes;

	public SimpleEventListener(List<T> classes) {
		this.classes = classes;
	}

	public void handleEvent(SimpleEvent event) {
	}

	public List<T> getHandledEventClasses() {
		return classes;
	}

	public final boolean supportsEvent(InterviewEvent interviewEvent) {
		return interviewEvent instanceof SimpleEvent;
	}

}
