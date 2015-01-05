package ebusiness.myapp.GoogleMaps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.LoginActivity;
import ebusiness.myapp.MainActivity;
import ebusiness.myapp.PlacesPackage.AddPlaceActivity;
import ebusiness.myapp.PlacesPackage.PlaceAdapter;
import ebusiness.myapp.ProfilActivity;
import ebusiness.myapp.ProfilDatenActivity;
import ebusiness.myapp.R;
import ebusiness.myapp.UpdateStatusActivity;
import ebusiness.myapp.Util.StaticKlasse;


/**
 * Created by User on 15.11.2014.
 */
public class MapActivity extends FragmentActivity
        implements GooglePlayServicesClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private GoogleMap myMap;            // map reference
    private LocationClient myLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    /**
     * Activity's lifecycle event.
     * onResume will be Called when the activity is starting.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getMapReference();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Place");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> place, ParseException e) {
                if(e==null){
                    //success
                    for(ParseObject pars : place)
                    {

                        if(pars.get("Latitude") != null && pars.get("Longitude") != null && pars.get("Latitude") != "" && pars.get("Longitude")  != "")
                        {
                            myMap.addMarker(new MarkerOptions()

                                            .position(new LatLng(Double.valueOf(pars.get("Latitude").toString()), Double.valueOf(pars.get("Longitude").toString())))
                                            .title(pars.get("Title").toString())
                                            .draggable(true)
                            );
                        }
                    }

                }
                else{
                    //there was a problem. Alert user
                }
            }
        });
    }

    /**
     * Activity's lifecycle event.
     * onResume will be called when the Activity receives focus
     * and is visible
     */
    @Override
    protected void onResume() {
        super.onResume();
        getMapReference();
        wakeUpLocationClient();
        myLocationClient.connect();
    }

    /**
     * Activity's lifecycle event.
     * onPause will be called when activity is going into the background,
     */
    @Override
    public void onPause() {
        super.onPause();
        if (myLocationClient != null) {
            myLocationClient.disconnect();
        }
    }

    /**
     * @param lat - latitude of the location to move the camera to
     * @param lng - longitude of the location to move the camera to
     *            Prepares a CameraUpdate object to be used with  callbacks
     */
    private void gotoMyLocation(double lat, double lng) {
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(lat, lng))
                        .zoom(16.0f)
                        .bearing(0)
                        .tilt(25)
                        .build()

        ), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                // Your code here to do something after the Map is rendered
            }

            @Override
            public void onCancel() {
                // Your code here to do something after the Map rendering is cancelled
            }
        });
    }


    private void gotoMyLocation1(Location loc) {
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(loc.getLatitude(), loc.getLongitude()), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(loc.getLatitude(), loc.getLongitude()))      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)

        , new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                // Your code here to do something after the Map is rendered
            }

            @Override
            public void onCancel() {
                // Your code here to do something after the Map rendering is cancelled
            }
        });
    }
    /**
     * When we receive focus, we need to get back our LocationClient
     * Creates a new LocationClient object if there is none
     */
    private void wakeUpLocationClient() {
        if (myLocationClient == null) {
            myLocationClient = new LocationClient(getApplicationContext(),
                    this,       // Connection Callbacks
                    this);      // OnConnectionFailedListener
        }
    }

    /**
     * Get a map object reference if none exits and enable blue arrow icon on map
     */
    private void getMapReference() {
        if (myMap == null) {
            myMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

        }
        if (myMap != null) {
            myMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.updateStatus:
                Intent upStatus = new Intent(MapActivity.this, UpdateStatusActivity.class);
                startActivity(upStatus);
                break;
            case R.id.AddPlace:
                Intent place = new Intent(MapActivity.this, AddPlaceActivity.class);
                startActivity(place);
                break;
            case R.id.Home:
                Intent home = new Intent(MapActivity.this, MainActivity.class);
                startActivity(home);
                break;
            case R.id.action_map:
                break;
            case R.id.action_settings:
                ;
                break;
            case R.id.action_fb_profil:
                Intent fb = new Intent(MapActivity.this, UserDetailsActivity.class);
                startActivity(fb);
                break;
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(MapActivity.this, ProfilDatenActivity.class);
                startActivity(takeUsertoProfil);
                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(MapActivity.this, LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * @param bundle LocationClient is connected
     */
    @Override
    public void onConnected(Bundle bundle) {
        myLocationClient.requestLocationUpdates(
                REQUEST,
                this); // LocationListener
    }

    /**
     * LocationClient is disconnected
     */
    @Override
    public void onDisconnected() {

    }

    /**
     * @param location - Location object with all the information about location
     *                 Callback from LocationClient every time our location is changed
     */
    @Override
    public void onLocationChanged(Location location) {
       // gotoMyLocation(location.getLatitude(), location.getLongitude());
       // gotoMyLocation1(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    private void changeCamera(CameraUpdate update, GoogleMap.CancelableCallback callback) {
        myMap.moveCamera(update);
    }


    private void addMarker() {

        /** Make sure that the map has been initialised **/
        if (null != myMap) {
/*            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Place");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> place, ParseException e) {
                    if(e==null){
                        //success
                        for(ParseObject pars : place)
                        {
                            pars.get("Longitude");
                            pars.get("Latitude");
                            pars.get("Title");

                            if(pars.get("Latitude").toString() != null && pars.get("Longitude").toString() != null) {
                                myMap.addMarker(new MarkerOptions()

                                                .position(new LatLng(Double.valueOf(pars.get("Latitude").toString()), Double.valueOf(pars.get("Longitude").toString())))
                                                .title(pars.get("Title").toString())
                                                .draggable(true)
                                );
                            }
                        }

                    }
                    else{
                        //there was a problem. Alert user
                    }
                }
            });*/
            myMap.addMarker(new MarkerOptions()

                            .position(new LatLng(49.0068901,8.4036527))
                            .title("Karlsruhe Zentrum")
                            .draggable(true)
            );
        }
    }

    public void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationLI locationListener = new LocationLI();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


    }


}