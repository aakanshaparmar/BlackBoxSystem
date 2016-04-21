package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MakeSOSCallActivity extends ActionBarActivity {

    Button callButton;
    ImageButton callAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_soscall);

        callButton = (Button) findViewById(R.id.callButton);
        callAgainButton = (ImageButton) findViewById(R.id.callAgain);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(getApplicationContext(), ElderlyEnterIDPasswordPage.class);
                startActivity(intent);

            }
        });

        callAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call();

            }
        });


        Toast.makeText(this, "Connecting...", Toast.LENGTH_LONG).show();
        call();

    }

    private void call()
    {

        SharedPreferences sharedPreferences = getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        String emergencyNo = sharedPreferences.getString("emergencyPhoneNo", "");

        Intent makeCallIntent = new Intent (Intent.ACTION_CALL);
        makeCallIntent.setData(Uri.parse("tel:" + emergencyNo));
        try
        {
            startActivity(makeCallIntent);
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(getApplicationContext(),"Sorry Could Not Make Call...Please Try Again Later",Toast.LENGTH_LONG).show();
        }


    }


}
