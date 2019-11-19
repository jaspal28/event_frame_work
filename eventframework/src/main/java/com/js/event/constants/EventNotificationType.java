package com.js.event.constants;

public enum EventNotificationType {
	/**
	 * Synchronous notification, i.e. listener will be notified synchronously in the
	 * same thread from which event was fired
	 */
	SYNCHRONOUS,

	/**
	 * Asynchronous notification, i.e. separate thread will be used to notify
	 * listener asynchronously. This type of notification can be used with
	 * precaution, as thread race condition may happen and there is no guarantee for
	 * when listener will be notified
	 * 
	 */
	ASYNCHRONOUS
}
