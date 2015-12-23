package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FamilyRegisterCommonPassword extends ActionBarActivity {

    Button continueButton;
    EditText commonPassField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_register_common_password);

        Intent intent1  = getIntent();
        final String personName = intent1.getExtras().getString("personName");
        final String phoneNumber = intent1.getExtras().getString("phoneNumber");
        final String eldPhoneNo = intent1.getExtras().getString("eldPhoneNo");
        final String address = intent1.getExtras().getString("address");
        final String emailID = intent1.getExtras().getString("emailID");


        continueButton = (Button)findViewById(R.id.button1);
        commonPassField = (EditText)findViewById(R.id.editText1);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commonPass = commonPassField.getText().toString();


                if(commonPass.equals("1234")){

                    Intent intent = new Intent(v.getContext(), FamilyRegisterFinal.class);
                    intent.putExtra("personName",personName);
                    intent.putExtra("phoneNumber",phoneNumber);
                    intent.putExtra("eldPhoneNo", eldPhoneNo);
                    intent.putExtra("address",address);
                    intent.putExtra("emailID", emailID);
                    startActivity(intent);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
