package xb3group.com.nutritionfreak;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedReader;
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
public class idServings {
    ArrayList<String[]> idslists;				//initialization of variables required
    String[] id;								//stores string id
    String[] idcat;								//idcat
    String[] servingsize;						//serving sizes
    String[] food;								//arraylist of string
    String[] createNode;						//creates node
    foodNode[] allNodes;						//group of all food nodes
    // group nodes
    ArrayList<foodNode> allFruitNVeg;			//arraylist to hold datasets of veges
    ArrayList<foodNode> allDairy;				//arraylist to hold datasets of dairy
    ArrayList<foodNode> allMeat;				//arraylist to hold datasets of meats
    ArrayList<foodNode> allGrain;				//arraylist to hold datasets of grain


    String[] edgeWeights;						//name of edgeweights

    ArrayList<foodEdge> meatEdges;				//meatedges
    ArrayList<foodEdge> vegeEdges;				//vegeedges
    ArrayList<foodEdge> dairyEdges;				//dairyedges
    ArrayList<foodEdge> grainEdges;				//grainedges
    Context mContext;
    AppCompatActivity myActivity;

    idServings(AppCompatActivity mActivity) throws IOException {		//constructor takes in AppCompatActivity
        myActivity = mActivity;											//my activity
        this.idslists = readFile("foods-en_ONPP_rev.csv");				//get id lists from file parsed in

        id = new String[idslists.size()];								//initialization of all
        idcat = new String[idslists.size()];							//id cat lists
        servingsize = new String[idslists.size()];						//serving size lists
        food = new String[idslists.size()];								//food lists
        edgeWeights = new String[idslists.size()];						//edgeweights lists
        createNode = new String[5];										//initialization of string
        allNodes = new foodNode[idslists.size()];						//initialization of nodes
        allFruitNVeg = new ArrayList<foodNode>();						//arralist of nodes containing veges
        allDairy = new ArrayList<foodNode>();							//arraylist of nodes containing
        allMeat = new ArrayList<foodNode>();							//arraylist of nodes in meat
        allGrain = new ArrayList<foodNode>();							//arraylist of nodes in grain

        for (int i = 0; i < this.idslists.size(); i++) {				//idslists size
            id[i] = idslists.get(i)[0];									//gets first row of comma seperated file
            idcat[i] = idslists.get(i)[1];								//gets second row of comma seperated file
            servingsize[i] = idslists.get(i)[2];						//gets third row of comma seperated file
            food[i] = idslists.get(i)[3];								//gets fourth row of comma serperated file
            edgeWeights[i] = idslists.get(i)[4];						//gets fifth row of seperated file
            createNode[0] = id[i];										//adds node
            createNode[1] = idcat[i];
            createNode[2] = servingsize[i];
            createNode[3] = food[i];
            createNode[4] = edgeWeights[i];
            foodNode add = new foodNode(createNode);					//add foodnode
            allNodes[i] = add;
            if (add.id.equals("vf")) {									//checks if foodgroup is type vegeterian
                allFruitNVeg.add(add);									//adds all fruit and vegetabless
            }
            if (add.id.equals("gr")) {									//checks if foodgroup is tyoe grain
                allGrain.add(add);										//adds all grain type
            }
            if (add.id.equals("me")) {									//checks if foodgroup is tyoe grain
                allMeat.add(add);										//adds all meat type
            }
            if (add.id.equals("mi")) {									//checks if foodgroup is tyoe milk
                allDairy.add(add);										//adds all dairy type
            }

        }
        vegeEdges=createEdges(allFruitNVeg);								//creates edges
        grainEdges=createEdges(allGrain);
        meatEdges=createEdges(allMeat);
        dairyEdges=createEdges(allDairy);

    }

    idServings(ArrayList<String[]> idslists) throws IOException {			//idservings
        this.idslists = idslists;
    }


    /**
     * Read internal file
     */
    private ArrayList<String[]> readFile(String fileName){					//readFile
        ArrayList<String[]> temp = new ArrayList<String[]>();				//to hold data of file
        double[] values = new double[10000];								//maximum numbe of enteries
        for (int i=0;i<values.length;i++){									//initialization
            values[i]=i;
        }
        AssetManager am = myActivity.getAssets();

        try {																//try to see if file exists
            InputStream is = am.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String input;
            while ((input = reader.readLine()) != null){					//while there is another line
                input = input.concat(","+randomServSize(values));			//input is randomized to a graph value
                String[] splitter;											//splits bye comma and add
                splitter = input.split(",");								//to
                temp.add(splitter);											//graph
            }

        } catch (IOException e) {
            e.printStackTrace();											//catch io exception
        }
        return temp;														//return arraylist
    }

    public static double randomServSize(double[] sizes) {					//returns random position on graph
        Random servings = new Random();
        int index = servings.nextInt(sizes.length);
        return sizes[index];
    }

    public ArrayList<foodEdge> createEdges(ArrayList<foodNode> foodGroup) {//creates edges between foodnodes
        ArrayList<foodEdge> foodX =new ArrayList<foodEdge>();				//foodX arraylist of food edges
        for (int i = 0; i < foodGroup.size(); i++) {						//traverse the htaph
            for (int j = 0; j < foodGroup.size(); j++) {
                if (!foodGroup.get(i).foodName.equals(foodGroup.get(j).foodName)) {	//if an edge exists between nodes
                    foodEdge a = new foodEdge(foodGroup.get(i), foodGroup.get(j));	//add it
                    foodX.add(a);
                }
            }
        }
        return foodX;															//returns edge weighted graph

    }
    public int getIndexOfMin(ArrayList<foodEdge> data) {						//gets minimum index of arraylist
        double min = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < data.size(); i++) {
            Double f = data.get(i).weight;
            if (Double.compare(f.doubleValue(), min) < 0) {
                min = f.doubleValue();
                index = i;
            }
        }
        return index;
    }
}
