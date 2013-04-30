package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Map.MessageDispatcher;
import com.zapedudas.chip.Tile.Unit.Unit;

public abstract class NPCDriver extends Driver {
	protected int ticksSinceLastMovement;
	
	public NPCDriver(Unit unit, Map map, MessageDispatcher messageDispatcher) {
		super(unit, map, messageDispatcher);
	}
	
	public final void tick() {
		ticksSinceLastMovement++;
		if (ticksSinceLastMovement % ticksBetweenMoves() == 0) doAction();
	}
	
	/**
	 * This method is queried for how many tick calls should occur until doAction is called
	 * @return The number of ticks between doAction calls
	 */
	protected abstract int ticksBetweenMoves();
	
	/**
	 * Action taken upon units should occur here
	 */
	protected abstract void doAction();
}
