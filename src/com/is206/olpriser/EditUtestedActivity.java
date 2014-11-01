package com.is206.olpriser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.is206.olpriser.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditUtestedActivity extends Activity {

	TextView txtName;
	TextView txtPrice;
	TextView txtDesc;
	EditText txtCreatedAt;
	Button btnSave;

	String uid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_utested_detials = "http://priser.leisegang.no/get_utested_details.php";

	// url to update product
	private static final String url_update_utested = "http://priser.leisegang.no/update_utested.php";
	
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_UTESTED = "utested";
	private static final String TAG_UID = "uid";
	private static final String TAG_NAME = "name";
	//private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";
	
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
         
        switch (item.getItemId())
        {
        case R.id.hjem:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
        	Intent utestederIntent = new Intent(getApplicationContext(), AllUtestederActivity.class);
			startActivityForResult(utestederIntent, 0);
            Toast.makeText(EditUtestedActivity.this, "Hjem", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.tipsOss:
            Toast.makeText(EditUtestedActivity.this, "Tips oss!", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.omOss:
        	Intent hjemIntent = new Intent(getApplicationContext(), MainScreenActivity.class);
			startActivityForResult(hjemIntent, 0);
            Toast.makeText(EditUtestedActivity.this, "Om oss", Toast.LENGTH_SHORT).show();
            return true;
 
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_utested);
		if (android.os.Build.VERSION.SDK_INT > 9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		// save and delete button
		btnSave = (Button) findViewById(R.id.btnSave);

		// getting product details from intent
		Intent i = getIntent();
		
		// getting product id (uid) from intent
		uid = i.getStringExtra(TAG_UID);

		// Getting complete product details in background thread
		new GetUtestedDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update product
				new SaveUtestedDetails().execute();
			}
		});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetUtestedDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditUtestedActivity.this);
			pDialog.setMessage("Laster utested detaljer. Vennligst vent...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("uid", uid));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_utested_detials, "GET", params);

						// check your log for json response
						Log.d("Single Utested Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received product details
							JSONArray utestedObj = json
									.getJSONArray(TAG_UTESTED); // JSON Array
							
							// get first product object from JSON Array
							JSONObject utested = utestedObj.getJSONObject(0);

							// product with this uid found
							// Edit Text
							txtName = (TextView) findViewById(R.id.inputName);
							txtPrice = (TextView) findViewById(R.id.inputPrice);
							txtDesc = (TextView) findViewById(R.id.inputDesc);

							// display product data in EditText
							txtName.setText(utested.getString(TAG_NAME));
							txtPrice.setText(utested.getString(TAG_NAME));
							txtDesc.setText(utested.getString(TAG_DESCRIPTION));

						}else{
							// product with uid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to  Save product Details
	 * */
	class SaveUtestedDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditUtestedActivity.this);
			pDialog.setMessage("Lagrer pris...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String name = txtName.getText().toString();
			String price = txtPrice.getText().toString();
			String description = txtDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_UID, uid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_utested,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					finish();
				} else {
					// failed to update product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			pDialog.dismiss();
		}
	}
}
