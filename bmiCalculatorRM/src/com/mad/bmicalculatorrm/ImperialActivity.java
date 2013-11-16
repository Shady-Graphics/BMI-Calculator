package com.mad.bmicalculatorrm;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ImperialActivity extends Activity {
	
	//gobal member variables for labels
	TextView weightTextView;
	//seekbar
	SeekBar weightSeekBar;
	//spinners
	Spinner heightFeet;
	Spinner heightInches;
	//buttons
	Button clear;
	Button Calculate;
	ImageButton info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imperial);

		//setting up seekbar
		weightSeekBar = (SeekBar) findViewById(R.id.weightSeekBar);
		weightSeekBar.setOnSeekBarChangeListener(weightSeekBarListener);
		//weight label
		weightTextView = (TextView) findViewById(R.id.weightLabel);
		
		//setting up spinners
		
		heightFeet = (Spinner) findViewById(R.id.heightFeetSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FeetArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		heightFeet.setAdapter(adapter);
		
		heightInches = (Spinner) findViewById(R.id.heightInchesSpinner);// Create an ArrayAdapter using the string array and a default spinner layout
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterInches = ArrayAdapter.createFromResource(this, R.array.inchesArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterInches.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		heightInches.setAdapter(adapterInches);
		
		//buttons
		clear = (Button) findViewById(R.id.clearButton);
		Calculate = (Button) findViewById(R.id.calculateBmi);
		
		clear.setOnClickListener(clearOnClick);
		Calculate.setOnClickListener(calculateOnClick);
		
		//info alert
		info = (ImageButton) findViewById(R.id.infoButton);
		info.setOnClickListener(infoOnClick);

	}

	
	//-------------------------------------------------------------------------------
	private OnSeekBarChangeListener weightSeekBarListener = new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				//get the value from seekbar
				int weight = seekBar.getProgress();
				//call method to update pounds label
				updateWeightLabel(weight);
			}
		};

	//-------------------------------------------------------------------------------
	
	//action performed for the clear button
	private OnClickListener clearOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// reset elements
			weightSeekBar.setProgress(0);
			heightFeet.setSelection(0);
			heightInches.setSelection(0);
		}
	};
		
	//action performed for the calculate button
	private OnClickListener calculateOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//error handling
			if(weightSeekBar.getProgress()<=0){
				//display toast with error
				Toast.makeText(getApplicationContext(),"ERROR - Check data ", Toast.LENGTH_SHORT)
                .show();
			}
			else{
			//get values
			int weight = weightSeekBar.getProgress();
			String heightftStr = heightFeet.getSelectedItem().toString();
			String heightinStr = heightInches.getSelectedItem().toString();
			//convert height values to numeric values
			int heightftInt = Integer.parseInt(heightftStr.substring(0, 1));
			int heightinInt = Integer.parseInt(heightinStr.substring(0,2).trim());

			//do calculation
			int height = (heightftInt * 12) + heightinInt;
			double bmi = weight / Math.pow(height, 2) * 703.06958;;
			//pass info onto intent
			Intent i = new Intent(getApplicationContext(), ResultActivity.class);
			//sending data to results screens
			i.putExtra("BMI", bmi + "");
			startActivity(i);
			// reset elements
			weightSeekBar.setProgress(0);
			heightFeet.setSelection(0);
			heightInches.setSelection(0);
		}
		}
	};
	
	//action performed for information button
	private OnClickListener infoOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// prepare the alert box                   
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ImperialActivity.this);
            
            // set the message to display
            alertbox.setMessage("This calculation is appropriate for adults over the age of 20!\n" +
            		"This calculation does not take into account differences between males and females.\n" +
            		"The BMI is meant to be used as a guideline and does not account for athletes, children, the ederly etc.!!");
            
            // add a neutral button to the alert box and assign a click listener
            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                 
                // click listener on the alert box
                public void onClick(DialogInterface arg0, int arg1) {
                    // the button was clicked and can be closed
                }
            });
		  
            // show it
            alertbox.show();
		}
	
	};
	
	private void updateWeightLabel(int weight)
		{
		//update the weight label
		weightTextView.setText(weight +" Ibs");
		}//end of  custom update

}
