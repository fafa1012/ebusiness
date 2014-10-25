package ebusiness.myapp.Facebook;


import android.app.Application;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import ebusiness.myapp.R;

/**
 * Created by User on 25.10.2014.
 */
public class IntegratingFacebookApplication extends Application {
    static final String TAG = "MyApp";
    @Override
    public void onCreate() {
        super.onCreate();
        //TODO Parse Keys müssen hier noch eingetragen werden!
        Parse.initialize(this, "YOUR_PARSE_APPLICATION_ID",
                "YOUR_PARSE_CLIENT_KEY");
// Set your Facebook App Id in strings.xml
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
    }
}
