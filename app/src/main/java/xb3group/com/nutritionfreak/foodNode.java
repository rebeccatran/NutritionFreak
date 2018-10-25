package xb3group.com.nutritionfreak;

import java.util.ArrayList;

/**
 * Created by Jeremy/Hahd on 4/10/2016.
 */
public class foodNode {
    String id, idcat, servSize, foodName;
    double eWeight;
    // for individual nodes
    foodNode(String[] info) {
        this.id = info[0].replace('|', ',');
        this.idcat = info[1].replace('|', ',');
        this.servSize = info[2].replace('|', ',');
        this.foodName = info[3].replace('|', ',');
        this.eWeight = Double.parseDouble(info[4]);
        //System.out.println(foodName);
    }
    // for group node
    foodNode(ArrayList<foodNode> groupNode){
        String[] def = {"G","G","G","G","100"};

        this.id = groupNode.get(0).id;
        this.foodName = this.id+" G";
        this.idcat = "G";
        this.servSize = "G";
        this.eWeight = 100;
        //System.out.println(foodGroup.foodName);
    }
    // for middle node
    foodNode(){

    }
}
