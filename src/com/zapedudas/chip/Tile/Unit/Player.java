package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Water;
import com.zapedudas.chip.Tile.Driver.LocalPlayerDriver;

public class Player extends Unit {
	final String IMAGEPATH_UP = "dog_up.png";
	final String IMAGEPATH_DOWN = "dog_down.png";
	final String IMAGEPATH_LEFT = "dog_left.png";
	final String IMAGEPATH_RIGHT = "dog_right.png";
	
	public Player(int X, int Y) {
		super(X, Y);
	}	
	
	@Override
	public String getCurrentImagePath() {
		String imagePath = super.getCurrentImagePath();
		if (imagePath != null) return imagePath;		
		
		if (Water.class == driver.getCurrentGroundTileType()) {
			switch (this.getDirection()) {
				case UP:
					return "dog_up_swimming.png";
				case DOWN:
					return "dog_down_swimming.png";
				case LEFT:
					return "dog_left_swimming.png";
				case RIGHT:
					return "dog_right_swimming.png";
				default:
					return null;
			}		
		}
		else {
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
	
	@Override
	public Class<?> getUnitDriverType() {
		// The player/s's driver setup is handled in the map
		return LocalPlayerDriver.class;
	}
}
