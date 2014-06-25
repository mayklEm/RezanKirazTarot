package done.rezankiraztarot.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
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
    WebService webService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_layout);
        webService = new WebService(this);

        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");
        cardID = intent.getStringExtra("cardID");
        cardName = intent.getStringExtra("cardName");
        imageID = intent.getIntExtra("imageID", 0);
        commentID = intent.getStringExtra("commentID");
        comment = intent.getStringExtra("comment");
        isFlipped = intent.getBooleanExtra("isFlipped", false);

        ImageButton promoVideo = (ImageButton) findViewById(R.id.promo_video);

        bigImage = (ImageView) findViewById(R.id.big_image);
        bigImage.setImageResource(imageID);
        if (isFlipped) {
            bigImage.setRotation(180);
        }

        TextView textViewCardName = (TextView)findViewById(R.id.card_title);
        TextView textViewCardComment = (TextView)findViewById(R.id.textViewDescription);
        TextView textViewPickCard = (TextView)findViewById(R.id.text_pick_card);
        TextView watchVideo = (TextView)findViewById(R.id.text_video);
        TextView detailTitle = (TextView) findViewById(R.id.detail_title);

        Typeface typeFaceRedressed= Typeface.createFromAsset(this.getAssets(), "fonts/Redressed.ttf");
        Typeface typeFaceFreeform= Typeface.createFromAsset(this.getAssets(), "fonts/freeform-710-bt.ttf");
        textViewCardName.setTypeface(typeFaceRedressed);
        textViewCardComment.setTypeface(typeFaceRedressed);
        detailTitle.setTypeface(typeFaceFreeform);

        textViewCardName.setText(cardName);
        textViewCardComment.setText(comment);

        textViewPickCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        watchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playCardVideo();
            }
        });

        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        // set animation listener
        animZoomOut.setAnimationListener(this);
        bigImage.startAnimation(animZoomOut);

        ImageView callImage = (ImageView) findViewById(R.id.call_rezan_image);

        callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, CallActivity.class);
                startActivity(i);
            }
        });
        promoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, VideoActivity.class);
                intent.putExtra("isPromo", true);
                startActivity(intent);
            }
        });
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


    // check if the internet is available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void playCardVideo() {
        if (isNetworkAvailable()) {
            if (webService.videoIsPurchased()) {
                makeAlertDialog(getString(R.string.video_title), getString(R.string.video_watch));
            }
            else {
                makeAlertDialog(getString(R.string.video_title), getString(R.string.video_purchase));
            }
        }
        else {
            Toast.makeText(this, getString(R.string.no_internet),
                    Toast.LENGTH_LONG).show();
        }
    }


    // create dialog for buy/call button
    private void makeAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true);

        // set positive response
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DetailActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        // set negative response
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
