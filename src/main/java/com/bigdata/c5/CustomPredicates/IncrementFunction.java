package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tuple.Tuple;
import cascalog.CascalogFunction;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class IncrementFunction extends CascalogFunction {
    @Override
    public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
        int v = functionCall.getArguments().getInteger(0);
        functionCall.getOutputCollector().add(new Tuple(v+1));
    }
}
