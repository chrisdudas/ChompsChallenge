package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.DrivableUnit;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public class Teeth extends DrivableUnit {
	private final String IMAGEPATH_UP = "teeth_up.png";
	private final String IMAGEPATH_DOWN = "teeth_down.png";
	private final String IMAGEPATH_LEFT = "teeth_left.png";
	private final String IMAGEPATH_RIGHT = "teeth_right.png";
	
	public Teeth(int x, int y, Map map,
			MessageDispatcher messageDispatch) {
		super(x, y, map, messageDispatch);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getCurrentImagePath() {
		String imagePath = super.getCurrentImagePath();
		if (imagePath != null) return imagePath;
		
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
		double closestDistance = distanceToTileFromCoords(player, this.getX(), this.getY() - 1);
		
		// RIGHT
		double distance_right = distanceToTileFromCoords(player, this.getX() + 1, this.getY());
		if ( distance_right < closestDistance ) {
			closestDirection = Directions.RIGHT;
			closestDistance = distance_right;
		}
		
		// DOWN
		double distance_down = distanceToTileFromCoords(player, this.getX(), this.getY() + 1);
		if ( distance_down < closestDistance ) {
			closestDirection = Directions.DOWN;
			closestDistance = distance_down;
		}
		
		// LEFT
		double distance_left = distanceToTileFromCoords(player, this.getX() - 1, this.getY());
		if ( distance_left < closestDistance ) {
			closestDirection = Directions.LEFT;
			closestDistance = distance_left;
		}
		
		if (!tryMoveInDirection(closestDirection)) {
			this.setDirection(closestDirection);
		}
	}
}
