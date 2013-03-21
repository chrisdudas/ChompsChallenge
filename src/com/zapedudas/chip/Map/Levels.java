package com.zapedudas.chip.Map;

public class Levels {
	public static final String nameProperty = "NAME";
	public static final String mapbeginProperty = "MAPDATA";
	
	public static final String[] levels = 
	{
	 "map1.csv"
	};
	
	public static String getLevelNameFromLevelData(String[] levelData) {
		for (int row = 0; row < levelData.length; row++) {
			String[] pair = levelData[row].split(",");
			
			if (pair[0] == Levels.nameProperty) {
				return pair[1];
			}
		}
		
		return "";
	}
}
