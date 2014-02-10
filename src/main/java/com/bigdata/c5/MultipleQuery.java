package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.*;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 7:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class MultipleQuery {

    public static  void main(String [] args) throws Exception{

        Api.execute(new StdoutTap(),
                new Subquery("?a", "?avg")
                .predicate(PersonData.VAL1, "?a", "?b")
                .predicate(PersonData.VAL2, "?a", "?c")

                .predicate(new Multiply(),2, "?b").out("?double-b")
                .predicate(new LT(), "?b", "?c")

                .predicate(new Count(), "?count")
                .predicate(new Sum(), "?double-b").out("?sum")
                .predicate(new Div(), "?sum", "?count").out("?avg")

                .predicate(new Multiply(), 2, "?avg").out("?double-avg")
                .predicate(new LT(), "?double-avg", 50)
        );
     }
}