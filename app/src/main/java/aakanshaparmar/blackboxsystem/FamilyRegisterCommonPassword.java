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

import com.example.aakanshaparmar.myapplication.backend.verifyFamilyElderlyNumberApi.VerifyFamilyElderlyNumberApi;
import com.example.aakanshaparmar.myapplication.backend.verifyFamilyElderlyNumberApi.model.VerifyFamilyElderlyNumber;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class FamilyRegisterCommonPassword extends ActionBarActivity {

    Button continueButton;
    EditText commonPassField;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_common_password);


        continueButton = (Button)findViewById(R.id.button1);
        commonPassField = (EditText)findViewById(R.id.editText1);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commonPass = commonPassField.getText().toString();


                if(commonPass.equals("1234")){

                    SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("commonPass", String.valueOf(commonPassField.getText()));
                    editor.commit();



                }

                else{
                    Toast.makeText(getApplicationContext(), "Wrong Password, Please Enter Correct One", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_family_register_common_password, menu);
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


    private class VerFamilyElderlyNumberAsyncTask extends AsyncTask<Context, Void, VerifyFamilyElderlyNumber> {
        private VerifyFamilyElderlyNumberApi myApiService = null;
        private Context context;

        @Override
        protected VerifyFamilyElderlyNumber doInBackground(Context... params) {
            if(myApiService == null) {  // Only do this once
                VerifyFamilyElderlyNumberApi.Builder builder = new VerifyFamilyElderlyNumberApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://bbsystemproject.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            VerifyFamilyElderlyNumber verifyFamInfo = new VerifyFamilyElderlyNumber();
            SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
            phoneNumber = sharedPreferences.getString("ePhoneNo", "");
            verifyFamInfo.setPhoneNo(phoneNumber);

            try {
                return myApiService.insertVerifyFamilyElderlyNumber(verifyFamInfo).execute();
            } catch (IOException e) {
                return null;
            }
        }

        protected void onPostExecute(VerifyFamilyElderlyNumber result) {


            if (result == null)
            {
                Toast.makeText(context, "Some unknown Error occurred", Toast.LENGTH_LONG).show();
            }
            else if (result.getPhoneNo().equals("0"))
            {

                Toast.makeText(context, "Sorry Elderly Phone Number Doesnt Exist!", Toast.LENGTH_LONG).show();



            } else {

                Toast.makeText(context, "Elderly Info Valid", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, FamilyRegisterName.class);
                startActivity(intent);

            }
        }
    }
}
