package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.OperationCall;
import cascalog.ParallelAgg;
import com.bigdata.c5.PersonData;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomParallel {
    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),
                new Subquery("?a", "?c")
                        .predicate(PersonData.VAL2, "?a", "?b")
                        .predicate(new SumParallel(), "?b").out("?c"));

    }

    private static class SumParallel implements ParallelAgg {
        @Override
        public void prepare(FlowProcess flowProcess, OperationCall operationCall) {

        }

        @Override
        public List<Object> init(List<Object> objects) {
            return objects;
        }

        @Override
        public List<Object> combine(List<Object> input1, List<Object> input2) {
            int val1 = (Integer) input1.get(0);
            int val2 = (Integer) input2.get(0);
            return Arrays.asList((Object)(val1 + val2));
        }
    }
}
