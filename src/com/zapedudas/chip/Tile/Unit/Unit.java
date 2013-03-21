package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Tile;

public abstract class Unit extends Tile{

	
	public Unit(int x, int y, Directions direction) {
		super(x, y);
	}
	
	/**
	 * Move this unit 1 space in the given direction
	 */
	public void move(Directions direction) {
		switch (direction) {
		case UP:
			this.setY(this.getY() - 1);
		case DOWN:
			this.setY(this.getY() + 1);
		case LEFT:
			this.setX(this.getX() - 1);
		case RIGHT:
			this.setX(this.getX() + 1);
		}
	}

	/**
	 * Move this unit to another unit. This is effectively saying jump this unit to given unit's location
	 */
	public void moveTo(Tile tile) {
		moveTo(tile.getX(), tile.getY());
	}
		
	/**
	 * This is simply move the unit to the given co-ords
	 */
	public void moveTo(int X, int Y) {
		this.setX(X);
		this.setY(Y);
	}
	
	@Override
	public void sendTileProperty(String property) {
		if (property.equals("UP")) setDirection(Directions.UP);
		else if (property.equals("DOWN")) setDirection(Directions.DOWN);
		else if (property.equals("LEFT")) setDirection(Directions.LEFT);
		else if (property.equals("RIGHT")) setDirection(Directions.RIGHT);
	}
}
