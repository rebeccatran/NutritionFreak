package xb3group.com.nutritionfreak;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jeremy/Hahd on 4/10/2016.
 */
public class Recorded {
    static ArrayList<String> existing;					//arraylist of record
    Recorded(){											//recorded
        existing=new ArrayList<String>();				//existing arraylist
    }

    public void add(idServings x){						//idServings x
        int minv=(x.getIndexOfMin(x.vegeEdges));		//gets minimum of veges graph
        int ming=(x.getIndexOfMin(x.grainEdges));		//gets minimum of grain edges
        int minm=(x.getIndexOfMin(x.meatEdges));		//gets minimum of meat edges
        int mind=(x.getIndexOfMin(x.dairyEdges));		//gets minimum of dairy edges

        String record;									//record
        record=x.vegeEdges.get(minv).descrip+"-"+x.grainEdges.get(ming).descrip+"-"+x.meatEdges.get(minm).descrip+"-"+x.dairyEdges.get(mind).descrip;

        if(this.existing.contains(record)==false){		//checks if meal exists in record;
            this.existing.add(record);
        }

    }
}
