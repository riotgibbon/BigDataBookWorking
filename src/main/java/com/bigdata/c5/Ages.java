package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.*;


import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 4:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Ages {
    static List AGE = Arrays.asList(
            Arrays.asList("alice",28),
            Arrays.asList("bob", 33),
            Arrays.asList("chris", 40),
            Arrays.asList("david", 25));

    static List GENDER = Arrays.asList(
            Arrays.asList("alice","f"),
           // Arrays.asList("bob", "m"),
            Arrays.asList("chris", "m"),
            Arrays.asList("david", "m"),
            Arrays.asList("emily", "f"));


    public static  void main(String [] args) throws Exception{


        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age")
                .predicate(AGE,"?person", "?age")
                .predicate(new GT(), "?age", 30));
        System.out.println("filtering on age");


        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age", "?gender")
                        .predicate(AGE,"?person", "?age")
                        .predicate(GENDER, "?person", "?gender"));
        System.out.println("inner join");


        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age", "!!gender")
                        .predicate(AGE,"?person", "?age")
                        .predicate(GENDER, "?person", "!!gender"));
        System.out.println("left outer join");

        Api.execute(new StdoutTap(),
                new Subquery("?person", "!!age", "!!gender")
                        .predicate(AGE,"?person", "!!age")
                        .predicate(GENDER, "?person", "!!gender"));
        System.out.println("full outer join");
    }
}
