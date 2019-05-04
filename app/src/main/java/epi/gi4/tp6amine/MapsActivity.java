package epi.gi4.tp6amine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lManager.getAllProviders();

        Criteria criteres = new Criteria();
        // Localisation la plus présice possible
        criteres.setAccuracy(Criteria.ACCURACY_FINE);
        criteres.setCostAllowed(false);
        String bestProvider = lManager.getBestProvider(criteres, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location local = lManager.getLastKnownLocation(bestProvider);

        double lat = local.getLatitude();
        double lon = local.getLongitude();
        Toast.makeText(getApplicationContext(), "Latitude =" + lat + "   Longitude" + lon, Toast.LENGTH_LONG).show();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lManager.requestLocationUpdates(bestProvider, 1000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location.getLatitude();
                location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        Geocoder geacoder = new Geocoder(getApplicationContext(), Location.ge);



            LatLng MaPosition = new LatLng(lat, lon);
        LatLng monastir = new LatLng(35.76, 10.81);
        LatLng sousse = new LatLng(35.76, 10.81);
        mMap.addMarker(new MarkerOptions().position(monastir).title("Marker in monastir"));
        mMap.addMarker(new MarkerOptions().position(MaPosition).title("Marker in MA position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MaPosition,20));
        PolylineOptions soussToMonastir  = new PolylineOptions().add(
                monastir,sousse
        );
        Polyline polyline = mMap.addPolyline(soussToMonastir);

        mMap.setMapType(4);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                double lat = point.latitude;
                double lon= point.longitude;
                Toast.makeText(getApplicationContext(),"Latitude ="+lat+"   Longitude" +lon,Toast.LENGTH_LONG).show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                   String adresse="http://fr.wikipedia.org/wiki/"+marker.getTitle();
                final Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse(adresse));
                startActivity(link);


                return false;
            }

        });





        //  String getBestProvider(Criteria criteria,boolean enabledOnly);
    }
}
