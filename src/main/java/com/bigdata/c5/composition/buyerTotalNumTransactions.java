package com.bigdata.c5.composition;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/12/14
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class buyerTotalNumTransactions {
    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
               new Subquery("?buyer", "?count", "?sum")
                .predicate(DynamicSubqueries.sourceNumTransactions("/tmp/json/input.txt", "?buyer"),"?buyer", "?count")
                .predicate(DynamicSubqueries.sourceTotalTransactions("/tmp/json/input.txt", "?buyer"), "?buyer", "?sum")
        );

    }
}
