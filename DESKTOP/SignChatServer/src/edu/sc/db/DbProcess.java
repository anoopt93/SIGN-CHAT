package edu.sc.db;

import edu.sc.models.UserDetails;
import edu.sc.models.UserLogin;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author pvr
 */
public class DbProcess extends DbCon {

    private String sql = "";
    private int i = 0;
    private ResultSet rs = null;
    private String tables[] = new String[2];
    private String dbName = "sign_chat";
    boolean flag = false;

    public DbProcess() {
        super();
        try {

            sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbName + "'";
            rs = getData(sql);
            if (!rs.next()) {
                sql = "CREATE SCHEMA IF NOT EXISTS " + dbName;
                putData(sql);
                sql = "USE " + dbName;
                putData(sql);
                createSchemaAndTables();
            }
            rs.close();
            sql = "USE " + dbName;
            putData(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSchemaAndTables() {
        try {
            //USER LOGIN
            tables[0] = "CREATE TABLE `user_login` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `uname` varchar(45) NOT NULL,\n"
                    + "  `passwd` varchar(45) NOT NULL,\n"
                    + "  `sessionStatus` varchar(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `uname_UNIQUE` (`uname`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=latin1";

            //USER DETAILS
            tables[1] = "CREATE TABLE `user_details` (\n"
                    + "  `id` int(11) NOT NULL,\n"
                    + "  `name` varchar(45) NOT NULL,\n"
                    + "  `mobile` varchar(45) NOT NULL,\n"
                    + "  `email` varchar(100) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE KEY `mobile_UNIQUE` (`mobile`),\n"
                    + "  UNIQUE KEY `email_UNIQUE` (`email`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=latin1";

            for (int i = 0; i < tables.length; i++) {
                putData(tables[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean userRegister(UserDetails ud) {
        try {
            sql = "insert into user_login values(0,'" + ud.userLogin.uname + "','" + ud.userLogin.passwd + "','offline')";
            i = putData(sql);
            if (i > 0) {
                i = 0;
                sql = "insert into user_details values((select id from user_login where uname='" + ud.userLogin.uname + "'),'" + ud.name + "','" + ud.mobile + "','" + ud.email + "')";

                i = putData(sql);
                if (i > 0) {
                    flag = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public boolean userLogin(UserLogin ul) {
        try {
            sql = "select * from user_login where uname='" + ul.uname + "' and passwd='" + ul.passwd + "'";
            rs = getData(sql);
            if (rs.next()) {
                sql = "update user_login set sessionStatus='online' where uname='" + ul.uname + "'";
                i = putData(sql);
                if (i > 0) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public boolean changePassword(String uname, String currentPasswd, String newPasswd) {
        try {
            sql = "update user_login set passwd='" + newPasswd + "' where uname='" + uname + "' and passwd='" + currentPasswd + "'";
            i = putData(sql);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public ArrayList<String> getAllOnlineUsers(String userName) {
        ArrayList<String> onlineUsers = new ArrayList<>();
        try {
            sql = "select * from user_login where sessionStatus='online' and uname!='" + userName + "'";
            rs = getData(sql);
            while (rs.next()) {
                String uname = rs.getString("uname");
                onlineUsers.add(uname);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return onlineUsers;

    }

    public boolean logout(String userName) {
        try {
            sql = "update user_login set sessionStatus='offline' where uname='" + userName + "'";
            i = putData(sql);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isEmailFound(String email) {
        try {
            sql = "select * from user_details where email='" + email + "'";
            rs = getData(sql);
            if (rs.next()) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isMobileFound(String mobile) {
        try {
            sql = "select * from user_details where mobile='" + mobile + "'";
            rs = getData(sql);
            if (rs.next()) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isUnameFound(String uname) {
        try {
            sql = "select * from user_login where uname='" + uname + "'";
            rs = getData(sql);
            if (rs.next()) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        DbProcess dbProcess = new DbProcess();
        System.out.println(dbProcess.getAllOnlineUsers("a"));
    }
}
