package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aakanshaparmar.myapplication.backend.elderlyLocationInfoApi.ElderlyLocationInfoApi;
import com.example.aakanshaparmar.myapplication.backend.elderlyLocationInfoApi.model.ElderlyLocationInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class FamilyViewEldLoc extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    float lat= (float) 22.2840;
    float lon = (float) 114.1350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_view_eld_loc);

        Toast.makeText(this, "Finding Location Please Wait", Toast.LENGTH_LONG).show();

        new EldLocInfoAsyncTask().execute(getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Marker"));
        LatLng last = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(last));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(20);
        mMap.animateCamera(zoom);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        //LatLng lastLoc = new LatLng(lat, lon);
        //map.addMarker(new MarkerOptions().position(lastLoc).title("Last known Location"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(lastLoc));
        //CameraUpdate zoom=CameraUpdateFactory.zoomTo(18);
        //map.animateCamera(zoom);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_idpage) {
            Intent intent = new Intent(getApplicationContext(), ElderlyIDPage.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_family_view_eld_loc, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class EldLocInfoAsyncTask extends AsyncTask<Context, Void, ElderlyLocationInfo> {
        private ElderlyLocationInfoApi myApiService = null;
        private Context context;


        protected ElderlyLocationInfo doInBackground(Context... params) {

            if (myApiService == null) {  // Only do this once
                ElderlyLocationInfoApi.Builder builder = new ElderlyLocationInfoApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://bbsystemproject.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            ElderlyLocationInfo eldLocInfo = new ElderlyLocationInfo();

            SharedPreferences pref2 = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);

            String eID = "e" + pref2.getString("ePhoneNo", "");

            Log.d("HELLLLOOOOO", eID);
            try {
                return myApiService.getElderlyLocationInfo(eID).execute();
            } catch (IOException e) {
                return null;
            }
        }

        protected void onPostExecute(ElderlyLocationInfo result) {

            if (result == null) {
                Toast.makeText(context, "Sorry ! Location Not Found", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Location Found!", Toast.LENGTH_LONG).show();
                lat = result.getLatitude();
                lon = result.getLongitude();
                setUpMap();

            }
        }
    }
}
