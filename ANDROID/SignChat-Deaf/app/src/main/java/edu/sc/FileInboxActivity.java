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

import java.util.ArrayList;

import edu.sc.db.DbProcess;
import edu.sc.models.FileHistory;

public class FileInboxActivity extends AppCompatActivity {
    ListView lv = null;
    ArrayList<FileHistory> fiList = new ArrayList<FileHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_inbox);
        lv = (ListView) findViewById(R.id.lv);
        initList();
        lv.setOnItemClickListener(new ListClick());
    }

    public void initList() {
        DbProcess dbp = new DbProcess(getApplicationContext());
        fiList = dbp.getAllReceivedFileHistory();
        dbp.close();
        ArrayList<String> items = new ArrayList<String>();

        for (FileHistory fi : fiList) {
            items.add(fi.fileName);
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
                .setTitle("File Received")
                .setMessage(fiList.get(pos).toString())
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
                                FileHistory fi = fiList.get(pos);
                                DbProcess dbp = new DbProcess(getApplicationContext());
                                dbp.deleteFileHistory(fi.id);
                                dbp.close();
                                initList();
                            }
                        }
                ).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FileInboxActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
