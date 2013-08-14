package com.veggie411.veggie411;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_INGREDIENTS = "ingredients";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ENAME = "ename";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_DESCRIPTION = "description";
	
	public static final String TABLE_PREFERENCES = "preferences";
	public static final String COLUMN_EDIBLE = "edible";
	
	private static final String DATABASE_NAME = "veggie411.db";
	private static final int DATABASE_VERSION = 1;
	// Database creation sql statement
	private static final String DATABASE_CREATE_INGREDIENTS = "create table "
			+ TABLE_INGREDIENTS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text unique not null, " + COLUMN_STATUS
			+ " integer not null, " + COLUMN_ENAME
			+ " text, " + COLUMN_DESCRIPTION 
			+ " text);";
	private static final String DATABASE_CREATE_PREFERENCES = "create table "
			+ TABLE_PREFERENCES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text unique not null, " + COLUMN_EDIBLE
			+ " boolean not null);";
	public MySQLiteHelper(Context context) { 
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_INGREDIENTS);
		database.execSQL(DATABASE_CREATE_PREFERENCES);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
		onCreate(db);
	}
}