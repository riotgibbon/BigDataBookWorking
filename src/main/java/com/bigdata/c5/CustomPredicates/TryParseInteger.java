package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tuple.Tuple;
import cascalog.CascalogFunction;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TryParseInteger extends CascalogFunction {
    @Override
    public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
        String s = functionCall.getArguments().getString(0);
        try {
            int i = Integer.parseInt(s);
            functionCall.getOutputCollector().add(new Tuple(i));
        } catch (NumberFormatException e){}
    }
}
