package done.rezankiraztarot.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;
import com.twilio.client.Twilio;

/**
 * Created by Peter on 16.6.2014.
 */
public class MonkeyPhone implements Twilio.InitListener {
    private Context context;
    Button dialButton, hangupButton;

    private Device device;
    private Connection connection;
    private String URL = "http://test-twilio.ic.cz/auth.php?clientName=jenny";
    private AudioManager myAudioManager;

    public MonkeyPhone(Context context, Button dialButton, Button hangupButton) {
        this.context = context;
        this.dialButton = dialButton;
        this.hangupButton = hangupButton;
        Twilio.initialize(context, this);
        myAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);


    }
    /* Twilio.InitListener method */
    @Override
    public void onInitialized() {
        try {

            final DeviceListener deviceListener = new DeviceListener() {
                @Override
                public boolean receivePresenceEvents(Device arg0) {
                    return false;
                }

                @Override
                public void onStopListening(Device arg0, int arg1, String arg2) {
                }

                @Override
                public void onStopListening(Device arg0) {
                }

                @Override
                public void onStartListening(Device arg0) {

                }

                @Override
                public void onPresenceChanged(Device arg0, PresenceEvent arg1) {
                }
            };

            HttpGetService httpGetService = new HttpGetService(URL) {
                @Override
                public void onPostExecute(String result) {
                    device = Twilio.createDevice(result, deviceListener);

                    Intent intent = new Intent(context, CallActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    device.setIncomingIntent(pendingIntent);
                }
            };
            httpGetService.execute();


        } catch (Exception e) {
            Log.d("test","phone initialization"+e.getLocalizedMessage());
        }
    }



    @Override
    public void onError(Exception e) {

        Log.d("test", "Twilio SDK couldn't start: " + e.getLocalizedMessage());
    }

    public void connect() {
        ConnectionListener connectionListener = new ConnectionListener() {

            @Override
            public void onDisconnected(Connection arg0, int arg1, String arg2) {
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run()
                    {
                        hangupButton.setVisibility(View.GONE);
                        dialButton.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onDisconnected(Connection arg0) {
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run()
                    {
                        hangupButton.setVisibility(View.GONE);
                        dialButton.setVisibility(View.VISIBLE);
                    }
                });



            }

            @Override
            public void onConnecting(Connection arg0) {

            }

            @Override
            public void onConnected(Connection arg0) {

//                myAudioManager.setMode(AudioManager.MODE_IN_CALL);
//                myAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
            }
        };

        connection = device.connect(null, connectionListener);

        if (connection == null) {
        }
    }

    public void disconnect() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

    @Override
    protected void finalize() {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (connection != null)
            connection.disconnect();
        if (device != null)
            device.release();
    }
}
