package com.zapedudas.chip.Collision;

import com.zapedudas.chip.Collision.Collision.CollisionDirections;
import com.zapedudas.chip.Tile.Unit.Player;
import com.zapedudas.chip.Tile.Unit.Unit;

public class CollisionManager {
	public void handleCollision(Unit unit1, Unit unit2, CollisionDirections collisionDirection) {
		if (isCollisionBetween(unit1, unit2, Player.class, Player.class)) {
	
		}
		
		if (unit1.getClass().isInstance(Player.class)) {
			
		}
	}
	
	public boolean isCollisionBetween(Unit unit1, Unit unit2, Class<?> class1, Class<?> class2) {
		return (unit1.getClass().isInstance(class1) && unit2.getClass().isInstance(class2)) ||
			   (unit1.getClass().isInstance(class2) && unit2.getClass().isInstance(class1));
	}
}
