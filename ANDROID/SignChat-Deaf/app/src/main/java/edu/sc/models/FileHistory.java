package edu.sc.models;

/**
 * Created by pvr on 4/10/16.
 */
public class FileHistory {
    public int id;
    public String fromName;
    public String toName;
    public String fileName;
    public String fileSize;
    public String status;
    public String fDate;
    public String ftime;

    public FileHistory(int id, String fromName, String toName, String fileName, String fileSize, String status, String fDate, String ftime) {
        this.id = id;
        this.fromName = fromName;
        this.toName = toName;
        this.fileName = fileName;
        this.status = status;
        this.fDate = fDate;
        this.ftime = ftime;
        ;
    }
}
