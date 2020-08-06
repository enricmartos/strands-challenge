package com.strands.interviews.eventsystem;

import com.strands.interviews.eventsystem.events.SimpleEvent;
import com.strands.interviews.eventsystem.listener.InterviewEventListener;

import java.util.List;

/**
 * EventListenerMock that supports subclasses of SimpleEvents
 */
class EventListenerMock<T extends InterviewEvent> implements InterviewEventListener
{
    private boolean called;
    List<T> classes;
    private int count;

    public EventListenerMock(List<T> classes)
    {
        this.classes = classes;
    }

    public void handleEvent(InterviewEvent event)
    {
        called = true;
        count++;
    }

    public void resetCalled()
    {
        called = false;
    }

    public boolean isCalled()
    {
        return called;
    }

    public List<T> getHandledEventClasses()
    {
        return classes;
    }

    public boolean supportsEvent(InterviewEvent interviewEvent) {
        return interviewEvent instanceof SimpleEvent;
    }

    public int getCount() {
        return count;
    }
}