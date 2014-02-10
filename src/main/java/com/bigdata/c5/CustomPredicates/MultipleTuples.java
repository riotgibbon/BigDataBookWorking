package com.bigdata.c5.CustomPredicates;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tuple.Tuple;
import cascalog.CascalogFunction;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/10/14
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultipleTuples {
    static List SENTENCE = Arrays.asList(
            Arrays.asList("The big dog"),
            Arrays.asList("data"));
    public static  void main(String [] args) throws Exception{

        Api.execute(new StdoutTap(),
                new Subquery("?s", "?w")
                .predicate(SENTENCE, "?s")
                .predicate(new CustomSplit(),"?s").out("?w"));


    }

    private static class CustomSplit extends CascalogFunction {
        @Override
        public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
            String sentence =functionCall.getArguments().getString(0);
            for (String word: sentence.split(" ")){
                functionCall.getOutputCollector().add(new Tuple((word)));
            }
        }
    }
}
