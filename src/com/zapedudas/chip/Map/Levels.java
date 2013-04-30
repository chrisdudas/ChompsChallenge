package com.zapedudas.chip.Map;

public class Levels {
	public static final String nameProperty = "NAME";
	public static final String previewProperty = "PREVIEW";
	public static final String mapbeginProperty = "MAPDATA";
	
	public static final String[] levels = 
	{
	 "levels/map1.csv"
	};
	
	public static String getLevelNameFromLevelData(String[] levelData) {
		return getPropertyFromLevelData(Levels.nameProperty, levelData);
	}
	
	public static String getPreviewFilenameFromLevelData(String[] levelData) {
		return getPropertyFromLevelData(Levels.previewProperty, levelData);
	}
	
	public static String getPropertyFromLevelData(String propertyName, String[] levelData) {
		for (String line : levelData) {
			String[] pair = line.split(",");
			if (pair[0].equalsIgnoreCase(propertyName)) return pair[1]; 
		}
		
		return null;
	}
}
