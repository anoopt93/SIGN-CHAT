package edu.sc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sc.nw.SignChatClient;
import edu.sc.utils.FileChooser;

public class FileSendActivity extends AppCompatActivity {

    private static final int REQUEST_PATH = 1;
    Spinner toSP = null;
    String curFileName;
    EditText pathET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_send);

        toSP = (Spinner) findViewById(R.id.toSP);

        pathET = (EditText) findViewById(R.id.filePath);

        initCombo();
    }

    public void initCombo() {
        ArrayList<String> onlineUsers = SignChatClient.getAllOnlineUsers();
        onlineUsers.add(0, "--SELECT USER--");
        onlineUsers.add(1, "ALL");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, onlineUsers);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSP.setAdapter(aa);


    }

    public void getFile(View view) {
        Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1, REQUEST_PATH);
    }

    public void uploadFile(View view) {

        if ("".equals(pathET.getText().toString())) {
            pathET.setError("Choose A File");
        } else if (toSP.getSelectedItemPosition() <= 0) {
            Toast.makeText(FileSendActivity.this, "Must Select A User", Toast.LENGTH_SHORT).show();
        } else {
            String to = toSP.getSelectedItem().toString();


            HomeActivity.fc.sender.sendFile(pathET.getText().toString(), to);


            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
        }

    }

    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH) {
            if (resultCode == RESULT_OK) {
                curFileName = data.getStringExtra("GetFileName");
                String filePath = data.getStringExtra("GetPath");
                pathET.setText(filePath + "/" + curFileName);
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FileSendActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void reset(View view) {
        toSP.setSelection(0);
        pathET.setText(null);
    }
}
