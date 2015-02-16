package in.bigo.lovegame;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Switch;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class SettingsActivity extends BaseActivity {

    private Switch musicSwitch;
    private boolean isMusicOn;
    private Slider volumeSlider;
    private AudioManager audioManager;
    private SharedPreferencesController sharedPreferencesController;
    private TextView shareText, feedbackText, versionText, webText;
    private PackageManager packageManager;
    private PackageInfo pInfo;
    private String version;
    private int volume;

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        musicSwitch = (Switch) findViewById(R.id.music_switch);
        volumeSlider = (Slider) findViewById(R.id.volumeslider);
        shareText = (TextView) findViewById(R.id.share);
        feedbackText = (TextView) findViewById(R.id.feedback);
        versionText = (TextView) findViewById(R.id.version);
        webText = (TextView) findViewById(R.id.web);

        sharedPreferencesController = SharedPreferencesController.getSharedPreferencesController(this);
        isMusicOn = sharedPreferencesController.getBoolean("music");
        volume = sharedPreferencesController.getInt("volume");
        volumeSlider.setValue(volume);

        musicSwitch.setChecked(isMusicOn);


        volumeSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int i) {
                sharedPreferencesController.putInt("volume", i);
                //Toast.makeText(getApplicationContext(), ""+i, Toast.LENGTH_SHORT).show();
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        i, 0);
            }
        });

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            versionText.setText("App version: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTracker(0);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Here is an awesome app for finding your real relationship with your partner. Try it https://play.google.com/store/apps/details?id=in.bigo.lovegame");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

                // Manually start a dispatch (Unnecessary if the tracker has a dispatch interval)
                GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();
            }
        });

        feedbackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTracker(1);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "intrepidkarthi@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Love app!");
                startActivity(Intent.createChooser(emailIntent, "Send email"));

                // Manually start a dispatch (Unnecessary if the tracker has a dispatch interval)
                GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();
            }
        });

        webText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTracker(2);
            }
        });

        musicSwitch.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(boolean b) {

                Log.d("notification ", b + "");

                sharedPreferencesController.putBoolean("music", b);

                if(b)
                {
                    Intent svc=new Intent(getApplicationContext(), BackgroundSoundService.class);
                    startService(svc); //OR stopService(svc);
                }
                else
                {
                    Intent svc=new Intent(getApplicationContext(), BackgroundSoundService.class);
                    stopService(svc); //OR stopService(svc);
                }


//                if(b) {
//                    notifications.setText(R.string.disable_notifications);
//                }
//                else {
//                    notifications.setText(R.string.enable_notifications);
//                }
            }
        });


        // Manually start a dispatch (Unnecessary if the tracker has a dispatch interval)
        GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();




    }


    private void clickTracker(int i)
    {
        if(i == 0)
        {
            // Get tracker.
            Tracker t = ((LoveGame) getApplication()).getTracker(
                    LoveGame.TrackerName.APP_TRACKER);

            // Set screen name.
            t.setScreenName("share link");


            // Send a screen view.
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        if(i == 1)
        {
            // Get tracker.
            Tracker t = ((LoveGame) getApplication()).getTracker(
                    LoveGame.TrackerName.APP_TRACKER);

            // Set screen name.
            t.setScreenName("feedback link");


            // Send a screen view.
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        if(i == 2)
        {
            // Get tracker.
            Tracker t = ((LoveGame) getApplication()).getTracker(
                    LoveGame.TrackerName.APP_TRACKER);

            // Set screen name.
            t.setScreenName("web page");


            // Send a screen view.
            t.send(new HitBuilders.AppViewBuilder().build());
        }


    }





    @Override
    protected void onPause() {
        super.onPause();
        //Intent svc=new Intent(getApplicationContext(), BackgroundSoundService.class);
        //stopService(svc); //OR stopService(svc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
