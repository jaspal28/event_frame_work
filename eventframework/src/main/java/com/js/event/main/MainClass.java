package com.js.event.main;

import com.js.event.ActionEvent;
import com.js.event.RefreshDataEvent;
import com.js.event.exception.EventException;
import com.js.event.listener.ActionEventListener;
import com.js.event.listener.RefreshDataCacheListener;
import com.js.event.manager.EventManagerImpl;

public class MainClass {

	private EventManagerImpl eventManagerImpl = null;

	public MainClass() {
		eventManagerImpl = new EventManagerImpl(5);
	}

	public static void main(String[] args) {
		MainClass mainClass = new MainClass();
		mainClass.registerEvents();
		mainClass.callListeners();

	}

	public void registerEvents() {
		eventManagerImpl.registerListener(new ActionEvent().getEventType(), new ActionEventListener());
		eventManagerImpl.registerListener(new RefreshDataEvent().getEventType(), new RefreshDataCacheListener());
	}

	public void callListeners() {
		ActionEvent actionEvent = new ActionEvent(new String[] { "Add", "Delete" });
		try {
			eventManagerImpl.fireEvent(actionEvent);
		} catch (EventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RefreshDataEvent refreshData = new RefreshDataEvent(new String[] { "Data1", "Data2" });
		try {
			eventManagerImpl.fireEvent(refreshData);
		} catch (EventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
