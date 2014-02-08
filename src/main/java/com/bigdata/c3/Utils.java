package com.bigdata.c3;

import com.backtype.hadoop.pail.Pail;
import com.backtype.hadoop.pail.PailStructure;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public static void writeLogins(String path, String name1, String name2) throws IOException {
        writeLogins(path, name1, name2, new LoginPailStructure());
    }

    public static void writeLogins(String path, String name1, String name2, PailStructure<Login> pailStructure) throws IOException {

        Pail<Login> loginPail = getLoginPail(path, pailStructure);
        writeNamesToPail(path, name1, name2, loginPail);
    }

    public static void writeNamesToPail(String path, String name1, String name2, Pail<Login> loginPail) throws IOException {
        System.out.println("writing " + name1 + " and " + name2 + " to " + path);
        Pail.TypedRecordOutputStream out = loginPail.openWrite();
        out.writeObject(new Login(name1, getUnixTime()));
        out.writeObject(new Login(name2, getUnixTime()));
        out.close();
    }

    public static void appendData(String path1, String path2) throws IOException {
        System.out.println("absorbing logins");
        Pail<Login> loginPail = new Pail<Login>(path1);
        Pail<Login> updatePail = new Pail<Login>(path2);
        loginPail.absorb(updatePail);
    }

    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static void readLogins(String path) throws IOException {
        System.out.println("Reading logins in " + path);
        Pail<Login> loginPail = new Pail<Login>(path);
        for (Login l:loginPail){
            java.util.Date time=new java.util.Date((long)l.loginUnixTime*1000);
            System.out.println(l.userName + ": " + time );
        }
    }



    public static  Pail<Login> getLoginPail(String path, PailStructure<Login> pailStructure) throws IOException {
        FileSystem local = FileSystem.get(new Configuration());
        Pail pail;
        if (local.exists(new Path(path)))
            local.delete(new Path(path), true);
        pail = Pail.create(local, path, pailStructure );
        return  pail;
    }

    public static String getFormattedDate(Long unixTime)
    {
        Date date = new Date(unixTime * 1000L);
        String targetDirectory = formatter.format(date);
        return targetDirectory;
    }
    public static Date parseDate(String toParse) throws ParseException {
        return formatter.parse(toParse) ;
    }
}
