package com.strands.interviews.eventsystem;

public class EventMock implements InterviewEvent
{
    private Object source;

    public EventMock(Object source)
    {
        this.source = source;
    }

    public Object getSource()
    {
        return source;
    }

}
