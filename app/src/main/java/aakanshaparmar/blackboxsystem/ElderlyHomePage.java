package aakanshaparmar.blackboxsystem;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aakanshaparmar.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class ElderlyHomePage extends ActionBarActivity {

    private ViewPager viewPager;
    private SlidingTabAdapter tabAdapter;

    android.support.v7.app.ActionBar.Tab instTab;
    android.support.v7.app.ActionBar.Tab hospPoliceTab;
    android.support.v7.app.ActionBar.Tab sosTab;

    ElderlyInstFragment instFragment = new ElderlyInstFragment();
    ElderlyHospPoliceFragment hospPoliceFragment = new  ElderlyHospPoliceFragment();
    ElderlySOSFragment sosFragment = new  ElderlySOSFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_home_page);


        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new SlidingTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);

        // Create a tab listener that is called when the user changes tabs.
        android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {
            @Override
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }


        };

        //Assign Tabs to Action Bar
        instTab = actionBar.newTab().setText("Instructions for home").setTabListener(tabListener);
        hospPoliceTab = actionBar.newTab().setText("Hospital and Police").setTabListener(tabListener);;
        sosTab = actionBar.newTab().setText("SOS").setTabListener(tabListener);

        actionBar.addTab(instTab);
        actionBar.addTab(sosTab);
        actionBar.addTab(hospPoliceTab);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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


