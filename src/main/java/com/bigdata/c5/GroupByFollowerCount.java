package com.bigdata.c5;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;
import jcascalog.op.Count;

import java.io.IOException;


public class GroupByFollowerCount {
    public static void main(String[] args) throws IOException {
        Api.execute(new StdoutTap(),
                new Subquery("?person", "?count")
                    .predicate(Data.FOLLOWS, "?person",  "_")
                    .predicate(new Count(),"?count"));
        System.out.println("grouping on follow count");
    }
}
