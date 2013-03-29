package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
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
}
