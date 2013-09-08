package com.zapedudas.chip.map;

import com.zapedudas.chip.Tile.Grass;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Wall;
import com.zapedudas.chip.Tile.Water;
import com.zapedudas.chip.Tile.Item.WaterBoots;
import com.zapedudas.chip.Tile.Unit.Bug;
import com.zapedudas.chip.Tile.Unit.MovableBlock;
import com.zapedudas.chip.Tile.Unit.Player;
import com.zapedudas.chip.Tile.Unit.Teeth;

public class TileSpawner {
	public static Tile spawnTile(int tileID, int x, int y, Map map, MessageDispatcher messageDispatch) {
		switch (tileID) {
		case 0:
			return new Wall(x, y);
		case 1:
			return new Grass(x, y);
		case 2:
			return new Water(x, y);
		case 15:
			return new WaterBoots(x, y);
			
		case 100:
			return new Player(x, y, map, messageDispatch);
			
		case 16:
			return new Bug(x, y, map, messageDispatch);
		case 17:
			return new Teeth(x, y, map, messageDispatch);
		case 200:
			return new MovableBlock(x, y, map, messageDispatch);
			
		default:
			return null;
		}
	}
}
