package edu.scn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import edu.scn.nw.FileClient;
import edu.scn.nw.SignChatClient;
import edu.scn.utils.Variables;

public class HomeActivity extends AppCompatActivity {
    public static Context context = null;
    public static FileClient fc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = getApplicationContext();
        fc = new FileClient(Variables.fileServerIP, Variables.fileServerPort, Variables.uname);
    }

    public void changePassword(View view) {
        Intent intent = new Intent(HomeActivity.this, ChangePasswdActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendFile(View view) {
        Intent intent = new Intent(HomeActivity.this, FileSendActivity.class);
        startActivity(intent);
        finish();
    }

    public void chatRoom(View view) {
        Intent intent = new Intent(HomeActivity.this, ChatRoomActivity.class);
        startActivity(intent);
        finish();
    }

    public void chatHistory(View view) {
        Intent intent = new Intent(HomeActivity.this, ChatHistoryActivity.class);
        startActivity(intent);
        finish();
    }

    public void fileHistory(View view) {
        Intent intent = new Intent(HomeActivity.this, FileHistoryActivity.class);
        startActivity(intent);
        finish();
    }

    public void fileInbox(View view) {
        Intent intent = new Intent(HomeActivity.this, FileInboxActivity.class);
        startActivity(intent);
        finish();
    }

    public void logout(View view) {
        boolean result = SignChatClient.logout(Variables.uname);
        if (result) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public static Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (Variables.isFileReceived) {
                Toast.makeText(HomeActivity.context, "File Received", Toast.LENGTH_LONG).show();
            }
            Variables.isFileReceived = false;
        }
    };
}
