package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Map.MessageDispatcher;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Driver.CollisionManager.Result;
import com.zapedudas.chip.Tile.Unit.Unit;

public abstract class Driver {
	protected Unit unit;
	protected Map map;
	protected MessageDispatcher messageDispatcher;
	
	public Driver(Unit unit, Map map, MessageDispatcher messageDispatch) {
		this.unit = unit;
		this.map = map;
		this.messageDispatcher = messageDispatch;
		
		this.unit.attachDriver(this);
	}
	
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
		
		Tile[] tilesAtIntent = map.getTilesAt(intent_x, intent_y);
		
		for (int i = tilesAtIntent.length - 1; i >= 0; i--) {
			Tile tileAtNew = tilesAtIntent[i];
			
			if (tileAtNew == null) continue;
			
			CollisionManager.Result collisionResult = CollisionManager.handleCollision(this.unit, tileAtNew);
			
			if (collisionResult == Result.BLOCKED) {
				return false;
			}
			else if (collisionResult == Result.DIED) {
//				break !!!!;
				return false;
			}
		}
		
		map.moveUnitTileFromCoordsToCoords(this.unit.getX(), this.unit.getY(), intent_x, intent_y);

		unit.setX(intent_x);
		unit.setY(intent_y);
		unit.setDirection(direction);
		
		return true;
	}

	public abstract void killUnit();
}
