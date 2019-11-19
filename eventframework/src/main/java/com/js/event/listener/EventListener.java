package com.js.event.listener;

import com.js.event.Event;
import com.js.event.context.EventContext;
import com.js.event.exception.EventException;

public interface EventListener<T extends Event<?, ?>> {

	/**
	 * Callback method used by event framework to notify the listener about some
	 * event.
	 * 
	 * @param event   event object
	 * @param context event notification context
	 * @throws XLEventException listener can throw this exception to indicate error
	 *                          (see also subclasses of this exception which can be
	 *                          used to adjust event handling flow)
	 */
	public void onEvent(T event, EventContext context) throws EventException;

}