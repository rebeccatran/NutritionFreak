package xb3group.com.nutritionfreak;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView noAccountYet;
    private Button logInButton;
    //private String validEmail = "foo@example.com";
    //private String validPassword = "hello";
    private List<String> mLines;
    //public static final String mPath = "hello.txt";
    private String prevAccounts;
    private String[] accounts;

    /**
     *Whe the activity is created
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUp();
        //String ret = readFromFile();
        //writeFileAddLine("");
        writeFileAddLine("foo,foo@example.com,hello,20,Ontario,165,5,10,23.0");
        final String prevAccounts = readFromFile();
        final String[] a = prevAccounts.split("\\$");

        /*try {
            ServingPerDay x= new ServingPerDay(LoginActivity.this);
            //idServings newserving=new idServings(LoginActivity.this);
            Toast.makeText(getApplicationContext(), x.Recommended("female",26), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Serving not found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/


        //final Account account = new Account(name,email,password,age,province,weight,feet,inches,bmi);
        //Toast.makeText(getApplicationContext(), prevAccounts, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), a[1], Toast.LENGTH_SHORT).show();



        //readFile();

        //when the login button is click on, check if there is the email and password that match the input and sign in
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the texts from the edit texts
                //String[] info = usersArray[0].split(",");
                //String email = info[0];
                //String password = info[1];

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                int idNumber = getUserNameID(a, email, password);



                //writeFileAddLine(email);
                //readFile();

                //check if the email and password is valid
                if (checkEmail(a,email,password) && idNumber != -1) {
                    final String[] info = a[idNumber].split(",");
                    final String name = info[0];
                    //String email = info[1];
                    //String password = info[2];
                    final int age = Integer.parseInt(info[3]);
                    final String province = info[4];
                    final double weight = Double.parseDouble(info[5]);
                    final double feet = Double.parseDouble(info[6]);
                    final double inches = Double.parseDouble(info[7]);
                    final double bmi = Double.parseDouble(info[8]);
                    //pass all the information to the main activity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("name",name);
                    i.putExtra("age",age);
                    i.putExtra("weight",weight);
                    i.putExtra("bmi",bmi);
                    i.putExtra("feet",feet);
                    i.putExtra("inches",inches);
                    i.putExtra("province",province);
                    startActivity(i);
                } else { //show error message if the email or password is not valid
                    Toast.makeText(getApplicationContext(), "Email or password is incorrect.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //if they haven't created an account yet, go the create account activity
        noAccountYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
                i.putExtra("accounts",prevAccounts);
                startActivity(i);
            }
        });

    }

    /**
     * Link the xml file buttons,edit text, and text view to the java code
     */
    private void setUp(){
        logInButton = (Button)findViewById(R.id.loginButton);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        noAccountYet = (TextView)findViewById(R.id.noAccountYetTextView);
    }

    /**
     * Read internal file
     */
    private void readFile(){
        //List<String> mLines = new ArrayList<>();

        AssetManager am = this.getAssets();

        try {
            InputStream is = am.open("hello.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                Toast.makeText(getApplicationContext(),line,Toast.LENGTH_LONG).show();
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /*try {
            String path = getFileStreamPath("hello.txt");
            Scanner input =  new Scanner(new File(getFileStreamPath("hello.txt")));//"app/src/res/data/hello.txt"
            String current = input.nextLine();
            Toast.makeText(getApplicationContext(),current,Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Did no Read",Toast.LENGTH_LONG).show();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"asdad",Toast.LENGTH_LONG).show();

        }*/

    /**
     * write data into a file given the info that goes in the text file
     * @param data The info going into the text file
     */
    private void writeFileAddLine(String data){
        AssetManager am = this.getAssets();

        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("accounts.txt",Context.MODE_PRIVATE));
            out.write(data);
            out.close();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            //String line;
            //Toast.makeText(getApplicationContext(),line,Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read internal file for storing local accounts
     * @return The file in as a string
     */
    private String readFromFile() {

        String ret = "";

        //try/catch
        try {
            InputStream inputStream = openFileInput("accounts.txt");

            //check if the file is not null
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                //put the file into the string builder
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    /**
     * Return the index/idNumber of an account given the array of account and email and password
     * @param a The list of accounts
     * @param email The email of th account we are looking for
     * @param password The passwword of the account we are looking for
     * @return The index/idNumber
     */
    private int getUserNameID(String[] a, String email, String password){
        //go through the all the accounts
        for(int i =0; i < a.length ; i++){
            String[] current = a[i].split(","); //current account
            //if the email and password match the user, return the index
            if(current[1].equals(email) && current[2].equals(password)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if there exists a user with the given email and password
     * @param a The list of users
     * @param email The given email
     * @param password Thr given password
     * @return Boolean of wether or not the user exits
     */
    private boolean checkEmail(String[] a,String email,String password){
        //go through all the accounts
        for(int i =0; i < a.length ; i++){
            String[] current = a[i].split(",");
            //if the email and password match a user return true
            if(current[1].equals(email) && current[2].equals(password)){
                return true;
            }
        }
        return false;
    }
}
