package xb3group.com.nutritionfreak;

/**
 * Created by Cesar on 3/30/2016.
 */
public class BMI {

    /**
     * Construct a BMI object
     */
    public BMI(){}

    /**
     * Calculates BMI given their height and weight
     * @param feet The given height in feet
     * @param inches The given height inches
     * @param weight The given wieght
     * @return Returns the BMI score
     */
    public double calculate(int feet, double inches, double weight) {
        inches = inches + (feet * 12); // convert feet to inches
        inches = inches / 39.37; // convert inches to metres
        weight = weight / 2.2; // convert to kilograms
        double fin = weight / (inches * inches);
        return fin;
    }

    /**
     * Returns the results given a BMI
     * @param BodMassInd The given BMI
     * @return The result
     */
    public String result(double BodMassInd) {
        if (BodMassInd < 18.5) {
            return "Underweight";
        }
        if (BodMassInd < 24.9) {
            return "Normal weight";
        }
        if (BodMassInd < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    /**
     * Returns a proper disclaimer based on given BMI
     * @param BodMassInd The given BMI
     * @return The response
     */
    public String disClaimer(double BodMassInd) {
        if (BodMassInd < 18.5) {
            return "If you are exercising a lot, perhaps you should be consuming a lot more food."
                    + " In order to maintain a healthy BMI, perhaps you should consider eating more nutritous food.";
        }else if (BodMassInd < 24.9) {
            return "There is no need to make major changes. You have a healthy body weight."
                    + " If you decide to, or are exercising a lot. Make sure you continue to consume enough calories to keep up with your physical activity.";
        }else if (BodMassInd < 30) {
            return "If you are not exercising, perhaps the amount of food you eat should be lowered."
                    + " If you are exercising, perhaps you should be exercising more to accomodate how much food you consume."
                    + " If you are a body builder, do not take this calculation seriously.";
        } else {
            return "If you are not exercising, perhaps the amount of food you eat should be lowered."
                    + " If you are exercising, perhaps you should be exercising more to accomodate how much food you consume."
                    + " If you are a body builder, do not take this calculation seriously.";
            }
        }

}

