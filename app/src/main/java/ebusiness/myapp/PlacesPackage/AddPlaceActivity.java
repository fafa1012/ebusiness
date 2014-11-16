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

import com.parse.ParseObject;
import com.parse.ParseUser;

import ebusiness.myapp.MainActivity;
import ebusiness.myapp.R;

public class AddPlaceActivity extends Activity {

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


                    //Take user to start page
                    Intent takeUserHome = new Intent(AddPlaceActivity.this, MainActivity.class);
                    startActivity(takeUserHome);
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_place, menu);
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
}
