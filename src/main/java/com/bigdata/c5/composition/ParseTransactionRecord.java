package com.bigdata.c5.composition;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tuple.Tuple;
import cascalog.CascalogFunction;
import org.json.simple.JSONValue;

import java.util.Map;

/**
* Created with IntelliJ IDEA.
* User: toby
* Date: 2/12/14
* Time: 8:48 AM
* To change this template use File | Settings | File Templates.
*/
public class ParseTransactionRecord extends CascalogFunction {

    public void operate(FlowProcess process, FunctionCall call) {
        String line = call.getArguments().getString(0);
        Map parsed = (Map) JSONValue.parse(line);
        Tuple tuple = new Tuple(
                parsed.get("buyer"),
                parsed.get("seller"),
                parsed.get("amt"),
                parsed.get("timestamp"));
        call.getOutputCollector().add(tuple);
    }
}
