package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ElderlyIDPage extends ActionBarActivity {

    TextView personNameField, phoneNoField, addressField;

    String personName;
    String phoneNo;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_idpage);

        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        personName = sharedPreferences.getString("fullName", "");
        phoneNo = sharedPreferences.getString("emergencyPhoneNo", "");
        address = sharedPreferences.getString("address", "");

        personNameField = (TextView) findViewById(R.id.personName);
        phoneNoField = (TextView) findViewById(R.id.phoneNo);
        addressField = (TextView) findViewById(R.id.address);

        personNameField.setText(personName);
        phoneNoField.setText(phoneNo);
        addressField.setText(address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elderly_idpage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_idpage) {
            Intent intent = new Intent(getApplicationContext(), ElderlyHomePage.class);
            startActivity(intent);
            return true;
        }
//        else if(id == R.id.action_change_role){
//            SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("role", "family");
//            editor.commit();
//            Intent intent = new Intent(getApplicationContext(), FamilyViewEldLoc.class);
//            startActivity(intent);
//        }
//
//        else if(id == R.id.action_logout){
//
//            Intent intent = new Intent(getApplicationContext(), chooseRole.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }
}
