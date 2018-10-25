package xb3group.com.nutritionfreak;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Jeremy/Hahd on 4/10/2016.
 */
public class ServingPerDay {
    ArrayList <String[]> Servings;			// servings arraylist
    ArrayList <String[]> ServSizes;			//serving sizes arraylist
    String []foodgroup;						//foodgroup of string arrays
    String []gender;						//gender of string arrays
    String []agegroup;						//age group of string arrays
    ArrayList <Integer[]> AgeRange;			//age ranges
    String []numberofservings;				//total number of servings per person type
    AppCompatActivity myActivity;

    public ServingPerDay(AppCompatActivity mActivity)throws IOException{	//constructor
        myActivity = mActivity;
        this.Servings=readFile("servings_per_day-en_ONPP.csv");				//reads csv file
        foodgroup=new String[Servings.size()];								//initializes
        gender=new String[Servings.size()];
        agegroup=new String[Servings.size()];
        numberofservings=new String[Servings.size()];
        AgeRange=new ArrayList<Integer[]>();
        for (int i=0;i<this.Servings.size();i++){							//for all servings size
            Integer[] x=new Integer[2];										//age min, age max
            String temp;													//gets temp
            String[] split;													//gets split
            foodgroup[i]=Servings.get(i)[0];								//gets first column of comma seperated file
            gender[i]=Servings.get(i)[1];									//gets second column of comma seperated file
            agegroup[i]=Servings.get(i)[2];									//gets thirs column of comma seperated file
            if(agegroup[i].contains("to")){
                split=agegroup[i].split("to");								//splits age range by "to"
                x[0]=Integer.parseInt(split[0].trim());						//trim and stores lower range
                x[1]=Integer.parseInt(split[1].trim());						//trims and stores higher range
                AgeRange.add(x);											//adds to arraylist AgeRange
            }
            else if (agegroup[i].contains("+")){							//splits by +
                temp=agegroup[i].replace("+", "");
                temp=temp.trim();
                x[0]=Integer.parseInt(temp);								//takes in number as lower range
                x[1]=200;													// what ever is bigger gets stored as max
            }
            numberofservings[i]=Servings.get(i)[3];							//number of servings gets stored from third column
        }

    }
    ServingPerDay(ArrayList<String[]> Servings)throws IOException{
        this.Servings=Servings;											//this servings
    }

    /**
     * Read internal file
     */
    private ArrayList<String[]> readFile(String fileName){				//reads file in
        ArrayList<String[]> temp = new ArrayList<String[]>();
        double[] values = new double[10000];
        for (int i=0;i<values.length;i++){
            values[i]=i;
        }

        AssetManager am =myActivity.getAssets();

        try {
            InputStream is = am.open(fileName);							//to hold data of file
            BufferedReader reader = new BufferedReader(new InputStreamReader(is)); //reads it in
            String input;
            while ((input = reader.readLine()) != null){				//while there is another input
                String[] splitter = input.split(",");					//split by comma
                temp.add(splitter);										//adds to arraylist
            }

        } catch (IOException e) {										//catches io exception
            e.printStackTrace();
        }
        return temp;													//returns arraylist
    }

    public String Recommended(String sex, int age) throws IOException{
        idServings newserving=new idServings(myActivity);					//gets idServings
        Recorded r=new Recorded();											//gets recorded data
        r.add(newserving);													//adds to meals that havent been prev added

        String vegeservingSizea=newserving.vegeEdges.get(newserving.getIndexOfMin(newserving.vegeEdges)).a.servSize;	//gets node of first vegefood
        String vegeservingSizeb=newserving.vegeEdges.get(newserving.getIndexOfMin(newserving.vegeEdges)).b.servSize;	//gets node of second food

        String meatservingSizea=newserving.meatEdges.get(newserving.getIndexOfMin(newserving.meatEdges)).a.servSize;	//gets node first meatfood
        String meatservingSizeb=newserving.meatEdges.get(newserving.getIndexOfMin(newserving.meatEdges)).b.servSize;	//gets node second meatfood

        String dairyservingSizea=newserving.dairyEdges.get(newserving.getIndexOfMin(newserving.dairyEdges)).a.servSize;	//gets node of first dairy Edges
        String dairyservingSizeb=newserving.dairyEdges.get(newserving.getIndexOfMin(newserving.dairyEdges)).b.servSize;	//gets node of second dairy edge

        String grainservingSizea=newserving.grainEdges.get(newserving.getIndexOfMin(newserving.grainEdges)).b.servSize;	//gets node of first grain Edges
        String grainservingSizeb=newserving.grainEdges.get(newserving.getIndexOfMin(newserving.grainEdges)).b.servSize;	//gets node of second grain Edges
        Random k = new Random();					//checks minimum if it has been randomized
        int v=(k.nextInt(r.existing.size()));		// shuffles through existing meal plans
        String []rsp=r.existing.get(v).split("-");	//splits by v and sotrs in rsp
        String[] order=new String[4];
        Integer []index=new Integer[4];

        for (int i=0;i<AgeRange.size();i++){		//checks what group age and gender user falls and breaks that index
            if (age>AgeRange.get(i)[0]&&age<AgeRange.get(i)[1]&&sex.equalsIgnoreCase(gender[i])){
                index[0]=i;							//gets i
                break;
            }
        }
        for (int i=(index[0]+1);i<AgeRange.size();i++){//checks what group age and gender user falls and breaks at the second index
            if (age>AgeRange.get(i)[0]&&age<AgeRange.get(i)[1]&&sex.equalsIgnoreCase(gender[i])){
                index[1]=i;
                break;
            }
        }
        for (int i=(index[1]+1);i<AgeRange.size();i++){//checks what group age and gender user falls and breaks at the third index
            if (age>AgeRange.get(i)[0]&&age<AgeRange.get(i)[1]&&sex.equalsIgnoreCase(gender[i])){
                index[2]=i;
                break;
            }
        }
        for (int i=(index[2]+1);i<AgeRange.size();i++){//checks what group age and gender user falls and breaks at the final index
            if (age>AgeRange.get(i)[0]&&age<AgeRange.get(i)[1]&&sex.equalsIgnoreCase(gender[i])){
                index[3]=i;
                break;
            }
        }
        //prints statesments for checking
        String veg="Fruit and Vegetables"+" "+numberofservings[index[0]]+" "+rsp[0]+":"+vegeservingSizeb+" "+vegeservingSizea;
        String meat="Grains"+" "+numberofservings[index[1]]+" "+rsp[1]+meatservingSizeb+" "+meatservingSizea;
        String dairy="Dairy"+" "+numberofservings[index[2]]+" "+rsp[3]+dairyservingSizeb+" "+dairyservingSizea;
        String grain="Meat"+" "+numberofservings[index[3]]+" "+rsp[2]+grainservingSizeb+" "+grainservingSizea;

        return veg+"\n"+meat+"\n"+dairy+"\n"+grain+"\n";
    }
}
