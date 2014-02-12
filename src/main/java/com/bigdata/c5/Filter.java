package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.*;

public class Filter {
    public static  void main(String [] args) throws Exception{


        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age")
                .predicate(Data.AGE,"?person", "?age")
                .predicate(new GT(), "?age", 30));
        System.out.println("filtering on age");

        Api.execute(new StdoutTap(),
                new Subquery("?person", "?age")
                        .predicate(Data.AGE,"?person", "?age")
                        .predicate(new LT(), "?age", 30));
        System.out.println("filtering on age");


    }
}
