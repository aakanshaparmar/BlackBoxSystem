package aakanshaparmar.blackboxsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class ElderlyHospPoliceFragment extends Fragment {

    LinearLayout hsLayout;

    int n=0;

    public static final String ARG_OBJECT = "object";


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_elderly_hosp_police, container, false);

        hsLayout = (LinearLayout) rootView.findViewById(R.id.hsLayout1);

        return rootView;
    }

}
