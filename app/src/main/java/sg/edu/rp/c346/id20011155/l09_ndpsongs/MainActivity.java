package sg.edu.rp.c346.id20011155.l09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.Year;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnShowList;
    EditText songTitle, singer, etYear;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        rb5 = findViewById(R.id.radioButton5);
        btnAdd = findViewById(R.id.insert);
        btnShowList = findViewById(R.id.showList);
        songTitle = findViewById(R.id.etTitle);
        singer = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);

                if(!songTitle.getText().toString().isEmpty() || !singer.getText().toString().isEmpty() || !etYear.getText().toString().isEmpty()) {
                    String title = songTitle.getText().toString();
                    String singers = singer.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    int chosenRb = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(chosenRb);
                    int stars = Integer.parseInt(rb.getText().toString());
                    dbh.insertSong(title, singers, year, stars);
                    Toast.makeText(MainActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please input something", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}