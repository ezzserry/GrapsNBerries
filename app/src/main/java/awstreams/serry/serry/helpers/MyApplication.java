package awstreams.serry.serry.helpers;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Serry on 7/8/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());

    }
}
