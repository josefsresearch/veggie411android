package com.veggie411.veggie411;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IngredientsActivity extends ListActivity {
	private IngredientsDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_INGREDIENTS, "CREATED");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredients);
		datasource = new IngredientsDataSource(this);
		datasource.open();
		List<Ingredient> ingre = datasource.getAllIngredients();
		String[] ingredients = (String[]) datasource.getIngredientsArray(ingre);
		final ArrayAdapter<String> adapter = new SimpleArrayAdapter(this, ingredients);
	    setListAdapter(adapter);
	    final ListView list = getListView();
	    list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String s = (String) adapter.getItem(position);
				//pop up with string
			}
	    });
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
	
}