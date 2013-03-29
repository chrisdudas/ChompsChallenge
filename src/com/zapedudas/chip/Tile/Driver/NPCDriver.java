package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.Unit;

public class NPCDriver extends Driver {
	private double movement_speed = 0.333;
	private int ticksSinceLastMovement;
	
	public NPCDriver(Unit unit, Map map, int unit_x, int unit_y) {
		super(unit, map, unit_x, unit_y);
	}
	
	public void tick() {
		ticksSinceLastMovement++;
		
		if (ticksSinceLastMovement > 10) {
			Directions unitDirection = unit.getDirection();
			
			if (true) {//unitDirection == Directions.UP) {
				Tile unitatnew = map.getUnitAt(unit_x, unit_y + 1);
				
				if (unitatnew == null) {
					map.moveUnitTileFromCoordsToCoords(unit_x, unit_y, unit_x, unit_y + 1);
					this.unit_y = unit_y + 1;
				}
				else {
					// TODO: trigger collision
				}
			}
		}
	}
}
