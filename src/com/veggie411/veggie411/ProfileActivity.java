package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	private ImageView image;
	private TextView name;
	private TextView ageCity;
	private TextView points;
	private ImageView statusImage;
	private TextView status;
	private ListView specialIngredients;
	private ImageButton editButton;
	private ArrayList<String> ingredients;
	private SimpleArrayAdapter adapter;

	private SharedPreferences sp;
	private PreferencesDataSource dataSource;

	private Intent editProfileActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_PROFILE, Constants.CREATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		image = (ImageView) findViewById(R.id.profile_image);
		name = (TextView) findViewById(R.id.profile_name);
		ageCity = (TextView) findViewById(R.id.profile_age_location);
		points = (TextView) findViewById(R.id.profile_points);
		statusImage = (ImageView) findViewById(R.id.profile_status_image);
		status = (TextView) findViewById(R.id.profile_status);
		specialIngredients = (ListView) findViewById(R.id.profile_additional_ingredients_list);
		editButton = (ImageButton) findViewById(R.id.profile_edit_button);

		sp = getSharedPreferences(Constants.SP_FILE, Context.MODE_PRIVATE);
		String n = sp.getString(Constants.SP_NAME, "");
		String c = sp.getString(Constants.SP_CITY, "");
		name.setText(n);
		ageCity.setText(c);
		dataSource = new PreferencesDataSource(this);

		editProfileActivity = new Intent(this, EditProfileActivity.class);
		dataSource.open();
		status.setText(sp.getString(Constants.SP_STATUS, "NONE"));
		//List<Preference> list = dataSource.getAllPreferences();
//		String[] ingreds = new String[list.size()];
		String[] ingreds = new String[MainActivity.blacklistDatabase.size()];
		int i =0;
		for (String s:MainActivity.blacklistDatabase.keySet()) {
			ingreds[i] = s;
			i++;
		}
//		for (int i=0;i<list.size();i++) {
//			Preference p = list.get(i);
//			ingreds[i] = p.getIngredient();
//			Log.i(p.getIngredient(), String.valueOf(MainActivity.blacklistDatabase.get("")));
//		}
		adapter = new SimpleArrayAdapter(this, ingreds);
		specialIngredients.setAdapter(adapter);

		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(editProfileActivity, Constants.RESULT_EDIT_PROFILE);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_CANCELED){
		} else if (requestCode == Constants.RESULT_EDIT_PROFILE) {
			dataSource.open();
			status.setText(intent.getStringExtra("STATUS"));
			//List<Preference> list = dataSource.getAllPreferences();
			String[] ingreds = new String[MainActivity.blacklistDatabase.size()];
			int i =0;
			for (String s:MainActivity.blacklistDatabase.keySet()) {
				ingreds[i] = s;
				i++;
			}
			//for (int i=0;i<list.size();i++) {
			//	Preference p = list.get(i);
			//	ingreds[i] = p.getIngredient();
			//	Log.i(p.getIngredient(), String.valueOf(MainActivity.blacklistDatabase.get("")));
			//}
			adapter = new SimpleArrayAdapter(this, ingreds);
			specialIngredients.setAdapter(adapter);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		dataSource.open();
	}

	@Override
	public void onPause() {
		super.onPause();
		dataSource.close();
	}

}
