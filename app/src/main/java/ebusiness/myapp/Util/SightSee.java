package ebusiness.myapp.Util;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import ebusiness.myapp.R;

/**
 * Created by User on 09.12.2014.
 */
public class SightSee extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "YqVll0YExesnCRN3eWDVgzxbOSSmoqMALzIRc04o", "Zj249eCqUlh01jkzg9NKhot40OoqrPFPIdWaO1SH");
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
    }
}
