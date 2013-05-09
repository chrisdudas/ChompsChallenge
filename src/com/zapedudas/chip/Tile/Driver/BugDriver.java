package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class BugDriver extends NPCDriver {
	public BugDriver(Unit unit, Map map, MessageDispatcher messageDispatcher) {
		super(unit, map, messageDispatcher);
	}
	
	@Override
	protected int getTicksBetweenMoves() {
		return 3;
	}

	@Override
	protected void doAction() {
		Directions unitDirection = unit.getDirection();
		
		Directions[] tryThese;
		
		if (unitDirection == Directions.UP) {
			tryThese = new Directions[] { Directions.LEFT, Directions.UP, Directions.RIGHT, Directions.DOWN }; 
		}
		else if (unitDirection == Directions.LEFT) {
			tryThese = new Directions[] { Directions.DOWN, Directions.LEFT, Directions.UP, Directions.RIGHT };
		}
		else if (unitDirection == Directions.DOWN) { 
			tryThese = new Directions[] { Directions.RIGHT, Directions.DOWN, Directions.LEFT, Directions.UP };
		}
		else if (unitDirection == Directions.RIGHT) {
			tryThese = new Directions[] { Directions.UP, Directions.RIGHT, Directions.DOWN, Directions.LEFT };
		}
		else tryThese = new Directions[0];
		
		for (Directions direction : tryThese) {
			if (tryMoveInDirection(direction)) break;
		}
	}
}
