package com.zapedudas.chip.activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import android.os.Bundle;
import android.widget.Toast;

import android.content.Context;

import com.zapedudas.chip.ImageCache;
import com.zapedudas.chip.map.Map;
import com.zapedudas.chip.map.MapSquare;
import com.zapedudas.chip.map.Message;
import com.zapedudas.chip.map.MessageDispatcher;
import com.zapedudas.chip.map.Message.MessageType;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Driver.Driver;
import com.zapedudas.chip.Tile.Driver.LocalPlayerDriver;
import com.zapedudas.chip.Tile.Driver.NPCDriver;
import com.zapedudas.chip.Tile.Item.Item;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Unit.*;
import com.zapedudas.chip.Tile.Unit.Unit.UnitState;

import processing.core.*;

public class LevelScreen extends PApplet {
	private final int BACKGROUND_R = 0;
	private final int BACKGROUND_G = 88;
	private final int BACKGROUND_B = 58;
	
	/* Milliseconds between each driver 'tick' */
	private final int MILLIS_PER_DRIVER_STEP = 100;
	/* Milliseconds between each animation 'tick' */
	private final int MILLIS_PER_ANIMATION_STEP = 10;
	
	private final int FRAMES_PER_SECOND = 60;
	
	private Map map;
	private int viewradii_x;
	private int viewradii_y;
	private int viewsize_x;
	private int viewsize_y;
	private int tile_width;
	private int tile_height;	
	private ImageCache imageCache;
	
	private int lastDriverStep = 0;
	private int lastAnimationStep = 0;
	
	private ArrayList<Driver> drivers;
	private MessageDispatcher messageDispatcher;
	
	// Buttons
	private int buttonSize = 80;
	private final String ARROW_UP = "arrow up.png";
	private final String ARROW_LEFT = "arrow left.png";
	private final String ARROW_RIGHT = "arrow right.png";
	private final String ARROW_DOWN = "arrow down.png";
	private final String ARROW_UP_PRESS = "arrow up glow.png";
	private final String ARROW_LEFT_PRESS = "arrow left glow.png";
	private final String ARROW_RIGHT_PRESS = "arrow right glow.png";
	private final String ARROW_DOWN_PRESS = "arrow down glow.png";
	
	// Player/s
	private Player localPlayer;
	private LocalPlayerDriver localPlayerDriver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void setup()
	{
		// TODO: CHANGE THIS - intelligently find the number of tiles to display (based on size and dpi)
		this.viewradii_x = 4;
		this.viewradii_y = 4;
		
		this.viewsize_x = (viewradii_x * 2) + 1;
		this.viewsize_y = (viewradii_y * 2) + 1;
		
		this.tile_width = this.width / this.viewsize_x;
		this.tile_height = this.tile_width;
		
		this.buttonSize = this.width / 6; // 6?
		
		this.frameRate(FRAMES_PER_SECOND);
		
		setupLevel();
	}
	
	private void setupLevel() {
		String levelFile = this.getIntent().getStringExtra("levelFile");
		
		this.map = new Map(loadStrings(levelFile));
		this.imageCache = new ImageCache(this);
		this.messageDispatcher = new MessageDispatcher();
		
		// setup unit drivers
		this.drivers = new ArrayList<Driver>();
		
		for (int row = 0; row < map.getHeight(); row++) {
			for (int col = 0; col < map.getWidth(); col++) {
				Unit[] units = map.getSquareAt(col, row).getUnits();
				
				Unit unit = null;
				try {
					if (units.length > 1) throw new Exception("More than one unit on square during driver scan!");
					if (units.length == 1) unit = units[0];
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				if (unit != null) {
					if (unit instanceof Player) {
						localPlayer = (Player)unit;
						this.localPlayerDriver = new LocalPlayerDriver(unit, map, messageDispatcher);
						this.drivers.add(localPlayerDriver);
					}
					else {
						setupDriverForUnit(unit);
					}
				}
			}
		}
		
		if (localPlayerDriver == null) {
			Toast.makeText(this, "ERROR: no player found", Toast.LENGTH_LONG).show();
			this.finish();
		}
	}

	/**
	 * Gets the driver class from the unit, creates the driver for the unit and finally adds the driver the npcDriver collection
	 * @param unit The unit to setup the driver for
	 */
	private void setupDriverForUnit(Unit unit) {
		Class<?> driverClass = unit.getUnitDriverType();
		
		try {
			Constructor<?> driverConstructor = driverClass.getConstructor(Unit.class, Map.class, MessageDispatcher.class);
			
			Driver driver = (Driver)driverConstructor.newInstance(unit, map, messageDispatcher);
			this.drivers.add(driver);
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
	}
	
	public void draw()
	{
		this.background(BACKGROUND_R, BACKGROUND_G, BACKGROUND_B);

		int screen_x;
		float screen_x_offset = 0;		
		if (localPlayer.getX() < this.viewradii_x) screen_x = 0;
		else if (localPlayer.getX() >= map.getWidth() - this.viewradii_x) screen_x = map.getWidth() - this.viewsize_x;
		else {
			screen_x = localPlayer.getX() - this.viewradii_x;
			
			if (!(localPlayer.getX() == this.viewradii_x && localPlayer.getMovementAnimationDirection() == Directions.RIGHT)
			 && !(localPlayer.getX() == map.getWidth() - this.viewradii_x - 1 && localPlayer.getMovementAnimationDirection() == Directions.LEFT)) {					
				screen_x_offset = this.tile_width * localPlayer.getOffsetPercentX();
			}
		}
				
		int screen_y;
		float screen_y_offset = 0;
		if (localPlayer.getY() < this.viewradii_y) screen_y = 0;
		else if (localPlayer.getY() >= map.getHeight() - this.viewradii_y) screen_y = map.getHeight() - this.viewsize_y;
		else {
			screen_y = localPlayer.getY() - this.viewradii_y;	

			if (!(localPlayer.getY() == this.viewradii_y && localPlayer.getMovementAnimationDirection() == Directions.DOWN)
			 && !(localPlayer.getY() == map.getHeight() - this.viewradii_y - 1 && localPlayer.getMovementAnimationDirection() == Directions.UP)) {					
				screen_y_offset =  this.tile_height * localPlayer.getOffsetPercentY();
			}
		}
		
		
		if (lastDriverStep == 0 || lastDriverStep + MILLIS_PER_DRIVER_STEP < millis()) {
			lastDriverStep = millis();
			triggerDrivers();
			handleMessages();
		}
		// // When drawing need to start 1 before and finish 1 after the viewport so the tiles can animate in at the edge 
		
		// Draw bottom layer (ground tiles)
		for (int row = screen_y - 1; row < screen_y + viewsize_y + 1; row++) {
			for (int col = screen_x - 1; col < screen_x + viewsize_x + 1; col++) {
				if (col < 0 || col >= map.getWidth() || row < 0 || row >= map.getHeight()) continue;
				
				MapSquare tileStack = this.map.getSquareAt(col, row);
				drawTileIfPresent(tileStack.getGroundTile(), col - screen_x, row - screen_y, screen_x_offset, screen_y_offset);
			}
		}
		
		boolean shouldTickAnimations = false;
		if (lastAnimationStep == 0 || lastAnimationStep + MILLIS_PER_ANIMATION_STEP < millis()) {
			lastAnimationStep = millis();
			shouldTickAnimations = true;
		}
		
		// Draw upper layers (items, units)
		for (int row = screen_y - 1; row < screen_y + viewsize_y + 1; row++) {
			for (int col = screen_x - 1; col < screen_x + viewsize_x + 1; col++) {
				if (col < 0 || col >= map.getWidth() || row < 0 || row >= map.getHeight()) continue;
				
				MapSquare tileStack = this.map.getSquareAt(col, row);		
				for (Tile tile: tileStack.toArrayTopLayer()) {
					if (shouldTickAnimations) tile.tickAnimation();
					drawTileIfPresent(tile, col - screen_x, row - screen_y, screen_x_offset, screen_y_offset);
				}
			}
		}
		
		// Draw buttons
		image(imageCache.getPImage(ARROW_UP), 10 + buttonSize, height - (3 * buttonSize + 60), buttonSize, buttonSize);
		image(imageCache.getPImage(ARROW_LEFT), 10, height - ( 2 * buttonSize + 60), buttonSize, buttonSize);
		image(imageCache.getPImage(ARROW_RIGHT), 10 + (2 * buttonSize), height - (2 * buttonSize + 60), buttonSize, buttonSize);
		image(imageCache.getPImage(ARROW_DOWN), 10 + buttonSize, height - (buttonSize + 60), buttonSize, buttonSize); 
		
		// Draw inventory overlay
		String inventoryTxt = "";
		for (Item item : localPlayerDriver.getInventory().getItems()) {
			inventoryTxt += inventoryTxt != "" ? "\n" : "";
			inventoryTxt += item.getCurrentImagePath();
		}
		text(inventoryTxt, 10, 10);
	}
		
	private void drawTileIfPresent(Tile tile, int x, int y, float screen_x_offset, float screen_y_offset) {
		if (tile == null) return;
		
		String imagePath = tile.getCurrentImagePath();
		PImage tileImage = this.imageCache.getPImage(imagePath);
		
		int loc_x = this.tile_width * x;
		int loc_y = this.tile_height * y;
		
		float offset_loc_x = loc_x + (this.tile_width * tile.getOffsetPercentX()) - screen_x_offset;
		float offset_loc_y = loc_y + (this.tile_height * tile.getOffsetPercentY()) - screen_y_offset;
		
		image(tileImage, offset_loc_x, offset_loc_y, this.tile_width, this.tile_height);	
	}
	
	private void triggerDrivers() {
		ArrayList<Driver> driversToRemove = new ArrayList<Driver>();
		
		for (Driver driver : drivers) {
			driver.tick();
			
			if (driver.isDriverStopped()) driversToRemove.add(driver);
		}

		for (Driver driver : driversToRemove) {
			drivers.remove(driver);
		}
	}
	
	private void handleMessages() {
		for (Message message : messageDispatcher.getMessages()) {
			MessageType messageType = message.getMessageType();
			
			if (messageType == MessageType.UNIT_KILLED) {

			}
			else if (messageType == MessageType.PLAYER_HAS_DIED) {
				setupLevel();
				
				final Context context = this;
				this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(context, "You dead sucka!!!", Toast.LENGTH_SHORT).show();
					}
				});
				return;
			}
		}
		
		messageDispatcher.clearMessages();
	}

	@Override
	public void mousePressed() {
		if (localPlayer.getUnitState() != UnitState.ALIVE) return;
		
		if (mouseX >= 10+buttonSize  && mouseX <= 10+(2*buttonSize) && mouseY >= height - (3*buttonSize+60) && mouseY <= height - (2*buttonSize+60)) {
			localPlayerDriver.move(Directions.UP);
			image(imageCache.getPImage(ARROW_UP_PRESS), 10 + buttonSize, height - (3 * buttonSize + 60), buttonSize, buttonSize);
		}
		
		if (mouseX >= 10 && mouseX <= 10+buttonSize && mouseY >= height - (2*buttonSize+60) && mouseY <= height - (buttonSize+60)) {
			localPlayerDriver.move(Directions.LEFT);
			image(imageCache.getPImage(ARROW_LEFT_PRESS), 10, height - ( 2 * buttonSize + 60), buttonSize, buttonSize);
		}
		
		if (mouseX >= 10+(2*buttonSize) && mouseX <= 10+(3*buttonSize) && mouseY >= height - (2*buttonSize+60) && mouseY <= height - (buttonSize+60)) {
			localPlayerDriver.move(Directions.RIGHT);
			image(imageCache.getPImage(ARROW_RIGHT_PRESS), 10 + (2 * buttonSize), height - (2 * buttonSize + 60), buttonSize, buttonSize);
		}
		
		if (mouseX >= 10+(buttonSize) && mouseX <= 10+(2*buttonSize) && mouseY >= height - (buttonSize+60) && mouseY <= height - 60) {
			localPlayerDriver.move(Directions.DOWN);
			image(imageCache.getPImage(ARROW_DOWN_PRESS), 10 + buttonSize, height - (buttonSize + 60), buttonSize, buttonSize);
		}
	}
}
