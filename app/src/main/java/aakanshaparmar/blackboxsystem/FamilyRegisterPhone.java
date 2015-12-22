package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FamilyRegisterPhone extends ActionBarActivity {

    Button continueButton;
    EditText phoneField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_register_phone_no);

        Intent intent = getIntent();
        final String personName = intent.getExtras().getString("personName");
        final String eldPhoneNo = intent.getExtras().getString("eldPhoneNo");

        continueButton = (Button) findViewById(R.id.button1);
        phoneField = (EditText) findViewById(R.id.editText1);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), FamilyRegisterAddress.class);
                intent.putExtra("phoneNumber", String.valueOf(phoneField.getText()));
                intent.putExtra("personName", personName);
                intent.putExtra("eldPhoneNo", eldPhoneNo);
                startActivity(intent);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
