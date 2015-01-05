package ebusiness.myapp.Rating;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.util.List;

import ebusiness.myapp.MainActivity;
import ebusiness.myapp.NewsFeed.NewsAdapter;
import ebusiness.myapp.R;
import ebusiness.myapp.Util.StaticKlasse;

public class RateAndComment extends Activity {

    protected EditText mComment;
    protected RatingBar mRating;
    protected Button mSaveBtn;
    String objectId;
    String rated;


    protected float totalRating;
    protected int i;
    protected float avgRating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_and_comment);



        //get the intent that started the activity
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        mComment = (EditText)findViewById(R.id.RateAndCommentComment);
        mRating = (RatingBar)findViewById(R.id.RateAndCommentRatingBar);
        mRating.setNumStars(4);
        mSaveBtn = (Button)findViewById(R.id.RateAndCommentSaveBtn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                final String currentUserName = currentUser.getUsername();


                String comment = mComment.getText().toString();
                float rating = (float) mRating.getRating();


                //rated = "yes";

                //CheckOnRating(currentUserName);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("RateAndComment");
                query.whereEqualTo("PlaceObjectId", objectId);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, com.parse.ParseException e) {
                        if (e == null) {
                            //success

                            for (ParseObject obj : list) {
                                String text = obj.getString("User");
                                if (text == currentUserName) {
                                    Log.d("text","in Schleife");
                                    rated = "yes";
                                    break;
                                }
                                else
                                    rated = "no";

                            }
                        }
                        else{
                                rated = "no";
                            }
                        }

                });

                if(rating == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RateAndComment.this);
                    builder.setMessage("Rating schould not be empty");
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
                }else {
                    if (rated == "yes") {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RateAndComment.this);
                        builder.setMessage("You rated already. You can only rate one time");
                        builder.setTitle("Sorry!");
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

                    else{
                        //save in backend.
                        ParseObject RateAndCommentObject = new ParseObject("RateAndComment");
                        RateAndCommentObject.put("User", currentUserName);
                        RateAndCommentObject.put("Comment", comment);
                        RateAndCommentObject.put("Rating", rating);
                        RateAndCommentObject.put("PlaceObjectId",objectId);
                        RateAndCommentObject.saveInBackground();

                        Toast.makeText(RateAndComment.this, "Success!", Toast.LENGTH_LONG).show();

                        RatePlace var = new RatePlace();
                        //var.CalculateRating(objectId);
                        //var.calculateRating2(objectId);
                        var.CalculateRating(objectId);
                        Log.d("Call Calculate Rating","Mesage");

                           //Take user to start page
                        Intent takeUserHome = new Intent(RateAndComment.this, MainActivity.class);
                        startActivity(takeUserHome);

                    }
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    private void CheckOnRating()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RateAndComment");
        query.whereEqualTo("objectId", objectId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    //success
                    if (list.isEmpty())
                        rated = "no";

                    for (ParseObject obj : list) {
                        if (obj.get("User") != currentUserNmae)
                            rated = "no";
                    }
                } else {
                    rated = "yes";
                }
            }
        });
    }*/

}
