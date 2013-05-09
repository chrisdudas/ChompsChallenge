package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.TeethDriver;

public class Teeth extends Unit {
	private final String IMAGEPATH_UP = "teeth_up.png";
	private final String IMAGEPATH_DOWN = "teeth_down.png";
	private final String IMAGEPATH_LEFT = "teeth_left.png";
	private final String IMAGEPATH_RIGHT = "teeth_right.png";
	
	public Teeth(int x, int y) {
		super(x, y);
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
	public Class<?> getUnitDriverType() {
		return TeethDriver.class;
	}
}
