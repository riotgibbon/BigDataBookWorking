package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.BufferCall;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;
import cascalog.CascalogBuffer;
import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomBuffer {

    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
                new Subquery("?a", "?c")
                        .predicate(Data.VAL2, "?a", "?b")
                        .predicate(new SumBuffer(), "?b").out("?c"));

    }

    private static class SumBuffer extends CascalogBuffer {
        @Override
        public void operate(FlowProcess flowProcess, BufferCall bufferCall) {
            Iterator<TupleEntry> it = bufferCall.getArgumentsIterator();
            int total = 0;
            while (it.hasNext()){
                TupleEntry t = it.next();
                total+=t.getInteger(0);
            }
            bufferCall.getOutputCollector().add(new Tuple(total));
        }
    }
}
