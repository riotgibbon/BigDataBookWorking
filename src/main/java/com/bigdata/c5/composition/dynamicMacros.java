package com.bigdata.c5.composition;

import com.bigdata.c5.CustomPredicates.IncrementFunction;
import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.*;
import jcascalog.op.Plus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by toby on 2/13/14.
 */
public class dynamicMacros {
    private static final String a = "?a";
    private static final String b = "?b";
    private static final String c = "?c";
    private static final String x = "?x";
    private static final String y = "?y";
    private static final String z = "?z";

    public static  void main(String [] args) throws Exception{

        Subquery repeating = new Subquery(x, y, z)
                .predicate(Data.TRIPLETS, a, b, c)
                .predicate(new IncrementFunction(), a).out(x)
                .predicate(new IncrementFunction(), b).out(y)
                .predicate(new IncrementFunction(), c).out(z);

        Subquery each = new Subquery(x, y, z)
                .predicate(Data.TRIPLETS, a, b, c)
                .predicate(new Each(new IncrementFunction()), a, b, c)
                .out(x, y, z);

        Subquery partial = new Subquery(x, y, z)
                .predicate(Data.TRIPLETS, a, b, c)
                .predicate(new Each(new Partial(new Plus(),1)), a, b, c)
                .out(x, y, z);

        Api.execute(new StdoutTap(),partial );



    }
    private static class Each implements PredicateMacro{
        Object _op;

        Each(Object op) {
            this._op = op;
        }

        @Override
        public List<Predicate> getPredicates(Fields inFields, Fields outFields) {
            List<Predicate> ret = new ArrayList<Predicate>();
            for (int i=0; i<inFields.size(); i++){
                Object in = inFields.get(i);
                Object out = outFields.get(i);
                ret.add(new Predicate(_op,
                        Arrays.asList(in),
                        Arrays.asList(out)
                ));
            }
            return ret;
        }
    }

    private static class Partial implements PredicateMacro {

        private Object op;
        private List<Object> args;

        public Partial(Object op, Object... args) {
            this.op = op;
            this.args =Arrays.asList(args);
        }

        @Override
        public List<Predicate> getPredicates(Fields inFields, Fields outFields) {
            List<Predicate> ret = new ArrayList<Predicate>();
            List<Object> input = new ArrayList<Object>();
            input.addAll(args);
            input.addAll(inFields);
            ret.add(new Predicate(op,input, outFields));
            return null;
        }
    }
}
