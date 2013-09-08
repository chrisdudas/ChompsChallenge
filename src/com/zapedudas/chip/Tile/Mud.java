package com.zapedudas.chip.Tile;

public class Mud extends Tile {
	private String IMAGEPATH = "mud.png";
	
	public Mud(int x, int y) {
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
