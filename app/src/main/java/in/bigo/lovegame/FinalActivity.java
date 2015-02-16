package in.bigo.lovegame;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class FinalActivity extends BaseActivity {

    TextView helpText;
    TextView thanksText;
    ImageView shareButton, mailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        helpText = (TextView) findViewById(R.id.help);
        helpText.setTypeface(Typeface.createFromAsset(getAssets(), Constants.LIGHTFONT));


        thanksText = (TextView) findViewById(R.id.thanks);
        thanksText.setTypeface(Typeface.createFromAsset(getAssets(), Constants.EXOLIGHTFONT));

        shareButton = (ImageView) findViewById(R.id.share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Here is an awesome app for finding your real relationship with your partner. Try it https://play.google.com/store/apps/details?id=in.bigo.lovegame");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

                googleTracker();
                // Manually start a dispatch (Unnecessary if the tracker has a dispatch interval)
                GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();

            }
        });


        mailButton = (ImageView) findViewById(R.id.mail);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "intrepidkarthi@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Love app!");
                startActivity(Intent.createChooser(emailIntent, "Send email"));

            }
        });



    }


    private void googleTracker()
    {
        // Get tracker.
        Tracker t = ((LoveGame) getApplication()).getTracker(
                LoveGame.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("share button click");


        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
