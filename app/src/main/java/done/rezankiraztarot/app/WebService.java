package done.rezankiraztarot.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 19.6.2014.
 */
public class WebService {
    final String SERVER_URL = "http://81.21.162.78:8282/TEST_RezanKirazCanliTarotWS/WebService";
    SharedPreferences sharedPreferences;
    Context context;
    SharedPreferences sharedpreferences;
    String uid;
    String ctid;
    String cid;
    String coid;

    public WebService(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences("webservice_preferences", Context.MODE_PRIVATE);
        uid = sharedpreferences.getString("uid", null);
        ctid = sharedpreferences.getString("ctid", null);
        cid = sharedpreferences.getString("cid", null);
        coid = sharedpreferences.getString("coid", null);
    }

    public JSONObject callRequest(int methodId, String arg0, String arg1, String arg2, String arg3) {
        // connection settings
        int TIME_OUT = 10000;
        int BUF_SIZE = 8;
        String CHAR_SET = "UTF-8";
        JSONObject result = null;

        /*TESTING BLOCK - FIXED USER ID HAS ALLOWED ALL SERVICES
        * REMOVE BEFORE RELEASE!!
        */
        arg0 = "3286";
        /*TESTING BLOCK*/

        /*
        * METHOD ID INFORMATION
        * 0 - general info
        * 1 - get user ID
        * 3 - check Rezan's availability
        * 5 - get video
        * 10 - check if video is purchased
        * */
        JSONObject jsonParameter = new JSONObject();
        try {
            jsonParameter.put("t", methodId);

            // decide which type of service should be called
            // for more information check the service structure
            // http://81.21.162.78:8282/TEST_RezanKirazCanliTarotWS/WebService?p={t:0}
            switch (methodId) {
                case 1:
                    jsonParameter.put("did", arg0);
                    break;
                case 3:
                    jsonParameter.put("uid", arg0);
                    break;
                case 5:
                    jsonParameter.put("uid", arg0);
                    jsonParameter.put("ctid", arg1);
                    jsonParameter.put("cid", arg2);
                    jsonParameter.put("coid", arg3);
                    break;
                case 10:
                    jsonParameter.put("uid", arg0);
                    jsonParameter.put("ctid", arg1);
                    jsonParameter.put("cid", arg2);
                    jsonParameter.put("coid", arg3);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            HttpPost httpPost = new HttpPost(SERVER_URL);
            HttpClient client = new DefaultHttpClient();
            // Timeout Limit
            HttpConnectionParams.setConnectionTimeout(client.getParams(),
                    TIME_OUT);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("p", jsonParameter.toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(httpPost);
            HttpEntity resEntityGet = response.getEntity();

            if (resEntityGet != null) {
                InputStream inputStream = resEntityGet.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, CHAR_SET), BUF_SIZE);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                String stringMyJson = sb.toString();
                result = new JSONObject(stringMyJson);
            } else {
            }

        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
        }
        return result;
    }

    public String getUserID(String deviceID) {
        JSONObject response = callRequest(1, deviceID, "","","");
        String result = null;
        try {
            result = response.getJSONObject("user").get("id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isRezanAvailable(String uid) {
        JSONObject response = callRequest(3, uid, "", "", "");
        String status = null;
        try {
            status = response.getJSONObject("rezan").getString("st");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (status.equals("AVAILABLE")) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getVideoUrl() {
        JSONObject response = callRequest(5, uid, ctid, cid, coid);
        String url = null;
        try {
            url = response.getJSONObject("video").getString("vu");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    public boolean videoIsPurchased() {
        JSONObject response = callRequest(10, uid, ctid, cid, coid);
        String status = null;
        Log.d("test","WebService.java.status: "+status);
        try {
            status = response.getString("vp" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (status.equals("11")) {
            return true;
        }
        else {
            return false;
        }
    }
}
