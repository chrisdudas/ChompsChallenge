package com.zapedudas.chip.Tile.Item;

public class Bone extends Item {
	private String IMAGEPATH = "grass.png";

	public Bone(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCurrentImagePath() {
		return IMAGEPATH;
	}

}
