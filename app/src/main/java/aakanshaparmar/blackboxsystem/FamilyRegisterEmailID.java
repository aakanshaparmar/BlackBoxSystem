package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FamilyRegisterEmailID extends ActionBarActivity {

    Button continueButton;
    EditText emailIDField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_email_id);

        Intent intent = getIntent();

        final String personName = intent.getExtras().getString("personName");
        final String phoneNo = intent.getExtras().getString("phoneNumber");
        final String eldPhoneNo = intent.getExtras().getString("eldPhoneNo");
        final String address = intent.getExtras().getString("address");

        continueButton = (Button) findViewById(R.id.button1);
        emailIDField = (EditText) findViewById(R.id.editText1);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( v.getContext(), FamilyRegisterCommonPassword.class);
                intent.putExtra("phoneNumber", phoneNo);
                intent.putExtra("personName", personName);
                intent.putExtra("eldPhoneNo", eldPhoneNo);
                intent.putExtra("address", address);
                intent.putExtra("emailID", String.valueOf(emailIDField.getText()));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}