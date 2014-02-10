package com.bigdata.c5.CustomPredicates;

import com.bigdata.c5.PersonData;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomAggregatorGrouped {
    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
        new Subquery("?a" ,"?c")
            .predicate(PersonData.VAL1, "?a", "?b")
            .predicate(new SumAggregator(),"?b").out("?c"));

    }

}
