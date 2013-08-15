package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;

public class SetUpApp extends AsyncTask<Object, Integer, String> {

	private Context context;
	private SharedPreferences sp;

	public SetUpApp(Context context) {
		this.context = context;
		sp = context.getSharedPreferences(Constants.SP_FILE, Context.MODE_PRIVATE);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		Log.i("GET BOOLEAN", String.valueOf(!sp.getBoolean("INGREDIENTS_POPULATED", false)));
		populateIngredients();
		return "SUCCESS";
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		Editor e = sp.edit();
		e.putBoolean(Constants.SP_DATABASE_UP_TO_DATE, true);
		e.commit();
		Log.i("DBsetup?", String.valueOf(sp.getBoolean(Constants.SP_DATABASE_UP_TO_DATE, false)));
		super.onPostExecute(result);
	}

	private void populateIngredients() {
		IngredientsDataSource datasource = new IngredientsDataSource(context);
		datasource.open();
		Ingredient ingredient;
		//TODO change from hashmap to Ingredient
		ArrayList<Ingredient> allIngredients = HelperMethods.getAllIngredients();
		//datasource.createIngredient("name", "ename", references, status, "description");
		String name = "";
		String ename = "";
		String ref = "";
		int references = -1;
		int status = 0;
		String description = "";
		Log.i("claims", String.valueOf(allIngredients.size()));
		for (int i=0;i<allIngredients.size();i++) {
			ingredient = allIngredients.get(i);
			name = ingredient.getName();
			Log.i(String.valueOf(i), name);
			ename = ingredient.getEName();
			ref = ingredient.getReferencesName();
			status = ingredient.getStatusInt();
			description = ingredient.getDescription();
			//here or when returning? atm when returning
//			if (ref != "") {
//				description = datasource.getDescription(ref);
//				if (description == null) {
//					description = "";
//				}
//			} else {
//				description = ingredient.getDescription();
//			}
			Log.i(name, ename+references);
			datasource.createIngredient(name, ename, references, status, description);
		}
	}
}
