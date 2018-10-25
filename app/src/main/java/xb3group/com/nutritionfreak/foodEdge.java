package xb3group.com.nutritionfreak;

/**
 * Created by Jeremy/Hahd on 4/10/2016.
 */
public class foodEdge {
    public foodNode a;
    public foodNode b;
    public double weight;
    public String descrip;
    public int order;
    foodEdge(foodNode a, foodNode b){
        this.a=a;
        this.b=b;
        this.descrip = "" + a.foodName + " | " + b.foodName;
        this.weight = b.eWeight;
    }
}
