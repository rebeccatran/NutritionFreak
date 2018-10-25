package xb3group.com.nutritionfreak;

import android.content.Intent;
import android.content.res.AssetManager;
import android.location.GpsSatellite;
import android.net.VpnService;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;

public class calendarActivity extends AppCompatActivity {
    private CalendarView calendar;
    private static String[][][] days = new String[13][32][];
    private ArrayList<String[]> VPfoods = new ArrayList<String[]>();
    private ArrayList<String[]> DPfoods = new ArrayList<String[]>();
    private ArrayList<String[]> GPfoods = new ArrayList<String[]>();
    private ArrayList<String[]> MPfoods = new ArrayList<String[]>();
    /*private static String[][] veggiefoods;
    private static String[] veggiefoodNames;
    private static  String[][] meatfoods;
    private static String[] meatFoodNames;
    private static String[][] dairyfoods;
    private static String[] dairyFoodsnames;
    private static String[][] grainfoods;
    private static String[] grainFoodsNames;*/
    private int vegCount;
    private int grainCount;
    private int dairyCount;
    private int meatCount;
    private int age =20;


    /**
     * When the calendar page is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //String[][] days = new String[12][];

        //calculatePoritonsSizes();
        //readFile("foods-en_ONPP_rev.csv");


        //calculatePoritonsSizes();
        //generateMealPlan();


        //String[] foodsOfTheDay = makeMealPlan();
        //Toast.makeText(getApplicationContext(), Arrays.toString(foodsOfTheDay),Toast.LENGTH_LONG).show();



        //String[] day1 = generateMealPlanOfTheDay();
        //Toast.makeText(getApplicationContext(), Arrays.toString(day1),Toast.LENGTH_LONG).show();

        calendar = (CalendarView)findViewById(R.id.calendarID);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //Toast.makeText(getApplicationContext(),Arrays.toString(days[0][0]),Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),dayOfMonth + "/"+ month + "/"+year, Toast.LENGTH_LONG).show();
                String[] foodsOfTheDay = makeMealPlan();
                Intent i = new Intent(calendarActivity.this,mealPlanOfTheDayActivity.class);
                //i.putExtra("menuOfTheDay",days[month-1][dayOfMonth-1]);
                i.putExtra("menuOfTheDay",foodsOfTheDay);
                startActivity(i);
            }
        });
    }

    /**
     * Read internal file
     *///ArrayList<String>
    private void readFile(String fileName){
        ArrayList<String> mLines = new ArrayList<String>();


        AssetManager am = this.getAssets();

        try {
            InputStream is = am.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                //Toast.makeText(getApplicationContext(), line, Toast.LENGTH_LONG).show();
            {
                String[] info = line.split(",");
                String type = info[0];
                if(type.equals("vf")){
                    VPfoods.add(info);
                }else if(type.equals("gr")){
                    GPfoods.add(info);
                }else if(type.equals("mi")){
                    DPfoods.add(info);
                }else if(type.equals("me")){
                    MPfoods.add(info);
                }else{

                }

                //mLines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //return mLines;
    }

    /**
     * calculate the user's protion size based on age
     */
    private void calculatePoritonsSizes(){
        vegCount = 9;
        grainCount = 6;
        dairyCount = 2;
        meatCount = 2;
    }



    /**
     * Generate meal plan for a given number of days (months/days)
     * 2D array of days represented as array[month][day][menu of day]
     */
    private void generateMealPlan(){
        for(int i =0; i < 12 ; i++){
            for(int j = 0; j < 31;j++){
                //String[] day = generateMealPlanOfTheDay();
                //String[] day = randomizeFoodsOfDay();
                String[] day = makeMealPlan();
                days[i][j] = day;
            }
        }


    }

    /**
     * Makes the meal plan and places an even about of randomly generated foods (e.g. 1-2 veggies per meal)
     * @return The meal plan in string format
     */
    private String[] makeMealPlan(){
        String[] meal = new String[20];
        //for each meal in the meal plan
        for(int i = 0; i < meal.length;i++){
            //randomly generate a food
            String[] randomMealPlan = randomizeFoodsOfDay();
            if (randomMealPlan !=null){
                if(i ==0 || i ==1 || i == 4 || i == 5 || i == 9 || i ==10 || i ==14 || i==17 || i==19){
                    meal[i] = randomMealPlan[0];
                }else if (i == 2 || i == 6 || i == 11 || i ==15 || i == 16 || i ==18){
                    meal[i] = randomMealPlan[1];
                }else if (i == 3 || i == 8){
                    meal[i] = randomMealPlan[2];
                }else{
                    meal[i] = randomMealPlan[3];
                }
            }
        }
        return meal;
    }

    /**
     * Randomizes the meal plan generated by the Graph
     * @return The meal plan of a day
     */
    private String[] randomizeFoodsOfDay(){
        try {
            ServingPerDay x = new ServingPerDay(calendarActivity.this);
            String menuOfDay= x.Recommended("male",26);
            //Toast.makeText(getApplicationContext(), menuOfDay,Toast.LENGTH_LONG).show();
            String[] foodGroupFoodsOfDay = menuOfDay.split("\n");
            String[] veggieInfo = foodGroupFoodsOfDay[0].split("\\|");
            String[] veggieFood = veggieInfo[1].trim().split(",");
            String veggie = veggieFood[0] +" : " +veggieFood[1];

            String[] grainInfo = foodGroupFoodsOfDay[1].split("\\|");
            String[] grainFood = grainInfo[1].trim().split(",");
            String grain = grainFood[0] +" : " +grainFood[1];

            String[] dairyInfo = foodGroupFoodsOfDay[2].split("\\|");
            String[] dairyFood = dairyInfo[1].trim().split(",");
            String dairy = dairyFood[0] + " : " + dairyFood[1];

            String[] meatInfo = foodGroupFoodsOfDay[3].split("\\|");
            String[] meatFood = meatInfo[1].trim().split(",");
            String meat = meatFood[0] + " : " + meatFood[1];

            String[] foodsOfTheDay = {
                        veggie,
                        grain,
                        dairy,
                        meat,
            };
            return foodsOfTheDay;
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
        return  null;
    }

    private String[] generateMealPlanOfTheDay(){
        Stack<String> veggies = new Stack<String>();
        Stack<String> grains =  new Stack<String>();
        Stack<String> milks =  new Stack<String>();
        Stack<String> meats = new Stack<String>();
        ArrayList<String> meal = new ArrayList<>();
        //Random random = new Random();

        vegCount = 9;
        grainCount = 6;
        dairyCount = 2;
        meatCount = 2;

        for(int i =0; i < vegCount; i++){
            String[] foodsOfDay = randomizeFoodsOfDay();
            veggies.push(foodsOfDay[0]);
            //int randomInt = 0 + (int)(Math.random()*((VPfoods.size()-1)-0)+1);
            //String[] veggieInfo = VPfoods.get(randomInt);

        }

        for(int i=0; i < grainCount;i++){
            String[] foodsOfDay = randomizeFoodsOfDay();
            veggies.push(foodsOfDay[1]);
            /*int randomInt = 0 + (int)(Math.random()*((GPfoods.size()-1)-0)+1);
            String[] grainInfo = GPfoods.get(randomInt);
            grains.push(grainInfo[3]);*/
        }

        for(int i=0; i < dairyCount;i++){
            String[] foodsOfDay = randomizeFoodsOfDay();
            veggies.push(foodsOfDay[2]);
            /*int randomInt = 0 + (int)(Math.random()*((DPfoods.size()-1)-0)+1);
            String[] dairyInfo = DPfoods.get(randomInt);
            milks.push(dairyInfo[3]);*/
        }

        for(int i=0; i < meatCount;i++){
            String[] foodsOfDay = randomizeFoodsOfDay();
            veggies.push(foodsOfDay[3]);
            /*int randomInt = 0 + (int)(Math.random()*((MPfoods.size()-1)-0)+1);;
            String[] meatInfo = MPfoods.get(randomInt);
            meats.push(meatInfo[3]);*/
        }

        while(!veggies.isEmpty()|| !grains.isEmpty() || !milks.isEmpty() || !meats.isEmpty()){
            int rand = 0 + (int)(Math.random() * ((3-0) + 1 ));
            if(!veggies.isEmpty() && rand == 0){
                meal.add(veggies.pop());
            }else if (!grains.isEmpty() && rand == 1){
                meal.add(grains.pop());
            }else if (!milks.isEmpty() && rand == 2){
                meal.add(milks.pop());
            }else if(!meats.isEmpty() && rand == 3){
                meal.add(meats.pop());
            }
        }

        return copyArray(meal);
    }

    private String[] copyArray(ArrayList<String> array){
        String[] copy = new String[array.size()];
        array.toArray(copy);
        return copy;
    }


}
