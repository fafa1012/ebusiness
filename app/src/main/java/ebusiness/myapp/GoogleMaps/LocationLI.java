package ebusiness.myapp.GoogleMaps;

/**
 * Created by User on 24.11.2014.
 */

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by User on 24.11.2014.
 */
public class LocationLI implements LocationListener {

    Location p;

    @Override
    public void onLocationChanged(Location location) {
        p = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public double getLatidude() {

        if (p != null) {
            return p.getLatitude();
        } else {
            return 0;
        }
    }

    public double getLongitude() {
        if (p != null) {
            return p.getLongitude();
        } else {
            return 0;
        }
    }
}
