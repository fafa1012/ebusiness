package ebusiness.myapp.NewsFeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginReg.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.PlacesPackage.AddPlaceActivity;
import ebusiness.myapp.Profil.ProfilDatenActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.Util.StaticKlasse;


public class NewsDetailView extends Activity {

    String objectId;
    protected TextView mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_view);

        //Initialize
        mNews = (TextView)findViewById(R.id.newsDetailView);

        //get the intent that started the activity
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    //success we have a status/news
                    String userNews = parseObject.getString("newStatus");
                    mNews.setText(userNews);
                } else {

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(StaticKlasse.status == 0)
        {
            getMenuInflater().inflate(R.menu.allgemein, menu);
        }
        else
        {
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
        switch(id) {
            case R.id.updateStatus:
                Intent upStatus = new Intent(NewsDetailView.this, UpdateStatusActivity.class);
                startActivity(upStatus);
                break;
            case R.id.AddPlace:
                Intent place = new Intent(NewsDetailView.this, AddPlaceActivity.class);
                startActivity(place);
                break;
            case R.id.Home:
                Intent home = new Intent(NewsDetailView.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_map:
                Intent map = new Intent(NewsDetailView.this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.action_fb_profil:
                Intent fb = new Intent(NewsDetailView.this, UserDetailsActivity.class);
                startActivity(fb);
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(NewsDetailView.this, ProfilDatenActivity.class);
                startActivity(takeUsertoProfil);
                break;
            case R.id.action_settings:;
                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(NewsDetailView.this,LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
