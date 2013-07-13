package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.MovableBlockDriver;

public class MovableBlock extends Unit {

	public MovableBlock(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<?> getUnitDriverType() {
		// TODO Auto-generated method stub
		return MovableBlockDriver.class;
	}
	
	@Override
	public String getCurrentImagePath() {
		return "block.png";
	}
}
