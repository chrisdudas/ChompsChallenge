package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Tile.Unit.Unit;

public class NPCDriver extends Driver {
	protected int ticksSinceLastMovement;
	
	public NPCDriver(Unit unit, Map map, int unit_x, int unit_y) {
		super(unit, map, unit_x, unit_y);
	}
	
	public void tick() {
		ticksSinceLastMovement++;		
	}
}
