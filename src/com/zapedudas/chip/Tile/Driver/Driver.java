package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Tile.Grass;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class Driver {
	protected Unit unit;
	protected Map map;
	
	protected int unit_x;
	protected int unit_y;

	public Driver(Unit unit, Map map, int start_x, int start_y) {
		this.unit = unit;
		this.map = map;
		this.unit_x = start_x;
		this.unit_y = start_y;
	}
	
	protected boolean tryMoveInDirection(Directions direction) {
		switch (direction) {
			case UP:
				if (checkIfTileFree(unit_x, unit_y - 1)) {
					map.moveUnitTileFromCoordsToCoords(unit_x, unit_y, unit_x, unit_y - 1);
					this.unit_y = unit_y - 1;
					unit.setY(unit_y);
					unit.setDirection(Directions.UP);
					return true;
				}
				break;
			case DOWN:
				if (checkIfTileFree(unit_x, unit_y + 1)) {
					map.moveUnitTileFromCoordsToCoords(unit_x, unit_y, unit_x, unit_y + 1);
					this.unit_y = unit_y + 1;
					unit.setY(unit_y);
					unit.setDirection(Directions.DOWN);
					return true;
				}
				break;
			case LEFT:
				if (checkIfTileFree(unit_x - 1, unit_y)) {
					map.moveUnitTileFromCoordsToCoords(unit_x, unit_y, unit_x - 1, unit_y);
					this.unit_x = unit_x - 1;
					unit.setX(unit_x);
					unit.setDirection(Directions.LEFT);
					return true;
				}
				break;
			case RIGHT:
				if (checkIfTileFree(unit_x + 1, unit_y)) {
					map.moveUnitTileFromCoordsToCoords(unit_x, unit_y, unit_x + 1, unit_y);
					this.unit_x = unit_x + 1;
					unit.setX(unit_x);
					unit.setDirection(Directions.RIGHT);
					return true;
				}
				break;
		}
		
		return false;
	}
	
	protected boolean checkIfTileFree(int x, int y) {
		if (x < 0 || x >= map.getWidth()) return false;
		if (y < 0 || y >= map.getHeight()) return false;
		
		Tile floor = map.getFloorAt(x, y);
		boolean floorIsOfGrass = floor instanceof Grass;
		
		Tile unitAtTarget = map.getUnitAt(x, y);
		
		if (!floorIsOfGrass) return false;
		if (unitAtTarget != null) return false;
		
		return true;
	}
}
