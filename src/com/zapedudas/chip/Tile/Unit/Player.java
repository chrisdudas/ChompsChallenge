package com.zapedudas.chip.Tile.Unit;

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
