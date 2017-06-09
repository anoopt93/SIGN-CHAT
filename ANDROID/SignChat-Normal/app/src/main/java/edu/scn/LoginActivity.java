package edu.scn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.scn.models.UserLogin;
import edu.scn.nw.SignChatClient;
import edu.scn.utils.Variables;

public class LoginActivity extends AppCompatActivity {
    EditText unameET, passwdET;
    Button loginBT, clearBT, registerBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unameET = (EditText) findViewById(R.id.unameET);
        passwdET = (EditText) findViewById(R.id.passwdET);

    }

    public void loginNow(View view) {
        String uname = unameET.getText().toString();
        String passwd = passwdET.getText().toString();
        if ("".equals(uname)) {
            unameET.setError("Enter Username");
        } else if ("".equals(passwd)) {
            passwdET.setError("Enter Password");
        } else {
            UserLogin ul = new UserLogin(0, uname, passwd, "");
            boolean result = SignChatClient.userLogin(ul);
            if (result) {
                Variables.uname = uname;
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void registerNow(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void clearNow(View view) {
        clear();
    }

    public void clear() {
        unameET.setText(null);
        passwdET.setText(null);
    }
}
