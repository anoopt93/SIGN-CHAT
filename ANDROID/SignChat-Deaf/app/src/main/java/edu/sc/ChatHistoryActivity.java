package edu.sc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.sc.db.DbProcess;
import edu.sc.models.ChatHistory;

public class ChatHistoryActivity extends AppCompatActivity {
    ListView lv = null;
    ArrayList<ChatHistory> chList = new ArrayList<ChatHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);
        lv = (ListView) findViewById(R.id.lv);
        initList();
        lv.setOnItemClickListener(new ListClick());

    }

    public void initList() {
        DbProcess dbp = new DbProcess(getApplicationContext());
        chList = dbp.getAllChatHistory();
        dbp.close();
        ArrayList<String> items = new ArrayList<String>();

        for (ChatHistory ch : chList) {
            items.add(ch.msg);
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(aa);

    }

    public class ListClick implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i != -1) showMessage(i);
        }
    }

    public void showMessage(final int pos) {
        new AlertDialog.Builder(this)
                .setTitle("Chat Message")
                .setMessage(chList.get(pos).toString())
                .setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int id) {

                            }
                        }
                )
                .setNegativeButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int id) {
                                ChatHistory ch = chList.get(pos);
                                DbProcess dbp = new DbProcess(getApplicationContext());
                                dbp.deleteChatHistory(ch.id);
                                dbp.close();
                                initList();
                            }
                        }
                ).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChatHistoryActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void clearAll(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING");
        builder.setMessage("Are You Want To Clear Full Chat History???");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbProcess dbProcess = new DbProcess(getApplicationContext());
                boolean result = dbProcess.clearAllChatHistory();
                if (result) {
                    ChatHistoryActivity.this.recreate();
                    Toast.makeText(ChatHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatHistoryActivity.this, "Failed Try Again", Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();


    }
}
