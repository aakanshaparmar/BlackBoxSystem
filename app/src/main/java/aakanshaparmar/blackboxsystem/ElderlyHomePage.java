package aakanshaparmar.blackboxsystem;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aakanshaparmar.myapplication.backend.elderlyLocationInfoApi.ElderlyLocationInfoApi;
import com.example.aakanshaparmar.myapplication.backend.elderlyLocationInfoApi.model.ElderlyLocationInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


public class ElderlyHomePage extends ActionBarActivity  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    protected static final String TAG = "ElderlyHomePage";


    private ViewPager viewPager;
    private SlidingTabAdapter tabAdapter;

    android.support.v7.app.ActionBar.Tab instTab;
    android.support.v7.app.ActionBar.Tab hospPoliceTab;
    android.support.v7.app.ActionBar.Tab sosTab;

    SlidingTabFragment instFragment = new SlidingTabFragment();
    SlidingTabFragment2 hospPoliceFragment = new  SlidingTabFragment2();
    SlidingTabFragment3 sosFragment = new  SlidingTabFragment3();

    protected GoogleApiClient mGoogleApiClient;
    protected boolean mRequestingLocationUpdates = true;
    protected Location mLastLocation;
    protected Location mCurrentLocation;
    protected LocationRequest mLocationRequest;

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 600000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected String mLastUpdateTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_home_page);


        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new SlidingTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);

        // Create a tab listener that is called when the user changes tabs.
        android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {
            @Override
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }


        };

        //Assign Tabs to Action Bar
        instTab = actionBar.newTab().setText("Instructions for home").setTabListener(tabListener);
        hospPoliceTab = actionBar.newTab().setText("Hospital and Police").setTabListener(tabListener);;
        sosTab = actionBar.newTab().setText("SOS").setTabListener(tabListener);

        actionBar.addTab(instTab);
        actionBar.addTab(hospPoliceTab);
        actionBar.addTab(sosTab);


        buildGoogleApiClient();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_elderly_home_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_idpage) {
            Intent intent = new Intent(getApplicationContext(), ElderlyIDPage.class);
            startActivity(intent);
            return true;
        }

        else if(id == R.id.action_change_role){
            stopLocationUpdates();
            SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("role", "family");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), FamilyViewEldLoc.class);
            startActivity(intent);
        }

        else if(id == R.id.action_logout){

            stopLocationUpdates();
            Intent intent = new Intent(getApplicationContext(), chooseRole.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    public void onConnected(Bundle connectionHint) {

        /*mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, "Location Found", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Location Not detected", Toast.LENGTH_LONG).show();
        }*/
        startLocationUpdates();
    }

    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        SharedPreferences sharedPreferences = getSharedPreferences("locationUpdates", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mLastUpdateTime = DateFormat.getDateTimeInstance().format(new Date());

        editor.putFloat("latitude", (float) mCurrentLocation.getLatitude());
        editor.putFloat("longitude", (float) mCurrentLocation.getLongitude());
        editor.putString("dateAndTime", mLastUpdateTime);
        editor.commit();
        Toast.makeText(this, "Location Found"+mLastUpdateTime, Toast.LENGTH_LONG).show();
        new EldLocInfoAsyncTask().execute(getApplicationContext());

    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    private class EldLocInfoAsyncTask extends AsyncTask<Context, Void, ElderlyLocationInfo> {
        private ElderlyLocationInfoApi myApiService = null;
        private Context context;


        protected ElderlyLocationInfo doInBackground(Context... params) {

            if(myApiService == null) {  // Only do this once
                ElderlyLocationInfoApi.Builder builder = new ElderlyLocationInfoApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://bbsystemproject.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            ElderlyLocationInfo eldLocInfo = new ElderlyLocationInfo();

            eldLocInfo.setLatitude(Float.valueOf("2.1"));
            eldLocInfo.setLongitude(Float.valueOf("2.2"));
            eldLocInfo.setEldID("e29211107");
            eldLocInfo.setLocID("l13");

            try {
                return myApiService.insertElderlyLocationInfo(eldLocInfo).execute();
            } catch (IOException e) {
                return null;
            }
        }

        protected void onPostExecute(ElderlyLocationInfo result) {

            if (result == null) {
                Toast.makeText(context, "Location not saved in DB! Some error occurred", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Location saved in DB!", Toast.LENGTH_LONG).show();

            }
        }

    }

}



