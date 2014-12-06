package com.is206.olpriser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * All Utesteder Activity
 */
public class SortUtestederRatingActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> utestederList;

	// url to get all utesteder list
	private static String url_all_utesteder = "http://priser.leisegang.no/get_all_utested_sort_rating.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_UTESTEDER = "utesteder";
	private static final String TAG_UID = "uid";
	private static final String TAG_NAME = "name";
	private static final String TAG_RATING = "rating";

	// products JSONArray
	JSONArray utesteder = null;
	
    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.sort_menu, menu);
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
        	Intent utestederIntent = new Intent(getApplicationContext(), SortUtestederRatingActivity.class);
			startActivityForResult(utestederIntent, 0);
            Toast.makeText(SortUtestederRatingActivity.this, "Hjem", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.tipsOss:
        	Intent tipsIntent = new Intent(getApplicationContext(), TipsUsActivity.class);
			startActivityForResult(tipsIntent, 0);
            Toast.makeText(SortUtestederRatingActivity.this, "Tips oss!", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.omOss:
        	Intent hjemIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
			startActivityForResult(hjemIntent, 0);
            Toast.makeText(SortUtestederRatingActivity.this, "Om oss", Toast.LENGTH_SHORT).show();
            return true;
            
        case R.id.sortNavn:
        	Intent navnIntent = new Intent(getApplicationContext(), SortUtestederNavnActivity.class);
			startActivityForResult(navnIntent, 0);
            Toast.makeText(SortUtestederRatingActivity.this, "Sortert etter navn", Toast.LENGTH_SHORT).show();
            return true;
            
        case R.id.sortPris:
        	Intent prisIntent = new Intent(getApplicationContext(), SortUtestederPrisActivity.class);
			startActivityForResult(prisIntent, 0);
            Toast.makeText(SortUtestederRatingActivity.this, "Sortert etter pris", Toast.LENGTH_SHORT).show();
            return true;
            
        case R.id.sortRating:
        	Intent ratingIntent = new Intent(getApplicationContext(), SortUtestederRatingActivity.class);
			startActivityForResult(ratingIntent, 0);
            Toast.makeText(SortUtestederRatingActivity.this, "Sortert etter rating", Toast.LENGTH_SHORT).show();
            return true;            
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	/**
	 * Sets layout to all_utesteder.xml
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_utesteder);

		// Hashmap for ListView
		utestederList = new ArrayList<HashMap<String, String>>();

		// Loading utesteder in Background Thread
		new LoadAllUtesteder().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single utested
		// launching Single Utested Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String uid = ((TextView) view.findViewById(R.id.uid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SingleUtestedActivity.class);
				// sending uid to next activity
				in.putExtra(TAG_UID, uid);
				
				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});

	}

	// Response from Single Utested Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// means user edited/deleted utested
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	/**
	 * Background Async Task to Load all utesteder by making HTTP Request
	 * */
	class LoadAllUtesteder extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SortUtestederRatingActivity.this);
			pDialog.setMessage("Laster utesteder. Vennligst vent...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All utesteder from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_utesteder, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("Alle utesteder: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// utesteder found
					// Getting Array of Utesteder
					utesteder = json.getJSONArray(TAG_UTESTEDER);

					// looping through All Utesteder
					for (int i = 0; i < utesteder.length(); i++) {
						JSONObject c = utesteder.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_UID);
						String name = c.getString(TAG_NAME);
						String rating = c.getString(TAG_RATING);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_UID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_RATING, rating);

						// adding HashList to ArrayList
						utestederList.add(map);
					}
				} else {
					// no utesteder found
					// Launch Add New Utested Activity
					Intent i = new Intent(getApplicationContext(),
							NewUtestedActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
			// dismiss the dialog after getting all utesteder
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							SortUtestederRatingActivity.this, utestederList,
							R.layout.list_item, new String[] { TAG_UID,
									TAG_NAME, TAG_RATING},
							new int[] { R.id.uid, R.id.name,  R.id.sort_info });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}
