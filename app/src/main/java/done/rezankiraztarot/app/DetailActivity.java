package done.rezankiraztarot.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Peter on 24.6.2014.
 */
public class DetailActivity extends Activity implements Animation.AnimationListener {
    Animation animZoomOut;
    ImageView bigImage;
    String categoryID;
    String cardID;
    String cardName;
    int imageID;
    String commentID;
    String comment;
    boolean isFlipped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_layout);

        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");
        cardID = intent.getStringExtra("cardID");
        cardName = intent.getStringExtra("cardName");
        imageID = intent.getIntExtra("imageID", 0);
        commentID = intent.getStringExtra("commentID");
        comment = intent.getStringExtra("comment");
        isFlipped = intent.getBooleanExtra("isFlipped", false);

        bigImage = (ImageView) findViewById(R.id.big_image);
        bigImage.setImageResource(imageID);
        if (isFlipped) {
            bigImage.setRotation(180);
        }

        TextView textViewCardName = (TextView)findViewById(R.id.card_title);
        TextView textViewCardComment = (TextView)findViewById(R.id.textViewDescription);
        TextView textViewPickCard = (TextView)findViewById(R.id.text_pick_card);

        Typeface typeFace= Typeface.createFromAsset(this.getAssets(), "fonts/Redressed.ttf");
        textViewCardName.setTypeface(typeFace);
        textViewCardComment.setTypeface(typeFace);

        textViewCardName.setText(cardName);
        textViewCardComment.setText(comment);

        textViewPickCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



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
