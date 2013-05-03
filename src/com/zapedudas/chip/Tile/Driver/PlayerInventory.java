package com.zapedudas.chip.Tile.Driver;

import java.util.ArrayList;

import com.zapedudas.chip.Tile.Item.Item;

public class PlayerInventory {
	ArrayList<Item> items;
	
	public PlayerInventory() {
		this.items = new ArrayList<Item>();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public void clearItems() {
		items.clear();
	}
	
	public boolean hasItem(Class<?> itemType) {
		for (Item item : items) {
			if (itemType.isInstance(item)) return true;
		}
		
		return false;
	}
	
	public Item[] getItems() {
		return items.toArray(new Item[items.size()]);
	}
}
