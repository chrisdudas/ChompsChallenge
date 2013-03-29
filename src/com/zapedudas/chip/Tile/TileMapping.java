package com.zapedudas.chip.Tile;

import android.util.SparseArray;

import com.zapedudas.chip.Tile.Driver.Driver;
import com.zapedudas.chip.Tile.Unit.Bug;

public class TileMapping {
	private static SparseArray<Class<?>> tileMap;
	
	static {
		tileMap = new SparseArray<Class<?>>();
		
		tileMap.put(0, Wall.class);
		tileMap.put(1, Grass.class);
		
		tileMap.put(16, Bug.class);
	}
	
	public static Class<?> getClassFromID(int tileID) {
		return tileMap.get(tileID);
	}
}
