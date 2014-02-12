package com.bigdata.c5.composition;

import cascalog.ops.RandLong;
import com.bigdata.c5.Data;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Option;
import jcascalog.Subquery;
import jcascalog.op.Limit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toby on 2/12/14.
 */
public class random {
    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),  fixedRandomSample(chains.chainsLengthN(Data.CHAINS, 2), 2) ) ;
    }
    
    public static Subquery fixedRandomSample(Object data, int n){
        List<String> inputVars = new ArrayList<String>();
        List<String> outputVars = new ArrayList<String>();
        
        for (int i=0; i < Api.numOutFields(data); i++){
            inputVars.add(Api.genNullableVar());
            outputVars.add(Api.genNullableVar());
        }

        String randVar = Api.genNullableVar();
        return  new Subquery(outputVars)
                .predicate(data, inputVars)
                .predicate(new RandLong(), randVar)
                .predicate(Option.SORT, randVar)
                .predicate( new Limit(n), inputVars).out(outputVars);
    }
}
