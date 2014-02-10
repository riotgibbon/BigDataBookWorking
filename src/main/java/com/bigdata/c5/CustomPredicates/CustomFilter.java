package com.bigdata.c5.CustomPredicates;

import com.bigdata.c5.PersonData;
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
public class CustomFilter {
    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age")
                        .predicate(PersonData.AGE, "?person", "?age")
                        .predicate(new GreaterThanThirtyFilter(), "?age"));
        System.out.println("filtering on GreaterThanThirtyFilter");
    }
}
