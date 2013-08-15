package com.veggie411.veggie411;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MoreActivity extends Activity {
	private ImageButton profileButton;
	private ImageButton nutritionButton;
	private ImageButton userTipsButton;
	private ImageButton settingsButton;
	private Toast notImplementedToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_MORE, Constants.CREATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		profileButton = (ImageButton) findViewById(R.id.more_profile);
		nutritionButton = (ImageButton) findViewById(R.id.more_nutrition);
		userTipsButton = (ImageButton) findViewById(R.id.more_user_tips);
		settingsButton = (ImageButton) findViewById(R.id.more_settings);
		
		notImplementedToast = Toast.makeText(this, Constants.TOAST_NOT_IMPLEMENTED, Toast.LENGTH_SHORT);
		
		profileButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					profileButton.setBackgroundResource(R.drawable.profile_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					profileButton.setBackgroundResource(R.drawable.profile);
				}
				return false;
			}
		});
		profileButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent profileActivity = new Intent(MoreActivity.this, ProfileActivity.class);
				startActivity(profileActivity);
			}
		});
		nutritionButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					nutritionButton.setBackgroundResource(R.drawable.nutrition);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					nutritionButton.setBackgroundResource(R.drawable.nutrition_pressed);
				}
				return false;
			}
		});
		nutritionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notImplementedToast.show();
			}
		});
		userTipsButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					userTipsButton.setBackgroundResource(R.drawable.user_tips_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					userTipsButton.setBackgroundResource(R.drawable.user_tips);
				}
				return false;
			}
		});
		userTipsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notImplementedToast.show();
			}
		});
		settingsButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					settingsButton.setBackgroundResource(R.drawable.settings_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					settingsButton.setBackgroundResource(R.drawable.settings);
				}
				return false;
			}
		});
		settingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//notImplementedToast.show();
				for (String s:MainActivity.blacklistDatabase.keySet()) {
					Log.i(s, String.valueOf(MainActivity.blacklistDatabase.get(s)));
				}
			}
		});
		
		
	}
}
