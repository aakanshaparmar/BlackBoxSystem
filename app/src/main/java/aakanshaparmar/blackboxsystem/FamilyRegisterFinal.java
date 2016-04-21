package aakanshaparmar.blackboxsystem;

import android.app.Activity;
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

import com.example.aakanshaparmar.myapplication.backend.familyRegistrationApi.FamilyRegistrationApi;
import com.example.aakanshaparmar.myapplication.backend.familyRegistrationApi.model.FamilyRegistration;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class FamilyRegisterFinal extends ActionBarActivity {

    TextView personNameField, phoneNoField, addressField,emailIDField, eldPhoneNoField;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_final);

        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        final String personName = sharedPreferences.getString("fullName", "");
        final String phoneNo = sharedPreferences.getString("phoneNo", "");
        final String address = sharedPreferences.getString("address", "");
        final String eldPhoneNo = sharedPreferences.getString("eldPhoneNo", "");
        final String emailID = sharedPreferences.getString("emailID", "");
        final String role = sharedPreferences.getString("role", "");

        personNameField = (TextView) findViewById(R.id.personName);
        phoneNoField = (TextView) findViewById(R.id.phoneNo);
        addressField = (TextView) findViewById(R.id.address);
        emailIDField = (TextView) findViewById(R.id.emailID);
        eldPhoneNoField = (TextView) findViewById(R.id.eldPhoneNo);

        submitButton = (Button)findViewById(R.id.button1);

        personNameField.setText(personName);
        phoneNoField.setText(phoneNo);
        addressField.setText(address);
        emailIDField.setText(emailID);
        eldPhoneNoField.setText(eldPhoneNo);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               new FamilyRegistrationAsyncTask().execute(getApplicationContext());

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


        return super.onOptionsItemSelected(item);
    }

    private class FamilyRegistrationAsyncTask extends AsyncTask<Context, Void, FamilyRegistration> {
        private FamilyRegistrationApi myApiService = null;
        private Context context;


        protected FamilyRegistration doInBackground(Context... params) {

            if(myApiService == null) {  // Only do this once
                FamilyRegistrationApi.Builder builder = new FamilyRegistrationApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://bbsystemproject.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            FamilyRegistration famInfo = new FamilyRegistration();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();

            String personName = prefs.getString("fullName", "");
            String phoneNo = prefs.getString("phoneNo", "");
            String address = prefs.getString("address", "");
            String eID = "e"+phoneNo;
            int commonPass = 1234;

            famInfo.setFullName("Vanisha");
            famInfo.setAddress("b908");
            //famInfo.set
            famInfo.setEldID("e123456789");
            famInfo.setCommonPass(1234);
            famInfo.setEmailID("van@gmail.com");
            famInfo.setPhoneNo("7654321");
            famInfo.setFamID("f7654321");



            try {
                return myApiService.insertFamilyRegistration(famInfo).execute();
            } catch (IOException e) {
                return null;
            }
        }

        protected void onPostExecute(FamilyRegistration result) {
            if (result == null) {
                Toast.makeText(context, "Error in registrations!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Registration complete", Toast.LENGTH_LONG).show();

                Intent intent;
                intent = new Intent(context, FamilyViewEldLoc.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        }

    }
}

