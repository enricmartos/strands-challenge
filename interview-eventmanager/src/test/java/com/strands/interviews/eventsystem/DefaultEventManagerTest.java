package com.strands.interviews.eventsystem;

import com.strands.interviews.eventsystem.events.CreationEvent;
import com.strands.interviews.eventsystem.events.SimpleEvent;
import com.strands.interviews.eventsystem.events.SubEvent;
import com.strands.interviews.eventsystem.impl.DefaultEventManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DefaultEventManagerTest
{
    private EventManager eventManager = new DefaultEventManager();

    @Test
    public void testPublishNullEvent()
    {
        eventManager.publishEvent(null);
    }

    @Test
    public void testRegisterListenerAndPublishEvent()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(Arrays.asList(new SimpleEvent(this)));
        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.publishEvent(new SimpleEvent(this));
        assertTrue(eventListenerMock.isCalled());
    }

    /**
     * Check that when a SubEvent (subclass) is published, the listeners of unrelated Events will not be notified
     */
    @Test
    public void testListenerWithoutMatchingEventClass()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(Arrays.asList(new SubEvent(this)));
        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.publishEvent(new EventMock(this));
        assertFalse(eventListenerMock.isCalled());
    }

    /**
     * Check that when a SimpleEvent (superclass) is published, the listeners of SimpleEvent subclasses will be notified
     */
    @Test
    public void testSimpleEventListenerThatAcceptsSubEvents() {
        EventListenerMock eventListenerMock = new EventListenerMock(Arrays.asList(new SimpleEvent(this)));
        eventManager.registerListener("listener.mock.key", eventListenerMock);
        eventManager.publishEvent(new SubEvent(this));
        assertTrue(eventListenerMock.isCalled());
    }

    /**
     * Check that when a new event listener is added with no events, it will listen to all events previously
     * registered in the system
     */
    @Test
    public void testListenerWithEmptyEventsListensToAllEvents() {
        EventListenerMock eventListenerMockWithTwoEvents = new EventListenerMock(Arrays.asList(new SimpleEvent(this), new CreationEvent(this)));
        EventListenerMock eventListenerMockWithEmptyEvents = new EventListenerMock(new ArrayList());
        eventManager.registerListener("listener.with.two.events.mock.key", eventListenerMockWithTwoEvents);
        eventManager.registerListener("listener.with.empty.events.mock.key", eventListenerMockWithEmptyEvents);
        eventManager.publishEvent(new SubEvent(this));
        eventManager.publishEvent(new CreationEvent(this));
        assertEquals(2, eventListenerMockWithEmptyEvents.getCount());
    }

    @Test
    public void testUnregisterListener()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(Arrays.asList(new SimpleEvent(this)));
        EventListenerMock eventListenerMock2 = new EventListenerMock(Arrays.asList(new SimpleEvent(this)));

        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.registerListener("another.key", eventListenerMock2);
        eventManager.unregisterListener("some.key");

        eventManager.publishEvent(new SimpleEvent(this));
        assertFalse(eventListenerMock.isCalled());
        assertTrue(eventListenerMock2.isCalled());
    }


    /**
     * Check that registering and unregistering listeners behaves properly.
     */
    @Test
    public void testRemoveNonexistentListener()
    {
        DefaultEventManager dem = (DefaultEventManager)eventManager;
        assertEquals(0, dem.getListeners().size());
        eventManager.registerListener("some.key", new EventListenerMock(Arrays.asList(new SimpleEvent(this))));
        assertEquals(1, dem.getListeners().size());
        eventManager.unregisterListener("this.key.is.not.registered");
        assertEquals(1, dem.getListeners().size());
        eventManager.unregisterListener("some.key");
        assertEquals(0, dem.getListeners().size());
    }

    /**
     * Registering duplicate keys on different listeners should only fire the most recently added.
     */
    @Test
    public void testDuplicateKeysForListeners()
    {
        EventListenerMock eventListenerMock = new EventListenerMock(Arrays.asList(new SimpleEvent(this)));
        EventListenerMock eventListenerMock2 = new EventListenerMock(Arrays.asList(new SimpleEvent(this)));

        eventManager.registerListener("some.key", eventListenerMock);
        eventManager.registerListener("some.key", eventListenerMock2);

        eventManager.publishEvent(new SimpleEvent(this));

        assertTrue(eventListenerMock2.isCalled());
        assertFalse(eventListenerMock.isCalled());

        eventListenerMock.resetCalled();
        eventListenerMock2.resetCalled();

        eventManager.unregisterListener("some.key");
        eventManager.publishEvent(new SimpleEvent(this));

        assertFalse(eventListenerMock2.isCalled());
        assertFalse(eventListenerMock.isCalled());
    }

    /**
     * Attempting to register a null with a valid key should result in an illegal argument exception
     */
    @Test
    public void testAddValidKeyWithNullListener()
    {
        try
        {
            eventManager.registerListener("bogus.key", null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException ex)
        {
        }
    }
}
