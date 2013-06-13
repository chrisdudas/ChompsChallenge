package com.zapedudas.chip.Tile.Item;

import com.zapedudas.chip.Tile.Tile;

public abstract class Item extends Tile {

	public Item(int x, int y) {
		super(x, y);
	}

	@Override
	public void sendTileProperty(String property) {
	}
	
	public abstract String getInventoryIcon();
}
