package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditProfileActivity extends Activity {

	private ImageButton imageButton;
	private RadioButton lacto_ovo;
	private RadioButton pescatarian;
	private RadioButton vegetarian;
	private RadioButton vegan;
	private RadioButton none;
	private RadioButton can_eat;
	private RadioButton cant_eat;
	private EditText addIngredient;
	private ImageButton addButton;
	private ListView ingredientsList;
	private ImageButton saveButton;

	private PreferencesDataSource preferencesDatasource;
	private SharedPreferences sp;

	private ArrayList<String> specialIngredients;
	private IngredientsArrayAdapter adapter;
	//private HashSet<String> tempBlacklist;
	private HashSet<String> oldBlacklist;
	private String[] specialIngredientsArray;
	private HashMap<String, Boolean> specials;
	Toast tFill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_EDIT_PROFILE, Constants.CREATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		imageButton = (ImageButton) findViewById(R.id.edit_profile_image);
		lacto_ovo = (RadioButton) findViewById(R.id.edit_profile_lacto_ovo);
		pescatarian = (RadioButton) findViewById(R.id.edit_profile_pescatarian);
		vegetarian = (RadioButton) findViewById(R.id.edit_profile_vegetarian);
		vegan = (RadioButton) findViewById(R.id.edit_profile_vegan);
		none = (RadioButton) findViewById(R.id.edit_profile_none);
		can_eat = (RadioButton) findViewById(R.id.edit_profile_can_eat);
		cant_eat = (RadioButton) findViewById(R.id.edit_profile_cant_eat);
		addIngredient = (EditText) findViewById(R.id.edit_profile_add_ingredient);
		addButton = (ImageButton) findViewById(R.id.edit_profile_add_button);
		ingredientsList = (ListView) findViewById(R.id.edit_profile_special_ingredients_list);
		saveButton = (ImageButton) findViewById(R.id.edit_profile_save_button);

		preferencesDatasource = new PreferencesDataSource(this);
		sp = getSharedPreferences("veg", Context.MODE_PRIVATE);
		tFill = Toast.makeText(this, "Please fill in an ingredient", Toast.LENGTH_SHORT);

		specialIngredients = new ArrayList<String>();
		specialIngredientsArray = new String[0];
		specials = MainActivity.blacklistDatabase;
		MainActivity.blacklistDatabase = new HashMap<String, Boolean>();

		adapter = new IngredientsArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		//adapter = new SimpleArrayAdapter(this, specialIngredientsArray);
		ingredientsList.setAdapter(adapter);

		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//if doesnt exist???TODO
				boolean canEat = true;
				String ingred = addIngredient.getText().toString().toUpperCase();
				if (ingred.matches("")) {
					tFill.show();
				} else {
					Log.i("INGRED", ingred);
					if (cant_eat.isChecked()) {
						Log.i("checked", "cant");
						canEat = false;
					} else if (can_eat.isChecked()) {
						Log.i("checked", "can");
					}
					if (!specials.containsKey(ingred)) {
						adapter.add(ingred);
					}
					addIngredient.setText("");
					MainActivity.blacklistDatabase.put(ingred, canEat);
					//atm if change false to true doesnt allow.
					adapter.notifyDataSetChanged();
				}
			}
		});

		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				String status = "";
				if (pescatarian.isChecked()) {
					status = "PESCATARIAN";
					Log.i("STATUS", "P");
				} else if (lacto_ovo.isChecked()) {
					status = "LACTO_OVO";
					Log.i("STATUS", "L");
				} else if (vegan.isChecked()) {
					status = "VEGAN";
					Log.i("STATUS", "G");
				} else if (none.isChecked()) {
					status = "NONE";
					Log.i("STATUS", "N");
				} else if (vegetarian.isChecked()) {
					status = "VEGETARIAN";
					Log.i("STATUS", "V");
				};
				Editor spEditor = sp.edit();
				spEditor.putString(Constants.SP_STATUS, status);
				spEditor.commit();
				Log.i("STATUS", "="+status);
				preferencesDatasource.open();
				for (Preference p:preferencesDatasource.getAllPreferences()) {
					Log.i("DELETING ", p.getIngredient());
					preferencesDatasource.deletePreference(p);
					specials.clear();
				}
				for (String s:MainActivity.blacklistDatabase.keySet()) {
					if (specials.containsKey(s)) {
						Preference p = preferencesDatasource.getPreference(s);
						if (p.isEdible() != MainActivity.blacklistDatabase.get(s)) {
							//TODO change to update instead of delete and create
							preferencesDatasource.deletePreference(p);
							Log.i("DELETING ", p.getIngredient());
							preferencesDatasource.createPreference(s, MainActivity.blacklistDatabase.get(s));
							Log.i("Creating ", s);
						}
					} else {
						Log.i("CREATING", s);
						preferencesDatasource.createPreference(s, MainActivity.blacklistDatabase.get(s));
						Log.i("Creating ", s);
					}
				}
				Intent ret = new Intent();
				ret.putExtra("STATUS", status);
				setResult(RESULT_OK, ret);
				finish();
			}
		});
	}

	public void onResume() {
		super.onResume();
		//preferencesDatasource = new PreferencesDataSource(this);
	}

	public void onPause() {
		super.onPause();
		preferencesDatasource.close();
	}

	@Override
	public void onBackPressed() {
		MainActivity.blacklistDatabase = specials;
		setResult(RESULT_CANCELED, new Intent());
		finish();
	}
}
