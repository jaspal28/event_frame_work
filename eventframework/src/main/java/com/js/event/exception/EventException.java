package com.js.event.exception;

import com.js.event.Event;

public class EventException extends Exception {
	
	
	/*
	 * Serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * XL Event 
	 */
	private final Event<?,?> event;
	
	/**
	 * Create new blank instance
	 * 
	 * @param event original event object
	 */
	public EventException(Event<?,?> event) {
		super();
		this.event = event;
	}
	
	/**
	 * Create a new instance with the given 'Throwable' as cause
	 * 
	 * @param throwable cause exception
	 * @param event original event object
	 */
	public EventException(Throwable throwable, Event<?,?> event) {
		super(throwable);
		this.event = event;
	}

	/**
	 * Create instance with the given error message
	 * 
	 * @param message error message
	 * @param event original event object
	 */
	public EventException(String message, Event<?,?> event) {
		super(message);
		this.event = event;
	}


	/**
	 * Create new instance
	 * 
	 * @param message error message
	 * @param cause error cause
	 * @param event original event
	 */
	public EventException(String message, Throwable cause, Event<?,?> event) {
		super(message, cause);
		this.event = event;
	}
	
	
	/**
	 * Create new instance with the given arguments
	 * 
	 * @param message error message
	 * @param cause error casue
	 * @param enableSuppression enable suppression
	 * @param writableStackTrace if stack trace is writable
	 * @param event original event object
	 */
	public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Event<?,?> event) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.event = event;
	}


	/**
	 * Get event which caused this exception
	 * 
	 * @return event object
	 */
	public Event<?, ?> getEvent() {
		return event;
	}


}
