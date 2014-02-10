package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.GT;
import jcascalog.op.LT;

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
    static List AGES = Arrays.asList(
            Arrays.asList("alice",28),
            Arrays.asList("bob", 33),
            Arrays.asList("chris", 40),
            Arrays.asList("david", 25));

    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age")
                .predicate(AGES,"?person", "?age")
                .predicate(new GT(), "?age", 30));

    }
}
