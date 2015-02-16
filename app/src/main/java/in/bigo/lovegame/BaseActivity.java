package in.bigo.lovegame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by SPARK on 12/02/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private static int sessionDepth = 0;

    @Override
    protected void onStart() {
        super.onStart();
        sessionDepth++;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sessionDepth > 0)
            sessionDepth--;
        if (sessionDepth == 0) {

            Log.v("onpause", "onp");
            // app went to background
            Intent svc=new Intent(getApplicationContext(), BackgroundSoundService.class);
            stopService(svc); //OR stopService(svc);
        }
    }

}
