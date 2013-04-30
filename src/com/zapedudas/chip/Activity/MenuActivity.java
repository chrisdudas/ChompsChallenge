package com.zapedudas.chip.Activity;

import com.zapedudas.chip.R;
import com.zapedudas.chip.Map.Levels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
//	private Button newGame;
	/**
	 * Button to take the player to the level selection activity
	 */
	private Button play;
//	private Button bluetooth;
	/**
	 * Button to take the player to the multiplayer
	 */
	private Button multiplayer;
	private Button instructions;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setUpViews();
    }

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * Method for setting up the views in this activity
	 */
	private void setUpViews() {
//		newGame = (Button)findViewById(R.id.new_game);
		play = (Button)findViewById(R.id.resume_game);
		instructions = (Button)findViewById(R.id.instructions);
		multiplayer = (Button)findViewById(R.id.multiplayer);
		
		
		play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, LevelScreen.class);
				intent.putExtra("levelFile", Levels.levels[0]);
				startActivity(intent);
			}
		});
		
//		multiplayer.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				Intent intent = new Intent(MenuActivity.this, ChipActivity.class);
//				Bundle b = new Bundle();
//				b.putBoolean("multiplayer", true);
//				intent.putExtras(b);
//				startActivity(intent);
//			}
//		});
//		
//		instructions.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				Intent intent = new Intent(MenuActivity.this, InstructionsActivity.class);
//				startActivity(intent);
//			}
//		});
	}
}
