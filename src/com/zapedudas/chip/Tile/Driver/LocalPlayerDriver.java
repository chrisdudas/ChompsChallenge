package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class LocalPlayerDriver extends Driver {

	public LocalPlayerDriver(Unit unit, Map map, int start_x, int start_y) {
		super(unit, map, start_x, start_y);
	}

	public void move(Directions direction) {
		tryMoveInDirection(direction);
	}
}
