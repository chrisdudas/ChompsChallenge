package com.zapedudas.chip.map;

import java.util.ArrayList;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Driver.DrivableUnit;
import com.zapedudas.chip.Tile.Item.Item;

public class MapSquare {
	private Tile groundTile;
	private Tile itemTile;
	private ArrayList<Tile> unitTiles;
	
	public MapSquare(Tile groundTile, Tile itemTile, Tile unitTile) {
		this();
		
		setGroundTile(groundTile);
		setItemTile(itemTile);
		if (unitTile != null) unitTiles.add(unitTile);
	}
	
	public MapSquare() {
		this.unitTiles = new ArrayList<Tile>();
	}

	public Tile getGroundTile() {
		return groundTile;
	}
	
	public void setGroundTile(Tile groundTile) {
		this.groundTile = groundTile;
	}
	
	public Tile getItemTile() {
		return itemTile;
	}
	
	public Item getItem() {
		return (Item)itemTile;
	}
	
	public void setItemTile(Tile itemTile) {
		this.itemTile = itemTile;
	}
	
	public void clearItemTile() {
		this.itemTile = null;
	}
	
	public Tile[] getUnitTiles() {
		return unitTiles.toArray(new Tile[unitTiles.size()]);
	}
	
	public DrivableUnit[] getDrivableUnits() {
		return unitTiles.toArray(new DrivableUnit[unitTiles.size()]);
	}
	
	/** This will add the unit to the top of the pile **/
	public void addDrivableUnitTile(Tile unitTileToAdd) {
		unitTiles.add(unitTileToAdd);
	}
	
	public void removeDrivableUnitTile(Tile unitToRemove) {
		unitTiles.remove(unitToRemove);
	}
	
	public Tile[] toArray() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		for (Tile unitTile : unitTiles) {
			if (unitTile != null) tiles.add(unitTile);
		}
		
		if (itemTile != null) tiles.add(itemTile);
		
		if (groundTile != null) tiles.add(groundTile);
		
		return tiles.toArray(new Tile[tiles.size()]);
	}
	
	public Tile[] toArrayTopLayer() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		if (itemTile != null) tiles.add(itemTile);
		
		for (Tile unitTile : unitTiles) {
			if (unitTile != null) tiles.add(unitTile);
		}
		
		return tiles.toArray(new Tile[tiles.size()]);		
	}
	
	public void replaceTile(Tile oldTile, Tile newTile) {
		if (oldTile == this.groundTile) {
			this.groundTile = newTile;
		}
		else if (oldTile == this.itemTile) {
			this.itemTile = newTile;
		}
		else {
			for (int i = 0; i < this.unitTiles.size(); i++) {
				if (oldTile == this.unitTiles.get(i)) {
					this.unitTiles.set(i, newTile);
				}
			}
		
			// If this point is reached, oldTile was not found
		}
	}
}
