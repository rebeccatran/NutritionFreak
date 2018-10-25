package xb3group.com.nutritionfreak;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;
    public String accounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setUp();

        //get the info from login activity (all the previous accounts)
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            accounts = extras.getString("accounts");
        }

        //event hanlder for when the button is pressed
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the inputs are valid
                if (isCompleted() && checkPasswordsEqual()) {
                    String password = confirmPasswordEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    //pass the email, password, and previousAccounts to the profile set up page
                    Intent i = new Intent(CreateAccountActivity.this, ProfileSetUp.class);
                    i.putExtra("accounts",accounts);
                    i.putExtra("myEmail",email);
                    i.putExtra("myPassword",password);
                    startActivity(i);
                }else if (checkPasswordsEqual() == false && isCompleted()){
                    Toast.makeText(getApplicationContext(),"The passwords are not the same",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Please complete all the information",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /*
     * Connect the xm and java files and do general set up
     */
    private void setUp(){
        emailEditText = (EditText)findViewById(R.id.newEmailEditText);
        passwordEditText = (EditText)findViewById(R.id.newPasswordEditTextID);
        confirmPasswordEditText = (EditText)findViewById(R.id.confirmPasswordEditTextID);
        signUpButton = (Button)findViewById(R.id.createAccountButtonID);
    }

    //checks if all the input boxes have been filled in
    private boolean isCompleted(){
        if(emailEditText.getText().length() > 0 && passwordEditText.getText().length() > 0 && confirmPasswordEditText.getText().length() > 0){
            return true;
        }
        return false;
    }

    //checks if the two passwords are equal
    private boolean checkPasswordsEqual (){
        String confirm= confirmPasswordEditText.getText().toString();
        if(passwordEditText.getText().toString().equals(confirm) && isCompleted()){
            //Toast.makeText(getApplicationContext(),"Please complete all the information",Toast.LENGTH_LONG).show();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Write to a file given some data
     * @param data The data
     */
    private void writeFileAddLine(String data){
        AssetManager am = this.getAssets();

        //try/catch
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("accounts.txt", Context.MODE_PRIVATE));
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
