package ebusiness.myapp.Profil;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.parse.ParseUser;

import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginReg.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.NewsFeed.UpdateStatusActivity;
import ebusiness.myapp.Util.StaticKlasse;


public class ProfilDatenActivity extends Activity {

    protected Button mToChangePwButton;
    protected TextView mShowName;
    protected TextView mShowEmail;
    protected NumberPicker mNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_daten);



        mToChangePwButton = (Button) findViewById(R.id.toChangePwButton);
        mShowName = (TextView) findViewById(R.id.showName);
        mShowEmail = (TextView) findViewById(R.id.showEmail);

        mToChangePwButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent takeUsertoProfil = new Intent(ProfilDatenActivity.this, ProfilActivity.class);
                startActivity(takeUsertoProfil);
            }
        });

        //Get current user
        ParseUser currentUser = ParseUser.getCurrentUser();

        //Convert name and email of current user into string
        String struser = currentUser.getUsername().toString();
        String cEmail = currentUser.getEmail().toString();

        //write name + email into Textview
        mShowName.setText(struser);
        mShowEmail.setText(cEmail);

        //Numberpicker
        mNumberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setDisplayedValues(new String[]{"0", "10", "20", "50"});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.allgemein, menu);
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
                Intent upStatus = new Intent(ProfilDatenActivity.this, UpdateStatusActivity.class);
                startActivity(upStatus);
                break;
            case R.id.AddPlace:
                break;
            case R.id.Home:
                Intent home = new Intent(ProfilDatenActivity.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_map:
                Intent map = new Intent(ProfilDatenActivity.this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.profil:
                break;
            case R.id.action_settings:
                break;
            case R.id.logoutUser:
//logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
//take User Back to the login screen
                Intent takeUsertoLogin = new Intent(ProfilDatenActivity.this, LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profil_daten, container, false);
            return rootView;
        }
    }
}
