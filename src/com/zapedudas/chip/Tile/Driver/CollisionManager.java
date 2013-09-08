package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Tile.Grass;
import com.zapedudas.chip.Tile.Mud;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Wall;
import com.zapedudas.chip.Tile.Water;
import com.zapedudas.chip.Tile.Item.WaterBoots;
import com.zapedudas.chip.Tile.Unit.Bug;
import com.zapedudas.chip.Tile.Unit.MovableBlock;
import com.zapedudas.chip.Tile.Unit.Player;
import com.zapedudas.chip.Tile.Unit.Unit;
import com.zapedudas.chip.Tile.Unit.Unit.UnitState;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MapSquare;

public class CollisionManager {
	public enum Result {
		/** The colliding unit can move **/
		CANMOVE,
		/** The colliding unit should not move **/
		BLOCKED,
		/** The colliding unit should kill itself **/
		DIED,
//		/** The colliding unit can move and should ignore any tiles lower on the tile stack **/
//		CANMOVE_IGNORELOWERTILES
	}
	
	public static Result handleCollision(Tile tile1, Tile tile2, Map map) {
		Tile[] tiles = new Tile[] { tile1, tile2 };
		
		if (isCollisionBetween(tiles, Player.class, Water.class)) {
			Player player = (Player)getDrivableUnitOfType(tiles, Player.class);
			
			if (player.getInventory().hasItem(WaterBoots.class)) {
				return Result.CANMOVE;
			}
			else {
				player.killUnit(UnitState.DROWNING);
				return Result.DIED;
			}
		}
		else if (isCollisionBetween(tiles, MovableBlock.class, Water.class)) {
			MovableBlock movableBlock = (MovableBlock)getDrivableUnitOfType(tiles, MovableBlock.class);
			movableBlock.killUnit(UnitState.DYING);
			
//			replaceTile(movableBlock, new Mud(movableBlock.getX(), movableBlock.getY()), map);
			
			return Result.CANMOVE;
		}
		else if (isCollisionBetween(tiles, Player.class, MovableBlock.class)) {
			Player player = (Player)getTileOfType(tiles, Player.class);
			MovableBlock movableBlock = (MovableBlock)getDrivableUnitOfType(tiles, MovableBlock.class);
			
			if (movableBlock.isKilled()) {
				return Result.CANMOVE;
			}
			if (movableBlock.tryMoveInDirection(player.getDirection())) {
				return Result.CANMOVE;
			}
			else {
				return Result.BLOCKED;
			}
		}
		else if (isCollisionBetween(tiles, Unit.class, MovableBlock.class)) {
			return Result.BLOCKED;
		}
		else if (isCollisionBetween(tiles, Player.class, Mud.class)) {
			Mud mud = (Mud)getTileOfType(tiles, Mud.class);
			
			replaceTile(mud, new Grass(mud.getX(), mud.getY()), map);

			return Result.CANMOVE;
		}
		else if (isCollisionBetween(tiles, Unit.class, Mud.class)) {
			return Result.BLOCKED;
		}
//		else if (isCollisionBetween(tiles, Player.class, MovableBlock.class)) {
//			MovableBlock movableBlock = (MovableBlock)getDrivableUnitOfType(tiles, MovableBlock.class);
//			Player player = (Player)getTileOfType(tiles, Player.class);
//			
//			if (movableBlock.getMovableBlockState() == MovableBlockState.NORMAL) { // Push the block
//				if (movableBlock.tryMoveInDirection(player.getDirection())) {
//					return Result.CANMOVE;
//				}
//				else {
//					return Result.BLOCKED;
//				}
//			}
//			else if (movableBlock.getMovableBlockState() == MovableBlockState.SUBMERGED) { // Sunk but not stepped on
//				movableBlock.setMovableBlockState(MovableBlockState.TRAMPLED);
//				return Result.CANMOVE_IGNORELOWERTILES;
//			}
//			else { // Sunk and trampled
//				return Result.CANMOVE_IGNORELOWERTILES;
//			}
//		}
		else if (isCollisionBetween(tiles, Unit.class, Water.class)) {
			return Result.BLOCKED;
		}
		else if (isCollisionBetween(tiles, Bug.class, Player.class)) {
			getDrivableUnitOfType(tiles, Player.class).killUnit(UnitState.DYING);
			return Result.DIED;
		}
		else if (isCollisionBetween(tiles, Unit.class, Unit.class)) {
			return Result.BLOCKED;
		}
		else if (isCollisionBetween(tiles, Unit.class, Wall.class)) {
			return Result.BLOCKED;
		}
		else {
			return Result.CANMOVE;
		}
	}
	
	private static boolean isCollisionBetween(Tile tile1, Tile tile2, Class<?> class1, Class<?> class2) {
		return (class1.isInstance(tile1) && class2.isInstance(tile2)) ||
				(class1.isInstance(tile2) && class2.isInstance(tile1));
	}
	
	private static boolean isCollisionBetween(Tile[] tiles, Class<?> class1, Class<?> class2) {
		return isCollisionBetween(tiles[0], tiles[1], class1, class2);
	}
	
//	private static boolean isCollisionBetween(Tile tile1, Tile tile2, Class<?> class1, Class<?> class2, Class<?>[] excludingClasses) {
//		for (Class<?> cls : excludingClasses) {
//			if (cls.isInstance(tile1) || cls.isInstance(tile2)) return false;
//		}
//		
//		return isCollisionBetween(tile1, tile2, class1, class2);
//	}
	
	private static Tile getTileOfType(Tile[] tiles, Class<?> classToMatch) {
		for (Tile tile : tiles) {
			if (classToMatch.isInstance(tile)) {
				return tile;
			}
		}
		
		return null;
	}
	
//	private static Unit getUnitOfType(Tile[] tiles, Class<?> classToMatch) {
//		return (Unit)getTileOfType(tiles, classToMatch);
//	}
	
	private static DrivableUnit getDrivableUnitOfType(Tile[] tiles, Class<? extends DrivableUnit> classToMatch) {
		return (DrivableUnit)getTileOfType(tiles, classToMatch);
	}
	
	private static void replaceTile(Tile oldTile, Tile newTile, Map map) {
		MapSquare square = map.getSquareAt(oldTile.getX(), oldTile.getY());
		square.replaceTile(oldTile, newTile);
	}
}
