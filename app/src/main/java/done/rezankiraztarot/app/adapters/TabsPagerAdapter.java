package done.rezankiraztarot.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import done.rezankiraztarot.app.tabs.BuyFragment;
import done.rezankiraztarot.app.tabs.DetailFragment;
import done.rezankiraztarot.app.tabs.SingleFragment;

/**
 * Created by Peter on 15.6.2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new SingleFragment();
            case 1:
                return new DetailFragment();
            case 2:
                return new BuyFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
}
