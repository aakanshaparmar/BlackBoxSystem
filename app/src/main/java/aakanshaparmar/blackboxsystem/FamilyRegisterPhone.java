package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aakanshaparmar.myapplication.backend.verifyFamilyRegistrationApi.VerifyFamilyRegistrationApi;
import com.example.aakanshaparmar.myapplication.backend.verifyFamilyRegistrationApi.model.VerifyFamilyRegistration;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class FamilyRegisterPhone extends ActionBarActivity {

    Button continueButton;
    EditText phoneField;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_phone);

        continueButton = (Button) findViewById(R.id.button1);
        phoneField = (EditText)findViewById(R.id.editText1);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("phoneNo", String.valueOf(phoneField.getText()));
                editor.commit();

                Toast.makeText(getApplicationContext(), "Please wait while phone number validity is checked", Toast.LENGTH_LONG).show();
                phoneNumber = String.valueOf(phoneField.getText());
                new VerFamRegistrationAsyncTask().execute(getApplicationContext());


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elderly_register_phone_no, menu);
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

    private class VerFamRegistrationAsyncTask extends AsyncTask<Context, Void, VerifyFamilyRegistration> {
        private VerifyFamilyRegistrationApi myApiService = null;
        private Context context;

        @Override
        protected VerifyFamilyRegistration doInBackground(Context... params) {
            if(myApiService == null) {  // Only do this once
                VerifyFamilyRegistrationApi.Builder builder = new VerifyFamilyRegistrationApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://bbsystemproject.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            VerifyFamilyRegistration verifyFamInfo = new VerifyFamilyRegistration();
            verifyFamInfo.setPhoneNo(phoneNumber);

            try {
                return myApiService.insertVerifyFamilyRegistration(verifyFamInfo).execute();
            } catch (IOException e) {
                return null;
            }
        }

        protected void onPostExecute(VerifyFamilyRegistration result) {


            if (result == null)
            {
                Toast.makeText(context, "Some unknown Error occurred", Toast.LENGTH_LONG).show();
            }
            else if (result.getPhoneNo().equals("0"))
            {

                Toast.makeText(context, "Phone Number Valid", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, FamilyRegisterAddress.class);
                startActivity(intent);


            } else {

                Toast.makeText(context, "Sorry Phone Number Already Exists!", Toast.LENGTH_LONG).show();

            }
        }
    }
}
