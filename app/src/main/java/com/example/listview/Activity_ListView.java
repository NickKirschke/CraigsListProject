package com.example.listview;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Activity_ListView extends AppCompatActivity {

	ListView my_listview;
    public static List<BikeData> bikeList;
	private ConnectivityCheck connection;
	public String myURL;
	private SharedPreferences.OnSharedPreferenceChangeListener listener;
	private SharedPreferences myPreferences;
	String jsonURL = "http://www.tetonsoftware.com/bikes/bikes.json";
    public CustomAdapter myAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Change title to indicate sort by
		setTitle("Sort by:");

		//listview that you will operate on
		my_listview = (ListView)findViewById(R.id.lv);
        myAdapter = new CustomAdapter(this);
        //adapter is set on dowloadTask.onPostExecute

		//toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		//preferences and connection check
		myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
				if (key.equals("listPref")) {
					myURL = myPreferences.getString("listPref", "");
                    connect();
				}
			}
		};
		myPreferences.registerOnSharedPreferenceChangeListener(listener);
		connect();

		//set the listview onclick listener
		setupListViewOnClickListener();
		//TODO when it returns it should process this data with bindData


	}
    public static int getBikeListSize() {
        return bikeList.size();
    }
	public void connect() {
		connection = new ConnectivityCheck();
		if (connection.isNetworkReachableAlertUserIfNot(this)) {
			myURL = myPreferences.getString("listPref", "");
			//download information by json
			DownloadTask task = new DownloadTask(this);
            Log.d("check website", jsonURL);
            //temporarily switched to jsonURL, want to check if works
            if(myURL.equals("http://www.tetonsoftware.com/bikes/")){
                task.execute(jsonURL);
            } else {
                Toast.makeText(Activity_ListView.this, "ERROR when connecting to " + myURL + " Server returned 404", Toast.LENGTH_SHORT).show();
            }


		}
	}

	private void setupListViewOnClickListener() {
		//TODO you want to call my_listviews setOnItemClickListener with a new instance of android.widget.AdapterView.OnItemClickListener() {
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_ListView.this);
                alert.setMessage(bikeList.get(position).toString());
                alert.setPositiveButton("OK",null);
                alert.show();
            }
        });
	}

	/**
	 * Takes the string of bikes, parses it using JSONHelper
	 * Sets the adapter with this list using a custom row layout and an instance of the CustomAdapter
	 * binds the adapter to the Listview using setAdapter
	 *
	 * @param JSONString  complete string of all bikes
	 */
	private void bindData(String JSONString) {
	}

	Spinner spinner;
	/**
	 * create a data adapter to fill above spinner with choices(Company,Location and Price),
	 * bind it to the spinner
	 * Also create a OnItemSelectedListener for this spinner so
	 * when a user clicks the spinner the list of bikes is resorted according to selection
	 * dontforget to bind the listener to the spinner with setOnItemSelectedListener!
	 */
	public void setupSimpleSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource
				(Activity_ListView.this, R.array.spinnerInfo, R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public static final int SELECTED_ITEM = 0;
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long rowId) {
                if (arg0.getChildAt(SELECTED_ITEM) != null) {
                    ((TextView) arg0.getChildAt(SELECTED_ITEM)).setTextColor(Color.WHITE);
                    Activity_ListView.this.myAdapter.sortList(pos);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				Intent myIntent = new Intent(this, activityPreference.class);
				startActivity(myIntent);
				return true;
            case R.id.refresh_view:
                connect();
			default:
				return super.onOptionsItemSelected(item);

		}
	}
}
