package xb3group.com.nutritionfreak;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView calendarImageView;
    private ImageView settingImageView;
    private ImageView aboutImageView;
    private Account account;
    private String name;
    private String email;
    private int age;
    private double weight;
    private double bmi;
    private double inches;
    private double feet;
    private String province;
    private ImageView searchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // /connect the xml and java
        setUp();

        //get the info from login page or profile set up page
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("name");
            email = extras.getString("myEmail");
            age = extras.getInt("age");
            weight = extras.getDouble("weight");
            bmi = extras.getDouble("bmi");
            inches = extras.getDouble("inches");
            feet = extras.getDouble("feet");
            province = extras.getString("province");
        }

        //event handler for when the calendar image is clicked
        calendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, calendarActivity.class);
                startActivity(i);
            }
        });

        //event handler for when the settings image is clicked
        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SettingsActivity.class);
                i.putExtra("name",name);
                i.putExtra("age",age);
                i.putExtra("weight",weight);
                i.putExtra("bmi",bmi);
                i.putExtra("feet",feet);
                i.putExtra("inches",inches);
                i.putExtra("province",province);
                startActivity(i);
            }
        });

        //event handler for when the about image is clicked
        aboutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(i);
            }
        });

        //event handler for when the searching foods image is clicked
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, searchingActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Connect the xml and java code
     */
    private void setUp(){
        calendarImageView = (ImageView)findViewById(R.id.calendarImageViewID);
        settingImageView = (ImageView)findViewById(R.id.settingsImageViewID);
        aboutImageView = (ImageView)findViewById(R.id.aboutImageViewID);
        searchImageView = (ImageView) findViewById(R.id.searchImageViewID);
    }

    //disable the back button
    @Override
    public void onBackPressed(){
    }

}
