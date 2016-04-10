package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ElderlyCallUber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_call_uber);

        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri =
                    "uber://?action=setPickup&pickup=my_location&client_id=YOUR_CLIENT_ID";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // No Uber app! Open mobile website.
            String url = "https://m.uber.com/sign-up?client_id=YOUR_CLIENT_ID";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }
}
