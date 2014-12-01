package com.is206.olpriser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Single Utested Activity
 */
public class SingleUtestedActivity extends Activity {

	TextView txtName;
	EditText txtPrice;
	TextView txtDesc;
	EditText txtRating;
	EditText txtCreatedAt;
	Button btnRating;
	Button btnPrice;

	String uid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single utested url
	private static final String url_utested_detials = "http://priser.leisegang.no/get_utested_details.php";

	// url to update price
	private static final String url_update_price = "http://priser.leisegang.no/update_pris.php";
	
	// url to update rating
	private static final String url_update_rating = "http://priser.leisegang.no/update_rating.php";	
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_UTESTED = "utested";
	private static final String TAG_UID = "uid";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_RATING = "rating";
	
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
            Toast.makeText(SingleUtestedActivity.this, "Hjem", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.tipsOss:
        	Intent tipsIntent = new Intent(getApplicationContext(), TipsUsActivity.class);
			startActivityForResult(tipsIntent, 0);
            Toast.makeText(SingleUtestedActivity.this, "Tips oss!", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.omOss:
        	Intent hjemIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
			startActivityForResult(hjemIntent, 0);
            Toast.makeText(SingleUtestedActivity.this, "Om oss", Toast.LENGTH_SHORT).show();
            return true;
 
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_utested);
		getActionBar().setHomeButtonEnabled(true);
		if (android.os.Build.VERSION.SDK_INT > 9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		// price and rating buttons
		btnRating = (Button) findViewById(R.id.btnEditRating);
		btnPrice = (Button) findViewById(R.id.btnEditPrice);

		// getting utested details from intent
		Intent i = getIntent();
		
		// getting utested id (uid) from intent
		uid = i.getStringExtra(TAG_UID);

		// Getting complete utested details in background thread
		new GetUtestedDetails().execute();

		// Save price button click event
		btnPrice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update price
				 new SavePriceDetails().execute();
			}
		});
		
		// Save rating button click event
		btnRating.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update rating
				new SaveRatingDetails().execute();
			}
		});		
	}

	/**
	 * Background Async Task to Get complete utested details
	 * */
	class GetUtestedDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleUtestedActivity.this);
			pDialog.setMessage("Laster utested detaljer. Vennligst vent...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting utested details in background thread
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

						// getting utested details by making HTTP request
						// Note that utested details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_utested_detials, "GET", params);

						// check your log for json response
						Log.d("Single Utested Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received utested details
							JSONArray utestedObj = json
									.getJSONArray(TAG_UTESTED); // JSON Array
							
							// get first utested object from JSON Array
							JSONObject utested = utestedObj.getJSONObject(0);

							// utested with this uid found
							// Edit Text and Text View
							txtName = (TextView) findViewById(R.id.inputName);
							txtPrice = (EditText) findViewById(R.id.inputPrice);
							txtDesc = (TextView) findViewById(R.id.inputDesc);
							txtRating = (EditText) findViewById(R.id.inputRating);

							// display utested data in EditText and TextView
							txtName.setText(utested.getString(TAG_NAME));
							txtPrice.setText(utested.getString(TAG_PRICE));
							txtDesc.setText(utested.getString(TAG_DESCRIPTION));
							txtRating.setText(utested.getString(TAG_RATING));

						}else{
							// utested with uid not found
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
	 * Background Async Task to  Save price Details
	 * */
	class SavePriceDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleUtestedActivity.this);
			pDialog.setMessage("Lagrer pris...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving price
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts and TextViews
			String name = txtName.getText().toString();
			String price = txtPrice.getText().toString();
			String description = txtDesc.getText().toString();
			String rating = txtRating.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_UID, uid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PRICE, price));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
			params.add(new BasicNameValuePair(TAG_RATING, rating));

			// sending modified data through http request
			// Notice that update price url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_price,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about price update
					setResult(100, i);
					finish();
				} else {
					// failed to update price
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
			// dismiss the dialog once price updated
			pDialog.dismiss();
		}
	}
	
	/**
	 * Background Async Task to  Save rating Details
	 * */
	class SaveRatingDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleUtestedActivity.this);
			pDialog.setMessage("Lagrer rating...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving rating
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts and TextViews
			String name = txtName.getText().toString();
			String price = txtPrice.getText().toString();
			String description = txtDesc.getText().toString();
			String rating = txtRating.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_UID, uid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PRICE, price));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
			params.add(new BasicNameValuePair(TAG_RATING, rating));

			// sending modified data through http request
			// Notice that update rating url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_rating,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about rating update
					setResult(100, i);
					finish();
				} else {
					// failed to update rating
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
			// dismiss the dialog once rating updated
			pDialog.dismiss();
		}
	}	
}
