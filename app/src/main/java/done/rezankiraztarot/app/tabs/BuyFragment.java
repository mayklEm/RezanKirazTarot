package done.rezankiraztarot.app.tabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import done.rezankiraztarot.app.CallActivity;
import done.rezankiraztarot.app.R;
import done.rezankiraztarot.app.VideoActivity;
import done.rezankiraztarot.app.WebService;

/**
 * Created by Peter on 15.6.2014.
 */
public class BuyFragment extends Fragment {

    Context context;
    Button buttonBuy, buttonCall;
    ImageView promoImage;
    VideoView promoVideo;
    WebService webService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy, container, false);
        buttonBuy = (Button) rootView.findViewById(R.id.button_buy);
        buttonCall = (Button) rootView.findViewById(R.id.button_call);
        promoVideo = (VideoView) rootView.findViewById(R.id.videoView1);
        promoImage = (ImageView) rootView.findViewById(R.id.imageView1);

        context = getActivity();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        webService = new WebService(getActivity());
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    if (webService.videoIsPurchased()) {
                        Log.d("text", "purchased");
                        makeAlertDialog(getString(R.string.video_title), getString(R.string.video_watch), 0);
                    }
                    else {
                        Log.d("text", "not purchased");
                        makeAlertDialog(getString(R.string.video_title), getString(R.string.video_purchase), 0);
                    }
                }
                else {
                    Toast.makeText(getActivity(), getString(R.string.no_internet),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    makeAlertDialog(getString(R.string.call_title), getString(R.string.call_message), 1);
                }
                else {
                    Toast.makeText(getActivity(), getString(R.string.no_internet),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // clicking on promo video image runs the video and hide the image
        promoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro_video_sample;
                promoVideo.setVideoURI(Uri.parse(path));
                promoImage.setVisibility(View.INVISIBLE);
                promoVideo.setVisibility(View.VISIBLE);
                promoVideo.start();
            }
        });
    }


    /****************
     CUSTOM METHODS
     ***************/

    // create dialog for buy/call button
    private void makeAlertDialog(String title, String message, final int activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true);

        // set positive response
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // decide which activity will be run buyVideo/call
                Intent intent;
                switch (activity) {
                    case 0:
                        intent = new Intent(getActivity(), VideoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), CallActivity.class);
                        startActivity(intent);
                        break;
                }
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

    // check if the internet is available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}