package com.zapedudas.chip.map;

import java.util.ArrayList;
import java.util.Arrays;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Driver.DrivableUnit;
import com.zapedudas.chip.Tile.Item.Item;

public class Map {
	@SuppressWarnings("unused")
	private String levelName;
	private MapSquare[][] mapSquareMatrix;
	
	private int width;
	private int height;
	
	private MessageDispatcher messageDispatch;

	public Map(String[] levelData, MessageDispatcher messageDispatch) {	
		this.messageDispatch = messageDispatch;
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
						
						if (tile instanceof DrivableUnit) {
							square.addDrivableUnitTile(tile);
						}
						else if (tile instanceof Item) {
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
		
		Tile tile = TileSpawner.spawnTile(tileID, x, y, this, messageDispatch);
		
		if (tile == null) {
			// Tile failed to spawn
			return null;
		}
		
		return tile;
		
//		try {
//			Class<?> tileClass = TileMapping.getClassFromID(tileID);
//			Constructor<?> tileConstructor = tileClass.getConstructor(int.class, int.class);
//			Tile tile = (Tile)tileConstructor.newInstance(x, y);
//			
//			for (int i = 1; i < values.length; i++) {
//				tile.sendTileProperty(values[i]);
//			}
//			
//			return tile;
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
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
	
	public Tile[] getTilesOfType(Class<?> type) {
		ArrayList<Tile> matches = new ArrayList<Tile>();
		
		for (MapSquare[] row : mapSquareMatrix) {
			for (MapSquare col : row) {
				for (Tile tile : col.toArray()) {
					if (type.isInstance(tile)) {
						matches.add(tile);
					}
				}
			}
		}
		
//		for (int y = 0; y < getHeight(); y++) {
//			for (int x = 0; x < getWidth(); x++) {
//				Tile[] tiles = mapSquareMatrix[y][x].toArray();
//				
//				for (Tile tile : tiles) {
//					if (type.isInstance(tile)) {
//						matches.add(tile);
//					}
//				}
//			}
//		}
		
		return matches.toArray(new Tile[matches.size()]);
	}
}
