package done.rezankiraztarot.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import done.rezankiraztarot.app.util.IabHelper;
import done.rezankiraztarot.app.util.IabResult;
import done.rezankiraztarot.app.util.Inventory;

/**
 * Created by Peter on 25.6.2014.
 */
public class InAppBillingActivity extends Activity {

    // Debug tag, for logging
    static final String TAG = "test";

    // The helper object
    IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2Wxt8kmt3wqbD1AS+916WJunJQeMSBFcmfgm18rrqYYvzbI4UjxsgLNAnaLZyt6jnXnBn03VrgQXmXHKtgBt8QmQ5cSChlJdtPuyBxROGoGKhpcSXV6e3WgGmPnf6i52Go4+dHTwtjhlb9r0XdBvhe503HEeLjxsspePlZap+9GxKXimWnjmodoA30ETK1ugweS6uzlN38p4+XUbsBJriLnPjX7EkcQu4E3pLEuEDTSfb1LRv7P69ur4C/y4qV5aZbUI18t4zVR7hmDgnTX9Y72C5EW3Xb39B6QkDbSM38OMF8pJqDlaIRSuAOxF/PReKoh7qpFNLtrUiqiN3SS2MQIDAQAB";

        // Create the helper, passing it our context and the public key to verify signatures with
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {



        }
    };

    void complain(String message) {
        Log.e(TAG, "**** InAppBilling error: " + message);
        alert("Error: " + message);
    }
    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
}
