package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Grass;
import com.zapedudas.chip.Tile.Mud;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Driver.DrivableUnit;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MapSquare;
import com.zapedudas.chip.map.MessageDispatcher;

public class MovableBlock extends DrivableUnit {
//	private MovableBlockState movableBlockState = MovableBlockState.NORMAL;
	
//	public enum MovableBlockState {
//		/** Default state; the block should be able to be pushed around **/
//		NORMAL,
//		/** When the block has been submerged in water, but HAS NOT been walked on yet **/
//		SUBMERGED,
//		/** When the block has been submerged in water and HAS been walked on **/
//		TRAMPLED
//	}

	public MovableBlock(int x, int y, Map map,
			MessageDispatcher messageDispatch) {
		super(x, y, map, messageDispatch);
	}

	@Override
	public String getCurrentImagePath() {
//		switch (movableBlockState) {
//		case NORMAL:
			return "block.png";
//		case SUBMERGED:
//			return "mud.png";
//		case TRAMPLED:
//			return "grass.png";
//		default:
//			return "";
//		}
	}

//	public MovableBlockState getMovableBlockState() {
//		return movableBlockState;
//	}
//
//	public void setMovableBlockState(MovableBlockState movableBlockState) {
//		this.movableBlockState = movableBlockState;
//	}
	
	@Override
	protected int getTicksBetweenMoves() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	public boolean tryMoveInDirection(Directions direction) {
		boolean didMove = super.tryMoveInDirection(direction);
		
		if (didMove && this.isKilled()) {
			// Decay into Mud
			Mud mud = new Mud(this.getX(), this.getY());
			
			MapSquare square = map.getSquareAt(this.getX(), this.getY());
			square.replaceTile(square.getGroundTile(), mud);
		}
		
		return didMove;
	}

	@Override
	protected void doAction() {
	}
	
	@Override
	public int getDyingDuration() {
		return 2;
	}
	
	
//	/** Should be called when the movable block has been pushed into water by the player **/
//	public void blockSubmerged() {
//		setMovableBlockState(MovableBlockState.SUBMERGED);
//	}
//	
//	/** Should be called when the movable block has been walked on top of by the player, after it has been submerged **/
//	public void blockTrampled() {
//		setMovableBlockState(MovableBlockState.TRAMPLED);
//		this.killUnit(UnitState.DYING);
//		
//		// TODO: ideally creation of units should not be handled by units themselves, probably the level itself should do this
//		MapSquare mapSquare = this.map.getSquareAt(this.getX(), this.getY());
//		mapSquare.setGroundTile(new Grass(this.getX(), this.getY()));
//	}
}
