package com.bigdata.c5.composition;

import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Subquery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toby
 * Date: 2/12/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class chains {
    public static List CHAINS = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(2, 3),
            Arrays.asList(3, 4),
            Arrays.asList(6, 7),
            Arrays.asList(7, 8),
            Arrays.asList(8, 9),
            Arrays.asList(7, 10),
            Arrays.asList(10, 11)
    );
    private static final String a = "?a";
    private static final String b = "?b";
    private static final String c = "?c";
    private static final String d = "?d";

    public static  void main(String [] args) throws Exception{
        Api.execute(new StdoutTap(),  chainsLengthN(CHAINS,4) ) ;
    }

    public static Subquery chainsLength3(Object pairs){
        return new Subquery(a, b, c)
                .predicate(pairs, a, b)
                .predicate(pairs, b, c);
    }

    public static Subquery chainsLength4(Object pairs){
        return new Subquery(a, b, c,d )
                .predicate(pairs, a, b)
                .predicate(pairs, b, c)
                .predicate(pairs,  c, d)
                ;
    }
    public static  Subquery  chainsLengthN(Object pairs, int n){
        List<String> genVars = new ArrayList<String>();
        for (int i=0; i<n; i++) genVars.add(Api.genNullableVar());
        Subquery ret = new Subquery(genVars);
        for(int i=0; i<n-1;i++)
            ret  = ret.predicate(pairs, genVars.get(i), genVars.get(i+1));
        return ret;
    }


}
