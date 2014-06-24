package done.rezankiraztarot.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.UUID;

/**
 * Created by Peter on 15.6.2014.
 */
public class AllCardsActivity extends Activity implements View.OnClickListener {

    final Context context = this;
    //	Animation fadeInAnimation;
    ImageView bigImage;
    ImageButton cardBack;
    ImageButton cardFace;
    View rootLayout;
    WebService webService;
    Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // allows httpPost requests!!
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.all_cards_activity);

        // initialise webService
        webService = new WebService(this);

        //verify users identity or generate unique userID
        userAuth();

        // all cards_old are oriented in 3 rows
        View topCardView, middleCardView, bottomCardView;
        topCardView = findViewById(R.id.top);
        middleCardView = findViewById(R.id.middle);
        bottomCardView = findViewById(R.id.bottom);

        placeCards(topCardView, 100); // id:1xx
        placeCards(middleCardView, 200); // id:2xx
        placeCards(bottomCardView, 300); // id:3xx

        placeBackCards(topCardView, 400); // id:1xx
        placeBackCards(middleCardView, 500); // id:2xx
        placeBackCards(bottomCardView, 600); // id:3xx
    }

    @Override
    protected void onStart() {
        // always generate random card when AllCardsActivity starts
        card = new Card(this.context);

        // save card details in SharedPreferences
        SharedPreferences sharedpreferences = getSharedPreferences("webservice_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("ctid", card.getCategoryID());
        editor.putString("cid", card.getCardID());
        editor.putString("coid", card.getCommentID());
        editor.commit();

        if (bigImage != null) {
            // Clears the fadeIn animation effect
            bigImage.setVisibility(View.INVISIBLE);

            // Clears the FadeIn animation effect
            bigImage = null;

            // Clears the flip animation effect
            cardFace.setVisibility(View.VISIBLE);
            cardBack.setVisibility(View.INVISIBLE);
            ChangeClickability(true);
        }
        super.onStart();
    }

    // will populate grid with cards_old
    private void placeCards(View v, int idx) {
        for (int i = 0; i < 5; i++) {


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            ImageButton oneCard;

            int c = R.drawable.kart;
            oneCard = new ImageButton(context);

            params.addRule(RelativeLayout.ALIGN_TOP, idx - 1);
            params.addRule(RelativeLayout.ALIGN_LEFT, idx - 1);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            // push every card to the right
            params.leftMargin = recalculateSize(30);
//
            oneCard.setImageResource(c);
            oneCard.setClickable(true);
            oneCard.setPadding(0, 0, 0, 0);
            oneCard.setId(idx);
            oneCard.setLayoutParams(params);
            oneCard.setOnClickListener(this);
            oneCard.setClickable(true);

            ((ViewGroup) v).addView(oneCard);
            idx++;
        }
    }

    // put invisible cards_old to same place as visible cards_old for flip animation
    private void placeBackCards(View v, int idx) {
        for (int i = 0; i < 5; i++) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(recalculateSize(100), recalculateSize(150));
            ImageButton backImageButton;

            backImageButton = new ImageButton(context);

            params.addRule(RelativeLayout.ALIGN_TOP, idx - 1);
            params.addRule(RelativeLayout.ALIGN_LEFT, idx - 1);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            // push every card to the right
            params.leftMargin = recalculateSize(30);

            backImageButton.setClickable(true);
            backImageButton.setPadding(0, 0, 0, 0);
            backImageButton.setId(idx);
            backImageButton.setLayoutParams(params);
            backImageButton.setOnClickListener(this);
            backImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);

            ((ViewGroup) v).addView(backImageButton);
            backImageButton.setVisibility(View.INVISIBLE);

            idx++;
        }
    }

    /********** CUSTOM METHODS *****************/

    public void userAuth() {
        // userID is stored in SharedPreferences
        // if user ID is null (doesn't exist) it is generated by webservice call
        SharedPreferences sharedpreferences = getSharedPreferences("webservice_preferences", Context.MODE_PRIVATE);
        String uid = sharedpreferences.getString("uid", null);
        if (uid == null){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("uid", createUserID());
            editor.commit();
        }
        /*TESTING BLOCK - REMOVE SHARED PREFERENCE - USER ID*/
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.remove("uid");
//        editor.commit();
        /*TESTING BLOCK*/
        Log.d("test", sharedpreferences.getString("uid", null));
    }
    public String createUserID() {
        UUID deviceID = UUID.randomUUID();
        return webService.getUserID(deviceID.toString());
    }

    public void ChangeClickability(boolean isClickable) {
        ImageButton toDisable;
        for (int i = 100; i <= 104; i++) {
            toDisable = (ImageButton) findViewById(i);
            if (toDisable != null) {
                toDisable.setClickable(isClickable);
            } else {}
        }

        for (int i = 200; i <= 204; i++) {
            toDisable = (ImageButton) findViewById(i);
            if (toDisable != null) {
                toDisable.setClickable(isClickable);
            } else {}
        }

        for (int i = 300; i <= 304; i++) {
            toDisable = (ImageButton) findViewById(i);
            if (toDisable != null) {
                toDisable.setClickable(isClickable);
            } else {}
        }
    }

    private void flipCard() {
        // cardBack = (ImageButton) findViewById(backcardid);
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);
        rootLayout.startAnimation(flipAnimation);
    }

    public int recalculateSize(int size) {

        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() <= 304 && v.getId() >= 99) {
            int BackCardID;
            int FaceCardID;

            // when user click one card disable to click others(also it)
            ChangeClickability(false);

            FaceCardID = v.getId();
            BackCardID = FaceCardID + 300;

            rootLayout = findViewById(FaceCardID);
            cardFace = (ImageButton) findViewById(FaceCardID);

            // Set the res. of back card.
            cardBack = (ImageButton) findViewById(BackCardID);
            cardBack.setImageResource(card.getCardImageID());
            if (card.isFlipped()) {
                cardBack.setRotation(180);
            }

            flipCard();

            // we have invisible big image for after fade in animation.
            bigImage = (ImageView) findViewById(R.id.big_image_allcards);
            bigImage.clearAnimation();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AllCardsActivity.this, TabsActivity.class);
                    intent.putExtra("categoryID", card.getCategoryID());
                    intent.putExtra("cardID", card.getCardID());
                    intent.putExtra("cardName", card.getName());
                    intent.putExtra("imageID", card.getCardImageID());
                    intent.putExtra("commentID", card.getCommentID());
                    intent.putExtra("comment", card.getComment());
                    intent.putExtra("isFlipped", card.isFlipped());
                    AllCardsActivity.this.startActivity(intent);
                }
            }, 1000);
        }
    }
}
