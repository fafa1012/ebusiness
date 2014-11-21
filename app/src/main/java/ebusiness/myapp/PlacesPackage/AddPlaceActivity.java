package ebusiness.myapp.PlacesPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.UpdateStatusActivity;
import ebusiness.myapp.Util.StaticKlasse;

public class AddPlaceActivity extends Activity {
    //protected Button mRecordButton;

    protected EditText mAddPlaceTitleText;
    protected EditText mAddPlacePostcodeText;
    protected EditText mAddPlaceStreetText;
    protected EditText mAddPlaceDescriptionText;
    protected RatingBar mAddPlaceRating;
    protected ImageButton mAddPlacePhotoBtn;
    protected Button mAddPlaceSaveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "YqVll0YExesnCRN3eWDVgzxbOSSmoqMALzIRc04o", "Zj249eCqUlh01jkzg9NKhot40OoqrPFPIdWaO1SH");
        setContentView(R.layout.activity_add_place);

        //Initialize
        mAddPlaceTitleText = (EditText) findViewById(R.id.AddPlaceTitleTextbox);
        mAddPlacePostcodeText = (EditText) findViewById(R.id.AddPlacePlzTextbox);
        mAddPlaceStreetText = (EditText) findViewById(R.id.AddPlaceStreetTextbox);
        mAddPlaceDescriptionText = (EditText) findViewById(R.id.AddPlaceDescriptionTextbox);
        mAddPlaceRating = (RatingBar) findViewById(R.id.AddPlaceRatingBar);
        mAddPlacePhotoBtn = (ImageButton) findViewById(R.id.AddPlacePhotoButton);
        mAddPlaceSaveBtn = (Button) findViewById(R.id.AddPlaceSaveButton);


        mAddPlaceSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserName = currentUser.getUsername();

                String title = mAddPlaceTitleText.getText().toString();
                String postcode = mAddPlacePostcodeText.getText().toString();
                String street = mAddPlaceStreetText.getText().toString();
                String description = mAddPlaceDescriptionText.getText().toString();
                Float rating = mAddPlaceRating.getRating();


                if (title.isEmpty()) {
                    //there was a problem, storing the new status
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPlaceActivity.this);
                    builder.setMessage("Title should not be empty");
                    builder.setTitle("Oops!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //close the Dialog
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


                    ParseObject placeObject = new ParseObject("Place");
                    placeObject.put("Title", title);
                    placeObject.put("Plz", postcode);
                    placeObject.put("Street", street);
                    placeObject.put("Description", description);
                    placeObject.put("Rating", rating);
                    placeObject.put("User", currentUserName);
                    placeObject.saveInBackground();

                    Toast.makeText(AddPlaceActivity.this, "Success!", Toast.LENGTH_LONG).show();

                    setNews(title, currentUserName);

                    Intent takeUserHome = new Intent(AddPlaceActivity.this, MainActivity.class);
                    startActivity(takeUserHome);
                }
            }
        });
    }

    public void setNews(String news, String currentUserName)
    {
        String newsfeed = "SightSee Object " + news + " was created";
        ParseObject newsObject = new ParseObject("Status");
        newsObject.put("newStatus", newsfeed);
        newsObject.put("user", currentUserName);
        newsObject.saveInBackground();
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

