package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.AggregatorCall;
import cascading.tuple.Tuple;
import cascalog.CascalogAggregator;
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
public class CustomAggregator {
    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
        new Subquery("?a" ,"?c")
            .predicate(PersonData.VAL1, "?a", "?b")
            .predicate(new SumAggregator(),"?b").out("?c"));
    }

    private static class SumAggregator extends CascalogAggregator {
        @Override
        public void start(FlowProcess flowProcess, AggregatorCall aggregatorCall) {
            aggregatorCall.setContext(0);
        }

        @Override
        public void aggregate(FlowProcess flowProcess, AggregatorCall aggregatorCall) {
            int total = (Integer) aggregatorCall.getContext();
            aggregatorCall.setContext(total + aggregatorCall.getArguments().getInteger(0));
        }

        @Override
        public void complete(FlowProcess flowProcess, AggregatorCall aggregatorCall) {
            int total =(Integer) aggregatorCall.getContext();
            aggregatorCall.getOutputCollector().add(new Tuple(total));
        }
    }
}
