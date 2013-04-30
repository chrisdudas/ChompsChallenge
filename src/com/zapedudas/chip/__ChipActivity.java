package com.zapedudas.chip;
//package com.zapedudas.chip;
//
//import java.io.BufferedInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//import android.content.Intent;
//import android.media.AudioManager;
//import android.media.SoundPool;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
////import android.widget.ArrayAdapter;
//import android.widget.Toast;
//import processing.core.*;
//
//public class ChipActivity extends PApplet
//{
//	/** The bluetooth service used for multiplayer*/
//	private BluetoothChatService mChatService;
//	/** The size in pixels of each tile in the game */
//	private float tileSize;
//	/** The size in pixels of the arrow buttons in the game */
//	private float buttonSize;
//	/** The array of tile images used in the game */
//	private PImage[] tileImages = new PImage[60];
//	/** The array of unit images used in the game */
//	private PImage[] unitsImages = new PImage[50];
//	/** The current map of the level, a 2-dimentional array of integers */
//	public int[][] map;
//	/** The array of images of the arrows used to control the player's character */
//	private PImage[] arrows = new PImage[8];
//	/** The left most position of where the map starts getting drawn from */
//	private float curL = 0;
//	/** The upper msot position of where the map starts getting drawn from */
//	private int curU = 0;
//	/** The array of starting locations of the units. Every time a level starts, the units array gets reset to this */
//	private int[][] unitsStart;
//	/** A boolean to see if the player has been notified of an event, this is so they don't get notified too many times */
//	private boolean notified = false;
//	/** The list of maps */
//	private List<String[]> maps = new LinkedList<String[]>();
//	/** The current level that is being played */
//	private int currentLevel;
//	/** The array of units which shows the current position of all the units in a map */
//	private Unit[][] units;
//	/** A string which can be used to display a message to the player */
//	private String message;
//	/** The size of the view of the player. 4 is the default setting and means the the player can see 4 tiles ahead of them */
//	private int viewSize = 4;
//	/** The screenSize is dependent on the viewSize and it is the width or height of the view in tiles */
//	private int screenSize = (viewSize*2)+1;
//	/** A boolean which when true will skip the current level */
//	private boolean skip;
//	/** The current player of the game */
//	private int currentPlayer = 0;
//	/** The other player in multiplayer */
//	private int otherPlayer = 1;
//	/** The list of all the players */
//    private List<Player> players = new LinkedList<Player>();
//	/** The list of all the moveable units in the map */
//    private List<Unit> moveableUnits;
//	/** The list of all the bugs in the map */
//	private LinkedList<Unit> bugs;
//	/** The list of all the gliders in the map */
//	private LinkedList<Unit> gliders;
//	/** The list of all the tanks in the map */
//	private LinkedList<Unit> tanks;
//	/** The list of all the fireballs in the map */
//	private LinkedList<Unit> fireballs;
//	/** The list of all the balls in the map */
//	private LinkedList<Unit> balls;
//	/** The list of all the teeth in the map */
//	private LinkedList<Unit> teeth;
//	/** The information on the level, including the level name and the hint for the level */
//	private String[] levelInformation;
//	/** The hint for the level, which comes from the level information */
//	private String hint;
//	/** The number of chips required for the current level, if zero, then it will be equal to the total number of chips on the level */
//	private int chipsRequired;
////	private String levelName;
//
//	/** The list of all the tiles that a fireball can move on */
//	private LinkedList<Integer> fireballMove;
//	/** The list of all the tiles that a glider can move on */
//	private LinkedList<Integer> gliderMove;
//	/** The list of all the tiles that a tank can move on */
//	private LinkedList<Integer> tankMove;
//	/** The list of all the tiles that a ball can move on */
//	private LinkedList<Integer> ballMove;
//	/** The list of all the tiles that an npc can move on */
//	private LinkedList<Integer> npcMove;
//
//	/** Boolean which is true if multiplayer mode is selected and false if single player is selected */
//	private boolean multiplayer;
//	/** Boolean which will be false unless the player has selected restart */
//	private boolean restart;
//	/** The speed that force tiles and ice will carry units */
//	private int directionSpeed = 3;
//
//	/** The sound pool which is used for the sounds in the game*/
//	private SoundPool soundPool;
//	/** The Hash map of sounds that are used in the game */
//	private HashMap<Integer, Integer> soundsMap;
//	/** The number for the death sound */
//	private final int SOUND1 = 1;
//	/** The number for the bomb sound */
//	private final int BOMB = 2;
//	/** The number for the pop sound */
//	private final int POP = 3;
//	/** The number for the exit sound */
//	private final int EXIT = 4;
//	/** The number for the door sound */
//	private final int DOOR = 5;
//	/** The number for the item sound */
//	private final int ITEM = 6;
//	/** The number for the oof sound */
//	private final int OOF = 7;
//	/** The number for the chip sound */
//	private final int CHIP = 8;
//	/** The number for the splash sound */
//	private final int SPLASH = 9;
//	/** The number for the teleport sound */
//	private final int TELEPORTSOUND = 10;
//	/** The number for the thief sound */
//	private final int THIEFSOUND = 11;
//	
//	/** For debugging bluetooth*/
//    private static final String TAG = "Bluetooth";
//    /** For debugging bluetooth*/
//    private static final boolean D = true;
//
//    /** The number for the state change message in bluetooth*/
//    public static final int MESSAGE_STATE_CHANGE = 1;
//    /** The number for the read in bluetooth*/
//    public static final int MESSAGE_READ = 2;
//    /** The number for the write message in bluetooth*/
//    public static final int MESSAGE_WRITE = 3;
//    /** The number for the device name message in bluetooth*/
//    public static final int MESSAGE_DEVICE_NAME = 4;
//    /** The number for the toast message in bluetooth*/
//    public static final int MESSAGE_TOAST = 5;
//
//    /** Name of the device for bluetooth */
//    public static final String DEVICE_NAME = "device_name";
//    /** Toast */
//    public static final String TOAST = "toast";
//
//    /** The number for a connection request for the bluetooth service */
//    private static final int REQUEST_CONNECT = 2;
//    /** The number for a request to enable the bluetooth service */
//    private static final int REQUEST_ENABLE_BT = 3;
//
//	
////    private ArrayAdapter<String> mConversationArrayAdapter;
//    /**
//     * The name of the connected device
//     */
//    private String mConnectedDeviceName = null;
//    /**
//     * The bluetooth adaptor
//     */
//    private BluetoothAdapter mBluetoothAdapter = null;
//
//    /**
//     * Boolean to see if the hint should be displayed
//     */
//	private boolean showHint;
//	/** Integer of the first time that the screen is pressed. Used for pressing and holding to move*/
//	private int firstPress = 0;
//    
//
//	/**	number of the player unit */
//	private static final int PLAYER = 1;
//	/**	number of the bug unit */
//	private static final int BUG = 16;
//	/**	number of the glider unit */
//	private static final int GLIDER = 22;
//	/**	number of the tank unit */
//	private static final int TANK = 32;
//	/**	number of the fireball unit */
//	private static final int FIREBALL = 36;
//	/**	number of the pink ball unit */
//	private static final int PINKBALL = 40;
//	/**	number of the teeth unit */
//	private static final int TEETH = 44;
//
//	/**	number of the block unit */
//	private static final int BLOCK = 26;
//
//	/**	number of the wall tile */
//	private static final int WALL = 0;
//	/**	number of the grass tile */
//	private static final int GRASS = 1;
//	/**	number of the water tile */
//	private static final int WATER = 4;
//	/**	number of the tile up tile */
//	private static final int TILEUP = 6;
//	/**	number of the tile left tile */
//	private static final int TILELEFT = 7;
//	/**	number of the tile right tile */
//	private static final int TILERIGHT = 8;
//	/**	number of the tile down tile */
//	private static final int TILEDOWN = 9;
//	/**	number of the ice tile */
//	private static final int ICE = 10;
//	/**	number of the fire tile */
//	private static final int FIRE = 15;
//	/**	number of the green button tile */
//	private static final int GREENBUTTON = 16;
//	/**	number of the blue button tile */
//	private static final int BLUEBUTTON = 23;
//	/**	number of the red button tile */
//	private static final int REDBUTTON = 26;
//	/**	number of the teleport tile */
//	private static final int TELEPORT = 38;
//	/**	number of the thief tile */
//	private static final int THIEF = 39;
//
//
//
//	@Override
//	public void onCreate(Bundle bundle) {
//		super.onCreate(bundle);
//		Bundle b = getIntent().getExtras();
//		multiplayer = b.getBoolean("multiplayer");
//		if(multiplayer) {
//			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//			if (mBluetoothAdapter == null) {
//				finish();
//	            return;
//			}
//		}
//		if(!multiplayer)
//			currentLevel = b.getInt("level");
//		
//		setVolumeControlStream(AudioManager.STREAM_MUSIC);
//	}
//    
//	/**
//	 * The method will set up the game such as locking the orientation to portrait, setting the frame rate, button and tile sizes,
//	 * loading all the images, maps and sounds required, and then calling the method startPosition()
//	 * (non-Javadoc)
//	 * @see processing.core.PApplet#setup()
//	 */
//	public void setup()
//	{
//		orientation(PORTRAIT);
//		frameRate(30);
//		tileSize = width/screenSize;
//		buttonSize = width/6;
//		
//		tileImages[WALL] = loadImage("wall.png");
//		tileImages[1] = loadImage("grass.png");
//		tileImages[2] = loadImage("wall.png");
//		tileImages[3] = loadImage("exit.png");
//		tileImages[WATER] = loadImage("water.png");
//		tileImages[5] = loadImage("grass.png");
//		tileImages[TILEUP] = loadImage("tile up.png");
//		tileImages[TILELEFT] = loadImage("tile left.png");
//		tileImages[TILERIGHT] = loadImage("tile right.png");
//		tileImages[TILEDOWN] = loadImage("tile down.png");
//		tileImages[ICE] = loadImage("ice.png");
//		tileImages[11] = loadImage("iceul.png");
//		tileImages[12] = loadImage("iceur.png");
//		tileImages[13] = loadImage("icedl.png");
//		tileImages[14] = loadImage("icedr.png");
//		tileImages[FIRE] = loadImage("fire.png");
//		tileImages[GREENBUTTON] = loadImage("green button.png");
//		tileImages[17] = loadImage("toggle block off.png");
//		tileImages[18] = loadImage("toggle block on.png");
//		tileImages[19] = loadImage("bomb.png");
//		tileImages[20] = loadImage("mud.png");
//		tileImages[21] = loadImage("trap.png");
//		tileImages[22] = loadImage("brown button.png");
//		tileImages[BLUEBUTTON] = loadImage("blue button.png");
//		tileImages[24] = loadImage("start.png");
//		tileImages[25] = loadImage("clone.png");
//		tileImages[REDBUTTON] = loadImage("red button.png");
//		tileImages[27] = loadImage("bone.png");
//		tileImages[28] = loadImage("gravel.png");
//		tileImages[29] = loadImage("blue wall.png");
//		tileImages[30] = loadImage("blue wall.png");
//		tileImages[31] = loadImage("grass.png");
//		tileImages[32] = loadImage("grass.png");
//		tileImages[33] = loadImage("hint.png");
//		tileImages[34] = loadImage("thin wall up.png");
//		tileImages[35] = loadImage("thin wall left.png");
//		tileImages[36] = loadImage("thin wall right.png");
//		tileImages[37] = loadImage("thin wall down.png");
//		tileImages[TELEPORT] = loadImage("teleport.png");
//		tileImages[THIEF] = loadImage("theif.png");
//		tileImages[40] = loadImage("recessed wall.png");
//		tileImages[41] = loadImage("green door.png");
//		tileImages[42] = loadImage("red door.png");
//		tileImages[43] = loadImage("blue door.png");
//		tileImages[44] = loadImage("yellow door.png");
//		tileImages[45] = loadImage("green key.png");
//		tileImages[46] = loadImage("red key.png");
//		tileImages[47] = loadImage("blue key.png");
//		tileImages[48] = loadImage("yellow key.png");
//		tileImages[49] = loadImage("food door.png");
//		tileImages[50] = loadImage("flippers.png");
//		tileImages[51] = loadImage("arrow boots.png");
//		tileImages[52] = loadImage("iceboots.png");
//		tileImages[53] = loadImage("fireboots.png");
//		
//		unitsImages[0] = loadImage("dog up.png");
//		unitsImages[1] = loadImage("dog up.png");
//		unitsImages[2] = loadImage("dog side final.png");
//		unitsImages[3] = loadImage("dog right.png");
//		unitsImages[4] = loadImage("dog down.png");
//		unitsImages[5] = loadImage("bone no background.png");
////		unitsImages[6] = loadImage("green door.png");
////		unitsImages[7] = loadImage("red door.png");
////		unitsImages[8] = loadImage("blue door.png");
////		unitsImages[9] = loadImage("yellow door.png");
//		unitsImages[10] = loadImage("green key no background.png");
//		unitsImages[11] = loadImage("red key no background.png");
//		unitsImages[12] = loadImage("blue key no background.png");
//		unitsImages[13] = loadImage("yellow key no background.png");
////		unitsImages[14] = loadImage("food door.png");
//		unitsImages[15] = loadImage("flippers no background.png");
//		unitsImages[BUG] = loadImage("bug up.png");
//		unitsImages[BUG+1] = loadImage("bug left.png");
//		unitsImages[BUG+2] = loadImage("bug right.png");
//		unitsImages[BUG+3] = loadImage("bug down.png");
////		unitsImages[20] = loadImage("mud.png");
//		unitsImages[21] = loadImage("arrow boots no background.png");
//		unitsImages[GLIDER] = loadImage("glider up.png");
//		unitsImages[GLIDER+1] = loadImage("glider left.png");
//		unitsImages[GLIDER+2] = loadImage("glider right.png");
//		unitsImages[GLIDER+3] = loadImage("glider down.png");
//		unitsImages[BLOCK] = loadImage("block.png");
//		unitsImages[BLOCK+1] = loadImage("block.png");
//		unitsImages[BLOCK+2] = loadImage("block.png");
//		unitsImages[BLOCK+3] = loadImage("block.png");
//		unitsImages[30] = loadImage("iceboots no background.png");
//		unitsImages[31] = loadImage("fireboots no background.png");
//		unitsImages[TANK] = loadImage("tank up.png");
//		unitsImages[TANK+1] = loadImage("tank left.png");
//		unitsImages[TANK+2] = loadImage("tank right.png");
//		unitsImages[TANK+3] = loadImage("tank down.png");
//		unitsImages[FIREBALL] = loadImage("fireball.png");
//		unitsImages[FIREBALL+1] = loadImage("fireball.png");
//		unitsImages[FIREBALL+2] = loadImage("fireball.png");
//		unitsImages[FIREBALL+3] = loadImage("fireball.png");
//		unitsImages[PINKBALL] = loadImage("ball.png");
//		unitsImages[PINKBALL+1] = loadImage("ball.png");
//		unitsImages[PINKBALL+2] = loadImage("ball.png");
//		unitsImages[PINKBALL+3] = loadImage("ball.png");
//		unitsImages[TEETH] = loadImage("teeth up.png");
//		unitsImages[TEETH+1] = loadImage("teeth left.png");
//		unitsImages[TEETH+2] = loadImage("teeth right.png");
//		unitsImages[TEETH+3] = loadImage("teeth down.png");
//		
//		arrows[0] = loadImage("arrow up.png");
//		arrows[1] = loadImage("arrow left.png");
//		arrows[2] = loadImage("arrow right.png");
//		arrows[3] = loadImage("arrow down.png");
//		arrows[4] = loadImage("arrow up glow.png");
//		arrows[5] = loadImage("arrow left glow.png");
//		arrows[6] = loadImage("arrow right glow.png");
//		arrows[7] = loadImage("arrow down glow.png");
//		
//		if(!multiplayer) {
//			maps.add(loadStrings("map1.csv"));
//			maps.add(loadStrings("map2.csv"));
//			maps.add(loadStrings("map3.csv"));
//			maps.add(loadStrings("map4.csv"));
//			maps.add(loadStrings("map5.csv"));
//			maps.add(loadStrings("map6.csv"));
//			maps.add(loadStrings("map7.csv"));
//			maps.add(loadStrings("map8.csv"));
//			maps.add(loadStrings("map9.csv"));
//			maps.add(loadStrings("map10.csv"));
//			maps.add(loadStrings("map11.csv"));
//			maps.add(loadStrings("map18.csv"));
//			maps.add(loadStrings("map20.csv"));
//			maps.add(loadStrings("map33.csv"));
//			maps.add(loadStrings("map103.csv"));
//			maps.add(loadStrings("test.csv"));
//		}
//		
//		if(multiplayer) {
//			maps.add(loadStrings("multi1.csv"));
//			maps.add(loadStrings("multi2.csv"));
//			maps.add(loadStrings("multi3.csv"));
//			maps.add(loadStrings("multi4.csv"));
//			maps.add(loadStrings("multi5.csv"));
//			maps.add(loadStrings("multi6.csv"));
//			maps.add(loadStrings("multiplayer.csv"));
//		}
//		
//		
//		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
//        soundsMap = new HashMap<Integer, Integer>();
//        soundsMap.put(SOUND1, soundPool.load(this, com.zapedudas.chip.R.raw.bummer, 1));
//        soundsMap.put(BOMB, soundPool.load(this, com.zapedudas.chip.R.raw.bomb, 1));
//        soundsMap.put(POP, soundPool.load(this, com.zapedudas.chip.R.raw.pop, 1));
//        soundsMap.put(EXIT, soundPool.load(this, com.zapedudas.chip.R.raw.exit, 1));
//        soundsMap.put(DOOR, soundPool.load(this, com.zapedudas.chip.R.raw.door, 1));
//        soundsMap.put(ITEM, soundPool.load(this, com.zapedudas.chip.R.raw.item, 1));
//        soundsMap.put(OOF, soundPool.load(this, com.zapedudas.chip.R.raw.oof, 1));
//        soundsMap.put(CHIP, soundPool.load(this, com.zapedudas.chip.R.raw.chip, 1));
//        soundsMap.put(SPLASH, soundPool.load(this, com.zapedudas.chip.R.raw.water, 1));
//        soundsMap.put(TELEPORTSOUND, soundPool.load(this, com.zapedudas.chip.R.raw.teleport, 1));
//        soundsMap.put(THIEFSOUND, soundPool.load(this, com.zapedudas.chip.R.raw.theif, 1));
//	
//		startPosition();	
//	}
//	
//	/**
//	 * This method is called every time either a new level needs to be started or the current level needs to be restarted
//	 * It will load the map of the current level from the required CSV file. It will take the 0th element for the tile type and the 1st element for the units type.
//	 * If there is a 2nd element it will use it for the direction the unit will start facing.
//	 * 
//	 */
//	private void startPosition() {
//		notified = false;
//		skip = false;
//		chipsRequired = 0;
//		map= new int[maps.get(currentLevel).length+ (2*viewSize)][maps.get(currentLevel)[0].split(",").length + (2*viewSize)];
//		unitsStart= new int[maps.get(currentLevel).length+ (2*viewSize)][maps.get(currentLevel)[0].split(",").length+ (2*viewSize)];
//		levelInformation = new String[maps.size()];
//		try {
//			levelInformation = readTxt("hints.txt")[currentLevel].split("-");
//		} catch (IOException e) {
//			
//		} catch(ArrayIndexOutOfBoundsException a) {
//			
//		}
////		levelName = levelInformation[0];
//		hint = levelInformation[1];
//		
//		for(int i = 0; i < maps.get(currentLevel).length ; i++){
//			String[] col = maps.get(currentLevel)[i].split(",");
//			for (int j = 0; j < col.length ; j++) {
//				String[] element = col[j].split(" ");
//				map[i+viewSize][j+viewSize] = Integer.parseInt(element[0]);
//				unitsStart[i+viewSize][j+viewSize] = Integer.parseInt(element[1]);
//			}
//		}
//		units = new Unit[unitsStart.length][unitsStart[0].length];
//		bugs = new LinkedList<Unit>();
//		gliders = new LinkedList<Unit>();
//		tanks = new LinkedList<Unit>();
//		fireballs = new LinkedList<Unit>();
//		balls = new LinkedList<Unit>();
//		teeth = new LinkedList<Unit>();
//		moveableUnits = new LinkedList<Unit>();
//		players = new LinkedList<Player>();
//		
//		gliderMove = new LinkedList<Integer>();
//		tankMove = new LinkedList<Integer>();
//		fireballMove = new LinkedList<Integer>();
//		ballMove = new LinkedList<Integer>();
//		npcMove = new LinkedList<Integer>();
//		
//		npcMove.add(GRASS);
//		npcMove.add(WATER);
//		npcMove.add(5);
//		npcMove.add(TILEUP);
//		npcMove.add(TILELEFT);
//		npcMove.add(TILERIGHT);
//		npcMove.add(TILEDOWN);
//		npcMove.add(ICE);
//		npcMove.add(11);
//		npcMove.add(12);
//		npcMove.add(13);
//		npcMove.add(14);
//		npcMove.add(GREENBUTTON);
//		npcMove.add(17);
//		npcMove.add(19);
//		npcMove.add(21);
//		npcMove.add(22);
//		npcMove.add(BLUEBUTTON);
//		npcMove.add(REDBUTTON);
//		npcMove.add(TELEPORT);
//		
//		gliderMove.add(FIRE);
//		tankMove.add(FIRE);
//		fireballMove.add(FIRE);
//		ballMove.add(FIRE);
//		
//		
//		
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				if (unitsStart[i][j] > 0) {
//					units[i][j] =  new Unit(this, unitsStart[i][j], i, j);
//				}
//				if (map[i][j] == 5) {
//					units[i][j] = new Unit(this, 1,i,j);
//					players.add(new Player(this,units[i][j].getY(),units[i][j].getX()));
//				}
//				if (map[i][j] == 24 && multiplayer) {
//					units[i][j] = new Unit(this, 1,i,j);
//					players.add(new Player(this,units[i][j].getY(),units[i][j].getX()));
//				}
//				if (units[i][j] != null && (units[i][j].getNumber() == PLAYER ||units[i][j].getNumber() == BUG 
//				|| units[i][j].getNumber() == GLIDER || units[i][j].getNumber() == BLOCK || units[i][j].getNumber() == TANK 
//				|| units[i][j].getNumber() == FIREBALL || units[i][j].getNumber() == PINKBALL || units[i][j].getNumber() == TEETH)) {
//					moveableUnits.add(units[i][j]);
//				}
//				if(units[i][j] != null && (units[i][j].getNumber() == GLIDER)) {
//					if (!units[i][j].isMoved()) {
//						gliders.add(units[i][j]);
//						units[i][j].setMoved(true);
//					}
//				}
//				if(units[i][j] != null && (units[i][j].getNumber() == BUG)) {
//					if (!units[i][j].isMoved()) {
//						bugs.add(units[i][j]);
//						units[i][j].setMoved(true);
//					}
//				}
//				if(units[i][j] != null && (units[i][j].getNumber() == TANK)) {
//					if (!units[i][j].isMoved()) {
//						tanks.add(units[i][j]);
//						units[i][j].setMoved(true);
//					}
//				}
//				if(units[i][j] != null && (units[i][j].getNumber() == FIREBALL)) {
//					if (!units[i][j].isMoved()) {
//						fireballs.add(units[i][j]);
//						units[i][j].setMoved(true);
//					}
//				}
//				if(units[i][j] != null && (units[i][j].getNumber() == PINKBALL)) {
//					if (!units[i][j].isMoved()) {
//						balls.add(units[i][j]);
//						units[i][j].setMoved(true);
//					}
//				}
//				if(units[i][j] != null && (units[i][j].getNumber() == TEETH)) {
//					if (!units[i][j].isMoved()) {
//						teeth.add(units[i][j]);
//						units[i][j].setMoved(true);
//					}
//				}
//			}
//		}
//		moveableUnits.add(players.get(currentPlayer));
//		if(multiplayer)
//			moveableUnits.add(players.get(otherPlayer));
//		for(int i = 0; i < maps.get(currentLevel).length; i++){
//			String[] col = maps.get(currentLevel)[i].split(",");
//			for (int j = 0; j < col.length; j++) {
//				String[] element = col[j].split(" ");
//				if (element.length > 2) {
//					if (map[i+viewSize][j+viewSize] == 49)
//						chipsRequired = Integer.parseInt(element[2]);
//					else
//						units[i+viewSize][j+viewSize].setOrientation(Integer.parseInt(element[2]));
//				}
//			}
//		}
//		removeItems();
//	}
//
//	/**
//	 * This method will be called on every frame that this activity is active. It sets the background colour, draws the map around the current player,
//	 * draws the arrow control buttons and the information box. The information box contains the players inventory and will show a hint if the player is 
//	 * standing on a hint tile. The method will then move any non-player character (NPC) if there are any every 10 frames. 
//	 * After this, the method loops through all movable units, including the player, blocks and NPC's and checks what type of tile they are on,
//	 * and does the appropriate action based on that. The method will then update the position of all the units. Lastly, it will skip or restart the level if necessary
//	 * (non-Javadoc)
//	 * @see processing.core.PApplet#draw()
//	 */
//	public void draw()
//	{
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
//	}
//	
//
//	/**
//	 * The results of pressing on the screen. Currently only the arrow direction buttons do anything
//	 * (non-Javadoc)
//	 * @see processing.core.PApplet#mousePressed()
//	 */
//	public void mousePressed() {
//		notified = false;
//		if(map[players.get(currentPlayer).getY()][players.get(currentPlayer).getX()] < 10 
//		|| map[players.get(currentPlayer).getY()][players.get(currentPlayer).getX()] > 14 || players.get(currentPlayer).hasIceBoots()) {
//			if (mouseX >= 10+buttonSize && mouseX <= 10+(2*buttonSize) && mouseY >= height - (3*buttonSize+60) && mouseY <= height - (2*buttonSize+60)) {
//				move(0);
//				image(arrows[4],10 + buttonSize,height - (3*buttonSize+60),buttonSize, buttonSize);
//			}
//			
//			if (mouseX >= 10 && mouseX <= 10+buttonSize && mouseY >= height - (2*buttonSize+60) && mouseY <= height - (buttonSize+60)) {
//				move(1);
//				image(arrows[5],10,height - (2*buttonSize+60),buttonSize, buttonSize);
//			}
//			
//			if (mouseX >= 10+(2*buttonSize) && mouseX <= 10+(3*buttonSize) && mouseY >= height - (2*buttonSize+60) && mouseY <= height - (buttonSize+60)) {
//				move(2);
//				image(arrows[6],10+(2*buttonSize),height - (2*buttonSize+60),buttonSize, buttonSize);
//			}
//			
//			if (mouseX >= 10+(buttonSize) && mouseX <= 10+(2*buttonSize) && mouseY >= height - (buttonSize+60) && mouseY <= height - 60) {
//				move(3);
//				image(arrows[7],10+buttonSize,height - (buttonSize+60),buttonSize, buttonSize);
//			}
//		}
//		firstPress  = frameCount;
//	}
//	
//	public void mouseReleased() {
//		firstPress = -1;
//	}
//
//	/**
//	 * This method converts the direction into a Y value
//	 * @param direction (0,1,2,3)
//	 * @return Y value (-1,1,0)
//	 */
//	private int getY(int direction) {
//		if(direction == 0)
//			return -1;
//		if(direction == 3)
//			return 1;
//		return 0;
//	}
//	/**
//	 * This method converts the direction into an X value
//	 * @param direction (0,1,2,3)
//	 * @return X value (-1,1,0)
//	 */
//	private int getX(int direction) {
//		if(direction == 1)
//			return -1;
//		if(direction == 2)
//			return 1;
//		return 0;
//	}
//	
//	/**
//	 * This method will check the map for a brown button, to see whether or not the unit in question can move from the trap. 
//	 * The method will search in reverse reading order for a brown button with a unit on it. If it finds another trap or a brown button without a unit on it
//	 * or no button or trap at all, it will not allow the unit on the trap to move. If it finds a brown button with a unit on it, it will allow the unit to move
//	 * @param unit The unit on the trap
//	 * @return true if the unit is trapped, false if it can move
//	 */
//	private boolean findBrownButton(Unit unit) {
//		int mapH = map.length;
//		int mapW = map[0].length;
//		int start = unit.getY()*mapW + unit.getX();
//		int i = start-1;
//		
//		while (i != start) {
//			if (map[i/mapW][i%mapW] == 21) {
//				return true;
//			}
//			if (map[i/mapW][i%mapW] == 22 && units[i/mapW][i%mapW] != null) {
//				return false;
//			}
//			if (map[i/mapW][i%mapW] == 22 && units[i/mapW][i%mapW] == null) {
//				return true;
//			}
//			if (i == 0) i = mapW * mapH;
//			i--;
//		}
//		return true;
//	}
//	
//	/**
//	 * This method will search the map in reading order for a clone machine and clone the unit that is on it
//	 * @param startY The Y position of the unit pressing the red button
//	 * @param startX The X position of the unit pressing the red button
//	 */
//	private void cloneMachine(int startY, int startX) {
//		int mapH = map.length;
//		int mapW = map[0].length;
//		int start = startY*mapW + startX;
//		int i = start+1;
//		
//		while (i != start) {
//			int y = i/mapW;
//			int x = i%mapW;
//			if (map[y][x] == 25) {
//				int dir = units[y][x].getOrientation();
//				int ox = getX(dir);
//				int oy = getY(dir);
//				Unit now = units[y][x];
//				if (units[y+oy][x+ox] == null) {
//					Unit temp = new Unit(this, now.getNumber(), y+oy, x+ox);
//					temp.setOrientation(dir);
//					moveableUnits.add(0,temp);
//					if (now.getNumber() == BUG) {
//						bugs.add(0,temp);
//					} else if (now.getNumber() == GLIDER) {
//						gliders.add(0,temp);
//					} else if (now.getNumber() == TANK) {
//						tanks.add(0,temp);
//					} else if (now.getNumber() == FIREBALL) {
//						fireballs.add(0,temp);
//					} else if (now.getNumber() == PINKBALL) {
//						balls.add(0,temp);
//					} else if (now.getNumber() == TEETH) {
//						teeth.add(0,temp);
//					}
//					units[y+oy][x+ox] = temp;
//				}
//				break;
//			}
//			i++;
//			if (i == mapW * mapH) i = 0;
//		}
//	}	
//	
//	/**
//	 * This method will search the map in reverse reading order for a teleporter and will teleport the unit there
//	 * and move it in the same direction that it entered the teleporter. If that direction is blocked, it will take the unit to the next teleporter
//	 * @param unit The unit entering the teleporter
//	 */
//	private void teleport(Unit unit) {
//		int mapH = map.length;
//		int mapW = map[0].length;
//		int start = unit.getY()*mapW + unit.getX();
//		int i = start-1;
//		
//		while (i != start) {
//			if (map[i/mapW][i%mapW] == TELEPORT) {
//				unit.setPosition(units, i/mapW, i%mapW, unit.getOrientation());
//				if(canMove(unit,unit.getOrientation())) {
//					unit.move(units, unit.getOrientation());
//					playSound(TELEPORTSOUND,1);
//				}
//				break;
//			}
//			if (i == 0) i = mapW * mapH;
//			i--;
//		}
//	}
//
//	/**
//	 * This method specifies whether or not a unit can move in a given direction
//	 * @param unit The unit in question
//	 * @param direction The direction that the unit in questions wants to move
//	 * @return true if the unit can move in that direction and false if it cannot
//	 */
//	public boolean canMove(Unit unit, int direction) {
//		int y = getY(direction);
//		int x = getX(direction);
//		if(unit != null && map[unit.getY()][unit.getX()] == 21 && findBrownButton(unit)) {
//			return false;
//		}
//		if(direction == 0 && (map[unit.getY()][unit.getX()] == 34 || map[unit.getY()][unit.getX()] == 11 || map[unit.getY()][unit.getX()] == 12
//		|| map[unit.getY() + y][unit.getX() + x] == 37 || map[unit.getY() + y][unit.getX() + x] == 13 || map[unit.getY() + y][unit.getX() + x] == 14))
//			return false;
//		if(direction == 1 && (map[unit.getY()][unit.getX()] == 35 || map[unit.getY()][unit.getX()] == 11 || map[unit.getY()][unit.getX()] == 13
//		|| map[unit.getY() + y][unit.getX() + x] == 36 || map[unit.getY() + y][unit.getX() + x] == 12 || map[unit.getY() + y][unit.getX() + x] == 14))
//			return false;
//		if(direction == 2 && (map[unit.getY()][unit.getX()] == 36 || map[unit.getY()][unit.getX()] == 12 || map[unit.getY()][unit.getX()] == 14
//		|| map[unit.getY() + y][unit.getX() + x] == 35|| map[unit.getY() + y][unit.getX() + x] == 11 || map[unit.getY() + y][unit.getX() + x] == 13))
//			return false;
//		if(direction == 3 && (map[unit.getY()][unit.getX()] == 37 || map[unit.getY()][unit.getX()] == 13 || map[unit.getY()][unit.getX()] == 14
//		|| map[unit.getY() + y][unit.getX() + x] == 34|| map[unit.getY() + y][unit.getX() + x] == 11 || map[unit.getY() + y][unit.getX() + x] == 12))
//			return false;
//		if(map[unit.getY()][unit.getX()] == 25)
//			return false;
//		if(map[unit.getY()+y][unit.getX()+x] == 25)
//			return false;
//		switch(unit.getNumber()) {
//			case 1:
//				if(!isWall(unit, direction) && !isLockedDoor(map[unit.getY()+y][unit.getX()+x]) 
//				&& !isBlueWall(unit.getY()+y,unit.getX()+x) && !isInvisibleWall(unit.getY()+y,unit.getX()+x)) {
//					if(units[unit.getY()+y][unit.getX()+x] == null)
//						return true;
//					if(units[unit.getY()+y][unit.getX()+x] != null && units[unit.getY()+y][unit.getX()+x].getNumber() != 1)
//						return true;
//				}
//			break;
//			case BUG:
//				if(npcMove.contains(map[unit.getY() + y][unit.getX() + x])
//				&& !isBlock(units[unit.getY() + y][unit.getX() + x]) && !isEnemy(units[unit.getY() + y][unit.getX() + x]))
//					return true;
//			break;
//			case GLIDER:
//				if((npcMove.contains(map[unit.getY() + y][unit.getX() + x]) || gliderMove.contains(map[unit.getY() + y][unit.getX() + x]))
//				&& !isBlock(units[unit.getY() + y][unit.getX() + x]) && !isEnemy(units[unit.getY() + y][unit.getX() + x]))
//					return true;
//			break;
//			case BLOCK:
//				if (units[unit.getY()+y][unit.getX()+x] != null && units[unit.getY()+y][unit.getX()+x].getNumber() == BLOCK)
//					return false;
//				if (!isWall(unit,direction) && map[unit.getY()+y][unit.getX()+x] != 20)
//					return true;
//			break;
//			case TANK:
//				if((npcMove.contains(map[unit.getY() + y][unit.getX() + x]) || tankMove.contains(map[unit.getY() + y][unit.getX() + x]))
//				&& !isBlock(units[unit.getY() + y][unit.getX() + x]) && !isEnemy(units[unit.getY() + y][unit.getX() + x]))
//					return true;
//			break;
//			case FIREBALL:
//				if((npcMove.contains(map[unit.getY() + y][unit.getX() + x]) || fireballMove.contains(map[unit.getY() + y][unit.getX() + x]))
//				&& !isBlock(units[unit.getY() + y][unit.getX() + x]) && !isEnemy(units[unit.getY() + y][unit.getX() + x]))
//					return true;
//			break;
//			case PINKBALL:
//				if((npcMove.contains(map[unit.getY() + y][unit.getX() + x]) || ballMove.contains(map[unit.getY() + y][unit.getX() + x]))
//				&& !isBlock(units[unit.getY() + y][unit.getX() + x]) && !isEnemy(units[unit.getY() + y][unit.getX() + x]))
//					return true;
//			break;
//			case TEETH:
//				if((npcMove.contains(map[unit.getY() + y][unit.getX() + x]) || npcMove.contains(map[unit.getY() + y][unit.getX() + x]))
//				&& !isBlock(units[unit.getY() + y][unit.getX() + x]) && !isEnemy(units[unit.getY() + y][unit.getX() + x]))
//					return true;
//			break;
//			}
//		return false;
//	}
//
//	/**
//	 * Moves the player in a direction if they can move
//	 * @param direction The direction that the player wants to move (0,1,2,3)
//	 */
//	public void move(int direction) {
//		if(canMove(players.get(currentPlayer),direction))
//			players.get(currentPlayer).move(units,direction);
//		else
//			playSound(OOF, 1);
//		players.get(currentPlayer).setOrientation(direction);
//		if(multiplayer)
//			sendMessage(direction+"");
//	}
//	
//	/**
//	 * Returns whether or not a unit is a block
//	 * @param unit The unit in question
//	 * @return true if it is a block and false if it is not
//	 */
//	public boolean isBlock(Unit unit) {
//		if (unit == null) return false;
//		return unit.getNumber() == BLOCK;
//	}
//
//	/**
//	 * Returns whether or not a unit is an enemy
//	 * @param unit The unit in question
//	 * @return true if it is an enemy and false if it is not
//	 */
//	public boolean isEnemy(Unit unit) {
//		if (unit == null) return false;
//		return unit.getNumber() == BUG || unit.getNumber() == GLIDER || unit.getNumber() == TANK || unit.getNumber() == FIREBALL || unit.getNumber() == PINKBALL || unit.getNumber() == TEETH;
//	}
//	
//	/**
//	 * Pushes a block in a given direction
//	 * @param block The block to push
//	 * @param direction the direction to push it
//	 */
//	public void pushBlock(Unit block, int direction) {
//		if(canMove(block,direction)) {
//			block.move(units, direction);
//		}
//	}
//	
//	/**
//	 * Removes all items from a player
//	 */
//	public void removeItems() {
//		players.get(currentPlayer).removeItems();
//	}
//	
//	/**
//	 * Counts all the remaining chips on the map and returns that if chips required is 0, otherwise it will return the remaining chips
//	 * @return remaining chips
//	 */
//	private int chipsRemaining() {
//		int number = 0;
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				if (map[i][j]  == 27) {
//					number += 1;
//				}
//			}
//		}
//		if(chipsRequired == 0)
//			return number;
//		if(chipsRequired - players.get(currentPlayer).numChips() > 0)
//			return chipsRequired - players.get(currentPlayer).numChips();
//		else return 0;
//	}
//
//	/**
//	 * Method for when the player walks into a blue wall. It will turn into a normal wall
//	 * @param y
//	 * @param x
//	 * @return
//	 */
//	private boolean isBlueWall(int y, int x) {
//		if(map[y][x] == 29) {
//			map[y][x] = WALL;
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * Method for when the player walks into an invisible wall. It will turn into a normal wall
//	 * @param y
//	 * @param x
//	 * @return
//	 */
//	private boolean isInvisibleWall(int y, int x) {
//		if(map[y][x] == 31)
//			return true;
//		if(map[y][x] == 32) {
//			map[y][x] = WALL;
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * Method for determining if the tile that the unit is walking into is a wall or not.
//	 * @param unit
//	 * @param direction
//	 * @return
//	 */
//	public boolean isWall(Unit unit, int direction) {
//		int y = getY(direction);
//		int x = getX(direction);
//		if(map[unit.getY() + y][unit.getX() + x]  == 25)
//			return true;
//		if (units[unit.getY() + y][unit.getX() + x] == null ) {
////			Log.d("isWall", "Return 1");
//			return map[unit.getY() + y][unit.getX() + x] == WALL || map[unit.getY() + y][unit.getX() + x]  == 2 
//			|| map[unit.getY() + y][unit.getX() + x]  == 18 || map[unit.getY() + y][unit.getX() + x]  == 25;
//		}
//		if (units[unit.getY() + 2*y][unit.getX() +2*x] == null) {
////			Log.d("isWall", "Return 2");
//			return units[unit.getY() + y][unit.getX() + x].getNumber() == BLOCK && !canMove(units[unit.getY() + y][unit.getX() + x],direction) ;
//		}
//		if (units[unit.getY() + y][unit.getX() + x] != null && units[unit.getY() + y][unit.getX() + x].getNumber() == BLOCK 
//		&& (units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == BUG || units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == GLIDER
//		|| units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == BLOCK || units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == TANK 
//		|| units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == FIREBALL || units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == PINKBALL
//		|| units[unit.getY() + 2*y][unit.getX() +2*x].getNumber() == TEETH
//		|| map[unit.getY() + 2*y][unit.getX() +2*x] == WALL || map[unit.getY() + 2*y][unit.getX() +2*x]  == 2 || map[unit.getY() + 2*y][unit.getX() +2*x]  == 18 
//		|| map[unit.getY() + 2*y][unit.getX() +2*x]  == 20 || map[unit.getY() + 2*y][unit.getX() +2*x]  == 25)) {
////			Log.d("isWall", "Return 3");
//			return true;
//		}
////		Log.d("isWall", "Return 4");
//		return false;
//	}
//	
//	/**
//	 * Method for handling what happens when a unit steps on fire. 
//	 * @param unit
//	 */
//	private void atFire(Unit unit) {
//		if(unit == players.get(currentPlayer)) {
//			if (!players.get(currentPlayer).hasFireBoots())
//			dead(2);
//		}
//	}
//	/**
//	 * Method for handling what happens when a unit steps on water. 
//	 * @param unit
//	 */
//	private void atWater(Unit unit) {
//		if(unit == players.get(currentPlayer)) {
//			if (!players.get(currentPlayer).hasFlippers())
//			dead(1);
//		}
//		else if (unit.getNumber() == BUG || unit.getNumber() == TANK || unit.getNumber() == FIREBALL || unit.getNumber() == PINKBALL || unit.getNumber() == TEETH) {
//			unit.kill(bugs);
//			unit.kill(fireballs);
//			unit.kill(balls);
//			unit.kill(tanks);
//			unit.kill(teeth);
////			units[unit.getY()][unit.getX()].setNumber(0);
//			units[unit.getY()][unit.getX()] = null;
////			units[unit.getY()][unit.getX()].setOrientation(0);
////			unit = null;
//		}
//	}
//
//	/**
//	 * Method for handling what happens when a unit steps onto the exit.
//	 */
//	private void atExit() {
//		if(!multiplayer) {
//			playSound(EXIT, 1);
//			finishLevel();
//		}
//		else {
//			for (int i = 0; i < map.length; i++) {
//				for (int j = 0; j < map[i].length; j++) {
//					if (map[i][j] == 3 && units[i][j] == null)
//						return;
//				}
//			}
//			playSound(EXIT, 1);
//			finishLevel();
//		}
//	}
//
//	/**
//	 * Method for handling the ending of a level. If the next level is the highest level that the player has reached, it will write it to the maxlevel file
//	 */
//	private void finishLevel() {
//		if(currentLevel != maps.size() - 1) {
//			currentLevel += 1;
//		}
//		else {
//			currentLevel = 0;
//		}
//		if(!multiplayer) {
//			if(currentLevel > Integer.parseInt(readFile(this, "maxLevel.txt")[0]))
//				writeFile(this,"maxLevel.txt",currentLevel+"");
//		}
//		startPosition();
//	}
//
//	/**
//	 * Method for handling player death
//	 * @param how Message to display to the player in a toast
//	 */
//	public void dead(int how) {
//		switch(how){
//		case 0:
//			message = "Ooops! Watch out for monsters!";
//		break;
//		case 1:
//			message = "You can't swim without flippers!";
//		break;
//		case 2:
//			message = "You can't walk on fire without fireboots!";
//		break;
//		}
//			if (!notified) {
//				this.runOnUiThread(new Runnable() {
//					public void run() {
//						Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();	
//					}
//				});
//				notified = true;
//			}
//		playSound(SOUND1, 1);
//		startPosition();
//	}
//	
//	/**
//	 * Method for when the player tries to walk into a locked door. If the player has the correct key, it will use the key and let them through
//	 * @param tile
//	 * @return true if the door is still locked. False if not
//	 */
//	private boolean isLockedDoor(int tile) {
//		if(tile >= 41 && tile <= 44 || tile == 49) {
//			switch(tile) {
//			case 41:
//				if(players.get(currentPlayer).hasGreenKey()) {
//					players.get(currentPlayer).useGreenKey();
//					playSound(DOOR, 1);
//					return false;
//				}
//			break;
//			case 42:
//				if(players.get(currentPlayer).hasRedKey()) {
//					players.get(currentPlayer).useRedKey();
//					playSound(DOOR, 1);
//					return false;
//				}
//			break;
//			case 43:
//				if(players.get(currentPlayer).hasBlueKey()) {
//					players.get(currentPlayer).useBlueKey();
//					playSound(DOOR, 1);
//					return false;
//				}
//			break;
//			case 44:
//				if(players.get(currentPlayer).hasYellowKey()) {
//					players.get(currentPlayer).useYellowKey();
//					playSound(DOOR, 1);
//					return false;
//				}
//			break;
//			case 49:
//				if(chipsRemaining() == 0) {
//					return false;
//				}
//			break;
//			}
//			return true; 
//		}
//		else
//		return false;
//	}
//	
//
//	/**
//	 * Method for handling all the functions of the tiles. 
//	 * @param u
//	 */
//    public void tileFunction(Unit u) {
//		switch (map[u.getY()][u.getX()]) {
//		case 3:
//			if(u.getNumber() == PLAYER)
//			atExit();
//		break;
//		case WATER:
//			atWater(u);
//			if (u.getNumber() == BLOCK) {
//				map[u.getY()][u.getX()] = 20;
//				u.setOrientation(0);
//				units[u.getY()][u.getX()] = null;
//				playSound(SPLASH, 1);
//			}
//		break;
//		case GREENBUTTON:
//			if(u.getPx() != u.getX() || u.getPy() != u.getY())
//				playSound(POP, 1);
//			for(int j = 0; j < map.length; j++) {
//				for(int k = 0; k < map[j].length;k++) {
//					if(u.getPx() != u.getX() || u.getPy() != u.getY()) {
//						if(map[j][k] == 17) {
//							map[j][k] = 18;
//						}
//						else if(map[j][k] == 18) {
//							map[j][k] = 17;
//						}
//					}
//				}
//			}
//		break;
//		case 19:
//			map[u.getY()][u.getX()] = 1;
//			if(u.getNumber() == BUG)
//				units[u.getY()][u.getX()].kill(bugs);
//			if(u.getNumber() == GLIDER)
//				units[u.getY()][u.getX()].kill(gliders);
//			if(u.getNumber() == TANK)
//				units[u.getY()][u.getX()].kill(tanks);
//			if(u.getNumber() == FIREBALL)
//				units[u.getY()][u.getX()].kill(fireballs);
//			if(u.getNumber() == PINKBALL)
//				units[u.getY()][u.getX()].kill(balls);
//			if(u.getNumber() == TEETH)
//				units[u.getY()][u.getX()].kill(teeth);
//			units[u.getY()][u.getX()] = null;
//			playSound(BOMB, 1);
//		break;
//		case 20:
//			if (u.getNumber() != BLOCK) {
//				map[u.getY()][u.getX()] = 1;
//			}
//		break;
//		case 22:
//			if (u.getPx() != u.getX() || u.getPy() != u.getY()) {
//				playSound(POP, 1);
//			}
//		break;
//		case BLUEBUTTON:
//			if(u.getPx() != u.getX() || u.getPy() != u.getY()) {
//				for (int a = 0; a < tanks.size(); a++) {
//					tanks.get(a).setOrientation(3 - tanks.get(a).getOrientation());
//				}
//				playSound(POP, 1);
//			}
//		break;
//		case REDBUTTON:
//			if (u.getPx() != u.getX() || u.getPy() != u.getY()) {
//				cloneMachine(u.getY(),u.getX());
//				playSound(POP, 1);
//			}
//		break;
//		case 27:
//			if(u.getNumber() == 1) {
//				map[u.getY()][u.getX()] = 1;
//				players.get(currentPlayer).getChip();
//				playSound(CHIP, 1);
//			}
//		break;
//		case 30:
//			map[u.getY()][u.getX()] = 1;
//		break;
//		case 33:
//			showHint = true;
//		break;
//		case THIEF:
//			if(u.getPx() != u.getX() || u.getPy() != u.getY()) {
//				((Player) u).loseBoots();
//				playSound(THIEFSOUND,1);
//			}
//		break;
//		case 40:
//			map[u.getY()][u.getX()] = WALL;
//		break;
//		case 41:
//			map[u.getY()][u.getX()] = GRASS;
//		break;
//		case 42:
//			map[u.getY()][u.getX()] = GRASS;
//		break;
//		case 43:
//			map[u.getY()][u.getX()] = GRASS;
//		break;
//		case 44:
//			map[u.getY()][u.getX()] = GRASS;
//		break;
//		case 45:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getGreenKey();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 46:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getRedKey();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 47:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getBlueKey();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 48:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getYellowKey();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 49:
//			map[u.getY()][u.getX()] = GRASS;
//		break;
//		case 50:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getFlippers();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 51:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getForceBoots();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 52:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getIceBoots();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		case 53:
//			if(u.getNumber() == 1) {
//				players.get(currentPlayer).getFireBoots();
//				playSound(ITEM, 1);
//				map[u.getY()][u.getX()] = GRASS;
//			}
//		break;
//		}
//    }
//    
//    /**
//     * Method for handling monster movement
//     * @param npc
//     */
//	private void npc(Unit npc) {
//		if(npc == null) return;
//		switch(npc.getNumber()) {
//			case BUG:
//				int[][] bug = 
//					{{1,0,2,3},{3,1,0,2},{0,2,3,1},{2,3,1,0}};
//				for (int i = 0; i < bug.length; i++) {
//					if (canMove(npc,bug[npc.getOrientation()][i])) {
//						npc.move(units, bug[npc.getOrientation()][i]);
//						break;
//					}
//				}
//				break;
//			case GLIDER:
//				int[][] glider = 
//					{{0,1,2,3},{1,3,0,2},{2,0,3,1},{3,2,1,0}};
//				for (int i = 0; i < 4; i++) {
//					if (canMove(npc,glider[npc.getOrientation()][i])) {
//						npc.move(units, glider[npc.getOrientation()][i]);
//						break;
//					}
//				}
//				break;
//			case TANK:
//				if (canMove(npc,npc.getOrientation())) {
//					npc.move(units, npc.getOrientation());
//				}
//				break;
//			case FIREBALL:
//				int[][] fireball = 
//					{{0,2,1,3},{1,0,3,2},{2,3,0,1},{3,1,2,0}};
//				for (int i = 0; i < 4; i++) {
//					if (canMove(npc,fireball[npc.getOrientation()][i])) {
//						npc.move(units, fireball[npc.getOrientation()][i]);
//						break;
//					}
//				}
//				break;
//			case PINKBALL:
//				if (!canMove(npc,npc.getOrientation())) 
//					npc.setOrientation(3 - npc.getOrientation());
//				if (canMove(npc,npc.getOrientation())) 
//					npc.move(units, npc.getOrientation());
//				break;
//			case TEETH:
//				int takeDir = 0;
//				int faceDir = 0;
//				int dist = abs(players.get(currentPlayer).getX() - npc.getX()) + abs(players.get(currentPlayer).getY() - npc.getY());
//				for (int i = 0; i < 4; i++) {
//					int ox = getX(i);
//					int oy = getY(i);
//					int newDist = abs(players.get(currentPlayer).getX() - (npc.getX() + ox)) + abs(players.get(currentPlayer).getY() - (npc.getY() + oy));
//					if (newDist < dist) {
//						if (canMove(units[npc.getY()][npc.getX()],i)) {
//							dist = newDist;
//							takeDir = i;
//						}
//						faceDir = i;
//					}
//				}
//				if (dist == abs(players.get(currentPlayer).getX() - npc.getX()) + abs(players.get(currentPlayer).getY() - npc.getY())) {
//					npc.setOrientation(faceDir);
//				}
//				if (dist < abs(players.get(currentPlayer).getX() - npc.getX()) + abs(players.get(currentPlayer).getY() - npc.getY())) {
//					npc.setOrientation(takeDir);
//					if (canMove(npc,npc.getOrientation())) 
//						npc.move(units, npc.getOrientation());
//				}
//				break;
//		}
//		if(npc.getX() == players.get(currentPlayer).getX() && npc.getY() == players.get(currentPlayer).getY())
//			dead(0);
//	}
//	
//	/**
//	 * Method to play sounds
//	 * @param sound
//	 * @param fSpeed
//	 */
//	public void playSound(int sound, float fSpeed) {
//        AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
//        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        float volume = streamVolumeCurrent / streamVolumeMax;  
// 
//        soundPool.play(soundsMap.get(sound), volume, volume, 1, 0, fSpeed);
//       }
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//	    MenuInflater inflater = getMenuInflater();
//	    if (!multiplayer)
//	    	inflater.inflate(com.zapedudas.chip.R.layout.game_menu, menu);
//	    if (multiplayer)
//	    	inflater.inflate(com.zapedudas.chip.R.layout.multiplayer_menu, menu);
//	    return true;
//	}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if(!multiplayer) {
//		    switch (item.getItemId()) {
//		        case com.zapedudas.chip.R.id.restart:
//		        	restart = true;
//		            return true;
//		        case com.zapedudas.chip.R.id.skip:
//		        	skip = true;
//		            return true;
//		        default:
//		            return super.onOptionsItemSelected(item);
//		    }
//		} else {
//			switch (item.getItemId()) {
//		        case com.zapedudas.chip.R.id.restart:
//		        	restart = true;
//		            return true;
//		        case com.zapedudas.chip.R.id.skip:
//		        	skip = true;
//		            return true;
//		        case com.zapedudas.chip.R.id.host:
//		    		ensureDiscoverable();
//		    		currentPlayer = 0;
//		            otherPlayer = 1;
//		            startPosition();
//		            return true;
//		        case com.zapedudas.chip.R.id.join:
//		    		ensureDiscoverable();
//		        	Intent serverIntent = new Intent(this, DeviceListActivity.class);
//		            startActivityForResult(serverIntent, REQUEST_CONNECT);
//		            startPosition();
//		            return true;
//		        default:
//		            return super.onOptionsItemSelected(item);
//			}
//		}
//	}
//	
//	/**
//	 * Writes to a file
//	 * @param context
//	 * @param filename
//	 * @param output
//	 */
//	public static void writeFile(Context context, String filename, String output) {
//		try {
//			FileOutputStream fos = context.openFileOutput(filename, 0);
//			fos.write(output.getBytes());
//			fos.close();
//		} catch (IOException e) {
//			
//		}
//	}
//	
//	/**
//	 * Reads a file
//	 * @param context
//	 * @param filename
//	 * @return
//	 */
//	public static String[] readFile(Context context, String filename) {
//		InputStream is = null;
//		int ch;
//		StringBuffer strContent = new StringBuffer("");
//		try {
//			is = new BufferedInputStream(context.openFileInput(filename));
//			while ((ch = is.read()) != -1) {
//				strContent.append((char) ch);
//			}
//			is.close();
//			String[] r = strContent.toString().split("\n");
//			return r;
//		} catch (IOException e) {
//			
//		}
//		return new String[] {"0"};
//	}
//	
//	/**
//	 * Reads from a file in the assets folder
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public String[] readTxt(String file) throws IOException {
//		InputStream inputStream = getAssets().open(file);
//	    System.out.println(inputStream);
//	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//	
//	    int i;
//	    try {
//	    	i = inputStream.read();
//	    	while (i != -1)
//	    	{
//	    		byteArrayOutputStream.write(i);
//	    		i = inputStream.read();
//	    	}
//	    	inputStream.close();
//	    	String[] r = byteArrayOutputStream.toString().split("\n");
//		    return r;
//	    } catch (IOException e) {
//	    }
//	    return null;
//   }
//	
//	@Override
//	public void onStart() {
//		super.onStart();
//		if (multiplayer) {
//	        if (!mBluetoothAdapter.isEnabled()) {
//	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//	            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//	        } else {
//	            if (mChatService == null) setupBluetooth();
//	        }
//		}
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (multiplayer && mChatService != null) {
//            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
//              mChatService.start();
//            }
//        }
//	}
//	
//	/**
//	 * Sets up the bluetooth for multiplayer
//	 */
//	private void setupBluetooth() {
//		mChatService = new BluetoothChatService(this, new Handler() {
//	        @Override
//	        public void handleMessage(Message msg) {
//	            switch (msg.what) {
//	            case MESSAGE_STATE_CHANGE:
//	                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
//	                switch (msg.arg1) {
//	                case BluetoothChatService.STATE_CONNECTED:
//	                    break;
//	                case BluetoothChatService.STATE_CONNECTING:
//	                    break;
//	                case BluetoothChatService.STATE_LISTEN:
//	                case BluetoothChatService.STATE_NONE:
//	                    break;
//	                }
//	                break;
//	            case MESSAGE_WRITE:
//	                break;
//	            case MESSAGE_READ:
//	                byte[] readBuf = (byte[]) msg.obj;
//	                String readMessage = new String(readBuf, 0, msg.arg1);
//	                if(canMove(players.get(otherPlayer),Integer.parseInt(readMessage)))
//						players.get(otherPlayer).move(units,Integer.parseInt(readMessage));
//					players.get(otherPlayer).setOrientation(Integer.parseInt(readMessage));
//	                break;
//	            case MESSAGE_DEVICE_NAME:
//	                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
//	                Toast.makeText(getApplicationContext(), "Connected to "
//	                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//	                break;
//	            case MESSAGE_TOAST:
//	                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
//	                               Toast.LENGTH_SHORT).show();
//	                break;
//	            }
//	        }
//	    });
//		
//	}
//	
//	/**
//	 * Sends a message to the other player in multiplayer
//	 * @param message
//	 */
//	public void sendMessage(String message) {
//        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
//        	this.runOnUiThread(new Runnable() {
//				public void run() {
//					Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT).show();	
//				}
//			});
//            return;
//        }
//        if (message.length() > 0) {
//            byte[] send = message.getBytes();
//            mChatService.write(send);
//        }
//    }
//	
//	/**
//	 * Ensures that the current device is discoverable to the other player in multiplayer
//	 */
//	private void ensureDiscoverable() {
//        if(D) Log.d(TAG, "ensure discoverable");
//        if (mBluetoothAdapter.getScanMode() !=
//            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//            startActivity(discoverableIntent);
//        }
//    }
//	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(D) Log.d(TAG, "onActivityResult " + resultCode);
//        switch (requestCode) {
//        case REQUEST_CONNECT:
//            if (resultCode == Activity.RESULT_OK) {
//                connectDevice(data, false);
//                currentPlayer = 1;
//	            otherPlayer = 0;
//	            startPosition();
//            }
//            break;
//        case REQUEST_ENABLE_BT:
//            if (resultCode == Activity.RESULT_OK) {
//                setupBluetooth();
//            } else {
//                Log.d(TAG, "BT not enabled");
//                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//
//	/**
//	 * Method for connecting devices for multiplayer
//	 * @param data
//	 * @param secure
//	 */
//    private void connectDevice(Intent data, boolean secure) {
//        String address = data.getExtras()
//            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//        mChatService.connect(device, secure);
//    }
//    
//}