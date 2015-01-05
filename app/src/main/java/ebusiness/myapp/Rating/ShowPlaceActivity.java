package ebusiness.myapp.Rating;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import ebusiness.myapp.MainActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.Util.StaticKlasse;

public class ShowPlaceActivity extends Activity {

    String objectId;
    protected TextView mTitle;
    protected ParseImageView mPlaceImage;
    protected TextView mShowPlaceStreet;
    protected TextView mPlz;
    protected RatingBar mRatingBar;
    protected Button mRateAndComment;
    protected Button mShowCommentAndRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place);

        //Initialize
        mTitle = (TextView) findViewById(R.id.showPlaceTitle);
        mPlaceImage = (ParseImageView) findViewById(R.id.showPlaceImage);
        mShowPlaceStreet = (TextView) findViewById(R.id.showPlaceStreet);
        mPlz = (TextView) findViewById(R.id.showPlacePlz);
        mRatingBar = (RatingBar) findViewById(R.id.showPlaceRatingBar);
        mRateAndComment = (Button) findViewById(R.id.showPlaceRateComment);
        mShowCommentAndRate = (Button) findViewById(R.id.showPlaceShowRateComment);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        mRateAndComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeUserToRateAndComment = new Intent(ShowPlaceActivity.this, RateAndComment.class);
                takeUserToRateAndComment.putExtra("objectId", objectId);
                startActivity(takeUserToRateAndComment);
            }
        });

        mShowCommentAndRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Place");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    //success
                    //String userNews = parseObject.getString("newStatus");
                    //mNews.setText(userNews);
                    mTitle.setText(parseObject.getString("Title"));
                    //mPlaceImage.setParseFile(parseObject.getParseFile("Image"));

                    ParseFile image = parseObject.getParseFile("Image");
                    if(image != null)
                    {
                        mPlaceImage.setParseFile(image);
                        mPlaceImage.loadInBackground();
                    }

                    mShowPlaceStreet.setText(parseObject.getString("Street"));
                    mPlz.setText(parseObject.getString("Plz"));
                    mRatingBar.setEnabled(false);
                    mRatingBar.setNumStars(4);
                    mRatingBar.setRating((float)parseObject.getDouble("Rating"));



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
}
