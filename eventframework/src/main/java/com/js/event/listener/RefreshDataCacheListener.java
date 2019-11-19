package com.js.event.listener;

import com.js.event.BaseEvent;
import com.js.event.context.EventContext;
import com.js.event.exception.EventException;

public class RefreshDataCacheListener implements EventListener<BaseEvent<String, String[]>> {

	public void onEvent(BaseEvent<String, String[]> event, EventContext context) throws EventException {
		System.out.println("RefreshDataCacheListener handling event of type " + event.getEventType());
		System.out.println("Event Data");
		for (String data : event.getEventData()) {
			System.out.print(data + "  :::  ");
		}
		System.out.println("");
	}

}
