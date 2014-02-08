package com.bigdata.c3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 36015To
 * Date: 05/02/14
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class PartitionedLoginPailStructure extends LoginPailStructure {


    @Override
    public List<String> getTarget(Login login){
        ArrayList<String> directoryPath = new ArrayList<String>();
        directoryPath.add(Utils.getFormattedDate(login.loginUnixTime));
        return directoryPath;
    }

    @Override
    public boolean isValidTarget(String... strings){
        if(strings.length!=2) return false;
        try {
            return (Utils.parseDate(strings[0])!=null) ;
        }
        catch (ParseException e){
            return false;
        }
    }
}
