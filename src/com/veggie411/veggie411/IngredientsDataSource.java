package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
//TODO check
//use getCount() on cursor
public class IngredientsDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_NAME,
			MySQLiteHelper.COLUMN_ENAME,
			MySQLiteHelper.COLUMN_REFERENCES,
			MySQLiteHelper.COLUMN_STATUS,
			MySQLiteHelper.COLUMN_DESCRIPTION };

	public IngredientsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	//
	public Ingredient createIngredient(String name, String ename, int references, int status, String description) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, name);
		values.put(MySQLiteHelper.COLUMN_ENAME, ename);
		values.put(MySQLiteHelper.COLUMN_REFERENCES, references);
		values.put(MySQLiteHelper.COLUMN_STATUS, status);
		values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
		//add others or add more create methods
		long insertId = database.insert(MySQLiteHelper.TABLE_INGREDIENTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENTS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Ingredient newIngredient = cursorToIngredient(cursor);
		cursor.close();
		return newIngredient;
	}



	public void deleteIngredient(Ingredient ingredient) {
		long id = ingredient.getId();
		System.out.println("Ingredient deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_INGREDIENTS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public String getDescription(String name) {
		String[] descrip = new String[1];
		descrip[0] = MySQLiteHelper.COLUMN_DESCRIPTION;
		Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENTS, descrip, MySQLiteHelper.COLUMN_NAME + "=" + name
				, null, null, null, null);
		if (cursor.moveToFirst()) {
			return cursorToIngredient(cursor).getDescription();
		} else {
			return null;
		}
	}

	public List<Ingredient> getAllIngredients() {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENTS,
				null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Ingredient ingredient = cursorToIngredient(cursor);
				ingredients.add(ingredient);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
		}
		cursor.close();
		return ingredients;
	}

	public String[] getIngredientsArray(List<Ingredient> ingredients) {
		String[] temp = new String[ingredients.size()];
		for (int i=0;i<ingredients.size();i++) {
			temp[i] = ingredients.get(i).getName();
		}
		return temp;
	}

	public List<Ingredient> getAll(String s) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENTS,
				allColumns, MySQLiteHelper.COLUMN_STATUS+"=="+s,
				null, null, null, MySQLiteHelper.COLUMN_NAME);
		while (!cursor.isAfterLast()) {
			Ingredient ingredient = cursorToIngredient(cursor);
			ingredients.add(ingredient);
			cursor.moveToNext();
		}
		cursor.close();
		return ingredients;
	}

	public List<Ingredient> getNon(String s) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENTS,
				allColumns, MySQLiteHelper.COLUMN_STATUS+"!="+s,
				null, null, null, MySQLiteHelper.COLUMN_NAME);
		while (!cursor.isAfterLast()) {
			Ingredient ingredient = cursorToIngredient(cursor);
			ingredients.add(ingredient);
			cursor.moveToNext();
		}
		cursor.close();
		return ingredients;
	}


	private Ingredient cursorToIngredient(Cursor cursor) {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(cursor.getLong(0));
		ingredient.setName(cursor.getString(1));
		return ingredient;
	}


}
