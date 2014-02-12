package com.bigdata.c5.CustomPredicates;

import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomFunctionWithFilter {


    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
                new Subquery("?a", "?b", "?c", "?d")
                        .predicate(Data.MIXEDVALUES, "?a", "?b")
                        .predicate(new TryParseInteger(), "?a").out("?c")
                        .predicate(new SquareFunction(), "?c").out("?d")
        ) ;

        System.out.println("filter with TryParseInteger");
    }
}
