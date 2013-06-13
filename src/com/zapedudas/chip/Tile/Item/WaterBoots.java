package com.zapedudas.chip.Tile.Item;

public class WaterBoots extends Item {
	private final String IMAGEPATH = "flippers.png";
	
	public WaterBoots(int x, int y) {
		super(x, y);
	}

	@Override
	public String getCurrentImagePath() {
		return IMAGEPATH;
	}

	@Override
	public String getInventoryIcon() {
		return "flippers.png";
	}

}
