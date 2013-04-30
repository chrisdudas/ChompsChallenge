package com.zapedudas.chip.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import android.os.Bundle;
import android.widget.Toast;

import android.content.Context;

import com.zapedudas.chip.ImageCache;
import com.zapedudas.chip.Map.Map;
import com.zapedudas.chip.Map.Message;
import com.zapedudas.chip.Map.MessageDispatcher;
import com.zapedudas.chip.Map.Message.MessageType;
import com.zapedudas.chip.Tile.Tile;
import com.zapedudas.chip.Tile.Tile.Directions;
import com.zapedudas.chip.Tile.Driver.LocalPlayerDriver;
import com.zapedudas.chip.Tile.Driver.NPCDriver;
import com.zapedudas.chip.Tile.Unit.*;

import processing.core.*;

public class LevelScreen extends PApplet {
	private final int BACKGROUND_R = 0;
	private final int BACKGROUND_G = 88;
	private final int BACKGROUND_B = 58;
	
	private final int MILLIS_PER_STEP = 100;
	
	private Map map;
	private int viewradii_x;
	private int viewradii_y;
	private int viewsize_x;
	private int viewsize_y;
	private int tile_width;
	private int tile_height;	
	private ImageCache imageCache;
	private int lastStep = 0;
	
	private ArrayList<NPCDriver> npcDrivers;
	private MessageDispatcher messageDispatcher;
	
	// Buttons
	private final int BUTTON_SIZE = 80;
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

		String levelFile = this.getIntent().getStringExtra("levelFile");
		
		this.map = new Map(loadStrings(levelFile));
		
		this.imageCache = new ImageCache(this);
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
		
		this.background(BACKGROUND_R, BACKGROUND_G, BACKGROUND_B);
		
		this.frameRate(60);
		
		this.messageDispatcher = new MessageDispatcher();
		
		// setup unit drivers
		npcDrivers = new ArrayList<NPCDriver>();
		
		for (int row = 0; row < map.getHeight(); row++) {
			for (int col = 0; col < map.getWidth(); col++) {
				Unit unit = map.getUnitAt(col, row);
				
				if (unit != null) {
					if (unit instanceof Player) {
						localPlayer = (Player)unit;
						this.localPlayerDriver = new LocalPlayerDriver(unit, map, messageDispatcher);
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
			Constructor<?> npcDriverConstructor = driverClass.getConstructor(Unit.class, Map.class, MessageDispatcher.class);
			
			NPCDriver npcdriver = (NPCDriver)npcDriverConstructor.newInstance(unit, map, messageDispatcher);
			this.npcDrivers.add(npcdriver);
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
		int screen_x;
		if (localPlayer.getX() < this.viewradii_x) screen_x = 0;
		else if (localPlayer.getX() >= map.getWidth() - this.viewradii_x) screen_x = map.getWidth() - this.viewsize_x;
		else screen_x = localPlayer.getX() - this.viewradii_x;		
		
		int screen_y;
		if (localPlayer.getY() < this.viewradii_y) screen_y = 0;
		else if (localPlayer.getY() >= map.getHeight() - this.viewradii_y) screen_y = map.getHeight() - this.viewsize_y;
		else screen_y = localPlayer.getY() - this.viewradii_y;	
		
		if (lastStep == 0 || lastStep + MILLIS_PER_STEP < millis()) {
			lastStep = millis();
			triggerDrivers();
			handleMessages();
		}
	
		for (int row = screen_y; row < screen_y + viewsize_y; row++) {
			for (int col = screen_x; col < screen_x + viewsize_x; col++) {
				Tile[] tiles = this.map.getTilesAt(col, row);
				
				Tile floor = tiles[0];
				Tile unit = tiles[1];
				
				if (floor != null) drawTile(floor, col - screen_x, row - screen_y);
				if (unit != null) drawTile(unit, col - screen_x, row - screen_y);	
			}
		}
		
		// Draw buttons
		image(imageCache.getPImage(ARROW_UP), 10 + BUTTON_SIZE, height - (3 * BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		image(imageCache.getPImage(ARROW_LEFT), 10, height - ( 2 * BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		image(imageCache.getPImage(ARROW_RIGHT), 10 + (2 * BUTTON_SIZE), height - (2 * BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		image(imageCache.getPImage(ARROW_DOWN), 10 + BUTTON_SIZE, height - (BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE); 
	}
		
	private void drawTile(Tile tile, int x, int y) {
		PImage tileImage = this.imageCache.getPImage(tile.getCurrentImagePath());
		
		int loc_x = this.tile_width * x;
		int loc_y = this.tile_height * y;
		image(tileImage, loc_x, loc_y, this.tile_width, this.tile_height);	
	}
	
	private void triggerDrivers() {
		for (NPCDriver driver : npcDrivers) {
			driver.tick();
		}
	}
	
	private void handleMessages() {
		for (Message message : messageDispatcher.getMessages()) {
			MessageType messageType = message.getMessageType();
			
			if (messageType == MessageType.UNIT_KILLED) {
//				Unit unit = (Unit)message.getSubject();
//				unit.getDriver().killUnit();
			}
			else if (messageType == MessageType.PLAYER_HAS_DIED) {
				final Context context = this;
				this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(context, "You dead sucka!!!", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		
		messageDispatcher.clearMessages();
	}

	@Override
	public void mousePressed() {
		if (mouseX >= 10+BUTTON_SIZE  && mouseX <= 10+(2*BUTTON_SIZE) && mouseY >= height - (3*BUTTON_SIZE+60) && mouseY <= height - (2*BUTTON_SIZE+60)) {
			localPlayerDriver.move(Directions.UP);
			image(imageCache.getPImage(ARROW_UP_PRESS), 10 + BUTTON_SIZE, height - (3 * BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		}
		
		if (mouseX >= 10 && mouseX <= 10+BUTTON_SIZE && mouseY >= height - (2*BUTTON_SIZE+60) && mouseY <= height - (BUTTON_SIZE+60)) {
			localPlayerDriver.move(Directions.LEFT);
			image(imageCache.getPImage(ARROW_LEFT_PRESS), 10, height - ( 2 * BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		}
		
		if (mouseX >= 10+(2*BUTTON_SIZE) && mouseX <= 10+(3*BUTTON_SIZE) && mouseY >= height - (2*BUTTON_SIZE+60) && mouseY <= height - (BUTTON_SIZE+60)) {
			localPlayerDriver.move(Directions.RIGHT);
			image(imageCache.getPImage(ARROW_RIGHT_PRESS), 10 + (2 * BUTTON_SIZE), height - (2 * BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		}
		
		if (mouseX >= 10+(BUTTON_SIZE) && mouseX <= 10+(2*BUTTON_SIZE) && mouseY >= height - (BUTTON_SIZE+60) && mouseY <= height - 60) {
			localPlayerDriver.move(Directions.DOWN);
			image(imageCache.getPImage(ARROW_DOWN_PRESS), 10 + BUTTON_SIZE, height - (BUTTON_SIZE + 60), BUTTON_SIZE, BUTTON_SIZE);
		}
	}
}
