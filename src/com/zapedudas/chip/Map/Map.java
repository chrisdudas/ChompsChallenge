package com.zapedudas.chip.Map;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.zapedudas.chip.Tile.Tile;

public class Map {
	private String levelName;
	private Tile[][][] mapMatrix;
	
	public Map(String[] levelData) {
		readLevelData(levelData);
	}
	
	private void readLevelData(String[] levelData) {
		for (int row = 0; row < levelData.length; row++) {
			String[] pair = levelData[row].split(",");
			
			if (pair[0] == Levels.nameProperty) {
				this.levelName = pair[1];
			}
			else if (pair[0] == Levels.mapbeginProperty) {
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
			Constructor<?> tileConstructor = TileMapping.tileMap.get(tileID).getConstructor(Integer.class, Integer.class);
			Tile tile = (Tile)tileConstructor.newInstance(x, y);
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
}
