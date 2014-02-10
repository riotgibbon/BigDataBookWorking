package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.GT;

public class Join {
    public static  void main(String [] args) throws Exception{



        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age", "?gender")
                        .predicate(PersonData.AGE,"?person", "?age")
                        .predicate(PersonData.GENDER, "?person", "?gender"));
        System.out.println("inner join");


        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age", "!!gender")
                        .predicate(PersonData.AGE,"?person", "?age")
                        .predicate(PersonData.GENDER, "?person", "!!gender"));
        System.out.println("left outer join");

        Api.execute(new StdoutTap(),
                new Subquery("?person", "!!age", "!!gender")
                        .predicate(PersonData.AGE,"?person", "!!age")
                        .predicate(PersonData.GENDER, "?person", "!!gender"));
        System.out.println("full outer join");
    }
}
