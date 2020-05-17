package com.example.myfavouriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

  private static final String LOG_TAG = FavouritesActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favourites);

//    Getting titles from the intent
    Intent intent = getIntent();
    List<String> markerTitles = intent.getStringArrayListExtra(MapsActivity.EXTRA_MARKERS);
    Log.d(LOG_TAG, "Intent loaded");

//    Presenting titles in a ListView
    ArrayAdapter<String> titlesAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, markerTitles);
    ListView listView = findViewById(R.id.list_view);
    listView.setAdapter(titlesAdapter);
  }

}
