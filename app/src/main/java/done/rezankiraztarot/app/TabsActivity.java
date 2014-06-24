package done.rezankiraztarot.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import done.rezankiraztarot.app.adapters.TabsPagerAdapter;

/**
 * Created by Peter on 15.6.2014.
 */
public class TabsActivity extends FragmentActivity implements ActionBar.TabListener{
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private android.app.ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Card", "Details", "Buy Video"};
    private String categoryID;
    private String cardID;



    private String cardName;
    private int imageID;
    private String commentID;
    private boolean isFlipped;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.tabs_activity);

        // get extras from AllCardsActivity
        Intent intent = getIntent();
        categoryID = intent.getStringExtra("categoryID");
        cardID = intent.getStringExtra("cardID");
        cardName = intent.getStringExtra("cardName");
        imageID = intent.getIntExtra("imageID", 0);
        commentID = intent.getStringExtra("commentID");
        comment = intent.getStringExtra("comment");
        isFlipped = intent.getBooleanExtra("isFlipped", false);

        // navigation buttons
        Button buttonHome = (Button) findViewById(R.id.home_button);
        final Button buttonLeft = (Button) findViewById(R.id.left_button);
        final Button buttonRight = (Button) findViewById(R.id.right_button);

        // viewpager initialisation
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        actionBar = getActionBar();
        actionBar.hide();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // home button will return user back to all cards_old view
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });


        // Adding Tabs in ViewPager
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        // on swiping the viewpager make respective tab selected
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page manage navigation buttons visibility
                buttonLeft.setVisibility(View.VISIBLE);
                buttonRight.setVisibility(View.VISIBLE);
                actionBar.setSelectedNavigationItem(position);
                switch (position) {
                    case 0:
                        buttonLeft.setVisibility(View.GONE);
                        break;
                    case 2:
                        buttonRight.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /* GET METHODS*/
    public int getImageID() {
        return imageID;
    }
    public boolean isFlipped() {
        return isFlipped;
    }
    public String getComment() {
        return comment;
    }
    public String getCommentID() {
        return commentID;
    }
    public String getCardID() {
        return cardID;
    }
    public String getCategoryID() {
        return categoryID;
    }
    public String getCardName() {
        return cardName;
    }
}
