package com.zapedudas.chip.Tile;

import android.util.SparseArray;

import com.zapedudas.chip.Tile.Unit.Bug;

public class TileMapping {
	public static SparseArray<Class<?>> tileMap;
	
	static {
		tileMap = new SparseArray<Class<?>>();
		
		tileMap.put(1, Grass.class);
		tileMap.put(16, Bug.class);
	}
}
