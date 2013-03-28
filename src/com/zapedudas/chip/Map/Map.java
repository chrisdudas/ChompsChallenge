package com.zapedudas.chip.Map;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.TileMapping;

public class Map {
	private String levelName;
	private Tile[][][] mapMatrix;
	
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
		int nRows = mapData.length;
		int nCols = mapData[0].split(",").length;
		mapMatrix = new Tile[nRows][nCols][2];		
		
		for (int row = 0; row < mapData.length; row++) {
			String[] columns = mapData[row].split(",");
			
			for (int col = 0; col < columns.length; col++) {
				String[] elements = columns[col].split(" ");
				
				Tile floor = null;
				Tile unit = null;
				
				if (elements.length >= 1) floor = spawnTile(elements[0], col, row);
				if (elements.length >= 2) unit = spawnTile(elements[1], col, row);
				
				mapMatrix[row][col] = new Tile[] { floor, unit };
			}
		}
	}
	
	private Tile spawnTile(String element, int x, int y) {
		String[] values = element.split("-");

		int tileID = Integer.parseInt(values[0]);
		
		try {
			Class<?> tileClass = TileMapping.tileMap.get(tileID);
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
	
	public Tile[] getTilesAt(int x, int y) {
		return mapMatrix[x][y];
	}
}
