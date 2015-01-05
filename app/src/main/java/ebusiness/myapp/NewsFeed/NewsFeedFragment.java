package ebusiness.myapp.NewsFeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * interface.
 */
public class NewsFeedFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected List<ParseObject> mNews;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnNewsFeedInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static NewsFeedFragment newInstance(String param1, String param2) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsFeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> news, ParseException e) {
                if(e==null){
                    //success
                    mNews = news;

                    NewsAdapter adapter = new NewsAdapter(getListView().getContext(), mNews);
                    setListAdapter(adapter);
                }
                else{
                    //there was a problem. Alert user
                }
            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNewsFeedInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnNewsFeedInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.println(10000000, "Hilfe", "Listener Newsfeed geht!");
        ParseObject newsObject = mNews.get(position);
        String objectId = newsObject.getObjectId();

        Intent goToDetailView = new Intent(getActivity().getApplicationContext(), NewsDetailView.class);
        goToDetailView.putExtra("objectId", objectId);
        startActivity(goToDetailView);
    }

    public interface OnNewsFeedInteractionListener {
        // TODO: Update argument type and name
        public void onNewsFeedInteraction(String id);
    }

}
