package done.rezankiraztarot.app;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Peter on 18.6.2014.
 */
public class Card {
    private String categoryID;
    private String cardID;


    private String name;
    private int cardImageID;
    private String comment;
    private String commentID;
    private boolean isFlipped;

    public Card(Context context) {

        Random r = new Random();
        JSONCardParser cardParser = new JSONCardParser(context, r);
        int randomDesc = r.nextInt(2);
        cardParser.getJsonCard();
        try {
            // get name of the first key = categoryID
            categoryID = cardParser.getJsonCard().names().get(0).toString();

            // get cardID
            cardID = cardParser.getJsonCard().getJSONObject(categoryID).names().get(0).toString();

            // temporary cardObject parsed from cardParser
            JSONObject cardObject = cardParser.getJsonCard().getJSONObject(categoryID).getJSONObject(cardID);

            // get categoryName
            name = cardObject.getString("cardName");

            // get imageID
            String imageID = "card_";
            imageID += cardObject.getString("cardImageName");
            cardImageID = context.getResources().getIdentifier(imageID, "drawable", context.getPackageName());

            //get polarity of card
            isFlipped = cardObject.getJSONObject("cardDetails").getJSONObject(String.valueOf(randomDesc)).getBoolean("isFlipped");

            // get comment and commentID
            commentID = String.valueOf(randomDesc);
            comment = cardObject.getJSONObject("cardDetails").getJSONObject(String.valueOf(randomDesc)).getString("cardComment");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**** GETTERS ****/
    public boolean isFlipped() {
        return isFlipped;
    }
    public int getCardImageID() {
        return cardImageID;
    }
    public String getName() {
        return name;
    }
    public String getComment() {
        return comment;
    }
    public String getCategoryID() {
        return categoryID;
    }
    public String getCardID() {
        return cardID;
    }
    public String getCommentID() {
        return commentID;
    }
}
