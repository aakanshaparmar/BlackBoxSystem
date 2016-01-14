package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ElderlyRegisterEnterName extends ActionBarActivity {

    Button continueButton;
    EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_register_enter_name);

        continueButton = (Button) findViewById(R.id.button1);
        nameField = (EditText) findViewById(R.id.editText1);




        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String role = new String();
                role = "elderly";

                SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("role", role);
                editor.putString("fullName", String.valueOf(nameField.getText()));
                editor.commit();

                Intent intent = new Intent( v.getContext(), ElderlyRegisterPhoneNo.class);
                startActivity(intent);
                
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elderly_register_enter_name, menu);
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
}
