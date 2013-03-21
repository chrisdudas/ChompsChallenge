package com.zapedudas.chip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class LevelsActivity extends Activity {
	
	/** The number of levels currently in the game*/
    private int numLevels;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);
		GridView g = (GridView) findViewById(R.id.levels_grid);
		try {
			numLevels = Integer.parseInt(readTxt());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.setAdapter(new ImageAdapter(this, numLevels));
		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//              Toast.makeText(LevelsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LevelsActivity.this, ChipActivity.class);
				Bundle b = new Bundle();
				int maxLevel = Integer.parseInt(ChipActivity.readFile(LevelsActivity.this, "maxLevel.txt")[0]);
				if(maxLevel >= position) {
					b.putInt("level", position);
					b.putBoolean("multiplayer", false);
					intent.putExtras(b);
					startActivity(intent);
				}
				else {
					Toast.makeText(getApplicationContext(), "You haven't reached that level yet!", Toast.LENGTH_SHORT).show();
				}
			}
        });
   	}
   
   /** Reads text from asset file*/
	private String readTxt() throws IOException {
		InputStream inputStream = getAssets().open("levels.txt");
		System.out.println(inputStream);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		int i;
		try {
			i = inputStream.read();
			while (i != -1)
			{
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArrayOutputStream.toString();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(com.zapedudas.chip.R.layout.levels_menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case com.zapedudas.chip.R.id.new_game:
	        	Intent intent = new Intent(LevelsActivity.this, ChipActivity.class);
				Bundle b = new Bundle();
				ChipActivity.writeFile(LevelsActivity.this, "maxLevel.txt", "0");
				b.putInt("level", 0);
				b.putBoolean("multiplayer", false);
				intent.putExtras(b);
				startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/** 
	 * Class for generating the images for the levels. 
	 * @author Alex
	 *
	 */
	public class ImageAdapter extends BaseAdapter {
		/** number of levels*/
		private int levels;
		/** Context for display the images in*/
		private Context mContext;
		
		/** Creates a new image adapter*/
		public ImageAdapter(Context c, int levels) {
	 	    mContext = c;
		    this.levels = levels;
		}
		
		/** Returns the number of levels*/
	    public int getCount() {
		    return levels;
	    }
	    
	    /** Returns the position of the item in the grid*/
	    public Object getItem(int position) {
		    return position;
	    }
	    
	    /** Returns the position of the item in the grid*/
	    public long getItemId(int position) {
		    return position;
	    }
	    /**
	     * Returns the image of either an unlocked level or a locked level, depending on which level the player has reached.
	     */
	    public View getView(int position, View convertView, ViewGroup parent) {
		    ImageView imageView;
		    if (convertView == null) {
			    imageView = new ImageView(mContext);
			    imageView.setLayoutParams(new GridView.LayoutParams(72, 72));
			    imageView.setAdjustViewBounds(false);
			    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			    imageView.setPadding(8, 8, 8, 8);
		    } else {
		    	imageView = (ImageView) convertView;
		    }
		   if(position <= Integer.parseInt(ChipActivity.readFile(LevelsActivity.this, "maxLevel.txt")[0]))
			   imageView.setImageResource(R.drawable.ic_launcher);
		   else
			   imageView.setImageResource(R.drawable.lock);
		   return imageView;
	   }
    }
}