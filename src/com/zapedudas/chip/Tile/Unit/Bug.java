package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.DrivableUnit;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public class Bug extends DrivableUnit {
	public Bug(int x, int y, Map map,
			MessageDispatcher messageDispatch) {
		super(x, y, map, messageDispatch);
	}

	private final String IMAGEPATH_UP = "bug_up.png";
	private final String IMAGEPATH_DOWN = "bug_down.png";
	private final String IMAGEPATH_LEFT = "bug_left.png";
	private final String IMAGEPATH_RIGHT = "bug_right.png";
	

	@Override
	public String getCurrentImagePath() {
		String imagePath = super.getCurrentImagePath();
		if (imagePath != null) return imagePath;
		
		switch (this.getDirection()) {
			case UP:
				return IMAGEPATH_UP;
			case DOWN:
				return IMAGEPATH_DOWN;
			case LEFT:
				return IMAGEPATH_LEFT;
			case RIGHT:
				return IMAGEPATH_RIGHT;
			default:
				return null;
		}	
	}

	@Override
	protected int getTicksBetweenMoves() {
		return 3;
	}

	@Override
	protected void doAction() {
		Directions unitDirection = this.getDirection();
		
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
