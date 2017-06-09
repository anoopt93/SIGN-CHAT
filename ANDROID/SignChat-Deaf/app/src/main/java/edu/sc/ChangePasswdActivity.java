package edu.sc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.sc.nw.SignChatClient;
import edu.sc.utils.Variables;

public class ChangePasswdActivity extends AppCompatActivity {
    EditText cupasswdET, npasswdET, cpasswdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);

        cupasswdET = (EditText) findViewById(R.id.cupasswdET);
        npasswdET = (EditText) findViewById(R.id.npasswdET);
        cpasswdET = (EditText) findViewById(R.id.cpasswdET);
    }

    public void changePassword(View view) {
        String cupasswd = cupasswdET.getText().toString();
        String npasswd = npasswdET.getText().toString();
        String cpasswd = cpasswdET.getText().toString();


        if ("".equals(cupasswd)) {
            cupasswdET.setError("Enter Current Password");
        } else if ("".equals(npasswd)) {
            npasswdET.setError("Enter New Password");
        } else if ("".equals(cpasswd)) {
            cpasswdET.setError("Enter Confirm Password");
        } else {
            if (cpasswd.equals(npasswd)) {
                boolean result = SignChatClient.changePassword(Variables.uname, cupasswd, npasswd);
                if (result) {
                    reset();
                    Toast.makeText(ChangePasswdActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswdActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(ChangePasswdActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                reset();
            }
        }


    }

    public void reset(View view) {
        reset();

    }

    public void reset() {
        cupasswdET.setText("");
        npasswdET.setText("");
        cpasswdET.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePasswdActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
