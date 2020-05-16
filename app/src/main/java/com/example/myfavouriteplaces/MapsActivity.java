package com.example.myfavouriteplaces;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
    OnInfoWindowClickListener {

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
   * Manipulates the map once available. This callback is triggered when the map is ready to be
   * used. This is where we can add markers or lines, add listeners or move the camera. If Google Play services is not installed on
   * the device, the user will be prompted to install it inside the SupportMapFragment. This method
   * will only be triggered once the user has installed Google Play services and returned to the
   * app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

//    Onclicklistener to add new favourite locations
    mMap.setOnMapClickListener(new OnMapClickListener() {
      @Override
      public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("New location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
      }
    });
    mMap.setOnInfoWindowClickListener(this);


    // Add a marker in Sydney and move the camera
    LatLng sydney = new LatLng(-34, 151);
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
  }

  @Override
  public void onInfoWindowClick(Marker marker) {
    Toast.makeText(this,"Info!",Toast.LENGTH_SHORT).show();
  }

  @Override
  // Inflate the menu; this adds items to the action bar if it is present.
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
//      case R.id.action_order:
////        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
////        intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
////        startActivity(intent);
////        return true;
      case R.id.menu_favourites:
        displayToast(getString(R.string.menu_favourites));
        return true;
      case R.id.menu_about:
        displayToast(getString(R.string.menu_about));
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
}
