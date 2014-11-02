package ebusiness.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

//Facebook
import java.util.Arrays;
import java.util.List;
import android.app.ProgressDialog;
import android.util.Log;

import ebusiness.myapp.Facebook.UserDetailsActivity;


public class LoginActivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginButton;
    protected Button mCreateAccountButton;

    //Facebook

    private Button loginButton;
    private Dialog progressDialog;


    //überschreibt Back-Button
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Nocheinmal um App zu schließen!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
    @Override
    public void finish() {
        System.out.println("finish activity");

        System.runFinalizersOnExit(true) ;
        super.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

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
                Intent takeUserToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
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
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {

                        if (e == null) {
                            //success User is logged in
                            Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();
                            //Take User to Start Page
                            //Intent takeUserHome = new Intent(LoginActivity.this,MyActivity.class);
                            Intent takeUserHome = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(takeUserHome);
                        } else {
                            //sorry there was a problem
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        });

        //Facebook

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            private int status;

            @Override
            public void onClick(View v) {
                MainActivity.status = 1;
                onLoginButtonClicked();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(MainActivity.status == 0) {
            getMenuInflater().inflate(R.menu.login, menu);
        }
        if(MainActivity.status == 1)
        {
            getMenuInflater().inflate(R.menu.facebook, menu);
        }
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

    //Facebook

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
    private void onLoginButtonClicked() {
        LoginActivity.this.progressDialog =
                ProgressDialog.show(LoginActivity.this, "", "Logging in...", true);
        List<String> permissions = Arrays.asList("public_profile", "email");
// NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
// (https://developers.facebook.com/docs/facebook-login/permissions/)
        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                LoginActivity.this.progressDialog.dismiss();
                if (user == null) {
                    Log.d(MainActivity.TAG, "Uh oh. The user cancelled the Facebook login.");
                    MainActivity.status = 0;
                } else if (user.isNew()) {
                    Log.d(MainActivity.TAG, "User signed up and logged in through Facebook!");
                    showUserDetailsActivity();
                } else {
                    Log.d(MainActivity.TAG, "User logged in through Facebook!");
                    showUserDetailsActivity();
                }
            }
        });
    }
    private void showUserDetailsActivity() {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        startActivity(intent);
    }


}
