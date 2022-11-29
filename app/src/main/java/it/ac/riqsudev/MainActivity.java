package it.ac.riqsudev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView cardCheckLocation, cardCheckSentence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardCheckSentence = findViewById(R.id.cvCheckSentence);
        cardCheckLocation = findViewById(R.id.cvCheckLocation);

        cardCheckLocation.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GetCurrentLocationActivity.class)));

        cardCheckSentence.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CheckSentenceActivity.class)));
    }
}