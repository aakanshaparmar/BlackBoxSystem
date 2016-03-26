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
        setContentView(R.layout.activity_make_soscall);
        callButton = (Button)findViewById(R.id.callButton);
        call();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_soscall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
