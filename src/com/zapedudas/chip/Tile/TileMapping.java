package com.zapedudas.chip.Tile;

import android.util.SparseArray;

import com.zapedudas.chip.Tile.Item.WaterBoots;
import com.zapedudas.chip.Tile.Unit.*;

public class TileMapping {
	private static SparseArray<Class<?>> tileMap;
	
	static {
		tileMap = new SparseArray<Class<?>>();
		
		tileMap.put(0, Wall.class);
		tileMap.put(1, Grass.class);
		tileMap.put(2, Water.class);
		
		tileMap.put(15, WaterBoots.class);
		
		tileMap.put(100, Player.class);
		
		tileMap.put(16, Bug.class);
		tileMap.put(17, Teeth.class);   
		
		tileMap.put(200, MovableBlock.class);  
	}
	
	public static Class<?> getClassFromID(int tileID) {
		return tileMap.get(tileID);
	}
}
