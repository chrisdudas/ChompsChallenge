package com.zapedudas.chip.map;

import java.util.ArrayList;

public class MessageDispatcher {
	private ArrayList<Message> messages;
	
	public MessageDispatcher() {
		this.messages = new ArrayList<Message>();
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public Message[] getMessages() {
		return this.messages.toArray(new Message[this.messages.size()]);
	}
	
	public void clearMessages() {
		this.messages.clear();
	}
}
