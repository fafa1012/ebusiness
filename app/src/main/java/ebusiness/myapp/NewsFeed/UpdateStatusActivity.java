package ebusiness.myapp.NewsFeed;

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
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginReg.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.PlacesPackage.AddPlaceActivity;
import ebusiness.myapp.Profil.ProfilDatenActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.Util.StaticKlasse;


public class UpdateStatusActivity extends Activity {

    protected EditText mStatusUpdate;
    protected Button mStatusUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);


        //initialize
        mStatusUpdate = (EditText) findViewById(R.id.updateStatusTextbox);
        mStatusUpdateBtn = (Button) findViewById(R.id.statusUpdateBtn);

        //listen to status update button click
        mStatusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserName = currentUser.getUsername();

                //get the Status user has entered, convert it to a string
                String newStatus = mStatusUpdate.getText().toString();

                if (newStatus.isEmpty()) {
                    //there was a problem, storing the new status
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                    builder.setMessage("status should not be empty");
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

                    //save the status in backend.
                    ParseObject statusObject = new ParseObject("Status");
                    statusObject.put("newStatus", newStatus);
                    if(StaticKlasse.status == 0)
                    {
                        statusObject.put("user", currentUserName);
                    }
                    else
                    {
                        try {
                            JSONObject userProfile = currentUser.getJSONObject("profile");
                            currentUserName = userProfile.getString("name");
                        }
                        catch(Exception e)
                        {

                        }
                        statusObject.put("user", currentUserName);
                    }

                    statusObject.setACL(new ParseACL());
                    statusObject.getACL().setPublicReadAccess(true);
                    statusObject.getACL().setPublicWriteAccess(true);
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //successfully stored the status in parse
                                Toast.makeText(UpdateStatusActivity.this, "Success!", Toast.LENGTH_LONG).show();

                                //Take user to start page
                                Intent takeUserHome = new Intent(UpdateStatusActivity.this, MainActivity.class);
                                startActivity(takeUserHome);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                                builder.setMessage("status should not be empty");
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
                            }

                        }

                    });
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
        if(StaticKlasse.status == 1)
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
        switch (id) {
            case R.id.updateStatus:
                break;
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(UpdateStatusActivity.this, ProfilDatenActivity.class);
                startActivity(takeUsertoProfil);
                break;
            case R.id.AddPlace:
                Intent place = new Intent(UpdateStatusActivity.this, AddPlaceActivity.class);
                startActivity(place);
                break;
            case R.id.Home:
                Intent home = new Intent(UpdateStatusActivity.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_map:
                Intent map = new Intent(UpdateStatusActivity.this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.action_fb_profil:
                Intent fb = new Intent(UpdateStatusActivity.this, UserDetailsActivity.class);
                startActivity(fb);
                break;
            case R.id.action_settings:

                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(UpdateStatusActivity.this, LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}

