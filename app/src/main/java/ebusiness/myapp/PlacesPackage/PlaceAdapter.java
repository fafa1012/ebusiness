package ebusiness.myapp.PlacesPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.List;

import ebusiness.myapp.R;

/**
 * Created by Fabian on 21.11.2014.
 */
public class PlaceAdapter extends ArrayAdapter<ParseObject> {
protected Context mContext;
protected List<ParseObject> mPlace;

public PlaceAdapter(Context context, List<ParseObject> place){
        super(context, R.layout.placelistcustomlayout , place);
        mContext = context;
        mPlace = place;
        }

@Override
public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.placelistcustomlayout, null);
            holder = new ViewHolder();
            holder.placeCustomLayoutImageView = (ParseImageView) convertView
                    .findViewById(R.id.placeCustomLayoutImageView);
            holder.placeCustomLayoutTextview = (TextView) convertView
                    .findViewById(R.id.placeCustomLayoutTextview);
            holder.placeCustomLayoutRatingBar = (RatingBar)convertView
                    .findViewById(R.id.placeCustomLayoutRatingBar);

            convertView.setTag(holder);
        }
        else{
        holder = (ViewHolder) convertView.getTag();
        }

        ParseObject placeObject = mPlace.get(position);

        //title
        String title = placeObject.getString("Title");
        holder.placeCustomLayoutTextview.setText(title);

        //Image
        ParseImageView placeImage = (ParseImageView) convertView.findViewById(R.id.placeCustomLayoutImageView);
        ParseFile image = placeObject.getParseFile("Image");
        if(image != null)
        {
            placeImage.setParseFile(image);
            placeImage.loadInBackground();
        }


        //Integer rating = placeObject.getInt("Rating");
        float rating = (float) placeObject.getDouble("Rating");
        holder.placeCustomLayoutRatingBar.setEnabled(false);
        holder.placeCustomLayoutRatingBar.setNumStars(4);
        holder.placeCustomLayoutRatingBar.setRating(rating);

            return convertView;
        }

public static class ViewHolder{
    //ImageView
    ImageView placeCustomLayoutImageView;
    TextView placeCustomLayoutTextview;
    RatingBar placeCustomLayoutRatingBar;
}
}
