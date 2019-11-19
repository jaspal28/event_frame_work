package com.js.event.manager;

import java.util.Map;

import com.js.event.Event;
import com.js.event.constants.EventListenerPriority;
import com.js.event.constants.EventNotificationType;
import com.js.event.exception.EventException;
import com.js.event.listener.EventListener;
import com.js.event.listener.EventListenerRegistration;

public interface EventManager {

	/**
	 * Register listener with the given parameters
	 * 
	 * @param eventType event type
	 * @param listener  listener
	 */
	public void registerListener(Object eventType, EventListener<?> listener);

	/**
	 * Register listener with the given parameters, use default option for listener
	 * priority
	 * 
	 * @param eventType        event type
	 * @param listener         listener
	 * @param notificationType notification type
	 */
	public void registerListener(Object eventType, EventListener<?> listener, EventNotificationType notificationType);

	/**
	 * Register listener with the given parameters
	 * 
	 * @param eventType        event type
	 * @param listener         listener
	 * @param notificationType notification type
	 * @param listenerPriority priority
	 */
	public void registerListener(Object eventType, EventListener<?> listener, EventNotificationType notificationType,
			EventListenerPriority listenerPriority);

	/**
	 * Register listener with the given registration info
	 * 
	 * @param eventType            event type
	 * @param listenerRegistration registration info
	 */
	public void registerListener(Object eventType, EventListenerRegistration listenerRegistration);

	/**
	 * Register listeners presented by map in which<br>
	 * - key is event type <br>
	 * - value is one of:
	 * <ul>
	 * <li>listener instance (default registration options will be used)</li>
	 * <li>listener registration object</li>
	 * <li>a mixed list of listener or listener registration objects</li>
	 * </ul>
	 * 
	 * @param listenerMap map of listeners, see explanation above.
	 * @throws IllegalArgumentException if unsupported listener type is passed
	 */
	public void registerListener(Map<Object, Object> listenerMap);

	/**
	 * Unregister given listener for thhe given event type
	 * 
	 * @param eventType event type
	 * @param listener  listener instance
	 */
	public void unregisterListener(Object eventType, EventListener<?> listener);

	/**
	 * Fire event. This method should be called by the client to fire event
	 * 
	 * @param event event object
	 * @throws EventException this exception is thrown if error happens. See
	 *                        EventException and subclasses for more details.
	 */
	public void fireEvent(Event<?, ?> event) throws EventException;

}