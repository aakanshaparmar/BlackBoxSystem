package aakanshaparmar.blackboxsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class ElderlyTurnByTurnNav extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    protected static final String TAG = "ElderlyShowPathToHome";


    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    double originLat, originLng, destLat, destLng;
    ImageButton homeButton;
    Button mapButton;

    Bundle extras;
    String root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_turn_by_turn_nav);

        homeButton = (ImageButton) findViewById (R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( v.getContext(), ElderlyHomePage.class);
                startActivity(intent);

            }
        });

        mapButton = (Button) findViewById (R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchGoogleMaps();

            }
        });



        root = "hello";
        Intent intent = getIntent();
        root = intent.getStringExtra("Parent");
        extras = intent.getExtras();

        //Get Destination i.e. Home from Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        destLat = Double.longBitsToDouble(sharedPreferences.getLong("homeLat", 0));
        destLng = Double.longBitsToDouble(sharedPreferences.getLong("homeLng", 0));

        buildGoogleApiClient();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API)
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

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            originLat = mLastLocation.getLatitude();
            originLng = mLastLocation.getLongitude();
            if(extras!= null)
            {
                if(root.equals("HomePage"))
                    launchGoogleMaps();
            }
        } else {
            Toast.makeText(this, "Location Not Found", Toast.LENGTH_LONG).show();
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

    protected void launchGoogleMaps()
    {

        root = "hello";
        //Fire intent for google maps api
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                /* Using the web based turn by turn directions url. */
                Uri.parse(
                        "http://maps.google.com/maps?" +
                                "saddr="+originLat+","+originLng+
                                "&daddr="+destLat+","+destLng));
        /*Setting the Class Name that should handle this intent.  We are setting the class name to
        the class name of the native maps activity. Android platform recognizes this and now knows that
        we want to open up the Native Maps application to handle the URL.  Hence it does not give the choice of
        application to the user and directly opens the Native Google Maps application.*/

        intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }




}
