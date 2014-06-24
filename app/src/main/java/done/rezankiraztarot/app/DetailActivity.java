package done.rezankiraztarot.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Peter on 24.6.2014.
 */
public class DetailActivity extends Activity implements Animation.AnimationListener {
    Animation animZoomOut;
    ImageView bigImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_layout);

        bigImage = (ImageView) findViewById(R.id.big_image);

        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        // set animation listener
        animZoomOut.setAnimationListener(this);
        bigImage.startAnimation(animZoomOut);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation



    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

}
