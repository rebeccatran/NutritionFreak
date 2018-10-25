package xb3group.com.nutritionfreak;

/**
 * Created by Cesar on 3/30/2016.
 */
public class Account {
    private String username;
    private String email;
    private final String password;
    private double weight;
    private double inches;
    private double feet;
    private int age;
    private String province;
    private double bmi;

    /**
     * Creates an Account object based on given info such as name, email, weight, and height
     * @param name The user's name
     * @param email The user's email
     * @param password The user's password
     * @param age The user's age
     * @param province The user's province of residence
     * @param weight The user's weight
     * @param feet The user's height(the feet portion)
     * @param inches The user's height (the inches portion)
     * @param bmi The user's BMI score
     */
    public Account(String name, String email, String password,int age ,String province,double weight, double feet, double inches,double bmi){
        this.username = name;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.feet = feet;
        this.inches = inches;
        this.age = age;
        this.province = province;
        this.bmi = bmi;
    }

    /**
     * Gets the name of the user
     * @return the user's name
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Gets the user's email
     * @return The user's email
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Gets the user's weight
     * @return Weight
     */
    public double getWeight(){
        return this.weight;
    }

    /**
     * Gets the user's height (the inches portion)
     * @return inches
     */
    public double getInches(){
        return this.inches;
    }

    /**
     * Gets the user's height (the feet portion)
     * @return feet
     */
    public double getFeet(){
        return this.feet;
    }

    /**
     * Gets the user's BMI score
     * @return BMI score
     */
    public double getBmi(){
        return this.bmi;
    }

    /**
     * Gets the user's password
     * @return Their password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Returns the sting representation of an account
     * @return User's email
     */
    public String toString(){
        return this.email;
    }
}
