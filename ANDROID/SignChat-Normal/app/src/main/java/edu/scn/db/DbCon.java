package edu.scn.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbCon extends SQLiteOpenHelper {
    SQLiteDatabase db = null;

    public DbCon(Context ctx) {
        super(ctx, "sign_chat", null, 1);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String[] sql = new String[2];


        sql[0] = "CREATE TABLE IF NOT EXISTS chat_history("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fromName VARCHAR(50),"
                + "toName VARCHAR(50),"
                + "msg VARCHAR(150),"
                + "status VARCHAR(150),"
                + "ctDate VARCHAR(50),"
                + "ctTime VARCHAR(50))";

        sql[1] = "CREATE TABLE IF NOT EXISTS file_history("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "fromName VARCHAR(50),"
                + "toName VARCHAR(50),"
                + "fileName VARCHAR(150),"
                + "fileSize VARCHAR(150),"
                + "status VARCHAR(150),"
                + "fDate VARCHAR(50),"
                + "fTime VARCHAR(50))";


        for (int i = 0; i < sql.length; i++) {

            putData(sql[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        onCreate(db);
    }

    public Cursor getData(String sql) {
        Cursor curSor = null;
        try {

            System.out.println(sql);
            curSor = db.rawQuery(sql, null);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return curSor;
    }

    public int putData(String sql) {
        int i = 0;
        try {
            System.out.println(sql);
            db.execSQL(sql);
            i++;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return i;
    }


}
