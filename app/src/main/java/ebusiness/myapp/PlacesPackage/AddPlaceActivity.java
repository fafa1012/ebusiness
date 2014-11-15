package ebusiness.myapp.PlacesPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.UpdateStatusActivity;
import ebusiness.myapp.Util.StaticKlasse;

public class AddPlaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (StaticKlasse.status == 0) {
            getMenuInflater().inflate(R.menu.add_place, menu);
        } else {
            getMenuInflater().inflate(R.menu.facebook, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            switch (id) {
                case R.id.updateStatus:
                    Intent upStatus = new Intent(AddPlaceActivity.this, UpdateStatusActivity.class);
                    startActivity(upStatus);
                    break;
                case R.id.AddPlace:
                    break;
                case R.id.Home:
                    Intent home = new Intent(AddPlaceActivity.this, MainActivity.class);
                    startActivity(home);
                    break;
                case R.id.action_map:
                    Intent map = new Intent(AddPlaceActivity.this, MapActivity.class);
                    startActivity(map);
                    break;
                case R.id.action_fb_profil:
                    Intent fb = new Intent(AddPlaceActivity.this, UserDetailsActivity.class);
                    startActivity(fb);
                    break;
                case R.id.action_settings:

                    break;
                case R.id.logoutUser:
                    //logout User
                    ParseUser.logOut();
                    StaticKlasse.status = 0;
                    //take User Back to the login screen
                    Intent takeUsertoLogin = new Intent(AddPlaceActivity.this, LoginActivity.class);
                    startActivity(takeUsertoLogin);
                    break;
            }

            return super.onOptionsItemSelected(item);

        }
    }

