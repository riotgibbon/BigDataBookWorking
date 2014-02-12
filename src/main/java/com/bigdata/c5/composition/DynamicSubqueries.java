package com.bigdata.c5.composition;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.Count;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/11/14
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicSubqueries {

    public static  void main(String [] args) throws Exception{
         Api.execute(new StdoutTap(),
                 new Subquery("?buyer", "?count")
                 .predicate( buyerNumTransactions("/tmp/json/input.txt"),"?buyer", "?count")
         );
    }

    public static Subquery buyerNumTransactions(String path) {
        return new Subquery("?buyer", "?count")
                .predicate(parseTransactionData(path), "?buyer", "_", "_", "_")
                .predicate(new Count(), "?count")  ;
    }

    public static Subquery parseTransactionData(String path) {
        return new Subquery("?buyer", "?seller", "?amt", "?timestamp")
                .predicate(Api.hfsTextline(path), "?line")
                .predicate(new ParseTransactionRecord(), "?line")
                .out("?buyer", "?seller", "?amt", "?timestamp");
    }
}
