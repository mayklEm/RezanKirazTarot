package done.rezankiraztarot.app.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import done.rezankiraztarot.app.R;
import done.rezankiraztarot.app.TabsActivity;

/**
 * Created by Peter on 15.6.2014.
 */
public class DetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView smallCard = (ImageView) rootView.findViewById(R.id.small_card);

        //change font of description
        TextView descriptionView=(TextView)rootView.findViewById(R.id.textViewDescription);
        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(), "fonts/Redressed.ttf");
        descriptionView.setTypeface(typeFace);
        descriptionView.setText(((TabsActivity)getActivity()).getComment());

        // rotate card if the polarity is negative
        smallCard.setImageResource(((TabsActivity)getActivity()).getImageID());
        if (((TabsActivity)getActivity()).isFlipped()) {
            smallCard.setRotation(180);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // click on ShowVideoButton
        getActivity().findViewById(R.id.button_show_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clicking on video button redirects to buyVideo page
                ViewPager pager = (ViewPager)getActivity().findViewById(R.id.pager);
                pager.setCurrentItem(2, true);
            }
        });


    }
}