package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.BugDriver;
import com.zapedudas.chip.Tile.Driver.Driver;

public class Bug extends Unit {
	private final String IMAGEPATH_UP = "bug_up.png";
	private final String IMAGEPATH_DOWN = "bug_down.png";
	private final String IMAGEPATH_LEFT = "bug_left.png";
	private final String IMAGEPATH_RIGHT = "bug_right.png";
	
	public Bug(int X, int Y) {
		super(X, Y);
		this.setDirection(Directions.DOWN);
	}

	@Override
	public String getCurrentImagePath() {
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
}
