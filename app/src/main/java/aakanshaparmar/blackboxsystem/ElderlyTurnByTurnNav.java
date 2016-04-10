package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ElderlyTurnByTurnNav extends AppCompatActivity {

    double originLat, originLng, destLat, destLng;
    ImageButton homeButton;

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


        //Get Origin i.e. Current Location
         getCurrentLocation();

        //Get Destination i.e. Home from Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        destLat = Double.longBitsToDouble(sharedPreferences.getLong("homeLat", 0));
        destLng = Double.longBitsToDouble(sharedPreferences.getLong("homeLng", 0));

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

    private void getCurrentLocation()
    {
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        //Converting location to separate latitude and longitude values
        originLat = location.getLatitude();

        // Getting longitude of the current location
        originLng = location.getLongitude();

    }
}
