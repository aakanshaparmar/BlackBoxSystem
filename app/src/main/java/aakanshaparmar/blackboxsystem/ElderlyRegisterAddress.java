package aakanshaparmar.blackboxsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class ElderlyRegisterAddress extends ActionBarActivity {

    Button continueButton;
    EditText addressField;

    String streetAddress;
    String newAddress;
    LatLng HomeAddressLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_register_address);


        continueButton = (Button) findViewById(R.id.button1);
        addressField = (EditText) findViewById(R.id.editText1);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                streetAddress = String.valueOf(addressField.getText());
                newAddress= replace(streetAddress);

                Log.d("Street Address", "" + newAddress);

               //Save street address in shared preferences
                editor.putString("address", String.valueOf(addressField.getText()));
                editor.commit();

                //Convert address to lat long in async task
                new ConvertAddressToLatLngAsyncTask ().execute(streetAddress);

                //Start new intent to accept emergency number
                Intent intent = new Intent( v.getContext(), ElderlyRegisterEmergencyNo.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elderly_register_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    //Function to replace all spaces in string with %20s for JSON object
    public String replace(String str) {

        String[] words = str.split(" ");

        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; ++i) {
            sentence.append("%20");
            sentence.append(words[i]);
        }

        String newAddress = sentence.toString();

        return newAddress;

    }

    //Async Task to convert address to lat long
    private class ConvertAddressToLatLngAsyncTask extends AsyncTask<String, Void, String[]> {
        ProgressDialog dialog = new ProgressDialog(ElderlyRegisterAddress.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String response;
            //Send URL to retrieve lat long to function getLatLongByURL
            try {
                response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address="+newAddress+"&sensor=false");
                Log.d("response",""+response);
                return new String[]{response};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... result) {
            //Convert JSON object retrieved from url request to latitude longitude values
            try {
                JSONObject jsonObject = new JSONObject(result[0]);

                double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");


                HomeAddressLatLng = new LatLng(lat,lng);

                //Save LatLong in Shared Preferences
                SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Store Double value of latlng as long bits
                editor.putLong("homeLat", Double.doubleToLongBits(lat));
                editor.putLong("homeLng", Double.doubleToLongBits(lng));
                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    //Function that accepts request URL for LatLong and generates a response
    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }



}
