package com.js.event.exception;

import com.js.event.Event;

public class EventInterruptActionException extends EventException {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create new blank instance
	 */
	public EventInterruptActionException(Event<?, ?> event) {
		super(event);
	}

	/**
	 * Create a new instance with the given 'Throwable' as cause
	 * 
	 * @param throwable cause exception
	 * @param event     original event object
	 */
	public EventInterruptActionException(Throwable throwable, Event<?, ?> event) {
		super(throwable, event);
	}

	/**
	 * Create instance with the given error message
	 * 
	 * @param message error message
	 * @param event   original event object
	 */
	public EventInterruptActionException(String message, Event<?, ?> event) {
		super(message, event);
	}

	/**
	 * Create instance with the given error message and cause
	 * 
	 * @param message   error message
	 * @param throwable error cause
	 * @param event     original event object
	 */
	public EventInterruptActionException(String message, Throwable throwable, Event<?, ?> event) {
		super(message, throwable, event);
	}

	/**
	 * Create new instance with the given arguments
	 * 
	 * @param message            error message
	 * @param cause              error casue
	 * @param enableSuppression  enable suppression
	 * @param writableStackTrace if stack trace is writable
	 * @param event              original event object
	 */
	public EventInterruptActionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Event<?, ?> event) {
		super(message, cause, enableSuppression, writableStackTrace, event);
	}
}
