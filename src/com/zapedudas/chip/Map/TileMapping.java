package com.zapedudas.chip.Map;

import java.util.HashMap;

import com.zapedudas.chip.Tile.Unit.Bug;

public class TileMapping {
	public static HashMap<Integer, Class<?>> tileMap;
	
	static {
		tileMap.put(16, Bug.class);
	}
}
