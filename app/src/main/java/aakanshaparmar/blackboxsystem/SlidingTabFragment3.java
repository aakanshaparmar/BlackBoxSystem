package aakanshaparmar.blackboxsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SlidingTabFragment3 extends Fragment  {

    ImageButton sosButton;
    PopupWindow popUp;
    FrameLayout mainLayout;

    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_sliding_tab_fragment3, container, false);

        sosButton = (ImageButton) rootView.findViewById(R.id.imageButton);
        popUp = new PopupWindow(getActivity());
        mainLayout = new FrameLayout(rootView.getContext());

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Connecting...", Toast.LENGTH_LONG).show();
                Intent makeCallIntent = new Intent (v.getContext(), MakeSOSCallActivity.class);
                startActivity(makeCallIntent);

            }
        });


        return rootView;
    }



}



