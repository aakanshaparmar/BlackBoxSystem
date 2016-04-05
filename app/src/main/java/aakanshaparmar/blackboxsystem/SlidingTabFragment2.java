package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SlidingTabFragment2 extends Fragment implements View.OnClickListener{

    Button hospitalButton;
    Button policeButton;
    Button emergencyNoButton;


    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_sliding_tab_fragment2, container, false);

        hospitalButton = (Button) rootView.findViewById(R.id.hospButton);
        policeButton = (Button) rootView.findViewById(R.id.polButton);
        emergencyNoButton = (Button) rootView.findViewById(R.id.emerButton);

        hospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), ElderlyHospitalLocate.class);
                startActivity(intent);

            }
        });

        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), ElderlyPoliceLocate.class);
                startActivity(intent);

            }
        });

        emergencyNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent makeCallIntent = new Intent (Intent.ACTION_CALL);
                makeCallIntent.setData(Uri.parse("tel:" + "+852999"));
                try
                {
                    startActivity(makeCallIntent);
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(getActivity(), "Sorry Could Not Make Call...Please Try Again Later", Toast.LENGTH_LONG).show();
                }

            }
        });

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }
}



