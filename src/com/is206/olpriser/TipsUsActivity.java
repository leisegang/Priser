package com.is206.olpriser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class TipsUsActivity extends Activity {
	
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
        	Intent utestederIntent = new Intent(getApplicationContext(), AllUtestederActivity.class);
			startActivityForResult(utestederIntent, 0);
            Toast.makeText(TipsUsActivity.this, "Hjem", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.tipsOss:
        	Intent tipsIntent = new Intent(getApplicationContext(), TipsUsActivity.class);
			startActivityForResult(tipsIntent, 0);
            Toast.makeText(TipsUsActivity.this, "Tips oss!", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.omOss:
        	Intent hjemIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
			startActivityForResult(hjemIntent, 0);
            Toast.makeText(TipsUsActivity.this, "Om oss", Toast.LENGTH_SHORT).show();
            return true;
 
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips_us);
		getActionBar().setHomeButtonEnabled(true);
	}    	
}