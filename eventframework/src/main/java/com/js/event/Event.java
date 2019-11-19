package com.js.event;

public interface Event<T extends Object, V extends Object> {

	/**
	 * Get Event type. Event type can be represented by any object and is used by
	 * the XL Event framework to match the registered listeners with the particular
	 * event. The typical 'getEventType' method implementation would be to return
	 * the full event class name. In order to match listeners registered for
	 * particular event type, the returned object should provide correct 'hashCode'
	 * and 'equal' implementations.
	 * 
	 * @return object representing event type
	 */
	public T getEventType();

	/**
	 * Get event data. Data can be any object providing additional information about
	 * the event.
	 * 
	 * @return object representing event data
	 */
	public V getEventData();

}