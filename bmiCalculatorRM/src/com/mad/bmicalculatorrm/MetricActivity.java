package com.mad.bmicalculatorrm;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MetricActivity extends Activity {

	//global variables
	//textviews
	TextView weigthTextView;
	TextView heightTextView;
	
	//seekbars
	SeekBar height;
	SeekBar weight;
	
	//buttons
	Button clear;
	Button Calculate;
	ImageButton info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metric);
		
		//getting reference to elements
		weigthTextView = (TextView) findViewById(R.id.metweightLabel);
		heightTextView = (TextView) findViewById(R.id.metheightLabel);
		height = (SeekBar) findViewById(R.id.metheightSeekBar);
		weight = (SeekBar) findViewById(R.id.metweightSeekBar);
		clear = (Button) findViewById(R.id.metclearButton);
		Calculate = (Button) findViewById(R.id.metcalculateBmi);
		
		//setting up seekbars
		height.setOnSeekBarChangeListener(heightSeekBarChangeListener);
		weight.setOnSeekBarChangeListener(weightSeekBarChangeListener);
		
		//buttons onclick listeners
		clear.setOnClickListener(clearOnClick);
		Calculate.setOnClickListener(calculateOnClick);
		
		//info alert
		info = (ImageButton) findViewById(R.id.infoButton);
		info.setOnClickListener(infoOnClick);
	}
	
	//buttons onclick listeners
	private OnClickListener clearOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// reset elements
			height.setProgress(0);
			weight.setProgress(0);	
		}
	};
	
	private OnClickListener calculateOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//error handling
			if(weight.getProgress()<=0 || height.getProgress()<=0){
				//display toast with error
				Toast.makeText(getApplicationContext(),"ERROR - Check data ", Toast.LENGTH_SHORT)
                .show();
			}
			else{
			//get values
			double heightValue = height.getProgress();
			int weightValue = weight.getProgress();
			//convert height from cms to meters
			double heightmeters = heightValue / 100;
			//calculation
			double bmi = weightValue / (Math.pow(heightmeters,2)); 
			//pass info onto intent
			Intent i = new Intent(getApplicationContext(), ResultActivity.class);
			//sending data to results screens
			i.putExtra("BMI", bmi + "");
			startActivity(i);
			//reset elements
			height.setProgress(0);
			weight.setProgress(0);
			}
		}
	};
	
	//action performed for information button
		private OnClickListener infoOnClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// prepare the alert box                   
	            AlertDialog.Builder alertbox = new AlertDialog.Builder(MetricActivity.this);
	            
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
	
	//seekbar onchange listeners
	private OnSeekBarChangeListener heightSeekBarChangeListener = new OnSeekBarChangeListener() {
				
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
				int heightValue = seekBar.getProgress();
				//call method to update pounds label
				updateLabels(-1, heightValue);
			}
	};
	
	private OnSeekBarChangeListener weightSeekBarChangeListener = new OnSeekBarChangeListener() {
		
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
			int weightValue = seekBar.getProgress();
			//call method to update pounds label
			updateLabels(weightValue, -1);
		}
	};
	//end of seekbar onchange listeners
	
	private void updateLabels(int weight, int height)
	{
		//checking which seekbar is being used
		if(weight==-1){
			//update height seekbar label
			heightTextView.setText(height + " cm");
		}
		else{
			//update weight seekbar label
			weigthTextView.setText(weight + " kg");
		}
	}//end of updateLabels

}
