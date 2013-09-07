package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.BugDriver;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public class Bug extends Unit<BugDriver> {
	private final String IMAGEPATH_UP = "bug_up.png";
	private final String IMAGEPATH_DOWN = "bug_down.png";
	private final String IMAGEPATH_LEFT = "bug_left.png";
	private final String IMAGEPATH_RIGHT = "bug_right.png";
	
	public Bug(int X, int Y) {
		super(X, Y);
	}

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
	public BugDriver constructDriver(Map map, MessageDispatcher messageDispatcher) {
		return new BugDriver(this, map, messageDispatcher);
	}
}
