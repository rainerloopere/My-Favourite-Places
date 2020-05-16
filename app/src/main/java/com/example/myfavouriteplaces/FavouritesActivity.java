package com.example.myfavouriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {
  private static final String LOG_TAG = FavouritesActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favourites);
    Intent intent = getIntent();
    List<MarkerOptions> markers = (List<MarkerOptions>) intent.getSerializableExtra(MapsActivity.EXTRA_MARKERS);
    Log.d(LOG_TAG, "Intent loaded");

    String message = "";
    for (MarkerOptions marker : markers){
      message += marker.getTitle() + ", ";
    }
    TextView textView = findViewById(R.id.textView);
    textView.setText(message);
  }

}
