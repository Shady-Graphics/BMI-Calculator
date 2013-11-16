package com.mad.bmicalculatorrm;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i = getIntent();
		int tab = i.getIntExtra("tab", 0);
		
		TabHost tabHost = getTabHost();
		
		// Tab for Imperial input
        TabSpec imperialspec = tabHost.newTabSpec("Imperial");
        // setting Title and Icon for the Tab
        imperialspec.setIndicator("Imperial", getResources().getDrawable(R.drawable.icon_imperial_tab));
        Intent imperialIntent = new Intent(this, ImperialActivity.class);
        imperialspec.setContent(imperialIntent);
 
        // Tab for Metric input
        TabSpec metricspec = tabHost.newTabSpec("Metric");
        metricspec.setIndicator("Metric", getResources().getDrawable(R.drawable.icon_metric_tab));
        Intent metricIntent = new Intent(this, MetricActivity.class);
        metricspec.setContent(metricIntent);
 
        // Tab for History
        TabSpec historyspec = tabHost.newTabSpec("History");
        historyspec.setIndicator("History", getResources().getDrawable(R.drawable.icon_history_tab));
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        historyspec.setContent(historyIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(imperialspec); // Adding imperial tab
        tabHost.addTab(metricspec); // Adding metric tab
        tabHost.addTab(historyspec); // Adding history tab
        
        tabHost.setCurrentTab(tab);
	}

}
