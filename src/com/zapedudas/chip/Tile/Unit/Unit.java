package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Driver.Driver;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public abstract class Unit<DriverType extends Driver<?>> extends Tile{
	private final String IMAGEPATH_DYING = "tombstone.png";
	private final String IMAGEPATH_DROWNING = "water_splash.png";
	private final String IMAGEPATH_BURNING = "tombstone.png";
	
	private DriverType driver;
	protected UnitState unitState;
	
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
		if (isAnimatingMovement() && getAnimationPercent() < 0.5) return null;
		else if (this.unitState == UnitState.DYING) return IMAGEPATH_DYING;
		else if (this.unitState == UnitState.DROWNING) return IMAGEPATH_DROWNING;
		else if (this.unitState == UnitState.BURNING) return IMAGEPATH_BURNING;
		else return null;
	}
	
	/**
	 * Move this unit 1 space in the given direction, animating the movement
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
		
		beginAnimation(direction);
	}
		
	/**
	 * This is simply move the unit to the given co-ords
	 */
	public void moveTo(int x, int y) {
		this.setCoords(x, y);
	}
	
	@Override
	public void sendTileProperty(String property) {
		if (property.equals("UP")) setDirection(Directions.UP);
		else if (property.equals("DOWN")) setDirection(Directions.DOWN);
		else if (property.equals("LEFT")) setDirection(Directions.LEFT);
		else if (property.equals("RIGHT")) setDirection(Directions.RIGHT);
	}
	
	@Override
	protected AnimationMode getAnimationMode() {
		return AnimationMode.MOVEMENT;
	}
	
	public void setupDriver(Map map, MessageDispatcher messageDispatcher) {
		DriverType driver = constructDriver(map, messageDispatcher);
		this.driver = driver;
	}
	
	protected abstract DriverType constructDriver(Map map, MessageDispatcher messageDispatcher);
	
//	public void attachDriver(Driver<?> driver) {
//		this.driver = driver;
//	}

	public DriverType getDriver() {
		return this.driver;
	}
	
	public UnitState getUnitState() {
		return unitState;
	}
	
	public void setUnitState(UnitState unitState) {
		this.unitState = unitState;
	}
}
