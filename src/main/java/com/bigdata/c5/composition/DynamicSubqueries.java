package com.bigdata.c5.composition;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.Count;
import jcascalog.op.Sum;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/11/14
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicSubqueries {


    public static Subquery sourceNumTransactions(String path, String source) {
        return new Subquery(source, "?count")
                .predicate(parseTransactionData(path), "?buyer", "?seller", "?amt", "?timestamp")
                .predicate(new Count(), "?count")  ;
    }

    public static Subquery sourceTotalTransactions(String path, String source) {
        return new Subquery(source, "?sum")
                .predicate(parseTransactionData(path), "?buyer", "?seller", "?amt", "?timestamp")
                .predicate(new Sum(), "?amt").out("?sum")  ;
    }

    public static Subquery parseTransactionData(String path) {
        return new Subquery("?buyer", "?seller", "?amt", "?timestamp")
                .predicate(Api.hfsTextline(path), "?line")
                .predicate(new ParseTransactionRecord(), "?line")
                .out("?buyer", "?seller", "?amt", "?timestamp");
    }
}
