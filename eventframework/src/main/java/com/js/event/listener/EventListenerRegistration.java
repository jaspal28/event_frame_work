package com.js.event.listener;

import com.js.event.constants.EventListenerPriority;
import com.js.event.constants.EventNotificationType;

public class EventListenerRegistration {

	/*
	 * Notification type
	 */
	private EventNotificationType notificationType;

	/*
	 * Listener priority
	 */
	private EventListenerPriority listenerPriority;

	/*
	 * Listener instance
	 */
	private EventListener<?> listener;

	/**
	 * Create new blank instance
	 */
	public EventListenerRegistration() {
		this.listenerPriority = EventListenerPriority.DEFAULT;
	}

	/**
	 * Create new instance with the given parameters
	 * 
	 * @param listener         event listener to be registered
	 * @param notificationType string presentation of the notification type
	 */
	public EventListenerRegistration(EventListener<?> listener, String notificationType) {
		this();
		this.listener = listener;
		this.notificationType = EventNotificationType.valueOf(notificationType);
	}

	/**
	 * Create new instance with the given parameters
	 * 
	 * @param listener         event listener to be registered
	 * @param notificationType string presentation of the notification type
	 * @param listenerPriority string presentation of the listener priority
	 */
	public EventListenerRegistration(EventListener<?> listener, String notificationType, String listenerPriority) {
		this.listener = listener;
		this.notificationType = EventNotificationType.valueOf(notificationType);
		this.listenerPriority = EventListenerPriority.valueOf(listenerPriority);
	}

	/**
	 * Create new instance with the given parameters
	 * 
	 * @param listener         event listener to be registered
	 * @param notificationType notification type
	 */
	public EventListenerRegistration(EventListener<?> listener, EventNotificationType notificationType) {
		this.listener = listener;
		this.notificationType = notificationType;
		this.listenerPriority = EventListenerPriority.DEFAULT;
	}

	/**
	 * Create new instance with the given parameters
	 * 
	 * @param listener         event listener to be registered
	 * @param notificationType notification type
	 * @param listenerPriority listener priority
	 */
	public EventListenerRegistration(EventListener<?> listener, EventNotificationType notificationType,
			EventListenerPriority listenerPriority) {
		this.listener = listener;
		this.notificationType = notificationType;
		this.listenerPriority = listenerPriority;
	}

	/**
	 * Get notification type
	 * 
	 * @return notification type value
	 */
	public EventNotificationType getNotificationType() {
		return notificationType;
	}

	/**
	 * Set notification type
	 * 
	 * @param notificationType notification type value
	 */
	public void setNotificationType(EventNotificationType notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * Get listener priority
	 * 
	 * @return priority value
	 */
	public EventListenerPriority getListenerPriority() {
		return listenerPriority;
	}

	/**
	 * Set listener priority
	 * 
	 * @param listenerPriority priority value
	 */
	public void setListenerPriority(EventListenerPriority listenerPriority) {
		this.listenerPriority = listenerPriority;
	}

	/**
	 * Get listener
	 * 
	 * @return listener instance
	 */
	public EventListener<?> getListener() {
		return listener;
	}

	/**
	 * Set listener
	 * 
	 * @param listener listener instance
	 */
	public void setListener(EventListener<?> listener) {
		this.listener = listener;
	}

	/**
	 * Equals method implementation
	 */
	@Override
	public boolean equals(Object object) {

		if (object instanceof EventListenerRegistration) {
			return ((EventListenerRegistration) object).listener.equals(listener);
		} else {
			return false;
		}
	}

	/**
	 * Hashcode method implementation
	 */
	@Override
	public int hashCode() {
		return listener.hashCode();
	}
}
