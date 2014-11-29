package com.is206.olpriser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class TipsUsActivity extends Activity {
	
	Button btnTipsUs;
	TextView inputTo;
	EditText inputName;
	EditText inputDesc;
	
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
		
		btnTipsUs = (Button) findViewById(R.id.btnTipsUs);
		inputTo = (TextView) findViewById(R.id.inputTo);
		inputName = (EditText) findViewById(R.id.inputName);
		inputDesc = (EditText) findViewById(R.id.inputDesc);
		
		btnTipsUs.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)	{
			
				String to = inputTo.getText().toString();
				String subject = inputName.getText().toString();
				String message = inputDesc.getText().toString();
				
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);
				
				// need this to prompt email client only
				email.setType("message/rdc822");
				
				startActivity(Intent.createChooser(email, "Velg en e-post klient:"));
				
			}
		});
	}    	
}
