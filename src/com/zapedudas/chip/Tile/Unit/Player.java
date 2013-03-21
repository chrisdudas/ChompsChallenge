package com.zapedudas.chip.Tile.Unit;

public class Player extends Unit {
	final String IMAGEPATH_UP = "dog_up.png";
	final String IMAGEPATH_DOWN = "dog_down.png";
	final String IMAGEPATH_LEFT = "dog_left.png";
	final String IMAGEPATH_RIGHT = "dog_right.png";
	
	public Player(int X, int Y, Directions direction) {
		super(X, Y, direction);
	}	
	
	@Override
	public String getCurrentImagePath() {
		if (pos_direction == Directions.UP) return IMAGEPATH_UP;
		else if (pos_direction == Directions.DOWN) return IMAGEPATH_DOWN;
		else if (pos_direction == Directions.LEFT) return IMAGEPATH_LEFT;
		else if (pos_direction == Directions.RIGHT) return IMAGEPATH_RIGHT;
		return null;	
	}	

}
