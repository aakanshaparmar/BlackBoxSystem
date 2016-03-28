package aakanshaparmar.blackboxsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.preference.PreferenceManager;


public class MakeSOSCallActivity extends ActionBarActivity {

    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

        newIntentAfterCall();
    }

    private void newIntentAfterCall()
    {
        Intent backToHomeScreenIntent = new Intent(this,ElderlyIDPage.class);
        startActivity(backToHomeScreenIntent);

    }
}
