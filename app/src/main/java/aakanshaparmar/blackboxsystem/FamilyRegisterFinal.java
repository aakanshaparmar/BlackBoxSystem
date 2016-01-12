package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FamilyRegisterFinal extends ActionBarActivity {

    TextView personNameField, phoneNoField, addressField,emailIDField, eldPhoneNoField;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_final);

        Intent intent = getIntent();
        final String personName = intent.getExtras().getString("personName");
        final String phoneNo = intent.getExtras().getString("phoneNumber");
        final String address = intent.getExtras().getString("address");
        final String eldPhoneNo = intent.getExtras().getString("eldPhoneNo");
        final String emailID = intent.getExtras().getString("emailID");

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

                Intent intent = new Intent(v.getContext(), FamilyHomePage.class);
                startActivity(intent);

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
}