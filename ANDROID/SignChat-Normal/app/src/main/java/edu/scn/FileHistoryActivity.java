package edu.scn;

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

import edu.scn.db.DbProcess;
import edu.scn.models.FileHistory;

public class FileHistoryActivity extends AppCompatActivity {
    ListView lv = null;
    ArrayList<FileHistory> fhList = new ArrayList<FileHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_history);
        lv = (ListView) findViewById(R.id.lv);
        initList();
        lv.setOnItemClickListener(new ListClick());
    }

    public void initList() {
        DbProcess dbp = new DbProcess(getApplicationContext());
        fhList = dbp.getAllFileHistory();
        dbp.close();
        ArrayList<String> items = new ArrayList<String>();

        for (FileHistory fh : fhList) {
            items.add(fh.fileName);
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
                .setTitle("File Send")
                .setMessage(fhList.get(pos).toString())
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
                                FileHistory fh = fhList.get(pos);
                                DbProcess dbp = new DbProcess(getApplicationContext());
                                dbp.deleteFileHistory(fh.id);
                                dbp.close();
                                initList();
                            }
                        }
                ).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FileHistoryActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void clearAll(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING");
        builder.setMessage("Are You Want To Clear Full File History???");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbProcess dbProcess = new DbProcess(getApplicationContext());
                boolean result = dbProcess.clearAllFileHistory();
                if (result) {
                    FileHistoryActivity.this.recreate();
                    Toast.makeText(FileHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FileHistoryActivity.this, "Failed Try Again", Toast.LENGTH_SHORT).show();

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
