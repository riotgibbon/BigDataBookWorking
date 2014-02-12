package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.Count;
import jcascalog.op.LT;

import java.io.IOException;


public class GroupByGenderAgeFilter {
    public static void main(String[] args) throws IOException {
        Api.execute(new StdoutTap(),
                new Subquery("?gender", "?count")
                    .predicate(Data.GENDER, "?person",  "?gender")
                    .predicate(Data.AGE, "?person", "?age")
                    .predicate(new LT(),  "?age", 35)
                    .predicate(new Count(),"?count"));
        System.out.println("grouping on filtered age");
    }
}
