package xb3group.com.nutritionfreak;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.text.Normalizer;

public class mealPlanOfTheDayActivity extends AppCompatActivity {
    private ListView mealPlan;
    private String[] menuOfTheDay;
    private String meal1;
    private String meal2 ;
    private String meal3;
    private String meal4;
    private String meal5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_of_the_day);



        //get info passed from create account activity
        Bundle extras = getIntent().getExtras();
        //check if the bundle is not null
        if (extras != null){
            menuOfTheDay = extras.getStringArray("menuOfTheDay");
            menuOfTheDay = replaceNulls(menuOfTheDay);
            meal1 = "9:30am    "+ menuOfTheDay[0] +" , "+menuOfTheDay[1] +" , "+menuOfTheDay[2] +" , "+menuOfTheDay[3];
            meal2 = "12:00pm   "+ menuOfTheDay[4] +" , "+menuOfTheDay[5] +" , "+menuOfTheDay[6] +" , "+menuOfTheDay[7];
            meal3 = "3:00pm    "+ menuOfTheDay[8] +" , "+menuOfTheDay[9] +" , "+menuOfTheDay[10] +" , "+menuOfTheDay[11];
            meal4 = "6:00pm    "+ menuOfTheDay[12] +" , "+menuOfTheDay[13] +" , "+menuOfTheDay[14] +" , "+menuOfTheDay[15];
            meal5 = "9:00pm    "+ menuOfTheDay[16] +" , "+menuOfTheDay[17] +" , "+menuOfTheDay[18] +" , "+menuOfTheDay[19];
        }

        meal1 = meal1.replace("\uFFFD","1/2");
        meal2 = meal2.replace("\uFFFD","1/2");
        meal3 = meal3.replace("\uFFFD","1/2");
        meal4 = meal4.replace("\uFFFD","1/2");
        meal5 = meal5.replace("\uFFFD","1/2");

        setUp();
        String[] mealTimes = new String[]{
                meal1,
                meal2,
                meal3,
                meal4,
                meal5
        };

        //print the generated meal plan of the day into a list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1,mealTimes);

        mealPlan.setAdapter(adapter);
    }


    /**
     * Connect the xml and java code
     */
    private void setUp(){
        mealPlan = (ListView)findViewById(R.id.mealPlanOfTheDayListViewID);
    }

    private String[] replaceNulls(String[] array){
        for(int i = 0; i < array.length ; i++){
            if (array[i] == null){
                array[i] = "";
            }
        }
        return array;
    }
}
