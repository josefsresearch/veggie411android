package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class SetUpApp extends AsyncTask<Object, Integer, String> {

	private Context context;
	private SharedPreferences sp;

	public SetUpApp(Context context) {
		this.context = context;
		sp = context.getSharedPreferences("veg", Context.MODE_PRIVATE);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		Log.i("GET BOOLEAN", String.valueOf(!sp.getBoolean("INGREDIENTS_POPULATED", false)));
		if (!sp.getBoolean("INGREDIENTS_POPULATED", false)) {
			//populateIngredients();
		}
		return "SUCCESS";
	}

	private void populateIngredients() {
		IngredientsDataSource datasource = new IngredientsDataSource(context);
		datasource.open();
		Ingredient ingredient;
		ArrayList<HashMap> allIngredients = HelperMethods.getAllIngredients();

		datasource.createIngredient("a", "", 1, "");
		datasource.createIngredient("b", "", 1, "");
		datasource.createIngredient("c", "", 1, "");
		datasource.createIngredient("d", "", 1, "");
		datasource.createIngredient("e", "", 1, "");
		datasource.createIngredient("f", "", 1, "");
		datasource.createIngredient("g", "", 1, "");
		datasource.createIngredient("h", "", 1, "");
		datasource.createIngredient("i", "", 1, "");
		sp.edit().putBoolean("INGREDIENTS_POPULATED", true);
	}
}
