package it.ac.riqsudev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckSentenceActivity extends AppCompatActivity {

    Button btnCheck;
    EditText etSearch;
    TextView totalConsonant, totalVowels, totalNumber;

    int vowels, consonant, digit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sentence);

        btnCheck = findViewById(R.id.btnCheck);
        etSearch = findViewById(R.id.edtSearch);

        btnCheck.setOnClickListener(view -> {
            if (etSearch.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Masukan Kata Dahulu!", Toast.LENGTH_SHORT).show();
            } else {
                String sentence = etSearch.getText().toString().trim();
                calculate(sentence);

            }
        });
    }

    public void calculate(String value) {
        vowels = 0;
        consonant = 0;
        digit = 0;

        totalConsonant = findViewById(R.id.totalConsonant);
        totalVowels = findViewById(R.id.totalVowels);
        totalNumber = findViewById(R.id.totalNumber);

        String str = value.toLowerCase();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u') {
                vowels++;
            }
            else if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                consonant++;
            }
            else if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                digit++;
            }
        }
        totalConsonant.setText(String.valueOf(consonant));
        totalVowels.setText(String.valueOf(vowels));
        totalNumber.setText(String.valueOf(digit));
    }
}