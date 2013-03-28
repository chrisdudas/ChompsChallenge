package com.zapedudas.chip;

import com.zapedudas.chip.Map.Levels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ChompActivity extends Activity {

	public ChompActivity() {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(this, LevelScreen.class);
		intent.putExtra("levelFile", Levels.levels[0]);
		this.startActivity(intent);
	}
}
