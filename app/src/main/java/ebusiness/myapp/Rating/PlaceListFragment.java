package ebusiness.myapp.Rating;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

import ebusiness.myapp.PlacesPackage.PlaceAdapter;


/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />

 * interface.
 */
public class PlaceListFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2= "param2";

    protected List<ParseObject> mPlace;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPlaceListInteractionListener placesListener;

    // TODO: Rename and change types of parameters
    public static PlaceListFragment newInstance(String param1, String param2) {
        PlaceListFragment fragment = new PlaceListFragment();
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
    public PlaceListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Place");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> place, ParseException e) {
                if (e == null) {
                    //success
                    mPlace = place;

                    PlaceAdapter adapter = new PlaceAdapter(getListView().getContext(), mPlace);
                    setListAdapter(adapter);
                } else {
                    //there was a problem. Alert user
                }
            }
        });
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            placesListener = (OnPlaceListInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        placesListener = null;
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ParseObject placeObject = mPlace.get(position);
        String objectId = placeObject.getObjectId();
        Intent gotoShowPlaces = new Intent(this.getActivity(), ShowPlaceActivity.class);
        gotoShowPlaces.putExtra("objectId", objectId);
        startActivity(gotoShowPlaces);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPlaceListInteractionListener {
        // TODO: Update argument type and name
        public void onPlaceListInteraction(String id);
    }

}
