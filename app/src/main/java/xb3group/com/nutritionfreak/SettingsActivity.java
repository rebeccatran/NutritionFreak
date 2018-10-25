package xb3group.com.nutritionfreak;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private TextView ageTextView;
    private TextView weightTextView;
    private TextView heightTextView;
    private TextView bmiTextView;
    private String name;
    private String email;
    private int age;
    private double weight;
    private double feet;
    private double inches;
    private double bmi;
    private Button updateStats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUp();
        updateStats.setVisibility(View.GONE);

        //get info from main activity
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("name");
            age = extras.getInt("age");
            weight = extras.getDouble("weight");
            feet = extras.getDouble("feet");
            inches = extras.getDouble("inches");
            bmi = extras.getDouble("bmi");
        }

        double roundedBMI  = Math.round(bmi*100.0) / 100.0;

        ageTextView.setText("Age: "+ age);
        weightTextView.setText("Weight: " + weight + " lbs");
        heightTextView.setText("Height:" + feet +" feet, " + inches + " inches");
        bmiTextView.setText("Current BMI: "+ roundedBMI);

    }

    /**
     * Connect the xml and java code
     */
    private void setUp(){
        ageTextView = (TextView)findViewById(R.id.ageSettingsTextViewID);
        weightTextView =(TextView)findViewById(R.id.weightSettingsTextViewID);
        heightTextView = (TextView)findViewById(R.id.heightSettingsTextViewID);
        bmiTextView = (TextView)findViewById(R.id.BMITextViewID);
        updateStats = (Button)findViewById(R.id.updateStatsTextViewID);
    }

}
