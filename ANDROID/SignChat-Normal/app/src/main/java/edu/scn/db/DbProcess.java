package edu.scn.db;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import edu.scn.models.ChatHistory;
import edu.scn.models.FileHistory;

public class DbProcess extends DbCon {
    String sql = "";
    Cursor cursor = null;
    int i = 0;
    boolean flag = false;

    public DbProcess(Context context) {
        super(context);
    }


    public boolean insertChatHistory(ChatHistory ch) {
        try {
            sql = "insert into chat_history(fromName,toName,msg,status,ctDate,ctTime) values('" + ch.fromName + "','" + ch.toName + "','" + ch.msg + "','" + ch.status + "','" + ch.ctDate + "','" + ch.ctTime + "')";
            i = putData(sql);
            if (i > 0) {
                flag = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean insertFileHistory(FileHistory fh) {
        try {
            sql = "insert into file_history(fromName,toName,fileName,fileSize,status,fDate,fTime) values('" + fh.fromName + "','" + fh.toName + "','" + fh.fileName + "','" + fh.fileSize + "','" + fh.status + "','" + fh.fDate + "','" + fh.ftime + "')";
            i = putData(sql);
            if (i > 0) {
                flag = true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public ArrayList<ChatHistory> getAllChatHistory() {
        ArrayList<ChatHistory> chatHistories = new ArrayList<>();
        try {
            sql = "select * from chat_history";
            cursor = getData(sql);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String fromName = cursor.getString(1);
                String toName = cursor.getString(2);
                String msg = cursor.getString(3);
                String status = cursor.getString(4);
                String ctDate = cursor.getString(5);
                String ctTime = cursor.getString(6);
                ChatHistory chatHistory = new ChatHistory(id, fromName, toName, msg, status, ctDate, ctTime);
                chatHistories.add(chatHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatHistories;

    }

    public ArrayList<FileHistory> getAllFileHistory() {
        ArrayList<FileHistory> fileHistories = new ArrayList<>();
        try {
            sql = "select * from file_history";
            cursor = getData(sql);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String fromName = cursor.getString(1);
                String toName = cursor.getString(2);
                String fileName = cursor.getString(3);
                String fileSize = cursor.getString(4);
                String status = cursor.getString(5);
                String fDate = cursor.getString(6);
                String fTime = cursor.getString(7);
                FileHistory fh = new FileHistory(id, fromName, toName, fileName, fileSize, "send", fDate, fTime);
                fileHistories.add(fh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileHistories;

    }

    public ArrayList<FileHistory> getAllReceivedFileHistory() {
        ArrayList<FileHistory> fileHistories = new ArrayList<>();
        try {
            sql = "select * from file_history where status='received'";
            cursor = getData(sql);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String fromName = cursor.getString(1);
                String toName = cursor.getString(2);
                String fileName = cursor.getString(3);
                String fileSize = cursor.getString(4);
                String status = cursor.getString(5);
                String fDate = cursor.getString(6);
                String fTime = cursor.getString(7);
                FileHistory fh = new FileHistory(id, fromName, toName, fileName, fileSize, "send", fDate, fTime);
                fileHistories.add(fh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileHistories;

    }

    public boolean deleteFileHistory(int id) {
        try {
            sql = "delete from file_history where id=" + id + "";
            i = putData(sql);
            if (i > 0) {
                flag = true;
            }

        } catch (Exception e)

        {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteChatHistory(int id) {
        try {
            sql = "delete from chat_history where id=" + id + "";
            i = putData(sql);
            if (i > 0) {
                flag = true;
            }

        } catch (Exception e)

        {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean clearAllChatHistory() {
        try {
            sql = "delete from chat_history";
            i = putData(sql);
            if (i > 0) {
                flag = true;
            }

        } catch (Exception e)

        {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean clearAllFileHistory() {
        try {
            sql = "delete from file_history";
            i = putData(sql);
            if (i > 0) {
                flag = true;
            }

        } catch (Exception e)

        {
            e.printStackTrace();
        }
        return flag;
    }
}
