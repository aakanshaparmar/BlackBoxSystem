package aakanshaparmar.blackboxsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by aakanshaparmar on 20/12/2015.
 */


public class SlidingTabAdapter extends FragmentPagerAdapter  {
    private String[] tabs = { "Hot", "Trending", "Fresh" };

    public SlidingTabAdapter(FragmentManager fm) {
        super(fm);
    }

    //Decides which fragment to shift to
    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0: {
                return new SlidingTabFragment();
            }
            case 1: {
                return new SlidingTabFragment2();
            }
            case 2: {
                return new SlidingTabFragment3();
            }
            default: {
                return new SlidingTabFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
