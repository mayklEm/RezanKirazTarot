package done.rezankiraztarot.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Peter on 24.6.2014.
 */
public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_layout);
    }
}
