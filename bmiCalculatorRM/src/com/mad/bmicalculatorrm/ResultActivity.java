package com.mad.bmicalculatorrm;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {

	//member variables for database
	SQLiteDatabase mDb;
	
	//member variable for GUI
	TextView bmiTextView;
	Button saveToDb;
	
	//member variables for bmi
	String bmi;
	Double bmiDouble;
	
	//Colour variable
	Color colour;
	
	//converting and formatting bmi into correct format
	NumberFormat twoDigits = new DecimalFormat("0.00");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
       
        //getting data from previous activity
        Intent i = getIntent();
        bmi = i.getStringExtra("BMI");
        
        //getting reference to gui elements
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        saveToDb = (Button) findViewById(R.id.saveToDB);      
        //set up colour
        setColour();

        //setting bmi on the result screen
        bmiTextView.setText(bmiTextView.getText().toString() + " " + twoDigits.format(bmiDouble));
        
        //database setup code
        MyDBHelper mh = new MyDBHelper(this);
        mDb = mh.getWritableDatabase();
         
        //action listener added to the saveToDb button
        saveToDb.setOnClickListener(saveOnClickListener);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		mDb.close();
	}

	//onclick listener for save button
	private OnClickListener saveOnClickListener = new OnClickListener() {
		
		@SuppressLint("SimpleDateFormat")
		@Override
		public void onClick(View v) {
			//date format
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date=new Date();
			String s = df.format(date);

			// write bmi to db			
			ContentValues cv = new ContentValues();
			cv.put("date", s);
			cv.put("bmi", twoDigits.format(bmiDouble));
			Long checkIfInserted =mDb.insert("savedBmi", null, cv);
			if(checkIfInserted!=-1){
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("tab", 2);
				startActivity(i);
			}
			else{
				//show toast with error
				Toast.makeText(getApplicationContext(),"Error saving Bmi", Toast.LENGTH_LONG)
                .show();
			}
		}
	};
	
	//set bmi colour code
	public void setColour() {
		//convert bmi to Double
		bmiDouble = Double.parseDouble(bmi);
		
		if(bmiDouble <= 18.5){
			bmiTextView.setTextColor(Color.rgb(0, 0, 255));
		}
		else if(bmiDouble > 18.5 && bmiDouble <= 24.9){
			bmiTextView.setTextColor(Color.rgb(51, 255, 0));
		}
		else if(bmiDouble >= 25 && bmiDouble <=29.9){
			bmiTextView.setTextColor(Color.rgb(255, 255, 102));
		}
		else{
			bmiTextView.setTextColor(Color.rgb(255, 51, 51));
		}
		
	}
}
