package com.mad.bmicalculatorrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryActivity extends Activity {

	//global member variables  
	SQLiteDatabase db;
	Cursor cursor;
	SimpleCursorAdapter ca;
	//list view
	ListView bmiListView;
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		//get resource to listview
		bmiListView = (ListView) findViewById(R.id.list);
		
		//setting up database
		final MyDBHelper mh = new MyDBHelper(this);
		db = mh.getWritableDatabase();
		
		cursor = db.query("savedBmi", null, null, null, null, null, "date desc");
		
		startManagingCursor(cursor);
		
		//binding cursor to list
		int views[] = { R.id.hisBmiDate, R.id.hisBmiTextView };
		String cols[] = {"date", "bmi"};
		ca = new SimpleCursorAdapter(this, R.layout.history_list_item, cursor, cols, views);
		bmiListView.setAdapter(ca);
		//delete action
		bmiListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, final long arg3) {
				//dialog to Confirm delete 
				AlertDialog.Builder b = new AlertDialog.Builder(HistoryActivity.this);
				b.setMessage("Delete this from history?");
				//yes option
				 b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int whichButton) {
						 //delete element from database
						 mh.delete(arg3 + "");						
                          
						 //update list by calling the activity again
						 Intent i = new Intent(getApplicationContext(), MainActivity.class);
						 i.putExtra("tab", 2);
						 startActivity(i);
						 //give feedback to user
						 Toast.makeText(getApplicationContext(),"Delete Succesful", Toast.LENGTH_LONG)
                              .show();
					 		}
                         });
				 //no option
				 b.setNegativeButton("No", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int whichButton) {
						 dialog.cancel();
					 		}
                         });

                 b.show();
                 return true;
			}
		});
	}
	
	//on destroy method for database
	public void onDestroy() {
		super.onDestroy();	
		db.close();
	}
	
}
