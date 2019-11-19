package com.js.event;

public class RefreshDataEvent extends BaseEvent<String, String[]> {

	public RefreshDataEvent(String... data) {
		super(RefreshDataEvent.class.getName(), data);
	}

}
