package ebusiness.myapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;


public class ProfilDatenActivity extends Activity {

    protected Button mToChangePwButton;
    protected TextView mShowName;
    protected TextView mShowEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_daten);

        Parse.initialize(this, "YqVll0YExesnCRN3eWDVgzxbOSSmoqMALzIRc04o", "Zj249eCqUlh01jkzg9NKhot40OoqrPFPIdWaO1SH");

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profil_daten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
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
