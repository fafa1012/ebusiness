package ebusiness.myapp.PlacesPackage;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.LocationLI;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.ProfilActivity;
import ebusiness.myapp.ProfilDatenActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.UpdateStatusActivity;
import ebusiness.myapp.Util.StaticKlasse;
public class AddPlaceActivity extends Activity implements LocationListener {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    protected EditText mAddPlaceTitleText;
    protected EditText mAddPlacePostcodeText;
    protected EditText mAddPlaceStreetText;
    protected EditText mAddPlaceDescriptionText;
    protected RatingBar mAddPlaceRating;
    protected ImageButton mAddPlacePhotoBtn;
    protected Button mAddPlaceSaveBtn;
    private Uri fileUri;
    private ParseFile photoFile;
    private ImageView imgPreview;
    private Location loc;
    private static File getOutputMediaFile(int type) {
// External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
// Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
// Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        setContentView(R.layout.activity_add_place);
        getLocation();
//Initialize
        mAddPlaceTitleText = (EditText) findViewById(R.id.AddPlaceTitleTextbox);
        mAddPlacePostcodeText = (EditText) findViewById(R.id.AddPlacePlzTextbox);
        mAddPlaceStreetText = (EditText) findViewById(R.id.AddPlaceStreetTextbox);
        mAddPlaceDescriptionText = (EditText) findViewById(R.id.AddPlaceDescriptionTextbox);
        mAddPlaceRating = (RatingBar) findViewById(R.id.AddPlaceRatingBar);
        mAddPlaceRating.setNumStars(4);
        mAddPlacePhotoBtn = (ImageButton) findViewById(R.id.AddPlacePhotoButton);
        mAddPlaceSaveBtn = (Button) findViewById(R.id.AddPlaceSaveButton);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        mAddPlacePhotoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // create Intent to take a picture and return control to the calling application
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); // start the image capture Intent
            }
        });
        mAddPlaceSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserName = currentUser.getUsername();
                String title = mAddPlaceTitleText.getText().toString();
                String postcode = mAddPlacePostcodeText.getText().toString();
                String street = mAddPlaceStreetText.getText().toString();
                String description = mAddPlaceDescriptionText.getText().toString();
                Float rating = mAddPlaceRating.getRating();
                double longi = loc.getLongitude();
                double lati = loc.getLatitude();
                if (title.isEmpty()) {
//there was a problem, storing the new status
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPlaceActivity.this);
                    builder.setMessage("Bitte einen Titel hinzufügen");
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
                    if (fileUri == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlaceActivity.this);
                        builder.setMessage("Bitte ein Bild hinzufügen");
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
                        BitmapFactory.Options options = new BitmapFactory.Options();
// downsizing image as it throws OutOfMemory Exception for larger
// images
                        options.inSampleSize = 8;
                        final Bitmap mealImageScaled = BitmapFactory.decodeFile(fileUri.getPath(),
                                options);
// Falls ImageSkalierung notwendig sein muss
// Matrix matrix = new Matrix();
// matrix.postRotate(90);
// Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
// 0, 300, 300,
// matrix, true);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        mealImageScaled.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        byte[] scaledData = bos.toByteArray();
                        photoFile = new ParseFile("place_photo.jpg", scaledData);
                        ParseObject placeObject = new ParseObject("Place");
                        placeObject.put("Title", title);
                        placeObject.put("Plz", postcode);
                        placeObject.put("Street", street);
                        placeObject.put("Description", description);
                        placeObject.put("Rating", rating);
                        placeObject.put("User", currentUserName);
                        placeObject.put("Image", photoFile);
                        placeObject.put("Longitude", longi);
                        placeObject.put("Latitude", lati);
                        placeObject.setACL(new ParseACL());
                        placeObject.getACL().setPublicReadAccess(true);
                        placeObject.getACL().setPublicWriteAccess(true);
                        placeObject.saveInBackground();
                        Toast.makeText(AddPlaceActivity.this, "Great Job *Tips Fedora*!", Toast.LENGTH_LONG).show();
                        setNews(title, currentUserName);
                        Intent takeUserHome = new Intent(AddPlaceActivity.this, MainActivity.class);
                        startActivity(takeUserHome);
                    }
                }
            }
        });
    }
    public void setNews(String news, String currentUserName) {
        String newsfeed = "SightSee Object " + news + " was created";
        ParseObject newsObject = new ParseObject("Status");
        if(StaticKlasse.status == 0)
        {
            currentUserName = currentUserName;
        }
        else
        {
            try {
                ParseUser currentUser = ParseUser.getCurrentUser();
                JSONObject userProfile = currentUser.getJSONObject("profile");
                currentUserName = userProfile.getString("name");
            }
            catch(Exception e)
            {

            }
        }
        newsObject.put("newStatus", newsfeed);
        newsObject.put("user", currentUserName);
        newsObject.setACL(new ParseACL());
        newsObject.getACL().setPublicReadAccess(true);
        newsObject.getACL().setPublicWriteAccess(true);
        newsObject.saveInBackground();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
// save file url in bundle as it will be null on screen orientation
// changes
        outState.putParcelable("file_uri", fileUri);
    }
    /*
    * Here we restore the fileUri again
    */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
// get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        if (StaticKlasse.status == 0) {
            getMenuInflater().inflate(R.menu.allgemein, menu);
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
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(AddPlaceActivity.this, ProfilDatenActivity.class);
                startActivity(takeUsertoProfil);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
// successfully captured the image
// display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
// user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
// failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void previewCapturedImage() {
        try {
            imgPreview.setVisibility(View.VISIBLE);
// bitmap factory
            BitmapFactory.Options options = new BitmapFactory.Options();
// downsizing image as it throws OutOfMemory Exception for larger
// images
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationLI locationListener = new LocationLI();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationManager.removeUpdates(locationListener);


    }

    @Override
    public void onLocationChanged(Location location) { loc = location;

    }
}

