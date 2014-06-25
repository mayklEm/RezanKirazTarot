package done.rezankiraztarot.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twilio.client.Connection;
import com.twilio.client.Twilio;

import org.w3c.dom.Text;

/**
 * Created by Peter on 16.6.2014.
 */
public class CallActivity extends Activity implements View.OnClickListener {
    private MonkeyPhone phone;
    Button dialButton, hangupButton;
    TextView backButton;
    ImageView statusIcon;
    PowerManager.WakeLock wakeLock;
    WebService webService;
    String userId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.call_activity);


        webService = new WebService(this);
        // get user ID from SharedPreferences (required for webservice calls)
        SharedPreferences sharedpreferences = getSharedPreferences("webservice_preferences", Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("uid", null);

        dialButton = (Button) findViewById(R.id.button_dial);
        hangupButton = (Button) findViewById(R.id.button_hangup);
        backButton = (TextView) findViewById(R.id.back_button);


        statusIcon = (ImageView) findViewById(R.id.status_icon);
        checkRezanAvailability(userId);

        if(checkRezanAvailability(userId)) {
            dialButton.setOnClickListener(this);
            hangupButton.setOnClickListener(this);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,"wake tag");
        wakeLock.acquire();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_dial){
            if(phone!=null){
                dialButton.setVisibility(View.GONE);
                hangupButton.setVisibility(View.VISIBLE);
                phone.connect();
            }
        }
        else if (view.getId() == R.id.button_hangup){
            phone.disconnect();
			hangupButton.setVisibility(View.GONE);
			dialButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test", "onResume()");
        if(phone==null){
            phone = new MonkeyPhone(getApplicationContext(),dialButton,hangupButton);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Twilio.isInitialized()){
            Twilio.shutdown();}
        wakeLock.release();
    }

    public boolean checkRezanAvailability(String userId) {
        boolean status = webService.isRezanAvailable(userId);
        if (status) {
            statusIcon.setImageResource(R.drawable.online);
            return true;
        }
        else {
            statusIcon.setImageResource(R.drawable.offline);
            return false;
        }
    }


}
