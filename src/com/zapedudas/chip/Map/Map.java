package com.zapedudas.chip.map;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.TileMapping;
import com.zapedudas.chip.Tile.Item.Item;
import com.zapedudas.chip.Tile.Unit.Unit;

public class Map {
	private String levelName;
//	private Tile[][][] mapMatrix;
	private MapSquare[][] mapSquareMatrix;
	
	private int width;
	private int height;

	public Map(String[] levelData) {		
		readLevelData(levelData);
	}
	
	private void readLevelData(String[] levelData) {
		for (int row = 0; row < levelData.length; row++) {
			String[] pair = levelData[row].split(",");
			
			if (pair[0].equals(Levels.nameProperty)) {
				this.levelName = pair[1];
			}
			else if (pair[0].equals(Levels.mapbeginProperty)) {
				String[] mapData = Arrays.copyOfRange(levelData, row + 1, levelData.length);
				parseMapData(mapData);
			}
		}
	}
	
	private void parseMapData(String[] mapData) {
		this.height = mapData.length;
		this.width = mapData[0].split(",").length;
		mapSquareMatrix = new MapSquare[this.height][this.width];
		
		for (int row = 0; row < this.height; row++) {
			String[] columns = mapData[row].split(",");
			
			for (int col = 0; col < this.width; col++) {
				MapSquare square = new MapSquare();

				String[] elements = columns[col].split(" ");
				if (elements.length >= 1) square.setGroundTile(spawnTile(elements[0], col, row));
				if (elements.length >= 2) {
					for (int i = 1; i < elements.length; i++) {
						Tile tile = spawnTile(elements[i], col, row);
						
						if (Unit.class.isInstance(tile)) {
							square.addUnitTile(tile);
						}
						else if (Item.class.isInstance(tile)) {
							square.setItemTile(tile);
						}
					}
				}
				
				mapSquareMatrix[row][col] = square;
			}
		}
	}
	
	private Tile spawnTile(String element, int x, int y) {
		String[] values = element.split("-");

		int tileID = Integer.parseInt(values[0]);
		
		try {
			Class<?> tileClass = TileMapping.getClassFromID(tileID);
			Constructor<?> tileConstructor = tileClass.getConstructor(int.class, int.class);
			Tile tile = (Tile)tileConstructor.newInstance(x, y);
			
			for (int i = 1; i < values.length; i++) {
				tile.sendTileProperty(values[i]);
			}
			
			return tile;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public MapSquare[][] getMapMatrix() {
		return this.mapSquareMatrix;
	}
	
	public MapSquare getSquareAt(int x, int y) {
		return mapSquareMatrix[y][x];
	}
	

//	public void moveUnitTileFromCoordsToCoords(int old_x, int old_y, int new_x, int new_y) {
//		Tile tile = getUnitAt(old_x, old_y);
//		setUnitAt(old_x, old_y, null);
//		setUnitAt(new_x, new_y, tile);
//	}
}
