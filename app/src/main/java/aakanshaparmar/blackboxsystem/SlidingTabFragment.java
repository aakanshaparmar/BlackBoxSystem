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
    Button turnByTurnNavButton;
    Button AddCardButton;
    Button UberButton;



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

        turnByTurnNavButton = (Button) rootView.findViewById(R.id.TurnNav);

        turnByTurnNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), ElderlyTurnByTurnNav.class);
                intent.putExtra("Parent","HomePage");
                startActivity(intent);

            }
        });

        AddCardButton = (Button) rootView.findViewById(R.id.AddCard);

        AddCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), ElderlyAddressCard.class);
                startActivity(intent);

            }
        });

        UberButton = (Button) rootView.findViewById(R.id.Uber);

        UberButton.setEnabled(false);


       /* UberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent( getActivity(), ElderlyAddressCard.class);
               // startActivity(intent);

            }
        });*/


        return rootView;
    }


}
