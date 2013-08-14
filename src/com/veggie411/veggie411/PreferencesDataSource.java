package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PreferencesDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_NAME};
	
	public PreferencesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	public Preference createPreference(String preference, boolean edible) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, preference);
		values.put(MySQLiteHelper.COLUMN_EDIBLE, edible);
		long insertId = database.insert(MySQLiteHelper.TABLE_PREFERENCES, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PREFERENCES,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Preference newPreference = cursorToPreference(cursor);
		cursor.close();
		return newPreference;
	}
	public void deletePreference(Preference preference) {
		long id = preference.getId();
		System.out.println("Preference deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_PREFERENCES, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}
	public List<Preference> getAllPreferences() {
		List<Preference> preferences = new ArrayList<Preference>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PREFERENCES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Preference preference = cursorToPreference(cursor);
			preferences.add(preference);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return preferences;
	}
	private Preference cursorToPreference(Cursor cursor) {
		Preference preference = new Preference();
		preference.setId(cursor.getLong(0));
		preference.setIngredient(cursor.getString(1));
		return preference;
	}
	
	public Preference getPreference(String s) {
		Cursor c = database.query(MySQLiteHelper.TABLE_PREFERENCES, allColumns, "ingredient="+s, allColumns, null, null, null);
		return cursorToPreference(c);
	}
}

