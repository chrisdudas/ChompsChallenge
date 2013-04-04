package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Tile.Grass;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class BugDriver extends NPCDriver {
	private final int TICKS_TILL_MOVE = 3;
	
	public BugDriver(Unit unit, Map map, int unit_x, int unit_y) {
		super(unit, map, unit_x, unit_y);
	}
	
	@Override
	public void tick() {
		super.tick();
						
		if (ticksSinceLastMovement % TICKS_TILL_MOVE == 0) {
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
}
