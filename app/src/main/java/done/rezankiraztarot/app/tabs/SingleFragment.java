package done.rezankiraztarot.app.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class SingleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single, container, false);
        //change font of cardName
        TextView cardName = (TextView)rootView.findViewById(R.id.card_name);
        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(), "fonts/Redressed.ttf");
        cardName.setTypeface(typeFace);
        cardName.setText(((TabsActivity)getActivity()).getCardName());

        // rotate card if polarity is negative
        ImageView bigImage = (ImageView)rootView.findViewById(R.id.big_image_allcards);
        bigImage.setImageResource(((TabsActivity)getActivity()).getImageID());
        if (((TabsActivity)getActivity()).isFlipped()) {
            bigImage.setRotation(180);
        }
        return rootView;
    }
}
