package ebusiness.myapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseFacebookUtils;


public class MyActivity extends ListActivity {

    //überschreibt Back-Button
    //TODO bei Doubleclick logout siehe Loginactivity
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Parse.initialize(this, "YqVll0YExesnCRN3eWDVgzxbOSSmoqMALzIRc04o", "Zj249eCqUlh01jkzg9NKhot40OoqrPFPIdWaO1SH");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
        } else {
            // show the login screen
            Intent takeUserToLogin = new Intent(MyActivity.this,LoginActivity.class);
            startActivity(takeUserToLogin);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.updateStatus:
                //takeUser to Update Status Activity
                Intent intent = new Intent(MyActivity.this,UpdateStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(MyActivity.this, ProfilActivity.class);
                startActivity(takeUsertoProfil);
                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                MainActivity.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(MyActivity.this,LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
