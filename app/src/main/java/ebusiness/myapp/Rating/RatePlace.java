package ebusiness.myapp.Rating;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by Fabian on 07.12.2014.
 */
public class RatePlace {

    protected double totalRating;
    protected int i;
    protected float avg;


    public void calculateRating2(final String objectId)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RateAndComment");
        //query.whereEqualTo("PlaceObjectId",objectId);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> list, com.parse.ParseException e) {
                                            i=0;
                                           for (ParseObject obj : list) {
                                               if(obj.getString("PlaceObjectId")==objectId) {
                                                   totalRating = totalRating + obj.getDouble("Rating");
                                                   i = i + 1;
                                               }
                                           }

                                   }

                               }
        );
        avg = (float) totalRating/i;
        avgRating(objectId);
    }

    public void CalculateRating(String objectId)
    {
        final String id = objectId;

        //ParseQuery<ParseObject> query = ParseQuery.getQuery("RateAndComment");
        ParseQuery query = new ParseQuery("RateAndComment");
        query.whereEqualTo("PlaceObjectId",objectId);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject>list,
                                                    com.parse.ParseException e) {
                                       if (e == null) {
                                           i=0;
                                           for (ParseObject obj : list) {
                                               totalRating = totalRating + obj.getDouble("Rating");
                                               i = i + 1;
                                           }
                                           avg = (float) totalRating/i;
                                           avgRating(id);
                                       } else {
                                           Log.d("Post retrieval", "Error: " + e.getMessage());
                                       }
                                   }

                               }
        );

    }

    public void avgRating(String objectId)
    {
        ParseQuery query = new ParseQuery("Place");
        query.getInBackground(objectId, new GetCallback() {
            public void done(final ParseObject object, com.parse.ParseException e) {
                if (e == null) {

                    object.put("Rating", avg);
                    object.saveInBackground(new SaveCallback() {
                        public void done(com.parse.ParseException e) {

                            object.put("Rating", avg);

                        }
                    });

                } else {
                    e.printStackTrace();
                }

            }
        });


        //avgRating = totalRating/i;
        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");


          // Retrieve the object by id
        query.getInBackground("hC4BNpuUtU", new GetCallback<ParseObject>() {
            public void done(ParseObject place, ParseException e) {
                if (e == null) {
                    place.put("Rating", 1);
                }
            }

            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {

            }
        });
*/
        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Place");
        query.whereEqualTo("objectId", objectId);
        //query.selectKeys(Arrays.asList("Rating"));
        List<ParseObject> results = null;
        try {
            results = query.find();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        ParseObject object = results.get(0);

        object.put("Rating",avgRating);

        //ParseObject test ;
        //ParseObject o = query.get("objectId",objectId);
        //query.whereEqualTo("objectId",objectId);
        //ParseObject o = query.get("objectId");
        //o.put("Rating", avgRating);
        */
    }

}
