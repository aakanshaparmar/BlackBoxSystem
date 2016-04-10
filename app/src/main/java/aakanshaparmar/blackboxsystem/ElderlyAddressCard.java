package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ElderlyAddressCard extends AppCompatActivity {

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
}
