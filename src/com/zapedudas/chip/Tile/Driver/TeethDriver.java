package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Player;
import com.zapedudas.chip.Tile.Unit.Teeth;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public class TeethDriver extends NPCDriver<Teeth> {

	public TeethDriver(Teeth unit, Map map, MessageDispatcher messageDispatcher) {
		super(unit, map, messageDispatcher);
	}

	@Override
	protected int getTicksBetweenMoves() {
		return 3;
	}

	@Override
	protected void doAction() {
		Player player = (Player)getClosestTileOfType(Player.class);
		
		if (player == null) return;
		
		// Look at tiles up, down, left and right of current unit, 
		//  looking for which future position would lead us closer to our target
		
		// Default is UP
		Directions closestDirection = Directions.UP;
		double closestDistance = distanceToTileFromCoords(player, unit.getX(), unit.getY() - 1);
		
		// RIGHT
		double distance_right = distanceToTileFromCoords(player, unit.getX() + 1, unit.getY());
		if ( distance_right < closestDistance ) {
			closestDirection = Directions.RIGHT;
			closestDistance = distance_right;
		}
		
		// DOWN
		double distance_down = distanceToTileFromCoords(player, unit.getX(), unit.getY() + 1);
		if ( distance_down < closestDistance ) {
			closestDirection = Directions.DOWN;
			closestDistance = distance_down;
		}
		
		// LEFT
		double distance_left = distanceToTileFromCoords(player, unit.getX() - 1, unit.getY());
		if ( distance_left < closestDistance ) {
			closestDirection = Directions.LEFT;
			closestDistance = distance_left;
		}
		
		if (!tryMoveInDirection(closestDirection)) {
			unit.setDirection(closestDirection);
		}
	}
}
