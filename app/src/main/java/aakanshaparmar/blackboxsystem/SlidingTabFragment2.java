package aakanshaparmar.blackboxsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class SlidingTabFragment2 extends Fragment  {

    ImageButton sosButton;
    PopupWindow popUp;
    FrameLayout mainLayout;

    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_sliding_tab_fragment2, container, false);

        sosButton = (ImageButton) rootView.findViewById(R.id.imageButton);
        popUp = new PopupWindow(getActivity());
        mainLayout = new FrameLayout(rootView.getContext());

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUp.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
                popUp.update(50, 50, 300, 80);

            }
        });


        return rootView;
    }



}



