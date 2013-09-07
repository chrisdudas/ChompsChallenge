package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MessageDispatcher;
import com.zapedudas.chip.Tile.Unit.Unit;

public abstract class NPCDriver<UnitType extends Unit> extends Driver<UnitType> {
	public NPCDriver(UnitType unit, Map map, MessageDispatcher messageDispatcher) {
		super(unit, map, messageDispatcher);
	}
	
//	@Override
//	public final void tick() {
//		ticksSinceStart++;
//		
//		if (driverStopped) return;
//		
//		if (unit.getUnitState() != UnitState.ALIVE) {
//			ticksSinceDeath++;
//			
//			if (ticksSinceDeath >= DYING_DURATION) {
//				// DESTROY UNIT INSTANCE 
//				this.map.getSquareAt(unit.getX(), unit.getY()).clearUnitTile();
//				
//				// `STOP` DRIVER INSTANCE
//				stopDriver();
//			}
//		}
//		else if (ticksSinceStart % ticksBetweenMoves() == 0) {
//			doAction();
//		}
//	}
}
