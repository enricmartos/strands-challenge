package com.strands.interviews.eventsystem.impl;

import com.strands.interviews.eventsystem.EventManager;
import com.strands.interviews.eventsystem.InterviewEvent;
import com.strands.interviews.eventsystem.listener.InterviewEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages the firing and receiving of events.
 *
 * <p>Any event passed to {@link #publishEvent} will be passed through to "interested" listeners.
 *
 * <p>Event listeners can register to receive events via
 * {@link #registerListener(String, InterviewEventListener)}
 */
public class DefaultEventManager implements EventManager
{
    private Map<String, InterviewEventListener> listeners = new HashMap();
    private Map<InterviewEvent, List<InterviewEventListener>> listenersByClass = new HashMap();

    public void publishEvent(InterviewEvent event)
    {
        if (event == null)
        {
            System.err.println("Null event fired?");
            return;
        }

        sendEventTo(event, calculateListeners(event));
    }

    private Set<InterviewEventListener> calculateListeners(InterviewEvent eventClass) {
        Set<InterviewEventListener> allListeners = new HashSet<InterviewEventListener>();
        for (InterviewEvent event : listenersByClass.keySet()) {
            allListeners.addAll(listenersByClass.get(event));

        }

        Iterator<InterviewEventListener> iterator = allListeners.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().supportsEvent(eventClass)) {
                iterator.remove();
            }
        }

        return allListeners;
    }

    public void registerListener(String listenerKey, InterviewEventListener listener)
    {
        if (listenerKey == null || listenerKey.equals(""))
            throw new IllegalArgumentException("Key for the listener must not be null: " + listenerKey);

        if (listener == null)
            throw new IllegalArgumentException("The listener must not be null: " + listener);

        if (listeners.containsKey(listenerKey))
            unregisterListener(listenerKey);

        List<InterviewEvent> classes = listener.getHandledEventClasses();

        if (classes.size() == 0) {
            subscribeListenerToAllEvents(listener);
        }

        for (InterviewEvent event : classes)
            addToListenerList(event, listener);


        listeners.put(listenerKey, listener);
    }

    private void subscribeListenerToAllEvents(InterviewEventListener listener) {
        for (Iterator it = listenersByClass.values().iterator(); it.hasNext();) {
            List list = (List) it.next();
            list.add(listener);
        }
    }


    public void unregisterListener(String listenerKey)
    {
        InterviewEventListener listener = listeners.get(listenerKey);

        for (Iterator it = listenersByClass.values().iterator(); it.hasNext();)
        {
            List list = (List) it.next();
            list.remove(listener);
        }

        listeners.remove(listenerKey);
    }

    private void sendEventTo(InterviewEvent event, Collection listeners)
    {
        if (listeners == null || listeners.size() == 0)
            return;

        for (Iterator it = listeners.iterator(); it.hasNext();)
        {
            InterviewEventListener eventListener = (InterviewEventListener) it.next();
            eventListener.handleEvent(event);
        }
    }

    private void addToListenerList(InterviewEvent aClass, InterviewEventListener listener) {
        if (!listenersByClass.containsKey(aClass))
            listenersByClass.put(aClass, new ArrayList());

        listenersByClass.get(aClass).add(listener);
    }

    public Map getListeners()
    {
        return listeners;
    }
}
