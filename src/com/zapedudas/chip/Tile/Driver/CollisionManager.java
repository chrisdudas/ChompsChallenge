package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Wall;
import com.zapedudas.chip.Tile.Water;
import com.zapedudas.chip.Tile.Item.WaterBoots;
import com.zapedudas.chip.Tile.Unit.Bug;
import com.zapedudas.chip.Tile.Unit.Player;
import com.zapedudas.chip.Tile.Unit.Unit;
import com.zapedudas.chip.Tile.Unit.Unit.UnitState;

public class CollisionManager {
	public enum Result {
		CANMOVE,
		BLOCKED,
		DIED
	}
	
	public static Result handleCollision(Tile tile1, Tile tile2) {
		Tile[] tiles = new Tile[] { tile1, tile2 };
		
		if (isCollisionBetween(tile1, tile2, Player.class, Water.class)) {
			LocalPlayerDriver localPlayerDriver = (LocalPlayerDriver)getDriverFromUnitOfType(tiles, Player.class);
			
			if (localPlayerDriver.getInventory().hasItem(WaterBoots.class)) {
				return Result.CANMOVE;
			}
			else {
				localPlayerDriver.killUnit(UnitState.DROWNING);
				return Result.DIED;
			}
		}
		else if (isCollisionBetween(tile1, tile2, Unit.class, Water.class)) {
			return Result.BLOCKED;
		}
		else if (isCollisionBetween(tile1, tile2, Bug.class, Player.class)) {
			getDriverFromUnitOfType(tiles, Player.class).killUnit(UnitState.DYING);
			return Result.DIED;
		}
		else if (isCollisionBetween(tile1, tile2, Unit.class, Unit.class)) {
			return Result.BLOCKED;
		}
		else if (isCollisionBetween(tile1, tile2, Unit.class, Wall.class)) {
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
	
	private static boolean isCollisionBetween(Tile tile1, Tile tile2, Class<?> class1, Class<?> class2, Class<?>[] excludingClasses) {
		for (Class<?> cls : excludingClasses) {
			if (cls.isInstance(tile1) || cls.isInstance(tile2)) return false;
		}
		
		return isCollisionBetween(tile1, tile2, class1, class2);
	}
	
	private static Tile getTileOfType(Tile[] tiles, Class<?> classToMatch) {
		for (Tile tile : tiles) {
			if (classToMatch.isInstance(tile)) {
				return tile;
			}
		}
		
		return null;
	}
	
	private static Unit getUnitOfType(Tile[] tiles, Class<?> classToMatch) {
		return (Unit)getTileOfType(tiles, classToMatch);
	}
	
	private static Driver getDriverFromUnitOfType(Tile[] tiles, Class<?> classToMatch) {
		return getUnitOfType(tiles, classToMatch).getDriver();
	}
}
