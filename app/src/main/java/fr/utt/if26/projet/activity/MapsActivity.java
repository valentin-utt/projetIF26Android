package fr.utt.if26.projet.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

import fr.utt.if26.projet.R;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;
import fr.utt.if26.projet.view.CacheViewModel;
import fr.utt.if26.projet.view.UserViewModel;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private android.support.v4.app.Fragment mapFragement;



    public static final String TAG = "MAIN_ACTIVITY";
    public static final int LOCATION_REQUEST_CODE = 42;
    public static final int ADD_CACHE_ACTIVITY_REQUEST_CODE = 43;

    private CacheViewModel mCacheViewModel;
    private UserViewModel mUserViewModel;

    private Boolean isConnnected;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        isConnnected = sharedPref.getBoolean(getString(R.string.login_status), false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filters :
                Intent i = new Intent(this, FilterActivity.class);
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }







    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "vous ne pouvez pas ajouter de caches sans avoir accès à votre localisation.", Toast.LENGTH_LONG).show();


            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "vous ne pouvez pas ajouter de caches sans avoir accès à votre localisation.", Toast.LENGTH_LONG).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

            }
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationClickListener(this);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(11)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(uttPosition));
        }

        RefreshCaches();



        // Add a marker to utt and move the camera
        //LatLng uttPosition = new LatLng(48.269126, 4.066899);
        //MarkerOptions uttMarker = new MarkerOptions().position(uttPosition).title("Cachette de l'UTT");
        //mMap.addMarker(uttMarker);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(uttPosition));
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        if(isConnnected) {
            //Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AddCacheActivity.class);
            intent.putExtra("lon", location.getLongitude());
            intent.putExtra("lat", location.getLatitude());
            startActivity(intent);
        }
        else{
            Toast.makeText(this, R.string.connect_to_add_cache, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {

            Log.d(TAG, "onRequestPermissionsResult: permission :" + Arrays.toString(permissions)  );
            Log.d(TAG, "onRequestPermissionsResult: grant result " + Arrays.toString(grantResults) );

            if (permissions.length == 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(MapsActivity.this, "TRIGED", Toast.LENGTH_LONG).show();
                mLocationPermissionGranted = true;
                mMap.setMyLocationEnabled(true);
                finish();
                startActivity(getIntent());
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CACHE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Cache cache = (Cache) data.getSerializableExtra(AddCacheActivity.EXTRA_ADD_CACHE);
            mCacheViewModel.insert(cache);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "error",
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        //Toast.makeText(MapsActivity.this, "oui4", Toast.LENGTH_LONG).show();
        int cacheId = (int) marker.getTag();
        Intent i = new Intent(getBaseContext(), ViewCacheActivity.class);
        i.putExtra("CacheId", cacheId);
        startActivity(i);
        return false;
    }

    public void RefreshCaches(){

        // Obtain the cache List
        mCacheViewModel = ViewModelProviders.of(this).get(CacheViewModel.class);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int filterKeyBox = -1;
        int filterKeyCase = -1;
        int filterKeyTreasure = -1;

        if (sharedPref.getBoolean(getString(R.string.box_filter_key), true)) {
            filterKeyBox = 1;
        }
        if (sharedPref.getBoolean(getString(R.string.case_filter_key), true)) {
            filterKeyCase = 2;
        }
        if (sharedPref.getBoolean(getString(R.string.treasure_filter_key), true)) {
            filterKeyTreasure = 3;
        }

        mCacheViewModel.findCacheByType(filterKeyBox,filterKeyCase,filterKeyTreasure).observe(this, new Observer<List<Cache>>() {
            @Override
            public void onChanged(@Nullable final List<Cache> caches) {
                //Toast.makeText(MapsActivity.this, "oui1", Toast.LENGTH_LONG).show();
                for (Iterator<Cache> iter = caches.iterator(); iter.hasNext(); ) {
                    Cache parsedCache = iter.next();
                    //Toast.makeText(MapsActivity.this, "oui2", Toast.LENGTH_LONG).show();
                    LatLng cachePosition = new LatLng(parsedCache.getLat(), parsedCache.getLon());
                    MarkerOptions cacheMarkerOption = new MarkerOptions().position(cachePosition);
                    switch (parsedCache.getType()) {
                        case 1:
                            cacheMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            break;
                        case 2:
                            cacheMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            break;
                        case 3:
                            cacheMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                            break;
                    }
                    Marker mCacheMarker = mMap.addMarker(cacheMarkerOption);
                    mCacheMarker.setTag(parsedCache.getId());
                }
            }
        });
    }
}
