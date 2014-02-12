package com.bigdata.c5.composition;

import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.Count;
import jcascalog.op.GT;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class manyFollows {

    public static  void main(String [] args) throws Exception{
        String person = "?person";
        String count = "?count";
        Subquery manyFollows = new Subquery(person)
                .predicate(Data.FOLLOWS, person, "_")
                .predicate(new Count(), count)
                .predicate(new GT(), count, 2);

        String person1 = "?person1";
        String person2 = "?person2";
        Api.execute(new StdoutTap(),
                new Subquery(person1, person2)
                .predicate(manyFollows, person1)
                .predicate(manyFollows,person2)
                .predicate(Data.FOLLOWS, person1, person2));
    }
}
