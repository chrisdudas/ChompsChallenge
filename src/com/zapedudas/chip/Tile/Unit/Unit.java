package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Driver.Driver;

public abstract class Unit extends Tile{
	private final String IMAGEPATH_DYING = "tombstone.png";
	private final String IMAGEPATH_DROWNING = "water_splash.png";
	private final String IMAGEPATH_BURNING = "tombstone.png";
	
	Driver driver;
	UnitState unitState;
	
	public enum UnitState {
		ALIVE,
		DYING,
		DROWNING,
		BURNING
	}
	
	public Unit(int x, int y) {
		super(x, y);
		this.setDirection(Directions.DOWN);
		this.unitState = UnitState.ALIVE;
	}
	
	@Override
	public String getCurrentImagePath() {
		switch (this.unitState) {
			case DYING:
				return IMAGEPATH_DYING;
			case DROWNING:
				return IMAGEPATH_DROWNING;
			case BURNING:
				return IMAGEPATH_BURNING;
			default:
				return null;
		}
	}
	
	/**
	 * Move this unit 1 space in the given direction
	 */
	public void move(Directions direction) {
		switch (direction) {
			case UP:
				this.setY(this.getY() - 1);
				break;
			case DOWN:
				this.setY(this.getY() + 1);
				break;
			case LEFT:
				this.setX(this.getX() - 1);
				break;
			case RIGHT:
				this.setX(this.getX() + 1);
				break;
		}
	}

	/**
	 * Move this unit to another unit. This is effectively saying jump this unit to given unit's location
	 */
	public void moveTo(Tile tile) {
		moveTo(tile.getX(), tile.getY());
	}
		
	/**
	 * This is simply move the unit to the given coords
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
	
	/**
	 * Get the driver that should drive this unit as a class
	 * @return The Class<?> of the unit's driver
	 */
	public abstract Class<?> getUnitDriverType();
	
	public void attachDriver(Driver driver) {
		this.driver = driver;
	}

	public Driver getDriver() {
		return this.driver;
	}
	
	public UnitState getUnitState() {
		return unitState;
	}
	
	public void setUnitState(UnitState unitState) {
		this.unitState = unitState;
	}
}
