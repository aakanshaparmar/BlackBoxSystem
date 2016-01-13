package aakanshaparmar.blackboxsystem;

import android.app.ActionBar;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;

import static android.app.ActionBar.NAVIGATION_MODE_TABS;


public class ElderlyHomePage extends ActionBarActivity  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "ElderlyHomePage";


    private ViewPager viewPager;
    private SlidingTabAdapter tabAdapter;

    android.support.v7.app.ActionBar.Tab instTab;
    android.support.v7.app.ActionBar.Tab hospPoliceTab;
    android.support.v7.app.ActionBar.Tab sosTab;

    ElderlyInstFragment instFragment = new ElderlyInstFragment();
    ElderlyHospPoliceFragment hospPoliceFragment = new  ElderlyHospPoliceFragment();
    ElderlySOSFragment sosFragment = new  ElderlySOSFragment();

    protected GoogleApiClient mGoogleApiClient;
    protected boolean mRequestingLocationUpdates = true;
    protected Location mLastLocation;
    protected Location mCurrentLocation;
    protected LocationRequest mLocationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_home_page);


        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(NAVIGATION_MODE_TABS);
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new SlidingTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);

        // Create a tab listener that is called when the user changes tabs.
        android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {
            @Override
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

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
        actionBar.addTab(sosTab);
        actionBar.addTab(hospPoliceTab);

        buildGoogleApiClient();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, "Location Found", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Location Not detected", Toast.LENGTH_LONG).show();
        }
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

}


