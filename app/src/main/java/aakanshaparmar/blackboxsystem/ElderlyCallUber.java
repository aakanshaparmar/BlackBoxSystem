package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ElderlyCallUber extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_call_uber);

        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri =
                    "uber://?action=setPickup&pickup=my_location";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // No Uber app! Open mobile website.
            String url = "https://m.uber.com/sign-up?";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }
}
