package aakanshaparmar.blackboxsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.preference.PreferenceManager;

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
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("aakanshaparmar.blackboxsystem", Context.MODE_PRIVATE);
        final String emergencyNo = sharedPreferences.getString("emergencyPhoneNo", "");

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent takePhotoIntent = new Intent (v.getContext(), ElderlySOSTakePhoto.class);
               startActivity(takePhotoIntent);

//                Intent makeCallIntent = new Intent (Intent.ACTION_CALL);
//                makeCallIntent.setData(Uri.parse("tel:" + emergencyNo));
//                try
//                {
//                    startActivity(makeCallIntent);
//                }
//                catch (android.content.ActivityNotFoundException ex)
//                {
//                    Toast.makeText(getActivity(),"Sorry Could Not Make Call...Please Try Again Later",Toast.LENGTH_LONG).show();
//                }

            }
        });


        return rootView;
    }

}



