package com.example.myfavouriteplaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewMarkerActivity extends AppCompatActivity {
  private static final String LOG_TAG = FavouritesActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_marker);

    Intent intent = getIntent();
    final MarkerOptions replyMarker = intent.getParcelableExtra("MARKER");
    Log.d(LOG_TAG, "Intent loaded");

    final EditText input_title = findViewById(R.id.input_title);
    final EditText input_description = findViewById(R.id.input_description);

    Button button_save = findViewById(R.id.button_save);
    button_save.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String title = input_title.getText().toString();
        String description = input_description.getText().toString();

        replyMarker.title(title);
        replyMarker.snippet(description);

//        Return a new intent with a market that has updated title and snippet
        Intent replyIntent = new Intent();
        replyIntent.putExtra("MARKER", replyMarker);
        setResult(RESULT_OK,replyIntent);
        Log.d(LOG_TAG, "End activity");
        finish();
      }
    });
  }

}
