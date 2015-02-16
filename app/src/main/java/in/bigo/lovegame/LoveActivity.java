package in.bigo.lovegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

public class LoveActivity extends BaseActivity {

    private ViewPager viewPager;
    private TutorialViewPagerAdapter myViewPagerAdapter;
    private ArrayList<Integer> listOfItems;
    private Animation textAnimation;
    private LinearLayout dotsLayout;
    ImageView settingsButton;
    private int dotsCount;
    private TextView[] dots;
    private SharedPreferencesController sharedPreferencesController;
    boolean isMusicOn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        initViews();
        setViewPagerItemsWithAdapter();
        googleTracker();


        // Manually start a dispatch (Unnecessary if the tracker has a dispatch interval)
        GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();


    }



    private void setViewPagerItemsWithAdapter() {
        myViewPagerAdapter = new TutorialViewPagerAdapter(Constants.QUESTION_ARRAY, Constants.BACKGROUND_COLOR_ARRAY);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        //viewPager.setOffscreenPageLimit(5);
        viewPager.setOnPageChangeListener(viewPagerPageChangeListener);
    }


    private void googleTracker()
    {
        // Get tracker.
        Tracker t = ((LoveGame) getApplication()).getTracker(
                LoveGame.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("game screen");


        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }


    private void questionsTracker(int i)
    {
        if(i == 0)
        {
            // Get tracker.
            Tracker t = ((LoveGame) getApplication()).getTracker(
                    LoveGame.TrackerName.APP_TRACKER);

            // Set screen name.
            t.setScreenName("set 1 questions");


            // Send a screen view.
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        if(i == 1)
        {
            // Get tracker.
            Tracker t = ((LoveGame) getApplication()).getTracker(
                    LoveGame.TrackerName.APP_TRACKER);

            // Set screen name.
            t.setScreenName("set 2 questions");


            // Send a screen view.
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        if(i == 2)
        {
            // Get tracker.
            Tracker t = ((LoveGame) getApplication()).getTracker(
                    LoveGame.TrackerName.APP_TRACKER);

            // Set screen name.
            t.setScreenName("set 3 questions");


            // Send a screen view.
            t.send(new HitBuilders.AppViewBuilder().build());
        }


    }




    //	page change listener
    OnPageChangeListener viewPagerPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            if(arg0 == 37)
//            {
//                Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
//                startActivity(intent);
//                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
//            }

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            //setBackgroundColor(arg0);
        }
    };

    private void initViews() {

//        getActionBar().hide();
        //textAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        settingsButton = (ImageView) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        sharedPreferencesController = SharedPreferencesController.getSharedPreferencesController(this);
        isMusicOn = sharedPreferencesController.getBoolean("music");

        if(isMusicOn)
        {
            Intent svc=new Intent(getApplicationContext(), BackgroundSoundService.class);
            startService(svc); //OR stopService(svc);
        }


        listOfItems = new ArrayList<Integer>();
        listOfItems.add(1);
        listOfItems.add(2);
        listOfItems.add(3);
        listOfItems.add(4);
        listOfItems.add(5);
    }

    //	adapter
    public class TutorialViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        private Integer[] items;
        private Integer[] colors;

        public TutorialViewPagerAdapter(Integer[] listOfItems, Integer[] listofcolors) {
            this.items = listOfItems;
            this.colors = listofcolors;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            try {
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.game_view_pager_item, container, false);
                TextView tutorialTextView = (TextView) view.findViewById(R.id.PageNumber);
                final ButtonFlat nextButton = (ButtonFlat) view.findViewById(R.id.next_button);
                //tutorialTextView.setText(listOfItems.get(position).toString());

                if(position == 11)
                    questionsTracker(0);

                if(position == 24)
                    questionsTracker(1);

                if(position == 37)
                    questionsTracker(2);

                if (position >= 0 && position <= 11)
                    tutorialTextView.setTypeface(Typeface.createFromAsset(getAssets(), Constants.LIGHTFONT));

                if (position >= 13 && position <= 24)
                    tutorialTextView.setTypeface(Typeface.createFromAsset(getAssets(), Constants.EXOLIGHTFONT));

                if (position >= 26 && position <= 37)
                    tutorialTextView.setTypeface(Typeface.createFromAsset(getAssets(), Constants.LIGHTFONT));


                //tutorialTextView.startAnimation(R.anim.slide_in_right);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Toast.makeText(getApplicationContext(), "" + position + "  " + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();



                        if (position <= 37)
                            viewPager.setCurrentItem(1 + viewPager.getCurrentItem());
                        if (position == 37) {

                            nextButton.setText(getResources().getString(R.string.finish));
                            Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
                            startActivity(intent);
                            overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
                            //finish();
                        } else
                            nextButton.setText(getResources().getString(R.string.next_button));


                    }
                });

                //Toast.makeText(getApplicationContext(), ""+colors[position], Toast.LENGTH_SHORT).show();

                //tutorialTextView.setAnimation(textAnimation);
                tutorialTextView.setText(items[position]);


                view.setBackgroundColor(getResources().getColor(colors[position]));


                ((ViewPager) container).addView(view);
                return view;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            ((ViewPager) container).removeView(view);
        }
    }
}