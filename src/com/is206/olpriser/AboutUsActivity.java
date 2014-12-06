package com.is206.olpriser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * About Us Activity
 */
public class AboutUsActivity extends Activity{
	
    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
     
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	if(android.R.id.home == item.getItemId()){
    		finish();
    	}
    	
        switch (item.getItemId())
        {
        case R.id.hjem:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
        	Intent utestederIntent = new Intent(getApplicationContext(), SortUtestederPrisActivity.class);
			startActivityForResult(utestederIntent, 0);
            Toast.makeText(AboutUsActivity.this, "Hjem", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.tipsOss:
        	Intent tipsIntent = new Intent(getApplicationContext(), TipsUsActivity.class);
			startActivityForResult(tipsIntent, 0);
            Toast.makeText(AboutUsActivity.this, "Tips oss!", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.omOss:
        	Intent hjemIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
			startActivityForResult(hjemIntent, 0);
            Toast.makeText(AboutUsActivity.this, "Om oss", Toast.LENGTH_SHORT).show();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	/**
	 * Sets layout to about_us.xml
	 * Implements the menu at the top
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		getActionBar().setHomeButtonEnabled(true);
	}    	
}
