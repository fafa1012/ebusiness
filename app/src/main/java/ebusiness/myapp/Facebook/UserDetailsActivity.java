package ebusiness.myapp.Facebook;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginReg.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.PlacesPackage.AddPlaceActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.NewsFeed.UpdateStatusActivity;
import ebusiness.myapp.Util.StaticKlasse;

public class UserDetailsActivity extends Activity {

    //Test
    private ProfilePictureView userProfilePictureView;
    private TextView userNameView;
    private TextView userGenderView;
    private TextView userEmailView;
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_userdetails);
        userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
        userNameView = (TextView) findViewById(R.id.userName);
        userGenderView = (TextView) findViewById(R.id.userGender);
        userEmailView = (TextView) findViewById(R.id.userEmail);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutButtonClicked();
            }
        });
// Fetch Facebook user info if the session is active
        Session session = ParseFacebookUtils.getSession();
        if (session != null && session.isOpened()) {
            makeMeRequest();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.facebook, menu);
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
                Intent upStatus = new Intent(UserDetailsActivity.this, UpdateStatusActivity.class);
                startActivity(upStatus);
                break;
            case R.id.AddPlace:
                Intent place = new Intent(UserDetailsActivity.this, AddPlaceActivity.class);
                startActivity(place);
                break;
            case R.id.Home:
                Intent home = new Intent(UserDetailsActivity.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_map:
                Intent map = new Intent(UserDetailsActivity.this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.action_fb_profil:
                break;
            case R.id.action_settings:;
                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(UserDetailsActivity.this,LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
// Check if the user is currently logged
// and show any cached content
            updateViewsWithProfileInfo();
        } else {
// If the user is not logged in, go to the
// activity showing the login view.
            startLoginActivity();
        }
    }
    private void makeMeRequest() {
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {
// Create a JSON object to hold the profile info
                            JSONObject userProfile = new JSONObject();
                            try {
// Populate the JSON object
                                userProfile.put("facebookId", user.getId());
                                userProfile.put("name", user.getName());
                                if (user.getProperty("gender") != null) {
                                    userProfile.put("gender", (String) user.getProperty("gender"));
                                }
                                if (user.getProperty("email") != null) {
                                    userProfile.put("email", (String) user.getProperty("email"));
                                }
// Save the user profile info in a user property
                                ParseUser currentUser = ParseUser.getCurrentUser();
                                currentUser.put("profile", userProfile);
                                currentUser.saveInBackground();
// Show the user info
                                updateViewsWithProfileInfo();
                            } catch (JSONException e) {
                                Log.d(MainActivity.TAG, "Error parsing returned user data. " + e);
                            }
                        } else if (response.getError() != null) {
                            if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY) ||
                                    (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
                                Log.d(MainActivity.TAG, "The facebook session was invalidated." + response.getError());
                                onLogoutButtonClicked();
                            } else {
                                Log.d(MainActivity.TAG,
                                        "Some other error: " + response.getError());
                            }
                        }
                    }
                }
        );
        request.executeAsync();
    }
    private void updateViewsWithProfileInfo() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.has("profile")) {
            JSONObject userProfile = currentUser.getJSONObject("profile");
            try {
                if (userProfile.has("facebookId")) {
                    userProfilePictureView.setProfileId(userProfile.getString("facebookId"));
                } else {
// Show the default, blank user profile picture
                    userProfilePictureView.setProfileId(null);
                }
                if (userProfile.has("name")) {
                    userNameView.setText(userProfile.getString("name"));
                } else {
                    userNameView.setText("");
                }
                if (userProfile.has("gender")) {
                    userGenderView.setText(userProfile.getString("gender"));
                } else {
                    userGenderView.setText("");
                }
                if (userProfile.has("email")) {
                    userEmailView.setText(userProfile.getString("email"));
                } else {
                    userEmailView.setText("");
                }
            } catch (JSONException e) {
                Log.d(MainActivity.TAG, "Error parsing saved user data.");
            }
        }
    }
    private void onLogoutButtonClicked() {
// Log the user out
        ParseUser.logOut();
        StaticKlasse.status = 0;
// Go to the login view
        startLoginActivity();
    }
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
