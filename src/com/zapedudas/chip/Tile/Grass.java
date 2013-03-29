package com.zapedudas.chip.Tile;

public class Grass extends Tile {
	private String IMAGEPATH = "grass.png";
	
	public Grass(int x, int y) {
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
