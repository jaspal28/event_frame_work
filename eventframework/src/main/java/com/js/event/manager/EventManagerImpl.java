package com.js.event.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.js.event.Event;
import com.js.event.constants.EventListenerPriority;
import com.js.event.constants.EventNotificationType;
import com.js.event.context.EventContext;
import com.js.event.context.EventHashMapContext;
import com.js.event.exception.EventException;
import com.js.event.exception.EventInterruptActionException;
import com.js.event.exception.EventInterruptNotificationException;
import com.js.event.listener.EventListener;
import com.js.event.listener.EventListenerRegistration;

public class EventManagerImpl implements EventManager {

	/*
	 * Logger instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EventManagerImpl.class);

	/*
	 * Default thread pool size
	 */
	private static final int DEFAULT_THREAD_POOL_SIZE = 8;

	/*
	 * Default notification type
	 */
	private static final EventNotificationType DEFAULT_NOTIFICATION_TYPE = EventNotificationType.SYNCHRONOUS;

	/*
	 * Default listener priority
	 */
	private static final EventListenerPriority DEFAULT_LISTENER_PRIORITY = EventListenerPriority.DEFAULT;

	/*
	 * Listener store !TODO: Change to use Concurrent collections
	 */
	private Map<Object, List<EventListenerRegistration>> eventListenerMap;

	/*
	 * Executor service used for asynchronous listener notification
	 */
	private ExecutorService executorService = null;

	/**
	 * Create instance with default parameters and empty listener map
	 */
	public EventManagerImpl() {

		/*
		 * Delegate to parameterized constructor
		 */
		this(DEFAULT_THREAD_POOL_SIZE);
	}

	/**
	 * Create instance with specified thread pool size for asynchronous notification
	 * 
	 * @param threadPoolSize asynchronous notification thread pool size
	 */
	public EventManagerImpl(int threadPoolSize) {

		/*
		 * Instantiate listener map
		 */
		eventListenerMap = new HashMap<Object, List<EventListenerRegistration>>();

		/*
		 * Instantiate executor service for asynchronous notification
		 */
		executorService = Executors.newFixedThreadPool(threadPoolSize);
	}

	/**
	 * Use this constructor if you want to intialize executor service with your
	 * custom executorservice. Useful when executorservice needs custom settings,
	 * such as it has to transaction aware.
	 * 
	 * @param the executor service to be set
	 */
	public EventManagerImpl(ExecutorService executorParam) {

		/*
		 * Instantiate listener map
		 */
		eventListenerMap = new HashMap<Object, List<EventListenerRegistration>>();

		/*
		 * Instantiate executor service from passed parameter
		 */
		executorService = executorParam;
	}

	/**
	 * Fire Event
	 */
	public void fireEvent(Event<?, ?> event) throws EventException {

		LOGGER.trace(" event was fired: {} ", event.toString());

		/*
		 * Create event context
		 */
		EventContext context = new EventHashMapContext();

		/*
		 * Iterate registered listeners
		 */
		for (EventListenerRegistration registration : getEventListenerList(event.getEventType())) {

			try {

				/*
				 * Check if listener is actually set
				 */
				if (null != registration.getListener()) {

					LOGGER.trace("Notifing listener: {}, notification type: {} ", registration.getListener(),
							registration.getNotificationType());

					/*
					 * Notify listener
					 */
					notifyListener(event, context, registration);
				}
			}

			/*
			 * If interrupt notification, then stop notifying listeners, but not throwing
			 * exception out
			 */
			catch (EventInterruptNotificationException e) {

				LOGGER.error(
						"Interrupt Notification Exception was thrown, listener notification will be stopped for this event",
						e);

				break;
			}

			/*
			 * If interrupt action, then re-throw exception out
			 */
			catch (EventInterruptActionException e) {

				LOGGER.error(
						"Interrupt Action Exception was thrown, AppException will be propogated to interrupt workflow.",
						e);

				throw e;
			}

			/*
			 * Other exception types - just log exception, but keep processing event
			 */
			catch (EventException e) {

				LOGGER.error("Event Exception was thrown, listeners notification will continue", e);
			}
		}

		LOGGER.trace("Listeners notification is finished.");
	}

	/**
	 * Notify listener. This method checks the listener subscription type -
	 * synchronous or asynchronous, and notifies listener accordingly.
	 * 
	 * @param event        event instance
	 * @param context      notification context
	 * @param registration registration info
	 * @throws EventException certain exceptions can be thrown by event listeners,
	 *                        see documentation for more details.
	 */
	@SuppressWarnings("unchecked")
	protected void notifyListener(Event<?, ?> event, EventContext context, EventListenerRegistration registration)
			throws EventException {
		/*
		 * If synchronous notification
		 */
		if (registration.getNotificationType() == EventNotificationType.SYNCHRONOUS) {
			notifySynchronousListener(event, (EventListener<Event<?, ?>>) registration.getListener(), context);
		}
		/*
		 * Otherwise asynchronous
		 */
		else {
			notifyAsynchronousListener(event, (EventListener<Event<?, ?>>) registration.getListener(), context);
		}
	}

	/**
	 * Register with default options
	 */
	public void registerListener(Object eventType, EventListener<?> listener) {
		/*
		 * Register with default options
		 */
		registerListener(eventType,
				new EventListenerRegistration(listener, DEFAULT_NOTIFICATION_TYPE, DEFAULT_LISTENER_PRIORITY));
	}

	/**
	 * Register with the given options
	 */
	public void registerListener(Object eventType, EventListener<?> listener, EventNotificationType notificationType) {
		/*
		 * Register with the given options
		 */
		registerListener(eventType,
				new EventListenerRegistration(listener, notificationType, DEFAULT_LISTENER_PRIORITY));
	}

	/**
	 * Register with the given options
	 */
	public void registerListener(Object eventType, EventListener<?> listener, EventNotificationType notificationType,
			EventListenerPriority listenerPriority) {
		/*
		 * Register with the given options
		 */
		registerListener(eventType, new EventListenerRegistration(listener, notificationType, listenerPriority));
	}

	/**
	 * Register listener
	 */
	public synchronized void registerListener(Object eventType, EventListenerRegistration registration) {

		LOGGER.info("Registering listener: {} for event type: {} , notification type: {} , listener priority: {} ",
				new Object[] { registration.getListener(), eventType, registration.getNotificationType(),
						registration.getListenerPriority() });

		/*
		 * If priority is HIGH add listener at the beginning of the list
		 */
		if (EventListenerPriority.HIGH == registration.getListenerPriority()) {
			getEventListenerList(eventType).add(0, registration);
		}
		/*
		 * Otherwise add listener at the end of the list
		 */
		else {
			getEventListenerList(eventType).add(registration);
		}
	}

	/**
	 * Register listeners presented by map.
	 * 
	 * @param listenerMap event listener map , where key is event type, value is the
	 *                    instance of <br>
	 *                    <ul>
	 *                    <li>EventListener (will be registered with default
	 *                    options)</li>
	 *                    <li>EventListenerRegistration (will be registered with the
	 *                    given options)</li>
	 *                    <li>List of either above</li>
	 *                    </ul>
	 */
	public void registerListener(Map<Object, Object> listenerMap) {

		LOGGER.info("Registering listener map , size of: {}", listenerMap.size());

		/*
		 * Iterate input listener map
		 */
		for (Map.Entry<Object, Object> entry : listenerMap.entrySet()) {

			Object listenerObj = entry.getValue();

			/*
			 * If list - iterate and process each entry
			 */
			if (listenerObj instanceof List) {
				for (Object listener : (List<?>) listenerObj) {
					registerListenerObject(entry.getKey(), listener);
				}
			}
			/*
			 * Otherwise just register listener object
			 */
			else {
				registerListenerObject(entry.getKey(), listenerObj);
			}
		}
	}

	/**
	 * Method accepts listener registration or listener directly, in which case it
	 * registers listener with the default options
	 * 
	 * @param eventType   event type
	 * @param listenerObj either listener registration object or listener object
	 * @throws IllegalArgumentException if unsupported listener type is passed
	 */
	protected void registerListenerObject(Object eventType, Object listenerObj) {

		/*
		 * If event listener, than register with default options
		 */
		if (listenerObj instanceof EventListener) {
			registerListener(eventType, new EventListenerRegistration((EventListener<?>) listenerObj,
					DEFAULT_NOTIFICATION_TYPE, DEFAULT_LISTENER_PRIORITY));
		}
		/*
		 * If listener registration - use options specified
		 */
		else if (listenerObj instanceof EventListenerRegistration) {
			registerListener(eventType, (EventListenerRegistration) listenerObj);
		}
		/*
		 * Otherwise - throw exception as listener type is not supported
		 */
		else {
			String message = "Passed listener object type is not supported: {} " + listenerObj.getClass().getName();
			LOGGER.warn(message);
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Unregister listener
	 */
	public synchronized void unregisterListener(Object eventType, EventListener<?> listener) {

		/*
		 * Remove listener from the list of registered listeners for particular event
		 * type
		 */
		getEventListenerList(eventType).remove(new EventListenerRegistration(listener, DEFAULT_NOTIFICATION_TYPE));
	}

	/**
	 * Get list of listeners for particular event type
	 * 
	 * @param eventType event type
	 * @return list of listener registration objects
	 */
	protected synchronized List<EventListenerRegistration> getEventListenerList(Object eventType) {

		if (eventListenerMap.containsKey(eventType)) {
			return eventListenerMap.get(eventType);
		} else {
			List<EventListenerRegistration> list = new ArrayList<EventListenerRegistration>();
			eventListenerMap.put(eventType, list);
			return list;
		}
	}

	/**
	 * Execute synchronous notification
	 * 
	 * @param event
	 * @param listener
	 * @param context
	 * @throws EventException
	 */
	protected void notifySynchronousListener(Event<?, ?> event, EventListener<Event<?, ?>> listener,
			EventContext context) throws EventException {
		listener.onEvent(event, context);
	}

	/**
	 * Execute asynchronous notification
	 * 
	 * @param event
	 * @param listener
	 * @param context
	 */
	protected void notifyAsynchronousListener(Event<?, ?> event, EventListener<Event<?, ?>> listener,
			EventContext context) {
		executorService.execute(new EventExecutor(event, listener, context));
	}

	/**
	 * Event executor class used for asynchronous listener notification
	 * 
	 */
	protected static class EventExecutor extends Thread {
		Event<?, ?> event;
		EventListener<Event<?, ?>> listener;
		EventContext context;

		/**
		 * Create new instance
		 * 
		 * @param event    event object
		 * @param listener listener instance
		 * @param context  event notification context
		 */
		protected EventExecutor(Event<?, ?> event, EventListener<Event<?, ?>> listener, EventContext context) {
			this.event = event;
			this.listener = listener;
			this.context = context;
		}

		public void run() {
			try {
				listener.onEvent(event, context);
			} catch (Exception e) {
				LOGGER.error("Error happened while notifying event listener " + listener.toString() + " about event "
						+ event.toString() + "As listener is asynchronous this error is ignored", e);
			}
		}
	}

}
