package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Tile.Grass;
import com.zapedudas.chip.Tile.Unit.MovableBlock;
import com.zapedudas.chip.Tile.Unit.MovableBlock.MovableBlockState;
import com.zapedudas.chip.Tile.Unit.Unit.UnitState;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MapSquare;
import com.zapedudas.chip.map.MessageDispatcher;

public class MovableBlockDriver extends Driver<MovableBlock> {
	public MovableBlockDriver(MovableBlock unit, Map map,
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
	}
	
	/** Should be called when the movable block has been pushed into water by the player **/
	public void blockSubmerged() {
		this.unit.setMovableBlockState(MovableBlockState.SUBMERGED);
	}
	
	/** Should be called when the movable block has been walked on top of by the player, after it has been submerged **/
	public void blockTrampled() {
		this.unit.setMovableBlockState(MovableBlockState.TRAMPLED);
		this.killUnit(UnitState.DYING);
		
		// TODO: ideally creation of units should not be handled by units themselves, probably the level itself should do this
		MapSquare mapSquare = this.map.getSquareAt(this.unit.getX(), this.unit.getY());
		mapSquare.setGroundTile(new Grass(this.unit.getX(), this.unit.getY()));
	}
}
