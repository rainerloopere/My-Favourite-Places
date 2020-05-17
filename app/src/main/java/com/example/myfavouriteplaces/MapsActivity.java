package com.example.myfavouriteplaces;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, Serializable {

  private static final String LOG_TAG = MapsActivity.class.getSimpleName();
  public static final String EXTRA_MARKERS = "com.example.android.myfavouriteplaces.extra.MARKERS";
  private static final int MARKER_REQUEST = 1;

  private GoogleMap mMap;
  private List<MarkerOptions> markers = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

//    Restoring markers if activty was destroyed previously
    if (savedInstanceState != null) {
      markers = (List<MarkerOptions>) savedInstanceState.getSerializable(EXTRA_MARKERS);
    }
  }

  /**
   * Manipulates the map once available. This callback is triggered when the map is ready to be
   * used. This is where we can add markers or lines, add listeners or move the camera. If Google
   * Play services is not installed on the device, the user will be prompted to install it inside
   * the SupportMapFragment. This method will only be triggered once the user has installed Google
   * Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

//    OnClickListener to add new favourite locations
    mMap.setOnMapClickListener(new OnMapClickListener() {
      @Override
      public void onMapClick(LatLng latLng) {
        Intent intent = new Intent(MapsActivity.this, NewMarkerActivity.class);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        intent.putExtra("MARKER", marker);
        startActivityForResult(intent, MARKER_REQUEST);

      }
    });

//    Markers array is not empty if it is restored from the state after destroying previous activity
    if (markers.isEmpty()) {
      // Add a marker in Tallinn, Estonia and move the camera
      LatLng tallinn = new LatLng(59.436962, 24.753574);
      MarkerOptions marker = new MarkerOptions().position(tallinn).title("Tallinn").snippet("One of top digital and best preserved medieval city in the world");
      mMap.addMarker(marker);
      // Tallinn is intentionally one of the favourite places by default
      markers.add(marker);
      mMap.moveCamera(CameraUpdateFactory.newLatLng(tallinn));
    } else {
      for (MarkerOptions marker : markers) {
        mMap.addMarker(marker);
      }
    }
  }

  @Override
  // Inflate the menu; this adds items to the action bar if it is present.
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_options, menu);
    return true;
  }

  //  Control what happens when menu items are clicked
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_favourites:
        Log.d(LOG_TAG, "Clicked on favourites");
        Intent intent = new Intent(MapsActivity.this, FavouritesActivity.class);
        intent.putStringArrayListExtra(EXTRA_MARKERS, getMarkerTitles(markers));
        startActivity(intent);
        return true;
      case R.id.menu_about:
        Log.d(LOG_TAG, "Clicked on about");
        displayToast("This app was created by Rainer Loopere");
        return true;
      default:
        // Do nothing
    }
    return super.onOptionsItemSelected(item);
  }

  public void displayToast(String message) {
    Toast.makeText(getApplicationContext(), message,
        Toast.LENGTH_SHORT).show();
  }

  public ArrayList<String> getMarkerTitles(List<MarkerOptions> markers) {
    ArrayList<String> titles = new ArrayList<>();
    for (MarkerOptions marker : markers) {
      titles.add(marker.getTitle());
    }
    return titles;
  }

  @Override
  public void onActivityResult(int requestCode,
      int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d(LOG_TAG, "Result received");
    if (requestCode == MARKER_REQUEST) {
      if (resultCode == RESULT_OK) {
        MarkerOptions marker = data.getParcelableExtra("MARKER");
        mMap.addMarker(marker);
        markers.add(marker);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10.0f));
      }
    }
  }

  //  Saving the markers if activity is destroyed
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if (markers.size() > 1) {
      outState.putSerializable(EXTRA_MARKERS, (Serializable) markers);
    }
  }
}
