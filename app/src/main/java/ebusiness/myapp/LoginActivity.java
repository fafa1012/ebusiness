package ebusiness.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginButton;
    protected Button mCreateAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.initialize(this, "YqVll0YExesnCRN3eWDVgzxbOSSmoqMALzIRc04o", "Zj249eCqUlh01jkzg9NKhot40OoqrPFPIdWaO1SH");

        //Initialize
        mUsername = (EditText) findViewById(R.id.usernameLoginTextBox);
        mPassword = (EditText) findViewById(R.id.passwordLoginTextBox);
        mLoginButton = (Button) findViewById(R.id.loginBtn);
        mCreateAccountButton = (Button) findViewById(R.id.createAccountBtnLogin);

        //Listen to CreateAccount Button click

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeUserToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(takeUserToRegister);
            }
        });

        //Listen when mLoginButton is clicked
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the User inputs from editText and convert to String
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //Login user with parse
                ParseUser.logInInBackground(username, password,new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {

                        if(e==null){
                            //success User is logged in
                            Toast.makeText(LoginActivity.this, "Welcome back!",Toast.LENGTH_LONG).show();
                            //Take User to Start Page
                            //Intent takeUserHome = new Intent(LoginActivity.this,MyActivity.class);
                            Intent takeUserHome = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(takeUserHome);
                        } else{
                            //sorry there was a problem
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry!");
                            builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //close the Dialog
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            }
                        }

                        });


            }
            } );

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
