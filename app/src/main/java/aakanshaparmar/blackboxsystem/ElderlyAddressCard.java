package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;

public class ElderlyAddressCard extends ActionBarActivity {

    TextView addressField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_address_card);

        addressField = (TextView) findViewById(R.id.address);

        //Retrieve Address
        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String address = sharedPreferences.getString("address", "");

        addressField.setText(address);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elderly_address_card, menu);
        return true;
    }
}
