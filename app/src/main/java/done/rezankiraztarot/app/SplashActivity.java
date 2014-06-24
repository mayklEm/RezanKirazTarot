package done.rezankiraztarot.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

/**
 * Created by Peter on 17.6.2014.
 */
public class SplashActivity extends Activity {
    //time of splash screen
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);

        // splash screen will be displayed specified time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //once the timer is over this method is called
                // main activity is started and splash screen terminated
                Intent i = new Intent(SplashActivity.this, AllCardsActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}