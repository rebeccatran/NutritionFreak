package xb3group.com.nutritionfreak;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ProfileSetUp extends AppCompatActivity {
    private EditText ageEditText;
    private EditText weightEditText;
    private EditText heightInchesEditText;
    private EditText heightFeetEditText;
    private Button submitButton;
    private EditText userNameEditText;
    private EditText provinceEditText;
    private RadioGroup provinceRadioGroup;
    private BMI bmi = new BMI();
    private String email;
    private String password;
    private String accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);

        //general set up
        setUp();

        //get info passed from create account activity
        Bundle extras = getIntent().getExtras();
        //check if the bundle is not null
        if (extras != null){
            email = extras.getString("myEmail");
            password = extras.getString("myPassword");
            accounts = extras.getString("accounts");
        }

        //even handler for when the submit button is clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isComplete;
                isComplete = checkComplete();
                //check if all the info has been filled in
                if(isComplete == true){
                    //get the info from edit texts and radio buttons
                    double feetHeight = Double.parseDouble(heightFeetEditText.getText().toString());
                    double inchesHeight = Double.parseDouble(heightInchesEditText.getText().toString());
                    double weight = Double.parseDouble(weightEditText.getText().toString());
                    String name = userNameEditText.getText().toString();
                    int radioID = provinceRadioGroup.getCheckedRadioButtonId();
                    RadioButton province = (RadioButton)findViewById(radioID);
                    String provinceName = (String)province.getText();
                    //Toast.makeText(getApplicationContext(),provinceName,Toast.LENGTH_LONG).show();
                    int age = Integer.parseInt(ageEditText.getText().toString());

                    //calculate the bmi
                    double result = bmi.calculate((int)feetHeight, inchesHeight, weight);
                    String newAccount ="$"+ name + "," + email + "," + password + ","+ age + "," + provinceName + ","+ weight + "," + feetHeight +","+inchesHeight+ ","+result;
                    writeFileAddLine(accounts + newAccount);

                    //Toast.makeText(getApplicationContext(), accounts + newAccount, Toast.LENGTH_SHORT).show();
                    //Account newAccount = new Account(name, email,password ,age,province,weight,feetHeight,inchesHeight,result);
                    //Toast.makeText(getApplicationContext(),"BMI: " + result,Toast.LENGTH_LONG).show();

                    //pass all the info to the main activity
                    Intent i = new Intent(ProfileSetUp.this, MainActivity.class);
                    i.putExtra("inches",inchesHeight);
                    i.putExtra("feet",feetHeight);
                    i.putExtra("name",name);
                    i.putExtra("province",provinceName);
                    i.putExtra("age",age);
                    i.putExtra("weight",weight);
                    i.putExtra("bmi",result);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Please complete all the information", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    /**
     * Connect the xml and java code
     */
    private void setUp(){
        ageEditText = (EditText)findViewById(R.id.ageEditTextID);
        weightEditText = (EditText)findViewById(R.id.weightEditTextID);
        heightInchesEditText = (EditText)findViewById(R.id.inchesEditTextID);
        heightFeetEditText = (EditText)findViewById(R.id.feetEditTextID);
        submitButton = (Button)findViewById(R.id.submitButtonID);
        userNameEditText = (EditText)findViewById(R.id.userNameEditTextID);
        provinceRadioGroup = (RadioGroup)findViewById(R.id.provinceRadioGroupID);
    }

    /**
     * check if all the info boxes have been filled in
     */
    private boolean checkComplete(){
        if(ageEditText.getText().length() > 0 && weightEditText.getText().length()>0 && heightFeetEditText.getText().length()>0 && heightInchesEditText.getText().length()>0 && userNameEditText.getText().length()>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Write to the accounts text file given some data
     * @param data The data
     */
    private void writeFileAddLine(String data){
        AssetManager am = this.getAssets();

        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("accounts.txt", Context.MODE_PRIVATE));
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
