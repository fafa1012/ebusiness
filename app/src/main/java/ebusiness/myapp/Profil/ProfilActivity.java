package ebusiness.myapp.Profil;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginReg.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.PlacesPackage.AddPlaceActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.Util.StaticKlasse;


public class ProfilActivity extends Activity {


    protected EditText mUserPasswordNew;
    protected EditText mUserPasswordNewWdh;
    protected Button mChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        mUserPasswordNew   = (EditText) findViewById(R.id.PwNew);
        mUserPasswordNewWdh   = (EditText) findViewById(R.id.PwNewWdh);
        mChangeButton = (Button) findViewById(R.id.Changebutton);

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get passwords and convert into string
                String pwNew = mUserPasswordNew.getText().toString().trim();
                String pwNewWdh = mUserPasswordNewWdh.getText().toString().trim();

                //TODO logs entfernen
                if(mUserPasswordNew.getText().toString().equals(mUserPasswordNewWdh.getText().toString())){
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    currentUser.setPassword(pwNew);
                    currentUser.saveInBackground();
                    Toast.makeText(ProfilActivity.this,"Guter Code! Passwort geändert", Toast.LENGTH_LONG).show();
                    Log.d("TAG", pwNew);
                    Log.d("TAG", pwNewWdh);
                }   else {
                    Toast.makeText(ProfilActivity.this, "Noob! Bitte 2 gleiche passwörter eingeben", Toast.LENGTH_LONG).show();
                    Log.d("TAG", pwNew);
                    Log.d("TAG", pwNewWdh);
                }

            }
        });
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
                break;
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(ProfilActivity.this, ProfilDatenActivity.class);
                startActivity(takeUsertoProfil);
                break;
            case R.id.AddPlace:
                Intent place = new Intent(ProfilActivity.this, AddPlaceActivity.class);
                startActivity(place);
                break;
            case R.id.Home:
                Intent home = new Intent(ProfilActivity.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_map:
                Intent map = new Intent(ProfilActivity.this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.action_fb_profil:
                Intent fb = new Intent(ProfilActivity.this, UserDetailsActivity.class);
                startActivity(fb);
                break;
            case R.id.action_settings:

                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(ProfilActivity.this, LoginActivity.class);
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
            View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
            return rootView;
        }
    }
}
