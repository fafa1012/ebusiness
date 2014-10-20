package ebusiness.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class RegisterActivity extends Activity {

    protected EditText mUsername;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected Button   mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Parse.initialize(this, "YqVll0YExesnCRN3eWDVgzxbOSSmoqMALzIRc04o", "Zj249eCqUlh01jkzg9NKhot40OoqrPFPIdWaO1SH");


        //Initialize
        mUsername       = (EditText) findViewById(R.id.userNameRegisterEditText);
        mUserEmail      = (EditText) findViewById(R.id.emailRegisterEditText);
        mUserPassword   = (EditText) findViewById(R.id.passwordRegisterEditText);
        mRegisterButton = (Button) findViewById(R.id.registerButton);


        //listen on register button click
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the username, password and email and convert them to string
                String username = mUsername.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();


                //store user in parse
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //user signed up successfully
                            Toast.makeText(RegisterActivity.this, "Success! Welcome", Toast.LENGTH_LONG).show();

                            //take user to mainpage
                            //Intent takeUserHome = new Intent(RegisterActivity.this, MyActivity.class);
                            Intent takeUserHome = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(takeUserHome);

                        } else {

                        }
                    }
                });

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
