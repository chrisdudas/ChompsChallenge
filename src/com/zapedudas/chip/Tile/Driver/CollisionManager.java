package com.zapedudas.chip.Tile.Driver;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Wall;
import com.zapedudas.chip.Tile.Water;
import com.zapedudas.chip.Tile.Unit.Bug;
import com.zapedudas.chip.Tile.Unit.Player;
import com.zapedudas.chip.Tile.Unit.Unit;

public class CollisionManager {
	public enum Result {
		CANMOVE,
		BLOCKED,
		DIED
	}
	
	public static Result handleCollision(Tile tile1, Tile tile2) {
		if (isCollisionBetween(tile1, tile2, Bug.class, Water.class)) {
			Unit bug = (Bug)getTileOfType(new Tile[] { tile1, tile2 }, Bug.class);
//			bug.getDriver().killUnit();
			return Result.CANMOVE;
		}
		else if (isCollisionBetween(tile1, tile2, Player.class, Water.class)) {
			Unit player = (Unit)getTileOfType(new Tile[] { tile1, tile2 }, Unit.class);
			player.getDriver().killUnit();
			return Result.DIED;
		}
		else if (isCollisionBetween(tile1, tile2, Unit.class, Unit.class)) {
			return Result.BLOCKED;
		}
		else if (isCollisionBetween(tile1, tile2, Unit.class, Wall.class)) {
			return Result.BLOCKED;
		}
		
		return Result.CANMOVE;
	}
	
	private static boolean isCollisionBetween(Tile tile1, Tile tile2, Class<?> class1, Class<?> class2) {
		return (class1.isInstance(tile1) && class2.isInstance(tile2)) ||
				(class1.isInstance(tile2) && class2.isInstance(tile1));
	}
	
	private static Tile getTileOfType(Tile[] tiles, Class<?> classToMatch) {
		for (Tile tile : tiles) {
			if (classToMatch.isInstance(tile)) {
				return tile;
			}
		}
		
		return null;
	}
}
