package com.bigdata.c5.composition;


import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.PredicateMacroTemplate;
import jcascalog.Subquery;
import jcascalog.op.Count;
import jcascalog.op.Div;
import jcascalog.op.Sum;

import java.io.IOException;

/**
 * Created by toby on 2/12/14.
 */
public class macroAverage {
    static String val = "?val";
    static String avg = "?avg";
    static String sum = "?sum";
    static String count = "?count";
    static String result = "?result";


    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
                new Subquery(result)
                        .predicate(Data.CHAINS,"_", val )
                        .predicate(MacroAverage, val).out(result)
        );
    }

    static PredicateMacroTemplate MacroAverage = PredicateMacroTemplate.build(val)
            .out(avg)
            .predicate(new Count(), count)
            .predicate(new Sum(), val).out(sum)
            .predicate(new Div(), sum, count).out(avg);


}
