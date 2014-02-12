package com.bigdata.c5.composition;

import cascading.flow.FlowProcess;
import cascading.operation.AggregatorCall;
import cascading.tuple.Tuple;
import cascalog.CascalogAggregator;
import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toby on 2/12/14.
 */
public class distinctFollowers {
    private static String result= "?distinctFollowers";
    private static String person= "?person";
    private static String followed = "?followed";

    public static  void main(String [] args) throws Exception{

        Api.execute(new StdoutTap(),
                new Subquery(result)
                        .predicate(Data.FOLLOWS, person, followed)
                        .predicate(new MyDiscountCount(), followed)
                        .out(result)
        );
    }

    private static class MyDiscountCount implements PredicateMacro{
        @Override
        public List<Predicate> getPredicates(Fields inFields, Fields outFields) {
            List<Predicate> ret = new ArrayList<Predicate>();
            ret.add(new Predicate(Option.SORT, inFields));
            ret.add(new Predicate(new DistinctCountAgg(), inFields, outFields));
            return ret;
        }


    }
    private static class DistinctCountAgg extends CascalogAggregator {
        static class State{
            int count=0;
            Tuple last =null;
        }

        @Override
        public void start(FlowProcess flowProcess, AggregatorCall call) {
            call.setContext(new State());
        }

        @Override
        public void aggregate(FlowProcess flowProcess, AggregatorCall call) {
            State state = (State) call.getContext();
            Tuple tuple = call.getArguments().getTupleCopy();
            if(state.last==null || !state.last.equals(tuple))
                state.count++;
            state.last = tuple;
        }

        @Override
        public void complete(FlowProcess flowProcess, AggregatorCall call) {
            State state= (State) call.getContext();
            call.getOutputCollector().add(new Tuple(state.count));
        }
    }
}
