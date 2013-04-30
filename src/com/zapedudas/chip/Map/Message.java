package com.zapedudas.chip.Map;

import com.zapedudas.chip.Tile.Tile;

public class Message {
	private MessageType messageType;
	private Tile subject;
	
	public enum MessageType {
		/* When subject tile should die */
		UNIT_KILLED,
		/* !! should be for when a unit should be created */
		UNIT_SPAWNED,
		/* When an item has been collected by a unit */
		ITEM_COLLECTED,
		
		PLAYER_HAS_DIED
	}
	
	public Message(MessageType messageType, Tile subject) {
		this.messageType = messageType;
		this.subject = subject;
	}
	
	public Message(MessageType messageType) {
		this.messageType = messageType;
	}
	
	public MessageType getMessageType() {
		return this.messageType;
	}
	
	public Tile getSubject() {
		return this.subject;
	}
}
