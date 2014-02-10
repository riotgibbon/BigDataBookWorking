package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.AggregatorCall;
import cascading.tuple.Tuple;
import cascalog.CascalogAggregator;

/**
* Created with IntelliJ IDEA.
* User: toby
* Date: 2/10/14
* Time: 7:02 PM
* To change this template use File | Settings | File Templates.
*/
class SumAggregator extends CascalogAggregator {
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
