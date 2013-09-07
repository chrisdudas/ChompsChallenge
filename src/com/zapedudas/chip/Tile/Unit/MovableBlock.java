package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Driver.MovableBlockDriver;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public class MovableBlock extends Unit<MovableBlockDriver> {
	public enum MovableBlockState {
		/** Default state; the block should be able to be pushed around **/
		NORMAL,
		/** When the block has been submerged in water, but HAS NOT been walked on yet **/
		SUBMERGED,
		/** When the block has been submerged in water and HAS been walked on **/
		TRAMPLED
	}

	private MovableBlockState movableBlockState = MovableBlockState.NORMAL;
	
	public MovableBlock(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MovableBlockDriver constructDriver(Map map,
			MessageDispatcher messageDispatcher) {
		return new MovableBlockDriver(this, map, messageDispatcher);
	}	
	@Override
	public String getCurrentImagePath() {
		switch (movableBlockState) {
		case NORMAL:
			return "block.png";
		case SUBMERGED:
			return "mud.png";
		case TRAMPLED:
			return "grass.png";
		default:
			return "";
		}
	}

	public MovableBlockState getMovableBlockState() {
		return movableBlockState;
	}

	public void setMovableBlockState(MovableBlockState movableBlockState) {
		this.movableBlockState = movableBlockState;
	}
}
