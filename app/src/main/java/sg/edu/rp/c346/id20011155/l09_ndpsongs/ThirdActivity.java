package sg.edu.rp.c346.id20011155.l09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Year;

public class ThirdActivity extends AppCompatActivity {

    Button btnUpd, btnDlt, btnCl;
    EditText etTitle, etSinger, etYear;
    TextView etID;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        rb5 = findViewById(R.id.radioButton5);
        btnUpd = findViewById(R.id.upd);
        btnDlt = findViewById(R.id.dlt);
        btnCl = findViewById(R.id.cl);
        etID = findViewById(R.id.songID);
        etTitle = findViewById(R.id.songTitle);
        etSinger = findViewById(R.id.singer);
        etYear = findViewById(R.id.yr);

        Intent i = getIntent();
        final Song currentSong = (Song) i.getSerializableExtra("data");

        etID.setText(currentSong.getId()+"");
        etTitle.setText(currentSong.getTitle());
        etSinger.setText(currentSong.getSingers());
        etYear.setText(currentSong.getYear()+"");

        switch (currentSong.getStars()) {
            case 1:
                rb1.setChecked(true);
                break;
            case 2:
                rb2.setChecked(true);
                break;
            case 3:
                rb3.setChecked(true);
                break;
            case 4:
                rb4.setChecked(true);
                break;
            case 5:
                rb5.setChecked(true);
                break;
        }

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);

                if(!etTitle.getText().toString().isEmpty() || !etSinger.getText().toString().isEmpty() || !etYear.getText().toString().isEmpty()) {
                    int chosenRB = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(chosenRB);
                    int stars = Integer.parseInt(rb.getText().toString());

                    data.setTitle(etTitle.getText().toString());
                    data.setSingers(etSinger.getText().toString());
                    data.setYear(Integer.parseInt(etYear.getText().toString()));
                    data.setStars(stars);
                    data.setId(data.getId());
                    dbh.updateSong(data);
                    dbh.close();
                    Toast.makeText(ThirdActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ThirdActivity.this, "Please input something", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteSong(data.getId());
            }
        });

        btnCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}