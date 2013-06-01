package com.zapedudas.chip.Tile.Item;

public class Bone extends Item {
	private String IMAGEPATH = "bone.png";

	public Bone(int x, int y) {
		super(x, y);
	}

	@Override
	public String getCurrentImagePath() {  
		return IMAGEPATH;
	}

}
