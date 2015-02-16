package in.bigo.lovegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

public class TutorialActivity extends BaseActivity {

    private ViewPager viewPager;
    private TutorialViewPagerAdapter myViewPagerAdapter;
    private ArrayList<Integer> listOfItems;
    private Animation textAnimation;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        initViews();
        googleTracker();
        setViewPagerItemsWithAdapter();
        setUiPageViewController();

        // Manually start a dispatch (Unnecessary if the tracker has a dispatch interval)
        GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();



    }


    private void googleTracker()
    {
        // Get tracker.
        Tracker t = ((LoveGame) getApplication()).getTracker(
                LoveGame.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("tutorial screen");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }




    private void setUiPageViewController() {
        dotsLayout = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        dotsCount = myViewPagerAdapter.getCount();
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(50);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }

        dots[0].setTextColor(getResources().getColor(R.color.app_yellow));
    }

    private void setViewPagerItemsWithAdapter() {
        myViewPagerAdapter = new TutorialViewPagerAdapter(listOfItems);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setOnPageChangeListener(viewPagerPageChangeListener);
    }


    //	page change listener
    OnPageChangeListener viewPagerPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setTextColor(getResources().getColor(R.color.app_grey));
            }
            dots[position].setTextColor(getResources().getColor(R.color.app_yellow));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
           //Toast.makeText(getApplicationContext(), " "+arg0+" "+arg1+"  "+arg2, Toast.LENGTH_SHORT).show();

//            if(arg0 == 4)
//            {
//                Intent intent = new Intent(getApplicationContext(), LoveActivity.class);
//                startActivity(intent);
//                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
//            }

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            //setBackgroundColor(arg0);
            //Toast.makeText(getApplicationContext(), "state "+arg0+" ", Toast.LENGTH_SHORT).show();
        }
    };

    private void initViews() {

//        getActionBar().hide();
        textAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text_animation);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

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
        private ArrayList<Integer> items;

        public TutorialViewPagerAdapter(ArrayList<Integer> listOfItems) {
            this.items = listOfItems;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            try {
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.tutorial_view_pager_item, container, false);
                TextView tutorialTextView = (TextView) view.findViewById(R.id.PageNumber);
                ButtonFlat nextButton = (ButtonFlat) view.findViewById(R.id.next_button);
                ButtonFlat skipButton = (ButtonFlat) view.findViewById(R.id.skip_button);
                tutorialTextView.setText(listOfItems.get(position).toString());
                tutorialTextView.setTypeface(Typeface.createFromAsset(getAssets(), Constants.ROBOTOFONT));
                //tutorialTextView.startAnimation(R.anim.slide_in_right);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(1 + position);
                        if(position == 4)
                        {
                            Intent intent = new Intent(getApplicationContext(), LoveActivity.class);
                            startActivity(intent);
                            overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    }
                });

                skipButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), LoveActivity.class);
                        startActivity(intent);
                        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                });


                switch (position) {
                    case 0:
                        tutorialTextView.setText(getResources().getString(R.string.question1));
                        view.setBackgroundColor(getResources().getColor(R.color.app_green));
                        skipButton.setText(getResources().getString(R.string.skip_button));
                        nextButton.setText(getResources().getString(R.string.next_button));
                        break;
                    case 1:
                        tutorialTextView.setText(getResources().getString(R.string.question2));
                        view.setBackgroundColor(getResources().getColor(R.color.app_indigo));
                        skipButton.setText(getResources().getString(R.string.skip_button));
                        nextButton.setText(getResources().getString(R.string.next_button));
                        break;
                    case 2:
                        tutorialTextView.setText(getResources().getString(R.string.question3));
                        view.setBackgroundColor(getResources().getColor(R.color.app_deep_orange));
                        skipButton.setText(getResources().getString(R.string.skip_button));
                        nextButton.setText(getResources().getString(R.string.next_button));
                        break;
                    case 3:
                        tutorialTextView.setText(getResources().getString(R.string.question4));
                        view.setBackgroundColor(getResources().getColor(R.color.app_pink));
                        skipButton.setText(getResources().getString(R.string.skip_button));
                        nextButton.setText(getResources().getString(R.string.next_button));
                        break;
                    case 4:
                        tutorialTextView.setText(getResources().getString(R.string.question5));
                        view.setBackgroundColor(getResources().getColor(R.color.app_blue));
                        skipButton.setText("");
                        nextButton.setText(getResources().getString(R.string.enter));
                        break;
                }
                ((ViewPager) container).addView(view);
                return view;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public int getCount() {
            return items.size();
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