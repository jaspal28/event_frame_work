package com.js.event.constants;

public enum EventListenerPriority {
	/**
	 * Use default listener order. Current behavior is to add the listener to the
	 * end of the subscribed listeners list. So the order in which listener will be
	 * notified depends on order in which listeners registered.
	 */
	DEFAULT,

	/**
	 * Listener is added to the beginning of subscribers list. So if no other
	 * listeners are registered later with the HIGH priority, then the listener will
	 * be the first who will receive the event notification. If another listener is
	 * registered with the HIGH priority later, then this listener will be moved
	 * down in the list.
	 */
	HIGH,

	/**
	 * Listener is added at the end of the subscribers list. So if no other
	 * listeners are registered later with the LOW (or DEFAULT) priority, then the
	 * listener will be the last to receive event notification.
	 */
	LOW
}
