package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SlidingTabFragment extends Fragment  {

    public static final String ARG_OBJECT = "object";

    Button pathToHomeButton;


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_sliding_tab, container, false);

        pathToHomeButton = (Button) rootView.findViewById(R.id.MapDir);

       pathToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), ElderlyShowPathToHome.class);
                startActivity(intent);

            }
        });

        return rootView;
    }




}
