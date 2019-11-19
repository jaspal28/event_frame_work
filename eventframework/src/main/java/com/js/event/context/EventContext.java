package com.js.event.context;

public interface EventContext {

	/**
	 * Get some data from the context
	 * 
	 * @param key data key
	 * @return data object
	 */
	public Object getProperty(Object key);

	/**
	 * Put some data into the context
	 * 
	 * @param key   data key
	 * @param value data value
	 */
	public void setProperty(Object key, Object value);

	/**
	 * Remove data from the context
	 * 
	 * @param key data key
	 */
	public void removeProperty(Object key);
}
