package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aakanshaparmar.myapplication.backend.elderlyRegistrationApi.ElderlyRegistrationApi;
import com.example.aakanshaparmar.myapplication.backend.elderlyRegistrationApi.model.ElderlyRegistration;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class ElderlyRegisterPageFinal extends ActionBarActivity {

    TextView personNameField, phoneNoField, addressField;
    Button submitButton;

    String personName;
    String phoneNo;
    String address;

    public ElderlyRegisterPageFinal() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_register_page_final);

        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        personName = sharedPreferences.getString("fullName", "");
        phoneNo = sharedPreferences.getString("phoneNo", "");
        address = sharedPreferences.getString("address", "");

        personNameField = (TextView) findViewById(R.id.personName);
        phoneNoField = (TextView) findViewById(R.id.phoneNo);
        addressField = (TextView) findViewById(R.id.address);

        submitButton = (Button)findViewById(R.id.button1);

        personNameField.setText(personName);
        phoneNoField.setText(phoneNo);
        addressField.setText(address);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new EldRegistrationAsyncTask().execute(getApplicationContext());

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elderly_register_page_final, menu);
        return true;
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

private class EldRegistrationAsyncTask extends AsyncTask<Context, Void, ElderlyRegistration> {
    private  ElderlyRegistrationApi myApiService = null;
    private Context context;


    protected ElderlyRegistration doInBackground(Context... params) {

        if(myApiService == null) {  // Only do this once
            ElderlyRegistrationApi.Builder builder = new ElderlyRegistrationApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://bbsystemproject.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        ElderlyRegistration eldInfo = new ElderlyRegistration();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        String personName = prefs.getString("fullName", "");
        String phoneNo = prefs.getString("phoneNo", "");
        String address = prefs.getString("address", "");
        String eID = "e"+phoneNo;
        int commonPass = 1234;

        eldInfo.setFullName("Vanisha");
        eldInfo.setPhoneNo("123456789");
        eldInfo.setAddress("b908");
        eldInfo.setEid("e123456789");
        eldInfo.setCommonPass(1234);


        try {
            return myApiService.insertElderlyRegistration(eldInfo).execute();
        } catch (IOException e) {
            return null;
        }
    }

    protected void onPostExecute(ElderlyRegistration result) {
         if (result == null) {
                Toast.makeText(context, "Error in registrations!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Registration complete", Toast.LENGTH_LONG).show();

                 Intent intent;
                 intent = new Intent(context, ElderlyShowCommonPass.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);

            }
    }

}
}

