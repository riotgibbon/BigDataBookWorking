package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 4:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnionCombine {
    static List TEST1 = Arrays.asList(
            Arrays.asList("a", 1),
            Arrays.asList("b", 4),
            Arrays.asList("a", 1));

    static List TEST2 = Arrays.asList(
            Arrays.asList("d",5),
            Arrays.asList("b", 4),
            Arrays.asList("b", 3));

    public static  void main(String [] args) throws Exception{
       List union= (List) Api.union(TEST1, TEST2);
        Api.execute(new StdoutTap(),
                new Subquery("?x", "?y")
                        .predicate(union,"?x", "?y") );
    }
}

