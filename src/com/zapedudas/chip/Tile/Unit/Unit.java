package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Tile;

public abstract class Unit extends Tile{
	private final String IMAGEPATH_DYING = "tombstone.png";
	private final String IMAGEPATH_DROWNING = "water_splash.png";
	private final String IMAGEPATH_BURNING = "tombstone.png";
	
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
	
	public UnitState getUnitState() {
		return unitState;
	}
	
	public void setUnitState(UnitState unitState) {
		this.unitState = unitState;
	}
}
