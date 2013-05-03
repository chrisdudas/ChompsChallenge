package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.Message;
import com.zapedudas.chip.map.MessageDispatcher;
import com.zapedudas.chip.map.Message.MessageType;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Driver.CollisionManager.Result;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;
import com.zapedudas.chip.Tile.Unit.Unit.UnitState;

public abstract class Driver {
	protected Unit unit;
	protected Map map;
	protected MessageDispatcher messageDispatcher;
	
	protected int ticksSinceStart;
	
	protected boolean driverStopped;
	protected boolean unitKilled;
	protected int ticksSinceDeath;
	
	public Driver(Unit unit, Map map, MessageDispatcher messageDispatch) {
		this.unit = unit;
		this.map = map;
		this.messageDispatcher = messageDispatch;
		
		this.unitKilled = false;
		this.driverStopped = false;
		
		this.unit.attachDriver(this);
	}
	
	public void tick() {
		ticksSinceStart++;
		
		if (driverStopped) return;
		
		if (unit.getUnitState() != UnitState.ALIVE) {
			ticksSinceDeath++;
			
			if (ticksSinceDeath >= getDyingDuration()) {
				// DESTROY UNIT INSTANCE 
				this.map.getSquareAt(unit.getX(), unit.getY()).removeUnitTile(unit);
				
				// `STOP` DRIVER INSTANCE
				stopDriver();
			}
		}
		else if (ticksSinceStart % getTicksBetweenMoves() == 0) {
			doAction();
		}
	}
	
	protected int getDyingDuration() {
		return 3;
	}
	
	/**
	 * This method is queried for how many tick calls should occur until doAction is called
	 * @return The number of ticks between doAction calls
	 */
	protected abstract int getTicksBetweenMoves();
	
	/**
	 * Action taken upon units should occur here
	 */
	protected abstract void doAction();
	
	protected boolean tryMoveInDirection(Directions direction) {
		int intent_x = this.unit.getX();
		int intent_y = this.unit.getY();
		
		switch (direction) {
			case UP:
				intent_y -= 1;
				break;
			case DOWN:
				intent_y += 1 ;
				break;
			case LEFT:
				intent_x -= 1;
				break;
			case RIGHT:
				intent_x += 1;
				break;
		}
		
		if (intent_x < 0 || intent_x >= map.getWidth()) return false;
		if (intent_y < 0 || intent_y >= map.getHeight()) return false;
		
		Tile[] tilesAtIntent = map.getSquareAt(intent_x, intent_y).toArray();
		
		for (int i = tilesAtIntent.length - 1; i >= 0; i--) {
			Tile tileAtNew = tilesAtIntent[i];
			
			if (tileAtNew == null) continue;
			
			CollisionManager.Result collisionResult = CollisionManager.handleCollision(this.unit, tileAtNew);
			
			if (collisionResult == Result.BLOCKED) {
				return false;
			}
			else if (collisionResult == Result.DIED) {	
				break;
			}
		}
		
		map.getSquareAt(this.unit.getX(), this.unit.getY()).removeUnitTile(unit);
		map.getSquareAt(intent_x, intent_y).addUnitTile(this.unit);
		
		unit.setX(intent_x);
		unit.setY(intent_y);
		unit.setDirection(direction);
		
		return true;
	}

	public void killUnit(UnitState newState) {
		unitKilled = true;
		this.unit.setUnitState(newState);
		ticksSinceDeath = 0;
	}
	
	public void stopDriver() {
		driverStopped = true;
	}
	
	public boolean isDriverStopped() {
		return driverStopped;
	}
	
	public Class<?> getCurrentGroundTileType() {
		return map.getSquareAt(this.unit.getX(), this.unit.getY()).getGroundTile().getClass();
	}
}
