package com.zapedudas.chip.Tile;

public class Water extends Tile {
	private String IMAGEPATH = "water.png";
	
	public Water(int x, int y) {
		super(x, y);
	}

	@Override
	public String getCurrentImagePath() {
		return IMAGEPATH;
	}

	@Override
	public void sendTileProperty(String property) {
	}
}
