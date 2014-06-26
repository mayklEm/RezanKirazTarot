package done.rezankiraztarot.app;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by Peter on 18.6.2014.
 */
public class JSONCardParser {
    private Context context;
    private JSONObject returnCard;
    private Random r;

    // class constructor
    public JSONCardParser(Context context, Random r) {
        this.context = context;
        this.r = r;

        // try parse the string to a JSON object
        try {
            JSONObject jsonFile = new JSONObject(loadJSONFromRaw());
            JSONObject categoryObject;
            JSONObject cardObject;
            JSONObject tempCard = new JSONObject();

            // pick the random category 0-4
            int randomCategoryID = r.nextInt(5);
            categoryObject = jsonFile.getJSONObject(String.valueOf(randomCategoryID));

            // pick the random card from selected category
            int randomCardID = r.nextInt(categoryObject.length());
            cardObject = categoryObject.getJSONObject(String.valueOf(randomCardID));

            tempCard.put(String.valueOf(randomCardID), cardObject);
            this.returnCard = new JSONObject();

            this.returnCard.put(String.valueOf(randomCategoryID), tempCard);
            Log.d("test", "returnCard:"+returnCard.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
    }

    public String loadJSONFromRaw() {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.cards);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public JSONObject getJsonCard() {
        return returnCard;
    }
}
