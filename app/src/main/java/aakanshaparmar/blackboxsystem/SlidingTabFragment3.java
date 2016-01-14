package aakanshaparmar.blackboxsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SlidingTabFragment3 extends Fragment  {

   public static final String ARG_OBJECT = "object";


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_sliding_tab_fragment3, container, false);

        return rootView;
    }



}
