package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.Message;
import com.zapedudas.chip.map.MessageDispatcher;
import com.zapedudas.chip.map.Message.MessageType;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class LocalPlayerDriver extends Driver {
	private PlayerInventory inventory;
	
	public LocalPlayerDriver(Unit unit, Map map, MessageDispatcher messageDispatcher) {
		super(unit, map, messageDispatcher);
		
		inventory = new PlayerInventory();
	}

	@Override
	protected int getDyingDuration() {
		return 6;
	}
	
	public void move(Directions direction) {
		if (!tryMoveInDirection(direction)) {
			this.unit.setDirection(direction);
		}
	}

	@Override
	public void stopDriver() {
		super.stopDriver();
		messageDispatcher.addMessage(new Message(MessageType.PLAYER_HAS_DIED));
	}
	

	@Override
	protected int getTicksBetweenMoves() {
		return 1;
	}

	@Override
	protected void doAction() {
	}
	
	public PlayerInventory getInventory() {
		return inventory;
	}
}
