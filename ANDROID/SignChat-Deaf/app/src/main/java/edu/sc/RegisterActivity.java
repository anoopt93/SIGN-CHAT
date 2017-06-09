package edu.sc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.sc.models.UserDetails;
import edu.sc.models.UserLogin;
import edu.sc.nw.SignChatClient;
import edu.sc.utils.Validation;

public class RegisterActivity extends AppCompatActivity {
    EditText nameET, mobileET, emailET, unameET, passwdET, cpasswdET;
    Button exitBT, registerBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameET = (EditText) findViewById(R.id.nameET);
        mobileET = (EditText) findViewById(R.id.mobileET);
        emailET = (EditText) findViewById(R.id.emailET);
        unameET = (EditText) findViewById(R.id.unameET);
        passwdET = (EditText) findViewById(R.id.passwdET);
        cpasswdET = (EditText) findViewById(R.id.cpasswdET);

    }

    public void registerNow(View view)

    {
        String name = nameET.getText().toString();
        String mobile = mobileET.getText().toString();
        String email = emailET.getText().toString();
        String uname = unameET.getText().toString();
        String passwd = passwdET.getText().toString();
        String cpasswd = cpasswdET.getText().toString();

        if ("".equals(name)) {
            nameET.setError("Enter Name");
        } else if ("".equals(mobile)) {
            mobileET.setError("Enter Mobile Number");
        } else if ("".equals(email)) {
            emailET.setError("Enter Email Address");
        } else if ("".equals(uname)) {
            unameET.setError("Enter Username");
        } else if ("".equals(passwd)) {
            passwdET.setError("Enter Password");
        } else if (!passwd.equals(cpasswd)) {
            Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_LONG).show();
        } else if (!Validation.checkEmail(email)) {
            emailET.setError("Invalid Email");
        } else if (!Validation.checkPhoneNo(mobile)) {
            mobileET.setError("Invalid Mobile");
        } else if (passwd.length() < 4) {
            passwdET.setError("Minimum length 4");
        } else {
            UserLogin ul = new UserLogin(0, uname, passwd, "");
            UserDetails ud = new UserDetails(0, name, mobile, email, ul);
            String result = SignChatClient.userRegister(ud);

            switch (result) {
                case "emailFound":
                    Toast.makeText(getApplicationContext(), "Email Exists", Toast.LENGTH_LONG).show();
                    break;
                case "mobileFound":
                    Toast.makeText(getApplicationContext(), "Mobile Exists", Toast.LENGTH_LONG).show();
                    break;
                case "unameFound":
                    Toast.makeText(getApplicationContext(), "Username Exists", Toast.LENGTH_LONG).show();
                    break;
                case "ok":
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case "fail":
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    break;

            }
        }


    }

    public void resetNow(View view)

    {
        reset();
    }

    public void reset()

    {
        nameET.setText(null);
        mobileET.setText(null);
        emailET.setText(null);
        unameET.setText(null);
        passwdET.setText(null);
        cpasswdET.setText(null);
    }

    @Override
    public void onBackPressed() {

        Intent in = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(in);
        finish();
    }
}
