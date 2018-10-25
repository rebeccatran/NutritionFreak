package xb3group.com.nutritionfreak;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class searchingActivity extends AppCompatActivity {
    private Button searchButton;
    private TextView showInfo;
    private EditText searchEditText;
    //private ArrayList<String> lines = new ArrayList<String>();
    //private ArrayList<String[]> foods = new ArrayList<String[]>();
    private static String[][] veggiefoods;
    private static String[] veggiefoodNames;
    private static  String[][] meatfoods;
    private static String[] meatFoodNames;
    private static String[][] dairyfoods;
    private static String[] dairyFoodsnames;
    private static String[][] grainfoods;
    private static String[] grainFoodsNames;


    private static RadioGroup filterOptions;
    private static RadioButton veggiesRadioButton;
    private static RadioButton meatsRadioButton;
    private static RadioButton dairyRadioButton;
    private static RadioButton grainsRadioButton;

    private static final String[] nutritionInfo = {
            "Measure: \t",
            "Weight: \t",
            "Energy: \t",
            "Protein: \t",
            "Carbohydate: \t",
            "Sugar: \t",
            "Fat: \t",
            "Sodium: \t",
            "Vitamin B12: \t"
    };

    private static String[] meatsNutritionInfo = {
            "Measure: \t",
            "Weight: \t",
            "Energy: \t",
            "Protein: \t",
            "Carbohydate: \t",
            "Sugar: \t",
            "Total Fat: \t",
            "Unsaturated Fat: \t",
            "Cholesterol: \t",
            "Sodium: \t"
    };

    private static final String[] nutritionMeasurements = {
            "",
            "g",
            "kcal",
            "g",
            "g",
            "g",
            "g",
            "mg",
            "ug"
    };

    private static final String[] meatsNutritionMeasurements = {
            "",
            "g",
            "kcal",
            "g",
            "g",
            "g",
            "g",
            "g",
            "mg",
            "mg"
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


        ArrayList<String> VPfoods = new ArrayList<String>();
        ArrayList<String> DPfoods = new ArrayList<String>();
        ArrayList<String> GPfoods = new ArrayList<String>();
        ArrayList<String> MPfoods = new ArrayList<String>();

        setUp();
        //readFile();
        VPfoods = readFile("VP.txt");
        DPfoods = readFile("DP.txt");
        GPfoods = readFile("GP.txt");
        MPfoods = readFile("MP.txt");

        veggiefoods = parseInfo2(VPfoods);
        veggiefoodNames = copyFoodNames(veggiefoods);
        sortBinary(veggiefoodNames, veggiefoods, veggiefoods.length);

        dairyfoods = parseInfo2(DPfoods);
        dairyFoodsnames = copyDairyFoodNames(dairyfoods);
        sortBinary(dairyFoodsnames,dairyfoods,dairyfoods.length-5);
        //int i = dairyfoods.length-1;
        //Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),dairyfoods[89][dairyfoods[80].length-1],Toast.LENGTH_LONG).show();


        grainfoods = parseInfo2(GPfoods);
        grainFoodsNames = copyGrainFoodNames(grainfoods);
        sortBinary(grainFoodsNames,grainfoods,dairyfoods.length-5);

        meatfoods = parseInfo2(MPfoods);
        int i = meatfoods.length-1;
        //Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_LONG).show();
        meatFoodNames = copyMeatFoodNames(meatfoods);
        sortBinary(meatFoodNames,meatfoods,meatfoods.length-7);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (veggiesRadioButton.isChecked()) {
                    String searchString = searchEditText.getText().toString().trim();
                    int location = binarySearch(veggiefoodNames, searchString, 0, veggiefoodNames.length) - 1;
                    String result = printFood(veggiefoods[location]);
                    if (result.equals(searchString) || result.toLowerCase().contains(searchString.toLowerCase())) {
                        showInfo.setText(result);
                        //Toast.makeText(getApplicationContext(),Arrays.toString(veggiefoods[location]), Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getApplicationContext(),result + ", " + searchString, Toast.LENGTH_LONG).show();
                        showInfo.setText("Sorry! Food was not found");
                    }

                }else if(meatsRadioButton.isChecked()) {
                    String searchString = searchEditText.getText().toString().trim();
                    int location = binarySearch(meatFoodNames, searchString, 0, meatFoodNames.length) - 1;
                    String result = printMeats(meatfoods[location]);
                    if (result.equals(searchString) || result.toLowerCase().contains(searchString.toLowerCase())) {
                        showInfo.setText(result);
                        //Toast.makeText(getApplicationContext(),Arrays.toString(veggiefoods[location]), Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getApplicationContext(),result + ", " + searchString, Toast.LENGTH_LONG).show();
                        showInfo.setText("Sorry! Food was not found");
                    }
                }else if (grainsRadioButton.isChecked()) {
                    String searchString = searchEditText.getText().toString().trim();
                    int location = binarySearch(grainFoodsNames, searchString, 0, grainFoodsNames.length) - 1;
                    String result = printFood(grainfoods[location]);
                    if (result.equals(searchString) || result.toLowerCase().contains(searchString.toLowerCase())) {
                        showInfo.setText(result);
                        //Toast.makeText(getApplicationContext(),Arrays.toString(veggiefoods[location]), Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getApplicationContext(),result + ", " + searchString, Toast.LENGTH_LONG).show();
                        showInfo.setText("Sorry! This food product was not found");
                    }
                }else if (dairyRadioButton.isChecked()){
                    String searchString = searchEditText.getText().toString().trim();
                    int location = binarySearch(dairyFoodsnames, searchString, 0, dairyFoodsnames.length) - 1;
                    String result = printFood(dairyfoods[location]);
                    if (result.equals(searchString) || result.toLowerCase().contains(searchString.toLowerCase())) {
                        showInfo.setText(result);
                        //Toast.makeText(getApplicationContext(),Arrays.toString(veggiefoods[location]), Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getApplicationContext(),result + ", " + searchString, Toast.LENGTH_LONG).show();
                        showInfo.setText("Sorry! This food product was not found");
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please choose a filter", Toast.LENGTH_LONG).show();
                }


            }
        });
    }



        //foods = parseInfo(lines);
        //Toast.makeText(getApplicationContext(),foods.get(0)[foods.get(0).length-1],Toast.LENGTH_LONG).show();

        //int location = binarySearch(veggiefoodNames,"Artichoke hearts, canned in water",0,veggiefoodNames.length) -1;
        //Toast.makeText(getApplicationContext(), veggiefoodNames[location],Toast.LENGTH_LONG).show();


        //Toast.makeText(getApplicationContext(), Arrays.toString(veggiefoods[location]),Toast.LENGTH_LONG).show();



    private void setUp(){
        searchButton = (Button)findViewById(R.id.searchButtonID);
        showInfo = (TextView)findViewById(R.id.textView);
        searchEditText = (EditText)findViewById(R.id.searchingEditTextID);
        filterOptions = (RadioGroup)findViewById(R.id.searchFilterRadioGroupID);
        dairyRadioButton = (RadioButton)findViewById(R.id.dairyRadioButtonID);
        meatsRadioButton = (RadioButton)findViewById(R.id.meatRadioButtonID);
        grainsRadioButton = (RadioButton)findViewById(R.id.grainRadioButtonID);
        veggiesRadioButton = (RadioButton)findViewById(R.id.veggiesRadioButtonID);
    }

    private String printFood(String[] food) {
        String result =food[food.length-1]+"\n";
        for(int i = 0; i < food.length-1;i++){
            if(food[i].trim().equals("tr")){
                result+="\n"+nutritionInfo[i] + "N/A";
            }else{
                result+="\n"+nutritionInfo[i]+ food[i] + " " +  nutritionMeasurements[i];
            }
        }
        return result;
    }

    private String printMeats(String[] food) {
        String result =food[food.length-1]+"\n";
        for(int i = 0; i < food.length-1;i++){
            if(food[i].trim().equals("tr")){
                result+="\n"+nutritionInfo[i] + "N/A";
            }else{
                result+="\n"+meatsNutritionInfo[i]+ food[i] + " " +  meatsNutritionMeasurements[i];
            }
        }
        return result;
    }




     /*   *//**
     * Read internal file
     *//*
    private void readFile(){
        //List<String> mLines = new ArrayList<>();

        AssetManager am = this.getAssets();

        try {
            InputStream is = am.open("VP.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                //Toast.makeText(getApplicationContext(), line, Toast.LENGTH_LONG).show();
                lines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /**
     * Read internal file
     */
    private ArrayList<String> readFile(String fileName){
        ArrayList<String> mLines = new ArrayList<String>();


        AssetManager am = this.getAssets();

        try {
            InputStream is = am.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                //Toast.makeText(getApplicationContext(), line, Toast.LENGTH_LONG).show();
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mLines;
    }


    private static ArrayList<String[]> parseInfo(ArrayList<String> data){
        ArrayList<String[]> myfoods = new ArrayList<String[]>();

        for(String line: data){
            String current = line;
            if (current.charAt(0) != '\"') {	//if a line does not start with ", it will do nothing

            } else {
                String[] info = current.split("\"");	//otherwise, split at the " to get the name of the food
                String[] veggieValues = info[2].split(",");	//getting the rest of the values, copying into string array
                String[] r;
                r = copyArray(veggieValues);
                r[r.length - 1] = info[1];
                myfoods.add(r);
            }
        }
        return myfoods;
    }

    private static String[][] parseInfo2(ArrayList<String> data){
        String[][] myfoods = new String[data.size()-5][];
        int counter = 0;

        for(String line: data){
            String current = line;
            if (current.charAt(0) != '\"') {	//if a line does not start with ", it will do nothing

            } else {
                String[] info = current.split("\"");	//otherwise, split at the " to get the name of the food
                String[] veggieValues = info[2].split(",");	//getting the rest of the values, copying into string array
                String[] r;
                r = copyArray(veggieValues);
                r[r.length - 1] = info[1];
                myfoods[counter] =r;
                counter++;
            }
        }
        return myfoods;
    }

    private String[] copyFoodNames(String[][] data)
    {
        String[] myFoodNames = new String[data.length];
        for(int i = 0; i< myFoodNames.length;i++){
            myFoodNames[i] = data[i][data[i].length-1];
        }

        return myFoodNames;
    }

    private String[] copyDairyFoodNames(String[][] data)
    {
        String[] myFoodNames = new String[data.length];
        for(int i = 0; i< myFoodNames.length-5;i++){
            myFoodNames[i] = data[i][data[i].length-1];
        }

        return myFoodNames;
    }

    private String[] copyGrainFoodNames(String[][] data)
    {
        String[] myFoodNames = new String[data.length-5];
        for(int i = 0; i< myFoodNames.length;i++){
            myFoodNames[i] = data[i][data[i].length-1];
        }

        return myFoodNames;
    }

    private String[] copyMeatFoodNames(String[][] data)
    {
        String[] myFoodNames = new String[data.length-7];
        for(int i = 0; i< myFoodNames.length;i++){
            myFoodNames[i] = data[i][data[i].length-1];
        }

        return myFoodNames;
    }

    private static String[] copyArray(String[] c) {	//copying data into a string array
        String[] result = new String[c.length];
        for (int i = 1; i < c.length; i++) {
            result[i - 1] = c[i];
        }
        return result;

    }

    /**
     * optimized insertion sort
     * @param x - the input array containing times of jobs that need to be sorted.
     * @param n - the size of the input array
     */
    public static void sortBinary (Comparable[] x,Comparable[][] y ,int n ) {
        //local variables
        int low;
        int High;
        Comparable temp;
        Comparable[] temp2;

        int location;

        //Sort x[] into increasing order
        for (int i = 1; i < n; i++) {
            location = binarySearch(x,x[i] ,0,i);	//find the location to place (in the already sorted array left of i)the current element
            temp = x[i];
            temp2 = y[i];
            //insert the element and shift the other elements
            for (int j = i-1; j >= location; j--) {
                x[j+1] = x[j];
                y[j+1]=y[j];
            }
            x[location] = temp;
            y[location] = temp2;
        }
    }


    //Exchanges/swaps jobs at indexes i and j
    private static void exch( String[] x, int i, int j){
        String temp = x[i];
        x[i] = x[j];
        x[j] = temp;
    }

    //Exchanges/swaps comparable elements at indexes i and j
    private static void exch( Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    //Returns true of comparable v is less than w
    private static boolean less( Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //Binary Search, returns the position at that current should be at
    private static int binarySearch( Comparable[] x, Comparable current, int low, int high){

        int middle=0;
        //while the object is not found
        while(high >= low){

            if(low==high) return low;
            middle = low + (high-low)/2;
            //if current is less than the middle element, search through the lower half of the array
            if(!less(current,x[middle]))
            {
                return binarySearch(x,current,middle+1,high);
            } //search through top half of array
            else if(less(current,x[middle])) {
                return binarySearch(x,current,low,middle);
            }

        }
        return middle;
    }

}
