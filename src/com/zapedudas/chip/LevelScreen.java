package com.zapedudas.chip;

import android.os.Bundle;

import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Tile.Tile;

import processing.core.*;

public class LevelScreen extends PApplet {
	private Map map;
	private int numtiles_x;
	private int numtiles_y;
	private int tile_width;
	private int tile_height;	
	private ImageCache imageCache;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String levelFile = this.getIntent().getStringExtra("levelFile");
		
		this.map = new Map(loadStrings(levelFile));
		
		this.imageCache = new ImageCache(this);
	}
	
	public void setup()
	{
		// TODO: CHANGE THIS
		this.numtiles_x = 9;
		this.numtiles_y = 9;
		
		this.tile_width = this.width / this.numtiles_x;
		this.tile_height = this.tile_width;
		
		this.background(0, 88, 58);
	}
	
	public void draw()
	{
		// TODO: get current player position and adjust top-left screen coords to center them;
		int screen_x = 0;
		int screen_y = 0;
		
		for (int row = 0; row <= screen_x + numtiles_x; row++) {
			for (int col = 0; col <= screen_y + numtiles_y; col++) {
				Tile[] tiles = this.map.getTilesAt(row, col);
				
				for (Tile tile : tiles) {
					if (tile != null) {
						PImage tileImage = this.imageCache.getPImage(tile.getCurrentImagePath());
						
						int loc_x = this.tile_width * row;
						int loc_y = this.tile_height * col;
						image(tileImage, loc_x, loc_y, this.tile_width, this.tile_height);
					}
				}
			}
		}
		
		
		
		
//		background(0,88,58);
//		for (int row = 0; row <= players.get(currentPlayer).getY() + viewSize; row++)
//		{	 
//			for (int col = 0; col <= players.get(currentPlayer).getX() + viewSize; col++)
//			{
//				curL = (int) ((col-players.get(currentPlayer).getX() + viewSize) * tileSize) + 2;
//				curU = (int) ((row-players.get(currentPlayer).getY() + viewSize) * tileSize);
//				image(tileImages[map[row][col]], curL, curU, tileSize, tileSize);
//				if (units[row][col] != null) { 
//					image(unitsImages[units[row][col].getNumber() + units[row][col].getOrientation()], curL, curU, tileSize, tileSize);
//				}
//			}
//		}
//		
//		image(arrows[0],10 + buttonSize,height - (3*buttonSize+60),buttonSize, buttonSize);
//		image(arrows[1],10,height - (2*buttonSize+60),buttonSize, buttonSize);
//		image(arrows[2],10+(2*buttonSize),height - (2*buttonSize+60),buttonSize, buttonSize);
//		image(arrows[3],10+buttonSize,height - (buttonSize+60),buttonSize, buttonSize);
//		
////		fill(255);
//		fill(252,145,13);
//		rect(20+(3*buttonSize),height - (3*buttonSize+60),width-(30+(3*buttonSize)),3*buttonSize+40);
//		if(players.get(currentPlayer).hasBlueKey())
//			image(unitsImages[12],25+(3*buttonSize),height - (3*buttonSize+55),tileSize,tileSize);
//		if(players.get(currentPlayer).hasRedKey())
//			image(unitsImages[11],25+(3*buttonSize),height - (3*buttonSize+55 - tileSize),tileSize,tileSize);
//		if(players.get(currentPlayer).hasGreenKey())
//			image(unitsImages[10],25+(3*buttonSize),height - (3*buttonSize+55 - 2*tileSize),tileSize,tileSize);
//		if(players.get(currentPlayer).hasYellowKey())
//			image(unitsImages[13],25+(3*buttonSize),height - (3*buttonSize+55 - 3*tileSize),tileSize,tileSize);
//		if(players.get(currentPlayer).hasFlippers())
//			image(unitsImages[15],35+(4*buttonSize),height - (3*buttonSize+55),tileSize,tileSize);
//		if(players.get(currentPlayer).hasForceBoots())
//			image(unitsImages[21],35+(4*buttonSize),height - (3*buttonSize+55 - tileSize),tileSize,tileSize);
//		if(players.get(currentPlayer).hasFireBoots())
//			image(unitsImages[31],35+(4*buttonSize),height - (3*buttonSize+55 - 2*tileSize),tileSize,tileSize);
//		if(players.get(currentPlayer).hasIceBoots())
//			image(unitsImages[30],35+(4*buttonSize),height - (3*buttonSize+55 - 3*tileSize),tileSize,tileSize);
//		image(unitsImages[5],25+(3*buttonSize),height - (3*buttonSize+55 - 4*tileSize),tileSize,tileSize);
//		fill(0);
//		textSize(tileSize / 2.4f);
//		text(chipsRemaining() + " remaining",15+(4*buttonSize),height - (3*buttonSize+20 - 4*tileSize));
//		if(showHint) {
//			fill(252,145,13);
//			rect(20+(3*buttonSize),height - (3*buttonSize+60),width-(30+(3*buttonSize)),3*buttonSize+40);
//			fill(0);
//			text(hint,25+(3*buttonSize),height - (3*buttonSize+35),200,750);
//		}
//
//		if (frameCount%10 == 0) {
//			//Log.d("NPC", "bugs size: " + bugs.size());
//			for (int i = bugs.size() - 1; i >= 0 ; i--) {
//				npc(bugs.get(i));
//			}
//			for (int i = gliders.size() - 1; i >= 0 ; i--) {
//				npc(gliders.get(i));
//			}
//			for (int i = tanks.size() - 1; i >= 0 ; i--) {
//				npc(tanks.get(i));
//			}
//			Log.d("NPC", "" + fireballs.size());
//			for (int i = fireballs.size() - 1; i >= 0 ; i--) {
////				Log.d("NPC", "Fireball: " + i + " at " + fireballs.get(i).getX() + ", " + fireballs.get(i).getY());
//				npc(fireballs.get(i));
//			}
//			for (int i = balls.size() - 1; i >= 0 ; i--) {
//				npc(balls.get(i));
//			}
//			for (int i = teeth.size() - 1; i >= 0 ; i--) {
//				npc(teeth.get(i));
//			}
//		}
//		for(int i = 0; i < moveableUnits.size(); i++) {
//			switch (map[moveableUnits.get(i).getY()][moveableUnits.get(i).getX()]) {
//			case TILEUP:
//				if(frameCount % directionSpeed  == 0) {
//					if (moveableUnits.get(i) != players.get(currentPlayer)) {
//						if(canMove(moveableUnits.get(i),0))
//							moveableUnits.get(i).move(units,0);
//					}
//					else if (!players.get(currentPlayer).hasForceBoots())
//						players.get(currentPlayer).move(units,0);
//				}
//			break;
//			case TILELEFT:
//				if(frameCount % directionSpeed == 0) {
//					if (moveableUnits.get(i) != players.get(currentPlayer)) {
//						if(canMove(moveableUnits.get(i),1))
//							moveableUnits.get(i).move(units,1);
//					}
//					else if (!players.get(currentPlayer).hasForceBoots())
//						players.get(currentPlayer).move(units,1);
//				}
//			break;
//			case TILERIGHT:
//				if(frameCount % directionSpeed == 0) {
//					if (moveableUnits.get(i) != players.get(currentPlayer)) {
//						if(canMove(moveableUnits.get(i),2))
//							moveableUnits.get(i).move(units,2);
//					}
//					else if (!players.get(currentPlayer).hasForceBoots())
//						players.get(currentPlayer).move(units,2);
//				}
//			break;
//			case TILEDOWN:
//				if(frameCount % directionSpeed == 0) {
//					if (moveableUnits.get(i) != players.get(currentPlayer)) {
//						if(canMove(moveableUnits.get(i),3))
//							moveableUnits.get(i).move(units,3);
//					}
//					else if (!players.get(currentPlayer).hasForceBoots())
//						players.get(currentPlayer).move(units,3);
//				}
//			break;
//			case ICE:
//				if(frameCount % directionSpeed == 0) {
//					if(moveableUnits.get(i) == players.get(currentPlayer)) {
//						if(!players.get(currentPlayer).hasIceBoots()) {
//							if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation())) {
//								moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//							}
//							else {
//								moveableUnits.get(i).setOrientation(3 - moveableUnits.get(i).getOrientation());
//								if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation())) {
//									moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//								}
//							}
//						}
//					}
//					else {
//						if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation())) {
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						}
//						else {
//							moveableUnits.get(i).setOrientation(3 - moveableUnits.get(i).getOrientation());
//							if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation())) {
//								moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//							}
//						}
//					}
//				}
//			break;
//			case 11:
//				if(frameCount % directionSpeed == 0) {
//					if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation() + 2)) {
//						if (moveableUnits.get(i) != players.get(currentPlayer)) {
//							moveableUnits.get(i).setOrientation(moveableUnits.get(i).getOrientation() + 2);
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						}
//						else if (!players.get(currentPlayer).hasIceBoots()) {
//							players.get(currentPlayer).setOrientation(moveableUnits.get(i).getOrientation() + 2);
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//						}
//					}
//					else {
//						moveableUnits.get(i).setOrientation(3 - moveableUnits.get(i).getOrientation());
//						if (moveableUnits.get(i) != players.get(currentPlayer))
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						else if (!players.get(currentPlayer).hasIceBoots())
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//					}	
//				}
//			break;
//			case 12:
//				if(frameCount % directionSpeed == 0) {
//					if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation() + 1)) {
//						if (moveableUnits.get(i) != players.get(currentPlayer)) {
//							moveableUnits.get(i).setOrientation(moveableUnits.get(i).getOrientation() + 1);
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						}
//						else if (!players.get(currentPlayer).hasIceBoots()) {
//							players.get(currentPlayer).setOrientation(moveableUnits.get(i).getOrientation() + 1);
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//						}
//					}
//					else {
//						moveableUnits.get(i).setOrientation(3 - moveableUnits.get(i).getOrientation());
//						if (moveableUnits.get(i) != players.get(currentPlayer))
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						else if (!players.get(currentPlayer).hasIceBoots())
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//					}	
//				}
//			break;
//			case 13:
//				if(frameCount % directionSpeed == 0) {
//					if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation() - 1)) {
//						if (moveableUnits.get(i) != players.get(currentPlayer)) {
//							moveableUnits.get(i).setOrientation(moveableUnits.get(i).getOrientation() - 1);
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						}
//						else if (!players.get(currentPlayer).hasIceBoots()) {
//							players.get(currentPlayer).setOrientation(moveableUnits.get(i).getOrientation() - 1);
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//						}
//					}
//					else {
//						moveableUnits.get(i).setOrientation(3 - moveableUnits.get(i).getOrientation());
//						if (moveableUnits.get(i) != players.get(currentPlayer))
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						else if (!players.get(currentPlayer).hasIceBoots())
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//					}	
//				}
//			break;
//			case 14:
//				if(frameCount % directionSpeed == 0) {
//					if(canMove(moveableUnits.get(i),moveableUnits.get(i).getOrientation() - 2)) {
//						if (moveableUnits.get(i) != players.get(currentPlayer)) {
//							moveableUnits.get(i).setOrientation(moveableUnits.get(i).getOrientation() - 2);
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						}
//						else if (!players.get(currentPlayer).hasIceBoots()) {
//							players.get(currentPlayer).setOrientation(moveableUnits.get(i).getOrientation() - 2);
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//						}
//					}
//					else {
//						moveableUnits.get(i).setOrientation(3 - moveableUnits.get(i).getOrientation());
//						if (moveableUnits.get(i) != players.get(currentPlayer))
//							moveableUnits.get(i).move(units,moveableUnits.get(i).getOrientation());
//						else if (!players.get(currentPlayer).hasIceBoots())
//							players.get(currentPlayer).move(units,players.get(currentPlayer).getOrientation());
//					}	
//				}
//			break;
//			case FIRE:
//				atFire(moveableUnits.get(i));
//			break;
//			case TELEPORT:
//				teleport(moveableUnits.get(i));
//			break;
//			}
//		}
//		if (units[players.get(currentPlayer).getY()][players.get(currentPlayer).getX()] == null) {
//			dead(0);
//		}
//		if (map[players.get(currentPlayer).getY()][players.get(currentPlayer).getX()] != 33)
//			showHint = false;
//		for(int j = 0; j < map.length; j++) {
//			for(int k = 0; k < map[j].length;k++) {
//				if (units[j][k] != null) {
//					units[j][k].updatePos();
//				}
//			}
//		}
//		if(skip)
//			finishLevel();
//		if (restart) {
//			restart = false;
//			startPosition();
//		}
//		
//		if (mousePressed) {
//			if (firstPress >= 0 && frameCount > firstPress + 10 && frameCount%(directionSpeed+1) == 0) {
//				if(map[players.get(currentPlayer).getY()][players.get(currentPlayer).getX()] < 10 
//				|| map[players.get(currentPlayer).getY()][players.get(currentPlayer).getX()] > 14 || players.get(currentPlayer).hasIceBoots()) {
//					if (mouseX >= 10+buttonSize && mouseX <= 10+(2*buttonSize) && mouseY >= height - (3*buttonSize+60) && mouseY <= height - (2*buttonSize+60)) {
//						move(0);
//						image(arrows[4],10 + buttonSize,height - (3*buttonSize+60),buttonSize, buttonSize);
//					}
//					
//					if (mouseX >= 10 && mouseX <= 10+buttonSize && mouseY >= height - (2*buttonSize+60) && mouseY <= height - (buttonSize+60)) {
//						move(1);
//						image(arrows[5],10,height - (2*buttonSize+60),buttonSize, buttonSize);
//					}
//					
//					if (mouseX >= 10+(2*buttonSize) && mouseX <= 10+(3*buttonSize) && mouseY >= height - (2*buttonSize+60) && mouseY <= height - (buttonSize+60)) {
//						move(2);
//						image(arrows[6],10+(2*buttonSize),height - (2*buttonSize+60),buttonSize, buttonSize);
//					}
//					
//					if (mouseX >= 10+(buttonSize) && mouseX <= 10+(2*buttonSize) && mouseY >= height - (buttonSize+60) && mouseY <= height - 60) {
//						move(3);
//						image(arrows[7],10+buttonSize,height - (buttonSize+60),buttonSize, buttonSize);
//					}
//				}
//			}
//		}
	}
}
