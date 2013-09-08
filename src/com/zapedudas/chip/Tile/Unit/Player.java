package com.zapedudas.chip.Tile.Unit;

import com.zapedudas.chip.Tile.Water;
import com.zapedudas.chip.Tile.Driver.DrivableUnit;
import com.zapedudas.chip.Tile.Driver.PlayerInventory;
import com.zapedudas.chip.Tile.Item.Item;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MapSquare;
import com.zapedudas.chip.map.Message;
import com.zapedudas.chip.map.MessageDispatcher;
import com.zapedudas.chip.map.Message.MessageType;

public class Player extends DrivableUnit {
	final String IMAGEPATH_UP = "dog_up.png";
	final String IMAGEPATH_DOWN = "dog_down.png";
	final String IMAGEPATH_LEFT = "dog_left.png";
	final String IMAGEPATH_RIGHT = "dog_right.png";
	
	private PlayerInventory inventory;
	
	public Player(int x, int y, Map map,
			MessageDispatcher messageDispatch) {
		super(x, y, map, messageDispatch);

		inventory = new PlayerInventory();
	}
	
	@Override
	public String getCurrentImagePath() {
		String imagePath = super.getCurrentImagePath();
		if (imagePath != null) return imagePath;		
		
		if (Water.class == this.getCurrentGroundTileType()) {
			switch (this.getDirection()) {
				case UP:
					return "dog_up_swimming.png";
				case DOWN:
					return "dog_down_swimming.png";
				case LEFT:
					return "dog_left_swimming.png";
				case RIGHT:
					return "dog_right_swimming.png";
				default:
					return null;
			}		
		}
		else {
			switch (this.getDirection()) {
				case UP:
					return IMAGEPATH_UP;
				case DOWN:
					return IMAGEPATH_DOWN;
				case LEFT:
					return IMAGEPATH_LEFT;
				case RIGHT:
					return IMAGEPATH_RIGHT;
				default:
					return null;
			}
		}
	}
	
	@Override
	protected int getDyingDuration() {
		return 6;
	}
	
	public void move(Directions direction) {
		this.setDirection(direction);
		
		if (tryMoveInDirection(direction)) {
			pickupItems();
		}
	}

	private void pickupItems() {
		MapSquare square = map.getSquareAt(this.getX(), this.getY());
		Item item = square.getItem();
		
		if (item != null) {
			inventory.addItem(item);
			square.setItemTile(null);
		}
	}
	
	@Override
	public void stopDriver() {
		super.stopDriver();
		messageDispatcher.addMessage(new Message(MessageType.PLAYER_HAS_DIED));
	}

	@Override
	protected int getTicksBetweenMoves() {
		return 1;
	}

	@Override
	protected void doAction() {
	}
	
	public PlayerInventory getInventory() {
		return inventory;
	}
}
