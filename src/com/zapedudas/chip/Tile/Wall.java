package com.zapedudas.chip.Tile;


public class Wall extends Tile {
	final String IMAGEPATH = "wall.png";
	
	public Wall(int x, int y) {
		super(x, y);
	}

	@Override
	public String getCurrentImagePath() {
		return this.IMAGEPATH;
	}

	@Override
	public void sendTileProperty(String property) {
	}

}
