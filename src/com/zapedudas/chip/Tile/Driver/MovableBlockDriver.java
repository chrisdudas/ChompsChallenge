package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Tile.Unit.Unit;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;

public class MovableBlockDriver extends Driver {

	public MovableBlockDriver(Unit unit, Map map,
			MessageDispatcher messageDispatch) {
		super(unit, map, messageDispatch);
	}

	@Override
	protected int getTicksBetweenMoves() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	protected void doAction() {
		// Movable blocks take no action on their own
	}
}
