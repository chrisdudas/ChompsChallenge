package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Map.Message;
import com.zapedudas.chip.Map.Message.MessageType;
import com.zapedudas.chip.Map.MessageDispatcher;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class LocalPlayerDriver extends Driver {
 
	public LocalPlayerDriver(Unit unit, Map map, MessageDispatcher messageDispatcher) {
		super(unit, map, messageDispatcher); //, start_x, start_y);
	}

	public void move(Directions direction) {
		if (!tryMoveInDirection(direction)) {
			this.unit.setDirection(direction);
		}
	}

	@Override
	public void killUnit() {
//		map.setUnitAt(this.unit_x, this.unit_y, null);
		messageDispatcher.addMessage(new Message(MessageType.PLAYER_HAS_DIED));
	}
}
