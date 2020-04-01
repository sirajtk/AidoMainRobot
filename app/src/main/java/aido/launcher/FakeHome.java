package aido.launcher;

import android.app.Activity;
import android.os.Bundle;

import com.whitesuntech.aidohomerobot.R;

public class FakeHome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_home);
    }

}
