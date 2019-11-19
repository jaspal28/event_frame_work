package com.js.event.context;

import java.util.HashMap;
import java.util.Map;

public class EventHashMapContext implements EventContext {

	/*
	 * Internal context store
	 */
	private Map<Object, Object> properties = new HashMap<Object, Object>();

	/**
	 * Put data into the context
	 */
	public void setProperty(Object key, Object value) {
		properties.put(key, value);
	}

	/**
	 * Get data from the context
	 */
	public Object getProperty(Object key) {
		return properties.get(key);
	}

	/**
	 * Remove data from the context
	 */
	public void removeProperty(Object key) {
		properties.remove(key);
	}

}
