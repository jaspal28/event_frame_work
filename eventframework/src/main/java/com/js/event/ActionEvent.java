package com.js.event;

public class ActionEvent extends BaseEvent<String, String[]> {

	public ActionEvent(String... data) {
		super(ActionEvent.class.getName(), data);
	}

}