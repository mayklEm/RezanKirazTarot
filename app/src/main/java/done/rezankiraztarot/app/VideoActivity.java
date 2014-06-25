package done.rezankiraztarot.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Peter on 16.6.2014.
 */
public class VideoActivity extends Activity {
    String file_url;
    VideoView videoView;
    WebService webService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.video_activity);

        webService = new WebService(this);
        Intent intent = getIntent();
        boolean isPromo = intent.getBooleanExtra("isPromo", false);
        if (isPromo){
            file_url = webService.getPromoVideoUrl();
        }
        else {
            file_url = webService.getVideoUrl();
        }
        // initialization
        videoView = (VideoView) findViewById(R.id.video_view_play);



        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        Uri uri = Uri.parse(file_url);
        videoView.setVideoURI(uri);

        videoView.requestFocus();
        videoView.start();
    }




}