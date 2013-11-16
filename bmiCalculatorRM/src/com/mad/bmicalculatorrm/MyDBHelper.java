package com.mad.bmicalculatorrm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
	//stores version of schema
	static int THIS_VERSION = 1;
	
	public MyDBHelper(Context ctx) {
		super(ctx, "bmi_db", null, THIS_VERSION);
	}
	
	public void onCreate(SQLiteDatabase DB) {
		Log.v("DB", "Creating schema");
		
		String sql = "create table savedBmi (" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, date STRING NOT NULL, " +
				"bmi DOULBE NOT NULL)";
		DB.execSQL(sql);
	}
	
	    public void delete(String id)
		{
	    	SQLiteDatabase db = this.getReadableDatabase();
	    	db.delete("savedBmi", "_id" + "=?", new String[]{id});
			db.close();
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
